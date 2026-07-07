package main.Administrador;

import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.*;
import main.Menu.FondoPanelSemi;
import main.Menu.DecoracionBotones;
import main.conexion.Conexion;

public class EditarStages extends JFrame {

    private FondoPanelSemi fondo;
    private Font fuente1, fuente2;
    private JPanel panelSemi;
    private JLabel lblTitulo;
    private JLabel lblID;
    private JTextField txtID;
    private JLabel lblMascota;
    private DecoracionBotones btnConfirmar;
    private DecoracionBotones btnSalir;

    // Referencia a la ventana de edición ya abierta, para reutilizarla si el
    // usuario cambia el ID y vuelve a presionar CONFIRMAR
    private M1_crearNuevo ventanaEdicion;

    public EditarStages() {
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

        setTitle("Editar Stages");
        setSize(1920, 1080);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        fondo.setLayout(null);

        crearComponentes();

        setVisible(true);
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
        panelSemi.setBounds(180, 180, 1000, 650);

        fondo.add(panelSemi);

        lblTitulo = new JLabel("EDITAR EXISTENTE", SwingConstants.CENTER);
        lblTitulo.setFont(fuente2.deriveFont(50f));
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setBounds(0, 40, 1000, 60);

        panelSemi.add(lblTitulo);

        lblID = new JLabel("Ingrese el ID del Stage a editar:");
        lblID.setFont(fuente2.deriveFont(32f));
        lblID.setForeground(Color.WHITE);
        lblID.setBounds(220, 240, 600, 45);

        panelSemi.add(lblID);

        txtID = new JTextField();
        txtID.setFont(fuente1.deriveFont(28f));
        txtID.setBounds(200, 340, 600, 70);

        panelSemi.add(txtID);

        lblMascota = new JLabel();

        ImageIcon mascotaIcon
                = new ImageIcon(
                        getClass().getResource("/Multimedia/utiles/mascotaKitsura/imagen/MENÚ-PRINCIPAL-LINTERNA.png"));

        Image mascotaEscalada
                = mascotaIcon.getImage().getScaledInstance(
                        750,
                        750,
                        Image.SCALE_SMOOTH);

        lblMascota.setIcon(new ImageIcon(mascotaEscalada));
        lblMascota.setBounds(1150, 140, 750, 750);

        fondo.add(lblMascota);

        btnConfirmar = new DecoracionBotones("CONFIRMAR",
                //ColorBase             ColorBorde              ColorLetra
                DecoracionBotones.ROSA, DecoracionBotones.ROJO, DecoracionBotones.AMARILLO_APAGADO, //MOUSE FUERA
                DecoracionBotones.ROJO, DecoracionBotones.ROSA, DecoracionBotones.ROSA); //MOUSE DENTRO

        btnConfirmar.setFont(fuente2.deriveFont(24f));
        btnConfirmar.setBounds(400, 510, 230, 50);

        // Acción del botón: valida el ID y abre M1_crearNuevo en modo edición
        btnConfirmar.addActionListener(e -> abrirEdicionDePregunta());

        btnSalir = new DecoracionBotones("VOLVER",
                //ColorBase             ColorBorde              ColorLetra
                DecoracionBotones.AZUL, DecoracionBotones.GRIS, DecoracionBotones.AMARILLO, //MOUSE FUERA
                DecoracionBotones.CELESTE, DecoracionBotones.AZUL, DecoracionBotones.AZUL); //MOUSE DENTRO

        btnSalir.setFont(fuente2.deriveFont(20F));
        btnSalir.setBounds(1680, 950, 210, 45);
        btnSalir.addActionListener(e -> dispose());
        fondo.add(btnSalir);

        panelSemi.add(btnConfirmar);
    }

