package main.Usuario;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.URL;
import java.sql.*;
import javax.swing.*;
import main.Menu.FondoPanelSemi;
import main.Menu.DecoracionBotones;
import main.conexion.Conexion;

public class PantallaPerfil extends JFrame {

    private FondoPanelSemi fondo;

    private JPanel panelCuenta;
    private JPanel panelPerfil;

    private JLabel lblLogo;
    private JLabel lblMascota;

    private JLabel lblTitulo;

    private JLabel lblCorreo;
    private JLabel lblPassword;

    public JTextField txtCorreo;
    public JPasswordField txtPassword;

    private JLabel lblEditarPassword;
    private JTextField txtUsuario;
    private JLabel lblFotoPerfil;

    // Se vuelven campos de instancia para poder actualizarlos al cargar datos reales
    private JLabel lblIdValor;
    private JLabel lblFechaValor;
    private JLabel lblEstadoValor;

    private DecoracionBotones btnEditarNombre;
    private DecoracionBotones btnCambiarFoto;

    private Font fuente1, fuente2;

    private boolean editandoNombre = false;

    // id y correo del usuario actualmente logueado (se llenan al cargar datos)
    private int idUsuario;
    private String correoUsuario;

    // Ruta de la imagen de perfil actualmente seleccionada (relativa a resources)
    private String rutaImagenPerfilActual;

    private final Conexion conexion = new Conexion();

    public PantallaPerfil() {

        if (!Sesion.haySesionActiva()) {
            JOptionPane.showMessageDialog(null,
                    "No hay una sesión activa. Inicia sesión nuevamente.",
                    "Sesión no encontrada",
                    JOptionPane.ERROR_MESSAGE);
            dispose();
            return;
        }
        this.idUsuario = Sesion.getIdUsuarioActual();

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

        fondo = new FondoPanelSemi("/Multimedia/utiles/fondos/interfaces/fondoDosK.png");
        setContentPane(fondo);

        setTitle("Gestión de Cuenta");
        setSize(1920, 1060);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        fondo.setLayout(null);

        crearComponentes();
        cargarDatosUsuario();

        setVisible(true);
    }

