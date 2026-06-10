package main;

import java.awt.*;
import javax.swing.*;

public class M2_crearNuevo extends JFrame {

    private FondoPanelSemi fondo;
    private FondoPanelSemi panelSemi;

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

    private JLabel lblMascota;

    public M2_crearNuevo() {

        fondo = new FondoPanelSemi("/Multimedia/utiles/fondoDosK.png");
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
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 42));
        lblTitulo.setBounds(40, 40, 700, 50);

        panelSemi.add(lblTitulo);

        lblPregunta = new JLabel("Ingrese la pregunta:");
        lblPregunta.setFont(new Font("Arial", Font.PLAIN, 26));
        lblPregunta.setBounds(50, 120, 350, 35);

        panelSemi.add(lblPregunta);

        txtPregunta = new JTextArea();
        txtPregunta.setFont(new Font("Arial", Font.PLAIN, 24));
        txtPregunta.setLineWrap(true);
        txtPregunta.setWrapStyleWord(true);
        txtPregunta.setBounds(50, 170, 1050, 320);

        panelSemi.add(txtPregunta);

        lblCheck = new JLabel();

        ImageIcon checkIcon
                = new ImageIcon(
                        getClass().getResource("/Multimedia/utiles/check.png"));

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

        lblRespuestaCorrecta.setFont(
                new Font("Arial", Font.PLAIN, 22));

        lblRespuestaCorrecta.setBounds(70, 530, 500, 35);

        panelSemi.add(lblRespuestaCorrecta);

        txtCorrecta = new JTextField();
        txtCorrecta.setFont(new Font("Arial", Font.PLAIN, 22));
        txtCorrecta.setBounds(110, 580, 380, 50);

        panelSemi.add(txtCorrecta);

        lblIncorrecto = new JLabel();

        ImageIcon incorrectoIcon
                = new ImageIcon(
                        getClass().getResource("/Multimedia/utiles/incorrecto.png"));

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

        lblRespuestaIncorrecta.setFont(
                new Font("Arial", Font.PLAIN, 22));

        lblRespuestaIncorrecta.setBounds(620, 530, 500, 35);

        panelSemi.add(lblRespuestaIncorrecta);

        txtIncorrecta = new JTextField();
        txtIncorrecta.setFont(new Font("Arial", Font.PLAIN, 22));
        txtIncorrecta.setBounds(680, 580, 380, 50);

        panelSemi.add(txtIncorrecta);

        btnSiguiente = new JButton("SIGUIENTE");
        btnSiguiente.setFont(new Font("Arial", Font.BOLD, 24));
        btnSiguiente.setBounds(50, 700, 300, 60);

        panelSemi.add(btnSiguiente);

        lblMascota = new JLabel();

        ImageIcon mascotaIcon
                = new ImageIcon(
                        getClass().getResource("/Multimedia/utiles/ZorroLeer.png"));

        Image mascotaEscalada
                = mascotaIcon.getImage().getScaledInstance(
                        650,
                        650,
                        Image.SCALE_SMOOTH);

        lblMascota.setIcon(new ImageIcon(mascotaEscalada));
        lblMascota.setBounds(1280, 180, 650, 650);

        fondo.add(lblMascota);
    }

    public static void main(String[] args) {

        new M2_crearNuevo();
    }
}
