package main;

import java.sql.*;
import java.awt.*;
import javax.swing.*;

public class IniciarSesion extends JFrame {

    private FondoPanel fondo;
    private Font fuente1, fuente2;
    private JLabel logo, mascota, titulo, lblUsuario, lblPassword;
    private JTextField txtUsuario;
    private JPasswordField txtPassword;
    private JButton btnLogIn;
    
    public IniciarSesion() {

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

        fondo = new FondoPanel("/Multimedia/utiles/fondoUnoK.png");
        setContentPane(fondo);
        setTitle("Inicio de sesión");
        setSize(1880, 1080);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        fondo.setLayout(null);

        crearComponentes();

        setVisible(true);
    }

    private void crearComponentes() {

        // LOGO
        logo = new JLabel();
        ImageIcon logoIcon = new ImageIcon(getClass().getResource("/Multimedia/utiles/logofK.png"));
        Image logoEscalado = logoIcon.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);
        logo.setIcon(new ImageIcon(logoEscalado));
        logo.setBounds(900, 40, 150, 150);
        fondo.add(logo);

        // MASCOTA
        mascota = new JLabel();
        ImageIcon mascotaIcon = new ImageIcon(getClass().getResource("/Multimedia/utiles/mascota1.png"));
        Image mascotaEscalada = mascotaIcon.getImage().getScaledInstance(191, 264, Image.SCALE_SMOOTH);
        mascota.setIcon(new ImageIcon(mascotaEscalada));
        mascota.setBounds(1250, 550, 191, 264);
        fondo.add(mascota);

        // ESLOGAN
        titulo = new JLabel("No es magia, es mente");
        titulo.setFont(fuente1.deriveFont(30f));
        titulo.setForeground(Color.BLUE);
        titulo.setBounds(865, 190, 400, 60);
        fondo.add(titulo);

        // USUARIO
        lblUsuario = new JLabel("Nombre:");
        lblUsuario.setFont(fuente2.deriveFont(25f));
        lblUsuario.setForeground(Color.WHITE);
        lblUsuario.setBounds(760, 380, 200, 30);
        fondo.add(lblUsuario);

        txtUsuario = new JTextField();
        txtUsuario.setFont(fuente1.deriveFont(22f));
        txtUsuario.setBounds(760, 415, 400, 50);
        fondo.add(txtUsuario);

        // PASSWORD
        lblPassword = new JLabel("Password:");
        lblPassword.setFont(fuente2.deriveFont(25f));
        lblPassword.setForeground(Color.WHITE);
        lblPassword.setBounds(760, 490, 200, 30);
        fondo.add(lblPassword);

        txtPassword = new JPasswordField();
        txtPassword.setFont(fuente1.deriveFont(22f));
        txtPassword.setBounds(760, 525, 400, 50);
        fondo.add(txtPassword);

        // BOTÓN
        btnLogIn = new JButton("INICIAR SESION");
        btnLogIn.setFont(fuente2.deriveFont(15f));
        btnLogIn.setBounds(820, 650, 285, 60);
        btnLogIn.addActionListener(e -> iniciarSesion());
        fondo.add(btnLogIn);
    }

    private void iniciarSesion() {
        
        String usuario = txtUsuario.getText().trim();
        String contrasena = new String(txtPassword.getPassword());

        if (usuario.isEmpty() || contrasena.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Complete todos los campos");
            return;
        }

        String sql = """
        SELECT *
        FROM Usuario
        WHERE nombre_usuario = ?
        AND contrasena = ?
        AND estado = 'activo'
        """;

        try (
                Connection con = (Connection) new Conexion().getConnection(); PreparedStatement ps = con.prepareStatement(sql);) {
            if (con == null) {
                JOptionPane.showMessageDialog(
                        this,
                        "No fue posible conectar con la base de datos");
                return;
            }

            ps.setString(1, usuario);
            ps.setString(2, contrasena);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {

                    JOptionPane.showMessageDialog(
                            this,
                            "Bienvenido " + rs.getString("nombre_usuario"));

                    new MenuPrincipal();
                    dispose();

                } else {

                    JOptionPane.showMessageDialog(
                            this,
                            "Usuario o contraseña incorrectos");
                }
            }

        } catch (Exception ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "Error: " + ex.getMessage());

            ex.printStackTrace();
        }
    }
}
