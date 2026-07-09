// ============== FOX JUMP ==============
package main.Usuario;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.sql.*;
import javax.swing.*;
import main.conexion.Conexion;

// CLASE PRINCIPAL DEL MINIJUEGO FOX JUMP!
public class FoxJump extends JFrame implements JuegoBase {

    // ── COMPONENTES GRAFICOS PRINCIPALES 
    // PANEL RAIZ QUE CONTIENE TODOS LOS ELEMENTOS VISUALES DEL JUEGO
    private final JPanel fondo;

    // FUENTES PERSONALIZADAS CARGADAS DESDE LOS RECURSOS DEL PROYECTO
    // fuente1 = LettersForLearners (USADA EN TEXTOS DE NENUFARES Y VIDAS)
    // fuente2 = KGPerfectPenmanship (USADA EN TITULOS, ETIQUETAS Y BOTONES)
    private Font fuente1, fuente2;

    // ── SISTEMA DE VIDAS DINAMICO ────────────────────────────────────────────
    // CAMBIA ESTE VALOR PARA QUE EL JUEGO TENGA MAS O MENOS VIDAS/CORAZONES.
    // ESTE ES EL TOPE ABSOLUTO: "vidas" NUNCA PUEDE SUPERAR ESTE NUMERO,
    // SIN IMPORTAR DESDE DONDE SE INTENTE SUMAR VIDAS (VER agregarVidas()).
    private int maxVidas;

    // ES LA VARIABLE QUE CONTIENE LA INFORMACIÓN DE LA BASE DE DATOS
    // BUG CORREGIDO: antes se declaraba sin inicializar y provocaba un
    // NullPointerException apenas se abría el minijuego (vidasDAO.obtenerVidas
    // se llamaba sobre un objeto null en el constructor).
    private VidasDAO vidasDAO = new VidasDAO();

    // CANTIDAD DE CORAZONES POR FILA. SI MAX_VIDAS NO ES MULTIPLO EXACTO,
    // LA ULTIMA FILA SIMPLEMENTE QUEDA INCOMPLETA (SE ACOMODAN LOS QUE SOBREN).
    private static final int CORAZONES_POR_FILA = 5;

    // ARREGLO DE LABELS DE CORAZONES, GENERADO DINAMICAMENTE SEGUN MAX_VIDAS
    private JLabel[] corazones;

    // LABELS UTILIZADOS EN LA PANTALLA DEL JUEGO 
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

    // ── ICONOS PRECARGADOS PARA RENDIMIENTO 
    // SE CARGAN UNA VEZ EN crearComponentes() PARA NO REPETIR LA OPERACION
    // CADA VEZ QUE SE NECESITEN DURANTE EL JUEGO
    private ImageIcon iconoCorazonLleno;
    private ImageIcon iconoCorazonRoto;

    // ── POSICION Y DIMENSIONES ORIGINALES DE LA MASCOTA 
    // ESTAS CONSTANTES SON EL PUNTO DE RETORNO DESPUES DE CADA ANIMACION
    // LA MASCOTA SIEMPRE VUELVE AQUI ANTES DE CARGAR LA SIGUIENTE PREGUNTA
    private static final int MASCOTA_W = 450;
    private static final int MASCOTA_H = 450;
    private static final int MASCOTA_X_ORIG = (1880 - MASCOTA_W) / 2;
    private static final int MASCOTA_Y_ORIG = 1080 - MASCOTA_H - 10;

    // ICONO NORMAL DE KITSURA 
    private ImageIcon iconoKitsura;

    // ICONO QUE APARECE  CUANDO EL JUGADOR RESPONDE MAL
    private ImageIcon iconoCambioDificultad;

    // BOTONES DE LA PANTALLA FINAL 
    private JButton btnFinalizar, btnJugarDeNuevo;

    // ── CONEXION A BASE DE DATOS 
    // CONSULTAS SQL DEL JUEGO
    private Connection con;

    // ── SISTEMA DE DIFICULTAD
    // REGISTROS DISTINTOS EN Configuracion_nivel
    private enum Dificultad {
        FACIL, INTERMEDIO, DIFICIL
    }

    // CANTIDAD DE RESPUESTAS CORRECTAS NECESARIAS PARA SUBIR DE DIFICULTAD
    private static final int CORRECTAS_SUBIR = 5;

    // DIFICULTAD EN LA QUE EMPIEZA Y VA CAMBIANDO LA PARTIDA
    private Dificultad dificultadActual = Dificultad.FACIL;

    // CONTADOR DE ACIERTOS EN LA DIFICULTAD ACTUAL (SE RESETEA AL SUBIR)
    private int correctasTotales = 0;

    // ID DEL NIVEL ACTUAL EN LA TABLA Configuracion_nivel
    private int idNivelActual;

    // ID DE LA PREGUNTA QUE SE ESTA MOSTRANDO EN ESTE MOMENTO
    private int idPreguntaActual = 0;

    // TRUE SI LA OPCION CORRECTA DE LA PREGUNTA ACTUAL ES "VERDADERO"
    private boolean respuestaCorrecta;

    // VIDAS ACTUALES DEL JUGADOR, EMPIEZA EN MAX_VIDAS Y DISMINUYE CON CADA ERROR
    private int vidas;

    // TOTAL DE PREGUNTAS DISPONIBLES EN EL NIVEL ACTUAL (ACTIVAS EN BD)
    private int totalPreguntas = 0;

    // CONJUNTO DE IDs DE PREGUNTAS YA MOSTRADAS EN ESTA DIFICULTAD
    private final java.util.Set<Integer> preguntasVistas = new java.util.HashSet<>();

    // FLAG PARA SABER SI YA SE ACTIVO LA PANTALLA FINAL
    private boolean finJuegoActivo = false;

    // FLAG QUE EVITA QUE SE PROCESE MAS DE UNA RESPUESTA A LA VEZ
    // (setEnabled EN UN JLabel NO BLOQUEA MouseListener POR SI SOLO, POR ESO
    // NECESITAMOS ESTE CANDADO EXPLICITO ADEMAS DE DESHABILITAR VISUALMENTE)
    private boolean procesandoRespuesta = false;

    // ── SISTEMA DE CUENTA REGRESIVA 
    // SEGUNDOS QUE QUEDAN PARA RESPONDER LA PREGUNTA ACTUAL
    private int segundosRestantes;

    // TIMER DE SWING QUE DESCUENTA UN SEGUNDO CADA VEZ QUE DISPARA
    private javax.swing.Timer countdown;

    // ── CONTROL DEL BOTON DE PISTA ────────────────────────────────────────────
    // CADA PREGUNTA TIENE DERECHO A UNA SOLA PISTA, ESTE FLAG LO CONTROLA
    // SE RESETEA A false EN cargarPregunta() PARA CADA NUEVA PREGUNTA
    private boolean pistaMostradaEnPreguntaActual = false;

