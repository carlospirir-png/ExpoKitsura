package main.Administrador;

import java.awt.*;
import javax.swing.*;
import main.Menu.DecoracionBotones;
import main.Menu.FondoPanel;

public class editarUsuario extends JFrame {

    private FondoPanel fondo;
    private Font fuente1;
    private Font fuente2;

    private JPanel panelSemi;
    private JLabel lblTitulo, lblID, lblNombre, lblCorreo, lblContra, lblImagen, lblPreview, lblMascota;
    private JTextField txtID, txtNombre, txtCorreo;
    private JPasswordField txtContra;
    private JButton btnCargar, btnEditar;

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


        btnCargar=new DecoracionBotones("CARGAR",
                //ColorBase             ColorBorde              ColorLetra
                DecoracionBotones.ROSA, DecoracionBotones.ROJO, DecoracionBotones.AMARILLO, //MOUSE FUERA
                DecoracionBotones.ROJO, DecoracionBotones.ROSA, DecoracionBotones.ROSA); //MOUSE DENTRO  

        btnCargar.setFont(fuente2.deriveFont(26f));
        btnCargar.setBounds(720, 610, 200, 60);
        panelSemi.add(btnCargar);

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

    public static void main(String[] args) {
        new editarUsuario();
    }

}
