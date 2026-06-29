package main.Usuario;

import java.awt.*;
import javax.swing.*;

public class MaulwurfRennt extends JFrame {

    private final JPanel fondo;
    private Font fuente1, fuente2;
    private ImageIcon corazonIcon, mascotaIcon;

    private JLabel vida1, vida2, vida3, titulo,
            tiempoTexto, tiempo,
            nivel, dificultad, categoria,
            mascota,
            tablero;

    private JButton btnAyuda;

    private static final int MAX_TOPOS = 7;

    private JLabel[] topos = new JLabel[MAX_TOPOS];
    private JLabel[] carteles = new JLabel[MAX_TOPOS];
    // Almacenaran las imagenes de los topos normales y golpeados
    private ImageIcon[] imagenesNormal = new ImageIcon[MAX_TOPOS];
    private ImageIcon[] imagenesGolpeado = new ImageIcon[MAX_TOPOS];

    public MaulwurfRennt() {
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

        //---------------- FONDO ----------------
        fondo = new JPanel();
        fondo.setLayout(null);
        fondo.setBackground(new Color(178, 197, 178));

        setContentPane(fondo);

        setTitle("Hidden Fox");
        setSize(1880, 1080);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        crearComponentes();
        cambiarCursorMazo();
        setVisible(true);
    }

    private void crearComponentes() {
        
        //---------------- VIDAS ----------------
        try {
            corazonIcon = new ImageIcon(
                    getClass().getResource("/Multimedia/utiles/ElementosGraficos/imagenes/corazon.png"));

            Image corazonEscalado = corazonIcon.getImage().getScaledInstance(
                    60, 60, Image.SCALE_SMOOTH);

            ImageIcon corazonFinal = new ImageIcon(corazonEscalado);

            vida1 = new JLabel(corazonFinal);
            vida2 = new JLabel(corazonFinal);
            vida3 = new JLabel(corazonFinal);

        } catch (Exception e) {
            vida1 = new JLabel("♥");
            vida2 = new JLabel("♥");
            vida3 = new JLabel("♥");

            vida1.setFont(fuente1.deriveFont(55f));
            vida2.setFont(fuente1.deriveFont(55f));
            vida3.setFont(fuente1.deriveFont(55f));

            vida1.setForeground(Color.RED);
            vida2.setForeground(Color.RED);
            vida3.setForeground(Color.RED);
        }

        vida1.setBounds(70, 25, 60, 60);
        vida2.setBounds(135, 25, 60, 60);
        vida3.setBounds(200, 25, 60, 60);

        // --------------- PANEL -----------------
        JPanel panelv = new JPanel();
        panelv.setBounds(60, 20, 200, 70);
        panelv.setBackground(Color.WHITE);
        panelv.add(vida1);
        panelv.add(vida2);
        panelv.add(vida3);
        fondo.add(panelv);

        //---------------- AYUDA ----------------
        btnAyuda = new JButton("¿Necesitas ayuda?");
        btnAyuda.setFont(fuente2.deriveFont(18f));
        btnAyuda.setBounds(60, 120, 280, 55);

        fondo.add(btnAyuda);

        //---------------- TITULO ----------------
        titulo = new JLabel("PREGUNTA", SwingConstants.CENTER);
        titulo.setFont(fuente1.deriveFont(Font.BOLD, 40f));
        titulo.setForeground(Color.BLACK);
        titulo.setBounds(500, 20, 900, 150);

        fondo.add(titulo);

        //---------------- TIEMPO ----------------
        tiempoTexto = new JLabel("Tiempo restante:");
        tiempoTexto.setFont(fuente2.deriveFont(25f));
        tiempoTexto.setForeground(Color.BLACK);
        tiempoTexto.setBounds(1450, 70, 300, 40);

        fondo.add(tiempoTexto);

        tiempo = new JLabel("00:00:00", SwingConstants.CENTER);
        tiempo.setOpaque(true);
        tiempo.setBackground(Color.WHITE);
        tiempo.setForeground(Color.BLACK);
        tiempo.setFont(fuente2.deriveFont(28f));
        tiempo.setBounds(1440, 120, 320, 60);

        fondo.add(tiempo);

        //---------------- TABLERO ----------------
        tablero = new JLabel();
        try {
            ImageIcon tableroIcon = new ImageIcon(
                    getClass().getResource("/Multimedia/Minijuegos/Minijuego_3/Fondo.png"));
            Image tableroEscalado = tableroIcon.getImage().getScaledInstance(
                    1050, 500, Image.SCALE_SMOOTH);

            tablero.setIcon(new ImageIcon(tableroEscalado));

        } catch (Exception e) {
            tablero.setOpaque(true);
            tablero.setBackground(new Color(180, 120, 60));
        }

        tablero.setLayout(null);
        tablero.setBounds(420, 230, 1050, 500);
        fondo.add(tablero);

        crearTopos();
        
        //---------------- NIVEL ----------------
        nivel = new JLabel("Nivel: ***");
        nivel.setFont(fuente2.deriveFont(25f));
        nivel.setForeground(Color.BLACK);
        nivel.setBounds(80, 750, 250, 40);

        fondo.add(nivel);

        //---------------- DIFICULTAD ----------------
        dificultad = new JLabel("Dificultad: ***");
        dificultad.setFont(fuente2.deriveFont(25f));
        dificultad.setForeground(Color.BLACK);
        dificultad.setBounds(80, 800, 250, 40);

        fondo.add(dificultad);

        //---------------- CATEGORIA ----------------
        categoria = new JLabel("Categoría: ***");
        categoria.setFont(fuente2.deriveFont(25f));
        categoria.setForeground(Color.BLACK);
        categoria.setBounds(80, 850, 250, 40);

        fondo.add(categoria);

        //---------------- MASCOTA ----------------
        mascota = new JLabel();
        try {
            mascotaIcon = new ImageIcon(
                    getClass().getResource("/Multimedia/utiles/mascotaKitsura/imagen/zorroMartillo.png"));

            Image mascotaEscalada = mascotaIcon.getImage().getScaledInstance(
                    450, 450, Image.SCALE_SMOOTH);

            mascota.setIcon(new ImageIcon(mascotaEscalada));

        } catch (Exception e) {
            mascota.setText("Mascota");
        }

        mascota.setBounds(1450, 480, 450, 450);
        fondo.add(mascota);
    }
    
