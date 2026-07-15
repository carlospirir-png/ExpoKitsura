package main.Usuario;

import java.awt.*;
import java.awt.event.ActionListener;
import java.net.URL;
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
        //------------- ÍCONO ------------------
        //se obtiene la imagen del logo con getResource
        URL iconUrl = getClass().getResource("/Multimedia/utiles/logotipo/logofK.png");

        //se instancia el ícono con la imagen
        ImageIcon icono = new ImageIcon(iconUrl);

        //Se coloca el ícono al JFrame
        setIconImage(icono.getImage());

        setSize(1980, 1060);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        fondo.setLayout(null);

        crearComponentes();
        //--------------- VOLVER --------------
        btnVolver.addActionListener(e -> {
            dispose();
            new MenuMinijuegos();
        });

        setVisible(true);
    }

    private void crearComponentes() {
        //---------------- PANEL SEMITRANSPARENTE ----------------
        JPanel panelContenedor = new JPanel();
        panelContenedor.setLayout(null);
        panelContenedor.setBackground(new Color(0, 0, 0, 115));
        panelContenedor.setBounds(930, 230, 650, 380);
        fondo.add(panelContenedor);

        //---------------- TÍTULO PRINCIPAL ----------------
        JLabel lblGameOver = new JLabel("GAME OVER", JLabel.CENTER);
        lblGameOver.setFont(fuente2.deriveFont(40f));
        lblGameOver.setForeground(Color.WHITE);
        lblGameOver.setBounds(25, 35, 600, 55);
        panelContenedor.add(lblGameOver);

        //---------------- SUBTÍTULO ----------------
        JLabel lblSubtitulo = new JLabel("Se acabó el tiempo", JLabel.CENTER);
        lblSubtitulo.setFont(fuente2.deriveFont(32f));
        lblSubtitulo.setForeground(Color.WHITE);
        lblSubtitulo.setBounds(25, 95, 600, 40);
        panelContenedor.add(lblSubtitulo);

        //---------------- CITA LÍNEA 1 ----------------
        JLabel lblCita1 = new JLabel(
                "<<El éxito no es definitivo; el fracaso no es fatal. Lo que realmente cuenta",
                JLabel.CENTER);
        lblCita1.setFont(fuente1.deriveFont(25f));
        lblCita1.setForeground(Color.WHITE);
        lblCita1.setBounds(25, 200, 600, 25);
        panelContenedor.add(lblCita1);

        //---------------- CITA LÍNEA 2 ----------------
        JLabel lblCita2 = new JLabel(
                "es tener valor para continuar>>. -Winston Churchill",
                JLabel.CENTER);
        lblCita2.setFont(fuente1.deriveFont(25f));
        lblCita2.setForeground(Color.WHITE);
        lblCita2.setBounds(25, 235, 600, 25);
        panelContenedor.add(lblCita2);

        //---------------- BOTÓN VOLVER ----------------
        btnVolver = new DecoracionBotones("VOLVER",
                DecoracionBotones.AZUL,
                DecoracionBotones.GRIS,
                DecoracionBotones.AMARILLO,
                DecoracionBotones.CELESTE,
                DecoracionBotones.AZUL,
                DecoracionBotones.AZUL);

        btnVolver.setFont(fuente2.deriveFont(20f));
        btnVolver.setBounds(1295, 645, 200, 50);
        fondo.add(btnVolver);
        
        //---------------- BOTÓN MENU ----------------
        btnVolver = new DecoracionBotones("MENÚ",
                DecoracionBotones.ROSA, DecoracionBotones.ROJO, DecoracionBotones.AMARILLO, //MOUSE FUERA
                DecoracionBotones.ROJO, DecoracionBotones.ROSA, DecoracionBotones.ROSA); //MOUSE DENTRO

        btnVolver.setFont(fuente2.deriveFont(20f));
        btnVolver.setBounds(1000, 645, 200, 50);
        fondo.add(btnVolver);

        //---------------- MASCOTA ----------------
        JLabel mascotaReloj = new JLabel();

        ImageIcon iconMascota = new ImageIcon(
                getClass().getResource("/Multimedia/utiles/mascotaKitsura/imagen/DERROTA-por-tiempo.png"));

        Image imgEscalada = iconMascota.getImage()
                .getScaledInstance(600, 600, Image.SCALE_SMOOTH);

        mascotaReloj.setIcon(new ImageIcon(imgEscalada));
        mascotaReloj.setBounds(165, 250, 600, 600);

        fondo.add(mascotaReloj);
    }
}
