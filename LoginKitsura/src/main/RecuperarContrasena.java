
package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class RecuperarContrasena extends JFrame {
    private FondoPanel fondo;
    
    public RecuperarContrasena (){
        fondo = new FondoPanel("/main/fondoTresK.png");
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
        //---------------- T I T U L O ----------------
        JLabel lblIndicacion = new JLabel("INGRESE LA CONTRASEÑA ACTUAL");
        lblIndicacion.setFont(new Font("Arial", Font.BOLD, 18));
        lblIndicacion.setForeground(Color.WHITE); 
        lblIndicacion.setBounds(45, 145, 365, 35);
        fondo.add(lblIndicacion);

        //---------------- CAMPO DE TEXTO ----------------
        JPasswordField txtPasswordActual = new JPasswordField();
        txtPasswordActual.setBounds(50, 195, 300, 45);
        fondo.add(txtPasswordActual);
        
        //---------------- BOTON ACEPTAR ----------------
        JButton btnAceptar = new JButton("Aceptar");
        btnAceptar.setFont(new Font("Arial", Font.BOLD, 16));
        btnAceptar.setBounds(125, 275, 150, 45);
        fondo.add(btnAceptar);

        //---------------- MASCOTA ----------------
        JLabel mascota = new JLabel();
        ImageIcon mascotaIcon = new ImageIcon(getClass().getResource("/main/mascota3.png")); 
        Image mascotaEscalada = mascotaIcon.getImage().getScaledInstance(160, 245, Image.SCALE_SMOOTH);
        mascota.setIcon(new ImageIcon(mascotaEscalada));
        mascota.setBounds(410, 110, 160, 245);
        fondo.add(mascota);
        
        //---------------- BOTON CERRAR ÓPTIMO ----------------
        JButton btnCerrar = new JButton("X");
        btnCerrar.setFont(new Font("Arial", Font.BOLD, 9));
        btnCerrar.setForeground(Color.RED);
        btnCerrar.setBounds(550, 10, 40, 30);
        btnCerrar.setFocusable(false);
        btnCerrar.addActionListener(e -> dispose());        
        fondo.add(btnCerrar);
    }
    
    public static void main(String[] args) {
        new RecuperarContrasena();
    }
}
