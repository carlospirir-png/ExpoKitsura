package main.Usuario;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;
import javax.swing.*;
import main.Menu.FondoPanelSemi;
import main.Menu.DecoracionBotones;

public class PantallaImagenPerfil extends JFrame {
    private FondoPanelSemi fondo;
    private JLabel lblTitulo,lblLogo,lblMascota;
    private DecoracionBotones btnVolver;

    private Font fuente1, fuente2;
    private JPanel panelImagenes;
    private JScrollPane scrollImagenes;

    // Referencia a la pantalla de perfil que abrió esta ventana
    private PantallaPerfil perfilAnterior;

    private static final String RUTA_BASE = "/Multimedia/utiles/ImagenesPerfil/";

    private static final String[] SECCION1 = {
        "PE_S1_N11.png", "PE_S1_N2.png", "PE_S1_N4.png", "PE_S1_N9.png"
    };
    private static final String[] SECCION2 = {
        "PE_S2_N1.png", "PE_S2_N3.png", "PE_S2_N4.png", "PE_S2_N5.png"
    };
    private static final String[] SECCION3 = {
        "PE_S3_N1.png", "PE_S3_N10.png", "PE_S3_N11.png", "PE_S3_N12.png",
        "PE_S3_N13.png", "PE_S3_N14.png", "PE_S3_N2.png", "PE_S3_N4.png",
        "PE_S3_N5.png", "PE_S3_N6.png", "PE_S3_N7.png", "PE_S3_N8.png",
        "PE_S3_N9.png"
    };

    private int cursorX = 10;
    private int cursorY = 10;
    private static final int ANCHO_IMG = 110;
    private static final int ALTO_IMG = 80;
    private static final int ESPACIO_X = 20;
    private static final int ESPACIO_Y = 30;
    private static final int COLUMNAS = 4;
    private int columnaActual = 0;

    public PantallaImagenPerfil(PantallaPerfil perfilAnterior) {
        this.perfilAnterior = perfilAnterior;

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
        fondo = new FondoPanelSemi("/Multimedia/utiles/fondos/interfaces/fondoDosK.png");
        setContentPane(fondo);
        //------------- ÍCONO ------------------
        //se obtiene la imagen del logo con getResource
        URL iconUrl = getClass().getResource("/Multimedia/utiles/logotipo/logofK.png");

        //se instancia el ícono con la imagen
        ImageIcon icono = new ImageIcon(iconUrl);

        //Se coloca el ícono al JFrame
        setIconImage(icono.getImage());

        setTitle("Imagen de Perfil");
        setSize(650, 450);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        fondo.setLayout(null);

        crearComponentes();

        setVisible(true);
    }

    private void crearComponentes() {

        lblTitulo = new JLabel("Imagen de Perfil", SwingConstants.CENTER);
        lblTitulo.setFont(fuente2.deriveFont(28f));
        lblTitulo.setBounds(175,20,300,30);
        lblTitulo.setForeground(new Color(196,221,227));
        fondo.add(lblTitulo);

        lblMascota = new JLabel();
        ImageIcon mascotaIcon = new ImageIcon(getClass().getResource("/Multimedia/utiles/mascotaKitsura/imagen/VENTANITA.png"));
        Image mascotaEscalada = mascotaIcon.getImage().getScaledInstance(140,140,Image.SCALE_SMOOTH);
        lblMascota.setIcon(new ImageIcon(mascotaEscalada));
        lblMascota.setBounds(460,15,140,140);
        fondo.add(lblMascota);

        panelImagenes = new JPanel();
        panelImagenes.setLayout(null);

        // Sección 1
        agregarTituloSeccion("Sección 1");
        for (String nombre : SECCION1) {
            agregarImagen(RUTA_BASE + "Seccion1/" + nombre);
        }
        saltarLinea();

        // Sección 2
        agregarTituloSeccion("Sección 2");
        for (String nombre : SECCION2) {
            agregarImagen(RUTA_BASE + "Seccion2/" + nombre);
        }
        saltarLinea();

        // Sección 3
        agregarTituloSeccion("Sección 3");
        for (String nombre : SECCION3) {
            agregarImagen(RUTA_BASE + "Seccion3/" + nombre);
        }

        int altoTotal = cursorY + ALTO_IMG + 20;
        panelImagenes.setPreferredSize(new Dimension(
                COLUMNAS * (ANCHO_IMG + ESPACIO_X) + 10, altoTotal));

        scrollImagenes = new JScrollPane(panelImagenes);
        scrollImagenes.setBounds(75,95,500,220);
        fondo.add(scrollImagenes);

        JButton btnVolver = new DecoracionBotones("VOLVER",
                DecoracionBotones.AZUL, DecoracionBotones.GRIS, DecoracionBotones.AMARILLO,
                DecoracionBotones.CELESTE, DecoracionBotones.AZUL, DecoracionBotones.AZUL);

        btnVolver.setFont(fuente2.deriveFont(15f));
        btnVolver.setBounds(250,350,140,40);
        btnVolver.addActionListener(e -> dispose());
        fondo.add(btnVolver);
    }

