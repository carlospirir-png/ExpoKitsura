package main.Usuario;

import java.awt.*;
import javax.swing.*;
import main.Menu.*;

public class ResultadoFinal extends JDialog {

    private FondoPanel fondo;
    private Font fuente1;
    private Font fuente2;

    private JLabel lblPuntaje;
    private JLabel lblTiempo;

    private int puntajeFinal;
    private int tiempoFinal;

    private SeAcaboVidas seAcaboVidas;
    private JuegoBase juego;

    public ResultadoFinal(JuegoBase juego, int puntaje, int tiempoSegundos) {
        super(juego.getFrame(), true);

        this.juego = juego;
        this.puntajeFinal = puntaje;
        this.tiempoFinal = tiempoSegundos;

        fondo = new FondoPanel("/Multimedia/utiles/fondos/interfaces/fondoTresK.png");
        setContentPane(fondo);

        setUndecorated(true);
        setSize(700, 450);
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

        //---------------- PANEL SEMITRANSPARENTE ----------------
        FondoPanelSemi panelContenedor = new FondoPanelSemi(new Color(0, 0, 0, 150));
        panelContenedor.setLayout(null);
        panelContenedor.setBounds(40, 50, 430, 225);
        fondo.add(panelContenedor);

        //---------------- PUNTAJE ----------------
        lblPuntaje = new JLabel("Puntaje: 0");
        lblPuntaje.setFont(fuente2.deriveFont(25f));
        lblPuntaje.setForeground(Color.WHITE);
        lblPuntaje.setBounds(25, 60, 270, 30);
        panelContenedor.add(lblPuntaje);

        //---------------- TIEMPO ----------------
        int min = tiempoFinal / 60;
        int seg = tiempoFinal % 60;
        lblTiempo = new JLabel(String.format("Tiempo: %02d:%02d", min, seg));
        lblTiempo.setFont(fuente2.deriveFont(25f));
        lblTiempo.setForeground(Color.WHITE);
        lblTiempo.setBounds(25, 140, 270, 30);
        panelContenedor.add(lblTiempo);

        //---------------- BOTÓN JUGAR DE NUEVO ----------------
        JButton btnJugarDeNuevo = new DecoracionBotones(
                "Jugar de nuevo",
                DecoracionBotones.AZUL,
                DecoracionBotones.GRIS,
                DecoracionBotones.AMARILLO,
                DecoracionBotones.CELESTE,
                DecoracionBotones.AZUL,
                DecoracionBotones.AZUL);

        btnJugarDeNuevo.setFont(fuente2.deriveFont(12f));
        btnJugarDeNuevo.setBounds(75, 300, 180, 45);

        btnJugarDeNuevo.addActionListener(e -> {

            if (seAcaboVidas != null) {
                seAcaboVidas.dispose();
            }

            dispose();

            new MenuMinijuegos();
        });

        fondo.add(btnJugarDeNuevo);

        //---------------- BOTÓN MENÚ ----------------
        JButton btnMenu = new DecoracionBotones("Menú",
                                //ColorBase             ColorBorde              ColorLetra
                DecoracionBotones.ROSA, DecoracionBotones.ROJO, DecoracionBotones.AMARILLO, //MOUSE FUERA
                DecoracionBotones.ROJO, DecoracionBotones.ROSA, DecoracionBotones.ROSA); //MOUSE DENTRO  

        btnMenu.setFont(fuente2.deriveFont(15f));
        btnMenu.setBounds(275, 300, 160, 45);

        btnMenu.addActionListener(e -> {

            if (seAcaboVidas != null) {
                seAcaboVidas.dispose();
            }

            dispose();

            new MenuPrincipal();
        });

        fondo.add(btnMenu);

        //---------------- MASCOTA ----------------
        JLabel mascota = new JLabel();

        try {
            ImageIcon icon = new ImageIcon(
                    getClass().getResource("/Multimedia/utiles/mascotaKitsura/imagen/VENTANITA.png"));

            Image img = icon.getImage().getScaledInstance(350, 350, Image.SCALE_SMOOTH);
            mascota.setIcon(new ImageIcon(img));

        } catch (Exception e) {
            mascota.setText("Mascota");
        }

        mascota.setBounds(400, 215, 350, 350);


        fondo.add(mascota);

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


    // Setter para guardar la referencia de SeAcaboVidas
    public void setSeAcaboVidas(SeAcaboVidas seAcaboVidas) {
        this.seAcaboVidas = seAcaboVidas;
    }
}