package main.Administrador;

import java.awt.*;
import java.awt.Image;
import java.sql.*;
import javax.swing.*;
import main.Menu.*;
import main.conexion.Conexion;

public class M3_crearNuevo extends JFrame {

    private FondoPanelSemi fondo, panelSemi;

    private Font fuente1, fuente2;

    private JLabel lblTitulo, lblPregunta,
            lblRespuestaIncorrecta, lblRespuestaCorrecta,
            lblMascota;

    private JTextArea txtPregunta;

    private JTextField txtCorrecta, txtIncorrecta,
            txtIncorrecta2, txtIncorrecta3, txtIncorrecta4, txtIncorrecta5, txtIncorrecta6;

    private JLabel lblCheck, lblIncorrecto,
            lblIncorrecto2, lblIncorrecto3, lblIncorrecto4, lblIncorrecto5, lblIncorrecto6;

    private JButton btnSiguiente;

    private DecoracionBotones btnSalir;

    // --- Datos que llegan desde la pantalla anterior (selección de categoría/dificultad) ---
    private final int idCategoria;   // Valor relativo dentro del minijuego 3: 1, 2 o 3
    private final int dificultad;    // 1 = Fácil, 2 = Intermedio, 3 = Difícil
    private final int idNivel;       // Calculado automáticamente
    private final int cantidadIncorrectas; // 4, 5 o 6 según la dificultad

    private static final int MINIJUEGO3_NIVEL_BASE = 18; // 18 + (categoria-1)*3 + dificultad = id_nivel

