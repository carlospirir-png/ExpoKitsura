package main.Usuario;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import main.Menu.*;

public class MenuMinijuegoC3 extends JFrame {

    private final FondoPanel fondo;
    private Font fuente1;
    private Font fuente2;
    private TutorialMaulwurfRennt tutorial;
    private boolean tutorialAbierto = false;
    private JButton btnComoJugar;

    public MenuMinijuegoC3() {

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

        fondo = new FondoPanel("/Multimedia/utiles/fondos/interfaces/fondoDosK.png");
        setContentPane(fondo);
        setTitle("MaulwurfRennt");
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
        btnComoJugar = new DecoracionBotones("¿CÓMO JUGAR?",
                //ColorBase             ColorBorde              ColorLetra
                DecoracionBotones.ROSA, DecoracionBotones.ROJO, DecoracionBotones.AMARILLO, //MOUSE FUERA
                DecoracionBotones.ROJO, DecoracionBotones.ROSA, DecoracionBotones.ROSA); //MOUSE DENTRO
        btnComoJugar.setFont(fuente2.deriveFont(25f));
        btnComoJugar.setBounds(100, 100, 280, 65);
        btnComoJugar.addActionListener(e -> {
  
                   if (tutorialAbierto) {
                return;
            }

            tutorialAbierto = true;
            btnComoJugar.setVisible(false); // Oculta el botón

            tutorial = new TutorialMaulwurfRennt();

            tutorial.addWindowListener(new WindowAdapter() {

                @Override
                public void windowClosed(WindowEvent e) {
                    tutorialAbierto = false;
                    tutorial = null;
                    btnComoJugar.setVisible(true); // Lo vuelve a mostrar
                }

            });
        });
        fondo.add(btnComoJugar);

        //---------------- MASCOTA ----------------
        JLabel mascota = new JLabel();
        try {
            ImageIcon mascotaIcon = new ImageIcon(
                    getClass().getResource("/Multimedia/utiles/mascotaKitsura/imagen/MINIJUEGO-CONTROL.png"));
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

        JLabel lblTitulo = new JLabel("Maulwurf Rennt", SwingConstants.CENTER);
        lblTitulo.setFont(fuente2.deriveFont(40f));
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setBounds(0, 0, 650, 70);
        panelTitulo.add(lblTitulo);

        //---------------- CATEGORÍA 1 ----------------
        JButton btnCategoria1 = new DecoracionBotones("OPERACIONES BASICAS",
                //ColorBase             ColorBorde              ColorLetra
                DecoracionBotones.AZUL, DecoracionBotones.GRIS, DecoracionBotones.AMARILLO, //MOUSE FUERA
                DecoracionBotones.CELESTE, DecoracionBotones.AZUL, DecoracionBotones.AZUL); //MOUSE DENTRO 
        btnCategoria1.setFont(fuente2.deriveFont(25f));
        btnCategoria1.setBounds(1100, 300, 400, 65);
        btnCategoria1.addActionListener(e -> {
            new JuegoMaulwurfRennt(1,7);
            dispose();
        });
        fondo.add(btnCategoria1);

        //---------------- CATEGORÍA 2 ----------------
        JButton btnCategoria2 = new DecoracionBotones("OPERACIONES AVANZADAS",
                //ColorBase             ColorBorde              ColorLetra
                DecoracionBotones.AMARILLO_MOSTAZA, DecoracionBotones.GRIS, DecoracionBotones.AMARILLO, //MOUSE FUERA
                DecoracionBotones.AMARILLO_SUAVE, DecoracionBotones.AMARILLO_MOSTAZA, DecoracionBotones.AMARILLO_MOSTAZA); //MOUSE DENTRO
        btnCategoria2.setFont(fuente2.deriveFont(25f));
        btnCategoria2.setBounds(1100, 430, 400, 65);
        btnCategoria2.addActionListener(e -> {
            new JuegoMaulwurfRennt(1,8);
            dispose();
        });
        fondo.add(btnCategoria2);

        //---------------- CATEGORÍA 3 ----------------
        JButton btnCategoria3 = new DecoracionBotones("CIENTIFICOS MATEMATICOS",
                //ColorBase             ColorBorde              ColorLetra
                DecoracionBotones.VERDE, DecoracionBotones.GRIS, DecoracionBotones.AMARILLO, //MOUSE FUERA
                DecoracionBotones.VERDE_SUAVE, DecoracionBotones.VERDE, DecoracionBotones.VERDE); //MOUSE DENTRO
        btnCategoria3.setFont(fuente2.deriveFont(24f));
        btnCategoria3.setBounds(1100, 560, 400, 65);
        btnCategoria3.addActionListener(e -> {
           new JuegoMaulwurfRennt(1,9);
            dispose();
        });
        fondo.add(btnCategoria3);

        //---------------- BOTÓN VOLVER ----------------
        JButton btnVolver = new DecoracionBotones("VOLVER",
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
        SwingUtilities.invokeLater(MenuMinijuegoC3::new);
    }
}