package main.Administrador;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

import main.conexion.Conexion;

import javax.swing.table.TableColumn;
import javax.swing.table.TableRowSorter;


import main.Menu.FondoPanel;
import main.Menu.FondoPanelSemi;
import main.Menu.DecoracionBotones;
import main.conexion.Conexion;

public class PistasAudioAdmin extends JFrame {

    private FondoPanel fondo;
    private Font fuente1;
    private Font fuente2;

    private JTextField txtIdPista;
    private JTable tablaPistas;
    private DefaultTableModel modelo;
    private TableRowSorter<DefaultTableModel> sorter;

    private JLabel lblArchivoActual;
    private JButton btnGuardar;
    private JButton btnEliminar;
    private JButton btnCancelarEdicion;

    private String rutaAudioSeleccionado = "";


   // CONEXIÓN A LA BASE DE DATOS
    private Conexion conexion = new Conexion();

    // ---------------- CONTROL DE MODO (AÑADIR / EDITAR) ----------------
    private boolean modoEdicion = false;
    private int idAyudaSeleccionado = -1;   // PK real de la fila (tabla Ayuda)
    private String audioActualEdicion = ""; // ruta ya guardada en BD, usada si no se elige un nuevo archivo al editar

    // Nivel real (id_nivel) resuelto a partir de la selección hecha en PedirMCN
    // (Minijuego + Categoría + Nivel/Dificultad). Todo el CRUD de esta pantalla
    // queda restringido a las preguntas que pertenecen a este nivel.
    private int idNivelActual = -1;

    private DatosConfiguracion datos;

    public PistasAudioAdmin(DatosConfiguracion datos) {

        this.datos = datos;

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

        // ---------------- RESOLVER MCN (Minijuego / Categoría / Nivel) ----------------
        idNivelActual = resolverIdNivel();

        if (idNivelActual == -1) {
            JOptionPane.showMessageDialog(
                    null,
                    "No se pudo identificar el nivel seleccionado (Minijuego/Categoría/Dificultad).",
                    "Error de selección",
                    JOptionPane.ERROR_MESSAGE);
            new PistasMenu(datos);
            dispose();
            return;
        }

        fondo = new FondoPanel("/Multimedia/utiles/fondos/interfaces/fondoTresK.png");

        setContentPane(fondo);
        setTitle("Pistas: Audio");
        setSize(1980, 1080);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        fondo.setLayout(null);

        crearComponentes();

        // ---------------- VALIDAR QUE EXISTAN PISTAS DE AUDIO EN ESTE NIVEL ----------------
        int totalPistasAudio = contarPistasAudioEnNivel(idNivelActual);

        if (totalPistasAudio == 0) {
            JOptionPane.showMessageDialog(
                    this,
                    "No existen pistas de audio registradas para este nivel.",
                    "Sin pistas de audio",
                    JOptionPane.INFORMATION_MESSAGE);
            new MenuAdmin();
            dispose();
            return;
        }

        cargarDatosDesdeBD();

        setVisible(true);
    }

