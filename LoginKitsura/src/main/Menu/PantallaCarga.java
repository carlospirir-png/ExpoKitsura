package main.Menu;

import java.awt.*;
import javax.swing.*;

public class PantallaCarga extends JFrame {
    private Font fuente1;
    private Font fuente2;
    private FondoPanelSemi fondo;

    private FondoPanelSemi panelOscuro;
    private FondoPanelSemi panelOscuro2;

    private JLabel lblFrase;
    private JLabel lblDato;

    public PantallaCarga() {
        try {
            // LettersForLearners
            fuente1 = Font.createFont(
                    Font.TRUETYPE_FONT,
                    getClass().getResourceAsStream("/fuentes/LettersForLearners.ttf"));
            // KGPerfectPenmanship
            fuente2 = Font.createFont(
                    Font.TRUETYPE_FONT,
                    getClass().getResourceAsStream("/fuentes/KGPerfectPenmanship.ttf"));

        } catch (Exception e) {
            e.printStackTrace();
            fuente1 = new Font("Arial", Font.PLAIN, 20);
            fuente2 = new Font("Arial", Font.PLAIN, 20);
        }
        fondo = new FondoPanelSemi("/Multimedia/utiles/FondoPrincipal.png");
        setContentPane(fondo);

        setTitle("Pantalla de Carga");
        setSize(1920, 1080);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        fondo.setLayout(null);

        crearComponentes();

        setVisible(true);
    }
    
    
    private void crearComponentes() {

        panelOscuro2 = new FondoPanelSemi(new Color(0, 0, 0, 170));
        panelOscuro2.setLayout(null);
        panelOscuro2.setBounds(0, 0, 1920, 165);

        fondo.add(panelOscuro2);

        lblFrase = new JLabel("\"NO ES MAGIA, ES MENTE\"", SwingConstants.CENTER);
        lblFrase.setFont(fuente1.deriveFont(35f));
        lblFrase.setForeground(new Color(230, 230, 230));
        lblFrase.setBounds(460, 50, 1000, 70);

        panelOscuro2.add(lblFrase);

        panelOscuro = new FondoPanelSemi(new Color(0, 0, 0, 220));
        panelOscuro.setLayout(null);
        panelOscuro.setBounds(0, 820, 1920, 250);

        fondo.add(panelOscuro);

        lblDato = new JLabel("¿Sabías qué?... Los zorros pueden escuchar pequeños sonidos a más de 30 metros.", SwingConstants.CENTER);
        lblDato.setForeground(Color.WHITE);
        lblDato.setFont(fuente1.deriveFont(40f));
        lblDato.setBounds(210, 50, 1500, 50);

        panelOscuro.add(lblDato);
    }
    public static void main(String[] args) {
        new PantallaCarga();
    }
}
