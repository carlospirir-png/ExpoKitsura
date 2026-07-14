// ============== HIDDEN FOX ==============
// ==================== HIDDEN FOX ==================== 
package main.Usuario;

//------------------------ IMPORTACIONES ----------------------------
import java.awt.*; //Se importa java awt
import javax.swing.*; //Se importa java swing
import java.net.URL; //Se importa URL
import main.Menu.DecoracionBotones;

public abstract class HiddenFox extends JFrame implements JuegoBase {

    //------------------------------ A T R I B U T O S  -------------------------
    
    //Atributos
    private final JPanel fondo;

    // Imágenes cargadas para decoración del programa
    ImageIcon fondoPapelIcon, corazonFinal, corazonRotoFinal;

    // Son fuentes que se utilizan en diferentes ocasiones
    private Font fuente1, fuente2;

    // Variables utilizadas para inficar acierto, tiempo, cantidad de puntos, aumento de dificultad, etc.
    // Son textos o etiquetas que pueden almacenar rutas de imagen para convertirlas o redimensionar
    private JLabel titulo, tiempoTexto, tiempo, acierto, puntos, dificultad, categoria, mascota, imagenSombra;
    
    // Cantidad máxima de vidas configurada por el administrador (VidasAdmin) para
    // el minijuego/categoría/dificultad correspondiente. 
    private int maxVidas;

    // Arreglo de corazones generado dinámicamente según "maxVidas"
    private JLabel[] corazones;

    // Ruta donde se almacenará la imagen de fondo del minijuego
    JLabel fondoPapel;

    // Utilizado para realizar una versión escalada o redimensionada 
    Image fondoPapelEscalado;

    // Componentes que provienen de la clase "DecoracionBotones", los cuales son para
    // diferentes acciones dentro del minijuego
    private DecoracionBotones btnAyuda, btnRespuesta1, btnRespuesta2, btnRespuesta3, btnRespuesta4;
    
    //-----------------------------  C O N S T R U C T O R --------------------------------------
   
    // Constructor donde se encuentran las fuentes del programa
    // "maxVidas" es la cantidad de corazones que debe dibujar la interfaz,
    // obtenida previamente desde la base de datos (tabla Configuracion_nivel)
    // por medio de VidasDAO
    public HiddenFox(int maxVidas) {
        
        // Resguardo por si llega un valor inválido (0 o negativo)
        this.maxVidas = (maxVidas < 1) ? 1 : maxVidas;

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
        
        //----------------- JFRAME -----------------
        
        //------------- ÍCONO ------------------
        //se obtiene la imagen del logo con getResource
        URL iconUrl = getClass().getResource("/Multimedia/utiles/logotipo/logofK.png");

        //se instancia el ícono con la imagen
        ImageIcon icono = new ImageIcon(iconUrl);

        //Se coloca el ícono al JFrame
        setIconImage(icono.getImage());
        
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
        //------------------ ICONO -------------
        // Cargamos el icono
        
        
        
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
                    50, 50, Image.SCALE_SMOOTH);
            Image corazonRotoEscalado = rotoIcon.getImage().getScaledInstance(
                    50, 50, Image.SCALE_SMOOTH);

            // Se vuelve a ImageIcon luego de los cambios
            corazonFinal = new ImageIcon(corazonEscalado);
            corazonRotoFinal = new ImageIcon(corazonRotoEscalado);

        } catch (Exception e) {
            // Muestra una excepción e indica en consola cual es el problema
            e.printStackTrace();
        }

        // Se generan dinámicamente tantos corazones como "maxVidas" indique
        // Es el valor obtenido desde la base de datos por medio de VidasAdmin y VidasDAO
        corazones = new JLabel[maxVidas];

        int xInicial = 70;
        int yInicial = 25;
        int espaciado = 55;
        int tamano = 50;

        // Todos los corazones (de 1 a 10) se dibujan en UNA sola línea
        for (int i = 0; i < maxVidas; i++) {

            JLabel corazon = new JLabel(corazonFinal);
            corazon.setBounds(
                    xInicial + (i * espaciado),
                    yInicial,
                    tamano, tamano);

            corazones[i] = corazon;
            fondo.add(corazon);
        }

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
        btnRespuesta1.setBounds(500, 730, 350, 75);
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
        btnRespuesta2.setBounds(1050, 730, 350, 75);
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
        btnRespuesta3.setBounds(500, 840, 350, 75);
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
        btnRespuesta4.setBounds(1050, 840, 350, 75);
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

