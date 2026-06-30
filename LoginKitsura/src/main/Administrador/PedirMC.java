package main.Administrador;

import java.awt.*;
import javax.swing.*;
import main.Menu.FondoPanel;
import main.Menu.DecoracionBotones;
import main.Menu.FondoPanelSemi;

public class PedirMC extends JFrame {
    private FondoPanel fondo;
    private Font fuente1;
    private Font fuente2;
    private DecoracionBotones btnModificar;
    private DecoracionBotones btnRegresar;
    
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
        FondoPanelSemi recuadroFormulario = new FondoPanelSemi(new Color(0, 0, 0, 120));
        recuadroFormulario.setBounds(900, 130, 860, 650); 
        recuadroFormulario.setLayout(null);
        fondo.add(recuadroFormulario);

        JLabel lblTituloCentral = new JLabel("Minijuegos y Categoría", JLabel.LEFT);
        lblTituloCentral.setFont(fuente2.deriveFont(40f));
        lblTituloCentral.setForeground(Color.decode("#447A9C")); 
        lblTituloCentral.setBounds(80, 40, 700, 55);
        recuadroFormulario.add(lblTituloCentral);

        // Minijuego
        JLabel lblMinijuego = new JLabel("Ingrese el minijuego a modificar:");
        lblMinijuego.setFont(fuente2.deriveFont(27f));
        lblMinijuego.setForeground(Color.WHITE);
        lblMinijuego.setBounds(80, 175, 700, 30);
        recuadroFormulario.add(lblMinijuego);

        JTextField txtMinijuego = new JTextField();
        txtMinijuego.setFont(fuente1.deriveFont(25f));
        txtMinijuego.setBounds(80, 240, 700, 45);
        recuadroFormulario.add(txtMinijuego);

        // Categoría
        JLabel lblCategoria = new JLabel("Ingrese la categoría a modificar:");
        lblCategoria.setFont(fuente2.deriveFont(27f));
        lblCategoria.setForeground(Color.WHITE);
        lblCategoria.setBounds(80, 315, 700, 30);
        recuadroFormulario.add(lblCategoria);

        JTextField txtCategoria = new JTextField();
        txtCategoria.setFont(fuente1.deriveFont(25f));
        txtCategoria.setBounds(80, 360, 700, 45);
        recuadroFormulario.add(txtCategoria);
        
        btnModificar = new DecoracionBotones("MODIFICAR",
                //ColorBase             ColorBorde              ColorLetra
                DecoracionBotones.ROSA, DecoracionBotones.ROJO, DecoracionBotones.AMARILLO, //MOUSE FUERA
                DecoracionBotones.ROJO, DecoracionBotones.ROSA, DecoracionBotones.ROSA); //MOUSE DENTRO
        btnModificar.setFont(fuente2.deriveFont(25f));
        btnModificar.setForeground(Color.WHITE);
        btnModificar.setBounds(280, 470, 300, 60); 
        recuadroFormulario.add(btnModificar);
        
        // MASCOTA
            JLabel mascotaControl = new JLabel();
            ImageIcon iconMascota = new ImageIcon(getClass().getResource("/Multimedia/utiles/mascotaKitsura/imagen/MINIJUEGO-CONTROL.png"));
            Image imgEscalada = iconMascota.getImage().getScaledInstance(650, 650, Image.SCALE_SMOOTH);
            mascotaControl.setIcon(new ImageIcon(imgEscalada));
            mascotaControl.setBounds(200, 240, 650, 650);
            fondo.add(mascotaControl);
            
        // BOTÓN REGRESAR 
        JButton btnRegresar = new DecoracionBotones("REGRESAR",
                                //ColorBase             ColorBorde              ColorLetra
                DecoracionBotones.AZUL, DecoracionBotones.GRIS, DecoracionBotones.AMARILLO, //MOUSE FUERA
                DecoracionBotones.CELESTE, DecoracionBotones.AZUL, DecoracionBotones.AZUL); //MOUSE DENTRO   

        btnRegresar.setFont(fuente2.deriveFont(25f));
        btnRegresar.setBounds(410, 850, 220, 55);
        btnRegresar.addActionListener(e -> dispose()); 
        fondo.add(btnRegresar);
    }
    public static void main(String[] args) {
        new PedirMC();
    }
     
}
