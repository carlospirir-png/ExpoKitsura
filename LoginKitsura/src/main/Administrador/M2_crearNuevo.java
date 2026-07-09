package main.Administrador;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.*;
import javax.swing.border.Border;
import main.Menu.FondoPanelSemi;
import main.Menu.DecoracionBotones;
import main.conexion.Conexion;

public class M2_crearNuevo extends JFrame {

    private FondoPanelSemi fondo;
    private FondoPanelSemi panelSemi;
    private Font fuente1;
    private Font fuente2;
    private JLabel lblTitulo;

    private JLabel lblPregunta;
    private JTextArea txtPregunta;

    private JLabel lblSeleccion;

    private DecoracionBotones btnVerdadero;
    private DecoracionBotones btnFalso;

    // Guarda cuál botón está seleccionado actualmente (true = Verdadero, false = Falso, null = ninguno)
    private Boolean respuestaEsVerdadera = null;

    // Colores fijos usados para "bloquear" el color del botón seleccionado,
    // sin necesidad de tocar la clase DecoracionBotones.
    private static final Color COLOR_VERDE = Color.decode(DecoracionBotones.VERDE);
    private static final Color COLOR_ROJO = Color.decode(DecoracionBotones.ROJO);
    private static final Color COLOR_GRIS = Color.decode(DecoracionBotones.GRIS);
    private static final Color COLOR_NEGRO = Color.decode(DecoracionBotones.NEGRO);
    private static final Color COLOR_BLANCO = Color.decode(DecoracionBotones.BLANCO);

    private JButton btnSiguiente;
    private DecoracionBotones btnSalir;

    private JLabel lblMascota;

    // Datos elegidos previamente en PedirMCN (minijuego, categoría, nivel/dificultad)
    private final DatosConfiguracion datos;

    // id_nivel resuelto en la BD a partir de "datos" (FK real hacia Configuracion_nivel)
    private final int idNivel;

    // Constructor real: recibe lo que ya seleccionó el admin en PedirMCN
    public M2_crearNuevo(DatosConfiguracion datos) {
        this.datos = datos;

        int idResuelto;
        try {
            idResuelto = resolverIdNivel(datos);
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,
                    "No se pudo determinar el nivel en la base de datos:\n" + e.getMessage(),
                    "Error de configuración",
                    JOptionPane.ERROR_MESSAGE);
            idResuelto = -1;
        }
        this.idNivel = idResuelto;

