package main.Usuario;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import main.Menu.*;

public class SeAcaboVidas extends JFrame {

    private FondoPanel fondo;
    private Font fuente1;
    private Font fuente2;
    private JuegoBase juego;

    public SeAcaboVidas(JuegoBase juego, ActionListener accion) {
        this.juego = juego;

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

        fondo = new FondoPanel("/Multimedia/utiles/fondos/interfaces/fondoCuatroK.png");
        setContentPane(fondo);
        setTitle("Se acabaron las vidas");
        setSize(1920, 1060);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        fondo.setLayout(null);

        crearComponentes();
        setVisible(true);
    }

    private void crearComponentes() {

        //---------------- MASCOTA ----------------
        JLabel mascota = new JLabel();
        try {
            ImageIcon icon = new ImageIcon(
                    getClass().getResource("/Multimedia/utiles/mascotaKitsura/imagen/DERROTA-por-vidas.png"));
            Image img = icon.getImage().getScaledInstance(600, 600, Image.SCALE_SMOOTH);
            mascota.setIcon(new ImageIcon(img));
        } catch (Exception e) {
            mascota.setText("~");
        }
        mascota.setBounds(100, 250, 600, 600);
        fondo.add(mascota);

//---------------- TEXTOS CENTRO DERECHA ----------------
        JLabel lblGameOver = new JLabel("GAME OVER", JLabel.CENTER);
        lblGameOver.setFont(fuente2.deriveFont(80f));
        lblGameOver.setForeground(Color.WHITE);
        lblGameOver.setBounds(800, 300, 900, 90);
        fondo.add(lblGameOver);

        JLabel lblSubtitulo = new JLabel("Se te acabaron las vidas", JLabel.CENTER);
        lblSubtitulo.setFont(fuente2.deriveFont(48f));
        lblSubtitulo.setForeground(Color.WHITE);
        lblSubtitulo.setBounds(800, 400, 900, 60);
        fondo.add(lblSubtitulo);

        JLabel lblCita = new JLabel("\"El conocimiento que no se rinde,", JLabel.CENTER);
        lblCita.setFont(fuente1.deriveFont(32f));
        lblCita.setForeground(Color.WHITE);
        lblCita.setBounds(800, 500, 900, 40);
        fondo.add(lblCita);

        JLabel lblCita2 = new JLabel("siempre encuentra el camino.\"", JLabel.CENTER);
        lblCita2.setFont(fuente1.deriveFont(32f));
        lblCita2.setForeground(Color.WHITE);
        lblCita2.setBounds(800, 545, 900, 40);
        fondo.add(lblCita2);

        //---------------- PANEL SEMITRANSPARENTE ----------------
        FondoPanelSemi panelTextos = new FondoPanelSemi(new Color(0, 0, 0, 150));
        panelTextos.setLayout(null);
        panelTextos.setBounds(800, 270, 900, 360);
        fondo.add(panelTextos);

        //---------------- BOTONES ----------------
        JButton btnContinuar = new DecoracionBotones("CONTINUAR",
                                //ColorBase             ColorBorde              ColorLetra
                DecoracionBotones.AZUL, DecoracionBotones.GRIS, DecoracionBotones.AMARILLO, //MOUSE FUERA
                DecoracionBotones.CELESTE, DecoracionBotones.AZUL, DecoracionBotones.AZUL); //MOUSE DENTRO   

        btnContinuar.setFont(fuente2.deriveFont(28f));
        btnContinuar.setBounds(980, 740, 280, 65);
        btnContinuar.addActionListener(e -> {
            dispose();
            ResultadoFinal resultado = new ResultadoFinal(
                    juego,
                    juego.getPuntajeTotal(),
                    juego.getTiempoTotalJugado()
            );
            resultado.mostrar();
        });
        fondo.add(btnContinuar);


        JButton btnMenu = new DecoracionBotones("MENÚ",
                //ColorBase             ColorBorde              ColorLetra
                DecoracionBotones.ROSA, DecoracionBotones.ROJO, DecoracionBotones.AMARILLO, //MOUSE FUERA
                DecoracionBotones.ROJO, DecoracionBotones.ROSA, DecoracionBotones.ROSA); //MOUSE DENTRO  

        btnMenu.setFont(fuente2.deriveFont(28f));
        btnMenu.setBounds(1300, 740, 280, 65);
        btnMenu.addActionListener(e -> {
            dispose();
            juego.irAlMenu();
        });
        fondo.add(btnMenu);
    }
}
