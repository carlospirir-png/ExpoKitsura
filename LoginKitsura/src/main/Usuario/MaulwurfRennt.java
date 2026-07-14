// ============== MAULWURF RENNT ==============
package main.Usuario;

import java.awt.*;
import java.net.URL;
import javax.swing.Timer;
import javax.swing.*;

// Clase principal que gestiona la interfaz gráfica del minijuego "MaulwurfRennt".
public class MaulwurfRennt extends JFrame {

    // Componentes principales, recursos de texto, imágenes y fuentes del juego.
    private final JPanel fondo;
    private Font fuente1, fuente2;
    private ImageIcon corazonNormal, mascotaIcon, corazonRoto;

    // Componentes gráficos para mostrar textos dinámicos, fondos e indicadores.
    private JLabel titulo,
            tiempoTexto, tiempo,
            nivel, dificultad, categoria,
            mascota,
            tablero, lblPuntos, lblProgreso;

    private JButton btnAyuda;

    // ---------------- SISTEMA DE VIDAS DINÁMICO ----------------
    // Cantidad máxima de vidas configurada por el administrador (VidasAdmin)
    private int maxVidas;
    private JLabel[] corazones;

    // Constante que define el número máximo de topos que soporta el tablero.
    private static final int MAX_TOPOS = 7;

    // Arreglos paralelos para indexar y manipular cada topo con su cartel de texto y estados de imagen.
    private JLabel[] topos = new JLabel[MAX_TOPOS];
    private JLabel[] carteles = new JLabel[MAX_TOPOS];
    private ImageIcon[] imagenesNormal = new ImageIcon[MAX_TOPOS];
    private ImageIcon[] imagenesGolpeado = new ImageIcon[MAX_TOPOS];
    private Point[] posicionOriginal = new Point[7];

    // Constructor de la clase: Inicializa fuentes, configura el Frame y prepara el escenario gráfico.
    public MaulwurfRennt(int maxVidas) {
        this.maxVidas = (maxVidas < 1) ? 1 : maxVidas;

        // Intento de carga de fuentes tipográficas desde los recursos del sistema.
        try {
            fuente1 = Font.createFont(
                    Font.TRUETYPE_FONT,
                    getClass().getResourceAsStream("/fuentes/LettersForLearners.ttf"));

            fuente2 = Font.createFont(
                    Font.TRUETYPE_FONT,
                    getClass().getResourceAsStream("/fuentes/KGPerfectPenmanship.ttf"));

        } catch (Exception e) {
            e.printStackTrace();
            // Respaldo en caso de error al leer los archivos .ttf.
            fuente1 = new Font("Arial", Font.PLAIN, 20);
            fuente2 = new Font("Arial", Font.PLAIN, 20);
        }

        // Configuración del panel de fondo principal con diseño absoluto (null layout).
        fondo = new JPanel();
        fondo.setLayout(null);
        fondo.setBackground(new Color(178, 197, 178));

        setContentPane(fondo);
        //------------- ÍCONO ------------------
        //se obtiene la imagen del logo con getResource
        URL iconUrl = getClass().getResource("/Multimedia/utiles/logotipo/logofK.png");

        //se instancia el ícono con la imagen
        ImageIcon icono = new ImageIcon(iconUrl);

        //Se coloca el ícono al JFrame
        setIconImage(icono.getImage());

        // Propiedades de la ventana de la aplicación.
        setTitle("Maulwurf Rennt");
        setSize(1880, 1080);
        setLocationRelativeTo(null); // Centra la ventana en pantalla.
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH); // Inicia maximizado a pantalla completa.

