// INICIAR SESIOOOOOOOOOOOOOOOON
package main.Usuario;

import java.awt.*;
import javax.swing.*;
import java.sql.*;
import main.Menu.FondoPanel;
import main.Menu.FondoPanelSemi;
import main.Menu.DecoracionBotones;
import main.conexion.Conexion;

public class IniciarSesion extends JFrame {

    private final FondoPanel fondo;
    private Font fuente1, fuente2;

    // Componentes globales
    private JTextField txtUsuario;
    private JPasswordField txtPassword;

    private DecoracionBotones btnLogIn, btnVolver;

    private JLabel logo, mascota, titulo;
    private JLabel lblUsuario, lblPassword;

    public IniciarSesion() {

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
                        350, 350, Image.SCALE_SMOOTH);

        mascota.setIcon(new ImageIcon(mascotaEscalada));
        mascota.setBounds(1250, 560, 350, 350);

        fondo.add(mascota);
        
        FondoPanelSemi panelEslogan = new FondoPanelSemi(new Color(0, 0, 0, 140));
        panelEslogan.setBounds(825, 210, 300, 35);
        panelEslogan.setLayout(null);
        fondo.add(panelEslogan);
        
        //---------------- ESLOGAN ----------------
        titulo = new JLabel("No es magia, es mente");
        titulo.setFont(fuente1.deriveFont(30f));
        titulo.setForeground(Color.decode("#82D3E0"));
        titulo.setBounds(35, -8, 280, 50);

        panelEslogan.add(titulo);

        //---------------- USUARIO ----------------
        lblUsuario = new JLabel("Nombre:");
        lblUsuario.setFont(fuente2.deriveFont(25f));
        lblUsuario.setForeground(Color.WHITE);
        lblUsuario.setBounds(760, 380, 200, 30);

        fondo.add(lblUsuario);

        txtUsuario = new JTextField();
        txtUsuario.setFont(fuente1.deriveFont(25f));
        txtUsuario.setBounds(760, 415, 400, 50);

        fondo.add(txtUsuario);

        //---------------- PASSWORD ----------------
        lblPassword = new JLabel("Password:");
        lblPassword.setFont(fuente2.deriveFont(25f));
        lblPassword.setForeground(Color.WHITE);
        lblPassword.setBounds(760, 490, 200, 30);

        fondo.add(lblPassword);

        txtPassword = new JPasswordField();
        txtPassword.setFont(fuente1.deriveFont(40f));
        txtPassword.setBounds(760, 525, 400, 50);

        fondo.add(txtPassword);

        //---------------- BOTON ----------------
        btnLogIn = new DecoracionBotones("INICIAR SESIÓN", "#FC767D", "#da4d58", "#da4d58");
        btnLogIn.setFont(fuente2.deriveFont(15f));
        btnLogIn.setBounds(820, 650, 285, 60);

        btnLogIn.addActionListener(e -> {

            if (iniciarSesion()) {

                JOptionPane.showMessageDialog(
                        this,
                        "Inicio de sesión exitoso.",
                        "Bienvenido",
                        JOptionPane.INFORMATION_MESSAGE);

                dispose();
                new MenuPrincipal();
            }
        });

        fondo.add(btnLogIn);

        //---------------- BOTON VOLVER ----------------
        JButton btnVolver = new DecoracionBotones("VOLVER");
        btnVolver.setFont(fuente2.deriveFont(10f));
        btnVolver.setBounds(40, 975, 120, 40);
        
        btnVolver.addActionListener(e -> {

            new RegistroUsuario();
            dispose();

        });

        fondo.add(btnVolver);
    }

    private boolean iniciarSesion() {

        try {

            String usuario = txtUsuario.getText().trim();
            String password = String.valueOf(txtPassword.getPassword());

            // VALIDACIONES
            if (usuario.isEmpty()) {
                throw new IllegalArgumentException(
                        "Debe ingresar un usuario.");
            }

            if (password.isEmpty()) {
                throw new IllegalArgumentException(
                        "Debe ingresar una contraseña.");
            }

            Connection con = new Conexion().getConnection();

            if (con == null) {
                throw new Exception(
                        "No fue posible conectar con la base de datos.");
            }

            String sql
                    = "SELECT * FROM Usuario "
                    + "WHERE nombre_usuario = ? "
                    + "AND contrasena = ? "
                    + "AND estado = 'activo'";

            PreparedStatement ps
                    = con.prepareStatement(sql);

            ps.setString(1, usuario);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                rs.close();
                ps.close();
                con.close();

                return true;
            }

            rs.close();
            ps.close();
            con.close();

            throw new IllegalArgumentException(
                    "Usuario o contraseña incorrectos.");

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
    
    public static void main(String[] args) {
        new IniciarSesion();
    }
}
