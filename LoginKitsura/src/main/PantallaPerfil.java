package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

public class PantallaPerfil extends JFrame {

    private FondoPanelSemi fondo;

    private JPanel panelCuenta;
    private JPanel panelPerfil;

    private JLabel lblLogo;
    private JLabel lblMascota;

    private JLabel lblTitulo;

    private JLabel lblCorreo;
    private JLabel lblPassword;
    private JLabel lblIdUsuario;
    private JLabel lblFechaCreacion;

    private JTextField txtCorreo;
    private JPasswordField txtPassword;

    private JLabel lblEditarCorreo;
    private JLabel lblEditarPassword;

    private JLabel lblEstado;
    private JLabel lblUsuario;

    private JLabel lblFotoPerfil;

    private JButton btnEditarNombre;
    private JButton btnVolver;

    private JScrollPane scrollImagenes;

    public PantallaPerfil() {

        fondo = new FondoPanelSemi("/Multimedia/utiles/fondoDosK.png");
        setContentPane(fondo);

        setTitle("Gestión de Cuenta");
        setSize(1920, 1060);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        fondo.setLayout(null);

        crearComponentes();

        setVisible(true);
    }

    private void crearComponentes() {

        lblLogo = new JLabel();

        ImageIcon logoIcon = new ImageIcon(getClass().getResource("/Multimedia/utiles/logoKitsura.png"));
        Image logoEscalado = logoIcon.getImage().getScaledInstance(300, 300, Image.SCALE_SMOOTH);

        lblLogo.setIcon(new ImageIcon(logoEscalado));
        lblLogo.setBounds(80, 20, 300, 300);

        fondo.add(lblLogo);

        lblMascota = new JLabel();

        ImageIcon mascotaIcon = new ImageIcon(getClass().getResource("/Multimedia/utiles/ZorroPerfil.png"));
        Image mascotaEscalada = mascotaIcon.getImage().getScaledInstance(450, 450, Image.SCALE_SMOOTH);

        lblMascota.setIcon(new ImageIcon(mascotaEscalada));
        lblMascota.setBounds(700, 30, 450, 450);

        fondo.add(lblMascota);

        panelCuenta = new FondoPanelSemi(new Color(220, 220, 220, 220));
        panelCuenta.setOpaque(false);
        panelCuenta.setLayout(null);
        panelCuenta.setBounds(80, 300, 950, 500);

        fondo.add(panelCuenta);

        lblTitulo = new JLabel("Gestión de Cuenta");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 40));
        lblTitulo.setBounds(280, 30, 400, 50);

        panelCuenta.add(lblTitulo);

        lblCorreo = new JLabel("Correo Electrónico");
        lblCorreo.setFont(new Font("Arial", Font.BOLD, 24));
        lblCorreo.setBounds(40, 120, 300, 40);

        panelCuenta.add(lblCorreo);

        txtCorreo = new JTextField("usuario@correo.com");
        txtCorreo.setFont(new Font("Arial", Font.PLAIN, 20));
        txtCorreo.setBounds(40, 170, 420, 50);

        panelCuenta.add(txtCorreo);

        lblEditarCorreo = new JLabel("Editar");
        lblEditarCorreo.setFont(new Font("Arial", Font.PLAIN, 22));
        lblEditarCorreo.setBounds(480, 170, 100, 50);

        panelCuenta.add(lblEditarCorreo);

        lblPassword = new JLabel("Contraseña");
        lblPassword.setFont(new Font("Arial", Font.BOLD, 24));
        lblPassword.setBounds(40, 270, 250, 40);

        panelCuenta.add(lblPassword);

        txtPassword = new JPasswordField("123456789");
        txtPassword.setFont(new Font("Arial", Font.PLAIN, 20));
        txtPassword.setBounds(40, 320, 420, 50);

        panelCuenta.add(txtPassword);

        lblEditarPassword = new JLabel("Editar");
        lblEditarPassword.setFont(new Font("Arial", Font.PLAIN, 22));
        lblEditarPassword.setBounds(480, 320, 100, 50);

        panelCuenta.add(lblEditarPassword);

        lblIdUsuario = new JLabel("<html><b>ID Usuario</b><br>19503236</html>");
        lblIdUsuario.setFont(new Font("Arial", Font.PLAIN, 22));
        lblIdUsuario.setBounds(700, 150, 220, 100);

        panelCuenta.add(lblIdUsuario);

        lblFechaCreacion = new JLabel("<html><b>Fecha de creación</b><br>00/00/2026</html>");
        lblFechaCreacion.setFont(new Font("Arial", Font.PLAIN, 22));
        lblFechaCreacion.setBounds(700, 290, 250, 100);

        panelCuenta.add(lblFechaCreacion);

        panelPerfil = new FondoPanelSemi(new Color(220, 220, 220, 220));
        panelPerfil.setOpaque(false);
        panelPerfil.setLayout(null);
        panelPerfil.setBounds(1180, 60, 650, 900);

        fondo.add(panelPerfil);

        lblEstado = new JLabel("<html><b>Estado:</b> Activo</html>");
        lblEstado.setFont(new Font("Arial", Font.PLAIN, 28));
        lblEstado.setBounds(210, 20, 300, 50);

        panelPerfil.add(lblEstado);

        lblFotoPerfil = new JLabel();

        ImageIcon fotoIcon = new ImageIcon(getClass().getResource("/Multimedia/utiles/Victoria.png"));
        Image fotoEscalada = fotoIcon.getImage().getScaledInstance(250, 250, Image.SCALE_SMOOTH);

        lblFotoPerfil.setIcon(new ImageIcon(fotoEscalada));
        lblFotoPerfil.setBounds(190, 90, 250, 250);
        lblFotoPerfil.setBorder(BorderFactory.createLineBorder(Color.GRAY, 4));

        panelPerfil.add(lblFotoPerfil);

        lblUsuario = new JLabel("Nombre de usuario");
        lblUsuario.setFont(new Font("Arial", Font.BOLD, 28));
        lblUsuario.setBounds(180, 370, 300, 40);

        panelPerfil.add(lblUsuario);

        btnEditarNombre = new JButton("EDITAR NOMBRE");
        btnEditarNombre.setFont(new Font("Arial", Font.BOLD, 20));
        btnEditarNombre.setBounds(180, 430, 280, 60);

        panelPerfil.add(btnEditarNombre);

        JLabel lblImagenActual = new JLabel("Imagen seleccionada actualmente");
        lblImagenActual.setFont(new Font("Arial", Font.BOLD, 18));
        lblImagenActual.setBounds(150, 530, 320, 30);

        panelPerfil.add(lblImagenActual);

        JPanel panelImagenes = new JPanel();
        panelImagenes.setPreferredSize(new java.awt.Dimension(450, 350));

        scrollImagenes = new JScrollPane(panelImagenes);
        scrollImagenes.setBounds(100, 580, 450, 250);

        panelPerfil.add(scrollImagenes);

        btnVolver = new JButton("VOLVER");
        btnVolver.setFont(new Font("Arial", Font.BOLD, 26));
        btnVolver.setBounds(450, 860, 220, 60);

        fondo.add(btnVolver);
    }

    public static void main(String[] args) {

        new PantallaPerfil();
    }
}
