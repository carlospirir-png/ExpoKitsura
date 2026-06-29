package main.Usuario;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.sql.*;
import javax.swing.*;
import main.conexion.Conexion;

public class FoxJump extends JFrame implements JuegoBase {

    // ── Interfaz gráfica ──────────────────────────────────────────────────────
    private final JPanel fondo;
    private Font fuente1, fuente2;

    private JLabel vida1, vida2, vida3;
    private JLabel titulo, tiempoTexto, tiempo;
    private JLabel nivelLabel, dificultadLabel, categoriaLabel;
    private JLabel mascota, florMascota;
    private JLabel panelLago, nenufarFlor;
    private JLabel nenufarVerdadero, nenufarFalso;
    private JButton btnAyuda;
    private int puntajeTotal = 0;
    private int tiempoTotalJugado = 0;
    private int tiempoMaximoPregunta;
    private String categoriaSeleccionada;
    // ── Pantalla de fin de juego ──────────────────────────────────────────────
    /**
     * Botones que aparecen al llegar a la última pregunta.
     */
    private JButton btnFinalizar, btnJugarDeNuevo;

    // ── Base de datos ─────────────────────────────────────────────────────────
    private Connection con;

    // ── Estado de la partida ──────────────────────────────────────────────────
    private enum Dificultad {
        FACIL, INTERMEDIO, DIFICIL
    }

    /**
     * Respuestas correctas totales necesarias para subir de nivel.
     */
    private static final int CORRECTAS_SUBIR = 5;

    private Dificultad dificultadActual = Dificultad.FACIL;
    private int correctasTotales = 0;

    private int idNivelActual;
    private int idPreguntaActual = 0;
    private boolean respuestaCorrecta;
    private int vidas = 3;

    /**
     * Total de preguntas activas disponibles para el nivel actual. Se consulta
     * cada vez que cambia idNivelActual.
     */
    private int totalPreguntas = 0;

    /**
     * IDs de preguntas ya mostradas en el nivel actual (se limpia al cambiar
     * nivel).
     */
    private final java.util.Set<Integer> preguntasVistas = new java.util.HashSet<>();

    /**
     * True cuando ya se activó la pantalla de fin de juego.
     */
    private boolean finJuegoActivo = false;

    // ── Countdown ─────────────────────────────────────────────────────────────
    private int segundosRestantes;
    private javax.swing.Timer countdown;

