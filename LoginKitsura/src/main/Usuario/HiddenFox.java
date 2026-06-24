package main.Usuario;

import java.awt.*;
import javax.swing.*;
import java.net.URL;

public class HiddenFox extends JFrame {

    private final JPanel fondo;

    ImageIcon fondoPapelIcon, iconoSombra;

    private Font fuente1, fuente2;

    private JLabel vida1, vida2, vida3, titulo, tiempoTexto, tiempo, nivel, dificultad, categoria, mascota, imagenSombra;

    JLabel fondoPapel;

    Image fondoPapelEscalado;

    private JButton btnAyuda, btnRespuesta1, btnRespuesta2, btnRespuesta3, btnRespuesta4;

    public HiddenFox() {
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

        //---------------- FONDO ----------------
        fondo = new JPanel();
        fondo.setLayout(null);
        fondo.setBackground(new Color(178, 197, 178));

        setContentPane(fondo);

        setTitle("Hidden Fox");
        setSize(1880, 1080);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        crearComponentes();

        //------------------- SOMBRA ---------
        imagenSombra = new JLabel(); //Se crea el label de la imagen sombra

        imagenSombra.setBounds(350, 60, 350, 320); //Se posiciona y configura el tamaño de la sombra

    }

    private void crearComponentes() {

        //---------------- VIDAS ----------------
        try {

            ImageIcon corazonIcon = new ImageIcon(
                    getClass().getResource("/Multimedia/utiles/ElementosGraficos/imagenes/corazon.png"));

            Image corazonEscalado = corazonIcon.getImage().getScaledInstance(
                    60, 60, Image.SCALE_SMOOTH);

            ImageIcon corazonFinal = new ImageIcon(corazonEscalado);

            vida1 = new JLabel(corazonFinal);
            vida2 = new JLabel(corazonFinal);
            vida3 = new JLabel(corazonFinal);

        } catch (Exception e) {

            vida1 = new JLabel("♥");
            vida2 = new JLabel("♥");
            vida3 = new JLabel("♥");

            vida1.setFont(fuente1.deriveFont(55f));
            vida2.setFont(fuente1.deriveFont(55f));
            vida3.setFont(fuente1.deriveFont(55f));

            vida1.setForeground(Color.RED);
            vida2.setForeground(Color.RED);
            vida3.setForeground(Color.RED);
        }

        vida1.setBounds(70, 25, 60, 60);
        vida2.setBounds(135, 25, 60, 60);
        vida3.setBounds(200, 25, 60, 60);

        fondo.add(vida1);
        fondo.add(vida2);
        fondo.add(vida3);

        //---------------- AYUDA ----------------
        btnAyuda = new JButton("¿Necesitas ayuda?");
        btnAyuda.setFont(fuente2.deriveFont(18f));
        btnAyuda.setBounds(60, 120, 280, 55);

        fondo.add(btnAyuda);

        //---------------- TITULO ----------------
        titulo = new JLabel(
                "<html><center>¿Quién o qué se encuentra<br>detrás de la sombra?</center></html>",
                SwingConstants.CENTER);

        titulo.setFont(fuente1.deriveFont(40f));
        titulo.setForeground(Color.BLACK);
        titulo.setBounds(500, 20, 900, 150);

        fondo.add(titulo);

        //---------------- TIEMPO ----------------
        tiempoTexto = new JLabel("Tiempo restante:");
        tiempoTexto.setFont(fuente2.deriveFont(25f));
        tiempoTexto.setForeground(Color.BLACK);
        tiempoTexto.setBounds(1450, 70, 300, 40);

        fondo.add(tiempoTexto);

        tiempo = new JLabel("00:00:00", SwingConstants.CENTER);
        tiempo.setFont(fuente2.deriveFont(28f));
        tiempo.setOpaque(true);
        tiempo.setBackground(Color.WHITE);
        tiempo.setForeground(Color.BLACK);
        tiempo.setBounds(1440, 120, 320, 60);

        fondo.add(tiempo);

        //---------------- RESPUESTA 1 ----------------
        btnRespuesta1 = new JButton("RESPUESTA 1");
        btnRespuesta1.setFont(fuente2.deriveFont(20f));
        btnRespuesta1.setBounds(500, 730, 300, 75);

        fondo.add(btnRespuesta1);

        //---------------- RESPUESTA 2 ----------------
        btnRespuesta2 = new JButton("RESPUESTA 2");
        btnRespuesta2.setFont(fuente2.deriveFont(20f));
        btnRespuesta2.setBounds(1020, 730, 300, 75);

        fondo.add(btnRespuesta2);

        //---------------- RESPUESTA 3 ----------------
        btnRespuesta3 = new JButton("RESPUESTA 3");
        btnRespuesta3.setFont(fuente2.deriveFont(20f));
        btnRespuesta3.setBounds(500, 840, 300, 75);

        fondo.add(btnRespuesta3);

        //---------------- RESPUESTA 4 ----------------
        btnRespuesta4 = new JButton("RESPUESTA 4");
        btnRespuesta4.setFont(fuente2.deriveFont(20f));
        btnRespuesta4.setBounds(1020, 840, 300, 75);

        fondo.add(btnRespuesta4);

        //---------------- NIVEL ----------------
        nivel = new JLabel("Nivel: ***");
        nivel.setFont(fuente2.deriveFont(25f));
        nivel.setForeground(Color.BLACK);
        nivel.setBounds(80, 740, 250, 40);

        fondo.add(nivel);

        //---------------- DIFICULTAD ----------------
        dificultad = new JLabel("Dificultad: ***");
        dificultad.setFont(fuente2.deriveFont(25f));
        dificultad.setForeground(Color.BLACK);
        dificultad.setBounds(80, 790, 250, 40);

        fondo.add(dificultad);

        //---------------- CATEGORIA ----------------
        categoria = new JLabel("Categoría: ***");
        categoria.setFont(fuente2.deriveFont(25f));
        categoria.setForeground(Color.BLACK);
        categoria.setBounds(80, 840, 250, 40);

        fondo.add(categoria);

        //---------------- MASCOTA ----------------
        mascota = new JLabel();

        ImageIcon mascotaIcon = new ImageIcon(
                getClass().getResource("/Multimedia/utiles/mascotaKitsura/imagen/zorroLupa.png"));

        Image mascotaEscalada = mascotaIcon.getImage().getScaledInstance(
                450, 450, Image.SCALE_SMOOTH);

        mascota.setIcon(new ImageIcon(mascotaEscalada));
        mascota.setBounds(1450, 480, 450, 450);

        fondo.add(mascota);
    }

