package main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {
    private static String User = "root";
    private static String Password = "123456789";
    private static String Url = "jdbc:mysql://localhost:3306/KITSURA_DB";

    public Connection getConnection() {
        Connection conx = null;
        try {
            conx = (Connection) DriverManager.getConnection(Url, User, Password);
            System.out.println("Conexion Establecida");
        } catch (SQLException e) {
            System.out.println("Error en la Conexion");
        }
        return conx;

    }
}
