package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class M3_crearNuevo extends JFrame {

    private FondoPanelSemi fondo;
    private FondoPanelSemi panelSemi;

    private JLabel lblTitulo;

    private JLabel lblPregunta;
    private JTextArea txtPregunta;

    private JLabel lblRespuestaCorrecta;
    private JLabel lblRespuestaIncorrecta;

    private JTextField txtCorrecta;
    private JTextField txtIncorrecta;
    private JTextField txtIncorrecta2;
    private JTextField txtIncorrecta3;
    private JTextField txtIncorrecta4;
    private JTextField txtIncorrecta5;

    private JLabel lblCheck;
    private JLabel lblIncorrecto;
    private JLabel lblIncorrecto2;
    private JLabel lblIncorrecto3;
    private JLabel lblIncorrecto4;
    private JLabel lblIncorrecto5;

    private JButton btnSiguiente;

    private JLabel lblMascota;

    public M3_crearNuevo() {

        fondo = new FondoPanelSemi("/Multimedia/utiles/FondoTopos.png");
        setContentPane(fondo);

        setTitle("Crear Nuevo - Minijuego 3");
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
        panelSemi.setBounds(60, 60, 1250, 850);

        fondo.add(panelSemi);

        lblTitulo = new JLabel("CREAR NUEVO (MINIJUEGO 3)");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 46));
        lblTitulo.setBounds(40, 30, 900, 60);

        panelSemi.add(lblTitulo);

        lblPregunta = new JLabel("Ingrese la pregunta:");
        lblPregunta.setFont(new Font("Arial", Font.PLAIN, 28));
        lblPregunta.setBounds(50, 110, 400, 40);

        panelSemi.add(lblPregunta);

        txtPregunta = new JTextArea();
        txtPregunta.setFont(new Font("Arial", Font.PLAIN, 24));
        txtPregunta.setLineWrap(true);
        txtPregunta.setWrapStyleWord(true);
        txtPregunta.setBounds(50, 170, 1150, 280);

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
        lblCheck.setBounds(25, 550, 50, 50);

        panelSemi.add(lblCheck);

        lblRespuestaCorrecta = new JLabel(
                "Ingrese la respuesta correcta del nivel:");

        lblRespuestaCorrecta.setFont(
                new Font("Arial", Font.PLAIN, 24));

        lblRespuestaCorrecta.setBounds(70, 490, 500, 35);

        panelSemi.add(lblRespuestaCorrecta);

        txtCorrecta = new JTextField();
        txtCorrecta.setFont(new Font("Arial", Font.PLAIN, 22));
        txtCorrecta.setBounds(90, 550, 400, 55);

        panelSemi.add(txtCorrecta);

        lblRespuestaIncorrecta = new JLabel(
                "Ingrese las respuestas incorrectas del nivel:");

        lblRespuestaIncorrecta.setFont(
                new Font("Arial", Font.PLAIN, 24));

        lblRespuestaIncorrecta.setBounds(620, 490, 550, 35);

        panelSemi.add(lblRespuestaIncorrecta);

        ImageIcon incorrectoIcon
                = new ImageIcon(
                        getClass().getResource("/Multimedia/utiles/incorrecto.png"));

        Image incorrectoEscalado
                = incorrectoIcon.getImage().getScaledInstance(
                        50,
                        50,
                        Image.SCALE_SMOOTH);

        lblIncorrecto = new JLabel(new ImageIcon(incorrectoEscalado));
        lblIncorrecto.setBounds(630, 550, 50, 50);

        panelSemi.add(lblIncorrecto);

        txtIncorrecta = new JTextField();
        txtIncorrecta.setFont(new Font("Arial", Font.PLAIN, 22));
        txtIncorrecta.setBounds(700, 550, 420, 55);

        panelSemi.add(txtIncorrecta);

        lblIncorrecto2 = new JLabel(new ImageIcon(incorrectoEscalado));
        lblIncorrecto2.setBounds(630, 620, 50, 50);

        panelSemi.add(lblIncorrecto2);

        txtIncorrecta2 = new JTextField();
        txtIncorrecta2.setFont(new Font("Arial", Font.PLAIN, 22));
        txtIncorrecta2.setBounds(700, 620, 420, 55);

        panelSemi.add(txtIncorrecta2);

        lblIncorrecto3 = new JLabel(new ImageIcon(incorrectoEscalado));
        lblIncorrecto3.setBounds(630, 690, 50, 50);

        panelSemi.add(lblIncorrecto3);

        txtIncorrecta3 = new JTextField();
        txtIncorrecta3.setFont(new Font("Arial", Font.PLAIN, 22));
        txtIncorrecta3.setBounds(700, 690, 420, 55);

        panelSemi.add(txtIncorrecta3);

        lblIncorrecto4 = new JLabel(new ImageIcon(incorrectoEscalado));
        lblIncorrecto4.setBounds(25, 620, 50, 50);

        panelSemi.add(lblIncorrecto4);

        txtIncorrecta4 = new JTextField();
        txtIncorrecta4.setFont(new Font("Arial", Font.PLAIN, 22));
        txtIncorrecta4.setBounds(90, 620, 400, 55);

        panelSemi.add(txtIncorrecta4);

        lblIncorrecto5 = new JLabel(new ImageIcon(incorrectoEscalado));
        lblIncorrecto5.setBounds(25, 690, 50, 50);

        panelSemi.add(lblIncorrecto5);

        txtIncorrecta5 = new JTextField();
        txtIncorrecta5.setFont(new Font("Arial", Font.PLAIN, 22));
        txtIncorrecta5.setBounds(90, 690, 400, 55);

        panelSemi.add(txtIncorrecta5);

        btnSiguiente = new JButton("SIGUIENTE");
        btnSiguiente.setFont(new Font("Arial", Font.BOLD, 24));
        btnSiguiente.setBounds(480, 780, 280, 55);

        panelSemi.add(btnSiguiente);

        lblMascota = new JLabel();

        ImageIcon mascotaIcon
                = new ImageIcon(
                        getClass().getResource("/Multimedia/utiles/TopoPala.png"));

        Image mascotaEscalada
                = mascotaIcon.getImage().getScaledInstance(
                        700,
                        500,
                        Image.SCALE_SMOOTH);

        lblMascota.setIcon(new ImageIcon(mascotaEscalada));
        lblMascota.setBounds(1250, 180, 700, 500);

        fondo.add(lblMascota);
    }

    public static void main(String[] args) {

        new M3_crearNuevo();
    }
}
