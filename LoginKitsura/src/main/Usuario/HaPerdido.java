package main.Usuario;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import main.Menu.FondoPanel;

public class HaPerdido extends JFrame {

    private FondoPanel fondo;
    private Font fuente1;
    private Font fuente2;


    private JuegoBase juego; // 🔥 GENÉRICO

    public HaPerdido(JuegoBase juego,ActionListener accion) {

        this.juego = juego;

        try{
            // LettersForLearners
            fuente1 = Font.createFont(
            Font.TRUETYPE_FONT,
            getClass().getResourceAsStream("/fuentes/LettersForLearners.ttf"));
            // KGPerfectPenmanship
            fuente2 = Font.createFont(
            Font.TRUETYPE_FONT,
            getClass().getResourceAsStream("/fuentes/KGPerfectPenmanship.ttf"));
            
        } catch (Exception e){
            e.printStackTrace();            
        fuente1 = new Font("Arial", Font.PLAIN,20);
        fuente2 = new Font("Arial", Font.PLAIN,20);

        }

        fondo = new FondoPanel("/Multimedia/utiles/fondos/interfaces/fondoCuatroK.png");
        setContentPane(fondo);

        setTitle("Ha perdido");
        setSize(1980, 1060);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        fondo.setLayout(null);

        crearComponentes();
    }

    private void crearComponentes() {

        JLabel lblGameOver = new JLabel("GAME OVER", JLabel.CENTER);
        lblGameOver.setFont(fuente2.deriveFont(34f));
        lblGameOver.setForeground(Color.WHITE);
        lblGameOver.setBounds(900, 100, 800, 60);
        fondo.add(lblGameOver);

        JLabel lblSubtitulo = new JLabel("Se te acabaron las vidas", JLabel.CENTER);
        lblSubtitulo.setFont(fuente2.deriveFont(28f));
        lblSubtitulo.setForeground(Color.WHITE);
        lblSubtitulo.setBounds(900, 170, 800, 45);
        fondo.add(lblSubtitulo);

        JLabel lblCita = new JLabel(
                "<<Aprender del error es avanzar>>",
                JLabel.CENTER);
        lblCita.setFont(fuente1.deriveFont(24f));
        lblCita.setForeground(Color.WHITE);
        lblCita.setBounds(900, 240, 800, 30);
        fondo.add(lblCita);

        JLabel mascota = new JLabel();
        ImageIcon icon = new ImageIcon(
                getClass().getResource("/Multimedia/utiles/mascotaKitsura/imagen/DERROTA-por-vidas.png"));

        Image img = icon.getImage().getScaledInstance(600, 600, Image.SCALE_SMOOTH);
        mascota.setIcon(new ImageIcon(img));
        mascota.setBounds(150, 250, 600, 600);
        fondo.add(mascota);

        //---------------- BOTÓN CONTINUAR ----------------
        JButton btnContinuar = new JButton("Continuar");
        btnContinuar.setFont(fuente1.deriveFont(25f));
        btnContinuar.setBounds(1225, 770, 200, 50);

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

        //---------------- BOTÓN MENÚ ----------------
        JButton btnMenu = new JButton("Menú");
        btnMenu.setFont(fuente1.deriveFont(25f));
        btnMenu.setBounds(1225, 830, 200, 50);

        btnMenu.addActionListener(e -> {
            dispose();

            juego.irAlMenu(); // 🔥 genérico
        });

        fondo.add(btnMenu);

    }
}
