package main.Usuario;

import java.awt.*;
import java.awt.event.*;
import java.util.List;
import javax.swing.*;

// Clase controladora que gestiona la lógica del minijuego "MaulwurfRennt       ".
// Conecta la interfaz gráfica, el estado de la sesión y las consultas a la base de datos.
public class JuegoMaulwurfRennt implements JuegoBase {

    // Referencia a la ventana de la interfaz gráfica.
    private final MaulwurfRennt vista;

    // Instancias de acceso a datos para preguntas, persistencia de partidas y sistema de pistas.
    private final PreguntaDAO_MaulwurfRennt preguntaDAO;
    private final PartidaDAO_MaulwurfRennt partidaDAO;
    private final AyudaDAO_MaulwurfRennt ayudaDAO;

    // Identificadores para el control de sesión del usuario, la partida actual y la categoría de estudio.
    private final int idUsuario;
    private final int idPartida;
    private final int idCategoria;
    private final int idMinijuego = 3;

    // Variables de estado del flujo de juego: vidas, puntuación, tiempo acumulado y progresión de niveles.
    // "vidas" ya no arranca fija en 3: se calcula en el constructor a partir de VidasDAO.
    private int vidas;
    // Tope máximo de vidas configurado por el administrador (para saber si la
    // partida fue "perfecta" y para dibujar la cantidad correcta de corazones).
    private int maxVidas;

    // "puntos" es el puntaje VISIBLE en pantalla: sube con aciertos y baja
    // con la penalización de -10 por cada error (puede llegar a 0).
    private int puntos = 0;

    // NUEVO: "puntosGanadosTotales" es un acumulador independiente que SOLO
    // suma (nunca resta ni se resetea a 0). Es el valor real que se manda a
    // Partida.puntuacion y a Estadistica al terminar la partida, para que
    // los aciertos obtenidos antes de perder no se pierdan si la
    // penalización de un error deja "puntos" en 0 justo al momento de morir.
    private int puntosGanadosTotales = 0;

    private int tiempoTotalJugado = 0;
    private int nivel = 1;
    private int preguntasContestadas = 0;
    private int preguntasNivel = 0;
    private int tiempoRestante;
    private int tiempoMaximoPregunta;

    // Componente de control de tiempo y bandera booleana para evitar colisiones por múltiples clics del usuario.
    private Timer timer;
    private boolean procesandoRespuesta = false;

    // Modelos que almacenan los datos de la pregunta en pantalla y sus respectivas opciones de respuesta.
    private Pregunta_MaulwurfRennt preguntaActual;
    private List<OpcionRespuesta_MaulwurfRennt> opcionesActuales;
    private final java.util.ArrayList<Integer> preguntasUsadas = new java.util.ArrayList<>();

    // Constructor de la clase: Inicializa variables de sesión, dependencias DAO, registra la partida en la base de datos y arranca el primer nivel.
    public JuegoMaulwurfRennt(int idUsuario, int idCategoria) {

        this.idUsuario = idUsuario;
        this.idCategoria = idCategoria;

        // Se consulta a la base de datos (VidasAdmin -> VidasDAO) cuántas vidas
        // corresponden a este minijuego/categoría en dificultad "Fácil" (con la
        // que siempre arranca la partida). Antes este valor venía fijo en 3.
        maxVidas = resolverVidasIniciales();
        vidas = maxVidas;

        vista = new MaulwurfRennt(maxVidas);
        preguntaDAO = new PreguntaDAO_MaulwurfRennt();
        partidaDAO = new PartidaDAO_MaulwurfRennt();
        ayudaDAO = new AyudaDAO_MaulwurfRennt();

        // Registra el inicio de la partida en la base de datos y obtiene su ID asignado.
        idPartida = partidaDAO.crearPartida(
                idUsuario,
                idMinijuego,
                vidas);

        iniciarNivel();
        registrarEventos();
        preguntasNivel = 0;
        vista.actualizarProgreso(0, 5);
        vista.actualizarCategoria(obtenerNombreCategoria());
        vista.actualizarNivel(nivel);
        vista.actualizarDificultad("Fácil");
        vista.actualizarPuntos(puntos);
    }

    /*------------------ RESOLVER VIDAS INICIALES ------------------
      Consulta en la base de datos (misma tabla que administra VidasAdmin)
      cuántas vidas corresponden al minijuego "Maulwurf Rennt", la categoría
      seleccionada y la dificultad "Fácil" (nivel inicial).
    ------------------------------------------------------------------*/
    private int resolverVidasIniciales() {
        VidasDAO vidasDAO = new VidasDAO();

        String categoria = obtenerNombreCategoria();

        int vidasConfiguradas = vidasDAO.obtenerVidas("Maulwurf Rennt", categoria, "Fácil");

        // Resguardo: si el nombre no coincide con la BD, se usa 3 por defecto.
        if (vidasConfiguradas < 1) {
            vidasConfiguradas = 3;
        }

        return vidasConfiguradas;
    }

