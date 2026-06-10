package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.Timer;

public class PantallaDificultad extends JFrame {

    private FondoPanelSemi fondo;

    private JPanel panelTexto;
    private JLabel lblTitulo;
    private JLabel lblMensaje;
    private JLabel lblEmpieza;
    private JLabel lblContador;
    private JLabel lblAlerta;
    private JLabel lblFlecha;

    private int tiempo = 3;

    public PantallaDificultad() {

        fondo = new FondoPanelSemi("/Multimedia/utiles/fondoUnoK.png");
        setContentPane(fondo);

        setTitle("Aumento de Dificultad");
        setSize(1920, 1080);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        fondo.setLayout(null);

        crearComponentes();
        iniciarContador();

        setVisible(true);
    }

    private void crearComponentes() {

        JLabel mascota = new JLabel();

        ImageIcon mascotaIcon
                = new ImageIcon(getClass().getResource("/Multimedia/utiles/ZorroAlerta.png"));

        Image mascotaEscalada
                = mascotaIcon.getImage().getScaledInstance(
                        650, 650, Image.SCALE_SMOOTH);

        mascota.setIcon(new ImageIcon(mascotaEscalada));
        mascota.setBounds(200, 200, 650, 650);

        fondo.add(mascota);

        panelTexto = new JPanel() {

            @Override
            protected void paintComponent(Graphics g) {

                Graphics2D g2 = (Graphics2D) g.create();

                g2.setColor(new Color(0, 0, 0, 170));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);

                g2.dispose();
            }
        };

        panelTexto.setOpaque(false);
        panelTexto.setLayout(null);
        panelTexto.setBounds(850, 150, 900, 750);

        fondo.add(panelTexto);

        lblFlecha = new JLabel();

        ImageIcon flechaicon
                = new ImageIcon(getClass().getResource("/Multimedia/utiles/FlechaAvanzar.png"));

        Image flechaEscalada
                = flechaicon.getImage().getScaledInstance(
                        300, 200, Image.SCALE_SMOOTH);

        lblFlecha.setIcon(new ImageIcon(flechaEscalada));
        lblFlecha.setBounds(320, 440, 300, 200);
        lblFlecha.setVisible(false);
        panelTexto.add(lblFlecha);

        lblAlerta = new JLabel();

        ImageIcon alertaIcon
                = new ImageIcon(getClass().getResource("/Multimedia/utiles/alerta.png"));

        Image alertaEscalada
                = alertaIcon.getImage().getScaledInstance(
                        200, 200, Image.SCALE_SMOOTH);

        lblAlerta.setIcon(new ImageIcon(alertaEscalada));
        lblAlerta.setBounds(350, 50, 200, 200);

        panelTexto.add(lblAlerta);

        lblTitulo = new JLabel("¡ALERTA!", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 60));
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setBounds(270, 330, 400, 60);

        panelTexto.add(lblTitulo);

        lblMensaje = new JLabel(
                "Se aumenta la dificultad",
                SwingConstants.CENTER);

        lblMensaje.setFont(new Font("Arial", Font.BOLD, 40));
        lblMensaje.setForeground(Color.WHITE);
        lblMensaje.setBounds(205, 430, 550, 50);

        panelTexto.add(lblMensaje);

        lblEmpieza = new JLabel(
                "Empieza en:",
                SwingConstants.CENTER);

        lblEmpieza.setFont(new Font("Arial", Font.BOLD, 40));
        lblEmpieza.setForeground(Color.WHITE);
        lblEmpieza.setBounds(270, 520, 400, 50);

        panelTexto.add(lblEmpieza);

        // CONTADOR
        lblContador = new JLabel("(3s)", SwingConstants.CENTER);
        lblContador.setFont(new Font("Arial", Font.BOLD, 60));
        lblContador.setForeground(Color.WHITE);
        lblContador.setBounds(320, 600, 300, 70);

        panelTexto.add(lblContador);
    }

    private void iniciarContador() {

        Timer timer = new Timer(1000, e -> {

            tiempo--;

            if (tiempo == 2) {
                lblContador.setText("(2s)");

            } else if (tiempo == 1) {
                lblContador.setText("(1s)");

            } else if (tiempo == 0) {

                lblContador.setText("(YA!)");

                ((Timer) e.getSource()).stop();

                mostrarNuevaDificultad();
            }
        });

        timer.start();
    }

    private void mostrarNuevaDificultad() {

        lblAlerta.setVisible(false);
        lblFlecha.setVisible(true);

        lblTitulo.setText("DIFICULTAD AUMENTADA");
        lblTitulo.setBounds(5, 130, 900, 60);

        lblMensaje.setText("El nivel ha aumentado");
        lblMensaje.setBounds(150, 250, 600, 60);

        lblEmpieza.setText("¡Continúa jugando!");
        lblEmpieza.setBounds(205, 350, 500, 60);

        lblContador.setText("");

        panelTexto.revalidate();
        panelTexto.repaint();
    }

    public static void main(String[] args) {
        new PantallaDificultad();
    }
}
