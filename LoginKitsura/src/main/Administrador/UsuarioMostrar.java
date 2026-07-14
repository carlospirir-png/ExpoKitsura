package main.Administrador;

import java.awt.*;
import java.net.URL;
import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import main.Menu.DecoracionBotones;
import main.Menu.FondoPanel;
import main.Menu.FondoPanelSemi;
import main.conexion.Conexion;

public class UsuarioMostrar extends JFrame {

    private FondoPanel fondo;
    private Font fuente1;
    private Font fuente2;

    // Se convierten en atributos de instancia para poder usarlos en varios métodos
    private JTextField txtID;
    private DefaultTableModel modelo;
    private JTable tablaUsuarios;

    public UsuarioMostrar() {

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

        fondo = new FondoPanel("/Multimedia/utiles/fondos/interfaces/fondoDosK.png");
        setContentPane(fondo);
//------------- ÍCONO ------------------
        //se obtiene la imagen del logo con getResource
        URL iconUrl = getClass().getResource("/Multimedia/utiles/logotipo/logofK.png");

        //se instancia el ícono con la imagen
        ImageIcon icono = new ImageIcon(iconUrl);

        //Se coloca el ícono al JFrame
        setIconImage(icono.getImage());

        setTitle("Usuarios");
        setSize(1920, 1080);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        fondo.setLayout(null);

        crearComponentes();

        // Se cargan los usuarios apenas se abre la ventana
        cargarDatos();

        setVisible(true);
    }