    // Configura las métricas del juego (dificultad, topos concurrentes en pantalla y límite de tiempo) basándose en el nivel actual.
    private void iniciarNivel() {

        preguntasNivel = 0;

        switch (nivel) {

            case 1:
                vista.actualizarNivel(1);
                vista.actualizarDificultad("Fácil");
                vista.mostrarTopos(5);
                vista.actualizarCategoria(obtenerNombreCategoria());
                vista.mostrarBotonAyuda(false);

                break;

            case 2:
                vista.actualizarNivel(2);
                vista.actualizarDificultad("Intermedio");
                vista.mostrarTopos(6);
                vista.cambiarColorFondo(new Color(239, 218, 154));
                vista.actualizarCategoria(obtenerNombreCategoria());
                vista.mostrarBotonAyuda(false);
                break;

            case 3:
                vista.actualizarNivel(3);
                vista.actualizarDificultad("Difícil");
                vista.mostrarTopos(7);
                vista.cambiarColorFondo(new Color(255, 180, 80));
                vista.actualizarCategoria(obtenerNombreCategoria());
                vista.mostrarBotonAyuda(true);
                break;
        }

        cargarPregunta();
    }

    // Inicializa o reinicia el reloj de cuenta regresiva, descontando segundos y evaluando la derrota por tiempo agotado.
    private void iniciarTemporizador() {

        if (timer != null) {
            timer.stop();
        }

        vista.actualizarTiempo(formatearTiempo());

        timer = new Timer(1000, e -> {
            tiempoRestante--;
            vista.actualizarTiempo(formatearTiempo());

            if (tiempoRestante <= 0) {
                timer.stop();
                perderNivel();
            }
        });

        timer.start();
    }

    // Convierte el valor numérico de segundos restantes en una cadena con formato cronométrico legible MM:SS.
    private String formatearTiempo() {

        int minutos = tiempoRestante / 60;
        int segundos = tiempoRestante % 60;

        return String.format("%02d:%02d", minutos, segundos);
    }

    // Solicita una nueva pregunta aleatoria sin repetir a la base de datos, reorganiza la posición de los topos e inyecta las respuestas en la interfaz.
    private void cargarPregunta() {

        String dificultad;

        switch (nivel) {

            case 1:
                dificultad = "Fácil";
                tiempoMaximoPregunta = 25;
                tiempoRestante = 25;
                break;

            case 2:
                dificultad = "Intermedio";
                tiempoMaximoPregunta = 20;
                tiempoRestante = 20;
                break;

            case 3:
                dificultad = "Difícil";
                tiempoMaximoPregunta = 15;
                tiempoRestante = 15;
                break;

            default:
                dificultad = "Fácil";
                tiempoMaximoPregunta = 25;
                tiempoRestante = 25;
                break;
        }

        iniciarTemporizador();

        preguntaActual = preguntaDAO.obtenerPreguntaAleatoria(
                idCategoria,
                dificultad,
                preguntasUsadas);

        if (preguntaActual == null) {
            vista.mostrarMensaje("No existen preguntas para este nivel.");
            return;
        }

        preguntasUsadas.add(preguntaActual.getIdPregunta());

        switch (nivel) {
            case 1:
                vista.mezclarTopos(5);
                break;
            case 2:
                vista.mezclarTopos(6);

                break;
            case 3:
                vista.mezclarTopos(7);
                break;
        }

        vista.actualizarPregunta(preguntaActual.getPregunta());
        vista.limpiarRespuestas();

        opcionesActuales = preguntaActual.getOpciones();

        for (int i = 0; i < opcionesActuales.size() && i < 7; i++) {
            vista.colocarRespuesta(
                    i,
                    opcionesActuales.get(i).getTextoOpcion());
            vista.animarEntradaTopos();
        }
    }

    // Asigna escuchadores de eventos del mouse (MouseListener) a cada topo y el listener de acción al botón de asistencia.
    private void registrarEventos() {

        for (int i = 0; i < 7; i++) {

            final int indice = i;

            vista.getTopo(i).addMouseListener(new MouseAdapter() {

                @Override
                public void mouseClicked(MouseEvent e) {

                    if (procesandoRespuesta) {
                        return;
                    }

                    procesandoRespuesta = true;
                    vista.golpearTopo(indice);

                    // Crea una pausa visual de un segundo para mostrar la animación del topo herido antes de procesar el resultado.
                    Timer espera = new Timer(300, ev -> {

                        // El topo baja al agujero
                        vista.ocultarTopo(indice);

                        // Comprueba la respuesta
                        verificarRespuesta(indice);

                        procesandoRespuesta = false;

                    });

                    espera.setRepeats(false);
                    espera.start();
                }
            });
        }

        vista.getBtnAyuda().addActionListener(e -> mostrarAyuda());

        // NOTA: no hay botón de estadísticas aquí. Las estadísticas son
        // GLOBALES y se acceden una sola vez desde MenuPrincipal.
    }

