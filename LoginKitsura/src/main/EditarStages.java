package main;

import java.awt.*;
import javax.swing.*;

public class EditarStages extends JFrame {

    private FondoPanelSemi fondo;

    private JPanel panelSemi;

    private JLabel lblTitulo;

    private JLabel lblID;
    private JTextField txtID;

    private JLabel lblMascota;

    public EditarStages() {

        fondo = new FondoPanelSemi("/Multimedia/utiles/fondoDosK.png");
        setContentPane(fondo);

        setTitle("Editar Existente");
        setSize(1920, 1080);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        fondo.setLayout(null);

        crearComponentes();

        setVisible(true);
    }

    private void crearComponentes() {

        panelSemi = new JPanel() {

            @Override
            protected void paintComponent(Graphics g) {

                super.paintComponent(g);

                Graphics2D g2 = (Graphics2D) g.create();

                g2.setColor(new Color(0, 0, 0, 120));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 40, 40);

                g2.dispose();
            }
        };

        panelSemi.setOpaque(false);
        panelSemi.setLayout(null);
        panelSemi.setBounds(180, 180, 1000, 650);

        fondo.add(panelSemi);

        lblTitulo = new JLabel("EDITAR EXISTENTE", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 52));
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setBounds(0, 40, 1000, 60);

        panelSemi.add(lblTitulo);

        lblID = new JLabel("Ingrese el ID del Stage a editar:");
        lblID.setFont(new Font("Arial", Font.PLAIN, 34));
        lblID.setForeground(Color.WHITE);
        lblID.setBounds(220, 240, 600, 45);

        panelSemi.add(lblID);

        txtID = new JTextField();
        txtID.setFont(new Font("Arial", Font.PLAIN, 30));
        txtID.setBounds(200, 340, 600, 70);

        panelSemi.add(txtID);

        lblMascota = new JLabel();

        ImageIcon mascotaIcon =
                new ImageIcon(
                        getClass().getResource("/Multimedia/utiles/ZorroPrincipal.png"));

        Image mascotaEscalada =
                mascotaIcon.getImage().getScaledInstance(
                        750,
                        750,
                        Image.SCALE_SMOOTH);

        lblMascota.setIcon(new ImageIcon(mascotaEscalada));
        lblMascota.setBounds(1150, 140, 750, 750);

        fondo.add(lblMascota);
    }

    public static void main(String[] args) {

        new EditarStages();
    }
}