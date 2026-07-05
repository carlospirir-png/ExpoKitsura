package main.Administrador;

import java.awt.*;
import javax.swing.*;
import main.Menu.DecoracionBotones;
import main.Menu.FondoPanel;
import main.Menu.FondoPanelSemi;

public class VidasAdmin extends JFrame {

    private FondoPanel fondo;
    private Font fuente1;
    private Font fuente2;
    private DatosConfiguracion datos;

    // Variables utilizables para toda la clase
    private VidasDAO vidasDAO;
    private JLabel lblValorActual;
    private JLabel lblMinijuego;
    private JLabel lblCategoria;
    private JLabel lblNivel;
    private JTextField txtNuevasVidas;

    public VidasAdmin(DatosConfiguracion datos) {
        this.datos = datos;
        this.vidasDAO = new VidasDAO();
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

        setTitle("Vidas");
        setSize(1980, 1080);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        fondo.setLayout(null);

        crearComponentes();

        cargarDatos();

        setVisible(true);
    }

    private void crearComponentes() {

        //---------------- PANEL TÍTULO ----------------
        FondoPanelSemi panelTitulo = new FondoPanelSemi(new Color(0, 0, 0, 150));
        panelTitulo.setLayout(null);
        panelTitulo.setBounds(90, 65, 1800, 75);
        fondo.add(panelTitulo);

        JLabel lblTitulo = new JLabel("VIDAS", JLabel.CENTER);
        lblTitulo.setFont(fuente2.deriveFont(45f));
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setBounds(0, 0, 1800, 75);
        panelTitulo.add(lblTitulo);

        //---------------- PANEL PRINCIPAL ----------------
        FondoPanelSemi panelVidas = new FondoPanelSemi(new Color(0, 0, 0, 130));
        panelVidas.setLayout(null);
        panelVidas.setBounds(100, 170, 950, 650);
        fondo.add(panelVidas);

        JLabel lblVidasActuales = new JLabel("Cantidad de vidas actual por este nivel:");
        lblVidasActuales.setFont(fuente2.deriveFont(26f));
        lblVidasActuales.setForeground(Color.WHITE);
        lblVidasActuales.setBounds(60, 50, 820, 35);
        panelVidas.add(lblVidasActuales);

        lblValorActual = new JLabel("****");
        lblValorActual.setFont(fuente1.deriveFont(32f));
        lblValorActual.setForeground(Color.WHITE);
        lblValorActual.setBounds(60, 95, 200, 40);
        panelVidas.add(lblValorActual);

        JLabel lblInstruccion = new JLabel("Ingrese la cantidad de corazones para este nivel:");
        lblInstruccion.setFont(fuente2.deriveFont(26f));
        lblInstruccion.setForeground(Color.WHITE);
        lblInstruccion.setBounds(60, 180, 820, 35);
        panelVidas.add(lblInstruccion);

        txtNuevasVidas = new JTextField();
        txtNuevasVidas.setFont(fuente1.deriveFont(34f));
        txtNuevasVidas.setBounds(60, 225, 830, 55);
        panelVidas.add(txtNuevasVidas);

        //---------------- BOTÓN EDITAR ----------------
        JButton btnEditar = new DecoracionBotones("EDITAR",
                //ColorBase             ColorBorde              ColorLetra
                DecoracionBotones.ROSA, DecoracionBotones.ROJO, DecoracionBotones.AMARILLO, //MOUSE FUERA
                DecoracionBotones.ROJO, DecoracionBotones.ROSA, DecoracionBotones.ROSA); //MOUSE DENTRO

        btnEditar.setFont(fuente2.deriveFont(26f));
        btnEditar.setBounds(340, 315, 260, 60);

        btnEditar.addActionListener(e -> editarVidas());

        panelVidas.add(btnEditar);

        //---------------- INFORMACIÓN ----------------
        JLabel lblModificando = new JLabel("Está modificando:");
        lblModificando.setFont(fuente2.deriveFont(28f));
        lblModificando.setForeground(Color.WHITE);
        lblModificando.setBounds(60, 430, 350, 35);
        panelVidas.add(lblModificando);

        lblMinijuego = new JLabel();
        lblMinijuego.setFont(fuente1.deriveFont(40f));
        lblMinijuego.setForeground(Color.WHITE);
        lblMinijuego.setBounds(60, 485, 600, 35);
        panelVidas.add(lblMinijuego);

        lblCategoria = new JLabel();
        lblCategoria.setFont(fuente1.deriveFont(40f));
        lblCategoria.setForeground(Color.WHITE);
        lblCategoria.setBounds(60, 525, 600, 35);
        panelVidas.add(lblCategoria);

        lblNivel = new JLabel();
        lblNivel.setFont(fuente1.deriveFont(40f));
        lblNivel.setForeground(Color.WHITE);
        lblNivel.setBounds(60, 565, 600, 35);
        panelVidas.add(lblNivel);

        //---------------- MASCOTA ----------------
        JLabel lblMascota = new JLabel();

        try {

            ImageIcon iconMascota = new ImageIcon(
                    getClass().getResource("/Multimedia/utiles/mascotaKitsura/imagen/zorro_vidas.png"));

            Image imgEscalada = iconMascota.getImage().getScaledInstance(
                    650,
                    650,
                    Image.SCALE_SMOOTH);

            lblMascota.setIcon(new ImageIcon(imgEscalada));

        } catch (Exception e) {

            lblMascota.setText("~");

        }

        lblMascota.setBounds(1100, 220, 650, 650);
        fondo.add(lblMascota);

        //---------------- BOTÓN VOLVER ----------------
        JButton btnVolver = new DecoracionBotones("VOLVER",
                //ColorBase             ColorBorde              ColorLetra
                DecoracionBotones.AZUL, DecoracionBotones.GRIS, DecoracionBotones.AMARILLO, //MOUSE FUERA
                DecoracionBotones.CELESTE, DecoracionBotones.AZUL, DecoracionBotones.AZUL); //MOUSE DENTRO   

        btnVolver.setFont(fuente2.deriveFont(28f));
        btnVolver.setBounds(1470, 870, 300, 65);

        btnVolver.addActionListener(e -> {
            new MenuAdmin(datos);
            dispose();
        });

        fondo.add(btnVolver);
    }

