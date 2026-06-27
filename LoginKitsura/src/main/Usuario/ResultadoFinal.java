package main.Usuario;

import java.awt.*;
import javax.swing.*;
import main.Menu.FondoPanel;
import main.Menu.DecoracionBotones;
import main.Menu.FondoPanelSemi;

public class ResultadoFinal extends JFrame {

    private FondoPanel fondo;
    private Font fuente1;
    private Font fuente2;
    private DecoracionBotones btnVolver;
private JLabel lblPuntaje;
private JLabel lblTiempo;

private int puntajeFinal;
private int tiempoFinal;

public ResultadoFinal(int puntaje, int tiempoSegundos) {

    this.puntajeFinal = puntaje;
    this.tiempoFinal = tiempoSegundos;

    fondo = new FondoPanel("/Multimedia/utiles/fondos/interfaces/fondoTresK.png");
    setContentPane(fondo);

    setTitle("Resultado Final");
    setSize(700, 450);
    setLocationRelativeTo(null);
    setDefaultCloseOperation(DISPOSE_ON_CLOSE);

    fondo.setLayout(null);

    crearComponentes();
}
    

    public void mostrar() {
        setVisible(true);
    }

    private void crearComponentes() {

        try {
            fuente1 = Font.createFont(Font.TRUETYPE_FONT,
                    getClass().getResourceAsStream("/fuentes/LettersForLearners.ttf"));

            fuente2 = Font.createFont(Font.TRUETYPE_FONT,
                    getClass().getResourceAsStream("/fuentes/KGPerfectPenmanship.ttf"));

        } catch (Exception e) {
            fuente1 = new Font("Arial", Font.PLAIN, 20);
            fuente2 = new Font("Arial", Font.PLAIN, 20);
        }
JPanel panelContenedor = new JPanel();
panelContenedor.setLayout(null);
panelContenedor.setBackground(new Color(0,0,0,100));
panelContenedor.setBounds(40,50,430,320);
fondo.add(panelContenedor);

        //---------------- PUNTAJE ----------------
        //---------------- PUNTAJE ----------------
lblPuntaje = new JLabel("Puntaje: 0");
lblPuntaje.setFont(fuente2.deriveFont(25f));
lblPuntaje.setForeground(Color.WHITE);
lblPuntaje.setBounds(25,60,270,30);
panelContenedor.add(lblPuntaje);

//---------------- TEXTO TIEMPO ----------------
int min = tiempoFinal / 60;
int seg = tiempoFinal % 60;

lblTiempo = new JLabel(String.format("Tiempo: %02d:%02d", min, seg));
lblTiempo.setFont(fuente2.deriveFont(25f));
lblTiempo.setForeground(Color.WHITE);
lblTiempo.setBounds(25,140,270,30);
panelContenedor.add(lblTiempo);

//---------------- BOTON VOLVER ----------------
JButton btnVolver = new DecoracionBotones("VOLVER");
btnVolver.setFont(fuente2.deriveFont(20f));
btnVolver.setBounds(135,240,160,45);
btnVolver.addActionListener(e -> dispose());
panelContenedor.add(btnVolver);

//---------------- MASCOTA ----------------
JLabel mascota = new JLabel();
ImageIcon mascotaIcon = new ImageIcon(
        getClass().getResource("/Multimedia/utiles/mascotaKitsura/imagen/VENTANITA.png"));
Image mascotaEscalada = mascotaIcon.getImage().getScaledInstance(
        350,350,Image.SCALE_SMOOTH);
mascota.setIcon(new ImageIcon(mascotaEscalada));
mascota.setBounds(400,190,350,350);
fondo.add(mascota);

animarPuntaje();
    }

    private void animarPuntaje() {

        int duracionMs = 1500;
        int pasos = 60;
        int intervalo = duracionMs / pasos;

        int[] contador = {0};

        Timer timer = new Timer(intervalo, null);

        timer.addActionListener(e -> {
            contador[0]++;

            float progreso = (float) contador[0] / pasos;
            float eased = 1 - (1 - progreso) * (1 - progreso);

            int valor = (int) (puntajeFinal * eased);

            lblPuntaje.setText("Puntaje: " + valor);

            if (contador[0] >= pasos) {
                lblPuntaje.setText("Puntaje: " + puntajeFinal);
                ((Timer) e.getSource()).stop();
            }
        });

        timer.start();
    }
}
