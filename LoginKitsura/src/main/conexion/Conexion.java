package main.conexion;

import java.sql.*;

public class Conexion {
    private static String User = "root";
    private static String Password = "admin";
 private static String Url =
    "jdbc:mysql://localhost:3306/KITSURA_DB"
    + "?useSSL=false"
    + "&allowPublicKeyRetrieval=true"
    + "&serverTimezone=America/Guatemala";

    public Connection getConnection() {
        Connection conx = null;
        try {
            conx = (Connection) DriverManager.getConnection(Url, User, Password);
            System.out.println("Conexion Establecida");
        } catch (SQLException e) {
            System.out.println("Error en la Conexion "+e);
        }
        return conx;
    }
}