/*---------------------------- M É T O D O S -----------------------------------
      Este es el apartado para los métodos que modifiquen la interfaz.*/
    /*---------------------- M O D I F I C A R -------------------------
      Este es el apartado donde se modifican cosas de la Interfaz 
      Gráfica a través de HiddenFox_Codigo, pero aquí no se mantiene nada
      lógico, solo cumple con modificar según lo que le llegue. 
    -------------------------------------------------------------------*/
    
    //----------------------- T E X T O S 
    //---------------------  PREGUNTA ------------------------
    public void modificarPregunta(String pregunta) {
        //Recibe el texto de la pregunta por el parámetro
        //Y luego establece el texto en el label correspondiente
        titulo.setText(pregunta);
    }

    //---------------------  CATEGORÍA -----------------------
    public void modificarCategoria(String nombreCategoria) {
        //Recibe el nombre de la categoría por el parámetro
        //Luego se establece la categoría en el label correspondiente
        categoria.setText("Categoría: " + nombreCategoria);
    }

    //---------------------  DIFICULTAD ----------------------
    public void modificarDificultad(String dificultadTexto) {
        //Recibe la dificultad a través del parámetro
        //Luego se establece la dificultad en el label correspondiente
        dificultad.setText("Dificultad: " + dificultadTexto);
    }

    //---------------------  ACIERTO -------------------------
    public void modificarAcierto(String texto) {
        //Recibe el acierto a través del parámetro
        //Luego se establece el acierto en el label correspondiente
        acierto.setText(texto);
    }

    //---------------------  PUNTOS --------------------------
    public void actualizarPuntos(int puntosObtenidos) {
        //Recibe la cantidad de puntos obtenidos por el parámetro
        //Luego se establece la cantidad de puntos en el label
        puntos.setText("Puntos: " + puntosObtenidos);
    }

    //--------------------- I M Á G E N E S   Y  F O N D O S
    //---------------- IMAGEN SOMBRA Y A COLOR ----------------     
    public void cambiarImagen(String rutaImagen) {
        /*El método recibe un String llamado rutaImagen a través del parámetro.
        Se usa getResorce para obtener la URL del recurso.*/
        
        URL ruta = getClass().getResource(rutaImagen);

        try {
       
            //Si getResource() no encontró el recurso
            if (ruta == null) {
                //Se lanza una excepción no comprobada
                throw new RuntimeException("No se encontró la imagen.");
            }
            
            //Se crea un nuevo ImageIcon con la imagen de la ruta
            ImageIcon icono = new ImageIcon(ruta);
            
            //Obtenemos la imagen del ImageIcon para poder escalarla
            Image imagen = icono.getImage();
            
            //Se escala la imagen a 350px X 320px con el algoritmo smooth
            Image iconoEscalado = imagen.getScaledInstance(350, 320, Image.SCALE_SMOOTH);
            
            //Se crea una nueva ImageIcon con la imagen escalada
            ImageIcon imagenEscalada = new ImageIcon(iconoEscalado);
            
            //Se le coloca al label la nueva ImageIcon.
            imagenSombra.setIcon(imagenEscalada);
            
            //Revalida
            imagenSombra.revalidate();
            
            //Re-dibuja
            imagenSombra.repaint();
            
        //Captura la excepción
        } catch (RuntimeException e) {
            //Imprime el StackTrace
            e.printStackTrace();
            
            //Se le coloca el texto de error
            imagenSombra.setText("ERROR AL CARGAR IMAGEN");
            //Centramos
            imagenSombra.setHorizontalAlignment(SwingConstants.CENTER);
            //Letra roja
            imagenSombra.setForeground(Color.RED);
        }
    }

    //------------------ FONDO DE LA SOMBRA --------------------
    public void cambiarFondoPapel(String rutaImagen) {
        //Recibe la ruta a través del parámetro
        
        //Se obtiene la imagen
        URL ruta = getClass().getResource(rutaImagen);

        try {
            
            //si no se encuentra
            if (ruta == null) {
                //Lanza excepción
                throw new RuntimeException("No se encontró la imagen.");
            }
            
            //Se le coloca un nuevo icon con la imagen
            fondoPapelIcon = new ImageIcon(ruta);
            
            //Se obtiene la imagen del icon, se escala a 1050px X 450px y con el algoritmo Smooth
            fondoPapelEscalado = fondoPapelIcon.getImage().getScaledInstance(1050, 450, Image.SCALE_SMOOTH);
            
            //Se le coloca un nuevo icon con la imagen escalada
            fondoPapel.setIcon(new ImageIcon(fondoPapelEscalado));

            fondoPapel.add(imagenSombra); //Se añade la sombra al fondo papel

            fondo.add(fondoPapel); //Se añade el fondo papel al fondo
            
            //Revalida
            imagenSombra.revalidate();
            
            //Re-dibuja
            imagenSombra.repaint();
            
            setVisible(true); //Se vuelve visible

        //Captura la excepción
        } catch (RuntimeException e) {
            //Imprime el StackTrace
            e.printStackTrace();
            
            //Se le coloca el texto de error
            imagenSombra.setText("ERROR AL CARGAR IMAGEN");
            //Centramos
            imagenSombra.setHorizontalAlignment(SwingConstants.CENTER);
            //Letra roja
            imagenSombra.setForeground(Color.RED);
        }
    }

    //------------------------- CAMBIAR FONDO -----------------
    public void modificarColorFondo(Color color) {
        //Recibe el color del parámetro
        fondo.setBackground(color); //Coloca el color de fondo
    }

    //------------------------- CORAZONES -----------------
    public void modificarCorazones(int vidas) {
        //Recibe las vidas en el parámetro

        //Recorre el arreglo dinámico de corazones (su tamaño es "maxVidas",
        //ya no está limitado a 3 como antes)
        for (int i = 0; i < corazones.length; i++) {

            //Si el índice no supera la cantidad de vidas actuales
            if (i < vidas) {
                //Ese label se le coloca el corazón completo
                corazones[i].setIcon(corazonFinal);
            //Si ahora hay menos corazones ingresados, el faltante:
            } else {
                //Se le coloca el corazón roto
                corazones[i].setIcon(corazonRotoFinal);
            }
        }
        
        //Re-dibuja
        fondo.repaint();
    }

    //------------------------- MAX VIDAS -----------------
    // Permite saber cuál es el tope de vidas configurado
    public int getMaxVidas() {
        return maxVidas;
    }

    //------------------- INICIALIZAR VIDAS  -----------------
    // Este método reconstruye el arreglo de corazones con el valor real
    public void inicializarVidas(int nuevoMaxVidas) {
        if (nuevoMaxVidas < 1) {
            nuevoMaxVidas = 1;
        }
        // Si el valor real coincide con el placeholder, no hay nada que rehacer
        if (nuevoMaxVidas == this.maxVidas) {
            return;
        }

        // Quita del panel los corazones dibujados con el valor placeholder
        for (JLabel corazon : corazones) {
            fondo.remove(corazon);
        }

        this.maxVidas = nuevoMaxVidas;
        corazones = new JLabel[maxVidas];

        int xInicial = 70;
        int yInicial = 25;
        int espaciado = 55;
        int tamano = 50;

        // Todos los corazones (de 1 a 10) en UNA sola línea, tamaño fijo
        for (int i = 0; i < maxVidas; i++) {

            JLabel corazon = new JLabel(corazonFinal);
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

    //--------------------- B O T O N E S
    //-------------------- MOSTRAR RESPUESTAS ------------------
    public void mostrarRespuestas(String[] respuestas, boolean[] correctas) {
        //Recibe el vector de Respuestas y si esas respuestas son correctas o no
        
        //Se crea el vector de JButton
        JButton[] botones = {
            //Se almacenan los botones
            btnRespuesta1,
            btnRespuesta2,
            btnRespuesta3,
            btnRespuesta4
        };
        
        //Mientras no sobrepase la cantidad de botones
        for (int i = 0; i < botones.length; i++) {
            
            //A ese botón se le cambia el texto por la respuesta almacenada
            botones[i].setText(respuestas[i]);
            
            //Guarda en cada botón la propiedad "correcta"".
            //Indica si la respuesta que está en el botón es correcta.
            botones[i].putClientProperty("correcta", correctas[i]);
                                        // Clave         valor
        }
    }

    //------------------HABILITAR ----------------------
    public void habilitarBotones(boolean estado) {
        //Recibe a través del parámetro si los botones se habilitan o no
        btnRespuesta1.setEnabled(estado);
        btnRespuesta2.setEnabled(estado);
        btnRespuesta3.setEnabled(estado);
        btnRespuesta4.setEnabled(estado);
    }

    //--------------------- T I E M P O
    //------------------ MODIFICAR TIEMPO --------------
    public void modificarTiempo(String tiempoActual) {
        //Recibe a través del parámetro el tiempo
        //Se establece el tiempo en el componente
        tiempo.setText(tiempoActual);
    }

    /*----------------- A B S T R A C T O S --------------
      Este es el apartado donde se encuentran los métodos abstractos
      cuya lógica se encuentra en HiddenFox_Codigo, pero como estos
      métodos dependen de las acciones de los botones se colocan aquí.
    --------------------------------------------------------------*/
    public abstract void ayuda(); //Para el botón ayuda
    
    //Para la respuesta seleccionada en el botón
    public abstract void respuestaSeleccionada(JButton boton);

    /*-------------------------- G E T S -------------------------
      Este es el apartado donde se encuentran los GETS conforme se
      vayan necesitando en HiddenFox_Codigo.
    --------------------------------------------------------------*/
    //------------------ FONDO --------------
    public JPanel getFondo() {
        return fondo; //Devuelve el fondo
    }
}