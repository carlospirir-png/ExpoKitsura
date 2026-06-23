package main.Usuario;

import java.awt.*;
import javax.swing.*;
import main.Menu.FondoPanel;

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
        JLabel lblTituloVentana = new JLabel("Pistas");
        lblTituloVentana.setFont(new Font("Arial", Font.PLAIN, 18));
        lblTituloVentana.setBounds(50, 40, 200, 30);
        fondo.add(lblTituloVentana);

        JLabel lblPistas = new JLabel("PISTAS", JLabel.CENTER);
        lblPistas.setFont(fuente2.deriveFont(35f));
        lblPistas.setForeground(Color.WHITE);
        lblPistas.setBounds(90, 150, 1800, 60);
        fondo.add(lblPistas);

        JButton btnTexto = new JButton("Texto");
        btnTexto.setFont(fuente1.deriveFont(25f));
        btnTexto.setBounds(400, 380, 320, 80);
        fondo.add(btnTexto);

        JButton btnAudio = new JButton("Audio");
        btnAudio.setFont(fuente1.deriveFont(25f));
        btnAudio.setBounds(400, 610, 320, 80);
        fondo.add(btnAudio);

        JLabel staticMascotaTablet = new JLabel();
        ImageIcon iconMascota = new ImageIcon(getClass().getResource("/Multimedia/utiles/mascotaKitsura/imagen/REGISTRARSE_USUARIO-PARTIDA_MINIJUEGO-TABLETA.png")); 
        Image imgEscalada = iconMascota.getImage().getScaledInstance(600, 600, Image.SCALE_SMOOTH);
        staticMascotaTablet.setIcon(new ImageIcon(imgEscalada));
        staticMascotaTablet.setBounds(1100, 280, 600, 600);
        fondo.add(staticMascotaTablet);
        
        JButton btnVolver = new JButton("Volver");
        btnVolver.setFont(fuente1.deriveFont(25f));
        btnVolver.setBounds(1600, 945, 220, 55);
        btnVolver.addActionListener(e -> dispose());
        fondo.add(btnVolver);
    }

}
