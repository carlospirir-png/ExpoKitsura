package main.Usuario;

import java.awt.*;
import javax.swing.*;
import main.Menu.FondoPanel;

public class PistasAudio extends JFrame {
    private FondoPanel fondo;
    private Font fuente1;
    private Font fuente2;
    
    public PistasAudio() {
        try{
            // LettersForLearners
            fuente1 = Font.createFont(
            Font.TRUETYPE_FONT,
            getClass().getResourceAsStream("/fuentes/LettersForLearners.ttf"));
            // KGPerfectPenmanship
            fuente2 = Font.createFont(
            Font.TRUETYPE_FONT,
            getClass().getResourceAsStream("/fuentes/KGPerfectPenmanship.ttf"));            
        } catch(Exception e){
            e.printStackTrace();            
        fuente1 = new Font("Arial", Font.PLAIN,20);
        fuente2 = new Font("Arial", Font.PLAIN,20);
        }
        fondo = new FondoPanel("/Multimedia/utiles/fondos/interfaces/fondoTresK.png"); 
        setContentPane(fondo);
        setUndecorated(true);     
        setSize(600, 450); 
        setLocationRelativeTo(null); 
        setDefaultCloseOperation(DISPOSE_ON_CLOSE); 
        fondo.setLayout(null);  
        crearComponentes();
        setVisible(true);
    }
    
    private void crearComponentes() {
    //---------------- BARRA SUPERIOR ----------------
    JPanel barra = new JPanel();
    barra.setLayout(null);
    barra.setBackground(Color.WHITE);
    barra.setBounds(0, 0, 600, 40);
    fondo.add(barra);

    JButton btnCerrar = new JButton("X");
        btnCerrar.setFont(fuente1.deriveFont(15f));
        btnCerrar.setBounds(520, 5, 60, 30);
        btnCerrar.setFocusable(false);
        btnCerrar.setBorderPainted(false);
        btnCerrar.setBackground(new Color(245, 245, 245));
        btnCerrar.setForeground(new Color(180, 50, 50));
        btnCerrar.addActionListener(e -> dispose());
        barra.add(btnCerrar);

    JSeparator linea = new JSeparator();
    linea.setBounds(0, 39, 600, 1);
    barra.add(linea);

    //---------------- RECUADRO AUDIO ----------------
    JPanel recuadroAudio = new JPanel();
    recuadroAudio.setLayout(null);
    recuadroAudio.setBackground(Color.WHITE);
    recuadroAudio.setBounds(40, 130, 340, 120);
    fondo.add(recuadroAudio);

    JLabel lblTiempoInicio = new JLabel("1:46");
    lblTiempoInicio.setFont(fuente2.deriveFont(16f));
    lblTiempoInicio.setBounds(20, 35, 40, 25);
    recuadroAudio.add(lblTiempoInicio);

    JPanel barraProgreso = new JPanel();
    barraProgreso.setBackground(Color.LIGHT_GRAY);
    barraProgreso.setBounds(65, 43, 200, 10);
    recuadroAudio.add(barraProgreso);

    JLabel lblTiempoFin = new JLabel("3:32");
    lblTiempoFin.setFont(fuente2.deriveFont(16f));
    lblTiempoFin.setBounds(275, 35, 40, 25);
    recuadroAudio.add(lblTiempoFin);

    JLabel lblIdAudio = new JLabel("M1-D1-N1", JLabel.CENTER);
    lblIdAudio.setFont(fuente2.deriveFont(22f));
    lblIdAudio.setBounds(20, 75, 300, 30);
    recuadroAudio.add(lblIdAudio);

    //---------------- BOTON SALIR ----------------
    JButton btnSalir = new JButton("Salir");
    btnSalir.setFont(fuente1.deriveFont(20f));
    btnSalir.setBounds(40, 320, 150, 45);
    btnSalir.addActionListener(e -> dispose());
    fondo.add(btnSalir);

    //---------------- BOTON REPETIR ----------------
    JButton btnRepetir = new JButton("Repetir");
    btnRepetir.setFont(fuente1.deriveFont(20f));
    btnRepetir.setBounds(230, 320, 150, 45);
    fondo.add(btnRepetir);

    //---------------- MASCOTA ----------------
    JLabel mascotaAudifonos = new JLabel();
    ImageIcon iconMascota = new ImageIcon(getClass().getResource("/Multimedia/utiles/mascotaKitsura/imagen/REGISTRARSE_USUARIO-PARTIDA_MINIJUEGO-TABLETA.png"));
    Image imgEscalada =iconMascota.getImage().getScaledInstance(300, 300, Image.SCALE_SMOOTH);
    mascotaAudifonos.setIcon(new ImageIcon(imgEscalada));
    mascotaAudifonos.setBounds(365, 140, 300, 300);

    fondo.add(mascotaAudifonos);
}
}
