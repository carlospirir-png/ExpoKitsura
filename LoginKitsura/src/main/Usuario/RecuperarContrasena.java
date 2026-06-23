
package main.Usuario;

import java.awt.*;
import javax.swing.*;
import main.Menu.FondoPanel;

public class RecuperarContrasena extends JFrame {
    private FondoPanel fondo;
    private Font fuente1;
    private Font fuente2;
    
    public RecuperarContrasena (){
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
        setContentPane (fondo);
        setUndecorated(true);
        setSize(600, 400);
        setLocationRelativeTo(null); 
        setDefaultCloseOperation(DISPOSE_ON_CLOSE); 
        fondo.setLayout(null);
        crearComponentes();
        setVisible(true);
    }
    private void crearComponentes() {
        JPanel barra = new JPanel();
        barra.setLayout(null);
        barra.setBackground(Color.WHITE);
        barra.setBounds(0, 0, 600, 40);
        fondo.add(barra);

        JLabel titulo = new JLabel(" ");
        titulo.setFont(fuente1.deriveFont(10f));
        titulo.setBounds(15, 0, 220, 40);
        barra.add(titulo);

        JButton btnCerrar = new JButton("X");
        btnCerrar.setFont(fuente1.deriveFont(15f));
        btnCerrar.setBounds(550, 5, 40, 30);
        btnCerrar.setFocusable(false);
        btnCerrar.setBorderPainted(false);
        btnCerrar.setBackground(new Color(245, 245, 245));
        btnCerrar.setForeground(new Color(180, 50, 50));
        btnCerrar.addActionListener(e -> dispose());
        barra.add(btnCerrar);

        JSeparator linea = new JSeparator();
        linea.setBounds(0, 39, 600, 1);
        barra.add(linea);
        
        //---------------- T I T U L O ----------------
        JLabel lblIndicacion = new JLabel("Ingrese la contraseña actual");
        lblIndicacion.setFont(fuente2.deriveFont(25f));
        lblIndicacion.setForeground(Color.WHITE); 
        lblIndicacion.setBounds(35, 125, 450, 45);
        fondo.add(lblIndicacion);

        //---------------- CAMPO DE TEXTO ----------------
        JPasswordField txtPasswordActual = new JPasswordField();
        txtPasswordActual.setBounds(50, 195, 300, 45);
        fondo.add(txtPasswordActual);
        
        //---------------- BOTON ACEPTAR ----------------
        JButton btnAceptar = new JButton("Aceptar");
        btnAceptar.setFont(fuente1.deriveFont(25f));
        btnAceptar.setBounds(125, 275, 150, 45);
        fondo.add(btnAceptar);

        //---------------- MASCOTA ----------------
        JLabel mascota = new JLabel();
        ImageIcon mascotaIcon = new ImageIcon(getClass().getResource("/Multimedia/utiles/mascotaKitsura/imagen/MINIJUEGO-CONTROL.png")); 
        Image mascotaEscalada = mascotaIcon.getImage().getScaledInstance(300, 300, Image.SCALE_SMOOTH);
        mascota.setIcon(new ImageIcon(mascotaEscalada));
        mascota.setBounds(330, 90, 300, 300);
        fondo.add(mascota);

    }
}
