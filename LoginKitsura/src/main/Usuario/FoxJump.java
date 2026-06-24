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

        setTitle("Fox Jump!");
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
                            "/Multimedia/utiles/ElementosGraficos/imagenes/corazon.png"));

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

            vida1.setFont(fuente1.deriveFont(55f));
            vida2.setFont(fuente1.deriveFont(55f));
            vida3.setFont(fuente1.deriveFont(55f));

            vida1.setForeground(Color.RED);
            vida2.setForeground(Color.RED);
            vida3.setForeground(Color.RED);
        }

        vida1.setBounds(70, 25, 60, 60);
        vida2.setBounds(135, 25, 60, 60);
        vida3.setBounds(200, 25, 60, 60);

        fondo.add(vida1);
        fondo.add(vida2);
        fondo.add(vida3);

        //---------------- AYUDA ----------------
        btnAyuda = new JButton("¿Necesitas ayuda?");
        btnAyuda.setBounds(60, 120, 280, 55);

        btnAyuda.setFocusPainted(false);
        btnAyuda.setFont(fuente2.deriveFont(18f));

        fondo.add(btnAyuda);

        //---------------- TITULO ----------------
        titulo = new JLabel("PREGUNTA", SwingConstants.CENTER);
        titulo.setBounds(500, 20, 900, 150);
        titulo.setFont(fuente1.deriveFont(Font.BOLD, 40f));
        titulo.setForeground(Color.BLACK);

        fondo.add(titulo);

        //---------------- TIEMPO ----------------
        tiempoTexto = new JLabel("Tiempo restante:");
        tiempoTexto.setBounds(1450, 70, 300, 40);
        tiempoTexto.setFont(fuente2.deriveFont(25f));
        tiempoTexto.setForeground(Color.BLACK);

        fondo.add(tiempoTexto);

        tiempo = new JLabel("00:00:00", SwingConstants.CENTER);
        tiempo.setBounds(1440, 120, 320, 60);
        tiempo.setOpaque(true);
        tiempo.setBackground(Color.WHITE);
        tiempo.setForeground(Color.BLACK);
        tiempo.setFont(fuente2.deriveFont(28f));

        fondo.add(tiempo);

        //---------------- PANEL LAGO ----------------
        panelLago = new JLabel();
        panelLago.setBounds(420, 250, 1050, 450);
        panelLago.setLayout(null);

        try {

            ImageIcon lagoIcon = new ImageIcon(
                    getClass().getResource(
                            "/Multimedia/utiles/fondo/interfaces/FondoLago.png"));

            Image lagoEscalado = lagoIcon.getImage()
                    .getScaledInstance(1050, 450, Image.SCALE_SMOOTH);

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
                            "/Multimedia/utiles/ElementosGraficos/imagenes/nenufarFlor.png"));

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
                            "/Multimedia/utiles/ElementosGraficos/imagenes/nenufar.png"));

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

        nenufarVerdadero.setBounds(180, 140, 240, 180);

        panelLago.add(nenufarVerdadero);

        //---------------- NENUFAR FALSO ----------------
        nenufarFalso = new JLabel();
        nenufarFalso.setLayout(null);

        try {

            ImageIcon icono = new ImageIcon(
                    getClass().getResource(
                            "/Multimedia/utiles/ElementosGraficos/imagenes/nenufar.png"));

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

        nenufarFalso.setBounds(650, 140, 240, 180);

        panelLago.add(nenufarFalso);

        //---------------- NIVEL ----------------
        nivel = new JLabel("Nivel: ***");
        nivel.setBounds(80, 740, 250, 40);
        nivel.setFont(fuente2.deriveFont(25f));
        nivel.setForeground(Color.BLACK);

        fondo.add(nivel);

        //---------------- DIFICULTAD ----------------
        dificultad = new JLabel("Dificultad: ***");
        dificultad.setBounds(80, 790, 250, 40);
        dificultad.setFont(fuente2.deriveFont(25f));
        dificultad.setForeground(Color.BLACK);

        fondo.add(dificultad);

        //---------------- CATEGORIA ----------------
        categoria = new JLabel("Categoría: ***");
        categoria.setBounds(80, 840, 250, 40);
        categoria.setFont(fuente2.deriveFont(25f));
        categoria.setForeground(Color.BLACK);

        fondo.add(categoria);

        //---------------- MASCOTA ----------------
        mascota = new JLabel();
        mascota.setBounds(1450, 480, 450, 450);
        mascota.setLayout(null);

        try {

            ImageIcon icono = new ImageIcon(
                    getClass().getResource(
                            "/Multimedia/utiles/mascotaKitsura/imagen/KitsuraFlotador.png"));

            Image img = icono.getImage()
                    .getScaledInstance(450, 450, Image.SCALE_SMOOTH);

            mascota.setIcon(
                    new ImageIcon(img));

        } catch (Exception e) {

            mascota.setText("Mascota");
        }

        //---------------- FLOR MASCOTA ----------------
        florMascota = new JLabel();

        try {

            ImageIcon iconoFlor = new ImageIcon(
                    getClass().getResource(
                            "/Multimedia/utiles/ElementosGraficos/imagenes/flor.png"));

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
    
    public static void main(String[] args) {
        new FoxJump();
    }
}