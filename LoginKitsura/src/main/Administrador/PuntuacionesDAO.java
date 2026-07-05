//------------------- PUNTUACIONES DAO ----------------
package main.Administrador;

import java.sql.*; //Importamos sql
import main.conexion.Conexion; //Importamos la conexión

//Clase
public class PuntuacionesDAO {
    //-------------- A T R I B U T O S ------------
    
    private Conexion conexion = new Conexion(); //Creamos un objeto del tipo Conexion
    //Guardamos en una variable la conexión
    Connection con = conexion.getConnection();
    
    public PuntuacionesDAO() {
        
    }
    
    
    
    //------------- OBTENER PUNTUACIÓN ------------------
    public int obtenerPuntuacion(String minijuego, String categoria, String nivel){
        //Recibimos a través de los parámetros los datos del minijuego, categoría y nivel a modificar
        
        //Inciializamos la variable de los puntos
        int puntos = 0;
        
        //Consulta
                    //Selecciona puntos base de la tabla Pregunta junto con la configuracion del nivel
        String sql = "SELECT puntos_base FROM Pregunta p INNER JOIN Configuracion_nivel cn ON p.id_nivel = cn.id_nivel "
                //Junto con la categoria
                + "INNER JOIN Categoria c ON cn.id_categoria = c.id_categoria "
                //Junto con el minijuego
                + "INNER JOIN Minijuego m ON c.id_minijuego = m.id_minijuego "
                //Cuando el nombre del minijuego, categoría y nivel coincidan (Limitando 1)
                + "WHERE m.nombre = ? AND c.nombre = ? AND cn.dificultad = ? LIMIT 1";
        
        
        try (PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, minijuego);
            ps.setString(2, categoria);
            ps.setString(3, nivel);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                //Obtenemos los puntos de la base
                puntos = rs.getInt("puntos_base");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return puntos;
    }
    
    //----------------- ACTUALIZAR PUNTUACIÓN ----------------
    public void actualizarPuntuacion(String minijuego, String categoria, String nivel, int nuevaPuntuacion){
        //Consulta
                    //Actualiza la tabla de pregunta junto con el nivel
        String sql = "UPDATE Pregunta p INNER JOIN Configuracion_nivel cn ON p.id_nivel = cn.id_nivel "
                //Junto con la categoria
                + "INNER JOIN Categoria c ON cn.id_categoria = c.id_categoria "
                //Junto con el minijuego
                + "INNER JOIN Minijuego m ON c.id_minijuego = m.id_minijuego "
                //Actualizar los puntos base
                + "SET p.puntos_base = ? "
                //Cuando el nombre del minijuego, categoría y nivel coincidan
                + "WHERE m.nombre = ? AND c.nombre = ? AND cn.dificultad = ?";
        
        try (PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, nuevaPuntuacion);
            ps.setString(2, minijuego);
            ps.setString(3, categoria);
            ps.setString(4, nivel);
            
            ps.executeUpdate();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}
