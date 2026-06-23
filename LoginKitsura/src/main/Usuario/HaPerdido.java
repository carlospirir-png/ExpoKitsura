package main.Usuario;

import java.awt.*;
import javax.swing.*;
import main.Menu.FondoPanel;

public class HaPerdido extends JFrame {
    private FondoPanel fondo;
    private Font fuente1;
    private Font fuente2;
    
    public HaPerdido() {
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
        fondo = new FondoPanel("/Multimedia/utiles/fondos/interfaces/fondoCuatroK.png"); 
        setContentPane(fondo);
        
        setTitle("Ha perdido");
        setSize(1980, 1060); 
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH); 
        fondo.setLayout(null);
        
        crearComponentes();
        setVisible(true);
    }
    
    private void crearComponentes() {
        //---------------- GAME OVER ----------------
        JLabel lblGameOver = new JLabel("GAME OVER", JLabel.CENTER);
        lblGameOver.setFont(fuente2.deriveFont(34f));
        lblGameOver.setForeground(Color.WHITE);
        lblGameOver.setBounds(900, 100, 800, 60);
        fondo.add(lblGameOver);

        //---------------- SUBTÍTULO ----------------
        JLabel lblSubtitulo = new JLabel("Se te acabaron las vidas", JLabel.CENTER);
        lblSubtitulo.setFont(fuente2.deriveFont(28f));
        lblSubtitulo.setForeground(Color.WHITE);
        lblSubtitulo.setBounds(900, 170, 800, 45);
        fondo.add(lblSubtitulo);

        //---------------- CITA DE DALE CARNEGIE ----------------
        JLabel lblCita = new JLabel("<<Desarrolla el éxito a partir de los fracasos. El desaliento y el fracaso son los peldaños hacia el éxito>>. -Dale Carnegie", JLabel.CENTER);
        lblCita.setFont(fuente1.deriveFont(24f));
        lblCita.setForeground(Color.WHITE);
        lblCita.setBounds(900, 240, 800, 30);
        fondo.add(lblCita);

        JLabel mascotaCorazones = new JLabel();
        ImageIcon iconMascota = new ImageIcon(getClass().getResource("/Multimedia/utiles/mascotaKitsura/imagen/zorroLupa.png")); 
        Image imgEscalada = iconMascota.getImage().getScaledInstance(600, 600, Image.SCALE_SMOOTH);
        mascotaCorazones.setIcon(new ImageIcon(imgEscalada));
        mascotaCorazones.setBounds(150, 250, 600, 600);
        fondo.add(mascotaCorazones);

        //---------------- BOTON VOLVER ----------------
        JButton btnVolver = new JButton("Volver");
        btnVolver.setFont(fuente1.deriveFont(25f));
        btnVolver.setForeground(Color.BLACK);
        btnVolver.setBounds(1225, 770, 200, 50);
        btnVolver.addActionListener(e -> dispose()); 
        fondo.add(btnVolver);
    }
}
