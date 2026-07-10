package main.Usuario;

import java.awt.*;
import javax.swing.*;
import main.Menu.FondoPanelSemi;

public class TutorialHiddenFox extends PantallaTutorial {

    // Se crea el constructor donde obtendrá todos los atributos y/o elementos necesarios
    // Para el funcionamiento de la clase hija
    public TutorialHiddenFox() {
        //Super(): Hace referencia a que todos los elementos que heredó de la clase padre "Pantalla Tutorial"
        super();
        
    }

    @Override
    public void agregarContenido() {
        // Remueve los elementos anteriores
        panelContenido.removeAll();
        
        // ---------------- Título de la página ------------------------
        lblTitulo = new JLabel("Reglas", SwingConstants.CENTER);
        lblTitulo.setFont(fuente2.deriveFont(Font.BOLD, 30f));
        lblTitulo.setBounds(0, 20, 540, 40);
        lblTitulo.setForeground(new Color(218, 77, 88));
        panelContenido.add(lblTitulo);

        //---------------- MASCOTA ----------------
        lblMascota = new JLabel();
        try {
            ImageIcon mascotaIcon = new ImageIcon(getClass().getResource("/Multimedia/utiles/mascotaKitsura/imagen/Zorro_kimono_rosa.png"));
            Image mascotaEscalada = mascotaIcon.getImage().getScaledInstance(250, 250, Image.SCALE_SMOOTH);
            lblMascota.setIcon(new ImageIcon(mascotaEscalada));
        } catch (Exception e) {
            lblMascota.setText("~");
        }
        lblMascota.setBounds(0, 30, 300, 300);
        panelContenido.add(lblMascota);

        // ----------------Titulo de Introducción----------------------
        JLabel lblIntro = new JLabel("Introducción");
        lblIntro.setFont(fuente2.deriveFont(Font.BOLD, 24f));
        lblIntro.setBounds(220, 80, 220, 30);
        lblIntro.setForeground(new Color(238, 151, 151));
        panelContenido.add(lblIntro);

        // --------- Contenido de Introducción ---------------------
        FondoPanelSemi pnlintro = new FondoPanelSemi(new Color(238, 151, 151, 150));
        pnlintro.setBounds(210, 110, 300, 200);
        
        
        JTextArea txtcontenido = new JTextArea("Hidden Fox (\"Zorro Escondido\") es un minijuego educativo de "
                + "\"Kitsura\" que pone a prueba la lógica, la observación y el "
                + "conocimiento del jugador. El objetivo es identificar correctamente "
                + "la figura representada por una silueta antes de que el tiempo se "
                + "agote. Conforme se avanza, la dificultad aumenta y los desafíos "
                + "se vuelven más complejos.");
        
        pnlintro.add(txtcontenido);
        txtcontenido.setBounds(30, 30, 280, 170);
        txtcontenido.setOpaque(false);
        txtcontenido.setEditable(false);
        txtcontenido.setLineWrap(true);
        txtcontenido.setWrapStyleWord(true);
        txtcontenido.setFont(fuente1.deriveFont(20f));
        
        panelContenido.add(pnlintro);
        // ------------------- Contenido: Paso 1 ---------------------------------
        JLabel lblObjetivos = new JLabel("Objetivos");
        lblObjetivos.setFont(fuente2.deriveFont(Font.BOLD, 24f));
        lblObjetivos.setBounds(40, 300, 200, 30);
        lblObjetivos.setForeground(new Color(238, 151, 151));
        panelContenido.add(lblObjetivos);
        
        FondoPanelSemi pnlobjetivos = new FondoPanelSemi(new Color(240, 208, 96, 150));
        pnlobjetivos.setBounds(20, 325, 490, 240);
        
        panelContenido.add(pnlobjetivos);

        JTextArea txtObjetivos = new JTextArea("Observa la silueta y selecciona la respuesta correcta "
                + "entre las cuatro opciones disponibles.\n"
                + "*-• Cada acierto suma puntos; cada error descuenta puntos y consume un corazón.\n"
                + "*-• Comienzas con tres corazones. Al perderlos todos, la partida finalizará.\n"
                + "*-• Cada dificultad dispone de un tiempo límite:\n"
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
        ImageIcon referencia1icon= new ImageIcon(getClass().getResource("/Multimedia/Minijuegos/Minijuego_1/Elementos_graficos/Referencia1.png"));
        Image referencia1Escalada = referencia1icon.getImage().getScaledInstance(400, 200, Image.SCALE_SMOOTH);
        lblImagen1.setIcon(new ImageIcon(referencia1Escalada));
        lblImagen1.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        lblImagen1.setHorizontalAlignment(SwingConstants.CENTER);
        lblImagen1.setText("");
        lblImagen1.setBounds(70, 580, 400, 220);
        
        panelContenido.add(lblImagen1);

        // ------------------ Información adicional -------------------------------
        JLabel lblinfo = new JLabel("Información Adicional");
        lblinfo.setFont(fuente2.deriveFont(Font.BOLD, 24f));
        lblinfo.setBounds(40, 760, 290, 150);
        lblinfo.setForeground(new Color(238, 151, 151));
        panelContenido.add(lblinfo);
        
        FondoPanelSemi pnlinfo = new FondoPanelSemi(new Color(240, 208, 96, 150));
        pnlinfo.setBounds(20, 840, 490, 165);

        // ------------ Contenido: Siguientes pasos -------------------
        JTextArea txtinfo = new JTextArea("*-• El botón \"¿Necesitas ayuda?\" mostrará una pista en forma de sonido o texto.\n"
                + "*-• Mientras esté abierta, el temporizador se detendrá. Utilizarla resta 10 puntos.\n"
                + "*-• Cada dificultad tiene 5 preguntas. Al completarlas, aparecerá una alerta antes de iniciar la siguiente dificultad.\n"
                + "*-• Cuanto menos tiempo tardes en responder, mayor será el puntaje obtenido.");

        txtinfo.setBounds(40, 850 ,450, 150);
        txtinfo.setOpaque(false);
        txtinfo.setEditable(false);
        txtinfo.setLineWrap(true);
        txtinfo.setWrapStyleWord(true);
        txtinfo.setFont(fuente1.deriveFont(18f));
        
        pnlinfo.add(txtinfo);
        
        panelContenido.add(pnlinfo);

        // ---------------------- Imagen 2 ---------------------------------
        ImageIcon referencia2icon= new ImageIcon(getClass().getResource("/Multimedia/Minijuegos/Minijuego_1/Elementos_graficos/Referencia2.png"));
        Image referencia2Escalada = referencia2icon.getImage().getScaledInstance(400, 200, Image.SCALE_SMOOTH);
        lblImagen2.setIcon(new ImageIcon(referencia2Escalada));
        lblImagen2.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        lblImagen2.setHorizontalAlignment(SwingConstants.CENTER);
        lblImagen2.setText("");
        lblImagen2.setBounds(70, 1020, 400, 220);

        panelContenido.add(lblImagen2);

        // Modifcar el tamaño del Scroll
        panelContenido.setPreferredSize(new Dimension(540, 1250));
        
        //------------------------ AL CERRAR -------------------
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    public static void main(String[] args) {
        new TutorialHiddenFox();
    }
}
