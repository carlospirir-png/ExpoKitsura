package main.Administrador;

import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import main.Menu.FondoPanelSemi;
import main.Menu.DecoracionBotones;
import main.conexion.Conexion;

public class EditarStages extends JFrame {

    private FondoPanelSemi fondo;
    private Font fuente1, fuente2;
    private JPanel panelSemi;
    private JLabel lblTitulo;
    private JLabel lblSubtitulo;
    private JLabel lblPreguntas;
    private JTable tablaPreguntas;
    private JScrollPane scrollPreguntas;
    private DecoracionBotones btnConfirmar;
    private DecoracionBotones btnSalir;

    // Referencia a la ventana de edición ya abierta (Minijuego 1), para
    // reutilizarla si el usuario selecciona otra fila sin cerrar la ventana
    private M1_crearNuevo ventanaEdicion;

    // Selección hecha previamente en PedirMCN (minijuego, categoría, nivel)
    private final DatosConfiguracion datos;

    // Contexto resuelto en la BD a partir de "datos"
    private int idMinijuego;
    private int idCategoriaLocal; // 1, 2 o 3 (posición de la categoría dentro del minijuego)
    private int idNivel;
    private int dificultadNumero; // 1=Fácil, 2=Intermedio, 3=Difícil

    public EditarStages(DatosConfiguracion datos) {
        this.datos = datos;

        inicializarFuentes();

        fondo = new FondoPanelSemi("/Multimedia/utiles/fondos/interfaces/fondoDosK.png");
        setContentPane(fondo);

        setTitle("Editar Stages");
        setSize(1920, 1080);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        fondo.setLayout(null);

        // Sin selección previa de Pedir M,C,N no hay contexto seguro que
        // mostrar: se oculta todo (no se abre la ventana).
        if (datos == null) {
            JOptionPane.showMessageDialog(null,
                    "Debes seleccionar primero el Minijuego, Categoría y Nivel en 'Pedir M, C, N'.",
                    "Falta selección", JOptionPane.WARNING_MESSAGE);
            dispose();
            return;
        }

        try {
            resolverContextoDesdeDatos();
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null,
                    "No se pudo determinar el nivel para la selección hecha en 'Pedir M, C, N':\n"
                    + ex.getMessage(),
                    "Error de configuración", JOptionPane.ERROR_MESSAGE);
            dispose();
            return;
        }

        crearComponentes();
        cargarPreguntas();

