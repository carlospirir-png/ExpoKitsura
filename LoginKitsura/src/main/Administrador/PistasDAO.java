//-------------------------------- PISTAS DAO ---------------------
package main.Administrador;

import java.sql.*;
import javax.swing.JOptionPane;
import main.conexion.Conexion;

public class PistasDAO {

    private Conexion conexion = new Conexion(); //Creamos un objeto del tipo Conexion
    //Guardamos en una variable la conexión
    Connection con = conexion.getConnection();

    public PistasDAO() {

    }

    //--------------------------- P I S T A   T E X T O --------------------------    
    //------------------------------- ACTUALIZAR PISTA -----------------------
    public boolean actualizarPistaTexto(String pistaTexto, String minijuego, String categoria, String nivel, int id_ayuda) {
        //Recibimos a través de los parámetros el id de la pista y el texto

        //Consulta
        //Actualiza la tabla ayuda con el contenido cuando el id coincida y el enum sea tipo texto
        String sql = "UPDATE Ayuda a "
                + "INNER JOIN Pregunta p ON a.id_pregunta = p.id_pregunta "
                + "INNER JOIN Configuracion_nivel cn ON p.id_nivel = cn.id_nivel "
                + "INNER JOIN Categoria c ON cn.id_categoria = c.id_categoria "
                + "INNER JOIN Minijuego m ON c.id_minijuego = m.id_minijuego "
                + "SET contenido = ? "
                + "WHERE m.nombre = ? AND c.nombre = ? AND cn.dificultad = ?  AND id_ayuda = ? AND tipo = 'texto'";

        try (PreparedStatement ps = con.prepareStatement(sql);) {

            ps.setString(1, pistaTexto);
            ps.setString(2, minijuego);
            ps.setString(3, categoria);
            ps.setString(4, nivel);
            ps.setInt(5, id_ayuda);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

    }

    //-------------------------- AGREGAR PISTA -------------------------
    public boolean agregarPistaTexto(int id_pregunta, String pistaTexto, String minijuego, String categoria, String nivel) {
        //Recibimos a través de los parámetros el id de la pregunta y el texto

        //Consulta
        //Inserta en la tabla ayuda, el id de pregunta, el tipo (texto) y el contenido de la pista Texto
        String sql
                = "INSERT INTO Ayuda (id_pregunta, tipo, contenido) "
                + "SELECT p.id_pregunta, 'texto', ? "
                + "FROM Pregunta p "
                + "INNER JOIN Configuracion_nivel cn ON p.id_nivel = cn.id_nivel "
                + "INNER JOIN Categoria c ON cn.id_categoria = c.id_categoria "
                + "INNER JOIN Minijuego m ON c.id_minijuego = m.id_minijuego "
                + "WHERE p.id_pregunta = ? "
                + "AND m.nombre = ? "
                + "AND c.nombre = ? "
                + "AND cn.dificultad = ?";

        try (PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, pistaTexto);
            ps.setInt(2, id_pregunta);
            ps.setString(3, minijuego);
            ps.setString(4, categoria);
            ps.setString(5, nivel);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

    }

    //--------------------- ELIMINAR PISTA ------------------
    public boolean eliminarPistaTexto(int id_ayuda, String minijuego, String categoria, String nivel) {

        String sql
                = "DELETE a "
                + "FROM Ayuda a "
                + "INNER JOIN Pregunta p ON a.id_pregunta = p.id_pregunta "
                + "INNER JOIN Configuracion_nivel cn ON p.id_nivel = cn.id_nivel "
                + "INNER JOIN Categoria c ON cn.id_categoria = c.id_categoria "
                + "INNER JOIN Minijuego m ON c.id_minijuego = m.id_minijuego "
                + "WHERE a.id_ayuda = ? "
                + "AND a.tipo = 'texto' "
                + "AND m.nombre = ? "
                + "AND c.nombre = ? "
                + "AND cn.dificultad = ?";

        try (PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id_ayuda);
            ps.setString(2, minijuego);
            ps.setString(3, categoria);
            ps.setString(4, nivel);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    //--------------------------- OBTENER PISTA ----------------
    public ResultSet obtenerPistasTexto(String minijuego, String categoria, String nivel) {

        //Consulta
        String sql
                = "SELECT a.id_ayuda, a.id_pregunta, a.contenido "
                + "FROM Ayuda a "
                + "INNER JOIN Pregunta p ON a.id_pregunta = p.id_pregunta "
                + "INNER JOIN Configuracion_nivel cn ON p.id_nivel = cn.id_nivel "
                + "INNER JOIN Categoria c ON cn.id_categoria = c.id_categoria "
                + "INNER JOIN Minijuego m ON c.id_minijuego = m.id_minijuego "
                + "WHERE a.tipo = 'texto' "
                + "AND m.nombre = ? "
                + "AND c.nombre = ? "
                + "AND cn.dificultad = ? "
                + "ORDER BY a.id_ayuda";

        try {
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, minijuego);
            ps.setString(2, categoria);
            ps.setString(3, nivel);

            return ps.executeQuery();

        } catch (SQLException e) {
            e.printStackTrace();
            return null;

        }
    }

    //---------------------------- BUSCAR PISTA -----------------
    public ResultSet buscarPistaTexto(String id_ayuda, String id_pregunta, String minijuego, String categoria, String nivel) {

        String sql
                = "SELECT a.id_ayuda, a.id_pregunta, a.contenido "
                + "FROM Ayuda a "
                + "INNER JOIN Pregunta p ON a.id_pregunta = p.id_pregunta "
                + "INNER JOIN Configuracion_nivel cn ON p.id_nivel = cn.id_nivel "
                + "INNER JOIN Categoria c ON cn.id_categoria = c.id_categoria "
                + "INNER JOIN Minijuego m ON c.id_minijuego = m.id_minijuego "
                + "WHERE a.tipo='texto' "
                + "AND m.nombre=? "
                + "AND c.nombre=? "
                + "AND cn.dificultad=? "
                + "AND (? = '' OR CAST(a.id_ayuda AS CHAR) LIKE ?) "
                + "AND (? = '' OR CAST(a.id_pregunta AS CHAR) LIKE ?) "
                + "ORDER BY a.id_ayuda";

        try {
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, minijuego);
            ps.setString(2, categoria);
            ps.setString(3, nivel);

            ps.setString(4, id_ayuda);
            ps.setString(5, id_ayuda + "%");

            ps.setString(6, id_pregunta);
            ps.setString(7, id_pregunta + "%");

            return ps.executeQuery();

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
