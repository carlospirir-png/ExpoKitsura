package main.Usuario;

import java.awt.*;
import java.io.IOException;
import javax.swing.*;

public class FoxJump extends JFrame {

    private final JPanel fondo;

    private Font fuente1, fuente2;

    private JLabel vida1, vida2, vida3, titulo, tiempoTexto, tiempo, nivel, dificultad, categoria, mascota, florMascota, panelLago, nenufarFlor, nenufarVerdadero, nenufarFalso;

    private JButton btnAyuda;

    public FoxJump() {

        cargarFuentes();

        fondo = new JPanel(null);
        fondo.setBackground(new Color(178, 197, 178));

        setContentPane(fondo);

        setTitle("Hidden Fox");
        setSize(1880, 1080);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        crearComponentes();

        setVisible(true);
    }

    private void cargarFuentes() {

        try {

            fuente1 = Font.createFont(
                    Font.TRUETYPE_FONT,
                    getClass().getResourceAsStream(
                            "/fuentes/LettersForLearners.ttf"));

            fuente2 = Font.createFont(
                    Font.TRUETYPE_FONT,
                    getClass().getResourceAsStream(
                            "/fuentes/KGPerfectPenmanship.ttf"));

        } catch (FontFormatException | IOException e) {

            fuente1 = new Font("Arial", Font.BOLD, 20);
            fuente2 = new Font("Arial", Font.PLAIN, 20);
        }
    }