        setVisible(true);
    }

    private void inicializarFuentes() {
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
    }

    /**
     * Traduce (minijuego, categoría, dificultad) elegidos en PedirMCN hacia
     * id_minijuego, id_categoria (local 1-3 dentro del minijuego), id_nivel y
     * dificultad numérica, uniendo Minijuego -> Categoria -> Configuracion_nivel.
     * Este es el único contexto que la ventana va a mostrar/permitir editar.
     */
    private void resolverContextoDesdeDatos() throws SQLException {
        String sql = "SELECT m.id_minijuego, c.id_categoria, cn.id_nivel, cn.dificultad "
                + "FROM Configuracion_nivel cn "
                + "JOIN Categoria c ON cn.id_categoria = c.id_categoria "
                + "JOIN Minijuego m ON c.id_minijuego = m.id_minijuego "
                + "WHERE m.nombre = ? AND c.nombre = ? AND cn.dificultad = ?";

        try (Connection con = new Conexion().getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {

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
                this.idMinijuego = rs.getInt("id_minijuego");
                int idCategoriaGlobal = rs.getInt("id_categoria");
                // Cada minijuego tiene exactamente 3 categorías insertadas de forma
                // consecutiva (1,2,3 / 4,5,6 / 7,8,9), así que el índice LOCAL es:
                this.idCategoriaLocal = ((idCategoriaGlobal - 1) % 3) + 1;
                this.idNivel = rs.getInt("id_nivel");
                this.dificultadNumero = mapearDificultadANumero(rs.getString("dificultad"));
            }
        }
    }

    private int mapearDificultadANumero(String dificultad) throws SQLException {
        if (dificultad == null) {
            throw new SQLException("La dificultad llegó vacía desde la base de datos.");
        }
        return switch (dificultad) {
            case "Fácil" -> 1;
            case "Intermedio" -> 2;
            case "Difícil" -> 3;
            default -> throw new SQLException("Dificultad desconocida: '" + dificultad + "'.");
        };
    }

    private void crearComponentes() {

        panelSemi = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setColor(new Color(0, 0, 0, 120));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 40, 40);
                g2.dispose();
            }
        };

        panelSemi.setOpaque(false);
        panelSemi.setLayout(null);
        panelSemi.setBounds(180, 140, 1560, 760);

        fondo.add(panelSemi);

        lblTitulo = new JLabel("EDITAR EXISTENTE", SwingConstants.CENTER);
        lblTitulo.setFont(fuente2.deriveFont(48f));
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setBounds(0, 25, 1560, 55);
        panelSemi.add(lblTitulo);

        lblSubtitulo = new JLabel(
                datos.getMinijuego() + "  -  " + datos.getCategoria() + "  -  " + datos.getNivel(),
                SwingConstants.CENTER);
        lblSubtitulo.setFont(fuente2.deriveFont(24f));
        lblSubtitulo.setForeground(Color.decode("#82D3E0"));
        lblSubtitulo.setBounds(0, 85, 1560, 35);
        panelSemi.add(lblSubtitulo);

        lblPreguntas = new JLabel("Preguntas disponibles para editar:");
        lblPreguntas.setFont(fuente2.deriveFont(24f));
        lblPreguntas.setForeground(Color.WHITE);
        lblPreguntas.setBounds(60, 135, 700, 35);
        panelSemi.add(lblPreguntas);

        tablaPreguntas = new JTable();
        tablaPreguntas.setFont(fuente1.deriveFont(18f));
        tablaPreguntas.setRowHeight(32);
        tablaPreguntas.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tablaPreguntas.getTableHeader().setFont(fuente2.deriveFont(18f));

        scrollPreguntas = new JScrollPane(tablaPreguntas);
        scrollPreguntas.setBounds(60, 180, 1440, 460);
        panelSemi.add(scrollPreguntas);

        btnConfirmar = new DecoracionBotones("EDITAR SELECCIONADA",
                //ColorBase             ColorBorde              ColorLetra
                DecoracionBotones.ROSA, DecoracionBotones.ROJO, DecoracionBotones.AMARILLO_APAGADO, //MOUSE FUERA
                DecoracionBotones.ROJO, DecoracionBotones.ROSA, DecoracionBotones.ROSA); //MOUSE DENTRO

        btnConfirmar.setFont(fuente2.deriveFont(22f));
        btnConfirmar.setBounds(560, 665, 440, 55);
        btnConfirmar.addActionListener(e -> abrirEdicionDePreguntaSeleccionada());
        panelSemi.add(btnConfirmar);

        btnSalir = new DecoracionBotones("VOLVER",
                //ColorBase             ColorBorde              ColorLetra
                DecoracionBotones.AZUL, DecoracionBotones.GRIS, DecoracionBotones.AMARILLO, //MOUSE FUERA
                DecoracionBotones.CELESTE, DecoracionBotones.AZUL, DecoracionBotones.AZUL); //MOUSE DENTRO

        btnSalir.setFont(fuente2.deriveFont(20F));
        btnSalir.setBounds(1680, 950, 210, 45);
        btnSalir.addActionListener(e -> 
                new PedirMCN("Datos"));
        fondo.add(btnSalir);
    }

   /**
     * Carga en la tabla ÚNICAMENTE las preguntas que pertenecen al id_nivel
     * resuelto desde la selección hecha en Pedir M,C,N, junto con su
     * respuesta correcta (la que tiene es_correcta = TRUE en Opcion_respuesta).
     * Cualquier pregunta de otro minijuego, categoría o dificultad queda
     * fuera y nunca se muestra.
     */
    private void cargarPreguntas() {
        String sql = "SELECT p.id_pregunta, p.pregunta, o.texto_opcion AS respuesta_correcta "
                + "FROM Pregunta p "
                + "LEFT JOIN Opcion_respuesta o "
                + "       ON o.id_pregunta = p.id_pregunta AND o.es_correcta = TRUE "
                + "WHERE p.id_nivel = ? AND p.estado = 'activo' "
                + "ORDER BY p.id_pregunta";

        DefaultTableModel modelo = new DefaultTableModel(new Object[]{"ID", "Pregunta", "Respuesta correcta"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        try (Connection con = new Conexion().getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {

            if (con == null) {
                throw new SQLException("No se pudo establecer conexión con la base de datos.");
            }

            ps.setInt(1, idNivel);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    modelo.addRow(new Object[]{
                        rs.getInt("id_pregunta"),
                        rs.getString("pregunta"),
                        rs.getString("respuesta_correcta") // puede venir null si aún no tiene opciones cargadas
                    });
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this,
                    "Error al cargar las preguntas: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }

        tablaPreguntas.setModel(modelo);

        // Ajuste de anchos para que la columna de pregunta/respuesta se lea bien
        tablaPreguntas.getColumnModel().getColumn(0).setPreferredWidth(60);
        tablaPreguntas.getColumnModel().getColumn(1).setPreferredWidth(800);
        tablaPreguntas.getColumnModel().getColumn(2).setPreferredWidth(400);

        if (modelo.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this,
                    "Todavía no hay preguntas guardadas para "
                    + datos.getMinijuego() + " / " + datos.getCategoria() + " / " + datos.getNivel() + ".",
                    "Sin preguntas", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    
    private void abrirEdicionDePreguntaSeleccionada() {
        int fila = tablaPreguntas.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this,
                    "Selecciona una pregunta de la tabla antes de continuar.",
                    "Ninguna selección", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int idPregunta = (int) tablaPreguntas.getValueAt(fila, 0);
        abrirInterfazSegunMinijuego(idPregunta);
    }

    /**
     * Abre la interfaz de edición del minijuego ya resuelto (a partir de la
     * selección hecha en Pedir M,C,N). Nunca se ofrece la posibilidad de
     * editar preguntas de otro minijuego/categoría/nivel.
     */
    private void abrirInterfazSegunMinijuego(int idPregunta) {
        switch (idMinijuego) {
            case 1 -> {
                if (ventanaEdicion != null && ventanaEdicion.isDisplayable()) {
                    ventanaEdicion.editar(idPregunta);
                    ventanaEdicion.toFront();
                    ventanaEdicion.requestFocus();
                } else {
                    ventanaEdicion = new M1_crearNuevo(idPregunta);
                }
            }
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

    public static void main(String[] args) {
        // Prueba rápida: simula una selección hecha en PedirMCN
        new EditarStages(new DatosConfiguracion("Hidden Fox", "Animales", "Fácil"));
    }
}