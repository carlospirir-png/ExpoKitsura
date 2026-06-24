package main.Administrador;

import java.awt.*;
import javax.swing.*;
import main.Menu.FondoPanel;

public class PuntuacionesAdmin extends JFrame{
    private FondoPanel fondo;
    private Font fuente1;
    private Font fuente2;
    
    public PuntuacionesAdmin() {
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
        
        setTitle("Puntuaciones");
        setSize(1980, 1080); 
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH); 
        fondo.setLayout(null);
        
        crearComponentes();
        setVisible(true);
    }
    
    private void crearComponentes() {
        JLabel lblTituloVentana = new JLabel("Puntuaciones");
        lblTituloVentana.setFont(new Font("Arial", Font.PLAIN, 18));
        lblTituloVentana.setBounds(50, 40, 200, 30);
        fondo.add(lblTituloVentana);

        JPanel recuadroPuntos = new JPanel();
        recuadroPuntos.setBackground(new Color(255, 255, 255, 180)); 
        recuadroPuntos.setOpaque(false);
        recuadroPuntos.setLayout(null);
        recuadroPuntos.setBounds(150, 180, 950, 650); 
        fondo.add(recuadroPuntos);

        JLabel lblTituloSeccion = new JLabel("PUNTUACIÓN", JLabel.CENTER);
        lblTituloSeccion.setFont(fuente2.deriveFont(30f));
        lblTituloSeccion.setForeground(Color.WHITE);
        lblTituloSeccion.setBounds(50, 40, 850, 55);
        recuadroPuntos.add(lblTituloSeccion);

        JLabel lblPuntosActuales = new JLabel("Puntuación actual por este nivel: ****");
        lblPuntosActuales.setFont(fuente1.deriveFont(25f));
        lblPuntosActuales.setForeground(Color.WHITE);
        lblPuntosActuales.setBounds(80, 150, 800, 30);
        recuadroPuntos.add(lblPuntosActuales);

        JLabel lblInstruccion = new JLabel("Ingrese la cantidad de puntuación que se dará por ese nivel:");
        lblInstruccion.setFont(fuente2.deriveFont(25f));
        lblInstruccion.setForeground(Color.WHITE);
        lblInstruccion.setBounds(80, 260, 800, 30);
        recuadroPuntos.add(lblInstruccion);

        JTextField txtNuevaPuntuacion = new JTextField();
        txtNuevaPuntuacion.setFont(fuente1.deriveFont(25f));
        txtNuevaPuntuacion.setBounds(80, 310, 790, 45);
        recuadroPuntos.add(txtNuevaPuntuacion);

        // Botón EDITAR
        JButton btnEditar = new JButton("EDITAR");
        btnEditar.setFont(fuente1.deriveFont(25f));
        btnEditar.setBounds(365, 390, 220, 55);
        recuadroPuntos.add(btnEditar);

        JLabel lblModificando = new JLabel("Estás modificando:");
        lblModificando.setFont(fuente2.deriveFont(25f));
        lblModificando.setForeground(Color.WHITE);
        lblModificando.setBounds(80, 480, 300, 25);
        recuadroPuntos.add(lblModificando);

        JLabel lblInfoJuego = new JLabel("Minijuego: **** Categoría: *** Nivel: ****");
        lblInfoJuego.setFont(fuente1.deriveFont(25f));
        lblInfoJuego.setForeground(Color.WHITE);
        lblInfoJuego.setBounds(80, 515, 800, 25);
        recuadroPuntos.add(lblInfoJuego);

            JLabel mascotaLapiz = new JLabel();
            ImageIcon iconMascota = new ImageIcon(getClass().getResource("/Multimedia/utiles/mascotaKitsura/imagen/INICIAR_SESIÓN-REGISTRARSE_INVITADO-EDITAR_CONTRASENA.png"));
            Image imgEscalada = iconMascota.getImage().getScaledInstance(500, 500, Image.SCALE_SMOOTH);
            mascotaLapiz.setIcon(new ImageIcon(imgEscalada));
            mascotaLapiz.setBounds(1250, 220, 500, 500);
            fondo.add(mascotaLapiz);
            
        // BOTÓN SALIR
        JButton btnSalir = new JButton("SALIR");
        btnSalir.setFont(fuente1.deriveFont(25f));
        btnSalir.setBounds(1390, 750, 220, 55);
        btnSalir.addActionListener(e -> dispose());
        fondo.add(btnSalir);
    }
    
   
}
