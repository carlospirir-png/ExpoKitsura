package main.Usuario;

//------------------------ IMPORTACIONES ----------------------------
import java.awt.*; //Se importa java awt
import javax.swing.*; //Se importa java swing
import java.net.URL; //Se importa URL
import main.conexion.Conexion; //Se importa Conexion
import java.sql.*; //Se importa el SQL

public class HiddenFox extends JFrame {

    //Atributos
    Connection con = new Conexion().getConnection();

    private final JPanel fondo;

    ImageIcon fondoPapelIcon, iconoSombra;

    private Font fuente1, fuente2;

    private JLabel vida1, vida2, vida3, titulo, tiempoTexto, tiempo, acierto, dificultad, categoria, mascota, imagenSombra;

    JLabel fondoPapel;

    Image fondoPapelEscalado;

    private JButton btnAyuda, btnRespuesta1, btnRespuesta2, btnRespuesta3, btnRespuesta4;

    public HiddenFox() {
        try {

            fuente1 = Font.createFont(
                    Font.TRUETYPE_FONT,
                    getClass().getResourceAsStream("/fuentes/LettersForLearners.ttf"));

            fuente2 = Font.createFont(
                    Font.TRUETYPE_FONT,
                    getClass().getResourceAsStream("/fuentes/KGPerfectPenmanship.ttf"));

        } catch (Exception e) {

            e.printStackTrace();

            fuente1 = new Font("Arial", Font.PLAIN, 20);
            fuente2 = new Font("Arial", Font.PLAIN, 20);
        }

        //---------------- FONDO ----------------
        fondo = new JPanel();
        fondo.setLayout(null);
        fondo.setBackground(Color.WHITE);

        setContentPane(fondo);

        setTitle("Hidden Fox");
        setSize(1880, 1080);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        crearComponentes();

        //------------------- SOMBRA ---------
        imagenSombra = new JLabel(); //Se crea el label de la imagen sombra
        imagenSombra.setBounds(350, 60, 350, 320); //Se posiciona y configura el tamaño de la sombra

        //----------------- FONDO PAPEL ----------
        fondoPapel = new JLabel();
        fondoPapel.setLayout(null);
        fondoPapel.setBounds(420, 250, 1050, 450);

    }