        inicializarVentana();
    }

    // Constructor de respaldo solo para pruebas rápidas (main), simulando una selección de PedirMCN
    public M2_crearNuevo() {
        this(new DatosConfiguracion("Fox Jump!", "Animales", "Fácil"));
    }

    /*------------------------------------------------------------------
      Traduce (minijuego, categoría, dificultad) -> id_nivel real,
      uniendo Minijuego -> Categoria -> Configuracion_nivel.
    -------------------------------------------------------------------*/
    private int resolverIdNivel(DatosConfiguracion datos) throws SQLException {
        String sql = "SELECT cn.id_nivel "
                + "FROM Configuracion_nivel cn "
                + "JOIN Categoria c ON cn.id_categoria = c.id_categoria "
                + "JOIN Minijuego m ON c.id_minijuego = m.id_minijuego "
                + "WHERE m.nombre = ? AND c.nombre = ? AND cn.dificultad = ?";

        try (Connection con = new Conexion().getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, datos.getMinijuego());
            ps.setString(2, datos.getCategoria());
            ps.setString(3, datos.getNivel());

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("id_nivel");
                } else {
                    throw new SQLException("No existe un nivel para: "
                            + datos.getMinijuego() + " / "
                            + datos.getCategoria() + " / "
                            + datos.getNivel());
                }
            }
        }
    }

    private void inicializarVentana() {
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
        fondo = new FondoPanelSemi("/Multimedia/utiles/fondos/interfaces/fondoDosK.png");
        setContentPane(fondo);

        setTitle("Crear Nuevo - Minijuego 2");
        setSize(1920, 1080);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        fondo.setLayout(null);

        crearComponentes();

        setVisible(true);
    }

    private void crearComponentes() {

        panelSemi = new FondoPanelSemi(new Color(220, 220, 220, 220));
        panelSemi.setLayout(null);
        panelSemi.setBounds(50, 50, 1200, 850);

        fondo.add(panelSemi);

        lblTitulo = new JLabel("CREAR NUEVO (MINIJUEGO 2)");
        lblTitulo.setFont(fuente2.deriveFont(40f));
        lblTitulo.setForeground(Color.decode("#447A9C"));
        lblTitulo.setBounds(40, 40, 700, 50);

        panelSemi.add(lblTitulo);

        lblPregunta = new JLabel("Ingrese la pregunta:");
        lblPregunta.setFont(fuente2.deriveFont(28f));
        lblPregunta.setBounds(50, 120, 350, 35);

        panelSemi.add(lblPregunta);

        txtPregunta = new JTextArea();
        txtPregunta.setFont(fuente1.deriveFont(30f));
        txtPregunta.setLineWrap(true);
        txtPregunta.setWrapStyleWord(true);
        txtPregunta.setBounds(50, 170, 1050, 320);

        panelSemi.add(txtPregunta);

        // --- ETIQUETA DE SELECCIÓN ---
        lblSeleccion = new JLabel("Selecciona la respuesta correcta:");
        lblSeleccion.setFont(fuente2.deriveFont(28f));
        lblSeleccion.setForeground(Color.decode("#447A9C"));
        lblSeleccion.setBounds(50, 530, 600, 35);
        panelSemi.add(lblSeleccion);

        // --- BOTÓN VERDADERO ---
        btnVerdadero = new DecoracionBotones("VERDADERO",
                //ColorBase             ColorBorde              ColorLetra
                DecoracionBotones.GRIS, DecoracionBotones.NEGRO, DecoracionBotones.BLANCO, //MOUSE FUERA (sin seleccionar = gris)
                DecoracionBotones.VERDE_SUAVE, DecoracionBotones.VERDE, DecoracionBotones.BLANCO); //MOUSE DENTRO
        btnVerdadero.setFont(fuente2.deriveFont(24f));
        btnVerdadero.setBounds(110, 580, 380, 90);
        btnVerdadero.addActionListener(e -> seleccionarRespuesta(true));
        // Listener EXTRA (agregado después del interno de DecoracionBotones):
        // como los listeners se disparan en orden de registro, este se ejecuta
        // después del que ya trae el botón y "gana" la pelea, re-fijando el
        // color verde si el botón está seleccionado, sin tocar la clase.
        btnVerdadero.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                if (Boolean.TRUE.equals(respuestaEsVerdadera)) {
                    fijarColorBoton(btnVerdadero, COLOR_VERDE, COLOR_NEGRO, COLOR_BLANCO);
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if (Boolean.TRUE.equals(respuestaEsVerdadera)) {
                    fijarColorBoton(btnVerdadero, COLOR_VERDE, COLOR_NEGRO, COLOR_BLANCO);
                }
            }
        });
        panelSemi.add(btnVerdadero);

        // --- BOTÓN FALSO ---
        btnFalso = new DecoracionBotones("FALSO",
                //ColorBase             ColorBorde              ColorLetra
                DecoracionBotones.GRIS, DecoracionBotones.NEGRO, DecoracionBotones.BLANCO, //MOUSE FUERA (sin seleccionar = gris)
                DecoracionBotones.ROSA, DecoracionBotones.ROJO, DecoracionBotones.BLANCO); //MOUSE DENTRO
        btnFalso.setFont(fuente2.deriveFont(24f));
        btnFalso.setBounds(680, 580, 380, 90);
        btnFalso.addActionListener(e -> seleccionarRespuesta(false));
        // Mismo truco que arriba, pero para el color rojo.
        btnFalso.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                if (Boolean.FALSE.equals(respuestaEsVerdadera)) {
                    fijarColorBoton(btnFalso, COLOR_ROJO, COLOR_NEGRO, COLOR_BLANCO);
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if (Boolean.FALSE.equals(respuestaEsVerdadera)) {
                    fijarColorBoton(btnFalso, COLOR_ROJO, COLOR_NEGRO, COLOR_BLANCO);
                }
            }
        });
        panelSemi.add(btnFalso);

        btnSiguiente = new DecoracionBotones("SIGUIENTE",
                //ColorBase             ColorBorde              ColorLetra
                DecoracionBotones.ROSA, DecoracionBotones.ROJO, DecoracionBotones.AMARILLO, //MOUSE FUERA
                DecoracionBotones.ROJO, DecoracionBotones.ROSA, DecoracionBotones.ROSA); //MOUSE DENTRO
        btnSiguiente.setFont(fuente2.deriveFont(18f));
        btnSiguiente.setBounds(450, 700, 300, 60);
        btnSiguiente.addActionListener(e -> validarYGuardar());
        fondo.add(btnSiguiente);

        panelSemi.add(btnSiguiente);

        lblMascota = new JLabel();

        ImageIcon mascotaIcon
                = new ImageIcon(
                        getClass().getResource("/Multimedia/utiles/mascotaKitsura/imagen/REGLAS-PISTA_TEXTUAL.png"));

        Image mascotaEscalada
                = mascotaIcon.getImage().getScaledInstance(
                        650,
                        650,
                        Image.SCALE_SMOOTH);

        lblMascota.setIcon(new ImageIcon(mascotaEscalada));
        lblMascota.setBounds(1280, 180, 650, 650);

        fondo.add(lblMascota);

        // --- BOTÓN VOLVER---
        btnSalir = new DecoracionBotones("VOLVER",
                //ColorBase             ColorBorde              ColorLetra
                DecoracionBotones.AZUL, DecoracionBotones.GRIS, DecoracionBotones.AMARILLO, //MOUSE FUERA
                DecoracionBotones.CELESTE, DecoracionBotones.AZUL, DecoracionBotones.AZUL); //MOUSE DENTRO   

        btnSalir.setFont(fuente2.deriveFont(18f));
        btnSalir.setBounds(1695, 950, 210, 45);
        btnSalir.addActionListener(e -> dispose());
        fondo.add(btnSalir);
    }

    /*------------------------------------------------------------------
      Aplica un color fijo (fondo, borde, texto) a un botón, replicando
      el mismo estilo de borde "pixel art" que usa DecoracionBotones,
      sin necesidad de modificar esa clase.
    -------------------------------------------------------------------*/
    private void fijarColorBoton(DecoracionBotones boton, Color colorFondo, Color colorBorde, Color colorTexto) {
        boton.setBackground(colorFondo);
        boton.setForeground(colorTexto);

        Border borde = BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(4, 4, 4, 4, colorBorde),
                BorderFactory.createEmptyBorder(6, 14, 6, 14));
        boton.setBorder(borde);

        boton.repaint();
    }

    /*------------------------------------------------------------------
      Marca cuál botón (Verdadero/Falso) representa la respuesta correcta.
      El botón elegido queda fijo en su color (verde/rojo) y el otro
      vuelve a su color normal (gris).
    -------------------------------------------------------------------*/
    private void seleccionarRespuesta(boolean esVerdadera) {
        respuestaEsVerdadera = esVerdadera;

        if (esVerdadera) {
            fijarColorBoton(btnVerdadero, COLOR_VERDE, COLOR_NEGRO, COLOR_BLANCO);
            btnFalso.mouseFuera(); // vuelve al gris normal
        } else {
            fijarColorBoton(btnFalso, COLOR_ROJO, COLOR_NEGRO, COLOR_BLANCO);
            btnVerdadero.mouseFuera(); // vuelve al gris normal
        }
    }

    /*------------------------------------------------------------------
      Valida los campos y, si todo está correcto, guarda la pregunta
      en la base de datos.
    -------------------------------------------------------------------*/
    private void validarYGuardar() {

        if (idNivel <= 0) {
            JOptionPane.showMessageDialog(this,
                    "No se pudo determinar el nivel (id_nivel) para esta pregunta.\n"
                    + "Vuelve a la selección de Minijuego / Categoría / Nivel.",
                    "Configuración inválida",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        String pregunta = txtPregunta.getText().trim();

        if (pregunta.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Por favor ingresa la pregunta.",
                    "Campo incompleto",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (respuestaEsVerdadera == null) {
            JOptionPane.showMessageDialog(this,
                    "Selecciona si la respuesta correcta es Verdadero o Falso.",
                    "Selección requerida",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        boolean exito = guardarPreguntaEnBD(pregunta, respuestaEsVerdadera);

        if (exito) {
            int opcion = JOptionPane.showConfirmDialog(this,
                    "¡Pregunta guardada correctamente! ¿Deseas agregar otra pregunta?",
                    "Guardado exitoso",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.INFORMATION_MESSAGE);

            if (opcion == JOptionPane.YES_OPTION) {
                limpiarFormulario();
            } else {
                dispose();
            }
        }
    }

    /*------------------------------------------------------------------
      Inserta la pregunta en la tabla Pregunta y sus dos opciones
      (Verdadero/Falso) en Opcion_respuesta, usando una transacción
      para que ambas inserciones se hagan juntas o ninguna.
    -------------------------------------------------------------------*/
    private boolean guardarPreguntaEnBD(String pregunta, boolean esVerdadera) {
        String sqlPregunta = "INSERT INTO Pregunta (id_nivel, pregunta) VALUES (?, ?)";
        String sqlOpcion = "INSERT INTO Opcion_respuesta (id_pregunta, texto_opcion, es_correcta) VALUES (?, ?, ?)";

        Connection con = null;

        try {
            con = new Conexion().getConnection();

            if (con == null) {
                throw new SQLException("getConnection() devolvió null. Revisa la conexión a MySQL.");
            }

            con.setAutoCommit(false);

            int idPreguntaGenerado;

            // 1) Insertar la pregunta
            try (PreparedStatement psPregunta = con.prepareStatement(
                    sqlPregunta, Statement.RETURN_GENERATED_KEYS)) {

                psPregunta.setInt(1, idNivel);
                psPregunta.setString(2, pregunta);
                psPregunta.executeUpdate();

                try (ResultSet rs = psPregunta.getGeneratedKeys()) {
                    if (rs.next()) {
                        idPreguntaGenerado = rs.getInt(1);
                    } else {
                        throw new SQLException("No se pudo obtener el id_pregunta generado.");
                    }
                }
            }

            // 2) Insertar las dos opciones de respuesta (Verdadero / Falso)
            try (PreparedStatement psOpcion = con.prepareStatement(sqlOpcion)) {

                psOpcion.setInt(1, idPreguntaGenerado);
                psOpcion.setString(2, "Verdadero");
                psOpcion.setBoolean(3, esVerdadera);
                psOpcion.addBatch();

                psOpcion.setInt(1, idPreguntaGenerado);
                psOpcion.setString(2, "Falso");
                psOpcion.setBoolean(3, !esVerdadera);
                psOpcion.addBatch();

                psOpcion.executeBatch();
            }

            con.commit();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();

            if (con != null) {
                try {
                    con.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }

            JOptionPane.showMessageDialog(this,
                    "Ocurrió un error al guardar la pregunta:\n" + e.getMessage(),
                    "Error de base de datos",
                    JOptionPane.ERROR_MESSAGE);
            return false;

        } finally {
            if (con != null) {
                try {
                    con.setAutoCommit(true);
                    con.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /*------------------------------------------------------------------
      Limpia el formulario para ingresar una nueva pregunta sin
      cerrar la ventana.
    -------------------------------------------------------------------*/
    private void limpiarFormulario() {
        txtPregunta.setText("");
        respuestaEsVerdadera = null;
        btnVerdadero.mouseFuera();  
        btnFalso.mouseFuera();
    }

    public static void main(String[] args) {
        new M2_crearNuevo();
    }
}