package main.Administrador;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import main.Menu.FondoPanel;
import main.Menu.FondoPanelSemi;
import main.Menu.DecoracionBotones;

import main.conexion.Conexion;

public class M1_crearNuevo extends JFrame {

    private FondoPanel fondo;
    private Font fuente1;
    private Font fuente2;

    // Declaramos los botones personalizados
    private DecoracionBotones btnCargarColor;
    private DecoracionBotones btnCargarSombra;
    private DecoracionBotones btnSalir;
    private DecoracionBotones btnSiguiente;
    private DecoracionBotones btnEliminar;

    // Paneles donde se muestra la vista previa de la imagen cargada
    private JPanel areaColor;
    private JPanel areaSombra;
    private JLabel previewColor;
    private JLabel previewSombra;

    // Campos de texto de respuestas: 1 correcta + 3 incorrectas por pregunta
    private JTextField txtCorrecta1;
    private JTextField txtIncorrecta1;
    private JTextField txtIncorrecta2;
    private JTextField txtIncorrecta3;

    // --- Selección de a qué categoría/dificultad pertenece la pregunta ---
    private JComboBox<String> cbCategoria;
    private JComboBox<String> cbDificultad;

    // El índice + 1 de cada arreglo corresponde al id_categoria / al nivel local
    // usado en la convención de nombres de carpetas (Nivel_X_CY_M1)
    private static final String[] NOMBRES_CATEGORIA = {"Animales", "Territorios", "Caricaturas"};
    private static final String[] NOMBRES_DIFICULTAD = {"Fácil", "Intermedio", "Difícil"};

    // --- Rutas de las imágenes ya copiadas a la carpeta del proyecto ---
    // IMPORTANTE: ahora estas rutas son ABSOLUTAS EN DISCO (no de classpath),
    // porque las imágenes que sube el admin en tiempo de ejecución nunca
    // pasan por un "build" que las incluya en el classpath del programa.
    private String rutaImagenColor;
    private String rutaImagenSombra;

    // Si no es null, estamos EDITANDO esta pregunta (INGRESAR hará UPDATE en vez de INSERT)
    private Integer idPreguntaEnEdicion = null;

    private static final int ID_MINIJUEGO = 1; // Hidden Fox

    // Selección hecha previamente en PedirMCN (minijuego, categoría, nivel).
    // Se guarda ÚNICAMENTE para poder propagarla de vuelta si el admin
    // presiona "VOLVER" (así AdminStages no pierde el contexto). Puede ser
    // null si esta ventana se abrió sin pasar por Pedir M,C,N (por ejemplo,
    // usando el constructor de compatibilidad M1_crearNuevo()).
    private final DatosConfiguracion datos;

    // Carpeta base FIJA en disco donde se guardan los recursos multimedia
    // subidos por el admin. Se ubica junto al directorio de ejecución del
    // programa, FUERA del classpath/Source Packages, para no depender de
    // ningún Clean & Build. Ajusta esta ruta si prefieres otra ubicación
    // (por ejemplo, una carpeta fija tipo "C:/KitsuraAssets").
    private static final Path CARPETA_BASE_MULTIMEDIA = Paths.get(
            System.getProperty("user.dir"),
            "assets_admin", "Multimedia", "Minijuegos", "Minijuego_1");

    /**
     * Constructor de compatibilidad (sin contexto de Pedir M,C,N). Si algo
     * más en el proyecto todavía llama a "new M1_crearNuevo()" a secas, esto
     * evita que deje de compilar, pero el botón VOLVER en ese caso reabrirá
     * AdminStages sin selección previa. Se recomienda usar siempre el
     * constructor con (DatosConfiguracion, Integer).
     */
    public M1_crearNuevo() {
        this(null, null);
    }

    /**
     * Constructor de compatibilidad para abrir directamente en modo edición
     * sin contexto de Pedir M,C,N.
     */
    public M1_crearNuevo(Integer idPreguntaExistente) {
        this(null, idPreguntaExistente);
    }

