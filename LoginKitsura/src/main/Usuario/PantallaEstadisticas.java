package main.Usuario;

import java.awt.*;
import java.net.URL;
import javax.swing.*;
import javax.swing.table.*;
import java.util.List;
import main.Menu.*;

public class PantallaEstadisticas extends JFrame {

    private final FondoPanelSemi fondo;
    private FondoPanelSemi panelFondo;

    private JLabel lblTitulo;

    private JTable tabla;
    private JScrollPane scrollTabla;

    private JLabel lblUltimaPartida;
    private JTextField txtUltimaPartida;

    private JLabel lblUltimoJuego;
    private JTextField txtUltimoJuego;

    private JLabel lblGanadas;
    private JTextField txtGanadas;

    private JLabel lblUltimaPuntuacion;
    private JTextField txtUltimaPuntuacion;

    // MODIFICADO: De puntuación total a puntuación máxima
    private JLabel lblPuntuacionMaxima;
    private JTextField txtPuntuacionMaxima;

    private JButton btnVolver;

    private JLabel lblMascota;
    private Font fuente1;
    private Font fuente2;

    /*=====================================================================
      DATOS NECESARIOS PARA CONSULTAR LA BASE DE DATOS
      Ya no se recibe idMinijuego: esta pantalla es una tabla GLOBAL que
      agrega los datos de Hidden Fox, Fox Jump! y Maulwurf Rennt.
     =====================================================================*/
    private final EstadisticaDAO dao = new EstadisticaDAO();
    private final int idUsuario;

    // Cuántos jugadores como máximo se muestran en la tabla global
    private static final int LIMITE_RANKING = 20;

    public PantallaEstadisticas() {

        this.idUsuario = Sesion.getIdUsuarioActual();

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
        
        //------------- ÍCONO ------------------
        //se obtiene la imagen del logo con getResource
        URL iconUrl = getClass().getResource("/Multimedia/utiles/logotipo/logofK.png");

        //se instancia el ícono con la imagen
        ImageIcon icono = new ImageIcon(iconUrl);

        //Se coloca el ícono al JFrame
        setIconImage(icono.getImage());

        setTitle("Tabla Global");
        setSize(1920, 1080);
        setResizable(false);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        fondo.setLayout(null);

        crearComponentes();

        setVisible(true);
    }

    private void crearComponentes() {

        lblTitulo = new JLabel("Tabla Global");
        lblTitulo.setFont(fuente2.deriveFont(55f));
        lblTitulo.setBounds(130, 60, 500, 80);
        lblTitulo.setForeground(Color.BLACK);

        fondo.add(lblTitulo);

        DefaultTableModel modelo = new DefaultTableModel();

        modelo.addColumn("JUGADOR");
        modelo.addColumn("TIEMPO");
        // MODIFICADO: Ahora la tercera columna es Puntuación Total
        modelo.addColumn("PUNTUACIÓN TOTAL");

        // Carga el ranking global real en la tabla
        cargarRanking(modelo);

        tabla = new JTable(modelo) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tabla.getTableHeader().setReorderingAllowed(false);
        tabla.setFont(fuente1.deriveFont(34f));
        tabla.setRowHeight(50);

        tabla.getTableHeader().setFont(fuente2.deriveFont(34f));

        scrollTabla = new JScrollPane(tabla);
        scrollTabla.setBounds(120, 150, 1100, 570);

        fondo.add(scrollTabla);

        lblUltimaPuntuacion = new JLabel("Última puntuación");
        lblUltimaPuntuacion.setFont(fuente2.deriveFont(36f));
        lblUltimaPuntuacion.setBounds(850, 740, 400, 50);
        lblUltimaPuntuacion.setForeground(new Color(196, 221, 227));

        fondo.add(lblUltimaPuntuacion);

        txtUltimaPuntuacion = new JTextField("0 pts");
        txtUltimaPuntuacion.setFont(fuente1.deriveFont(36f));
        txtUltimaPuntuacion.setEditable(false);
        txtUltimaPuntuacion.setBounds(850, 790, 340, 50);

        fondo.add(txtUltimaPuntuacion);

        lblUltimaPartida = new JLabel("Última partida");
        lblUltimaPartida.setFont(fuente2.deriveFont(38f));
        lblUltimaPartida.setBounds(120, 740, 250, 50);
        lblUltimaPartida.setForeground(new Color(196, 221, 227));

        fondo.add(lblUltimaPartida);

        txtUltimaPartida = new JTextField(" ");
        txtUltimaPartida.setFont(fuente2.deriveFont(38f));
        txtUltimaPartida.setEditable(false);
        txtUltimaPartida.setBounds(120, 790, 340, 50);

        fondo.add(txtUltimaPartida);

        lblUltimoJuego = new JLabel("Ultimo Juego");
        lblUltimoJuego.setFont(fuente2.deriveFont(36f));
        lblUltimoJuego.setBounds(540, 790, 340, 50);
        lblUltimoJuego.setForeground(new Color(196, 221, 227));

        fondo.add(lblUltimoJuego);

        txtUltimoJuego = new JTextField("Sin Partida");
        txtUltimoJuego.setFont(fuente1.deriveFont(36f));
        txtUltimoJuego.setEditable(false);
        txtUltimoJuego.setBounds(540, 840, 240, 50);

        fondo.add(txtUltimoJuego);

        lblGanadas = new JLabel("Partidas Ganadas");
        lblGanadas.setFont(fuente2.deriveFont(38f));
        lblGanadas.setBounds(120, 850, 400, 50);
        lblGanadas.setForeground(new Color(196, 221, 227));

        fondo.add(lblGanadas);

        txtGanadas = new JTextField("0");
        txtGanadas.setFont(fuente1.deriveFont(36f));
        txtGanadas.setEditable(false);
        txtGanadas.setBounds(120, 900, 340, 50);

        fondo.add(txtGanadas);

        // MODIFICADO: De Puntuación Total a Puntuación Máxima en la interfaz inferior
        lblPuntuacionMaxima = new JLabel("Puntuación máxima");
        lblPuntuacionMaxima.setFont(fuente2.deriveFont(36f));
        lblPuntuacionMaxima.setBounds(850, 850, 340, 50);
        lblPuntuacionMaxima.setForeground(new Color(196, 221, 227));

        fondo.add(lblPuntuacionMaxima);

        txtPuntuacionMaxima = new JTextField("0 pts");
        txtPuntuacionMaxima.setFont(fuente1.deriveFont(36f));
        txtPuntuacionMaxima.setEditable(false);
        txtPuntuacionMaxima.setBounds(850, 900, 340, 50);

        fondo.add(txtPuntuacionMaxima);

        // Llena los campos con los datos del usuario en sesión
        cargarDatosUsuario();

        btnVolver = new DecoracionBotones("VOLVER",
                DecoracionBotones.AZUL, DecoracionBotones.GRIS, DecoracionBotones.AMARILLO,
                DecoracionBotones.CELESTE, DecoracionBotones.AZUL, DecoracionBotones.AZUL);

        btnVolver.setFont(fuente1.deriveFont(40f));
        btnVolver.setBounds(1540, 900, 240, 70);
        btnVolver.addActionListener(e -> {
            new MenuPrincipal();
            dispose();
        });
        fondo.add(btnVolver);

        lblMascota = new JLabel();

        ImageIcon mascotaIcon
                = new ImageIcon(
                        getClass().getResource("/Multimedia/utiles/mascotaKitsura/imagen/ESTADÍSTICAS-TROFEO.png"));

        Image mascotaEscalada
                = mascotaIcon.getImage().getScaledInstance(
                        650,
                        650,
                        Image.SCALE_SMOOTH);

        lblMascota.setIcon(new ImageIcon(mascotaEscalada));
        lblMascota.setBounds(1200, 150, 650, 650);

        fondo.add(lblMascota);

        panelFondo = new FondoPanelSemi(new Color(110, 110, 110, 190));
        panelFondo.setLayout(null);
        panelFondo.setBounds(120, 30, 1100, 540);

        fondo.add(panelFondo);
    }

