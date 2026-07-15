//-------------------------- HIDDEN FOX CÓDIGO ----------------------
package main.Usuario;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;
import java.util.ArrayList;

public class HiddenFox_Codigo extends HiddenFox implements JuegoBase {

    //--------------- A T R I B U T O S ---------------
    //--------------- C O L O R E S
    private Color facil = new Color(178, 197, 178);
    private Color intermedio = new Color(239, 218, 154);
    private Color dificil = new Color(227, 157, 139);

    //--------------- T I E M P O
    int segundosRestantes;

    private HiddenFoxDAO dao = new HiddenFoxDAO();  

    private ArrayList<Integer> preguntasPartida = new ArrayList<>();
    private int preguntaActual = 0;
    private int vidas;
    private int nivelActual;
    private int nivelFinal;
    private int puntos = 0;
    private boolean usoPista = false;

    private Timer countdown;

    private int penalizacion;
    private int tiempoTotalJugado = 0;
    private int tiempoMaximoPregunta;
    private PantallaDificultad pantallaDificultad;

    private static final int CORRECTAS = 5;
    private int respuestas_Correctas = 0;
    private final int puntajeMaximo = CORRECTAS * 3 * 100;
    private boolean partidaTerminada = false;

    // Id del minijuego "Hidden Fox" según la tabla Minijuego
    private static final int ID_MINIJUEGO = 1;

    // Usuario logueado, obtenido de la sesión guardada por IniciarSesion
    private int idUsuario;

    // Id de la partida en curso (fila de la tabla Partida). -1 mientras no se ha creado.
    private int idPartida = -1;

    // NUEVO: indica si la sesión actual es de un invitado. Cuando es true,
    // este minijuego NO escribe absolutamente nada en la base de datos.
    private boolean esInvitado;

    //---------------- CONSTRUCTOR ----------------
    public HiddenFox_Codigo(int nivel, int vidas, int puntos, boolean usoPista, int tiempoTotalJugado) {
        super(vidas);

        this.nivelActual = nivel;
        this.vidas = vidas;
        this.puntos = puntos;
        this.usoPista = usoPista;
        this.tiempoTotalJugado = tiempoTotalJugado;

        // NUEVO: se consulta una sola vez si la sesión actual es invitado.
        this.esInvitado = Sesion.isEsInvitado();
        this.idUsuario = Sesion.getIdUsuarioActual();

        if (nivelActual >= 1 && nivelActual <= 3) {
            nivelFinal = 3;
        } else if (nivelActual >= 4 && nivelActual <= 6) {
            nivelFinal = 6;
        } else {
            nivelFinal = 9;
        }

        //DEBUG
        System.out.println("Nivel actual: " + nivelActual);
        System.out.println("Nivel final: " + nivelFinal);
        //DEBUG
        Partida(nivelActual);
    }

    public HiddenFox_Codigo(int nivel) {
        this(nivel, resolverVidasIniciales(nivel), 0, false, 0);
    }

    private static int resolverVidasIniciales(int nivel) {
        HiddenFoxDAO daoTemporal = new HiddenFoxDAO();
        VidasDAO vidasDAO = new VidasDAO();

        String categoria = daoTemporal.obtenerCategoria(mapearIdCategoria(nivel));
        String dificultad = mapearDificultad(nivel);

        int vidas = vidasDAO.obtenerVidas("Hidden Fox", categoria, dificultad);

        if (vidas < 1) {
            vidas = 3;
        }

        return vidas;
    }

    private static int mapearIdCategoria(int nivel) {
        if (nivel >= 1 && nivel <= 3) {
            return 1;
        } else if (nivel >= 4 && nivel <= 6) {
            return 2;
        } else {
            return 3;
        }
    }

    private static String mapearDificultad(int nivel) {
        int posicion = ((nivel - 1) % 3) + 1;

        switch (posicion) {
            case 1:
                return "Fácil";
            case 2:
                return "Intermedio";
            default:
                return "Difícil";
        }
    }

