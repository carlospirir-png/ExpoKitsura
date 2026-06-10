// 
package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class RegistroInvitado extends JFrame {
    private FondoPanel fondo;

    public RegistroInvitado() {
        fondo = new FondoPanel("/main/fondoUnoK.png");
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
        ImageIcon logoIcon = new ImageIcon(getClass().getResource("/main/logofK.png"));
        Image logoEscalado = logoIcon.getImage().getScaledInstance(150,150,Image.SCALE_SMOOTH);
        logo.setIcon(new ImageIcon(logoEscalado));
        logo.setBounds(900, 40, 150, 150);
        fondo.add(logo);

        //---------------- LABEL NOMBRE ----------------
        JLabel lblNombre = new JLabel("NOMBRE");
        lblNombre.setFont(new Font("Arial", Font.BOLD, 18));
        lblNombre.setForeground(Color.WHITE);
        lblNombre.setBounds( 760, 380, 200,30);
        fondo.add(lblNombre);

        JTextField txtNombre = new JTextField();
        txtNombre.setBounds(760,415,400,40);
        fondo.add(txtNombre);

        //---------------- BOTON JUGAR ----------------
        JButton btnJugar = new JButton("JUGAR");
        btnJugar.setBounds(820,500,285,60);
        fondo.add(btnJugar);

        //---------------- BOTON VOLVER ----------------
        JButton btnVolver = new JButton("VOLVER");
        btnVolver.setBounds(40,985,120,40);
        fondo.add(btnVolver);

        //---------------- MASCOTA ----------------
        JLabel mascota = new JLabel();
        ImageIcon mascotaIcon = new ImageIcon(getClass().getResource("/main/mascota1.png"));
        Image mascotaEscalada = mascotaIcon.getImage().getScaledInstance(191,264,Image.SCALE_SMOOTH);
        mascota.setIcon( new ImageIcon(mascotaEscalada));
        mascota.setBounds(1250,550,191,264);
        fondo.add(mascota);
    }

    public static void main(String[] args) {

        new RegistroInvitado();
    }
}
