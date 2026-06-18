package main;

import java.awt.*;
import javax.swing.*;

public class M1 extends JFrame {
    private FondoPanel fondo;
    private Font fuente1;
    private Font fuente2;
    
    public M1() {
        try{
            // LettersForLearners
            fuente1 = Font.createFont(
            Font.TRUETYPE_FONT,
            getClass().getResourceAsStream("/fuentes/LettersForLearners.ttf"));
            // KGPerfectPenmanship
            fuente2 = Font.createFont(
            Font.TRUETYPE_FONT,
            getClass().getResourceAsStream("/fuentes/KGPerfectPenmanship.ttf"));
            
        } catch (Exception e){
            e.printStackTrace();            
        fuente1 = new Font("Arial", Font.PLAIN,20);
        fuente2 = new Font("Arial", Font.PLAIN,20);
        }
        fondo = new FondoPanel("/utilidades/fondoTresK.png"); 
        setContentPane(fondo);        
        setTitle("M1-Crear nuevo");
        setSize(1980, 1080); 
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH); 
        fondo.setLayout(null);       
        crearComponentes();
        setVisible(true);
    }
    
    private void crearComponentes() {
        JLabel lblTituloSeccion = new JLabel("CREAR NUEVO (MINIJUEGO 1)");
        lblTituloSeccion.setFont(fuente2.deriveFont(35f));
        lblTituloSeccion.setForeground(Color.BLUE);
        lblTituloSeccion.setBounds(70, 140, 600, 50);
        fondo.add(lblTituloSeccion);

        JLabel staticMascotaTablet = new JLabel();
        ImageIcon iconMascota = new ImageIcon(getClass().getResource("/utilidades/mascotaUno.png")); 
        Image imgEscalada = iconMascota.getImage().getScaledInstance(600, 600, Image.SCALE_SMOOTH);
        staticMascotaTablet.setIcon(new ImageIcon(imgEscalada));
        staticMascotaTablet.setBounds(20, 240, 600, 600);
        fondo.add(staticMascotaTablet);

        JButton btnSiguiente = new JButton("SIGUIENTE");
        btnSiguiente.setFont(fuente1.deriveFont(20f));
        btnSiguiente.setBounds(150, 810, 240, 40);
        fondo.add(btnSiguiente);

        JLabel lblCargueColor = new JLabel("Cargue la Imagen a color");
        lblCargueColor.setFont(fuente2.deriveFont(22f));
        lblCargueColor.setForeground(Color.WHITE);
        lblCargueColor.setBounds(700, 175, 400, 30);
        fondo.add(lblCargueColor);

        JPanel areaColor = new JPanel();
        areaColor.setBackground(Color.LIGHT_GRAY);
        areaColor.setBounds(700, 210, 420, 240);
        fondo.add(areaColor);

        JButton btnCargarColor = new JButton("CARGAR");
        btnCargarColor.setFont(fuente1.deriveFont(25f));
        btnCargarColor.setBounds(800, 470, 220, 45);
        fondo.add(btnCargarColor);

        JLabel lblCargueSombra = new JLabel("Cargue la sombra:");
        lblCargueSombra.setFont(fuente2.deriveFont(22f));
        lblCargueSombra.setForeground(Color.WHITE);
        lblCargueSombra.setBounds(1250, 160, 400, 30);
        fondo.add(lblCargueSombra);

        JPanel areaSombra = new JPanel();
        areaSombra.setBackground(Color.LIGHT_GRAY);
        areaSombra.setBounds(1250, 210, 420, 240);
        fondo.add(areaSombra);

        JButton btnCargarSombra = new JButton("CARGAR");
        btnCargarSombra.setFont(fuente1.deriveFont(25f));
        btnCargarSombra.setBounds(1350, 470, 220, 45);
        fondo.add(btnCargarSombra);

        JLabel lblCorrecta1 = new JLabel("Ingrese la respuesta correcta del nivel:");
        lblCorrecta1.setFont(fuente1.deriveFont(25f));
        lblCorrecta1.setForeground(Color.WHITE);
        lblCorrecta1.setBounds(700, 560, 450, 25);
        fondo.add(lblCorrecta1);

        JTextField txtCorrecta1 = new JTextField();
        txtCorrecta1.setFont(fuente1.deriveFont(18f));
        txtCorrecta1.setBounds(700, 595, 420, 40);
        fondo.add(txtCorrecta1);

        JLabel lblIncorrecta1 = new JLabel("Ingrese la respuesta incorrecta del nivel:");
        lblIncorrecta1.setFont(fuente1.deriveFont(25f));
        lblIncorrecta1.setForeground(Color.WHITE);
        lblIncorrecta1.setBounds(1250, 560, 450, 25);
        fondo.add(lblIncorrecta1);

        JTextField txtIncorrecta1 = new JTextField();
        txtIncorrecta1.setFont(fuente1.deriveFont(18f));
        txtIncorrecta1.setBounds(1250, 595, 420, 40);
        fondo.add(txtIncorrecta1);

        JLabel lblCorrecta2 = new JLabel("Ingrese la respuesta correcta del nivel:");
        lblCorrecta2.setFont(fuente1.deriveFont(25f));
        lblCorrecta2.setForeground(Color.WHITE);
        lblCorrecta2.setBounds(700, 680, 450, 25);
        fondo.add(lblCorrecta2);

        JTextField txtCorrecta2 = new JTextField();
        txtCorrecta2.setFont(fuente1.deriveFont(18f));
        txtCorrecta2.setBounds(700, 715, 420, 40);
        fondo.add(txtCorrecta2);

        JLabel lblIncorrecta2 = new JLabel("Ingrese la respuesta incorrecta del nivel:");
        lblIncorrecta2.setFont(fuente1.deriveFont(25f));
        lblIncorrecta2.setForeground(Color.WHITE);
        lblIncorrecta2.setBounds(1250, 680, 450, 25);
        fondo.add(lblIncorrecta2);

        JTextField txtIncorrecta2 = new JTextField();
        txtIncorrecta2.setFont(fuente1.deriveFont(18f));
        txtIncorrecta2.setBounds(1250, 715, 420, 40);
        fondo.add(txtIncorrecta2);
    }

    public static void main(String[] args) {
        new M1();
    }
}
