package main.Administrador;

import java.awt.*;
import javax.swing.*;
import main.Menu.FondoPanel;

public class MenuAdmin extends JFrame{
    private FondoPanel fondo;
    private Font fuente1;
    private Font fuente2;
    // ME HACE FALTA HACER EL PANEL QUE ESTARA DEBAJO DEL BOTON ADMINISTRAR STAGES
    public MenuAdmin() {
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
        fondo = new FondoPanel("/Multimedia/utiles/fondos/interfaces/fondoDosK.png"); 
        setContentPane(fondo);      
        setTitle("Menú Administrador");
        setSize(1980, 1080); 
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH); 
        fondo.setLayout(null);       
        crearComponentes();
        setVisible(true);
    }
    
    private void crearComponentes() {
        JLabel lblTituloCentral = new JLabel("Menú", JLabel.CENTER);
        lblTituloCentral.setFont(fuente2.deriveFont(50F));
        lblTituloCentral.setForeground(Color.WHITE);
        lblTituloCentral.setBounds(90, 140, 1800, 60);
        fondo.add(lblTituloCentral);

        JButton btnUsuario = new JButton("USUARIO");
        btnUsuario.setFont(fuente1.deriveFont(25f));
        btnUsuario.setBounds(250, 320, 280, 55);
        fondo.add(btnUsuario);

        JButton btnPuntuaciones = new JButton("PUNTUACIONES");
        btnPuntuaciones.setFont(fuente1.deriveFont(25f));
        btnPuntuaciones.setBounds(250, 430, 280, 55);
        fondo.add(btnPuntuaciones);

        JButton btnVidas = new JButton("VIDAS");
        btnVidas.setFont(fuente1.deriveFont(25f));
        btnVidas.setBounds(250, 540, 280, 55);
        fondo.add(btnVidas);

        JButton btnTiempo = new JButton("TIEMPO");
        btnTiempo.setFont(fuente1.deriveFont(25f));
        btnTiempo.setBounds(250, 650, 280, 55);
        fondo.add(btnTiempo);

        JButton btnPistas = new JButton("PISTAS");
        btnPistas.setFont(fuente1.deriveFont(25f));
        btnPistas.setBounds(250, 760, 280, 55);
        fondo.add(btnPistas);

        JButton btnAdminStages = new JButton ("ADMINISTRAR STAGES");
        btnAdminStages.setFont(fuente1.deriveFont(25f));
        btnAdminStages.setBounds(840, 520, 280, 55);
        fondo.add(btnAdminStages);

        // MASCOTA 
        JLabel mascotaLampara = new JLabel();
        ImageIcon iconMascota = new ImageIcon(getClass().getResource("/Multimedia/utiles/mascotaKitsura/imagen/MENÚ-PRINCIPAL-LINTERNA.png"));
        Image imgEscalada = iconMascota.getImage().getScaledInstance(650, 650, Image.SCALE_SMOOTH);
        mascotaLampara.setIcon(new ImageIcon(imgEscalada));
        mascotaLampara.setBounds(1200, 230, 650, 650);
        fondo.add(mascotaLampara);

        // BOTÓN SALIR
        JButton btnSalir = new JButton("SALIR");
        btnSalir.setFont(fuente1.deriveFont(25f));
        btnSalir.setBounds(1400, 850, 250, 55);
        btnSalir.addActionListener(e -> dispose());
        fondo.add(btnSalir);
    }
}
