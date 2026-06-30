//-------------------------- HIDDEN FOX CÓDIGO ----------------------
package main.Usuario;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import main.conexion.Conexion;
import java.sql.*;
import java.util.Random;

public class HiddenFox_Codigo extends HiddenFox implements JuegoBase {

    //--------------- A T R I B U T O S ---------------
    //--------------- C O L O R E S
    private Color facil = new Color(178, 197, 178);
    private Color intermedio = new Color(239, 218, 154);
    private Color dificil = new Color(218, 77, 88);

    //--------------- T I E M P O
    /*Segundos que quedan en el turno actual.*/
    int segundosRestantes;
    
    private HiddenFoxDAO dao = new HiddenFoxDAO();

    //Son 5 preguntas las que se muestran
    private int[] preguntasPartida = new int[5];
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
    //Puntaje máximo posible de la categoria -> 5 preguntas por nivel, 3 niveles, 100 puntos por pregunta = 1500 pts
    private final int puntajeMaximo = 1500;
    private int puntajeTotal;
    /*Timer de Swing que descuenta el tiempo cada segundo.*/
    private Timer countdown;

    private int penalizacion;
    private int tiempoTotalJugado = 0;
    private int tiempoMaximoPregunta;
    private PantallaDificultad pantallaDificultad;