        // Inicialización y renderizado de componentes.
        crearComponentes();
        cambiarCursorMazo();
        setVisible(true);
    }

    // Método para instanciar, posicionar (setBounds) e insertar todos los elementos visuales en el fondo.
    private void crearComponentes() {

        // Indicador de vidas: Carga imágenes de corazones con respaldo de caracteres de texto (♥).
        try {

            // Corazón normal
            ImageIcon corazonIcon = new ImageIcon(
                    getClass().getResource(
                            "/Multimedia/utiles/ElementosGraficos/imagenes/corazon.png"));

            Image corazonEscalado = corazonIcon.getImage().getScaledInstance(
                    50,
                    50,
                    Image.SCALE_SMOOTH);

            corazonNormal = new ImageIcon(corazonEscalado);

            // Corazón roto
            ImageIcon corazonRotoIcon = new ImageIcon(
                    getClass().getResource(
                            "/Multimedia/utiles/ElementosGraficos/imagenes/corazon-roto.png"));

            Image corazonRotoEscalado = corazonRotoIcon.getImage().getScaledInstance(
                    50,
                    50,
                    Image.SCALE_SMOOTH);

            corazonRoto = new ImageIcon(corazonRotoEscalado);

        } catch (Exception e) {
            // Si fallan las imágenes, se recurre al carácter de texto "♥" como respaldo
            corazonNormal = null;
            corazonRoto = null;
        }

        // Se generan dinámicamente tantos corazones como "maxVidas" indique
        corazones = new JLabel[maxVidas];

        int xInicial = 70;
        int yInicial = 25;
        int espaciado = 55;
        int tamano = 50;

        // Todos los corazones (de 1 a 10) en UNA sola línea, tamaño fijo
        for (int i = 0; i < maxVidas; i++) {

            JLabel corazon;
            if (corazonNormal != null) {
                corazon = new JLabel(corazonNormal);
            } else {
                corazon = new JLabel("♥");
                corazon.setFont(fuente1.deriveFont(55f));
                corazon.setForeground(Color.RED);
            }

            corazon.setBounds(
                    xInicial + (i * espaciado),
                    yInicial,
                    tamano, tamano);

            corazones[i] = corazon;
            fondo.add(corazon);
        }

        // Botón de ayuda.
        btnAyuda = new JButton("¿Necesitas ayuda?");
        btnAyuda.setFont(fuente2.deriveFont(18f));
        btnAyuda.setBounds(60, 120, 280, 55);
        fondo.add(btnAyuda);

        // Título o enunciado de la pregunta.
        titulo = new JLabel("PREGUNTA", SwingConstants.CENTER);
        titulo.setFont(fuente2.deriveFont(25f));
        titulo.setForeground(Color.BLACK);
        titulo.setBounds(500, 20, 900, 150);
        fondo.add(titulo);

        // Cronómetro de tiempo restante.
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

        // Tablero central del juego donde emergen los topos y carteles
        tablero = new JLabel();
        try {
            ImageIcon tableroIcon = new ImageIcon(
                    getClass().getResource("/Multimedia/Minijuegos/Minijuego_3/Fondo.jpg"));
            Image tableroEscalado = tableroIcon.getImage().getScaledInstance(
                    1180, 620, Image.SCALE_SMOOTH);

            tablero.setIcon(new ImageIcon(tableroEscalado));

        } catch (Exception e) {
            tablero.setOpaque(true);
            tablero.setBackground(new Color(180, 120, 60)); // Respaldo color café si falta la imagen
        }

        tablero.setLayout(null);
        tablero.setBounds(420, 230, 1050, 500);
        fondo.add(tablero);

        // Inicialización de los topos dentro del tablero
        crearTopos();

        //---------------- PROGRESO ----------------
        lblProgreso = new JLabel("Progreso: 0/5");

        lblProgreso.setFont(fuente2.deriveFont(24f));
        lblProgreso.setForeground(Color.BLACK);
        lblProgreso.setBounds(80, 750, 250, 40);

        fondo.add(lblProgreso);

        //---------------- PUNTOS ----------------
        lblPuntos = new JLabel("Puntos: 0");

        lblPuntos.setFont(fuente2.deriveFont(24f));
        lblPuntos.setForeground(Color.BLACK);
        lblPuntos.setBounds(80, 800, 250, 40);

        fondo.add(lblPuntos);

        // Etiquetas informativas (Nivel, Dificultad, Categoría)
        nivel = new JLabel("Nivel: ***");
        nivel.setFont(fuente2.deriveFont(25f));
        nivel.setForeground(Color.BLACK);
        nivel.setBounds(80, 850, 250, 40);
        fondo.add(nivel);

        dificultad = new JLabel("Dificultad: ***");
        dificultad.setFont(fuente2.deriveFont(23f));
        dificultad.setForeground(Color.BLACK);
        dificultad.setBounds(80, 900, 250, 40);
        fondo.add(dificultad);

        categoria = new JLabel("Categoría: ***");
        categoria.setFont(fuente2.deriveFont(25f));
        categoria.setForeground(Color.BLACK);
        categoria.setBounds(80, 950, 500, 40);
        fondo.add(categoria);

        // Ilustración de la mascota guía
        mascota = new JLabel();
        try {
            mascotaIcon = new ImageIcon(
                    getClass().getResource("/Multimedia/utiles/mascotaKitsura/imagen/zorroMartillo.png"));

            Image mascotaEscalada = mascotaIcon.getImage().getScaledInstance(
                    580, 580, Image.SCALE_SMOOTH);

            mascota.setIcon(new ImageIcon(mascotaEscalada));

        } catch (Exception e) {
            mascota.setText("Mascota");
        }

        mascota.setBounds(1410, 480, 580, 580);
        fondo.add(mascota);
    }

    public void cambiarColorFondo(Color color) {

        fondo.setOpaque(true);
        fondo.setBackground(color);
        fondo.revalidate();
        fondo.repaint();

    }

    // Modifica el puntero del mouse para renderizar la imagen de un mazo o martillo
    private void cambiarCursorMazo() {
        try {
            Toolkit toolkit = Toolkit.getDefaultToolkit();

            Image mazo = new ImageIcon(
                    getClass().getResource("/Multimedia/Minijuegos/Minijuego_3/Mazo.png"))
                    .getImage();

            mazo = mazo.getScaledInstance(20, 20, Image.SCALE_SMOOTH);

            // Define la imagen, el punto crítico de colisión (5,5) y el nombre del cursor
            Cursor cursor = toolkit.createCustomCursor(
                    mazo,
                    new Point(5, 5),
                    "Mazo");

            setCursor(cursor);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Instancia los topos y sus carteles, escala sus imágenes y los añade al tablero según una matriz de posiciones.
    private void crearTopos() {

        // Matriz de coordenadas fijas (X, Y) relativas al panel del tablero.
        int[][] posiciones = {
            {210, 17},
            {440, 22},
            {675, 22},
            {100, 200},
            {330, 200},
            {570, 195},
            {810, 200}
        };

        // Rutas de los recursos de imagen para estados normales y heridos.
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

                // Imagen normal
                ImageIcon iconoNormal = new ImageIcon(
                        getClass().getResource(imagenes[i]));

                Image imgNormal = iconoNormal.getImage().getScaledInstance(
                        200, 280, Image.SCALE_SMOOTH);

                imagenesNormal[i] = new ImageIcon(imgNormal);

                // Imagen golpeado
                ImageIcon iconoHerido = new ImageIcon(
                        getClass().getResource(imagenesHerido[i]));

                Image imgHerido = iconoHerido.getImage().getScaledInstance(
                        200, 280, Image.SCALE_SMOOTH);

                imagenesGolpeado[i] = new ImageIcon(imgHerido);

                topos[i].setIcon(imagenesNormal[i]);

            } catch (Exception e) {

                topos[i].setText("TOPO");
            }

            // Posición del topo
            topos[i].setBounds(
                    posiciones[i][0],
                    posiciones[i][1],
                    200,
                    280);

            // Guarda la posición original para las animaciones
            posicionOriginal[i] = new Point(
                    posiciones[i][0],
                    posiciones[i][1]);

            // Cartel de respuestas
            carteles[i] = new JLabel("", SwingConstants.CENTER);
            carteles[i].setOpaque(true);
            carteles[i].setBackground(new Color(118, 169, 170));
            carteles[i].setFont(fuente2.deriveFont(15f));

            carteles[i].setBounds(
                    posiciones[i][0] + 5,
                    posiciones[i][1] + 160,
                    210,
                    80);

            tablero.add(carteles[i]);
            tablero.add(topos[i]);
        }

        // Muestra cinco topos al iniciar el juego
        mostrarTopos(5);
    }

    public void ocultarTopo(int indice) {

        JLabel topo = topos[indice];

        int yFinal = posicionOriginal[indice].y + 90;

        Timer timer = new Timer(10, null);

        timer.addActionListener(e -> {

            if (topo.getY() < yFinal) {

                topo.setLocation(
                        topo.getX(),
                        topo.getY() + 5);

            } else {

                ((Timer) e.getSource()).stop();
            }

        });

        timer.start();
    }

    public void mostrarTopo(int indice) {

        JLabel topo = topos[indice];

        topo.setLocation(
                topo.getX(),
                posicionOriginal[indice].y + 90);

        Timer timer = new Timer(10, null);

        timer.addActionListener(e -> {

            if (topo.getY() > posicionOriginal[indice].y) {

                topo.setLocation(
                        topo.getX(),
                        topo.getY() - 5);

            } else {

                topo.setLocation(
                        topo.getX(),
                        posicionOriginal[indice].y);

                ((Timer) e.getSource()).stop();
            }

        });

        timer.start();
    }

    public void animarEntradaTopos() {

        for (int i = 0; i < topos.length; i++) {

            if (topos[i].isVisible()) {

                mostrarTopo(i);
            }
        }
    }

    public void animarSalidaTopos() {

        for (int i = 0; i < topos.length; i++) {

            if (topos[i].isVisible()) {

                ocultarTopo(i);
            }
        }
    }

    // Controla cuántos topos y carteles se hacen visibles en la pantalla según el nivel.
    public void mostrarTopos(int cantidad) {
        for (int i = 0; i < MAX_TOPOS; i++) {
            boolean mostrar = i < cantidad;
            topos[i].setVisible(mostrar);
            carteles[i].setVisible(mostrar);
        }
    }

    // Reubica aleatoriamente las coordenadas de los topos activos para desordenar el tablero. 
    public void mezclarTopos(int cantidad) {
//        Point[] posiciones = {
//            new Point(180, 0),
//            new Point(430, 0),
//            new Point(680, 0),
//            new Point(60, 180),
//            new Point(310, 180),
//            new Point(560, 180),
//            new Point(800, 180)
//        };
//
//        java.util.ArrayList<Point> lista = new java.util.ArrayList<>();
//        for (Point p : posicionOriginal) {
//            lista.add(p);
//        }
//
//        // Desordena aleatoriamente la lista de puntos.
//        Collections.shuffle(lista);
//
//        // Aplica las nuevas posiciones a los componentes visibles.
//        for (int i = 0; i < cantidad; i++) {
//            Point p = lista.get(i);
//            topos[i].setLocation(p);
//            carteles[i].setLocation(
//                    p.x + 15,
//                    p.y + 160);
//        }
    }

    public void actualizarProgreso(int realizadas, int total) {

        lblProgreso.setText("Progreso: " + realizadas + "/" + total);

    }

    public void actualizarPuntos(int puntos) {
        lblPuntos.setText("Puntos: " + puntos);

    }

    public void actualizarNivel(int Nivel) {
        nivel.setText("Nivel: " + Nivel);
    }

    public void actualizarDificultad(String Dificultad) {
        dificultad.setText("Dificultad: " + Dificultad);

    }

    public void actualizarCategoria(String Categoria) {
        categoria.setText("Categoría: " + Categoria);
    }

    // Asigna el texto de la respuesta al cartel del índice indicado.
    public void colocarRespuesta(int indice, String respuesta) {
        if (indice >= 0 && indice < MAX_TOPOS) {
            carteles[indice].setText(respuesta);
        }
    }

    // Retorna el JLabel del topo solicitado mediante su índice.
    public JLabel getTopo(int indice) {
        return topos[indice];
    }

    // Cambia el icono gráfico del topo a su estado "herido".
    public void golpearTopo(int indice) {
        if (indice < 0 || indice >= MAX_TOPOS) {
            return;
        }
        topos[indice].setIcon(imagenesGolpeado[indice]);
    }

    // Restablece el icono gráfico de un topo a su versión estándar normal.
    public void restaurarTopo(int indice) {
        if (indice < 0 || indice >= MAX_TOPOS) {
            return;
        }
        topos[indice].setIcon(imagenesNormal[indice]);
    }

    // Devuelve a todos los topos del arreglo a su imagen normal.
    public void restaurarTodosLosTopos() {
        for (int i = 0; i < MAX_TOPOS; i++) {
            restaurarTopo(i);
        }
    }

    // Actualiza el texto del indicador de tiempo.
    public void actualizarTiempo(String texto) {
        tiempo.setText(texto);
    }

    // Muestra u oculta los corazones de vida según el número de vidas restantes.
    public void actualizarVidas(int vidas) {

        // Recorre el arreglo dinámico (tamaño = maxVidas), ya no limitado a 3.
        for (int i = 0; i < corazones.length; i++) {

            if (i < vidas) {
                corazones[i].setIcon(corazonNormal);
            } else {
                corazones[i].setIcon(corazonRoto);
            }

        }

    }

    // Permite que JuegoMaulwurfRennt conozca el tope de vidas configurado
    // (por ejemplo, para decidir si la partida fue "perfecta").
    public int getMaxVidas() {
        return maxVidas;
    }

    public void reconstruirVidas(int nuevoMaxVidas) {
        if (nuevoMaxVidas < 1) {
            nuevoMaxVidas = 1;
        }
        if (nuevoMaxVidas == this.maxVidas) {
            return;
        }

        for (JLabel corazon : corazones) {
            fondo.remove(corazon);
        }

        this.maxVidas = nuevoMaxVidas;
        corazones = new JLabel[maxVidas];

        int xInicial = 70;
        int yInicial = 25;
        int espaciado = 55;
        int tamano = 50;

        // Todos los corazones (de 1 a 10) en UNA sola línea, tamaño fijo.
        for (int i = 0; i < maxVidas; i++) {

            JLabel corazon;
            if (corazonNormal != null) {
                corazon = new JLabel(corazonNormal);
            } else {
                corazon = new JLabel("♥");
                corazon.setFont(fuente1.deriveFont(55f));
                corazon.setForeground(Color.RED);
            }

            corazon.setBounds(
                    xInicial + (i * espaciado),
                    yInicial,
                    tamano, tamano);

            corazones[i] = corazon;
            fondo.add(corazon);
        }

        fondo.revalidate();
        fondo.repaint();
    }

    // Cambia el enunciado de la pregunta usando HTML para habilitar el salto de línea automático y centrado.
    public void actualizarPregunta(String pregunta) {
        titulo.setText("<html><center>" + pregunta + "</center></html>");
    }

    // Borra el texto de todos los carteles de respuesta.
    public void limpiarRespuestas() {
        for (JLabel cartel : carteles) {
            cartel.setText("");
        }
    }

    // Cuenta y retorna cuántos topos están actualmente visibles en el tablero.
    public int getCantidadToposVisibles() {
        int cantidad = 0;
        for (JLabel topo : topos) {
            if (topo.isVisible()) {
                cantidad++;
            }
        }
        return cantidad;
    }

    // Retorna la instancia del botón de ayuda para poder asignarle listeners externos.
// Retorna la instancia del botón de ayuda para poder asignarle listeners externos.
    public JButton getBtnAyuda() {
        
        return btnAyuda;
    }

// Muestra u oculta el botón de ayuda según si la categoría actual admite pistas.
// Las categorías de operaciones matemáticas (Básicas/Avanzadas) no la necesitan;
// solo tiene sentido en "Científicos Matemáticos", que sí tiene preguntas teóricas.
    public void mostrarBotonAyuda(boolean mostrar) {
        btnAyuda.setVisible(mostrar);
    }

    // Despliega un cuadro de diálogo emergente (JOptionPane) con un mensaje.
    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }

    // Hace visible la ventana del juego tras interactuar con la selección de dificultad.
    public void continuarDespuesDeDificultad() {
        setVisible(true);
    }

}
