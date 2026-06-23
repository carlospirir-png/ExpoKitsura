// REGISTRO USUARIOOOOOOOO
package main.Usuario;

import java.awt.*;
import javax.swing.*;
import java.sql.*;
import main.Menu.FondoPanel;
import main.Menu.SalirDelJuego;
import main.conexion.Conexion;

public class RegistroUsuario extends JFrame {

    private FondoPanel fondo;
    private Font fuente1, fuente2;

    private JTextField txtNombre, txtCorreo;
    private JPasswordField txtPassword;

    private JButton btnJugar, btnSalir;

    private JLabel logo, mascota;
    private JLabel lblNombre, lblCorreo, lblPassword, lblInvitado;

    public RegistroUsuario() {

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

        fondo = new FondoPanel("/Multimedia/utiles/fondos/interfaces/fondoUnoK.png");

        setContentPane(fondo);
        setTitle("Registro");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);

        fondo.setLayout(null);

        crearComponentes();

        setVisible(true);
    }

    private void crearComponentes() {

        //---------------- LOGO ----------------
        logo = new JLabel();

        ImageIcon logoIcon
                = new ImageIcon(getClass().getResource("/Multimedia/utiles/logotipo/logofK.png"));

        Image logoEscalado
                = logoIcon.getImage().getScaledInstance(
                        150, 150, Image.SCALE_SMOOTH);

        logo.setIcon(new ImageIcon(logoEscalado));
        logo.setBounds(900, 40, 150, 150);

        fondo.add(logo);

        //---------------- MASCOTA ----------------
        mascota = new JLabel();

        ImageIcon mascotaIcon
                = new ImageIcon(getClass().getResource("/Multimedia/utiles/mascotaKitsura/imagen/REGISTRARSE_USUARIO-PARTIDA_MINIJUEGO-TABLETA.png"));

        Image mascotaEscalada
                = mascotaIcon.getImage().getScaledInstance(
                        191, 264, Image.SCALE_SMOOTH);

        mascota.setIcon(new ImageIcon(mascotaEscalada));
        mascota.setBounds(1250, 550, 191, 264);

        fondo.add(mascota);

        //---------------- LABEL NOMBRE ----------------
        lblNombre = new JLabel("Nombre");
        lblNombre.setFont(fuente2.deriveFont(25f));
        lblNombre.setForeground(Color.WHITE);
        lblNombre.setBounds(760, 380, 200, 30);

        fondo.add(lblNombre);

        txtNombre = new JTextField();
        txtNombre.setFont(fuente1.deriveFont(22f));
        txtNombre.setBounds(760, 415, 400, 50);

        fondo.add(txtNombre);

        //---------------- LABEL CORREO ----------------
        lblCorreo = new JLabel("Correo");
        lblCorreo.setFont(fuente2.deriveFont(25f));
        lblCorreo.setForeground(Color.WHITE);
        lblCorreo.setBounds(760, 480, 200, 30);

        fondo.add(lblCorreo);

        txtCorreo = new JTextField();
        txtCorreo.setFont(fuente1.deriveFont(22f));
        txtCorreo.setBounds(760, 515, 400, 50);

        fondo.add(txtCorreo);

        //---------------- LABEL PASSWORD ----------------
        lblPassword = new JLabel("Password");
        lblPassword.setFont(fuente2.deriveFont(25f));
        lblPassword.setForeground(Color.WHITE);
        lblPassword.setBounds(760, 580, 200, 30);

        fondo.add(lblPassword);

        txtPassword = new JPasswordField();
        txtPassword.setFont(fuente1.deriveFont(22f));
        txtPassword.setBounds(760, 615, 400, 50);

        fondo.add(txtPassword);

        //---------------- BOTON JUGAR ----------------
        btnJugar = new JButton("JUGAR");
        btnJugar.setFont(fuente2.deriveFont(15f));
        btnJugar.setBounds(820, 815, 285, 60);

        btnJugar.addActionListener(e -> {
            if (registrarUsuario()) {
                new MenuPrincipal();
                dispose();
            }
        });

        fondo.add(btnJugar);

        //---------------- BOTON SALIR ----------------
        btnSalir = new JButton("SALIR");
        btnSalir.setFont(fuente2.deriveFont(15f));
        btnSalir.setBounds(1750, 950, 120, 40);

        btnSalir.addActionListener(e -> {
            new SalirDelJuego();
            dispose();
        });
 

        fondo.add(btnSalir);

        //---------------- LABEL INVITADO ----------------
        lblInvitado = new JLabel("<html><u>Invitado</u></html>");

        lblInvitado.setFont(fuente2.deriveFont(20f));
        lblInvitado.setForeground(Color.WHITE);

        lblInvitado.setCursor(
                new Cursor(Cursor.HAND_CURSOR));
        lblInvitado.setBounds(
                1700, 40, 120, 30);

        lblInvitado.addMouseListener(
                new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e
            ) {
                new RegistroInvitado();
                dispose();
            }
        });
        fondo.add(lblInvitado);

        //---------------- LABEL INICIAR SESION ----------------
        JLabel lblIniciarSesion
                = new JLabel("<html><u>Iniciar Sesión</u></html>");

        lblIniciarSesion.setFont(fuente2.deriveFont(18f));
        lblIniciarSesion.setForeground(Color.WHITE);
        lblIniciarSesion.setCursor(new Cursor(Cursor.HAND_CURSOR));
        lblIniciarSesion.setBounds(40, 985, 200, 30);

        lblIniciarSesion.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                new IniciarSesion();
                dispose();
            }
        });

        fondo.add(lblIniciarSesion);
    }

    private boolean registrarUsuario() {

        try {

            String nombre = txtNombre.getText().trim();
            String correo = txtCorreo.getText().trim();
            String password = String.valueOf(txtPassword.getPassword());

            // VALIDACIONES
            if (nombre.isEmpty()) {
                throw new IllegalArgumentException(
                        "Debe ingresar un nombre.");
            }

            if (correo.isEmpty()) {
                throw new IllegalArgumentException(
                        "Debe ingresar un correo.");
            }

            if (!correo.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
                throw new IllegalArgumentException(
                        "Ingrese un correo válido.");
            }

            if (password.isEmpty()) {
                throw new IllegalArgumentException(
                        "Debe ingresar una contraseña.");
            }

            if (password.length() < 6) {
                throw new IllegalArgumentException(
                        "La contraseña debe tener mínimo 6 caracteres.");
            }

            // CONEXIÓN A LA BASE DE DATOS
            Connection con = new Conexion().getConnection();

            if (con == null) {
                throw new Exception(
                        "No fue posible conectar con la base de datos.");
            }

            // VERIFICAR SI EL CORREO YA EXISTE
            String verificar
                    = "SELECT correo FROM Usuario WHERE correo = ?";

            PreparedStatement psVerificar
                    = con.prepareStatement(verificar);

            psVerificar.setString(1, correo);

            ResultSet rs = psVerificar.executeQuery();

            if (rs.next()) {

                rs.close();
                psVerificar.close();
                con.close();

                throw new IllegalArgumentException(
                        "Ya existe una cuenta con ese correo.");
            }

            rs.close();
            psVerificar.close();

            // INSERTAR USUARIO
            String sql
                    = "INSERT INTO Usuario(nombre_usuario, correo, contrasena) "
                    + "VALUES (?, ?, ?)";

            PreparedStatement ps
                    = con.prepareStatement(sql);

            ps.setString(1, nombre);
            ps.setString(2, correo);
            ps.setString(3, password);

            int filas = ps.executeUpdate();

            ps.close();
            con.close();

            if (filas > 0) {

                JOptionPane.showMessageDialog(
                        this,
                        "Usuario registrado correctamente.",
                        "Registro exitoso",
                        JOptionPane.INFORMATION_MESSAGE);

                return true;
            }

            return false;

        } catch (IllegalArgumentException e) {

            JOptionPane.showMessageDialog(
                    this,
                    e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);

            return false;

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Error: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);

            e.printStackTrace();

            return false;
        }
    }
}
