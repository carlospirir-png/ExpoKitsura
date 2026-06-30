package main.Administrador;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import main.Menu.FondoPanel;
import main.Menu.FondoPanelSemi;
import main.Menu.DecoracionBotones;

public class PistasTxtAdmin extends JFrame {

    private FondoPanel fondo;
    private Font fuente1;
    private Font fuente2;

    public PistasTxtAdmin() {
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

        fondo = new FondoPanel("/Multimedia/utiles/fondos/interfaces/fondoTresK.png");
        setContentPane(fondo);
        setTitle("Pistas: TXT");
        setSize(1980, 1080);
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
        panelTitulo.setBounds(90, 65, 1800, 75);
        fondo.add(panelTitulo);

        JLabel lblTituloSeccion = new JLabel("PISTAS: TEXTO", JLabel.CENTER);
        lblTituloSeccion.setFont(fuente2.deriveFont(45f));
        lblTituloSeccion.setForeground(Color.WHITE);
        lblTituloSeccion.setBounds(0, 0, 1800, 75);
        panelTitulo.add(lblTituloSeccion);

        //---------------- PANEL IZQUIERDO (TEXTO PISTA) ----------------
        FondoPanelSemi panelIzquierdo = new FondoPanelSemi(new Color(0, 0, 0, 130));
        panelIzquierdo.setLayout(null);
        panelIzquierdo.setBounds(100, 170, 500, 620);
        fondo.add(panelIzquierdo);

        JLabel lblTextoPista = new JLabel("Ingrese el texto de la pista");
        lblTextoPista.setFont(fuente2.deriveFont(28f));
        lblTextoPista.setForeground(Color.WHITE);
        lblTextoPista.setBounds(30, 30, 440, 40);
        panelIzquierdo.add(lblTextoPista);

        JTextArea txtAreaPista = new JTextArea();
        txtAreaPista.setFont(fuente1.deriveFont(36f));
        txtAreaPista.setLineWrap(true);
        txtAreaPista.setWrapStyleWord(true);
        txtAreaPista.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));

        JScrollPane scrollPaneTexto = new JScrollPane(txtAreaPista);
        scrollPaneTexto.setBounds(30, 85, 440, 440);
        panelIzquierdo.add(scrollPaneTexto);

        //---------------- BOTÓN EDITAR ----------------
<<<<<<< HEAD
        JButton btnEditar = new DecoracionBotones("EDITAR", "#FC767D", "#da4d58", "#da4d58");
=======
        JButton btnEditar = new DecoracionBotones("EDITAR",
                                //ColorBase             ColorBorde              ColorLetra
                DecoracionBotones.AZUL, DecoracionBotones.GRIS, DecoracionBotones.AMARILLO, //MOUSE FUERA
                DecoracionBotones.CELESTE, DecoracionBotones.AZUL, DecoracionBotones.AZUL); //MOUSE DENTRO   

>>>>>>> 8d29a9e75b19afa2cfd4b44a742a0f73e91fab9b
        btnEditar.setFont(fuente2.deriveFont(26f));
        btnEditar.setBounds(130, 550, 240, 55);
        btnEditar.addActionListener(e -> {
            // Acción de editar pista
        });
        panelIzquierdo.add(btnEditar);

        //---------------- PANEL DERECHO (ID + TABLA) ----------------
        FondoPanelSemi panelDerecho = new FondoPanelSemi(new Color(0, 0, 0, 130));
        panelDerecho.setLayout(null);
        panelDerecho.setBounds(680, 170, 880, 620);
        fondo.add(panelDerecho);

        JLabel lblInstruccionId = new JLabel("Ingrese el ID de la pista a editar o añadir");
        lblInstruccionId.setFont(fuente2.deriveFont(26f));
        lblInstruccionId.setForeground(Color.WHITE);
        lblInstruccionId.setBounds(30, 30, 820, 35);
        panelDerecho.add(lblInstruccionId);

        JTextField txtIdPista = new JTextField();
        txtIdPista.setFont(fuente1.deriveFont(36f));
        txtIdPista.setBounds(30, 75, 820, 55);
        panelDerecho.add(txtIdPista);

        //---------------- TABLA (ID 1/3, CONTENIDO 2/3) ----------------
        DefaultTableModel modelo = new DefaultTableModel(
                new String[]{"ID", "CONTENIDO"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        for (int i = 0; i < 7; i++) {
            modelo.addRow(new Object[]{"", ""});
        }

        JTable tablaPistas = new JTable(modelo);
        tablaPistas.setFont(fuente1.deriveFont(18f));
        tablaPistas.setRowHeight(50);
        tablaPistas.getTableHeader().setFont(fuente2.deriveFont(20f));
        tablaPistas.getTableHeader().setReorderingAllowed(false);

        // ID = 1/3, CONTENIDO = 2/3
        tablaPistas.getColumnModel().getColumn(0).setPreferredWidth(200);
        tablaPistas.getColumnModel().getColumn(1).setPreferredWidth(560);

        JScrollPane scrollPaneTabla = new JScrollPane(tablaPistas);
        scrollPaneTabla.setBounds(30, 155, 820, 440);
        panelDerecho.add(scrollPaneTabla);

        //---------------- MASCOTA ----------------
        JLabel staticMascotaLibro = new JLabel();
        try {
            ImageIcon iconMascota = new ImageIcon(getClass().getResource("/Multimedia/utiles/mascotaKitsura/imagen/REGLAS-PISTA_TEXTUAL.png"));
            Image imgEscalada = iconMascota.getImage().getScaledInstance(550, 550, Image.SCALE_SMOOTH);
            staticMascotaLibro.setIcon(new ImageIcon(imgEscalada));
        } catch (Exception e) {
            staticMascotaLibro.setText("~");
        }
        staticMascotaLibro.setBounds(1480, 250, 550, 550);
        fondo.add(staticMascotaLibro);

        //---------------- BOTÓN VOLVER ----------------
        JButton btnVolver = new DecoracionBotones("VOLVER",
                                //ColorBase             ColorBorde              ColorLetra
                DecoracionBotones.AZUL, DecoracionBotones.GRIS, DecoracionBotones.AMARILLO, //MOUSE FUERA
                DecoracionBotones.CELESTE, DecoracionBotones.AZUL, DecoracionBotones.AZUL); //MOUSE DENTRO   

        btnVolver.setFont(fuente2.deriveFont(28f));
        btnVolver.setBounds(1470, 870, 300, 65);
        btnVolver.addActionListener(e -> 
        {
            new PistasMenu();
            dispose();
                });
        fondo.add(btnVolver);
    }
    
    public static void main (String[] args){
        new PistasTxtAdmin();
    }
}