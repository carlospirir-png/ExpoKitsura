package main.Usuario;

import javax.swing.*;
import java.awt.*;
import main.Menu.FondoPanel;
import main.Menu.FondoPanelSemi;
import main.Menu.DecoracionBotones;

public class MenuMinijuegosC extends JFrame {

    public FondoPanel fondo;
    public Font fuente1;
    public Font fuente2;

    public JLabel lblTitulo;
    public JLabel mascota;

    public JButton btnCategoria1;
    public JButton btnCategoria2;
    public JButton btnCategoria3;
    public JButton btnComoJugar;
    public JButton btnVolver;

    private String textoCategoria1;
    private String textoCategoria2;
    private String textoCategoria3;

    public MenuMinijuegosC(String titulo, String categoria1, String categoria2, String categoria3) {
        try {
            fuente1 = Font.createFont(
                    Font.TRUETYPE_FONT,
                    getClass().getResourceAsStream("/fuentes/LettersForLearners.ttf"));
            fuente2 = Font.createFont(
                    Font.TRUETYPE_FONT,
                    getClass().getResourceAsStream("/fuentes/KGPerfectPenmanship.ttf"));
        } catch (Exception e) {
            e.printStackTrace();
            fuente1 = new Font("Arial", Font.PLAIN, 20);
            fuente2 = new Font("Arial", Font.PLAIN, 20);
        }

        this.textoCategoria1 = categoria1;
        this.textoCategoria2 = categoria2;
        this.textoCategoria3 = categoria3;

        lblTitulo = new JLabel(titulo, SwingConstants.CENTER);
<<<<<<< HEAD
=======
        btnCategoria1 = new DecoracionBotones(categoria1,
                                //ColorBase             ColorBorde              ColorLetra
                DecoracionBotones.AZUL, DecoracionBotones.GRIS, DecoracionBotones.AMARILLO, //MOUSE FUERA
                DecoracionBotones.CELESTE, DecoracionBotones.AZUL, DecoracionBotones.AZUL); //MOUSE DENTRO   

        btnCategoria2 = new DecoracionBotones(categoria2,                 //ColorBase             ColorBorde              ColorLetra
                DecoracionBotones.AZUL, DecoracionBotones.GRIS, DecoracionBotones.AMARILLO, //MOUSE FUERA
                DecoracionBotones.CELESTE, DecoracionBotones.AZUL, DecoracionBotones.AZUL); //MOUSE DENTRO   

        btnCategoria3 = new DecoracionBotones(categoria3,
                                //ColorBase             ColorBorde              ColorLetra
                DecoracionBotones.AZUL, DecoracionBotones.GRIS, DecoracionBotones.AMARILLO, //MOUSE FUERA
                DecoracionBotones.CELESTE, DecoracionBotones.AZUL, DecoracionBotones.AZUL); //MOUSE DENTRO   

>>>>>>> 8d29a9e75b19afa2cfd4b44a742a0f73e91fab9b

        fondo = new FondoPanel("/Multimedia/utiles/fondos/interfaces/fondoDosK.png");
        setContentPane(fondo);
        setTitle("Categorías de Minijuego");
        setSize(1880, 1080);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        fondo.setLayout(null);

        crearComponentes();
        setVisible(true);
    }

    private void crearComponentes() {

        //---------------- BOTÓN ¿CÓMO JUGAR? ----------------
<<<<<<< HEAD
        btnComoJugar = new DecoracionBotones("¿CÓMO JUGAR?", "#FC767D", "#da4d58", "#da4d58");
=======
        btnComoJugar = new DecoracionBotones("¿CÓMO JUGAR?",
                //ColorBase             ColorBorde              ColorLetra
                DecoracionBotones.ROSA, DecoracionBotones.ROJO, DecoracionBotones.AMARILLO, //MOUSE FUERA
                DecoracionBotones.ROJO, DecoracionBotones.ROSA, DecoracionBotones.ROSA); //MOUSE DENTRO
        
>>>>>>> 8d29a9e75b19afa2cfd4b44a742a0f73e91fab9b
        btnComoJugar.setFont(fuente2.deriveFont(25f));
        btnComoJugar.setBounds(100, 100, 280, 65);
        btnComoJugar.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Instrucciones del minijuego...");
        });
        fondo.add(btnComoJugar);

        //---------------- MASCOTA ----------------
        mascota = new JLabel();
        try {
            ImageIcon mascotaIcon = new ImageIcon(getClass().getResource("/Multimedia/utiles/mascotaKitsura/imagen/MINIJUEGO-CONTROL.png"));
            Image mascotaEscalada = mascotaIcon.getImage().getScaledInstance(600, 600, Image.SCALE_SMOOTH);
            mascota.setIcon(new ImageIcon(mascotaEscalada));
        } catch (Exception e) {
            mascota.setText("~");
        }
        mascota.setBounds(150, 280, 600, 600);
        fondo.add(mascota);

        //---------------- PANEL TÍTULO ----------------
        FondoPanelSemi panelTitulo = new FondoPanelSemi(new Color(0, 0, 0, 150));
        panelTitulo.setLayout(null);
        panelTitulo.setBounds(950, 100, 650, 70);
        fondo.add(panelTitulo);

        lblTitulo.setFont(fuente2.deriveFont(40f));
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setBounds(0, 0, 650, 70);
        panelTitulo.add(lblTitulo);

        //---------------- BOTONES CATEGORÍAS ----------------
        btnCategoria1 = new DecoracionBotones(textoCategoria1);
        btnCategoria1.setFont(fuente2.deriveFont(25f));
        btnCategoria1.setBounds(1100, 300, 400, 65);
        fondo.add(btnCategoria1);

        btnCategoria2 = new DecoracionBotones(textoCategoria2, "#E8BE18", "#F0D060", "#3E454C");
        btnCategoria2.setFont(fuente2.deriveFont(25f));
        btnCategoria2.setBounds(1100, 430, 400, 65);
        fondo.add(btnCategoria2);

        btnCategoria3 = new DecoracionBotones(textoCategoria3, "#91BF4B", "#B4DC64", "#3E454C");
        btnCategoria3.setFont(fuente2.deriveFont(25f));
        btnCategoria3.setBounds(1100, 560, 400, 65);
        fondo.add(btnCategoria3);

        //---------------- BOTÓN VOLVER ----------------
        btnVolver = new DecoracionBotones("VOLVER",
                                //ColorBase             ColorBorde              ColorLetra
                DecoracionBotones.AZUL, DecoracionBotones.GRIS, DecoracionBotones.AMARILLO, //MOUSE FUERA
                DecoracionBotones.CELESTE, DecoracionBotones.AZUL, DecoracionBotones.AZUL); //MOUSE DENTRO   

        btnVolver.setFont(fuente2.deriveFont(28f));
        btnVolver.setBounds(1150, 720, 320, 65);
        btnVolver.addActionListener(e -> {
            new MenuMinijuegos();
            dispose();
        });
        fondo.add(btnVolver);
    }

    public static void main(String[] args) {
        new MenuMinijuegosC("Menu Minijuego", "Categoria 1", "Categoria 2", "Categoria 3");
    }
}
