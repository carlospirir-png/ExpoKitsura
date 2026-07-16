package main.Usuario;

import java.awt.*;
import java.awt.event.*;
import java.net.URL;
import javax.sound.sampled.*;
import javax.swing.*;
import main.Menu.FondoPanel;
import main.Menu.DecoracionBotones;

public class PistasAudio extends JFrame {

    private FondoPanel fondo;
    private Font fuente1;
    private Font fuente2;
    private DecoracionBotones btnSalir, btnRepetir;

    // ---------------- REPRODUCCIÓN DE AUDIO (MP3 vía mp3spi) ----------------
    // Se reemplaza Clip por SourceDataLine porque los archivos MP3 decodificados
    // por mp3spi casi siempre reportan duración "no especificada"
    // (AudioSystem.NOT_SPECIFIED), y Clip.open(AudioInputStream) falla con eso.
    // SourceDataLine reproduce el audio en bloques (streaming), lo cual sí
    // funciona con el stream ya decodificado a PCM.
    private SourceDataLine lineaAudio;
    private Thread hiloReproduccion;
    private volatile boolean reproduciendo = false;

    private String rutaAudio;

    public PistasAudio(String rutaAudio) {

        this.rutaAudio = rutaAudio;
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
        //------------- ÍCONO ------------------
        //se obtiene la imagen del logo con getResource
        URL iconUrl = getClass().getResource("/Multimedia/utiles/logotipo/logofK.png");

        //se instancia el ícono con la imagen
        ImageIcon icono = new ImageIcon(iconUrl);

        //Se coloca el ícono al JFrame
        setIconImage(icono.getImage());

        setTitle("Pistas de Audio");
        setSize(700, 450);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        fondo.setLayout(null);
        crearComponentes();

        reproducirAudio();

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                detenerAudio();
            }
        });

        setVisible(true);

    }

    private void crearComponentes() {

        //---------------- RECUADRO AUDIO ----------------
        JPanel recuadroAudio = new JPanel();
        recuadroAudio.setLayout(null);
        recuadroAudio.setBackground(Color.WHITE);
        recuadroAudio.setBounds(40, 100, 340, 100);
        fondo.add(recuadroAudio);

        JLabel lblTiempoInicio = new JLabel("1:46");
        lblTiempoInicio.setFont(fuente2.deriveFont(16f));
        lblTiempoInicio.setBounds(20, 40, 40, 25);
        recuadroAudio.add(lblTiempoInicio);

        JPanel barraProgreso = new JPanel();
        barraProgreso.setBackground(Color.LIGHT_GRAY);
        barraProgreso.setBounds(65, 48, 200, 10);
        recuadroAudio.add(barraProgreso);

        JLabel lblTiempoFin = new JLabel("3:32");
        lblTiempoFin.setFont(fuente2.deriveFont(16f));
        lblTiempoFin.setBounds(275, 40, 40, 25);
        recuadroAudio.add(lblTiempoFin);

        //---------------- BOTON SALIR ----------------
        JButton btnSalir = new DecoracionBotones("SALIR",
                //ColorBase             ColorBorde              ColorLetra
                DecoracionBotones.AZUL, DecoracionBotones.GRIS, DecoracionBotones.AMARILLO, //MOUSE FUERA
                DecoracionBotones.CELESTE, DecoracionBotones.AZUL, DecoracionBotones.AZUL); //MOUSE DENTRO   

        btnSalir.setFont(fuente2.deriveFont(20f));
        btnSalir.setBounds(40, 290, 150, 45);
        btnSalir.addActionListener(e -> {
            detenerAudio();
            dispose();
        });
        fondo.add(btnSalir);

        //---------------- BOTON REPETIR ----------------
        btnRepetir = new DecoracionBotones("REPETIR",
                //ColorBase             ColorBorde              ColorLetra
                DecoracionBotones.ROSA, DecoracionBotones.ROJO, DecoracionBotones.AMARILLO, //MOUSE FUERA
                DecoracionBotones.ROJO, DecoracionBotones.ROSA, DecoracionBotones.ROSA); //MOUSE DENTRO
        
        btnRepetir.setFont(fuente2.deriveFont(20f));
        btnRepetir.setBounds(230, 290, 150, 45);
        btnRepetir.addActionListener(e -> reproducirAudio());
        
        fondo.add(btnRepetir);

        //---------------- MASCOTA ----------------
        JLabel mascotaAudifonos = new JLabel();
        ImageIcon iconMascota = new ImageIcon(getClass().getResource("/Multimedia/utiles/mascotaKitsura/imagen/PISTA_AUDIO.png"));
        Image imgEscalada = iconMascota.getImage().getScaledInstance(300, 300, Image.SCALE_SMOOTH);
        mascotaAudifonos.setIcon(new ImageIcon(imgEscalada));
        mascotaAudifonos.setBounds(390, 65, 300, 300);

        fondo.add(mascotaAudifonos);
    }

    //---------------- DETENER AUDIO ----------------
    // Señaliza al hilo de reproducción que debe detenerse y cierra la línea.
    // El propio hilo (en reproducirAudio) es responsable de cerrar el
    // AudioInputStream una vez que termina su bucle de lectura.
    public void detenerAudio() {

        reproduciendo = false;

        if (hiloReproduccion != null) {
            hiloReproduccion.interrupt();
        }

        if (lineaAudio != null) {
            lineaAudio.stop();
            lineaAudio.close();
            lineaAudio = null;
        }
    }

    //---------------- REPRODUCIR AUDIO (MP3 vía mp3spi + SourceDataLine) ----------------
    public void reproducirAudio() {

        // Si ya había una reproducción en curso (por ejemplo, se presionó
        // REPETIR mientras el audio sonaba), se detiene primero.
        detenerAudio();

        System.out.println("Ruta en BD: " + rutaAudio);

        URL url = getClass().getResource(rutaAudio);

        System.out.println("URL encontrada: " + url);

        if (url == null) {
            JOptionPane.showMessageDialog(
                    this,
                    "No se encontró el archivo de audio:\n" + rutaAudio);
            return;
        }

        reproduciendo = true;

        hiloReproduccion = new Thread(() -> {

            AudioInputStream streamOriginal = null;
            AudioInputStream streamDecodificado = null;

            try {
                // 1. Abrir el stream del archivo (mp3spi lo reconoce gracias
                //    a los .jar mp3spi/jlayer/tritonus-share agregados al proyecto)
                streamOriginal = AudioSystem.getAudioInputStream(url);
                AudioFormat formatoBase = streamOriginal.getFormat();

                // 2. Definir el formato PCM al que se va a decodificar
                AudioFormat formatoDecodificado = new AudioFormat(
                        AudioFormat.Encoding.PCM_SIGNED,
                        formatoBase.getSampleRate(),
                        16,
                        formatoBase.getChannels(),
                        formatoBase.getChannels() * 2,
                        formatoBase.getSampleRate(),
                        false);

                // 3. Obtener el stream ya convertido a PCM
                streamDecodificado = AudioSystem.getAudioInputStream(
                        formatoDecodificado, streamOriginal);

                // 4. Abrir la línea de reproducción con el formato decodificado
                lineaAudio = AudioSystem.getSourceDataLine(formatoDecodificado);
                lineaAudio.open(formatoDecodificado);
                lineaAudio.start();

                byte[] buffer = new byte[4096];
                int bytesLeidos;

                while (reproduciendo
                        && (bytesLeidos = streamDecodificado.read(buffer, 0, buffer.length)) != -1) {
                    lineaAudio.write(buffer, 0, bytesLeidos);
                }

                if (reproduciendo) {
                    // Terminó porque el audio llegó a su fin (no porque lo detuvieron)
                    lineaAudio.drain();
                }

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (lineaAudio != null) {
                        lineaAudio.stop();
                        lineaAudio.close();
                    }
                    if (streamDecodificado != null) {
                        streamDecodificado.close();
                    }
                    if (streamOriginal != null) {
                        streamOriginal.close();
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                reproduciendo = false;
            }
        });

        hiloReproduccion.start();
    }

}