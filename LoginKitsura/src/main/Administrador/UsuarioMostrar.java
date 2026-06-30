package main.Administrador;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import main.Menu.DecoracionBotones;
import main.Menu.FondoPanel;
import main.Menu.FondoPanelSemi;

public class UsuarioMostrar extends JFrame {

    private FondoPanel fondo;
    private Font fuente1;
    private Font fuente2;

    public UsuarioMostrar() {

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

        fondo = new FondoPanel("/Multimedia/utiles/fondos/interfaces/fondoDosK.png");
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


        //---------------- PANEL IZQUIERDO ----------------
        FondoPanelSemi panelIzquierdo = new FondoPanelSemi(new Color(0, 0, 0, 130));
        panelIzquierdo.setLayout(null);
        panelIzquierdo.setBounds(100, 170, 520, 700);
        fondo.add(panelIzquierdo);

<<<<<<< HEAD
        JButton btnEliminar = new DecoracionBotones("ELIMINAR", "#FC767D", "#da4d58", "#da4d58");
=======
        JButton btnEliminar = new DecoracionBotones("ELIMINAR",
                                //ColorBase             ColorBorde              ColorLetra
                DecoracionBotones.AZUL, DecoracionBotones.GRIS, DecoracionBotones.AMARILLO, //MOUSE FUERA
                DecoracionBotones.CELESTE, DecoracionBotones.AZUL, DecoracionBotones.AZUL); //MOUSE DENTRO   

>>>>>>> 8d29a9e75b19afa2cfd4b44a742a0f73e91fab9b
        btnEliminar.setFont(fuente2.deriveFont(24f));
        btnEliminar.setBounds(40, 40, 190, 60);

        btnEliminar.addActionListener(e -> {

            // Acción eliminar usuario

        });

        panelIzquierdo.add(btnEliminar);

<<<<<<< HEAD
        JButton btnBuscar = new DecoracionBotones("BUSCAR", "#FC767D", "#da4d58", "#da4d58");
=======
        JButton btnBuscar = new DecoracionBotones("BUSCAR",
                                //ColorBase             ColorBorde              ColorLetra
                DecoracionBotones.AZUL, DecoracionBotones.GRIS, DecoracionBotones.AMARILLO, //MOUSE FUERA
                DecoracionBotones.CELESTE, DecoracionBotones.AZUL, DecoracionBotones.AZUL); //MOUSE DENTRO   

>>>>>>> 8d29a9e75b19afa2cfd4b44a742a0f73e91fab9b
        btnBuscar.setFont(fuente2.deriveFont(24f));
        btnBuscar.setBounds(280, 40, 190, 60);

        btnBuscar.addActionListener(e -> {

            // Acción buscar usuario

        });

        panelIzquierdo.add(btnBuscar);

        JLabel lblID = new JLabel("Ingrese el ID del usuario");
        lblID.setFont(fuente2.deriveFont(26f));
        lblID.setForeground(Color.WHITE);
        lblID.setBounds(35, 150, 440, 35);
        panelIzquierdo.add(lblID);

        JTextField txtID = new JTextField();
        txtID.setFont(fuente1.deriveFont(34f));
        txtID.setBounds(35, 200, 440, 55);
        panelIzquierdo.add(txtID);

        //---------------- MASCOTA ----------------
        JLabel lblMascota = new JLabel();

        try {

            ImageIcon mascota = new ImageIcon(
                    getClass().getResource("/Multimedia/utiles/mascotaKitsura/imagen/REGISTRARSE_USUARIO-PARTIDA_MINIJUEGO-TABLETA.png"));

            Image img = mascota.getImage().getScaledInstance(
                    430,
                    430,
                    Image.SCALE_SMOOTH);

            lblMascota.setIcon(new ImageIcon(img));

        } catch (Exception e) {

            lblMascota.setText("~");

        }

        lblMascota.setBounds(40, 280, 430, 430);
        panelIzquierdo.add(lblMascota);

        //---------------- PANEL DERECHO ----------------
        FondoPanelSemi panelTabla = new FondoPanelSemi(new Color(0, 0, 0, 130));
        panelTabla.setLayout(null);
        panelTabla.setBounds(700, 170, 980, 700);
        fondo.add(panelTabla);

        JLabel lblTabla = new JLabel("LISTA DE USUARIOS", JLabel.CENTER);
        lblTabla.setFont(fuente2.deriveFont(34f));
        lblTabla.setForeground(Color.WHITE);
        lblTabla.setBounds(0, 20, 980, 45);
        panelTabla.add(lblTabla);

        //---------------- TABLA ----------------
        DefaultTableModel modelo = new DefaultTableModel(
                new String[]{"ID", "NOMBRE", "CORREO", "CONTRASEÑA"}, 0) {

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }

        };

        for (int i = 0; i < 8; i++) {
            modelo.addRow(new Object[]{"", "", "", ""});
        }

        JTable tablaUsuarios = new JTable(modelo);

        tablaUsuarios.setFont(fuente1.deriveFont(18f));
        tablaUsuarios.setRowHeight(55);

        tablaUsuarios.getTableHeader().setFont(fuente2.deriveFont(20f));
        tablaUsuarios.getTableHeader().setReorderingAllowed(false);

        tablaUsuarios.getColumnModel().getColumn(0).setPreferredWidth(90);
        tablaUsuarios.getColumnModel().getColumn(1).setPreferredWidth(220);
        tablaUsuarios.getColumnModel().getColumn(2).setPreferredWidth(320);
        tablaUsuarios.getColumnModel().getColumn(3).setPreferredWidth(250);

        JScrollPane scrollTabla = new JScrollPane(tablaUsuarios);
        scrollTabla.setBounds(30, 90, 920, 580);

        panelTabla.add(scrollTabla);

        //---------------- BOTÓN VOLVER ----------------
        JButton btnVolver = new DecoracionBotones("VOLVER",
                                //ColorBase             ColorBorde              ColorLetra
                DecoracionBotones.AZUL, DecoracionBotones.GRIS, DecoracionBotones.AMARILLO, //MOUSE FUERA
                DecoracionBotones.CELESTE, DecoracionBotones.AZUL, DecoracionBotones.AZUL); //MOUSE DENTRO   

        btnVolver.setFont(fuente2.deriveFont(28f));
        btnVolver.setBounds(1550, 900, 300, 65);

        btnVolver.addActionListener(e -> {
            new UsuarioMenu();
            dispose();
                });

        fondo.add(btnVolver);

    }

    public static void main(String[] args) {
        new UsuarioMostrar();
    }

}