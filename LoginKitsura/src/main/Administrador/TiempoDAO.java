package main.Administrador;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import main.conexion.Conexion;

public class TiempoDAO {

    // Objeto encargado de establecer la conexión con la base de datos.
    private Conexion conexion;

    // Constructor.
    public TiempoDAO() {
        conexion = new Conexion();
    }

    // Obtiene la configuración del tiempo para el minijuego, categoría y nivel seleccionados.
    public DatosConfiguracion obtenerConfiguracion(DatosConfiguracion datos) {

        String sql = """
            SELECT cn.tiempo_limite
            FROM Configuracion_nivel cn
            INNER JOIN Categoria c
                    ON cn.id_categoria = c.id_categoria
            INNER JOIN Minijuego m
                    ON c.id_minijuego = m.id_minijuego
            WHERE m.nombre = ?
            AND c.nombre = ?
            AND cn.dificultad = ?
            """;

        try (Connection con = conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, datos.getMinijuego());
            ps.setString(2, datos.getCategoria());
            ps.setString(3, datos.getNivel());

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                datos.setTiempoLimite(rs.getInt("tiempo_limite"));
            }
            rs.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return datos;
    }

    // Actualiza el tiempo configurado para un nivel.
    // Retorna true si la actualización fue correcta.
    public boolean actualizarTiempo(DatosConfiguracion datos, int tiempo) {

        String sql = """
            UPDATE Configuracion_nivel cn
            INNER JOIN Categoria c
                    ON cn.id_categoria = c.id_categoria
            INNER JOIN Minijuego m
                    ON c.id_minijuego = m.id_minijuego
            SET cn.tiempo_limite = ?
            WHERE m.nombre = ?
            AND c.nombre = ?
            AND cn.dificultad = ?
            """;

        try (Connection con = conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, tiempo);
            ps.setString(2, datos.getMinijuego());
            ps.setString(3, datos.getCategoria());
            ps.setString(4, datos.getNivel());

            int filas = ps.executeUpdate();
            System.out.println("Filas actualizadas: " + filas);

            return filas > 0;

        } catch (Exception e) {
            e.printStackTrace();

        }
        return false;
    }
}