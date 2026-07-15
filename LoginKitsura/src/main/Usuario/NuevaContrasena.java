package main.Usuario;

import java.awt.*;
import java.net.URL;
import java.sql.*;
import javax.swing.*;
import main.Menu.FondoPanel;
import main.Menu.DecoracionBotones;
import main.conexion.Conexion;

public class NuevaContrasena extends JFrame {

    //-------------- CONEXIÓN
    private Connection con = new Conexion().getConnection();

    private FondoPanel fondo;
    private Font fuente1;
    private Font fuente2;

    //-------------- STRING
    private String correo; // correo del usuario que ya validó su código

    public NuevaContrasena(String correo) {

        this.correo = correo;

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

        fondo = new FondoPanel("/Multimedia/utiles/fondos/interfaces/fondoTresK.png");
        setContentPane(fondo);
//------------- ÍCONO ------------------
        //se obtiene la imagen del logo con getResource
        URL iconUrl = getClass().getResource("/Multimedia/utiles/logotipo/logofK.png");

        //se instancia el ícono con la imagen
        ImageIcon icono = new ImageIcon(iconUrl);

        //Se coloca el ícono al JFrame
        setIconImage(icono.getImage());

        setTitle("Establecer Nueva Contraseña");
        setSize(700, 450);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        fondo.setLayout(null);
        crearComponentes();
        setVisible(true);
    }

    private void crearComponentes() {
        JPanel panelContenedor = new JPanel();
        panelContenedor.setLayout(null);
        panelContenedor.setBackground(new Color(0, 0, 0, 100));
        panelContenedor.setBounds(25, 60, 400, 310);
        fondo.add(panelContenedor);

        //---------------- N U E V A  C O N T R A S E Ñ A ----------------
        JLabel lblNuevaPassword = new JLabel("Nueva contraseña:");
        lblNuevaPassword.setFont(fuente2.deriveFont(22F));
        lblNuevaPassword.setForeground(Color.WHITE);
        lblNuevaPassword.setBounds(25, 20, 350, 30);
        panelContenedor.add(lblNuevaPassword);

        JPasswordField txtNuevaPassword = new JPasswordField();
        txtNuevaPassword.setBounds(25, 55, 350, 40);
        panelContenedor.add(txtNuevaPassword);

        //---------------- C O N F I R M A R  C O N T R A S E Ñ A ----------------
        JLabel lblConfirmarPassword = new JLabel("Confirmar contraseña:");
        lblConfirmarPassword.setFont(fuente2.deriveFont(22F));
        lblConfirmarPassword.setForeground(Color.WHITE);
        lblConfirmarPassword.setBounds(25, 115, 350, 30);
        panelContenedor.add(lblConfirmarPassword);

        JPasswordField txtConfirmarPassword = new JPasswordField();
        txtConfirmarPassword.setBounds(25, 150, 350, 40);
        panelContenedor.add(txtConfirmarPassword);

        //---------------- B O T O N  G U A R D A R ----------------
        JButton btnGuardar = new DecoracionBotones("GUARDAR",
                                //ColorBase             ColorBorde              ColorLetra
                DecoracionBotones.AZUL, DecoracionBotones.GRIS, DecoracionBotones.AMARILLO, //MOUSE FUERA
                DecoracionBotones.CELESTE, DecoracionBotones.AZUL, DecoracionBotones.AZUL); //MOUSE DENTRO

        btnGuardar.setFont(fuente2.deriveFont(16f));
        btnGuardar.setBounds(100, 235, 200, 45);
        panelContenedor.add(btnGuardar);

        btnGuardar.addActionListener(e -> {

            String nuevaContrasena = new String(txtNuevaPassword.getPassword()).trim();
            String confirmarContrasena = new String(txtConfirmarPassword.getPassword()).trim();

            // Verificar campos vacíos
            if (nuevaContrasena.isEmpty() || confirmarContrasena.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Complete todos los campos.");
                return;
            }

            // Verificar longitud mínima
            if (nuevaContrasena.length() < 6) {
                JOptionPane.showMessageDialog(this, "La contraseña debe tener al menos 6 caracteres.");
                return;
            }

            // Verificar que ambas coincidan
            if (!nuevaContrasena.equals(confirmarContrasena)) {
                JOptionPane.showMessageDialog(this, "Las contraseñas no coinciden.");
                return;
            }

            // Actualizar en la base de datos
            actualizarContrasena(nuevaContrasena);
        });

        //---------------- M A S C O T A ----------------
        JLabel mascota = new JLabel();
        ImageIcon mascotaIcon = new ImageIcon(getClass().getResource("/Multimedia/utiles/mascotaKitsura/imagen/INICIAR_SESIÓN-REGISTRARSE_INVITADO-EDITAR_CONTRASENA.png"));
        Image mascotaEscalada = mascotaIcon.getImage().getScaledInstance(300, 300, Image.SCALE_SMOOTH);
        mascota.setIcon(new ImageIcon(mascotaEscalada));
        mascota.setBounds(420, 70, 300, 300);
        fondo.add(mascota);
    }

    //--------------- A C T U A L I Z A R   C O N T R A S E Ñ A -------------
    private void actualizarContrasena(String nueva) {
        String sql = "UPDATE Usuario SET contrasena = ? WHERE correo = ?";

        try (PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, nueva);
            ps.setString(2, correo);

            int filas = ps.executeUpdate();

            if (filas > 0) {
                JOptionPane.showMessageDialog(
                        this,
                        "Contraseña actualizada correctamente.",
                        "Actualización realizada",
                        JOptionPane.INFORMATION_MESSAGE);
                dispose();
            } else {
                JOptionPane.showMessageDialog(
                        this,
                        "No se encontró el usuario para actualizar la contraseña.",
                        "Error de actualización",
                        JOptionPane.ERROR_MESSAGE);
            }

        } catch (SQLException e) {
            System.out.println("Error al actualizar la contraseña: " + e.getMessage());
            JOptionPane.showMessageDialog(
                    this,
                    "Ocurrió un error al actualizar la contraseña.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        new NuevaContrasena("123@gmail.com");
    }
}