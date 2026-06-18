package main;

import java.awt.*;
import javax.swing.*;

public class TiempoAdmin extends JFrame{
    private FondoPanel fondo;
    private Font fuente1;
    private Font fuente2;
    
    public TiempoAdmin() {
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
        setTitle("Tiempo");
        setSize(1980, 1080); 
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH); 
        fondo.setLayout(null);        
        crearComponentes();
        setVisible(true);
    }
    
    private void crearComponentes() {
        JLabel lblTituloVentana = new JLabel("Tiempo");
        lblTituloVentana.setFont(new Font("Arial", Font.PLAIN, 18));
        lblTituloVentana.setBounds(50, 40, 200, 30);
        fondo.add(lblTituloVentana);

        JLabel lblTituloSeccion = new JLabel("Tiempo", JLabel.CENTER);
        lblTituloSeccion.setFont(fuente2.deriveFont(35f));
        lblTituloSeccion.setForeground(Color.WHITE);
        lblTituloSeccion.setBounds(150, 220, 850, 55);
        fondo.add(lblTituloSeccion);

        JLabel lblTiempoActual = new JLabel("Cantidad de tiempo actual por este nivel: ****");
        lblTiempoActual.setFont(fuente1.deriveFont(25f));
        lblTiempoActual.setForeground(Color.WHITE);
        lblTiempoActual.setBounds(230, 330, 800, 30);
        fondo.add(lblTiempoActual);

        JLabel lblInstruccion = new JLabel("Ingrese la cantidad de tiempo que se dará por ese nivel:");
        lblInstruccion.setFont(fuente2.deriveFont(25f));
        lblInstruccion.setForeground(Color.WHITE);
        lblInstruccion.setBounds(230, 440, 800, 30);
        fondo.add(lblInstruccion);

        JTextField txtNuevoTiempo = new JTextField("YYYY-MM-DDTHH:MM:SSZ");
        txtNuevoTiempo.setFont(fuente1.deriveFont(25f));
        txtNuevoTiempo.setBounds(230, 490, 790, 45);
        fondo.add(txtNuevoTiempo);

        JButton btnEditar = new JButton("EDITAR");
        btnEditar.setFont(fuente1.deriveFont(25f));
        btnEditar.setBounds(515, 570, 220, 55);
        fondo.add(btnEditar);

        JLabel lblModificando = new JLabel("Estás modificando:");
        lblModificando.setFont(fuente2.deriveFont(25f));
        lblModificando.setForeground(Color.WHITE);
        lblModificando.setBounds(230, 720, 300, 25);
        fondo.add(lblModificando);

        JLabel lblInfoJuego = new JLabel("Minijuego: **** | Categoría: *** | Nivel: ****");
        lblInfoJuego.setFont(fuente1.deriveFont(25f));
        lblInfoJuego.setForeground(Color.WHITE);
        lblInfoJuego.setBounds(230, 755, 800, 25);
        fondo.add(lblInfoJuego);
        
        JLabel staticMascotaLapiz = new JLabel();
        ImageIcon iconMascota = new ImageIcon(getClass().getResource("/utilidades/mascotaUno.png"));
        Image imgEscalada = iconMascota.getImage().getScaledInstance(600, 600, Image.SCALE_SMOOTH);
        staticMascotaLapiz.setIcon(new ImageIcon(imgEscalada));
        staticMascotaLapiz.setBounds(1250, 220, 600, 600);
        fondo.add(staticMascotaLapiz);

        JButton btnSalir = new JButton("SALIR");
        btnSalir.setFont(fuente1.deriveFont(25f));
        btnSalir.setBounds(1390, 800, 280, 45);
        btnSalir.addActionListener(e -> dispose());
        fondo.add(btnSalir);
    }

    public static void main(String[] args) {
        new TiempoAdmin();
    }
}
