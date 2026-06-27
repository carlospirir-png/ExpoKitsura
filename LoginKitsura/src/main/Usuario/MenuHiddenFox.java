package main.Usuario;

public class MenuHiddenFox extends MenuMinijuegosC {

    public MenuHiddenFox() {
        super("Hidden Fox", "ANIMALES", "TERRITORIOS", "CARICATURAS");

        btnCategoria1.addActionListener(e -> {
            new HiddenFox_Codigo(1);
            dispose();
        });

        btnCategoria2.addActionListener(e -> {
            new HiddenFox_Codigo(4);
            dispose();
        });

        btnCategoria3.addActionListener(e -> {
            new HiddenFox_Codigo(7);
            dispose();
        });
        
        
    }

    public static void main(String[] args) {
        new MenuHiddenFox();
    }

}
