
package main.Usuario;

import javax.swing.*;
import java.awt.*;
import main.Menu.FondoPanel;

public class MenuMinijuegosC extends JFrame{
    
    //------------- A T R I B U T O S -------------
    // ------------- FONDO Y FUENTES --------------
    public FondoPanel fondo; //Atributo del fondo
    public Font fuente1; //Atributo de la primera Fuente
    public Font fuente2; //Atributo de la segunda Fuente
    
    //----------------- LABELS --------------------
    public JLabel lblTitulo; //Se declara el título
    public JLabel mascota; //Se declara la mascota

    //------------------ BOTONES ------------------
    public JButton btnCategoria1; //El botón de categoría 1
    public JButton btnCategoria2; //El botón de categoría 2
    public JButton btnCategoria3; //El botón de categoría 3
    public JButton btnComoJugar; //El botón de categoría 4
    public JButton btnVolver; //El botón para volver
    
    // ------------ C O N S T R U C T O R --------------
    /*El constructor recible los parámetros de:
    lblTitulo = titulo
    btnCategoria1.setText = categoria1
    btnCategoria2.SetText = categoria2
    btnCategoria3.setText = categoria3
    ----------------------------------------------------
    Para reemplazar el texto del título y los botones
    */
    public MenuMinijuegosC(String titulo, String categoria1, String categoria2, String categoria3) {
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
        
        // ---------- C O M P O N E N T E S   T E X T O ------------
        lblTitulo = new JLabel(titulo, SwingConstants.CENTER);
        btnCategoria1 = new JButton(categoria1);
        btnCategoria2 = new JButton(categoria2);
        btnCategoria3 = new JButton(categoria3);
        
        
        // Reutilizamos el mismo fondo o el que prefieras
        fondo = new FondoPanel("/Multimedia/utiles/fondos/interfaces/fondoDosK.png");
        setContentPane(fondo);
        setTitle("Categorías de Minijuego");
        setSize(1880, 1080);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        fondo.setLayout(null);
        
        crearComponentes();
        setVisible(true);
    }
    
    // ------------------------ C O M P O N E N T E S --------------------------------
    private void crearComponentes() {
        //---------------- BOTÓN ¿CÓMO JUGAR? (Arriba a la izquierda) ----------------
        //Se mantiene btnComoJugar el texto igual porque en los tres minijuegos se utiliza
        btnComoJugar = new JButton("¿Cómo jugar?"); //Para mostrar el cómo jugar
        btnComoJugar.setFont(fuente1.deriveFont(25f));
        btnComoJugar.setBounds(100, 100, 250, 55);
        btnComoJugar.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Instrucciones del minijuego...");
        });
        fondo.add(btnComoJugar);

        //---------------- MASCOTA (Ahora a la izquierda) ----------------
        mascota = new JLabel();
        ImageIcon mascotaIcon = new ImageIcon(getClass().getResource("/Multimedia/utiles/mascotaKitsura/imagen/MINIJUEGO-CONTROL.png"));
        Image mascotaEscalada = mascotaIcon.getImage().getScaledInstance(600, 600, Image.SCALE_SMOOTH);
        mascota.setIcon(new ImageIcon(mascotaEscalada));
        // Posicionada a la izquierda y centrada verticalmente en base a las medidas
        mascota.setBounds(150, 280, 600, 600); 
        fondo.add(mascota);

        //---------------- T I T U L O (A la derecha) ----------------
        lblTitulo.setFont(fuente2.deriveFont(40f)); // Un poco más grande para el título del juego
        lblTitulo.setForeground(Color.WHITE); 
        lblTitulo.setBounds(1000, 120, 600, 60);
        fondo.add(lblTitulo);

        //---------------- B O T O N E S   C A T E G O R Í A S ----------------
        // >. C A T E G O R Í A   1
        btnCategoria1.setFont(fuente1.deriveFont(25f));
        btnCategoria1.setBounds(1100, 300, 400, 55);
        btnCategoria1.addActionListener(e -> {
            // Acción para la Categoría 1
        });
        fondo.add(btnCategoria1);

        // >. C A T E G O R Í A   2
        btnCategoria2.setFont(fuente1.deriveFont(25f));
        btnCategoria2.setBounds(1100, 430, 400, 55);
        btnCategoria2.addActionListener(e -> {
            // Acción para la Categoría 2
        });
        fondo.add(btnCategoria2);

        // >. C A T E G O R Í A   3
        btnCategoria3.setFont(fuente1.deriveFont(25f));
        btnCategoria3.setBounds(1100, 560, 400, 55);
        btnCategoria3.addActionListener(e -> {
            // Acción para la Categoría 3
        });
        fondo.add(btnCategoria3);

        //---------------- V O L V E R (Abajo a la derecha) ----------------
        btnVolver = new JButton("VOLVER");
        btnVolver.setFont(fuente1.deriveFont(25f));
        btnVolver.setBounds(1175, 720, 250, 45);
        btnVolver.addActionListener(e -> {
            // Regresa al menú anterior de Minijuegos que ya tenías hecho
            new MenuMinijuegos();
            dispose();
        });
        fondo.add(btnVolver);
    }
    
    public static void main(String[] args) {
        new MenuMinijuegosC("Menu Minijuego", "Categoria 1", "Categoria 2", "Categoria 3");
    }
}
