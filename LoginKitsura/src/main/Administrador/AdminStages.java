package main.Administrador;

import java.awt.*;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.*;
import main.Menu.*;
import main.conexion.Conexion;

public class AdminStages extends JFrame {

    private FondoPanel fondo;
    private Font fuente1;
    private Font fuente2;
    private DecoracionBotones btnSalir;
    private DecoracionBotones btnCrearUno;
    private DecoracionBotones btnEditarExistente;

    private final DatosConfiguracion datos;

    public AdminStages() {
        this(null);
    }

    public AdminStages(DatosConfiguracion datos) {
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
        fondo = new FondoPanel("/Multimedia/utiles/fondos/interfaces/fondoDosK.png");
        setContentPane(fondo);
        //------------- ÍCONO ------------------
        //se obtiene la imagen del logo con getResource
        URL iconUrl = getClass().getResource("/Multimedia/utiles/logotipo/logofK.png");

        //se instancia el ícono con la imagen
        ImageIcon icono = new ImageIcon(iconUrl);

        //Se coloca el ícono al JFrame
        setIconImage(icono.getImage());

        setTitle("Administrar Stages");
        setSize(1980, 1080);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        fondo.setLayout(null);
        crearComponentes();
        setVisible(true);
    }

    private void crearComponentes() {
        FondoPanelSemi panelTitulo = new FondoPanelSemi(new Color(0, 0, 0, 120));
        panelTitulo.setBounds(75, 130, 1800, 95);
        panelTitulo.setLayout(null);
        fondo.add(panelTitulo);

        String sufijo = (datos != null)
                ? " - " + datos.getMinijuego() + " / " + datos.getCategoria() + " / " + datos.getNivel()
                : "";
        JLabel lblTituloSeccion = new JLabel("ADMINISTRAR STAGES" + sufijo, JLabel.CENTER);
        lblTituloSeccion.setFont(fuente2.deriveFont(38f));
        lblTituloSeccion.setForeground(Color.WHITE);
        lblTituloSeccion.setBounds(0, 20, 1800, 55);
        panelTitulo.add(lblTituloSeccion);

        btnCrearUno = new DecoracionBotones("CREAR UNO",
                DecoracionBotones.ROSA, DecoracionBotones.ROJO, DecoracionBotones.AMARILLO_APAGADO,
                DecoracionBotones.ROJO, DecoracionBotones.ROSA, DecoracionBotones.ROSA);

        btnCrearUno.setFont(fuente2.deriveFont(20f));
        btnCrearUno.setBounds(325, 500, 360, 70);
        btnCrearUno.addActionListener(e -> {
            // Distinto de "Administrar Stages": este flujo, al terminar en
            // Pedir M,C,N, abre DIRECTO la pantalla de creación (M1/M2/M3)
            // según el minijuego elegido, sin pasar por EditarStages.
            new PedirMCN("Crear Stage");
            dispose();
        });
        fondo.add(btnCrearUno);

        btnEditarExistente = new DecoracionBotones("EDITAR UNO EXISTENTE",
                DecoracionBotones.ROSA, DecoracionBotones.ROJO, DecoracionBotones.AMARILLO_APAGADO,
                DecoracionBotones.ROJO, DecoracionBotones.ROSA, DecoracionBotones.ROSA);
        btnEditarExistente.setFont(fuente2.deriveFont(20f));
        btnEditarExistente.setBounds(1245, 500, 360, 70);
        btnEditarExistente.addActionListener(e -> {
            // Este flujo abre EditarStages, que muestra la tabla de preguntas
            // ya guardadas para elegir cuál editar.
            new PedirMCN("Administrar Stages");
            dispose();
        });
        fondo.add(btnEditarExistente);

        JLabel staticMascotaControl = new JLabel();
        ImageIcon iconMascota = new ImageIcon(getClass().getResource("/Multimedia/utiles/mascotaKitsura/imagen/MINIJUEGO-CONTROL.png"));
        Image imgEscalada = iconMascota.getImage().getScaledInstance(600, 600, Image.SCALE_SMOOTH);
        staticMascotaControl.setIcon(new ImageIcon(imgEscalada));
        staticMascotaControl.setBounds(655, 290, 600, 600);
        fondo.add(staticMascotaControl);

        btnSalir = new DecoracionBotones("VOLVER",
                DecoracionBotones.AZUL, DecoracionBotones.GRIS, DecoracionBotones.AMARILLO,
                DecoracionBotones.CELESTE, DecoracionBotones.AZUL, DecoracionBotones.AZUL);

        btnSalir.setFont(fuente2.deriveFont(20F));
        btnSalir.setBounds(1680, 950, 210, 45);
        btnSalir.addActionListener(e -> {
            dispose();
            new MenuAdmin();
        });
        fondo.add(btnSalir);
    }

    // (Los métodos crearPreguntaEnContextoActual() y resolverContextoDesdeDatos()
    //  ya no se usan aquí: esa lógica ahora vive en PedirMCN.abrirCreacionSegunMinijuego().
    //  Puedes dejarlos o borrarlos; no afectan si se quedan sin llamar.)

    private void crearPreguntaEnContextoActual() {
        if (datos == null) {
            JOptionPane.showMessageDialog(this,
                    "Primero debes seleccionar Minijuego, Categoría y Nivel en 'Pedir M, C, N'.",
                    "Falta selección", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            int[] contexto = resolverContextoDesdeDatos();
            int idMinijuego = contexto[0];
            int idCategoriaLocal = contexto[1];
            int dificultadNumero = contexto[2];

            switch (idMinijuego) {
                case 1 ->
                    new M1_crearNuevo();
                case 2 ->
                    new M2_crearNuevo(datos);
                case 3 ->
                    new M3_crearNuevo(idCategoriaLocal, dificultadNumero);
                default ->
                    JOptionPane.showMessageDialog(this,
                            "Minijuego no reconocido (id_minijuego = " + idMinijuego + ").",
                            "Error", JOptionPane.WARNING_MESSAGE);
            }
            dispose();
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this,
                    "No se pudo determinar el nivel para la selección actual:\n" + ex.getMessage(),
                    "Error de configuración", JOptionPane.ERROR_MESSAGE);
        }
    }

    private int[] resolverContextoDesdeDatos() throws SQLException {
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
                return new int[]{idMinijuego, idCategoriaLocal, dificultadNumero};
            }
        }
    }

    public static void main(String[] args) {
        new AdminStages();
    }
}