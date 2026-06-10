
package main;

import java.awt.*;
import javax.swing.*;

public class MenuPrincipal extends JFrame {

    private FondoPanel fondo;
    private Font fuente2;

    private JLabel lblTitulo, mascota;
    private JButton btnPerfil, btnMinijuegos, btnEstadisticas, btnSalir;

    public MenuPrincipal() {

        try {
            fuente2 = Font.createFont(
                    Font.TRUETYPE_FONT,
                    getClass().getResourceAsStream("/fuentes/KGPerfectPenmanship.ttf"));

        } catch (Exception e) {
            e.printStackTrace();
            fuente2 = new Font("Arial", Font.PLAIN, 20);
        }

        fondo = new FondoPanel("/Multimedia/utiles/fondoDosK.png");

        setContentPane(fondo);
        setTitle("Menú Principal");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);

        fondo.setLayout(null);

        crearComponentes();

        setVisible(true);
    }

    private void crearComponentes() {

        // TÍTULO
        lblTitulo = new JLabel("MENÚ PRINCIPAL");
        lblTitulo.setFont(fuente2.deriveFont(35f));
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setBounds(810, 120, 500, 50);
        fondo.add(lblTitulo);

        // BOTÓN PERFIL
        btnPerfil = new JButton("PERFIL");
        btnPerfil.setFont(fuente2.deriveFont(15f));
        btnPerfil.setBounds(820, 320, 285, 65);
        btnPerfil.setIconTextGap(5);

        ImageIcon icoPerfil = new ImageIcon(
                getClass().getResource("/Multimedia/utiles/pencil.png"));

        Image imgPerfil = icoPerfil.getImage().getScaledInstance(
                35, 35, Image.SCALE_SMOOTH);

        btnPerfil.setIcon(new ImageIcon(imgPerfil));
        fondo.add(btnPerfil);

        // BOTÓN MINIJUEGOS
        btnMinijuegos = new JButton("MINIJUEGOS");
        btnMinijuegos.setFont(fuente2.deriveFont(15f));
        btnMinijuegos.setBounds(820, 440, 285, 65);
        btnMinijuegos.setIconTextGap(5);

        ImageIcon icoMinijuegos = new ImageIcon(
                getClass().getResource("/Multimedia/utiles/mandoo.png"));

        Image imgMinijuegos = icoMinijuegos.getImage().getScaledInstance(
                35, 35, Image.SCALE_SMOOTH);

        btnMinijuegos.setIcon(new ImageIcon(imgMinijuegos));
        fondo.add(btnMinijuegos);

        // BOTÓN ESTADÍSTICAS
        btnEstadisticas = new JButton("ESTADISTICAS");
        btnEstadisticas.setFont(fuente2.deriveFont(15f));
        btnEstadisticas.setBounds(820, 560, 285, 65);
        btnEstadisticas.setIconTextGap(5);

        ImageIcon icoEstadisticas = new ImageIcon(
                getClass().getResource("/Multimedia/utiles/abaco.png"));

        Image imgAbaco = icoEstadisticas.getImage().getScaledInstance(
                35, 35, Image.SCALE_SMOOTH);

        btnEstadisticas.setIcon(new ImageIcon(imgAbaco));
        fondo.add(btnEstadisticas);

        // BOTÓN SALIR
        btnSalir = new JButton("SALIR");
        btnSalir.setFont(fuente2.deriveFont(15f));
        btnSalir.setBounds(820, 680, 285, 65);
        btnSalir.setIconTextGap(5);

        ImageIcon icoSalir = new ImageIcon(
                getClass().getResource("/Multimedia/utiles/salir.png"));

        Image imgSalir = icoSalir.getImage().getScaledInstance(
                35, 35, Image.SCALE_SMOOTH); 

        btnSalir.setIcon(new ImageIcon(imgSalir));
        fondo.add(btnSalir);

        // MASCOTA
        mascota = new JLabel();

        ImageIcon mascotaIcon = new ImageIcon(
                getClass().getResource("/Multimedia/utiles/mascota3.png"));

        Image mascotaEscalada = mascotaIcon.getImage().getScaledInstance(
                600, 600, Image.SCALE_SMOOTH);

        mascota.setIcon(new ImageIcon(mascotaEscalada));
        mascota.setBounds(240, 300, 600, 600);

        fondo.add(mascota);
    }
    }
