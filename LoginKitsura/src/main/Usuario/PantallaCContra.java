//--------------------------- PANTALLA CAMBIAR CONTRSEÑA ----------------------
//Paquete
package main.Usuario;

//Imports
import java.awt.*; //Java AWT
import java.net.URL;
import java.sql.*; //SQL
import javax.swing.*; //Java Swing
import main.Menu.DecoracionBotones;
import main.Menu.FondoPanelSemi; //Panel
import main.conexion.Conexion; //La conexión

//Clase de Cambiar Contraseña
public class PantallaCContra extends JFrame {

    //-------------- A T R I B U T O S ----------------
    //-------------- CONEXIÓN
    private Connection con = new Conexion().getConnection();

    //-------------- FONDO PANEL
    private FondoPanelSemi fondo;
    private FondoPanelSemi panelSemi;

    //-------------- JLABEL
    private JLabel lblTitulo;
    private JLabel lblActual;
    private JLabel lblNueva;
    private JLabel lblOlvido;
    private JLabel lblMascota;
    private JLabel lblLogo;

    //-------------- JPASSWORD
    private JPasswordField txtActual;
    private JPasswordField txtNueva;

    //-------------- BUTTON
    private JButton btnAceptar;
    
    //------------- FUENTE
    private Font fuente1;
    private Font fuente2;
    
    //------------- STRING
    private String correo;
    
    //---------------- C O N S T R U C T O R -------------
    public PantallaCContra(String correo) {
        //------------- INICIALIZAR -------------
        
        //Se recibe en los parámetros el correo.
        //Se inicializa el correo, ya que nos servirá como clave para identificar al usuario
        this.correo = correo;
        
        //------------- TRY - CATCH -------------
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

        //----------------- JFRAME -----------------
        fondo = new FondoPanelSemi("/Multimedia/utiles/fondos/interfaces/fondoDosK.png");
        setContentPane(fondo);
        
        //------------- ÍCONO ------------------
        //se obtiene la imagen del logo con getResource
        URL iconUrl = getClass().getResource("/Multimedia/utiles/logotipo/logofK.png");

        //se instancia el ícono con la imagen
        ImageIcon icono = new ImageIcon(iconUrl);

        //Se coloca el ícono al JFrame
        setIconImage(icono.getImage());

        setTitle("Editar Contraseña");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        fondo.setLayout(null);
        
        //------------------ COMPONENTES ---------
        crearComponentes();

        setVisible(true);
    }
    
    //------------------- M É T O D O S -----------------
    //---------------- CREAR COMPONENTES ----------------
    private void crearComponentes() {
        
        //--------------- PANEL SEMI -----------------
        panelSemi = new FondoPanelSemi(new Color(118, 169, 170, 200));
        panelSemi.setLayout(null);
        panelSemi.setBounds(10, 10, 330, 340);

        fondo.add(panelSemi);

        //--------------- L A B E L S
        //--------------- TÍTULO
        lblTitulo = new JLabel("Editar Contraseña");
        lblTitulo.setFont(fuente2.deriveFont(28f));
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setBounds(40, 15, 565, 30);

        panelSemi.add(lblTitulo);

        //-------------- CONTRASEÑA ACTUAL
        lblActual = new JLabel("Ingrese la contraseña actual:");
        lblActual.setFont(fuente1.deriveFont(28f));
        lblActual.setForeground(Color.WHITE);
        lblActual.setBounds(20, 60, 280, 30);

        panelSemi.add(lblActual);

        //------------- CONTRASEÑA NUEVA
        lblNueva = new JLabel("Ingrese la contraseña nueva:");
        lblNueva.setFont(fuente1.deriveFont(28f));
        lblNueva.setForeground(Color.WHITE);
        lblNueva.setBounds(20, 170, 300, 30);

        panelSemi.add(lblNueva);
        
        //--------------- T X T
        //-------------- TXT CONTRASEÑA ACTUAL
        txtActual = new JPasswordField();
        txtActual.setBounds(20, 105, 280, 35);
        txtActual.setBackground(Color.WHITE);
        txtActual.setForeground(Color.BLACK);

        panelSemi.add(txtActual);

        //--------------- TXT CONTRASEÑA NUEVA
        txtNueva = new JPasswordField();
        txtNueva.setBounds(20, 215, 280, 35);
        txtNueva.setBackground(Color.WHITE);
        txtNueva.setForeground(Color.BLACK);

        panelSemi.add(txtNueva);
        
        //---------------- B O T O N E S ----------------
        //---------------- ACEPTAR
        btnAceptar = new DecoracionBotones("ACEPTAR",
                                //ColorBase             ColorBorde              ColorLetra
                DecoracionBotones.VERDE, DecoracionBotones.GRIS, DecoracionBotones.AMARILLO, //MOUSE FUERA
                DecoracionBotones.VERDE_SUAVE, DecoracionBotones.VERDE, DecoracionBotones.VERDE); //MOUSE DENTRO 
        btnAceptar.setFont(fuente1.deriveFont(28f));
        btnAceptar.setForeground(Color.WHITE);
        btnAceptar.setFocusPainted(false);
        btnAceptar.setBounds(95, 265, 140, 42);

        btnAceptar.addActionListener(e -> compararContrasena());

        panelSemi.add(btnAceptar);
        
        
        //------------------ M A S C O T A -----------------

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
    }
    
    
    //------------ O B T E N E R    C O N T R A S E Ñ A ------------
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
    
    //--------------- C O M P A R A R   C O N T R A S E Ñ A ------------
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
    
    //--------------- C A M B I A R     C O N T R A S E Ñ A -------------
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
            } else {
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
