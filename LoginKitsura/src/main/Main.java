package main;

import javax.swing.JOptionPane;
import main.Usuario.*;
import main.Menu.*;
import main.conexion.Conexion;

public class Main {

    public static void main(String[] args) {
        Conexion conectar = new Conexion();

        if (conectar.getConnection() != null) {
            PantallaInicio nuevo = new PantallaInicio();
            nuevo.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(
                    null,
                    "Error al conectar con la base de datos",
                    "Error de conexión",
                    JOptionPane.ERROR_MESSAGE
            );
        }
            
        
        HiddenFox_Codigo hiddenfox = new HiddenFox_Codigo();
    }
}