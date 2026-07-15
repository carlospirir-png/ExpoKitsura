package main.Menu;

import java.awt.*;
import java.net.*;
import javax.swing.*;
import main.Usuario.RegistroUsuario;

public class PantallaInicio extends JFrame {

    private final FondoPanelSemi fondo;

    private PanelDecoracion panelDecoracion;

    private JLabel lblLogo;
    private JLabel lblFrase;
    private DecoracionBotones btnJugar;
    private Font fuente1;
    private Font fuente2;

    public PantallaInicio() {

        try {
            // LettersForLearners
            fuente1 = Font.createFont(
                    Font.TRUETYPE_FONT,
                    getClass().getResourceAsStream("/fuentes/LettersForLearners.ttf"));
            // KGPerfectPenmanship
            fuente2 = Font.createFont(Font.TRUETYPE_FONT,
                    getClass().getResourceAsStream("/fuentes/KGPerfectPenmanship.ttf"));
        } catch (Exception e) {
            e.printStackTrace();
            fuente1 = new Font("Arial", Font.PLAIN, 20);
            fuente2 = new Font("Arial", Font.PLAIN, 20);
        }

        fondo = new FondoPanelSemi("/Multimedia/utiles/fondos/fondoPrincipal/FondoPrincipal2.png");
        setContentPane(fondo);
        
        //----------------- J       F R A M E 

        //------------- ÍCONO ------------------
        //se obtiene la imagen del logo con getResource
        URL iconUrl = getClass().getResource("/Multimedia/utiles/logotipo/logofK.png");

        //se instancia el ícono con la imagen
        ImageIcon icono = new ImageIcon(iconUrl);

        //Se coloca el ícono al JFrame
        setIconImage(icono.getImage());


        setTitle("Kitsura");
        setSize(1920, 1080);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        fondo.setLayout(null);
        crearComponentes();
        setVisible(true);
    }

    private void crearComponentes() {

        panelDecoracion = new PanelDecoracion();
        panelDecoracion.setBounds(590, 0, 700, 700);

        fondo.add(panelDecoracion);

        FondoPanelSemi panelFrase = new FondoPanelSemi(new Color(0, 0, 0, 140));
        panelFrase.setBounds(550, 690, 800, 70);
        panelFrase.setLayout(null);

        fondo.add(panelFrase);

        lblLogo = new JLabel();

        ImageIcon logoIcon = new ImageIcon(getClass().getResource("/Multimedia/utiles/logotipo/LogoKitsura.png"));
        Image logoEscalado = logoIcon.getImage().getScaledInstance(570, 600, Image.SCALE_SMOOTH);

        lblLogo.setIcon(new ImageIcon(logoEscalado));
        lblLogo.setBounds(650, 100, 570, 600);

        fondo.add(lblLogo);

        fondo.setComponentZOrder(lblLogo, 0);

        lblFrase = new JLabel("\"No es magia, es mente\"", SwingConstants.CENTER);
        lblFrase.setFont(fuente1.deriveFont(55f));
        lblFrase.setForeground(new Color(196, 221, 227));
        lblFrase.setBounds(530, 690, 850, 60);

        fondo.add(lblFrase);

        //---------------- BOTON JUGAR ----------------
        btnJugar = new DecoracionBotones("JUGAR",
                //ColorBase             ColorBorde              ColorLetra
                DecoracionBotones.AZUL, DecoracionBotones.GRIS, DecoracionBotones.AMARILLO, //MOUSE FUERA
                DecoracionBotones.CELESTE, DecoracionBotones.AZUL, DecoracionBotones.AZUL); //MOUSE DENTRO      
        btnJugar.setFont(fuente2.deriveFont(30f));
        btnJugar.setBounds(810, 880, 250, 70);
        btnJugar.addActionListener(e -> {
            new RegistroUsuario();
            dispose(); // Cierra PantallaInicio
        });
        fondo.add(btnJugar);
        fondo.setComponentZOrder(btnJugar, 0);
        fondo.setComponentZOrder(lblFrase, 0);

    }

    public static void main(String[] args) {
        new PantallaInicio();
    }
}
