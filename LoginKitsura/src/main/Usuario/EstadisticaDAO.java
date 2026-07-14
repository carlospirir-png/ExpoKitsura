package main.Usuario;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import main.conexion.Conexion;

public class EstadisticaDAO {

    //------------------------ CLASE INTERNA: FILA DE RANKING ------------------------
    /*Representa una fila de la tabla "Tabla Global": un jugador con su tiempo
      total acumulado y su puntuación total, SUMADOS entre los 3 minijuegos.*/
    public static class FilaRanking {

        public String nombreUsuario;
        public int tiempoTotal;       // en segundos, suma de los 3 minijuegos
        public int puntuacionTotal;   // suma de puntuacion_total de los 3 minijuegos

        public FilaRanking(String nombreUsuario, int tiempoTotal, int puntuacionTotal) {
            this.nombreUsuario = nombreUsuario;
            this.tiempoTotal = tiempoTotal;
            this.puntuacionTotal = puntuacionTotal;
        }
    }

    //------------------------ CLASE INTERNA: RESUMEN DEL USUARIO ------------------------
    /*Resumen GLOBAL del usuario: agrega (suma o máximo, según corresponda)
      las filas que tenga en Estadistica para Hidden Fox, Fox Jump! y
      Maulwurf Rennt.*/
    public static class ResumenUsuario {

        public int puntuacionTotal;   // SUMA de puntuacion_total de los 3 minijuegos
        public int partidasJugadas;   // SUMA de partidas_jugadas de los 3 minijuegos
        public int mejorPuntuacion;   // MAX de mejor_puntuacion entre los 3 minijuegos
        public int tiempoTotal;       // SUMA de tiempo_total de los 3 minijuegos

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
        public String nombreMinijuego; // NUEVO: para dar contexto, ya que ahora puede ser cualquiera de los 3

        public UltimaPartida(int puntuacion, int tiempoJugado, String estado, String nombreMinijuego) {
            this.puntuacion = puntuacion;
            this.tiempoJugado = tiempoJugado;
            this.estado = estado;
            this.nombreMinijuego = nombreMinijuego;
        }
    }

