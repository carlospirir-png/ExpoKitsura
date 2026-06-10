package main;

import java.awt.*;
import java.sql.*;
import javax.swing.*;

public class RegistroUsuario extends JFrame {

    private FondoPanel fondo;
    private Font fuente1, fuente2;

    private JLabel logo, mascota, lblNombre, lblCorreo, lblPassword;
    private JTextField txtNombre, txtCorreo;
    private JPasswordField txtPassword;
    private JButton btnJugar;

    public RegistroUsuario() {
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
        setTitle("Registro");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
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

        // NOMBRE
        lblNombre = new JLabel("Nombre");
        lblNombre.setFont(fuente2.deriveFont(25f));
        lblNombre.setForeground(Color.WHITE);
        lblNombre.setBounds(760, 380, 200, 30);
        fondo.add(lblNombre);

        txtNombre = new JTextField();
        txtNombre.setFont(fuente1.deriveFont(22f));
        txtNombre.setBounds(760, 415, 400, 50);
        fondo.add(txtNombre);

        // CORREO
        lblCorreo = new JLabel("Correo");
        lblCorreo.setFont(fuente2.deriveFont(25f));
        lblCorreo.setForeground(Color.WHITE);
        lblCorreo.setBounds(760, 480, 200, 30);
        fondo.add(lblCorreo);

        txtCorreo = new JTextField();
        txtCorreo.setFont(fuente1.deriveFont(22f));
        txtCorreo.setBounds(760, 515, 400, 50);
        fondo.add(txtCorreo);

        // PASSWORD
        lblPassword = new JLabel("Password");
        lblPassword.setFont(fuente2.deriveFont(25f));
        lblPassword.setForeground(Color.WHITE);
        lblPassword.setBounds(760, 580, 200, 30);
        fondo.add(lblPassword);

        txtPassword = new JPasswordField();
        txtPassword.setFont(fuente1.deriveFont(22f));
        txtPassword.setBounds(760, 615, 400, 50);
        fondo.add(txtPassword);

        // BOTÓN
        btnJugar = new JButton("JUGAR");
        btnJugar.setFont(fuente2.deriveFont(15f));
        btnJugar.setBounds(820, 815, 285, 60);
        btnJugar.addActionListener(e -> registrarUsuario());
        fondo.add(btnJugar);
    }

    private void registrarUsuario() {

        String nombre = txtNombre.getText().trim();
        String correo = txtCorreo.getText().trim();
        String contrasena = new String(txtPassword.getPassword());

        if (nombre.isEmpty() || correo.isEmpty() || contrasena.isEmpty()) {
            JOptionPane.showMessageDialog(
                    this,
                    "Complete todos los campos");
            return;
        }

        String sql = "INSERT INTO Usuario(nombre_usuario, correo, contrasena) VALUES (?, ?, ?)";

        try (
                Connection con = new Conexion().getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {

            if (con == null) {
                JOptionPane.showMessageDialog(
                        this,
                        "No fue posible conectar con la base de datos");
                return;
            }

            ps.setString(1, nombre);
            ps.setString(2, correo);
            ps.setString(3, contrasena);

            int resultado = ps.executeUpdate();

            if (resultado > 0) {
                JOptionPane.showMessageDialog(this,
                        "Usuario registrado correctamente");
                new MenuPrincipal();
                this.dispose();

            }
        } catch (SQLIntegrityConstraintViolationException ex) {
            JOptionPane.showMessageDialog(
                    this,
                    "El correo ya se encuentra registrado");

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(
                    this,
                    "Error: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
}