    public M3_crearNuevo(int idCategoria, int dificultad) {

        this.idCategoria = idCategoria;
        this.dificultad = dificultad;
        this.idNivel = MINIJUEGO3_NIVEL_BASE + (idCategoria - 1) * 3 + dificultad;
        this.cantidadIncorrectas = dificultad + 3; // Fácil=4, Intermedio=5, Difícil=6

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
        fondo = new FondoPanelSemi("/Multimedia/Minijuegos/Minijuego_3/Fondo.jpg");
        setContentPane(fondo);

        setTitle("Crear Nuevo - Minijuego 3");
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
        panelSemi.setBounds(60, 60, 1250, 900);

        fondo.add(panelSemi);

        lblTitulo = new JLabel("CREAR NUEVO (MINIJUEGO 3) - CATEGORÍA " + idCategoria
                + " - " + nombreDificultad(dificultad));
        lblTitulo.setFont(fuente2.deriveFont(32f));
        lblTitulo.setForeground(Color.decode("#447A9C"));
        lblTitulo.setBounds(40, 30, 1150, 60);

        panelSemi.add(lblTitulo);

        lblPregunta = new JLabel("Ingrese la pregunta:");
        lblPregunta.setFont(fuente2.deriveFont(28f));
        lblPregunta.setBounds(50, 110, 400, 40);

        panelSemi.add(lblPregunta);

        txtPregunta = new JTextArea();
        txtPregunta.setFont(fuente1.deriveFont(30f));
        txtPregunta.setLineWrap(true);
        txtPregunta.setWrapStyleWord(true);
        txtPregunta.setBounds(50, 170, 1150, 280);

        panelSemi.add(txtPregunta);

        lblCheck = new JLabel();

        ImageIcon checkIcon
                = new ImageIcon(
                        getClass().getResource("/Multimedia/utiles/ElementosGraficos/imagenes/check.png"));

        Image checkEscalado
                = checkIcon.getImage().getScaledInstance(
                        50,
                        50,
                        Image.SCALE_SMOOTH);

        lblCheck.setIcon(new ImageIcon(checkEscalado));
        lblCheck.setBounds(25, 550, 50, 50);

        panelSemi.add(lblCheck);

        lblRespuestaCorrecta = new JLabel(
                "Ingrese la respuesta correcta del nivel:");

        lblRespuestaCorrecta.setFont(fuente2.deriveFont(22f));

        lblRespuestaCorrecta.setBounds(70, 490, 500, 35);

        panelSemi.add(lblRespuestaCorrecta);

        txtCorrecta = new JTextField();
        txtCorrecta.setFont(fuente1.deriveFont(26f));
        txtCorrecta.setBounds(90, 550, 400, 55);

        panelSemi.add(txtCorrecta);

        lblRespuestaIncorrecta = new JLabel(
                "Ingrese las respuestas incorrectas del nivel:");

        lblRespuestaIncorrecta.setFont(fuente2.deriveFont(22f));

        lblRespuestaIncorrecta.setBounds(620, 490, 550, 35);

        panelSemi.add(lblRespuestaIncorrecta);

        ImageIcon incorrectoIcon
                = new ImageIcon(
                        getClass().getResource("/Multimedia/utiles/ElementosGraficos/imagenes/incorrecto.png"));

        Image incorrectoEscalado
                = incorrectoIcon.getImage().getScaledInstance(
                        50,
                        50,
                        Image.SCALE_SMOOTH);

        // --- Columna derecha: incorrectas 1, 2, 3 ---
        lblIncorrecto = new JLabel(new ImageIcon(incorrectoEscalado));
        lblIncorrecto.setBounds(630, 550, 50, 50);
        panelSemi.add(lblIncorrecto);

        txtIncorrecta = new JTextField();
        txtIncorrecta.setFont(fuente1.deriveFont(26f));
        txtIncorrecta.setBounds(700, 550, 420, 55);
        panelSemi.add(txtIncorrecta);

        lblIncorrecto2 = new JLabel(new ImageIcon(incorrectoEscalado));
        lblIncorrecto2.setBounds(630, 620, 50, 50);
        panelSemi.add(lblIncorrecto2);

        txtIncorrecta2 = new JTextField();
        txtIncorrecta2.setFont(fuente1.deriveFont(26f));
        txtIncorrecta2.setBounds(700, 620, 420, 55);
        panelSemi.add(txtIncorrecta2);

        lblIncorrecto3 = new JLabel(new ImageIcon(incorrectoEscalado));
        lblIncorrecto3.setBounds(630, 690, 50, 50);
        panelSemi.add(lblIncorrecto3);

        txtIncorrecta3 = new JTextField();
        txtIncorrecta3.setFont(fuente1.deriveFont(26f));
        txtIncorrecta3.setBounds(700, 690, 420, 55);
        panelSemi.add(txtIncorrecta3);

        // --- Columna izquierda: incorrectas 4, 5, 6 ---
        lblIncorrecto4 = new JLabel(new ImageIcon(incorrectoEscalado));
        lblIncorrecto4.setBounds(25, 620, 50, 50);
        panelSemi.add(lblIncorrecto4);

        txtIncorrecta4 = new JTextField();
        txtIncorrecta4.setFont(fuente1.deriveFont(26f));
        txtIncorrecta4.setBounds(90, 620, 400, 55);
        panelSemi.add(txtIncorrecta4);

        lblIncorrecto5 = new JLabel(new ImageIcon(incorrectoEscalado));
        lblIncorrecto5.setBounds(25, 690, 50, 50);
        panelSemi.add(lblIncorrecto5);

        txtIncorrecta5 = new JTextField();
        txtIncorrecta5.setFont(fuente1.deriveFont(26f));
        txtIncorrecta5.setBounds(90, 690, 400, 55);
        panelSemi.add(txtIncorrecta5);

        lblIncorrecto6 = new JLabel(new ImageIcon(incorrectoEscalado));
        lblIncorrecto6.setBounds(25, 760, 50, 50);
        panelSemi.add(lblIncorrecto6);

        txtIncorrecta6 = new JTextField();
        txtIncorrecta6.setFont(fuente1.deriveFont(26f));
        txtIncorrecta6.setBounds(90, 760, 400, 55);
        panelSemi.add(txtIncorrecta6);

        // Se ocultan los campos de incorrectas que no se necesitan según la dificultad
        aplicarVisibilidadSegunDificultad();

        btnSiguiente = new DecoracionBotones("SIGUIENTE",
                //ColorBase             ColorBorde              ColorLetra
                DecoracionBotones.ROSA, DecoracionBotones.ROJO, DecoracionBotones.AMARILLO, //MOUSE FUERA
                DecoracionBotones.ROJO, DecoracionBotones.ROSA, DecoracionBotones.ROSA); //MOUSE DENTRO

        btnSiguiente.setFont(fuente2.deriveFont(25f));
        btnSiguiente.setForeground(Color.WHITE);
        btnSiguiente.setBounds(480, 830, 280, 55);
        btnSiguiente.addActionListener(e -> guardarPregunta());
        fondo.add(btnSiguiente);
        panelSemi.add(btnSiguiente);

        lblMascota = new JLabel();

        ImageIcon mascotaIcon
                = new ImageIcon(
                        getClass().getResource("/Multimedia/Minijuegos/Minijuego_3/Topo_pala.png"));

        Image mascotaEscalada
                = mascotaIcon.getImage().getScaledInstance(
                        700,
                        500,
                        Image.SCALE_SMOOTH);

        lblMascota.setIcon(new ImageIcon(mascotaEscalada));
        lblMascota.setBounds(1250, 180, 700, 500);

        fondo.add(lblMascota);

        btnSalir = new DecoracionBotones("VOLVER",
                //ColorBase             ColorBorde              ColorLetra
                DecoracionBotones.AZUL, DecoracionBotones.GRIS, DecoracionBotones.AMARILLO, //MOUSE FUERA
                DecoracionBotones.CELESTE, DecoracionBotones.AZUL, DecoracionBotones.AZUL); //MOUSE DENTRO   

        btnSalir.setFont(fuente2.deriveFont(30f));
        btnSalir.setForeground(Color.WHITE);
        btnSalir.setBounds(1695, 950, 210, 45);
        btnSalir.addActionListener(e -> dispose());
        fondo.add(btnSalir);
    }

