//-------------------------- HIDDEN FOX ----------------------
package main.Usuario;

//------------------------ IMPORTACIONES ----------------------------
import java.awt.*; //Se importa java awt
import javax.swing.*; //Se importa java swing
import java.net.URL; //Se importa URL
import main.Menu.DecoracionBotones;

public abstract class HiddenFox extends JFrame implements JuegoBase {

    //Atributos
    private final JPanel fondo;

    // Imágenes cargadas para decoración del programa
    ImageIcon fondoPapelIcon, corazonFinal, corazonRotoFinal;

    // Son fuentes que se utilizan en diferentes ocasiones
    private Font fuente1, fuente2;

    // Variables utilizadas para inficar vidas, acierto, tiempo, cantidad de puntos, aumento de dificultad, etc.
    // Son textos o etiquetas que pueden almacenar rutas de imagen para convertirlas o redimensionar
    private JLabel vida1, vida2, vida3, titulo, tiempoTexto, tiempo, acierto, puntos, dificultad, categoria, mascota, imagenSombra;

    // Ruta donde se almacenará la imagen de fondo del minijuego
    JLabel fondoPapel;

    // Utilizado para realizar una versión escalada o redimensionada 
    Image fondoPapelEscalado;

    // Componentes que provienen de la clase "DecoracionBotones", los cuales son para
    // diferentes acciones dentro del minijuego
    private DecoracionBotones btnAyuda, btnRespuesta1, btnRespuesta2, btnRespuesta3, btnRespuesta4;

    // Constructor donde se encuentran las fuentes del programa
    public HiddenFox() {
        // Inicio del bloque donde puede generar un error
        try {

            //  Crea una nueva fuente llamada "fuente1"
            // Donde almacenará la fuente General para texto
            fuente1 = Font.createFont(
                    // Indica que la fuente es del tipo TrueType
                    // Utilizada para tener un formato original de la letra
                    Font.TRUETYPE_FONT,
                    // Busca y abre el archivo de la
                    // fuente dentro del proyecto         Es la ruta donde está guardando el archivo
                    getClass().getResourceAsStream("/fuentes/LettersForLearners.ttf"));

            // Crea una fuente llamada "fuente2"
            // Donde almacenará la fuente de títulos o los botones
            fuente2 = Font.createFont(
                    Font.TRUETYPE_FONT,
                    // Busca y abre el archivo              Ruta de la fuente
                    getClass().getResourceAsStream("/fuentes/KGPerfectPenmanship.ttf"));

            // Si ocurre un error en el bloque Try y muestra un mensaje en consola
            // Puede ser si el archivo no existe o la fuente está dañada
            // Permite que el programa siga funcionando aunque la fuente personalizada no esté
        } catch (Exception e) {

            // Muestra en consola información del error para ayudar a identificarlo
            e.printStackTrace();

            // Es la fuente predeterminada en caso de error
            //                          Nombre | Estilo Normal | Tamaño
            fuente1 = new Font("Arial", Font.PLAIN, 20);
            fuente2 = new Font("Arial", Font.PLAIN, 20);
        }

        //---------------- FONDO ----------------
        // Crea el panel general de la ventana
        // Servirá como superficie para agregar componentes
        fondo = new JPanel();
        //Indica que se pueden ubicar los componentes de forma libre 
        // Luego, cada elemento debe de tener sus coordenadas donde se colocará
        fondo.setLayout(null);
        // Se le asigna un color de fondo: Blanco
        fondo.setBackground(Color.WHITE);

        // Asigna el panel principal de la ventana (JFrame).
        setContentPane(fondo);

        // Se agrega título a la ventana
        setTitle("Hidden Fox");
        // Tamaño de la pantalla (ancho x alto)
        setSize(1880, 1080);
        // Se coloca la ventana al centro de la pantalla cuando se ejecute
        setLocationRelativeTo(null);
        // Al cerrar la ventana, finaliza la ejecución
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        // La ventana ocupará tota la pantalla al iniciar
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        // Método donde se inicializan los componentes de la ventana
        crearComponentes();
    }

