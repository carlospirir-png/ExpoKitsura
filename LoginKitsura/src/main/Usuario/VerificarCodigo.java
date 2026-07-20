package main.Usuario;

import java.awt.*;
import java.net.URL;
import javax.swing.*;
import main.Menu.FondoPanel;
import main.Menu.DecoracionBotones;

public class VerificarCodigo extends JFrame {
    private FondoPanel fondo;
    private Font fuente1;
    private Font fuente2;
    
    //-------------- STRING
    private String correo; // correo recibido desde RecuperarContrasena
    
    public VerificarCodigo (String correo){
        
        this.correo = correo;
        
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
        setContentPane(fondo);
        //------------- ÍCONO ------------------
        URL iconUrl = getClass().getResource("/Multimedia/utiles/logotipo/logofK.png");
        ImageIcon icono = new ImageIcon(iconUrl);
        setIconImage(icono.getImage());

        setTitle("Recuperar Contraseña - Verificar");
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
        JLabel lblTitulo = new JLabel("Recuperar contraseña", SwingConstants.CENTER);
        lblTitulo.setFont(fuente2.deriveFont(28F)); 
        lblTitulo.setForeground(Color.WHITE); 
        lblTitulo.setBounds(20, 15, 360, 40);
        panelContenedor.add(lblTitulo);

        //---------------- S U B T I T U L O / I N F O ----------------
        JLabel lblInfo = new JLabel("Revisa tu correo e ingresa el código.", SwingConstants.CENTER);
        lblInfo.setFont(fuente1.deriveFont(22F)); 
        lblInfo.setForeground(Color.WHITE); 
        lblInfo.setBounds(20, 60, 360, 30);
        panelContenedor.add(lblInfo);

        //---------------- E T I Q U E T A  C O D I G O ----------------
        JLabel lblCodigo = new JLabel("Código:", SwingConstants.CENTER);
        lblCodigo.setFont(fuente2.deriveFont(22F));
        lblCodigo.setForeground(Color.WHITE); 
        lblCodigo.setBounds(20, 110, 360, 30);
        panelContenedor.add(lblCodigo);

        //---------------- CAMPO DE TEXTO ----------------
        JTextField txtCodigo = new JTextField();
        txtCodigo.setHorizontalAlignment(JTextField.CENTER); 
        txtCodigo.setBounds(45, 150, 310, 40);
        panelContenedor.add(txtCodigo);
        txtCodigo.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyTyped(java.awt.event.KeyEvent e) {
                char c = e.getKeyChar();

                // Solo permitir números
                if (!Character.isDigit(c)) {
                    e.consume();
                    return;
                }

                // Máximo 6 dígitos
                if (txtCodigo.getText().length() >= 6) {
                    e.consume();
                }
            }
        });
        
        //---------------- BOTON VERIFICAR ----------------
        JButton btnVerificar = new DecoracionBotones("VERIFICAR",
                                //ColorBase             ColorBorde              ColorLetra
                DecoracionBotones.AZUL, DecoracionBotones.GRIS, DecoracionBotones.AMARILLO, //MOUSE FUERA
                DecoracionBotones.CELESTE, DecoracionBotones.AZUL, DecoracionBotones.AZUL); //MOUSE DENTRO   

        btnVerificar.setFont(fuente2.deriveFont(16f));
        btnVerificar.setBounds(100, 230, 200, 45);
        panelContenedor.add(btnVerificar);
        btnVerificar.addActionListener(e -> {

            String codigoIngresado = txtCodigo.getText().trim();

            if(codigoIngresado.isEmpty()){
                JOptionPane.showMessageDialog(
                        this,
                        "Ingrese el código de recuperación.",
                        "Campo vacío",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            if(EnvioCorreo.verificarCodigo(codigoIngresado)){

                JOptionPane.showMessageDialog(
                        this,
                        "✔ Código verificado correctamente.",
                        "Verificación exitosa",
                        JOptionPane.INFORMATION_MESSAGE);

                EnvioCorreo.limpiarCodigo();

                new NuevaContrasena(correo);
                dispose();

            }else{

                JOptionPane.showMessageDialog(
                        this,
                        "✘ Código incorrecto.\n\nVerifica el código enviado a tu correo e intenta nuevamente.",
                        "Error de verificación",
                        JOptionPane.ERROR_MESSAGE);

            }

        });
        
        //---------------- MASCOTA ----------------
        JLabel mascota = new JLabel();
        ImageIcon mascotaIcon = new ImageIcon(getClass().getResource("/Multimedia/utiles/mascotaKitsura/imagen/Zorro_banderrilla.png")); 
        Image mascotaEscalada = mascotaIcon.getImage().getScaledInstance(300, 300, Image.SCALE_SMOOTH);
        mascota.setIcon(new ImageIcon(mascotaEscalada));
        mascota.setBounds(420, 70, 300, 300);
        fondo.add(mascota);
    }
    
}