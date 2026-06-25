package main.Administrador;

import java.awt.*;
import javax.swing.*;
import main.Menu.FondoPanel;

public class PedirMC extends JFrame {
    private FondoPanel fondo;
    private Font fuente1;
    private Font fuente2;
    
    public PedirMC() {
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
        fondo = new FondoPanel("/Multimedia/utiles/fondos/interfaces/fondoTresK.png"); 
        setContentPane(fondo);   
        setTitle("Pedir M, C");
        setSize(1980, 1080); 
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH); 
        fondo.setLayout(null);       
        crearComponentes();
        setVisible(true);
    }
    
    private void crearComponentes() {
        JPanel recuadroFormulario = new JPanel();
        recuadroFormulario.setBackground(new Color(255, 255, 255, 180)); 
        recuadroFormulario.setOpaque(false);
        recuadroFormulario.setLayout(null);
        recuadroFormulario.setBounds(900, 180, 900, 560); 
        fondo.add(recuadroFormulario);

        JLabel lblTituloCentral = new JLabel("Minijuegos y Categoría", JLabel.LEFT);
        lblTituloCentral.setFont(fuente2.deriveFont(40f));
        lblTituloCentral.setForeground(Color.WHITE);
        lblTituloCentral.setBounds(80, 50, 750, 55);
        recuadroFormulario.add(lblTituloCentral);

        // Minijuego
        JLabel lblMinijuego = new JLabel("Ingrese el minijuego a modificar:");
        lblMinijuego.setFont(fuente2.deriveFont(25f));
        lblMinijuego.setForeground(Color.WHITE);
        lblMinijuego.setBounds(80, 180, 700, 30);
        recuadroFormulario.add(lblMinijuego);

        JTextField txtMinijuego = new JTextField();
        txtMinijuego.setFont(fuente1.deriveFont(25f));
        txtMinijuego.setBounds(80, 225, 700, 45);
        recuadroFormulario.add(txtMinijuego);

        // Categoría
        JLabel lblCategoria = new JLabel("Ingrese la categoría a modificar:");
        lblCategoria.setFont(fuente2.deriveFont(25f));
        lblCategoria.setForeground(Color.WHITE);
        lblCategoria.setBounds(80, 330, 700, 30);
        recuadroFormulario.add(lblCategoria);

        JTextField txtCategoria = new JTextField();
        txtCategoria.setFont(fuente1.deriveFont(25f));
        txtCategoria.setBounds(80, 375, 700, 45);
        recuadroFormulario.add(txtCategoria);
        
        // MASCOTA
            JLabel mascotaControl = new JLabel();
            ImageIcon iconMascota = new ImageIcon(getClass().getResource("/Multimedia/utiles/mascotaKitsura/imagen/MINIJUEGO-CONTROL.png"));
            Image imgEscalada = iconMascota.getImage().getScaledInstance(650, 650, Image.SCALE_SMOOTH);
            mascotaControl.setIcon(new ImageIcon(imgEscalada));
            mascotaControl.setBounds(200, 240, 650, 650);
            fondo.add(mascotaControl);
            
        // BOTÓN REGRESAR 
        JButton btnRegresar = new JButton("Regresar");
        btnRegresar.setFont(fuente1.deriveFont(25f));
        btnRegresar.setBounds(410, 850, 220, 55);
        btnRegresar.addActionListener(e -> dispose()); 
        fondo.add(btnRegresar);
    }
    public static void main(String[] args) {
        new PedirMC();
    }
     
}
