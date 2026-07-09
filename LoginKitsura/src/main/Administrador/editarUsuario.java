package main.Administrador;

import java.awt.*;
import javax.swing.*;
import main.Menu.DecoracionBotones;
import main.Menu.FondoPanel;

// Importaciones para MySQL
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.regex.Pattern;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

public class editarUsuario extends JFrame {

    private FondoPanel fondo;
    private Font fuente1;
    private Font fuente2;

    private JPanel panelSemi;
    private JLabel lblTitulo, lblID, lblNombre, lblCorreo, lblContra, lblImagen, lblPreview, lblMascota;
    private JTextField txtID, txtNombre, txtCorreo;
    private JPasswordField txtContra;
    private JButton btnCargar, btnEditar;
    
    // Conexion MySQL
    private final String URL = "jdbc:mysql://localhost:3306/KITSURA_DB";
    private final String USER = "root";
    private final String PASSWORD = "";
    
    // ruta de la imagen 
    private String rutaImagen = "";

    public editarUsuario() {

        try {
            fuente1 = Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("/fuentes/LettersForLearners.ttf"));
            fuente2 = Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("/fuentes/KGPerfectPenmanship.ttf"));
        } catch (Exception e) {
            fuente1 = new Font("Arial", Font.PLAIN, 20);
            fuente2 = new Font("Arial", Font.PLAIN, 20);
        }

        fondo = new FondoPanel("/Multimedia/utiles/fondos/interfaces/fondoDosK.png");
        setContentPane(fondo);
        setTitle("Editar Usuario");
        setSize(1920, 1080);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        fondo.setLayout(null);

        panelSemi = new JPanel() {
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setColor(new Color(0, 0, 0, 110));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 40, 40);
                g2.dispose();
            }
        };
        panelSemi.setOpaque(false);
        panelSemi.setLayout(null);
        panelSemi.setBounds(80, 80, 1150, 900);
        fondo.add(panelSemi);

        lblTitulo = new JLabel("EDITAR USUARIO", SwingConstants.CENTER);
        lblTitulo.setFont(fuente2.deriveFont(45f));
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setBounds(300, 30, 550, 60);
        panelSemi.add(lblTitulo);

        lblID = new JLabel("Ingrese el ID del usuario:");
        lblID.setFont(fuente2.deriveFont(26f));
        lblID.setForeground(Color.WHITE);
        lblID.setBounds(50, 140, 420, 40);
        panelSemi.add(lblID);

        txtID = new JTextField();
        txtID.setFont(fuente1.deriveFont(34f));
        txtID.setBounds(50, 200, 500, 55);
        panelSemi.add(txtID);

        lblNombre = new JLabel("Ingrese el nombre nuevo:");
        lblNombre.setFont(fuente2.deriveFont(26f));
        lblNombre.setForeground(Color.WHITE);
        lblNombre.setBounds(50, 300, 420, 40);
        panelSemi.add(lblNombre);

        txtNombre = new JTextField();
        txtNombre.setFont(fuente1.deriveFont(34f));
        txtNombre.setBounds(50, 360, 500, 55);
        panelSemi.add(txtNombre);

        lblCorreo = new JLabel("Ingrese el correo nuevo:");
        lblCorreo.setFont(fuente2.deriveFont(26f));
        lblCorreo.setForeground(Color.WHITE);
        lblCorreo.setBounds(50, 470, 420, 40);
        panelSemi.add(lblCorreo);

        txtCorreo = new JTextField();
        txtCorreo.setFont(fuente1.deriveFont(34f));
        txtCorreo.setBounds(50, 530, 500, 55);
        panelSemi.add(txtCorreo);

        lblContra = new JLabel("Ingrese la contraseña nueva:");
        lblContra.setFont(fuente2.deriveFont(26f));
        lblContra.setForeground(Color.WHITE);
        lblContra.setBounds(50, 640, 450, 40);
        panelSemi.add(lblContra);

        txtContra = new JPasswordField();
        txtContra.setFont(fuente1.deriveFont(34f));
        txtContra.setBounds(50, 700, 500, 55);
        panelSemi.add(txtContra);

        btnEditar=new DecoracionBotones("EDITAR",
                //ColorBase             ColorBorde              ColorLetra
                DecoracionBotones.ROSA, DecoracionBotones.ROJO, DecoracionBotones.AMARILLO, //MOUSE FUERA
                DecoracionBotones.ROJO, DecoracionBotones.ROSA, DecoracionBotones.ROSA); //MOUSE DENTRO   
        btnEditar.setFont(fuente2.deriveFont(26f));
        btnEditar.setBounds(190, 790, 220, 65);
        btnEditar.addActionListener(e -> {
            editarUsuario();
        });
        panelSemi.add(btnEditar);

        lblImagen = new JLabel("Cargue la imagen de perfil:");
        lblImagen.setFont(fuente2.deriveFont(26f));
        lblImagen.setForeground(Color.WHITE);
        lblImagen.setBounds(660, 300, 400, 40);
        panelSemi.add(lblImagen);

        lblPreview = new JLabel();
        ImageIcon preview = new ImageIcon(getClass().getResource("/Multimedia/utiles/ImagenesPerfil/Seccion3/PE_S3_N14.png"));
        lblPreview.setIcon(new ImageIcon(preview.getImage().getScaledInstance(320, 220, Image.SCALE_SMOOTH)));
        lblPreview.setBounds(660, 360, 320, 220);
        panelSemi.add(lblPreview);

        // Boton Cargar 
        btnCargar=new DecoracionBotones("CARGAR",
                //ColorBase             ColorBorde              ColorLetra
                DecoracionBotones.ROSA, DecoracionBotones.ROJO, DecoracionBotones.AMARILLO, //MOUSE FUERA
                DecoracionBotones.ROJO, DecoracionBotones.ROSA, DecoracionBotones.ROSA); //MOUSE DENTRO  

        btnCargar.setFont(fuente2.deriveFont(26f));
        btnCargar.setBounds(720, 610, 200, 60);
        btnCargar.addActionListener(e -> {
            cargarImagen();
        });
        panelSemi.add(btnCargar);

        // Mascota
        lblMascota = new JLabel();
        ImageIcon mascota = new ImageIcon(getClass().getResource("/Multimedia/utiles/mascotaKitsura/imagen/MINIJUEGO-CONTROL.png"));
        lblMascota.setIcon(new ImageIcon(mascota.getImage().getScaledInstance(650, 650, Image.SCALE_SMOOTH)));
        lblMascota.setBounds(1280, 230, 650, 650);
        fondo.add(lblMascota);

        JButton btnVolver = new DecoracionBotones("VOLVER",
        //ColorBase             ColorBorde              ColorLetra
                DecoracionBotones.AZUL, DecoracionBotones.GRIS, DecoracionBotones.AMARILLO, //MOUSE FUERA
                DecoracionBotones.CELESTE, DecoracionBotones.AZUL, DecoracionBotones.AZUL); //MOUSE DENTRO   
        btnVolver.setFont(fuente2.deriveFont(28f));
        btnVolver.setBounds(1470, 870, 300, 65);

        btnVolver.addActionListener(e -> {
            new UsuarioMenu();
            dispose();
        });

        fondo.add(btnVolver);

        setVisible(true);
    }

        // CARGAR IMAGEN DE PERFIL
        // Permite seleccionar una imagen y mostrar una vista previa

        private void cargarImagen() {
        /* Cuando se presiona el boton Cargar se abre el explorador,
        se guarda la ruta y cambia la imagen de vista previa */
        
            JFileChooser selector = new JFileChooser();
            selector.setDialogTitle("Seleccione una imagen");
            selector.setFileFilter(

                    new FileNameExtensionFilter(
                            "Imágenes",
                            "png",
                            "jpg",
                            "jpeg"));

            int opcion = selector.showOpenDialog(this);

            if (opcion == JFileChooser.APPROVE_OPTION) {
                File archivo = selector.getSelectedFile();
                rutaImagen = archivo.getAbsolutePath();
                ImageIcon icono = new ImageIcon(rutaImagen);
                Image imagenEscalada = icono.getImage().getScaledInstance(320, 220, Image.SCALE_SMOOTH);

                lblPreview.setIcon(
                        new ImageIcon(imagenEscalada));
            }
        }
        
        /* Valida los datos ingresados y actualiza la información
         del usuario en la base de datos */

        private void editarUsuario() {

            // OBTENER LOS DATOS INGRESADOS
            String idTexto = txtID.getText().trim();
            String nombre = txtNombre.getText().trim();
            String correo = txtCorreo.getText().trim();
            String contrasena = String.valueOf(txtContra.getPassword()).trim();

            // VALIDAR QUE LOS CAMPOS NO ESTÉN VACÍOS
            if (idTexto.isEmpty() || nombre.isEmpty()
                    || correo.isEmpty() || contrasena.isEmpty()) {

                JOptionPane.showMessageDialog( this, "Complete todos los campos.");
                return;
            }

            // VALIDAR QUE EL ID SEA NUMÉRICO
            int idUsuario;
            try {
                idUsuario = Integer.parseInt(idTexto);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "El ID debe contener únicamente números.");
                return;
            }

            //------------------------------------------------------
            // VALIDAR EL FORMATO DEL CORREO
            //------------------------------------------------------
            String patronCorreo = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
            if (!Pattern.matches(patronCorreo, correo)) {
                JOptionPane.showMessageDialog(this, "Ingrese un correo válido.");
                return;
            }

            // CONECTAR A MYSQL
            try {
                Connection con = DriverManager.getConnection(
                        URL,
                        USER,
                        PASSWORD);

                // VERIFICAR SI EL USUARIO EXISTE
                String consulta =
                        "SELECT id_usuario "
                        + "FROM Usuario "
                        + "WHERE id_usuario = ?";

                PreparedStatement psBuscar =
                        con.prepareStatement(consulta);
                psBuscar.setInt(1, idUsuario);

                if (!psBuscar.executeQuery().next()) {
                    JOptionPane.showMessageDialog(
                            this,
                            "No existe un usuario con ese ID.");
                    psBuscar.close();
                    con.close();
                    return;
                }

                psBuscar.close();

                
                
                // ACTUALIZAR USUARIO
                String sql =
                        "UPDATE Usuario "
                        + "SET nombre_usuario = ?, "
                        + "correo = ?, "
                        + "contrasena = ?, "
                        + "imagen_perfil = ? "
                        + "WHERE id_usuario = ?";

                PreparedStatement ps =
                        con.prepareStatement(sql);

                ps.setString(1, nombre);
                ps.setString(2, correo);
                ps.setString(3, contrasena);
                ps.setString(4, rutaImagen);
                ps.setInt(5, idUsuario);

                int filas = ps.executeUpdate();

                if (filas > 0) {
                    JOptionPane.showMessageDialog(
                            this,
                            "Usuario actualizado correctamente.");
                    // LIMPIAR CAMPOS
                    txtID.setText("");
                    txtNombre.setText("");
                    txtCorreo.setText("");
                    txtContra.setText("");

                } else {
                    JOptionPane.showMessageDialog(
                            this,
                            "No fue posible actualizar el usuario.");
                }

                ps.close();
                con.close();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(
                        this,
                        "Error:\n" + e.getMessage());
            }
        }
    
    public static void main(String[] args) {
        new editarUsuario();
    }

}
