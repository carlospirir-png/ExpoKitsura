package main.Usuario;

import java.awt.*;
import javax.swing.*;
import main.Menu.FondoPanel;

public class SeAcaboTiempo extends JFrame {
    private FondoPanel fondo;
    private Font fuente1;
    private Font fuente2;
    
    public SeAcaboTiempo() {
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
        setTitle("Se acabo el tiempo");
        setSize(1980, 1060); 
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH); 
        fondo.setLayout(null);        
        crearComponentes();
        setVisible(true);
    }
    
    private void crearComponentes() {
        //---------------- TÍTULO PRINCIPAL ----------------
        JLabel lblGameOver = new JLabel("GAME OVER", JLabel.CENTER);
        lblGameOver.setFont(fuente2.deriveFont(34f));
        lblGameOver.setForeground(Color.WHITE);
        lblGameOver.setBounds(900, 100, 800, 60);
        fondo.add(lblGameOver);

        //---------------- TITULO 2 ----------------
        JLabel lblSubtitulo = new JLabel("Se acabó el tiempo", JLabel.CENTER);
        lblSubtitulo.setFont(fuente2.deriveFont(28f));
        lblSubtitulo.setForeground(Color.WHITE);
        lblSubtitulo.setBounds(900, 170, 800, 45);
        fondo.add(lblSubtitulo);

        //---------------- CITA DE WINSTON CHURCHILL ----------------
        JLabel lblCita = new JLabel("<<El éxito no es definitivo; el fracaso no es fatal. Lo que realmente cuenta es tener valor para continuar>>. -Winston churchill", JLabel.CENTER);
        lblCita.setFont(fuente1.deriveFont(24f));
        lblCita.setForeground(Color.WHITE);
        lblCita.setBounds(900, 240, 800, 30);
        fondo.add(lblCita);

        //---------------- MASCOTA  ----------------
            JLabel mascotaReloj = new JLabel();
            ImageIcon iconMascota = new ImageIcon(getClass().getResource("/Multimedia/utiles/mascotaKitsura/imagen/DERROTA-por-tiempo.png")); 
            Image imgEscalada = iconMascota.getImage().getScaledInstance(600, 600, Image.SCALE_SMOOTH);
            mascotaReloj.setIcon(new ImageIcon(imgEscalada));
            mascotaReloj.setBounds(150, 250, 600, 600);
            fondo.add(mascotaReloj);

        //---------------- BOTON VOLVER  ----------------
        JButton btnVolver = new JButton("Volver");
        btnVolver.setFont(fuente1.deriveFont(25f));
        btnVolver.setForeground(Color.BLACK);
        btnVolver.setBounds(1225, 770, 200, 50);
        btnVolver.addActionListener(e -> dispose()); 
        fondo.add(btnVolver);
    }
    
    public static void main(String[] args) {
        new SeAcaboTiempo();
    }
}
