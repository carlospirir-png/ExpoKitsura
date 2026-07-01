package main.Usuario;

import java.util.ArrayList;

// Clase modelo que representa una pregunta dentro del minijuego "Atrapa al topo".
// Almacena los metadatos de la pregunta, rutas de imágenes asociadas y sus opciones de respuesta.
public class Pregunta_MaulwurfRennt {

    // Atributos privados que identifican la pregunta, su nivel, puntajes asignados y estados en el juego.
    private int idPregunta;
    private int idNivel;
    private String pregunta;
    private int puntosBase;
    private String estado;
    private String imagenSombra;
    private String imagenColor;

    // Lista dinámica que contiene el conjunto de opciones de respuesta asignadas a esta pregunta específica.
    private ArrayList<OpcionRespuesta_MaulwurfRennt> opciones;

    // Constructor por defecto: Inicializa el contenedor dinámico de opciones para prevenir excepciones de tipo NullPointerException.
    public Pregunta_MaulwurfRennt() {
        opciones = new ArrayList<>();
    }

    // Constructor parametrizado: Asigna los valores correspondientes a todas las propiedades base de la pregunta e inicializa el listado de opciones.
    public Pregunta_MaulwurfRennt(int idPregunta, int idNivel, String pregunta,
                                  int puntosBase, String estado,
                                  String imagenSombra, String imagenColor) {

        this.idPregunta = idPregunta;
        this.idNivel = idNivel;
        this.pregunta = pregunta;
        this.puntosBase = puntosBase;
        this.estado = estado;
        this.imagenSombra = imagenSombra;
        this.imagenColor = imagenColor;
        this.opciones = new ArrayList<>();
    }

    // Obtiene el identificador único de la pregunta en la base de datos.
    public int getIdPregunta() {
        return idPregunta;
    }

    // Define el identificador único de la pregunta.
    public void setIdPregunta(int idPregunta) {
        this.idPregunta = idPregunta;
    }

    // Obtiene el identificador del nivel al que pertenece la pregunta.
    public int getIdNivel() {
        return idNivel;
    }

    // Define el identificador del nivel al que pertenece la pregunta.
    public void setIdNivel(int idNivel) {
        this.idNivel = idNivel;
    }

    // Obtiene el texto o enunciado de la pregunta que se mostrará en la interfaz visual.
    public String getPregunta() {
        return pregunta;
    }

    // Define el texto o enunciado de la pregunta.
    public void setPregunta(String pregunta) {
        this.pregunta = pregunta;
    }

    // Obtiene el puntaje base que recompensa esta pregunta al ser respondida de forma correcta.
    public int getPuntosBase() {
        return puntosBase;
    }

    // Define el puntaje base asignado a la pregunta.
    public void setPuntosBase(int puntosBase) {
        this.puntosBase = puntosBase;
    }

    // Obtiene el estado operativo actual de la pregunta (por ejemplo: Activa o Inactiva).
    public String getEstado() {
        return estado;
    }

    // Define el estado operativo de la pregunta.
    public void setEstado(String estado) {
        this.estado = estado;
    }

    // Obtiene la ruta del archivo de imagen correspondiente a la silueta o sombra de la pregunta.
    public String getImagenSombra() {
        return imagenSombra;
    }

    // Define la ruta de la imagen en formato silueta o sombra.
    public void setImagenSombra(String imagenSombra) {
        this.imagenSombra = imagenSombra;
    }

    // Obtiene la ruta del archivo de imagen a color que se revela al responder.
    public String getImagenColor() {
        return imagenColor;
    }

    // Define la ruta de la imagen definitiva a color.
    public void setImagenColor(String imagenColor) {
        this.imagenColor = imagenColor;
    }

    // Obtiene la colección completa de opciones de respuesta vinculadas a la pregunta.
    public ArrayList<OpcionRespuesta_MaulwurfRennt> getOpciones() {
        return opciones;
    }

    // Asigna un lote completo de opciones de respuesta reemplazando la lista previa.
    public void setOpciones(ArrayList<OpcionRespuesta_MaulwurfRennt> opciones) {
        this.opciones = opciones;
    }

    // Añade de forma individual un objeto de opción de respuesta al listado interno de la pregunta.
    public void agregarOpcion(OpcionRespuesta_MaulwurfRennt opcion) {
        opciones.add(opcion);
    }

    // Sobrescribe el comportamiento por defecto de toString para devolver el texto directo de la pregunta facilitando la depuración.
    @Override
    public String toString() {
        return pregunta;
    }
}