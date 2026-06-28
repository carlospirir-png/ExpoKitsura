package main.Usuario;

import java.awt.*;
import java.awt.event.ActionListener;
import javax.swing.*;
import main.Menu.FondoPanel;
import main.Menu.DecoracionBotones;

public class SeAcaboTiempo extends JFrame {

    private FondoPanel fondo;
    private Font fuente1;
    private Font fuente2;


    private DecoracionBotones btnVolver; // Usada correctamente ahora
    private JuegoBase juego;

    public SeAcaboTiempo(JuegoBase juego, ActionListener accion) {

        this.juego = juego;

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
        fondo = new FondoPanel("/Multimedia/utiles/fondos/interfaces/fondoCuatroK.png");
        setContentPane(fondo);
        setTitle("Se acabo el tiempo");

        setSize(1980, 1060);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        fondo.setLayout(null);

        crearComponentes();
        //--------------- VOLVER --------------
btnVolver.addActionListener(e -> {
    dispose();
    accion.actionPerformed(e);
});

setVisible(true);
    }

    private void crearComponentes() {
        JPanel panelContenedor = new JPanel();
        panelContenedor.setLayout(null);
        panelContenedor.setBackground(new Color(0, 0, 0, 115)); 
        panelContenedor.setBounds(860, 60, 880, 820);
        fondo.add(panelContenedor);

        //---------------- TÍTULO PRINCIPAL ----------------
        JLabel lblGameOver = new JLabel("GAME OVER", JLabel.CENTER);
        lblGameOver.setFont(fuente2.deriveFont(34f));
        lblGameOver.setForeground(Color.WHITE);
        lblGameOver.setBounds(40, 40, 800, 60);
        panelContenedor.add(lblGameOver);

        //---------------- TITULO 2 ----------------
        JLabel lblSubtitulo = new JLabel("Se acabó el tiempo", JLabel.CENTER);
        lblSubtitulo.setFont(fuente2.deriveFont(28f));
        lblSubtitulo.setForeground(Color.WHITE);
        lblSubtitulo.setBounds(40, 110, 800, 45);
        panelContenedor.add(lblSubtitulo);

        //---------------- CITA LÍNEA 1  ----------------
        JLabel lblCita1 = new JLabel("<<El éxito no es definitivo; el fracaso no es fatal. Lo que realmente cuenta", JLabel.CENTER);
        lblCita1.setFont(fuente1.deriveFont(24f));
        lblCita1.setForeground(Color.WHITE);
        lblCita1.setBounds(40, 180, 800, 35); 
        panelContenedor.add(lblCita1);

        //---------------- CITA LÍNEA 2 ----------------
        JLabel lblCita2 = new JLabel("es tener valor para continuar>>. -Winston churchill", JLabel.CENTER);
        lblCita2.setFont(fuente1.deriveFont(24f));
        lblCita2.setForeground(Color.WHITE);
        lblCita2.setBounds(40, 220, 800, 35); 
        panelContenedor.add(lblCita2);

        //---------------- BOTON VOLVER ----------------
        btnVolver = new DecoracionBotones("VOLVER");
        btnVolver.setFont(fuente1.deriveFont(20f));
        btnVolver.setForeground(Color.BLACK);
        btnVolver.setBounds(340, 710, 200, 50);
        panelContenedor.add(btnVolver);

        //---------------- MASCOTA ----------------
        JLabel mascotaReloj = new JLabel();
        ImageIcon iconMascota = new ImageIcon(getClass().getResource("/Multimedia/utiles/mascotaKitsura/imagen/DERROTA-por-tiempo.png"));
        Image imgEscalada = iconMascota.getImage().getScaledInstance(600, 600, Image.SCALE_SMOOTH);
        mascotaReloj.setIcon(new ImageIcon(imgEscalada));
        mascotaReloj.setBounds(150, 250, 600, 600);
        fondo.add(mascotaReloj);
    }
}