    // Recupera la opción ligada al topo golpeado y evalúa si corresponde a la respuesta correcta o incorrecta.
    private void verificarRespuesta(int indiceTopo) {

        if (opcionesActuales == null || indiceTopo >= opcionesActuales.size()) {
            return;
        }

        OpcionRespuesta_MaulwurfRennt opcion = opcionesActuales.get(indiceTopo);

        if (opcion.isCorrecta()) {
            responderCorrecto();
        } else {
            responderIncorrecto();
        }
    }

    // Gestiona las consecuencias de un acierto: asigna puntaje, guarda el registro detallado en BD, notifica al usuario y valida si avanza de nivel.
    private void responderCorrecto() {

        int puntosGanados = calcularPuntosPorTiempo();

        // Tiempo real que tardó el jugador en responder esta pregunta.
        int tiempoUsado = tiempoMaximoPregunta - tiempoRestante;
        if (tiempoUsado < 0) {
            tiempoUsado = tiempoMaximoPregunta;
        }

        puntos += puntosGanados;

        // NUEVO: el acumulador que se guardará en BD también suma aquí.
        puntosGanadosTotales += puntosGanados;

        vista.actualizarPuntos(puntos);
        preguntasContestadas++;
        preguntasNivel++;

        vista.actualizarProgreso(preguntasNivel, 5);

        partidaDAO.guardarDetalle(
                idPartida,
                preguntaActual.getIdPregunta(),
                puntosGanados,
                tiempoUsado,
                true
        );

        if (timer != null) {
            timer.stop();
        }

        vista.mostrarMensaje("¡Respuesta correcta!");
        vista.restaurarTodosLosTopos();

        comprobarNivel();
    }

    // Gestiona las consecuencias de un error: resta vidas, penaliza puntaje, guarda historial en BD y evalúa la condición de fin de juego.
    private void responderIncorrecto() {

        vidas--;

        // Esta penalización de -10 SOLO afecta el puntaje visible en
        // pantalla (feedback inmediato al jugador). NO afecta
        // "puntosGanadosTotales", que es lo que realmente se guarda en
        // Estadistica -- así los aciertos ya ganados en esta partida no
        // desaparecen aunque "puntos" quede en 0 justo antes de perder.
        if (puntos >= 10) {
            puntos -= 10;
        } else {
            puntos = 0;
        }
        vista.actualizarPuntos(puntos);
        vista.actualizarVidas(vidas);

        // Tiempo real que tardó el jugador en responder esta pregunta
        int tiempoUsado = tiempoMaximoPregunta - tiempoRestante;
        if (tiempoUsado < 0) {
            tiempoUsado = tiempoMaximoPregunta;
        }

        partidaDAO.guardarDetalle(
                idPartida,
                preguntaActual.getIdPregunta(),
                0,
                tiempoUsado,
                false
        );

        if (vidas <= 0) {
            terminarJuego("abandonada");
            return;
        }

        if (timer != null) {
            timer.stop();
        }

        vista.mostrarMensaje("Respuesta incorrecta");
        vista.restaurarTodosLosTopos();

        if (timer != null) {
            timer.start();
        }

        cargarPregunta();
    }