    /**
     * Abre la pantalla, opcionalmente en modo EDICIÓN, cargando desde la BD
     * la categoría, dificultad, imágenes y respuestas de la pregunta
     * indicada. Pasa null en idPreguntaExistente para el modo "crear nuevo".
     *
     * @param datos selección hecha en Pedir M,C,N (puede ser null si no
     * aplica). Se guarda solo para poder propagarla de vuelta a AdminStages
     * cuando el admin presione "VOLVER", sin perder el contexto ya elegido.
     * @param idPreguntaExistente id de la pregunta a editar, o null para
     * crear una pregunta nueva.
     */
    public M1_crearNuevo(DatosConfiguracion datos, Integer idPreguntaExistente) {
        this.datos = datos;
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
        //------------- ÍCONO ------------------
        //se obtiene la imagen del logo con getResource
        URL iconUrl = getClass().getResource("/Multimedia/utiles/logotipo/logofK.png");

        //se instancia el ícono con la imagen
        ImageIcon icono = new ImageIcon(iconUrl);

        //Se coloca el ícono al JFrame
        setIconImage(icono.getImage());

        setTitle("M1-Crear nuevo");
        setSize(1980, 1080);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        fondo.setLayout(null);
        crearComponentes();

        if (idPreguntaExistente != null) {
            editar(idPreguntaExistente);
        }

        setVisible(true);
    }