    //---------------- CONSTRUCTOR ----------------
    public HiddenFox_Codigo(int nivel, int vidas, int puntos, boolean usoPista, int tiempoTotalJugado) {
        this.nivelActual = nivel;
        this.vidas = vidas;
        this.puntos = puntos;
        this.usoPista = usoPista;
        this.tiempoTotalJugado = tiempoTotalJugado;

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

    // Segundo contructor que indica cuando el jugador inicia una categoria desde el menu
    public HiddenFox_Codigo(int nivel) {
        this(nivel, 3, 0, false, 0);
    }

    //------------- SIGUIENTE PREGUNTA ------------
    public void SiguientePregunta() {
        System.out.println("Comparando: " + nivelActual + " < " + nivelFinal);
        if (preguntaActual < preguntasPartida.length) {

            MostrarPregunta();

        } else {
            if (nivelActual < nivelFinal) {

                pantallaDificultad = new PantallaDificultad(this,
                        nivelActual + 1, vidas, puntos, false);
                fadeTo(() -> {
                    setContentPane(pantallaDificultad.getFondo());
                }, 400);

            } else {

                if (vidas == 3 && puntos == puntajeMaximo && !usoPista) {

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
        // Metodo que realiza el aumento o disminución de puntos
        actualizarPuntos(puntos);
        modificarCorazones(vidas);
        SiguientePregunta();
    }

    

    //--------- MOSTRAR PREGUNTA -----------
    public void MostrarPregunta() {

        habilitarBotones(true);

        int id_pregunta = preguntasPartida[preguntaActual];
        //DEBUG
        System.out.println(
                "Mostrando pregunta ID: "
                + id_pregunta);
        //DEBUG

        //ModificarPregunta es un método de HiddenFox,
        //En el que está llamando al método de HiddenFox_Codigo
        //para obtener la pregunta.
        modificarPregunta(dao.obtenerPregunta(id_pregunta));

        respuestas(id_pregunta);

        cambiarImagen(dao.obtenerRutaImagen(id_pregunta, false));

        modificarAcierto("Problema: " + (preguntaActual + 1) + "/5");

        iniciarTiempo(dao.obtenerTiempoLimite(id_pregunta));
    }

    // -------------- MODIFICAR ------------
    //------------------- TIEMPO ----------------
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

        if (countdown != null) {
            countdown.stop();
            countdown = null;
        }

        habilitarBotones(false);

        dispose();

        new SeAcaboTiempo(this, e -> {
            new MenuHiddenFox().setVisible(true);
        });
    }

    

    private int calcularPuntosPorTiempo() {

        int tiempoUsado = tiempoMaximoPregunta - segundosRestantes;

        double porcentajeRapidez
                = 1.0 - ((double) tiempoUsado / tiempoMaximoPregunta);

        int puntos = (int) (100 * porcentajeRapidez);

        if (puntos < 10) {
            puntos = 10;
        }

        return puntos;
    }

    //-------------- BOTONES -------------
    //---------------- ACTION LISTENER -----------------
    @Override
    public void respuestaSeleccionada(JButton boton) {

        habilitarBotones(false);

        if (countdown != null) {
            countdown.stop();
        }

        System.out.println("Botón presionado");

        boolean correcta
                = (Boolean) boton.getClientProperty("correcta");

        if (correcta) {
            //suma puntos por responder correctamente
            int puntosGanados = calcularPuntosPorTiempo();

            puntos += puntosGanados;

            actualizarPuntos(puntos);

            JOptionPane.showMessageDialog(
                    this,
                    "¡Correcto!\nGanaste "
                    + puntosGanados
                    + " puntos."
            );

            // Revela la imagen
            cambiarImagen(
                    dao.obtenerRutaImagen(
                            preguntasPartida[preguntaActual],
                            true));
            Esperar();
        } else {

            // Pierde una vida
            vidas--;

            // Resta los puntos (evitando números negativos)
            if (puntos >= 5) {
                puntos -= 5;
            } else {
                puntos = 0;
            }

            actualizarPuntos(puntos);
            modificarCorazones(vidas);

            // Si ya no quedan vidas, termina la partida
            if (vidas <= 0) {
                DerrotaVidas();
                return;
            }
            Esperar();
        }
    }

    //-------------- AYUDA Y PISTAS-------------------
    // --------------- ABRIR VENTANA ---------------
    @Override
    public void ayuda() {
        Random random = new Random();
        boolean esTexto = random.nextBoolean();
        
            //esTexto | true = texto  | false = audio.
        penalizacion = dao.obtenerPenalizacionPista(
                preguntasPartida[preguntaActual],
                esTexto);

        int opcion = JOptionPane.showConfirmDialog(
                this,
                "Si utilizas una pista perderás " + penalizacion + " puntos.\n\n¿Deseas continuar?",
                "Usar pista",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);

        // Si el usuario presionó "No" o cerró la ventana
        if (opcion != JOptionPane.YES_OPTION) {
            return;
        }
        pausarPartida();
        // Aunque el usuario utilice una pista durante toda la partida, afectará su victoria perfecta
        usoPista = true;

        RestarPuntos(penalizacion);

        JFrame ventana;

        if (esTexto) {
            ventana = new PistasTexto(
                    dao.obtenerPista(preguntasPartida[preguntaActual]));
        } else {
            ventana = new PistasAudio(
                    dao.obtenerRutaAudio(preguntasPartida[preguntaActual]));
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

    /*------------------  M O D I F I C A R ------------------
      Este es el apartado donde se modifican cosas de la Interfaz Gráfica
      a través de HiddenFox y la base de datos con HiddenFoxDAO, 
      pero la parte lógica de dichas modificaciones permanecen en este bloque.
    ----------------------------------------------------------*/

    public void respuestas(int id_pregunta){
        String [] respuestas = new String[4];
        boolean [] correctas = new boolean[4]; //Determina si son correctas
        dao.obtenerRespuestas(id_pregunta, respuestas, correctas);
        
        mostrarRespuestas(respuestas, correctas);
    }

    /*------------------  C O N F I G U R A C I Ó N ------------------
      Este es el apartado donde se establece la configuración principal
      del minijuego.
    ------------------------------------------------------------------*/
    public void ConfiguracionNivel(int nivel) {
        /*NIVELES: 
        1. Animales - Fácil
        2. Animales - Intermedio
        3. Animales - Difícil
        4. Territorios - Fácil
        5. Territorios - Intermedio
        6. Territorios - Difícil
        7. Caricaturas - Fácil
        8. Caricaturas - Intermedio
        9. Caricaturas - Difícil*/
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
        
    /*-------------------------- P A R T I D A ------------------------
        Este apartado es donde se encuentra la configuración y generación
        de las partidas.
    -------------------------------------------------------------------*/
    
    //-------------------------- GENERAR PARTIDA
    

    /*--------------------------  D E R R O T A -----------------------
      Este es el apartado donde se coloca la lógica de las derrotas.
      Actualmente, solo puedes perder por dos motivos: Falta de tiempo
      y falta de vidas (por equivocaciones, pierdes una).
    ------------------------------------------------------------------*/
    //--------------------- T I E M P O
    //--------------------- V I D A S
    public void DerrotaVidas() {
        if (vidas <= 0) {

            if (vidas == 0) {

                JOptionPane.showMessageDialog(this, "Has perdido.");

                fadeTo(() -> {
                    new SeAcaboVidas(this, e -> {
                    }).setVisible(true);
                    dispose();
                }, 400);
            }
        }
    }

    /*-------------------------- T I E M P O -----------------------
      Este es el apartado donde se encuentra toda la lógica de los
      tiempos.
    ------------------------------------------------------------------*/
    //--------------------- F O R M A T O
    public String formatearTiempo(int segundos) {

        int minutos = segundos / 60;
        segundosRestantes = segundos % 60;

        return String.format("%02d:%02d", minutos, segundosRestantes);
    }

}
