package main.Usuario;

import java.awt.*;
import javax.swing.*;
import main.Menu.FondoPanel;
import main.Menu.PantallaInicio;
import main.Menu.VolverMenu;

public class MenuMinijuegos extends JFrame {

    private FondoPanel fondo;
    private Font fuente1;
    private Font fuente2;

    public MenuMinijuegos() {
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
        fondo = new FondoPanel("/Multimedia/utiles/fondos/interfaces/fondoDosK.png");
        setContentPane(fondo);
        setTitle("Minijuegos");
        setSize(1880, 1080);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        fondo.setLayout(null);
        crearComponentes();
        setVisible(true);
    }

    private void crearComponentes() {
        //---------------- T I T U L O ----------------
        JLabel lblTitulo = new JLabel("MINIJUEGOS");
        lblTitulo.setFont(fuente2.deriveFont(35f));
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setBounds(880, 120, 500, 50);
        fondo.add(lblTitulo);

        //---------------- B O T O N E S ----------------
        // >. H I D D E N   F O X
        JButton btnHiddenFox = new JButton("HIDDEN FOX");
        btnHiddenFox.setFont(fuente1.deriveFont(25f));
        btnHiddenFox.setBounds(780, 340, 400, 55);
        btnHiddenFox.addActionListener(e -> {
            new MenuHiddenFox();
            dispose();
        });
        fondo.add(btnHiddenFox);
        // >. F O X   J U M P !
        JButton btnFoxJump = new JButton("FOX JUMP !");
        btnFoxJump.setFont(fuente1.deriveFont(25f));
        btnFoxJump.setBounds(780, 460, 400, 55);
        btnFoxJump.addActionListener(e -> {
            new FoxJump();
            dispose();
        });
        fondo.add(btnFoxJump);
        // >. M A U L W U R F   R E N N T
        JButton btnMaulwurf = new JButton("MAULWURF RENNT");
        btnMaulwurf.setFont(fuente1.deriveFont(25f));
        btnMaulwurf.setBounds(780, 590, 400, 55);
        btnMaulwurf.addActionListener(e -> {
            new MaulwurfRennt();
            dispose();
        });
        fondo.add(btnMaulwurf);
        // V O L V E R
        JButton btnVolver = new JButton("Volver");
        btnVolver.setFont(fuente1.deriveFont(25f));
        btnVolver.setBounds(850, 725, 250, 45);
        btnVolver.addActionListener(e -> {
            new MenuPrincipal();
            dispose();
        });
        fondo.add(btnVolver);

        //---------------- M A S C O T A S ----------------
        JLabel mascota = new JLabel();
        ImageIcon mascotaIcon = new ImageIcon(getClass().getResource("/Multimedia/utiles/mascotaKitsura/imagen/MINIJUEGO-CONTROL.png"));
        Image mascotaEscalada = mascotaIcon.getImage().getScaledInstance(600, 600, Image.SCALE_SMOOTH);
        mascota.setIcon(new ImageIcon(mascotaEscalada));
        mascota.setBounds(1245, 300, 600, 600);
        fondo.add(mascota);
    }

    public static void main(String[] args) {
        new MenuMinijuegos();
    }
}
