package main.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Collections;

import main.conexion.Conexion;

public class PreguntaDAO_MaulwurfRennt {

    private Conexion conexion;

    public PreguntaDAO_MaulwurfRennt() {
        conexion = new Conexion();
    }

    public Pregunta_MaulwurfRennt obtenerPreguntaAleatoria(String dificultad) {

        Pregunta_MaulwurfRennt pregunta = null;

        String sql = """
            SELECT p.*
            FROM Pregunta p
            INNER JOIN Configuracion_nivel cn
                ON p.id_nivel = cn.id_nivel
            INNER JOIN Categoria c
                ON cn.id_categoria = c.id_categoria
            WHERE c.id_minijuego = 3
              AND cn.dificultad = ?
              AND p.estado = 'activo'
            ORDER BY RAND()
            LIMIT 1
            """;

        try (Connection con = conexion.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, dificultad);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                pregunta = new Pregunta_MaulwurfRennt(
                        rs.getInt("id_pregunta"),
                        rs.getInt("id_nivel"),
                        rs.getString("pregunta"),
                        rs.getInt("puntos_base"),
                        rs.getString("estado"),
                        rs.getString("imagen_sombra"),
                        rs.getString("imagen_color")
                );

                cargarOpciones(pregunta);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return pregunta;
    }

    private void cargarOpciones(Pregunta_MaulwurfRennt pregunta) {

        String sql = """
                SELECT *
                FROM Opcion_respuesta
                WHERE id_pregunta=?
                LIMIT 7
                """;

        try (Connection con = conexion.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, pregunta.getIdPregunta());

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                pregunta.agregarOpcion(
                        new OpcionRespuesta_MaulwurfRennt(
                                rs.getInt("id_opcion"),
                                rs.getInt("id_pregunta"),
                                rs.getString("texto_opcion"),
                                rs.getBoolean("es_correcta")
                        )
                );
            }

            if (!pregunta.getOpciones().isEmpty()) {
                Collections.shuffle(pregunta.getOpciones());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
