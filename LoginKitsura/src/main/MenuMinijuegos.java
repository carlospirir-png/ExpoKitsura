
package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class MenuMinijuegos extends JFrame {
    private FondoPanel fondo;
    
    public MenuMinijuegos () {
        fondo = new FondoPanel("/main/fondoDosK.png");
        setContentPane(fondo);
        setTitle("Minijuegos");
        setSize(1880,1080);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        fondo.setLayout(null);
        crearComponentes();
        setVisible(true);
    }
    private void crearComponentes (){        
        //---------------- T I T U L O ----------------
        JLabel lblTitulo = new JLabel("MINIJUEGOS");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 36));
        lblTitulo.setForeground(Color.WHITE); 
        lblTitulo.setBounds(880, 120, 500, 50); 
        fondo.add(lblTitulo);
        
        //---------------- B O T O N E S ----------------
        // >. H I D D E N   F O X
        JButton btnHiddenFox = new JButton("HIDDEN FOX");
        btnHiddenFox.setFont(new Font("Arial", Font.BOLD, 16));
        btnHiddenFox.setBounds(780, 340, 400, 55);
        fondo.add(btnHiddenFox);
        // >. F O X   J U M P !
        JButton btnFoxJump = new JButton("FOX JUMP !");
        btnFoxJump.setFont(new Font("Arial", Font.BOLD, 16));
        btnFoxJump.setBounds(780, 460, 400, 55);
        fondo.add(btnFoxJump);
        // >. M A U L W U R F   R E N N T
        JButton btnMaulwurf = new JButton("MAULWURF RENNT");
        btnMaulwurf.setFont(new Font("Arial", Font.BOLD, 16));
        btnMaulwurf.setBounds(780, 590, 400, 55);
        fondo.add(btnMaulwurf);
        // V O L V E R
        JButton btnVolver = new JButton ("VOLVER");
        btnVolver.setFont(new Font("Arial", Font.BOLD, 14));
        btnVolver.setBounds(850, 725, 250, 45); 
        fondo.add(btnVolver);
        
        //---------------- M A S C O T A S ----------------
        JLabel mascota = new JLabel();
        ImageIcon mascotaIcon = new ImageIcon(getClass().getResource("/main/mascota3.png")); 
        Image mascotaEscalada = mascotaIcon.getImage().getScaledInstance(181, 264, Image.SCALE_SMOOTH);
        mascota.setIcon(new ImageIcon(mascotaEscalada));
        mascota.setBounds(1465, 560, 181, 264);
        fondo.add(mascota);
    }
    
    public static void main(String[] args) {
        new MenuMinijuegos();
}
}
