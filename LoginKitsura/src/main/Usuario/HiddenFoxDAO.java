package main.Usuario;

import java.sql.*;
import main.conexion.*;
import java.util.*;

public class HiddenFoxDAO {

    private Connection con;

    public HiddenFoxDAO() {
        con = new Conexion().getConnection();
    }

    /*---------------- M É T O D O S ----------------
    ------------------ T I E M P O*/
    public int obtenerTiempoLimite(int id_pregunta) {

        String sql
                = "SELECT cn.tiempo_limite "
                + "FROM Pregunta p "
                + "INNER JOIN Configuracion_nivel cn "
                + "ON p.id_nivel = cn.id_nivel "
                + "WHERE p.id_pregunta = ?";

        try (PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id_pregunta);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt("tiempo_limite");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 15; // valor por defecto
    }

    //---------------------- P I S T A S
    //true texto | false audios
    //---------------------- PISTAS TEXTO
    public String obtenerPista(int id_pregunta) {
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

    //--------------------- PISTAS AUDIO
    public String obtenerRutaAudio(int id_pregunta) {
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

    //----------------------- PENALIZACIÓN PISTAS
    //esTexto | true = texto  | false = audio.
    public int obtenerPenalizacionPista(int id_pregunta, boolean esTexto) {
        String pista;
        if (esTexto) {
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

    //---------------------- I M Á G E N E S
    //boolean color:   false = sombra      |       true = color
    public String obtenerRutaImagen(int id_pregunta, boolean color) {

        String sql = "SELECT imagen_sombra, imagen_color FROM Pregunta WHERE id_pregunta = ?";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id_pregunta);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                if (color) {
                    return rs.getString("imagen_color");
                } else {
                    return rs.getString("imagen_sombra");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    //------------------------ C A T E G O R Í A
    public String obtenerCategoria(int id_categoria) {
        String sql = "SELECT nombre FROM Categoria WHERE id_categoria = ?";

        try (PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id_categoria);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getString("nombre");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "Categoría no encontrada.";
    }

    //----------------------- R E S P U E S T A S
    public void obtenerRespuestas(int id_pregunta, String[] respuestas, boolean[] correctas) {

        String sql = "SELECT texto_opcion, es_correcta FROM Opcion_respuesta WHERE id_pregunta = ? ORDER BY RAND()";

        try (PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id_pregunta);

            ResultSet rs = ps.executeQuery();

            int i = 0;

            while (rs.next() && i < 4) {
                respuestas[i] = rs.getString("texto_opcion");
                correctas[i] = rs.getBoolean("es_correcta");
                i++;
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // ----------------------- PREGUNTA -------------------
    public String obtenerPregunta(int id_pregunta) {
        String sql = "SELECT pregunta FROM Pregunta WHERE id_pregunta = ?";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id_pregunta);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getString("pregunta");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return "Pregunta no encontrada.";
    }

    //------------ GENERAR PARTIDA ------------
    public ArrayList<Integer> generarPartida(int id_nivel) {
        ArrayList<Integer> preguntas = new ArrayList<>();

        String sql = "SELECT id_pregunta FROM Pregunta WHERE id_nivel = ? ORDER BY RAND() ";

        try (PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id_nivel);

            ResultSet rs = ps.executeQuery();

            int i = 0;
            while (rs.next()) {
                preguntas.add(rs.getInt("id_pregunta"));
                i++;
            }

        } catch (SQLException e) {
            System.out.println("Error al generar partida: " + e.getMessage());
        }

        return preguntas;
    }
}