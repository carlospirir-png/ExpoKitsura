package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

public class PantallaTutorial extends JFrame {

    private FondoPanelSemi fondo;

    private JScrollPane scrollPane;
    private JPanel panelContenido;

    private JLabel lblTitulo;
    private JLabel lblMascota;
    private JLabel lblPaso1;
    private JLabel lblTextoPaso1;
    private JLabel lblImagen1;
    private JLabel lblImagen2;
    private JLabel lblInfo;

    private JButton btnVolver;

    public PantallaTutorial() {

        fondo = new FondoPanelSemi("/Multimedia/utiles/fondoDosK.png");
        setContentPane(fondo);

        setTitle("Tutorial");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        fondo.setLayout(null);

        crearComponentes();

        setVisible(true);
    }

    private void crearComponentes() {

        panelContenido = new JPanel() {

            @Override
            protected void paintComponent(Graphics g) {

                Graphics2D g2 = (Graphics2D) g.create();

                g2.setColor(new Color(255, 255, 255, 180));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);

                g2.dispose();

                super.paintComponent(g);
            }
        };

        panelContenido.setOpaque(false);
        panelContenido.setLayout(null);
        panelContenido.setPreferredSize(new Dimension(540, 1000));

        lblTitulo = new JLabel("Reglas", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 20));
        lblTitulo.setBounds(200, 10, 150, 30);

        panelContenido.add(lblTitulo);

        lblMascota = new JLabel();

        ImageIcon mascotaIcon = new ImageIcon(getClass().getResource("/Multimedia/utiles/ZorroLeerV.png"));

        Image mascotaEscalada = mascotaIcon.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);

        lblMascota.setIcon(new ImageIcon(mascotaEscalada));
        lblMascota.setBounds(0, 70, 200, 200);

        panelContenido.add(lblMascota);

        lblPaso1 = new JLabel("Paso 1", SwingConstants.CENTER);
        lblPaso1.setFont(new Font("Arial", Font.BOLD, 18));
        lblPaso1.setBounds(170, 60, 120, 25);

        panelContenido.add(lblPaso1);

        lblTextoPaso1 = new JLabel("Texto explicativo");
        lblTextoPaso1.setFont(new Font("Arial", Font.PLAIN, 14));
        lblTextoPaso1.setBounds(200, 90, 180, 25);

        panelContenido.add(lblTextoPaso1);

        lblImagen1 = new JLabel();

        ImageIcon imagen1 = new ImageIcon(getClass().getResource("/Multimedia/utiles/ejemplo.png"));

        Image imgEscalada1 = imagen1.getImage().getScaledInstance(150, 120, Image.SCALE_SMOOTH);

        lblImagen1.setIcon(new ImageIcon(imgEscalada1));
        lblImagen1.setBounds(200, 140, 150, 120);

        panelContenido.add(lblImagen1);
        lblImagen2 = new JLabel();

        ImageIcon imagen2 = new ImageIcon(getClass().getResource("/Multimedia/utiles/ejemplo.png"));

        Image imgEscalada2 = imagen2.getImage().getScaledInstance(150, 120, Image.SCALE_SMOOTH);

        lblImagen2.setIcon(new ImageIcon(imgEscalada2));
        lblImagen2.setBounds(370, 140, 150, 120);

        panelContenido.add(lblImagen2);

        lblInfo = new JLabel(
                "<html><center>"
                + "Más información sobre el minijuego.<br>"
                + "Aquí puedes explicar reglas,<br>"
                + "objetivos y controles."
                + "</center></html>");

        lblInfo.setFont(new Font("Arial", Font.PLAIN, 14));
        lblInfo.setBounds(205, 280, 320, 80);
        lblInfo.setHorizontalAlignment(SwingConstants.CENTER);

        panelContenido.add(lblInfo);

        btnVolver = new JButton("Volver");
        btnVolver.setFont(new Font("Arial", Font.BOLD, 16));
        btnVolver.setBounds(50, 280, 120, 40);

        panelContenido.add(btnVolver);

        JLabel lblPaso2 = new JLabel("Paso 2", SwingConstants.CENTER);

        lblPaso2.setFont(new Font("Arial", Font.BOLD, 18));
        lblPaso2.setBounds(180, 380, 120, 25);

        panelContenido.add(lblPaso2);

        JLabel lblTextoPaso2 = new JLabel(
                "<html><center>"
                + "Explicación adicional del juego.<br>"
                + "Puedes agregar imágenes y texto."
                + "</center></html>");

        lblTextoPaso2.setFont(new Font("Arial", Font.PLAIN, 14));
        lblTextoPaso2.setBounds(210, 410, 380, 60);
        lblTextoPaso2.setHorizontalAlignment(SwingConstants.CENTER);

        panelContenido.add(lblTextoPaso2);

        JLabel lblPaso3 = new JLabel("Paso 3", SwingConstants.CENTER);

        lblPaso3.setFont(new Font("Arial", Font.BOLD, 18));
        lblPaso3.setBounds(190, 750, 120, 25);

        panelContenido.add(lblPaso3);

        JLabel lblTextoPaso3 = new JLabel(
                "<html><div style='text-align:center;'>"
                + "Última sección del tutorial.<br>"
                + "Coloca aquí cualquier regla adicional."
                + "</div></html>");

        lblTextoPaso3.setFont(new Font("Arial", Font.PLAIN, 14));
        lblTextoPaso3.setBounds(180, 790, 380, 60);
        lblTextoPaso3.setHorizontalAlignment(SwingConstants.CENTER);

        panelContenido.add(lblTextoPaso3);

        scrollPane = new JScrollPane(panelContenido);

        scrollPane.setBounds(10, 10, 565, 340);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBorder(null);

        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        fondo.add(scrollPane);
    }

    public static void main(String[] args) {

        new PantallaTutorial();
    }
}
