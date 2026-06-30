package main.Usuario;

import java.sql.*;
import java.util.*;

import main.conexion.Conexion;

// Clase de Acceso a Datos (DAO) que gestiona las consultas SQL en la base de datos para las preguntas y opciones del juego.
public class PreguntaDAO_MaulwurfRennt {

    // Componente encargado de establecer el puente de conexión con el servidor de la base de datos.
    private Conexion conexion;

    // Constructor de la clase: Inicializa el objeto de conexión listo para abrir los canales de comunicación SQL.
    public PreguntaDAO_MaulwurfRennt() {
        conexion = new Conexion();
    }

    // Busca y retorna una pregunta aleatoria que coincida con la categoría y dificultad deseadas, omitiendo un listado de IDs para evitar repeticiones.
    public Pregunta_MaulwurfRennt obtenerPreguntaAleatoria(int idCategoria, String dificultad, ArrayList<Integer> preguntasUsadas) {

        Pregunta_MaulwurfRennt pregunta = null;

        // Estructura base de la consulta para enlazar los datos de la pregunta con su configuración de nivel jerárquico.
        StringBuilder sql = new StringBuilder("""
        SELECT p.*
        FROM Pregunta p
        INNER JOIN Configuracion_nivel n
                ON p.id_nivel = n.id_nivel
        WHERE n.id_categoria = ?
        AND n.dificultad = ?
        AND p.estado = 'activo'
        """);

        // Bloque de construcción dinámica: Añade los marcadores posicionales (?) para la cláusula NOT IN si el arreglo contiene IDs excluidos.
        if (!preguntasUsadas.isEmpty()) {

            sql.append(" AND p.id_pregunta NOT IN (");

            for (int i = 0; i < preguntasUsadas.size(); i++) {
                sql.append("?");
                if (i < preguntasUsadas.size() - 1) {
                    sql.append(",");
                }
            }

            sql.append(")");
        }

        // Ordenamiento aleatorio nativo de la base de datos limitado a un solo registro resultante.
        sql.append(" ORDER BY RAND() LIMIT 1");

        // Bloque Try-With-Resources que asegura el cierre automático de los flujos de conexión, declaraciones y sentencias preparadas.
        try (Connection con = conexion.getConnection(); PreparedStatement ps = con.prepareStatement(sql.toString());) {

            // Inyección de los parámetros obligatorios fijos de la consulta.
            ps.setInt(1, idCategoria);
            ps.setString(2, dificultad);

            // Índice variable para inyectar dinámicamente los valores de exclusión dentro del segmento NOT IN.
            int indice = 3;

            for (Integer id : preguntasUsadas) {
                ps.setInt(indice++, id);
            }

            ResultSet rs = ps.executeQuery();

            // Mapeo directo del registro de la base de datos hacia la instancia del modelo de datos de la pregunta.
            if (rs.next()) {

                pregunta = new Pregunta_MaulwurfRennt(
                        rs.getInt("id_pregunta"),
                        rs.getInt("id_nivel"),
                        rs.getString("pregunta"),
                        rs.getInt("puntos_base"),
                        rs.getString("estado"),
                        rs.getString("imagen_sombra"),
                        rs.getString("imagen_color")
                );

                // Consulta e incorpora de forma complementaria las opciones de respuesta correspondientes a la pregunta elegida.
                cargarOpciones(pregunta);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return pregunta;
    }

    // Consulta las opciones de respuesta vinculadas al ID de una pregunta, restringiendo el resultado a un máximo de 7 elementos para el tablero.
    private void cargarOpciones(Pregunta_MaulwurfRennt pregunta) {

        String sql = """
                SELECT *
                FROM Opcion_respuesta
                WHERE id_pregunta=?
                LIMIT 7
                """;

        try (Connection con = conexion.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, pregunta.getIdPregunta());

            ResultSet rs = ps.executeQuery();

            // Recorre secuencialmente las filas obtenidas creando los objetos de las opciones y registrándolos en la pregunta.
            while (rs.next()) {

                pregunta.agregarOpcion(
                        new OpcionRespuesta_MaulwurfRennt(
                                rs.getInt("id_opcion"),
                                rs.getInt("id_pregunta"),
                                rs.getString("texto_opcion"),
                                rs.getBoolean("es_correcta")
                        )
                );
            }

            // Desordena aleatoriamente el orden de los elementos del listado para que la respuesta correcta cambie constantemente de posición en la interfaz.
            if (!pregunta.getOpciones().isEmpty()) {
                Collections.shuffle(pregunta.getOpciones());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}