    private void crearComponentes() {

        //---------------- PANEL TÍTULO ----------------
        FondoPanelSemi panelTitulo = new FondoPanelSemi(new Color(0, 0, 0, 150));
        panelTitulo.setLayout(null);
        panelTitulo.setBounds(90, 65, 1800, 75);

        fondo.add(panelTitulo);

        JLabel lblTituloSeccion = new JLabel("PISTAS: AUDIO", JLabel.CENTER);

        lblTituloSeccion.setFont(fuente2.deriveFont(45f));
        lblTituloSeccion.setForeground(Color.WHITE);
        lblTituloSeccion.setBounds(0, 0, 1800, 75);

        panelTitulo.add(lblTituloSeccion);

        //---------------- PANEL IZQUIERDO ----------------
        // Se aumenta el alto de 420 a 630 para albergar el nuevo panel interno
        FondoPanelSemi panelIzquierdo = new FondoPanelSemi(new Color(0, 0, 0, 130));

        panelIzquierdo.setLayout(null);
        panelIzquierdo.setBounds(100, 200, 500, 630);

        fondo.add(panelIzquierdo);

        JLabel lblSubirAudio = new JLabel("Suba el Audio de la pista");

        lblSubirAudio.setFont(fuente2.deriveFont(28f));
        lblSubirAudio.setForeground(Color.WHITE);
        lblSubirAudio.setBounds(30, 40, 440, 40);

        panelIzquierdo.add(lblSubirAudio);

        JPanel panelOndaAudio = new JPanel();

        panelOndaAudio.setBackground(Color.DARK_GRAY);
        panelOndaAudio.setBounds(30, 100, 440, 90);
        panelOndaAudio.setLayout(new BorderLayout());

        JLabel lblIconoOnda = new JLabel(" ||||||ıııııı||||||ıııııı||||||", JLabel.CENTER);

        lblIconoOnda.setFont(new Font("Arial", Font.BOLD, 22));
        lblIconoOnda.setForeground(Color.WHITE);

        panelOndaAudio.add(lblIconoOnda, BorderLayout.CENTER);

        panelIzquierdo.add(panelOndaAudio);

        //---------------- LABEL ARCHIVO ACTUAL (MOSTRAR) ----------------
        lblArchivoActual = new JLabel("Ningún archivo seleccionado");

        lblArchivoActual.setFont(fuente1.deriveFont(18f));
        lblArchivoActual.setForeground(Color.LIGHT_GRAY);
        lblArchivoActual.setBounds(30, 195, 440, 30);

        panelIzquierdo.add(lblArchivoActual);

        //---------------- BOTÓN CARGAR AUDIO ----------------
        JButton btnCargar = new DecoracionBotones(
                "CARGAR",
                DecoracionBotones.ROSA,
                DecoracionBotones.ROJO,
                DecoracionBotones.AMARILLO,
                DecoracionBotones.ROJO,
                DecoracionBotones.ROSA,
                DecoracionBotones.ROSA);

        btnCargar.setFont(fuente2.deriveFont(26f));
        btnCargar.setBounds(130, 250, 240, 65);

        panelIzquierdo.add(btnCargar);

        //---------------- PANEL DE OPERACIONES (BAJO CARGAR) ----------------
        FondoPanelSemi panelOperaciones = new FondoPanelSemi(new Color(0, 0, 0, 100));
        panelOperaciones.setLayout(null);
        panelOperaciones.setBounds(30, 320, 440, 270);
        panelIzquierdo.add(panelOperaciones);

        // Botón Buscar
        JButton btnBuscar = new DecoracionBotones(
                "BUSCAR",
                DecoracionBotones.AZUL, DecoracionBotones.CELESTE, DecoracionBotones.AMARILLO,
                DecoracionBotones.CELESTE, DecoracionBotones.AZUL, DecoracionBotones.AZUL);
        btnBuscar.setFont(fuente2.deriveFont(24f));
        btnBuscar.setBounds(50, 25, 340, 55);
        panelOperaciones.add(btnBuscar);

        // Botón Editar
        JButton btnEditar = new DecoracionBotones(
                "EDITAR",
                DecoracionBotones.AZUL, DecoracionBotones.CELESTE, DecoracionBotones.AMARILLO,
                DecoracionBotones.CELESTE, DecoracionBotones.AZUL, DecoracionBotones.AZUL);
        btnEditar.setFont(fuente2.deriveFont(24f));
        btnEditar.setBounds(50, 105, 340, 55);
        panelOperaciones.add(btnEditar);

        // Botón Eliminar
        JButton btnEliminar = new DecoracionBotones(
                "ELIMINAR",
                DecoracionBotones.AZUL, DecoracionBotones.CELESTE, DecoracionBotones.AMARILLO,
                DecoracionBotones.CELESTE, DecoracionBotones.AZUL, DecoracionBotones.AZUL);
        btnEliminar.setFont(fuente2.deriveFont(24f));
        btnEliminar.setBounds(50, 185, 340, 55);
        panelOperaciones.add(btnEliminar);

        //---------------- PANEL DERECHO ----------------
        FondoPanelSemi panelDerecho = new FondoPanelSemi(new Color(0, 0, 0, 130));

        panelDerecho.setLayout(null);
        panelDerecho.setBounds(680, 170, 880, 560);

        fondo.add(panelDerecho);

        JLabel lblInstruccionId = new JLabel("Ingrese / busque el ID de la pregunta");

        lblInstruccionId.setFont(fuente2.deriveFont(22f));
        lblInstruccionId.setForeground(Color.WHITE);
        lblInstruccionId.setBounds(30, 30, 820, 35);

        panelDerecho.add(lblInstruccionId);

        txtIdPista = new JTextField();

        txtIdPista.setFont(fuente1.deriveFont(26f));
        txtIdPista.setBounds(30, 75, 820, 55);

        panelDerecho.add(txtIdPista);

        //---------------- TABLA ----------------
        // Columna 0 (id_ayuda) queda oculta: es la PK real usada para EDITAR/ELIMINAR.
        modelo = new DefaultTableModel(
                new String[]{"id_ayuda", "ID Pregunta", "Audio"}, 0) {

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tablaPistas = new JTable(modelo);

        tablaPistas.setFont(fuente1.deriveFont(18f));
        tablaPistas.setRowHeight(42);

        tablaPistas.getTableHeader().setFont(fuente2.deriveFont(20f));
        tablaPistas.getTableHeader().setReorderingAllowed(false);

        sorter = new TableRowSorter<>(modelo);
        tablaPistas.setRowSorter(sorter);

        // Ocultar columna id_ayuda sin quitarla del modelo (se sigue pudiendo leer su valor)
        TableColumn columnaId = tablaPistas.getColumnModel().getColumn(0);
        columnaId.setMinWidth(0);
        columnaId.setMaxWidth(0);
        columnaId.setPreferredWidth(0);

        JScrollPane scrollPaneTabla = new JScrollPane(tablaPistas);

        scrollPaneTabla.setBounds(30, 155, 820, 300);

        panelDerecho.add(scrollPaneTabla);

        //---------------- CLIC EN FILA: MOSTRAR/EDITAR ----------------
        tablaPistas.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int filaVista = tablaPistas.getSelectedRow();
                if (filaVista == -1) {
                    return;
                }
                int filaModelo = tablaPistas.convertRowIndexToModel(filaVista);
                cargarFilaParaEdicion(filaModelo);
            }
        });

        //---------------- BUSCAR (automático, tipo "contiene") ----------------
        txtIdPista.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) { filtrarTabla(); }
            @Override
            public void removeUpdate(DocumentEvent e) { filtrarTabla(); }
            @Override
            public void changedUpdate(DocumentEvent e) { filtrarTabla(); }
        });

        //---------------- BOTÓN GUARDAR / ACTUALIZAR ----------------
        btnGuardar = new DecoracionBotones(
                "GUARDAR",
                DecoracionBotones.VERDE,
                DecoracionBotones.AZUL,
                DecoracionBotones.AMARILLO,
                DecoracionBotones.AZUL,
                DecoracionBotones.VERDE,
                DecoracionBotones.VERDE);

        btnGuardar.setFont(fuente2.deriveFont(26f));
        btnGuardar.setBounds(980, 760, 250, 70);

        fondo.add(btnGuardar);

        //---------------- BOTÓN MOSTRAR (BAJO GUARDAR) ----------------
