package main.Menu;

import java.awt.*;
import java.net.URL;
import javax.swing.*;

public class SalirDelJuego extends JFrame {

    private Font fuente1;
    private Font fuente2;

    public SalirDelJuego() {
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

        getContentPane().setBackground(new Color(145, 191, 75));
        //------------- ÍCONO ------------------
        //se obtiene la imagen del logo con getResource
        URL iconUrl = getClass().getResource("/Multimedia/utiles/logotipo/logofK.png");

        //se instancia el ícono con la imagen
        ImageIcon icono = new ImageIcon(iconUrl);

        //Se coloca el ícono al JFrame
        setIconImage(icono.getImage());

        setTitle("Salir del juego");
        setSize(520, 320);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        getContentPane().setLayout(null);
        crearComponentes();
        setVisible(true);
    }

    private void crearComponentes() {

        //---------------- PANEL PREGUNTA ----------------
        FondoPanelSemi panelPregunta = new FondoPanelSemi(new Color(0, 0, 0, 100));
        panelPregunta.setBounds(30, 40, 440, 110);
        panelPregunta.setLayout(null);
        getContentPane().add(panelPregunta);

        JLabel lblPregunta1 = new JLabel("¿Deseas salir", JLabel.CENTER);
        lblPregunta1.setFont(fuente2.deriveFont(28f));
        lblPregunta1.setForeground(Color.WHITE);
        lblPregunta1.setBounds(0, 15, 460, 38);
        panelPregunta.add(lblPregunta1);

        JLabel lblPregunta2 = new JLabel("del juego?", JLabel.CENTER);
        lblPregunta2.setFont(fuente2.deriveFont(28f));
        lblPregunta2.setForeground(Color.WHITE);
        lblPregunta2.setBounds(0, 58, 460, 38);
        panelPregunta.add(lblPregunta2);

        //---------------- MASCOTA ----------------
        JLabel mascota = new JLabel();
        try {
            ImageIcon mascotaIcon = new ImageIcon(getClass().getResource("/Multimedia/utiles/mascotaKitsura/imagen/VENTANITA.png"));
            Image mascotaEscalada = mascotaIcon.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
            mascota.setIcon(new ImageIcon(mascotaEscalada));
        } catch (Exception e) {
            mascota.setText("~");
        }
        mascota.setBounds(350, 150, 200, 200);
        getContentPane().add(mascota);

        //---------------- BOTÓN SI ----------------
        JButton btnSi = new DecoracionBotones("SI",
                                //ColorBase             ColorBorde              ColorLetra
                DecoracionBotones.AZUL, DecoracionBotones.GRIS, DecoracionBotones.AMARILLO, //MOUSE FUERA
                DecoracionBotones.CELESTE, DecoracionBotones.AZUL, DecoracionBotones.AZUL); //MOUSE DENTRO   

        btnSi.setFont(fuente1.deriveFont(22f));
        btnSi.setBounds(60, 200, 140, 50);
        btnSi.addActionListener(e -> {
            new PantallaInicio();
            dispose();
        });
        getContentPane().add(btnSi);

        //---------------- BOTÓN REGRESAR ----------------
        JButton btnRegresar = new DecoracionBotones("REGRESAR",
                                //ColorBase             ColorBorde              ColorLetra
                DecoracionBotones.AZUL, DecoracionBotones.GRIS, DecoracionBotones.AMARILLO, //MOUSE FUERA
                DecoracionBotones.CELESTE, DecoracionBotones.AZUL, DecoracionBotones.AZUL); //MOUSE DENTRO   

        btnRegresar.setFont(fuente1.deriveFont(22f));
        btnRegresar.setBounds(250, 200, 160, 50);
        btnRegresar.addActionListener(e -> dispose());
        getContentPane().add(btnRegresar);
    }

}
