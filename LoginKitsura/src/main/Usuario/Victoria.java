package main.Usuario;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import main.Menu.FondoPanel;

public class Victoria extends JFrame {

    private FondoPanel fondo;
    private Font fuente1;
    private Font fuente2;
        private JFrame ventanaAnterior;
            private JButton btnVolver;

    
    public Victoria(ActionListener accion, JFrame ventanaAnterior) {
                this.ventanaAnterior = ventanaAnterior;
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

        fondo = new FondoPanel("/Multimedia/utiles/fondos/interfaces/fondoCincoK.png");
        // NO hace setContentPane ni setVisible
        fondo.setLayout(null);

        
        
        crearComponentes();
        
        //--------------- VOLVER --------------
        btnVolver.addActionListener(e -> {
            dispose();          // Cierra esta ventana
            accion.actionPerformed(e); // Ejecuta la acción que te pasaron
        });
        setVisible(true);
    }

    public FondoPanel getFondo() {
        return fondo;
    }

    private void crearComponentes() {
        //---------------- TÍTULO ----------------
        JLabel lblGanado = new JLabel("¡Has Ganado!", JLabel.CENTER);
        lblGanado.setFont(fuente2.deriveFont(35f));
        lblGanado.setForeground(Color.WHITE);
        lblGanado.setBounds(100, 120, 700, 60);
        fondo.add(lblGanado);

        //---------------- FRASE DE MOTIVACIÓN ----------------
        JLabel lblFrase = new JLabel("-- Tu potencial es infinito, atrévete a explorarlo --.", JLabel.CENTER);
        lblFrase.setFont(fuente1.deriveFont(30f));
        lblFrase.setForeground(Color.DARK_GRAY);
        lblFrase.setBounds(100, 200, 700, 35);
        fondo.add(lblFrase);

        //---------------- MASCOTA ----------------
        JLabel mascotaCongrats = new JLabel();
        ImageIcon iconMascota = new ImageIcon(getClass().getResource(
                "/Multimedia/utiles/mascotaKitsura/imagen/VICTORIA-Imperfecta.png"));
        Image imgEscalada = iconMascota.getImage().getScaledInstance(600, 600, Image.SCALE_SMOOTH);
        mascotaCongrats.setIcon(new ImageIcon(imgEscalada));
        mascotaCongrats.setBounds(225, 270, 600, 600);
        fondo.add(mascotaCongrats);

        //---------------- FRASE INFERIOR ----------------
        JLabel lblFraseAbajo = new JLabel("Aún con errores, Pudiste lograrlo", JLabel.CENTER);
        lblFraseAbajo.setFont(fuente1.deriveFont(15f));
        lblFraseAbajo.setForeground(Color.WHITE);
        lblFraseAbajo.setBounds(100, 740, 700, 35);
        fondo.add(lblFraseAbajo);

        //---------------- DATOS DE USUARIO ----------------
        JLabel lblUsuario = new JLabel("Nombre de usuario");
        lblUsuario.setFont(fuente2.deriveFont(25f));
        lblUsuario.setForeground(Color.BLACK);
        lblUsuario.setBounds(1250, 150, 400, 40);
        fondo.add(lblUsuario);



            JLabel fotoPerfil = new JLabel();
            ImageIcon paisajeIcon = new ImageIcon(getClass().getResource("/Multimedia/utiles/logotipo/logofK.png"));
            Image paisajeEscalado = paisajeIcon.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);
            fotoPerfil.setIcon(new ImageIcon(paisajeEscalado));
            fotoPerfil.setBounds(1030, 115, 150, 150);
            fondo.add(fotoPerfil); 
        
        //---------------- BOTON VOLVER  ----------------
        btnVolver = new JButton("Volver");
        btnVolver.setFont(fuente1.deriveFont(25f));
        btnVolver.setBounds(1320, 710, 200, 50);
        btnVolver.addActionListener(e -> dispose());
        fondo.add(btnVolver);
        
                //---------------- BOTÓN MOSTRAR RESULTADOS ----------------
        JButton btnResultados = new JButton("Ver resultados");
        btnResultados.setFont(fuente1.deriveFont(25f));
        btnResultados.setBounds(1320, 660, 200, 50);
        btnResultados.addActionListener(e -> {
            if (ventanaAnterior instanceof FoxJump foxJump) {
                foxJump.mostrarResultadoConFade();
            }
        });
        fondo.add(btnResultados);
    }
    
  }