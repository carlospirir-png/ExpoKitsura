package main.Administrador;

import java.awt.*;
import java.net.URL;
import javax.swing.*;
import main.Menu.DecoracionBotones;
import main.Menu.FondoPanel;
import main.Menu.FondoPanelSemi;

public class UsuarioMenu extends JFrame {

    private FondoPanel fondo;
    private Font fuente1;
    private Font fuente2;

    public UsuarioMenu() {

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

        setTitle("Usuario");
        setSize(1920, 1080);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        fondo.setLayout(null);

        crearComponentes();

        setVisible(true);
    }

    private void crearComponentes() {

        //---------------- PANEL TÍTULO ----------------
        FondoPanelSemi panelTitulo = new FondoPanelSemi(new Color(0, 0, 0, 150));
        panelTitulo.setLayout(null);
        panelTitulo.setBounds(220, 105, 500, 75);
        fondo.add(panelTitulo);

        JLabel lblTitulo = new JLabel("USUARIO", JLabel.CENTER);
        lblTitulo.setFont(fuente2.deriveFont(45f));
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setBounds(0, 0, 500, 75);
        panelTitulo.add(lblTitulo);

        //---------------- PANEL IZQUIERDO ----------------
        FondoPanelSemi panelOpciones = new FondoPanelSemi(new Color(0, 0, 0, 130));
        panelOpciones.setLayout(null);
        panelOpciones.setBounds(120, 180, 700, 620);
        fondo.add(panelOpciones);

        //---------------- BOTÓN AGREGAR ADMIN ----------------
        JButton btnAgregarAdmin = new DecoracionBotones("AÑADIR ADMINISTRADOR",
                                //ColorBase             ColorBorde              ColorLetra
                DecoracionBotones.AZUL, DecoracionBotones.GRIS, DecoracionBotones.AMARILLO, //MOUSE FUERA
                DecoracionBotones.CELESTE, DecoracionBotones.AZUL, DecoracionBotones.AZUL); //MOUSE DENTRO   

        btnAgregarAdmin.setFont(fuente2.deriveFont(26f));
        btnAgregarAdmin.setBounds(120, 140, 460, 70);

        btnAgregarAdmin.addActionListener(e -> {

            new NuevoAdmin();
            dispose();

        });

        panelOpciones.add(btnAgregarAdmin);

        //---------------- BOTÓN EDITAR ----------------
        JButton btnEditar = new DecoracionBotones("EDITAR",
                                //ColorBase             ColorBorde              ColorLetra
                DecoracionBotones.AZUL, DecoracionBotones.GRIS, DecoracionBotones.AMARILLO, //MOUSE FUERA
                DecoracionBotones.CELESTE, DecoracionBotones.AZUL, DecoracionBotones.AZUL); //MOUSE DENTRO   

        btnEditar.setFont(fuente2.deriveFont(26f));
        btnEditar.setBounds(180, 290, 340, 70);

        btnEditar.addActionListener(e -> {

            new editarUsuario();
            dispose();

        });

        panelOpciones.add(btnEditar);

        //---------------- BOTÓN MOSTRAR ----------------
        JButton btnMostrar = new DecoracionBotones("MOSTRAR",
                                //ColorBase             ColorBorde              ColorLetra
                DecoracionBotones.AZUL, DecoracionBotones.GRIS, DecoracionBotones.AMARILLO, //MOUSE FUERA
                DecoracionBotones.CELESTE, DecoracionBotones.AZUL, DecoracionBotones.AZUL); //MOUSE DENTRO   

        btnMostrar.setFont(fuente2.deriveFont(26f));
        btnMostrar.setBounds(180, 440, 340, 70);

        btnMostrar.addActionListener(e -> {

            new UsuarioMostrar();
            dispose();

        });

        panelOpciones.add(btnMostrar);

        //---------------- MASCOTA ----------------
        JLabel lblMascota = new JLabel();

        try {

            ImageIcon mascotaIcon = new ImageIcon(
                    getClass().getResource("/Multimedia/utiles/mascotaKitsura/imagen/REGISTRARSE_USUARIO-PARTIDA_MINIJUEGO-TABLETA.png"));

            Image mascotaEscalada = mascotaIcon.getImage().getScaledInstance(
                    700,
                    700,
                    Image.SCALE_SMOOTH);

            lblMascota.setIcon(new ImageIcon(mascotaEscalada));

        } catch (Exception e) {

            lblMascota.setText("~");

        }

        lblMascota.setBounds(1080, 180, 700, 700);
        fondo.add(lblMascota);

        //---------------- BOTÓN VOLVER ----------------
        JButton btnVolver = new DecoracionBotones("VOLVER",
                                //ColorBase             ColorBorde              ColorLetra
                DecoracionBotones.AZUL, DecoracionBotones.GRIS, DecoracionBotones.AMARILLO, //MOUSE FUERA
                DecoracionBotones.CELESTE, DecoracionBotones.AZUL, DecoracionBotones.AZUL); //MOUSE DENTRO   

        btnVolver.setFont(fuente2.deriveFont(28f));
        btnVolver.setBounds(1470, 870, 300, 65);

        btnVolver.addActionListener(e -> {
            new MenuAdmin();
            dispose();
                });

        fondo.add(btnVolver);
    }

    public static void main(String[] args) {
        new UsuarioMenu();
    }
}