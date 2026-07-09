/*---------------- D E C O R A C I Ó N   D E   B O T O N E S -----------------
    Apartado donde se crean el estilo de diseño de los botones.
------------------------------------------------------------------------------*/
package main.Menu; //Paquete

//--------------- I M P O R T A C I O N E S ---------------
import java.awt.*; //Se importa Java AWT
import java.awt.event.*; //Se importa los eventos de Java AWT
import javax.swing.*; //Se importa Java Swing
import javax.swing.border.*; //Se importa el borde de Java Swing

//-------------- C L A S E -----------------
//DecoracionBotones hereda de JButton
public class DecoracionBotones extends JButton {

    //------------------- A T R I B U T O S --------------
    //--------------- COLORES DEL BOTÓN (MOUSE FUERA)
    private Color colorBase; //Color de base
    private Color colorBorde; //Color de borde
    private Color colorTexto; //Color de texto

    //--------------- COLORES DEL BOTÓN (MOUSE DENTRO)
    private Color colorBaseHover; //Color de base
    private Color colorBordeHover; //Color de borde
    private Color colorTextoHover; //Color de texto

    //-------------- COLORES DEL BOTÓN (CUANDO ESTÉ DESHABILITADO)
    private Color colorBaseDisabled; //Color de base
    private Color colorBordeDisabled; //Color de borde
    private Color colorTextoDisabled; //Color de texto

    //-------------- FUENTE ------------
    private Font fuente2; //Llamada fuente2, es KGPerfectPenmanship

    //-------------- COLORES PREDEFINIDOS -----------
    /*Definimos constantes. Por convención, las constantes se colocan con Mayúsculas.
    - public las hace públicas.
    - static indica que pertenecen a la clase así que no hay necesidad de crear 
      objetos para llamarlas. 
    - final indica que es su valor "final" y que por lo tanto, no puede modificarse.*/
    public static final String AZUL = "#447a9c";
    public static final String GRIS = "#3E454C";
    public static final String AMARILLO = "#EFDA9A";
    public static final String CELESTE = "#82D3E0";
    public static final String ROSA = "#FC767D";
    public static final String ROJO = "#DA4D58";
    public static final String AMARILLO_MOSTAZA = "#E8BE18";
    public static final String AMARILLO_APAGADO = "#EBBF66";
    public static final String NEGRO = "#000000";
    public static final String BLANCO = "#FFFFFF";
    public static final String NARANJA_PASTEL = "#E39D8B";
    public static final String ROSA_PASTEL = "#EE9797";
    public static final String AMARILLO_SUAVE = "#F0D060";
    public static final String VERDE = "#91BF4B";
    public static final String VERDE_SUAVE = "#B4DC64";
    public static final String TURQUESA = "#76A9AA";
    

    //---------------------- C O N S T R U C T O R E S -------------------------
    //COLORES DEL BOTÓN | MOUSE FUERA | MOUSE DENTRO
    public DecoracionBotones(String text,
            // COLORES DEL BOTÓN (MOUSE FUERA)
            String colorBase, String colorBorde, String colorTexto,
            // COLORES DEL BOTÓN (MOUSE DENTRO)
            String colorBaseHover, String colorBordeHover, String colorTextoHover) {
        super(text); //LLama al constructor del JButton para colocar el texto.

        //COLORES DEL BOTÓN (MOUSE FUERA)
        this.colorBase = Color.decode(colorBase);
        this.colorBorde = Color.decode(colorBorde);
        this.colorTexto = Color.decode(colorTexto);

        //COLORES DEL BOTÓN (MOUSE DENTRO)
        this.colorBaseHover = Color.decode(colorBaseHover);
        this.colorBordeHover = Color.decode(colorBordeHover);
        this.colorTextoHover = Color.decode(colorTextoHover);
        
        //Método para inicializar Componentes.
        inicializarComponentes();
    }

