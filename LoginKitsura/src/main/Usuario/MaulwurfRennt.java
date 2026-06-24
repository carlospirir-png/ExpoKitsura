package main.Usuario;

import java.awt.*;
import javax.swing.*;

public class MaulwurfRennt extends JFrame {

    private final JPanel fondo;
    private Font fuente1, fuente2;
    private ImageIcon corazonIcon, mascotaIcon;

    private JLabel vida1, vida2, vida3, titulo,
            tiempoTexto, tiempo,
            nivel, dificultad, categoria,
            mascota,
            tablero,
            topoCasco, topoLentes,
            cartelCasco, cartelLentes;

    private JButton btnAyuda;
 
    public MaulwurfRennt() {
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

        //---------------- FONDO ----------------
        fondo = new JPanel();
        fondo.setLayout(null);
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

    private void crearComponentes() {

        //---------------- VIDAS ----------------
        try {
            corazonIcon = new ImageIcon(
                    getClass().getResource("/Multimedia/utiles/ElementosGraficos/imagenes/corazon.png"));

            Image corazonEscalado = corazonIcon.getImage().getScaledInstance(
                    60, 60, Image.SCALE_SMOOTH);

            ImageIcon corazonFinal = new ImageIcon(corazonEscalado);

            vida1 = new JLabel(corazonFinal);
            vida2 = new JLabel(corazonFinal);
            vida3 = new JLabel(corazonFinal);

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
        btnAyuda.setFont(fuente2.deriveFont(18f));
        btnAyuda.setBounds(60, 120, 280, 55);

        fondo.add(btnAyuda);

        //---------------- TITULO ----------------
        titulo = new JLabel("PREGUNTA", SwingConstants.CENTER);
        titulo.setFont(fuente1.deriveFont(Font.BOLD, 40f));
        titulo.setForeground(Color.BLACK);
        titulo.setBounds(500, 20, 900, 150);

        fondo.add(titulo);

        //---------------- TIEMPO ----------------
        tiempoTexto = new JLabel("Tiempo restante:");
        tiempoTexto.setFont(fuente2.deriveFont(25f));
        tiempoTexto.setForeground(Color.BLACK);
        tiempoTexto.setBounds(1450, 70, 300, 40);

        fondo.add(tiempoTexto);

        tiempo = new JLabel("00:00:00", SwingConstants.CENTER);
        tiempo.setOpaque(true);
        tiempo.setBackground(Color.WHITE);
        tiempo.setForeground(Color.BLACK);
        tiempo.setFont(fuente2.deriveFont(28f));
        tiempo.setBounds(1440, 120, 320, 60);

        fondo.add(tiempo);

        //---------------- TABLERO ----------------
        tablero = new JLabel();
        try {
            ImageIcon tableroIcon = new ImageIcon(
                    getClass().getResource("/Multimedia/Minijuegos/Minijuego_3/Fondo.png"));

            // Se aumentó la altura a 500 para mejorar la perspectiva de las filas
            Image tableroEscalado = tableroIcon.getImage().getScaledInstance(
                    1050, 500, Image.SCALE_SMOOTH);

            tablero.setIcon(new ImageIcon(tableroEscalado));

        } catch (Exception e) {
            tablero.setOpaque(true);
            tablero.setBackground(new Color(180, 120, 60));
        }

        tablero.setLayout(null);
        tablero.setBounds(420, 230, 1050, 500); 
        fondo.add(tablero);

        //---------------- TOPO CASCO (SUPERIOR IZQUIERDO) ----------------
        topoCasco = new JLabel();
        try {
            ImageIcon iconoCasco = new ImageIcon(
                    getClass().getResource("/Multimedia/Minijuegos/Minijuego_3/Topo_casco_tierra.png"));

            Image imgCasco = iconoCasco.getImage().getScaledInstance(
                    200, 280, Image.SCALE_SMOOTH);

            topoCasco.setIcon(new ImageIcon(imgCasco));

        } catch (Exception e) {
            topoCasco.setText("TOPO");
        }
        topoCasco.setBounds(178, 0, 200, 280);

        //---------------- CARTEL CASCO ----------------
        cartelCasco = new JLabel("Respuesta", SwingConstants.CENTER);
        cartelCasco.setOpaque(true);
        cartelCasco.setBackground(new Color(150, 150, 150));
        cartelCasco.setFont(fuente2.deriveFont(30f));
        cartelCasco.setBounds(192, 160, 170, 90);
        
        tablero.add(cartelCasco);
        tablero.add(topoCasco);


        //---------------- TOPO LENTES  ----------------
        topoLentes = new JLabel();
        try {
            ImageIcon iconoLentes = new ImageIcon(
                    getClass().getResource("/Multimedia/Minijuegos/Minijuego_3/Topo_Lentes.png"));

            Image imgLentes = iconoLentes.getImage().getScaledInstance(
                    200, 275, Image.SCALE_SMOOTH);

            topoLentes.setIcon(new ImageIcon(imgLentes));

        } catch (Exception e) {
            topoLentes.setText("TOPO");
        }
        topoLentes.setBounds(728, 140, 200, 275);

        //---------------- CARTEL LENTES ----------------
        cartelLentes = new JLabel("Respuesta", SwingConstants.CENTER);
        cartelLentes.setOpaque(true);
        cartelLentes.setBackground(new Color(150, 150, 150));
        cartelLentes.setFont(fuente2.deriveFont(30f));
        cartelLentes.setBounds(745, 300, 170, 90);

        tablero.add(cartelLentes);
        tablero.add(topoLentes);


        //---------------- NIVEL ----------------
        nivel = new JLabel("Nivel: ***");
        nivel.setFont(fuente2.deriveFont(25f));
        nivel.setForeground(Color.BLACK);
        nivel.setBounds(80, 750, 250, 40);

        fondo.add(nivel);

        //---------------- DIFICULTAD ----------------
        dificultad = new JLabel("Dificultad: ***");
        dificultad.setFont(fuente2.deriveFont(25f));
        dificultad.setForeground(Color.BLACK);
        dificultad.setBounds(80, 800, 250, 40);

        fondo.add(dificultad);

        //---------------- CATEGORIA ----------------
        categoria = new JLabel("Categoría: ***");
        categoria.setFont(fuente2.deriveFont(25f));
        categoria.setForeground(Color.BLACK);
        categoria.setBounds(80, 850, 250, 40);

        fondo.add(categoria);

        //---------------- MASCOTA ----------------
        mascota = new JLabel();
        try {
            mascotaIcon = new ImageIcon(
                    getClass().getResource("/Multimedia/utiles/mascotaKitsura/imagen/zorroMartillo.png"));

            Image mascotaEscalada = mascotaIcon.getImage().getScaledInstance(
                    450, 450, Image.SCALE_SMOOTH);

            mascota.setIcon(new ImageIcon(mascotaEscalada));

        } catch (Exception e) {
            mascota.setText("Mascota");
        }

        mascota.setBounds(1450, 480, 450, 450);
        fondo.add(mascota);
    }
    
    public static void main(String[] args) {
        new MaulwurfRennt();
    }
}