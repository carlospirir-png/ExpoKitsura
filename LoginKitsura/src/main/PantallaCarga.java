package main;

import java.awt.*;
import javax.swing.*;

public class PantallaCarga extends JFrame {

    private FondoPanelSemi fondo;

    private FondoPanelSemi panelOscuro;
    private FondoPanelSemi panelOscuro2;

    private JLabel lblFrase;
    private JLabel lblDato;

    public PantallaCarga() {

        fondo = new FondoPanelSemi("/Multimedia/utiles/FondoPrincipal2.png");
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
        panelOscuro2.setBounds(0, 0, 1920, 180);

        fondo.add(panelOscuro2);

        lblFrase = new JLabel("\"NO ES MAGIA, ES MENTE\"", SwingConstants.CENTER);
        lblFrase.setFont(new Font("Segoe UI", Font.BOLD, 46));
        lblFrase.setForeground(new Color(230, 230, 230));
        lblFrase.setBounds(460, 50, 1000, 70);

        panelOscuro2.add(lblFrase);

        panelOscuro = new FondoPanelSemi(new Color(0, 0, 0, 220));
        panelOscuro.setLayout(null);
        panelOscuro.setBounds(0, 820, 1920, 260);

        fondo.add(panelOscuro);

        lblDato = new JLabel("¿Sabías qué?... Los zorros pueden escuchar pequeños sonidos a más de 30 metros.", SwingConstants.CENTER);
        lblDato.setForeground(Color.WHITE);
        lblDato.setFont(new Font("Segoe UI", Font.PLAIN, 28));
        lblDato.setBounds(210, 50, 1500, 50);

        panelOscuro.add(lblDato);
    }

    public static void main(String[] args) {

        new PantallaCarga();
    }
}
