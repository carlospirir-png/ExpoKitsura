package main.Usuario;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;
import main.Menu.FondoPanelSemi;
import main.Menu.DecoracionBotones;

public class PantallaPerfil extends JFrame {

    private FondoPanelSemi fondo;

    private JPanel panelCuenta;
    private JPanel panelPerfil;

    private JLabel lblLogo;
    private JLabel lblMascota;

    private JLabel lblTitulo;

    private JLabel lblCorreo;
    private JLabel lblPassword;

    public JTextField txtCorreo;
    public JPasswordField txtPassword;

    private JLabel lblEditarPassword;
    private JTextField txtUsuario;
    private JLabel lblFotoPerfil;

    private DecoracionBotones btnEditarNombre;

    private Font fuente1, fuente2;
    private JScrollPane scrollImagenes;

    private boolean editandoNombre = false;
    private boolean editandoPassword = false;

    public PantallaPerfil() {
        try {
            fuente1 = Font.createFont(
                    Font.TRUETYPE_FONT,
                    getClass().getResourceAsStream("/fuentes/LettersForLearners.ttf"));
            fuente2 = Font.createFont(
                    Font.TRUETYPE_FONT,
                    getClass().getResourceAsStream("/fuentes/KGPerfectPenmanship.ttf"));
        } catch (Exception e) {
            e.printStackTrace();
            fuente1 = new Font("Arial", Font.PLAIN, 20);
            fuente2 = new Font("Arial", Font.PLAIN, 20);
        }

        fondo = new FondoPanelSemi("/Multimedia/utiles/fondos/interfaces/fondoDosK.png");
        setContentPane(fondo);

        setTitle("Gestión de Cuenta");
        setSize(1920, 1060);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        fondo.setLayout(null);

        crearComponentes();

        setVisible(true);
    }

