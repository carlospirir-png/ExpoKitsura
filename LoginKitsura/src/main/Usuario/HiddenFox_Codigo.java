// ============== HIDDEN FOX CODIGO (VERSIÓN FINAL) ==============
// IMPORTANTE: esta es la ÚNICA versión de esta clase que debe existir en el
// proyecto. Reemplaza por completo a la versión anterior, que usaba métodos
// static (resolverVidasIniciales, mapearIdCategoria, mapearDificultad) antes
// de super(vidas). Esa versión anterior debe eliminarse del archivo/proyecto.
package main.Usuario;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;
import java.util.ArrayList;
import main.Administrador.*;

public class HiddenFox_Codigo extends HiddenFox implements JuegoBase {

    //--------------- A T R I B U T O S ---------------
    //--------------- C O L O R E S
    private Color facil = new Color(178, 197, 178);
    private Color intermedio = new Color(239, 218, 154);
    private Color dificil = new Color(227, 157, 139);

    //--------------- T I E M P O
    /*Segundos que quedan en el turno actual.*/
    int segundosRestantes;

    private HiddenFoxDAO dao = new HiddenFoxDAO();

    //Son 5 preguntas las que se muestran
    private ArrayList<Integer> preguntasPartida = new ArrayList<>();
    //La pregunta en que se encuentra automáticamente
    private int preguntaActual = 0;
    // Las vidas por defecto son 3 para el jugador
    private int vidas;
    // Nivel actual y final de la categoria
    private int nivelActual;
    private int nivelFinal;
    // Variable que almacena los puntos obtenidos
    private int puntos = 0;
    // Indica si el jugador a utilizado alguna pista durante la partida
    private boolean usoPista = false;

    /*Timer de Swing que descuenta el tiempo cada segundo.*/
    private Timer countdown;

    private int penalizacion;
    private int tiempoTotalJugado = 0;
    private int tiempoMaximoPregunta;
    private PantallaDificultad pantallaDificultad;

    //Esta variable defina la cantidad de respuestas correctas que se necesitan para pasar a la siguiente dificultad.
    private static final int CORRECTAS = 5;

    //Esta variable almacena las respuestas correctas totales que lleva el jugador.
    private int respuestas_Correctas = 0;

    //Puntaje máximo posible de la categoria -> X aciertos por nivel, 3 niveles, da como resultado: (x*3) cuyo puntaje máximo es de 100 (3x*100
    private final int puntajeMaximo = CORRECTAS * 3 * 100;

    //Este atributo indica si la partida ya terminó. Se utiliza para deshabilitar otros comportamientos cuando la partida finalice.
    private boolean partidaTerminada = false;

    /*=====================================================================
      ATRIBUTOS DE PERSISTENCIA
    =====================================================================*/
    // Id del minijuego "Hidden Fox" según la tabla Minijuego (INSERT inicial: 1 = Hidden Fox)
    private static final int ID_MINIJUEGO = 1;

    // Usuario actualmente logueado, obtenido de la sesión guardada en IniciarSesion
    private int idUsuario;

    // Id de la partida en curso (fila de la tabla Partida). -1 mientras no se ha creado.
    private int idPartida = -1;

    // Snapshot de las vidas con las que arrancó la partida (para la columna vidas_iniciales_snapshot)
    private int vidasInicialesSnapshot;
    
    //se 
    private PuntuacionesDAO puntuacionesDAO = new PuntuacionesDAO();
    
    //se obtienen los puntos del minijuego HiddenFox, de la categoría y nivel que se encuentre
   private int puntosDB;

    private String categoriaResuelta = null;
    private String dificultadResuelta = null;
    
    //El tiempo en el que se repsondió la pregunta
     private int tiempoRespuesta = tiempoMaximoPregunta - segundosRestantes;

    //---------------- CONSTRUCTOR ----------------
    public HiddenFox_Codigo(int nivel, int vidas, int puntos, boolean usoPista, int tiempoTotalJugado) {
        super(vidas);

        this.nivelActual = nivel;
        this.vidas = vidas;
        this.puntos = puntos;
        this.usoPista = usoPista;
        this.tiempoTotalJugado = tiempoTotalJugado;

        // Se obtiene el usuario que inició sesión (guardado por IniciarSesion)
        this.idUsuario = Sesion.getIdUsuarioActual();

        if (nivelActual >= 1 && nivelActual <= 3) {
            nivelFinal = 3;
        } else if (nivelActual >= 4 && nivelActual <= 6) {
            nivelFinal = 6;
        } else {
            nivelFinal = 9;
        }
        
        //se obtienen los puntos del minijuego HiddenFox, de la categoría y nivel que se encuentre
        puntosDB = puntuacionesDAO.obtenerPuntuacion("Hidden Fox", categoriaResuelta, dificultadResuelta);

        //DEBUG
        System.out.println("Nivel actual: " + nivelActual);
        System.out.println("Nivel final: " + nivelFinal);
        //DEBUG
        Partida(nivelActual);
    }

