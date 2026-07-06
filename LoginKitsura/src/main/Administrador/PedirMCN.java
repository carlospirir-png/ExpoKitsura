package main.Administrador;

import java.awt.*;
import java.util.List;
import javax.swing.*;
import main.Menu.FondoPanel;
import main.Menu.DecoracionBotones;
import main.Menu.FondoPanelSemi;
import main.Administrador.DatosConfiguracion;
import main.Usuario.IniciarSesion;

public class PedirMCN extends JFrame {

    private FondoPanel fondo;
    private Font fuente1;
    private Font fuente2;

    // DAO encargado de traer los valores reales desde la base de datos
    // para llenar los combos (en vez de que el admin los escriba a mano).
    private SeleccionDAO_Vidas seleccionDAO;

    // Se reemplazan los antiguos JTextField por JComboBox: el administrador
    // ahora ELIGE de una lista cargada desde la BD, en vez de escribir texto
    // libre. Así se elimina el riesgo de nombres mal escritos (ej. "HiddenFox"
    // en vez de "Hidden Fox") que hacían fallar la comparación en VidasDAO.
    private JComboBox<String> cbMinijuego;
    private JComboBox<String> cbCategoria;
    private JComboBox<String> cbNivel;

    // Bandera para evitar que, mientras se están recargando los combos por
    // código, sus propios listeners se disparen en cadena innecesariamente.
    private boolean cargandoCombos = false;

    public PedirMCN() {
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

        seleccionDAO = new SeleccionDAO_Vidas();

        fondo = new FondoPanel("/Multimedia/utiles/fondos/interfaces/fondoTresK.png");
        setContentPane(fondo);
        setTitle("Pedir M, C, N");
        setSize(1980, 1080);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        fondo.setLayout(null);
        crearComponentes();
        setVisible(true);
    }

