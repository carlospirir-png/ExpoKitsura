package main.Administrador;

import java.awt.*;
import javax.swing.*;
import main.Menu.FondoPanel;
import main.Menu.FondoPanelSemi; 
import main.Menu.DecoracionBotones; 

public class M1_crearNuevo extends JFrame {
    private FondoPanel fondo;
    private Font fuente1;
    private Font fuente2;
    
    // Declaramos los botones personalizados
    private DecoracionBotones btnCargarColor;
    private DecoracionBotones btnCargarSombra;
    private DecoracionBotones btnSalir;
    
    public M1_crearNuevo() {
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
        setTitle("M1-Crear nuevo");
        setSize(1980, 1080); 
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH); 
        fondo.setLayout(null);       
        crearComponentes();
        setVisible(true);
    }
    
    private void crearComponentes() {
        FondoPanelSemi panelTitulo = new FondoPanelSemi(new Color(0, 0, 0, 140)); 
        panelTitulo.setBounds(50, 135, 530, 60); 
        panelTitulo.setLayout(null);
        fondo.add(panelTitulo);

        // --- COMPONENTES FUERA DEL PANEL ---
        JLabel lblTituloSeccion = new JLabel("CREAR NUEVO (MINIJUEGO 1)");
        lblTituloSeccion.setFont(fuente2.deriveFont(35f));
        lblTituloSeccion.setForeground(Color.decode("#82D3E0"));
        lblTituloSeccion.setBounds(20, 5, 500, 50);
        panelTitulo.add(lblTituloSeccion);

        JLabel staticMascotaTablet = new JLabel();
        ImageIcon iconMascota = new ImageIcon(getClass().getResource("/Multimedia/utiles/mascotaKitsura/imagen/REGISTRARSE_USUARIO-PARTIDA_MINIJUEGO-TABLETA.png")); 
        Image imgEscalada = iconMascota.getImage().getScaledInstance(600, 600, Image.SCALE_SMOOTH);
        staticMascotaTablet.setIcon(new ImageIcon(imgEscalada));
        staticMascotaTablet.setBounds(20, 240, 600, 600);
        fondo.add(staticMascotaTablet);

        JButton btnSiguiente = new JButton("SIGUIENTE");
        btnSiguiente.setFont(fuente1.deriveFont(20f));
        btnSiguiente.setBounds(150, 810, 240, 40);
        fondo.add(btnSiguiente);

        // --- EL PANEL SEMI-TRANSPARENTE ---
        FondoPanelSemi panelFormulario = new FondoPanelSemi(new Color(0, 0, 0, 120));
        panelFormulario.setBounds(660, 130, 1100, 660);
        panelFormulario.setLayout(null);
        fondo.add(panelFormulario);
        
        JLabel lblCargueColor = new JLabel("Cargue la Imagen a color");
        lblCargueColor.setFont(fuente2.deriveFont(22f));
        lblCargueColor.setForeground(Color.WHITE);
        lblCargueColor.setBounds(40, 45, 400, 30); 
        panelFormulario.add(lblCargueColor);

        JPanel areaColor = new JPanel();
        areaColor.setBackground(Color.LIGHT_GRAY);
        areaColor.setBounds(40, 80, 420, 240);
        panelFormulario.add(areaColor);

        btnCargarColor = new DecoracionBotones("CARGAR", "#FC767D", "#da4d58", "#da4d58");
        btnCargarColor.setFont(fuente1.deriveFont(25f));
        btnCargarColor.setForeground(Color.WHITE);
        btnCargarColor.setBounds(130, 340, 255, 45); 
        panelFormulario.add(btnCargarColor);

        JLabel lblCargueSombra = new JLabel("Cargue la sombra:");
        lblCargueSombra.setFont(fuente2.deriveFont(22f));
        lblCargueSombra.setForeground(Color.WHITE);
        lblCargueSombra.setBounds(590, 30, 400, 30); 
        panelFormulario.add(lblCargueSombra);

        JPanel areaSombra = new JPanel();
        areaSombra.setBackground(Color.LIGHT_GRAY);
        areaSombra.setBounds(590, 80, 420, 240); 
        panelFormulario.add(areaSombra);

        btnCargarSombra = new DecoracionBotones("CARGAR", "#FC767D", "#da4d58", "#da4d58");
        btnCargarSombra.setFont(fuente1.deriveFont(25f));
        btnCargarSombra.setForeground(Color.WHITE);
        btnCargarSombra.setBounds(680, 340, 255, 45); 
        panelFormulario.add(btnCargarSombra);

        JLabel lblCorrecta1 = new JLabel("Ingrese la respuesta correcta del nivel:");
        lblCorrecta1.setFont(fuente1.deriveFont(32f));
        lblCorrecta1.setForeground(Color.WHITE);
        lblCorrecta1.setBounds(40, 430, 450, 25);
        panelFormulario.add(lblCorrecta1);

        JTextField txtCorrecta1 = new JTextField();
        txtCorrecta1.setFont(fuente1.deriveFont(22f));
        txtCorrecta1.setBounds(40, 465, 420, 40); 
        panelFormulario.add(txtCorrecta1);

        JLabel lblIncorrecta1 = new JLabel("Ingrese la respuesta incorrecta del nivel:");
        lblIncorrecta1.setFont(fuente1.deriveFont(32f));
        lblIncorrecta1.setForeground(Color.WHITE);
        lblIncorrecta1.setBounds(590, 430, 450, 25);
        panelFormulario.add(lblIncorrecta1);

        JTextField txtIncorrecta1 = new JTextField();
        txtIncorrecta1.setFont(fuente1.deriveFont(22f));
        txtIncorrecta1.setBounds(590, 465, 420, 40); 
        panelFormulario.add(txtIncorrecta1);

        JLabel lblCorrecta2 = new JLabel("Ingrese la respuesta correcta del nivel:");
        lblCorrecta2.setFont(fuente1.deriveFont(32f));
        lblCorrecta2.setForeground(Color.WHITE);
        lblCorrecta2.setBounds(40, 550, 450, 25);
        panelFormulario.add(lblCorrecta2);

        JTextField txtCorrecta2 = new JTextField();
        txtCorrecta2.setFont(fuente1.deriveFont(22f));
        txtCorrecta2.setBounds(40, 585, 420, 40); 
        panelFormulario.add(txtCorrecta2);

        JLabel lblIncorrecta2 = new JLabel("Ingrese la respuesta incorrecta del nivel:");
        lblIncorrecta2.setFont(fuente1.deriveFont(32f));
        lblIncorrecta2.setForeground(Color.WHITE);
        lblIncorrecta2.setBounds(590, 550, 450, 25); 
        panelFormulario.add(lblIncorrecta2);

        JTextField txtIncorrecta2 = new JTextField();
        txtIncorrecta2.setFont(fuente1.deriveFont(22f));
        txtIncorrecta2.setBounds(590, 585, 420, 40); 
        panelFormulario.add(txtIncorrecta2);

        // --- BOTÓN VOLVER---
        btnSalir = new DecoracionBotones("VOLVER");
        btnSalir.setFont(fuente2.deriveFont(30f));
        btnSalir.setForeground(Color.WHITE);
        btnSalir.setBounds(1695, 950, 210, 45);
        btnSalir.addActionListener(e -> dispose());
        fondo.add(btnSalir);
    }
    
    public static void main(String[] args) {
        new M1_crearNuevo();
    }
}