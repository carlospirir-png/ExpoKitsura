package main.Administrador;

import java.awt.*;
import javax.swing.*;
import main.Menu.DecoracionBotones;
import main.Menu.FondoPanel;
import main.Menu.FondoPanelSemi;

public class TiempoAdmin extends JFrame {

    private FondoPanel fondo;
    private Font fuente1;
    private Font fuente2;

    public TiempoAdmin() {

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

        fondo = new FondoPanel("/Multimedia/utiles/fondos/interfaces/fondoTresK.png");
        setContentPane(fondo);

        setTitle("Tiempo");
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

        JLabel lblTitulo = new JLabel("TIEMPO", JLabel.CENTER);
        lblTitulo.setFont(fuente2.deriveFont(45f));
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setBounds(0, 0, 1800, 75);
        panelTitulo.add(lblTitulo);

        //---------------- PANEL PRINCIPAL ----------------

        FondoPanelSemi panelTiempo = new FondoPanelSemi(new Color(0, 0, 0, 130));
        panelTiempo.setLayout(null);
        panelTiempo.setBounds(100, 170, 950, 650);
        fondo.add(panelTiempo);

        JLabel lblTiempoActual = new JLabel("Cantidad de tiempo actual por este nivel:");
        lblTiempoActual.setFont(fuente2.deriveFont(26f));
        lblTiempoActual.setForeground(Color.WHITE);
        lblTiempoActual.setBounds(60, 50, 820, 35);
        panelTiempo.add(lblTiempoActual);

        JLabel lblValorActual = new JLabel("****");
        lblValorActual.setFont(fuente1.deriveFont(32f));
        lblValorActual.setForeground(Color.WHITE);
        lblValorActual.setBounds(60, 95, 200, 40);
        panelTiempo.add(lblValorActual);

        JLabel lblInstruccion = new JLabel("Ingrese la nueva cantidad de tiempo:");
        lblInstruccion.setFont(fuente2.deriveFont(26f));
        lblInstruccion.setForeground(Color.WHITE);
        lblInstruccion.setBounds(60, 180, 820, 35);
        panelTiempo.add(lblInstruccion);

        JTextField txtNuevoTiempo = new JTextField();
        txtNuevoTiempo.setFont(fuente1.deriveFont(34f));
        txtNuevoTiempo.setBounds(60, 225, 830, 55);
        panelTiempo.add(txtNuevoTiempo);

        //---------------- BOTÓN EDITAR ----------------

        JButton btnEditar = new DecoracionBotones("EDITAR",
                                //ColorBase             ColorBorde              ColorLetra
                DecoracionBotones.AZUL, DecoracionBotones.GRIS, DecoracionBotones.AMARILLO, //MOUSE FUERA
                DecoracionBotones.CELESTE, DecoracionBotones.AZUL, DecoracionBotones.AZUL); //MOUSE DENTRO   

        btnEditar.setFont(fuente2.deriveFont(26f));
        btnEditar.setBounds(340, 315, 260, 60);

        btnEditar.addActionListener(e -> {

            // Acción editar tiempo

        });

        panelTiempo.add(btnEditar);

        //---------------- INFORMACIÓN ----------------

        JLabel lblModificando = new JLabel("Está modificando:");
        lblModificando.setFont(fuente2.deriveFont(28f));
        lblModificando.setForeground(Color.WHITE);
        lblModificando.setBounds(60, 430, 350, 35);
        panelTiempo.add(lblModificando);

        JLabel lblMinijuego = new JLabel("Minijuego: ****");
        lblMinijuego.setFont(fuente1.deriveFont(28f));
        lblMinijuego.setForeground(Color.WHITE);
        lblMinijuego.setBounds(60, 485, 600, 35);
        panelTiempo.add(lblMinijuego);

        JLabel lblCategoria = new JLabel("Categoría: ****");
        lblCategoria.setFont(fuente1.deriveFont(28f));
        lblCategoria.setForeground(Color.WHITE);
        lblCategoria.setBounds(60, 525, 600, 35);
        panelTiempo.add(lblCategoria);

        JLabel lblNivel = new JLabel("Nivel: ****");
        lblNivel.setFont(fuente1.deriveFont(28f));
        lblNivel.setForeground(Color.WHITE);
        lblNivel.setBounds(60, 565, 600, 35);
        panelTiempo.add(lblNivel);

        //---------------- MASCOTA ----------------

        JLabel lblMascota = new JLabel();

        try {

            ImageIcon iconMascota = new ImageIcon(
                    getClass().getResource("/Multimedia/utiles/mascotaKitsura/imagen/zorro_tiempo.png"));

            Image imgEscalada = iconMascota.getImage().getScaledInstance(
                    700,
                    700,
                    Image.SCALE_SMOOTH);

            lblMascota.setIcon(new ImageIcon(imgEscalada));

        } catch (Exception e) {

            lblMascota.setText("~");

        }

        lblMascota.setBounds(1100, 200, 700, 700);
        fondo.add(lblMascota);

        //---------------- BOTÓN VOLVER ----------------

        JButton btnVolver = new DecoracionBotones("VOLVER",
                                //ColorBase             ColorBorde              ColorLetra
                DecoracionBotones.AZUL, DecoracionBotones.GRIS, DecoracionBotones.AMARILLO, //MOUSE FUERA
                DecoracionBotones.CELESTE, DecoracionBotones.AZUL, DecoracionBotones.AZUL); //MOUSE DENTRO   

        btnVolver.setFont(fuente2.deriveFont(28f));
        btnVolver.setBounds(1470, 870, 300, 65);

        btnVolver.addActionListener(e -> dispose());

        fondo.add(btnVolver);
    }

    public static void main(String[] args) {
        new TiempoAdmin();
    }
}