package main.Usuario;

import java.awt.*;
import java.net.URL;
import javax.swing.*;
import main.Menu.FondoPanel;
import main.Menu.DecoracionBotones;

public class RecuperarContrasena extends JFrame {

    private JPanel fondo;
    private Font fuente1;
    private Font fuente2;

    public RecuperarContrasena() {
        try {
            fuente1 = Font.createFont(
                    Font.TRUETYPE_FONT,
                    getClass().getResourceAsStream("/fuentes/LettersForLearners.ttf"));

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

        URL iconUrl = getClass().getResource("/Multimedia/utiles/logotipo/logofK.png");
        ImageIcon icono = new ImageIcon(iconUrl);
        setIconImage(icono.getImage());

        setTitle("Recuperar Contraseña");
        setSize(700, 450);
        setResizable(false);
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

        JLabel lblIndicacion = new JLabel("Ingrese su correo electronico: ");
        lblIndicacion.setFont(fuente2.deriveFont(25F));
        lblIndicacion.setForeground(Color.WHITE);
        lblIndicacion.setBounds(20, 20, 365, 45);
        panelContenedor.add(lblIndicacion);

        JTextField txtCorreo = new JTextField();
        txtCorreo.setBounds(20, 95, 350, 40);
        txtCorreo.setFont(fuente1.deriveFont(20f));
        panelContenedor.add(txtCorreo);

        JButton btnAceptar = new DecoracionBotones("ENVIAR CÓDIGO",
                DecoracionBotones.AZUL, DecoracionBotones.GRIS, DecoracionBotones.AMARILLO,
                DecoracionBotones.CELESTE, DecoracionBotones.AZUL, DecoracionBotones.AZUL);
        btnAceptar.setFont(fuente2.deriveFont(15f));
        btnAceptar.setBounds(95, 230, 200, 45);
        panelContenedor.add(btnAceptar);

        btnAceptar.addActionListener(e -> {
            String correo = txtCorreo.getText().trim();

            if (correo.isEmpty()) {
                JOptionPane.showMessageDialog(
                        this,
                        "Ingrese su correo electrónico",
                        "Campo vacío",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            if (!correo.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {
                JOptionPane.showMessageDialog(
                        this,
                        "Ingrese un correo electrónico válido.",
                        "Correo inválido",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            btnAceptar.setEnabled(false);

            SwingWorker<Void, Void> worker = new SwingWorker<>() {
                @Override
                protected Void doInBackground() {
                    EnvioCorreo.enviarCodigo(correo);
                    return null;
                }

                @Override
                protected void done() {
                    btnAceptar.setEnabled(true);
                    JOptionPane.showMessageDialog(
                            RecuperarContrasena.this,
                            "✔ Código enviado correctamente.\n\nRevisa tu bandeja de entrada.",
                            "Código enviado",
                            JOptionPane.INFORMATION_MESSAGE);
                    new VerificarCodigo(correo);
                    dispose();
                }
            };
            worker.execute();
        });

        JLabel mascota = new JLabel();
        ImageIcon mascotaIcon = new ImageIcon(getClass().getResource("/Multimedia/utiles/mascotaKitsura/imagen/Zorro_kimono_azul.png"));
        Image mascotaEscalada = mascotaIcon.getImage().getScaledInstance(300, 300, Image.SCALE_SMOOTH);
        mascota.setIcon(new ImageIcon(mascotaEscalada));
        mascota.setBounds(420, 70, 300, 300);
        fondo.add(mascota);
    }

    public static void main(String[] args) {
        new RecuperarContrasena();
    }
}