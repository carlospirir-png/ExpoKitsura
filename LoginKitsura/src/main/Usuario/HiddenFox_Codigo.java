package main.Usuario;

import java.awt.*;
import javax.swing.*;

public class HiddenFox_Codigo extends HiddenFox {

    public HiddenFox_Codigo() {
        //?
        CambiarFondo(1);
        ModificarDificultad(1);
        ModificarNivel(1);
        CambiarFondoPapel("/Multimedia/Minijuegos/Minijuego_1/Elementos_graficos/M1_C1_N1_FONDO.png");

        //BD
        CargarRespuestas(1);
        ModificarCategoria(1);
        CargarImagen(1, false);
    }

    public void N1_C1() {

    }

    public static void main(String[] args) {
        new HiddenFox_Codigo();
    }

}
