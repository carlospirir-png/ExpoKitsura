package main.Usuario;

import java.awt.*;
import javax.swing.*;
import main.Menu.FondoPanel;
import main.Menu.DecoracionBotones;

public class PistasAudio extends JFrame {

    private FondoPanel fondo;
    private Font fuente1;
    private Font fuente2;
    private DecoracionBotones btnSalir, btnRepetir;
    
    public PistasAudio() {
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
        fondo = new FondoPanel("/Multimedia/utiles/fondos/interfaces/fondoTresK.png");
        setContentPane(fondo);
        
        setTitle("Pistas de Audio");
        setSize(700, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        fondo.setLayout(null);
        crearComponentes();
        setVisible(true);
    }

    private void crearComponentes() {

        //---------------- RECUADRO AUDIO ----------------
        JPanel recuadroAudio = new JPanel();
        recuadroAudio.setLayout(null);
        recuadroAudio.setBackground(Color.WHITE);
        recuadroAudio.setBounds(40, 100, 340, 120);
        fondo.add(recuadroAudio);

        JLabel lblTiempoInicio = new JLabel("1:46");
        lblTiempoInicio.setFont(fuente2.deriveFont(16f));
        lblTiempoInicio.setBounds(20, 35, 40, 25);
        recuadroAudio.add(lblTiempoInicio);

        JPanel barraProgreso = new JPanel();
        barraProgreso.setBackground(Color.LIGHT_GRAY);
        barraProgreso.setBounds(65, 43, 200, 10);
        recuadroAudio.add(barraProgreso);

        JLabel lblTiempoFin = new JLabel("3:32");
        lblTiempoFin.setFont(fuente2.deriveFont(16f));
        lblTiempoFin.setBounds(275, 35, 40, 25);
        recuadroAudio.add(lblTiempoFin);

        JLabel lblIdAudio = new JLabel("M1-D1-N1", JLabel.CENTER);
        lblIdAudio.setFont(fuente2.deriveFont(22f));
        lblIdAudio.setBounds(20, 75, 300, 30);
        recuadroAudio.add(lblIdAudio);

        //---------------- BOTON SALIR ----------------
        JButton btnSalir = new DecoracionBotones("SALIR");
        btnSalir.setFont(fuente2.deriveFont(20f));
        btnSalir.setBounds(40, 290, 150, 45);
        btnSalir.addActionListener(e -> dispose());
        fondo.add(btnSalir);

        //---------------- BOTON REPETIR ----------------
        btnRepetir = new DecoracionBotones("REPETIR", "#FC767D", "#da4d58", "#da4d58");
        btnRepetir.setFont(fuente2.deriveFont(20f));
        btnRepetir.setBounds(230, 290, 150, 45);
        fondo.add(btnRepetir);

        //---------------- MASCOTA ----------------
        JLabel mascotaAudifonos = new JLabel();
        ImageIcon iconMascota = new ImageIcon(getClass().getResource("/Multimedia/utiles/mascotaKitsura/imagen/PISTA_AUDIO.png"));
        Image imgEscalada = iconMascota.getImage().getScaledInstance(300, 300, Image.SCALE_SMOOTH);
        mascotaAudifonos.setIcon(new ImageIcon(imgEscalada));
        mascotaAudifonos.setBounds(390, 65, 300, 300);

        fondo.add(mascotaAudifonos);
    }

    public static void main(String[] args) {
        new PistasAudio();
    }
}