package main.Administrador;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import main.conexion.Conexion;

/*
 * DAO encargado de alimentar los JComboBox de PedirMCN con los valores
 * EXACTOS que existen en la base de datos (Minijuego, Categoria,
 * Configuracion_nivel). Al elegir de una lista en vez de escribir el
 * nombre a mano, se elimina por completo el riesgo de que "Hidden Fox"
 * se escriba como "HiddenFox" y la consulta de VidasDAO no encuentre
 * coincidencia.
 */
public class SeleccionDAO_Vidas {

    private Conexion conexion;

    public SeleccionDAO_Vidas() {
        conexion = new Conexion();
    }

    // Devuelve los minijuegos activos, en el orden en que fueron registrados.
    public List<String> obtenerMinijuegos() {
        List<String> minijuegos = new ArrayList<>();

        String sql = """
            SELECT nombre
            FROM Minijuego
            WHERE estado = 'activo'
            ORDER BY id_minijuego
            """;

        try (Connection con = conexion.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                minijuegos.add(rs.getString("nombre"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return minijuegos;
    }

    // Devuelve las categorías que pertenecen al minijuego indicado.
    public List<String> obtenerCategorias(String minijuego) {
        List<String> categorias = new ArrayList<>();

        String sql = """
            SELECT c.nombre
            FROM Categoria c
            INNER JOIN Minijuego m
                    ON c.id_minijuego = m.id_minijuego
            WHERE m.nombre = ?
            ORDER BY c.id_categoria
            """;

        try (Connection con = conexion.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, minijuego);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                categorias.add(rs.getString("nombre"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return categorias;
    }

    // Devuelve las dificultades (Fácil/Intermedio/Difícil) configuradas para
    // la combinación minijuego + categoría indicada.
    public List<String> obtenerDificultades(String minijuego, String categoria) {
        List<String> dificultades = new ArrayList<>();

        String sql = """
            SELECT cn.dificultad
            FROM Configuracion_nivel cn
            INNER JOIN Categoria c
                    ON cn.id_categoria = c.id_categoria
            INNER JOIN Minijuego m
                    ON c.id_minijuego = m.id_minijuego
            WHERE m.nombre = ?
              AND c.nombre = ?
            ORDER BY cn.id_nivel
            """;

        try (Connection con = conexion.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, minijuego);
            ps.setString(2, categoria);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                dificultades.add(rs.getString("dificultad"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return dificultades;
    }
}
