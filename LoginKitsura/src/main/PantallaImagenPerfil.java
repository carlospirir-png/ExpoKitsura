package main;

import java.awt.*;
import javax.swing.*;

public class PantallaImagenPerfil extends JFrame {
    private FondoPanelSemi fondo;
    private JLabel lblTitulo,lblLogo,lblMascota;
    private JButton btnVolver;

    private JPanel panelImagenes;
    private JScrollPane scrollImagenes;

    public PantallaImagenPerfil() {

        fondo = new FondoPanelSemi("/Multimedia/utiles/fondoDosK.png");
        setContentPane(fondo);

        setTitle("Imagen de Perfil");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        fondo.setLayout(null);

        crearComponentes();

        setVisible(true);
    }

    private void crearComponentes() {

        lblTitulo = new JLabel("Imagen de Perfil", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 30));
        lblTitulo.setBounds(170,20,250,30);
        lblTitulo.setForeground(new Color(196,221,227));
        fondo.add(lblTitulo);

        lblMascota = new JLabel();
        ImageIcon mascotaIcon = new ImageIcon(getClass().getResource("/Multimedia/utiles/ZorroPerfil.png"));
        Image mascotaEscalada = mascotaIcon.getImage().getScaledInstance(130,130,Image.SCALE_SMOOTH);
        lblMascota.setIcon(new ImageIcon(mascotaEscalada));
        lblMascota.setBounds(450,0,130,130);
        fondo.add(lblMascota);

        panelImagenes = new JPanel();
        panelImagenes.setLayout(null);
        panelImagenes.setPreferredSize(new java.awt.Dimension(500,220));

        agregarImagen("/Multimedia/utiles/ejemplo.png",10,10);
        agregarImagen("/Multimedia/utiles/ejemplo.png",130,10);
        agregarImagen("/Multimedia/utiles/ejemplo.png",250,10);
        agregarImagen("/Multimedia/utiles/ejemplo.png",370,10);

        agregarImagen("/Multimedia/utiles/ejemplo.png",10,110);
        agregarImagen("/Multimedia/utiles/ejemplo.png",130,110);
        agregarImagen("/Multimedia/utiles/ejemplo.png",250,110);
        agregarImagen("/Multimedia/utiles/ejemplo.png",370,110);

        scrollImagenes = new JScrollPane(panelImagenes);
        scrollImagenes.setBounds(40,70,500,220);
        fondo.add(scrollImagenes);

        btnVolver = new JButton("Volver");
        btnVolver.setFont(new Font("Arial", Font.BOLD, 20));
        btnVolver.setBounds(230,310,140,40);
        fondo.add(btnVolver);

        lblLogo = new JLabel();
        ImageIcon logoIcon = new ImageIcon(getClass().getResource("/Multimedia/utiles/logoKitsura2.png"));
        Image logoEscalado = logoIcon.getImage().getScaledInstance(100,40,Image.SCALE_SMOOTH);
        lblLogo.setIcon(new ImageIcon(logoEscalado));
        lblLogo.setBounds(450,310,100,40);
        fondo.add(lblLogo);
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