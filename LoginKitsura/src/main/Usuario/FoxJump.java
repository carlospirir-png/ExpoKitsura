package main.Usuario;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.sql.*;
import javax.swing.*;
import main.conexion.Conexion;


public class FoxJump extends JFrame implements JuegoBase {

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

    private ImageIcon iconoCorazonLleno;
    private ImageIcon iconoCorazonRoto;

    private static final int MASCOTA_W      = 450;
    private static final int MASCOTA_H      = 450;
    private static final int MASCOTA_X_ORIG = (1880 - MASCOTA_W) / 2; 
    private static final int MASCOTA_Y_ORIG = 1080 - MASCOTA_H - 10;  

    private ImageIcon iconoKitsura;
    private ImageIcon iconoCambioDificultad;

    private JButton btnFinalizar, btnJugarDeNuevo;

    private Connection con;

    private enum Dificultad {
        FACIL, INTERMEDIO, DIFICIL
    }

    private static final int CORRECTAS_SUBIR = 5;

    private Dificultad dificultadActual = Dificultad.FACIL;

    private int correctasTotales = 0;

    private int idNivelActual;

    private int idPreguntaActual = 0;

    private boolean respuestaCorrecta;

    private int vidas = 3;

    private int totalPreguntas = 0;

    private final java.util.Set<Integer> preguntasVistas = new java.util.HashSet<>();

    private boolean finJuegoActivo = false;

    private int segundosRestantes;

    private javax.swing.Timer countdown;

