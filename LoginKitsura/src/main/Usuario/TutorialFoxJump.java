package main.Usuario;

import java.awt.*;
import javax.swing.*;

public class TutorialFoxJump extends PantallaTutorial {

    // Se crea el constructor donde obtendrá todos los atributos y/o elementos necesarios
    // Para el funcionamiento de la clase hija
    public TutorialFoxJump() {
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
        panelContenido.add(lblTitulo);

        //---------------- MASCOTA ----------------
        lblMascota = new JLabel();
        try {
            ImageIcon mascotaIcon = new ImageIcon(getClass().getResource("/Multimedia/utiles/mascotaKitsura/imagen/KitsuraFlotador.png"));
            Image mascotaEscalada = mascotaIcon.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
            lblMascota.setIcon(new ImageIcon(mascotaEscalada));
        } catch (Exception e) {
            lblMascota.setText("~");
        }
        lblMascota.setBounds(20, 80, 180, 180);
        panelContenido.add(lblMascota);

        // ----------------Titulo de Introducción----------------------
        JLabel lblIntro = new JLabel("Introducción");
        lblIntro.setFont(fuente2.deriveFont(Font.BOLD, 24f));
        lblIntro.setBounds(220, 80, 220, 30);
        panelContenido.add(lblIntro);

        // --------- Contenido de Introducción ---------------------
        JTextArea txtcontenido = new JTextArea("Fox Jump! es un minijuego educativo de "
                + "\"Kitsura\" que pone a prueba tu rapidez de pensamiento con "
                + "preguntas de verdadero o falso. Kitsura saltará entre dos "
                + "nenúfares para responder, y conforme avanzas la dificultad "
                + "aumenta y el tiempo disponible se reduce.");

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

        JTextArea txtPaso1 = new JTextArea("Lee la pregunta y haz clic en el nenúfar que dice "
                + "\"Verdadero\" o \"Falso\", según creas correcto.\n"
                + "• Kitsura saltará hacia el nenúfar elegido para mostrar tu respuesta.\n"
                + "• Cada acierto suma puntos según qué tan rápido respondiste;\n"
                + "cada error resta una vida.\n"
                + "• Comienzas con varios corazones. Al perderlos todos, la partida finalizará.\n"
                + "• Cada dificultad tiene un tiempo límite por pregunta. Si el temporizador\n"
                + "llega a cero, perderás la partida inmediatamente.\n");
        txtPaso1.setBounds(40, 325, 450, 150);
        txtPaso1.setOpaque(false);
        txtPaso1.setEditable(false);
        txtPaso1.setLineWrap(true);
        txtPaso1.setWrapStyleWord(true);
        txtPaso1.setFont(fuente1.deriveFont(18f));
        panelContenido.add(txtPaso1);

        // ---------------------- Imagen 1 ---------------------------------
try {
    ImageIcon referencia1icon = new ImageIcon(getClass().getResource("/Multimedia/utiles/ElementosGraficos/imagenes/ImgTutorial1-Juego2.png"));
    Image referencia1Escalada = referencia1icon.getImage().getScaledInstance(400, 200, Image.SCALE_SMOOTH);
    lblImagen1.setIcon(new ImageIcon(referencia1Escalada));
} catch (Exception e) {
    lblImagen1.setText("Imagen no disponible");
    System.out.println(e);
}
        lblImagen1.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        lblImagen1.setHorizontalAlignment(SwingConstants.CENTER);
        lblImagen1.setText("");
        lblImagen1.setBounds(100, 500, 350, 200);

        panelContenido.add(lblImagen1);

        // ------------------ Pasos siguientes -------------------------------
        JLabel lblPaso2 = new JLabel("Información Adicional");
        lblPaso2.setFont(fuente2.deriveFont(Font.BOLD, 24f));
        lblPaso2.setBounds(40, 650, 290, 150);
        panelContenido.add(lblPaso2);

        // ------------ Contenido: Siguientes pasos -------------------
        JTextArea txtPaso2 = new JTextArea("• El botón \"¿Necesitas ayuda?\" muestra una pista relacionada con la\n"
                + "pregunta actual. Mientras esté abierta, el temporizador se detiene.\n"
                + "Solo puedes usarla una vez por pregunta.\n"
                + "• Cada dificultad consta de cinco aciertos totales para subir de\n"
                + "nivel: Fácil → Intermedio → Difícil. Al subir, verás una pantalla\n"
                + "de aviso antes de continuar.\n"
                + "• Cuanto menos tiempo tardes en responder, mayor será tu puntaje.\n"
                + "• Si completas todas las preguntas de la dificultad Difícil sin perder\n"
                + "ninguna vida, obtienes una Victoria Perfecta.");

        txtPaso2.setBounds(40, 740, 450, 170);
        txtPaso2.setOpaque(false);
        txtPaso2.setEditable(false);
        txtPaso2.setLineWrap(true);
        txtPaso2.setWrapStyleWord(true);
        txtPaso2.setFont(fuente1.deriveFont(18f));
        panelContenido.add(txtPaso2);

        // ---------------------- Imagen 2 ---------------------------------
try {
    ImageIcon referencia1icon = new ImageIcon(getClass().getResource("/Multimedia/utiles/ElementosGraficos/imagenes/ImgTutorial2-Juego2.png"));
    Image referencia1Escalada = referencia1icon.getImage().getScaledInstance(400, 200, Image.SCALE_SMOOTH);
    lblImagen2.setIcon(new ImageIcon(referencia1Escalada));
} catch (Exception e) {
    lblImagen2.setText("Imagen no disponible");
    System.out.println(e);
}
        lblImagen2.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        lblImagen2.setHorizontalAlignment(SwingConstants.CENTER);
        lblImagen2.setText("");
        lblImagen2.setBounds(100, 940, 350, 200);

        panelContenido.add(lblImagen2);

        // Modifcar el tamaño del Scroll
        panelContenido.setPreferredSize(new Dimension(540, 1250));

    }

    public static void main(String[] args) {
        new TutorialFoxJump();
    }
}