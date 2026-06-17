package main;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class PistasTxtAdmin extends JFrame{
    private FondoPanel fondo;
    private Font fuente1;
    private Font fuente2;
    
    public PistasTxtAdmin(){
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
        fondo = new FondoPanel("/utilidades/fondoTresK.png"); 
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

        JLabel lblTituloSeccion = new JLabel("PISTAS: TEXTO", JLabel.CENTER);
        lblTituloSeccion.setFont(fuente2.deriveFont(35f));
        lblTituloSeccion.setForeground(Color.WHITE);
        lblTituloSeccion.setBounds(90, 80, 1800, 55);
        fondo.add(lblTituloSeccion);

        JLabel lblTextoPista = new JLabel("Ingrese el texto de la pista");
        lblTextoPista.setFont(fuente1.deriveFont(25f));
        lblTextoPista.setForeground(Color.BLACK);
        lblTextoPista.setBounds(150, 180, 400, 30);
        fondo.add(lblTextoPista);

        JTextArea txtAreaPista = new JTextArea();
        txtAreaPista.setFont(fuente1.deriveFont(25f));
        txtAreaPista.setLineWrap(true);
        txtAreaPista.setWrapStyleWord(true);
        txtAreaPista.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
        
        JScrollPane scrollPaneTexto = new JScrollPane(txtAreaPista);
        scrollPaneTexto.setBounds(150, 230, 400, 550);
        fondo.add(scrollPaneTexto);

        JLabel lblInstruccionId = new JLabel("Ingrese el ID de la pista a editar o añadir");
        lblInstruccionId.setFont(fuente2.deriveFont(25f));
        lblInstruccionId.setForeground(Color.LIGHT_GRAY);
        lblInstruccionId.setBounds(630, 280, 600, 30);
        fondo.add(lblInstruccionId);

        JTextField txtIdPista = new JTextField();
        txtIdPista.setFont(new Font("Arial", Font.PLAIN, 18));
        txtIdPista.setBounds(630, 325, 550, 45);
        fondo.add(txtIdPista);

        String[] columnas = {"ID", "CONTENIDO"};
        String[][] datos = {
            {"", ""}, {"", ""}, {"", ""}, {"", ""}, {"", ""}
        };
        DefaultTableModel modelo = new DefaultTableModel(datos, columnas);
        JTable tablaPistas = new JTable(modelo);
        tablaPistas.setFont(new Font("Arial", Font.PLAIN, 16));
        tablaPistas.setRowHeight(35);
        
        tablaPistas.setPreferredScrollableViewportSize(tablaPistas.getPreferredSize());
        
        JScrollPane scrollPaneTabla = new JScrollPane(tablaPistas);
        scrollPaneTabla.setBounds(630, 410, 550, 205); 
        scrollPaneTabla.getViewport().setBackground(Color.WHITE); 
        fondo.add(scrollPaneTabla);

        JLabel staticMascotaLibro = new JLabel();
        ImageIcon iconMascota = new ImageIcon(getClass().getResource("/utilidades/mascotaUno.png")); 
        Image imgEscalada = iconMascota.getImage().getScaledInstance(600, 600, Image.SCALE_SMOOTH);
        staticMascotaLibro.setIcon(new ImageIcon(imgEscalada));
        staticMascotaLibro.setBounds(1320, 310, 600, 600);
        fondo.add(staticMascotaLibro);

        JButton btnSalir = new JButton("Volver");
        btnSalir.setFont(fuente1.deriveFont(25f));
        btnSalir.setBounds(1430, 800, 220, 55);
        btnSalir.addActionListener(e -> dispose());
        fondo.add(btnSalir);
    }

    public static void main(String[] args) {
        new PistasTxtAdmin();
    }
}
