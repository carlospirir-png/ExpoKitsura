package main.Menu;

import java.awt.*;
import java.net.URL;
import javax.swing.*;
import main.Usuario.MenuPrincipal;

public class VolverMenu extends JFrame {

    private JPanel fondo;
    private Font fuente1;
    private Font fuente2;

    public VolverMenu() {
        try {
            fuente1 = Font.createFont(
                    Font.TRUETYPE_FONT,
                    getClass().getResourceAsStream("/fuentes/LettersForLearners.ttf"));
            fuente2 = Font.createFont(Font.TRUETYPE_FONT,
                    getClass().getResourceAsStream("/fuentes/KGPerfectPenmanship.ttf"));
        } catch (Exception e) {
            e.printStackTrace();
            fuente1 = new Font("Arial", Font.PLAIN, 20);
            fuente2 = new Font("Arial", Font.PLAIN, 20);
        }
        fondo = new JPanel();
        fondo.setBackground(new Color(145, 191, 75));
        fondo.setLayout(null);
        //------------- ÍCONO ------------------
        //se obtiene la imagen del logo con getResource
        URL iconUrl = getClass().getResource("/Multimedia/utiles/logotipo/logofK.png");

        //se instancia el ícono con la imagen
        ImageIcon icono = new ImageIcon(iconUrl);

        //Se coloca el ícono al JFrame
        setIconImage(icono.getImage());

        setContentPane(fondo);
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        crearComponentes();
        setVisible(true);
    }

    private void crearComponentes() {
        FondoPanelSemi panelPregunta = new FondoPanelSemi(new Color(0, 0, 0, 120));
        panelPregunta.setBounds(50, 65, 500, 100);
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
        JLabel mascota = new JLabel();
        ImageIcon mascotaIcon = new ImageIcon(getClass().getResource("/Multimedia/utiles/mascotaKitsura/imagen/VENTANITA.png"));
        Image mascotaEscalada = mascotaIcon.getImage().getScaledInstance(240, 240, Image.SCALE_SMOOTH);
        mascota.setIcon(new ImageIcon(mascotaEscalada));
        mascota.setBounds(400, 210, 240, 240);
        fondo.add(mascota);
        JButton btnSI = new DecoracionBotones("SI",
        //ColorBase             ColorBorde              ColorLetra
                DecoracionBotones.AZUL, DecoracionBotones.GRIS, DecoracionBotones.AMARILLO, //MOUSE FUERA
                DecoracionBotones.CELESTE, DecoracionBotones.AZUL, DecoracionBotones.AZUL); //MOUSE DENTRO   

        btnSI.setFont(fuente1.deriveFont(22f));
        btnSI.setBounds(100, 220, 160, 50);
        btnSI.addActionListener(e -> {
            new PantallaInicio();
            dispose();
        });
        fondo.add(btnSI);
        JButton btnCancelar = new DecoracionBotones("CANCELAR",
                //ColorBase             ColorBorde              ColorLetra
                DecoracionBotones.ROSA, DecoracionBotones.ROJO, DecoracionBotones.AMARILLO, //MOUSE FUERA
                DecoracionBotones.ROJO, DecoracionBotones.ROSA, DecoracionBotones.ROSA); //MOUSE DENTRO 
        btnCancelar.setFont(fuente1.deriveFont(22f));
        btnCancelar.setBounds(290, 220, 160, 50);
        btnCancelar.addActionListener(e -> {
            new MenuPrincipal();
            dispose();
        });
        fondo.add(btnCancelar);
    }

}