    private void crearComponentes() {

        //---------------- VIDAS ----------------
        try {

            ImageIcon corazonIcon = new ImageIcon(
                    getClass().getResource("/Multimedia/utiles/ElementosGraficos/imagenes/corazon.png"));

            Image corazonEscalado = corazonIcon.getImage().getScaledInstance(
                    60, 60, Image.SCALE_SMOOTH);

            ImageIcon corazonFinal = new ImageIcon(corazonEscalado);

            vida1 = new JLabel(corazonFinal);
            vida2 = new JLabel(corazonFinal);
            vida3 = new JLabel(corazonFinal);

        } catch (Exception e) {

            vida1 = new JLabel("♥");
            vida2 = new JLabel("♥");
            vida3 = new JLabel("♥");

            vida1.setFont(fuente1.deriveFont(55f));
            vida2.setFont(fuente1.deriveFont(55f));
            vida3.setFont(fuente1.deriveFont(55f));

            vida1.setForeground(Color.RED);
            vida2.setForeground(Color.RED);
            vida3.setForeground(Color.RED);
        }

        vida1.setBounds(70, 25, 60, 60);
        vida2.setBounds(135, 25, 60, 60);
        vida3.setBounds(200, 25, 60, 60);
        // --------------- PANEL -----------------
        JPanel panelv = new JPanel();
        panelv.setBounds(60, 20, 200, 70);
        panelv.setBackground(Color.WHITE);
        panelv.add(vida1);
        panelv.add(vida2);
        panelv.add(vida3);
        fondo.add(panelv);

        //---------------- AYUDA ----------------
        btnAyuda = new JButton("¿Necesitas ayuda?");
        btnAyuda.setFont(fuente2.deriveFont(18f));
        btnAyuda.setBounds(60, 120, 280, 55);

        fondo.add(btnAyuda);

        //---------------- TITULO ----------------
        titulo = new JLabel(
                "<html><center>¿Quién o qué se encuentra<br>detrás de la sombra?</center></html>",
                SwingConstants.CENTER);

        titulo.setFont(fuente1.deriveFont(40f));
        titulo.setForeground(Color.BLACK);
        titulo.setBounds(500, 20, 900, 150);

        fondo.add(titulo);

        //---------------- TIEMPO ----------------
        tiempoTexto = new JLabel("Tiempo restante:");
        tiempoTexto.setFont(fuente2.deriveFont(25f));
        tiempoTexto.setForeground(Color.BLACK);
        tiempoTexto.setBounds(1450, 70, 300, 40);

        fondo.add(tiempoTexto);

        tiempo = new JLabel("00:00:00", SwingConstants.CENTER);
        tiempo.setFont(fuente2.deriveFont(28f));
        tiempo.setOpaque(true);
        tiempo.setBackground(Color.WHITE);
        tiempo.setForeground(Color.BLACK);
        tiempo.setBounds(1440, 120, 320, 60);

        fondo.add(tiempo);

        //---------------- RESPUESTA 1 ----------------
        btnRespuesta1 = new JButton("RESPUESTA 1");
        btnRespuesta1.setFont(fuente2.deriveFont(20f));
        btnRespuesta1.setBounds(500, 730, 300, 75);
        
        btnRespuesta1.addActionListener(e -> respuestaSeleccionada(btnRespuesta1));

        fondo.add(btnRespuesta1);

        //---------------- RESPUESTA 2 ----------------
        btnRespuesta2 = new JButton("RESPUESTA 2");
        btnRespuesta2.setFont(fuente2.deriveFont(20f));
        btnRespuesta2.setBounds(1020, 730, 300, 75);
        
        btnRespuesta2.addActionListener(e -> respuestaSeleccionada(btnRespuesta2));

        fondo.add(btnRespuesta2);

        //---------------- RESPUESTA 3 ----------------
        btnRespuesta3 = new JButton("RESPUESTA 3");
        btnRespuesta3.setFont(fuente2.deriveFont(20f));
        btnRespuesta3.setBounds(500, 840, 300, 75);
        
        btnRespuesta3.addActionListener(e -> respuestaSeleccionada(btnRespuesta3));

        fondo.add(btnRespuesta3);

        //---------------- RESPUESTA 4 ----------------
        btnRespuesta4 = new JButton("RESPUESTA 4");
        btnRespuesta4.setFont(fuente2.deriveFont(20f));
        btnRespuesta4.setBounds(1020, 840, 300, 75);
        
        btnRespuesta4.addActionListener(e -> respuestaSeleccionada(btnRespuesta4));

        fondo.add(btnRespuesta4);

        //---------------- NIVEL ----------------
        acierto = new JLabel("Acierto: ***");
        acierto.setFont(fuente2.deriveFont(25f));
        acierto.setForeground(Color.BLACK);
        acierto.setBounds(80, 740, 350, 40);

        fondo.add(acierto);

        //---------------- DIFICULTAD ----------------
        dificultad = new JLabel("Dificultad: ***");
        dificultad.setFont(fuente2.deriveFont(25f));
        dificultad.setForeground(Color.BLACK);
        dificultad.setBounds(80, 790, 350, 40);

        fondo.add(dificultad);

        //---------------- CATEGORIA ----------------
        categoria = new JLabel("Categoría: ***");
        categoria.setFont(fuente2.deriveFont(25f));
        categoria.setForeground(Color.BLACK);
        categoria.setBounds(80, 840, 350, 40);

        fondo.add(categoria);

        //---------------- MASCOTA ----------------
        mascota = new JLabel();

        ImageIcon mascotaIcon = new ImageIcon(
                getClass().getResource("/Multimedia/utiles/mascotaKitsura/imagen/zorroLupa.png"));

        Image mascotaEscalada = mascotaIcon.getImage().getScaledInstance(
                450, 450, Image.SCALE_SMOOTH);

        mascota.setIcon(new ImageIcon(mascotaEscalada));
        mascota.setBounds(1450, 480, 450, 450);

        fondo.add(mascota);
    }

    //-------------------- CAMBIAR FONDOS -----------
    public void CambiarImagen(String linkImagen) {
        //---------------- IMAGEN SOMBRA Y A COLOR ----------------    

        URL link = getClass().getResource(linkImagen);

        try {

            if (link == null) {
                throw new RuntimeException("No se encontró la imagen: " + linkImagen);
            }

            iconoSombra = new ImageIcon(link);

            Image sombraEscalada = iconoSombra.getImage().getScaledInstance(350, 320, Image.SCALE_SMOOTH);

            imagenSombra.setIcon(new ImageIcon(sombraEscalada));

            imagenSombra.revalidate();
            imagenSombra.repaint();

        } catch (Exception e) {

            e.printStackTrace();

            imagenSombra.setText("ERROR AL CARGAR IMAGEN");
            imagenSombra.setHorizontalAlignment(SwingConstants.CENTER);
            imagenSombra.setForeground(Color.RED);
        }
    }

