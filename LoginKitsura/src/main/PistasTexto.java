package main;

import java.awt.*;
import javax.swing.*;

public class PistasTexto extends JFrame {
    private FondoPanel fondo;
    private Font fuente1;
    private Font fuente2;

    public PistasTexto() {
        try{
            // LettersForLearners
            fuente1 = Font.createFont(
                Font.TRUETYPE_FONT,
                getClass().getResourceAsStream("/fuentes/LettersForLearners.ttf"));
            // KGPerfectPenmanship
            fuente2 = Font.createFont(
                Font.TRUETYPE_FONT,
                getClass().getResourceAsStream("/fuentes/KGPerfectPenmanship.ttf"));
        } catch(Exception e){
            e.printStackTrace();
            fuente1 = new Font("Arial", Font.PLAIN, 20);
            fuente2 = new Font("Arial", Font.PLAIN, 20);
        }
        fondo = new FondoPanel("/utilidades/fondoTresK.png");
        setContentPane(fondo);
        setUndecorated(true);
        setSize(600, 440);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        fondo.setLayout(null);
        crearComponentes();
        setVisible(true);
    }

    private void crearComponentes() {
        //---------------- BARRA SUPERIOR ----------------
        JPanel barra = new JPanel();
        barra.setLayout(null);
        barra.setBackground(Color.WHITE);
        barra.setBounds(0, 0, 600, 40);
        fondo.add(barra);

        JButton btnCerrar = new JButton("X");
        btnCerrar.setFont(fuente1.deriveFont(15f));
        btnCerrar.setBounds(530, 5, 60, 30);
        btnCerrar.setFocusable(false);
        btnCerrar.setBorderPainted(false);
        btnCerrar.setBackground(new Color(245, 245, 245));
        btnCerrar.setForeground(new Color(180, 50, 50));
        btnCerrar.addActionListener(e -> dispose());
        barra.add(btnCerrar);

        JSeparator linea = new JSeparator();
        linea.setBounds(0, 39, 600, 1);
        barra.add(linea);

        //---------------- RECUADRO TEXTO ----------------
        JPanel recuadroTexto = new JPanel();
        recuadroTexto.setLayout(null);
        recuadroTexto.setBackground(Color.WHITE);
        recuadroTexto.setBounds(40, 100, 320, 180);
        fondo.add(recuadroTexto);

        JLabel lblTextoPredefinido = new JLabel("Texto predefinido");
        lblTextoPredefinido.setFont(fuente2.deriveFont(20f));
        lblTextoPredefinido.setForeground(Color.LIGHT_GRAY);
        lblTextoPredefinido.setBounds(20, 45, 280, 30);
        recuadroTexto.add(lblTextoPredefinido);

        JLabel lblTextoPredefinido2 = new JLabel("de la pista aquí...");
        lblTextoPredefinido2.setFont(fuente2.deriveFont(20f));
        lblTextoPredefinido2.setForeground(Color.LIGHT_GRAY);
        lblTextoPredefinido2.setBounds(20, 85, 280, 30);
        recuadroTexto.add(lblTextoPredefinido2);

        //---------------- BOTÓN SALIR ----------------
        JButton btnSalir = new JButton("Salir");
        btnSalir.setFont(fuente1.deriveFont(15f));
        btnSalir.setBounds(120, 320, 160, 45);
        btnSalir.addActionListener(e -> dispose());
        fondo.add(btnSalir);

        //---------------- MASCOTA ----------------
        JLabel mascotaLector = new JLabel();
        ImageIcon iconMascota = new ImageIcon(getClass().getResource("/utilidades/mascotaUno.png"));
        Image imgEscalada = iconMascota.getImage().getScaledInstance(300, 300, Image.SCALE_SMOOTH);
        mascotaLector.setIcon(new ImageIcon(imgEscalada));
        mascotaLector.setBounds(335, 140, 300, 300);
        fondo.add(mascotaLector);
    }

    public static void main(String[] args) {
        new PistasTexto();
    }
}
