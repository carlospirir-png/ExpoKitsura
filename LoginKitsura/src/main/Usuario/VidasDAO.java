// ==================== VIDAS DAO ====================
// Obtener las vidas actuales y actualizar las vidas
package main.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import main.conexion.Conexion;

public class VidasDAO {

    // Objeto encargado de establecer la conexión con la base de datos.
    private Conexion conexion;

    // Constructor.
    public VidasDAO() {
        conexion = new Conexion();
    }

    // Obtiene la cantidad de vidas configuradas para un nivel.
    public int obtenerVidas(String minijuego, String categoria, String nivel) {

        int vidas = 3;
        
        String sql = """
            SELECT cn.vidas
            FROM Configuracion_nivel cn
            INNER JOIN Categoria c
                    ON cn.id_categoria = c.id_categoria
            INNER JOIN Minijuego m
                    ON c.id_minijuego = m.id_minijuego
            WHERE m.nombre = ?
            AND c.nombre = ?
            AND cn.dificultad = ?
            """;

        try (Connection con = conexion.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, minijuego);
            ps.setString(2, categoria);
            ps.setString(3, nivel);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                vidas = rs.getInt("vidas");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return vidas;
    }

    // Actualiza la cantidad de vidas de un nivel.
    // return true si la actualización fue correcta.
    public boolean actualizarVidas(String minijuego,
            String categoria,
            String nivel,
            int vidas) {

        String sql = """
            UPDATE Configuracion_nivel cn
            INNER JOIN Categoria c
                    ON cn.id_categoria = c.id_categoria
            INNER JOIN Minijuego m
                    ON c.id_minijuego = m.id_minijuego
            SET cn.vidas  = ?
            WHERE m.nombre = ?
            AND c.nombre = ?
            AND cn.dificultad = ?
            """;

        try (Connection con = conexion.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, vidas);
            ps.setString(2, minijuego);
            ps.setString(3, categoria);
            ps.setString(4, nivel);
            int filas = ps.executeUpdate();
            System.out.println("Filas actualizadas: " + filas);
            return filas > 0;
            // return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    // Obtiene la cantidad de vidas configurada para TODA la categoría
    // (Fácil, Intermedio, Difícil) de una misma categoría comparten el mismo
    // valor de vidas, así que basta con consultar una de ellas como referencia
    public int obtenerVidasCategoria(String minijuego, String categoria) {
        return obtenerVidas(minijuego, categoria, "Fácil");
    }

    // Actualiza la cantidad de vidas para TODAS las dificultades de la categoría
    // return true si la actualización fue correcta.
    public boolean actualizarVidasCategoria(String minijuego,
            String categoria,
            int vidas) {

        String sql = """
            UPDATE Configuracion_nivel cn
            INNER JOIN Categoria c
                    ON cn.id_categoria = c.id_categoria
            INNER JOIN Minijuego m
                    ON c.id_minijuego = m.id_minijuego
            SET cn.vidas  = ?
            WHERE m.nombre = ?
            AND c.nombre = ?
            """;

        try (Connection con = conexion.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, vidas);
            ps.setString(2, minijuego);
            ps.setString(3, categoria);
            int filas = ps.executeUpdate();
            System.out.println("Filas actualizadas (categoría completa): " + filas);
            return filas > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
}