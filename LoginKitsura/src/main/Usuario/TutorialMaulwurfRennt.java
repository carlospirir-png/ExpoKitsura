package main.Usuario;

import java.awt.*;
import javax.swing.*;
import main.Menu.FondoPanelSemi;

public class TutorialMaulwurfRennt extends PantallaTutorial {

    public TutorialMaulwurfRennt() {
        super();
    }

    @Override
    public void agregarContenido() {
        panelContenido.removeAll();

        // ---------------- Título de la página ------------------------
        lblTitulo = new JLabel("Reglas", SwingConstants.CENTER);
        lblTitulo.setFont(fuente2.deriveFont(Font.BOLD, 30f));
        lblTitulo.setBounds(0, 20, 540, 40);
        lblTitulo.setForeground(new Color(102, 106, 114));
        panelContenido.add(lblTitulo);

        //---------------- MASCOTA ----------------
        lblMascota = new JLabel();
        try {
            ImageIcon mascotaIcon = new ImageIcon(getClass().getResource("/Multimedia/utiles/mascotaKitsura/imagen/Zorro_kimono_azul.png"));
            Image mascotaEscalada = mascotaIcon.getImage().getScaledInstance(235, 235, Image.SCALE_SMOOTH);
            lblMascota.setIcon(new ImageIcon(mascotaEscalada));
        } catch (Exception e) {
            lblMascota.setText("~");
        }
        lblMascota.setBounds(0, 30, 235, 235);
        panelContenido.add(lblMascota);

        // ----------------Titulo de Introducción----------------------
        JLabel lblIntro = new JLabel("Introducción");
        lblIntro.setFont(fuente2.deriveFont(Font.BOLD, 24f));
        lblIntro.setBounds(220, 80, 220, 30);
        lblIntro.setForeground(new Color(118, 169, 170));
        panelContenido.add(lblIntro);

        // --------- Contenido de Introducción ---------------------
        FondoPanelSemi pnlintro = new FondoPanelSemi(new Color(168, 202, 207));
        pnlintro.setBounds(210, 110, 300, 200);

        JTextArea txtcontenido = new JTextArea("Maulwurf Rennt es un minijuego educativo que "
                + "reinventa el clásico \"atrapa al topo\", combinando tus reflejos con "
                + "preguntas sobre científicos destacados de las matemáticas y ecuaciones "
                + "básicas. Los topos emergen de sus madrigueras sosteniendo letreros con "
                + "las posibles respuestas: golpea al que sostenga la correcta.");

        pnlintro.add(txtcontenido);
        txtcontenido.setBounds(30, 30, 280, 170);
        txtcontenido.setOpaque(false);
        txtcontenido.setEditable(false);
        txtcontenido.setLineWrap(true);
        txtcontenido.setWrapStyleWord(true);
        txtcontenido.setFont(fuente1.deriveFont(20f));

        panelContenido.add(pnlintro);

        // ------------------- Contenido: Objetivos ---------------------------------
        JLabel lblObjetivos = new JLabel("Objetivos");
        lblObjetivos.setFont(fuente2.deriveFont(Font.BOLD, 24f));
        lblObjetivos.setBounds(40, 290, 200, 30);
        lblObjetivos.setForeground(new Color(118, 169, 170));
        panelContenido.add(lblObjetivos);

        FondoPanelSemi pnlobjetivos = new FondoPanelSemi(new Color(168,202,207));
        pnlobjetivos.setBounds(20, 325, 480, 240);

        panelContenido.add(pnlobjetivos);

        JTextArea txtObjetivos = new JTextArea("Lee la pregunta y haz clic en el topo que sostenga "
                + "el letrero con la respuesta correcta.\n"
                + "*-• Cada topo aparece brevemente desde su madriguera mostrando una alternativa distinta.\n"
                + "*-• Cada acierto suma puntos según qué tan rápido respondiste; cada error resta una vida.\n"
                + "*-• Comienzas con tres corazones. Al perderlos todos, la partida finalizará.\n"
                + "*-• Cada categoría tiene un tiempo límite por pregunta:\n"
                + "    °°° Fácil (25 s).\n"
                + "    °°° Intermedio (20 s).\n"
                + "    °°° Difícil (15 s).\n"
                + "*-• Si el temporizador llega a 0, perderás la partida inmediatamente.");

        txtObjetivos.setBounds(20, 20, 460, 250);
        txtObjetivos.setOpaque(false);
        txtObjetivos.setEditable(false);
        txtObjetivos.setLineWrap(true);
        txtObjetivos.setWrapStyleWord(true);
        txtObjetivos.setFont(fuente1.deriveFont(20f));
        pnlobjetivos.add(txtObjetivos);

        // ---------------------- Imagen 1 ---------------------------------
        try {
            ImageIcon referencia1icon = new ImageIcon(getClass().getResource("/Multimedia/Minijuegos/Minijuego_3/Elementos_graficos/Referencia1.png"));
            Image referencia1Escalada = referencia1icon.getImage().getScaledInstance(400, 200, Image.SCALE_SMOOTH);
            lblImagen1.setIcon(new ImageIcon(referencia1Escalada));
        } catch (Exception e) {
            lblImagen1.setText("Imagen no disponible");
            System.out.println(e);
        }
        lblImagen1.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        lblImagen1.setHorizontalAlignment(SwingConstants.CENTER);
        lblImagen1.setText("");
        lblImagen1.setBounds(70, 580, 400, 220);

        panelContenido.add(lblImagen1);

        // ------------------ Información adicional -------------------------------
        JLabel lblinfo = new JLabel("Información Adicional");
        lblinfo.setFont(fuente2.deriveFont(Font.BOLD, 24f));
        lblinfo.setBounds(40, 750, 290, 150);
        lblinfo.setForeground(new Color(118, 169, 170));
        panelContenido.add(lblinfo);

        FondoPanelSemi pnlinfo = new FondoPanelSemi(new Color(168, 202, 207));
        pnlinfo.setBounds(20, 840, 480, 165);

        JTextArea txtinfo = new JTextArea("*-• Puedes elegir la categoría de las preguntas (Científicos o Ecuaciones) antes de comenzar la partida.\n"
                + "*-• Cada categoría contiene tres niveles; en cada uno tendrás menos tiempo para responder.\n"
                + "*-• Cuanto menos tiempo tardes en responder, mayor será tu puntaje.\n"
                + "*-• Si completas todas las preguntas de una categoría sin perder ninguna vida, obtienes una Victoria Perfecta.");

        txtinfo.setBounds(40, 850, 450, 150);
        txtinfo.setOpaque(false);
        txtinfo.setEditable(false);
        txtinfo.setLineWrap(true);
        txtinfo.setWrapStyleWord(true);
        txtinfo.setFont(fuente1.deriveFont(18f));

        pnlinfo.add(txtinfo);

        panelContenido.add(pnlinfo);

        // ---------------------- Imagen 2 ---------------------------------
        try {
            ImageIcon referencia2icon = new ImageIcon(getClass().getResource("/Multimedia/Minijuegos/Minijuego_3/Elementos_graficos/Referencia2.png"));
            Image referencia2Escalada = referencia2icon.getImage().getScaledInstance(400, 200, Image.SCALE_SMOOTH);
            lblImagen2.setIcon(new ImageIcon(referencia2Escalada));
        } catch (Exception e) {
            lblImagen2.setText("Imagen no disponible");
            System.out.println(e);
        }
        lblImagen2.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        lblImagen2.setHorizontalAlignment(SwingConstants.CENTER);
        lblImagen2.setText("");
        lblImagen2.setBounds(70, 1020, 400, 220);

        panelContenido.add(lblImagen2);

        panelContenido.setPreferredSize(new Dimension(540, 1250));

        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    public static void main(String[] args) {
        new TutorialMaulwurfRennt();
    }
}