package main.Usuario;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.net.URL;
import javax.swing.*;
import main.Menu.FondoPanel;
import main.Menu.DecoracionBotones;

public class Victoria extends JFrame {

    private FondoPanel fondo;
    private Font fuente1;
    private Font fuente2;
    private JuegoBase juego;
    private DecoracionBotones btnResultados;

    public Victoria(JuegoBase juego) {
        this.juego = juego;
        try {
            // LettersForLearners
            fuente1 = Font.createFont(
                    Font.TRUETYPE_FONT,
                    getClass().getResourceAsStream("/fuentes/LettersForLearners.ttf"));
            // KGPerfectPenmanship
            fuente2 = Font.createFont(
                    Font.TRUETYPE_FONT,
                    getClass().getResourceAsStream("/fuentes/KGPerfectPenmanship.ttf"));

        } catch (Exception e) {
            e.printStackTrace();

            fuente1 = new Font("Arial", Font.PLAIN, 20);
            fuente2 = new Font("Arial", Font.PLAIN, 20);
        }

        fondo = new FondoPanel("/Multimedia/utiles/fondos/interfaces/fondoCincoK.png");
        // NO hace setContentPane ni setVisible
        fondo.setLayout(null);
//------------- ÍCONO ------------------
        //se obtiene la imagen del logo con getResource
        URL iconUrl = getClass().getResource("/Multimedia/utiles/logotipo/logofK.png");

        //se instancia el ícono con la imagen
        ImageIcon icono = new ImageIcon(iconUrl);

        //Se coloca el ícono al JFrame
        setIconImage(icono.getImage());

        crearComponentes();
        setVisible(true);
    }

    public FondoPanel getFondo() {
        return fondo;
    }

    private void crearComponentes() {

        JPanel panelIzquierdo = new JPanel();
        panelIzquierdo.setLayout(null);
        panelIzquierdo.setBackground(new Color(0, 0, 0, 115));
        panelIzquierdo.setBounds(100, 80, 700, 180);
        fondo.add(panelIzquierdo);

        //---------------- TÍTULO ----------------
        JLabel lblGanado = new JLabel("¡Has Ganado!", JLabel.CENTER);
        lblGanado.setFont(fuente2.deriveFont(35f));
        lblGanado.setForeground(Color.WHITE);
        lblGanado.setBounds(0, 30, 700, 60);
        panelIzquierdo.add(lblGanado);

        //---------------- FRASE DE MOTIVACIÓN ----------------
        JLabel lblFrase = new JLabel("Tu potencial es infinito, atrévete a explorarlo.", JLabel.CENTER);
        lblFrase.setFont(fuente1.deriveFont(40f));
        lblFrase.setForeground(Color.WHITE);
        lblFrase.setBounds(0, 105, 700, 35);
        panelIzquierdo.add(lblFrase);

        JPanel panelDerecho = new JPanel();
        panelDerecho.setLayout(null);
        panelDerecho.setBackground(new Color(0, 0, 0, 115));
        panelDerecho.setBounds(950, 80, 880, 800);
        fondo.add(panelDerecho);

        //---------------- FOTO PERFIL ----------------
        JLabel fotoPerfil = new JLabel();
        String rutaFoto = Sesion.getRutaFotoPerfil();
        ImageIcon perfilIcon = new ImageIcon(getClass().getResource(rutaFoto));
        Image perfilRedondo = crearImagenRedonda(perfilIcon.getImage(), 150);
        fotoPerfil.setIcon(new ImageIcon(perfilRedondo));
        fotoPerfil.setBounds(80, 60, 150, 150);
        panelDerecho.add(fotoPerfil);

        //---------------- DATOS DE USUARIO ----------------
        JLabel lblUsuario = new JLabel(Sesion.getNombreUsuario());
        lblUsuario.setFont(fuente2.deriveFont(25f));
        lblUsuario.setForeground(Color.decode("#FC767D"));
        lblUsuario.setBounds(250, 110, 400, 40);
        panelDerecho.add(lblUsuario);

        //---------------- MASCOTA ----------------
        JLabel mascotaCongrats = new JLabel();
        ImageIcon iconMascota = new ImageIcon(getClass().getResource("/Multimedia/utiles/mascotaKitsura/imagen/VICTORIA-Imperfecta.png"));
        Image imgEscalada = iconMascota.getImage().getScaledInstance(600, 600, Image.SCALE_SMOOTH);
        mascotaCongrats.setIcon(new ImageIcon(imgEscalada));
        mascotaCongrats.setBounds(150, 270, 600, 600);
        fondo.add(mascotaCongrats);

        //---------------- FRASE INFERIOR DE LA MASCOTA ----------------
        JLabel lblFraseAbajo = new JLabel("Aún con errores, pudiste lograrlo.", JLabel.CENTER);
        lblFraseAbajo.setFont(fuente1.deriveFont(26f));
        lblFraseAbajo.setForeground(Color.WHITE);
        lblFraseAbajo.setBounds(100, 880, 700, 35);
        fondo.add(lblFraseAbajo);

        //---------------- BOTÓN MOSTRAR RESULTADOS ----------------
        btnResultados = new DecoracionBotones("VER RESULTADOS",
                //ColorBase             ColorBorde              ColorLetra
                DecoracionBotones.ROSA, DecoracionBotones.ROJO, DecoracionBotones.AMARILLO, //MOUSE FUERA
                DecoracionBotones.ROJO, DecoracionBotones.ROSA, DecoracionBotones.ROSA); //MOUSE DENTRO
        btnResultados.setBounds(1210, 910, 400, 50);
        btnResultados.setFont(fuente2.deriveFont(25f));
        btnResultados.addActionListener(e -> {
            juego.mostrarResultadoConFade();
        });
        fondo.add(btnResultados);
    }

    private Image crearImagenRedonda(Image imgOriginal, int diametro) {
        // Genera una imagen vacía que soporta transparencias (Alpha)
        BufferedImage master = new BufferedImage(diametro, diametro, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = master.createGraphics();

        // Activa suavizado para evitar bordes "pixelados" o ásperos
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);

        // Dibuja el molde del círculo
        g2.fillOval(0, 0, diametro, diametro);

        // Cambia el modo de composición: Solo los pixeles de la imagen que caigan dentro del círculo serán visibles
        g2.setComposite(AlphaComposite.SrcIn);
        g2.drawImage(imgOriginal, 0, 0, diametro, diametro, null);

        g2.dispose();
        return master;
    }

}
