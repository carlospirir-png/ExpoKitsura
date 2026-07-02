package main.Administrador;

import java.awt.*;
import javax.swing.*;
import main.Menu.FondoPanelSemi;
import main.Menu.DecoracionBotones; 

public class M2_crearNuevo extends JFrame {
    private FondoPanelSemi fondo;
    private FondoPanelSemi panelSemi;
    private Font fuente1;
    private Font fuente2;
    private JLabel lblTitulo;

    private JLabel lblPregunta;
    private JTextArea txtPregunta;

    private JLabel lblRespuestaCorrecta;
    private JLabel lblRespuestaIncorrecta;

    private JTextField txtCorrecta;
    private JTextField txtIncorrecta;

    private JLabel lblCheck;
    private JLabel lblIncorrecto;

    private JButton btnSiguiente;
    private DecoracionBotones btnSalir;
    
    private JLabel lblMascota;

    public M2_crearNuevo() {
        try{
            // LettersForLearners
            fuente1 = Font.createFont(
            Font.TRUETYPE_FONT,
            getClass().getResourceAsStream("/fuentes/LettersForLearners.ttf"));
            // KGPerfectPenmanship
            fuente2 = Font.createFont(
            Font.TRUETYPE_FONT,
            getClass().getResourceAsStream("/fuentes/KGPerfectPenmanship.ttf"));
            
        } catch (Exception e){
            e.printStackTrace();            
            fuente1 = new Font("Arial", Font.PLAIN,20);
            fuente2 = new Font("Arial", Font.PLAIN,20);
        }
        fondo = new FondoPanelSemi("/Multimedia/utiles/fondos/interfaces/fondoDosK.png");
        setContentPane(fondo);

        setTitle("Crear Nuevo - Minijuego 2");
        setSize(1920, 1080);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        fondo.setLayout(null);

        crearComponentes();

        setVisible(true);
    }

    private void crearComponentes() {

        panelSemi = new FondoPanelSemi(new Color(220, 220, 220, 220));
        panelSemi.setLayout(null);
        panelSemi.setBounds(50, 50, 1200, 850);

        fondo.add(panelSemi);

        lblTitulo = new JLabel("CREAR NUEVO (MINIJUEGO 2)");
        lblTitulo.setFont(fuente2.deriveFont(40f));
        lblTitulo.setForeground(Color.decode("#447A9C"));
        lblTitulo.setBounds(40, 40, 700, 50);

        panelSemi.add(lblTitulo);

        lblPregunta = new JLabel("Ingrese la pregunta:");
        lblPregunta.setFont(fuente2.deriveFont(28f));
        lblPregunta.setBounds(50, 120, 350, 35);

        panelSemi.add(lblPregunta);

        txtPregunta = new JTextArea();
        txtPregunta.setFont(fuente1.deriveFont(30f));
        txtPregunta.setLineWrap(true);
        txtPregunta.setWrapStyleWord(true);
        txtPregunta.setBounds(50, 170, 1050, 320);

        panelSemi.add(txtPregunta);

        lblCheck = new JLabel();

        ImageIcon checkIcon
                = new ImageIcon(
                        getClass().getResource("/Multimedia/utiles/ElementosGraficos/imagenes/check.png"));

        Image checkEscalado
                = checkIcon.getImage().getScaledInstance(
                        50,
                        50,
                        Image.SCALE_SMOOTH);

        lblCheck.setIcon(new ImageIcon(checkEscalado));
        lblCheck.setBounds(50, 580, 50, 50);
        panelSemi.add(lblCheck);

        lblRespuestaCorrecta = new JLabel(
                "Ingrese la respuesta correcta del nivel:");
        lblRespuestaCorrecta.setFont(fuente2.deriveFont(22f));
        lblRespuestaCorrecta.setBounds(70, 530, 500, 35);
        panelSemi.add(lblRespuestaCorrecta);

        txtCorrecta = new JTextField();
        txtCorrecta.setFont(fuente1.deriveFont(26f));
        txtCorrecta.setBounds(110, 580, 380, 50);

        panelSemi.add(txtCorrecta);

        lblIncorrecto = new JLabel();

        ImageIcon incorrectoIcon
                = new ImageIcon(
                        getClass().getResource("/Multimedia/utiles/ElementosGraficos/imagenes/incorrecto.png"));
        Image incorrectoEscalado
                = incorrectoIcon.getImage().getScaledInstance(
                        50,
                        50,
                        Image.SCALE_SMOOTH);
        lblIncorrecto.setIcon(new ImageIcon(incorrectoEscalado));
        lblIncorrecto.setBounds(620, 580, 50, 50);

        panelSemi.add(lblIncorrecto);

        lblRespuestaIncorrecta = new JLabel(
                "Ingrese la respuesta incorrecta del nivel:");
        lblRespuestaIncorrecta.setFont(fuente2.deriveFont(22f));
        lblRespuestaIncorrecta.setBounds(620, 530, 500, 35);
        panelSemi.add(lblRespuestaIncorrecta);

        txtIncorrecta = new JTextField();
        txtIncorrecta.setFont(fuente1.deriveFont(26f));
        txtIncorrecta.setBounds(680, 580, 380, 50);

        panelSemi.add(txtIncorrecta);

        btnSiguiente = new DecoracionBotones("SIGUIENTE",
                //ColorBase             ColorBorde              ColorLetra
                DecoracionBotones.ROSA, DecoracionBotones.ROJO, DecoracionBotones.AMARILLO, //MOUSE FUERA
                DecoracionBotones.ROJO, DecoracionBotones.ROSA, DecoracionBotones.ROSA); //MOUSE DENTRO
        btnSiguiente.setFont(fuente2.deriveFont(18f));
        btnSiguiente.setBounds(450, 700, 300, 60);
        fondo.add(btnSiguiente);

        panelSemi.add(btnSiguiente);

        lblMascota = new JLabel();

        ImageIcon mascotaIcon
                = new ImageIcon(
                        getClass().getResource("/Multimedia/utiles/mascotaKitsura/imagen/REGLAS-PISTA_TEXTUAL.png"));

        Image mascotaEscalada
                = mascotaIcon.getImage().getScaledInstance(
                        650,
                        650,
                        Image.SCALE_SMOOTH);

        lblMascota.setIcon(new ImageIcon(mascotaEscalada));
        lblMascota.setBounds(1280, 180, 650, 650);

        fondo.add(lblMascota);
        
        // --- BOTÓN VOLVER---
        btnSalir = new DecoracionBotones("VOLVER",
                                //ColorBase             ColorBorde              ColorLetra
                DecoracionBotones.AZUL, DecoracionBotones.GRIS, DecoracionBotones.AMARILLO, //MOUSE FUERA
                DecoracionBotones.CELESTE, DecoracionBotones.AZUL, DecoracionBotones.AZUL); //MOUSE DENTRO   

        btnSalir.setFont(fuente2.deriveFont(18f));
        btnSalir.setBounds(1695, 950, 210, 45);
        btnSalir.addActionListener(e -> dispose());
        fondo.add(btnSalir);
    }
  public static void main(String[] args) {
        new M2_crearNuevo();
    }
}
