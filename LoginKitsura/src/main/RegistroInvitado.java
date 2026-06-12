package main;

import java.awt.*;
import javax.swing.*;

public class RegistroInvitado extends JFrame {

    private FondoPanel fondo;
    private JLabel logo, lblNombre, mascota;
    private JTextField txtNombre;
    private JButton btnJugar, btnVolver;
    private String nombreInvitado;

    public RegistroInvitado() {
        fondo = new FondoPanel("/Multimedia/utiles/fondoUnoK.png");
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
        logo = new JLabel();
        ImageIcon logoIcon = new ImageIcon(getClass().getResource("/Multimedia/utiles/logofK.png"));
        Image logoEscalado = logoIcon.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);
        logo.setIcon(new ImageIcon(logoEscalado));
        logo.setBounds(900, 40, 150, 150);
        fondo.add(logo);

        //---------------- LABEL NOMBRE ----------------
        lblNombre = new JLabel("NOMBRE");
        lblNombre.setFont(new Font("Arial", Font.BOLD, 18));
        lblNombre.setForeground(Color.WHITE);
        lblNombre.setBounds(760, 380, 200, 30);
        fondo.add(lblNombre);

        txtNombre = new JTextField();
        txtNombre.setBounds(760, 415, 400, 40);
        fondo.add(txtNombre);

        //---------------- BOTON JUGAR ----------------
        btnJugar = new JButton("JUGAR");
        btnJugar.setBounds(820, 500, 285, 60);
        btnJugar.addActionListener(e -> jugar());
        fondo.add(btnJugar);

        //---------------- BOTON VOLVER ----------------
        btnVolver = new JButton("VOLVER");
        btnVolver.setBounds(40, 985, 120, 40);
        fondo.add(btnVolver);

        //---------------- MASCOTA ----------------
        mascota = new JLabel();
        ImageIcon mascotaIcon = new ImageIcon(getClass().getResource("/Multimedia/utiles/mascota1.png"));
        Image mascotaEscalada = mascotaIcon.getImage().getScaledInstance(191, 264, Image.SCALE_SMOOTH);
        mascota.setIcon(new ImageIcon(mascotaEscalada));
        mascota.setBounds(1250, 550, 191, 264);
        fondo.add(mascota);
    }

    private void jugar() {
        nombreInvitado = txtNombre.getText().trim();

        if (nombreInvitado.isEmpty()) {
            JOptionPane.showMessageDialog(
                    this,
                    "Ingrese un nombre para continuar.",
                    "Aviso",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        new MenuPrincipal();
        dispose();
    }
}