    private void crearComponentes() {

        //---------------- VIDAS ----------------
        try {

            ImageIcon icono = new ImageIcon(
                    getClass().getResource(
                            "/Multimedia/utiles/corazon.png"));

            Image imagen = icono.getImage()
                    .getScaledInstance(60, 60, Image.SCALE_SMOOTH);

            ImageIcon corazon = new ImageIcon(imagen);

            vida1 = new JLabel(corazon);
            vida2 = new JLabel(corazon);
            vida3 = new JLabel(corazon);

        } catch (Exception e) {

            vida1 = new JLabel("♥");
            vida2 = new JLabel("♥");
            vida3 = new JLabel("♥");

            vida1.setFont(new Font("Arial", Font.BOLD, 55));
            vida2.setFont(new Font("Arial", Font.BOLD, 55));
            vida3.setFont(new Font("Arial", Font.BOLD, 55));

            vida1.setForeground(Color.GRAY);
            vida2.setForeground(Color.GRAY);
            vida3.setForeground(Color.GRAY);
        }

        vida1.setBounds(80, 40, 60, 60);
        vida2.setBounds(150, 40, 60, 60);
        vida3.setBounds(220, 40, 60, 60);

        fondo.add(vida1);
        fondo.add(vida2);
        fondo.add(vida3);

        //---------------- AYUDA ----------------
        btnAyuda = new JButton("¿Necesitas ayuda?");
        btnAyuda.setBounds(60, 140, 270, 50);

        btnAyuda.setFocusPainted(false);
        btnAyuda.setFont(fuente2.deriveFont(18f));

        fondo.add(btnAyuda);

        //---------------- TITULO ----------------
        titulo = new JLabel("PREGUNTA", SwingConstants.CENTER);

        titulo.setBounds(600, 40, 700, 80);

        titulo.setFont(fuente1.deriveFont(Font.BOLD, 50f));

        fondo.add(titulo);

        //---------------- TIEMPO ----------------
        tiempoTexto = new JLabel("Tiempo restante:");

        tiempoTexto.setBounds(1450, 70, 300, 40);

        tiempoTexto.setFont(fuente2.deriveFont(28f));

        fondo.add(tiempoTexto);

        tiempo = new JLabel("00:00:00", SwingConstants.CENTER);

        tiempo.setBounds(1440, 120, 320, 60);

        tiempo.setOpaque(true);
        tiempo.setBackground(new Color(150, 150, 150));

        tiempo.setFont(fuente2.deriveFont(28f));

        fondo.add(tiempo);

        //---------------- PANEL LAGO ----------------
        panelLago = new JLabel();
        panelLago.setBounds(420, 250, 1050, 550);
        panelLago.setLayout(null);

        try {

            ImageIcon lagoIcon = new ImageIcon(
                    getClass().getResource(
                            "/Multimedia/utiles/FondoLago.png"));

            Image lagoEscalado = lagoIcon.getImage()
                    .getScaledInstance(1050, 550, Image.SCALE_SMOOTH);

            panelLago.setIcon(
                    new ImageIcon(lagoEscalado));

        } catch (Exception e) {

            panelLago.setOpaque(true);
            panelLago.setBackground(
                    new Color(126, 177, 190));
        }

        fondo.add(panelLago);

        //---------------- NENUFAR CON FLOR ----------------
        nenufarFlor = new JLabel();

        try {

            ImageIcon icono = new ImageIcon(
                    getClass().getResource(
                            "/Multimedia/utiles/nenufarFlor.png"));

            Image img = icono.getImage()
                    .getScaledInstance(95, 95, Image.SCALE_SMOOTH);

            nenufarFlor.setIcon(
                    new ImageIcon(img));

        } catch (Exception e) {

            nenufarFlor.setText("Flor");
        }

        nenufarFlor.setBounds(60, 60, 90, 90);

        panelLago.add(nenufarFlor);

        //---------------- NENUFAR VERDADERO ----------------
        nenufarVerdadero = new JLabel();
        nenufarVerdadero.setLayout(null);

        try {

            ImageIcon icono = new ImageIcon(
                    getClass().getResource(
                            "/Multimedia/utiles/Nenufar.png"));

            Image img = icono.getImage()
                    .getScaledInstance(240, 180, Image.SCALE_SMOOTH);

            nenufarVerdadero.setIcon(
                    new ImageIcon(img));

        } catch (Exception e) {

            nenufarVerdadero.setOpaque(true);
            nenufarVerdadero.setBackground(Color.GREEN);
        }

        JLabel lblVerdadero = new JLabel(
                "Verdadero",
                SwingConstants.CENTER);

        lblVerdadero.setFont(
                fuente1.deriveFont(Font.BOLD, 22f));

        lblVerdadero.setBounds(0, 60, 240, 90);

        nenufarVerdadero.add(lblVerdadero);

        nenufarVerdadero.setBounds(180, 170, 240, 180);

        panelLago.add(nenufarVerdadero);

        //---------------- NENUFAR FALSO ----------------
        nenufarFalso = new JLabel();
        nenufarFalso.setLayout(null);

        try {

            ImageIcon icono = new ImageIcon(
                    getClass().getResource(
                            "/Multimedia/utiles/Nenufar.png"));

            Image img = icono.getImage()
                    .getScaledInstance(240, 180, Image.SCALE_SMOOTH);

            nenufarFalso.setIcon(
                    new ImageIcon(img));

        } catch (Exception e) {

            nenufarFalso.setOpaque(true);
            nenufarFalso.setBackground(Color.GREEN);
        }

        JLabel lblFalso = new JLabel(
                "Falso",
                SwingConstants.CENTER);

        lblFalso.setFont(
                fuente1.deriveFont(Font.BOLD, 22f));

        lblFalso.setBounds(0, 60, 240, 90);

        nenufarFalso.add(lblFalso);

        nenufarFalso.setBounds(650, 170, 240, 180);

        panelLago.add(nenufarFalso);

        //---------------- NIVEL ----------------
        nivel = new JLabel("Nivel: ***");
        nivel.setBounds(80, 860, 250, 40);
        nivel.setFont(fuente2.deriveFont(25f));

        fondo.add(nivel);

        //---------------- DIFICULTAD ----------------
        dificultad = new JLabel("Dificultad: ***");
        dificultad.setBounds(80, 910, 300, 40);
        dificultad.setFont(fuente2.deriveFont(25f));

        fondo.add(dificultad);

        //---------------- CATEGORIA ----------------
        categoria = new JLabel("Categoría: ***");
        categoria.setBounds(80, 960, 300, 40);
        categoria.setFont(fuente2.deriveFont(25f));

        fondo.add(categoria);

        //---------------- MASCOTA ----------------
        mascota = new JLabel();

        try {

            ImageIcon icono = new ImageIcon(
                    getClass().getResource(
                            "/Multimedia/utiles/mascota10.png"));

            Image img = icono.getImage()
                    .getScaledInstance(420, 420, Image.SCALE_SMOOTH);

            mascota.setIcon(
                    new ImageIcon(img));

        } catch (Exception e) {

            mascota.setText("Mascota");
        }

        mascota.setBounds(1500, 470, 450, 450);
        mascota.setLayout(null);

        //---------------- FLOR MASCOTA ----------------
        florMascota = new JLabel();

        try {

            ImageIcon iconoFlor = new ImageIcon(
                    getClass().getResource(
                            "/Multimedia/utiles/florMascota.png"));

            Image imgFlor = iconoFlor.getImage()
                    .getScaledInstance(120, 120, Image.SCALE_SMOOTH);

            florMascota.setIcon(
                    new ImageIcon(imgFlor));

        } catch (Exception e) {

            florMascota.setText("Flor");
        }

        florMascota.setBounds(210, 40, 120, 120);

        mascota.add(florMascota);

        fondo.add(mascota);
    }
    
     
 
}