    private void agregarTituloSeccion(String texto) {
        if (columnaActual != 0) {
            saltarLinea();
        }

        JLabel titulo = new JLabel(texto);
        titulo.setFont(fuente2 != null ? fuente2.deriveFont(16f) : new Font("Arial", Font.BOLD, 16));
        titulo.setForeground(new Color(196,221,227));
        titulo.setBounds(10, cursorY, 300, 20);
        panelImagenes.add(titulo);

        cursorY += 25;
        cursorX = 10;
        columnaActual = 0;
    }

    private void agregarImagen(String ruta) {

        JLabel imagen = new JLabel();

        java.net.URL recurso = getClass().getResource(ruta);
        if (recurso == null) {
            System.err.println("No se encontró la imagen: " + ruta);
            return;
        }

        ImageIcon icono = new ImageIcon(recurso);
        Image escalada = icono.getImage().getScaledInstance(ANCHO_IMG, ALTO_IMG, Image.SCALE_SMOOTH);

        imagen.setIcon(new ImageIcon(escalada));
        imagen.setBounds(cursorX, cursorY, ANCHO_IMG, ALTO_IMG);
        imagen.setCursor(new Cursor(Cursor.HAND_CURSOR));
        imagen.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));

        // Al hacer clic, se pide confirmación antes de aplicar la imagen como nueva foto de perfil
        imagen.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                confirmarSeleccion(ruta, escalada);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                imagen.setBorder(BorderFactory.createLineBorder(Color.decode("#447A9C"), 2));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                imagen.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
            }
        });

        panelImagenes.add(imagen);

        columnaActual++;
        if (columnaActual >= COLUMNAS) {
            saltarLinea();
        } else {
            cursorX += ANCHO_IMG + ESPACIO_X;
        }
    }

    /**
     * Muestra un diálogo de confirmación con una vista previa de la imagen elegida.
     * Solo si el usuario confirma, se aplica el cambio en PantallaPerfil y se cierra esta ventana.
     */
    private void confirmarSeleccion(String ruta, Image vistaPrevia) {

        ImageIcon iconoVistaPrevia = new ImageIcon(
                vistaPrevia.getScaledInstance(150, 110, Image.SCALE_SMOOTH));

        int opcion = JOptionPane.showConfirmDialog(
                this,
                "¿Deseas usar esta imagen como tu nueva foto de perfil?",
                "Confirmar cambio de foto",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                iconoVistaPrevia);

        if (opcion == JOptionPane.YES_OPTION) {
            if (perfilAnterior != null) {
                perfilAnterior.actualizarFotoPerfil(ruta);
            }
            dispose();
        }
        // Si elige "No" o cierra el diálogo, se queda en esta pantalla sin cambiar nada
    }

    private void saltarLinea() {
        cursorX = 10;
        cursorY += ALTO_IMG + ESPACIO_Y;
        columnaActual = 0;
    }

    public static void main(String[] args) {
        new PantallaImagenPerfil(null);
    }

}