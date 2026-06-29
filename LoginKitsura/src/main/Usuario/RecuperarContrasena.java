package main.Usuario;

import java.awt.*;
import javax.swing.*;
import main.Menu.FondoPanel;
import main.Menu.DecoracionBotones;

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
        
        setTitle("Recuperar Contraseña");
        setSize(700, 450);
        setLocationRelativeTo(null); 
        setDefaultCloseOperation(DISPOSE_ON_CLOSE); 
        fondo.setLayout(null);
        crearComponentes();
        setVisible(true);
    }
    
    private void crearComponentes() {
        JPanel panelContenedor = new JPanel();
        panelContenedor.setLayout(null);
        panelContenedor.setBackground(new Color(0, 0, 0, 100)); 
        panelContenedor.setBounds(25, 60, 400, 310);
        fondo.add(panelContenedor);

        //---------------- T I T U L O ----------------
        JLabel lblIndicacion = new JLabel("Ingrese la contraseña actual");
        lblIndicacion.setFont(fuente2.deriveFont(25F));
        lblIndicacion.setForeground(Color.WHITE); 
        lblIndicacion.setBounds(20, 20, 365, 45);
        panelContenedor.add(lblIndicacion);

        //---------------- CAMPO DE TEXTO ----------------
        JPasswordField txtPasswordActual = new JPasswordField();
        txtPasswordActual.setBounds(20, 95, 350, 40);
        panelContenedor.add(txtPasswordActual);
        
        //---------------- BOTON ACEPTAR ----------------
        JButton btnAceptar = new DecoracionBotones("ACEPTAR",
                                //ColorBase             ColorBorde              ColorLetra
                DecoracionBotones.AZUL, DecoracionBotones.GRIS, DecoracionBotones.AMARILLO, //MOUSE FUERA
                DecoracionBotones.CELESTE, DecoracionBotones.AZUL, DecoracionBotones.AZUL); //MOUSE DENTRO   

        btnAceptar.setFont(fuente2.deriveFont(20f));
        btnAceptar.setBounds(115, 230, 150, 45);
        panelContenedor.add(btnAceptar);

        //---------------- MASCOTA ----------------
        JLabel mascota = new JLabel();
        ImageIcon mascotaIcon = new ImageIcon(getClass().getResource("/Multimedia/utiles/mascotaKitsura/imagen/MINIJUEGO-CONTROL.png")); 
        Image mascotaEscalada = mascotaIcon.getImage().getScaledInstance(300, 300, Image.SCALE_SMOOTH);
        mascota.setIcon(new ImageIcon(mascotaEscalada));
        mascota.setBounds(420, 70, 300, 300);
        fondo.add(mascota);
    }
    
    public static void main(String[] args) {
        new RecuperarContrasena();
    }
}