    private boolean pistaMostradaEnPreguntaActual = false;


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
        resultado.mostrar();
    }

    private void resolverIdNivel() {

        String difStr = switch (dificultadActual) {
            case FACIL       -> "Fácil";
            case INTERMEDIO  -> "Intermedio";
            case DIFICIL     -> "Difícil";
        };

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
            } else {
                JOptionPane.showMessageDialog(this,
                        "No existe la categoría \"" + categoriaSeleccionada
                        + "\" con dificultad " + difStr);
                return;
            }
        } catch (SQLException ex) {
            mostrarError(ex);
        }

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

        Color colorFondo = switch (dificultadActual) {
            case FACIL      -> new Color(178, 197, 178);
            case INTERMEDIO -> new Color(210, 200, 140);
            case DIFICIL    -> new Color(210, 155, 155);
        };
        fondo.setBackground(colorFondo);
    }

    private void cargarPregunta() {

        detenerCountdown();

        pistaMostradaEnPreguntaActual = false;
        btnAyuda.setEnabled(true);
        btnAyuda.setText("¿Necesitas ayuda?");

        String exclusion = preguntasVistas.isEmpty()
                ? "0"
                : preguntasVistas.toString().replace("[", "").replace("]", "");

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

    private void mostrarPista() {

        if (idPreguntaActual == 0) {
            JOptionPane.showMessageDialog(this,
                    "Todavía no hay una pregunta activa.",
                    "Sin pista", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        String sql = """
            SELECT contenido, tipo
            FROM Ayuda
            WHERE id_pregunta = ?
            LIMIT 1
            """;

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idPreguntaActual);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String contenido = rs.getString("contenido");

                detenerCountdown();

                JOptionPane.showMessageDialog(
                        this,
                        "<html><body style='width:380px; font-size:13px;'>"
                        + "<b>💡 Pista:</b><br><br>"
                        + contenido
                        + "</body></html>",
                        "Pista — pregunta",
                        JOptionPane.INFORMATION_MESSAGE
                );

                if (!finJuegoActivo && segundosRestantes > 0) {
                    reanudarCountdown();
                }

            } else {
                JOptionPane.showMessageDialog(this,
                        "No hay pista disponible para esta pregunta.",
                        "Sin pista", JOptionPane.INFORMATION_MESSAGE);
            }

        } catch (SQLException ex) {
            mostrarError(ex);
        }
    }

    private void activarFinJuego() {

        if (finJuegoActivo) return;
        finJuegoActivo = true;

        detenerCountdown();

        titulo.setText("");
        tiempo.setText("");
        tiempoTexto.setVisible(false);

        try {
            ImageIcon finIcon = new ImageIcon(
                    getClass().getResource(
                            "/Multimedia/Minijuegos/Minijuego_2/Fin_Juego.png"));
            panelLago.setIcon(new ImageIcon(
                    finIcon.getImage().getScaledInstance(1050, 450, Image.SCALE_SMOOTH)));
        } catch (Exception e) {
            panelLago.setBackground(new Color(80, 140, 80));
        }

        nenufarVerdadero.setVisible(false);
        nenufarFalso.setVisible(false);

        panelLago.revalidate();
        panelLago.repaint();

        new javax.swing.Timer(800, e -> {
            ((javax.swing.Timer) e.getSource()).stop();

            if (vidas == 3) {
                VictoriaPerfecta vp = new VictoriaPerfecta(e2 -> {}, this);
                fadeTo(() -> setContentPane(vp.getFondo()), 400);
            } else {
                Victoria v = new Victoria(e2 -> {}, this);
                fadeTo(() -> setContentPane(v.getFondo()), 400);
            }
        }).start();
    }

    private void reiniciarJuego() {

        detenerCountdown();

        tiempoTexto.setVisible(true);
        tiempo.setText("00:00");

        nenufarVerdadero.setVisible(true);
        nenufarFalso.setVisible(true);

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

        vidas = 3;
        vida1.setIcon(iconoCorazonLleno);
        vida2.setIcon(iconoCorazonLleno);
        vida3.setIcon(iconoCorazonLleno);
        vida1.setVisible(true);
        vida2.setVisible(true);
        vida3.setVisible(true);

        mascota.setIcon(iconoKitsura);
        mascota.setBounds(MASCOTA_X_ORIG, MASCOTA_Y_ORIG, MASCOTA_W, MASCOTA_H);

        fondo.setBackground(new Color(178, 197, 178));

        dificultadActual  = Dificultad.FACIL;
        correctasTotales  = 0;
        idPreguntaActual  = 0;
        finJuegoActivo    = false;
        puntajeTotal      = 0;
        tiempoTotalJugado = 0;
        preguntasVistas.clear();

        pistaMostradaEnPreguntaActual = false;
        btnAyuda.setEnabled(true);
        btnAyuda.setText("¿Necesitas ayuda?");

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
                if (finJuegoActivo) return;
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

    private void reanudarCountdown() {

        if (segundosRestantes <= 0 || finJuegoActivo) return;

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
        if (puntos < 10) puntos = 10;
        return puntos;
    }

    private Point centroNenufar(JLabel nenufar) {
        int lagoX = panelLago.getX();
        int lagoY = panelLago.getY();
        int nx = nenufar.getX() + nenufar.getWidth()  / 2;
        int ny = nenufar.getY() + nenufar.getHeight() / 2;
        return new Point(lagoX + nx, lagoY + ny);
    }

    // ── FIX: SE ELIMINO setComponentZOrder Y fondo.repaint() DEL INTERIOR
    //        DEL TIMER. EL Z-ORDER SE FIJA UNA SOLA VEZ EN crearComponentes()
    //        Y NO SE TOCA DURANTE LA ANIMACION PARA EVITAR EL GRIS EN NENUFARES.
    private void animarMascota(int destX, int destY, int duracionMs, Runnable onFin) {

        int startX = mascota.getX();
        int startY = mascota.getY();
        int pasos    = 20;
        int intervalo = duracionMs / pasos;
        int[] paso = {0};

        javax.swing.Timer anim = new javax.swing.Timer(intervalo, null);
        anim.addActionListener(e -> {
            paso[0]++;
            double t = (double) paso[0] / pasos;
            int nx = startX + (int) ((destX - startX) * t);
            int ny = startY + (int) ((destY - startY) * t);
            mascota.setBounds(nx, ny, MASCOTA_W, MASCOTA_H);
            // SOLO REPINTAR LA MASCOTA, SIN TOCAR EL Z-ORDER EN CADA FRAME
            mascota.repaint();

            if (paso[0] >= pasos) {
                anim.stop();
                if (onFin != null) onFin.run();
            }
        });
        anim.start();
    }

    private void responder(boolean respuestaUsuario) {

        if (finJuegoActivo) return;

        detenerCountdown();

        nenufarVerdadero.setEnabled(false);
        nenufarFalso.setEnabled(false);

        boolean eligioVerdadero = (Boolean) nenufarVerdadero.getClientProperty("respuesta") == respuestaUsuario;
        JLabel nenufarElegido = eligioVerdadero ? nenufarVerdadero : nenufarFalso;

        Point destino = centroNenufar(nenufarElegido);
        int destX = destino.x - MASCOTA_W / 2;
        int destY = destino.y - MASCOTA_H / 2;

        animarMascota(destX, destY, 700, () -> {

            if (respuestaUsuario == respuestaCorrecta) {
                animarMascota(MASCOTA_X_ORIG, MASCOTA_Y_ORIG, 500, () -> {
                    nenufarVerdadero.setEnabled(true);
                    nenufarFalso.setEnabled(true);
                    procesarRespuestaCorrecta();
                });

            } else {
                mascota.setIcon(iconoCambioDificultad);

                new javax.swing.Timer(900, e2 -> {
                    ((javax.swing.Timer) e2.getSource()).stop();

                    mascota.setIcon(iconoKitsura);
                    animarMascota(MASCOTA_X_ORIG, MASCOTA_Y_ORIG, 500, () -> {
                        nenufarVerdadero.setEnabled(true);
                        nenufarFalso.setEnabled(true);
                        procesarRespuestaIncorrecta();
                    });
                }).start();
            }
        });
    }

    private void procesarRespuestaCorrecta() {

        correctasTotales++;

        int tiempoUsado = tiempoMaximoPregunta - segundosRestantes;
        if (tiempoUsado < 0) tiempoUsado = tiempoMaximoPregunta;

        puntajeTotal += calcularPuntosPorTiempo(tiempoUsado, tiempoMaximoPregunta);

        boolean subio = intentarSubirDificultad();

        if (!subio) {
            String msg = "¡Correcto! ("
                    + correctasTotales + "/" + CORRECTAS_SUBIR
                    + " para subir)";
            JOptionPane.showMessageDialog(this, msg);
            cargarPregunta();
        }
    }

    private void procesarRespuestaIncorrecta() {
        JOptionPane.showMessageDialog(this, "¡Incorrecto!");
        perderVida();
    }

    private boolean intentarSubirDificultad() {

        if (correctasTotales < CORRECTAS_SUBIR) return false;

        boolean subio = switch (dificultadActual) {
            case FACIL -> {
                dificultadActual = Dificultad.INTERMEDIO;
                yield true;
            }
            case INTERMEDIO -> {
                dificultadActual = Dificultad.DIFICIL;
                yield true;
            }
            case DIFICIL -> false;
        };

        if (subio) {
            correctasTotales = 0;
            idPreguntaActual = 0;
            resolverIdNivel();

            PantallaDificultad pd = new PantallaDificultad(

                    this, idNivelActual, vidas, puntajeTotal, false);
            fadeTo(() -> setContentPane(pd.getFondo()), 400);

           


        } else {
            correctasTotales = 0;
            activarFinJuego();
            detenerCountdown();
        }

        return subio;
    }

    private void perderVida() {

        vidas--;

        switch (vidas) {
            case 2 -> vida3.setIcon(iconoCorazonRoto);
            case 1 -> vida2.setIcon(iconoCorazonRoto);
            case 0 -> {
                vida1.setIcon(iconoCorazonRoto);
                new javax.swing.Timer(400, e -> {
                    ((javax.swing.Timer) e.getSource()).stop();
                    mostrarHaPerdido();
                }).start();
                return;
            }
        }

        cargarPregunta();
    }

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

    private void mostrarError(SQLException ex) {
        JOptionPane.showMessageDialog(this,
                "Error BD:\n" + ex.getMessage(),
                "Error", JOptionPane.ERROR_MESSAGE);
    }

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

    private void crearComponentes() {

        // ── PRECARGAR ICONOS ──────────────────────────────────────────────────
        try {
            Image imgKit = new ImageIcon(
                    getClass().getResource(
                            "/Multimedia/utiles/mascotaKitsura/imagen/KitsuraFlotador.png"))
                    .getImage().getScaledInstance(MASCOTA_W, MASCOTA_H, Image.SCALE_SMOOTH);
            iconoKitsura = new ImageIcon(imgKit);
        } catch (Exception e) {
            iconoKitsura = null;
        }

        try {
            Image imgCambio = new ImageIcon(
                    getClass().getResource(
                            "/Multimedia/utiles/mascotaKitsura/imagen/CAMBIO_DE_DIFICULTAD.png"))
                    .getImage().getScaledInstance(MASCOTA_W, MASCOTA_H, Image.SCALE_SMOOTH);
            iconoCambioDificultad = new ImageIcon(imgCambio);
        } catch (Exception e) {
            iconoCambioDificultad = null;
        }

        try {
            Image imgLleno = new ImageIcon(
                    getClass().getResource(
                            "/Multimedia/utiles/ElementosGraficos/imagenes/corazon.png"))
                    .getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH);
            iconoCorazonLleno = new ImageIcon(imgLleno);
        } catch (Exception e) {
            iconoCorazonLleno = null;
        }

        try {
            Image imgRoto = new ImageIcon(
                    getClass().getResource(
                            "/Multimedia/utiles/ElementosGraficos/imagenes/corazon-roto.png"))
                    .getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH);
            iconoCorazonRoto = new ImageIcon(imgRoto);
        } catch (Exception e) {
            iconoCorazonRoto = null;
        }

        // ── VIDAS ─────────────────────────────────────────────────────────────
        if (iconoCorazonLleno != null) {
            vida1 = new JLabel(iconoCorazonLleno);
            vida2 = new JLabel(iconoCorazonLleno);
            vida3 = new JLabel(iconoCorazonLleno);
        } else {
            vida1 = new JLabel("♥");
            vida2 = new JLabel("♥");
            vida3 = new JLabel("♥");
            vida1.setFont(fuente1.deriveFont(55f)); vida1.setForeground(Color.RED);
            vida2.setFont(fuente1.deriveFont(55f)); vida2.setForeground(Color.RED);
            vida3.setFont(fuente1.deriveFont(55f)); vida3.setForeground(Color.RED);
        }
        vida1.setBounds(70,  25, 60, 60);
        vida2.setBounds(135, 25, 60, 60);
        vida3.setBounds(200, 25, 60, 60);
        fondo.add(vida1);
        fondo.add(vida2);
        fondo.add(vida3);

        // ── BOTON DE AYUDA ────────────────────────────────────────────────────
        btnAyuda = new JButton("¿Necesitas ayuda?");
        btnAyuda.setBounds(60, 120, 280, 55);
        btnAyuda.setFocusPainted(false);
        btnAyuda.setFont(fuente2.deriveFont(18f));

        btnAyuda.addActionListener(e -> {
            if (pistaMostradaEnPreguntaActual) return;
            pistaMostradaEnPreguntaActual = true;
            btnAyuda.setEnabled(false);
            btnAyuda.setText("Pista usada");
            mostrarPista();
        });

        fondo.add(btnAyuda);

        // ── TITULO DE LA PREGUNTA ─────────────────────────────────────────────
        titulo = new JLabel("Cargando pregunta…", SwingConstants.CENTER);
        titulo.setBounds(500, 20, 900, 150);
        titulo.setFont(fuente2.deriveFont(25f));
        titulo.setForeground(Color.BLACK);
        titulo.setHorizontalAlignment(SwingConstants.CENTER);
        titulo.setVerticalAlignment(SwingConstants.CENTER);
        fondo.add(titulo);

        // ── ETIQUETAS DE TIEMPO ───────────────────────────────────────────────
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

        // ── PANEL DEL LAGO ────────────────────────────────────────────────────
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

        // ── NENUFAR DECORATIVO ────────────────────────────────────────────────
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

        // ── NENUFAR VERDADERO ─────────────────────────────────────────────────
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

        // ── NENUFAR FALSO ─────────────────────────────────────────────────────
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

        // ── ETIQUETAS INFORMATIVAS ────────────────────────────────────────────
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

        // ── MASCOTA ───────────────────────────────────────────────────────────
        // SE AGREGA AL FINAL Y SE FIJA SU Z-ORDER A 0 UNA SOLA VEZ.
        // EL panelLago SE FIJA EN INDICE 1 PARA GARANTIZAR QUE LA MASCOTA
        // SIEMPRE QUEDE DELANTE SIN NECESITAR TOCAR EL Z-ORDER EN LA ANIMACION.
        mascota = new JLabel();
        mascota.setBounds(MASCOTA_X_ORIG, MASCOTA_Y_ORIG, MASCOTA_W, MASCOTA_H);
        mascota.setLayout(null);

        if (iconoKitsura != null) {
            mascota.setIcon(iconoKitsura);
        } else {
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

        // FIX: Z-ORDER FIJADO UNA SOLA VEZ AQUI.
        // MASCOTA EN INDICE 0 (FRENTE), panelLago EN INDICE 1 (DETRAS DE MASCOTA).
        // ASI animarMascota() NO NECESITA TOCAR EL Z-ORDER EN CADA FRAME,
        // LO QUE ELIMINABA EL COLOR GRIS EN LOS NENUFARES DURANTE LA ANIMACION.
        fondo.setComponentZOrder(mascota, 0);
        fondo.setComponentZOrder(panelLago, 1);
    }

    // ── METODOS PUBLICOS ──────────────────────────────────────────────────────

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
            new SeAcaboVidas(this, e -> {}).setVisible(true);
            dispose();
        }, 400);
    }

    private void mostrarTiempoAgotado() {
        fadeTo(() -> {
            new SeAcaboTiempo(this, e -> {}).setVisible(true);
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

        int pasos    = 20;
        int intervalo = (duracionMs / 2) / pasos;
        int[] alpha  = {0};
        int[] fase   = {0};

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
        fadeTo(() -> setContentPane(fondo), 400);
        new javax.swing.Timer(420, e -> {
            ((javax.swing.Timer) e.getSource()).stop();
            cargarPregunta();
        }).start();
    }

    public static void main(String[] args) {
        new FoxJump("Animales");
    }
}