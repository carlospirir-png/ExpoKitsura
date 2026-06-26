package main.Usuario;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import main.conexion.Conexion;
import java.sql.*;
import java.util.Random;

public class HiddenFox_Codigo extends HiddenFox {

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
    /*Segundos que quedan en el turno actual.*/
    private int segundosRestantes;
    /*Timer de Swing que descuenta el tiempo cada segundo.*/
    private Timer countdown;
    private boolean TipoPista; //true texto | false audio
    int penalizacion;

    //---------------- CONSTRUCTOR ----------------
    public HiddenFox_Codigo(int nivel, int vidas, int puntos, boolean usoPista) {
        this.nivelActual = nivel;
        this.vidas = vidas;
        this.puntos = puntos;
        this.usoPista = usoPista;

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
        this(nivel, 3, 0, false);
    }

    //------------- SIGUIENTE PREGUNTA ------------
    public void SiguientePregunta() {
        System.out.println("Comparando: " + nivelActual + " < " + nivelFinal);
        if (preguntaActual < preguntasPartida.length) {

            MostrarPregunta();

        } else {
            if (nivelActual < nivelFinal) {

                dispose();

                new PantallaDificultad(nivelActual + 1, vidas, puntos, usoPista);

            } else {

                dispose();

                if (vidas == 3 && puntos == puntajeMaximo && !usoPista) {
                    new VictoriaPerfecta(e -> {
                        new MenuHiddenFox().setVisible(true);
                    });
                } else {
                    new Victoria(e -> {
                        new MenuHiddenFox().setVisible(true);
                    });
                }
            }
        }
    }

    //----------- PARTIDA -------------------
    public void Partida(int id_nivel) {
        preguntaActual = 0;
        GenerarPartida(id_nivel);
        ConfiguracionNivel(id_nivel);
        // Metodo que realiza el aumento o disminución de puntos
        ActualizarPuntos(puntos);
        ModificarCorazones(vidas);
        SiguientePregunta();
    }

    //------------ GENERAR PARTIDA ------------
    public void GenerarPartida(int id_nivel) {

        String sql = "SELECT id_pregunta FROM Pregunta WHERE id_nivel = ? ORDER BY RAND() LIMIT 5";

        try (PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id_nivel);

            ResultSet rs = ps.executeQuery();

            int i = 0;
            while (rs.next()) {
                preguntasPartida[i] = rs.getInt("id_pregunta");
                i++;
            }

        } catch (SQLException e) {
            System.out.println("Error al generar partida: " + e.getMessage());
        }
    }

    //--------- MOSTRAR PREGUNTA -----------
    public void MostrarPregunta() {

        HabilitarBotones(true);

        int id_pregunta = preguntasPartida[preguntaActual];
        //DEBUG
        System.out.println(
                "Mostrando pregunta ID: "
                + id_pregunta);
        //DEBUG

        ModificarPregunta(id_pregunta);

        CargarRespuestas(id_pregunta);

        CargarImagen(id_pregunta, false);

        ModificarAcierto(preguntaActual + 1);

        iniciarTiempo(ObtenerTiempoLimite(id_pregunta));
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

        segundosRestantes = tiempoLimite;

        ModificarTiempo(segundosRestantes);

        countdown = new Timer(1000, e -> {

            segundosRestantes--;

            ModificarTiempo(segundosRestantes);

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

        HabilitarBotones(false);

        dispose();

        new SeAcaboTiempo(e -> {
            new MenuHiddenFox().setVisible(true);
        });
    }

    private int ObtenerTiempoLimite(int idPregunta) {

        String sql
                = "SELECT cn.tiempo_limite "
                + "FROM Pregunta p "
                + "INNER JOIN Configuracion_nivel cn "
                + "ON p.id_nivel = cn.id_nivel "
                + "WHERE p.id_pregunta = ?";

        try (PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idPregunta);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt("tiempo_limite");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 15; // valor por defecto
    }

    //-------------- BOTONES -------------
    //---------------- ACTION LISTENER -----------------
    @Override
    public void respuestaSeleccionada(JButton boton) {

        HabilitarBotones(false);

        if (countdown != null) {
            countdown.stop();
        }

        System.out.println("Botón presionado");

        boolean correcta
                = (Boolean) boton.getClientProperty("correcta");

        if (correcta) {
            //suma puntos por responder correctamente
            puntos += 100;
            ActualizarPuntos(puntos);

            // Revela la imagen
            CargarImagen(
                    preguntasPartida[preguntaActual],
                    true);
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

            ActualizarPuntos(puntos);
            ModificarCorazones(vidas);

            // Si ya no quedan vidas, termina la partida
            if (vidas <= 0) {
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

        penalizacion = ObtenerPenalizacionPista(
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
                    ObtenerPista(preguntasPartida[preguntaActual]));
        } else {
            ventana = new PistasAudio(
                    ObtenerRutaAudio(preguntasPartida[preguntaActual]));
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
        HabilitarBotones(false);
    }

    public void reanudarPartida() {
        countdown.start();
        HabilitarBotones(true);
    }

    //-------------- PISTAS TEXTO --------------
    private String ObtenerPista(int id_pregunta) {
        TipoPista = true;

        String sql
                = "SELECT contenido "
                + "FROM Ayuda "
                + "WHERE id_pregunta = ? "
                + "AND tipo = 'texto'";

        try (PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id_pregunta);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getString("contenido");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return "No hay pista disponible.";
    }

    //------------------ PISTAS AUDIO -------------
    private String ObtenerRutaAudio(int id_pregunta) {
        TipoPista = false;
        String sql = "SELECT audio "
                + "FROM Ayuda "
                + "WHERE id_pregunta = ? "
                + "AND tipo = 'audio'";

        try (PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id_pregunta);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getString("audio");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    //--------------- PUNTOS ------------------
    private void RestarPuntos(int penalizacion) {

        puntos -= penalizacion;

        if (puntos < 0) {
            puntos = 0;
        }

        ActualizarPuntos(puntos);
    }

    //TipoPista | true = texto  | false = audio.
    private int ObtenerPenalizacionPista(int id_pregunta, boolean TipoPista) {
        String pista;
        if (TipoPista) {
            pista = "texto";
        } else {
            pista = "audio";
        }

        String sql
                = "SELECT penalizacion_puntos "
                + "FROM Ayuda "
                + "WHERE id_pregunta = ? "
                + "AND tipo = ?";

        try (PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id_pregunta);
            ps.setString(2, pista);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt("penalizacion_puntos");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 50; // valor por defecto
    }
}
