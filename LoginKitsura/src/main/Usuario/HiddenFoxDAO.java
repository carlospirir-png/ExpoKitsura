//------------------------------ HIDDEN FOX DAO ------------------------
package main.Usuario; //Paquete

import java.sql.*; //Se importa el sql
import main.conexion.*; //Se importa la conexion
import java.util.*; //Se importan los útiles

//Clase HiddenFoxDAO
public class HiddenFoxDAO {

    //----------------- ATRIBUTOS ----------------
    private Connection con;

    public HiddenFoxDAO() {
        con = new Conexion().getConnection();
    }

    /*---------------- M É T O D O S ----------------
    ------------------ T I E M P O*/
    public int obtenerTiempoLimite(int id_pregunta) {

        String sql
                = "SELECT cn.tiempo_limite "
                + "FROM Pregunta p "
                + "INNER JOIN Configuracion_nivel cn "
                + "ON p.id_nivel = cn.id_nivel "
                + "WHERE p.id_pregunta = ?";

        try (PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id_pregunta);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt("tiempo_limite");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 15; // valor por defecto
    }

    //---------------------- P I S T A S
    //true texto | false audios
    //---------------------- PISTAS TEXTO
    public String obtenerPista(int id_pregunta) {
        String sql
                = "SELECT contenido "
                + "FROM Ayuda "
                + "WHERE id_pregunta = ? "
                + "AND tipo = 'texto' "
                + "ORDER BY RAND() "
                + "LIMIT 1";

        try (PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id_pregunta);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getString("contenido");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return "No hay pista disponible.";
    }

