//--------------------------------- AÑADIR ADMINISTRADOR --------------------
package main.Administrador;

import java.awt.*;
import javax.swing.*;
import main.Menu.*;
import java.sql.*; //Importamos sql
import main.conexion.Conexion; //Importamos la conexión

public class NuevoAdmin extends JFrame {

    private FondoPanelSemi fondo;
    private FondoPanelSemi panelSemi;
    private Font fuente1;
    private Font fuente2;
    private JLabel lblTitulo;

    private JLabel lblNombre;
    private JLabel lblCorreo;
    private JLabel lblContra;

    private JTextField txtNombre;
    private JTextField txtCorreo;
    private JPasswordField txtContra;

    private JButton btnAnadir;
    private DecoracionBotones btnSalir;
    private JLabel lblMascota;

    private Conexion conexion = new Conexion(); //Creamos un objeto del tipo Conexion
    //Guardamos en una variable la conexión
    Connection con = conexion.getConnection();

    public NuevoAdmin() {
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
        fondo = new FondoPanelSemi("/Multimedia/utiles/fondos/interfaces/fondoDosK.png");
        setContentPane(fondo);

        setTitle("Añadir Administrador");
        setSize(1920, 1080);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        fondo.setLayout(null);

        crearComponentes();

        setVisible(true);
    }

    private void crearComponentes() {
        //------------------- PANEL -----------------
        panelSemi = new FondoPanelSemi(new Color(0, 0, 0, 120));
        panelSemi.setLayout(null);
        panelSemi.setBounds(120, 100, 1000, 850);

        fondo.add(panelSemi);

        //--------------------- TÍTULO ----------------
        lblTitulo = new JLabel("AÑADIR ADMINISTRADOR", SwingConstants.CENTER);
        lblTitulo.setFont(fuente2.deriveFont(42f));
        lblTitulo.setForeground(Color.decode("#82D3E0"));
        lblTitulo.setBounds(50, 40, 900, 70);

        panelSemi.add(lblTitulo);

        //----------------------- NOMBRE ----------------------
        //--------------- LABEL
        lblNombre = new JLabel("Ingrese el nombre del nuevo administrador:");
        lblNombre.setFont(fuente2.deriveFont(26f));
        lblNombre.setForeground(Color.WHITE);
        lblNombre.setBounds(90, 170, 700, 45);
        panelSemi.add(lblNombre);

        //-------------- TEXTFIELD
        txtNombre = new JTextField();
        txtNombre.setFont(fuente1.deriveFont(30f));
        txtNombre.setBounds(90, 235, 810, 55); // 

        panelSemi.add(txtNombre);
        //---------------------- CORREO --------------------
        //--------------- LABEL
        lblCorreo = new JLabel("Ingrese el correo del nuevo administrador:");
        lblCorreo.setFont(fuente2.deriveFont(26f));
        lblCorreo.setForeground(Color.WHITE);
        lblCorreo.setBounds(90, 370, 750, 45);

        panelSemi.add(lblCorreo);

        //------------------ TEXTFIELD
        txtCorreo = new JTextField();
        txtCorreo.setFont(fuente1.deriveFont(30f));
        txtCorreo.setBounds(90, 435, 810, 55);

        panelSemi.add(txtCorreo);

        //--------------------- CONTRASEÑA ----------------------
        // ------------- LABEL
        lblContra = new JLabel("Ingrese la contraseña del nuevo administrador:");
        lblContra.setFont(fuente2.deriveFont(26f));
        lblContra.setForeground(Color.WHITE);
        lblContra.setBounds(90, 570, 810, 45);

        panelSemi.add(lblContra);

        //---------------PASSWORDFIELD
        txtContra = new JPasswordField();
        txtContra.setFont(fuente1.deriveFont(30f));
        txtContra.setBounds(90, 635, 810, 55);

        panelSemi.add(txtContra);

        //------------------------ BOTÓN AÑADIR -----------------
        btnAnadir = new DecoracionBotones("AÑADIR",
                //ColorBase             ColorBorde              ColorLetra
                DecoracionBotones.ROSA, DecoracionBotones.ROJO, DecoracionBotones.AMARILLO, //MOUSE FUERA
                DecoracionBotones.ROJO, DecoracionBotones.ROSA, DecoracionBotones.ROSA); //MOUSE DENTRO
        btnAnadir.setFont(fuente2.deriveFont(25f));
        btnAnadir.setForeground(Color.WHITE);
        btnAnadir.setBounds(350, 730, 300, 70);
        fondo.add(btnAnadir);
        panelSemi.add(btnAnadir);

        btnAnadir.addActionListener(e -> {
            agregarAdministrador();
        });

        //---------------------------- MASCOTA -------------------
        lblMascota = new JLabel();

        ImageIcon mascotaIcon
                = new ImageIcon(
                        getClass().getResource("/Multimedia/utiles/mascotaKitsura/imagen/MINIJUEGO-CONTROL.png"));

        Image mascotaEscalada
                = mascotaIcon.getImage().getScaledInstance(
                        800,
                        800,
                        Image.SCALE_SMOOTH);

        lblMascota.setIcon(new ImageIcon(mascotaEscalada));
        lblMascota.setBounds(1080, 120, 800, 800);

        fondo.add(lblMascota);

        //--------------------------- BOTÓN VOLVER --------------
        btnSalir = new DecoracionBotones("VOLVER",
                //ColorBase             ColorBorde              ColorLetra
                DecoracionBotones.AZUL, DecoracionBotones.GRIS, DecoracionBotones.AMARILLO, //MOUSE FUERA
                DecoracionBotones.CELESTE, DecoracionBotones.AZUL, DecoracionBotones.AZUL); //MOUSE DENTRO);
        btnSalir.setFont(fuente2.deriveFont(30f));
        btnSalir.setForeground(Color.WHITE);
        btnSalir.setBounds(1695, 950, 210, 45);
        btnSalir.addActionListener(e -> {
            new UsuarioMenu();
            dispose();
                });
        fondo.add(btnSalir);
    }

