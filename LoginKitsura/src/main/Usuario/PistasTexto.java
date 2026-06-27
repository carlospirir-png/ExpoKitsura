package main.Usuario;

import java.awt.*;
import javax.swing.*;
import main.Menu.FondoPanel;

public class PistasTexto extends JFrame {

    private FondoPanel fondo;
    private Font fuente1;
    private Font fuente2;

    private JLabel lblPista;

    public PistasTexto(String pista) {
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
        fondo = new FondoPanel("/Multimedia/utiles/fondos/interfaces/fondoTresK.png");
        setContentPane(fondo);
        setUndecorated(true);
        setSize(600, 440);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        fondo.setLayout(null);
        crearComponentes();

        lblPista.setText(
                "<html><body style='width:260px'>" + pista + "</body></html>"
        );

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

        lblPista = new JLabel();
        lblPista.setFont(fuente2.deriveFont(20f));
        lblPista.setForeground(Color.BLACK);
        lblPista.setBounds(20, 30, 280, 120);

        recuadroTexto.add(lblPista);

        //---------------- BOTÓN SALIR ----------------
        JButton btnSalir = new JButton("Salir");
        btnSalir.setFont(fuente1.deriveFont(15f));
        btnSalir.setBounds(120, 320, 160, 45);
        btnSalir.addActionListener(e -> dispose());
        fondo.add(btnSalir);

        //---------------- MASCOTA ----------------
        JLabel mascotaLector = new JLabel();
        ImageIcon iconMascota = new ImageIcon(getClass().getResource("/Multimedia/utiles/mascotaKitsura/imagen/PISTA_AUDIO.png"));
        Image imgEscalada = iconMascota.getImage().getScaledInstance(300, 300, Image.SCALE_SMOOTH);
        mascotaLector.setIcon(new ImageIcon(imgEscalada));
        mascotaLector.setBounds(335, 140, 300, 300);
        fondo.add(mascotaLector);
    }
}
