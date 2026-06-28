package main.Usuario;

import java.awt.*;
import javax.swing.*;
import main.Menu.FondoPanelSemi;
import main.Menu.DecoracionBotones;

public class PantallaImagenPerfil extends JFrame {
    private FondoPanelSemi fondo;
    private JLabel lblTitulo,lblLogo,lblMascota;
    private DecoracionBotones btnVolver;
    
    private Font fuente1, fuente2;
    private JPanel panelImagenes;
    private JScrollPane scrollImagenes;

    public PantallaImagenPerfil() {
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
        fondo = new FondoPanelSemi("/Multimedia/utiles/fondos/interfaces/fondoDosK.png");
        setContentPane(fondo);

        setTitle("Imagen de Perfil");
        setSize(650, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        fondo.setLayout(null);

        crearComponentes();

        setVisible(true);
    }

    private void crearComponentes() {

        lblTitulo = new JLabel("Imagen de Perfil", SwingConstants.CENTER);
        lblTitulo.setFont(fuente2.deriveFont(28f));
        lblTitulo.setBounds(175,20,300,30);
        lblTitulo.setForeground(new Color(196,221,227));
        fondo.add(lblTitulo);

        lblMascota = new JLabel();
        ImageIcon mascotaIcon = new ImageIcon(getClass().getResource("/Multimedia/utiles/mascotaKitsura/imagen/VENTANITA.png"));
        Image mascotaEscalada = mascotaIcon.getImage().getScaledInstance(140,140,Image.SCALE_SMOOTH);
        lblMascota.setIcon(new ImageIcon(mascotaEscalada));
        lblMascota.setBounds(460,15,140,140);
        fondo.add(lblMascota);

        panelImagenes = new JPanel();
        panelImagenes.setLayout(null);
        panelImagenes.setPreferredSize(new java.awt.Dimension(500,220));

        agregarImagen("/Multimedia/utiles/ElementosGraficos/imagenes/ejemplo.png",10,10);
        agregarImagen("/Multimedia/utiles/ElementosGraficos/imagenes/ejemplo.png",130,10);
        agregarImagen("/Multimedia/utiles/ElementosGraficos/imagenes/ejemplo.png",250,10);
        agregarImagen("/Multimedia/utiles/ElementosGraficos/imagenes/ejemplo.png",370,10);

        agregarImagen("/Multimedia/utiles/ElementosGraficos/imagenes/ejemplo.png",10,110);
        agregarImagen("/Multimedia/utiles/ElementosGraficos/imagenes/ejemplo.png",130,110);
        agregarImagen("/Multimedia/utiles/ElementosGraficos/imagenes/ejemplo.png",250,110);
        agregarImagen("/Multimedia/utiles/ElementosGraficos/imagenes/ejemplo.png",370,110);

        scrollImagenes = new JScrollPane(panelImagenes);
        scrollImagenes.setBounds(75,95,500,220);
        fondo.add(scrollImagenes);

        JButton btnVolver = new DecoracionBotones("VOLVER");
        btnVolver.setFont(fuente2.deriveFont(15f));
        btnVolver.setBounds(250,350,140,40);
        fondo.add(btnVolver);
    }

    private void agregarImagen(String ruta, int x, int y) {

        JLabel imagen = new JLabel();

        ImageIcon icono = new ImageIcon(getClass().getResource(ruta));
        Image escalada = icono.getImage().getScaledInstance(110,80,Image.SCALE_SMOOTH);

        imagen.setIcon(new ImageIcon(escalada));
        imagen.setBounds(x,y,110,80);

        panelImagenes.add(imagen);
    }
    
    public static void main(String[] args) {
        new PantallaImagenPerfil();
    }

}