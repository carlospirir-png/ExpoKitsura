package main.Administrador;

import java.awt.*;
import java.net.URL;
import javax.swing.*;
import main.Menu.*;

public class PuntuacionesAdmin extends JFrame {

    private FondoPanel fondo;
    private Font fuente1;
    private Font fuente2;

    private JLabel lblValorActual;
    private JTextField txtNuevaPuntuacion;

    private DatosConfiguracion datos;
    private PuntuacionesDAO dao = new PuntuacionesDAO(); //Creamos un objeto del dao

    public PuntuacionesAdmin(DatosConfiguracion datos) {

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

        fondo = new FondoPanel("/Multimedia/utiles/fondos/interfaces/fondoTresK.png");
        setContentPane(fondo);
//------------- ÍCONO ------------------
        //se obtiene la imagen del logo con getResource
        URL iconUrl = getClass().getResource("/Multimedia/utiles/logotipo/logofK.png");

        //se instancia el ícono con la imagen
        ImageIcon icono = new ImageIcon(iconUrl);

        //Se coloca el ícono al JFrame
        setIconImage(icono.getImage());

        setTitle("Puntuaciones");
        setSize(1980, 1080);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        fondo.setLayout(null);

        crearComponentes();

        setVisible(true);
    }

    private void crearComponentes() {

        //---------------- PANEL TÍTULO ----------------
        FondoPanelSemi panelTitulo = new FondoPanelSemi(new Color(130, 211, 224, 200));
        panelTitulo.setLayout(null);
        panelTitulo.setBounds(90, 65, 1800, 75);
        fondo.add(panelTitulo);

        JLabel lblTitulo = new JLabel("PUNTUACIÓN", JLabel.CENTER);
        lblTitulo.setFont(fuente2.deriveFont(45f));
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setBounds(0, 0, 1800, 75);
        panelTitulo.add(lblTitulo);

        //---------------- PANEL PRINCIPAL ----------------
        FondoPanelSemi panelPuntuacion = new FondoPanelSemi(new Color(68, 122, 156, 200));
        panelPuntuacion.setLayout(null);
        panelPuntuacion.setBounds(100, 170, 950, 650);
        fondo.add(panelPuntuacion);

        JLabel lblPuntosActuales = new JLabel("Puntuación actual por este nivel:");
        lblPuntosActuales.setFont(fuente2.deriveFont(26f));
        lblPuntosActuales.setForeground(Color.WHITE);
        lblPuntosActuales.setBounds(60, 50, 800, 35);
        panelPuntuacion.add(lblPuntosActuales);

        lblValorActual = new JLabel("****");
        lblValorActual.setFont(fuente1.deriveFont(32f));
        lblValorActual.setForeground(Color.WHITE);
        lblValorActual.setBounds(60, 95, 200, 40);
        panelPuntuacion.add(lblValorActual);

        //Se obtienen los puntos Actuales
        int puntosActuales = dao.obtenerPuntuacion(datos.getMinijuego(), datos.getCategoria(), datos.getNivel());

        //se cambia el valor
        lblValorActual.setText(String.valueOf(puntosActuales));

        JLabel lblInstruccion = new JLabel("Ingrese la nueva puntuación:");
        lblInstruccion.setFont(fuente2.deriveFont(26f));
        lblInstruccion.setForeground(Color.WHITE);
        lblInstruccion.setBounds(60, 180, 800, 35);
        panelPuntuacion.add(lblInstruccion);

        txtNuevaPuntuacion = new JTextField();
        txtNuevaPuntuacion.setFont(fuente1.deriveFont(34f));
        txtNuevaPuntuacion.setBounds(60, 225, 830, 55);
        panelPuntuacion.add(txtNuevaPuntuacion);

        //---------------- BOTÓN EDITAR ----------------
        //Se inicializa el botón de editar
        JButton btnEditar = new DecoracionBotones("EDITAR",
                //ColorBase             ColorBorde              ColorLetra
                DecoracionBotones.ROSA, DecoracionBotones.ROJO, DecoracionBotones.AMARILLO, //MOUSE FUERA
                DecoracionBotones.ROJO, DecoracionBotones.ROSA, DecoracionBotones.ROSA); //MOUSE DENTRO

        //se le coloca fuente y tamaño a la fuente
        btnEditar.setFont(fuente2.deriveFont(26f));
        //Se le posiciona y coloca tamaño
        btnEditar.setBounds(340, 315, 260, 60);

        //ActionListener
        btnEditar.addActionListener(e -> {
            Editar();
        });

        panelPuntuacion.add(btnEditar);

        //---------------- I N F O R M A C I Ó N ----------------
        //Se inicializan los Labels
        //---------------- MODIFICANDO -----------------
        JLabel lblModificando = new JLabel("Estás modificando:");
        //Fuente y tamaño de fuente
        lblModificando.setFont(fuente2.deriveFont(28f));
        //Color de fuente
        lblModificando.setForeground(Color.WHITE);
        //Posición y tamaño del label
        lblModificando.setBounds(60, 430, 400, 35);
        //Se añade al panel
        panelPuntuacion.add(lblModificando);

        //---------------- MINIJUEGO -------------------
        //Se inicializa el label
        JLabel lblMinijuego = new JLabel("Minijuego: ");
        //Se le coloca la fuente y su respectivo tamaño
        lblMinijuego.setFont(fuente1.deriveFont(40f));
        //Color de fuente
        lblMinijuego.setForeground(Color.WHITE);
        //Posición y tamaño del label
        lblMinijuego.setBounds(60, 485, 600, 35);
        //Se añade al panel
        panelPuntuacion.add(lblMinijuego);

        //Colocamos la información
        //Llamamos los datos de DatosConfiguración
        lblMinijuego.setText("Minijuego: " + datos.getMinijuego());

        //---------------- CATEGORÍA -------------------
        //Se inicializa el label
        JLabel lblCategoria = new JLabel("Categoría: ");
        //Se le coloca la fuente y su respectivo tamaño
        lblCategoria.setFont(fuente1.deriveFont(40f));
        //Se le coloca color a la fuente
        lblCategoria.setForeground(Color.WHITE);
        //Posición y tamaño al label
        lblCategoria.setBounds(60, 525, 600, 35);
        //Se agrega al panel
        panelPuntuacion.add(lblCategoria);

        //Colocamos la información
        //Llamamos los datos de DatosConfiguración
        lblCategoria.setText("Categoría: " + datos.getCategoria());

        //---------------- NIVEL -----------------------
        //Se inicializa el label
        JLabel lblNivel = new JLabel("Nivel: ");
        //Se le coloca la fuente y el tamaño
        lblNivel.setFont(fuente1.deriveFont(40f));
        //Se le coloca color a la fuente
        lblNivel.setForeground(Color.WHITE);
        //se le coloca posición y tamaño al label
        lblNivel.setBounds(60, 565, 600, 35);
        //Se agrega al panel
        panelPuntuacion.add(lblNivel);

        //Colocamos la información
        //Llamamos los datos de DatosConfiguración
        lblNivel.setText("Nivel: " + datos.getNivel());

        //---------------- MASCOTA ----------------
        JLabel mascota = new JLabel();

        try {

            ImageIcon iconMascota = new ImageIcon(
                    getClass().getResource("/Multimedia/utiles/mascotaKitsura/imagen/Zorro_samurai.png"));

            Image img = iconMascota.getImage().getScaledInstance(
                    550,
                    550,
                    Image.SCALE_SMOOTH);

            mascota.setIcon(new ImageIcon(img));

        } catch (Exception e) {

            mascota.setText("~");

        }

        mascota.setBounds(1320, 220, 550, 550);
        fondo.add(mascota);

        //---------------- BOTÓN VOLVER ----------------
        JButton btnVolver = new DecoracionBotones("VOLVER",
                //ColorBase             ColorBorde              ColorLetra
                DecoracionBotones.AZUL, DecoracionBotones.GRIS, DecoracionBotones.AMARILLO, //MOUSE FUERA
                DecoracionBotones.CELESTE, DecoracionBotones.AZUL, DecoracionBotones.AZUL); //MOUSE DENTRO   

        btnVolver.setFont(fuente2.deriveFont(28f));
        btnVolver.setBounds(1470, 870, 300, 65);

        btnVolver.addActionListener(e -> {
            new MenuAdmin();
            dispose();
        });

        fondo.add(btnVolver);

    }

