// INICIAR SESIOOOOOOOOOOOOOOOON
package main;

import java.awt.*;
import javax.swing.*;

public class IniciarSesion extends JFrame {
    private FondoPanel fondo;
    private Font fuente1;
    private Font fuente2;
    
     public IniciarSesion () {
         // importamos ambas fuentes
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
        
        fondo = new FondoPanel("/utilidades/fondoUnoK.png");
        setContentPane(fondo);
        setTitle("Inicio de sesión");
        setSize(1880,1080);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        fondo.setLayout(null);
        crearComponentes();
        setVisible(true);
    }
     private void crearComponentes() {
        //---------------- L O G O ----------------
        JLabel logo = new JLabel();
        ImageIcon logoIcon = new ImageIcon(getClass().getResource("/utilidades/logofK.png"));
        Image logoEscalado = logoIcon.getImage().getScaledInstance(150,150,Image.SCALE_SMOOTH );
        logo.setIcon(new ImageIcon(logoEscalado));
        logo.setBounds(900,40,150,150);
        fondo.add(logo);
        
        //---------------- MASCOTA ----------------
        JLabel mascota = new JLabel();
        ImageIcon mascotaIcon = new ImageIcon(getClass().getResource("/utilidades/mascota1.png"));
        Image mascotaEscalada = mascotaIcon.getImage().getScaledInstance(191,264,Image.SCALE_SMOOTH);
        mascota.setIcon(new ImageIcon(mascotaEscalada));
        mascota.setBounds(1250,550,191,264);
        fondo.add(mascota);
        
        //---------------- ESLOGAN ----------------
        JLabel titulo = new JLabel("No es magia, es mente ");
        titulo.setFont(fuente1.deriveFont(30f));
        titulo.setForeground(Color.BLUE);
        titulo.setBounds(865,190,400,60);
        fondo.add(titulo);
        
        //---------------- USUARIO ----------------
        JLabel lblUsuario = new JLabel("Nombre: ");
        lblUsuario.setFont(fuente2.deriveFont(25f));
        lblUsuario.setForeground(Color.WHITE);
        lblUsuario.setBounds(760,380,200,30);
        fondo.add(lblUsuario);
        
        JTextField txtUsuario = new JTextField();
        txtUsuario.setFont(fuente1.deriveFont(25f));
        txtUsuario.setBounds(760,415,400,50);
        fondo.add(txtUsuario);
        
        //---------------- PASSWORD ----------------
        JLabel lblPassword = new JLabel("Password: ");
        lblPassword.setFont(fuente2.deriveFont(25f));
        lblPassword.setForeground(Color.WHITE);
        lblPassword.setBounds(760,490,200,30);
        fondo.add(lblPassword);
        
        JPasswordField txtPassword = new JPasswordField();
        txtPassword.setFont(fuente1.deriveFont(40f));
        txtPassword.setBounds(760,525,400,50);
        fondo.add(txtPassword);

        //---------------- BOTON ----------------
        JButton btnLogIn = new JButton("INICIAR SESION");
        btnLogIn.setFont(fuente2.deriveFont(15f));
        btnLogIn.setBounds(820,650,285,60);
        fondo.add(btnLogIn);
    }

    public static void main(String[] args) {

        new IniciarSesion();
}
}