//        JButton btnMostrar = new DecoracionBotones(
//                "MOSTRAR",
        //---------------- BOTÓN ELIMINAR ----------------
        btnEliminar = new DecoracionBotones(
                "ELIMINAR",

                DecoracionBotones.ROJO,
                DecoracionBotones.ROSA,
                DecoracionBotones.AMARILLO,

                DecoracionBotones.ROSA,
                DecoracionBotones.ROJO,
                DecoracionBotones.ROJO);

        btnEliminar.setFont(fuente2.deriveFont(26f));
        btnEliminar.setBounds(1250, 760, 250, 70);
        btnEliminar.setEnabled(false);

        fondo.add(btnEliminar);

        //---------------- BOTÓN CANCELAR EDICIÓN ----------------
        btnCancelarEdicion = new DecoracionBotones(
                "CANCELAR",

                DecoracionBotones.GRIS,
                DecoracionBotones.CELESTE,
                DecoracionBotones.AMARILLO,

                DecoracionBotones.CELESTE,
                DecoracionBotones.GRIS,
                DecoracionBotones.GRIS);

        btnCancelarEdicion.setFont(fuente2.deriveFont(24f));
        btnCancelarEdicion.setBounds(980, 850, 250, 60);
        btnCancelarEdicion.setEnabled(false);
        btnCancelarEdicion.setVisible(false);

        fondo.add(btnCancelarEdicion);

        //---------------- BOTÓN VOLVER ----------------
        JButton btnVolver = new DecoracionBotones(
                "VOLVER",
                DecoracionBotones.AZUL,
                DecoracionBotones.GRIS,
                DecoracionBotones.AMARILLO,
                DecoracionBotones.CELESTE,
                DecoracionBotones.AZUL,
                DecoracionBotones.AZUL);

        btnVolver.setFont(fuente2.deriveFont(28f));
        btnVolver.setBounds(1550, 860, 300, 65);

        btnVolver.addActionListener(e -> {
            new PistasMenu(datos);
            dispose();
        });

        fondo.add(btnVolver);

        //---------------- EVENTOS ----------------
        btnCargar.addActionListener(e -> seleccionarAudio());
