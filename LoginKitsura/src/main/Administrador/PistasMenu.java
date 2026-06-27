package main.Administrador;

import java.awt.*;
import javax.swing.*;
import main.Menu.FondoPanel;
import main.Menu.DecoracionBotones;

public class PistasMenu extends JFrame{
    private FondoPanel fondo;
    private Font fuente1;
    private Font fuente2;
    
    public PistasMenu() {
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
        
        setTitle("Pistas");
        setSize(1980, 1080); 
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH); 
        fondo.setLayout(null);
        
        crearComponentes();
        setVisible(true);
    }
    
    private void crearComponentes() {

        JLabel lblPistas = new JLabel("PISTAS", JLabel.CENTER);
        lblPistas.setFont(fuente2.deriveFont(35f));
        lblPistas.setForeground(Color.WHITE);
        lblPistas.setBounds(90, 150, 1800, 60);
        fondo.add(lblPistas);

        JButton btnTexto = new DecoracionBotones("TEXTO", "#FC767D", "#da4d58", "#da4d58");
        btnTexto.setFont(fuente1.deriveFont(25f));
        btnTexto.setBounds(400, 380, 415, 75);
        fondo.add(btnTexto);

        JButton btnAudio = new DecoracionBotones("AUDIO", "#FC767D", "#da4d58", "#da4d58");
        btnAudio.setFont(fuente1.deriveFont(25f));
        btnAudio.setBounds(400, 610, 415, 75);
        fondo.add(btnAudio);

        JLabel staticMascotaTablet = new JLabel();
        ImageIcon iconMascota = new ImageIcon(getClass().getResource("/Multimedia/utiles/mascotaKitsura/imagen/MENÚ-PRINCIPAL-LINTERNA.png")); 
        Image imgEscalada = iconMascota.getImage().getScaledInstance(650, 650, Image.SCALE_SMOOTH);
        staticMascotaTablet.setIcon(new ImageIcon(imgEscalada));
        staticMascotaTablet.setBounds(1100, 280, 650, 650);
        fondo.add(staticMascotaTablet);
        
        JButton btnVolver = new DecoracionBotones("VOLVER");
        btnVolver.setFont(fuente2.deriveFont(25f));
        btnVolver.setForeground(Color.WHITE);
        btnVolver.setBounds(1600, 945, 220, 55);
        btnVolver.addActionListener(e -> dispose());
        fondo.add(btnVolver);
        
    }
    
  public static void main(String[] args) {
        new PistasMenu();
    }

}