    private void cambiarCursorMazo() {

        try {

            Toolkit toolkit = Toolkit.getDefaultToolkit();

            Image mazo = new ImageIcon(
                    getClass().getResource(
                            "/Multimedia/Minijuegos/Minijuego_3/Mazo.png"))
                    .getImage();

            mazo = mazo.getScaledInstance(
                    70,
                    70,
                    Image.SCALE_SMOOTH);

            Cursor cursor = toolkit.createCustomCursor(
                    mazo,
                    new Point(35, 10),
                    "Mazo");

            setCursor(cursor);

        } catch (Exception e) {

            e.printStackTrace();

        }

    }
    
    private void crearTopos() {

        int[][] posiciones = {
            {180, 0},
            {430, 0},
            {680, 0},
            {60, 180},
            {310, 180},
            {560, 180},
            {800, 180}  // Verificar estas medidaaaaas !!
        };

        String[] imagenes = {
            "/Multimedia/Minijuegos/Minijuego_3/Topo_camisa.png",
            "/Multimedia/Minijuegos/Minijuego_3/Topo_casco.png",
            "/Multimedia/Minijuegos/Minijuego_3/Topo_casco_tierra.png",
            "/Multimedia/Minijuegos/Minijuego_3/Topo_cono.png",
            "/Multimedia/Minijuegos/Minijuego_3/Topo_loco.png",
            "/Multimedia/Minijuegos/Minijuego_3/Topo_pala.png",
            "/Multimedia/Minijuegos/Minijuego_3/Topo_Lentes.png"

        };
        
        String[] imagenesHerido = {
            "/Multimedia/Minijuegos/Minijuego_3/Topo_camisa-herido.png",
            "/Multimedia/Minijuegos/Minijuego_3/Topo_casco-herido.png",
            "/Multimedia/Minijuegos/Minijuego_3/Topo_casco-herido.png",
            "/Multimedia/Minijuegos/Minijuego_3/Topo_cono-herido.png",
            "/Multimedia/Minijuegos/Minijuego_3/Topo_normal-herido.png",
            "/Multimedia/Minijuegos/Minijuego_3/Topo_pala-herido.png",
            "/Multimedia/Minijuegos/Minijuego_3/Topo_Lentes-herido.png"
        };

        for (int i = 0; i < MAX_TOPOS; i++) {

            topos[i] = new JLabel();

            try {

                ImageIcon iconoNormal = new ImageIcon(
                        getClass().getResource(imagenes[i]));

                Image imgNormal = iconoNormal.getImage().getScaledInstance(
                        200,
                        280,
                        Image.SCALE_SMOOTH);

                imagenesNormal[i] = new ImageIcon(imgNormal);

                ImageIcon iconoHerido = new ImageIcon(
                        getClass().getResource(imagenesHerido[i]));

                Image imgHerido = iconoHerido.getImage().getScaledInstance(
                        200,
                        280,
                        Image.SCALE_SMOOTH);

                imagenesGolpeado[i] = new ImageIcon(imgHerido);

                topos[i].setIcon(imagenesNormal[i]);

            } catch (Exception e) {

                topos[i].setText("TOPO");

            }

            topos[i].setBounds(
                    posiciones[i][0],
                    posiciones[i][1],
                    200,
                    280);

            carteles[i] = new JLabel(
                    "",
                    SwingConstants.CENTER);

            carteles[i].setOpaque(true);

            carteles[i].setBackground(new Color(150, 150, 150));

            carteles[i].setFont(fuente2.deriveFont(24f));

            carteles[i].setBounds(
                    posiciones[i][0] + 15,
                    posiciones[i][1] + 160,
                    170,
                    90);

            tablero.add(carteles[i]);

            tablero.add(topos[i]);

        }
        /* Cuando el usuario ingrese al juego 
        se mostraran 5 topos y cambiaran segun el nivel*/
        mostrarTopos(5);

    }

