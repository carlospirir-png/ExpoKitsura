package main.Usuario;

public class MenuHiddenFox extends MenuMinijuegosC {

    public MenuHiddenFox() {
        super("HIDDEN FOX", "ANIMALES", "TERRITORIOS", "CARICATURAS");

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
        
        setTitle("Hidden Fox");
        ComoJugar();
    }
    
    @Override
    public void ComoJugar(){
        btnComoJugar.addActionListener(e -> {
            new TutorialHiddenFox();
        });
    }

    public static void main(String[] args) {
        new MenuHiddenFox();
    }

}
