package main;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.*;

public class UsuarioMostrar extends JFrame {

    private FondoPanelSemi fondo;
    private JPanel panelIzquierdo;
    private JPanel panelTabla;
    private JButton btnEliminar;
    private JButton btnBuscar;
    private JLabel lblID;
    private JTextField txtID;
    private JLabel lblTitulo;
    private JTable tablaUsuarios;
    private JScrollPane scrollTabla;
    private JLabel lblMascota;

    public UsuarioMostrar() {

        fondo = new FondoPanelSemi("/Multimedia/utilesfondoDosK.png");
        setContentPane(fondo);

        setTitle("Usuarios");
        setSize(1920, 1080);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        fondo.setLayout(null);

        crearComponentes();

        setVisible(true);
    }

    private void crearComponentes() {

        panelIzquierdo = new JPanel() {

            @Override
            protected void paintComponent(Graphics g) {

                super.paintComponent(g);

                Graphics2D g2 = (Graphics2D) g.create();

                g2.setColor(new Color(0, 0, 0, 120));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 40, 40);

                g2.dispose();
            }
        };

        panelIzquierdo.setOpaque(false);
        panelIzquierdo.setLayout(null);
        panelIzquierdo.setBounds(60, 70, 720, 900);

        fondo.add(panelIzquierdo);

        btnEliminar = new JButton("ELIMINAR");
        btnEliminar.setFont(new Font("Arial", Font.BOLD, 28));
        btnEliminar.setBounds(60, 50, 250, 75);

        panelIzquierdo.add(btnEliminar);

        btnBuscar = new JButton("BUSCAR");
        btnBuscar.setFont(new Font("Arial", Font.BOLD, 28));
        btnBuscar.setBounds(390, 50, 250, 75);

        panelIzquierdo.add(btnBuscar);

        lblID = new JLabel("Ingrese el ID del usuario:");
        lblID.setFont(new Font("Arial", Font.PLAIN, 32));
        lblID.setForeground(Color.WHITE);
        lblID.setBounds(60, 190, 500, 45);

        panelIzquierdo.add(lblID);

        txtID = new JTextField();
        txtID.setFont(new Font("Arial", Font.PLAIN, 28));
        txtID.setBounds(60, 255, 580, 65);

        panelIzquierdo.add(txtID);

        lblMascota = new JLabel();

        ImageIcon mascotaIcon = new ImageIcon(getClass().getResource("/multimedia/utiles/ZorroControl.png"));

        Image mascotaEscalada = mascotaIcon.getImage().getScaledInstance(520, 520, Image.SCALE_SMOOTH);

        lblMascota.setIcon(new ImageIcon(mascotaEscalada));
        lblMascota.setBounds(100, 360, 520, 520);

        panelIzquierdo.add(lblMascota);

        panelTabla = new JPanel() {

            @Override
            protected void paintComponent(Graphics g) {

                super.paintComponent(g);

                Graphics2D g2 = (Graphics2D) g.create();

                g2.setColor(new Color(0, 0, 0, 120));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 40, 40);

                g2.dispose();
            }
        };

        panelTabla.setOpaque(false);
        panelTabla.setLayout(null);
        panelTabla.setBounds(830, 70, 1040, 900);

        fondo.add(panelTabla);

        lblTitulo = new JLabel("USUARIOS", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 52));
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setBounds(220, 30, 600, 60);

        panelTabla.add(lblTitulo);

        String[] columnas = {"ID", "Nombre", "Correo", "Contraseña"};

        DefaultTableModel modelo = new DefaultTableModel(columnas, 10);

        tablaUsuarios = new JTable(modelo);

        tablaUsuarios.setRowHeight(75);
        tablaUsuarios.setFont(new Font("Arial", Font.PLAIN, 22));

        tablaUsuarios.getTableHeader().setFont(new Font("Arial", Font.BOLD, 24));

        scrollTabla = new JScrollPane(tablaUsuarios);
        scrollTabla.setBounds(30, 120, 980, 730);

        panelTabla.add(scrollTabla);
    }

    public static void main(String[] args) {

        new UsuarioMostrar();
    }
}