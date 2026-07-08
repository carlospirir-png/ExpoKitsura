package main.Usuario;

import java.sql.*;
import main.conexion.Conexion;

public class Partida_DAO {

    private Connection con;

    public Partida_DAO(Connection con) {
        Conexion conexion = new Conexion();
        con = conexion.getConnection();
    }

    public int crearPartida(int idUsuario, int idMinijuego, int vidasIniciales) {
        String sql = "INSERT INTO Partida (id_usuario, id_minijuego, vidas_iniciales_snapshot) VALUES (?, ?, ?)";
        try (PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, idUsuario);
            ps.setInt(2, idMinijuego);
            ps.setInt(3, vidasIniciales);
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public void registrarDetalle(int idPartida, int idPregunta, int puntos, int tiempoRespuesta, boolean correcta) {
        String sql = "INSERT INTO Detalle_partida (id_partida, id_pregunta, puntos_obtenidos, tiempo_respuesta, respondio_correctamente) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idPartida);
            ps.setInt(2, idPregunta);
            ps.setInt(3, puntos);
            ps.setInt(4, tiempoRespuesta);
            ps.setBoolean(5, correcta);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void finalizarPartida(int idPartida, int puntuacion, int tiempoJugado, String estado) {
        String sql = "UPDATE Partida SET puntuacion = ?, tiempo_jugado = ?, estado = ? WHERE id_partida = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, puntuacion);
            ps.setInt(2, tiempoJugado);
            ps.setString(3, estado);
            ps.setInt(4, idPartida);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void actualizarEstadistica(int idUsuario, int idMinijuego, int puntuacion, int tiempoJugado) {
        String sql = "INSERT INTO Estadistica (id_usuario, id_minijuego, mejor_puntuacion, puntuacion_total, partidas_jugadas, tiempo_total) "
                + "VALUES (?, ?, ?, ?, 1, ?) "
                + "ON DUPLICATE KEY UPDATE "
                + "mejor_puntuacion = GREATEST(mejor_puntuacion, VALUES(mejor_puntuacion)), "
                + "puntuacion_total = puntuacion_total + VALUES(puntuacion_total), "
                + "partidas_jugadas = partidas_jugadas + 1, "
                + "tiempo_total = tiempo_total + VALUES(tiempo_total)";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idUsuario);
            ps.setInt(2, idMinijuego);
            ps.setInt(3, puntuacion);
            ps.setInt(4, puntuacion);
            ps.setInt(5, tiempoJugado);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