    //--------------------------- M É T O D O S --------------------------------
    // Aquí se configura todo el comportamiento visual del botón
    private void inicializarComponentes() {
        //----------------- FUENTE ----------------
        try {
            fuente2 = Font.createFont(Font.TRUETYPE_FONT,
                    getClass().getResourceAsStream("/fuentes/KGPerfectPenmanship.ttf"));
        } catch (Exception e) {
            e.printStackTrace();
            fuente2 = new Font("Arial", Font.PLAIN, 20);
        }

        setContentAreaFilled(false);
        setFocusPainted(false);
        setOpaque(false);
        setFont(fuente2.deriveFont(30f)); //Fuente

        mouseFuera(); //Para que se coloque antes que el usuario ingrese el mouse

        // Eventos para detectar cuando el cursor entra o sale del botón
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                mouseDentro();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                mouseFuera();
            }
        });
    }

    //--------------------- MOUSE FUERA ---------------------
    public void mouseFuera() {
        setBackground(colorBase); //Color de fondo
        setForeground(colorTexto); //Color del texto
        setBorder(createPixelBorder(colorBorde)); //Color del borde 
        repaint();
    }

    //--------------------- MOUSE DENTRO ---------------------
    public void mouseDentro() {
        setBackground(colorBaseHover); //Color de fondo
        setForeground(colorTextoHover); //Color del texto
        setBorder(createPixelBorder(colorBordeHover)); //Color del borde 
        repaint();
    }

    // Dibuja el fondo sólido y plano (estilo pixel art, sin suavizados)
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setColor(getBackground());
        // Rellena el interior dejando espacio para el borde de 4 píxeles
        g2d.fillRect(4, 4, getWidth() - 8, getHeight() - 8);
        g2d.dispose();

        super.paintComponent(g);
    }

    // Crea un borde grueso "bloque por bloque" característico del pixel art
    private Border createPixelBorder(Color colorDelBorde) {
        return BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(4, 4, 4, 4, colorDelBorde), // Borde de 4px de grosor
                BorderFactory.createEmptyBorder(6, 14, 6, 14) // Margen del texto interno
        );
    }
    
    //-------------------------- B O T Ó N   D E S H A B I L I T A D O -------------------------
    
    //Este método guarda colores cuando el botón esté deshabilitado
    public void estiloBotonDeshabilitado(String colorBaseDisabled, String colorBordeDisabled, String colorTextoDisabled) {
        //COLORES DEL BOTÓN (DESHABILITAD)
        this.colorBaseDisabled = Color.decode(colorBaseDisabled); //Color base
        this.colorBordeDisabled = Color.decode(colorBordeDisabled); //Color borde
        this.colorTextoDisabled = Color.decode(colorTextoDisabled); //Color texto
    }

    //Este método aplica los colores que anteriormente se guardaron
    private void aplicarEstiloBotonDeshabilitado() {
        setBackground(colorBaseDisabled); //Color base
        setForeground(colorTextoDisabled); //Color texto
        setBorder(createPixelBorder(colorBordeDisabled)); //Color borde
    }

    /*------------------------------------ SETENABLED --------------------------
      Método de la clase JComponent, indica si el componente está habilitado o no.
      Se utiliza Override porque estamos sobreescribiendo un método de la clase
      padre. Ya que DecoracionBotones hereda de JButton, que a su vez hereda de 
      AbstractButton, y por lo tanto de Jcomponent.
    ----------------------------------------------------------------------------*/

    @Override
    public void setEnabled(boolean enabled) {
        
        /*Se utiliza super para llamar a la clase padre del método, para que
        primero se ejecute el método normal y haga su trabajo, ya luego comenzamos
        a agregar el resto de comportamientos que queremos.*/
        super.setEnabled(enabled);
        
        //Si el botón está habilitado
        if (enabled) {
            //Se coloca la fuente del botón
            setFont(fuente2.deriveFont(30f));
            //Se llama al método del aspecto del botón cuando el mouse no interactua.
            mouseFuera();
        //Si el botón está deshabilitado
        } else {
            //Se llama al enterior método para aplicar el estilo del botón.
             aplicarEstiloBotonDeshabilitado();
        }
        
        //Se llama al repaint
        repaint();
    }
}
