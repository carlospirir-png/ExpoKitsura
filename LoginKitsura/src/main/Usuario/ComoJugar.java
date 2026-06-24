package main.Usuario;

import java.awt.*;
import javax.swing.*;
import main.Menu.FondoPanel;

public class ComoJugar extends JFrame{
    private FondoPanel fondo;
    private Font fuente1;
    private Font fuente2;
    
    public ComoJugar() {
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
        
        fondo = new FondoPanel("/Multimedia/utiles/fondos/interfaces/fondoDosK.png"); 
        setContentPane(fondo);     
        setTitle("¿Cómo Jugar?");
        setSize(1980, 1060); 
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH); 
        fondo.setLayout(null);       
        crearComponentes();
        setVisible(true);
    }
    
    private void crearComponentes() {
        JPanel recuadroExplicativo = new JPanel();
        recuadroExplicativo.setBackground(new Color(255, 255, 255, 180)); 
        recuadroExplicativo.setOpaque(false);
        recuadroExplicativo.setLayout(null);
        recuadroExplicativo.setBounds(750, 150, 1050, 750);
        fondo.add(recuadroExplicativo);

        JLabel lblPaso = new JLabel("Paso 1", JLabel.CENTER);
        lblPaso.setFont(fuente2.deriveFont(45f));
        lblPaso.setForeground(Color.WHITE);
        lblPaso.setBounds(25, 60, 950, 45);
        recuadroExplicativo.add(lblPaso);

        // TEXTO EXPLICATIVO
        JLabel lblTextoExplicativo = new JLabel("*Texto Explicativo con las instrucciones del juego...", JLabel.CENTER);
        lblTextoExplicativo.setFont(fuente2.deriveFont(25f));
        lblTextoExplicativo.setForeground(Color.GRAY);
        lblTextoExplicativo.setBounds(25, 140, 950, 40);
        recuadroExplicativo.add(lblTextoExplicativo);

        JPanel recuadroImg1 = new JPanel();
        recuadroImg1.setBackground(Color.WHITE);
        recuadroImg1.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        recuadroImg1.setBounds(115, 300, 320, 220);
        recuadroExplicativo.add(recuadroImg1);

        JPanel recuadroImg2 = new JPanel();
        recuadroImg2.setBackground(Color.WHITE);
        recuadroImg2.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        recuadroImg2.setBounds(545, 300, 320, 220);
        recuadroExplicativo.add(recuadroImg2);

        // BARRA DE DESPLAZAMIENTO 
        JScrollBar scrollSimulado = new JScrollBar(JScrollBar.VERTICAL);
        scrollSimulado.setBounds(995, 20, 20, 710);
        recuadroExplicativo.add(scrollSimulado);

        //---------------- MASCOTA ----------------
            JLabel mascotaLector = new JLabel();
            ImageIcon iconMascota = new ImageIcon(getClass().getResource("/Multimedia/utiles/mascotaKitsura/imagen/REGLAS-PISTA_TEXTUAL.png")); 
            Image imgEscalada = iconMascota.getImage().getScaledInstance(600, 600, Image.SCALE_SMOOTH);
            mascotaLector.setIcon(new ImageIcon(imgEscalada));
            mascotaLector.setBounds(85, 250, 600, 600);
            fondo.add(mascotaLector);

        //---------------- BOTON VOLVER  ----------------
        JButton btnVolver = new JButton("Volver");
        btnVolver.setFont(fuente1.deriveFont(25f));
        btnVolver.setForeground(Color.BLACK);
        btnVolver.setBounds(215, 800, 280, 55); 
        btnVolver.addActionListener(e -> dispose()); 
        fondo.add(btnVolver);
    }
    
  public static void main(String[] args) {
        new ComoJugar();
    }
}