    public void mostrarTopos(int cantidad) {

        for (int i = 0; i < MAX_TOPOS; i++) {

            boolean mostrar = i < cantidad;

            topos[i].setVisible(mostrar);

            carteles[i].setVisible(mostrar);

        }

    }

    public void colocarRespuesta(int indice, String respuesta) {

        if (indice >= 0 && indice < MAX_TOPOS) {

            carteles[indice].setText(respuesta);

        }

    }

    public JLabel getTopo(int indice) {

        return topos[indice];

    }
    
    public void golpearTopo(int indice) {

        if (indice < 0 || indice >= MAX_TOPOS) {
            return;
        }

        topos[indice].setIcon(imagenesGolpeado[indice]);

        Timer timer = new Timer(300, e -> {
            topos[indice].setIcon(imagenesNormal[indice]);
        });

        timer.setRepeats(false);
        timer.start();

    }
    
    
    public void actualizarTiempo(String texto) {

        tiempo.setText(texto);

    }

    public void actualizarNivel(int n) {

        nivel.setText("Nivel: " + n);

    }

    public void actualizarCategoria(String texto) {

        categoria.setText("Categoría: " + texto);

    }

    public void actualizarVidas(int vidas) {

        vida1.setVisible(vidas >= 1);

        vida2.setVisible(vidas >= 2);

        vida3.setVisible(vidas >= 3);

    }

    /**
     * Actualiza el texto de la pregunta.
     */
    public void actualizarPregunta(String pregunta) {
        titulo.setText("<html><center>" + pregunta + "</center></html>");
    }

    /**
     * Actualiza la dificultad.
     */
    public void actualizarDificultad(String texto) {
        dificultad.setText("Dificultad: " + texto);
    }

    /**
     * Limpia los carteles de respuesta.
     */
    public void limpiarRespuestas() {

        for (JLabel cartel : carteles) {
            cartel.setText("");
        }

    }

    /**
     * Devuelve la cantidad de topos visibles.
     */
    public int getCantidadToposVisibles() {

        int cantidad = 0;

        for (JLabel topo : topos) {
            if (topo.isVisible()) {
                cantidad++;
            }
        }

        return cantidad;
    }

    /**
     * Deshabilita todos los topos.
     */
    public void bloquearTopos() {

        for (JLabel topo : topos) {
            topo.setEnabled(false);
        }

    }

    /**
     * Habilita todos los topos.
     */
    public void desbloquearTopos() {

        for (JLabel topo : topos) {
            topo.setEnabled(true);
        }

    }

    /**
     * Devuelve el botón de ayuda.
     */
    public JButton getBtnAyuda() {
        return btnAyuda;
    }

    /**
     * Muestra un mensaje al usuario.
     */
    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }
}
