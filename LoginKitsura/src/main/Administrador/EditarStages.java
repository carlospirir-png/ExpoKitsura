package main.Administrador;

import java.awt.*;
import javax.swing.*;
import main.Menu.FondoPanelSemi;
import main.Menu.DecoracionBotones;

public class EditarStages extends JFrame {

    private FondoPanelSemi fondo;
    private Font fuente1, fuente2;
    private JPanel panelSemi;
    private JLabel lblTitulo;
    private JLabel lblID;
    private JTextField txtID;
    private JLabel lblMascota;
    private DecoracionBotones btnConfirmar;

    public EditarStages() {
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
        fondo = new FondoPanelSemi("/Multimedia/utiles/fondos/interfaces/fondoDosK.png");
        setContentPane(fondo);

        setTitle("Editar Stages");
        setSize(1920, 1080);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        fondo.setLayout(null);

        crearComponentes();

        setVisible(true);
    }

    private void crearComponentes() {

        panelSemi = new JPanel() {

            @Override
            protected void paintComponent(Graphics g) {

                super.paintComponent(g);

                Graphics2D g2 = (Graphics2D) g.create();

                g2.setColor(new Color(0, 0, 0, 120));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 40, 40);

                g2.dispose();
            }
        };

        panelSemi.setOpaque(false);
        panelSemi.setLayout(null);
        panelSemi.setBounds(180, 180, 1000, 650);

        fondo.add(panelSemi);

        lblTitulo = new JLabel("EDITAR EXISTENTE", SwingConstants.CENTER);
        lblTitulo.setFont(fuente2.deriveFont(50f));
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setBounds(0, 40, 1000, 60);

        panelSemi.add(lblTitulo);

        lblID = new JLabel("Ingrese el ID del Stage a editar:");
        lblID.setFont(fuente2.deriveFont(32f));
        lblID.setForeground(Color.WHITE);
        lblID.setBounds(220, 240, 600, 45);

        panelSemi.add(lblID);

        txtID = new JTextField();
        txtID.setFont(fuente1.deriveFont(28f));
        txtID.setBounds(200, 340, 600, 70);

        panelSemi.add(txtID);

        lblMascota = new JLabel();

        ImageIcon mascotaIcon =
                new ImageIcon(
                        getClass().getResource("/Multimedia/utiles/mascotaKitsura/imagen/MENÚ-PRINCIPAL-LINTERNA.png"));

        Image mascotaEscalada =
                mascotaIcon.getImage().getScaledInstance(
                        750,
                        750,
                        Image.SCALE_SMOOTH);

        lblMascota.setIcon(new ImageIcon(mascotaEscalada));
        lblMascota.setBounds(1150, 140, 750, 750);

        fondo.add(lblMascota);
        
        btnConfirmar = new DecoracionBotones("CONFIRMAR");
        btnConfirmar.setFont(fuente2.deriveFont(24f));
        btnConfirmar.setForeground(Color.WHITE);
        btnConfirmar.setBounds(400, 510, 230, 50); 
        
        // Acción del botón
        btnConfirmar.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "¡Cambio guardado con éxito!", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        });

        panelSemi.add(btnConfirmar);
    }
   public static void main(String[] args) {
        new EditarStages();
    }

}