package main.Usuario;

import java.awt.*;
import java.awt.event.*;
import java.net.URL;
import javax.sound.sampled.*;
import javax.swing.*;
import main.Menu.FondoPanel;
import main.Menu.DecoracionBotones;

public class PistasAudio extends JFrame {

    private FondoPanel fondo;
    private Font fuente1;
    private Font fuente2;
    private DecoracionBotones btnSalir, btnRepetir;
    private Clip clip;
    private String rutaAudio;

    public PistasAudio(String rutaAudio) {

        this.rutaAudio = rutaAudio;
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

        reproducirAudio();

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                detenerAudio();
            }
        });

        setVisible(true);

    }

    private void crearComponentes() {

        //---------------- RECUADRO AUDIO ----------------
        JPanel recuadroAudio = new JPanel();
        recuadroAudio.setLayout(null);
        recuadroAudio.setBackground(Color.WHITE);
        recuadroAudio.setBounds(40, 100, 340, 100);
        fondo.add(recuadroAudio);

        JLabel lblTiempoInicio = new JLabel("1:46");
        lblTiempoInicio.setFont(fuente2.deriveFont(16f));
        lblTiempoInicio.setBounds(20, 40, 40, 25);
        recuadroAudio.add(lblTiempoInicio);

        JPanel barraProgreso = new JPanel();
        barraProgreso.setBackground(Color.LIGHT_GRAY);
        barraProgreso.setBounds(65, 48, 200, 10);
        recuadroAudio.add(barraProgreso);

        JLabel lblTiempoFin = new JLabel("3:32");
        lblTiempoFin.setFont(fuente2.deriveFont(16f));
        lblTiempoFin.setBounds(275, 40, 40, 25);
        recuadroAudio.add(lblTiempoFin);

        //---------------- BOTON SALIR ----------------
        JButton btnSalir = new DecoracionBotones("SALIR",
                //ColorBase             ColorBorde              ColorLetra
                DecoracionBotones.AZUL, DecoracionBotones.GRIS, DecoracionBotones.AMARILLO, //MOUSE FUERA
                DecoracionBotones.CELESTE, DecoracionBotones.AZUL, DecoracionBotones.AZUL); //MOUSE DENTRO   

        btnSalir.setFont(fuente2.deriveFont(20f));
        btnSalir.setBounds(40, 290, 150, 45);
        btnSalir.addActionListener(e -> {
            detenerAudio();
            dispose();
        });
        fondo.add(btnSalir);

        //---------------- BOTON REPETIR ----------------
        btnRepetir = new DecoracionBotones("REPETIR",
                //ColorBase             ColorBorde              ColorLetra
                DecoracionBotones.ROSA, DecoracionBotones.ROJO, DecoracionBotones.AMARILLO, //MOUSE FUERA
                DecoracionBotones.ROJO, DecoracionBotones.ROSA, DecoracionBotones.ROSA); //MOUSE DENTRO
        
        btnRepetir.setFont(fuente2.deriveFont(20f));
        btnRepetir.setBounds(230, 290, 150, 45);
        btnRepetir.addActionListener(e -> reproducirAudio());
        
        fondo.add(btnRepetir);

        //---------------- MASCOTA ----------------
        JLabel mascotaAudifonos = new JLabel();
        ImageIcon iconMascota = new ImageIcon(getClass().getResource("/Multimedia/utiles/mascotaKitsura/imagen/PISTA_AUDIO.png"));
        Image imgEscalada = iconMascota.getImage().getScaledInstance(300, 300, Image.SCALE_SMOOTH);
        mascotaAudifonos.setIcon(new ImageIcon(imgEscalada));
        mascotaAudifonos.setBounds(390, 65, 300, 300);

        fondo.add(mascotaAudifonos);
    }

    public void detenerAudio() {
        if (clip != null) {
            clip.stop();
            clip.close();
            clip = null;
        }
    }

    public void reproducirAudio() {

        try {

            if (clip != null) {
                clip.stop();
                clip.close();
            }
            System.out.println("Ruta en BD: " + rutaAudio);
            System.out.println(getClass().getResource(rutaAudio));

            URL url = getClass().getResource(rutaAudio);

            System.out.println("URL encontrada: " + url);

            AudioInputStream audio = AudioSystem.getAudioInputStream(url);

            clip = AudioSystem.getClip();
            clip.open(audio);
            clip.start();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
