package main.Usuario;

import java.awt.*;
import javax.swing.*;
import main.Menu.FondoPanel;
import main.Menu.DecoracionBotones;

public class RecuperarContrasena extends JFrame {

    private JPanel fondo;
    private Font fuente1;
    private Font fuente2;
    private static String codigoRecuperacion;

    /* Variable global que nos ayudara a generar el
                                                codigo para el recuperar contraseña*/

    public RecuperarContrasena() {
        try {
            // LettersForLearners
            fuente1 = Font.createFont(
                    Font.TRUETYPE_FONT,
                    getClass().getResourceAsStream("/fuentes/LettersForLearners.ttf"));

            // KGPerfectPenmanship
            fuente2 = Font.createFont(
                    Font.TRUETYPE_FONT,
                    getClass().getResourceAsStream("/fuentes/KGPerfectPenmanship.ttf"));

        } catch (Exception e) {
            e.printStackTrace();
            fuente1 = new Font("Arial", Font.PLAIN, 20);
            fuente2 = new Font("Arial", Font.PLAIN, 20);
        }
        
        fondo = new JPanel();
        fondo.setBackground(new Color(130, 211, 224));
        setContentPane(fondo);

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
        JLabel lblIndicacion = new JLabel("Ingrese su nombre de usuario: ");
        lblIndicacion.setFont(fuente2.deriveFont(25F));
        lblIndicacion.setForeground(Color.WHITE);
        lblIndicacion.setBounds(20, 20, 365, 45);
        panelContenedor.add(lblIndicacion);

        //---------------- CAMPO DE TEXTO ----------------
        JTextField txtUsuario = new JTextField();
        txtUsuario.setBounds(20, 95, 350, 40);
        txtUsuario.setFont(fuente1.deriveFont(20f));
        panelContenedor.add(txtUsuario);

        //---------------- BOTON ACEPTAR ----------------
        JButton btnAceptar = new DecoracionBotones("ENVIAR CÓDIGO",
                //ColorBase             ColorBorde              ColorLetra
                DecoracionBotones.AZUL, DecoracionBotones.GRIS, DecoracionBotones.AMARILLO, //MOUSE FUERA
                DecoracionBotones.CELESTE, DecoracionBotones.AZUL, DecoracionBotones.AZUL); //MOUSE DENTRO   
        btnAceptar.setFont(fuente2.deriveFont(15f));
        btnAceptar.setBounds(95, 230, 200, 45);
        panelContenedor.add(btnAceptar);
        btnAceptar.addActionListener(e -> {
            // Programamos el boton que nos ayudara a:
            String usuario = txtUsuario.getText().trim();
            // Verificar si el usuario ha dejado vacio el campo
            if (usuario.isEmpty()) {
                JOptionPane.showMessageDialog(
                        this,
                        "Ingrese un nombre de usuario.");
                return; // mostrara la frase "Ingrese un nombre de usuario."
            }

            codigoRecuperacion = generarCodigo(); // es igual al metodo creado

            JOptionPane.showMessageDialog(
                    this,
                    "Su código de recuperación es:\n\n" + codigoRecuperacion);
            // Abre la nueva interfaz donde el usuario ingresara el codigo generado
            new VerificarCodigo();
            dispose();
        });

        //---------------- MASCOTA ----------------
        JLabel mascota = new JLabel();
        ImageIcon mascotaIcon = new ImageIcon(getClass().getResource("/Multimedia/utiles/mascotaKitsura/imagen/MINIJUEGO-CONTROL.png"));
        Image mascotaEscalada = mascotaIcon.getImage().getScaledInstance(300, 300, Image.SCALE_SMOOTH);
        mascota.setIcon(new ImageIcon(mascotaEscalada));
        mascota.setBounds(420, 70, 300, 300);
        fondo.add(mascota);
    }

    // Metodo creado para generar codigo
    private String generarCodigo() {
        int numero = (int) (Math.random() * 900000) + 100000; // Creara codigos siempre de 6 digitos
        return String.valueOf(numero);
    }

    // Método para obtener el código generado
    public static String getCodigoRecuperacion() {
        return codigoRecuperacion;
    }

    public static void main(String[] args) {
        new RecuperarContrasena();
    }

}
