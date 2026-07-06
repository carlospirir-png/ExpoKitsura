package main.Usuario;

import java.awt.*;
import javax.swing.*;
import main.Menu.*;

public class PistasTexto extends JFrame {

    private FondoPanel fondo;
    private Font fuente1;
    private Font fuente2;
    private DecoracionBotones btnSalir;
    private JLabel lblPista;

    public PistasTexto(String pista) {
        try {
            // LettersForLearners
            fuente1 = Font.createFont(
                    Font.TRUETYPE_FONT,
                    getClass().getResourceAsStream("/fuentes/LettersForLearners.ttf"));
            // KGPerfectPenmanship
            fuente2 = Font.createFont(
                    Font.TRUETYPE_FONT,
                    getClass().getResourceAsStream("/fuentes/KGPerfectPenmanship.ttf"));
        } catch (Exception e) {
            e.printStackTrace();
            fuente1 = new Font("Arial", Font.PLAIN, 20);
            fuente2 = new Font("Arial", Font.PLAIN, 20);
        }
        fondo = new FondoPanel("/Multimedia/utiles/fondos/interfaces/fondoTresK.png");
        setContentPane(fondo);
        
        setTitle("Pistas de Texto");
        setSize(700, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        fondo.setLayout(null);
        crearComponentes();
        lblPista.setText(
                "<html><body style='width:240px; padding-left:10px;'>" + pista + "</body></html>"
        );

        setVisible(true);
    }

    private void crearComponentes() {

        //---------------- RECUADRO TEXTO ----------------
        JPanel recuadroTexto = new JPanel();
        recuadroTexto.setLayout(null);
        recuadroTexto.setBackground(Color.WHITE);
        recuadroTexto.setBounds(40, 75, 380, 210);
        fondo.add(recuadroTexto);

        lblPista = new JLabel();
        
        lblPista.setFont(fuente1.deriveFont(18f)); 
        lblPista.setForeground(Color.BLACK); 
        
        lblPista.setBounds(12, 20, 280, 140);
        recuadroTexto.add(lblPista);

        //---------------- BOTÓN SALIR ----------------
        JButton btnSalir = new DecoracionBotones("SALIR",
                                //ColorBase             ColorBorde              ColorLetra
                DecoracionBotones.AZUL, DecoracionBotones.GRIS, DecoracionBotones.AMARILLO, //MOUSE FUERA
                DecoracionBotones.CELESTE, DecoracionBotones.AZUL, DecoracionBotones.AZUL); //MOUSE DENTRO   

        btnSalir.setFont(fuente2.deriveFont(15f));
        btnSalir.setBounds(120, 320, 160, 45);
        btnSalir.addActionListener(e -> dispose());
        fondo.add(btnSalir);

        //---------------- MASCOTA ----------------
        JLabel mascotaLector = new JLabel();
        ImageIcon iconMascota = new ImageIcon(getClass().getResource("/Multimedia/utiles/mascotaKitsura/imagen/Zorro_Foco.png"));
        Image imgEscalada = iconMascota.getImage().getScaledInstance(350, 350, Image.SCALE_SMOOTH);

        mascotaLector.setIcon(new ImageIcon(imgEscalada));
        mascotaLector.setBounds(355, 60, 350, 350);
        fondo.add(mascotaLector);
    }


    public static void main(String[] args) {
        new PistasTexto("Texto predefinido de la pista aquí...");
    }
}
