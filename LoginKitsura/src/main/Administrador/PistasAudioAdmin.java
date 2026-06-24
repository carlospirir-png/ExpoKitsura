package main.Administrador;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import main.Menu.FondoPanel;

public class PistasAudioAdmin extends JFrame {
    private FondoPanel fondo;
    private Font fuente1;
    private Font fuente2;
    
    public PistasAudioAdmin() {
        try{
            // LettersForLearners
            fuente1 = Font.createFont(
            Font.TRUETYPE_FONT,
            getClass().getResourceAsStream("/fuentes/LettersForLearners.ttf"));
            // KGPerfectPenmanship
            fuente2 = Font.createFont(
            Font.TRUETYPE_FONT,
            getClass().getResourceAsStream("/fuentes/KGPerfectPenmanship.ttf"));
            
        } catch (Exception e){
            e.printStackTrace();            
        fuente1 = new Font("Arial", Font.PLAIN,20);
        fuente2 = new Font("Arial", Font.PLAIN,20);
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
        
        JLabel lblTituloSeccion = new JLabel("PISTAS: AUDIO", JLabel.CENTER);
        lblTituloSeccion.setFont(fuente2.deriveFont(35f));
        lblTituloSeccion.setForeground(Color.WHITE);
        lblTituloSeccion.setBounds(90, 80, 1800, 55);
        fondo.add(lblTituloSeccion);

        JLabel lblSubirAudio = new JLabel("Suba el Audio de la pista");
        lblSubirAudio.setFont(fuente1.deriveFont(35f));
        lblSubirAudio.setForeground(Color.white);
        lblSubirAudio.setBounds(150, 310, 400, 30);
        fondo.add(lblSubirAudio);

        JPanel panelOndaAudio = new JPanel();
        panelOndaAudio.setBackground(Color.LIGHT_GRAY);
        panelOndaAudio.setBounds(150, 370, 340, 70);
        panelOndaAudio.setLayout(new BorderLayout());
         
        JLabel lblIconoOnda = new JLabel("🎤 ||||||ıııııı||||||ıııııı||||||", JLabel.CENTER);
        lblIconoOnda.setFont(new Font("Arial", Font.BOLD, 20));
        lblIconoOnda.setForeground(Color.WHITE);
        panelOndaAudio.add(lblIconoOnda, BorderLayout.CENTER);
        fondo.add(panelOndaAudio);

        JButton btnCargar = new JButton("CARGAR");
        btnCargar.setFont(fuente1.deriveFont(25f));
        btnCargar.setBounds(200, 480, 220, 55);
        fondo.add(btnCargar);

        JLabel lblInstruccionId = new JLabel("Ingrese el ID de la pista a editar o añadir");
        lblInstruccionId.setFont(fuente2.deriveFont(20f));
        lblInstruccionId.setForeground(Color.LIGHT_GRAY);
        lblInstruccionId.setBounds(720, 260, 600, 30);
        fondo.add(lblInstruccionId);

        JTextField txtIdPista = new JTextField();
        txtIdPista.setFont(fuente1.deriveFont(25f));
        txtIdPista.setBounds(720, 310, 550, 45);
        fondo.add(txtIdPista);

        // --- Configuración de la Tabla Ajustada ---
        String[] columnas = {"ID", "Audio"};
        String[][] datos = {
            {"", ""}, {"", ""}, {"", ""}, {"", ""}, {"", ""}
        };
        DefaultTableModel modelo = new DefaultTableModel(datos, columnas);
        JTable tablaPistas = new JTable(modelo);
        tablaPistas.setFont(fuente1.deriveFont(16f));
        tablaPistas.setRowHeight(35);
        tablaPistas.setPreferredScrollableViewportSize(tablaPistas.getPreferredSize());
         
        JScrollPane scrollPaneTabla = new JScrollPane(tablaPistas);
        scrollPaneTabla.setBounds(720, 460, 550, 205); 
        scrollPaneTabla.getViewport().setBackground(Color.WHITE); 
        fondo.add(scrollPaneTabla);

        JLabel staticMascotaLibro = new JLabel();
        ImageIcon iconMascota = new ImageIcon(getClass().getResource("/Multimedia/utiles/mascotaKitsura/imagen/REGLAS-PISTAS_TEXTUAL.png")); 
        Image imgEscalada = iconMascota.getImage().getScaledInstance(550, 550, Image.SCALE_SMOOTH);
        staticMascotaLibro.setIcon(new ImageIcon(imgEscalada));
        staticMascotaLibro.setBounds(1320, 320, 550, 550);
        fondo.add(staticMascotaLibro);

        JButton btnSalir = new JButton("Volver");
        btnSalir.setFont(fuente1.deriveFont(25f));
        btnSalir.setBounds(1450, 850, 270, 45);
        btnSalir.addActionListener(e -> dispose());
        fondo.add(btnSalir);
    }
}
