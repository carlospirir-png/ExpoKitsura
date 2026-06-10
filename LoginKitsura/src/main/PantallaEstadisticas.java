package main;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.*;

public class PantallaEstadisticas extends JFrame {

    private FondoPanelSemi fondo;
    private FondoPanelSemi panelFondo;

    private JLabel lblTitulo;

    private JTable tabla;
    private JScrollPane scrollTabla;

    private JLabel lblUltimaPartida;
    private JTextField txtUltimaPartida;

    private JLabel lblGanadas;
    private JTextField txtGanadas;

    private JLabel lblUltimaPuntuacion;
    private JTextField txtUltimaPuntuacion;

    private JLabel lblPuntuacionTotal;
    private JTextField txtPuntuacionTotal;

    private JButton btnVolver;

    private JLabel lblLogo;
    private JLabel lblMascota;

    public PantallaEstadisticas() {

        fondo = new FondoPanelSemi("/Multimedia/utiles/fondoDosK.png");
        setContentPane(fondo);

        setTitle("Tabla Global");
        setSize(1920, 1080);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        fondo.setLayout(null);

        crearComponentes();

        setVisible(true);
    }

    private void crearComponentes() {

        lblTitulo = new JLabel("Tabla Global");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 60));
        lblTitulo.setBounds(130, 60, 500, 80);
        lblTitulo.setForeground(new Color(196, 221, 227));

        fondo.add(lblTitulo);

        DefaultTableModel modelo = new DefaultTableModel();

        modelo.addColumn("JUGADOR");
        modelo.addColumn("TIEMPO");
        modelo.addColumn("MAYOR PUNTUACIÓN");

        modelo.addRow(new Object[]{
            "Jugador_que_juega123",
            "00:00:00",
            "99999"
        });

        tabla = new JTable(modelo);

        tabla.setFont(new Font("Arial", Font.PLAIN, 24));
        tabla.setRowHeight(50);

        tabla.getTableHeader().setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        28));

        scrollTabla = new JScrollPane(tabla);
        scrollTabla.setBounds(120, 150, 1100, 570);

        fondo.add(scrollTabla);

        lblUltimaPartida = new JLabel("Última partida");
        lblUltimaPartida.setFont(new Font("Arial", Font.BOLD, 36));
        lblUltimaPartida.setBounds(120, 740, 250, 50);
        lblUltimaPartida.setForeground(new Color(196, 221, 227));

        fondo.add(lblUltimaPartida);

        txtUltimaPartida = new JTextField(" ");
        txtUltimaPartida.setFont(new Font("Arial", Font.PLAIN, 30));
        txtUltimaPartida.setBounds(120, 790, 340, 50);

        fondo.add(txtUltimaPartida);

        lblGanadas = new JLabel("Partidas Ganadas");
        lblGanadas.setFont(new Font("Arial", Font.BOLD, 36));
        lblGanadas.setBounds(120, 850, 400, 50);
        lblGanadas.setForeground(new Color(196, 221, 227));

        fondo.add(lblGanadas);

        txtGanadas = new JTextField("0");
        txtGanadas.setFont(new Font("Arial", Font.PLAIN, 30));
        txtGanadas.setBounds(120, 900, 340, 50);

        fondo.add(txtGanadas);

        lblUltimaPuntuacion = new JLabel("Última puntuación");
        lblUltimaPuntuacion.setFont(new Font("Arial", Font.BOLD, 36));
        lblUltimaPuntuacion.setBounds(850, 740, 400, 50);
        lblUltimaPuntuacion.setForeground(new Color(196, 221, 227));

        fondo.add(lblUltimaPuntuacion);

        txtUltimaPuntuacion = new JTextField("0 pts");
        txtUltimaPuntuacion.setFont(new Font("Arial", Font.PLAIN, 30));
        txtUltimaPuntuacion.setBounds(850, 790, 340, 50);

        fondo.add(txtUltimaPuntuacion);

        lblPuntuacionTotal = new JLabel("Puntuación total");
        lblPuntuacionTotal.setFont(new Font("Arial", Font.BOLD, 36));
        lblPuntuacionTotal.setBounds(850, 850, 340, 50);
        lblPuntuacionTotal.setForeground(new Color(196, 221, 227));

        fondo.add(lblPuntuacionTotal);

        txtPuntuacionTotal = new JTextField("9999 pts");
        txtPuntuacionTotal.setFont(new Font("Arial", Font.PLAIN, 30));
        txtPuntuacionTotal.setBounds(850, 900, 340, 50);

        fondo.add(txtPuntuacionTotal);

        btnVolver = new JButton("Volver");
        btnVolver.setFont(new Font("Arial", Font.BOLD, 40));
        btnVolver.setBounds(1540, 900, 240, 50);

        fondo.add(btnVolver);

        lblMascota = new JLabel();

        ImageIcon mascotaIcon
                = new ImageIcon(
                        getClass().getResource("/Multimedia/utiles/ZorroTrofeo.png"));

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

    public static void main(String[] args) {

        new PantallaEstadisticas();
    }
}
