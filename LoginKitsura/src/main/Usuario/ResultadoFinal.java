package main.Usuario;

import java.awt.*;
import javax.swing.*;
import main.Menu.FondoPanel;

public class ResultadoFinal extends JFrame {
    private FondoPanel fondo;
    private Font fuente1;
    private Font fuente2;
    
    public ResultadoFinal() {
        fondo = new FondoPanel("/Multimedia/utiles/fondoTresK.png"); 
        setContentPane(fondo);
        setUndecorated(true);
        setSize(600, 400); 
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
        //---------------- BOTON CERRAR (X) ----------------
        JButton btnCerrar = new JButton("X");
        btnCerrar.setFont(new Font("Arial", Font.BOLD, 9));
        btnCerrar.setForeground(Color.BLACK);
        btnCerrar.setBounds(550, 10, 40, 30);
        btnCerrar.setFocusable(false);
        btnCerrar.addActionListener(e -> dispose());        
        fondo.add(btnCerrar);

        //---------------- TEXTO PUNTAJE ----------------
        JLabel lblPuntaje = new JLabel("Puntaje:");
        lblPuntaje.setFont(fuente2.deriveFont(25f));
        lblPuntaje.setForeground(Color.BLACK);
        lblPuntaje.setBounds(60, 140, 300, 30);
        fondo.add(lblPuntaje);
        
        //---------------- TEXTO TIEMPO ----------------
        JLabel lblTiempo = new JLabel("Tiempo:");
        lblTiempo.setFont(fuente2.deriveFont(25f));
        lblTiempo.setForeground(Color.BLACK);
        lblTiempo.setBounds(60, 200, 300, 30);
        fondo.add(lblTiempo);
        
        //---------------- BOTON VOLVER ----------------
        JButton btnVolver = new JButton("Volver");
        btnVolver.setFont(fuente1.deriveFont(25f));
        btnVolver.setBounds(200, 310, 160, 45);
        btnVolver.addActionListener(e -> dispose());
        fondo.add(btnVolver);

        //---------------- MASCOTA SALIENDO DESDE ABAJO (Derecha) ----------------
            JLabel mascota = new JLabel();
            ImageIcon mascotaIcon = new ImageIcon(getClass().getResource("/Multimedia/utiles/mascota4.png")); 
            Image mascotaEscalada = mascotaIcon.getImage().getScaledInstance(300, 300, Image.SCALE_SMOOTH);
            mascota.setIcon(new ImageIcon(mascotaEscalada));
            mascota.setBounds(370, 210, 300, 300);
            fondo.add(mascota);
    }
}
