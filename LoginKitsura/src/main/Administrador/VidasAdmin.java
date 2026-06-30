package main.Administrador;

import java.awt.*;
import javax.swing.*;
import main.Menu.DecoracionBotones;
import main.Menu.FondoPanel;
import main.Menu.FondoPanelSemi;

public class VidasAdmin extends JFrame {

    private FondoPanel fondo;
    private Font fuente1;
    private Font fuente2;

    public VidasAdmin() {

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

        setTitle("Vidas");
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

        JLabel lblTitulo = new JLabel("VIDAS", JLabel.CENTER);
        lblTitulo.setFont(fuente2.deriveFont(45f));
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setBounds(0, 0, 1800, 75);
        panelTitulo.add(lblTitulo);

        //---------------- PANEL PRINCIPAL ----------------

        FondoPanelSemi panelVidas = new FondoPanelSemi(new Color(0, 0, 0, 130));
        panelVidas.setLayout(null);
        panelVidas.setBounds(100, 170, 950, 650);
        fondo.add(panelVidas);

        JLabel lblVidasActuales = new JLabel("Cantidad de vidas actual por este nivel:");
        lblVidasActuales.setFont(fuente2.deriveFont(26f));
        lblVidasActuales.setForeground(Color.WHITE);
        lblVidasActuales.setBounds(60, 50, 820, 35);
        panelVidas.add(lblVidasActuales);

        JLabel lblValorActual = new JLabel("****");
        lblValorActual.setFont(fuente1.deriveFont(32f));
        lblValorActual.setForeground(Color.WHITE);
        lblValorActual.setBounds(60, 95, 200, 40);
        panelVidas.add(lblValorActual);

        JLabel lblInstruccion = new JLabel("Ingrese la cantidad de corazones para este nivel:");
        lblInstruccion.setFont(fuente2.deriveFont(26f));
        lblInstruccion.setForeground(Color.WHITE);
        lblInstruccion.setBounds(60, 180, 820, 35);
        panelVidas.add(lblInstruccion);

        JTextField txtNuevasVidas = new JTextField();
        txtNuevasVidas.setFont(fuente1.deriveFont(34f));
        txtNuevasVidas.setBounds(60, 225, 830, 55);
        panelVidas.add(txtNuevasVidas);

        //---------------- BOTÓN EDITAR ----------------


        JButton btnEditar = new DecoracionBotones("EDITAR",
                //ColorBase             ColorBorde              ColorLetra
                DecoracionBotones.ROSA, DecoracionBotones.ROJO, DecoracionBotones.AMARILLO, //MOUSE FUERA
                DecoracionBotones.ROJO, DecoracionBotones.ROSA, DecoracionBotones.ROSA); //MOUSE DENTRO
        
        btnEditar.setFont(fuente2.deriveFont(26f));
        btnEditar.setBounds(340, 315, 260, 60);

        btnEditar.addActionListener(e -> {

            // Acción editar vidas

        });

        panelVidas.add(btnEditar);

        //---------------- INFORMACIÓN ----------------

        JLabel lblModificando = new JLabel("Está modificando:");
        lblModificando.setFont(fuente2.deriveFont(28f));
        lblModificando.setForeground(Color.WHITE);
        lblModificando.setBounds(60, 430, 350, 35);
        panelVidas.add(lblModificando);

        JLabel lblMinijuego = new JLabel("Minijuego: ****");
        lblMinijuego.setFont(fuente1.deriveFont(40f));
        lblMinijuego.setForeground(Color.WHITE);
        lblMinijuego.setBounds(60, 485, 600, 35);
        panelVidas.add(lblMinijuego);

        JLabel lblCategoria = new JLabel("Categoría: ****");
        lblCategoria.setFont(fuente1.deriveFont(40f));
        lblCategoria.setForeground(Color.WHITE);
        lblCategoria.setBounds(60, 525, 600, 35);
        panelVidas.add(lblCategoria);

        JLabel lblNivel = new JLabel("Nivel: ****");
        lblNivel.setFont(fuente1.deriveFont(40f));
        lblNivel.setForeground(Color.WHITE);
        lblNivel.setBounds(60, 565, 600, 35);
        panelVidas.add(lblNivel);

        //---------------- MASCOTA ----------------

        JLabel lblMascota = new JLabel();

        try {

            ImageIcon iconMascota = new ImageIcon(
                    getClass().getResource("/Multimedia/utiles/mascotaKitsura/imagen/zorro_vidas.png"));

            Image imgEscalada = iconMascota.getImage().getScaledInstance(
                    650,
                    650,
                    Image.SCALE_SMOOTH);

            lblMascota.setIcon(new ImageIcon(imgEscalada));

        } catch (Exception e) {

            lblMascota.setText("~");

        }

        lblMascota.setBounds(1100, 220, 650, 650);
        fondo.add(lblMascota);

        //---------------- BOTÓN VOLVER ----------------

        JButton btnVolver = new DecoracionBotones("VOLVER",
                                //ColorBase             ColorBorde              ColorLetra
                DecoracionBotones.AZUL, DecoracionBotones.GRIS, DecoracionBotones.AMARILLO, //MOUSE FUERA
                DecoracionBotones.CELESTE, DecoracionBotones.AZUL, DecoracionBotones.AZUL); //MOUSE DENTRO   

        btnVolver.setFont(fuente2.deriveFont(28f));
        btnVolver.setBounds(1470, 870, 300, 65);

        btnVolver.addActionListener(e ->{
                new MenuAdmin();
                dispose();
                        });

        fondo.add(btnVolver);
    }

    public static void main(String[] args) {
        new VidasAdmin();
    }
}