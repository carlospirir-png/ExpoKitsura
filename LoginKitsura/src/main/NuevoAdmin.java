package main;

import java.awt.*;
import javax.swing.*;

public class NuevoAdmin extends JFrame {

    private FondoPanelSemi fondo;
    private FondoPanelSemi panelSemi;

    private JLabel lblTitulo;

    private JLabel lblNombre;
    private JLabel lblCorreo;
    private JLabel lblContra;

    private JTextField txtNombre;
    private JTextField txtCorreo;
    private JPasswordField txtContra;

    private JButton btnAnadir;

    private JLabel lblMascota;

    public NuevoAdmin() {

        fondo = new FondoPanelSemi("/Multimedia/utiles/fondoDosK.png");
        setContentPane(fondo);

        setTitle("Añadir Administrador");
        setSize(1920, 1080);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        fondo.setLayout(null);

        crearComponentes();

        setVisible(true);
    }

    private void crearComponentes() {

        panelSemi = new FondoPanelSemi(new Color(0, 0, 0, 120));
        panelSemi.setLayout(null);
        panelSemi.setBounds(120, 100, 1000, 800);

        fondo.add(panelSemi);

        lblTitulo = new JLabel("AÑADIR ADMINISTRADOR", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 52));
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setBounds(50, 40, 900, 70);

        panelSemi.add(lblTitulo);

        lblNombre = new JLabel("Ingrese el nombre del nuevo administrador:");
        lblNombre.setFont(new Font("Arial", Font.PLAIN, 32));
        lblNombre.setForeground(Color.WHITE);
        lblNombre.setBounds(70, 170, 700, 45);

        panelSemi.add(lblNombre);

        txtNombre = new JTextField();
        txtNombre.setFont(new Font("Arial", Font.PLAIN, 28));
        txtNombre.setBounds(70, 235, 650, 65);

        panelSemi.add(txtNombre);

        lblCorreo = new JLabel("Ingrese el correo del nuevo administrador:");
        lblCorreo.setFont(new Font("Arial", Font.PLAIN, 32));
        lblCorreo.setForeground(Color.WHITE);
        lblCorreo.setBounds(70, 370, 750, 45);

        panelSemi.add(lblCorreo);

        txtCorreo = new JTextField();
        txtCorreo.setFont(new Font("Arial", Font.PLAIN, 28));
        txtCorreo.setBounds(70, 435, 650, 65);

        panelSemi.add(txtCorreo);

        lblContra = new JLabel("Ingrese la contraseña del nuevo administrador:");
        lblContra.setFont(new Font("Arial", Font.PLAIN, 32));
        lblContra.setForeground(Color.WHITE);
        lblContra.setBounds(70, 570, 800, 45);

        panelSemi.add(lblContra);

        txtContra = new JPasswordField();
        txtContra.setFont(new Font("Arial", Font.PLAIN, 28));
        txtContra.setBounds(70, 635, 650, 65);

        panelSemi.add(txtContra);

        btnAnadir = new JButton("AÑADIR");
        btnAnadir.setFont(new Font("Arial", Font.BOLD, 30));
        btnAnadir.setBounds(350, 730, 300, 70);

        panelSemi.add(btnAnadir);

        lblMascota = new JLabel();

        ImageIcon mascotaIcon =
                new ImageIcon(
                        getClass().getResource("/Multimedia/utiles/ZorroControl.png"));

        Image mascotaEscalada =
                mascotaIcon.getImage().getScaledInstance(
                        800,
                        800,
                        Image.SCALE_SMOOTH);

        lblMascota.setIcon(new ImageIcon(mascotaEscalada));
        lblMascota.setBounds(1080, 120, 800, 800);

        fondo.add(lblMascota);
    }

    public static void main(String[] args) {

        new NuevoAdmin();
    }
}