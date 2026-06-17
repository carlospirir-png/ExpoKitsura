// REGISTRO INVITADO
package main;

import java.awt.*;
import javax.swing.*;

public class RegistroInvitado extends JFrame {
    private FondoPanel fondo;
    private Font fuente1;
    private Font fuente2;

    public RegistroInvitado() {
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
        setTitle("Registro (invitado)");
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
        ImageIcon logoIcon = new ImageIcon(getClass().getResource("/utilidades/logofK.png"));
        Image logoEscalado = logoIcon.getImage().getScaledInstance(150,150,Image.SCALE_SMOOTH);
        logo.setIcon(new ImageIcon(logoEscalado));
        logo.setBounds(900, 40, 150, 150);
        fondo.add(logo);

        //---------------- LABEL NOMBRE ----------------
        JLabel lblNombre = new JLabel("NOMBRE");
        lblNombre.setFont(fuente2.deriveFont(25f));
        lblNombre.setForeground(Color.WHITE);
        lblNombre.setBounds( 760, 420, 200,30);
        fondo.add(lblNombre);

        JTextField txtNombre = new JTextField();
        txtNombre.setFont(fuente1.deriveFont(24f));
        txtNombre.setBounds(760,455,400,40); 
        fondo.add(txtNombre);

        //---------------- BOTON JUGAR ----------------
        JButton btnJugar = new JButton("JUGAR");
        btnJugar.setFont(fuente2.deriveFont(15f));
        btnJugar.setBounds(820,600,285,60);
        fondo.add(btnJugar);

        //---------------- BOTON VOLVER ----------------
        JButton btnVolver = new JButton("VOLVER");
        btnVolver.setFont(fuente2.deriveFont(10f));
        btnVolver.setBounds(40,985,120,40);
        fondo.add(btnVolver);

        //---------------- MASCOTA ----------------
        JLabel mascota = new JLabel();
        ImageIcon mascotaIcon = new ImageIcon(getClass().getResource("/utilidades/mascota1.png"));
        Image mascotaEscalada = mascotaIcon.getImage().getScaledInstance(191,264,Image.SCALE_SMOOTH);
        mascota.setIcon( new ImageIcon(mascotaEscalada));
        mascota.setBounds(1250,550,191,264);
        fondo.add(mascota);
    }

    public static void main(String[] args) {

        new RegistroInvitado();
    }
}


