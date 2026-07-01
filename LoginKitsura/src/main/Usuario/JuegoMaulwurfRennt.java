package main.Usuario;

import java.awt.event.*;
import java.util.List;
import javax.swing.*;

// Clase controladora que gestiona la lógica del minijuego "MaulwurfRennt       ".
// Conecta la interfaz gráfica, el estado de la sesión y las consultas a la base de datos.
public class JuegoMaulwurfRennt implements JuegoBase {

    // Referencia a la ventana de la interfaz gráfica.
    private MaulwurfRennt vista;

    // Instancias de acceso a datos para preguntas, persistencia de partidas y sistema de pistas.
    private PreguntaDAO_MaulwurfRennt preguntaDAO;
    private PartidaDAO_MaulwurfRennt partidaDAO;
    private AyudaDAO_MaulwurfRennt ayudaDAO;

    // Identificadores para el control de sesión del usuario, la partida actual y la categoría de estudio.
    private int idUsuario;
    private int idPartida;
    private int idCategoria;
    private final int idMinijuego = 3;

    // Variables de estado del flujo de juego: vidas, puntuación, tiempo acumulado y progresión de niveles.
    private int vidas = 3;
    private int puntos = 0;
    private int tiempoTotalJugado = 0;
    private int nivel = 1;
    private int preguntasContestadas = 0;
    private int preguntasNivel = 0;
    private int tiempoRestante;

    // Componente de control de tiempo y bandera booleana para evitar colisiones por múltiples clics del usuario.
    private Timer timer;
    private boolean procesandoRespuesta = false;

    // Modelos que almacenan los datos de la pregunta en pantalla y sus respectivas opciones de respuesta.
    private Pregunta_MaulwurfRennt preguntaActual;
    private List<OpcionRespuesta_MaulwurfRennt> opcionesActuales;
    private java.util.ArrayList<Integer> preguntasUsadas = new java.util.ArrayList<>();

    // Constructor de la clase: Inicializa variables de sesión, dependencias DAO, registra la partida en la base de datos y arranca el primer nivel.
    public JuegoMaulwurfRennt(int idUsuario, int idCategoria) {

        this.idUsuario = idUsuario;
        this.idCategoria = idCategoria;

        vista = new MaulwurfRennt();
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
    }

    // Configura las métricas del juego (dificultad, topos concurrentes en pantalla y límite de tiempo) basándose en el nivel actual.
    private void iniciarNivel() {

        preguntasNivel = 0;

        switch (nivel) {

            case 1:
                vista.actualizarNivel(1);
                vista.actualizarDificultad("Fácil");
                vista.mostrarTopos(5);
                tiempoRestante = 60;
                break;

            case 2:
                vista.actualizarNivel(2);
                vista.actualizarDificultad("Intermedio");
                vista.mostrarTopos(6);
                tiempoRestante = 45;
                break;

            case 3:
                vista.actualizarNivel(3);
                vista.actualizarDificultad("Difícil");
                vista.mostrarTopos(7);
                tiempoRestante = 30;
                break;
        }

        iniciarTemporizador();
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
                break;
            case 2:
                dificultad = "Intermedio";
                break;
            default:
                dificultad = "Difícil";
                break;
        }

        preguntaActual = preguntaDAO.obtenerPreguntaAleatoria(
                idCategoria,
                dificultad,
                preguntasUsadas);

        if (preguntaActual == null) {
            vista.mostrarMensaje("No existen preguntas para este nivel.");
            return;
        }

        preguntasUsadas.add(preguntaActual.getIdPregunta());

        // Modifica aleatoriamente la distribución en el tablero según el volumen de topos admitidos.
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

        // Mapea y distribuye el texto de cada opción de respuesta sobre los letreros de los topos.
        for (int i = 0; i < opcionesActuales.size() && i < 7; i++) {
            vista.colocarRespuesta(
                    i,
                    opcionesActuales.get(i).getTextoOpcion());
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
                    Timer espera = new Timer(1000, ev -> {
                        vista.restaurarTopo(indice);
                        verificarRespuesta(indice);
                        procesandoRespuesta = false;
                    });

                    espera.setRepeats(false);
                    espera.start();
                }
            });
        }

        vista.getBtnAyuda().addActionListener(e -> mostrarAyuda());
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

        puntos += preguntaActual.getPuntosBase();
        preguntasContestadas++;
        preguntasNivel++;

        partidaDAO.guardarDetalle(
                idPartida,
                preguntaActual.getIdPregunta(),
                preguntaActual.getPuntosBase(),
                0,
                true
        );

        if (timer != null) {
            timer.stop();
        }

        vista.mostrarMensaje("¡Respuesta correcta!");
        vista.restaurarTodosLosTopos();

        if (timer != null) {
            timer.start();
        }

        comprobarNivel();
    }

    // Gestiona las consecuencias de un error: resta vidas, penaliza puntaje, guarda historial en BD y evalúa la condición de fin de juego.
    private void responderIncorrecto() {

        vidas--;

        if (puntos >= 10) {
            puntos -= 10;
        } else {
            puntos = 0;
        }

        vista.actualizarVidas(vidas);

        partidaDAO.guardarDetalle(
                idPartida,
                preguntaActual.getIdPregunta(),
                -10,
                0,
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

            puntos = Math.max(0, puntos - 10);
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

        partidaDAO.finalizarPartida(
                idPartida,
                puntos,
                tiempoTotalJugado,
                estado);

        partidaDAO.actualizarEstadisticas(
                idUsuario,
                idMinijuego,
                puntos,
                tiempoTotalJugado);

        if (estado.equals("completada")) {
            vista.mostrarMensaje("¡Felicidades!\n\nHas completado el minijuego.\n\nPuntuación: " + puntos);
            vista.dispose();
        } else {
            vista.dispose();
            if (vidas <= 0) {
                new SeAcaboVidas(this, null);
            } else {
                new SeAcaboTiempo(this, null);
            }
        }
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