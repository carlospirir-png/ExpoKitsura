package main.Usuario;

import java.util.ArrayList;

public class Pregunta_MaulwurfRennt {

    private int idPregunta;
    private int idNivel;
    private String pregunta;
    private int puntosBase;
    private String estado;
    private String imagenSombra;
    private String imagenColor;

    // Lista de opciones de respuesta
    private ArrayList<OpcionRespuesta_MaulwurfRennt> opciones;

    public Pregunta_MaulwurfRennt() {
        opciones = new ArrayList<>();
    }

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

    public int getIdPregunta() {
        return idPregunta;
    }

    public void setIdPregunta(int idPregunta) {
        this.idPregunta = idPregunta;
    }

    public int getIdNivel() {
        return idNivel;
    }

    public void setIdNivel(int idNivel) {
        this.idNivel = idNivel;
    }

    public String getPregunta() {
        return pregunta;
    }

    public void setPregunta(String pregunta) {
        this.pregunta = pregunta;
    }

    public int getPuntosBase() {
        return puntosBase;
    }

    public void setPuntosBase(int puntosBase) {
        this.puntosBase = puntosBase;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getImagenSombra() {
        return imagenSombra;
    }

    public void setImagenSombra(String imagenSombra) {
        this.imagenSombra = imagenSombra;
    }

    public String getImagenColor() {
        return imagenColor;
    }

    public void setImagenColor(String imagenColor) {
        this.imagenColor = imagenColor;
    }

    public ArrayList<OpcionRespuesta_MaulwurfRennt> getOpciones() {
        return opciones;
    }

    public void setOpciones(ArrayList<OpcionRespuesta_MaulwurfRennt> opciones) {
        this.opciones = opciones;
    }

    public void agregarOpcion(OpcionRespuesta_MaulwurfRennt opcion) {
        opciones.add(opcion);
    }

    @Override
    public String toString() {
        return pregunta;
    }
}