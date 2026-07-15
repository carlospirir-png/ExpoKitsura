package main.Usuario;

import java.awt.*;
import java.net.URL;
import javax.swing.*;
import main.Menu.*;

public class MenuPrincipal extends JFrame {

    private FondoPanel fondo;
    private Font fuente1;
    private Font fuente2;
    private int idUsuario;
    private boolean esInvitado; // <-- NUEVA VARIABLE: identifica si el usuario es invitado

    // Constructor para USUARIOS REGISTRADOS
    public MenuPrincipal(int idUsuario) {
        this.idUsuario = idUsuario;
        this.esInvitado = false;
        inicializar();
    }

     public MenuPrincipal() {

        this.esInvitado = Sesion.isEsInvitado();

        try {
            fuente1 = Font.createFont(Font.TRUETYPE_FONT,
                    getClass().getResourceAsStream("/fuentes/LettersForLearners.ttf"));
            fuente2 = Font.createFont(Font.TRUETYPE_FONT,
                    getClass().getResourceAsStream("/fuentes/KGPerfectPenmanship.ttf"));
        } catch (Exception e) {
            e.printStackTrace();
            fuente1 = new Font("Arial", Font.PLAIN, 20);
            fuente2 = new Font("Arial", Font.PLAIN, 20);
        }

        fondo = new FondoPanel("/Multimedia/utiles/fondos/interfaces/fondoDosK.png");
        setContentPane(fondo);
        //------------- ÍCONO ------------------
        //se obtiene la imagen del logo con getResource
        URL iconUrl = getClass().getResource("/Multimedia/utiles/logotipo/logofK.png");

        //se instancia el ícono con la imagen
        ImageIcon icono = new ImageIcon(iconUrl);

        //Se coloca el ícono al JFrame
        setIconImage(icono.getImage());

        setTitle("Página Principal");
        setSize(1880, 1080);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        fondo.setLayout(null);
        crearComponentes();
        setVisible(true);
    }


    private void inicializar() {

        try {
            fuente1 = Font.createFont(Font.TRUETYPE_FONT,
                    getClass().getResourceAsStream("/fuentes/LettersForLearners.ttf"));
            fuente2 = Font.createFont(Font.TRUETYPE_FONT,
                    getClass().getResourceAsStream("/fuentes/KGPerfectPenmanship.ttf"));
        } catch (Exception e) {
            e.printStackTrace();
            fuente1 = new Font("Arial", Font.PLAIN, 20);
            fuente2 = new Font("Arial", Font.PLAIN, 20);
        }

        fondo = new FondoPanel("/Multimedia/utiles/fondos/interfaces/fondoDosK.png");
        setContentPane(fondo);
        setTitle("Página Principal");
        setSize(1880, 1080);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        fondo.setLayout(null);
        crearComponentes();
        setVisible(true);
    }