    private void crearComponentes() {
        FondoPanelSemi recuadroFormulario = new FondoPanelSemi(new Color(0, 0, 0, 120));
        recuadroFormulario.setBounds(900, 160, 900, 650);
        recuadroFormulario.setLayout(null);
        fondo.add(recuadroFormulario);

        JLabel lblTituloCentral = new JLabel("Minijuegos, Categoría y Nivel", JLabel.LEFT);
        lblTituloCentral.setFont(fuente2.deriveFont(35f));
        lblTituloCentral.setForeground(Color.decode("#82D3E0"));
        lblTituloCentral.setBounds(80, 40, 540, 50);
        recuadroFormulario.add(lblTituloCentral);

        // ---------------- MINIJUEGO ----------------
        JLabel lblMinijuego = new JLabel("Seleccione el minijuego a modificar:");
        lblMinijuego.setFont(fuente2.deriveFont(27f));
        lblMinijuego.setForeground(Color.WHITE);
        lblMinijuego.setBounds(80, 160, 700, 30);
        recuadroFormulario.add(lblMinijuego);

        cbMinijuego = new JComboBox<>();
        cbMinijuego.setFont(fuente1.deriveFont(25f));
        cbMinijuego.setBounds(80, 205, 700, 45);
        recuadroFormulario.add(cbMinijuego);

        // ---------------- CATEGORÍA ----------------
        JLabel lblCategoria = new JLabel("Seleccione la categoría a modificar:");
        lblCategoria.setFont(fuente2.deriveFont(27f));
        lblCategoria.setForeground(Color.WHITE);
        lblCategoria.setBounds(80, 290, 700, 30);
        recuadroFormulario.add(lblCategoria);

        cbCategoria = new JComboBox<>();
        cbCategoria.setFont(fuente1.deriveFont(25f));
        cbCategoria.setBounds(80, 335, 700, 45);
        recuadroFormulario.add(cbCategoria);

        // ---------------- NIVEL (DIFICULTAD) ----------------
        JLabel lblNivel = new JLabel("Seleccione el nivel a modificar:");
        lblNivel.setFont(fuente2.deriveFont(27f));
        lblNivel.setForeground(Color.WHITE);
        lblNivel.setBounds(80, 420, 700, 30);
        recuadroFormulario.add(lblNivel);

        cbNivel = new JComboBox<>();
        cbNivel.setFont(fuente1.deriveFont(25f));
        cbNivel.setBounds(80, 465, 700, 45);
        recuadroFormulario.add(cbNivel);

        // ---------------- LISTENERS EN CASCADA ----------------
        // Al cambiar el minijuego seleccionado, se recargan sus categorías.
        cbMinijuego.addActionListener(e -> {
            if (!cargandoCombos) {
                cargarCategorias();
            }
        });

        // Al cambiar la categoría seleccionada, se recargan sus dificultades.
        cbCategoria.addActionListener(e -> {
            if (!cargandoCombos) {
                cargarNiveles();
            }
        });

        // Se cargan los minijuegos al abrir la ventana; esto dispara en cadena
        // la carga de categorías y luego de niveles para dejar todo listo.
        cargarMinijuegos();

        // BOTÓN CONTINUAR
        JButton btnContinuar = new DecoracionBotones("CONTINUAR",
                //ColorBase             ColorBorde              ColorLetra
                DecoracionBotones.ROSA, DecoracionBotones.ROJO, DecoracionBotones.AMARILLO, //MOUSE FUERA
                DecoracionBotones.ROJO, DecoracionBotones.ROSA, DecoracionBotones.ROSA); //MOUSE DENTRO
        btnContinuar.setFont(fuente2.deriveFont(20f));
        btnContinuar.setBounds(560, 560, 220, 55);
        recuadroFormulario.add(btnContinuar);

        // Ahora se toman los valores SELECCIONADOS en los combos (garantizados
        // por la BD), en vez de texto escrito a mano.
        btnContinuar.addActionListener(e -> {

            String minijuego = (String) cbMinijuego.getSelectedItem();
            String categoria = (String) cbCategoria.getSelectedItem();
            String nivel = (String) cbNivel.getSelectedItem();

            if (minijuego == null || categoria == null || nivel == null) {
                JOptionPane.showMessageDialog(
                        this,
                        "No hay datos suficientes en la base de datos para continuar.",
                        "Selección incompleta",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            DatosConfiguracion datos = new DatosConfiguracion(minijuego, categoria, nivel);
            new MenuAdmin(datos);
            dispose();
        });

        // MASCOTA
        JLabel mascotaControl = new JLabel();
        ImageIcon iconMascota = new ImageIcon(getClass().getResource("/Multimedia/utiles/mascotaKitsura/imagen/REGISTRARSE_USUARIO-PARTIDA_MINIJUEGO-TABLETA.png"));
        Image imgEscalada = iconMascota.getImage().getScaledInstance(650, 650, Image.SCALE_SMOOTH);
        mascotaControl.setIcon(new ImageIcon(imgEscalada));
        mascotaControl.setBounds(200, 180, 650, 650);
        fondo.add(mascotaControl);

        // BOTÓN REGRESAR
        JButton btnRegresar = new DecoracionBotones("REGRESAR",
                //ColorBase             ColorBorde              ColorLetra
                DecoracionBotones.AZUL, DecoracionBotones.GRIS, DecoracionBotones.AMARILLO, //MOUSE FUERA
                DecoracionBotones.CELESTE, DecoracionBotones.AZUL, DecoracionBotones.AZUL); //MOUSE DENTRO   

        btnRegresar.setFont(fuente2.deriveFont(20f));
        btnRegresar.setBounds(380, 800, 220, 55);
        btnRegresar.addActionListener(e -> {
            new IniciarSesion();
            dispose();
                });
        fondo.add(btnRegresar);
    }

    /*------------------ CARGA EN CASCADA ------------------
      cargarMinijuegos() -> dispara cargarCategorias() del primer minijuego
      cargarCategorias() -> dispara cargarNiveles() de la primera categoría
      cargarNiveles()    -> deja las dificultades listas para elegir
    --------------------------------------------------------*/
    private void cargarMinijuegos() {
        cargandoCombos = true;

        cbMinijuego.removeAllItems();

        List<String> minijuegos = seleccionDAO.obtenerMinijuegos();
        for (String nombre : minijuegos) {
            cbMinijuego.addItem(nombre);
        }

        cargandoCombos = false;

        // Con el primer minijuego ya seleccionado por defecto, se cargan
        // inmediatamente sus categorías (y estas, a su vez, sus niveles).
        cargarCategorias();
    }

    private void cargarCategorias() {
        cargandoCombos = true;

        cbCategoria.removeAllItems();

        String minijuegoSeleccionado = (String) cbMinijuego.getSelectedItem();

        if (minijuegoSeleccionado != null) {
            List<String> categorias = seleccionDAO.obtenerCategorias(minijuegoSeleccionado);
            for (String nombre : categorias) {
                cbCategoria.addItem(nombre);
            }
        }

        cargandoCombos = false;

        cargarNiveles();
    }

    private void cargarNiveles() {
        cargandoCombos = true;

        cbNivel.removeAllItems();

        String minijuegoSeleccionado = (String) cbMinijuego.getSelectedItem();
        String categoriaSeleccionada = (String) cbCategoria.getSelectedItem();

        if (minijuegoSeleccionado != null && categoriaSeleccionada != null) {
            List<String> dificultades = seleccionDAO.obtenerDificultades(minijuegoSeleccionado, categoriaSeleccionada);
            for (String dificultad : dificultades) {
                cbNivel.addItem(dificultad);
            }
        }

        cargandoCombos = false;
    }

    public static void main(String[] args) {
        new PedirMCN();
    }

}