//        btnBuscar.addActionListener(e -> buscarAudio());
//        btnEditar.addActionListener(e -> editarAudio());
//        btnEliminar.addActionListener(e -> eliminarAudio());
//        btnMostrar.addActionListener(e -> mostrarAudios());

        //---------------- EVENTO GUARDAR / ACTUALIZAR ----------------
        btnGuardar.addActionListener(e -> guardarOActualizarAudioBD());

        //---------------- EVENTO ELIMINAR ----------------
        btnEliminar.addActionListener(e -> eliminarAudioBD());

        //---------------- EVENTO CANCELAR EDICIÓN ----------------
        btnCancelarEdicion.addActionListener(e -> limpiarFormulario());

        //---------------- MASCOTA ----------------
        JLabel staticMascotaLibro = new JLabel();

        try {
            ImageIcon iconMascota = new ImageIcon(
                    getClass().getResource("/Multimedia/utiles/mascotaKitsura/imagen/REGLAS-PISTA_TEXTUAL.png"));

            Image imgEscalada = iconMascota.getImage()
                    .getScaledInstance(550, 550, Image.SCALE_SMOOTH);

            staticMascotaLibro.setIcon(new ImageIcon(imgEscalada));

        } catch (Exception e) {
            staticMascotaLibro.setText("~");
        }
        staticMascotaLibro.setBounds(1490, 220, 550, 550);

        fondo.add(staticMascotaLibro);
    }

    //---------------- RESOLVER MCN: id_nivel a partir de Minijuego/Categoría/Dificultad ----------------
    private int resolverIdNivel() {

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
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return -1;
    }

    //---------------- CUENTA CUÁNTAS PISTAS DE AUDIO ACTIVAS TIENE EL NIVEL ----------------
    private int contarPistasAudioEnNivel(int idNivel) {

        String sql = "SELECT COUNT(*) FROM Ayuda a "
                + "JOIN Pregunta p ON a.id_pregunta = p.id_pregunta "
                + "WHERE a.tipo = 'audio' AND a.estado = 'activo' AND p.id_nivel = ?";

        try (Connection con = new Conexion().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idNivel);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }

    //---------------- MOSTRAR: CARGAR REGISTROS DEL NIVEL ACTUAL (MCN) ----------------
    private void cargarDatosDesdeBD() {

        modelo.setRowCount(0);

        // Solo pistas de audio activas, y solo de preguntas que pertenecen al nivel
        // resuelto desde MCN (Minijuego/Categoría/Dificultad seleccionados en PedirMCN).
        String sql = "SELECT a.id_ayuda, a.id_pregunta, a.audio "
                + "FROM Ayuda a "
                + "JOIN Pregunta p ON a.id_pregunta = p.id_pregunta "
                + "WHERE a.tipo = 'audio' AND a.estado = 'activo' AND p.id_nivel = ?";

        try (Connection con = new Conexion().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idNivelActual);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    int idAyuda = rs.getInt("id_ayuda");
                    int idPregunta = rs.getInt("id_pregunta");
                    String rutaAudio = rs.getString("audio");
                    String nombreArchivo = rutaAudio != null
                            ? new File(rutaAudio).getName()
                            : "";

                    modelo.addRow(new Object[]{idAyuda, idPregunta, nombreArchivo});
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(
                    this,
                    "Error al cargar los datos:\n" + e.getMessage());
        }
    }

    //---------------- BUSCAR: FILTRO EN VIVO (contiene) ----------------
    private void filtrarTabla() {

        String texto = txtIdPista.getText().trim();

        if (texto.isEmpty()) {
            sorter.setRowFilter(null);
            return;
        }

        try {
            // Columna 1 = "ID Pregunta". Coincide con cualquier ID que CONTENGA el texto ingresado.
            sorter.setRowFilter(RowFilter.regexFilter(Pattern.quote(texto), 1));
        } catch (PatternSyntaxException ex) {
            sorter.setRowFilter(null);
        }
    }

    //---------------- CLIC EN FILA: CARGA DATOS PARA EDITAR ----------------
    private void cargarFilaParaEdicion(int filaModelo) {

        idAyudaSeleccionado = (int) modelo.getValueAt(filaModelo, 0);
        Object idPregunta = modelo.getValueAt(filaModelo, 1);
        String nombreArchivo = String.valueOf(modelo.getValueAt(filaModelo, 2));

        txtIdPista.setText(String.valueOf(idPregunta));

        // Se guarda la ruta actual para poder mantenerla si el usuario no elige un nuevo archivo
        audioActualEdicion = nombreArchivo;
        rutaAudioSeleccionado = "";

        lblArchivoActual.setText("Actual: " + nombreArchivo);

        modoEdicion = true;
        btnGuardar.setText("ACTUALIZAR");
        btnEliminar.setEnabled(true);
        btnCancelarEdicion.setEnabled(true);
        btnCancelarEdicion.setVisible(true);
    }

    //---------------- LIMPIA EL FORMULARIO Y VUELVE A MODO "AÑADIR" ----------------
    private void limpiarFormulario() {

        txtIdPista.setText("");
        rutaAudioSeleccionado = "";
        audioActualEdicion = "";
        idAyudaSeleccionado = -1;
        modoEdicion = false;

        lblArchivoActual.setText("Ningún archivo seleccionado");

        btnGuardar.setText("GUARDAR");
        btnEliminar.setEnabled(false);
        btnCancelarEdicion.setEnabled(false);
        btnCancelarEdicion.setVisible(false);

        tablaPistas.clearSelection();
    }

    //---------------- SELECCIONAR AUDIO ----------------
    private void seleccionarAudio() {

        JFileChooser chooser = new JFileChooser();

        FileNameExtensionFilter filtro = new FileNameExtensionFilter(
                "Archivos de Audio",
                "mp3", "wav", "ogg");

        chooser.setFileFilter(filtro);

        int resultado = chooser.showOpenDialog(this);

        if (resultado == JFileChooser.APPROVE_OPTION) {
            File archivo = chooser.getSelectedFile();
            rutaAudioSeleccionado = archivo.getAbsolutePath();

            lblArchivoActual.setText("Nuevo: " + archivo.getName());

            JOptionPane.showMessageDialog(
                    this,
                    "Audio seleccionado:\n" + archivo.getName());
        }
    }
    
    //--------------- MOSTRAR AUDIOS ----------------
    private void mostrarAudios() {
        modelo.setRowCount(0);
        try {
            Connection con = conexion.getConnection();
            String sql = "SELECT id_pregunta, audio FROM Ayuda WHERE tipo='audio'";
            PreparedStatement ps = con.prepareStatement(sql);
            var rs = ps.executeQuery();
            while (rs.next()) {
                String nombreArchivo = new File(rs.getString("audio")).getName();
                modelo.addRow(new Object[]{
                    rs.getInt("id_pregunta"),
                    nombreArchivo
                });
            }
            rs.close();
            ps.close();
            con.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Error al mostrar los audios");
        }
    }

