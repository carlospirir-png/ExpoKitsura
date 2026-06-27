package main.Usuario;

public class OpcionRespuesta_MaulwurfRennt {

    private int idOpcion;
    private int idPregunta;
    private String textoOpcion;
    private boolean correcta;

    public OpcionRespuesta_MaulwurfRennt() {
    }

    public OpcionRespuesta_MaulwurfRennt(int idOpcion, int idPregunta,
                                         String textoOpcion, boolean correcta) {
        this.idOpcion = idOpcion;
        this.idPregunta = idPregunta;
        this.textoOpcion = textoOpcion;
        this.correcta = correcta;
    }

    public int getIdOpcion() {
        return idOpcion;
    }

    public void setIdOpcion(int idOpcion) {
        this.idOpcion = idOpcion;
    }

    public int getIdPregunta() {
        return idPregunta;
    }

    public void setIdPregunta(int idPregunta) {
        this.idPregunta = idPregunta;
    }

    public String getTextoOpcion() {
        return textoOpcion;
    }

    public void setTextoOpcion(String textoOpcion) {
        this.textoOpcion = textoOpcion;
    }

    public boolean isCorrecta() {
        return correcta;
    }

    public void setCorrecta(boolean correcta) {
        this.correcta = correcta;
    }

    @Override
    public String toString() {
        return textoOpcion;
    }
}