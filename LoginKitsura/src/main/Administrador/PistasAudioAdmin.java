package main.Administrador;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import main.Menu.FondoPanel;
import main.Menu.FondoPanelSemi;
import main.Menu.DecoracionBotones;

public class PistasAudioAdmin extends JFrame {
    private FondoPanel fondo;
    private Font fuente1;
    private Font fuente2;

    public PistasAudioAdmin() {
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
        setTitle("Pistas: Audio");
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

        JLabel lblTituloSeccion = new JLabel("PISTAS: AUDIO", JLabel.CENTER);
        lblTituloSeccion.setFont(fuente2.deriveFont(45f));
        lblTituloSeccion.setForeground(Color.WHITE);
        lblTituloSeccion.setBounds(0, 0, 1800, 75);
        panelTitulo.add(lblTituloSeccion);

        //---------------- PANEL IZQUIERDO (AUDIO) ----------------
        FondoPanelSemi panelIzquierdo = new FondoPanelSemi(new Color(0, 0, 0, 130));
        panelIzquierdo.setLayout(null);
        panelIzquierdo.setBounds(100, 200, 500, 420);
        fondo.add(panelIzquierdo);

        JLabel lblSubirAudio = new JLabel("Suba el Audio de la pista");
        lblSubirAudio.setFont(fuente2.deriveFont(28f));
        lblSubirAudio.setForeground(Color.WHITE);
        lblSubirAudio.setBounds(30, 40, 440, 40);
        panelIzquierdo.add(lblSubirAudio);

        JPanel panelOndaAudio = new JPanel();
        panelOndaAudio.setBackground(Color.DARK_GRAY);
        panelOndaAudio.setBounds(30, 100, 440, 90);
        panelOndaAudio.setLayout(new BorderLayout());

        JLabel lblIconoOnda = new JLabel(" ||||||ıııııı||||||ıııııı||||||", JLabel.CENTER);
        lblIconoOnda.setFont(new Font("Arial", Font.BOLD, 22));
        lblIconoOnda.setForeground(Color.WHITE);
        panelOndaAudio.add(lblIconoOnda, BorderLayout.CENTER);
        panelIzquierdo.add(panelOndaAudio);

        JButton btnCargar = new DecoracionBotones("CARGAR", "#FC767D", "#da4d58", "#da4d58");
        btnCargar.setFont(fuente2.deriveFont(26f));
        btnCargar.setBounds(130, 230, 240, 65);
        panelIzquierdo.add(btnCargar);

        //---------------- PANEL DERECHO (ID + TABLA) ----------------
        FondoPanelSemi panelDerecho = new FondoPanelSemi(new Color(0, 0, 0, 130));
        panelDerecho.setLayout(null);
        panelDerecho.setBounds(680, 170, 880, 560);
        fondo.add(panelDerecho);

        JLabel lblInstruccionId = new JLabel("Ingrese el ID de la pista a editar o añadir");
        lblInstruccionId.setFont(fuente2.deriveFont(22f));
        lblInstruccionId.setForeground(Color.WHITE);
        lblInstruccionId.setBounds(30, 30, 820, 35);
        panelDerecho.add(lblInstruccionId);

        JTextField txtIdPista = new JTextField();
        txtIdPista.setFont(fuente1.deriveFont(26f));
        txtIdPista.setBounds(30, 75, 820, 55);
        panelDerecho.add(txtIdPista);

        //---------------- TABLA (ID 1/3, Audio 2/3) ----------------
        DefaultTableModel modelo = new DefaultTableModel(
                new String[]{"ID", "Audio"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        for (int i = 0; i < 5; i++) {
            modelo.addRow(new Object[]{"", ""});
        }

        JTable tablaPistas = new JTable(modelo);
        tablaPistas.setFont(fuente1.deriveFont(18f));
        tablaPistas.setRowHeight(42);
        tablaPistas.getTableHeader().setFont(fuente2.deriveFont(20f));
        tablaPistas.getTableHeader().setReorderingAllowed(false);

        // ID = 1/3, Audio = 2/3
        tablaPistas.getColumnModel().getColumn(0).setPreferredWidth(200);
        tablaPistas.getColumnModel().getColumn(1).setPreferredWidth(560);

        JScrollPane scrollPaneTabla = new JScrollPane(tablaPistas);
        scrollPaneTabla.setBounds(30, 155, 820, 370);
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
        staticMascotaLibro.setBounds(1490, 220, 550, 550);
        fondo.add(staticMascotaLibro);

        //---------------- BOTÓN VOLVER ----------------
        JButton btnVolver = new DecoracionBotones("VOLVER");
        btnVolver.setFont(fuente2.deriveFont(28f));
        btnVolver.setBounds(1550, 860, 300, 65);
        btnVolver.addActionListener(e ->{
            new PistasMenu();
            dispose();
        });
        fondo.add(btnVolver);
    }
    
    
    public static void main (String[] args){
        new PistasAudioAdmin();
    }
}