//    //---------------- GUARDAR EN MYSQL ----------------
//    private void guardarAudioBD() {
//        String idTexto = txtIdPista.getText().trim();
//        if (idTexto.isEmpty()) {
//            JOptionPane.showMessageDialog(this, "Ingrese el ID de la pregunta");
//            return;
//        }
//        if (rutaAudioSeleccionado.isEmpty()) {
//            JOptionPane.showMessageDialog(this, "Seleccione un audio");
//            return;
//        }   
//        try {
//            int idPregunta = Integer.parseInt(idTexto);
//            Connection con = conexion.getConnection();
//
//            String sql = "INSERT INTO Ayuda (id_pregunta, tipo, audio) VALUES (?, 'audio', ?)";
//            PreparedStatement ps = con.prepareStatement(sql);
//            ps.setInt(1, idPregunta);
//            ps.setString(2, rutaAudioSeleccionado);
//            ps.executeUpdate();
//            
//            String nombreArchivo = new File(rutaAudioSeleccionado).getName();
//            modelo.addRow(new Object[]{
//                idPregunta,
//                nombreArchivo
//            });
//            
//            JOptionPane.showMessageDialog(this, "Audio guardado correctamente");
//            mostrarAudios();
//            txtIdPista.setText("");
//            rutaAudioSeleccionado = "";
//            ps.close();
//            con.close();

    //---------------- VALIDA QUE EL ID PERTENEZCA AL MCN (MINIJUEGO/CATEGORÍA/NIVEL) SELECCIONADO ----------------
    private boolean idPreguntaExisteEnMCN(int idPregunta, Connection con) throws Exception {

        // No basta con que la pregunta exista: debe pertenecer al mismo id_nivel
        // resuelto a partir de la selección hecha en PedirMCN (idNivelActual).
        String sql = "SELECT COUNT(*) FROM Pregunta "
                + "WHERE id_pregunta = ? AND estado = 'activo' AND id_nivel = ?";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idPregunta);
            ps.setInt(2, idNivelActual);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        }
        return false;
    }

    //---------------- AÑADIR / EDITAR EN MYSQL ----------------
    private void guardarOActualizarAudioBD() {

        String idTexto = txtIdPista.getText().trim();
        if (idTexto.isEmpty()) {
            JOptionPane.showMessageDialog(
                    this,
                    "Ingrese el ID de la pregunta");
            return;
        }

        // Al añadir se exige audio nuevo; al editar se puede mantener el existente
        if (rutaAudioSeleccionado.isEmpty() && !(modoEdicion && !audioActualEdicion.isEmpty())) {
            JOptionPane.showMessageDialog(
                    this,
                    "Seleccione un audio");
            return;
        }

        try (Connection con = new Conexion().getConnection()) {

            int idPregunta = Integer.parseInt(idTexto);

            // Concordancia con MCN: el ID debe existir y pertenecer al nivel
            // (Minijuego/Categoría/Dificultad) seleccionado en PedirMCN
            if (!idPreguntaExisteEnMCN(idPregunta, con)) {
                JOptionPane.showMessageDialog(
                        this,
                        "El ID de pregunta no pertenece al Minijuego/Categoría/Nivel seleccionado (MCN)");
                return;
            }

            String rutaFinal = rutaAudioSeleccionado.isEmpty()
                    ? audioActualEdicion
                    : rutaAudioSeleccionado;

            if (modoEdicion) {

                String sql = "UPDATE Ayuda SET id_pregunta = ?, audio = ? "
                        + "WHERE id_ayuda = ? AND tipo = 'audio'";

                try (PreparedStatement ps = con.prepareStatement(sql)) {
                    ps.setInt(1, idPregunta);
                    ps.setString(2, rutaFinal);
                    ps.setInt(3, idAyudaSeleccionado);
                    ps.executeUpdate();
                }

                JOptionPane.showMessageDialog(
                        this,
                        "Audio actualizado correctamente");

            } else {

                String sql = "INSERT INTO Ayuda "
                        + "(id_pregunta, tipo, audio) "
                        + "VALUES (?, 'audio', ?)";

                try (PreparedStatement ps = con.prepareStatement(sql)) {
                    ps.setInt(1, idPregunta);
                    ps.setString(2, rutaFinal);
                    ps.executeUpdate();
                }

                JOptionPane.showMessageDialog(
                        this,
                        "Audio guardado correctamente");
            }

            cargarDatosDesdeBD();
            limpiarFormulario();

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "El ID debe ser numérico");
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al guardar:\n" + e.getMessage());
        }
    }
    
    // ---------------- BUSCAR EN MYSQL ----------------