    //------------- SIGUIENTE PREGUNTA ------------
    public void SiguientePregunta() {
        System.out.println("Comparando: " + nivelActual + " < " + nivelFinal);

        if (respuestas_Correctas < CORRECTAS || preguntaActual >= preguntasPartida.size()) {
            MostrarPregunta();
        } else {
            terminarNivel();
        }
    }

    //--------------- TERMINAR NIVEL ---------------
    public void terminarNivel() {

        if (partidaTerminada) {
            return;

        } else {

            if (nivelActual < nivelFinal) {
                
                respuestas_Correctas = 0;

                pantallaDificultad = new PantallaDificultad(this,
                        nivelActual + 1);
                fadeTo(() -> {
                    setContentPane(pantallaDificultad.getFondo());
                }, 400);
                
            } else {
                
                partidaTerminada = true;

                // NUEVO: si es invitado, no se toca la base de datos.
                if (!esInvitado) {
                    guardarFinDePartida("completada");
                }

                if (vidas == getMaxVidas() && puntos == puntajeMaximo && !usoPista) {

                    VictoriaPerfecta vp = new VictoriaPerfecta(this);

                    fadeTo(() -> {
                        setContentPane(vp.getFondo());
                    }, 400);
                } else {
                    Victoria v = new Victoria(this);

                    fadeTo(() -> {
                        setContentPane(v.getFondo());
                    }, 400);
                }
            }
        }

    }

    //----------- PARTIDA -------------------
    public void Partida(int id_nivel) {
        preguntaActual = 0;
        preguntasPartida = dao.generarPartida(id_nivel);
        ConfiguracionNivel(id_nivel);

        // NUEVO: si es invitado, nunca se crea una fila en Partida.
        if (!esInvitado && idPartida == -1) {
            idPartida = dao.crearPartida(idUsuario, ID_MINIJUEGO, vidas);

            if (idPartida == -1) {
                System.out.println("ADVERTENCIA: no se pudo crear el registro de la partida en la base de datos.");
            }
        }

        actualizarPuntos(puntos);
        modificarCorazones(vidas);
        SiguientePregunta();
    }

    private Integer obtenerIdPreguntaActual() {
        if (preguntaActual < 0 && preguntaActual >= preguntasPartida.size()) {
            return null;
        }
        return preguntasPartida.get(preguntaActual);
    }

    //--------- MOSTRAR PREGUNTA -----------
    public void MostrarPregunta() {

        habilitarBotones(true);

        Integer id_pregunta = obtenerIdPreguntaActual();

        if (id_pregunta == null) {
            return;
        }

        //DEBUG
        System.out.println(
                "Mostrando pregunta ID: "
                + id_pregunta);
        //DEBUG

        modificarPregunta(dao.obtenerPregunta(id_pregunta));

        respuestas(id_pregunta);

        cambiarImagen(dao.obtenerRutaImagen(id_pregunta, false));

        modificarAcierto("Aciertos: " + (respuestas_Correctas) + "/" + CORRECTAS);

        iniciarTiempo(dao.obtenerTiempoLimite(id_pregunta));
    }

    public void Esperar() {
        Timer timer = new Timer(1000, e -> {
            preguntaActual++;
            SiguientePregunta();
        });

        timer.setRepeats(false);
        timer.start();
    }

    private void iniciarTiempo(int tiempoLimite) {

        if (countdown != null) {
            countdown.stop();
        }

        tiempoMaximoPregunta = tiempoLimite;
        segundosRestantes = tiempoLimite;

        modificarTiempo(formatearTiempo(segundosRestantes));

        countdown = new Timer(1000, e -> {

            segundosRestantes--;
            tiempoTotalJugado++;

            modificarTiempo(formatearTiempo(segundosRestantes));

            if (segundosRestantes <= 0) {
                FinTiempo();
            }

        });

        countdown.start();
    }