    /**
     * Muestra u oculta los campos de respuestas incorrectas 5 y 6
     * dependiendo de la dificultad seleccionada:
     * Fácil = 4 incorrectas, Intermedio = 5, Difícil = 6.
     */
    private void aplicarVisibilidadSegunDificultad() {
        boolean mostrarQuinta = cantidadIncorrectas >= 5;
        boolean mostrarSexta = cantidadIncorrectas >= 6;

        lblIncorrecto5.setVisible(mostrarQuinta);
        txtIncorrecta5.setVisible(mostrarQuinta);

        lblIncorrecto6.setVisible(mostrarSexta);
        txtIncorrecta6.setVisible(mostrarSexta);
    }

    private String nombreDificultad(int dificultad) {
        switch (dificultad) {
            case 1:
                return "FÁCIL";
            case 2:
                return "INTERMEDIO";
            case 3:
                return "DIFÍCIL";
            default:
                return "";
        }
    }

    /**
     * Valida los campos, guarda la pregunta en la tabla Pregunta
     * y sus respuestas (correcta + incorrectas) en Opcion_respuesta.
     */
    private void guardarPregunta() {

        String pregunta = txtPregunta.getText().trim();
        String correcta = txtCorrecta.getText().trim();

        if (pregunta.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Debe ingresar la pregunta.",
                    "Campo vacío", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (correcta.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Debe ingresar la respuesta correcta.",
                    "Campo vacío", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Se arman los campos de texto de incorrectas de acuerdo a la dificultad
        JTextField[] camposIncorrectas = obtenerCamposIncorrectasActivos();

        String[] incorrectas = new String[camposIncorrectas.length];
        for (int i = 0; i < camposIncorrectas.length; i++) {
            String texto = camposIncorrectas[i].getText().trim();
            if (texto.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Debe completar todas las respuestas incorrectas ("
                        + camposIncorrectas.length + " en total para esta dificultad).",
                        "Campo vacío", JOptionPane.WARNING_MESSAGE);
                return;
            }
            incorrectas[i] = texto;
        }

        // Se valida que no haya respuestas incorrectas duplicadas o iguales a la correcta
        for (String inc : incorrectas) {
            if (inc.equalsIgnoreCase(correcta)) {
                JOptionPane.showMessageDialog(this,
                        "Una respuesta incorrecta no puede ser igual a la respuesta correcta.",
                        "Datos inválidos", JOptionPane.WARNING_MESSAGE);
                return;
            }
        }

        Connection conexion = null;
        PreparedStatement psPregunta = null;
        PreparedStatement psOpcion = null;
        ResultSet generatedKeys = null;

        try {
            conexion = new Conexion().getConnection();

            if (conexion == null) {
                JOptionPane.showMessageDialog(this,
                        "No se pudo establecer conexión con la base de datos.",
                        "Error de conexión", JOptionPane.ERROR_MESSAGE);
                return;
            }

            conexion.setAutoCommit(false);

            // 1. Insertar la pregunta
            String sqlPregunta = "INSERT INTO Pregunta (id_nivel, pregunta) VALUES (?, ?)";
            psPregunta = conexion.prepareStatement(sqlPregunta, Statement.RETURN_GENERATED_KEYS);
            psPregunta.setInt(1, idNivel);
            psPregunta.setString(2, pregunta);
            psPregunta.executeUpdate();

            generatedKeys = psPregunta.getGeneratedKeys();
            int idPregunta;
            if (generatedKeys.next()) {
                idPregunta = generatedKeys.getInt(1);
            } else {
                throw new SQLException("No se pudo obtener el ID de la pregunta generada.");
            }

            // 2. Insertar la respuesta correcta
            String sqlOpcion = "INSERT INTO Opcion_respuesta (id_pregunta, texto_opcion, es_correcta) VALUES (?, ?, ?)";
            psOpcion = conexion.prepareStatement(sqlOpcion);

            psOpcion.setInt(1, idPregunta);
            psOpcion.setString(2, correcta);
            psOpcion.setBoolean(3, true);
            psOpcion.addBatch();

            // 3. Insertar las respuestas incorrectas
            for (String inc : incorrectas) {
                psOpcion.setInt(1, idPregunta);
                psOpcion.setString(2, inc);
                psOpcion.setBoolean(3, false);
                psOpcion.addBatch();
            }

            psOpcion.executeBatch();

            conexion.commit();

            JOptionPane.showMessageDialog(this,
                    "Pregunta guardada correctamente.",
                    "Éxito", JOptionPane.INFORMATION_MESSAGE);

            limpiarFormulario();

        } catch (SQLException ex) {
            ex.printStackTrace();
            if (conexion != null) {
                try {
                    conexion.rollback();
                } catch (SQLException rollbackEx) {
                    rollbackEx.printStackTrace();
                }
            }
            JOptionPane.showMessageDialog(this,
                    "Ocurrió un error al guardar la pregunta:\n" + ex.getMessage(),
                    "Error de base de datos", JOptionPane.ERROR_MESSAGE);
        } finally {
            try {
                if (generatedKeys != null) generatedKeys.close();
                if (psPregunta != null) psPregunta.close();
                if (psOpcion != null) psOpcion.close();
                if (conexion != null) conexion.close();
            } catch (SQLException closeEx) {
                closeEx.printStackTrace();
            }
        }
    }

    /**
     * Retorna solo los campos de texto de respuestas incorrectas
     * que corresponden a la dificultad actual (4, 5 o 6 campos).
     */
    private JTextField[] obtenerCamposIncorrectasActivos() {
        switch (cantidadIncorrectas) {
            case 4:
                return new JTextField[]{txtIncorrecta, txtIncorrecta2, txtIncorrecta3, txtIncorrecta4};
            case 5:
                return new JTextField[]{txtIncorrecta, txtIncorrecta2, txtIncorrecta3, txtIncorrecta4, txtIncorrecta5};
            case 6:
            default:
                return new JTextField[]{txtIncorrecta, txtIncorrecta2, txtIncorrecta3, txtIncorrecta4, txtIncorrecta5, txtIncorrecta6};
        }
    }

    private void limpiarFormulario() {
        txtPregunta.setText("");
        txtCorrecta.setText("");
        txtIncorrecta.setText("");
        txtIncorrecta2.setText("");
        txtIncorrecta3.setText("");
        txtIncorrecta4.setText("");
        txtIncorrecta5.setText("");
        txtIncorrecta6.setText("");
        txtPregunta.requestFocus();
    }

    public static void main(String[] args) {
        // Ejemplo: categoría 1, dificultad 3 (Difícil) -> id_nivel = 21, 6 respuestas incorrectas
        new M3_crearNuevo(1, 3);
    }
}