    /*=====================================================================
      CARGAR RANKING GLOBAL EN LA TABLA
     =====================================================================*/
    private void cargarRanking(DefaultTableModel modelo) {

        List<EstadisticaDAO.FilaRanking> ranking = dao.obtenerRankingGlobal(LIMITE_RANKING);

        if (ranking.isEmpty()) {
            modelo.addRow(new Object[]{"Aún no hay partidas registradas", "-", "-"});
            return;
        }

        for (EstadisticaDAO.FilaRanking fila : ranking) {
            modelo.addRow(new Object[]{
                fila.nombreUsuario,
                formatearTiempo(fila.tiempoTotal),
                fila.puntuacionTotal // Se mantiene puntuación total aquí para la tabla
            });
        }
    }

    /*=====================================================================
      CARGAR LOS CAMPOS CON LOS DATOS GLOBALES DEL USUARIO EN SESIÓN
     =====================================================================*/
    private void cargarDatosUsuario() {

        // ---- Partidas ganadas ----
        int ganadas = dao.contarPartidasGanadas(idUsuario);
        txtGanadas.setText(String.valueOf(ganadas));

        // ---- MODIFICADO: Ahora asigna la puntuación máxima al txt inferior ----
        EstadisticaDAO.ResumenUsuario resumen = dao.obtenerResumenUsuario(idUsuario);
        // Nota: Asegúrate de que tu objeto 'resumen' cuente con el atributo 'puntuacionMaxima' 
        // o el equivalente que retorne tu EstadisticaDAO (ej: mayorPuntuacion).
        txtPuntuacionMaxima.setText(resumen.mejorPuntuacion + " pts");

        // ---- Última partida jugada ----
        EstadisticaDAO.UltimaPartida ultima = dao.obtenerUltimaPartida(idUsuario);

        if (ultima != null) {
            txtUltimaPartida.setText(formatearTiempo(ultima.tiempoJugado));
            txtUltimaPartida.setToolTipText("Minijuego: " + ultima.nombreMinijuego);

            txtUltimaPuntuacion.setText(ultima.puntuacion + " pts");
            txtUltimaPuntuacion.setToolTipText("Minijuego: " + ultima.nombreMinijuego);
        } else {
            txtUltimaPartida.setText("Sin partidas");
            txtUltimaPuntuacion.setText("0 pts");
        }

        // Último juego
        String ultimoMinijuego = dao.obtenerUltimoMinijuegoJugado(idUsuario);
        txtUltimoJuego.setText(ultimoMinijuego != null ? ultimoMinijuego : "Sin Partida");
    }

    //--------------------- F O R M A T O   D E   T I E M P O ---------------------
    private String formatearTiempo(int segundosTotales) {
        int hours = segundosTotales / 3600;
        int minutos = (segundosTotales % 3600) / 60;
        int segundos = segundosTotales % 60;

        if (hours > 0) {
            return String.format("%02d:%02d:%02d", hours, minutos, segundos);
        }
        return String.format("%02d:%02d", minutos, segundos);
    }

    public static void main(String[] args) {
        new PantallaEstadisticas();
    }
}