    //boolean imagen:   false = sombra      |       true = color
    public void CargarImagen(int id_pregunta, boolean imagen) {
        String sql = "SELECT imagen_sombra, imagen_color FROM Pregunta WHERE id_pregunta = ?";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id_pregunta);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String rutaSombra = rs.getString("imagen_sombra");
                String rutaColor = rs.getString("imagen_color");
                if (imagen) {
                    CambiarImagen(rutaColor);
                } else {
                    CambiarImagen(rutaSombra);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void CambiarFondoPapel(String linkImagen) {
        //----------------- FONDO PAPEL  --------------

        URL link = getClass().getResource(linkImagen);

        try {

            if (link == null) {
                throw new RuntimeException("No se encontró la imagen: " + linkImagen);
            }

            fondoPapelIcon = new ImageIcon(link);

            fondoPapelEscalado = fondoPapelIcon.getImage().getScaledInstance(1050, 450, Image.SCALE_SMOOTH);

            fondoPapel.setIcon(new ImageIcon(fondoPapelEscalado));

            imagenSombra.revalidate();
            imagenSombra.repaint();

            fondoPapel.add(imagenSombra); //Se añade la sombra al fondo papel

            fondo.add(fondoPapel); //Se añade el fondo papel al fondo
            setVisible(true); //Se vuelve visible
        } catch (Exception e) {
            e.printStackTrace();

            imagenSombra.setText("ERROR AL CARGAR IMAGEN");
            imagenSombra.setHorizontalAlignment(SwingConstants.CENTER);
            imagenSombra.setForeground(Color.RED);
        }

    }

    public void CargarFondoPapel(int nivel) {
        /*
        NIVELES: 
        1. Animales - Fácil
        2. Animales - Intermedio
        3. Animales - Difícil
        4. Territorios - Fácil
        5. Territorios - Intermedio
        6. Territorios - Difícil
        7. Caricaturas - Fácil
        8. Caricaturas - Intermedio
        9. Caricaturas - Difícil
         */

        switch (nivel) {
            case 1:
                CambiarFondoPapel("/Multimedia/Minijuegos/Minijuego_1/Elementos_graficos/M1_C1_N1_FONDO.png");
                break;
            case 2:
                CambiarFondoPapel("/Multimedia/Minijuegos/Minijuego_1/Elementos_graficos/M1_C1_N2_FONDO.png");
                break;
            case 3:
                CambiarFondoPapel("/Multimedia/Minijuegos/Minijuego_1/Elementos_graficos/M1_C1_N3_FONDO.png");
                break;
            case 4:
                CambiarFondoPapel("/Multimedia/Minijuegos/Minijuego_1/Elementos_graficos/M1_C2_N1_FONDO.png");
                break;
            case 5:
                CambiarFondoPapel("/Multimedia/Minijuegos/Minijuego_1/Elementos_graficos/M1_C2_N2_FONDO.png");
                break;
            case 6:
                CambiarFondoPapel("/Multimedia/Minijuegos/Minijuego_1/Elementos_graficos/M1_C2_N3_FONDO.png");
                break;
            case 7:
                CambiarFondoPapel("/Multimedia/Minijuegos/Minijuego_1/Elementos_graficos/M1_C3_N1_FONDO.png");
                break;
            case 8:
                CambiarFondoPapel("/Multimedia/Minijuegos/Minijuego_1/Elementos_graficos/M1_C3_N2_FONDO.png");
                break;
            case 9:
                CambiarFondoPapel("/Multimedia/Minijuegos/Minijuego_1/Elementos_graficos/M1_C3_N3_FONDO.png");
                break;
            default:
                JOptionPane.showMessageDialog(null, "ERROR: No se pudo cargar el fondo.", "ERROR.", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void CambiarFondo(int dificultad) {
        Color verde = new Color(178, 197, 178);
        Color amarillo = new Color(239, 218, 154);
        Color rojo = new Color(218, 77, 88);

        if (dificultad == 1) {
            fondo.setBackground(verde);
        } else if (dificultad == 2) {
            fondo.setBackground(amarillo);
        } else if (dificultad == 3) {
            fondo.setBackground(rojo);
        } else {
            System.out.println("Error en la colocación de color.");
        }

    }

    //---------------- BOTONES ---------------------
    //----------------- CARGAR RESPUESTAS -----------
    //Cargar respuestas por medio de la base de datos.
    public void CargarRespuestas(int id_pregunta) {
        String sql = "SELECT texto_opcion, es_correcta FROM Opcion_respuesta WHERE id_pregunta = ? ORDER BY RAND()";

        try (PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id_pregunta);

            ResultSet rs = ps.executeQuery();

            JButton[] botones = {
                btnRespuesta1,
                btnRespuesta2,
                btnRespuesta3,
                btnRespuesta4

            };

            int i = 0;

            while (rs.next() && i < botones.length) {
                botones[i].setText(rs.getString("texto_opcion"));
                botones[i].putClientProperty("correcta", rs.getBoolean("es_correcta"));
                i++;
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    //-------------------------------- CAMBIAR DATOS -----------------------------
    //------------ ACIERTO --------------------------
    public void ModificarAcierto(int n) {
        //Rango del 1 al 5
        if (n >= 1 && n <= 5) {
            acierto.setText("Nivel: " + n);
        } else {
            System.out.println("Número de nivel inválido.");
        }
    }

    //-------------- DIFICULTAD ----------------------
    public void ModificarDificultad(int d) {
        switch (d) {
            case 1:
                dificultad.setText("Dificultad: Fácil");
                break;
            case 2:
                dificultad.setText("Dificultad: Intermedio");
                break;
            case 3:
                dificultad.setText("Dificultad: Difícil");
                break;
            default:
                System.out.println("Número de dificultad inválido.");
                break;
        }
    }

    //----------------------- CATEGORÍA --------------------
    public void ModificarCategoria(int id_categoria) {
        String sql = "SELECT nombre FROM Categoria WHERE id_categoria = ?";

        try (PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id_categoria);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                categoria.setText("Categoría: " + rs.getString("nombre"));
            } else {
                categoria.setText("Categoría no encontrada");
            }

        } catch (SQLException e) {
            System.out.println("Error al obtener categoría: " + e.getMessage());
        }
    }

    //---------------------------- PREGUNTA --------------------
    public void ModificarPregunta(int id_pregunta) {
        String sql = "SELECT pregunta FROM Pregunta WHERE id_pregunta = ?";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id_pregunta);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                titulo.setText(rs.getString("pregunta"));
            } else {
                titulo.setText("Pregunta no encontrada.");
            }

        } catch (SQLException e) {
            System.out.println("Error al obtener la pregunta: " + e.getMessage());
        }
    }

    //-------------------------------------- UNIFICAR ----------------------------------
    public void ConfiguracionNivel(int nivel) {
        /*
        NIVELES: 
        1. Animales - Fácil
        2. Animales - Intermedio
        3. Animales - Difícil
        4. Territorios - Fácil
        5. Territorios - Intermedio
        6. Territorios - Difícil
        7. Caricaturas - Fácil
        8. Caricaturas - Intermedio
        9. Caricaturas - Difícil
         */
        switch (nivel) {
            case 1:
                CambiarFondo(1);
                ModificarDificultad(1);
                CargarFondoPapel(1);
                ModificarCategoria(1);

                break;
            case 2:
                CambiarFondo(2);
                ModificarDificultad(2);
                CargarFondoPapel(2);
                ModificarCategoria(1);

                break;
            case 3:
                CambiarFondo(3);
                ModificarDificultad(3);
                CargarFondoPapel(3);
                ModificarCategoria(1);

                break;
            case 4:
                CambiarFondo(1);
                ModificarDificultad(1);
                CargarFondoPapel(4);
                ModificarCategoria(2);

                break;
            case 5:
                CambiarFondo(2);
                ModificarDificultad(2);
                CargarFondoPapel(5);
                ModificarCategoria(2);

                break;
            case 6:
                CambiarFondo(3);
                ModificarDificultad(3);
                CargarFondoPapel(6);
                ModificarCategoria(2);

                break;
            case 7:
                CambiarFondo(1);
                ModificarDificultad(1);
                CargarFondoPapel(7);
                ModificarCategoria(3);

                break;
            case 8:
                CambiarFondo(2);
                ModificarDificultad(2);
                CargarFondoPapel(8);
                ModificarCategoria(3);

                break;
            case 9:
                CambiarFondo(3);
                ModificarDificultad(3);
                CargarFondoPapel(9);
                ModificarCategoria(3);

                break;
            default:
                JOptionPane.showMessageDialog(null, "ERROR: No se pudo cambiar la dificultad.", "ERROR.", JOptionPane.ERROR_MESSAGE);
        }
    }
    
public void ModificarTiempo(int segundos) {

    tiempo.setText(String.format("%02d", segundos));

}

    public void respuestaSeleccionada(JButton boton) {
        // Se implementará en HiddenFox_Codigo
    }

    
    //-------------------- CORAZONES -----------------
    public void ModificarCorazones(int vidas) {
        
        

        JLabel[] corazones = {
            vida1,
            vida2,
            vida3
        };

        for (int i = 0; i < corazones.length; i++) {

            if (i < vidas) {
                //corazones[i].setIcon(lleno);
            } else {
                //corazones[i].setIcon(roto);
            }

        }

        if (vidas == 0) {
            JOptionPane.showMessageDialog(null, "Has perdido.");
        }
    }
    
    //------------------DESHABILITARLOS --------------------------
    public void HabilitarBotones(boolean estado) {

    btnRespuesta1.setEnabled(estado);
    btnRespuesta2.setEnabled(estado);
    btnRespuesta3.setEnabled(estado);
    btnRespuesta4.setEnabled(estado);

}
    
   

}