    private void crearComponentes() {

        //---------------- LOGO ----------------
        lblLogo = new JLabel();
        ImageIcon logoIcon = new ImageIcon(getClass().getResource("/Multimedia/utiles/logotipo/logoKitsura.png"));
        Image logoEscalado = logoIcon.getImage().getScaledInstance(300, 300, Image.SCALE_SMOOTH);
        lblLogo.setIcon(new ImageIcon(logoEscalado));
        lblLogo.setBounds(80, 20, 300, 300);
        fondo.add(lblLogo);

        //---------------- MASCOTA ----------------
        lblMascota = new JLabel();
        ImageIcon mascotaIcon = new ImageIcon(getClass().getResource("/Multimedia/utiles/mascotaKitsura/imagen/VENTANITA.png"));
        Image mascotaEscalada = mascotaIcon.getImage().getScaledInstance(450, 450, Image.SCALE_SMOOTH);
        lblMascota.setIcon(new ImageIcon(mascotaEscalada));
        lblMascota.setBounds(700, 30, 450, 450);
        fondo.add(lblMascota);

        //---------------- PANEL CUENTA ----------------
        panelCuenta = new FondoPanelSemi(new Color(220, 220, 220, 220));
        panelCuenta.setOpaque(false);
        panelCuenta.setLayout(null);
        panelCuenta.setBounds(80, 300, 950, 500);
        fondo.add(panelCuenta);

        lblTitulo = new JLabel("Gestión de Cuenta");
        lblTitulo.setFont(fuente2.deriveFont(40f));
        lblTitulo.setForeground(Color.decode("#447A9C"));
        lblTitulo.setBounds(280, 30, 400, 50);
        panelCuenta.add(lblTitulo);

        //---------------- CORREO ----------------
        lblCorreo = new JLabel("Correo Electrónico");
        lblCorreo.setFont(fuente2.deriveFont(24f));
        lblCorreo.setForeground(Color.decode("#FC767D"));
        lblCorreo.setBounds(40, 120, 300, 40);
        panelCuenta.add(lblCorreo);

        txtCorreo = new JTextField("Cargando...");
        txtCorreo.setFont(fuente1.deriveFont(28f));
        txtCorreo.setBounds(40, 170, 420, 50);
        txtCorreo.setEditable(false);
        panelCuenta.add(txtCorreo);

        //---------------- CONTRASEÑA ----------------
        lblPassword = new JLabel("Contraseña");
        lblPassword.setFont(fuente2.deriveFont(24f));
        lblPassword.setForeground(Color.decode("#FC767D"));
        lblPassword.setBounds(40, 270, 250, 40);
        panelCuenta.add(lblPassword);

        txtPassword = new JPasswordField("Cargando...");
        txtPassword.setFont(fuente1.deriveFont(30f));
        txtPassword.setBounds(40, 320, 420, 50);
        txtPassword.setEditable(false);
        panelCuenta.add(txtPassword);

        //---------------- EDITAR CONTRASEÑA (abre PantallaCContra) ----------------
        lblEditarPassword = new JLabel("<html><u>Editar</u></html>");
        lblEditarPassword.setFont(fuente1.deriveFont(22f));
        lblEditarPassword.setForeground(Color.BLACK);
        lblEditarPassword.setCursor(new Cursor(Cursor.HAND_CURSOR));
        lblEditarPassword.setBounds(480, 320, 120, 50);
        lblEditarPassword.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                lblEditarPassword.setForeground(Color.decode("#447A9C"));
            }
            @Override
            public void mouseExited(MouseEvent e) {
                lblEditarPassword.setForeground(Color.BLACK);
            }
            @Override
            public void mouseClicked(MouseEvent e) {
                if (correoUsuario == null || correoUsuario.isBlank()) {
                    JOptionPane.showMessageDialog(PantallaPerfil.this,
                            "No se pudo determinar el correo del usuario.",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                PantallaCContra pantallaContra = new PantallaCContra(correoUsuario);

                // Al cerrarse la ventana de cambio de contraseña, se refrescan los datos
                // por si la contraseña fue actualizada exitosamente
                pantallaContra.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosed(WindowEvent evt) {
                        cargarDatosUsuario();
                    }
                });
            }
        });
        panelCuenta.add(lblEditarPassword);

        //---------------- ID USUARIO ----------------
        JLabel lblIdTitulo = new JLabel("ID Usuario");
        lblIdTitulo.setFont(fuente2.deriveFont(Font.BOLD, 23f));
        lblIdTitulo.setBounds(700, 150, 220, 35);
        panelCuenta.add(lblIdTitulo);

        lblIdValor = new JLabel("---");
        lblIdValor.setFont(fuente1.deriveFont(36f));
        lblIdValor.setBounds(700, 185, 220, 35);
        panelCuenta.add(lblIdValor);

        //---------------- FECHA CREACIÓN ----------------
        JLabel lblFechaTitulo = new JLabel("Fecha de creación");
        lblFechaTitulo.setFont(fuente2.deriveFont(Font.BOLD, 23f));
        lblFechaTitulo.setBounds(700, 290, 250, 35);
        panelCuenta.add(lblFechaTitulo);

        lblFechaValor = new JLabel("--/--/----");
        lblFechaValor.setFont(fuente1.deriveFont(36f));
        lblFechaValor.setBounds(700, 325, 250, 35);
        panelCuenta.add(lblFechaValor);

        //---------------- PANEL PERFIL ----------------
        panelPerfil = new FondoPanelSemi(new Color(220, 220, 220, 220));
        panelPerfil.setOpaque(false);
        panelPerfil.setLayout(null);
        panelPerfil.setBounds(1180, 60, 650, 900);
        fondo.add(panelPerfil);

        //---------------- ESTADO ----------------
        JLabel lblEstadoTitulo = new JLabel("Estado:");
        lblEstadoTitulo.setFont(fuente2.deriveFont(Font.BOLD, 28f));
        lblEstadoTitulo.setBounds(210, 20, 120, 50);
        panelPerfil.add(lblEstadoTitulo);

        lblEstadoValor = new JLabel("---");
        lblEstadoValor.setFont(fuente1.deriveFont(55f));
        lblEstadoValor.setBounds(335, 20, 200, 50);
        panelPerfil.add(lblEstadoValor);

        //---------------- FOTO PERFIL ----------------
        lblFotoPerfil = new JLabel();
        cargarImagenEnLabel(Sesion.FOTO_PERFIL_DEFECTO);
        lblFotoPerfil.setBounds(190, 90, 250, 250);
        lblFotoPerfil.setBorder(BorderFactory.createLineBorder(Color.GRAY, 4));
        panelPerfil.add(lblFotoPerfil);

        //---------------- NOMBRE USUARIO ----------------
        txtUsuario = new JTextField("Cargando...");
        txtUsuario.setFont(fuente1.deriveFont(26f));
        txtUsuario.setBounds(180, 370, 300, 45);
        txtUsuario.setEditable(false);
        panelPerfil.add(txtUsuario);

        //---------------- BOTÓN EDITAR/CONFIRMAR NOMBRE ----------------
        btnEditarNombre = new DecoracionBotones("EDITAR NOMBRE",
                //ColorBase             ColorBorde              ColorLetra
                DecoracionBotones.ROSA, DecoracionBotones.ROJO, DecoracionBotones.AMARILLO, //MOUSE FUERA
                DecoracionBotones.ROJO, DecoracionBotones.ROSA, DecoracionBotones.ROSA); //MOUSE DENTRO
        btnEditarNombre.setFont(fuente2.deriveFont(20f));
        btnEditarNombre.setBounds(190, 430, 280, 60);
        btnEditarNombre.addActionListener(e -> {
            if (!editandoNombre) {
                txtUsuario.setEditable(true);
                txtUsuario.requestFocus();
                btnEditarNombre.setText("CONFIRMAR");
                editandoNombre = true;
            } else {
                String nuevoNombre = txtUsuario.getText().trim();

                if (nuevoNombre.isEmpty()) {
                    JOptionPane.showMessageDialog(this,
                            "El nombre de usuario no puede estar vacío.",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                    return; // No se sale del modo edición hasta que sea válido
                }

                if (guardarNombreUsuario(nuevoNombre)) {
                    txtUsuario.setEditable(false);
                    btnEditarNombre.setText("EDITAR NOMBRE");
                    editandoNombre = false;
                    JOptionPane.showMessageDialog(this,
                            "Nombre de usuario actualizado correctamente.",
                            "Éxito",
                            JOptionPane.INFORMATION_MESSAGE);
                }
                // Si guardarNombreUsuario devuelve false, ya se mostró el error dentro del método
            }
        });
        panelPerfil.add(btnEditarNombre);

        //---------------- BOTÓN CAMBIAR FOTO (abre PantallaImagenPerfil) ----------------
        btnCambiarFoto = new DecoracionBotones("CAMBIAR FOTO",
                //ColorBase             ColorBorde              ColorLetra
                DecoracionBotones.ROSA, DecoracionBotones.ROJO, DecoracionBotones.AMARILLO, //MOUSE FUERA
                DecoracionBotones.ROJO, DecoracionBotones.ROSA, DecoracionBotones.ROSA); //MOUSE DENTRO
        btnCambiarFoto.setFont(fuente2.deriveFont(20f));
        btnCambiarFoto.setBounds(190, 530, 280, 60);
        btnCambiarFoto.addActionListener(e -> new PantallaImagenPerfil(this));
        panelPerfil.add(btnCambiarFoto);

        //---------------- BOTÓN VOLVER ----------------
        JButton btnVolver = new DecoracionBotones("VOLVER",
                                //ColorBase             ColorBorde              ColorLetra
                DecoracionBotones.AZUL, DecoracionBotones.GRIS, DecoracionBotones.AMARILLO, //MOUSE FUERA
                DecoracionBotones.CELESTE, DecoracionBotones.AZUL, DecoracionBotones.AZUL); //MOUSE DENTRO   

        btnVolver.setFont(fuente2.deriveFont(26f));
        btnVolver.setBounds(450, 860, 220, 60);
        btnVolver.addActionListener(e -> {
            new MenuPrincipal();
            dispose();
        });
        fondo.add(btnVolver);
    }

    /**
     * Carga desde KITSURA_DB los datos del usuario actualmente logueado
     * (Sesion.getIdUsuarioActual()) y los muestra en pantalla.
     */
    private void cargarDatosUsuario() {
        String sql = "SELECT nombre_usuario, correo, contrasena, fecha_registro, estado, imagen_perfil "
                + "FROM Usuario WHERE id_usuario = ?";

        try (Connection con = conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idUsuario);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    String nombreUsuario = rs.getString("nombre_usuario");
                    String correo = rs.getString("correo");
                    String contrasena = rs.getString("contrasena");
                    Timestamp fechaRegistro = rs.getTimestamp("fecha_registro");
                    String estado = rs.getString("estado");
                    String imagenPerfil = rs.getString("imagen_perfil");

                    txtUsuario.setText(nombreUsuario);
                    txtCorreo.setText(correo);
                    this.correoUsuario = correo;

                    txtPassword.setText(contrasena);

                    lblIdValor.setText(String.valueOf(idUsuario));

                    if (fechaRegistro != null) {
                        java.text.SimpleDateFormat formato = new java.text.SimpleDateFormat("dd/MM/yyyy");
                        lblFechaValor.setText(formato.format(fechaRegistro));
                    }

                    lblEstadoValor.setText(
                            estado != null && estado.equalsIgnoreCase("activo") ? "Activo" : "Inactivo");

                    if (imagenPerfil != null && !imagenPerfil.isBlank()) {
                        cargarImagenEnLabel(imagenPerfil);
                    } else {
                        cargarImagenEnLabel(Sesion.FOTO_PERFIL_DEFECTO);
                    }

                } else {
                    JOptionPane.showMessageDialog(this,
                            "No se encontró información para este usuario.",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this,
                    "Error al cargar los datos del usuario:\n" + e.getMessage(),
                    "Error de base de datos",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Actualiza el nombre_usuario en la base de datos.
     * @return true si se guardó correctamente, false si hubo un error.
     */
    private boolean guardarNombreUsuario(String nuevoNombre) {
        String sql = "UPDATE Usuario SET nombre_usuario = ? WHERE id_usuario = ?";

        try (Connection con = conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, nuevoNombre);
            ps.setInt(2, idUsuario);

            int filasActualizadas = ps.executeUpdate();
            
            if (filasActualizadas > 0) {
            Sesion.setNombreUsuario(nuevoNombre); // Mantiene Sesion actualizada
            }
            
            return filasActualizadas > 0;

        } catch (SQLIntegrityConstraintViolationException e) {
            JOptionPane.showMessageDialog(this,
                    "Ese nombre de usuario ya está en uso o no es válido.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this,
                    "Error al actualizar el nombre de usuario:\n" + e.getMessage(),
                    "Error de base de datos",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    /**
     * Llamado desde PantallaImagenPerfil cuando el usuario selecciona una nueva imagen.
     * Actualiza la vista y guarda la ruta seleccionada en la base de datos.
     */
    public void actualizarFotoPerfil(String rutaImagen) {
        if (!guardarImagenPerfil(rutaImagen)) {
            return; // Si falló el guardado en BD, no se actualiza la vista
        }
        cargarImagenEnLabel(rutaImagen);
        Sesion.setRutaFotoPerfil(rutaImagen); // Mantiene Sesion actualizada
        JOptionPane.showMessageDialog(this,
                "Foto de perfil actualizada correctamente.",
                "Éxito",
                JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Actualiza la columna imagen_perfil en la base de datos.
     * @return true si se guardó correctamente, false si hubo un error.
     */
    private boolean guardarImagenPerfil(String rutaImagen) {
        String sql = "UPDATE Usuario SET imagen_perfil = ? WHERE id_usuario = ?";

        try (Connection con = conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, rutaImagen);
            ps.setInt(2, idUsuario);

            int filasActualizadas = ps.executeUpdate();
            return filasActualizadas > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this,
                    "Error al actualizar la foto de perfil:\n" + e.getMessage(),
                    "Error de base de datos",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    /**
     * Carga una imagen (por ruta de resource) en lblFotoPerfil, escalada a 250x250.
     * Si el recurso no existe, se conserva/usa la imagen por defecto.
     */
    private void cargarImagenEnLabel(String ruta) {
        URL recurso = getClass().getResource(ruta);

        if (recurso == null) {
            System.err.println("No se encontró la imagen de perfil: " + ruta);
            if (!ruta.equals(Sesion.FOTO_PERFIL_DEFECTO)) {
                cargarImagenEnLabel(Sesion.FOTO_PERFIL_DEFECTO);
            }
            return;
        }

        ImageIcon icono = new ImageIcon(recurso);
        Image escalada = icono.getImage().getScaledInstance(250, 250, Image.SCALE_SMOOTH);
        lblFotoPerfil.setIcon(new ImageIcon(escalada));
        this.rutaImagenPerfilActual = ruta;
    }

    public static void main (String[] args){
        new PantallaPerfil();
    }
}   