package main.Administrador;
// Interfaz de Añadir Admin
import java.awt.*;
import javax.swing.*;
import main.Menu.FondoPanelSemi;
import main.Menu.DecoracionBotones;

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

    public NuevoAdmin() {
        try{
            // LettersForLearners
            fuente1 = Font.createFont(
            Font.TRUETYPE_FONT,
            getClass().getResourceAsStream("/fuentes/LettersForLearners.ttf"));
            // KGPerfectPenmanship
            fuente2 = Font.createFont(
            Font.TRUETYPE_FONT,
            getClass().getResourceAsStream("/fuentes/KGPerfectPenmanship.ttf"));
            
        } catch (Exception e){
            e.printStackTrace();            
            fuente1 = new Font("Arial", Font.PLAIN,20);
            fuente2 = new Font("Arial", Font.PLAIN,20);
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

        panelSemi = new FondoPanelSemi(new Color(0, 0, 0, 120));
        panelSemi.setLayout(null);
        panelSemi.setBounds(120, 100, 1000, 800);

        fondo.add(panelSemi);

        lblTitulo = new JLabel("AÑADIR ADMINISTRADOR", SwingConstants.CENTER);
        lblTitulo.setFont(fuente2.deriveFont(42f));
        lblTitulo.setForeground(Color.decode("#82D3E0"));
        lblTitulo.setBounds(50, 40, 900, 70);

        panelSemi.add(lblTitulo);

        lblNombre = new JLabel("Ingrese el nombre del nuevo administrador:");
        lblNombre.setFont(fuente2.deriveFont(26f));
        lblNombre.setForeground(Color.WHITE);
        lblNombre.setBounds(90, 170, 700, 45);
        panelSemi.add(lblNombre);

        txtNombre = new JTextField();
        txtNombre.setFont(fuente1.deriveFont(30f));
        txtNombre.setBounds(90, 235, 810, 55); // 

        panelSemi.add(txtNombre);

        lblCorreo = new JLabel("Ingrese el correo del nuevo administrador:");
        lblCorreo.setFont(fuente2.deriveFont(26f));
        lblCorreo.setForeground(Color.WHITE);
        lblCorreo.setBounds(90, 370, 750, 45);

        panelSemi.add(lblCorreo);

        txtCorreo = new JTextField();
        txtCorreo.setFont(fuente1.deriveFont(30f));
        txtCorreo.setBounds(90, 435, 810, 55);

        panelSemi.add(txtCorreo);

        lblContra = new JLabel("Ingrese la contraseña del nuevo administrador:");
        lblContra.setFont(fuente2.deriveFont(26f));
        lblContra.setForeground(Color.WHITE);
        lblContra.setBounds(90, 570, 810, 45);

        panelSemi.add(lblContra);

        txtContra = new JPasswordField();
        txtContra.setFont(fuente1.deriveFont(30f));
        txtContra.setBounds(90, 635, 810, 55);

        panelSemi.add(txtContra);

        btnAnadir = new DecoracionBotones("AÑADIR", "#FC767D", "#da4d58", "#da4d58");
        btnAnadir.setFont(fuente2.deriveFont(25f));
        btnAnadir.setForeground(Color.WHITE);
        btnAnadir.setBounds(350, 730, 300, 70);
        fondo.add(btnAnadir);
        panelSemi.add(btnAnadir);

        lblMascota = new JLabel();

        ImageIcon mascotaIcon =
                new ImageIcon(
                        getClass().getResource("/Multimedia/utiles/mascotaKitsura/imagen/MINIJUEGO-CONTROL.png"));

        Image mascotaEscalada =
                mascotaIcon.getImage().getScaledInstance(
                        800,
                        800,
                        Image.SCALE_SMOOTH);

        lblMascota.setIcon(new ImageIcon(mascotaEscalada));
        lblMascota.setBounds(1080, 120, 800, 800);

        fondo.add(lblMascota);
        
        btnSalir = new DecoracionBotones("VOLVER");
        btnSalir.setFont(fuente2.deriveFont(30f));
        btnSalir.setForeground(Color.WHITE);
        btnSalir.setBounds(1695, 950, 210, 45);
        btnSalir.addActionListener(e -> dispose());
        fondo.add(btnSalir);
    }
  
    public static void main(String[] args) {
        new NuevoAdmin();
    }
}