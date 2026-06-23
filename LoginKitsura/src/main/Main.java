package main;

import javax.swing.JOptionPane;
import main.Usuario.*;
import main.Menu.*;
import main.conexion.Conexion;

public class Main {

    public static void main(String[] args) {

        try {
            Conexion conectar = new Conexion();

            if (conectar.getConnection() == null) {
                throw new Exception("No se pudo establecer la conexión con la base de datos.");
            }

            MaulwurfRennt nuevo = new MaulwurfRennt();
            nuevo.setVisible(true);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(
                    null,
                    "Error al conectar con la base de datos\n" + e.getMessage(),
                    "Error de conexión",
                    JOptionPane.ERROR_MESSAGE
            );
        }
<<<<<<< HEAD

=======
>>>>>>> e9c8b9b (Redimension de interfaces de juegos)
    }
}