package main.Usuario;

import java.awt.*;
import javax.swing.*;

public class HiddenFox_Codigo extends HiddenFox {

    public HiddenFox_Codigo() {
        CambiarFondo(2);
        CambiarFondoPapel("/Multimedia/Minijuegos/Minijuego_1/Elementos_graficos/M1_C1_N1_FONDO.png");
        CambiarImagen("/Multimedia/Minijuegos/Minijuego_1/Categoria_1_M1/Nivel_1_C1_M1/Imagen_N1_C1_M1/Sombra_N1_C1_M1/N1_C1_M1_S_BuhoNival.png");
        ModificarCategoria(1);
        ModificarDificultad(2);
        ModificarNivel(1);
        
        CargarRespuestas(1, "Hola");
        CargarRespuestas(2, "Waza");
        CargarRespuestas(3, "skibidi");
        CargarRespuestas(4, "insano");
    }
    
    public void N1_C1(){
        
    }
    
    public static void main(String[] args) {
        new HiddenFox_Codigo();
    }

}
