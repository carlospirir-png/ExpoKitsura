package main.Administrador;

import java.awt.*;
import javax.swing.*;
import main.Menu.*;
import main.Usuario.RegistroUsuario;

public class MenuAdmin extends JFrame {

    private FondoPanel fondo;
    private Font fuente1;
    private Font fuente2;
    // Recibe los datos temporales: minijuego, categoría y nivel
    private DatosConfiguracion datos;
    
    // Constructor sobrecargado: 
    public MenuAdmin(){
       inicializar();
    }
    
    public MenuAdmin(DatosConfiguracion datos){
        this.datos = datos;
        inicializar();
    }

   private void inicializar() {
        
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

        // ---------------- USUARIO ----------------
        JButton btnUsuario = new DecoracionBotones("USUARIO",
                //ColorBase             ColorBorde              ColorLetra
                DecoracionBotones.AZUL, DecoracionBotones.GRIS, DecoracionBotones.AMARILLO, //MOUSE FUERA
                DecoracionBotones.CELESTE, DecoracionBotones.AZUL, DecoracionBotones.AZUL); //MOUSE DENTRO   
        btnUsuario.setFont(fuente2.deriveFont(26f));
        btnUsuario.setBounds(180, 240, 430, 65);
        btnUsuario.addActionListener(e -> {
            new UsuarioMenu();
            dispose();
        });
        fondo.add(btnUsuario);
        // ---------------- PUNTUACIONES ----------------
        //Se inicializa el botón de puntuaciones
        JButton btnPuntuaciones = new DecoracionBotones("PUNTUACIONES",
                //ColorBase             ColorBorde              ColorLetra
                DecoracionBotones.AMARILLO_MOSTAZA, DecoracionBotones.GRIS, DecoracionBotones.AMARILLO, //MOUSE FUERA
                DecoracionBotones.AMARILLO_SUAVE, DecoracionBotones.AMARILLO_MOSTAZA, DecoracionBotones.AMARILLO_MOSTAZA); //MOUSE DENTRO
        
        //Se el coloca al botón la fuente 2 y tamaño a dicha fuente
        btnPuntuaciones.setFont(fuente2.deriveFont(26f));
      
        //Se le coloca la posición y tamaño al botón
        btnPuntuaciones.setBounds(180, 335, 430, 65);
        
        //ActionListener
        btnPuntuaciones.addActionListener(e -> {
            //Se abre una nueva ventana de puntuacionesAdmin
            new PuntuacionesAdmin(datos); //se envían los datos
            dispose();//se cierra esta ventana
        });
        
        //Se agrega el botón al panel
        fondo.add(btnPuntuaciones);
        
        // ---------------- VIDAS ----------------
        JButton btnVidas = new DecoracionBotones("VIDAS",
                //ColorBase             ColorBorde              ColorLetra
                DecoracionBotones.ROSA, DecoracionBotones.ROJO, DecoracionBotones.AMARILLO, //MOUSE FUERA
                DecoracionBotones.ROJO, DecoracionBotones.ROSA, DecoracionBotones.ROSA); //MOUSE DENTRO  
        
        // Se agrega al botón la fuente y tamaño deseado
        btnVidas.setFont(fuente2.deriveFont(26f));
        // Se asigna el tamaño y posición del botón
        btnVidas.setBounds(180, 430, 430, 65);
        // Dirige a la clase de VidasAdmin y cierra el menú
        btnVidas.addActionListener(e -> {
            new VidasAdmin(datos);
            dispose();
        });
        // Se agrega el botón al panel principal
        fondo.add(btnVidas);
        // ---------------- TIEMPO ----------------
        JButton btnTiempo = new DecoracionBotones("TIEMPO",
                //ColorBase             ColorBorde              ColorLetra
                DecoracionBotones.VERDE, DecoracionBotones.GRIS, DecoracionBotones.AMARILLO, //MOUSE FUERA
                DecoracionBotones.VERDE_SUAVE, DecoracionBotones.VERDE, DecoracionBotones.VERDE); //MOUSE DENTRO  
        btnTiempo.setFont(fuente2.deriveFont(26f));
        btnTiempo.setBounds(180, 525, 430, 65);
        btnTiempo.addActionListener(e -> {
            new TiempoAdmin();
            dispose();
        });
        fondo.add(btnTiempo);
        // ---------------- PISTAS ----------------
        JButton btnPistas = new DecoracionBotones("PISTAS",
                //ColorBase             ColorBorde              ColorLetra
                DecoracionBotones.AZUL, DecoracionBotones.GRIS, DecoracionBotones.AMARILLO, //MOUSE FUERA
                DecoracionBotones.CELESTE, DecoracionBotones.AZUL, DecoracionBotones.AZUL); //MOUSE DENTRO   
        btnPistas.setFont(fuente2.deriveFont(26f));
        btnPistas.setBounds(180, 620, 430, 65);
        btnPistas.addActionListener(e -> {
            new PistasMenu(datos);
            dispose();
        });
        fondo.add(btnPistas);
        // ---------------- ADMINISTRAR STAGES ----------------
        JButton btnStages = new DecoracionBotones("ADMINISTRAR STAGES",
                 //ColorBase             ColorBorde              ColorLetra
                DecoracionBotones.AMARILLO_MOSTAZA, DecoracionBotones.GRIS, DecoracionBotones.AMARILLO, //MOUSE FUERA
                DecoracionBotones.AMARILLO_SUAVE, DecoracionBotones.AMARILLO_MOSTAZA, DecoracionBotones.AMARILLO_MOSTAZA); //MOUSE DENTRO   
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
        JButton volver = new DecoracionBotones("VOLVER",        
                //ColorBase             ColorBorde              ColorLetra
                DecoracionBotones.AZUL, DecoracionBotones.GRIS, DecoracionBotones.AMARILLO, //MOUSE FUERA
                DecoracionBotones.CELESTE, DecoracionBotones.AZUL, DecoracionBotones.AZUL); //MOUSE DENTRO);

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
        volver.setFont(fuente2.deriveFont(28f));
        volver.setBounds(1500, 870, 300, 65);
        volver.addActionListener(e -> {
            new RegistroUsuario();
            dispose();
                });
        fondo.add(volver);
        setVisible(true);
    }
    

    public static void main(String[] args) {
        DatosConfiguracion datos = new DatosConfiguracion("HiddenFox","Animales","Fácil");
        new MenuAdmin(datos);
    }

}
