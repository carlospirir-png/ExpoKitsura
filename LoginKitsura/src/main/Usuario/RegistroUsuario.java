// REGISTRO USUARIOOOOOOOO
package main.Usuario;

import java.awt.*;
import java.net.URL;
import javax.swing.*;
import java.sql.*;
import main.Menu.*;
import main.conexion.Conexion;

public class RegistroUsuario extends JFrame {

    private final FondoPanel fondo;
    private Font fuente1, fuente2;

    private JTextField txtNombre, txtCorreo;
    private JPasswordField txtPassword;

    private DecoracionBotones btnJugar, btnSalir;

    private JLabel logo, mascota, titulo;
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
//------------- ÍCONO ------------------
        //se obtiene la imagen del logo con getResource
        URL iconUrl = getClass().getResource("/Multimedia/utiles/logotipo/logofK.png");

        //se instancia el ícono con la imagen
        ImageIcon icono = new ImageIcon(iconUrl);

        //Se coloca el ícono al JFrame
        setIconImage(icono.getImage());

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
                        400, 400, Image.SCALE_SMOOTH);

        mascota.setIcon(new ImageIcon(mascotaEscalada));
        mascota.setBounds(1210, 520, 400, 400);

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
        
        //---------------- LABEL NOMBRE ----------------
        lblNombre = new JLabel("Nombre");
        lblNombre.setFont(fuente2.deriveFont(25f));
        lblNombre.setForeground(Color.WHITE);
        lblNombre.setBounds(760, 365, 200, 30);

        fondo.add(lblNombre);

        txtNombre = new JTextField();
        txtNombre.setFont(fuente1.deriveFont(22f));
        txtNombre.setBounds(760, 410, 400, 50);

        fondo.add(txtNombre);

        //---------------- LABEL CORREO ----------------
        lblCorreo = new JLabel("Correo");
        lblCorreo.setFont(fuente2.deriveFont(25f));
        lblCorreo.setForeground(Color.WHITE);
        lblCorreo.setBounds(760, 465, 200, 30);

        fondo.add(lblCorreo);

        txtCorreo = new JTextField();
        txtCorreo.setFont(fuente1.deriveFont(22f));
        txtCorreo.setBounds(760, 500, 400, 50);

        fondo.add(txtCorreo);

        //---------------- LABEL PASSWORD ----------------
        lblPassword = new JLabel("Password");
        lblPassword.setFont(fuente2.deriveFont(25f));
        lblPassword.setForeground(Color.WHITE);
        lblPassword.setBounds(760, 565, 200, 30);

        fondo.add(lblPassword);

        txtPassword = new JPasswordField();
        txtPassword.setFont(fuente1.deriveFont(22f));
        txtPassword.setBounds(760, 600, 400, 50);

        fondo.add(txtPassword);

        //---------------- BOTON JUGAR ----------------
        btnJugar = new DecoracionBotones("JUGAR",
                //ColorBase             ColorBorde              ColorLetra
                DecoracionBotones.ROSA, DecoracionBotones.ROJO, DecoracionBotones.AMARILLO, //MOUSE FUERA
                DecoracionBotones.ROJO, DecoracionBotones.ROSA, DecoracionBotones.ROSA); //MOUSE DENTRO
        btnJugar.setFont(fuente2.deriveFont(15f));
        btnJugar.setBounds(820, 725, 285, 60);

        btnJugar.addActionListener(e -> {
            if (registrarUsuario()) {
                new MenuPrincipal();
                dispose();
            }
        });
        fondo.add(btnJugar);

        //---------------- BOTON SALIR ----------------
        btnSalir = new DecoracionBotones("SALIR",
                                //ColorBase             ColorBorde              ColorLetra
                DecoracionBotones.AZUL, DecoracionBotones.GRIS, DecoracionBotones.AMARILLO, //MOUSE FUERA
                DecoracionBotones.CELESTE, DecoracionBotones.AZUL, DecoracionBotones.AZUL); //MOUSE DENTRO   

        btnSalir.setFont(fuente2.deriveFont(15f));
        btnSalir.setBounds(1750, 950, 120, 40);

        btnSalir.addActionListener(e -> {
            new SalirDelJuego();
            dispose();
        });

        fondo.add(btnSalir);

        //---------------- PANEL SEMITRANSPARENTE INVITADO ----------------
        JPanel pnlInvitado = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(new Color(0, 0, 0, 100)); // Negro con 100 de opacidad (semi-transparente)
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15); // Bordes redondeados estilizados
            }
        };
        pnlInvitado.setOpaque(false);
        pnlInvitado.setLayout(null);
        pnlInvitado.setBounds(1680, 30, 140, 50); // Un poco más grande que el texto para el margen

        //---------------- LABEL INVITADO ----------------
        lblInvitado = new JLabel("<html><u>Invitado</u></html>", SwingConstants.CENTER);
        lblInvitado.setFont(fuente2.deriveFont(20f));
        lblInvitado.setForeground(Color.WHITE);
        lblInvitado.setCursor(new Cursor(Cursor.HAND_CURSOR));
        lblInvitado.setBounds(0, 0, 140, 50); // Se acopla completamente al tamaño del panel contenedor

        lblInvitado.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                new RegistroInvitado();
                dispose();
            }

            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                lblInvitado.setForeground(Color.decode("#EE9797"));
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                lblInvitado.setForeground(Color.WHITE);
            }
        });
        
        pnlInvitado.add(lblInvitado);
        fondo.add(pnlInvitado);


        //---------------- PANEL SEMITRANSPARENTE INICIAR SESION ----------------
        JPanel pnlIniciarSesion = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(new Color(0, 0, 0, 100)); // Negro con 100 de opacidad
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
            }
        };
        pnlIniciarSesion.setOpaque(false);
        pnlIniciarSesion.setLayout(null);
        pnlIniciarSesion.setBounds(20, 945, 180, 50); // Ajustado para hacer juego con la altura del botón salir

        //---------------- LABEL INICIAR SESION ----------------
        JLabel lblIniciarSesion = new JLabel("<html><u>Iniciar Sesión</u></html>", SwingConstants.CENTER);
        lblIniciarSesion.setFont(fuente2.deriveFont(18f));
        lblIniciarSesion.setForeground(Color.WHITE);
        lblIniciarSesion.setCursor(new Cursor(Cursor.HAND_CURSOR));
        lblIniciarSesion.setBounds(0, 0, 180, 50); // Se acopla completamente al tamaño del panel contenedor

        lblIniciarSesion.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                new IniciarSesion();
                dispose();
            }

            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                lblIniciarSesion.setForeground(Color.decode("#EE9797"));
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                lblIniciarSesion.setForeground(Color.WHITE);
            }
        });

        pnlIniciarSesion.add(lblIniciarSesion);
        fondo.add(pnlIniciarSesion);
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

            PreparedStatement ps = con.prepareStatement(
                    sql,
                    Statement.RETURN_GENERATED_KEYS);

            ps.setString(1, nombre);
            ps.setString(2, correo);
            ps.setString(3, password);
            int filas = ps.executeUpdate();

            int idUsuario = 0;

            ResultSet rsId = ps.getGeneratedKeys();

            if (rsId.next()) {
                idUsuario = rsId.getInt(1);
            }

            rsId.close();
            ps.close();
            con.close();

            if (filas > 0) {

                // Se guarda el id del usuario recién creado como sesión activa,
                // para que MenuPrincipal / PantallaPerfil sepan de quién es la partida
                Sesion.setIdUsuarioActual(idUsuario);
                Sesion.setNombreUsuario(nombre);
                
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
<<<<<<< HEAD
    public static void main(String[] args) {
        new RegistroUsuario();
    }
=======

    public static void main(String[] args) {
        new RegistroUsuario();
    }

>>>>>>> 4130457a911d0254da11acfcf01ac5fae96e975b
}