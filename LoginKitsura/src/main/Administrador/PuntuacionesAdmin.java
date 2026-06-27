package main.Administrador;

import java.awt.*;
import javax.swing.*;
import main.Menu.DecoracionBotones;
import main.Menu.FondoPanel;
import main.Menu.FondoPanelSemi;

public class PuntuacionesAdmin extends JFrame {

    private FondoPanel fondo;
    private Font fuente1;
    private Font fuente2;

    public PuntuacionesAdmin() {

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

        fondo = new FondoPanel("/Multimedia/utiles/fondos/interfaces/fondoTresK.png");
        setContentPane(fondo);

        setTitle("Puntuaciones");
        setSize(1980, 1080);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        fondo.setLayout(null);

        crearComponentes();

        setVisible(true);
    }

    private void crearComponentes() {

        //---------------- PANEL TÍTULO ----------------

        FondoPanelSemi panelTitulo = new FondoPanelSemi(new Color(0, 0, 0, 150));
        panelTitulo.setLayout(null);
        panelTitulo.setBounds(90, 65, 1800, 75);
        fondo.add(panelTitulo);

        JLabel lblTitulo = new JLabel("PUNTUACIÓN", JLabel.CENTER);
        lblTitulo.setFont(fuente2.deriveFont(45f));
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setBounds(0, 0, 1800, 75);
        panelTitulo.add(lblTitulo);

        //---------------- PANEL PRINCIPAL ----------------

        FondoPanelSemi panelPuntuacion = new FondoPanelSemi(new Color(0, 0, 0, 130));
        panelPuntuacion.setLayout(null);
        panelPuntuacion.setBounds(100, 170, 950, 650);
        fondo.add(panelPuntuacion);

        JLabel lblPuntosActuales = new JLabel("Puntuación actual por este nivel:");
        lblPuntosActuales.setFont(fuente2.deriveFont(26f));
        lblPuntosActuales.setForeground(Color.WHITE);
        lblPuntosActuales.setBounds(60, 50, 800, 35);
        panelPuntuacion.add(lblPuntosActuales);

        JLabel lblValorActual = new JLabel("****");
        lblValorActual.setFont(fuente1.deriveFont(32f));
        lblValorActual.setForeground(Color.WHITE);
        lblValorActual.setBounds(60, 95, 200, 40);
        panelPuntuacion.add(lblValorActual);

        JLabel lblInstruccion = new JLabel("Ingrese la nueva puntuación:");
        lblInstruccion.setFont(fuente2.deriveFont(26f));
        lblInstruccion.setForeground(Color.WHITE);
        lblInstruccion.setBounds(60, 180, 800, 35);
        panelPuntuacion.add(lblInstruccion);

        JTextField txtNuevaPuntuacion = new JTextField();
        txtNuevaPuntuacion.setFont(fuente1.deriveFont(34f));
        txtNuevaPuntuacion.setBounds(60, 225, 830, 55);
        panelPuntuacion.add(txtNuevaPuntuacion);

        //---------------- BOTÓN EDITAR ----------------

        JButton btnEditar = new DecoracionBotones("EDITAR");
        btnEditar.setFont(fuente2.deriveFont(26f));
        btnEditar.setBounds(340, 315, 260, 60);

        btnEditar.addActionListener(e -> {

            // Acción editar puntuación

        });

        panelPuntuacion.add(btnEditar);

        //---------------- INFORMACIÓN ----------------

        JLabel lblModificando = new JLabel("Está modificando:");
        lblModificando.setFont(fuente2.deriveFont(28f));
        lblModificando.setForeground(Color.WHITE);
        lblModificando.setBounds(60, 430, 400, 35);
        panelPuntuacion.add(lblModificando);

        JLabel lblMinijuego = new JLabel("Minijuego: ****");
        lblMinijuego.setFont(fuente1.deriveFont(28f));
        lblMinijuego.setForeground(Color.WHITE);
        lblMinijuego.setBounds(60, 485, 600, 35);
        panelPuntuacion.add(lblMinijuego);

        JLabel lblCategoria = new JLabel("Categoría: ****");
        lblCategoria.setFont(fuente1.deriveFont(28f));
        lblCategoria.setForeground(Color.WHITE);
        lblCategoria.setBounds(60, 525, 600, 35);
        panelPuntuacion.add(lblCategoria);

        JLabel lblNivel = new JLabel("Nivel: ****");
        lblNivel.setFont(fuente1.deriveFont(28f));
        lblNivel.setForeground(Color.WHITE);
        lblNivel.setBounds(60, 565, 600, 35);
        panelPuntuacion.add(lblNivel);

        //---------------- MASCOTA ----------------

        JLabel mascota = new JLabel();

        try {

            ImageIcon iconMascota = new ImageIcon(
                    getClass().getResource("/Multimedia/utiles/mascotaKitsura/imagen/REGISTRARSE_USUARIO-PARTIDA_MINIJUEGO-TABLETA.png"));

            Image img = iconMascota.getImage().getScaledInstance(
                    550,
                    550,
                    Image.SCALE_SMOOTH);

            mascota.setIcon(new ImageIcon(img));

        } catch (Exception e) {

            mascota.setText("~");

        }

        mascota.setBounds(1320, 220, 550, 550);
        fondo.add(mascota);

        //---------------- BOTÓN VOLVER ----------------

        JButton btnVolver = new DecoracionBotones("VOLVER");
        btnVolver.setFont(fuente2.deriveFont(28f));
        btnVolver.setBounds(1470, 870, 300, 65);

        btnVolver.addActionListener(e -> dispose());

        fondo.add(btnVolver);

    }

    public static void main(String[] args) {
        new PuntuacionesAdmin();
    }

}