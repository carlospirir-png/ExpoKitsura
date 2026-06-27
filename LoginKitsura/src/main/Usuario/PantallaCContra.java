package main.Usuario;

import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.*;
import main.Menu.FondoPanelSemi;
import main.conexion.Conexion;

public class PantallaCContra extends JFrame {

    Connection con = new Conexion().getConnection();

    private FondoPanelSemi fondo;
    private FondoPanelSemi panelSemi;

    private JLabel lblTitulo;

    private JLabel lblActual;
    private JLabel lblNueva;

    private JPasswordField txtActual;
    private JPasswordField txtNueva;

    private JButton btnAceptar;

    private JLabel lblOlvido;

    private JLabel lblMascota;
    private JLabel lblLogo;

    private String correo;

    public PantallaCContra(String correo) {
        this.correo = correo;
        fondo = new FondoPanelSemi("/Multimedia/utiles/fondos/interfaces/fondoDosK.png");
        setContentPane(fondo);

        setTitle("Editar Contraseña");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        fondo.setLayout(null);

        crearComponentes();

        setVisible(true);
    }

    private void crearComponentes() {

        panelSemi = new FondoPanelSemi(new Color(0, 0, 0, 150));
        panelSemi.setLayout(null);
        panelSemi.setBounds(10, 10, 330, 340);

        fondo.add(panelSemi);

        lblTitulo = new JLabel("Editar Contraseña");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 28));
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setBounds(20, 10, 565, 30);

        panelSemi.add(lblTitulo);

        lblActual = new JLabel("Ingrese la contraseña actual:");
        lblActual.setFont(new Font("Arial", Font.PLAIN, 18));
        lblActual.setForeground(Color.WHITE);
        lblActual.setBounds(20, 60, 280, 30);

        panelSemi.add(lblActual);

        txtActual = new JPasswordField();
        txtActual.setBounds(20, 105, 280, 35);
        txtActual.setBackground(new Color(90, 90, 90));
        txtActual.setForeground(Color.WHITE);

        panelSemi.add(txtActual);

        lblNueva = new JLabel("Ingrese la contraseña nueva:");
        lblNueva.setFont(new Font("Arial", Font.PLAIN, 18));
        lblNueva.setForeground(Color.WHITE);
        lblNueva.setBounds(20, 170, 300, 30);

        panelSemi.add(lblNueva);

        txtNueva = new JPasswordField();
        txtNueva.setBounds(20, 215, 280, 35);
        txtNueva.setBackground(new Color(90, 90, 90));
        txtNueva.setForeground(Color.WHITE);

        panelSemi.add(txtNueva);

        btnAceptar = new JButton("ACEPTAR");
        btnAceptar.setFont(new Font("Arial", Font.BOLD, 18));
        btnAceptar.setBackground(new Color(74, 110, 157));
        btnAceptar.setForeground(Color.WHITE);
        btnAceptar.setFocusPainted(false);
        btnAceptar.setBounds(95, 265, 140, 42);
        
        btnAceptar.addActionListener(e -> compararContrasena());

        panelSemi.add(btnAceptar);

        lblOlvido = new JLabel("<html><u>¿Olvidaste tu contraseña?</u></html>");
        lblOlvido.setFont(new Font("Arial", Font.PLAIN, 14));
        lblOlvido.setForeground(Color.WHITE);
        lblOlvido.setBounds(80, 315, 220, 20);

        panelSemi.add(lblOlvido);

        lblMascota = new JLabel();

        ImageIcon mascotaIcon
                = new ImageIcon(
                        getClass().getResource("/Multimedia/utiles/mascotaKitsura/imagen/INICIAR_SESIÓN-REGISTRARSE_INVITADO-EDITAR_CONTRASENA.png"));

        Image mascotaEscalada
                = mascotaIcon.getImage().getScaledInstance(
                        280,
                        280,
                        Image.SCALE_SMOOTH);

        lblMascota.setIcon(new ImageIcon(mascotaEscalada));
        lblMascota.setBounds(320, 40, 280, 280);

        fondo.add(lblMascota);

        lblLogo = new JLabel();

        ImageIcon logoIcon
                = new ImageIcon(
                        getClass().getResource("/Multimedia/utiles/logotipo/LogoKitsura2.png"));

        Image logoEscalado
                = logoIcon.getImage().getScaledInstance(
                        140,
                        55,
                        Image.SCALE_SMOOTH);

        lblLogo.setIcon(new ImageIcon(logoEscalado));
        lblLogo.setBounds(380, 270, 150, 80);

        fondo.add(lblLogo);
    }

    public String ObtenerContrasena() {

        String sql = "SELECT contrasena FROM Usuario WHERE correo = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, correo);
            ResultSet rs = ps.executeQuery();

            String contrasena = "";
            if (rs.next()) {
                return rs.getString("contrasena");
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener la contraseña: " + e.getMessage());
        }

        return null;
    }

    public void compararContrasena() {
        try {
            String contraActual = new String(txtActual.getPassword());

            String contra = ObtenerContrasena();

            if (contra != null && contra.equals(contraActual)) {
                cambiarContrasena();
            } else {
                JOptionPane.showMessageDialog(null, "No se pudo encontrar la contraseña.", "ERROR Contrasena", JOptionPane.ERROR_MESSAGE);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void cambiarContrasena() {
        String nueva = new String(txtNueva.getPassword());

        String sql = "UPDATE Usuario SET contrasena = ? WHERE correo = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, nueva);
            ps.setString(2, correo);

            int filas = ps.executeUpdate();

            if (filas > 0) {
                JOptionPane.showMessageDialog(null, "Contraseña actualizada correctamente.", "Actualización realizada.", JOptionPane.INFORMATION_MESSAGE);
                dispose();
            }else{
                JOptionPane.showMessageDialog(null, "Errro al actualizar la contraseña.", "ERRROR de actualización", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener la contraseña: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        new PantallaCContra("123@gmail.com");
    }

}
