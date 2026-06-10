package main;

import java.awt.*;
import javax.swing.*;

public class editarUsuario extends JFrame {

    private FondoPanelSemi fondo;

    private JPanel panelSemi;

    private JLabel lblTitulo;

    private JLabel lblID;
    private JLabel lblNombre;
    private JLabel lblCorreo;
    private JLabel lblContra;
    private JLabel lblImagen;

    private JTextField txtID;
    private JTextField txtNombre;
    private JTextField txtCorreo;
    private JPasswordField txtContra;

    private JLabel lblPreview;
    private JButton btnCargar;
    private JButton btnEditar;

    private JLabel lblMascota;

    public editarUsuario() {

        fondo = new FondoPanelSemi("/img/fondoDosK.png");
        setContentPane(fondo);

        setTitle("Editar Usuario");
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

                g2.setColor(new Color(0, 0, 0, 110));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 40, 40);

                g2.dispose();
            }
        };

        panelSemi.setOpaque(false);
        panelSemi.setLayout(null);
        panelSemi.setBounds(80, 80, 1150, 900);

        fondo.add(panelSemi);

        lblTitulo = new JLabel("EDITAR USUARIO", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 44));
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setBounds(300, 30, 550, 60);

        panelSemi.add(lblTitulo);

        lblID = new JLabel("Ingrese el ID del usuario:");
        lblID.setFont(new Font("Arial", Font.PLAIN, 28));
        lblID.setForeground(Color.WHITE);
        lblID.setBounds(50, 140, 420, 40);

        panelSemi.add(lblID);

        txtID = new JTextField();
        txtID.setFont(new Font("Arial", Font.PLAIN, 24));
        txtID.setBounds(50, 200, 500, 55);

        panelSemi.add(txtID);

        lblNombre = new JLabel("Ingrese el nombre nuevo:");
        lblNombre.setFont(new Font("Arial", Font.PLAIN, 28));
        lblNombre.setForeground(Color.WHITE);
        lblNombre.setBounds(50, 300, 420, 40);

        panelSemi.add(lblNombre);

        txtNombre = new JTextField();
        txtNombre.setFont(new Font("Arial", Font.PLAIN, 24));
        txtNombre.setBounds(50, 360, 500, 55);

        panelSemi.add(txtNombre);

        lblCorreo = new JLabel("Ingrese el correo nuevo:");
        lblCorreo.setFont(new Font("Arial", Font.PLAIN, 28));
        lblCorreo.setForeground(Color.WHITE);
        lblCorreo.setBounds(50, 470, 420, 40);

        panelSemi.add(lblCorreo);

        txtCorreo = new JTextField();
        txtCorreo.setFont(new Font("Arial", Font.PLAIN, 24));
        txtCorreo.setBounds(50, 530, 500, 55);

        panelSemi.add(txtCorreo);

        lblContra = new JLabel("Ingrese la contraseña nueva:");
        lblContra.setFont(new Font("Arial", Font.PLAIN, 28));
        lblContra.setForeground(Color.WHITE);
        lblContra.setBounds(50, 640, 450, 40);

        panelSemi.add(lblContra);

        txtContra = new JPasswordField();
        txtContra.setFont(new Font("Arial", Font.PLAIN, 24));
        txtContra.setBounds(50, 700, 500, 55);

        panelSemi.add(txtContra);

        btnEditar = new JButton("EDITAR");
        btnEditar.setFont(new Font("Arial", Font.BOLD, 26));
        btnEditar.setBounds(190, 790, 220, 65);

        panelSemi.add(btnEditar);

        lblImagen = new JLabel("Cargue la imagen de perfil:");
        lblImagen.setFont(new Font("Arial", Font.PLAIN, 28));
        lblImagen.setForeground(Color.WHITE);
        lblImagen.setBounds(660, 300, 400, 40);

        panelSemi.add(lblImagen);

        lblPreview = new JLabel();

        ImageIcon previewIcon =
                new ImageIcon(getClass().getResource("/img/Victoria.png"));

        Image previewEscalada =
                previewIcon.getImage().getScaledInstance(
                        320,
                        220,
                        Image.SCALE_SMOOTH);

        lblPreview.setIcon(new ImageIcon(previewEscalada));
        lblPreview.setBounds(660, 360, 320, 220);

        panelSemi.add(lblPreview);

        btnCargar = new JButton("CARGAR");
        btnCargar.setFont(new Font("Arial", Font.BOLD, 24));
        btnCargar.setBounds(720, 610, 200, 60);

        panelSemi.add(btnCargar);

        lblMascota = new JLabel();

        ImageIcon mascotaIcon =
                new ImageIcon(getClass().getResource("/img/ZorroControl.png"));

        Image mascotaEscalada =
                mascotaIcon.getImage().getScaledInstance(
                        650,
                        650,
                        Image.SCALE_SMOOTH);

        lblMascota.setIcon(new ImageIcon(mascotaEscalada));
        lblMascota.setBounds(1280, 230, 650, 650);

        fondo.add(lblMascota);
    }

    public static void main(String[] args) {

        new editarUsuario();
    }
}