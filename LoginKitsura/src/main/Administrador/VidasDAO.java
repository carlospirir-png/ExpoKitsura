// Obtener las vidas actuales y actualizar las vidas
package main.Administrador;

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

    // * Obtiene la cantidad de vidas configuradas para un nivel.
    // * minijuego: Nombre del minijuego.
    // * categoria: Nombre de la categoría.
    // * nivel: Dificultad del nivel.
    // * Cantidad de vidas.
    public int obtenerVidas(String minijuego, String categoria, String nivel) {

        int vidas = 3;

        String sql = """
            UPDATE Configuracion_nivel cn
            INNER JOIN Categoria c
                    ON cn.id_categoria = c.id_categoria
            INNER JOIN Minijuego m
                    ON c.id_minijuego = m.id_minijuego
            SET cn.vidas_iniciales = ?
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
                vidas = rs.getInt("vidas_iniciales");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return vidas;
    }

    // Actualiza la cantidad de vidas de un nivel.
    // minijuego Nombre del minijuego.
    // categoria: Categoría.
    // nivel Dificultad.
    // vidas Nueva: cantidad de vidas.
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
            SET cn.vidas = ?
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
}
