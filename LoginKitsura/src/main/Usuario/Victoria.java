package main.Usuario;

import java.awt.*;
import javax.swing.*;
import main.Menu.FondoPanel;

public class Victoria extends JFrame {
    private FondoPanel fondo;
    private Font fuente1;
    private Font fuente2;
    
    public Victoria() {
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
        fondo = new FondoPanel("/utilidades/fondoCincoK.png"); 
        setContentPane(fondo);
        setTitle("Victoria");
        setSize(1980, 1060); 
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH); 
        fondo.setLayout(null);
        crearComponentes();
        setVisible(true);
    }
    
    private void crearComponentes() {
        //---------------- TÍTULO ----------------
        JLabel lblGanado = new JLabel("¡Has Ganado!", JLabel.CENTER);
        lblGanado.setFont(fuente2.deriveFont(35f));
        lblGanado.setForeground(Color.WHITE);
        lblGanado.setBounds(100, 120, 700, 60);
        fondo.add(lblGanado);

        //---------------- FRASE DE MOTIVACIÓN ----------------
        JLabel lblFrase = new JLabel("-- Tu potencial es infinito, atrévete a explorarlo --.", JLabel.CENTER);
        lblFrase.setFont(fuente1.deriveFont(30f));
        lblFrase.setForeground(Color.DARK_GRAY);
        lblFrase.setBounds(100, 200, 700, 35);
        fondo.add(lblFrase);

        //---------------- MASCOTA ----------------
            JLabel mascotaCongrats = new JLabel();
            ImageIcon iconMascota = new ImageIcon(getClass().getResource("/utilidades/mascota5.png")); 
            Image imgEscalada = iconMascota.getImage().getScaledInstance(600, 600, Image.SCALE_SMOOTH);
            mascotaCongrats.setIcon(new ImageIcon(imgEscalada));
            mascotaCongrats.setBounds(225, 270, 600, 600);
            fondo.add(mascotaCongrats);

        //---------------- FRASE INFERIOR ----------------
        JLabel lblFraseAbajo = new JLabel("Aún con errores, Pudiste lograrlo", JLabel.CENTER);
        lblFraseAbajo.setFont(fuente1.deriveFont(15f));
        lblFraseAbajo.setForeground(Color.WHITE);
        lblFraseAbajo.setBounds(100, 740, 700, 35);
        fondo.add(lblFraseAbajo);

        //---------------- DATOS DE USUARIO ----------------
        JLabel lblUsuario = new JLabel("Nombre de usuario"); 
        lblUsuario.setFont(fuente2.deriveFont(25f));
        lblUsuario.setForeground(Color.BLACK);
        lblUsuario.setBounds(1250, 150, 400, 40);
        fondo.add(lblUsuario);

            JLabel fotoPerfil = new JLabel();
            ImageIcon paisajeIcon = new ImageIcon(getClass().getResource("/utilidades/logofK.png"));
            Image paisajeEscalado = paisajeIcon.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);
            fotoPerfil.setIcon(new ImageIcon(paisajeEscalado));
            fotoPerfil.setBounds(1030, 115, 150, 150);
            fondo.add(fotoPerfil); 
        
        //---------------- BOTON VOLVER  ----------------
        JButton btnVolver = new JButton("Volver");
        btnVolver.setFont(fuente1.deriveFont(25f));
        btnVolver.setBounds(1320, 710, 200, 50);
        btnVolver.addActionListener(e -> dispose());
        fondo.add(btnVolver);
    }
}