    //------------------------ R A N K I N G   G L O B A L ------------------------
    /*Devuelve el top de jugadores considerando los 3 minijuegos juntos.
      Cada usuario puede tener hasta 3 filas en Estadistica (una por
      minijuego); aquí se agrupan y se suman para obtener un solo valor
      "global" por usuario.*/
    public List<FilaRanking> obtenerRankingGlobal(int limite) {

        List<FilaRanking> ranking = new ArrayList<>();

        String sql
                = "SELECT u.nombre_usuario, "
                + "       SUM(e.tiempo_total) AS tiempo_total, "
                + "       SUM(e.puntuacion_total) AS puntuacion_total "
                + "FROM Estadistica e "
                + "INNER JOIN Usuario u ON u.id_usuario = e.id_usuario "
                + "GROUP BY e.id_usuario, u.nombre_usuario "
                + "ORDER BY puntuacion_total DESC "
                + "LIMIT ?";

        try (Connection con = new Conexion().getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, limite);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                ranking.add(new FilaRanking(
                        rs.getString("nombre_usuario"),
                        rs.getInt("tiempo_total"),
                        rs.getInt("puntuacion_total")
                ));
            }

        } catch (SQLException e) {
            System.out.println("Error al obtener el ranking global: " + e.getMessage());
        }

        return ranking;
    }

    //------------------------ R E S U M E N   D E L   U S U A R I O ------------------------
    /*Agrega las filas de Estadistica del usuario para los 3 minijuegos en
      un solo resumen. Si el usuario nunca ha jugado ningún minijuego,
      devuelve un resumen en ceros (no null), para simplificar el uso en la UI.*/
    public ResumenUsuario obtenerResumenUsuario(int idUsuario) {

        String sql
                = "SELECT "
                + "    COALESCE(SUM(puntuacion_total), 0) AS puntuacion_total, "
                + "    COALESCE(SUM(partidas_jugadas), 0) AS partidas_jugadas, "
                + "    COALESCE(MAX(mejor_puntuacion), 0) AS mejor_puntuacion, "
                + "    COALESCE(SUM(tiempo_total), 0) AS tiempo_total "
                + "FROM Estadistica "
                + "WHERE id_usuario = ?";

        try (Connection con = new Conexion().getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idUsuario);

            ResultSet rs = ps.executeQuery();

            // Como es una agregación sin GROUP BY, SIEMPRE devuelve una fila
            // (aunque el usuario no tenga ninguna partida, gracias a COALESCE).
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

        return new ResumenUsuario(0, 0, 0, 0);
    }

    //------------------------ P A R T I D A S   G A N A D A S ------------------------
    /*Cuenta cuántas partidas completó (estado = 'completada') el usuario
      en CUALQUIERA de los 3 minijuegos.*/
    public int contarPartidasGanadas(int idUsuario) {

        String sql
                = "SELECT COUNT(*) AS total "
                + "FROM Partida "
                + "WHERE id_usuario = ? AND estado = 'completada'";

        try (Connection con = new Conexion().getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idUsuario);

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
    /*Última partida jugada por el usuario, sin importar el minijuego.
      Igual que antes, se usa id_partida DESC como aproximación de "más
      reciente" porque Partida no tiene columna de fecha. Se hace JOIN con
      Minijuego para poder mostrar de cuál se trató.*/
    public UltimaPartida obtenerUltimaPartida(int idUsuario) {

        String sql
                = "SELECT p.puntuacion, p.tiempo_jugado, p.estado, m.nombre AS nombre_minijuego "
                + "FROM Partida p "
                + "INNER JOIN Minijuego m ON m.id_minijuego = p.id_minijuego "
                + "WHERE p.id_usuario = ? "
                + "ORDER BY p.id_partida DESC "
                + "LIMIT 1";

        try (Connection con = new Conexion().getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idUsuario);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new UltimaPartida(
                        rs.getInt("puntuacion"),
                        rs.getInt("tiempo_jugado"),
                        rs.getString("estado"),
                        rs.getString("nombre_minijuego")
                );
            }

        } catch (SQLException e) {
            System.out.println("Error al obtener la última partida: " + e.getMessage());
        }

        return null; // el usuario todavía no ha jugado ningún minijuego
    }

    //------------------------ G U A R D A R   P A R T I D A ------------------------
    /*Registra el resultado de una partida (para CUALQUIER minijuego) y
      actualiza/crea la fila correspondiente en Estadistica para ese
      usuario + minijuego. Como la Tabla Global agrega las filas de los
      3 minijuegos al leer (SUM/MAX sin filtrar por id_minijuego), basta
      con que cada minijuego mantenga su propia fila aquí.*/
    public void guardarPartida(int idUsuario, String nombreMinijuego,
            int puntuacion, int tiempoJugado, int vidasIniciales, boolean victoria) {

        // TRADUCCION UNICA A LOS VALORES REALES DEL ENUM DE Partida.estado
        String estado = victoria ? "completada" : "abandonada";

        try (Connection con = new Conexion().getConnection()) {

            if (con == null) {
                System.out.println("No se pudo conectar a la BD para guardar la partida.");
                return;
            }

            int idMinijuego = obtenerIdMinijuego(con, nombreMinijuego);
            if (idMinijuego == -1) {
                System.out.println("No se encontró el minijuego: " + nombreMinijuego);
                return;
            }

            insertarPartida(con, idUsuario, idMinijuego, puntuacion, tiempoJugado, vidasIniciales, estado);
            actualizarEstadistica(con, idUsuario, idMinijuego, puntuacion, tiempoJugado);

        } catch (SQLException e) {
            System.out.println("Error al guardar la partida: " + e.getMessage());
        }
    }

    private int obtenerIdMinijuego(Connection con, String nombre) throws SQLException {
        String sql = "SELECT id_minijuego FROM Minijuego WHERE nombre = ? LIMIT 1";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, nombre);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("id_minijuego");
            }
        }
        return -1;
    }

    private void insertarPartida(Connection con, int idUsuario, int idMinijuego,
            int puntuacion, int tiempoJugado, int vidasIniciales, String estado) throws SQLException {

        String sql = "INSERT INTO Partida "
                + "(id_usuario, id_minijuego, puntuacion, vidas_iniciales_snapshot, tiempo_jugado, estado) "
                + "VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idUsuario);
            ps.setInt(2, idMinijuego);
            ps.setInt(3, puntuacion);
            ps.setInt(4, vidasIniciales);
            ps.setInt(5, tiempoJugado);
            ps.setString(6, estado);
            ps.executeUpdate();
        }
    }

    /*Upsert manual: primero intenta SUMAR sobre la fila existente
      (id_usuario + id_minijuego). Si no existe ninguna fila (0 filas
      afectadas), la crea desde cero.*/
    private void actualizarEstadistica(Connection con, int idUsuario, int idMinijuego,
            int puntuacion, int tiempoJugado) throws SQLException {

        String sqlUpdate = "UPDATE Estadistica SET "
                + "puntuacion_total = puntuacion_total + ?, "
                + "partidas_jugadas = partidas_jugadas + 1, "
                + "mejor_puntuacion = GREATEST(mejor_puntuacion, ?), "
                + "tiempo_total = tiempo_total + ? "
                + "WHERE id_usuario = ? AND id_minijuego = ?";

        int filasActualizadas;
        try (PreparedStatement ps = con.prepareStatement(sqlUpdate)) {
            ps.setInt(1, puntuacion);
            ps.setInt(2, puntuacion);
            ps.setInt(3, tiempoJugado);
            ps.setInt(4, idUsuario);
            ps.setInt(5, idMinijuego);
            filasActualizadas = ps.executeUpdate();
        }

        if (filasActualizadas == 0) {
            String sqlInsert = "INSERT INTO Estadistica "
                    + "(id_usuario, id_minijuego, puntuacion_total, partidas_jugadas, mejor_puntuacion, tiempo_total) "
                    + "VALUES (?, ?, ?, 1, ?, ?)";

            try (PreparedStatement ps = con.prepareStatement(sqlInsert)) {
                ps.setInt(1, idUsuario);
                ps.setInt(2, idMinijuego);
                ps.setInt(3, puntuacion);
                ps.setInt(4, puntuacion);
                ps.setInt(5, tiempoJugado);
                ps.executeUpdate();
            }
        }
    }
}
