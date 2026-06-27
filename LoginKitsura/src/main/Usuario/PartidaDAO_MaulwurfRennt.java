package main.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import main.conexion.Conexion;

public class PartidaDAO_MaulwurfRennt {

    private Conexion conexion;

    public PartidaDAO_MaulwurfRennt() {
        conexion = new Conexion();
    }
        
// Crea una nueva partida.
public int crearPartida(int idUsuario,
            int idMinijuego,
            int vidasIniciales) {

        int idPartida = -1;

        String sql = """
        INSERT INTO Partida
        (
            id_usuario,
            id_minijuego,
            puntuacion,
            vidas_iniciales_snapshot,
            tiempo_jugado,
            estado
        )
        VALUES (?,?,?,?,?,?)
        """;

        try (Connection con = conexion.getConnection(); PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, idUsuario);
            ps.setInt(2, idMinijuego);
            ps.setInt(3, 0); // puntuación inicial
            ps.setInt(4, vidasIniciales);
            ps.setInt(5, 0); // tiempo jugado
            ps.setString(6, "en_curso");

            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();

            if (rs.next()) {
                idPartida = rs.getInt(1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return idPartida;
    }

    //Guarda una respuesta del jugador.
    public void guardarDetalle(
            int idPartida,
            int idPregunta,
            int puntosObtenidos,
            int tiempoRespuesta,
            boolean respondioCorrectamente) {

        String sql = """
            INSERT INTO Detalle_partida
            (id_partida,
             id_pregunta,
             puntos_obtenidos,
             tiempo_respuesta,
             respondio_correctamente)
            VALUES (?,?,?,?,?)
            """;

        try (Connection con = conexion.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idPartida);
            ps.setInt(2, idPregunta);
            ps.setInt(3, puntosObtenidos);
            ps.setInt(4, tiempoRespuesta);
            ps.setBoolean(5, respondioCorrectamente);

            ps.executeUpdate();

        } catch (Exception e) {

            e.printStackTrace();

        }

    }

    //Finaliza una partida.
    public void finalizarPartida(int idPartida,
            int puntuacion,
            int tiempoJugado,
            String estado) {

        String sql = """
                UPDATE Partida
                SET puntuacion=?,
                    tiempo_jugado=?,
                    estado=?
                WHERE id_partida=?
                """;

        try (Connection con = conexion.getConnection(); PreparedStatement ps
                = con.prepareStatement(sql)) {

            ps.setInt(1, puntuacion);
            ps.setInt(2, tiempoJugado);
            ps.setString(3, estado);
            ps.setInt(4, idPartida);

            ps.executeUpdate();

        } catch (Exception e) {

            e.printStackTrace();

        }

    }

    //Actualiza las estadísticas del jugador.
    public void actualizarEstadisticas(int idUsuario,
            int idMinijuego,
            int puntuacion,
            int tiempo) {

        try (Connection con = conexion.getConnection()) {

            String buscar = """
                    SELECT *
                    FROM Estadistica
                    WHERE id_usuario=?
                    AND id_minijuego=?
                    """;

            PreparedStatement psBuscar
                    = con.prepareStatement(buscar);

            psBuscar.setInt(1, idUsuario);
            psBuscar.setInt(2, idMinijuego);

            ResultSet rs = psBuscar.executeQuery();

            if (rs.next()) {

                int mejor = rs.getInt("mejor_puntuacion");

                if (puntuacion > mejor) {

                    mejor = puntuacion;

                }

                String update = """
                        UPDATE Estadistica
                        SET mejor_puntuacion=?,
                            puntuacion_total=puntuacion_total+?,
                            partidas_jugadas=partidas_jugadas+1,
                            tiempo_total=tiempo_total+?
                        WHERE id_usuario=?
                        AND id_minijuego=?
                        """;

                PreparedStatement ps
                        = con.prepareStatement(update);

                ps.setInt(1, mejor);
                ps.setInt(2, puntuacion);
                ps.setInt(3, tiempo);
                ps.setInt(4, idUsuario);
                ps.setInt(5, idMinijuego);

                ps.executeUpdate();

            } else {

                String insert = """
                        INSERT INTO Estadistica
                        (
                            id_usuario,
                            id_minijuego,
                            mejor_puntuacion,
                            puntuacion_total,
                            partidas_jugadas,
                            tiempo_total
                        )
                        VALUES(?,?,?,?,?,?)
                        """;

                PreparedStatement ps
                        = con.prepareStatement(insert);

                ps.setInt(1, idUsuario);
                ps.setInt(2, idMinijuego);
                ps.setInt(3, puntuacion);
                ps.setInt(4, puntuacion);
                ps.setInt(5, 1);
                ps.setInt(6, tiempo);

                ps.executeUpdate();

            }

        } catch (Exception e) {

            e.printStackTrace();

        }

    }

}
