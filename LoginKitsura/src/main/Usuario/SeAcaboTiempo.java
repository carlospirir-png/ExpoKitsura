package main.Usuario;

import java.awt.*;
import javax.swing.*;
import main.Menu.FondoPanel;

public class SeAcaboTiempo extends JFrame {

    private FondoPanel fondo;
    private Font fuente1;
    private Font fuente2;

    private JuegoBase juego;

    public SeAcaboTiempo(JuegoBase juego) {

        this.juego = juego;

        try {
            fuente1 = Font.createFont(Font.TRUETYPE_FONT,
                    getClass().getResourceAsStream("/fuentes/LettersForLearners.ttf"));

            fuente2 = Font.createFont(Font.TRUETYPE_FONT,
                    getClass().getResourceAsStream("/fuentes/KGPerfectPenmanship.ttf"));

        } catch (Exception e) {
            fuente1 = new Font("Arial", Font.PLAIN, 20);
            fuente2 = new Font("Arial", Font.PLAIN, 20);
        }

        fondo = new FondoPanel("/Multimedia/utiles/fondos/interfaces/fondoCuatroK.png");
        setContentPane(fondo);

        setTitle("Tiempo agotado");
        setSize(1980, 1060);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        fondo.setLayout(null);

        crearComponentes();
        setVisible(true);
    }

    private void crearComponentes() {

        //---------------- TITULO ----------------
        JLabel lblTitulo = new JLabel("TIEMPO AGOTADO", JLabel.CENTER);
        lblTitulo.setFont(fuente2.deriveFont(34f));
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setBounds(900, 100, 800, 60);
        fondo.add(lblTitulo);

        //---------------- SUBTITULO ----------------
        JLabel lblSub = new JLabel("No lograste responder a tiempo", JLabel.CENTER);
        lblSub.setFont(fuente2.deriveFont(28f));
        lblSub.setForeground(Color.WHITE);
        lblSub.setBounds(900, 170, 800, 45);
        fondo.add(lblSub);

        //---------------- FRASE ----------------
        JLabel lblFrase = new JLabel(
                "<<La rapidez también es parte del aprendizaje>>",
                JLabel.CENTER);
        lblFrase.setFont(fuente1.deriveFont(24f));
        lblFrase.setForeground(Color.WHITE);
        lblFrase.setBounds(900, 240, 800, 30);
        fondo.add(lblFrase);

        //---------------- MASCOTA ----------------
        JLabel mascota = new JLabel();
        try {
            ImageIcon icon = new ImageIcon(
                    getClass().getResource("/Multimedia/utiles/mascotaKitsura/imagen/TIEMPO_AGOTADO.png"));

            Image img = icon.getImage().getScaledInstance(600, 600, Image.SCALE_SMOOTH);
            mascota.setIcon(new ImageIcon(img));

        } catch (Exception e) {
            mascota.setText("Mascota");
        }

        mascota.setBounds(150, 250, 600, 600);
        fondo.add(mascota);

        //---------------- BOTON CONTINUAR ----------------
        JButton btnContinuar = new JButton("Continuar");
        btnContinuar.setFont(fuente1.deriveFont(25f));
        btnContinuar.setBounds(1225, 770, 200, 50);

        btnContinuar.addActionListener(e -> {

            dispose();

            ResultadoFinal resultado = new ResultadoFinal(
                    juego,
                    juego.getPuntajeTotal(),
                    juego.getTiempoTotalJugado()
            );

            resultado.mostrar();
        });

        fondo.add(btnContinuar);

        //---------------- BOTON MENU ----------------
        JButton btnMenu = new JButton("Menú");
        btnMenu.setFont(fuente1.deriveFont(25f));
        btnMenu.setBounds(1225, 830, 200, 50);

        btnMenu.addActionListener(e -> {
            dispose();
            juego.irAlMenu();
        });

        fondo.add(btnMenu);
    }
}
