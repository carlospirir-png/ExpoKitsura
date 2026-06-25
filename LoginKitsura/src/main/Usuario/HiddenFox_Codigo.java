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

    public HiddenFox_Codigo() {
        ConfiguracionNivel(2);
        GenerarPartida(2);
        MostrarPregunta();
  

    }

    //------------ GENERAR PARTIDA ------------
    public void GenerarPartida(int id_nivel) {

        String sql = "SELECT id_pregunta FROM Pregunta WHERE id_nivel = ? ORDER BY RAND() LIMIT 5";

        try (PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id_nivel);

            ResultSet rs = ps.executeQuery();

            int i = 0;
            while (rs.next()) {
                preguntasPartida[i]
                        = rs.getInt("id_pregunta");
                i++;
            }
        } catch (SQLException e) {
            System.out.println("Error al generar partida: " + e.getMessage());
        }
    }

    //--------- MOSTRAR PREGUNTA -----------
    public void MostrarPregunta() {
        int id_pregunta = preguntasPartida[preguntaActual];

        CargarRespuestas(id_pregunta);

        CargarImagen(id_pregunta, false);

        ModificarNivel(preguntaActual + 1);
    }

    public static void main(String[] args) {
        new HiddenFox_Codigo();
    }

}
