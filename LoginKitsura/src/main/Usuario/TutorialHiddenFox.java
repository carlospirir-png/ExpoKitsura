package main.Usuario;

import java.awt.*;
import javax.swing.*;

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
        lblMascota.setBounds(0, 100, 300, 300);
        panelContenido.add(lblMascota);

        // ----------------Titulo de Introducción----------------------
        JLabel lblIntro = new JLabel("Introducción");
        lblIntro.setFont(fuente2.deriveFont(Font.BOLD, 24f));
        lblIntro.setBounds(220, 80, 220, 30);
        panelContenido.add(lblIntro);

        // --------- Contenido de Introducción ---------------------
        JTextArea txtcontenido = new JTextArea("Hidden Fox (\"Zorro Escondido\") es un minijuego educativo de "
                + "\"Kitsura\" que pone a prueba la lógica, la observación y el "
                + "conocimiento del jugador. El objetivo es identificar correctamente "
                + "la figura representada por una silueta antes de que el tiempo se "
                + "agote. Conforme se avanza, la dificultad aumenta y los desafíos "
                + "se vuelven más complejos.");

        txtcontenido.setBounds(220, 120, 280, 170);
        txtcontenido.setOpaque(false);
        txtcontenido.setEditable(false);
        txtcontenido.setLineWrap(true);
        txtcontenido.setWrapStyleWord(true);
        txtcontenido.setFont(fuente1.deriveFont(18f));
        panelContenido.add(txtcontenido);

        // ------------------- Contenido: Paso 1 ---------------------------------
        JLabel lblPaso1 = new JLabel("Paso 1");
        lblPaso1.setFont(fuente2.deriveFont(Font.BOLD, 24f));
        lblPaso1.setBounds(40, 300, 200, 30);
        panelContenido.add(lblPaso1);

        JTextArea txtPaso1 = new JTextArea("Observa la silueta y selecciona la respuesta correcta"
                + "entre las cuatro opciones disponibles.\n"
                + "• Cada acierto suma puntos; cada error descuenta .\n"
                + "puntos y consume un corazón\n"
                + "• Comienzas con tres corazones. Al perderlos todos, la partida finalizará..\n"
                + "• Cada dificultad dispone de un tiempo límite: Fácil (25 s), Intermedio (20 s).\n"
                + "y Difícil (15 s). Si el temporizador llega a cero, perderás la partida inmediatamente.\n");
        txtPaso1.setBounds(40, 325, 450, 130);
        txtPaso1.setOpaque(false);
        txtPaso1.setEditable(false);
        txtPaso1.setLineWrap(true);
        txtPaso1.setWrapStyleWord(true);
        txtPaso1.setFont(fuente1.deriveFont(18f));
        panelContenido.add(txtPaso1);

        // ---------------------- Imagen 1 ---------------------------------
        ImageIcon referencia1icon= new ImageIcon(getClass().getResource("/Multimedia/Minijuegos/Minijuego_1/Elementos_graficos/Referencia1.png"));
        Image referencia1Escalada = referencia1icon.getImage().getScaledInstance(400, 200, Image.SCALE_SMOOTH);
        lblImagen1.setIcon(new ImageIcon(referencia1Escalada));
        lblImagen1.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        lblImagen1.setHorizontalAlignment(SwingConstants.CENTER);
        lblImagen1.setText("");
        lblImagen1.setBounds(70, 440, 400, 220);
        
        panelContenido.add(lblImagen1);

        // ------------------ Pasos siguientes -------------------------------
        JLabel lblPaso2 = new JLabel("Información Adicional");
        lblPaso2.setFont(fuente2.deriveFont(Font.BOLD, 24f));
        lblPaso2.setBounds(40, 630, 290, 150);
        panelContenido.add(lblPaso2);

        // ------------ Contenido: Siguientes pasos -------------------
        JTextArea txtPaso2 = new JTextArea("• El botón \"¿Necesitas ayuda?\" mostrará una pista en forma de sonido o texto.\n"
                + "Mientras esté abierta, el temporizador se detendrá. Utilizarla resta 10 puntos.\n"
                + "• Cada dificultad consta de cinco preguntas.Al completarlas, aparecerá una alerta\n "
                + "antes de iniciar la siguiente dificultad."
                + "• Cuanto menos tiempo tardes en responder, mayor será el puntaje obtenido.");

        txtPaso2.setBounds(40, 720, 450, 150);
        txtPaso2.setOpaque(false);
        txtPaso2.setEditable(false);
        txtPaso2.setLineWrap(true);
        txtPaso2.setWrapStyleWord(true);
        txtPaso2.setFont(fuente1.deriveFont(18f));
        panelContenido.add(txtPaso2);

        // ---------------------- Imagen 2 ---------------------------------
        ImageIcon referencia2icon= new ImageIcon(getClass().getResource("/Multimedia/Minijuegos/Minijuego_1/Elementos_graficos/Referencia2.png"));
        Image referencia2Escalada = referencia2icon.getImage().getScaledInstance(400, 200, Image.SCALE_SMOOTH);
        lblImagen2.setIcon(new ImageIcon(referencia2Escalada));
        lblImagen2.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        lblImagen2.setHorizontalAlignment(SwingConstants.CENTER);
        lblImagen2.setText("");
        lblImagen2.setBounds(70, 870, 400, 220);

        panelContenido.add(lblImagen2);

        // Modifcar el tamaño del Scroll
        panelContenido.setPreferredSize(new Dimension(540, 1200));
        
        //------------------------ AL CERRAR -------------------
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    public static void main(String[] args) {
        new TutorialHiddenFox();
    }
}