    private void crearComponentes() {

        //---------------- PANEL IZQUIERDO ----------------
        FondoPanelSemi panelIzquierdo = new FondoPanelSemi(new Color(0, 0, 0, 130));
        panelIzquierdo.setLayout(null);
        panelIzquierdo.setBounds(100, 170, 520, 700);
        fondo.add(panelIzquierdo);

        JButton btnEliminar = new DecoracionBotones("ELIMINAR",
                DecoracionBotones.ROSA, DecoracionBotones.ROJO, DecoracionBotones.AMARILLO,
                DecoracionBotones.ROJO, DecoracionBotones.ROSA, DecoracionBotones.ROSA);

        btnEliminar.setFont(fuente2.deriveFont(24f));
        btnEliminar.setBounds(40, 40, 190, 60);

        btnEliminar.addActionListener(e -> eliminarUsuario());

        panelIzquierdo.add(btnEliminar);

        JButton btnBuscar = new DecoracionBotones("BUSCAR",
                DecoracionBotones.ROSA, DecoracionBotones.ROJO, DecoracionBotones.AMARILLO,
                DecoracionBotones.ROJO, DecoracionBotones.ROSA, DecoracionBotones.ROSA);

        btnBuscar.setFont(fuente2.deriveFont(24f));
        btnBuscar.setBounds(280, 40, 190, 60);

        btnBuscar.addActionListener(e -> buscarUsuario());

        panelIzquierdo.add(btnBuscar);

        JLabel lblID = new JLabel("Ingrese el ID del usuario");
        lblID.setFont(fuente2.deriveFont(26f));
        lblID.setForeground(Color.WHITE);
        lblID.setBounds(35, 150, 440, 35);
        panelIzquierdo.add(lblID);

        txtID = new JTextField();
        txtID.setFont(fuente1.deriveFont(34f));
        txtID.setBounds(35, 200, 440, 55);
        panelIzquierdo.add(txtID);

        //---------------- MASCOTA ----------------
        JLabel lblMascota = new JLabel();

        try {

            ImageIcon mascota = new ImageIcon(
                    getClass().getResource("/Multimedia/utiles/mascotaKitsura/imagen/REGISTRARSE_USUARIO-PARTIDA_MINIJUEGO-TABLETA.png"));

            Image img = mascota.getImage().getScaledInstance(430, 430, Image.SCALE_SMOOTH);

            lblMascota.setIcon(new ImageIcon(img));

        } catch (Exception e) {
            lblMascota.setText("~");
        }

        lblMascota.setBounds(40, 280, 430, 430);
        panelIzquierdo.add(lblMascota);

        //---------------- PANEL DERECHO ----------------
        FondoPanelSemi panelTabla = new FondoPanelSemi(new Color(0, 0, 0, 130));
        panelTabla.setLayout(null);
        panelTabla.setBounds(700, 170, 980, 700);
        fondo.add(panelTabla);

        JLabel lblTabla = new JLabel("LISTA DE USUARIOS", JLabel.CENTER);
        lblTabla.setFont(fuente2.deriveFont(34f));
        lblTabla.setForeground(Color.WHITE);
        lblTabla.setBounds(0, 20, 980, 45);
        panelTabla.add(lblTabla);

        //---------------- TABLA ----------------
        modelo = new DefaultTableModel(
                new String[]{"ID", "NOMBRE", "CORREO", "CONTRASEÑA"}, 0) {

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tablaUsuarios = new JTable(modelo);

        tablaUsuarios.setFont(fuente1.deriveFont(18f));
        tablaUsuarios.setRowHeight(55);

        tablaUsuarios.getTableHeader().setFont(fuente2.deriveFont(20f));
        tablaUsuarios.getTableHeader().setReorderingAllowed(false);

        tablaUsuarios.getColumnModel().getColumn(0).setPreferredWidth(90);
        tablaUsuarios.getColumnModel().getColumn(1).setPreferredWidth(220);
        tablaUsuarios.getColumnModel().getColumn(2).setPreferredWidth(320);
        tablaUsuarios.getColumnModel().getColumn(3).setPreferredWidth(250);

        JScrollPane scrollTabla = new JScrollPane(tablaUsuarios);
        scrollTabla.setBounds(30, 90, 920, 580);

        panelTabla.add(scrollTabla);

        //---------------- BOTÓN VOLVER ----------------
        JButton btnVolver = new DecoracionBotones("VOLVER",
                DecoracionBotones.AZUL, DecoracionBotones.GRIS, DecoracionBotones.AMARILLO,
                DecoracionBotones.CELESTE, DecoracionBotones.AZUL, DecoracionBotones.AZUL);

        btnVolver.setFont(fuente2.deriveFont(28f));
        btnVolver.setBounds(1550, 900, 300, 65);

        btnVolver.addActionListener(e -> {
            new UsuarioMenu();
            dispose();
        });

        fondo.add(btnVolver);
    }

    private void buscarUsuario() {

        String texto = txtID.getText().trim();

        if (texto.isEmpty()) {
            // Si no se ingresó nada, mostramos todos
            cargarDatos();
            return;
        }

        int id;
        try {
            id = Integer.parseInt(texto);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                    "El ID debe ser un número válido.",
                    "Dato inválido",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        modelo.setRowCount(0);

        String sql = "SELECT id_usuario, nombre_usuario, correo, contrasena FROM Usuario WHERE id_usuario = ?";

        try (Connection conx = new Conexion().getConnection(); PreparedStatement ps = conx.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {

                boolean encontrado = false;

                while (rs.next()) {
                    encontrado = true;
                    modelo.addRow(new Object[]{
                        rs.getInt("id_usuario"),
                        rs.getString("nombre_usuario"),
                        rs.getString("correo"),
                        rs.getString("contrasena")
                    });
                }

                if (!encontrado) {
                    JOptionPane.showMessageDialog(this,
                            "No se encontró ningún usuario con ese ID.",
                            "Sin resultados",
                            JOptionPane.INFORMATION_MESSAGE);
                }
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this,
                    "Error al buscar el usuario: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Consulta todos los usuarios registrados en la base de datos y los carga
     * en la tabla.
     */
    private void cargarDatos() {

        modelo.setRowCount(0); // Se limpia la tabla antes de llenarla

        String sql = "SELECT id_usuario, nombre_usuario, correo, contrasena FROM Usuario";

        try (Connection conx = new Conexion().getConnection(); PreparedStatement ps = conx.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                modelo.addRow(new Object[]{
                    rs.getInt("id_usuario"),
                    rs.getString("nombre_usuario"),
                    rs.getString("correo"),
                    rs.getString("contrasena")
                });
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this,
                    "Error al mostrar los usuarios: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Elimina un usuario según el ID ingresado en el TextField, previa
     * confirmación del usuario.
     */
    private void eliminarUsuario() {

        String texto = txtID.getText().trim();

        if (texto.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Debe ingresar un ID de usuario.",
                    "Campo vacío",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        int id;
        try {
            id = Integer.parseInt(texto);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                    "El ID debe ser un número válido.",
                    "Dato inválido",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirmacion = JOptionPane.showConfirmDialog(this,
                "¿Está seguro que desea eliminar al usuario con ID " + id + "?",
                "Confirmar eliminación",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);

        if (confirmacion != JOptionPane.YES_OPTION) {
            return;
        }

        String sql = "DELETE FROM Usuario WHERE id_usuario = ?";

        try (Connection conx = new Conexion().getConnection(); PreparedStatement ps = conx.prepareStatement(sql)) {

            ps.setInt(1, id);
            int filasAfectadas = ps.executeUpdate();

            if (filasAfectadas > 0) {
                JOptionPane.showMessageDialog(this,
                        "Usuario eliminado correctamente.",
                        "Éxito",
                        JOptionPane.INFORMATION_MESSAGE);
                txtID.setText("");
                cargarDatos(); // Se refresca la tabla
            } else {
                JOptionPane.showMessageDialog(this,
                        "No se encontró ningún usuario con ese ID.",
                        "Sin resultados",
                        JOptionPane.INFORMATION_MESSAGE);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this,
                    "Error al eliminar el usuario: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }

    }

    public static void main(String[] args) {
        new UsuarioMostrar();
    }

}