    // Método que se encargará de llenar toda la interfaz
    private void cargarDatos() {
        // Al abrir la interfaz, mostrará los datos que se ingresaron en "PedidosMCN"
        lblMinijuego.setText(
                "Minijuego: " + datos.getMinijuego());

        lblCategoria.setText(
                "Categoría: " + datos.getCategoria());

        lblNivel.setText(
                "Nivel: " + datos.getNivel());

        int vidas = vidasDAO.obtenerVidas(
                datos.getMinijuego(),
                datos.getCategoria(),
                datos.getNivel());

        lblValorActual.setText(String.valueOf(vidas));
        // Llenar el JTextField
        txtNuevasVidas.setText(String.valueOf(vidas));

    }

    // Metodo editarVidas()
    public void editarVidas() {
        String texto = txtNuevasVidas.getText().trim();

        // Verifica que no esté vacío
        if (texto.isEmpty()) {
            JOptionPane.showMessageDialog(
                    this,
                    "Ingrese una cantidad de vidas.",
                    "Campo vacío",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {

            int nuevasVidas = Integer.parseInt(texto);

            // Validación del rango permitido
            if (nuevasVidas < 1 || nuevasVidas > 10) {
                JOptionPane.showMessageDialog(
                        this,
                        "La cantidad de vidas debe estar entre 1 y 10.",
                        "Dato inválido",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }
            // Temporales
            System.out.println("Minijuego: " + datos.getMinijuego());
            System.out.println("Categoría: " + datos.getCategoria());
            System.out.println("Nivel: " + datos.getNivel());
            System.out.println("Nuevas vidas: " + nuevasVidas);
            // Actualiza la base de datos
            boolean actualizado = vidasDAO.actualizarVidas(
                    datos.getMinijuego(),
                    datos.getCategoria(),
                    datos.getNivel(),
                    nuevasVidas);

            if (actualizado) {

                // Actualiza el valor mostrado en pantalla
                lblValorActual.setText(String.valueOf(nuevasVidas));

                JOptionPane.showMessageDialog(
                        this,
                        "Las vidas se actualizaron correctamente.");

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "No fue posible actualizar las vidas.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }

        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Ingrese únicamente números enteros.",
                    "Dato inválido",
                    JOptionPane.WARNING_MESSAGE);
        }
    }
}

//    public static void main(String[] args) {
//new VidasAdmin(datos);
// }
