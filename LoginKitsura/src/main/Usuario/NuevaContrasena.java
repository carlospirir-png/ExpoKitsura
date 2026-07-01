package main.Usuario;

import java.awt.*;
import javax.swing.*;
import main.Menu.FondoPanel;
import main.Menu.DecoracionBotones;

public class NuevaContrasena extends JFrame {
    private FondoPanel fondo;
    private Font fuente1;
    private Font fuente2;
    
    public NuevaContrasena (){
        try {
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
            fuente1 = new Font("Arial", Font.PLAIN, 20);
            fuente2 = new Font("Arial", Font.PLAIN, 20);
        }
        
        fondo = new FondoPanel("/Multimedia/utiles/fondos/interfaces/fondoTresK.png");
        setContentPane(fondo);
        
        setTitle("Establecer Nueva Contraseña");
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

        //---------------- N U E V A  C O N T R A S E Ñ A ----------------
        JLabel lblNuevaPassword = new JLabel("Nueva contraseña:");
        lblNuevaPassword.setFont(fuente2.deriveFont(22F));
        lblNuevaPassword.setForeground(Color.WHITE); 
        lblNuevaPassword.setBounds(25, 20, 350, 30);
        panelContenedor.add(lblNuevaPassword);

        JPasswordField txtNuevaPassword = new JPasswordField();
        txtNuevaPassword.setBounds(25, 55, 350, 40);
        panelContenedor.add(txtNuevaPassword);

        //---------------- C O N F I R M A R  C O N T R A S E Ñ A ----------------
        JLabel lblConfirmarPassword = new JLabel("Confirmar contraseña:");
        lblConfirmarPassword.setFont(fuente2.deriveFont(22F));
        lblConfirmarPassword.setForeground(Color.WHITE); 
        lblConfirmarPassword.setBounds(25, 115, 350, 30);
        panelContenedor.add(lblConfirmarPassword);

        JPasswordField txtConfirmarPassword = new JPasswordField();
        txtConfirmarPassword.setBounds(25, 150, 350, 40);
        panelContenedor.add(txtConfirmarPassword);
        
        //---------------- B O T O N  G U A R D A R ----------------
        JButton btnGuardar = new DecoracionBotones("GUARDAR",
                                //ColorBase             ColorBorde              ColorLetra
                DecoracionBotones.AZUL, DecoracionBotones.GRIS, DecoracionBotones.AMARILLO, //MOUSE FUERA
                DecoracionBotones.CELESTE, DecoracionBotones.AZUL, DecoracionBotones.AZUL); //MOUSE DENTRO   

        btnGuardar.setFont(fuente2.deriveFont(16f));
        btnGuardar.setBounds(100, 235, 200, 45);
        panelContenedor.add(btnGuardar);
        
        btnGuardar.addActionListener(e -> {

            String nuevaContrasena = new String(txtNuevaPassword.getPassword()).trim();
            String confirmarContrasena = new String(txtConfirmarPassword.getPassword()).trim();

            // Verificar campos vacíos
            if(nuevaContrasena.isEmpty() || confirmarContrasena.isEmpty()){
                JOptionPane.showMessageDialog(
                        this,
                        "Complete todos los campos.");
                return;
            }

            // Verificar longitud mínima
            if(nuevaContrasena.length() < 8){
                JOptionPane.showMessageDialog(
                        this,
                        "La contraseña debe tener al menos 8 caracteres.");
                return;
            }

            // Verificar que ambas coincidan
            if(!nuevaContrasena.equals(confirmarContrasena)){
                JOptionPane.showMessageDialog(
                        this,
                        "Las contraseñas no coinciden.");
                return;
            }

            // Simulación de cambio de contraseña
            JOptionPane.showMessageDialog(
                    this,
                    "Contraseña actualizada correctamente.");

            // Aquí más adelante irá el UPDATE de la base de datos

            dispose();

        });
        
        //---------------- M A S C O T A ----------------
        JLabel mascota = new JLabel();
        ImageIcon mascotaIcon = new ImageIcon(getClass().getResource("/Multimedia/utiles/mascotaKitsura/imagen/MINIJUEGO-CONTROL.png")); 
        Image mascotaEscalada = mascotaIcon.getImage().getScaledInstance(300, 300, Image.SCALE_SMOOTH);
        mascota.setIcon(new ImageIcon(mascotaEscalada));
        mascota.setBounds(420, 70, 300, 300);
        fondo.add(mascota);
    }
    
    public static void main(String[] args) {
        new NuevaContrasena();
    }
}
