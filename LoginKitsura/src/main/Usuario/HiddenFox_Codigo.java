package main.Usuario;

import java.awt.*;
import javax.swing.*;
import main.conexion.Conexion;
import java.sql.*;

public class HiddenFox_Codigo extends HiddenFox {

    //Son 5 preguntas las que se muestran
    private int[] preguntasPartida = new int[5];
    //La pregunta en que se encuentra automáticamente
    private int preguntaActual = 0;
    private int vidas = 3;
    private int nivelActual;
    private int nivelFinal;

    //---------------- CONSTRUCTOR ----------------
    public HiddenFox_Codigo(int nivel) {
        nivelActual = nivel;

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

    //------------- SIGUIENTE PREGUNTA ------------
    public void SiguientePregunta() {
        System.out.println("Comparando: " + nivelActual + " < " + nivelFinal);
        if (preguntaActual < preguntasPartida.length) {

            MostrarPregunta();

        } else {
            if (nivelActual < nivelFinal) {

                dispose();

                new PantallaDificultad(nivelActual + 1);

            } else {

                dispose();

                JOptionPane.showMessageDialog(
                        this,
                        "¡Has completado la categoría!");

                new MenuHiddenFox();
            }
        }
    }

    //----------- PARTIDA -------------------
    public void Partida(int id_nivel) {
        preguntaActual = 0;
        vidas = 3;
        GenerarPartida(id_nivel);
        ConfiguracionNivel(id_nivel);
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

    //-------------- BOTONES -------------
    //---------------- ACTION LISTENER -----------------
    @Override
    public void respuestaSeleccionada(JButton boton) {

        System.out.println("Botón presionado");

        boolean correcta
                = (Boolean) boton.getClientProperty("correcta");

        if (correcta) {

            // Revela la imagen
            CargarImagen(
                    preguntasPartida[preguntaActual],
                    true);

        } else {

            // Pierde una vida
            vidas--;

            ModificarCorazones(vidas);

        }

        Esperar();
    }

    public static void main(String[] args) {
        new HiddenFox_Codigo(1);
    }

}
