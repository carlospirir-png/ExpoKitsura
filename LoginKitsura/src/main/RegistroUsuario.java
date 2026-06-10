// REGISTRO USUARIOOOOOOOO
package main;

import java.awt.*;
import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLIntegrityConstraintViolationException;

public class RegistroUsuario extends JFrame {

    private FondoPanel fondo;
    private JLabel logo, mascota, lblNombre, lblCorreo, lblPassword;
    private JTextField txtNombre, txtCorreo;
    private JPasswordField txtPassword;
    private JButton btnJugar;

    public RegistroUsuario() {
        fondo = new FondoPanel("/multimedia/utiles/fondoUnoK.png");
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
        ImageIcon logoIcon = new ImageIcon(getClass().getResource("/multimedia/utiles/logofK.png"));
        Image logoEscalado = logoIcon.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);
        logo.setIcon(new ImageIcon(logoEscalado));
        logo.setBounds(900, 40, 150, 150);
        fondo.add(logo);

        //---------------- MASCOTA ----------------
        mascota = new JLabel();
        ImageIcon mascotaIcon = new ImageIcon(getClass().getResource("/multimedia/utiles/mascota1.png"));
        Image mascotaEscalada = mascotaIcon.getImage().getScaledInstance(191, 264, Image.SCALE_SMOOTH);
        mascota.setIcon(new ImageIcon(mascotaEscalada));
        mascota.setBounds(1250, 550, 191, 264);
        fondo.add(mascota);

        //---------------- LABEL NOMBRE ----------------
        lblNombre = new JLabel("NOMBRE");
        lblNombre.setFont(new Font("Arial", Font.BOLD, 18));
        lblNombre.setForeground(Color.WHITE);
        lblNombre.setBounds(760, 380, 200, 30);
        fondo.add(lblNombre);

        txtNombre = new JTextField();
        txtNombre.setBounds(760, 415, 400, 50);
        fondo.add(txtNombre);

        //---------------- LABEL CORREO ----------------
        lblCorreo = new JLabel("CORREO");
        lblCorreo.setFont(new Font("Arial", Font.BOLD, 18));
        lblCorreo.setForeground(Color.WHITE);
        lblCorreo.setBounds(760, 480, 200, 30);
        fondo.add(lblCorreo);
        txtCorreo = new JTextField();
        txtCorreo.setBounds(760, 515, 400, 50);
        fondo.add(txtCorreo);

        //---------------- LABEL CONTRASEÑA ----------------
        lblPassword = new JLabel("CONTRASEÑA");
        lblPassword.setFont(new Font("Arial", Font.BOLD, 18));
        lblPassword.setForeground(Color.WHITE);
        lblPassword.setBounds(760, 580, 200, 30);
        fondo.add(lblPassword);
        txtPassword = new JPasswordField();
        txtPassword.setBounds(760, 615, 400, 50);
        fondo.add(txtPassword);

        //---------------- BOTON JUGAR ----------------//
        btnJugar = new JButton("REGISTRARSE");
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
    Connection con = null;
    PreparedStatement ps = null;
    try {
        Conexion conexion = new Conexion();
        con = conexion.getConnection();
        if (con == null) {
            JOptionPane.showMessageDialog(
                    this,
                    "No fue posible conectar con la base de datos");
            return;
        }

        ps = con.prepareStatement(sql);
        ps.setString(1, nombre);
        ps.setString(2, correo);
        ps.setString(3, contrasena);
        int resultado = ps.executeUpdate();
        if (resultado > 0) {
            JOptionPane.showMessageDialog(
                    this,
                    "Usuario registrado correctamente");
            txtNombre.setText("");
            txtCorreo.setText("");
            txtPassword.setText("");
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
    } finally {
        try {
            if (ps != null) {
                ps.close();
            }
            if (con != null) {
                con.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
}