    /**
     * Valida el ID ingresado, busca en la BD a qué minijuego/categoría/
     * dificultad pertenece esa pregunta, y abre la interfaz correspondiente
     * (M1_crearNuevo, M2_crearNuevo o M3_crearNuevo) usando sus constructores
     * tal como ya están definidos, sin modificar esas clases.
     *
     * NOTA: aquí "Stage" se interpreta como id_pregunta de la tabla Pregunta.
     * Si en tu proyecto "Stage" corresponde a otra tabla/columna, dime cuál y
     * ajusto la consulta.
     */
    private void abrirEdicionDePregunta() {
        String texto = txtID.getText().trim();

        if (texto.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Debes ingresar un ID antes de continuar.",
                    "Falta el ID", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int idPregunta;
        try {
            idPregunta = Integer.parseInt(texto);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                    "El ID debe ser un número entero.",
                    "ID inválido", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            InfoPregunta info = buscarInfoPregunta(idPregunta);
            abrirInterfazSegunMinijuego(idPregunta, info);
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this,
                    "Error al buscar la pregunta #" + idPregunta + ": " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Consulta Pregunta -> Configuracion_nivel -> Categoria para saber a qué
     * minijuego, categoría y dificultad pertenece el id_pregunta indicado.
     */
    private InfoPregunta buscarInfoPregunta(int idPregunta) throws SQLException {
        String sql = "SELECT c.id_minijuego, c.id_categoria, cn.dificultad "
                + "FROM Pregunta p "
                + "JOIN Configuracion_nivel cn ON p.id_nivel = cn.id_nivel "
                + "JOIN Categoria c ON cn.id_categoria = c.id_categoria "
                + "WHERE p.id_pregunta = ?";

        try (Connection con = new Conexion().getConnection()) {
            if (con == null) {
                throw new SQLException("No se pudo establecer conexión con la base de datos.");
            }
            try (PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setInt(1, idPregunta);
                try (ResultSet rs = ps.executeQuery()) {
                    if (!rs.next()) {
                        throw new SQLException("No existe ninguna pregunta con id_pregunta = " + idPregunta);
                    }
                    int idMinijuego = rs.getInt("id_minijuego");
                    int idCategoriaGlobal = rs.getInt("id_categoria");
                    String dificultadTexto = rs.getString("dificultad");

                    // Cada minijuego tiene exactamente 3 categorías, insertadas de forma
                    // consecutiva (1,2,3 / 4,5,6 / 7,8,9), así que el índice LOCAL dentro
                    // del minijuego (el que espera M3_crearNuevo) se calcula así:
                    int idCategoriaLocal = ((idCategoriaGlobal - 1) % 3) + 1;
                    int dificultadNumero = mapearDificultadANumero(dificultadTexto);

                    return new InfoPregunta(idMinijuego, idCategoriaLocal, dificultadNumero);
                }
            }
        }
    }

    private int mapearDificultadANumero(String dificultad) throws SQLException {
        if (dificultad == null) {
            throw new SQLException("La dificultad de la pregunta llegó vacía desde la base de datos.");
        }
        switch (dificultad) {
            case "Fácil":
                return 1;
            case "Intermedio":
                return 2;
            case "Difícil":
                return 3;
            default:
                throw new SQLException("Dificultad desconocida: '" + dificultad + "'.");
        }
    }

    /**
     * Abre la interfaz de "crear nuevo" del minijuego correspondiente, usando
     * sus constructores existentes tal cual (no se modifica ninguna de esas
     * clases): - Minijuego 1 (Hidden Fox) -> M1_crearNuevo(idPregunta) [modo
     * edición] - Minijuego 2 (Fox Jump!) -> M2_crearNuevo() - Minijuego 3
     * (Maulwurf Rennt) -> M3_crearNuevo(idCategoriaLocal, dificultad)
     */
    private void abrirInterfazSegunMinijuego(int idPregunta, InfoPregunta info) {
        switch (info.idMinijuego) {
            case 1 -> {
                // Reutilizamos la ventana de edición si ya está abierta
                if (ventanaEdicion != null && ventanaEdicion.isDisplayable()) {
                    ventanaEdicion.editar(idPregunta);
                    ventanaEdicion.toFront();
                    ventanaEdicion.requestFocus();
                } else {
                    ventanaEdicion = new M1_crearNuevo(idPregunta);
                }
            }
            case 2 ->
                new M2_crearNuevo();
            case 3 ->
                new M3_crearNuevo(info.idCategoriaLocal, info.dificultad);
            default ->
                JOptionPane.showMessageDialog(this,
                        "La pregunta #" + idPregunta + " pertenece a un id_minijuego desconocido ("
                        + info.idMinijuego + ").",
                        "Minijuego no reconocido", JOptionPane.WARNING_MESSAGE);
        }
    }

    /**
     * Pequeño contenedor con los datos que se necesitan para decidir a qué
     * interfaz enrutar (a qué minijuego pertenece la pregunta, su categoría
     * local dentro de ese minijuego, y su dificultad como número 1/2/3).
     */
    private static final class InfoPregunta {

        final int idMinijuego;
        final int idCategoriaLocal;
        final int dificultad;

        InfoPregunta(int idMinijuego, int idCategoriaLocal, int dificultad) {
            this.idMinijuego = idMinijuego;
            this.idCategoriaLocal = idCategoriaLocal;
            this.dificultad = dificultad;
        }
    }

    public static void main(String[] args) {
        new EditarStages();
    }

}