    private void crearComponentes() {

        //---------------- PANEL TÍTULO ----------------
        FondoPanelSemi panelTitulo = new FondoPanelSemi(new Color(0, 0, 0, 150));
        panelTitulo.setLayout(null);
        panelTitulo.setBounds(760, 100, 480, 70);
        fondo.add(panelTitulo);

        JLabel lblTitulo = new JLabel("MENÚ PRINCIPAL", JLabel.CENTER);
        lblTitulo.setFont(fuente2.deriveFont(38f));
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setBounds(0, 0, 480, 70);
        panelTitulo.add(lblTitulo);


        // Definimos las posiciones Y según si es invitado o no,
        // para que los botones existentes queden centrados/ordenados
        int yMinijuegos, ySalir;


        if (esInvitado) {
            // Solo hay 2 botones -> los centramos en el espacio disponible
            yMinijuegos = 440;
            ySalir = 560;
        } else {
            // Hay 4 botones -> posiciones originales
            yMinijuegos = 440;
            ySalir = 680;
        }

        //---------------- BOTÓN PERFIL (solo usuarios registrados) ----------------
        if (!esInvitado) {
            JButton btnPerfil = new DecoracionBotones("PERFIL",
                    //ColorBase             ColorBorde              ColorLetra
                DecoracionBotones.ROSA, DecoracionBotones.ROJO, DecoracionBotones.AMARILLO, //MOUSE FUERA
                DecoracionBotones.ROJO, DecoracionBotones.ROSA, DecoracionBotones.ROSA); //MOUSE DENTRO

            btnPerfil.setFont(fuente2.deriveFont(25f));
            btnPerfil.setBounds(820, 320, 320, 65);
            btnPerfil.setIconTextGap(10);
            try {
                ImageIcon icoPerfil = new ImageIcon(getClass().getResource("/Multimedia/utiles/ElementosGraficos/imagenes/pencil.png"));
                Image imgPerfil = icoPerfil.getImage().getScaledInstance(35, 35, Image.SCALE_SMOOTH);
                btnPerfil.setIcon(new ImageIcon(imgPerfil));
            } catch (Exception e) {}
            btnPerfil.addActionListener(e -> {
                new PantallaPerfil();
                dispose();
            });
            fondo.add(btnPerfil);
        }

        //---------------- BOTÓN MINIJUEGOS (siempre visible) ----------------
        JButton btnMinijuegos = new DecoracionBotones("MINIJUEGOS",
                //ColorBase             ColorBorde              ColorLetra
                DecoracionBotones.AMARILLO_MOSTAZA, DecoracionBotones.GRIS, DecoracionBotones.AMARILLO, //MOUSE FUERA
                DecoracionBotones.AMARILLO_SUAVE, DecoracionBotones.AMARILLO_MOSTAZA, DecoracionBotones.AMARILLO_MOSTAZA); //MOUSE DENTRO

        btnMinijuegos.setFont(fuente2.deriveFont(25f));
        btnMinijuegos.setBounds(820, yMinijuegos, 320, 65);
        btnMinijuegos.setIconTextGap(10);
        try {
            ImageIcon icoMinijuegos = new ImageIcon(getClass().getResource("/Multimedia/utiles/ElementosGraficos/imagenes/mandoo.png"));
            Image imgMinijuegos = icoMinijuegos.getImage().getScaledInstance(35, 35, Image.SCALE_SMOOTH);
            btnMinijuegos.setIcon(new ImageIcon(imgMinijuegos));
        } catch (Exception e) {}
        btnMinijuegos.addActionListener(e -> {
            new MenuMinijuegos();
            dispose();
        });
        fondo.add(btnMinijuegos);

        //---------------- BOTÓN ESTADÍSTICAS (solo usuarios registrados) ----------------
        if (!esInvitado) {
            JButton btnEstadisticas = new DecoracionBotones("ESTADÍSTICAS",
                                    //ColorBase             ColorBorde              ColorLetra
                    DecoracionBotones.AZUL, DecoracionBotones.GRIS, DecoracionBotones.AMARILLO, //MOUSE FUERA
                    DecoracionBotones.CELESTE, DecoracionBotones.AZUL, DecoracionBotones.AZUL); //MOUSE DENTRO   

            btnEstadisticas.setFont(fuente2.deriveFont(25f));
            btnEstadisticas.setBounds(820, 560, 320, 65);
            btnEstadisticas.setIconTextGap(10);
            try {
                ImageIcon icoEstadisticas = new ImageIcon(getClass().getResource("/Multimedia/utiles/ElementosGraficos/imagenes/abaco.png"));
                Image imgAbaco = icoEstadisticas.getImage().getScaledInstance(35, 35, Image.SCALE_SMOOTH);
                btnEstadisticas.setIcon(new ImageIcon(imgAbaco));
            } catch (Exception e) {}
            btnEstadisticas.addActionListener(e -> {
                new PantallaEstadisticas();
                dispose();
            });
            fondo.add(btnEstadisticas);
        }

        //---------------- BOTÓN SALIR (siempre visible) ----------------
        JButton btnSalir = new DecoracionBotones("SALIR",
                                //ColorBase             ColorBorde              ColorLetra
                DecoracionBotones.VERDE, DecoracionBotones.GRIS, DecoracionBotones.AMARILLO, //MOUSE FUERA
                DecoracionBotones.VERDE_SUAVE, DecoracionBotones.VERDE, DecoracionBotones.VERDE); //MOUSE DENTRO  

        btnSalir.setFont(fuente2.deriveFont(25f));
        btnSalir.setBounds(820, ySalir, 320, 65);
        btnSalir.setIconTextGap(10);
        try {
            ImageIcon icoSalir = new ImageIcon(getClass().getResource("/Multimedia/utiles/ElementosGraficos/imagenes/salir.png"));
            Image imgSalir = icoSalir.getImage().getScaledInstance(35, 35, Image.SCALE_SMOOTH);
            btnSalir.setIcon(new ImageIcon(imgSalir));
        } catch (Exception e) {}
        btnSalir.addActionListener(e -> {
            dispose();
            new SalirDelJuego();
        });
        fondo.add(btnSalir);

        //---------------- MASCOTA ----------------
        JLabel mascota = new JLabel();
        try {
            ImageIcon mascotaIcon = new ImageIcon(getClass().getResource("/Multimedia/utiles/mascotaKitsura/imagen/MENÚ-PRINCIPAL-LINTERNA.png"));
            Image mascotaEscalada = mascotaIcon.getImage().getScaledInstance(600, 600, Image.SCALE_SMOOTH);
            mascota.setIcon(new ImageIcon(mascotaEscalada));
        } catch (Exception e) {
            mascota.setText("~");
        }
        mascota.setBounds(240, 300, 600, 600);
        fondo.add(mascota);
    }
}