    public void agregarAdministrador() {
        //Se obtienen los datos de los txt
        String nombre = txtNombre.getText().trim();
        String correo = txtCorreo.getText().trim();
        String contrasena = String.valueOf(txtContra.getPassword());

        //------------- VALIDACIONES
        //si el nombre o el correo o la contraseña están vacíos
        if (nombre.isEmpty() || correo.isEmpty() || contrasena.isEmpty()) {
            //se muestra mensaje
            JOptionPane.showMessageDialog(
                    this,
                    "Debe llenar todos los campos.");
            return;
        }

        //si el correo es inválido
        if (!correo.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
            JOptionPane.showMessageDialog(
                    this,
                    "Ingrese un correo válido. (ej. @kitsura.com)",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        //Si la conttraseña es demasiado pequeña
        if (contrasena.length() < 6) {
            JOptionPane.showMessageDialog(
                    this,
                    "La contraseña debe tener mínimo 6 caracteres.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        //------- SI EL CORREO EXISTE -----------
        if (verificarCorreo(correo)){
            JOptionPane.showMessageDialog(
                        this,
                        "Ya existe una cuenta con ese correo.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
        }

        //Consulta
        //Inserta en usuario el nombre, correo,contraseña y rol
        String sql = "INSERT INTO Usuario (nombre_usuario, correo, contrasena, rol) VALUES (?, ?, ?, 'administrador')";

        try (PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, nombre);
            ps.setString(2, correo);
            ps.setString(3, contrasena);

            ps.executeUpdate();

            //mensaje si se agregó
            JOptionPane.showMessageDialog(
                    this,
                    "Administrador agregado correctamente.");

            //se limpian los text
            txtNombre.setText("");
            txtCorreo.setText("");
            txtContra.setText("");

            //Captura la excepción SQl
        } catch (SQLException e) {
            //Si hay excepción: mensaje de error
            JOptionPane.showMessageDialog(
                    this,
                    "Error al agregar administrador:\n" + e.getMessage());
            //Imprime el StackTrace
            e.printStackTrace();
        }
    }

    public boolean verificarCorreo(String correo) {
        String sql = "SELECT correo FROM Usuario WHERE correo = ?";

        try (PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, correo);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return true;
            }
            
            return false;
        //Captura la excepción SQL
        } catch (SQLException e) {
            //Si hay excepción: mensaje de error
            JOptionPane.showMessageDialog(
                    this,
                    "Ocurrió un error en el correo:\n" + e.getMessage());
            //Imprime el StackTrace
            e.printStackTrace();
            return false;
        }
    }

    public static void main(String[] args) {
        new NuevoAdmin();
    }
}
