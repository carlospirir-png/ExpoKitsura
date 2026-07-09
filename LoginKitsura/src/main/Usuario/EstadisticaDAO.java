package main.Usuario;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import main.conexion.Conexion;

public class EstadisticaDAO {

    //------------------------ CLASE INTERNA: FILA DE RANKING ------------------------
    /*Representa una fila de la tabla "Tabla Global": un jugador con su tiempo
      acumulado y su mejor puntuación para el minijuego consultado.*/
    public static class FilaRanking {
        
        public String nombreUsuario;
        public int tiempoTotal;      // en segundos
        public int puntuacionTotal;

        public FilaRanking(String nombreUsuario, int tiempoTotal, int puntuacionTotal) {
            this.nombreUsuario = nombreUsuario;
            this.tiempoTotal = tiempoTotal;
            this.puntuacionTotal = puntuacionTotal;
        }
    }

    //------------------------ CLASE INTERNA: RESUMEN DEL USUARIO ------------------------
    public static class ResumenUsuario {

        public int puntuacionTotal;
        public int partidasJugadas;
        public int mejorPuntuacion;
        public int tiempoTotal;

        public ResumenUsuario(int puntuacionTotal, int partidasJugadas, int mejorPuntuacion, int tiempoTotal) {
            this.puntuacionTotal = puntuacionTotal;
            this.partidasJugadas = partidasJugadas;
            this.mejorPuntuacion = mejorPuntuacion;
            this.tiempoTotal = tiempoTotal;
        }
    }

    //------------------------ CLASE INTERNA: ÚLTIMA PARTIDA ------------------------
    public static class UltimaPartida {

        public int puntuacion;
        public int tiempoJugado;
        public String estado;

        public UltimaPartida(int puntuacion, int tiempoJugado, String estado) {
            this.puntuacion = puntuacion;
            this.tiempoJugado = tiempoJugado;
            this.estado = estado;
        }
    }

    //------------------------ R A N K I N G   G L O B A L ------------------------
    /*Devuelve el top de jugadores para un minijuego, ordenado por mejor
      puntuación descendente (aprovecha el índice idx_estadistica_ranking).*/
    public List<FilaRanking> obtenerRankingGlobal(int idMinijuego, int limite) {

    List<FilaRanking> ranking = new ArrayList<>();

    // SQL modificado para traer puntuacion_total y ordenar por ella
    String sql
            = "SELECT u.nombre_usuario, e.tiempo_total, e.puntuacion_total " // <--- CAMBIADO
            + "FROM Estadistica e "
            + "INNER JOIN Usuario u ON u.id_usuario = e.id_usuario "
            + "WHERE e.id_minijuego = ? "
            + "ORDER BY e.puntuacion_total DESC " // <--- CAMBIADO
            + "LIMIT ?";

    try (Connection con = new Conexion().getConnection();
            PreparedStatement ps = con.prepareStatement(sql)) {

        ps.setInt(1, idMinijuego);
        ps.setInt(2, limite);

        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            ranking.add(new FilaRanking(
                    rs.getString("nombre_usuario"),
                    rs.getInt("tiempo_total"),
                    rs.getInt("puntuacion_total") // <--- CAMBIADO
            ));
        }

    } catch (SQLException e) {
        System.out.println("Error al obtener el ranking global: " + e.getMessage());
    }

    return ranking;
}

    //------------------------ R E S U M E N   D E L   U S U A R I O ------------------------
    /*Devuelve el resumen acumulado (fila de Estadistica) del usuario para
      ese minijuego. Si nunca ha jugado, devuelve null.*/
    public ResumenUsuario obtenerResumenUsuario(int idUsuario, int idMinijuego) {

        String sql
                = "SELECT mejor_puntuacion, puntuacion_total, partidas_jugadas, tiempo_total "
                + "FROM Estadistica "
                + "WHERE id_usuario = ? AND id_minijuego = ?";

        try (Connection con = new Conexion().getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idUsuario);
            ps.setInt(2, idMinijuego);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new ResumenUsuario(
                        rs.getInt("puntuacion_total"),
                        rs.getInt("partidas_jugadas"),
                        rs.getInt("mejor_puntuacion"),
                        rs.getInt("tiempo_total")
                );
            }

        } catch (SQLException e) {
            System.out.println("Error al obtener el resumen del usuario: " + e.getMessage());
        }

        return null; // el usuario todavía no tiene estadísticas para este minijuego
    }

    //------------------------ P A R T I D A S   G A N A D A S ------------------------
    /*Cuenta cuántas partidas completó (estado = 'completada') el usuario
      en este minijuego.*/
    public int contarPartidasGanadas(int idUsuario, int idMinijuego) {

        String sql
                = "SELECT COUNT(*) AS total "
                + "FROM Partida "
                + "WHERE id_usuario = ? AND id_minijuego = ? AND estado = 'completada'";

        try (Connection con = new Conexion().getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idUsuario);
            ps.setInt(2, idMinijuego);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt("total");
            }

        } catch (SQLException e) {
            System.out.println("Error al contar las partidas ganadas: " + e.getMessage());
        }

        return 0;
    }

    //------------------------ Ú L T I M A   P A R T I D A ------------------------
    /*Como la tabla Partida no tiene columna de fecha, se usa id_partida
      DESC como aproximación de "la más reciente" (al ser AUTO_INCREMENT,
      el id más alto corresponde a la última partida insertada).*/
    public UltimaPartida obtenerUltimaPartida(int idUsuario, int idMinijuego) {

        String sql
                = "SELECT puntuacion, tiempo_jugado, estado "
                + "FROM Partida "
                + "WHERE id_usuario = ? AND id_minijuego = ? "
                + "ORDER BY id_partida DESC "
                + "LIMIT 1";

        try (Connection con = new Conexion().getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idUsuario);
            ps.setInt(2, idMinijuego);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new UltimaPartida(
                        rs.getInt("puntuacion"),
                        rs.getInt("tiempo_jugado"),
                        rs.getString("estado")
                );
            }

        } catch (SQLException e) {
            System.out.println("Error al obtener la última partida: " + e.getMessage());
        }

        return null; // el usuario todavía no ha jugado este minijuego
    }
}
