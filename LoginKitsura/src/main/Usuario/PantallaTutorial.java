package main.Usuario;

import java.awt.*;
import java.net.URL;
import javax.swing.*;
import main.Menu.DecoracionBotones;

public class PantallaTutorial extends JFrame {

    public JScrollPane scrollPane;
    public JPanel panelContenido;

    public JLabel lblTitulo;
    public JLabel lblMascota;
    public JLabel lblPaso1;
    public JLabel lblTextoPaso1;
    public JLabel lblImagen1;
    public JLabel lblImagen2;

    public JButton btnVolver;

    public Font fuente1;
    public Font fuente2;

    public PantallaTutorial() {

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

        getContentPane().setBackground(new Color(180, 180, 180));
        //------------- ÍCONO ------------------
        //se obtiene la imagen del logo con getResource
        URL iconUrl = getClass().getResource("/Multimedia/utiles/logotipo/logofK.png");

        //se instancia el ícono con la imagen
        ImageIcon icono = new ImageIcon(iconUrl);

        //Se coloca el ícono al JFrame
        setIconImage(icono.getImage());

        setTitle("Tutorial");
        setSize(600, 420);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        getContentPane().setLayout(null);

        crearComponentes();
        agregarContenido();

        setVisible(true);
    }

    public void crearComponentes() {

        //---------------- PANEL CONTENIDO ----------------
        panelContenido = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setColor(new Color(245, 245, 245));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
                g2.dispose();
                super.paintComponent(g);
            }
        };

        panelContenido.setOpaque(false);
        panelContenido.setLayout(null);
        panelContenido.setPreferredSize(new Dimension(540, 1000));

        //---------------- TÍTULO ----------------
        lblTitulo = new JLabel("Reglas", SwingConstants.CENTER);
        lblTitulo.setFont(fuente2.deriveFont(Font.BOLD, 28f));
        lblTitulo.setBounds(180, 10, 200, 40);
        panelContenido.add(lblTitulo);

        //---------------- MASCOTA ----------------
        lblMascota = new JLabel();
        try {
            ImageIcon mascotaIcon = new ImageIcon(getClass().getResource("/Multimedia/utiles/mascotaKitsura/imagen/zorroLeerVolteado.png"));
            Image mascotaEscalada = mascotaIcon.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
            lblMascota.setIcon(new ImageIcon(mascotaEscalada));
        } catch (Exception e) {
            lblMascota.setText("~");
        }
        lblMascota.setBounds(0, 70, 200, 200);
        panelContenido.add(lblMascota);

        //---------------- PASO 1 ----------------
        lblPaso1 = new JLabel("Paso 1", SwingConstants.CENTER);
        lblPaso1.setFont(fuente2.deriveFont(Font.BOLD, 22f));
        lblPaso1.setBounds(170, 60, 120, 30);
        panelContenido.add(lblPaso1);

        lblTextoPaso1 = new JLabel("Texto explicativo");
        lblTextoPaso1.setFont(fuente1.deriveFont(34f));
        lblTextoPaso1.setBounds(200, 95, 280, 30);
        panelContenido.add(lblTextoPaso1);

        //---------------- IMÁGENES ----------------
        lblImagen1 = new JLabel();
        try {
            ImageIcon imagen1 = new ImageIcon(getClass().getResource("/Multimedia/utiles/ElementosGraficos/imagenes/ejemplo.png"));
            Image imgEscalada1 = imagen1.getImage().getScaledInstance(150, 120, Image.SCALE_SMOOTH);
            lblImagen1.setIcon(new ImageIcon(imgEscalada1));
        } catch (Exception e) {
            lblImagen1.setText("[imagen]");
        }
        lblImagen1.setBounds(200, 140, 150, 120);
        panelContenido.add(lblImagen1);

        lblImagen2 = new JLabel();
        try {
            ImageIcon imagen2 = new ImageIcon(getClass().getResource("/Multimedia/utiles/ElementosGraficos/imagenes/ejemplo.png"));
            Image imgEscalada2 = imagen2.getImage().getScaledInstance(150, 120, Image.SCALE_SMOOTH);
            lblImagen2.setIcon(new ImageIcon(imgEscalada2));
        } catch (Exception e) {
            lblImagen2.setText("[imagen]");
        }
        lblImagen2.setBounds(370, 140, 150, 120);
        panelContenido.add(lblImagen2);

        //---------------- INFO ----------------
        JLabel lblInfo1 = new JLabel("Más información sobre el minijuego.", SwingConstants.CENTER);
        lblInfo1.setFont(fuente1.deriveFont(34f));
        lblInfo1.setBounds(205, 270, 320, 25);
        panelContenido.add(lblInfo1);

        JLabel lblInfo2 = new JLabel("Aquí puedes explicar reglas,", SwingConstants.CENTER);
        lblInfo2.setFont(fuente1.deriveFont(34f));
        lblInfo2.setBounds(205, 295, 320, 25);
        panelContenido.add(lblInfo2);

        JLabel lblInfo3 = new JLabel("objetivos y controles.", SwingConstants.CENTER);
        lblInfo3.setFont(fuente1.deriveFont(34f));
        lblInfo3.setBounds(205, 320, 320, 25);
        panelContenido.add(lblInfo3);

        //---------------- BOTÓN VOLVER ----------------
        btnVolver = new DecoracionBotones("VOLVER",
                                //ColorBase             ColorBorde              ColorLetra
                DecoracionBotones.AZUL, DecoracionBotones.GRIS, DecoracionBotones.AMARILLO, //MOUSE FUERA
                DecoracionBotones.CELESTE, DecoracionBotones.AZUL, DecoracionBotones.AZUL); //MOUSE DENTRO   

        btnVolver.setFont(fuente2.deriveFont(20f));
        btnVolver.setBounds(30, 270, 150, 50);
        btnVolver.addActionListener(e -> dispose());
        panelContenido.add(btnVolver);

        //---------------- PASO 2 ----------------
        JLabel lblPaso2 = new JLabel("Paso 2", SwingConstants.CENTER);
        lblPaso2.setFont(fuente2.deriveFont(Font.BOLD, 22f));
        lblPaso2.setBounds(180, 380, 120, 30);
        panelContenido.add(lblPaso2);

        JLabel lblTextoPaso2a = new JLabel("Explicación adicional del juego.", SwingConstants.CENTER);
        lblTextoPaso2a.setFont(fuente1.deriveFont(34f));
        lblTextoPaso2a.setBounds(180, 415, 380, 25);
        panelContenido.add(lblTextoPaso2a);

        JLabel lblTextoPaso2b = new JLabel("Puedes agregar imágenes y texto.", SwingConstants.CENTER);
        lblTextoPaso2b.setFont(fuente1.deriveFont(34f));
        lblTextoPaso2b.setBounds(180, 440, 380, 25);
        panelContenido.add(lblTextoPaso2b);

        //---------------- PASO 3 ----------------
        JLabel lblPaso3 = new JLabel("Paso 3", SwingConstants.CENTER);
        lblPaso3.setFont(fuente2.deriveFont(Font.BOLD, 22f));
        lblPaso3.setBounds(190, 750, 120, 30);
        panelContenido.add(lblPaso3);

        JLabel lblTextoPaso3a = new JLabel("Última sección del tutorial.", SwingConstants.CENTER);
        lblTextoPaso3a.setFont(fuente1.deriveFont(34f));
        lblTextoPaso3a.setBounds(180, 790, 380, 25);
        panelContenido.add(lblTextoPaso3a);

        JLabel lblTextoPaso3b = new JLabel("Coloca aquí cualquier regla adicional.", SwingConstants.CENTER);
        lblTextoPaso3b.setFont(fuente1.deriveFont(34f));
        lblTextoPaso3b.setBounds(180, 815, 380, 25);
        panelContenido.add(lblTextoPaso3b);

        //---------------- SCROLL ----------------
        scrollPane = new JScrollPane(panelContenido);
        scrollPane.setBounds(10, 10, 565, 360);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        getContentPane().add(scrollPane);
        
        
    }
    
    public void agregarContenido(){
        // Este método lo usará la clase hija
    }

    public static void main(String[] args) {
        new PantallaTutorial();
    }
}