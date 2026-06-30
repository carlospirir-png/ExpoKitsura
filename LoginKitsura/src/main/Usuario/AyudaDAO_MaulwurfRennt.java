package main.Usuario;

import java.sql.*;
import main.conexion.Conexion;

// Clase de Acceso a Datos (DAO) encargada de gestionar la recuperación de pistas escritas asociadas a las preguntas.
public class AyudaDAO_MaulwurfRennt {

    // Consulta la base de datos para extraer la pista textual vinculada a una pregunta específica, validando su tipo y estado.
    public String obtenerPistaTexto(int idPregunta) {

        // Sentencia SQL estructurada para filtrar las ayudas de tipo 'texto' que se encuentren en estado operacional 'activo'.
        String sql =
                "SELECT contenido "
                + "FROM Ayuda "
                + "WHERE id_pregunta = ? "
                + "AND tipo = 'texto' "
                + "AND estado = 'activo'";

        // Bloque Try-With-Resources que inicializa la conexión y prepara la sentencia, garantizando su cierre seguro al finalizar.
        try (Connection con = new Conexion().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            // Asigna el identificador único de la pregunta al parámetro de la consulta.
            ps.setInt(1, idPregunta);

            ResultSet rs = ps.executeQuery();

            // Mapea el resultado y extrae el texto explicativo de la pista si se encuentra un registro coincidente.
            if (rs.next()) {
                return rs.getString("contenido");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        // Mensaje de respaldo amigable en caso de no hallar ninguna pista registrada o ante un fallo en el servidor.
        return "No hay pista disponible.";
    }
}