package main.Usuario;

import java.awt.*;
import javax.swing.*;
import main.Menu.FondoPanel;
import main.Menu.DecoracionBotones;

public class ResultadoFinal extends JFrame {
    private FondoPanel fondo;
    private Font fuente1;
    private Font fuente2;
    private DecoracionBotones btnVolver;
    
    public ResultadoFinal() {
        fondo = new FondoPanel("/Multimedia/utiles/fondos/interfaces/fondoTresK.png"); 
        setContentPane(fondo);
        
        setTitle("Resultado Final");
        setSize(700, 450); 
        setLocationRelativeTo(null); 
        setDefaultCloseOperation(DISPOSE_ON_CLOSE); 
        fondo.setLayout(null);
        crearComponentes();
        setVisible(true);
    }
    
    private void crearComponentes() {
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
        
        JPanel panelContenedor = new JPanel();
        panelContenedor.setLayout(null);
        panelContenedor.setBackground(new Color(0, 0, 0, 100)); 
        panelContenedor.setBounds(40, 50, 430, 320);
        fondo.add(panelContenedor);

        //---------------- TEXTO PUNTAJE ----------------
        JLabel lblPuntaje = new JLabel("Puntaje:");
        lblPuntaje.setFont(fuente2.deriveFont(25f));
        lblPuntaje.setForeground(Color.WHITE); 
        lblPuntaje.setBounds(25, 60, 270, 30);
        panelContenedor.add(lblPuntaje);
        
        //---------------- TEXTO TIEMPO ----------------
        JLabel lblTiempo = new JLabel("Tiempo:");
        lblTiempo.setFont(fuente2.deriveFont(25f));
        lblTiempo.setForeground(Color.WHITE); 
        lblTiempo.setBounds(25, 140, 270, 30);
        panelContenedor.add(lblTiempo);
        
        //---------------- BOTON VOLVER ----------------
        JButton btnVolver = new DecoracionBotones("VOLVER");
        btnVolver.setFont(fuente2.deriveFont(20f));
        btnVolver.setBounds(135, 240, 160, 45);
        btnVolver.addActionListener(e -> dispose());
        panelContenedor.add(btnVolver);

        //---------------- MASCOTA  ----------------
        JLabel mascota = new JLabel();
        ImageIcon mascotaIcon = new ImageIcon(getClass().getResource("/Multimedia/utiles/mascotaKitsura/imagen/VENTANITA.png")); 
        Image mascotaEscalada = mascotaIcon.getImage().getScaledInstance(350, 350, Image.SCALE_SMOOTH);
        mascota.setIcon(new ImageIcon(mascotaEscalada));
        mascota.setBounds(400, 190, 350, 350);
        fondo.add(mascota);
    }
    
    public static void main(String[] args) {
        new ResultadoFinal();
    }
}