    // CONSTRUCTOR***********
    /**
     * INICIALIZA EL JUEGO CON LA CATEGORIA RECIBIDA DESDE EL MENU. 1. CREAR
     * TODOS LOS COMPONENTES GRAFICOS 2. REGISTRAR LISTENERS DE VENTANA (PARA
     * PAUSAR AL MINIMIZAR) 3. HACER VISIBLE EL FRAME 4. CONECTAR A LA BD Y
     * CARGAR LA PRIMERA PREGUNTA 5. CARGA TODAS LAS FUENTES
     */
    public FoxJump(String categoria) {

        this.categoriaSeleccionada = categoria;

        // EL JUEFO COMIENZA EN FÁCIL, CUANDO SE QUIERE PASAR A OTRO NIVEL, SE VA ACTUALIZAR EL VALOR
        // BUG CORREGIDO: el nombre debe coincidir EXACTAMENTE con el de la tabla Minijuego
        // ("Fox Jump!", con espacio). Antes decía "FoxJump!" y nunca encontraba coincidencia,
        // por lo que siempre caía en el valor por defecto sin avisar del error.
        maxVidas = vidasDAO.obtenerVidas("Fox Jump!", categoriaSeleccionada, "Fácil");

        if (maxVidas < 1) {
            maxVidas = 3;
        }

        // OBTENER LAS VIDAS
        vidas = maxVidas;

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

        // PAUSAR EL COUNTDOWN SI EL USUARIO MINIMIZA LA VENTANA
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowIconified(java.awt.event.WindowEvent e) {
                detenerCountdown();
            }

            @Override
            public void windowDeiconified(java.awt.event.WindowEvent e) {
                if (!finJuegoActivo && !procesandoRespuesta && segundosRestantes > 0) {
                    reanudarCountdown();
                }
            }
        });

        // TIENE LA MISMA ACCION  PARA CUANDO EL COMPONENTE SE OCULTA 
        // (POR EJEMPLO AL NAVEGAR ENTRE PANELES SIN CERRAR EL FRAME)
        addComponentListener(new java.awt.event.ComponentAdapter() {
            @Override
            public void componentHidden(java.awt.event.ComponentEvent e) {
                detenerCountdown();
            }

            @Override
            public void componentShown(java.awt.event.ComponentEvent e) {
                if (!finJuegoActivo && !procesandoRespuesta && segundosRestantes > 0) {
                    reanudarCountdown();
                }
            }
        });

        setVisible(true);

        // LA CONEXION SE ESTABLECE DESPUES DE setVisible() PARA QUE EL FRAME
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