    //-------------------- CAMBIAR FONDOS -----------
    public void CambiarImagen(String linkImagen) {
        //---------------- IMAGEN SOMBRA ----------------

        URL link = getClass().getResource(linkImagen);

        try {

            if (link == null) {
                throw new RuntimeException("No se encontró la imagen: " + linkImagen);
            }

            iconoSombra = new ImageIcon(link);

            Image sombraEscalada = iconoSombra.getImage().getScaledInstance(
                    350, 320, Image.SCALE_SMOOTH);

            imagenSombra.setIcon(new ImageIcon(sombraEscalada));
            
            fondoPapel.add(imagenSombra); //Se añade la sombra al fondo papel

        } catch (Exception e) {

            e.printStackTrace();

            imagenSombra.setText("ERROR AL CARGAR IMAGEN");
            imagenSombra.setHorizontalAlignment(SwingConstants.CENTER);
            imagenSombra.setForeground(Color.RED);
        }
    }

    public void CambiarFondoPapel(String linkImagen) {
        //----------------- FONDO PAPEL  --------------

        URL link = getClass().getResource(linkImagen);

        try {

            if (link == null) {
                throw new RuntimeException("No se encontró la imagen: " + linkImagen);
            }

            fondoPapelIcon = new ImageIcon(link);

            fondoPapelEscalado = fondoPapelIcon.getImage().getScaledInstance(
                    1050, 450, Image.SCALE_SMOOTH);

            fondoPapel = new JLabel(new ImageIcon(fondoPapelEscalado));
            fondoPapel.setLayout(null);
            fondoPapel.setBounds(420, 250, 1050, 450);
            fondo.add(fondoPapel); //Se añade el fondo papel al fondo
            setVisible(true); //Se vuelve visible
        } catch (Exception e) {
            e.printStackTrace();

            imagenSombra.setText("ERROR AL CARGAR IMAGEN");
            imagenSombra.setHorizontalAlignment(SwingConstants.CENTER);
            imagenSombra.setForeground(Color.RED);
        }

    }

    public void CambiarFondo(int dificultad) {
        Color verde = new Color(178, 197, 178);
        Color amarillo = new Color(239, 218, 154);
        Color rojo = new Color(218, 77, 88);

        if (dificultad == 1) {
            fondo.setBackground(verde);
        } else if (dificultad == 2) {
            fondo.setBackground(amarillo);
        } else if (dificultad == 3) {
            fondo.setBackground(rojo);
        } else {
            System.out.println("Error en la colocación de color.");
        }

    }
    
    public static void main(String[] args) {
        new HiddenFox();
    }

}
