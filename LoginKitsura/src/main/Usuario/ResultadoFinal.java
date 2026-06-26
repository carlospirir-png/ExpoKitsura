package main.Usuario;

import java.awt.*;
import javax.swing.*;
import main.Menu.FondoPanel;
import main.Menu.FondoPanelSemi;

public class ResultadoFinal extends JDialog {

    private FondoPanel fondo;
    private Font fuente1;
    private Font fuente2;
    private FondoPanelSemi panelSemi;

    private JLabel lblPuntaje;
    private JLabel lblTiempo;

    private int puntajeFinal;
    private int tiempoFinal;

    private JuegoBase juego; // 🔥 ahora es genérico

    public ResultadoFinal(JuegoBase juego, int puntaje, int tiempoSegundos) {
        super(juego.getFrame(), true);

        this.juego = juego;
        this.puntajeFinal = puntaje;
        this.tiempoFinal = tiempoSegundos;

        fondo = new FondoPanel("/Multimedia/utiles/fondos/interfaces/fondoTresK.png");
        setContentPane(fondo);

        setUndecorated(true);
        setSize(600, 400);
        setLocationRelativeTo(juego.getFrame());
        fondo.setLayout(null);

        crearComponentes();
    }

    public void mostrar() {
        setVisible(true);
    }

    private void crearComponentes() {

        try {
            fuente1 = Font.createFont(Font.TRUETYPE_FONT,
                    getClass().getResourceAsStream("/fuentes/LettersForLearners.ttf"));

            fuente2 = Font.createFont(Font.TRUETYPE_FONT,
                    getClass().getResourceAsStream("/fuentes/KGPerfectPenmanship.ttf"));

        } catch (Exception e) {
            fuente1 = new Font("Arial", Font.PLAIN, 20);
            fuente2 = new Font("Arial", Font.PLAIN, 20);
        }

        //---------------- BOTÓN CERRAR ----------------
        JButton btnCerrar = new JButton("X");
        btnCerrar.setFont(new Font("Arial", Font.BOLD, 9));
        btnCerrar.setBounds(550, 10, 40, 30);
        btnCerrar.addActionListener(e -> dispose());
        fondo.add(btnCerrar);

        //---------------- PUNTAJE ----------------
        lblPuntaje = new JLabel("Puntaje: 0");
        lblPuntaje.setFont(fuente2.deriveFont(25f));
        lblPuntaje.setForeground(Color.WHITE);
        lblPuntaje.setBounds(60, 140, 300, 30);
        fondo.add(lblPuntaje);

        //---------------- TIEMPO ----------------
        int min = tiempoFinal / 60;
        int seg = tiempoFinal % 60;

        lblTiempo = new JLabel(String.format("Tiempo: %02d:%02d", min, seg));
        lblTiempo.setFont(fuente2.deriveFont(25f));
        lblTiempo.setForeground(Color.WHITE);
        lblTiempo.setBounds(60, 200, 300, 30);
        fondo.add(lblTiempo);

        //---------------- MASCOTA ----------------
        JLabel mascota = new JLabel();
        try {
            ImageIcon icon = new ImageIcon(
                    getClass().getResource("/Multimedia/utiles/mascotaKitsura/imagen/VENTANITA.png"));

            Image img = icon.getImage().getScaledInstance(300, 300, Image.SCALE_SMOOTH);
            mascota.setIcon(new ImageIcon(img));

        } catch (Exception e) {
            mascota.setText("Mascota");
        }

        mascota.setBounds(370, 210, 300, 300);
        fondo.add(mascota);

        //---------------- PANEL SEMI ----------------
        panelSemi = new FondoPanelSemi(new Color(0, 0, 0, 150));
        panelSemi.setLayout(null);
        panelSemi.setBounds(30, 110, 550, 150);
        fondo.add(panelSemi);

        //---------------- BOTÓN REINTENTAR ----------------
        JButton btnJugarDeNuevo = new JButton("Jugar de nuevo");
        btnJugarDeNuevo.setFont(fuente1.deriveFont(25f));
        btnJugarDeNuevo.setBounds(60, 310, 200, 45);

        btnJugarDeNuevo.addActionListener(e -> {
            dispose();
            MenuMinijuegos menuM = new MenuMinijuegos();
        });

        fondo.add(btnJugarDeNuevo);

        //---------------- BOTÓN MENÚ ----------------
        JButton btnMenu = new JButton("Menú");
        btnMenu.setFont(fuente1.deriveFont(25f));
        btnMenu.setBounds(340, 310, 160, 45);

        btnMenu.addActionListener(e -> {
            dispose();
            MenuPrincipal mp = new MenuPrincipal();
        });

        fondo.add(btnMenu);

        animarPuntaje();
    }

    private void animarPuntaje() {

        int duracionMs = 1500;
        int pasos = 60;
        int intervalo = duracionMs / pasos;

        int[] contador = {0};

        Timer timer = new Timer(intervalo, null);

        timer.addActionListener(e -> {
            contador[0]++;

            float progreso = (float) contador[0] / pasos;
            float eased = 1 - (1 - progreso) * (1 - progreso);

            int valor = (int) (puntajeFinal * eased);

            lblPuntaje.setText("Puntaje: " + valor);

            if (contador[0] >= pasos) {
                lblPuntaje.setText("Puntaje: " + puntajeFinal);
                ((Timer) e.getSource()).stop();
            }
        });

        timer.start();
    }
}
