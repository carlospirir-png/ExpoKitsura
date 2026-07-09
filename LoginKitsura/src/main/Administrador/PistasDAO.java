//-------------------------------- PISTAS DAO ---------------------
package main.Administrador;

import java.sql.*;
import main.conexion.Conexion;

public class PistasDAO {

    private Conexion conexion = new Conexion(); //Creamos un objeto del tipo Conexion
    //Guardamos en una variable la conexión
    Connection con = conexion.getConnection();

    public PistasDAO() {

    }

    //--------------------------- P I S T A   T E X T O --------------------------    
    //------------------------------- ACTUALIZAR PISTA -----------------------
    public boolean actualizarPistaTexto(int id_ayuda, String pistaTexto) {
        //Recibimos a través de los parámetros el id de la pista y el texto

        //Consulta
        //Actualiza la tabla ayuda con el contenido cuando el id coincida y el enum sea tipo texto
        String sql = "UPDATE Ayuda SET contenido = ? WHERE id_ayuda = ? AND tipo = 'texto'";

        try (PreparedStatement ps = con.prepareStatement(sql);) {

            ps.setString(1, pistaTexto);
            ps.setInt(2, id_ayuda);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

    }

    //-------------------------- AGREGAR PISTA -------------------------
    public boolean agregarPistaTexto(int id_pregunta, String pistaTexto) {
        //Recibimos a través de los parámetros el id de la pregunta y el texto

        //Consulta
        //Inserta en la tabla ayuda, el id de pregunta, el tipo (texto) y el contenido de la pista Texto
        String sql = "INSERT INTO Ayuda (id_pregunta, tipo, contenido) VALUES (?, 'texto', ?)";

        try (PreparedStatement ps = con.prepareStatement(sql);) {

            ps.setInt(1, id_pregunta);
            ps.setString(2, pistaTexto);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {

            e.printStackTrace();
            return false;

        }

    }

    //--------------------- ELIMINAR PISTA ------------------
    public boolean eliminarPistaTexto(int id_ayuda) {

        String sql = "DELETE FROM Ayuda WHERE id_ayuda = ? AND tipo = 'texto'";

        try (PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id_ayuda);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {

            e.printStackTrace();
            return false;
        }
    }

    //--------------------------- OBTENER PISTA ----------------
    public ResultSet obtenerPistasTexto() {

        //Consulta
        String sql = "SELECT id_ayuda, contenido FROM Ayuda WHERE tipo = 'texto' ORDER BY id_ayuda";
        try {
            PreparedStatement ps = con.prepareStatement(sql);

            return ps.executeQuery();

        } catch (SQLException e) {

            e.printStackTrace();
            return null;

        }

    }
}
