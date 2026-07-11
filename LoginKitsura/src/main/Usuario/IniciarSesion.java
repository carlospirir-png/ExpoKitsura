// INICIAR SESIOOOOOOOOOOOOOOOON
package main.Usuario;

import java.awt.*;
import javax.swing.*;
import java.sql.*;
import main.Administrador.*;
import main.Menu.*;
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
                        400, 400, Image.SCALE_SMOOTH);
        mascota.setIcon(new ImageIcon(mascotaEscalada));
        mascota.setBounds(1210, 540, 400, 400);

        fondo.add(mascota);

        FondoPanelSemi panelEslogan = new FondoPanelSemi(new Color(0, 0, 0, 140));
        panelEslogan.setBounds(815, 210, 320, 35);
        panelEslogan.setLayout(null);
        fondo.add(panelEslogan);

        //---------------- ESLOGAN ----------------
        titulo = new JLabel("No es magia, es mente");
        titulo.setFont(fuente1.deriveFont(34f));
        titulo.setForeground(Color.decode("#EBBF66"));
        titulo.setBounds(30, -8, 280, 50);

        panelEslogan.add(titulo);

        //---------------- USUARIO ----------------
        lblUsuario = new JLabel("Correo:");
        lblUsuario.setFont(fuente2.deriveFont(25f));
        lblUsuario.setForeground(Color.WHITE);
        lblUsuario.setBounds(760, 380, 200, 30);

        fondo.add(lblUsuario);

        txtUsuario = new JTextField();
        txtUsuario.setFont(fuente1.deriveFont(25f));
        txtUsuario.setBounds(760, 415, 400, 50);

        fondo.add(txtUsuario);

        //---------------- PASSWORD ----------------
        lblPassword = new JLabel("Contraseña:");
        lblPassword.setFont(fuente2.deriveFont(25f));
        lblPassword.setForeground(Color.WHITE);
        lblPassword.setBounds(760, 490, 200, 30);

        fondo.add(lblPassword);

        txtPassword = new JPasswordField();
        txtPassword.setFont(fuente1.deriveFont(40f));
        txtPassword.setBounds(760, 525, 400, 50);

        fondo.add(txtPassword);
        //---------------- PANEL SEMITRANSPARENTE CONTRASEÑA ----------------
        JPanel pnlcontrasena = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(new Color(0, 0, 0, 100)); // Negro con 100 de opacidad (semi-transparente)
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15); // Bordes redondeados estilizados
            }
        };
        pnlcontrasena.setOpaque(false);
        pnlcontrasena.setLayout(null);
        pnlcontrasena.setBounds(760, 580, 400, 50); // Un poco más grande que el texto para el margen

        //---------------- LABEL RECUPERAR CONTRASEÑA
        JLabel lblRC = new JLabel("<html><u>¿Olvidaste tu contraseña?</u></html>", SwingConstants.CENTER);
        lblRC.setFont(fuente2.deriveFont(18f));
        lblRC.setForeground(Color.WHITE);
        lblRC.setCursor(new Cursor(Cursor.HAND_CURSOR));
        lblRC.setBounds(0, 0, 400, 50); // Se acopla completamente al tamaño del panel contenedor

        lblRC.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                new RecuperarContrasena();
            }

            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                lblRC.setForeground(Color.decode("#EE9797"));
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                lblRC.setForeground(Color.WHITE);
            }
        });

        pnlcontrasena.add(lblRC);
        fondo.add(pnlcontrasena);

        //---------------- BOTON ----------------
        btnLogIn = new DecoracionBotones("INICIAR SESIÓN",
                //ColorBase             ColorBorde              ColorLetra
                DecoracionBotones.ROSA, DecoracionBotones.ROJO, DecoracionBotones.AMARILLO, //MOUSE FUERA
                DecoracionBotones.ROJO, DecoracionBotones.ROSA, DecoracionBotones.ROSA); //MOUSE DENTRO

        btnLogIn.setFont(fuente2.deriveFont(15f));
        btnLogIn.setBounds(820, 650, 285, 60);

        btnLogIn.addActionListener(e -> {

            String rol = iniciarSesion();

            if (rol != null) {

                if (rol.equalsIgnoreCase("administrador")) {
                    new MenuAdmin();
                } else {
                    new MenuPrincipal();
                }

                dispose();
            }
        });

        fondo.add(btnLogIn);

        //---------------- BOTON VOLVER ----------------
        JButton btnVolver = new DecoracionBotones("VOLVER",
                //ColorBase             ColorBorde              ColorLetra
                DecoracionBotones.AZUL, DecoracionBotones.GRIS, DecoracionBotones.AMARILLO, //MOUSE FUERA
                DecoracionBotones.CELESTE, DecoracionBotones.AZUL, DecoracionBotones.AZUL); //MOUSE DENTRO 
        btnVolver.setFont(fuente2.deriveFont(13f));
        btnVolver.setBounds(40, 945, 150, 40);

        btnVolver.addActionListener(e -> {

            new RegistroUsuario();
            dispose();

        });

        fondo.add(btnVolver);
    }

    private String iniciarSesion() {

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
                    + "WHERE correo = ? "
                    + "AND contrasena = ? "
                    + "AND estado = 'activo'";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, usuario);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                // Guardar el id del usuario
                int idUsuario = rs.getInt("id_usuario");
                Sesion.setIdUsuarioActual(idUsuario);

                // Obtener el rol
                String rol = rs.getString("rol");
                
                // Guardar nombre y foto del usuario que inició sesión
                String nombreUsuario = rs.getString("nombre_usuario"); 
                Sesion.setNombreUsuario(nombreUsuario);

                String rutaFoto = rs.getString("imagen_perfil");
                Sesion.setRutaFotoPerfil(rutaFoto); // si es null (nunca cambió su foto), el setter lo ignora y queda la de defecto
    
                rs.close();
                ps.close();
                con.close();

                return rol;
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

            return null;

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Error: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);

            e.printStackTrace();

            return null;
        }
    }

    public static void main(String[] args) {
        new IniciarSesion();
    }
}