    /**
     * Pone la pantalla en modo EDICIÓN para el id_pregunta indicado: carga su
     * categoría, dificultad, imágenes y respuestas, y hace que el botón
     * INGRESAR haga UPDATE (en vez de INSERT) al guardar.
     *
     * Se puede llamar tanto al abrir la ventana (ej. desde EditarStages) como
     * en cualquier momento después, si se necesita cambiar de pregunta sin
     * cerrar esta ventana (por ejemplo, si el ID ingresado en EditarStages
     * cambió).
     */
    public void editar(int idPregunta) {
        try {
            cargarPreguntaExistente(idPregunta);
            setTitle("M1-Editar pregunta #" + idPregunta);
            actualizarEstadoBotonEliminar();
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this,
                    "No se pudo cargar la pregunta #" + idPregunta + ": " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void crearComponentes() {
        FondoPanelSemi panelTitulo = new FondoPanelSemi(new Color(0, 0, 0, 140));
        panelTitulo.setBounds(50, 135, 530, 60);
        panelTitulo.setLayout(null);
        fondo.add(panelTitulo);

        // --- COMPONENTES FUERA DEL PANEL ---
        JLabel lblTituloSeccion = new JLabel("CREAR NUEVO (MINIJUEGO 1)");
        lblTituloSeccion.setFont(fuente2.deriveFont(35f));
        lblTituloSeccion.setForeground(Color.decode("#82D3E0"));
        lblTituloSeccion.setBounds(20, 5, 500, 50);
        panelTitulo.add(lblTituloSeccion);

        JLabel staticMascotaTablet = new JLabel();
        ImageIcon iconMascota = new ImageIcon(getClass().getResource("/Multimedia/utiles/mascotaKitsura/imagen/REGISTRARSE_USUARIO-PARTIDA_MINIJUEGO-TABLETA.png"));
        Image imgEscalada = iconMascota.getImage().getScaledInstance(600, 600, Image.SCALE_SMOOTH);
        staticMascotaTablet.setIcon(new ImageIcon(imgEscalada));
        staticMascotaTablet.setBounds(20, 240, 600, 600);
        fondo.add(staticMascotaTablet);

        // -- BOTON SIGUIENTE --
        btnSiguiente = new DecoracionBotones("SIGUIENTE",
                DecoracionBotones.AZUL, DecoracionBotones.GRIS, DecoracionBotones.AMARILLO, //MOUSE FUERA
                DecoracionBotones.CELESTE, DecoracionBotones.AZUL, DecoracionBotones.AZUL); //MOUSE DENTRO
        btnSiguiente.setFont(fuente2.deriveFont(15f));
        btnSiguiente.setBounds(150, 810, 240, 40);
        btnSiguiente.addActionListener(e -> guardarNivelYAvanzar());
        fondo.add(btnSiguiente);

        // -- BOTON ELIMINAR --
        // Solo tiene efecto real cuando hay una pregunta cargada en modo
        // edición (idPreguntaEnEdicion != null). Se deja siempre visible,
        // pero si se presiona sin una pregunta cargada, se avisa y no hace
        // nada (ver actualizarEstadoBotonEliminar/eliminarPreguntaActual).
        btnEliminar = new DecoracionBotones("ELIMINAR",
                DecoracionBotones.ROJO, DecoracionBotones.GRIS, DecoracionBotones.AMARILLO, //MOUSE FUERA
                DecoracionBotones.ROSA, DecoracionBotones.ROJO, DecoracionBotones.ROJO); //MOUSE DENTRO
        btnEliminar.setFont(fuente2.deriveFont(15f));
        btnEliminar.setBounds(410, 810, 240, 40);
        btnEliminar.addActionListener(e -> eliminarPreguntaActual());
        fondo.add(btnEliminar);
        actualizarEstadoBotonEliminar();

        // --- EL PANEL SEMI-TRANSPARENTE ---
        FondoPanelSemi panelFormulario = new FondoPanelSemi(new Color(0, 0, 0, 120));
        panelFormulario.setBounds(660, 130, 1100, 660);
        panelFormulario.setLayout(null);
        fondo.add(panelFormulario);

        JLabel lblCargueColor = new JLabel("Cargue la Imagen a color");
        lblCargueColor.setFont(fuente2.deriveFont(22f));
        lblCargueColor.setForeground(Color.WHITE);
        lblCargueColor.setBounds(40, 45, 400, 30);
        panelFormulario.add(lblCargueColor);

        areaColor = new JPanel(new BorderLayout());
        areaColor.setBackground(Color.LIGHT_GRAY);
        areaColor.setBounds(40, 80, 420, 240);
        previewColor = new JLabel("", SwingConstants.CENTER);
        areaColor.add(previewColor, BorderLayout.CENTER);
        panelFormulario.add(areaColor);

        btnCargarColor = new DecoracionBotones("CARGAR",
                DecoracionBotones.ROSA, DecoracionBotones.ROJO, DecoracionBotones.AMARILLO, //MOUSE FUERA
                DecoracionBotones.ROJO, DecoracionBotones.ROSA, DecoracionBotones.ROSA); //MOUSE DENTRO
        btnCargarColor.setFont(fuente2.deriveFont(18f));
        btnCargarColor.setBounds(130, 340, 255, 45);
        btnCargarColor.addActionListener(e -> cargarImagen(true));
        panelFormulario.add(btnCargarColor);

        JLabel lblCargueSombra = new JLabel("Cargue la sombra:");
        lblCargueSombra.setFont(fuente2.deriveFont(22f));
        lblCargueSombra.setForeground(Color.WHITE);
        lblCargueSombra.setBounds(590, 30, 400, 30);
        panelFormulario.add(lblCargueSombra);

        areaSombra = new JPanel(new BorderLayout());
        areaSombra.setBackground(Color.LIGHT_GRAY);
        areaSombra.setBounds(590, 80, 420, 240);
        previewSombra = new JLabel("", SwingConstants.CENTER);
        areaSombra.add(previewSombra, BorderLayout.CENTER);
        panelFormulario.add(areaSombra);

        btnCargarSombra = new DecoracionBotones("CARGAR",
                DecoracionBotones.ROSA, DecoracionBotones.ROJO, DecoracionBotones.AMARILLO, //MOUSE FUERA
                DecoracionBotones.ROJO, DecoracionBotones.ROSA, DecoracionBotones.ROSA); //MOUSE DENTRO
        btnCargarSombra.setFont(fuente2.deriveFont(18f));
        btnCargarSombra.setBounds(680, 340, 255, 45);
        btnCargarSombra.addActionListener(e -> cargarImagen(false));
        panelFormulario.add(btnCargarSombra);

        // --- SELECCIÓN DE CATEGORÍA Y DIFICULTAD (define el id_nivel real) ---
        JLabel lblCategoria = new JLabel("Categoría:");
        lblCategoria.setFont(fuente2.deriveFont(18f));
        lblCategoria.setForeground(Color.WHITE);
        lblCategoria.setBounds(40, 392, 120, 30);
        panelFormulario.add(lblCategoria);

        cbCategoria = new JComboBox<>(NOMBRES_CATEGORIA);
        cbCategoria.setFont(fuente1.deriveFont(18f));
        cbCategoria.setBounds(160, 392, 300, 30);
        panelFormulario.add(cbCategoria);

        JLabel lblDificultad = new JLabel("Dificultad:");
        lblDificultad.setFont(fuente2.deriveFont(18f));
        lblDificultad.setForeground(Color.WHITE);
        lblDificultad.setBounds(590, 392, 120, 30);
        panelFormulario.add(lblDificultad);

        cbDificultad = new JComboBox<>(NOMBRES_DIFICULTAD);
        cbDificultad.setFont(fuente1.deriveFont(18f));
        cbDificultad.setBounds(710, 392, 300, 30);
        panelFormulario.add(cbDificultad);

        JLabel lblCorrecta1 = new JLabel("Ingrese la respuesta correcta del nivel:");
        lblCorrecta1.setFont(fuente1.deriveFont(32f));
        lblCorrecta1.setForeground(Color.WHITE);
        lblCorrecta1.setBounds(40, 430, 450, 25);
        panelFormulario.add(lblCorrecta1);

        txtCorrecta1 = new JTextField();
        txtCorrecta1.setFont(fuente1.deriveFont(22f));
        txtCorrecta1.setBounds(40, 465, 420, 40);
        panelFormulario.add(txtCorrecta1);

        JLabel lblIncorrecta1 = new JLabel("Ingrese la respuesta incorrecta del nivel:");
        lblIncorrecta1.setFont(fuente1.deriveFont(32f));
        lblIncorrecta1.setForeground(Color.WHITE);
        lblIncorrecta1.setBounds(590, 430, 450, 25);
        panelFormulario.add(lblIncorrecta1);

        txtIncorrecta1 = new JTextField();
        txtIncorrecta1.setFont(fuente1.deriveFont(22f));
        txtIncorrecta1.setBounds(590, 465, 420, 40);
        panelFormulario.add(txtIncorrecta1);

        JLabel lblIncorrecta2 = new JLabel("Ingrese la respuesta incorrecta del nivel:");
        lblIncorrecta2.setFont(fuente1.deriveFont(32f));
        lblIncorrecta2.setForeground(Color.WHITE);
        lblIncorrecta2.setBounds(40, 550, 450, 25);
        panelFormulario.add(lblIncorrecta2);

        txtIncorrecta2 = new JTextField();
        txtIncorrecta2.setFont(fuente1.deriveFont(22f));
        txtIncorrecta2.setBounds(40, 585, 420, 40);
        panelFormulario.add(txtIncorrecta2);

        JLabel lblIncorrecta3 = new JLabel("Ingrese la respuesta incorrecta del nivel:");
        lblIncorrecta3.setFont(fuente1.deriveFont(32f));
        lblIncorrecta3.setForeground(Color.WHITE);
        lblIncorrecta3.setBounds(590, 550, 450, 25);
        panelFormulario.add(lblIncorrecta3);

        txtIncorrecta3 = new JTextField();
        txtIncorrecta3.setFont(fuente1.deriveFont(22f));
        txtIncorrecta3.setBounds(590, 585, 420, 40);
        panelFormulario.add(txtIncorrecta3);

        // --- BOTÓN VOLVER---
        // IMPORTANTE: se propaga "datos" para que AdminStages no pierda la
        // selección de Minijuego/Categoría/Nivel hecha en Pedir M,C,N.
        btnSalir = new DecoracionBotones("VOLVER",
                DecoracionBotones.AZUL, DecoracionBotones.GRIS, DecoracionBotones.AMARILLO, //MOUSE FUERA
                DecoracionBotones.CELESTE, DecoracionBotones.AZUL, DecoracionBotones.AZUL); //MOUSE DENTRO

        btnSalir.setFont(fuente2.deriveFont(15f));
        btnSalir.setBounds(1695, 950, 210, 45);
        btnSalir.addActionListener(e -> {
            new AdminStages(datos);
            dispose();
        });
        fondo.add(btnSalir);
    }

    /**
     * Habilita/deshabilita visualmente el botón ELIMINAR según si hay o no
     * una pregunta cargada en modo edición. En modo "crear nuevo" (sin
     * idPreguntaEnEdicion) no tiene sentido eliminar nada todavía.
     */
    private void actualizarEstadoBotonEliminar() {
        if (btnEliminar == null) {
            return;
        }
        btnEliminar.setEnabled(idPreguntaEnEdicion != null);
    }

    // ------------------------------------------------------------------
    //  CARGA Y VISTA PREVIA DE IMÁGENES
    // ------------------------------------------------------------------
    /**
     * Abre un JFileChooser, muestra la vista previa en el panel correspondiente
     * y copia el archivo a la carpeta FIJA en disco (fuera del classpath),
     * siguiendo la misma convención de nombres usada en el script SQL
     * (N{nivel}_C1_M1_S_/_C_ + nombre original).
     */
    private void cargarImagen(boolean esColor) {
        JFileChooser chooser = new JFileChooser();
        chooser.setFileFilter(new FileNameExtensionFilter(
                "Imágenes (jpg, jpeg, png)", "jpg", "jpeg", "png"));

        int resultado = chooser.showOpenDialog(this);
        if (resultado != JFileChooser.APPROVE_OPTION) {
            return;
        }

        File archivoOriginal = chooser.getSelectedFile();

        try {
            String rutaAbsoluta = copiarImagenAlProyecto(archivoOriginal, esColor);
            ImageIcon icono = new ImageIcon(archivoOriginal.getAbsolutePath());
            Image escalada = icono.getImage().getScaledInstance(420, 240, Image.SCALE_SMOOTH);

            if (esColor) {
                previewColor.setIcon(new ImageIcon(escalada));
                rutaImagenColor = rutaAbsoluta;
            } else {
                previewSombra.setIcon(new ImageIcon(escalada));
                rutaImagenSombra = rutaAbsoluta;
            }
        } catch (IOException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this,
                    "No se pudo cargar la imagen: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Copia la imagen seleccionada a una carpeta FIJA EN DISCO:
     * CARPETA_BASE_MULTIMEDIA/Categoria_{cat}_M1/Nivel_{niv}_C{cat}_M1/Imagen_N{niv}_C{cat}_M1/(Sombra|Color)_N{niv}_C{cat}_M1/
     * usando la categoría y dificultad elegidas en los combos, y devuelve la
     * RUTA ABSOLUTA que se guardará en la BD (columna imagen_sombra /
     * imagen_color). Esta ruta se lee luego con new File(ruta) / new
     * ImageIcon(ruta), NO con getClass().getResource(), porque el archivo
     * nunca pasa a formar parte del classpath compilado del proyecto.
     *
     * IMPORTANTE: selecciona la Categoría y Dificultad ANTES de cargar las
     * imágenes, ya que la ruta de guardado depende de esa selección.
     */
    private String copiarImagenAlProyecto(File archivoOriginal, boolean esColor) throws IOException {
        int idCategoria = getIdCategoriaSeleccionada();
        int nivelLocal = getNivelLocalSeleccionado();

        String sufijo = "C" + idCategoria + "_M1";
        String tipoCarpeta = esColor ? "Color_N" + nivelLocal + "_" + sufijo : "Sombra_N" + nivelLocal + "_" + sufijo;
        String prefijo = esColor ? "N" + nivelLocal + "_" + sufijo + "_C_" : "N" + nivelLocal + "_" + sufijo + "_S_";

        Path carpetaDestino = CARPETA_BASE_MULTIMEDIA
                .resolve("Categoria_" + idCategoria + "_M1")
                .resolve("Nivel_" + nivelLocal + "_" + sufijo)
                .resolve("Imagen_N" + nivelLocal + "_" + sufijo)
                .resolve(tipoCarpeta);
        Files.createDirectories(carpetaDestino);

        String nombreArchivo = prefijo + archivoOriginal.getName();
        Path destino = carpetaDestino.resolve(nombreArchivo);
        Files.copy(archivoOriginal.toPath(), destino, StandardCopyOption.REPLACE_EXISTING);

        return destino.toAbsolutePath().toString();
    }

    private int getIdCategoriaSeleccionada() {
        return cbCategoria.getSelectedIndex() + 1;
    }

    private int getNivelLocalSeleccionado() {
        return cbDificultad.getSelectedIndex() + 1;
    }

    private String getDificultadSeleccionada() {
        return NOMBRES_DIFICULTAD[cbDificultad.getSelectedIndex()];
    }

    // ------------------------------------------------------------------
    //  CARGA DE UNA PREGUNTA EXISTENTE (MODO EDICIÓN)
    // ------------------------------------------------------------------
    /**
     * Carga desde la BD la categoría, dificultad, imágenes (vista previa) y
     * respuestas de la pregunta indicada, y las muestra en el formulario.
     */
    private void cargarPreguntaExistente(int idPregunta) throws SQLException {
        String sqlPregunta
                = "SELECT p.imagen_color, p.imagen_sombra, cn.id_categoria, cn.dificultad "
                + "FROM Pregunta p "
                + "JOIN Configuracion_nivel cn ON p.id_nivel = cn.id_nivel "
                + "WHERE p.id_pregunta = ?";

        try (Connection con = new Conexion().getConnection()) {
            if (con == null) {
                throw new SQLException("No se pudo establecer conexión con la base de datos.");
            }

            String imagenColorBD;
            String imagenSombraBD;
            int idCategoriaBD;
            String dificultadBD;

            try (PreparedStatement ps = con.prepareStatement(sqlPregunta)) {
                ps.setInt(1, idPregunta);
                try (ResultSet rs = ps.executeQuery()) {
                    if (!rs.next()) {
                        throw new SQLException("No existe ninguna pregunta con id_pregunta = " + idPregunta);
                    }
                    imagenColorBD = rs.getString("imagen_color");
                    imagenSombraBD = rs.getString("imagen_sombra");
                    idCategoriaBD = rs.getInt("id_categoria");
                    dificultadBD = rs.getString("dificultad");
                }
            }

            // --- Seleccionar la Categoría y Dificultad correspondientes en los combos ---
            cbCategoria.setSelectedIndex(idCategoriaBD - 1);
            int indiceDificultad = java.util.Arrays.asList(NOMBRES_DIFICULTAD).indexOf(dificultadBD);
            if (indiceDificultad >= 0) {
                cbDificultad.setSelectedIndex(indiceDificultad);
            }

            // --- Mostrar la vista previa de las imágenes ya guardadas ---
            rutaImagenColor = imagenColorBD;
            rutaImagenSombra = imagenSombraBD;
            previewColor.setIcon(cargarPreviewDesdeRutaGuardada(imagenColorBD));
            previewSombra.setIcon(cargarPreviewDesdeRutaGuardada(imagenSombraBD));

            // --- Cargar las 4 respuestas de la pregunta ---
            String sqlOpciones = "SELECT texto_opcion, es_correcta FROM Opcion_respuesta WHERE id_pregunta = ? ORDER BY id_opcion";
            int siguienteIncorrecta = 1; // 1, 2 o 3 -> txtIncorrecta1/2/3
            try (PreparedStatement ps = con.prepareStatement(sqlOpciones)) {
                ps.setInt(1, idPregunta);
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        String texto = rs.getString("texto_opcion");
                        boolean esCorrecta = rs.getBoolean("es_correcta");
                        if (esCorrecta) {
                            txtCorrecta1.setText(texto);
                        } else {
                            switch (siguienteIncorrecta) {
                                case 1 ->
                                    txtIncorrecta1.setText(texto);
                                case 2 ->
                                    txtIncorrecta2.setText(texto);
                                case 3 ->
                                    txtIncorrecta3.setText(texto);
                                default -> {
                                    /* ya se llenaron las 3, se ignoran extras */ }
                            }
                            siguienteIncorrecta++;
                        }
                    }
                }
            }

            idPreguntaEnEdicion = idPregunta;
        }
    }

    /**
     * Carga la imagen ya guardada a partir de la RUTA ABSOLUTA EN DISCO
     * almacenada en la BD (columna imagen_color / imagen_sombra), para
     * mostrarla como vista previa. Si el archivo no se encuentra en disco,
     * deja el espacio vacío en vez de fallar.
     *
     * NOTA: si tienes preguntas antiguas guardadas con rutas de classpath
     * (formato "/Multimedia/..."), este método también intenta resolverlas
     * como recurso del proyecto para no romper la vista previa de esos
     * registros previos.
     */
    private ImageIcon cargarPreviewDesdeRutaGuardada(String rutaAlmacenada) {
        if (rutaAlmacenada == null || rutaAlmacenada.isBlank()) {
            return null;
        }

        // Caso 1: ruta absoluta en disco (formato nuevo, imágenes subidas por el admin)
        File archivo = new File(rutaAlmacenada);
        if (archivo.exists()) {
            ImageIcon icono = new ImageIcon(archivo.getAbsolutePath());
            Image escalada = icono.getImage().getScaledInstance(420, 240, Image.SCALE_SMOOTH);
            return new ImageIcon(escalada);
        }

        // Caso 2: ruta de classpath antigua (imágenes precompiladas en Source Packages)
        java.net.URL recurso = getClass().getResource(rutaAlmacenada);
        if (recurso != null) {
            ImageIcon icono = new ImageIcon(recurso);
            Image escalada = icono.getImage().getScaledInstance(420, 240, Image.SCALE_SMOOTH);
            return new ImageIcon(escalada);
        }

        System.err.println("No se encontró la imagen ni en disco ni como recurso del proyecto: " + rutaAlmacenada);
        return null;
    }

    // ------------------------------------------------------------------
    //  GUARDADO EN BASE DE DATOS Y AVANCE DE NIVEL
    // ------------------------------------------------------------------
    private void guardarNivelYAvanzar() {
        if (!validarFormulario()) {
            return;
        }

        try {
            boolean esEdicion = idPreguntaEnEdicion != null;
            guardarPreguntaEnBD();
            String accion = esEdicion ? "actualizada" : "guardada";
            JOptionPane.showMessageDialog(this,
                    "Pregunta " + accion + " correctamente en " + cbCategoria.getSelectedItem()
                    + " - " + cbDificultad.getSelectedItem() + ".");
            if (!esEdicion) {
                limpiarFormularioParaSiguientePregunta();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this,
                    "Error al guardar en la base de datos: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private boolean validarFormulario() {
        if (rutaImagenColor == null || rutaImagenSombra == null) {
            JOptionPane.showMessageDialog(this,
                    "Debes cargar la imagen a color y la sombra antes de continuar.",
                    "Faltan datos", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        if (txtCorrecta1.getText().isBlank() || txtIncorrecta1.getText().isBlank()
                || txtIncorrecta2.getText().isBlank() || txtIncorrecta3.getText().isBlank()) {
            JOptionPane.showMessageDialog(this,
                    "Debes completar todas las respuestas antes de continuar.",
                    "Faltan datos", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        return true;
    }

    /**
     * Busca el id_nivel real en Configuracion_nivel según la categoría y
     * dificultad seleccionadas en los combos.
     */
    private int obtenerIdNivel(Connection con, int idCategoria, String dificultad) throws SQLException {
        String sql = "SELECT id_nivel FROM Configuracion_nivel WHERE id_categoria = ? AND dificultad = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idCategoria);
            ps.setString(2, dificultad);
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) {
                    throw new SQLException("No existe configuración de nivel para la categoría "
                            + idCategoria + " y dificultad '" + dificultad + "'.");
                }
                return rs.getInt("id_nivel");
            }
        }
    }

    /**
     * Guarda la pregunta actual: si idPreguntaEnEdicion tiene valor, ACTUALIZA
     * esa pregunta existente (y reemplaza sus 4 opciones); si es null, INSERTA
     * una pregunta nueva. En ambos casos guarda 1 respuesta correcta + 3
     * incorrectas, tal como requiere Hidden Fox.
     */
    private void guardarPreguntaEnBD() throws SQLException {
        try (Connection con = new Conexion().getConnection()) {
            if (con == null) {
                throw new SQLException("No se pudo establecer conexión con la base de datos.");
            }

            int idCategoria = getIdCategoriaSeleccionada();
            String dificultad = getDificultadSeleccionada();
            int idNivel = obtenerIdNivel(con, idCategoria, dificultad);

            int idPregunta;
            if (idPreguntaEnEdicion != null) {
                idPregunta = idPreguntaEnEdicion;
                String sqlUpdate = "UPDATE Pregunta SET id_nivel = ?, imagen_sombra = ?, imagen_color = ? "
                        + "WHERE id_pregunta = ?";
                try (PreparedStatement psUpdate = con.prepareStatement(sqlUpdate)) {
                    psUpdate.setInt(1, idNivel);
                    psUpdate.setString(2, rutaImagenSombra);
                    psUpdate.setString(3, rutaImagenColor);
                    psUpdate.setInt(4, idPregunta);
                    psUpdate.executeUpdate();
                }

                // Reemplazamos las opciones anteriores por las nuevas
                try (PreparedStatement psDelete = con.prepareStatement(
                        "DELETE FROM Opcion_respuesta WHERE id_pregunta = ?")) {
                    psDelete.setInt(1, idPregunta);
                    psDelete.executeUpdate();
                }
            } else {
                String sqlInsert = "INSERT INTO Pregunta (id_nivel, pregunta, imagen_sombra, imagen_color) "
                        + "VALUES (?, ?, ?, ?)";
                try (PreparedStatement psInsert = con.prepareStatement(sqlInsert, Statement.RETURN_GENERATED_KEYS)) {
                    psInsert.setInt(1, idNivel);
                    psInsert.setString(2, "¿Qué animal representa esta sombra?");
                    psInsert.setString(3, rutaImagenSombra);
                    psInsert.setString(4, rutaImagenColor);
                    psInsert.executeUpdate();

                    try (ResultSet rs = psInsert.getGeneratedKeys()) {
                        if (!rs.next()) {
                            throw new SQLException("No se obtuvo el id_pregunta generado.");
                        }
                        idPregunta = rs.getInt(1);
                    }
                }
            }

            String sqlOpcion = "INSERT INTO Opcion_respuesta (id_pregunta, texto_opcion, es_correcta) "
                    + "VALUES (?, ?, ?)";
            try (PreparedStatement psOpcion = con.prepareStatement(sqlOpcion)) {
                insertarOpcion(psOpcion, idPregunta, txtCorrecta1.getText().trim(), true);
                insertarOpcion(psOpcion, idPregunta, txtIncorrecta1.getText().trim(), false);
                insertarOpcion(psOpcion, idPregunta, txtIncorrecta2.getText().trim(), false);
                insertarOpcion(psOpcion, idPregunta, txtIncorrecta3.getText().trim(), false);
            }
        }
    }

    private void insertarOpcion(PreparedStatement ps, int idPregunta, String texto, boolean esCorrecta) throws SQLException {
        ps.setInt(1, idPregunta);
        ps.setString(2, texto);
        ps.setBoolean(3, esCorrecta);
        ps.executeUpdate();
    }

    // ------------------------------------------------------------------
    //  ELIMINAR PREGUNTA
    // ------------------------------------------------------------------
    /**
     * Elimina la pregunta actualmente cargada en modo edición (junto con sus
     * opciones de respuesta y ayudas asociadas, gracias al ON DELETE CASCADE
     * definido en el esquema: Opcion_respuesta y Ayuda referencian a
     * Pregunta). Pide confirmación antes de borrar, ya que es una acción
     * irreversible.
     *
     * Si no hay ninguna pregunta cargada (idPreguntaEnEdicion == null, es
     * decir estamos en modo "crear nuevo"), se avisa y no se hace nada.
     */
    private void eliminarPreguntaActual() {
        if (idPreguntaEnEdicion == null) {
            JOptionPane.showMessageDialog(this,
                    "No hay ninguna pregunta cargada para eliminar. "
                    + "Selecciona una pregunta existente desde 'Editar Stages'.",
                    "Nada que eliminar", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirmacion = JOptionPane.showConfirmDialog(this,
                "¿Seguro que deseas eliminar la pregunta #" + idPreguntaEnEdicion + "?\n"
                + "Esta acción no se puede deshacer.",
                "Confirmar eliminación",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);

        if (confirmacion != JOptionPane.YES_OPTION) {
            return;
        }

        try (Connection con = new Conexion().getConnection()) {
            if (con == null) {
                throw new SQLException("No se pudo establecer conexión con la base de datos.");
            }

            try (PreparedStatement psDelete = con.prepareStatement(
                    "DELETE FROM Pregunta WHERE id_pregunta = ?")) {
                psDelete.setInt(1, idPreguntaEnEdicion);
                int filasAfectadas = psDelete.executeUpdate();

                if (filasAfectadas == 0) {
                    JOptionPane.showMessageDialog(this,
                            "No se encontró la pregunta #" + idPreguntaEnEdicion + " en la base de datos "
                            + "(puede que ya haya sido eliminada).",
                            "Aviso", JOptionPane.WARNING_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this,
                            "Pregunta #" + idPreguntaEnEdicion + " eliminada correctamente.",
                            "Eliminado", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this,
                    "Error al eliminar la pregunta: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Tras eliminar, la ventana ya no tiene una pregunta válida cargada:
        // se limpia el formulario y se cierra, ya que no tiene sentido seguir
        // "editando" algo que ya no existe en la BD.
        limpiarFormularioParaSiguientePregunta();
        idPreguntaEnEdicion = null;
        actualizarEstadoBotonEliminar();
        dispose();
    }

    private void limpiarFormularioParaSiguientePregunta() {
        previewColor.setIcon(null);
        previewSombra.setIcon(null);
        rutaImagenColor = null;
        rutaImagenSombra = null;
        txtCorrecta1.setText("");
        txtIncorrecta1.setText("");
        txtIncorrecta2.setText("");
        txtIncorrecta3.setText("");
    }

    public static void main(String[] args) {
        new M1_crearNuevo();
    }
}