package main;

import javax.swing.JOptionPane;

public class Main {

    public static void main(String[] args) {
        Conexion conectar = new Conexion();

        if (conectar.getConnection() != null) {
            MenuPrincipal nuevo = new MenuPrincipal();
            nuevo.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(
                    null,
                    "Error al conectar con la base de datos",
                    "Error de conexión",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }
}