    private void FinTiempo() {
        if (partidaTerminada) {
            return;

        } else {

            partidaTerminada = true;

            if (countdown != null) {
                countdown.stop();
                countdown = null;
            }

            habilitarBotones(false);

            // NUEVO: solo se registra el detalle y se cierra la partida si NO es invitado.
            if (!esInvitado) {
                Integer id_pregunta = obtenerIdPreguntaActual();
                if (id_pregunta != null && idPartida != -1) {
                    dao.registrarDetalle(idPartida, id_pregunta, 0, tiempoMaximoPregunta, false);
                }

                guardarFinDePartida("abandonada");
            }
            dispose();

            new SeAcaboTiempo(this, e -> {
                new MenuHiddenFox().setVisible(true);
            });
        }

    }

    private int calcularPuntosPorTiempo() {

        int tiempoUsado = tiempoMaximoPregunta - segundosRestantes;

        if (tiempoUsado > 2) {
            double porcentajeRapidez
                    = 1.0 - ((double) tiempoUsado / tiempoMaximoPregunta);

            int puntos = (int) (100 * porcentajeRapidez);

            if (puntos < 10) {
                puntos = 10;
            }

            return puntos;

        } else {
            return 100;
        }

    }

    //---------------- ACTION LISTENER -----------------
    @Override
    public void respuestaSeleccionada(JButton boton) {

        Integer id_pregunta = obtenerIdPreguntaActual();

        if (id_pregunta == null) {
            return;
        }

        habilitarBotones(false);

        if (countdown != null) {
            countdown.stop();
        }

        System.out.println("Botón presionado");

        boolean correcta
                = (Boolean) boton.getClientProperty("correcta");

        int tiempoRespuesta = tiempoMaximoPregunta - segundosRestantes;

        if (correcta) {
            int puntosGanados = calcularPuntosPorTiempo();

            respuestas_Correctas = respuestas_Correctas + 1;

            puntos += puntosGanados;

            actualizarPuntos(puntos);

            // NUEVO: solo se persiste si NO es invitado.
            if (!esInvitado && idPartida != -1) {
                dao.registrarDetalle(idPartida, id_pregunta, puntosGanados, tiempoRespuesta, true);
            }

            JOptionPane.showMessageDialog(
                    this,
                    "¡Correcto!\nGanaste "
                    + puntosGanados
                    + " puntos.\n"
                    + respuestas_Correctas + "/" + CORRECTAS
            );

            cambiarImagen(
                    dao.obtenerRutaImagen(
                            id_pregunta,
                            true));
            Esperar();
        } else {

            vidas--;

            if (puntos >= 5) {
                puntos -= 5;
            } else {
                puntos = 0;
            }

            // NUEVO: solo se persiste si NO es invitado.
            if (!esInvitado && idPartida != -1) {
                dao.registrarDetalle(idPartida, id_pregunta, 0, tiempoRespuesta, false);
            }

            JOptionPane.showMessageDialog(
                    this,
                    "¡Incorrecto!\n"
                    + "Mejor suerte la próxima vez."
            );

            actualizarPuntos(puntos);
            modificarCorazones(vidas);

            if (vidas <= 0) {
                DerrotaVidas();
                return;
            }
            Esperar();
        }
    }