//    private void buscarAudio(){
//        if(txtIdPista.getText().trim().isEmpty()){
//            JOptionPane.showMessageDialog(this,
//                    "Ingrese el ID");
//            return;
//        }
//        modelo.setRowCount(0);
//        try{
//            Connection con = conexion.getConnection();
//            String sql="SELECT id_pregunta,audio FROM Ayuda WHERE id_pregunta=? AND tipo='audio'";
//            PreparedStatement ps=con.prepareStatement(sql);
//            ps.setInt(1,Integer.parseInt(txtIdPista.getText()));
//            var rs=ps.executeQuery();
//            if(rs.next()){
//                modelo.addRow(new Object[]{
//                        rs.getInt("id_pregunta"),
//                        new File(rs.getString("audio")).getName()
//                });
//                txtIdPista.setText("");
//            }else{
//                JOptionPane.showMessageDialog(this,
//                        "No existe un audio para ese ID");
//            }
//            rs.close();
//            ps.close();
//            con.close();
//        }catch(Exception e){
//            JOptionPane.showMessageDialog(this,
//                    "Error al buscar");
//        }
//    }
    // --------------- ELIMINAR EN MYSQL ----------------
//    private void eliminarAudio(){
//        if(txtIdPista.getText().trim().isEmpty()){
//            JOptionPane.showMessageDialog(this,
//                    "Ingrese el ID");
//            return;
//        }
//        try{
//            Connection con = conexion.getConnection();
//            String sql="DELETE FROM Ayuda WHERE id_pregunta=? AND tipo='audio'";
//            PreparedStatement ps=con.prepareStatement(sql);
//            ps.setInt(1,Integer.parseInt(txtIdPista.getText()));
//            int filas=ps.executeUpdate();
//            if(filas>0){
//                JOptionPane.showMessageDialog(this,
//                        "Audio eliminado");
//                        txtIdPista.setText("");
//                mostrarAudios();
//            }else{
//                JOptionPane.showMessageDialog(this,
//                        "No existe ese audio");
//            }
//            ps.close();
//            con.close();
//        }catch(Exception e){
//            JOptionPane.showMessageDialog(this,
//                    "Error al eliminar");
//            }
//        }
    // --------------- EDITAR EN MYSQL ----------------
