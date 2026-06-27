package main.Usuario;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import javax.swing.Timer;

public class JuegoMaulwurfRennt {

    // VISTA
    private MaulwurfRennt vista;

    // BASE DE DATOS
    private PreguntaDAO_MaulwurfRennt preguntaDAO;
    private PartidaDAO_MaulwurfRennt partidaDAO;

    // PARTIDA
    private int idUsuario;
    private int idPartida;
    private final int idMinijuego = 3;

    // JUEGO
    private int vidas = 3;
    private int puntos = 0;

    private int nivel = 1;

    private int preguntasContestadas = 0;
    private int preguntasNivel = 0;

    private int tiempoRestante;

    private Timer timer;

    // PREGUNTA ACTUAL
    private Pregunta_MaulwurfRennt preguntaActual;

    private List<OpcionRespuesta_MaulwurfRennt> opcionesActuales;

    // CONSTRUCTOR
    public JuegoMaulwurfRennt(int idUsuario) {

        this.idUsuario = idUsuario;

        vista = new MaulwurfRennt();

        preguntaDAO = new PreguntaDAO_MaulwurfRennt();

        partidaDAO = new PartidaDAO_MaulwurfRennt();

        idPartida = partidaDAO.crearPartida(
                idUsuario,
                idMinijuego,
                vidas);

        iniciarNivel();

        registrarEventos();

    }

    // INICIAR NIVEL
    private void iniciarNivel() {

        preguntasNivel = 0;

        switch (nivel) {

            case 1:

                vista.actualizarNivel(1);

                vista.actualizarDificultad("Fácil");

                vista.mostrarTopos(2);

                tiempoRestante = 60;

                break;

            case 2:

                vista.actualizarNivel(2);

                vista.actualizarDificultad("Intermedio");

                vista.mostrarTopos(4);

                tiempoRestante = 45;

                break;

            case 3:

                vista.actualizarNivel(3);

                vista.actualizarDificultad("Difícil");

                vista.mostrarTopos(6);

                tiempoRestante = 30;

                break;

        }

        iniciarTemporizador();

        cargarPregunta();

    }

    // TEMPORIZADOR
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

    // FORMATO DEL TIEMPO
    private String formatearTiempo() {

        int minutos = tiempoRestante / 60;

        int segundos = tiempoRestante % 60;

        return String.format("%02d:%02d", minutos, segundos);

    }

    // CARGAR PREGUNTA
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

        preguntaActual = preguntaDAO.obtenerPreguntaAleatoria(dificultad);

        if (preguntaActual == null) {

            vista.mostrarMensaje(
                    "No existen preguntas para este nivel.");

            return;

        }

        vista.actualizarPregunta(
                preguntaActual.getPregunta());

        vista.limpiarRespuestas();

        opcionesActuales
                = preguntaActual.getOpciones();

        for (int i = 0;
                i < opcionesActuales.size() && i < 6;
                i++) {

            vista.colocarRespuesta(
                    i,
                    opcionesActuales
                            .get(i)
                            .getTextoOpcion());
        }

    }

    // REGISTRAR EVENTOS
    private void registrarEventos() {

        for (int i = 0; i < 6; i++) {

            final int indice = i;

            vista.getTopo(i).addMouseListener(new MouseAdapter() {

                @Override
                public void mouseClicked(MouseEvent e) {

                    verificarRespuesta(indice);

                }

            });

        }

    }

    // VERIFICAR RESPUESTA
    private void verificarRespuesta(int indiceTopo) {

        if (indiceTopo >= opcionesActuales.size()) {
            return;
        }

        OpcionRespuesta_MaulwurfRennt opcion
                = opcionesActuales.get(indiceTopo);

        if (opcion.isCorrecta()) {

            responderCorrecto();

        } else {

            responderIncorrecto();

        }

    }

    // RESPUESTA CORRECTA
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

        comprobarNivel();

    }

    // RESPUESTA INCORRECTA
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

            puntos = 0;

            terminarJuego("abandonada");

            return;

        }

        cargarPregunta();

    }

    // CAMBIO DE NIVEL
    private void comprobarNivel() {

        if (preguntasNivel >= 5) {

            timer.stop();

            if (nivel < 3) {

                nivel++;

                iniciarNivel();

            } else {

                terminarJuego("completada");

            }

        } else {

            cargarPregunta();

        }

    }

    // PIERDE POR TIEMPO
    private void perderNivel() {

        vista.mostrarMensaje(
                "Se terminó el tiempo.");

        terminarJuego("abandonada");

    }

    // TERMINAR JUEGO
    private void terminarJuego(String estado) {

        if (timer != null) {
            timer.stop();
        }

        vista.bloquearTopos();

        // Si perdió todas las vidas pierde todos los puntos
        if (vidas <= 0) {
            puntos = 0;
        }

        int tiempoJugado = calcularTiempoJugado();

        partidaDAO.finalizarPartida(
                idPartida,
                puntos,
                tiempoJugado,
                estado);

        partidaDAO.actualizarEstadisticas(
                idUsuario,
                idMinijuego,
                puntos,
                tiempoJugado);

        if (estado.equals("completada")) {

            vista.mostrarMensaje(
                    "¡Felicidades!\n\nHas completado el minijuego.\n\nPuntuación: " + puntos);

        } else {

            vista.mostrarMensaje(
                    "Fin del juego.\n\nPuntuación: " + puntos);

        }

        vista.dispose();

    }

    // CALCULAR TIEMPO JUGADO
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

}
