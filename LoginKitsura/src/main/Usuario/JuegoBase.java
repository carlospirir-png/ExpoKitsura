package main.Usuario;

import javax.swing.JFrame;

public interface JuegoBase {
    void reiniciar();
    void irAlMenu();
    void jugarDeNuevo();
    void mostrarResultadoConFade();
    JFrame getFrame();
    
    int getPuntajeTotal();
int getTiempoTotalJugado();
}