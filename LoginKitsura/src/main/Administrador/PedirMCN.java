package main.Administrador;

import java.awt.*;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import javax.swing.*;
import main.Menu.FondoPanel;
import main.Menu.DecoracionBotones;
import main.Menu.FondoPanelSemi;
import main.Usuario.IniciarSesion;
import main.conexion.Conexion;

public class PedirMCN extends JFrame {

    private FondoPanel fondo;
    private Font fuente1;
    private Font fuente2;

    private SeleccionDAO_Vidas seleccionDAO;

    private JComboBox<String> cbMinijuego;
    private JComboBox<String> cbCategoria;
    private JComboBox<String> cbNivel;

    private boolean cargandoCombos = false;

    private String ventanaAbrir;

    public PedirMCN(String ventanaAbrir) {

        this.ventanaAbrir = ventanaAbrir;

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

        seleccionDAO = new SeleccionDAO_Vidas();

        fondo = new FondoPanel("/Multimedia/utiles/fondos/interfaces/fondoTresK.png");
        setContentPane(fondo);
        //------------- ÍCONO ------------------
        //se obtiene la imagen del logo con getResource
        URL iconUrl = getClass().getResource("/Multimedia/utiles/logotipo/logofK.png");

        //se instancia el ícono con la imagen
        ImageIcon icono = new ImageIcon(iconUrl);

        //Se coloca el ícono al JFrame
        setIconImage(icono.getImage());

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
        cbMinijuego.addActionListener(e -> {
            if (!cargandoCombos) {
                cargarCategorias();
            }
        });

        cbCategoria.addActionListener(e -> {
            if (!cargandoCombos) {
                cargarNiveles();
            }
        });

        cargarMinijuegos();

        // BOTÓN CONTINUAR
        JButton btnContinuar = new DecoracionBotones("CONTINUAR",
                DecoracionBotones.ROSA, DecoracionBotones.ROJO, DecoracionBotones.AMARILLO,
                DecoracionBotones.ROJO, DecoracionBotones.ROSA, DecoracionBotones.ROSA);
        btnContinuar.setFont(fuente2.deriveFont(20f));
        btnContinuar.setBounds(560, 560, 220, 55);
        recuadroFormulario.add(btnContinuar);

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

            switch (ventanaAbrir) {
                case "Puntuaciones":
                    new PuntuacionesAdmin(datos);
                    dispose();
                    break;
                case "Vidas":
                    new VidasAdmin(datos);
                    dispose();
                    break;
                case "Tiempo":
                    new TiempoAdmin(datos);
                    dispose();
                    break;
                case "Pistas":
                    new PistasMenu(datos);
                    dispose();
                    break;
                case "Crear Stage":
                    // Viene del botón "CREAR UNO" de AdminStages: abre directo
                    // la pantalla de creación (M1/M2/M3) según el minijuego elegido.
                    abrirCreacionSegunMinijuego(datos);
                    dispose();
                    break;
                case "Administrar Stages":
                    // Viene del botón "EDITAR UNO EXISTENTE" de AdminStages:
                    // muestra la tabla de preguntas ya guardadas para elegir cuál editar.
                    new EditarStages(datos);
                    dispose();
                    break;
                default:
                    JOptionPane.showMessageDialog(null, "Pedir MCN no sabe que ventana debe abrir.", "Error en abrir Interfaz", JOptionPane.ERROR_MESSAGE);
            }

        });

        // MASCOTA
        JLabel mascotaControl = new JLabel();
        ImageIcon iconMascota = new ImageIcon(getClass().getResource("/Multimedia/utiles/mascotaKitsura/imagen/PAPEL_INSTRUCCIONES.png"));
        Image imgEscalada = iconMascota.getImage().getScaledInstance(650, 650, Image.SCALE_SMOOTH);
        mascotaControl.setIcon(new ImageIcon(imgEscalada));
        mascotaControl.setBounds(200, 180, 650, 650);
        fondo.add(mascotaControl);

        // BOTÓN REGRESAR
        JButton btnRegresar = new DecoracionBotones("REGRESAR",
                DecoracionBotones.AZUL, DecoracionBotones.GRIS, DecoracionBotones.AMARILLO,
                DecoracionBotones.CELESTE, DecoracionBotones.AZUL, DecoracionBotones.AZUL);

        btnRegresar.setFont(fuente2.deriveFont(20f));
        btnRegresar.setBounds(380, 800, 220, 55);
        btnRegresar.addActionListener(e -> {
            new MenuAdmin();
            dispose();
        });
        fondo.add(btnRegresar);
    }

    /**
     * Resuelve, a partir de (minijuego, categoría, nivel) elegidos en los
     * combos, cuál es el id_minijuego real en la BD, y abre DIRECTO la
     * pantalla de creación correspondiente:
     *   id_minijuego 1 -> M1_crearNuevo (Hidden Fox)
     *   id_minijuego 2 -> M2_crearNuevo (Verdadero/Falso)
     *   id_minijuego 3 -> M3_crearNuevo (Opción múltiple)
     * No se muestra ninguna tabla intermedia: se va directo a crear.
     */
    private void abrirCreacionSegunMinijuego(DatosConfiguracion datos) {
        String sql = "SELECT m.id_minijuego, c.id_categoria, cn.dificultad "
                + "FROM Configuracion_nivel cn "
                + "JOIN Categoria c ON cn.id_categoria = c.id_categoria "
                + "JOIN Minijuego m ON c.id_minijuego = m.id_minijuego "
                + "WHERE m.nombre = ? AND c.nombre = ? AND cn.dificultad = ?";

        try (Connection con = new Conexion().getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {

            if (con == null) {
                throw new SQLException("No se pudo establecer conexión con la base de datos.");
            }

            ps.setString(1, datos.getMinijuego());
            ps.setString(2, datos.getCategoria());
            ps.setString(3, datos.getNivel());

            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) {
                    throw new SQLException("No existe un nivel para: "
                            + datos.getMinijuego() + " / " + datos.getCategoria() + " / " + datos.getNivel());
                }

                int idMinijuego = rs.getInt("id_minijuego");
                int idCategoriaGlobal = rs.getInt("id_categoria");
                // Cada minijuego tiene 3 categorías consecutivas (1,2,3 / 4,5,6 / 7,8,9)
                int idCategoriaLocal = ((idCategoriaGlobal - 1) % 3) + 1;
                int dificultadNumero = switch (rs.getString("dificultad")) {
                    case "Fácil" ->
                        1;
                    case "Intermedio" ->
                        2;
                    case "Difícil" ->
                        3;
                    default ->
                        throw new SQLException("Dificultad desconocida: '" + rs.getString("dificultad") + "'.");
                };

                switch (idMinijuego) {
                    case 1 ->
                        new M1_crearNuevo(datos, null);
                    case 2 ->
                        new M2_crearNuevo(datos);
                    case 3 ->
                        new M3_crearNuevo(idCategoriaLocal, dificultadNumero);
                    default ->
                        JOptionPane.showMessageDialog(this,
                                "Minijuego no reconocido (id_minijuego = " + idMinijuego + ").",
                                "Error", JOptionPane.WARNING_MESSAGE);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this,
                    "No se pudo determinar el nivel para la selección actual:\n" + ex.getMessage(),
                    "Error de configuración", JOptionPane.ERROR_MESSAGE);
        }
    }

    /*------------------ CARGA EN CASCADA ------------------*/
    private void cargarMinijuegos() {
        cargandoCombos = true;

        cbMinijuego.removeAllItems();

        List<String> minijuegos = seleccionDAO.obtenerMinijuegos();
        for (String nombre : minijuegos) {
            cbMinijuego.addItem(nombre);
        }

        cargandoCombos = false;

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

}