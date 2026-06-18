package main.Administrador;

import java.awt.*;
import javax.swing.*;
import main.Menu.FondoPanel;

public class AdminStages extends JFrame{
    private FondoPanel fondo;
    private Font fuente1;
    private Font fuente2;
    
    public AdminStages() {
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
        fondo = new FondoPanel("/Multimedia/utiles/fondoDosK.png"); 
        setContentPane(fondo);       
        setTitle("Administrar Stages");
        setSize(1980, 1080); 
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH); 
        fondo.setLayout(null);       
        crearComponentes();
        setVisible(true);
    }
    
    private void crearComponentes() {
        JLabel lblTituloVentana = new JLabel("Administrar Stages");
        lblTituloVentana.setFont(new Font("Arial", Font.PLAIN, 18));
        lblTituloVentana.setBounds(50, 40, 200, 30);
        fondo.add(lblTituloVentana);

        JLabel lblTituloSeccion = new JLabel("ADMINISTRAR STAGES", JLabel.CENTER);
        lblTituloSeccion.setFont(fuente2.deriveFont(45f));
        lblTituloSeccion.setForeground(Color.WHITE);
        lblTituloSeccion.setBounds(75, 150, 1800, 55);
        fondo.add(lblTituloSeccion);

        JButton btnCrearUno = new JButton("CREAR UNO");
        btnCrearUno.setFont(fuente1.deriveFont(25f));
        btnCrearUno.setBounds(350, 500, 320, 60);
        fondo.add(btnCrearUno);

        JButton btnEditarExistente = new JButton("EDITAR UNO EXISTENTE");
        btnEditarExistente.setFont(fuente1.deriveFont(25f));
        btnEditarExistente.setBounds(1270, 500, 320, 60);
        fondo.add(btnEditarExistente);
        
        JLabel staticMascotaControl = new JLabel();
        ImageIcon iconMascota = new ImageIcon(getClass().getResource("/Multimedia/utiles/mascota3.png")); 
        Image imgEscalada = iconMascota.getImage().getScaledInstance(600, 600, Image.SCALE_SMOOTH);
        staticMascotaControl.setIcon(new ImageIcon(imgEscalada));
        staticMascotaControl.setBounds(700, 290, 600, 600);
        fondo.add(staticMascotaControl);

        JButton btnSalir = new JButton("Volver");
        btnSalir.setFont(fuente1.deriveFont(30f));
        btnSalir.setBounds(1695, 950, 210, 45);
        btnSalir.addActionListener(e -> dispose());
        fondo.add(btnSalir);
    }
}