    // ─────────────────────────────────────────────────────────────────────────
    // Constructor
    // ─────────────────────────────────────────────────────────────────────────
    public FoxJump(String categoria) {

        this.categoriaSeleccionada = categoria;

        cargarFuentes();

        fondo = new JPanel(null);
        fondo.setBackground(new Color(178, 197, 178));
        setContentPane(fondo);

        setTitle("Fox Jump!");
        setSize(1880, 1080);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        crearComponentes();

        // Pausar countdown cuando la ventana se minimiza
        addWindowListener(new java.awt.event.WindowAdapter() {

            @Override
            public void windowIconified(java.awt.event.WindowEvent e) {
                detenerCountdown();
            }

            @Override
            public void windowDeiconified(java.awt.event.WindowEvent e) {
                if (!finJuegoActivo && segundosRestantes > 0) {
                    reanudarCountdown();
                }
            }
        });

        // Pausar cuando la ventana se oculta
        addComponentListener(new java.awt.event.ComponentAdapter() {

            @Override
            public void componentHidden(java.awt.event.ComponentEvent e) {
                detenerCountdown();
            }

            @Override
            public void componentShown(java.awt.event.ComponentEvent e) {
                if (!finJuegoActivo && segundosRestantes > 0) {
                    reanudarCountdown();
                }
            }
        });

        setVisible(true);

        con = new Conexion().getConnection();

        if (con != null) {
            resolverIdNivel();
            cargarPregunta();
        } else {
            JOptionPane.showMessageDialog(
                    this,
                    "No se pudo conectar a la base de datos.",
                    "Error de conexión",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    public void mostrarResultadoConFade() {
        ResultadoFinal resultado = new ResultadoFinal(this, puntajeTotal, tiempoTotalJugado);
        resultado.mostrar();  // modal: FoxJump queda de fondo hasta que se cierre
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Resolución de id_nivel y conteo de preguntas disponibles
    // ─────────────────────────────────────────────────────────────────────────
    private void resolverIdNivel() {

        String difStr = switch (dificultadActual) {
            case FACIL ->
                "Fácil";
            case INTERMEDIO ->
                "Intermedio";
            case DIFICIL ->
                "Difícil";
        };

        // Obtener id_nivel según categoría y dificultad
        String sqlNivel = """
            SELECT cn.id_nivel
            FROM Configuracion_nivel cn
            INNER JOIN Categoria c
                ON cn.id_categoria = c.id_categoria
            INNER JOIN Minijuego m
                ON c.id_minijuego = m.id_minijuego
            WHERE m.nombre = ?
              AND c.nombre = ?
              AND cn.dificultad = ?
            LIMIT 1
            """;

        try (PreparedStatement ps = con.prepareStatement(sqlNivel)) {

            ps.setString(1, "Fox Jump!");
            ps.setString(2, categoriaSeleccionada);
            ps.setString(3, difStr);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                idNivelActual = rs.getInt("id_nivel");
                System.out.println("Categoría: " + categoriaSeleccionada);
                System.out.println("Dificultad: " + difStr);
                System.out.println("idNivelActual: " + idNivelActual);
            } else {
                JOptionPane.showMessageDialog(this,
                        "No existe la categoría \"" + categoriaSeleccionada
                        + "\" con dificultad " + difStr);
                return;
            }

        } catch (SQLException ex) {
            mostrarError(ex);
        }

        // Contar preguntas
        String sqlCount = """
            SELECT COUNT(*) AS total
            FROM Pregunta
            WHERE id_nivel = ?
              AND estado = 'activo'
            """;

        try (PreparedStatement ps = con.prepareStatement(sqlCount)) {

            ps.setInt(1, idNivelActual);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                totalPreguntas = rs.getInt("total");
            }

        } catch (SQLException ex) {
            mostrarError(ex);
        }

        preguntasVistas.clear();
        finJuegoActivo = false;

        nivelLabel.setText("Nivel: Fox Jump!");
        dificultadLabel.setText("Dificultad: " + difStr);
        categoriaLabel.setText("Categoría: " + categoriaSeleccionada);
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Carga de pregunta
    // ─────────────────────────────────────────────────────────────────────────
    private void cargarPregunta() {

        detenerCountdown();

        String exclusion = preguntasVistas.isEmpty()
                ? "0"
                : preguntasVistas.toString()
                        .replace("[", "")
                        .replace("]", "");

        String sql = """
            SELECT
                p.id_pregunta,
                p.pregunta,
                o.texto_opcion,
                o.es_correcta,
                cn.tiempo_limite,
                c.nombre AS categoria
            FROM Pregunta p
            INNER JOIN Opcion_respuesta o
                ON p.id_pregunta = o.id_pregunta
            INNER JOIN Configuracion_nivel cn
                ON p.id_nivel = cn.id_nivel
            INNER JOIN Categoria c
                ON cn.id_categoria = c.id_categoria
            WHERE p.id_nivel = ?
              AND p.estado = 'activo'
              AND p.id_pregunta NOT IN (""" + exclusion + """
            )
            ORDER BY RAND()
            LIMIT 1
            """;

        try (PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idNivelActual);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                idPreguntaActual = rs.getInt("id_pregunta");

                preguntasVistas.add(idPreguntaActual);

                respuestaCorrecta = rs.getBoolean("es_correcta");

                // Si ya no hay más preguntas
                if (preguntasVistas.size() >= totalPreguntas
                        && dificultadActual == Dificultad.DIFICIL) {
                    activarFinJuego();
                    return;
                }

                titulo.setText("<html><center>"
                        + rs.getString("pregunta")
                        + "</center></html>");

                titulo.setFont(fuente2.deriveFont(30f));

                categoriaLabel.setText("Categoría: " + rs.getString("categoria"));

                tiempoMaximoPregunta = rs.getInt("tiempo_limite");

                iniciarCountdown(tiempoMaximoPregunta);

            } else {

                if (!finJuegoActivo && dificultadActual == Dificultad.DIFICIL) {
                    activarFinJuego();
                }
                return;
            }

        } catch (SQLException ex) {
            mostrarError(ex);
            return;
        }

        generarPosiciones();
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Fin de juego — fondo Fin_Juego + botones
    // ─────────────────────────────────────────────────────────────────────────
    private void activarFinJuego() {

        if (finJuegoActivo) {
            return;
        }
        finJuegoActivo = true;

        detenerCountdown();

        // Limpiar pregunta y tiempo de la pantalla
        titulo.setText("");
        tiempo.setText("");
        tiempoTexto.setVisible(false);

        // Cambiar fondo del lago a Fin_Juego
        try {
            ImageIcon finIcon = new ImageIcon(
                    getClass().getResource(
                            "/Multimedia/Minijuegos/Minijuego_2/Fin_Juego.png"));
            panelLago.setIcon(new ImageIcon(
                    finIcon.getImage().getScaledInstance(1050, 450, Image.SCALE_SMOOTH)));
        } catch (Exception e) {
            panelLago.setBackground(new Color(80, 140, 80));
        }

        // Bloquear nénufares
        nenufarVerdadero.setVisible(false);
        nenufarFalso.setVisible(false);

        panelLago.revalidate();
        panelLago.repaint();

        // Espera un momento y luego muestra Victoria con fade
        new javax.swing.Timer(800, e -> {
            ((javax.swing.Timer) e.getSource()).stop();

            if (vidas == 3) {
                // ✅ CORRECCIÓN: se pasa ActionListener (sin uso) y JFrame
                VictoriaPerfecta vp = new VictoriaPerfecta(e2 -> {
                }, this);
                fadeTo(() -> setContentPane(vp.getFondo()), 400);
            } else {
                // ✅ CORRECCIÓN: se pasa ActionListener (sin uso) y JFrame
                Victoria v = new Victoria(e2 -> {
                }, this);
                fadeTo(() -> setContentPane(v.getFondo()), 400);
            }
        }).start();
    }

    /**
     * Reinicia todas las variables de estado y vuelve a cargar el juego desde
     * Fácil sin crear una nueva ventana.
     */
    private void reiniciarJuego() {

        detenerCountdown();

        // Restaurar visibilidad del tiempo
        tiempoTexto.setVisible(true);
        tiempo.setText("00:00");

        // Restaurar nénufares
        nenufarVerdadero.setVisible(true);
        nenufarFalso.setVisible(true);

        // Restaurar fondo original del lago
        try {
            ImageIcon lagoIcon = new ImageIcon(
                    getClass().getResource(
                            "/Multimedia/Minijuegos/Minijuego_2/Fondo.png"));
            panelLago.setIcon(new ImageIcon(
                    lagoIcon.getImage().getScaledInstance(1050, 450, Image.SCALE_SMOOTH)));
        } catch (Exception e) {
            panelLago.setBackground(new Color(126, 177, 190));
        }

        panelLago.revalidate();
        panelLago.repaint();

        // Restaurar vidas
        vidas = 3;
        vida1.setVisible(true);
        vida2.setVisible(true);
        vida3.setVisible(true);

        // Reiniciar estado
        dificultadActual = Dificultad.FACIL;
        correctasTotales = 0;
        idPreguntaActual = 0;
        finJuegoActivo = false;
        puntajeTotal = 0;
        tiempoTotalJugado = 0;
        preguntasVistas.clear();

        resolverIdNivel();
        cargarPregunta();
    }

    @Override
    public void jugarDeNuevo() {
        fadeTo(() -> setContentPane(fondo), 400);

        new javax.swing.Timer(420, e -> {
            ((javax.swing.Timer) e.getSource()).stop();
            reiniciarJuego();
            tiempoTexto.setVisible(true);
        }).start();
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Countdown
    // ─────────────────────────────────────────────────────────────────────────
    private void iniciarCountdown(int segundos) {

        segundosRestantes = segundos;
        actualizarLabelTiempo();

        countdown = new javax.swing.Timer(1000, e -> {

            if (finJuegoActivo) {
                detenerCountdown();
                return;
            }

            segundosRestantes--;
            tiempoTotalJugado++;
            actualizarLabelTiempo();

            tiempo.setForeground(segundosRestantes <= 5 ? Color.RED : Color.BLACK);

            if (segundosRestantes <= 0) {

                detenerCountdown();

                if (finJuegoActivo) {
                    return;
                }
                finJuegoActivo = true;

                mostrarTiempoAgotado();
            }
        });

        countdown.setInitialDelay(0);
        countdown.start();
    }

    private void detenerCountdown() {
        if (countdown != null && countdown.isRunning()) {
            countdown.stop();
        }
    }

    /**
     * Reanuda el countdown con los segundos que quedaban al pausar.
     */
    private void reanudarCountdown() {

        if (segundosRestantes <= 0 || finJuegoActivo) {
            return;
        }

        countdown = new javax.swing.Timer(1000, e -> {

            segundosRestantes--;
            actualizarLabelTiempo();

            tiempo.setForeground(segundosRestantes <= 5 ? Color.RED : Color.BLACK);

            if (segundosRestantes <= 0) {
                detenerCountdown();
                JOptionPane.showMessageDialog(FoxJump.this, "¡Tiempo agotado!");
                perderVida();
            }
        });

        countdown.setInitialDelay(0);
        countdown.start();
    }

    private void actualizarLabelTiempo() {
        int min = segundosRestantes / 60;
        int seg = segundosRestantes % 60;
        tiempo.setText(String.format("%02d:%02d", min, seg));
    }

    private int calcularPuntosPorTiempo(int tiempoUsado, int tiempoMaximo) {

        double porcentajeRapidez = 1.0 - ((double) tiempoUsado / tiempoMaximo);

        int puntos = (int) (100 * porcentajeRapidez);

        if (puntos < 10) {
            puntos = 10;
        }

        return puntos;
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Lógica de respuesta y progresión de dificultad
    // ─────────────────────────────────────────────────────────────────────────
    private void responder(boolean respuestaUsuario) {
        if (finJuegoActivo) {
            return;
        }

        detenerCountdown();

        if (respuestaUsuario == respuestaCorrecta) {

            correctasTotales++;

            int tiempoUsado = tiempoMaximoPregunta - segundosRestantes;

            if (tiempoUsado < 0) {
                tiempoUsado = tiempoMaximoPregunta;
            }

            int puntos = calcularPuntosPorTiempo(tiempoUsado, tiempoMaximoPregunta);

            puntajeTotal += puntos;

            boolean subio = intentarSubirDificultad();

            if (!subio) {
                String msg = "¡Correcto! ("
                        + correctasTotales + "/" + CORRECTAS_SUBIR
                        + " para subir)";
                JOptionPane.showMessageDialog(this, msg);
                cargarPregunta();
            }

        } else {
            JOptionPane.showMessageDialog(this, "¡Incorrecto!");
            perderVida();
        }
    }

    private boolean intentarSubirDificultad() {

        if (correctasTotales < CORRECTAS_SUBIR) {
            return false;
        }

        boolean subio = switch (dificultadActual) {
            case FACIL -> {
                dificultadActual = Dificultad.INTERMEDIO;
                yield true;
            }
            case INTERMEDIO -> {
                dificultadActual = Dificultad.DIFICIL;
                yield true;
            }
            case DIFICIL ->
                false;
        };

        if (subio) {
            correctasTotales = 0;
            idPreguntaActual = 0;

            resolverIdNivel();

            PantallaDificultad pd = new PantallaDificultad(
                    this, // JFrame ventanaAnterior
                    idNivelActual // nivel        
            );
            fadeTo(() -> {
                setContentPane(pd.getFondo());
            }, 400);

        } else {
            correctasTotales = 0;
            activarFinJuego();
            detenerCountdown();
        }

        return subio;
    }

    private String nombreDificultad() {
        return switch (dificultadActual) {
            case FACIL ->
                "Fácil";
            case INTERMEDIO ->
                "Intermedio";
            case DIFICIL ->
                "Difícil";
        };
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Vidas
    // ─────────────────────────────────────────────────────────────────────────
    private void perderVida() {

        vidas--;

        switch (vidas) {
            case 2 ->
                vida3.setVisible(false);
            case 1 ->
                vida2.setVisible(false);
            case 0 -> {
                vida1.setVisible(false);
                mostrarHaPerdido();
                return;
            }
        }

        cargarPregunta();
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Aleatorización de nénufares
    // ─────────────────────────────────────────────────────────────────────────
    private void generarPosiciones() {

        boolean verdaderoIzquierda = Math.random() < 0.5;

        JLabel lblIzq = (JLabel) nenufarVerdadero.getComponent(0);
        JLabel lblDer = (JLabel) nenufarFalso.getComponent(0);

        if (verdaderoIzquierda) {
            lblIzq.setText("Verdadero");
            lblDer.setText("Falso");
            nenufarVerdadero.putClientProperty("respuesta", true);
            nenufarFalso.putClientProperty("respuesta", false);
        } else {
            lblIzq.setText("Falso");
            lblDer.setText("Verdadero");
            nenufarVerdadero.putClientProperty("respuesta", false);
            nenufarFalso.putClientProperty("respuesta", true);
        }
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Utilidades
    // ─────────────────────────────────────────────────────────────────────────
    private void mostrarError(SQLException ex) {
        JOptionPane.showMessageDialog(this,
                "Error BD:\n" + ex.getMessage(),
                "Error", JOptionPane.ERROR_MESSAGE);
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Fuentes
    // ─────────────────────────────────────────────────────────────────────────
    private void cargarFuentes() {
        try {
            fuente1 = Font.createFont(Font.TRUETYPE_FONT,
                    getClass().getResourceAsStream("/fuentes/LettersForLearners.ttf"));
            fuente2 = Font.createFont(Font.TRUETYPE_FONT,
                    getClass().getResourceAsStream("/fuentes/KGPerfectPenmanship.ttf"));
        } catch (FontFormatException | IOException e) {
            fuente1 = new Font("Arial", Font.BOLD, 20);
            fuente2 = new Font("Arial", Font.PLAIN, 20);
        }
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Construcción de interfaz gráfica
    // ─────────────────────────────────────────────────────────────────────────
    private void crearComponentes() {

        //---------------- VIDAS ----------------
        try {
            ImageIcon icono = new ImageIcon(
                    getClass().getResource(
                            "/Multimedia/utiles/ElementosGraficos/imagenes/corazon.png"));
            Image imagen = icono.getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH);
            ImageIcon corazon = new ImageIcon(imagen);
            vida1 = new JLabel(corazon);
            vida2 = new JLabel(corazon);
            vida3 = new JLabel(corazon);
        } catch (Exception e) {
            vida1 = new JLabel("♥");
            vida2 = new JLabel("♥");
            vida3 = new JLabel("♥");
            vida1.setFont(fuente1.deriveFont(55f));
            vida1.setForeground(Color.RED);
            vida2.setFont(fuente1.deriveFont(55f));
            vida2.setForeground(Color.RED);
            vida3.setFont(fuente1.deriveFont(55f));
            vida3.setForeground(Color.RED);
        }
        vida1.setBounds(70, 25, 60, 60);
        vida2.setBounds(135, 25, 60, 60);
        vida3.setBounds(200, 25, 60, 60);
        fondo.add(vida1);
        fondo.add(vida2);
        fondo.add(vida3);

        //---------------- AYUDA ----------------
        btnAyuda = new JButton("¿Necesitas ayuda?");
        btnAyuda.setBounds(60, 120, 280, 55);
        btnAyuda.setFocusPainted(false);
        btnAyuda.setFont(fuente2.deriveFont(18f));
        fondo.add(btnAyuda);

        //---------------- TITULO ----------------
        titulo = new JLabel("Cargando pregunta…", SwingConstants.CENTER);
        titulo.setBounds(500, 20, 900, 150);
        titulo.setFont(fuente2.deriveFont(25f));
        titulo.setForeground(Color.BLACK);
        titulo.setHorizontalAlignment(SwingConstants.CENTER);
        titulo.setVerticalAlignment(SwingConstants.CENTER);
        fondo.add(titulo);

        //---------------- TIEMPO ----------------
        tiempoTexto = new JLabel("Tiempo restante:");
        tiempoTexto.setBounds(1450, 70, 300, 40);
        tiempoTexto.setFont(fuente2.deriveFont(25f));
        tiempoTexto.setForeground(Color.BLACK);
        fondo.add(tiempoTexto);

        tiempo = new JLabel("00:00", SwingConstants.CENTER);
        tiempo.setBounds(1440, 120, 320, 60);
        tiempo.setOpaque(true);
        tiempo.setBackground(Color.WHITE);
        tiempo.setForeground(Color.BLACK);
        tiempo.setFont(fuente2.deriveFont(28f));
        fondo.add(tiempo);

        //---------------- PANEL LAGO ----------------
        panelLago = new JLabel();
        panelLago.setBounds(420, 250, 1050, 450);
        panelLago.setLayout(null);
        try {
            ImageIcon lagoIcon = new ImageIcon(
                    getClass().getResource("/Multimedia/Minijuegos/Minijuego_2/Fondo.png"));
            panelLago.setIcon(new ImageIcon(
                    lagoIcon.getImage().getScaledInstance(1050, 450, Image.SCALE_SMOOTH)));
        } catch (Exception e) {
            panelLago.setOpaque(true);
            panelLago.setBackground(new Color(126, 177, 190));
        }
        fondo.add(panelLago);

        //---------------- NENUFAR FLOR ----------------
        nenufarFlor = new JLabel();
        try {
            ImageIcon icono = new ImageIcon(
                    getClass().getResource("/Multimedia/Minijuegos/Minijuego_2/Nenufar_Flor.png"));
            nenufarFlor.setIcon(new ImageIcon(
                    icono.getImage().getScaledInstance(95, 95, Image.SCALE_SMOOTH)));
        } catch (Exception e) {
            nenufarFlor.setText("Flor");
        }
        nenufarFlor.setBounds(60, 60, 90, 90);
        panelLago.add(nenufarFlor);

        //---------------- NENUFAR VERDADERO (izquierdo) ----------------
        nenufarVerdadero = new JLabel();
        nenufarVerdadero.setLayout(null);
        try {
            ImageIcon icono = new ImageIcon(
                    getClass().getResource("/Multimedia/Minijuegos/Minijuego_2/Nenufar.png"));
            nenufarVerdadero.setIcon(new ImageIcon(
                    icono.getImage().getScaledInstance(240, 180, Image.SCALE_SMOOTH)));
        } catch (Exception e) {
            nenufarVerdadero.setOpaque(true);
            nenufarVerdadero.setBackground(Color.GREEN);
        }
        JLabel lblIzq = new JLabel("Verdadero", SwingConstants.CENTER);
        lblIzq.setFont(fuente1.deriveFont(Font.BOLD, 22f));
        lblIzq.setBounds(0, 60, 240, 90);
        nenufarVerdadero.add(lblIzq);
        nenufarVerdadero.setBounds(180, 140, 240, 180);
        nenufarVerdadero.putClientProperty("respuesta", true);
        nenufarVerdadero.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                responder((Boolean) nenufarVerdadero.getClientProperty("respuesta"));
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                nenufarVerdadero.setCursor(new Cursor(Cursor.HAND_CURSOR));
                nenufarVerdadero.setBounds(175, 135, 250, 190);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                nenufarVerdadero.setBounds(180, 140, 240, 180);
            }
        });
        panelLago.add(nenufarVerdadero);

        //---------------- NENUFAR FALSO (derecho) ----------------
        nenufarFalso = new JLabel();
        nenufarFalso.setLayout(null);
        try {
            ImageIcon icono = new ImageIcon(
                    getClass().getResource("/Multimedia/Minijuegos/Minijuego_2/Nenufar.png"));
            nenufarFalso.setIcon(new ImageIcon(
                    icono.getImage().getScaledInstance(240, 180, Image.SCALE_SMOOTH)));
        } catch (Exception e) {
            nenufarFalso.setOpaque(true);
            nenufarFalso.setBackground(Color.GREEN);
        }
        JLabel lblDer = new JLabel("Falso", SwingConstants.CENTER);
        lblDer.setFont(fuente1.deriveFont(Font.BOLD, 22f));
        lblDer.setBounds(0, 60, 240, 90);
        nenufarFalso.add(lblDer);
        nenufarFalso.setBounds(650, 140, 240, 180);
        nenufarFalso.putClientProperty("respuesta", false);
        nenufarFalso.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                responder((Boolean) nenufarFalso.getClientProperty("respuesta"));
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                nenufarFalso.setCursor(new Cursor(Cursor.HAND_CURSOR));
                nenufarFalso.setBounds(645, 135, 250, 190);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                nenufarFalso.setBounds(650, 140, 240, 180);
            }
        });
        panelLago.add(nenufarFalso);

        //---------------- NIVEL / DIFICULTAD / CATEGORÍA ----------------
        nivelLabel = new JLabel("Nivel: Fox Jump!");
        nivelLabel.setBounds(80, 740, 300, 40);
        nivelLabel.setFont(fuente2.deriveFont(25f));
        nivelLabel.setForeground(Color.BLACK);
        fondo.add(nivelLabel);

        dificultadLabel = new JLabel("Dificultad: Fácil");
        dificultadLabel.setBounds(80, 790, 300, 40);
        dificultadLabel.setFont(fuente2.deriveFont(25f));
        dificultadLabel.setForeground(Color.BLACK);
        fondo.add(dificultadLabel);

        categoriaLabel = new JLabel("Categoría: ---");
        categoriaLabel.setBounds(80, 840, 300, 40);
        categoriaLabel.setFont(fuente2.deriveFont(25f));
        categoriaLabel.setForeground(Color.BLACK);
        fondo.add(categoriaLabel);

        //---------------- MASCOTA ----------------
        mascota = new JLabel();
        mascota.setBounds(1450, 480, 450, 450);
        mascota.setLayout(null);
        try {
            ImageIcon icono = new ImageIcon(
                    getClass().getResource(
                            "/Multimedia/utiles/mascotaKitsura/imagen/KitsuraFlotador.png"));
            mascota.setIcon(new ImageIcon(
                    icono.getImage().getScaledInstance(450, 450, Image.SCALE_SMOOTH)));
        } catch (Exception e) {
            mascota.setText("Mascota");
        }

        florMascota = new JLabel();
        try {
            ImageIcon iconoFlor = new ImageIcon(
                    getClass().getResource("/Multimedia/Minijuegos/Minijuego_2/Flor.png"));
            florMascota.setIcon(new ImageIcon(
                    iconoFlor.getImage().getScaledInstance(120, 120, Image.SCALE_SMOOTH)));
        } catch (Exception e) {
            florMascota.setText("Flor");
        }
        florMascota.setBounds(210, 40, 120, 120);
        mascota.add(florMascota);
        fondo.add(mascota);
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Métodos públicos para integración con PantallaDificultad
    // ─────────────────────────────────────────────────────────────────────────
    public JPanel getFondo() {
        return fondo;
    }

    @Override
    public int getPuntajeTotal() {
        return puntajeTotal;
    }

    @Override
    public int getTiempoTotalJugado() {
        return tiempoTotalJugado;
    }

    @Override
    public void reiniciar() {
        jugarDeNuevo();
    }

    @Override
    public void irAlMenu() {
        fadeTo(() -> {
            dispose();
            new MenuMinijuegos();
        }, 400);
    }

    @Override
    public JFrame getFrame() {
        return this;
    }

    private void mostrarHaPerdido() {
        fadeTo(() -> {
            new SeAcaboVidas(this, e -> {
            }).setVisible(true);
            dispose();
        }, 400);
    }

    private void mostrarTiempoAgotado() {
        fadeTo(() -> {
            // ✅ CORRECCIÓN: se pasa JuegoBase y ActionListener (sin uso)
            new SeAcaboTiempo(this, e -> {
            }).setVisible(true);
            dispose();
        }, 400);
    }

    private void fadeTo(Runnable onMidpoint, int duracionMs) {

        JPanel overlay = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                g.setColor(new Color(0, 0, 0, getBackground().getAlpha()));
                g.fillRect(0, 0, getWidth(), getHeight());
            }
        };

        overlay.setOpaque(false);
        overlay.setBackground(new Color(0, 0, 0, 0));

        JPanel glass = (JPanel) getGlassPane();
        glass.setLayout(new BorderLayout());
        glass.add(overlay, BorderLayout.CENTER);
        glass.setVisible(true);

        int pasos = 20;
        int intervalo = (duracionMs / 2) / pasos;
        int[] alpha = {0};
        int[] fase = {0};

        javax.swing.Timer fadeTimer = new javax.swing.Timer(intervalo, null);

        fadeTimer.addActionListener(e -> {

            if (fase[0] == 0) {
                alpha[0] += 255 / pasos;

                if (alpha[0] >= 255) {
                    alpha[0] = 255;
                    overlay.setBackground(new Color(0, 0, 0, alpha[0]));
                    overlay.repaint();

                    onMidpoint.run();
                    revalidate();
                    repaint();

                    fase[0] = 1;
                }

            } else {
                alpha[0] -= 255 / pasos;

                if (alpha[0] <= 0) {
                    alpha[0] = 0;
                    overlay.setBackground(new Color(0, 0, 0, alpha[0]));
                    overlay.repaint();

                    glass.remove(overlay);
                    glass.setVisible(false);

                    fadeTimer.stop();
                    return;
                }
            }

            overlay.setBackground(new Color(0, 0, 0, alpha[0]));
            overlay.repaint();
        });

        fadeTimer.start();
    }

    public void continuarDespuesDeDificultad() {

        fadeTo(() -> {
            setContentPane(fondo);
        }, 400);

        new javax.swing.Timer(420, e -> {
            ((javax.swing.Timer) e.getSource()).stop();
            cargarPregunta();
        }).start();
    }

    public static void main(String[] args) {
        new FoxJump("Animales");
    }
}
