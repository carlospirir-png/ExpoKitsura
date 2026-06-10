package main;

import java.awt.*;
import javax.swing.*;

public class UsuarioMenu extends JFrame {

    private FondoPanelSemi fondo;
    private JPanel panelSemi;
    private JLabel lblTitulo;
    private JButton btnAgregarAdmin;
    private JButton btnEditar;
    private JButton btnMostrar;
    private JLabel lblMascota;

    public UsuarioMenu() {

        fondo = new FondoPanelSemi("/Multimedia/utiles/fondoDosK.png");
        setContentPane(fondo);

        setTitle("Usuario");
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
        panelSemi.setBounds(120, 90, 1000, 850);

        fondo.add(panelSemi);

        lblTitulo = new JLabel("USUARIO", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 56));
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setBounds(250, 50, 500, 70);

        panelSemi.add(lblTitulo);

        btnAgregarAdmin = new JButton("AÑADIR ADMINISTRADOR");
        btnAgregarAdmin.setFont(new Font("Arial", Font.BOLD, 30));
        btnAgregarAdmin.setBounds(250, 250, 500, 85);

        panelSemi.add(btnAgregarAdmin);

        btnEditar = new JButton("EDITAR");
        btnEditar.setFont(new Font("Arial", Font.BOLD, 30));
        btnEditar.setBounds(250, 430, 320, 85);

        panelSemi.add(btnEditar);

        btnMostrar = new JButton("MOSTRAR");
        btnMostrar.setFont(new Font("Arial", Font.BOLD, 30));
        btnMostrar.setBounds(250, 610, 320, 85);

        panelSemi.add(btnMostrar);

        lblMascota = new JLabel();

        ImageIcon mascotaIcon = new ImageIcon(getClass().getResource("/Multimedia/utiles/ZorroTablet.png"));

        Image mascotaEscalada = mascotaIcon.getImage().getScaledInstance(850, 850, Image.SCALE_SMOOTH);

        lblMascota.setIcon(new ImageIcon(mascotaEscalada));
        lblMascota.setBounds(1050, 100, 850, 850);

        fondo.add(lblMascota);
    }

    public static void main(String[] args) {

        new UsuarioMenu();
    }
}