    // Método donde se inicializan y crean los diferentes componentes con sus ajustes personalizados
    private void crearComponentes() {
        //------------------- SOMBRA ---------
        imagenSombra = new JLabel(); //Se crea el label de la imagen sombra
        imagenSombra.setBounds(350, 60, 350, 320); //Se posiciona y configura el tamaño de la sombra

        //----------------- FONDO PAPEL ----------
        fondoPapel = new JLabel(); // Se crea un label donde se encuentra  el fondo donde se encuentran las sombras
        fondoPapel.setLayout(null); // Permite que se pueda colocar cualquier coordenada 
        fondoPapel.setBounds(420, 250, 1050, 450); // Indica la posición donde se colocará el fondoPapel

        //---------------- VIDAS ----------------
        // Comienzo de bloque que contiene la lógica de la conversión de imagen de corazón
        // Puede producirse un error al momento de cargar las imágenes desde el proyecto
        try {

            // Se carga la imagen de corazón entero y corazón roto
            ImageIcon corazonIcon = new ImageIcon(
                    // Busca el archivo dentro del proyecto
                    getClass().getResource("/Multimedia/utiles/ElementosGraficos/imagenes/corazon.png"));

            // Utilizado cuando el jugador pierde una vida
            ImageIcon rotoIcon = new ImageIcon(
                    // Busca el archivo dentro del proyecto
                    getClass().getResource("/Multimedia/utiles/ElementosGraficos/imagenes/corazon-roto.png"));

            // Obtiene la imagen dentro del ImageIcon 
            // Esta conversión es importante para redimensionar la imagen y que no pierda la calidad
            Image corazonEscalado = corazonIcon.getImage().getScaledInstance(
                    // Ancho * alto    Cambio de tamaño con buena calidad
                    60, 60, Image.SCALE_SMOOTH);
            Image corazonRotoEscalado = rotoIcon.getImage().getScaledInstance(
                    60, 60, Image.SCALE_SMOOTH);

            // Se vuelve a ImageIcon luego de los cambios
            corazonFinal = new ImageIcon(corazonEscalado);
            corazonRotoFinal = new ImageIcon(corazonRotoEscalado);

            // Se crean las tres vidas por medio de JLabel
            vida1 = new JLabel(corazonFinal);
            vida2 = new JLabel(corazonFinal);
            vida3 = new JLabel(corazonFinal);

        } catch (Exception e) {
            // Muestra una excepción e indica en consola cual es el problema
            e.printStackTrace();
        }

        // Indica la posición y tamaño de los corazones en la interfaz
        vida1.setBounds(70, 25, 60, 60);
        vida2.setBounds(135, 25, 60, 60);
        vida3.setBounds(200, 25, 60, 60);

        // Se agregan los 3 corazones en la interfaz
        fondo.add(vida1);
        fondo.add(vida2);
        fondo.add(vida3);

        //---------------- TITULO ----------------
        // Pregunta generalizada para el jugador
        titulo = new JLabel(
                // Centrando de la pregunta
                "<html><center>¿Quién o qué se encuentra<br>detrás de la sombra?</center></html>",
                // Inidca que se centra el texto
                SwingConstants.CENTER);

        // Cambia el tamaño de la letra
        titulo.setFont(fuente1.deriveFont(50f));
        // Color de la letra
        titulo.setForeground(Color.BLACK);
        // Tamaño y posición 
        titulo.setBounds(400, 70, 1000, 150);

        // Agrega el componente al panel principal
        fondo.add(titulo);

        //---------------- TIEMPO ----------------
        // Etiqueta que indica el tiempo que tiene el usuario
        tiempoTexto = new JLabel("Tiempo restante:");
        // Tamaño y tipo fuente
        tiempoTexto.setFont(fuente2.deriveFont(25f));
        // Color de la fuente
        tiempoTexto.setForeground(Color.BLACK);
        // Tamaño y posición del componente
        tiempoTexto.setBounds(1450, 70, 300, 40);

        // Se agrega el componente al panel principal
        fondo.add(tiempoTexto);

        // Es la representación de los segundos que tiene el jugador
        tiempo = new JLabel("00:00:00", SwingConstants.CENTER);
        // Tamaño y tipo de fuente
        tiempo.setFont(fuente2.deriveFont(28f));
        // Establecer la opacidad 
        tiempo.setOpaque(true);
        // Indica el color de fondo del componente
        tiempo.setBackground(Color.WHITE);
        // Indica el color de la fuente
        tiempo.setForeground(Color.BLACK);
        // Indica la posición y tamaño del componente
        tiempo.setBounds(1440, 120, 320, 60);
        // Agrega el componente al panel principal
        fondo.add(tiempo);

        //---------------- B O T O N E S -------------
        //---------------- AYUDA ----------------
        // Se crea el botón con la descripción de apoyo
        btnAyuda = new DecoracionBotones("¿Necesitas ayuda?",
                //ColorBase             ColorBorde              ColorLetra
                DecoracionBotones.AMARILLO_APAGADO, DecoracionBotones.AMARILLO, DecoracionBotones.NEGRO, //MOUSE FUERA
                DecoracionBotones.AMARILLO_MOSTAZA, DecoracionBotones.AMARILLO, DecoracionBotones.NEGRO); //MOUSE DENTRO   

        // Se indica el tamaño y tipo de fuente 
        btnAyuda.setFont(fuente2.deriveFont(18f));
        // Indica la posición y tamaño del componente 
        btnAyuda.setBounds(60, 120, 280, 55);
        // Realiza la acción del método ayuda(), que es generalizado
        // Luego se cambia para mostrar los del minijuego
        btnAyuda.addActionListener(e -> ayuda());
        // Se agrega el componente al panel principal
        fondo.add(btnAyuda);

        //---------------- RESPUESTA 1 ----------------
        btnRespuesta1 = new DecoracionBotones("RESPUESTA 1",
                //ColorBase             ColorBorde              ColorLetra
                DecoracionBotones.TURQUESA, DecoracionBotones.CELESTE, DecoracionBotones.BLANCO, //MOUSE FUERA
                DecoracionBotones.CELESTE, DecoracionBotones.TURQUESA, DecoracionBotones.NEGRO); //MOUSE DENTRO   

        // Se agrega el tamaño y tipo de fuente a la letra
        btnRespuesta1.setFont(fuente2.deriveFont(20f));
        // Se indica la posición y tamaño del componente 
        btnRespuesta1.setBounds(500, 730, 300, 75);
        // Al momento de hacer click al botón, cambiará su diseño
        btnRespuesta1.estiloBotonDeshabilitado(DecoracionBotones.GRIS, DecoracionBotones.ROSA, DecoracionBotones.BLANCO);

        // Al realizar la acción de click, indicará su la respuesta es correcta 
        btnRespuesta1.addActionListener(e -> respuestaSeleccionada(btnRespuesta1));

        // Se agrega al panel principal
        fondo.add(btnRespuesta1);

        //---------------- RESPUESTA 2 ----------------
        btnRespuesta2 = new DecoracionBotones("RESPUESTA 2",
                //ColorBase             ColorBorde              ColorLetra
                DecoracionBotones.TURQUESA, DecoracionBotones.CELESTE, DecoracionBotones.BLANCO, //MOUSE FUERA
                DecoracionBotones.CELESTE, DecoracionBotones.TURQUESA, DecoracionBotones.NEGRO); //MOUSE DENTRO   

        // Se agrega el tamaño y tipo de fuente a la letra
        btnRespuesta2.setFont(fuente2.deriveFont(20f));
        // Se indica la posición y tamaño del componente 
        btnRespuesta2.setBounds(1020, 730, 300, 75);
        // Al momento de hacer click al botón, cambiará su diseño
        btnRespuesta2.estiloBotonDeshabilitado(DecoracionBotones.GRIS, DecoracionBotones.ROSA, DecoracionBotones.BLANCO);
        // Al realizar la acción de click, indicará su la respuesta es correcta 
        btnRespuesta2.addActionListener(e -> respuestaSeleccionada(btnRespuesta2));

        fondo.add(btnRespuesta2);

        //---------------- RESPUESTA 3 ----------------
        btnRespuesta3 = new DecoracionBotones("RESPUESTA 3",
                //ColorBase             ColorBorde              ColorLetra
                DecoracionBotones.TURQUESA, DecoracionBotones.CELESTE, DecoracionBotones.BLANCO, //MOUSE FUERA
                DecoracionBotones.CELESTE, DecoracionBotones.TURQUESA, DecoracionBotones.NEGRO); //MOUSE DENTRO     
        // Se agrega el tamaño y tipo de fuente a la letra
        btnRespuesta3.setFont(fuente2.deriveFont(20f));
        // Se indica la posición y tamaño 
        btnRespuesta3.setBounds(500, 840, 300, 75);
        // Al momento de hacer click al botón, cambiará su diseño
        btnRespuesta3.estiloBotonDeshabilitado(DecoracionBotones.GRIS, DecoracionBotones.ROSA, DecoracionBotones.BLANCO);
        // Al realizar la acción de click, indicará su la respuesta es correcta 
        btnRespuesta3.addActionListener(e -> respuestaSeleccionada(btnRespuesta3));
        // Se agrega
        fondo.add(btnRespuesta3);

        //---------------- RESPUESTA 4 ----------------
        btnRespuesta4 = new DecoracionBotones("RESPUESTA 4",
                //ColorBase             ColorBorde              ColorLetra
                DecoracionBotones.TURQUESA, DecoracionBotones.CELESTE, DecoracionBotones.BLANCO, //MOUSE FUERA
                DecoracionBotones.CELESTE, DecoracionBotones.TURQUESA, DecoracionBotones.NEGRO); //MOUSE DENTRO   

        // Se agrega el tamaño y tipo de fuente
        btnRespuesta4.setFont(fuente2.deriveFont(20f));
        // Se indica el tamaño y posición
        btnRespuesta4.setBounds(1020, 840, 300, 75);
        // Al momento de hacer click al botón, cambiará su diseño
        btnRespuesta4.estiloBotonDeshabilitado(DecoracionBotones.GRIS, DecoracionBotones.ROSA, DecoracionBotones.BLANCO);
        // Al realizar la acción de click, indicará su la respuesta es correcta 
        btnRespuesta4.addActionListener(e -> respuestaSeleccionada(btnRespuesta4));
        // Se agrega el botón al panel 
        fondo.add(btnRespuesta4);

        //---------------- ACIERTO ----------------
        acierto = new JLabel("Acierto: ***");
        // Se agrega el tamaño y fuente 
        acierto.setFont(fuente2.deriveFont(25f));
        // Se indica el color de la letra
        acierto.setForeground(Color.BLACK);
        // Se indica el tamaño y posición
        acierto.setBounds(80, 740, 350, 40);
        // Se agrega al panel
        fondo.add(acierto);

        // --------------- PUNTUACIÓN ---------------
        puntos = new JLabel("Puntos: 0");
        // Se agrega el tamaño y fuente
        puntos.setFont(fuente2.deriveFont(25f));
        // Se indica el color de la letra
        puntos.setForeground(Color.BLACK);
        // Se indica el tamaño y posición de la etiqueta
        puntos.setBounds(80, 690, 350, 40);

        // Se agrega al panel principal 
        fondo.add(puntos);

        //---------------- DIFICULTAD ----------------
        dificultad = new JLabel("Dificultad: ***");
        // Se agrega el tamaño y fuente del componente
        dificultad.setFont(fuente2.deriveFont(25f));
        // Se asigna el color a la letra
        dificultad.setForeground(Color.BLACK);
        // Se indica la posición y el tamaño
        dificultad.setBounds(80, 790, 350, 40);

        // Se agrega al panel principal
        fondo.add(dificultad);

        //---------------- CATEGORIA ----------------
        categoria = new JLabel("Categoría: ***");
        // Se agrega el tamaño y fuente
        categoria.setFont(fuente2.deriveFont(25f));
        // Se asigna el color de la letra
        categoria.setForeground(Color.BLACK);
        // Se agrega la posición y el tamaño
        categoria.setBounds(80, 840, 350, 40);
        // Se agrega al panel principal
        fondo.add(categoria);

        //---------------- MASCOTA ----------------
        mascota = new JLabel();
        // Se asiga la ruta de la mascota "zorrito"
        ImageIcon mascotaIcon = new ImageIcon(
                getClass().getResource("/Multimedia/utiles/mascotaKitsura/imagen/zorroLupa.png"));
        // Se redimensiona y se permanece la calidad 
        Image mascotaEscalada = mascotaIcon.getImage().getScaledInstance(
                450, 450, Image.SCALE_SMOOTH);
        
        // Se cambia de Image a ImageIcon para un aspecto profesional
        mascota.setIcon(new ImageIcon(mascotaEscalada));
        // Se indica la posición y tamaño 
        mascota.setBounds(1450, 480, 450, 450);
        // Se agrega al panel 
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
