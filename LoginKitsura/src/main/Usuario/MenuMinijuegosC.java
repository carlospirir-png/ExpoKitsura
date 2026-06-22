
package main.Usuario;

import javax.swing.*;
import java.awt.*;
import main.Menu.FondoPanel;

public class MenuMinijuegosC extends JFrame{
    
    private FondoPanel fondo;
    private Font fuente1;
    private Font fuente2;

    public MenuMinijuegosC() {
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
        
        // Reutilizamos el mismo fondo o el que prefieras
        fondo = new FondoPanel("/Multimedia/utiles/fondoDosK.png");
        setContentPane(fondo);
        setTitle("Categorías de Minijuego");
        setSize(1880, 1080);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        fondo.setLayout(null);
        
        crearComponentes();
        setVisible(true);
    }

    private void crearComponentes() {
        //---------------- BOTÓN ¿CÓMO JUGAR? (Arriba a la izquierda) ----------------
        JButton btnComoJugar = new JButton("¿Cómo jugar?");
        btnComoJugar.setFont(fuente1.deriveFont(25f));
        btnComoJugar.setBounds(100, 100, 250, 55);
        btnComoJugar.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Instrucciones del minijuego...");
        });
        fondo.add(btnComoJugar);

        //---------------- MASCOTA (Ahora a la izquierda) ----------------
        JLabel mascota = new JLabel();
        ImageIcon mascotaIcon = new ImageIcon(getClass().getResource("/Multimedia/utiles/mascota3.png"));
        Image mascotaEscalada = mascotaIcon.getImage().getScaledInstance(600, 600, Image.SCALE_SMOOTH);
        mascota.setIcon(new ImageIcon(mascotaEscalada));
        // Posicionada a la izquierda y centrada verticalmente en base a las medidas
        mascota.setBounds(150, 280, 600, 600); 
        fondo.add(mascota);

        //---------------- T I T U L O (A la derecha) ----------------
        // Puedes cambiar "Nombre de Minijuego" dinámicamente pasándolo por constructor si lo deseas
        JLabel lblTitulo = new JLabel("Nombre de Minijuego", SwingConstants.CENTER);
        lblTitulo.setFont(fuente2.deriveFont(40f)); // Un poco más grande para el título del juego
        lblTitulo.setForeground(Color.WHITE); 
        lblTitulo.setBounds(1000, 120, 600, 60);
        fondo.add(lblTitulo);

        //---------------- B O T O N E S   C A T E G O R Í A S ----------------
        // >. C A T E G O R Í A   1
        JButton btnCategoria1 = new JButton("Categoría 1");
        btnCategoria1.setFont(fuente1.deriveFont(25f));
        btnCategoria1.setBounds(1100, 300, 400, 55);
        btnCategoria1.addActionListener(e -> {
            // Acción para la Categoría 1
        });
        fondo.add(btnCategoria1);

        // >. C A T E G O R Í A   2
        JButton btnCategoria2 = new JButton("Categoría 2");
        btnCategoria2.setFont(fuente1.deriveFont(25f));
        btnCategoria2.setBounds(1100, 430, 400, 55);
        btnCategoria2.addActionListener(e -> {
            // Acción para la Categoría 2
        });
        fondo.add(btnCategoria2);

        // >. C A T E G O R Í A   3
        JButton btnCategoria3 = new JButton("Categoría 3");
        btnCategoria3.setFont(fuente1.deriveFont(25f));
        btnCategoria3.setBounds(1100, 560, 400, 55);
        btnCategoria3.addActionListener(e -> {
            // Acción para la Categoría 3
        });
        fondo.add(btnCategoria3);

        //---------------- V O L V E R (Abajo a la derecha) ----------------
        JButton btnVolver = new JButton("Volver");
        btnVolver.setFont(fuente1.deriveFont(25f));
        btnVolver.setBounds(1175, 720, 250, 45);
        btnVolver.addActionListener(e -> {
            // Regresa al menú anterior de Minijuegos que ya tenías hecho
            new MenuMinijuegos();
            dispose();
        });
        fondo.add(btnVolver);
    }
    
    public static void main(String[] args) {
        new MenuMinijuegosC();
    }
}