    @Override
    public void ayuda() {
        Integer id_pregunta = obtenerIdPreguntaActual();

        if (id_pregunta == null) {
            return;
        }

        Random random = new Random();
        boolean esTexto = random.nextBoolean();

        penalizacion = dao.obtenerPenalizacionPista(
                id_pregunta, esTexto);

        int opcion = JOptionPane.showConfirmDialog(
                this,
                "Si utilizas una pista perderás " + penalizacion + " puntos.\n\n¿Deseas continuar?",
                "Usar pista",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);

        if (opcion != JOptionPane.YES_OPTION) {
            return;
        }
        pausarPartida();
        usoPista = true;

        RestarPuntos(penalizacion);

        JFrame ventana;

        if (esTexto) {
            ventana = new PistasTexto(
                    dao.obtenerPista(id_pregunta));
        } else {
            ventana = new PistasAudio(
                    dao.obtenerRutaAudio(id_pregunta));
        }

        ventana.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                reanudarPartida();
            }
        });
    }

    public void pausarPartida() {
        countdown.stop();
        habilitarBotones(false);
    }

    public void reanudarPartida() {
        countdown.start();
        habilitarBotones(true);
    }

    private void RestarPuntos(int penalizacion) {

        puntos -= penalizacion;

        if (puntos < 0) {
            puntos = 0;
        }

        actualizarPuntos(puntos);
    }

    // Centraliza el cierre de la partida (victoria) para no repetir la
    // lógica. Ya viene protegida con "!esInvitado" en cada punto de llamada.
    private void guardarFinDePartida(String estado) {

        if (idPartida == -1) {
            System.out.println("ADVERTENCIA: no hay idPartida válido, no se guardó el resultado final.");
            return;
        }

        dao.finalizarPartida(idPartida, puntos, tiempoTotalJugado, estado);
        dao.actualizarEstadistica(idUsuario, ID_MINIJUEGO, puntos, tiempoTotalJugado);
    }

    // ─────────────────────────────────────────────────────────────────────────
    // JuegoBase
    // ─────────────────────────────────────────────────────────────────────────
    @Override
    public int getPuntajeTotal() {
        return puntos;
    }

    @Override
    public int getTiempoTotalJugado() {
        return tiempoTotalJugado;
    }

    @Override
    public void reiniciar() {
        jugarDeNuevo();
    }

    @Override
    public void jugarDeNuevo() {
        dispose();
        new HiddenFox_Codigo(nivelActual);
    }

    @Override
    public void irAlMenu() {
        dispose();
        new MenuHiddenFox().setVisible(true);
    }

    @Override
    public JFrame getFrame() {
        return this;
    }

    @Override
    public void mostrarResultadoConFade() {
        ResultadoFinal resultado = new ResultadoFinal(this, puntos, tiempoTotalJugado);
        resultado.mostrar();
    }

    //---------------- TRANSICIÓN ----------
    private void fadeTo(Runnable onMidpoint, int duracionMs) {

        JPanel overlay = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                g.setColor(new Color(0, 0, 0, getBackground().getAlpha()));
                g.fillRect(0, 0, getWidth(), getHeight());
            }
        };

        overlay.setOpaque(false);
        overlay.setBackground(new Color(0, 0, 0, 0));

        JPanel glass = (JPanel) getGlassPane();
        glass.setLayout(new BorderLayout());
        glass.add(overlay, BorderLayout.CENTER);
        glass.setVisible(true);

        int pasos = 20;
        int intervalo = (duracionMs / 2) / pasos;
        int[] alpha = {0};
        int[] fase = {0};

        javax.swing.Timer fadeTimer = new javax.swing.Timer(intervalo, null);

        fadeTimer.addActionListener(e -> {

            if (fase[0] == 0) {
                alpha[0] += 255 / pasos;

                if (alpha[0] >= 255) {
                    alpha[0] = 255;
                    overlay.setBackground(new Color(0, 0, 0, alpha[0]));
                    overlay.repaint();

                    onMidpoint.run();
                    revalidate();
                    repaint();

                    fase[0] = 1;
                }

            } else {
                alpha[0] -= 255 / pasos;

                if (alpha[0] <= 0) {
                    alpha[0] = 0;
                    overlay.setBackground(new Color(0, 0, 0, alpha[0]));
                    overlay.repaint();

                    glass.remove(overlay);
                    glass.setVisible(false);

                    fadeTimer.stop();
                    return;
                }
            }

            overlay.setBackground(new Color(0, 0, 0, alpha[0]));
            overlay.repaint();
        });

        fadeTimer.start();
    }

    public void continuarDespuesDeDificultad(int nuevoNivel) {

        fadeTo(() -> {
            setContentPane(getFondo());
        }, 400);

        new Timer(420, e -> {

            ((Timer) e.getSource()).stop();

            nivelActual = nuevoNivel;

            Partida(nivelActual);

        }).start();
    }

    public void respuestas(int id_pregunta) {
        String[] respuestas = new String[4];
        boolean[] correctas = new boolean[4];
        dao.obtenerRespuestas(id_pregunta, respuestas, correctas);

        mostrarRespuestas(respuestas, correctas);
    }

    public void ConfiguracionNivel(int nivel) {
        switch (nivel) {
            case 1:
                modificarColorFondo(facil);
                modificarDificultad("Fácil");
                cambiarFondoPapel("/Multimedia/Minijuegos/Minijuego_1/Elementos_graficos/M1_C1_N1_FONDO.png");
                modificarCategoria(dao.obtenerCategoria(1));

                break;
            case 2:
                modificarColorFondo(intermedio);
                modificarDificultad("Intermedio");
                cambiarFondoPapel("/Multimedia/Minijuegos/Minijuego_1/Elementos_graficos/M1_C1_N2_FONDO.png");
                modificarCategoria(dao.obtenerCategoria(1));

                break;
            case 3:
                modificarColorFondo(dificil);
                modificarDificultad("Difícil");
                cambiarFondoPapel("/Multimedia/Minijuegos/Minijuego_1/Elementos_graficos/M1_C1_N3_FONDO.png");
                modificarCategoria(dao.obtenerCategoria(1));

                break;
            case 4:
                modificarColorFondo(facil);
                modificarDificultad("Fácil");
                cambiarFondoPapel("/Multimedia/Minijuegos/Minijuego_1/Elementos_graficos/M1_C2_N1_FONDO.png");
                modificarCategoria(dao.obtenerCategoria(2));

                break;
            case 5:
                modificarColorFondo(intermedio);
                modificarDificultad("Intermedio");
                cambiarFondoPapel("/Multimedia/Minijuegos/Minijuego_1/Elementos_graficos/M1_C2_N2_FONDO.png");
                modificarCategoria(dao.obtenerCategoria(2));

                break;
            case 6:
                modificarColorFondo(dificil);
                modificarDificultad("Difícil");
                cambiarFondoPapel("/Multimedia/Minijuegos/Minijuego_1/Elementos_graficos/M1_C2_N3_FONDO.png");
                modificarCategoria(dao.obtenerCategoria(2));

                break;
            case 7:
                modificarColorFondo(facil);
                modificarDificultad("Fácil");
                cambiarFondoPapel("/Multimedia/Minijuegos/Minijuego_1/Elementos_graficos/M1_C3_N1_FONDO.png");
                modificarCategoria(dao.obtenerCategoria(3));

                break;
            case 8:
                modificarColorFondo(intermedio);
                modificarDificultad("Intermedio");
                cambiarFondoPapel("/Multimedia/Minijuegos/Minijuego_1/Elementos_graficos/M1_C3_N2_FONDO.png");
                modificarCategoria(dao.obtenerCategoria(3));

                break;
            case 9:
                modificarColorFondo(dificil);
                modificarDificultad("Difícil");
                cambiarFondoPapel("/Multimedia/Minijuegos/Minijuego_1/Elementos_graficos/M1_C3_N3_FONDO.png");
                modificarCategoria(dao.obtenerCategoria(3));

                break;
            default:
                JOptionPane.showMessageDialog(null, "ERROR: No se pudo cambiar la dificultad.", "ERROR.", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void DerrotaVidas() {
        if (vidas <= 0) {

            if (vidas == 0) {

                // NUEVO: solo se cierra la partida en BD si NO es invitado.
                if (!esInvitado) {
                    guardarFinDePartida("abandonada");
                }

                JOptionPane.showMessageDialog(this, "Has perdido.");

                fadeTo(() -> {
                    new SeAcaboVidas(this, e -> {
                    }).setVisible(true);
                    dispose();
                }, 400);
            }
        }
    }

    public String formatearTiempo(int segundos) {

        int minutos = segundos / 60;
        segundosRestantes = segundos % 60;

        return String.format("%02d:%02d", minutos, segundosRestantes);
    }

}