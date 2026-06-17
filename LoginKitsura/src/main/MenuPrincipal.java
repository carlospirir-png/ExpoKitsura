package main;

import java.awt.*;
import javax.swing.*;

public class MenuPrincipal extends JFrame {
    private FondoPanel fondo;
    private Font fuente2;
    
    public MenuPrincipal (){
        try{
            fuente2 = Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("/fuentes/KGPerfectPenmanship.ttf"));
        } catch(Exception e){
            e.printStackTrace();
            fuente2 = new Font("Arial", Font.PLAIN, 20);
        }
        
        fondo = new FondoPanel("/Multimedia/utiles/fondoDosK.png");
        setContentPane(fondo);
        setTitle("Página Principal");
        setSize(1880,1080);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        fondo.setLayout(null);
        crearComponentes();
        setVisible(true);
    }
    private void crearComponentes(){
        //---------------- T I T U L O ----------------
        JLabel lblTitulo = new JLabel("MENÚ PRINCIPAL");
        lblTitulo.setFont(fuente2.deriveFont(35f));
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setBounds(810, 120, 500, 50);
        fondo.add(lblTitulo);
        
        //---------------- BOTON PERFIL ----------------
        JButton btnPerfil = new JButton("PERFIL");
        btnPerfil.setFont(fuente2.deriveFont(15f));
        btnPerfil.setBounds(820, 320, 285, 65);    
        btnPerfil.setIconTextGap(5);  
        ImageIcon icoPerfil = new ImageIcon(getClass().getResource("/Multimedia/utiles/pencil.png"));
        Image imgPerfil = icoPerfil.getImage().getScaledInstance(35,35, Image.SCALE_SMOOTH);
        btnPerfil.setIcon(new ImageIcon(imgPerfil));        
        fondo.add(btnPerfil);
        
        //---------------- BOTON MINIJUEGOS ----------------
        JButton btnMinijuegos = new JButton("MINIJUEGOS");
        btnMinijuegos.setFont(fuente2.deriveFont(15f));
        btnMinijuegos.setBounds(820, 440, 285, 65);
        btnMinijuegos.setIconTextGap(5);        
        ImageIcon icoMinijuegos = new ImageIcon(getClass().getResource("/Multimedia/utiles/mandoo.png"));
        Image imgMinijuegos = icoMinijuegos.getImage().getScaledInstance(35, 35, Image.SCALE_SMOOTH);
        btnMinijuegos.setIcon(new ImageIcon(imgMinijuegos));       
        fondo.add(btnMinijuegos);
        
        //---------------- BOTON ESTADISTICAS ----------------
        JButton btnLogIn = new JButton("ESTADISTICAS");
        btnLogIn.setFont(fuente2.deriveFont(15f));
        btnLogIn.setBounds(820,560,285,65);
        btnLogIn.setIconTextGap(5);        
        ImageIcon icoLogIn = new ImageIcon(getClass().getResource("/Multimedia/utiles/abaco.png"));
        Image imgAbaco = icoLogIn.getImage().getScaledInstance(35, 35, Image.SCALE_SMOOTH);
        btnLogIn.setIcon(new ImageIcon(imgAbaco)); 
        fondo.add(btnLogIn);
        
        //---------------- BOTON SALIR ----------------
        JButton btnSalir = new JButton("SALIR");
        btnSalir.setFont(fuente2.deriveFont(15f));
        btnSalir.setBounds(820, 680, 285, 65);
        btnSalir.setIconTextGap(5);        
        ImageIcon icoSalir = new ImageIcon(getClass().getResource("/Multimedia/utiles/salir.png"));
        Image imgSalir = icoSalir.getImage().getScaledInstance(35, 35, Image.SCALE_SMOOTH);
        btnSalir.setIcon(new ImageIcon(imgSalir));        
        fondo.add(btnSalir);
        
        //---------------- MASCOTA ----------------
        JLabel mascota = new JLabel();
        ImageIcon mascotaIcon = new ImageIcon(getClass().getResource("/Multimedia/utiles/mascota3.png"));
        Image mascotaEscalada = mascotaIcon.getImage().getScaledInstance(600, 600, Image.SCALE_SMOOTH);
        mascota.setIcon(new ImageIcon(mascotaEscalada));
        mascota.setBounds(240, 300, 600, 600); 
        fondo.add(mascota);
    }
    
}

