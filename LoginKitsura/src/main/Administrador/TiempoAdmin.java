package main.Administrador;

import java.awt.*;
import javax.swing.*;
import main.Menu.DecoracionBotones;
import main.Menu.FondoPanel;
import main.Menu.FondoPanelSemi;
// Inserts SQL
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class TiempoAdmin extends JFrame {

    private FondoPanel fondo;
    private Font fuente1;
    private Font fuente2;
    /* Se declaran como atributos para poder utilizarlos
    desde cualquier método de la clase.    */ 
    private JTextField txtNuevoTiempo;
    private JLabel lblValorActual;
    private JLabel lblMinijuego;
    private JLabel lblCategoria;
    private JLabel lblNivel;
    // CONEXION MYSQL
    /* Verificar datos segun en que maquina estan trabajando 
    */
    private final String URL = "jdbc:mysql://localhost:3306/KITSURA_DB";
    private final String USER = "root";
    private final String PASSWORD = "admin";
    // Temporalmente se mantiene fijo
    private int idNivel = 1;
    
    private DatosConfiguracion datos;
    
    public TiempoAdmin() {

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

        setTitle("Tiempo");
        setSize(1980, 1080);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        fondo.setLayout(null);
        crearComponentes();
        cargarInformacionNivel();
        setVisible(true);
    }

    private void crearComponentes() {

        //---------------- PANEL TÍTULO ----------------
        FondoPanelSemi panelTitulo = new FondoPanelSemi(new Color(0, 0, 0, 150));
        panelTitulo.setLayout(null);
        panelTitulo.setBounds(90, 65, 1800, 75);
        fondo.add(panelTitulo);

        JLabel lblTitulo = new JLabel("TIEMPO", JLabel.CENTER);
        lblTitulo.setFont(fuente2.deriveFont(45f));
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setBounds(0, 0, 1800, 75);
        panelTitulo.add(lblTitulo);

        //---------------- PANEL PRINCIPAL ----------------
        FondoPanelSemi panelTiempo = new FondoPanelSemi(new Color(0, 0, 0, 130));
        panelTiempo.setLayout(null);
        panelTiempo.setBounds(100, 170, 950, 650);
        fondo.add(panelTiempo);

        JLabel lblTiempoActual = new JLabel("Cantidad de tiempo actual por este nivel:");
        lblTiempoActual.setFont(fuente2.deriveFont(26f));
        lblTiempoActual.setForeground(Color.WHITE);
        lblTiempoActual.setBounds(60, 50, 820, 35);
        panelTiempo.add(lblTiempoActual);

        lblValorActual = new JLabel("****");
        lblValorActual.setFont(fuente1.deriveFont(32f));
        lblValorActual.setForeground(Color.WHITE);
        lblValorActual.setBounds(60, 95, 200, 40);
        panelTiempo.add(lblValorActual);

        JLabel lblInstruccion = new JLabel("Ingrese la nueva cantidad de tiempo:");
        lblInstruccion.setFont(fuente2.deriveFont(26f));
        lblInstruccion.setForeground(Color.WHITE);
        lblInstruccion.setBounds(60, 180, 820, 35);
        panelTiempo.add(lblInstruccion);

        txtNuevoTiempo = new JTextField();
        txtNuevoTiempo.setFont(fuente1.deriveFont(34f));
        txtNuevoTiempo.setBounds(60, 225, 830, 55);
        panelTiempo.add(txtNuevoTiempo);

        //---------------- BOTÓN EDITAR ----------------
        JButton btnEditar = new DecoracionBotones("EDITAR", 
                        //ColorBase             ColorBorde              ColorLetra
                DecoracionBotones.ROSA, DecoracionBotones.ROJO, DecoracionBotones.AMARILLO, //MOUSE FUERA
                DecoracionBotones.ROJO, DecoracionBotones.ROSA, DecoracionBotones.ROSA); //MOUSE DENTRO
                                
        btnEditar.setFont(fuente2.deriveFont(26f));
        btnEditar.setBounds(340, 315, 260, 60);
        btnEditar.addActionListener(e -> {
            // Acción editar tiempo
            editarTiempo();
        });

        panelTiempo.add(btnEditar);

        //---------------- INFORMACIÓN ----------------
        JLabel lblModificando = new JLabel("Está modificando:");
        lblModificando.setFont(fuente2.deriveFont(28f));
        lblModificando.setForeground(Color.WHITE);
        lblModificando.setBounds(60, 430, 350, 35);
        panelTiempo.add(lblModificando);

        lblMinijuego = new JLabel("Minijuego: ****");
        lblMinijuego.setFont(fuente1.deriveFont(40f));
        lblMinijuego.setForeground(Color.WHITE);
        lblMinijuego.setBounds(60, 485, 600, 35);
        panelTiempo.add(lblMinijuego);

        lblCategoria = new JLabel("Categoría: ****");
        lblCategoria.setFont(fuente1.deriveFont(40f));
        lblCategoria.setForeground(Color.WHITE);
        lblCategoria.setBounds(60, 525, 600, 35);
        panelTiempo.add(lblCategoria);

        lblNivel = new JLabel("Nivel: ****");
        lblNivel.setFont(fuente1.deriveFont(40f));
        lblNivel.setForeground(Color.WHITE);
        lblNivel.setBounds(60, 565, 600, 35);
        panelTiempo.add(lblNivel);

        //---------------- MASCOTA ----------------
        JLabel lblMascota = new JLabel();

        try {

            ImageIcon iconMascota = new ImageIcon(
                    getClass().getResource("/Multimedia/utiles/mascotaKitsura/imagen/zorro_tiempo.png"));

            Image imgEscalada = iconMascota.getImage().getScaledInstance(
                    700,
                    700,
                    Image.SCALE_SMOOTH);

            lblMascota.setIcon(new ImageIcon(imgEscalada));

        } catch (Exception e) {

            lblMascota.setText("~");

        }

        lblMascota.setBounds(1100, 200, 700, 700);
        fondo.add(lblMascota);

        //---------------- BOTÓN VOLVER ----------------

        JButton btnVolver = new DecoracionBotones("VOLVER",
                                //ColorBase             ColorBorde              ColorLetra
                DecoracionBotones.AZUL, DecoracionBotones.GRIS, DecoracionBotones.AMARILLO, //MOUSE FUERA
                DecoracionBotones.CELESTE, DecoracionBotones.AZUL, DecoracionBotones.AZUL); //MOUSE DENTRO   

        btnVolver.setFont(fuente2.deriveFont(28f));
        btnVolver.setBounds(1470, 870, 300, 65);

        btnVolver.addActionListener(e ->{
            new MenuAdmin(datos);
            dispose();
                });

        fondo.add(btnVolver);
    }
    
    private void cargarInformacionNivel() {

            try {

                Connection con = DriverManager.getConnection(URL, USER, PASSWORD);

                String sql =
                "SELECT " +
                "cn.tiempo_limite, " +
                "cn.dificultad, " +
                "c.nombre AS categoria, " +
                "m.nombre AS minijuego " +
                "FROM Configuracion_nivel cn " +
                "INNER JOIN Categoria c ON cn.id_categoria = c.id_categoria " +
                "INNER JOIN Minijuego m ON c.id_minijuego = m.id_minijuego " +
                "WHERE cn.id_nivel = ?";

                PreparedStatement ps = con.prepareStatement(sql);

                ps.setInt(1, idNivel);

                ResultSet rs = ps.executeQuery();

                if (rs.next()) {

                    lblValorActual.setText(rs.getInt("tiempo_limite") + " segundos");

                    lblNivel.setText("Nivel: " + rs.getString("dificultad"));

                    lblCategoria.setText(
                            "Categoría: "
                            + rs.getString("categoria"));

                    lblMinijuego.setText(
                            "Minijuego: "
                            + rs.getString("minijuego"));

                }

                rs.close();
                ps.close();
                con.close();

            } catch (Exception e) {

                JOptionPane.showMessageDialog(
                        this,
                        "Error al cargar la información\n"
                        + e.getMessage());

            }

        }

    private void editarTiempo() {
                String tiempoTexto = txtNuevoTiempo.getText().trim();

                // VALIDAR QUE EL CAMPO NO ESTÉ VACÍO
                if (tiempoTexto.isEmpty()) {
                    JOptionPane.showMessageDialog(
                            this,
                            "Ingrese un nuevo tiempo.");
                    return;
                }
                int nuevoTiempo;

                // VALIDAR QUE SOLO CONTENGA NÚMEROS
                try {
                    nuevoTiempo = Integer.parseInt(tiempoTexto);
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(
                            this,
                            "El tiempo debe contener únicamente números.");
                    return;

                }

                // VALIDAR QUE SEA MAYOR A CERO
                if (nuevoTiempo <= 0) {
                    JOptionPane.showMessageDialog(
                            this,
                            "Ingrese un tiempo mayor que cero.");
                    return;
                }
                // ACTUALIZAR EN MYSQL
                try {
                    Connection con = DriverManager.getConnection(
                            URL,
                            USER,
                            PASSWORD);
                    String sql =
                            "UPDATE Configuracion_nivel "
                          + "SET tiempo_limite = ? "
                          + "WHERE id_nivel = ?";

                    PreparedStatement ps = con.prepareStatement(sql);
                    ps.setInt(1, nuevoTiempo);
                    ps.setInt(2, idNivel);
                    int filas = ps.executeUpdate();

                    if (filas > 0) {
                        JOptionPane.showMessageDialog(
                                this,
                                "Tiempo actualizado correctamente.");

                        lblValorActual.setText(nuevoTiempo + " segundos");

                        txtNuevoTiempo.setText("");

                    } else {

                        JOptionPane.showMessageDialog(
                                this,
                                "No se encontró el nivel seleccionado.");
                    }
                    ps.close();
                    con.close();
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(
                            this,
                            "Error al actualizar el tiempo.\n\n"
                            + e.getMessage());
                }

            }
    public static void main(String[] args) {
        new TiempoAdmin();
    }
}