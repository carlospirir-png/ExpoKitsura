// REGISTRO USUARIOOOOOOOO
package main;

import java.awt.*;
import javax.swing.*;

public class RegistroUsuario extends JFrame {
    private FondoPanel fondo;
    private Font fuente1;
    private Font fuente2;
    
    public RegistroUsuario() {
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
        
        fondo = new FondoPanel("/Multimedia/utiles/fondoUnoK.png");
        setContentPane(fondo);
        setTitle("Registro");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        fondo.setLayout(null);
        crearComponentes();
        setVisible(true);
    }

    private void crearComponentes() { 
        //---------------- LOGO ----------------
        JLabel logo = new JLabel();
        ImageIcon logoIcon = new ImageIcon(getClass().getResource("/Multimedia/utiles/logofK.png"));
        Image logoEscalado = logoIcon.getImage().getScaledInstance(150,150,Image.SCALE_SMOOTH);
        logo.setIcon(new ImageIcon(logoEscalado));
        logo.setBounds(900, 40, 150, 150);
        fondo.add(logo);
        
        //---------------- MASCOTA ----------------
        JLabel mascota = new JLabel();
        ImageIcon mascotaIcon = new ImageIcon(getClass().getResource("/Multimedia/utiles/mascota1.png"));
        Image mascotaEscalada = mascotaIcon.getImage().getScaledInstance(191,264,Image.SCALE_SMOOTH);
        mascota.setIcon(new ImageIcon(mascotaEscalada));
        mascota.setBounds(1250, 550, 191, 264);
        fondo.add(mascota);

        //---------------- LABEL NOMBRE ----------------
        JLabel lblNombre = new JLabel("Nombre");
        lblNombre.setFont(fuente2.deriveFont(25f));
        lblNombre.setForeground(Color.WHITE);
        lblNombre.setBounds(760, 380, 200, 30);
        fondo.add(lblNombre);

        JTextField txtNombre = new JTextField();
        txtNombre.setFont(fuente1.deriveFont(22f));
        txtNombre.setBounds(760,415,400,50);
        fondo.add(txtNombre);
        
        //---------------- LABEL CORREO ----------------
        JLabel lblCorreo = new JLabel("Correo");
        lblCorreo.setFont(fuente2.deriveFont(25f));
        lblCorreo.setForeground(Color.WHITE);
        lblCorreo.setBounds(760, 480, 200, 30);
        fondo.add(lblCorreo);
        
        JTextField txtCorreo = new JTextField();
        txtCorreo.setFont(fuente1.deriveFont(22f));
        txtCorreo.setBounds(760,515,400,50);
        fondo.add(txtCorreo);
        
        //---------------- LABEL CONTRASEÑA ----------------
        JLabel lblPassword = new JLabel("Password");
        lblPassword.setFont(fuente2.deriveFont(25f));
        lblPassword.setForeground(Color.WHITE);
        lblPassword.setBounds(760, 580, 200, 30);
        fondo.add(lblPassword);
        
        JPasswordField txtPassword =new JPasswordField();
        txtPassword.setFont(fuente1.deriveFont(22f));
        txtPassword.setBounds(760,615,400,50);
        fondo.add(txtPassword);

        //---------------- BOTON JUGAR ----------------//
        JButton btnJugar = new JButton("JUGAR");
        btnJugar.setFont(fuente2.deriveFont(15f));
        btnJugar.setBounds(820, 815, 285,60);
        fondo.add(btnJugar);
    }
}
