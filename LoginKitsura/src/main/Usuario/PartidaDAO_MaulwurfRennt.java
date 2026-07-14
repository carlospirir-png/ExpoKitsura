package main.Usuario;

import java.sql.*;

import main.conexion.Conexion;

// Clase de Acceso a Datos (DAO) encargada de la persistencia de las partidas, sus detalles y la actualización de las estadísticas globales de los usuarios.
public class PartidaDAO_MaulwurfRennt {

    // Componente encargado de establecer y gestionar el puente de conexión con el servidor de la base de datos.
    private Conexion conexion;

    // Constructor de la clase: Inicializa el objeto de conexión listo para interactuar con los datos transaccionales del juego.
    public PartidaDAO_MaulwurfRennt() {
        conexion = new Conexion();
    }
        
    // Inserta un nuevo registro de partida en estado "en_curso" y retorna el ID autogenerado por la base de datos.
    public int crearPartida(int idUsuario, int idMinijuego, int vidasIniciales) {

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

        // Inicializa la sentencia solicitando explícitamente el retorno de las llaves primarias generadas automáticamente (Statement.RETURN_GENERATED_KEYS).
        try (Connection con = conexion.getConnection(); PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, idUsuario);
            ps.setInt(2, idMinijuego);
            ps.setInt(3, 0); // Puntuación inicial por defecto.
            ps.setInt(4, vidasIniciales);
            ps.setInt(5, 0); // Tiempo inicial por defecto.
            ps.setString(6, "en_curso");

            ps.executeUpdate();

            // NUEVO: try-with-resources también para el ResultSet de las llaves generadas.
            try (ResultSet rs = ps.getGeneratedKeys()) {
                // Recupera la clave numérica generada para la partida actual.
                if (rs.next()) {
                    idPartida = rs.getInt(1);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return idPartida;
    }

    // Registra de forma pormenorizada cada respuesta individual dada por el usuario en el transcurso de la sesión.
    public void guardarDetalle(int idPartida, int idPregunta, int puntosObtenidos, int tiempoRespuesta, boolean respondioCorrectamente) {

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

    // Actualiza los valores definitivos de una partida específica modificando su puntuación, tiempo y el estado final (por ejemplo: "completada" o "abandonada").
    public void finalizarPartida(int idPartida, int puntuacion, int tiempoJugado, String estado) {

        String sql = """
                UPDATE Partida
                SET puntuacion=?,
                    tiempo_jugado=?,
                    estado=?
                WHERE id_partida=?
                """;

        try (Connection con = conexion.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, puntuacion);
            ps.setInt(2, tiempoJugado);
            ps.setString(3, estado);
            ps.setInt(4, idPartida);

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Acumula las métricas de la partida finalizada en el registro histórico del usuario, evaluando y actualizando además su récord personal de puntuación.
    public void actualizarEstadisticas(int idUsuario, int idMinijuego, int puntuacion, int tiempo) {

        // NUEVO: try-with-resources también para Connection completo del método,
        // ya que aquí se abren varios PreparedStatement dentro del mismo bloque.
        try (Connection con = conexion.getConnection()) {

            String buscar = """
                    SELECT *
                    FROM Estadistica
                    WHERE id_usuario=?
                    AND id_minijuego=?
                    """;

            int mejorExistente = -1; // -1 indica "no existe fila todavía"

            // NUEVO: PreparedStatement y ResultSet de la búsqueda ahora se
            // cierran automáticamente al salir de este bloque.
            try (PreparedStatement psBuscar = con.prepareStatement(buscar)) {
                psBuscar.setInt(1, idUsuario);
                psBuscar.setInt(2, idMinijuego);

                try (ResultSet rs = psBuscar.executeQuery()) {
                    if (rs.next()) {
                        mejorExistente = rs.getInt("mejor_puntuacion");
                    }
                }
            }

            // Bloque de actualización: Si el usuario ya registra estadísticas previas en este minijuego, se modifican los acumuladores.
            if (mejorExistente != -1) {

                int mejor = mejorExistente;

                // Evalúa si la puntuación obtenida en la última partida supera la marca histórica del jugador.
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

                try (PreparedStatement ps = con.prepareStatement(update)) {
                    ps.setInt(1, mejor);
                    ps.setInt(2, puntuacion);
                    ps.setInt(3, tiempo);
                    ps.setInt(4, idUsuario);
                    ps.setInt(5, idMinijuego);

                    ps.executeUpdate();
                }

            // Bloque de inserción: Si es la primera vez que el usuario completa el minijuego, se genera un registro nuevo desde cero.
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

                try (PreparedStatement ps = con.prepareStatement(insert)) {
                    ps.setInt(1, idUsuario);
                    ps.setInt(2, idMinijuego);
                    ps.setInt(3, puntuacion);
                    ps.setInt(4, puntuacion);
                    ps.setInt(5, 1);
                    ps.setInt(6, tiempo);

                    ps.executeUpdate();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}