    private void crearComponentes() {

        //---------------- LOGO ----------------
        lblLogo = new JLabel();
        ImageIcon logoIcon = new ImageIcon(getClass().getResource("/Multimedia/utiles/logotipo/logoKitsura.png"));
        Image logoEscalado = logoIcon.getImage().getScaledInstance(300, 300, Image.SCALE_SMOOTH);
        lblLogo.setIcon(new ImageIcon(logoEscalado));
        lblLogo.setBounds(80, 20, 300, 300);
        fondo.add(lblLogo);

        //---------------- MASCOTA ----------------
        lblMascota = new JLabel();
        ImageIcon mascotaIcon = new ImageIcon(getClass().getResource("/Multimedia/utiles/mascotaKitsura/imagen/VENTANITA.png"));
        Image mascotaEscalada = mascotaIcon.getImage().getScaledInstance(450, 450, Image.SCALE_SMOOTH);
        lblMascota.setIcon(new ImageIcon(mascotaEscalada));
        lblMascota.setBounds(700, 30, 450, 450);
        fondo.add(lblMascota);

        //---------------- PANEL CUENTA ----------------
        panelCuenta = new FondoPanelSemi(new Color(220, 220, 220, 220));
        panelCuenta.setOpaque(false);
        panelCuenta.setLayout(null);
        panelCuenta.setBounds(80, 300, 950, 500);
        fondo.add(panelCuenta);

        lblTitulo = new JLabel("Gestión de Cuenta");
        lblTitulo.setFont(fuente2.deriveFont(40f));
        lblTitulo.setForeground(Color.decode("#447A9C"));
        lblTitulo.setBounds(280, 30, 400, 50);
        panelCuenta.add(lblTitulo);

        //---------------- CORREO ----------------
        lblCorreo = new JLabel("Correo Electrónico");
        lblCorreo.setFont(fuente2.deriveFont(24f));
        lblCorreo.setForeground(Color.decode("#FC767D"));
        lblCorreo.setBounds(40, 120, 300, 40);
        panelCuenta.add(lblCorreo);

        txtCorreo = new JTextField("usuario@correo.com");
        txtCorreo.setFont(fuente1.deriveFont(28f));
        txtCorreo.setBounds(40, 170, 420, 50);
        txtCorreo.setEditable(false);
        panelCuenta.add(txtCorreo);

        //---------------- CONTRASEÑA ----------------
        lblPassword = new JLabel("Contraseña");
        lblPassword.setFont(fuente2.deriveFont(24f));
        lblPassword.setForeground(Color.decode("#FC767D"));
        lblPassword.setBounds(40, 270, 250, 40);
        panelCuenta.add(lblPassword);

        txtPassword = new JPasswordField("123456789");
        txtPassword.setFont(fuente1.deriveFont(30f));
        txtPassword.setBounds(40, 320, 420, 50);
        txtPassword.setEditable(false);
        panelCuenta.add(txtPassword);

        //---------------- EDITAR CONTRASEÑA ----------------
        lblEditarPassword = new JLabel("<html><u>Editar</u></html>");
        lblEditarPassword.setFont(fuente1.deriveFont(22f));
        lblEditarPassword.setForeground(Color.BLACK);
        lblEditarPassword.setCursor(new Cursor(Cursor.HAND_CURSOR));
        lblEditarPassword.setBounds(480, 320, 120, 50);
        lblEditarPassword.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                lblEditarPassword.setForeground(Color.decode("#447A9C"));
            }
            @Override
            public void mouseExited(MouseEvent e) {
                lblEditarPassword.setForeground(Color.BLACK);
            }
            @Override
            public void mouseClicked(MouseEvent e) {
                if (!editandoPassword) {
                    txtPassword.setEditable(true);
                    txtPassword.requestFocus();
                    lblEditarPassword.setText("<html><u>Confirmar</u></html>");
                    editandoPassword = true;
                } else {
                    txtPassword.setEditable(false);
                    lblEditarPassword.setText("<html><u>Editar</u></html>");
                    editandoPassword = false;
                }
            }
        });
        panelCuenta.add(lblEditarPassword);

        //---------------- ID USUARIO ----------------
        JLabel lblIdTitulo = new JLabel("ID Usuario");
        lblIdTitulo.setFont(fuente2.deriveFont(Font.BOLD, 23f));
        lblIdTitulo.setBounds(700, 150, 220, 35);
        panelCuenta.add(lblIdTitulo);

        JLabel lblIdValor = new JLabel("19503236");
        lblIdValor.setFont(fuente1.deriveFont(36f));
        lblIdValor.setBounds(700, 185, 220, 35);
        panelCuenta.add(lblIdValor);

        //---------------- FECHA CREACIÓN ----------------
        JLabel lblFechaTitulo = new JLabel("Fecha de creación");
        lblFechaTitulo.setFont(fuente2.deriveFont(Font.BOLD, 23f));
        lblFechaTitulo.setBounds(700, 290, 250, 35);
        panelCuenta.add(lblFechaTitulo);

        JLabel lblFechaValor = new JLabel("00/00/2026");
        lblFechaValor.setFont(fuente1.deriveFont(36f));
        lblFechaValor.setBounds(700, 325, 250, 35);
        panelCuenta.add(lblFechaValor);

        //---------------- PANEL PERFIL ----------------
        panelPerfil = new FondoPanelSemi(new Color(220, 220, 220, 220));
        panelPerfil.setOpaque(false);
        panelPerfil.setLayout(null);
        panelPerfil.setBounds(1180, 60, 650, 900);
        fondo.add(panelPerfil);

        //---------------- ESTADO ----------------
        JLabel lblEstadoTitulo = new JLabel("Estado:");
        lblEstadoTitulo.setFont(fuente2.deriveFont(Font.BOLD, 28f));
        lblEstadoTitulo.setBounds(210, 20, 120, 50);
        panelPerfil.add(lblEstadoTitulo);

        JLabel lblEstadoValor = new JLabel("Activo");
        lblEstadoValor.setFont(fuente1.deriveFont(28f));
        lblEstadoValor.setBounds(335, 20, 150, 50);
        panelPerfil.add(lblEstadoValor);

        //---------------- FOTO PERFIL ----------------
        lblFotoPerfil = new JLabel();
        ImageIcon fotoIcon = new ImageIcon(getClass().getResource("/Multimedia/utiles/mascotaKitsura/imagen/VICTORIA-Imperfecta.png"));
        Image fotoEscalada = fotoIcon.getImage().getScaledInstance(250, 250, Image.SCALE_SMOOTH);
        lblFotoPerfil.setIcon(new ImageIcon(fotoEscalada));
        lblFotoPerfil.setBounds(190, 90, 250, 250);
        lblFotoPerfil.setBorder(BorderFactory.createLineBorder(Color.GRAY, 4));
        panelPerfil.add(lblFotoPerfil);

        //---------------- NOMBRE USUARIO ----------------
        txtUsuario = new JTextField("Nombre de usuario");
        txtUsuario.setFont(fuente1.deriveFont(26f));
        txtUsuario.setBounds(180, 370, 300, 45);
        txtUsuario.setEditable(false);
        panelPerfil.add(txtUsuario);

        //---------------- BOTÓN EDITAR/CONFIRMAR NOMBRE ----------------
        btnEditarNombre = new DecoracionBotones("EDITAR NOMBRE", "#FC767D", "#da4d58", "#da4d58");
        btnEditarNombre.setFont(fuente2.deriveFont(20f));
        btnEditarNombre.setBounds(190, 430, 280, 60);
        btnEditarNombre.addActionListener(e -> {
            if (!editandoNombre) {
                txtUsuario.setEditable(true);
                txtUsuario.requestFocus();
                btnEditarNombre.setText("CONFIRMAR");
                editandoNombre = true;
            } else {
                txtUsuario.setEditable(false);
                btnEditarNombre.setText("EDITAR NOMBRE");
                editandoNombre = false;
            }
        });
        panelPerfil.add(btnEditarNombre);

        //---------------- IMAGEN SELECCIONADA ----------------
        JLabel lblImagenActual = new JLabel("Imagen seleccionada actualmente");
        lblImagenActual.setFont(fuente2.deriveFont(16f));
        lblImagenActual.setBounds(205, 530, 320, 30);
        panelPerfil.add(lblImagenActual);

        JPanel panelImagenes = new JPanel();
        panelImagenes.setPreferredSize(new java.awt.Dimension(450, 350));
        scrollImagenes = new JScrollPane(panelImagenes);
        scrollImagenes.setBounds(100, 580, 450, 250);
        panelPerfil.add(scrollImagenes);

        //---------------- BOTÓN VOLVER ----------------
        JButton btnVolver = new DecoracionBotones("VOLVER");
        btnVolver.setFont(fuente2.deriveFont(26f));
        btnVolver.setBounds(450, 860, 220, 60);
        btnVolver.addActionListener(e -> {
            new MenuPrincipal();
            dispose();
        });
        fondo.add(btnVolver);
    }
}