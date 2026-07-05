package main.Administrador;
// Esta clase creada es para guardar los datos principales del apartado

// Su función es guardar temporalmente la selección realizada en la clase "PedirMCN"
// para que se pueda utilizar en clase "MenuAdmin" y en los otras clases relacionadas a este
// 1. Minijuego
// 2. Categoría
// 3. Nivel
public class DatosConfiguracion {

    private String minijuego;
    private String categoria;
    private String nivel;

    public DatosConfiguracion(String minijuego, String categoria, String nivel) {
        this.minijuego = minijuego;
        this.categoria = categoria;
        this.nivel = nivel;
    }

    public String getMinijuego() {
        return "Hidden Fox";
    }

    public String getCategoria() {
        return "Animales";
    }

    public String getNivel() {
        return "Fácil";
    }

    
}