    public void Editar() {

        //Obtenemos lo que se escribió en el textbox
        String punto = txtNuevaPuntuacion.getText().trim();

        //Si los puntos que se ingresaron están vacíos
        if (punto.isEmpty()) {

            //Mensaje
            JOptionPane.showMessageDialog(
                    this,
                    "Debe ingresar una puntuación.");

            return; //regresa
        }

        try {

            //Declaramos una nueva puntuacion
            int nuevaPuntuacion;

            //parseamos los números obtenidos del textbox
            nuevaPuntuacion = Integer.parseInt(punto);
            
            //Si la nueva puntuación es negativa
            if (nuevaPuntuacion < 0) {
                
                //mensaje
                JOptionPane.showMessageDialog(
                        this,
                        "La puntuación no puede ser negativa.");

                return;
            }

            //Llamamos al método para actualizar la puntuacion
            dao.actualizarPuntuacion(
                    datos.getMinijuego(),
                    datos.getCategoria(),
                    datos.getNivel(),
                    nuevaPuntuacion);

            lblValorActual.setText(String.valueOf(nuevaPuntuacion));

            JOptionPane.showMessageDialog(
                    this,
                    "La puntuación fue actualizada correctamente.");

        } catch (NumberFormatException ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "Ingrese únicamente números enteros.\n" + ex.getMessage(),
                    "Dato inválido",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

}
