package main.Usuario;

import java.awt.*;
import java.net.URL;
import javax.swing.*;
import main.Menu.*;

public class MenuMinijuegos extends JFrame {

    private FondoPanel fondo;
    private Font fuente1;
    private Font fuente2;

    public MenuMinijuegos() {
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
        //------------- ÍCONO ------------------
        //se obtiene la imagen del logo con getResource
        URL iconUrl = getClass().getResource("/Multimedia/utiles/logotipo/logofK.png");

        //se instancia el ícono con la imagen
        ImageIcon icono = new ImageIcon(iconUrl);

        //Se coloca el ícono al JFrame
        setIconImage(icono.getImage());

        setTitle("Minijuegos");
        setSize(1880, 1080);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        fondo.setLayout(null);

        crearComponentes();
        setVisible(true);
    }

    private void crearComponentes() {

        //---------------- PANEL TÍTULO ----------------
        FondoPanelSemi panelTitulo = new FondoPanelSemi(new Color(0, 0, 0, 150));
        panelTitulo.setLayout(null);
        panelTitulo.setBounds(780, 100, 420, 70);
        fondo.add(panelTitulo);

        JLabel lblTitulo = new JLabel("MINIJUEGOS", JLabel.CENTER);
        lblTitulo.setFont(fuente2.deriveFont(45f));
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setBounds(0, 0, 420, 70);
        panelTitulo.add(lblTitulo);

        //---------------- BOTONES MINIJUEGOS ----------------
        JButton btnHiddenFox = new DecoracionBotones("HIDDEN FOX",
                //ColorBase             ColorBorde              ColorLetra
                DecoracionBotones.ROSA, DecoracionBotones.ROJO, DecoracionBotones.AMARILLO, //MOUSE FUERA
                DecoracionBotones.ROJO, DecoracionBotones.ROSA, DecoracionBotones.ROSA); //MOUSE DENTRO
        
        btnHiddenFox.setFont(fuente2.deriveFont(25f));
        btnHiddenFox.setBounds(780, 340, 400, 65);
        btnHiddenFox.addActionListener(e -> {
            new MenuHiddenFox();
            dispose();
        });
        fondo.add(btnHiddenFox);

        JButton btnFoxJump = new DecoracionBotones("FOX JUMP !",
                 //ColorBase             ColorBorde              ColorLetra
                DecoracionBotones.AMARILLO_MOSTAZA, DecoracionBotones.GRIS, DecoracionBotones.AMARILLO, //MOUSE FUERA
                DecoracionBotones.AMARILLO_SUAVE, DecoracionBotones.AMARILLO_MOSTAZA, DecoracionBotones.AMARILLO_MOSTAZA); //MOUSE DENTRO 

        btnFoxJump.setFont(fuente2.deriveFont(25f));
        btnFoxJump.setBounds(780, 460, 400, 65);
        btnFoxJump.addActionListener(e -> {
            new MenuMinijuegoC2();
            dispose();
        });
        fondo.add(btnFoxJump);

        JButton btnMaulwurf = new DecoracionBotones("MAULWURF RENNT",
                               //ColorBase             ColorBorde              ColorLetra
                DecoracionBotones.VERDE, DecoracionBotones.GRIS, DecoracionBotones.AMARILLO, //MOUSE FUERA
                DecoracionBotones.VERDE_SUAVE, DecoracionBotones.VERDE, DecoracionBotones.VERDE); //MOUSE DENTRO  

        btnMaulwurf.setFont(fuente2.deriveFont(25f));
        btnMaulwurf.setBounds(780, 580, 400, 65);
        btnMaulwurf.addActionListener(e -> {
            new MenuMinijuegoC3();
            dispose();
        });
        fondo.add(btnMaulwurf);

        //---------------- BOTÓN VOLVER ----------------
        JButton btnVolver = new DecoracionBotones("VOLVER",
                                //ColorBase             ColorBorde              ColorLetra
                DecoracionBotones.AZUL, DecoracionBotones.GRIS, DecoracionBotones.AMARILLO, //MOUSE FUERA
                DecoracionBotones.CELESTE, DecoracionBotones.AZUL, DecoracionBotones.AZUL); //MOUSE DENTRO   

        btnVolver.setFont(fuente2.deriveFont(28f));
        btnVolver.setBounds(820, 720, 320, 65);
        btnVolver.addActionListener(e -> {
            new MenuPrincipal();
            dispose();
        });
        fondo.add(btnVolver);

        //---------------- MASCOTA ----------------
        JLabel mascota = new JLabel();
        try {
            ImageIcon mascotaIcon = new ImageIcon(getClass().getResource("/Multimedia/utiles/mascotaKitsura/imagen/MINIJUEGO-CONTROL.png"));
            Image mascotaEscalada = mascotaIcon.getImage().getScaledInstance(600, 600, Image.SCALE_SMOOTH);
            mascota.setIcon(new ImageIcon(mascotaEscalada));
        } catch (Exception e) {
            mascota.setText("~");
        }
        mascota.setBounds(1245, 300, 600, 600);
        fondo.add(mascota);
    }
}