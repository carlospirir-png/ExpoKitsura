package main.Administrador;

import java.awt.*;
import javax.swing.*;
import main.Menu.*;

public class AdminStages extends JFrame{
    private FondoPanel fondo;
    private Font fuente1;
    private Font fuente2;
    private DecoracionBotones btnSalir;
    private DecoracionBotones btnCrearUno;
    private DecoracionBotones btnEditarExistente;
    
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
        fondo = new FondoPanel("/Multimedia/utiles/fondos/interfaces/fondoDosK.png"); 
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
        // --- AGREGANDO EL PANEL SEMI-TRANSPARENTE PARA EL TÍTULO ---
        // Lo posicionamos centrado abarcando el espacio del título principal (X: 75, Y: 130, Ancho: 1800, Alto: 95)
        FondoPanelSemi panelTitulo = new FondoPanelSemi(new Color(0, 0, 0, 120)); 
        panelTitulo.setBounds(75, 130, 1800, 95); 
        panelTitulo.setLayout(null);
        fondo.add(panelTitulo);
        
        JLabel lblTituloSeccion = new JLabel("ADMINISTRAR STAGES", JLabel.CENTER);
        lblTituloSeccion.setFont(fuente2.deriveFont(45f));
        lblTituloSeccion.setForeground(Color.WHITE);
        lblTituloSeccion.setBounds(0, 20, 1800, 55);
        panelTitulo.add(lblTituloSeccion); // En lugar de agregarlo al fondo lo agregamos al panel 

        btnCrearUno = new DecoracionBotones("CREAR UNO",
                //ColorBase             ColorBorde              ColorLetra
                DecoracionBotones.ROSA, DecoracionBotones.ROJO, DecoracionBotones.AMARILLO_APAGADO, //MOUSE FUERA
                DecoracionBotones.ROJO, DecoracionBotones.ROSA, DecoracionBotones.ROSA); //MOUSE DENTRO
        // PD. Intente cambiarle el color por un amarillo pero senti que ningun otro color quedaba  
        
        btnCrearUno.setFont(fuente2.deriveFont(20f));
        btnCrearUno.setBounds(325, 500, 360, 70);
        btnCrearUno.addActionListener(e -> {
            
        });
        fondo.add(btnCrearUno);
        

        btnEditarExistente = new DecoracionBotones("EDITAR UNO EXISTENTE",
                //ColorBase             ColorBorde              ColorLetra
                DecoracionBotones.ROSA, DecoracionBotones.ROJO, DecoracionBotones.AMARILLO_APAGADO, //MOUSE FUERA
                DecoracionBotones.ROJO, DecoracionBotones.ROSA, DecoracionBotones.ROSA); //MOUSE DENTRO
        btnEditarExistente.setFont(fuente2.deriveFont(20f));
        btnEditarExistente.setBounds(1245, 500, 360, 70);
        btnEditarExistente.addActionListener(e ->{
            new EditarStages();
            dispose();
        });
        
        fondo.add(btnEditarExistente);
        
        JLabel staticMascotaControl = new JLabel();
        ImageIcon iconMascota = new ImageIcon(getClass().getResource("/Multimedia/utiles/mascotaKitsura/imagen/MINIJUEGO-CONTROL.png")); 
        Image imgEscalada = iconMascota.getImage().getScaledInstance(600, 600, Image.SCALE_SMOOTH);
        staticMascotaControl.setIcon(new ImageIcon(imgEscalada));
        staticMascotaControl.setBounds(655, 290, 600, 600);
        fondo.add(staticMascotaControl);

        btnSalir = new DecoracionBotones("VOLVER",
                                //ColorBase             ColorBorde              ColorLetra
                DecoracionBotones.AZUL, DecoracionBotones.GRIS, DecoracionBotones.AMARILLO, //MOUSE FUERA
                DecoracionBotones.CELESTE, DecoracionBotones.AZUL, DecoracionBotones.AZUL); //MOUSE DENTRO   

        btnSalir.setFont(fuente2.deriveFont(20F));
        btnSalir.setBounds(1680, 950, 210, 45);
        btnSalir.addActionListener(e -> {
            new MenuAdmin();
            dispose();
                });
        fondo.add(btnSalir);
    }
    
    public static void main(String[] args) {
        new AdminStages();
    }

}
