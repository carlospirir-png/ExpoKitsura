package main.Administrador;

import java.awt.*;
import javax.swing.*;
import main.Menu.DecoracionBotones;
import main.Menu.FondoPanel;
import main.Menu.FondoPanelSemi;

public class MenuAdmin extends JFrame {

    private FondoPanel fondo;
    private Font fuente1;
    private Font fuente2;

    public MenuAdmin() {
        try {
            fuente1 = Font.createFont(Font.TRUETYPE_FONT,
                    getClass().getResourceAsStream("/fuentes/LettersForLearners.ttf"));
            fuente2 = Font.createFont(Font.TRUETYPE_FONT,
                    getClass().getResourceAsStream("/fuentes/KGPerfectPenmanship.ttf"));
        } catch (Exception e) {
            fuente1 = new Font("Arial", Font.PLAIN, 20);
            fuente2 = new Font("Arial", Font.PLAIN, 20);
        }
        fondo = new FondoPanel("/Multimedia/utiles/fondos/interfaces/fondoDosK.png");
        setContentPane(fondo);
        setTitle("Menú Administrador");
        setSize(1980, 1080);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        fondo.setLayout(null);
        FondoPanelSemi panelTitulo = new FondoPanelSemi(new Color(0, 0, 0, 150));
        panelTitulo.setLayout(null);
        panelTitulo.setBounds(800, 65, 500, 75);
        fondo.add(panelTitulo);
        JLabel lbl = new JLabel("MENÚ", JLabel.CENTER);
        lbl.setFont(fuente2.deriveFont(45f));
        lbl.setForeground(Color.WHITE);
        lbl.setBounds(0, 0, 500, 75);
        panelTitulo.add(lbl);
<<<<<<< HEAD
        // ---------------- USUARIO ----------------
        JButton btnUsuario = new DecoracionBotones("USUARIO");
        btnUsuario.setFont(fuente2.deriveFont(26f));
        btnUsuario.setBounds(180, 240, 430, 65);
        btnUsuario.addActionListener(e -> {
            new UsuarioMenu();
            dispose();
        });
        fondo.add(btnUsuario);
        // ---------------- PUNTUACIONES ----------------
        JButton btnPuntuaciones = new DecoracionBotones("PUNTUACIONES");
        btnPuntuaciones.setFont(fuente2.deriveFont(26f));
        btnPuntuaciones.setBounds(180, 335, 430, 65);
        btnPuntuaciones.addActionListener(e -> {
            new PuntuacionesAdmin();
            dispose();
        });
        fondo.add(btnPuntuaciones);
        // ---------------- VIDAS ----------------
        JButton btnVidas = new DecoracionBotones("VIDAS");
        btnVidas.setFont(fuente2.deriveFont(26f));
        btnVidas.setBounds(180, 430, 430, 65);
        btnVidas.addActionListener(e -> {
            new VidasAdmin();
            dispose();
        });
        fondo.add(btnVidas);
        // ---------------- TIEMPO ----------------
        JButton btnTiempo = new DecoracionBotones("TIEMPO");
        btnTiempo.setFont(fuente2.deriveFont(26f));
        btnTiempo.setBounds(180, 525, 430, 65);
        btnTiempo.addActionListener(e -> {
            new TiempoAdmin();
            dispose();
        });
        fondo.add(btnTiempo);
        // ---------------- PISTAS ----------------
        JButton btnPistas = new DecoracionBotones("PISTAS");
        btnPistas.setFont(fuente2.deriveFont(26f));
        btnPistas.setBounds(180, 620, 430, 65);
        btnPistas.addActionListener(e -> {
            new PistasMenu();
            dispose();
        });
        fondo.add(btnPistas);
        // ---------------- ADMINISTRAR STAGES ----------------
        JButton btnStages = new DecoracionBotones("ADMINISTRAR STAGES");
        btnStages.setFont(fuente2.deriveFont(24f));
        btnStages.setBounds(180, 715, 430, 65);
        btnStages.addActionListener(e -> {
            new AdminStages();
            dispose();
        });
        fondo.add(btnStages);
        // ---------------- MASCOTA ----------------
        JLabel mascota = new JLabel();
        ImageIcon icon = new ImageIcon(getClass().getResource("/Multimedia/utiles/mascotaKitsura/imagen/MENÚ-PRINCIPAL-LINTERNA.png"));
        mascota.setIcon(new ImageIcon(icon.getImage().getScaledInstance(700, 700, Image.SCALE_SMOOTH)));
        mascota.setBounds(700, 150, 700, 700);
        fondo.add(mascota);
        // ---------------- VOLVER ----------------
        JButton volver = new DecoracionBotones("VOLVER");
=======

        String[] txt={"USUARIO","PUNTUACIONES","VIDAS","TIEMPO","PISTAS","ADMINISTRAR STAGES"};
        int y=240;
        for(int i=0;i<txt.length;i++){
            JButton b =new DecoracionBotones(txt[i],
                                    //ColorBase             ColorBorde              ColorLetra
                DecoracionBotones.AZUL, DecoracionBotones.GRIS, DecoracionBotones.AMARILLO, //MOUSE FUERA
                DecoracionBotones.CELESTE, DecoracionBotones.AZUL, DecoracionBotones.AZUL); //MOUSE DENTRO   

            b.setFont(fuente2.deriveFont(i==5?24f:26f));
            b.setBounds(180,y,430,65);
            fondo.add(b);
            y+=95;
        }

        JLabel mascota=new JLabel();
        ImageIcon icon=new ImageIcon(getClass().getResource("/Multimedia/utiles/mascotaKitsura/imagen/MENÚ-PRINCIPAL-LINTERNA.png"));
        mascota.setIcon(new ImageIcon(icon.getImage().getScaledInstance(700,700,Image.SCALE_SMOOTH)));
        mascota.setBounds(700,150,700,700);
        fondo.add(mascota);

        JButton volver=new DecoracionBotones("VOLVER",
                                //ColorBase             ColorBorde              ColorLetra
                DecoracionBotones.AZUL, DecoracionBotones.GRIS, DecoracionBotones.AMARILLO, //MOUSE FUERA
                DecoracionBotones.CELESTE, DecoracionBotones.AZUL, DecoracionBotones.AZUL); //MOUSE DENTRO   

>>>>>>> 8d29a9e75b19afa2cfd4b44a742a0f73e91fab9b
        volver.setFont(fuente2.deriveFont(28f));
        volver.setBounds(1500, 870, 300, 65);
        volver.addActionListener(e -> dispose());
        fondo.add(volver);
        setVisible(true);
    }

    public static void main(String[] args) {
        new MenuAdmin();
    }
}
