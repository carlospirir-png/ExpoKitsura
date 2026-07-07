package main.Administrador;

import java.awt.*;
import javax.swing.*;
import main.Menu.FondoPanel;
import main.Menu.DecoracionBotones;

public class PistasMenu extends JFrame{
    private FondoPanel fondo;
    private Font fuente1;
    private Font fuente2;
    
    private DatosConfiguracion datos;
    
    public PistasMenu(DatosConfiguracion datos) {
        
        this.datos = datos;
        
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
        
        setTitle("Pistas");
        setSize(1980, 1080); 
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH); 
        fondo.setLayout(null);
        
        crearComponentes();
        setVisible(true);
    }
    
    private void crearComponentes() {
        // --- PANEL SEMITRANSPARENTE ---
        // Creamos un panel anónimo sobreescribiendo paintComponent para un renderizado limpio
        JPanel panelTitulo = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                // Color negro con opacidad (0 = transparente, 255 = opaco)
                g2d.setColor(new Color(0, 0, 0, 130)); 
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
                g2d.dispose();
            }
        };
        panelTitulo.setOpaque(false);
        panelTitulo.setLayout(new BorderLayout()); // Usamos BorderLayout para centrar el label fácilmente
        panelTitulo.setBounds(90, 120, 1800, 80); // Ajustamos tamaño un poco alto
        fondo.add(panelTitulo);

        // --- LABEL DE PISTAS ---
        JLabel lblPistas = new JLabel("PISTAS", JLabel.CENTER);
        lblPistas.setFont(fuente2.deriveFont(35f));
        lblPistas.setForeground(Color.WHITE);
        
        // Ahora agregamos el label DENTRO del panel semitransparente, no directamente en el fondo
        panelTitulo.add(lblPistas, BorderLayout.CENTER);

        // --- RESTO DE COMPONENTES ---
        JButton btnTexto = new DecoracionBotones("TEXTO",
                DecoracionBotones.ROSA, DecoracionBotones.ROJO, DecoracionBotones.AMARILLO, 
                DecoracionBotones.ROJO, DecoracionBotones.ROSA, DecoracionBotones.ROSA); 
        btnTexto.setFont(fuente1.deriveFont(45f));
        btnTexto.setBounds(400, 420, 415, 75);
                btnTexto.addActionListener(e ->{
                     dispose();    
                    new PistasTxtAdmin(datos);
                });
        fondo.add(btnTexto);

        JButton btnAudio = new DecoracionBotones("AUDIO",
                DecoracionBotones.ROSA, DecoracionBotones.ROJO, DecoracionBotones.AMARILLO, 
                DecoracionBotones.ROJO, DecoracionBotones.ROSA, DecoracionBotones.ROSA); 
        btnAudio.setFont(fuente1.deriveFont(45f));
        btnAudio.setBounds(400, 650, 415, 75);
                        btnAudio.addActionListener(e ->{
                        dispose();
                        new PistasAudioAdmin(datos);
                       
                        });
        fondo.add(btnAudio);

        JLabel staticMascotaTablet = new JLabel();
        ImageIcon iconMascota = new ImageIcon(getClass().getResource("/Multimedia/utiles/mascotaKitsura/imagen/MENÚ-PRINCIPAL-LINTERNA.png")); 
        Image imgEscalada = iconMascota.getImage().getScaledInstance(650, 650, Image.SCALE_SMOOTH);
        staticMascotaTablet.setIcon(new ImageIcon(imgEscalada));
        staticMascotaTablet.setBounds(1100, 280, 650, 650);
        fondo.add(staticMascotaTablet);
        
        JButton btnVolver = new DecoracionBotones("VOLVER",
                DecoracionBotones.AZUL, DecoracionBotones.GRIS, DecoracionBotones.AMARILLO, 
                DecoracionBotones.CELESTE, DecoracionBotones.AZUL, DecoracionBotones.AZUL);   
        btnVolver.setFont(fuente2.deriveFont(20f));
        btnVolver.setBounds(1640, 935, 220, 55);
        btnVolver.addActionListener(e -> 
                
        {
            new MenuAdmin(datos);
            dispose();
        });
        fondo.add(btnVolver);
    }
    
}