    //--------------------- PISTAS AUDIO
    public String obtenerRutaAudio(int id_pregunta) {
        String sql = "SELECT audio "
                + "FROM Ayuda "
                + "WHERE id_pregunta = ? "
                + "AND tipo = 'audio' "
                + "ORDER BY RAND() "
                + "LIMIT 1";

        try (PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id_pregunta);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getString("audio");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    //----------------------- PENALIZACIÓN PISTAS
    //esTexto | true = texto  | false = audio.
    public int obtenerPenalizacionPista(int id_pregunta, boolean esTexto) {
        String pista;
        if (esTexto) {
            pista = "texto";
        } else {
            pista = "audio";
        }

        String sql
                = "SELECT penalizacion_puntos "
                + "FROM Ayuda "
                + "WHERE id_pregunta = ? "
                + "AND tipo = ?";

        try (PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id_pregunta);
            ps.setString(2, pista);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt("penalizacion_puntos");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 50; // valor por defecto
    }

    //---------------------- I M Á G E N E S
    //boolean color:   false = sombra      |       true = color
    public String obtenerRutaImagen(int id_pregunta, boolean color) {

        String sql = "SELECT imagen_sombra, imagen_color FROM Pregunta WHERE id_pregunta = ?";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id_pregunta);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                if (color) {
                    return rs.getString("imagen_color");
                } else {
                    return rs.getString("imagen_sombra");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    //------------------------ C A T E G O R Í A
    public String obtenerCategoria(int id_categoria) {
        String sql = "SELECT nombre FROM Categoria WHERE id_categoria = ?";

        try (PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id_categoria);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getString("nombre");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "Categoría no encontrada.";
    }

    //----------------------- R E S P U E S T A S
    public void obtenerRespuestas(int id_pregunta, String[] respuestas, boolean[] correctas) {

        String sql = "SELECT texto_opcion, es_correcta FROM Opcion_respuesta WHERE id_pregunta = ? ORDER BY RAND()";

        try (PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id_pregunta);

            ResultSet rs = ps.executeQuery();

            int i = 0;

            while (rs.next() && i < 4) {
                respuestas[i] = rs.getString("texto_opcion");
                correctas[i] = rs.getBoolean("es_correcta");
                i++;
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // ----------------------- PREGUNTA -------------------
    public String obtenerPregunta(int id_pregunta) {
        String sql = "SELECT pregunta FROM Pregunta WHERE id_pregunta = ?";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id_pregunta);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getString("pregunta");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return "Pregunta no encontrada.";
    }

    //------------ GENERAR PARTIDA ------------
    //Método que devuelve un ArrayList de tipo Integer
    public ArrayList<Integer> generarPartida(int id_nivel) {
        //Recibimos el id del nivel a través del parámetro

        //Creamos el ArrayList vacío (por ahora) de tipo Integer
        ArrayList<Integer> preguntas = new ArrayList<>();

        //Consulta:
        //Selecciona el id pregunta de la tabla Pregunta cuando el id nivel coincida con el parámetro en orden al azar (RAND())
        String sql = "SELECT id_pregunta FROM Pregunta WHERE id_nivel = ? ORDER BY RAND() ";

        //Se prepara el sql
        try (PreparedStatement ps = con.prepareStatement(sql)) {

            //Se llenan los parámetros
            ps.setInt(1, id_nivel);

            //Se ejecuta la consulta
            ResultSet rs = ps.executeQuery();

            //Mientras haya un dato en la siguiente fila
            while (rs.next()) {
                //Se obtiene el valor de la columna y fila que apunta el rs
                //ese valor obtenido se agrega al ArrayList
                preguntas.add(rs.getInt("id_pregunta"));
            }

            //Captura la excepción SQL
        } catch (SQLException e) {

            System.out.println("Error al generar partida: " + e.getMessage());
        }

        //Devuelve el ArrayList con los id de nivel
        return preguntas;
    }
    
    
    // /*/*/*/ Funcion de Estadisticas /*/*/*/
      //------------------------ MÉTODOS DE PERSISTENCIA DE PARTIDA
      /*A partir de aquí se agregan los métodos necesarios para que el
      minijuego guarde su progreso en las tablas Partida, Detalle_partida
      y Estadistica. Se mantiene el mismo estilo (try-with-resources sobre
      el PreparedStatement, sin cerrar "con", igual que el resto del DAO).*/
    
    
    //------------------------ C R E A R   P A R T I D A
    /*Se llama una única vez, al iniciar una partida nueva (no en cada
      cambio de dificultad dentro de la misma categoría). Devuelve el
      id_partida generado por la base de datos, o -1 si algo falló.*/
    public int crearPartida(int idUsuario, int idMinijuego, int vidasInicialesSnapshot) {

        String sql
                = "INSERT INTO Partida (id_usuario, id_minijuego, vidas_iniciales_snapshot) "
                + "VALUES (?, ?, ?)";

        try (PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, idUsuario);
            ps.setInt(2, idMinijuego);
            ps.setInt(3, vidasInicialesSnapshot);

            ps.executeUpdate();

            //Se obtiene el id_partida generado automáticamente por el AUTO_INCREMENT
            ResultSet rs = ps.getGeneratedKeys();

            if (rs.next()) {
                return rs.getInt(1);
            }

        } catch (SQLException e) {
            System.out.println("Error al crear la partida: " + e.getMessage());
        }

        return -1;
    }

    //------------------------ R E G I S T R A R   D E T A L L E
    /*Guarda el resultado de UNA pregunta respondida (o vencida por tiempo)
      dentro de la partida actual.*/
    public void registrarDetalle(int idPartida, int idPregunta, int puntosObtenidos,
            int tiempoRespuesta, boolean respondioCorrectamente) {

        String sql
                = "INSERT INTO Detalle_partida "
                + "(id_partida, id_pregunta, puntos_obtenidos, tiempo_respuesta, respondio_correctamente) "
                + "VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idPartida);
            ps.setInt(2, idPregunta);
            ps.setInt(3, puntosObtenidos);
            ps.setInt(4, tiempoRespuesta);
            ps.setBoolean(5, respondioCorrectamente);

            ps.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Error al registrar el detalle de la partida: " + e.getMessage());
        }
    }

    //------------------------ F I N A L I Z A R   P A R T I D A
    /*Actualiza la partida cuando termina, ya sea por victoria, por
      derrota de vidas o por tiempo agotado.
      estado esperado: "completada" | "abandonada"*/
    public void finalizarPartida(int idPartida, int puntuacion, int tiempoJugado, String estado) {

        String sql
                = "UPDATE Partida "
                + "SET puntuacion = ?, tiempo_jugado = ?, estado = ? "
                + "WHERE id_partida = ?";

        try (PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, puntuacion);
            ps.setInt(2, tiempoJugado);
            ps.setString(3, estado);
            ps.setInt(4, idPartida);

            ps.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Error al finalizar la partida: " + e.getMessage());
        }
    }

    //------------------------ A C T U A L I Z A R   E S T A D Í S T I C A
    /*Inserta o actualiza (gracias a la UNIQUE KEY uq_estadistica) el
      resumen histórico del usuario para este minijuego.*/
    public void actualizarEstadistica(int idUsuario, int idMinijuego, int puntuacion, int tiempoJugado) {

        String sql
                = "INSERT INTO Estadistica "
                + "(id_usuario, id_minijuego, mejor_puntuacion, puntuacion_total, partidas_jugadas, tiempo_total) "
                + "VALUES (?, ?, ?, ?, 1, ?) "
                + "ON DUPLICATE KEY UPDATE "
                + "mejor_puntuacion = GREATEST(mejor_puntuacion, VALUES(mejor_puntuacion)), "
                + "puntuacion_total = puntuacion_total + VALUES(puntuacion_total), "
                + "partidas_jugadas = partidas_jugadas + 1, "
                + "tiempo_total = tiempo_total + VALUES(tiempo_total)";

        try (PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idUsuario);
            ps.setInt(2, idMinijuego);
            ps.setInt(3, puntuacion);
            ps.setInt(4, puntuacion);
            ps.setInt(5, tiempoJugado);

            ps.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Error al actualizar la estadística: " + e.getMessage());
        }
    }
}