    // Detiene el tiempo temporalmente para ofrecer ayuda escrita al usuario, penalizando su puntaje global tras su confirmación.
    private void mostrarAyuda() {

        if (timer != null) {
            timer.stop();
        }

        int opcion = JOptionPane.showConfirmDialog(
                vista,
                "Si utilizas una pista se descontarán 10 puntos.\n\n¿Deseas continuar?",
                "Advertencia",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.WARNING_MESSAGE);

        if (opcion == JOptionPane.OK_OPTION) {

            // NOTA: al igual que en responderIncorrecto(), esta penalización
            // por usar pista solo afecta el puntaje visible ("puntos"), no
            // "puntosGanadosTotales". Usar una pista no debería borrar
            // aciertos ya ganados del acumulado que se guarda en BD.
            puntos = Math.max(0, puntos - 10);
            vista.actualizarPuntos(puntos);

            String pista = ayudaDAO.obtenerPistaTexto(preguntaActual.getIdPregunta());
            PistasTexto ventana = new PistasTexto(pista);

            // Reanuda el conteo del temporizador en el momento exacto en que la ventana de pistas sea cerrada.
            ventana.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosed(java.awt.event.WindowEvent e) {
                    if (timer != null) {
                        timer.start();
                    }
                }
            });

        } else {
            if (timer != null) {
                timer.start();
            }
        }
    }

    // Controla la progresión de las rondas: si se alcanzan 5 respuestas correctas, incrementa la dificultad o completa el juego.
    private void comprobarNivel() {

        if (preguntasNivel >= 5) {

            timer.stop();

            if (nivel < 3) {
                nivel++;
                vista.setVisible(false);
                new PantallaDificultad(vista, nivel);
                iniciarNivel();
            } else {
                terminarJuego("completada");
            }

        } else {
            cargarPregunta();
        }
    }

    // Rutina de derrota rápida invocada cuando el contador de tiempo llega a cero.
    private void perderNivel() {
        terminarJuego("abandonada");
    }

    // Cierra el ciclo del juego, computa tiempos finales, actualiza las tablas estadísticas generales y despliega pantallas de desenlace según el estado.
    private void terminarJuego(String estado) {

        if (timer != null) {
            timer.stop();
        }

        tiempoTotalJugado = calcularTiempoJugado();

        // NUEVO: se usa "puntosGanadosTotales" (solo suma, nunca se resetea)
        // en vez de "puntos" (el visible, que puede haber quedado en 0 por
        // la penalización del último error). Así la partida y las
        // estadísticas siempre reflejan lo que el jugador realmente ganó,
        // tanto si terminó en victoria como en derrota.
        partidaDAO.finalizarPartida(
                idPartida,
                puntosGanadosTotales,
                tiempoTotalJugado,
                estado);

        partidaDAO.actualizarEstadisticas(
                idUsuario,
                idMinijuego,
                puntosGanadosTotales,
                tiempoTotalJugado);

        if (estado.equals("completada")) {

            if (vidas == maxVidas) {

                System.out.println("Entró a Victoria Perfecta");

                VictoriaPerfecta vp = new VictoriaPerfecta(this);

                vista.setContentPane(vp.getFondo());
                vista.revalidate();
                vista.repaint();

            } else {

                System.out.println("Entró a Victoria");

                Victoria v = new Victoria(this);

                vista.setContentPane(v.getFondo());
                vista.revalidate();
                vista.repaint();
            }

        } else {

            vista.dispose();

            if (vidas <= 0) {

                new SeAcaboVidas(this, null);

            } else {

                new SeAcaboTiempo(this, null);

            }
        }
        //vista.dispose();

    }
    // Calcula de forma inversa los segundos transcurridos en la partida restando el tiempo restante del límite de cada nivel.

    private int calcularTiempoJugado() {

        switch (nivel) {
            case 1:
                return 60 - tiempoRestante;
            case 2:
                return 45 - tiempoRestante;
            case 3:
                return 30 - tiempoRestante;
            default:
                return 0;
        }
    }

    private int calcularPuntosPorTiempo() {

        int tiempoUsado = tiempoMaximoPregunta - tiempoRestante;

        if (tiempoUsado <= 7) {
            return preguntaActual.getPuntosBase();
        }

        double porcentajeRapidez
                = 1.0 - ((double) tiempoUsado / tiempoMaximoPregunta);

        int puntos
                = (int) (preguntaActual.getPuntosBase() * porcentajeRapidez);

        if (puntos < 10) {
            puntos = 10;
        }

        return puntos;
    }

    private String obtenerNombreCategoria() {

        switch (idCategoria) {

            case 7:
                return "Operaciones Básicas";

            case 8:
                return "Operaciones Avanzadas";

            case 9:
                return "Científicos Matemáticos";

            default:
                return "Sin categoría";

        }

    }

    // Implementaciones de la interfaz JuegoBase para la navegación, reinicio de componentes y retorno de metadatos del juego.
    @Override
    public void reiniciar() {
        vista.dispose();
        new JuegoMaulwurfRennt(idUsuario, idCategoria);
    }

    @Override
    public void irAlMenu() {
        vista.dispose();
        new MenuPrincipal();
    }

    @Override
    public void jugarDeNuevo() {
        reiniciar();
    }

    @Override
    public void mostrarResultadoConFade() {
        ResultadoFinal resultado = new ResultadoFinal(
                this,
                puntos,
                tiempoTotalJugado);
        resultado.mostrar();
    }

    @Override
    public javax.swing.JFrame getFrame() {
        return vista;
    }

    @Override
    public int getPuntajeTotal() {
        return puntos;
    }

    @Override
    public int getTiempoTotalJugado() {
        return calcularTiempoJugado();
    }
}   