//    private void editarAudio(){
//        if(txtIdPista.getText().trim().isEmpty()){
//            JOptionPane.showMessageDialog(this,
//                    "Ingrese el ID");
//            return;
//        }
//        seleccionarAudio();
//        if(rutaAudioSeleccionado.isEmpty()){
//            return;
//        }
//        try{
//            Connection con = conexion.getConnection();
//            String sql="UPDATE Ayuda SET audio=? WHERE id_pregunta=? AND tipo='audio'";
//            PreparedStatement ps=con.prepareStatement(sql);
//            ps.setString(1,rutaAudioSeleccionado);
//            ps.setInt(2,Integer.parseInt(txtIdPista.getText()));
//            int filas=ps.executeUpdate();
//            if(filas>0){
//                JOptionPane.showMessageDialog(this,
//                        "Audio actualizado");
//                mostrarAudios();
//                txtIdPista.setText("");
//            }else{
//                JOptionPane.showMessageDialog(this,
//                        "No existe ese ID");
//            }
//            ps.close();
//            con.close();
//        }catch(Exception e){
//            JOptionPane.showMessageDialog(this,
//                    "Error al editar");
//        }
//    }

    //---------------- ELIMINAR (soft delete: estado = 'inactivo') ----------------
    // La tabla Ayuda define una columna "estado", igual que Pregunta/Minijuego/Categoria,
    // por lo que el borrado sigue el mismo patrón del resto del proyecto: no se elimina
    // físicamente el registro, solo se desactiva para que deje de mostrarse en el juego.
    private void eliminarAudioBD() {

        if (idAyudaSeleccionado == -1) {
            return;
        }

        int confirmacion = JOptionPane.showConfirmDialog(
                this,
                "¿Desea eliminar este registro de audio?",
                "Confirmar eliminación",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);

        if (confirmacion != JOptionPane.YES_OPTION) {
            return;
        }

        try (Connection con = new Conexion().getConnection()) {

            String sql = "UPDATE Ayuda SET estado = 'inactivo' "
                    + "WHERE id_ayuda = ? AND tipo = 'audio'";

            try (PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setInt(1, idAyudaSeleccionado);
                ps.executeUpdate();
            }

            JOptionPane.showMessageDialog(
                    this,
                    "Audio eliminado correctamente");

            cargarDatosDesdeBD();
            limpiarFormulario();

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(
                    this,
                    "Error al eliminar:\n" + e.getMessage());
        }
    }
}