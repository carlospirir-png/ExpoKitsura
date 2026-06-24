package main.Menu;

import java.awt.*;
import javax.swing.*;
import main.Menu.FondoPanel;
import main.Usuario.MenuPrincipal;

public class VolverMenu extends JFrame {

    private FondoPanel fondo;
    private Font fuente1;
    private Font fuente2;

    public VolverMenu() {
        try {
            // LettersForLearners
            fuente1 = Font.createFont(
                    Font.TRUETYPE_FONT,
                    getClass().getResourceAsStream("/fuentes/LettersForLearners.ttf"));
            // KGPerfectPenmanship
            fuente2 = Font.createFont(Font.TRUETYPE_FONT,
                    getClass().getResourceAsStream("/fuentes/KGPerfectPenmanship.ttf"));
        } catch (Exception e) {
            e.printStackTrace();
            fuente1 = new Font("Arial", Font.PLAIN, 20);
            fuente2 = new Font("Arial", Font.PLAIN, 20);
        }

        fondo = new FondoPanel("/Multimedia/utiles/fondos/interfaces/fondoTresK.png");
        setContentPane(fondo);
        setUndecorated(true);
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        fondo.setLayout(null);
        crearComponentes();
        setVisible(true);
    }

    private void crearComponentes() {
        JPanel barra = new JPanel();
        barra.setLayout(null);
        barra.setBackground(new Color(255, 255, 255));
        barra.setBounds(0, 0, 600, 40);
        fondo.add(barra);

        JLabel titulo = new JLabel(" ");
        titulo.setFont(fuente1.deriveFont(10f));
        titulo.setForeground(new Color(60, 60, 60));
        titulo.setBounds(15, 0, 220, 40);
        barra.add(titulo);

        JButton btnCerrar = new JButton("X");
        btnCerrar.setFont(fuente1.deriveFont(15f));
        btnCerrar.setBounds(550, 5, 40, 30);
        btnCerrar.setFocusable(false);
        btnCerrar.setBorderPainted(false);
        btnCerrar.setBackground(new Color(245, 245, 245));
        btnCerrar.setForeground(new Color(180, 50, 50));
        btnCerrar.addActionListener(e -> dispose());
        barra.add(btnCerrar);

        JSeparator linea = new JSeparator();
        linea.setBounds(0, 39, 600, 1);
        barra.add(linea);
        
        FondoPanelSemi panelPregunta = new FondoPanelSemi(new Color(0, 0, 0, 120)); // 120 de transparencia
        panelPregunta.setBounds(50, 65, 500, 100); // Posicionado al centro
        panelPregunta.setLayout(null);
        fondo.add(panelPregunta);

        JLabel lblPregunta1 = new JLabel("¿Deseas regresar a la", JLabel.CENTER);
        lblPregunta1.setFont(fuente2.deriveFont(25f));
        lblPregunta1.setForeground(Color.WHITE);
        lblPregunta1.setBounds(0, 15, 500, 35);
        panelPregunta.add(lblPregunta1);

        JLabel lblPregunta2 = new JLabel("Pantalla Principal?", JLabel.CENTER);
        lblPregunta2.setFont(fuente2.deriveFont(25f));
        lblPregunta2.setForeground(Color.WHITE);
        lblPregunta2.setBounds(0, 50, 500, 35);
        panelPregunta.add(lblPregunta2);

        //---------------- MASCOTA ----------------
        JLabel mascota = new JLabel();
        ImageIcon mascotaIcon = new ImageIcon(getClass().getResource("/Multimedia/utiles/mascotaKitsura/imagen/VENTANITA.png"));
        Image mascotaEscalada = mascotaIcon.getImage().getScaledInstance(145, 145, Image.SCALE_SMOOTH);
        mascota.setIcon(new ImageIcon(mascotaEscalada));
        mascota.setBounds(440, 255, 145, 145);
        fondo.add(mascota);

        //---------------- BOTON SI ----------------
        JButton btnSi = new JButton("SI");
        btnSi.setFont(fuente1.deriveFont(20f));
        btnSi.setForeground(Color.BLACK);
        btnSi.setBounds(100, 220, 160, 45);
        btnSi.addActionListener(e -> {
            new PantallaInicio();
            dispose();
        });
        fondo.add(btnSi);

        //---------------- BOTON MENÚ ----------------
        JButton btnMenu = new JButton("MENÚ");
        btnMenu.setFont(fuente1.deriveFont(20f));
        btnMenu.setForeground(Color.BLACK);
        btnMenu.setBounds(340, 220, 160, 45);
        btnMenu.addActionListener(e -> {
             new MenuPrincipal();
            dispose();});
        fondo.add(btnMenu);
    }
    public static void main(String[] args) {
        new VolverMenu();
    }
}
