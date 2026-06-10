// INICIAR SESIOOOOOOOOOOOOOOOON
package main;

import java.awt.*;
import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class IniciarSesion extends JFrame {

    private FondoPanel fondo;
    private JLabel logo, mascota, titulo, lblUsuario, lblPassword;
    private JTextField txtUsuario;
    private JPasswordField txtPassword;
    private JButton btnLogIn;

    public IniciarSesion() {
        fondo = new FondoPanel("/multimedia/utiles/fondoUnoK.png");
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

        //---------------- L O G O ----------------
        logo = new JLabel();
        ImageIcon logoIcon = new ImageIcon(
                getClass().getResource("/multimedia/utiles/logofK.png"));

        Image logoEscalado = logoIcon.getImage()
                .getScaledInstance(150, 150, Image.SCALE_SMOOTH);

        logo.setIcon(new ImageIcon(logoEscalado));
        logo.setBounds(900, 40, 150, 150);
        fondo.add(logo);

        //---------------- MASCOTA ----------------
        mascota = new JLabel();

        ImageIcon mascotaIcon = new ImageIcon(
                getClass().getResource("/multimedia/utiles/mascota1.png"));

        Image mascotaEscalada = mascotaIcon.getImage()
                .getScaledInstance(191, 264, Image.SCALE_SMOOTH);

        mascota.setIcon(new ImageIcon(mascotaEscalada));
        mascota.setBounds(1250, 550, 191, 264);
        fondo.add(mascota);

        //---------------- ESLOGAN ----------------
        titulo = new JLabel("No es magia, es mente");
        titulo.setFont(new Font("Arial", Font.BOLD, 20));
        titulo.setForeground(Color.WHITE);
        titulo.setBounds(865, 190, 400, 60);
        fondo.add(titulo);

        //---------------- USUARIO ----------------
        lblUsuario = new JLabel("NOMBRE:");
        lblUsuario.setFont(new Font("Arial", Font.BOLD, 18));
        lblUsuario.setForeground(Color.WHITE);
        lblUsuario.setBounds(760, 350, 200, 30);
        fondo.add(lblUsuario);

        txtUsuario = new JTextField();
        txtUsuario.setBounds(760, 385, 400, 50);
        fondo.add(txtUsuario);

        //---------------- PASSWORD ----------------
        lblPassword = new JLabel("CONTRASEÑA");
        lblPassword.setFont(new Font("Arial", Font.BOLD, 18));
        lblPassword.setForeground(Color.WHITE);
        lblPassword.setBounds(760, 450, 200, 30);
        fondo.add(lblPassword);

        txtPassword = new JPasswordField();
        txtPassword.setBounds(760, 485, 400, 50);
        fondo.add(txtPassword);

        //---------------- BOTON ----------------
        btnLogIn = new JButton("INICIAR SESION");
        btnLogIn.setBounds(820, 600, 285, 60);

        btnLogIn.addActionListener(e -> iniciarSesion());

        fondo.add(btnLogIn);
    }

    private void iniciarSesion() {
        String usuario = txtUsuario.getText().trim();
        String contrasena = new String(txtPassword.getPassword());

        if (usuario.isEmpty() || contrasena.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Complete todos los campos");

            return;
        }

        String sql = """
            SELECT *
            FROM Usuario
            WHERE nombre_usuario = ?
            AND contrasena = ?
            AND estado = 'activo'
            """;

        try {

            Conexion conexion = new Conexion();
            Connection con = conexion.getConnection();

            if (con == null) {

                JOptionPane.showMessageDialog(
                        this,
                        "No fue posible conectar con la base de datos");

                return;
            }

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, usuario);
            ps.setString(2, contrasena);

            ResultSet rs = ps.executeQuery();

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

            rs.close();
            ps.close();
            con.close();

        } catch (Exception ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "Error: " + ex.getMessage());

            ex.printStackTrace();
        }
    }
}
