//-------------- P A N T A L L A  D E  D I F I C U L T A D --------------
package main.Usuario;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;
import main.Menu.FondoPanelSemi;

public class PantallaDificultad extends JFrame {

    private FondoPanelSemi fondo;
    private Font fuente1, fuente2;
    private JPanel panelTexto;
    private JLabel lblTitulo;
    private JLabel lblMensaje;
    private JLabel lblEmpieza;
    private JLabel lblContador;
    private JLabel lblAlerta;
    private JLabel lblFlecha;

    private int nivel;
    private JFrame ventanaAnterior;
    private int tiempo = 3;

    private Timer timerContinuar;

    public PantallaDificultad(JFrame ventanaAnterior, int nivel, int vidas, int puntajeTotal, boolean par) {

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

        this.ventanaAnterior = ventanaAnterior;
        this.nivel = nivel;

        fondo = new FondoPanelSemi("/Multimedia/utiles/fondos/interfaces/fondoUnoK.png");
        fondo.setLayout(null);

        crearComponentes();
        iniciarContador();

        
        if (!(ventanaAnterior instanceof FoxJump) && !(ventanaAnterior instanceof HiddenFox_Codigo)) {
            setContentPane(fondo);
            setTitle("Pantalla de Dificultad");
            setSize(1880, 1080);
            setLocationRelativeTo(null);
            setDefaultCloseOperation(EXIT_ON_CLOSE);
            setExtendedState(JFrame.MAXIMIZED_BOTH);
            setVisible(true);
        }
        

    }

    /**
     * Expone el panel para que FoxJump lo ponga como contentPane.
     */
    public FondoPanelSemi getFondo() {
        return fondo;
    }

    private void crearComponentes() {

        JLabel mascota = new JLabel();

        ImageIcon mascotaIcon = new ImageIcon(
                getClass().getResource(
                        "/Multimedia/utiles/mascotaKitsura/imagen/CAMBIO_DE_DIFICULTAD.png"));

        Image mascotaEscalada = mascotaIcon.getImage().getScaledInstance(
                650, 650, Image.SCALE_SMOOTH);

        mascota.setIcon(new ImageIcon(mascotaEscalada));
        mascota.setBounds(135, 200, 650, 650);

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

        // FLECHA
        lblFlecha = new JLabel();

        ImageIcon flechaicon = new ImageIcon(
                getClass().getResource(
                        "/Multimedia/utiles/ElementosGraficos/imagenes/FlechaAvanzar.png"));

        Image flechaEscalada = flechaicon.getImage().getScaledInstance(
                300, 200, Image.SCALE_SMOOTH);

        lblFlecha.setIcon(new ImageIcon(flechaEscalada));
        lblFlecha.setBounds(320, 440, 300, 200);
        lblFlecha.setVisible(false);
        lblFlecha.setCursor(new Cursor(Cursor.HAND_CURSOR));

        lblFlecha.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                // ✅ CORRECCIÓN 1: cancelar el timer antes de continuar para evitar doble ejecución
                if (timerContinuar != null) {
                    timerContinuar.stop();
                }

                if (ventanaAnterior instanceof FoxJump foxJump) {
                    foxJump.continuarDespuesDeDificultad();
                } else if (ventanaAnterior instanceof HiddenFox_Codigo hiddenFox) {
                    hiddenFox.continuarDespuesDeDificultad(nivel);
                }
            }
        });

        panelTexto.add(lblFlecha);

        // ALERTA
        lblAlerta = new JLabel();

        ImageIcon alertaIcon = new ImageIcon(
                getClass().getResource(
                        "/Multimedia/utiles/ElementosGraficos/imagenes/alerta.png"));

        Image alertaEscalada = alertaIcon.getImage().getScaledInstance(
                200, 200, Image.SCALE_SMOOTH);

        lblAlerta.setIcon(new ImageIcon(alertaEscalada));
        lblAlerta.setBounds(350, 50, 200, 200);

        panelTexto.add(lblAlerta);

        // TITULO
        lblTitulo = new JLabel("¡ALERTA!", SwingConstants.CENTER);
        lblTitulo.setFont(fuente2.deriveFont(55f));
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setBounds(270, 330, 400, 60);

        panelTexto.add(lblTitulo);

// MENSAJE
        lblMensaje = new JLabel(
                "Se aumenta la dificultad",
                SwingConstants.CENTER);

        lblMensaje.setFont(fuente1.deriveFont(40f));
        lblMensaje.setForeground(Color.WHITE);
        lblMensaje.setBounds(205, 430, 550, 50);

        panelTexto.add(lblMensaje);

        // EMPIEZA EN
        lblEmpieza = new JLabel(
                "Empieza en:",
                SwingConstants.CENTER);

        lblEmpieza.setFont(fuente1.deriveFont(40f));
        lblEmpieza.setForeground(Color.WHITE);
        lblEmpieza.setBounds(270, 520, 400, 50);

        panelTexto.add(lblEmpieza);

        // CONTADOR
        lblContador = new JLabel("(3s)", SwingConstants.CENTER);
        lblContador.setFont(fuente2.deriveFont(60f));
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
        lblTitulo.setFont(fuente2.deriveFont(35f));
        lblTitulo.setBounds(5, 130, 900, 60);

        lblMensaje.setText("El nivel ha aumentado");
        lblTitulo.setFont(fuente1.deriveFont(25f));
        lblMensaje.setBounds(150, 250, 600, 60);

        lblEmpieza.setText("¡Continúa jugando!");
        lblEmpieza.setFont(fuente2.deriveFont(20f));
        lblEmpieza.setBounds(205, 350, 500, 60);

        lblContador.setText("");

        // ✅ CORRECCIÓN 1: guardar referencia al timer para cancelarlo si el jugador
        //    presiona la flecha antes de que se ejecute automáticamente
        timerContinuar = new Timer(1500, e -> {
            if (ventanaAnterior instanceof FoxJump foxJump) {
                foxJump.continuarDespuesDeDificultad();
            } else if (ventanaAnterior instanceof HiddenFox_Codigo hiddenFox) {
                hiddenFox.continuarDespuesDeDificultad(nivel);
            }
        });

        timerContinuar.setRepeats(false);
        timerContinuar.start();

        panelTexto.revalidate();
        panelTexto.repaint();
    }
}
