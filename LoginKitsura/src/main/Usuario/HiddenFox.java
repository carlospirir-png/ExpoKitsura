//-------------------------- HIDDEN FOX ----------------------
package main.Usuario;

//------------------------ IMPORTACIONES ----------------------------
import java.awt.*; //Se importa java awt
import javax.swing.*; //Se importa java swing
import java.net.URL; //Se importa URL

public abstract class HiddenFox extends JFrame implements JuegoBase {

    //Atributos
    private final JPanel fondo;

    ImageIcon fondoPapelIcon, corazonFinal, corazonRotoFinal;

    private Font fuente1, fuente2;

    private JLabel vida1, vida2, vida3, titulo, tiempoTexto, tiempo, acierto, puntos, dificultad, categoria, mascota, imagenSombra;

    JLabel fondoPapel;

    Image fondoPapelEscalado;

    private JButton btnAyuda, btnRespuesta1, btnRespuesta2, btnRespuesta3, btnRespuesta4;

    public HiddenFox() {
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
        fondo.setBackground(Color.WHITE);

        setContentPane(fondo);

        setTitle("Hidden Fox");
        setSize(1880, 1080);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        crearComponentes();
    }

    private void crearComponentes() {
        //------------------- SOMBRA ---------
        imagenSombra = new JLabel(); //Se crea el label de la imagen sombra
        imagenSombra.setBounds(350, 60, 350, 320); //Se posiciona y configura el tamaño de la sombra

        //----------------- FONDO PAPEL ----------
        fondoPapel = new JLabel();
        fondoPapel.setLayout(null);
        fondoPapel.setBounds(420, 250, 1050, 450);

        //---------------- VIDAS ----------------
        try {

            ImageIcon corazonIcon = new ImageIcon(
                    getClass().getResource("/Multimedia/utiles/ElementosGraficos/imagenes/corazon.png"));

            ImageIcon rotoIcon = new ImageIcon(
                    getClass().getResource("/Multimedia/utiles/ElementosGraficos/imagenes/corazon-roto.png"));

            Image corazonEscalado = corazonIcon.getImage().getScaledInstance(
                    60, 60, Image.SCALE_SMOOTH);
            Image corazonRotoEscalado = rotoIcon.getImage().getScaledInstance(
                    60, 60, Image.SCALE_SMOOTH);

            corazonFinal = new ImageIcon(corazonEscalado);

            corazonRotoFinal = new ImageIcon(corazonRotoEscalado);

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
        btnAyuda.addActionListener(e -> ayuda());
        fondo.add(btnAyuda);

        //---------------- TITULO ----------------
        titulo = new JLabel(
                "<html><center>¿Quién o qué se encuentra<br>detrás de la sombra?</center></html>",
                SwingConstants.CENTER);

        titulo.setFont(fuente1.deriveFont(40f));
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
        tiempo.setFont(fuente2.deriveFont(28f));
        tiempo.setOpaque(true);
        tiempo.setBackground(Color.WHITE);
        tiempo.setForeground(Color.BLACK);
        tiempo.setBounds(1440, 120, 320, 60);

        fondo.add(tiempo);

        //---------------- RESPUESTA 1 ----------------
        btnRespuesta1 = new JButton("RESPUESTA 1");
        btnRespuesta1.setFont(fuente2.deriveFont(20f));
        btnRespuesta1.setBounds(500, 730, 300, 75);

        btnRespuesta1.addActionListener(e -> respuestaSeleccionada(btnRespuesta1));

        fondo.add(btnRespuesta1);

        //---------------- RESPUESTA 2 ----------------
        btnRespuesta2 = new JButton("RESPUESTA 2");
        btnRespuesta2.setFont(fuente2.deriveFont(20f));
        btnRespuesta2.setBounds(1020, 730, 300, 75);

        btnRespuesta2.addActionListener(e -> respuestaSeleccionada(btnRespuesta2));

        fondo.add(btnRespuesta2);

        //---------------- RESPUESTA 3 ----------------
        btnRespuesta3 = new JButton("RESPUESTA 3");
        btnRespuesta3.setFont(fuente2.deriveFont(20f));
        btnRespuesta3.setBounds(500, 840, 300, 75);

        btnRespuesta3.addActionListener(e -> respuestaSeleccionada(btnRespuesta3));

        fondo.add(btnRespuesta3);

        //---------------- RESPUESTA 4 ----------------
        btnRespuesta4 = new JButton("RESPUESTA 4");
        btnRespuesta4.setFont(fuente2.deriveFont(20f));
        btnRespuesta4.setBounds(1020, 840, 300, 75);

        btnRespuesta4.addActionListener(e -> respuestaSeleccionada(btnRespuesta4));

        fondo.add(btnRespuesta4);

        //---------------- ACIERTO ----------------
        acierto = new JLabel("Acierto: ***");
        acierto.setFont(fuente2.deriveFont(25f));
        acierto.setForeground(Color.BLACK);
        acierto.setBounds(80, 740, 350, 40);

        fondo.add(acierto);

        // --------------- PUNTUACIÓN ---------------
        puntos = new JLabel("Puntos: 0");
        puntos.setFont(fuente2.deriveFont(25f));
        puntos.setForeground(Color.BLACK);
        puntos.setBounds(80, 690, 350, 40);

        fondo.add(puntos);

        //---------------- DIFICULTAD ----------------
        dificultad = new JLabel("Dificultad: ***");
        dificultad.setFont(fuente2.deriveFont(25f));
        dificultad.setForeground(Color.BLACK);
        dificultad.setBounds(80, 790, 350, 40);

        fondo.add(dificultad);

        //---------------- CATEGORIA ----------------
        categoria = new JLabel("Categoría: ***");
        categoria.setFont(fuente2.deriveFont(25f));
        categoria.setForeground(Color.BLACK);
        categoria.setBounds(80, 840, 350, 40);

        fondo.add(categoria);

        //---------------- MASCOTA ----------------
        mascota = new JLabel();

        ImageIcon mascotaIcon = new ImageIcon(
                getClass().getResource("/Multimedia/utiles/mascotaKitsura/imagen/zorroLupa.png"));

        Image mascotaEscalada = mascotaIcon.getImage().getScaledInstance(
                450, 450, Image.SCALE_SMOOTH);

        mascota.setIcon(new ImageIcon(mascotaEscalada));
        mascota.setBounds(1450, 480, 450, 450);

        fondo.add(mascota);
    }


    /*---------------------------- M É T O D O S -------------------
      Este es el apartado para los métodos que modifiquen la interfaz.*/
    /*-------------------- M O D I F I C A R ----------------
      Este es el apartado donde se modifican cosas de la Interfaz Gráfica a través
      de HiddenFox_Codigo, pero aquí no se mantiene nada lógico, solo cumple con
      modificar según lo que le llegue. 
    ---------------------------------------------------------*/
    //----------------------- T E X T O S 
    //---------------------  PREGUNTA ------------------------
    public void modificarPregunta(String pregunta) {
        titulo.setText(pregunta);
    }

    //---------------------  CATEGORÍA -----------------------
    public void modificarCategoria(String nombreCategoria) {
        categoria.setText("Categoría: " + nombreCategoria);
    }

    //-------------- DIFICULTAD ----------------------
    public void modificarDificultad(String dificultadTexto) {
        dificultad.setText("Dificultad: " + dificultadTexto);
    }

    //------------ ACIERTO --------------------------
    public void modificarAcierto(String texto) {
        acierto.setText(texto);
    }
    
    //---------------- PUNTOS --------------------------
    public void actualizarPuntos(int puntosObtenidos) {
        puntos.setText("Puntos: " + puntosObtenidos);
    }

    //--------------------- I M Á G E N E S   Y  F O N D O S
    //---------------- IMAGEN SOMBRA Y A COLOR ----------------     
    public void cambiarImagen(String rutaImagen) {

        URL ruta = getClass().getResource(rutaImagen);

        try {

            if (ruta == null) {
                throw new RuntimeException("No se encontró la imagen.");
            }

            ImageIcon icono = new ImageIcon(ruta);

            Image iconoEscalado = icono.getImage().getScaledInstance(350, 320, Image.SCALE_SMOOTH);

            imagenSombra.setIcon(new ImageIcon(iconoEscalado));

            imagenSombra.revalidate();
            imagenSombra.repaint();

        } catch (Exception e) {

            e.printStackTrace();

            imagenSombra.setText("ERROR AL CARGAR IMAGEN");
            imagenSombra.setHorizontalAlignment(SwingConstants.CENTER);
            imagenSombra.setForeground(Color.RED);
        }
    }

    //------------------ FONDO DE LA SOMBRA --------------------
    public void cambiarFondoPapel(String rutaImagen) {

        URL ruta = getClass().getResource(rutaImagen);

        try {

            if (ruta == null) {
                throw new RuntimeException("No se encontró la imagen.");
            }

            fondoPapelIcon = new ImageIcon(ruta);

            fondoPapelEscalado = fondoPapelIcon.getImage().getScaledInstance(1050, 450, Image.SCALE_SMOOTH);

            fondoPapel.setIcon(new ImageIcon(fondoPapelEscalado));

            imagenSombra.revalidate();
            imagenSombra.repaint();

            fondoPapel.add(imagenSombra); //Se añade la sombra al fondo papel

            fondo.add(fondoPapel); //Se añade el fondo papel al fondo
            setVisible(true); //Se vuelve visible

        } catch (Exception e) {
            e.printStackTrace();

            imagenSombra.setText("ERROR AL CARGAR IMAGEN");
            imagenSombra.setHorizontalAlignment(SwingConstants.CENTER);
            imagenSombra.setForeground(Color.RED);
        }
    }

    //------------------------- CAMBIAR FONDO -----------------
    public void modificarColorFondo(Color color) {
        fondo.setBackground(color);
    }

    //------------------------- CORAZONES -----------------
    public void modificarCorazones(int vidas) {
        JLabel[] corazones = {
            vida1,
            vida2,
            vida3
        };

        for (int i = 0; i < corazones.length; i++) {

            if (i < vidas) {
                corazones[i].setIcon(corazonFinal);
            } else {
                corazones[i].setIcon(corazonRotoFinal);
            }
        }
        fondo.repaint();
    }

    //--------------------- B O T O N E S
    //-------------------- MOSTRAR RESPUESTAS ------------------
    public void mostrarRespuestas(String[] respuestas, boolean[] correctas) {

        JButton[] botones = {
            btnRespuesta1,
            btnRespuesta2,
            btnRespuesta3,
            btnRespuesta4
        };

        for (int i = 0; i < botones.length; i++) {

            botones[i].setText(respuestas[i]);
            botones[i].putClientProperty(
                    "correcta",
                    correctas[i]);
        }
    }

    //------------------DESHABILITARLOS ----------------------
    public void habilitarBotones(boolean estado) {

        btnRespuesta1.setEnabled(estado);
        btnRespuesta2.setEnabled(estado);
        btnRespuesta3.setEnabled(estado);
        btnRespuesta4.setEnabled(estado);

    }

    //--------------------- T I E M P O
    public void modificarTiempo(String tiempo) {
        this.tiempo.setText(tiempo);
    }

    /*----------------- A B S T R A C T O S --------------
      Este es el apartado donde se encuentran los métodos abstractos
      cuya lógica se encuentra en HiddenFox_Codigo, pero como estos
      métodos dependen de las acciones de los botones se colocan aquí.
    --------------------------------------------------------------*/
    
    public abstract void ayuda();

    public abstract void respuestaSeleccionada(JButton boton);


    /*-------------------------- G E T S -------------------------
      Este es el apartado donde se encuentran los GETS conforme se
      vayan necesitando en HiddenFox_Codigo.
    --------------------------------------------------------------*/
    
    public JPanel getFondo() {
        return fondo;
    }
}