//          MUESTRA LA PANTALLA DE RESULTADO FINAL CON EFECTO DE TRANSICION.
    public void mostrarResultadoConFade() {
        ResultadoFinal resultado = new ResultadoFinal(this, puntajeTotal, tiempoTotalJugado);
        resultado.mostrar();
    }

    // RESOLUCION DEL NIVEL EN BASE DE DATOS************************
    /**
     * BUSCA EN LA DB EL id_nivel QUE CORRESPONDE A LA COMBINACION ACTUAL DE:
     * MINIJUEGO ("FOX JUMP!") + CATEGORIA + DIFICULTAD
     *
     * SI NO EXISTE EL NIVEL EN BD, MUESTRA UN DIALOGO DE ERROR Y RETORNA SIN
     * HACER NADA. ESTO PUEDE PASAR SI LOS DATOS DE LA BD NO ESTAN BIEN
     * CONFIGURADOS.
     */
    private void resolverIdNivel() {

        // CONVERTIR EL ENUM A LA CADENA QUE USA LA DB 
        String difStr = switch (dificultadActual) {
            case FACIL ->
                "Fácil";
            case INTERMEDIO ->
                "Intermedio";
            case DIFICIL ->
                "Difícil";
        };

        maxVidas = vidasDAO.obtenerVidas(
                "Fox Jump!",
                categoriaSeleccionada,
                difStr);

        if (maxVidas < 1) {
            maxVidas = 3;
        }
        // El jugador inicia con el máximo de vidas configurado
        vidas = maxVidas;
        
        reconstruirCorazones();
        
        // CONSULTA QUE CRUZA TRES TABLAS PARA ENCONTRAR EL NIVEL CORRECTO
        // SE FILTRA POR NOMBRE DEL MINIJUEGO, NOMBRE DE CATEGORIA Y DIFICULTAD
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

        // CONTAR CUANTAS PREGUNTAS ACTIVAS TIENE ESTE NIVEL
        // SE USA MAS ADELANTE PARA SABER CUANDO ACTIVAR EL FIN DEL JUEGO
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

        // LIMPIAR EL HISTORIAL DE PREGUNTAS VISTAS AL CAMBIAR DE NIVEL
        preguntasVistas.clear();
        finJuegoActivo = false;

        // ACTUALIZAR LAS ETIQUETAS DE INFORMACION EN PANTALLA
        nivelLabel.setText("Nivel: Fox Jump!");
        dificultadLabel.setText("Dificultad: " + difStr);
        categoriaLabel.setText("Categoría: " + categoriaSeleccionada);

        // CAMBIAR EL COLOR DE FONDO SEGUN LA DIFICULTAD ACTUAL
        // VERDE = FACIL, AMARILLO = INTERMEDIO, ROJO = DIFICIL
        Color colorFondo = switch (dificultadActual) {
            case FACIL ->
                new Color(178, 197, 178);
            case INTERMEDIO ->
                new Color(239, 218, 154);
            case DIFICIL ->
                new Color(227, 157, 139);
        };
        fondo.setBackground(colorFondo);
    }

    // CARGA DE PREGUNTAS****************************
    /**
     * OBTIENE UNA PREGUNTA ALEATORIA DE LA BD QUE AUN NO SE HAYA MOSTRADO EN
     * ESTA RONDA Y ACTUALIZA TODOS LOS ELEMENTOS DE LA PANTALLA.
     *
     * EL METODO HACE LO SIGUIENTE EN ORDEN: 1. DETIENE EL COUNTDOWN ANTERIOR 2.
     * RESETEA EL BOTON DE AYUDA PARA LA NUEVA PREGUNTA 3. EJECUTA LA CONSULTA Y
     * LLENA LOS CAMPOS DE LA UI 4. INICIA EL NUEVO COUNTDOWN 7. ALEATORIZA LA
     * POSICION DE LOS NENUFARES
     */
    private void cargarPregunta() {

        detenerCountdown();

        // ASEGURAR QUE LOS NENUFARES QUEDEN LIBRES PARA LA NUEVA PREGUNTA
        procesandoRespuesta = false;
        desbloquearNenufares();

        // RESETEAR EL ESTADO DEL BOTON DE AYUDA PARA ESTA NUEVA PREGUNTA
        // CADA PREGUNTA TIENE DERECHO A QUE EL JUGADOR USE LA PISTA UNA VEZ
        pistaMostradaEnPreguntaActual = false;
        btnAyuda.setEnabled(true);
        btnAyuda.setText("¿Necesitas ayuda?");

        // SI NO HAY PREGUNTAS VISTAS, SE USA "0" PARA QUE LA SINTAXIS SEA VALIDA
        String exclusion = preguntasVistas.isEmpty()
                ? "0"
                : preguntasVistas.toString().replace("[", "").replace("]", "");

        // LA CONSULTA TRAE UNA SOLA PREGUNTA ALEATORIA QUE:
        // - PERTENEZCA AL NIVEL ACTUAL
        // - ESTE EN ESTADO 'activo'
        // - NO HAYA SIDO MOSTRADA YA EN ESTA RONDA 
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

                // es_correcta INDICA SI LA OPCION "VERDADERO" ES LA RESPUESTA CORRECTA
                respuestaCorrecta = rs.getBoolean("es_correcta");

                // SI YA SE VIERON TODAS LAS PREGUNTAS DEL NIVEL DIFICIL, TERMINAR
                if (preguntasVistas.size() >= totalPreguntas
                        && dificultadActual == Dificultad.DIFICIL) {
                    activarFinJuego();
                    return;
                }

                // MOSTRAR EL TEXTO DE LA PREGUNTA EN EL LABEL CENTRAL
                titulo.setText("<html><center>"
                        + rs.getString("pregunta")
                        + "</center></html>");
                titulo.setFont(fuente2.deriveFont(30f));
                categoriaLabel.setText("Categoría: " + rs.getString("categoria"));

                // INICIAR EL CONTADOR CON EL TIEMPO DEFINIDO PARA ESTE NIVEL
                tiempoMaximoPregunta = rs.getInt("tiempo_limite");
                iniciarCountdown(tiempoMaximoPregunta);

            } else {
                // NO HAY MAS PREGUNTAS DISPONIBLES PARA ESTE NIVEL
                if (!finJuegoActivo && dificultadActual == Dificultad.DIFICIL) {
                    activarFinJuego();
                }
                return;
            }
        } catch (SQLException ex) {
            mostrarError(ex);
            return;
        }

        // ALEATORIZAR CUAL NENUFAR QUEDA A LA IZQUIERDA Y CUAL A LA DERECHA
        generarPosiciones();
    }

    // SISTEMA DE PISTAS (BOTON DE AYUDA)********************************
    /**
     * CONSULTA LA TABLA Ayuda EN DB Y MUESTRA LA PISTA CORRESPONDIENTE A LA
     * PREGUNTA QUE SE ESTA MOSTRANDO EN ESTE MOMENTO.
     *
     * MIENTRAS LA VENTANA DE PISTA (PistasTexto) ESTA ABIERTA, EL COUNTDOWN SE
     * PAUSA. AL CERRARSE (BOTON "SALIR" -> dispose()), SE DISPARA windowClosed
     * Y AHI SE REANUDA EL COUNTDOWN SI CORRESPONDE.
     *
     * SI LA PREGUNTA NO TIENE PISTA REGISTRADA EN DB, SE MUESTRA UN MENSAJE
     * INDICANDO QUE NO ESTA DISPONIBLE.
     */
    private void mostrarPista() {

        // VERIFICACION DE SEGURIDAD: NO DEBERIA LLEGAR ACA SIN UNA PREGUNTA ACTIVA
        if (idPreguntaActual == 0) {
            JOptionPane.showMessageDialog(this,
                    "Todavía no hay una pregunta activa.",
                    "Sin pista", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        // BUSCAR LA PISTA QUE CORRESPONDE A ESTA PREGUNTA EN PARTICULAR
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

                // PAUSAR EL TIEMPO MIENTRAS EL JUGADOR LEE LA PISTA
                detenerCountdown();

                // MOSTRAR LA PISTA EN LA VENTANA PERSONALIZADA PistasTexto
                PistasTexto ventanaPista = new PistasTexto(contenido);

                // REANUDAR EL COUNTDOWN SOLO CUANDO EL JUGADOR CIERRE LA VENTANA
                // (Y SOLO SI EL JUEGO SIGUE ACTIVO Y NO SE ESTA PROCESANDO UNA RESPUESTA)
                ventanaPista.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosed(java.awt.event.WindowEvent e) {
                        if (!finJuegoActivo && !procesandoRespuesta && segundosRestantes > 0) {
                            reanudarCountdown();
                        }
                    }
                });

            } else {
                // ESTA PREGUNTA NO TIENE PISTA REGISTRADA EN LA TABLA Ayuda
                JOptionPane.showMessageDialog(this,
                        "No hay pista disponible para esta pregunta.",
                        "Sin pista", JOptionPane.INFORMATION_MESSAGE);
            }

        } catch (SQLException ex) {
            mostrarError(ex);
        }
    }

    // FIN DE JUEGO Y REINICIO*******************************************
    /**
     * ACTIVA LA SECUENCIA DE FIN DE JUEGO CUANDO EL JUGADOR COMPLETA TODAS LAS
     * PREGUNTAS DEL NIVEL DIFICIL.
     *
     * TRANSICIONA A LA PANTALLA DE VICTORIA (PERFECTA O NORMAL SEGUN LAS VIDAS
     * QUE LE QUEDARON AL JUGADOR).
     *
     * EL FLAG finJuegoActivo EVITA QUE ESTE METODO SE LLAME DOS VECES SI ALGUN
     * EVENTO INTENTA DISPARARLO DE NUEVO.
     */
    private void activarFinJuego() {

        if (finJuegoActivo) {
            return;
        }
        finJuegoActivo = true;

        detenerCountdown();
        bloquearNenufares();

        // LIMPIAR LOS TEXTOS DE PREGUNTA Y TIEMPO ANTES DE MOSTRAR LA IMAGEN FINAL
        titulo.setText("");
        tiempo.setText("");
        tiempoTexto.setVisible(false);

        // CAMBIAR LA IMAGEN DEL LAGO POR LA IMAGEN DE FIN DE JUEGO
        try {
            ImageIcon finIcon = new ImageIcon(
                    getClass().getResource(
                            "/Multimedia/Minijuegos/Minijuego_2/Fin_Juego.png"));
            panelLago.setIcon(new ImageIcon(
                    finIcon.getImage().getScaledInstance(1050, 450, Image.SCALE_SMOOTH)));
        } catch (Exception e) {
            panelLago.setBackground(new Color(80, 140, 80));
        }

        // OCULTAR LOS NENUFARES PORQUE YA NO SE NECESITAN
        nenufarVerdadero.setVisible(false);
        nenufarFalso.setVisible(false);

        panelLago.revalidate();
        panelLago.repaint();

        // ESPERAR 800ms PARA QUE EL JUGADOR VEA LA IMAGEN ANTES DE TRANSICIONAR
        new javax.swing.Timer(800, e -> {
            ((javax.swing.Timer) e.getSource()).stop();

            // SI CONSERVO TODAS LAS VIDAS = VICTORIA PERFECTA, SINO = VICTORIA NORMAL
            if (vidas == maxVidas) {
                VictoriaPerfecta vp = new VictoriaPerfecta(e2 -> {
                }, this);
                fadeTo(() -> setContentPane(vp.getFondo()), 400);
            } else {
                Victoria v = new Victoria(e2 -> {
                }, this);
                fadeTo(() -> setContentPane(v.getFondo()), 400);
            }
        }).start();
    }

    /**
     * RESETEA TODOS LOS VALORES DEL JUEGO A SU ESTADO INICIAL PARA EMPEZAR UNA
     * PARTIDA NUEVA DESDE CERO.
     */
    private void reiniciarJuego() {

        detenerCountdown();

        // RESTAURAR VISIBILIDAD DEL CONTADOR DE TIEMPO
        tiempoTexto.setVisible(true);
        tiempo.setText("00:00");

        // VOLVER A MOSTRAR LOS NENUFARES QUE SE OCULTARON AL FINAL
        nenufarVerdadero.setVisible(true);
        nenufarFalso.setVisible(true);

        // RESTAURAR LA IMAGEN ORIGINAL DEL LAGO (QUITAR LA DE FIN DE JUEGO)
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

        // RESTAURAR TODOS LOS CORAZONES LLENOS (LA CANTIDAD LA DEFINE MAX_VIDAS)
        // SE ASIGNA DIRECTO A MAX_VIDAS, NUNCA MAS ALTO, PORQUE MAX_VIDAS ES EL TOPE
        vidas = maxVidas;
        for (JLabel corazon : corazones) {
            corazon.setIcon(iconoCorazonLleno);
            corazon.setVisible(true);
        }

        // VOLVER LA MASCOTA A SU POSICION E ICONO ORIGINALES
        mascota.setIcon(iconoKitsura);
        mascota.setBounds(MASCOTA_X_ORIG, MASCOTA_Y_ORIG, MASCOTA_W, MASCOTA_H);

        // RESTAURAR EL COLOR DE FONDO VERDE DEL NIVEL FACIL
        fondo.setBackground(new Color(178, 197, 178));

        // REINICIAR TODOS LOS CONTADORES Y FLAGS DE LA PARTIDA
        dificultadActual = Dificultad.FACIL;
        correctasTotales = 0;
        idPreguntaActual = 0;
        finJuegoActivo = false;
        procesandoRespuesta = false;
        puntajeTotal = 0;
        tiempoTotalJugado = 0;
        preguntasVistas.clear();

        // ASEGURAR QUE LOS NENUFARES QUEDEN HABILITADOS PARA LA NUEVA PARTIDA
        desbloquearNenufares();

        // RESETEAR EL BOTON DE AYUDA TAMBIEN
        pistaMostradaEnPreguntaActual = false;
        btnAyuda.setEnabled(true);
        btnAyuda.setText("¿Necesitas ayuda?");

        // RESOLVER EL NIVEL INICIAL Y CARGAR LA PRIMERA PREGUNTA
        resolverIdNivel();
        cargarPregunta();
    }

    /**
     * IMPLEMENTACION DE JuegoBase: REINICIA EL JUEGO CON ANIMACION DE FADE.
     */
    @Override
    public void jugarDeNuevo() {
        fadeTo(() -> setContentPane(fondo), 400);
        new javax.swing.Timer(420, e -> {
            ((javax.swing.Timer) e.getSource()).stop();
            reiniciarJuego();
            tiempoTexto.setVisible(true);
        }).start();
    }

    // SISTEMA DE CUENTA REGRESIVA**********************************************
    /**
     * INICIA UN NUEVO COUNTDOWN CON EL NUMERO DE SEGUNDOS INDICADO. CADA
     * SEGUNDO ACTUALIZA EL LABEL DE TIEMPO Y SUMA UN SEGUNDO AL TOTAL.
     *
     */
    private void iniciarCountdown(int segundos) {

        // POR SI QUEDABA UN COUNTDOWN VIEJO CORRIENDO (SEGURIDAD EXTRA)
        detenerCountdown();

        segundosRestantes = segundos;
        actualizarLabelTiempo();

        countdown = new javax.swing.Timer(1000, e -> {

            // RED DE SEGURIDAD: SI EL JUEGO YA TERMINO O SE ESTA PROCESANDO
            // UNA RESPUESTA, DETENER EL TIMER SIN HACER NADA MAS
            if (finJuegoActivo || procesandoRespuesta) {
                detenerCountdown();
                return;
            }

            segundosRestantes--;
            tiempoTotalJugado++;
            actualizarLabelTiempo();

            // PONER EL TEXTO EN ROJO CUANDO QUEDAN POCOS SEGUNDOS
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

    /**
     * DETIENE EL COUNTDOWN SI ESTA CORRIENDO. SE LLAMA ANTES DE MOSTRAR
     * DIALOGOS, PISTAS, O INICIAR ANIMACIONES.
     */
    private void detenerCountdown() {
        if (countdown != null && countdown.isRunning()) {
            countdown.stop();
        }
    }

    /**
     * CREA Y ARRANCA UN NUEVO COUNTDOWN CON LOS SEGUNDOS QUE QUEDABAN. SE USA
     * AL REANUDAR DESPUES DE QUE EL JUGADOR CIERRA UN DIALOGO DE PISTA O CUANDO
     * VUELVE A ABRIR LA VENTANA QUE HABIA MINIMIZADO.
     *
     * NO HACE NADA SI EL TIEMPO YA SE AGOTO, EL JUEGO TERMINO, O SE ESTA
     * PROCESANDO UNA RESPUESTA EN ESTE MOMENTO.
     */
    private void reanudarCountdown() {

        if (segundosRestantes <= 0 || finJuegoActivo || procesandoRespuesta) {
            return;
        }

        // POR SI QUEDO UN COUNTDOWN VIEJO CORRIENDO, LO DETENEMOS ANTES DE CREAR OTRO
        // (EVITA TENER DOS TIMERS EN PARALELO QUE PUEDAN DISPARAR EL GAME OVER)
        detenerCountdown();

        countdown = new javax.swing.Timer(1000, e -> {

            // MISMA RED DE SEGURIDAD QUE EN iniciarCountdown()
            if (finJuegoActivo || procesandoRespuesta) {
                detenerCountdown();
                return;
            }

            segundosRestantes--;
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

    /**
     * REINICIA LOS SEGUNDOS RESTANTES EN MM:SS Y LOS MUESTRA EN EL LABEL. SE
     * LLAMA CADA VEZ QUE EL TIMER CAMBIA (O SEA, CADA SEGUNDO).
     */
    private void actualizarLabelTiempo() {
        int min = segundosRestantes / 60;
        int seg = segundosRestantes % 60;
        tiempo.setText(String.format("%02d:%02d", min, seg));
    }

    /**
     * CALCULA LOS PUNTOS A GANAR SEGUN QUE TAN RAPIDO RESPONDIO EL JUGADOR.
     *
     * SI EL JUGADOR RESPONDIO EN 5 SEGUNDOS O MENOS, SE LE OTORGA EL PUNTAJE
     * MAXIMO (100) DIRECTAMENTE, SIN IMPORTAR EL TIEMPO LIMITE DE LA PREGUNTA.
     * ESTO PREMIA LA RAPIDEZ EXTREMA POR ENCIMA DE LA FORMULA PROPORCIONAL
     * NORMAL.
     *
     * SI TARDO MAS DE 5 SEGUNDOS, SE APLICA LA FORMULA PROPORCIONAL: MAXIMO DE
     * 100 PUNTOS SI RESPONDE CASI AL INSTANTE, Y UN MINIMO DE 10 PUNTOS AUNQUE
     * TARDE TODO EL TIEMPO DISPONIBLE.
     *
     * @param tiempoUsado SEGUNDOS QUE TARDO EN RESPONDER
     * @param tiempoMaximo SEGUNDOS MAXIMOS DISPONIBLES PARA ESTA PREGUNTA
     * @return PUNTOS CALCULADOS (ENTRE 10 Y 100)
     */
    private int calcularPuntosPorTiempo(int tiempoUsado, int tiempoMaximo) {

        // RESPUESTA SUPER RAPIDA: PUNTAJE MAXIMO GARANTIZADO
        if (tiempoUsado <= 5) {
            return 100;
        }

        double porcentajeRapidez = 1.0 - ((double) tiempoUsado / tiempoMaximo);
        int puntos = (int) (100 * porcentajeRapidez);
        if (puntos < 10) {
            puntos = 10;
        }
        return puntos;
    }

    // =========================================================================
    // LOGICA DE RESPUESTA Y ANIMACION
    // =========================================================================
    /**
     * CALCULA EL CENTRO ABSOLUTO DE UN NENUFAR EN COORDENADAS DEL PANEL FONDO.
     *
     * NECESARIO PORQUE LOS NENUFARES VIVEN DENTRO DE panelLago, ENTONCES SUS
     * COORDENADAS SON RELATIVAS AL LAGO, NO AL PANEL DE FONDO.
     *
     * @param nenufar EL JLabel DEL NENUFAR OBJETIVO
     * @return PUNTO CON LAS COORDENADAS ABSOLUTAS DEL CENTRO DEL NENUFAR
     */
    private Point centroNenufar(JLabel nenufar) {
        int lagoX = panelLago.getX();
        int lagoY = panelLago.getY();
        int nx = nenufar.getX() + nenufar.getWidth() / 2;
        int ny = nenufar.getY() + nenufar.getHeight() / 2;
        return new Point(lagoX + nx, lagoY + ny);
    }

    /**
     * ANIMA LA MASCOTA DESDE SU POSICION ACTUAL HASTA LA POSICION DESEADA EN EL
     * TIEMPO INDICADO, USANDO UNA FORMULA
     *
     * SE DIVIDE EL MOVIMIENTO EN 20 PASOS Y SE USA UN TIMER DE SWING PARA
     * ACTUALIZAR LA POSICION EN CADA PASO.
     *
     * DURANTE LA ANIMACION SE FUERZA EL Z-ORDER DE LA MASCOTA A 0 (FRENTE) PARA
     * QUE NUNCA QUEDE TAPADA POR OTRO COMPONENTE.
     *
     * @param destX COORDENADA X DESTINO (EN COORDENADAS DE fondo)
     * @param destY COORDENADA Y DESTINO (EN COORDENADAS DE fondo)
     * @param duracionMs DURACION TOTAL DE LA ANIMACION EN MILISEGUNDOS
     * @param onFin CODIGO A EJECUTAR AL TERMINAR LA ANIMACION (PUEDE SER NULL)
     */
    private void animarMascota(int destX, int destY, int duracionMs, Runnable onFin) {

        int startX = mascota.getX();
        int startY = mascota.getY();
        int pasos = 20;
        int intervalo = duracionMs / pasos;
        int[] paso = {0};

        javax.swing.Timer anim = new javax.swing.Timer(intervalo, null);
        anim.addActionListener(e -> {
            paso[0]++;
            double t = (double) paso[0] / pasos;
            int nx = startX + (int) ((destX - startX) * t);
            int ny = startY + (int) ((destY - startY) * t);
            mascota.setBounds(nx, ny, MASCOTA_W, MASCOTA_H);

            // MANTENER LA MASCOTA AL FRENTE DURANTE TODA LA ANIMACION
            fondo.setComponentZOrder(mascota, 0);
            fondo.repaint();

            if (paso[0] >= pasos) {
                anim.stop();
                if (onFin != null) {
                    onFin.run();
                }
            }
        });
        anim.start();
    }

    /**
     * BLOQUEA LOS DOS NENUFARES PARA QUE NO SE PUEDAN VOLVER A CLICKEAR
     * MIENTRAS HAY UNA ANIMACION O RESPUESTA EN PROCESO.
     *
     * IMPORTANTE: nenufarVerdadero Y nenufarFalso SON JLabel, NO JButton, ASI
     * QUE setEnabled(false) NO IMPIDE POR SI SOLO QUE EL MouseListener SIGA
     * RECIBIENDO CLICKS. POR ESO SIEMPRE SE COMBINA CON EL FLAG
     * procesandoRespuesta Y CON LA VALIDACION isEnabled() DENTRO DE CADA
     * LISTENER.
     */
    private void bloquearNenufares() {
        nenufarVerdadero.setEnabled(false);
        nenufarFalso.setEnabled(false);
    }

    /**
     * DESBLOQUEA LOS DOS NENUFARES PARA QUE VUELVAN A ACEPTAR CLICKS. SE LLAMA
     * CUANDO TERMINA LA ANIMACION DE RESPUESTA O AL CARGAR UNA PREGUNTA NUEVA.
     */
    private void desbloquearNenufares() {
        nenufarVerdadero.setEnabled(true);
        nenufarFalso.setEnabled(true);
    }

    /**
     * PROCESA EL CLIC DEL USUARIO EN UN NENUFAR.
     *
     * FLUJO CUANDO ES CORRECTO: 1. ANIMAR HACIA EL NENUFAR ELEGIDO (700ms) 2.
     * ANIMAR DE REGRESO AL ORIGEN (500ms) 3. LLAMAR A
     * procesarRespuestaCorrecta()
     *
     * FLUJO CUANDO ES INCORRECTO: 1. ANIMAR HACIA EL NENUFAR ELEGIDO (700ms) 2.
     * MOSTRAR ICONO DE CAMBIO DE DIFICULTAD POR 900ms 3. RESTAURAR ICONO NORMAL
     * Y ANIMAR DE REGRESO (500ms) 4. LLAMAR A procesarRespuestaIncorrecta()
     *
     * LOS NENUFARES SE DESHABILITAN DURANTE TODA LA ANIMACION PARA EVITAR QUE
     * EL USUARIO HAGA CLICK VARIAS VECES.
     *
     * @param respuestaUsuario LA RESPUESTA BOOLEANA QUE ELIGIO EL JUGADOR
     */
    private void responder(boolean respuestaUsuario) {

        // DOBLE CANDADO: SI EL JUEGO TERMINO O YA HAY UNA RESPUESTA EN PROCESO,
        // IGNORAR POR COMPLETO ESTE CLICK (EVITA DOBLE CLICK Y CLICK EN LOS 2 A LA VEZ)
        if (finJuegoActivo || procesandoRespuesta) {
            return;
        }
        procesandoRespuesta = true;

        detenerCountdown();

        // DESHABILITAR NENUFARES PARA EVITAR VARIOS CLICKS DURANTE LA ANIMACION
        bloquearNenufares();

        // DETERMINAR A CUAL DE LOS DOS NENUFARES SE DIRIGIRA LA MASCOTA
        boolean eligioVerdadero = (Boolean) nenufarVerdadero.getClientProperty("respuesta") == respuestaUsuario;
        JLabel nenufarElegido = eligioVerdadero ? nenufarVerdadero : nenufarFalso;

        Point destino = centroNenufar(nenufarElegido);
        int destX = destino.x - MASCOTA_W / 2;
        int destY = destino.y - MASCOTA_H / 2;

        // FASE 1: IR HACIA EL NENUFAR
        animarMascota(destX, destY, 700, () -> {

            if (respuestaUsuario == respuestaCorrecta) {
                // RESPUESTA CORRECTA: VOLVER A LA POSICION ORIGINAL Y CONTINUAR
                animarMascota(MASCOTA_X_ORIG, MASCOTA_Y_ORIG, 500, () -> {
                    procesandoRespuesta = false;
                    desbloquearNenufares();
                    procesarRespuestaCorrecta();
                });

            } else {
                // RESPUESTA INCORRECTA: MOSTRAR ICONO DE ZORRO ASUSTADO
                mascota.setIcon(iconoCambioDificultad);

                new javax.swing.Timer(900, e2 -> {
                    ((javax.swing.Timer) e2.getSource()).stop();

                    // RESTAURAR ICONO NORMAL Y VOLVER A LA POSICION ORIGINAL 
                    mascota.setIcon(iconoKitsura);
                    animarMascota(MASCOTA_X_ORIG, MASCOTA_Y_ORIG, 500, () -> {
                        procesandoRespuesta = false;
                        desbloquearNenufares();
                        procesarRespuestaIncorrecta();
                    });
                }).start();
            }
        });
    }

    /**
     * MANEJA TODA LA LOGICA POSTERIOR A UNA RESPUESTA CORRECTA: - SUMA UNO A
     * LOS ACIERTOS - CALCULA Y ACUMULA EL PUNTAJE SEGUN EL TIEMPO UTILIZADO -
     * INTENTA SUBIR DE DIFICULTAD - SI NO SUBIO, MUESTRA MENSAJE Y CARGA LA
     * SIGUIENTE PREGUNTA
     */
    private void procesarRespuestaCorrecta() {

        correctasTotales++;

        // CALCULAR CUANTOS SEGUNDOS TARDO EL JUGADOR EN RESPONDER
        int tiempoUsado = tiempoMaximoPregunta - segundosRestantes;
        if (tiempoUsado < 0) {
            tiempoUsado = tiempoMaximoPregunta;
        }

        puntajeTotal += calcularPuntosPorTiempo(tiempoUsado, tiempoMaximoPregunta);

        // INTENTAR SUBIR DE DIFICULTAD (SI LLEGO A 5 CORRECTAS)
        // SI SUBIO, LA SIGUIENTE PREGUNTA SE CARGA DESDE continuarDespuesDeDificultad()
        boolean subio = intentarSubirDificultad();

        if (!subio) {
            // MOSTRAR PROGRESO HACIA EL SIGUIENTE NIVEL Y CONTINUAR
            String msg = "¡Correcto! ("
                    + correctasTotales + "/" + CORRECTAS_SUBIR
                    + " para subir)";
            JOptionPane.showMessageDialog(this, msg);
            cargarPregunta();
        }
    }

    /**
     * MANEJA LA LOGICA POSTERIOR A UNA RESPUESTA INCORRECTA: MUESTRA EL MENSAJE
     * DE ERROR Y LLAMA A perderVida().
     */
    private void procesarRespuestaIncorrecta() {
        JOptionPane.showMessageDialog(this, "¡Incorrecto!");
        perderVida();
    }

    /**
     * VERIFICA SI EL JUGADOR ACUMULO SUFICIENTES ACIERTOS PARA SUBIR DE NIVEL.
     *
     * SI CORRESPONDE SUBIR: - CAMBIA dificultadActual AL SIGUIENTE NIVEL -
     * RESETEA EL CONTADOR DE CORRECTAS - RESUELVE EL NUEVO id_nivel EN BD -
     * MUESTRA LA PantallaDificultad CON TRANSICION
     *
     * SI YA ESTA EN DIFICIL Y LLEGO A 5, SE ACTIVA EL FIN DE JUEGO
     * DIRECTAMENTE.
     *
     * @return TRUE SI SE SUBIO DE DIFICULTAD, FALSE SI NO CORRESPONDIA SUBIR
     */
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

            // MOSTRAR PANTALLA INTERMEDIA QUE ANUNCIA EL CAMBIO DE DIFICULTAD
            PantallaDificultad pd = new PantallaDificultad(
                    this, idNivelActual);
            fadeTo(() -> setContentPane(pd.getFondo()), 400);

        } else {
            // YA ESTABA EN DIFICIL Y COMPLETO 5 CORRECTAS: FIN DEL JUEGO
            correctasTotales = 0;
            activarFinJuego();
            detenerCountdown();
        }

        return subio;
    }

    // =========================================================================
    // SISTEMA DE VIDAS
    // =========================================================================
    /**
     * DESCUENTA UNA VIDA Y ACTUALIZA EL CORAZON CORRESPONDIENTE.
     *
     * EL ORDEN DE LOS CORAZONES ROTOS ES DE DERECHA A IZQUIERDA. COMO
     * corazones[] SE LLENA DE IZQUIERDA A DERECHA (INDICE 0 = PRIMER CORAZON),
     * Y vidas VA BAJANDO DESDE MAX_VIDAS HASTA 0, EL INDICE DEL CORAZON QUE SE
     * ROMPE EN CADA PASO ES SIEMPRE EL NUEVO VALOR DE vidas (DESPUES DE RESTAR
     * 1). ESTO FUNCIONA SIN IMPORTAR CUANTAS VIDAS TENGA EL JUEGO (MAX_VIDAS).
     *
     * CUANDO vidas LLEGA A 0, SE MUESTRA LA PANTALLA DE DERROTA CON UNA ESPERA
     * DE 400ms PARA QUE EL JUGADOR VEA EL ULTIMO CORAZON ROMPERSE ANTES DE
     * SALIR.
     */
    private void perderVida() {

        vidas--;

        // ROMPER EL CORAZON QUE CORRESPONDE A ESTA VIDA PERDIDA
        if (vidas >= 0 && vidas < corazones.length) {
            corazones[vidas].setIcon(iconoCorazonRoto);
        }

        if (vidas <= 0) {
            bloquearNenufares();
            new javax.swing.Timer(400, e -> {
                ((javax.swing.Timer) e.getSource()).stop();
                mostrarHaPerdido();
            }).start();
            return;
        }

        // SI AUN LE QUEDAN VIDAS, CARGAR LA SIGUIENTE PREGUNTA
        cargarPregunta();
    }

    /**
     * SUMA VIDAS AL JUGADOR (POR EJEMPLO, SI EN EL FUTURO SE AGREGA UNA
     * RECOMPENSA O POWER-UP QUE OTORGUE VIDAS EXTRA).
     *
     * ESTE ES EL UNICO LUGAR DONDE "vidas" PUEDE AUMENTAR, Y ESTA BLINDADO CON
     * Math.min() PARA QUE NUNCA, BAJO NINGUNA CIRCUNSTANCIA, SUPERE MAX_VIDAS.
     * SI SE INTENTA SUMAR MAS DE LO QUE CABE, EL EXCEDENTE SIMPLEMENTE SE
     * IGNORA.
     *
     * TAMBIEN SE ENCARGA DE RESTAURAR VISUALMENTE LOS CORAZONES ROTOS QUE
     * VUELVEN A ESTAR "LLENOS" TRAS LA RECUPERACION.
     *
     * @param cantidad CUANTAS VIDAS SE INTENTAN AGREGAR (DEBE SER POSITIVO)
     */
    public void agregarVidas(int cantidad) {

        if (cantidad <= 0) {
            return;
        }

        int vidasAnteriores = vidas;

        // TOPE DURO: NUNCA PASAR DE MAX_VIDAS, SIN IMPORTAR "cantidad"
        vidas = Math.min(maxVidas, vidas + cantidad);

        // RESTAURAR LOS CORAZONES QUE PASARON DE ROTOS A LLENOS
        for (int i = vidasAnteriores; i < vidas; i++) {
            if (i >= 0 && i < corazones.length) {
                corazones[i].setIcon(iconoCorazonLleno);
            }
        }
    }

    // =========================================================================
    // ALEATORIZACION DE NENUFARES
    // =========================================================================
    /**
     * DECIDE ALEATORIAMENTE SI "VERDADERO" QUEDA A LA IZQUIERDA O DERECHA.
     *
     * ACTUALIZA TANTO EL TEXTO VISIBLE DEL NENUFAR COMO LA clientProperty QUE
     * GUARDA SU VALOR BOOLEANO REAL. ASI, AL HACER CLIC, EL LISTENER PUEDE
     * SABER QUE RESPUESTA REPRESENTA CADA NENUFAR SIN IMPORTAR EN QUE POSICION
     * QUEDO.
     *
     * ESTO EVITA QUE EL JUGADOR APRENDA LA POSICION EN LUGAR DE LA RESPUESTA.
     */
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

    // =========================================================================
    // UTILIDADES GENERALES
    // =========================================================================
    /**
     * MUESTRA UN DIALOGO DE ERROR CON EL MENSAJE DE LA EXCEPCION SQL. SE LLAMA
     * DESDE TODOS LOS BLOQUES CATCH DE LAS CONSULTAS A BD.
     *
     * @param ex LA EXCEPCION SQL CAPTURADA
     */
    private void mostrarError(SQLException ex) {
        JOptionPane.showMessageDialog(this,
                "Error BD:\n" + ex.getMessage(),
                "Error", JOptionPane.ERROR_MESSAGE);
    }

    /**
     * CARGA LAS FUENTES PERSONALIZADAS DESDE LOS RECURSOS DEL PROYECTO. SI
     * ALGUNA FALLA, SE USA ARIAL COMO RESPALDO PARA NO ROMPER LA UI.
     *
     * SE LLAMA LO PRIMERO EN EL CONSTRUCTOR PORQUE LOS COMPONENTES DEPENDEN DE
     * ESTAS FUENTES PARA CONFIGURAR SU TEXTO.
     */
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

    // =========================================================================
    // CONSTRUCTOR DE LA INTERFAZ GRAFICA
    // =========================================================================
    /**
     * CREA Y POSICIONA TODOS LOS COMPONENTES VISUALES DEL JUEGO.
     *
     * SE ORGANIZA EN ESTE ORDEN: 1. PRECARGAR ICONOS (MASCOTA Y CORAZONES) PARA
     * REUTILIZARLOS SIN RECARGAR 2. CREAR LAS VIDAS (CORAZONES) DE FORMA
     * DINAMICA SEGUN MAX_VIDAS, EN FILAS 3. BOTON DE AYUDA CON SU LISTENER 4.
     * TITULO DE LA PREGUNTA 5. ETIQUETAS DE TIEMPO 6. PANEL DEL LAGO CON SUS
     * NENUFARES 7. ETIQUETAS DE NIVEL, DIFICULTAD Y CATEGORIA 8. MASCOTA (SE
     * AGREGA AL FINAL PARA QUEDAR ENCIMA DE TODO)
     *
     */
    private void crearComponentes() {

        // PRECARGAR ICONO NORMAL DE KITSURA
        try {
            Image imgKit = new ImageIcon(
                    getClass().getResource(
                            "/Multimedia/utiles/mascotaKitsura/imagen/KitsuraFlotador.png"))
                    .getImage().getScaledInstance(MASCOTA_W, MASCOTA_H, Image.SCALE_SMOOTH);
            iconoKitsura = new ImageIcon(imgKit);
        } catch (Exception e) {
            iconoKitsura = null;
        }

        // PRECARGAR ICONO DE REACCION (SE MUESTRA AL RESPONDER MAL)
        try {
            Image imgCambio = new ImageIcon(
                    getClass().getResource(
                            "/Multimedia/utiles/mascotaKitsura/imagen/CAMBIO_DE_DIFICULTAD.png"))
                    .getImage().getScaledInstance(MASCOTA_W, MASCOTA_H, Image.SCALE_SMOOTH);
            iconoCambioDificultad = new ImageIcon(imgCambio);
        } catch (Exception e) {
            iconoCambioDificultad = null;
        }

        // PRECARGAR ICONO DE CORAZON LLENO
        try {
            Image imgLleno = new ImageIcon(
                    getClass().getResource(
                            "/Multimedia/utiles/ElementosGraficos/imagenes/corazon.png"))
                    .getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
            iconoCorazonLleno = new ImageIcon(imgLleno);
        } catch (Exception e) {
            iconoCorazonLleno = null;
        }

        // PRECARGAR ICONO DE CORAZON ROTO (SE USA EN perderVida())
        try {
            Image imgRoto = new ImageIcon(
                    getClass().getResource(
                            "/Multimedia/utiles/ElementosGraficos/imagenes/corazon-roto.png"))
                    .getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
            iconoCorazonRoto = new ImageIcon(imgRoto);
        } catch (Exception e) {
            iconoCorazonRoto = null;
        }

        // ── VIDAS (CORAZONES) DINAMICAS SEGUN MAX_VIDAS, ACOMODADAS EN FILAS ────
        // SI CAMBIAS MAX_VIDAS O CORAZONES_POR_FILA, AQUI SE RECALCULA SOLO
        // LA CANTIDAD Y POSICION DE CADA CORAZON, SIN TOCAR NADA MAS DEL CODIGO.
        // EL TOPE REAL DE VIDAS LO IMPONE MAX_VIDAS EN TODA LA CLASE
        // (perderVida() NUNCA BAJA DE 0, Y agregarVidas() NUNCA SUBE DE MAX_VIDAS).
        corazones = new JLabel[maxVidas];
        int xInicialCorazon = 70;
        int yInicialCorazon = 20;
        int espaciadoX = 55;
        int espaciadoY = 55;
        int tamanoCorazon = 50;

        for (int i = 0; i < maxVidas; i++) {
            int fila = i / CORAZONES_POR_FILA;
            int columna = i % CORAZONES_POR_FILA;

            JLabel corazon;
            if (iconoCorazonLleno != null) {
                corazon = new JLabel(iconoCorazonLleno);
            } else {
                corazon = new JLabel("♥");
                corazon.setFont(fuente1.deriveFont(40f));
                corazon.setForeground(Color.RED);
            }
            corazon.setBounds(
                    xInicialCorazon + (columna * espaciadoX),
                    yInicialCorazon + (fila * espaciadoY),
                    tamanoCorazon, tamanoCorazon);
            corazones[i] = corazon;
            fondo.add(corazon);
        }

        // ── BOTON DE AYUDA ────────────────────────────────────────────────────
        // SE BAJA UN POCO PARA NO CHOCAR CON LAS DOS FILAS DE CORAZONES
        btnAyuda = new JButton("¿Necesitas ayuda?");
        btnAyuda.setBounds(60, 140, 280, 55);
        btnAyuda.setFocusPainted(false);
        btnAyuda.setFont(fuente2.deriveFont(18f));

        btnAyuda.addActionListener(e -> {

            // VERIFICAR QUE NO SE HAYA USADO YA LA PISTA EN ESTA PREGUNTA
            if (pistaMostradaEnPreguntaActual) {
                return;
            }

            // MARCAR COMO USADA Y DESHABILITAR EL BOTON VISUALMENTE
            pistaMostradaEnPreguntaActual = true;
            btnAyuda.setEnabled(false);
            btnAyuda.setText("Pista usada");

            // CONSULTAR Y MOSTRAR LA PISTA DESDE LA BD
            mostrarPista();
        });

        fondo.add(btnAyuda);

        // ── TITULO DE LA PREGUNTA ─────────────────────────────────────────────
        // USA HTML PARA PERMITIR TEXTO MULTILINIA CENTRADO
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
        // ESTE LABEL ACTUA COMO CONTENEDOR (USA setLayout(null)) Y COMO
        // FONDO VISUAL AL MISMO TIEMPO (TIENE UN ICONO DE IMAGEN)
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

        // ── NENUFAR DECORATIVO (SIN INTERACCION) ─────────────────────────────
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

        // ── NENUFAR VERDADERO (LADO IZQUIERDO INICIAL) ────────────────────────
        // SU POSICION REAL (VERDADERO/FALSO) SE ALEATORIA EN generarPosiciones()
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
                // VALIDACION EXPLICITA: NO CONFIAR SOLO EN QUE Swing "BLOQUEE"
                // EL CLICK DE UN JLabel DESHABILITADO, PORQUE NO LO HACE
                if (!nenufarVerdadero.isEnabled() || procesandoRespuesta || finJuegoActivo) {
                    return;
                }
                responder((Boolean) nenufarVerdadero.getClientProperty("respuesta"));
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                if (!nenufarVerdadero.isEnabled()) {
                    return;
                }
                nenufarVerdadero.setCursor(new Cursor(Cursor.HAND_CURSOR));
                // EFECTO DE AGRANDADO AL PASAR EL MOUSE POR ENCIMA
                nenufarVerdadero.setBounds(175, 135, 250, 190);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                nenufarVerdadero.setBounds(180, 140, 240, 180);
            }
        });
        panelLago.add(nenufarVerdadero);

        // ── NENUFAR FALSO (LADO DERECHO INICIAL) ──────────────────────────────
        // MISMO MECANISMO QUE EL NENUFAR VERDADERO, PERO PARA EL LADO DERECHO
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
                if (!nenufarFalso.isEnabled() || procesandoRespuesta || finJuegoActivo) {
                    return;
                }
                responder((Boolean) nenufarFalso.getClientProperty("respuesta"));
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                if (!nenufarFalso.isEnabled()) {
                    return;
                }
                nenufarFalso.setCursor(new Cursor(Cursor.HAND_CURSOR));
                nenufarFalso.setBounds(645, 135, 250, 190);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                nenufarFalso.setBounds(650, 140, 240, 180);
            }
        });
        panelLago.add(nenufarFalso);

        // ── TEXTO  INFORMATIVO (ESQUINA INFERIOR IZQUIERDA) ───────────────
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

        // ── MASCOTA (SE AGREGA AL FINAL PARA QUEDAR ENCIMA DE TODO) ──────────
        // SWING PINTA LOS COMPONENTES EN ORDEN INVERSO AL QUE FUERON AGREGADOS,
        // POR ESO SE AGREGA AQUI Y LUEGO SE FUERZA SU Z-ORDER A 0 (FRENTE)
        mascota = new JLabel();
        mascota.setBounds(MASCOTA_X_ORIG, MASCOTA_Y_ORIG, MASCOTA_W, MASCOTA_H);
        mascota.setLayout(null);

        if (iconoKitsura != null) {
            mascota.setIcon(iconoKitsura);
        } else {
            mascota.setText("Mascota");
        }

        // FLOR DECORATIVA QUE VA ENCIMA DE LA CABEZA DE KITSURA
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

        // INDICE 0 = PRIMER PLANO: LA MASCOTA SIEMPRE SE DIBUJA SOBRE LOS DEMAS
        fondo.setComponentZOrder(mascota, 0);
    }

    private void reconstruirCorazones() {
        if (corazones != null) {
            for (JLabel corazon : corazones) {
                fondo.remove(corazon);
            }
        }

        corazones = new JLabel[maxVidas];
        int xInicialCorazon = 70;
        int yInicialCorazon = 20;
        int espaciadoX = 55;
        int espaciadoY = 55;
        int tamanoCorazon = 50;

        for (int i = 0; i < maxVidas; i++) {
            int fila = i / CORAZONES_POR_FILA;
            int columna = i % CORAZONES_POR_FILA;

            JLabel corazon;
            if (iconoCorazonLleno != null) {
                corazon = new JLabel(iconoCorazonLleno);
            } else {
                corazon = new JLabel("♥");
                corazon.setFont(fuente1.deriveFont(40f));
                corazon.setForeground(Color.RED);
            }
            corazon.setBounds(
                    xInicialCorazon + (columna * espaciadoX),
                    yInicialCorazon + (fila * espaciadoY),
                    tamanoCorazon, tamanoCorazon);
            corazones[i] = corazon;
            fondo.add(corazon);
        }

        fondo.revalidate();
        fondo.repaint();
    }

    // =========================================================================
    // METODOS PUBLICOS (USADOS POR OTRAS CLASES)
    // =========================================================================
    /**
     * DEVUELVE EL PANEL LO USAN CLASES COMO PantallaDificultad, Victoria, ETC.
     * PARA PODER HACER setContentPane(foxJump.getFondo()) Y VOLVER AL JUEGO.
     */
    public JPanel getFondo() {
        return fondo;
    }

    /**
     * @return PUNTAJE TOTAL ACUMULADO HASTA ESTE MOMENTO
     */
    @Override
    public int getPuntajeTotal() {
        return puntajeTotal;
    }

    /**
     * @return SEGUNDOS TOTALES QUE EL JUGADOR HA ESTADO RESPONDIENDO
     */
    @Override
    public int getTiempoTotalJugado() {
        return tiempoTotalJugado;
    }

    /**
     * IMPLEMENTACION DE JuegoBase: DELEGA EN jugarDeNuevo()
     */
    @Override
    public void reiniciar() {
        jugarDeNuevo();
    }

    /**
     * IMPLEMENTACION DE JuegoBase: CIERRA ESTE FRAME Y ABRE EL MENU
     */
    @Override
    public void irAlMenu() {
        fadeTo(() -> {
            dispose();
            new MenuMinijuegos();
        }, 400);
    }

    /**
     * IMPLEMENTACION DE JuegoBase: DEVUELVE ESTE MISMO FRAME
     */
    @Override
    public JFrame getFrame() {
        return this;
    }

    /**
     * MUESTRA LA PANTALLA DE DERROTA POR VIDAS AGOTADAS. SE LLAMA CON UN
     * RETARDO DESDE perderVida() PARA DAR TIEMPO A VER EL ULTIMO CORAZON
     * ROMPERSE.
     */
    private void mostrarHaPerdido() {
        fadeTo(() -> {
            new SeAcaboVidas(this, e -> {
            }).setVisible(true);
            dispose();
        }, 400);
    }

    /**
     * MUESTRA LA PANTALLA DE DERROTA POR TIEMPO AGOTADO. SE LLAMA CUANDO EL
     * COUNTDOWN LLEGA A CERO.
     */
    private void mostrarTiempoAgotado() {
        bloquearNenufares();
        fadeTo(() -> {
            new SeAcaboTiempo(this, e -> {
            }).setVisible(true);
            dispose();
        }, 400);
    }

    /**
     * EFECTO DE TRANSICION: OSCURECE GRADUALMENTE LA PANTALLA HASTA NEGRO,
     * EJECUTA onMidpoint EN EL PUNTO MAS OSCURO, LUEGO ACLARA DE NUEVO.
     *
     * SE USA PARA TODAS LAS TRANSICIONES ENTRE PANTALLAS DEL JUEGO. USA EL
     * GLASS PANE DEL FRAME COMO CAPA SEMITRANSPARENTE.
     *
     * @param onMidpoint CODIGO A EJECUTAR CUANDO LA PANTALLA ESTA COMPLETAMENTE
     * NEGRA
     * @param duracionMs DURACION TOTAL DE LA ANIMACION (FADE IN + FADE OUT)
     */
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
        int[] fase = {0}; // FASE 0 = OSCURECIENDO | FASE 1 = ACLARANDO

        javax.swing.Timer fadeTimer = new javax.swing.Timer(intervalo, null);

        fadeTimer.addActionListener(e -> {

            if (fase[0] == 0) {
                // FASE DE OSCURECIMIENTO
                alpha[0] += 255 / pasos;

                if (alpha[0] >= 255) {
                    alpha[0] = 255;
                    overlay.setBackground(new Color(0, 0, 0, alpha[0]));
                    overlay.repaint();

                    // EJECUTAR LA ACCION CENTRAL (CAMBIO DE CONTENIDO)
                    onMidpoint.run();
                    revalidate();
                    repaint();

                    fase[0] = 1; // CAMBIAR A FASE DE ACLARAMIENTO
                }

            } else {
                // FASE DE ACLARAMIENTO
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

    /**
     * SE LLAMA DESDE PantallaDificultad CUANDO EL JUGADOR ELIGE CONTINUAR. HACE
     * EL FADE DE REGRESO AL PANEL DEL JUEGO Y LUEGO CARGA LA PROXIMA PREGUNTA.
     * EL RETARDO DE 420ms COINCIDE CON EL PUNTO MEDIO DEL FADE.
     */
    public void continuarDespuesDeDificultad() {
        fadeTo(() -> setContentPane(fondo), 400);
        new javax.swing.Timer(420, e -> {
            ((javax.swing.Timer) e.getSource()).stop();
            cargarPregunta();
        }).start();
    }

    /**
     * MAIN PARA LA FASE DE PRUEBAS DURANTE EL DESAROLLO .DURANTE EL JUEGO REAL
     * SOLO SE LLAMA DURANTE EL MENU
     */
    public static void main(String[] args) {
        new FoxJump("Animales");
    }
}