    public HiddenFox_Codigo(int nivel) {
        this(nivel, 3, 0, false, 0);
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

                guardarFinDePartida("completada");

                if (vidas == getMaxVidas() && puntos == puntajeMaximo && !usoPista) {

                    VictoriaPerfecta vp = new VictoriaPerfecta(e -> {
                    }, this);

                    fadeTo(() -> {
                        setContentPane(vp.getFondo());
                    }, 400);
                } else {
                    Victoria v = new Victoria(e -> {
                    }, this);

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

        if (idPartida == -1) {
            vidasInicialesSnapshot = vidas;
            idPartida = dao.crearPartida(idUsuario, ID_MINIJUEGO, vidasInicialesSnapshot);

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

        System.out.println(
                "Mostrando pregunta ID: "
                + id_pregunta);

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

            Integer id_pregunta = obtenerIdPreguntaActual();
            if (id_pregunta != null && idPartida != -1) {
                dao.registrarDetalle(idPartida, id_pregunta, 0, tiempoMaximoPregunta, false);
            }

            guardarFinDePartida("abandonada");

            dispose();

            new SeAcaboTiempo(this, e -> {
                new MenuHiddenFox().setVisible(true);
            });
        }

    }

    private int calcularPuntosPorTiempo() {

        if (tiempoRespuesta > 2) {
            double porcentajeRapidez
                    = 1.0 - ((double) tiempoRespuesta / tiempoMaximoPregunta);

            //los puntos que estén en l abase de datos se multiplican por la rapiz en la que se respondió
            int puntos = (int) (puntosDB * porcentajeRapidez);

            if (puntos < 10) {
                puntos = 10;
            }

            return puntos;

        } else {
            //devuelve el punteo completo
            return puntosDB;
        }

    }

    //-------------- BOTONES -------------
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

        if (correcta) {
            int puntosGanados = calcularPuntosPorTiempo();

            respuestas_Correctas = respuestas_Correctas + 1;

            puntos += puntosGanados;

            actualizarPuntos(puntos);

            if (idPartida != -1) {
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

            if (idPartida != -1) {
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

    //-------------- AYUDA Y PISTAS-------------------
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

    //--------------- PUNTOS ------------------
    private void RestarPuntos(int penalizacion) {

        puntos -= penalizacion;

        if (puntos < 0) {
            puntos = 0;
        }

        actualizarPuntos(puntos);
    }

    //---------------  GUARDAR FIN DE PARTIDA
    /* Centraliza el cierre de la partida (usado tanto en victoria como en
      derrota por tiempo o por vidas) para no repetir la lógica tres veces.*/
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

    /*------------------  C O N F I G U R A C I Ó N ------------------
      Este es el apartado donde se establece la configuración principal
      del minijuego.
    ------------------------------------------------------------------*/
    public void ConfiguracionNivel(int nivel) {

        // Se van guardando aquí la categoría y dificultad que el switch ya
        // resuelve para este nivel, para poder consultar VidasDAO al final
        // sin tener que volver a calcularlas en otro método aparte.
        switch (nivel) {
            case 1:
                modificarColorFondo(facil);
                dificultadResuelta = "Fácil";
                modificarDificultad(dificultadResuelta);
                cambiarFondoPapel("/Multimedia/Minijuegos/Minijuego_1/Elementos_graficos/M1_C1_N1_FONDO.png");
                categoriaResuelta = dao.obtenerCategoria(1);
                modificarCategoria(categoriaResuelta);
                break;
            case 2:
                modificarColorFondo(intermedio);
                dificultadResuelta = "Intermedio";
                modificarDificultad(dificultadResuelta);
                cambiarFondoPapel("/Multimedia/Minijuegos/Minijuego_1/Elementos_graficos/M1_C1_N2_FONDO.png");
                categoriaResuelta = dao.obtenerCategoria(1);
                modificarCategoria(categoriaResuelta);
                break;
            case 3:
                modificarColorFondo(dificil);
                dificultadResuelta = "Difícil";
                modificarDificultad(dificultadResuelta);
                cambiarFondoPapel("/Multimedia/Minijuegos/Minijuego_1/Elementos_graficos/M1_C1_N3_FONDO.png");
                categoriaResuelta = dao.obtenerCategoria(1);
                modificarCategoria(categoriaResuelta);
                break;
            case 4:
                modificarColorFondo(facil);
                dificultadResuelta = "Fácil";
                modificarDificultad(dificultadResuelta);
                cambiarFondoPapel("/Multimedia/Minijuegos/Minijuego_1/Elementos_graficos/M1_C2_N1_FONDO.png");
                categoriaResuelta = dao.obtenerCategoria(2);
                modificarCategoria(categoriaResuelta);
                break;
            case 5:
                modificarColorFondo(intermedio);
                dificultadResuelta = "Intermedio";
                modificarDificultad(dificultadResuelta);
                cambiarFondoPapel("/Multimedia/Minijuegos/Minijuego_1/Elementos_graficos/M1_C2_N2_FONDO.png");
                categoriaResuelta = dao.obtenerCategoria(2);
                modificarCategoria(categoriaResuelta);
                break;
            case 6:
                modificarColorFondo(dificil);
                dificultadResuelta = "Difícil";
                modificarDificultad(dificultadResuelta);
                cambiarFondoPapel("/Multimedia/Minijuegos/Minijuego_1/Elementos_graficos/M1_C2_N3_FONDO.png");
                categoriaResuelta = dao.obtenerCategoria(2);
                modificarCategoria(categoriaResuelta);
                break;
            case 7:
                modificarColorFondo(facil);
                dificultadResuelta = "Fácil";
                modificarDificultad(dificultadResuelta);
                cambiarFondoPapel("/Multimedia/Minijuegos/Minijuego_1/Elementos_graficos/M1_C3_N1_FONDO.png");
                categoriaResuelta = dao.obtenerCategoria(3);
                modificarCategoria(categoriaResuelta);
                break;
            case 8:
                modificarColorFondo(intermedio);
                dificultadResuelta = "Intermedio";
                modificarDificultad(dificultadResuelta);
                cambiarFondoPapel("/Multimedia/Minijuegos/Minijuego_1/Elementos_graficos/M1_C3_N2_FONDO.png");
                categoriaResuelta = dao.obtenerCategoria(3);
                modificarCategoria(categoriaResuelta);
                break;
            case 9:
                modificarColorFondo(dificil);
                dificultadResuelta = "Difícil";
                modificarDificultad(dificultadResuelta);
                cambiarFondoPapel("/Multimedia/Minijuegos/Minijuego_1/Elementos_graficos/M1_C3_N3_FONDO.png");
                categoriaResuelta = dao.obtenerCategoria(3);
                modificarCategoria(categoriaResuelta);
                break;
            default:
                JOptionPane.showMessageDialog(null, "ERROR: No se pudo cambiar la dificultad.", "ERROR.", JOptionPane.ERROR_MESSAGE);
        }

        // Se reconsulta VidasDAO y se reconstruyen los corazones en CADA
        // cambio de nivel (ver comentario del método más abajo).
        establecerVidasPorNivel(categoriaResuelta, dificultadResuelta);
    }

    /*------------------ ESTABLECER VIDAS POR NIVEL ------------------
      Consulta VidasDAO con la categoría y dificultad que ConfiguracionNivel
      ya resolvió, y reconstruye los corazones según lo configurado en
      VidasAdmin PARA ESE NIVEL/DIFICULTAD.
    ------------------------------------------------------------------------*/
    private void establecerVidasPorNivel(String categoria, String dificultad) {
        if (categoria == null || dificultad == null) {
            return;
        }

        VidasDAO vidasDAO = new VidasDAO();
        int vidasConfiguradas = vidasDAO.obtenerVidas("Hidden Fox", categoria, dificultad);

        if (vidasConfiguradas < 1) {
            vidasConfiguradas = 3;
        }

        inicializarVidas(vidasConfiguradas);
        this.vidas = vidasConfiguradas;
    }

    public void DerrotaVidas() {
        if (vidas <= 0) {

            if (vidas == 0) {

                if (partidaTerminada) {
                    return;
                }
                partidaTerminada = true;

                guardarFinDePartida("abandonada");

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
