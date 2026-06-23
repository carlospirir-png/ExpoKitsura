// REGISTRO INVITADO
package main.Usuario;

import java.awt.*;
import javax.swing.*;
import main.Menu.FondoPanel;

public class RegistroInvitado extends JFrame {

    private FondoPanel fondo;
    private Font fuente1, fuente2;
    private String nombreInvitado;

    private JTextField txtNombre;
    private JButton btnJugar, btnVolver;
    private JLabel logo, lblNombre, mascota;

    public RegistroInvitado() {

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

        fondo = new FondoPanel("/Multimedia/utiles/fondos/interfaces/fondoUnoK.png");

        setContentPane(fondo);
        setTitle("Registro (Invitado)");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);

        fondo.setLayout(null);

        crearComponentes();

        setVisible(true);
    }

    private void crearComponentes() {

        //---------------- LOGO ----------------
        logo = new JLabel();

        ImageIcon logoIcon = new ImageIcon(
                getClass().getResource("/Multimedia/utiles/logotipo/logofK.png"));

        Image logoEscalado = logoIcon.getImage().getScaledInstance(
                150, 150, Image.SCALE_SMOOTH);

        logo.setIcon(new ImageIcon(logoEscalado));
        logo.setBounds(900, 40, 150, 150);

        fondo.add(logo);

        //---------------- LABEL NOMBRE ----------------
        lblNombre = new JLabel("NOMBRE");
        lblNombre.setFont(fuente2.deriveFont(25f));
        lblNombre.setForeground(Color.WHITE);
        lblNombre.setBounds(760, 420, 200, 30);

        fondo.add(lblNombre);

        //---------------- CAMPO NOMBRE ----------------
        txtNombre = new JTextField();
        txtNombre.setFont(fuente1.deriveFont(24f));
        txtNombre.setBounds(760, 455, 400, 40);

        fondo.add(txtNombre);

        //---------------- BOTON JUGAR ----------------
        btnJugar = new JButton("JUGAR");
        btnJugar.setFont(fuente2.deriveFont(15f));
        btnJugar.setBounds(820, 600, 285, 60);

        btnJugar.addActionListener(e -> {

            if (registrarInvitado(txtNombre.getText())) {
                new MenuPrincipal();
                dispose();
            }
        });

        fondo.add(btnJugar);

        //---------------- BOTON VOLVER ----------------
        btnVolver = new JButton("VOLVER");
        btnVolver.setFont(fuente2.deriveFont(10f));
        btnVolver.setBounds(40, 985, 120, 40);

        btnVolver.addActionListener(e -> {

            new RegistroUsuario();
            dispose();

        });

        fondo.add(btnVolver);

        //---------------- MASCOTA ----------------
        mascota = new JLabel();

        ImageIcon mascotaIcon = new ImageIcon(
                getClass().getResource("/Multimedia/utiles/mascotaKitsura/imagen/REGISTRARSE_USUARIO-PARTIDA_MINIJUEGO-TABLETA.png"));

        Image mascotaEscalada = mascotaIcon.getImage().getScaledInstance(
                191, 264, Image.SCALE_SMOOTH);

        mascota.setIcon(new ImageIcon(mascotaEscalada));
        mascota.setBounds(1250, 550, 191, 264);

        fondo.add(mascota);
    }

    // Método para registrar al invitado sin base de datos
    private boolean registrarInvitado(String nombre) {

    try {

        nombre = nombre.trim();

        if (nombre.isEmpty()) {
            throw new IllegalArgumentException(
                    "Debe ingresar un nombre.");
        }

        if (nombre.length() > 20) {
            throw new IllegalArgumentException(
                    "El nombre no puede superar los 20 caracteres.");
        }

        nombreInvitado = nombre;

        JOptionPane.showMessageDialog(
                this,
                "¡Bienvenido " + nombreInvitado + "!",
                "Registro exitoso",
                JOptionPane.INFORMATION_MESSAGE);

        return true;

    } catch (IllegalArgumentException e) {

        JOptionPane.showMessageDialog(
                this,
                e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);

        return false;
    }
}

    // Getter por si necesitas usar el nombre en otra ventana
    public String getNombreInvitado() {
        return nombreInvitado;
    }
}