package main.Usuario;

import javax.swing.*;
import java.awt.*;
import main.Menu.FondoPanel;
import main.Menu.FondoPanelSemi;
import main.Menu.DecoracionBotones;

public class MenuMinijuegoC2 extends JFrame {

    private FondoPanel fondo;
    private Font fuente1;
    private Font fuente2;

    public MenuMinijuegoC2() {

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
        setTitle("Fox Jump!");
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
        JButton btnComoJugar = new DecoracionBotones("¿CÓMO JUGAR?");
        btnComoJugar.setFont(fuente2.deriveFont(25f));
        btnComoJugar.setBounds(100, 100, 280, 65);
        btnComoJugar.addActionListener(e -> {
            JOptionPane.showMessageDialog(this,
                    """
                    Fox Jump!

                    • Selecciona la categoría que deseas jugar.
                    • Lee la pregunta.
                    • Salta al nenúfar de Verdadero o Falso.
                    • Responde correctamente para avanzar.
                    • Después de 5 respuestas correctas subirás de dificultad.
                    • Si pierdes las 3 vidas termina la partida.
                    """);
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

        JLabel lblTitulo = new JLabel("FOX JUMP!", SwingConstants.CENTER);
        lblTitulo.setFont(fuente2.deriveFont(40f));
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setBounds(0, 0, 650, 70);
        panelTitulo.add(lblTitulo);

        //---------------- CATEGORÍA 1 ----------------
        JButton btnCategoria1 = new DecoracionBotones("ANIMALES");
        btnCategoria1.setFont(fuente2.deriveFont(25f));
        btnCategoria1.setBounds(1100, 300, 400, 65);
        btnCategoria1.addActionListener(e -> {
            new FoxJump("Animales");
            dispose();
        });
        fondo.add(btnCategoria1);

        //---------------- CATEGORÍA 2 ----------------
        JButton btnCategoria2 = new DecoracionBotones("PLANTAS");
        btnCategoria2.setFont(fuente2.deriveFont(25f));
        btnCategoria2.setBounds(1100, 430, 400, 65);
        btnCategoria2.addActionListener(e -> {
            new FoxJump("Plantas");
            dispose();
        });
        fondo.add(btnCategoria2);

        //---------------- CATEGORÍA 3 ----------------
        JButton btnCategoria3 = new DecoracionBotones("HÁBITATS");
        btnCategoria3.setFont(fuente2.deriveFont(25f));
        btnCategoria3.setBounds(1100, 560, 400, 65);
        btnCategoria3.addActionListener(e -> {
            new FoxJump("Hábitats");
            dispose();
        });
        fondo.add(btnCategoria3);

        //---------------- BOTÓN VOLVER ----------------
        JButton btnVolver = new DecoracionBotones("VOLVER");
        btnVolver.setFont(fuente2.deriveFont(28f));
        btnVolver.setBounds(1150, 720, 320, 65);
        btnVolver.addActionListener(e -> {
            new MenuMinijuegos();
            dispose();
        });
        fondo.add(btnVolver);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MenuMinijuegoC2::new);
    }
}