package main.Usuario;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.print.*;
import javax.imageio.ImageIO;
import javax.print.*;
import java.io.File;
import java.net.URL;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.*;

import main.conexion.Conexion;

/**
 * Clase encargada de imprimir el ticket de resultado final
 * en la impresora térmica (rollo de 58mm).
 * Se invoca únicamente desde ResultadoFinal.
 */
public class TicketImpresora {

    private static final float PT_POR_MM = 72f / 25.4f;
    private static final float ANCHO_TICKET_MM = 58f;

    // imagen
    private static final String LOGO_PATH = "/Multimedia/utiles/mascotaKitsura/imagen/KitsuraImagenNegro.png";

    public static void imprimir(Component parent, int puntaje, int tiempoSegundos, int vidasPerdidas) {

        DatosJugador datos = obtenerDatosJugador();

        int min = tiempoSegundos / 60;
        int seg = tiempoSegundos % 60;
        String tiempoTexto = String.format("%02d:%02d", min, seg);

        String fechaTexto = new SimpleDateFormat("dd/MM/yyyy HH:mm").format(new Date());

        float anchoPt = ANCHO_TICKET_MM * PT_POR_MM;
        float altoPt = 600f; // alto "de sobra", el contenido real es el que manda

        Paper papel = new Paper();
        papel.setSize(anchoPt, altoPt);
        papel.setImageableArea(3, 3, anchoPt - 6, altoPt - 6);

        PageFormat formato = new PageFormat();
        formato.setPaper(papel);
        formato.setOrientation(PageFormat.PORTRAIT);

        PrinterJob job = PrinterJob.getPrinterJob();

        PrintService impresora = buscarImpresoraTermica();
        if (impresora != null) {
            try {
                job.setPrintService(impresora);
            } catch (PrinterException ex) {
                ex.printStackTrace();
            }
        }

        final int MARGEN = 5;
        final int ANCHO_LOGICO = Math.round(anchoPt) - (MARGEN * 2);

        // ---- Cargamos las imágenes ANTES del callback de impresión, de forma síncrona ----
        BufferedImage logoImg = cargarImagen(LOGO_PATH);
        BufferedImage fotoImg = cargarImagen(datos.rutaFoto);

        job.setPrintable((Graphics g, PageFormat pf, int pageIndex) -> {

            if (pageIndex > 0) return Printable.NO_SUCH_PAGE;

            Graphics2D g2 = (Graphics2D) g;
            g2.translate(pf.getImageableX() + MARGEN, pf.getImageableY());
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);

            int ancho = ANCHO_LOGICO;
            int y = 0;

            // ---- Logo (zorro + "Kitsura"), anclado a la izquierda ----
            if (logoImg != null) {
                int[] dim = calcularDimensiones(logoImg, ancho, 90);
                g2.drawImage(logoImg, 0, y, dim[0], dim[1], null);
                y += dim[1] + 8;
            } else {
                // Si la imagen no carga, dejamos el texto como respaldo para no perder el encabezado
                g2.setFont(new Font("Arial", Font.BOLD, 13));
                g2.drawString("KITSURA", 0, y + 15);
                y += 25;
            }

            g2.drawLine(0, y, ancho, y);
            y += 12;

            // ---- Foto de perfil, anclada a la izquierda ----
            if (fotoImg != null) {
                int[] dim = calcularDimensiones(fotoImg, 70, 70);
                g2.drawImage(fotoImg, 0, y, dim[0], dim[1], null);
                y += dim[1] + 12;
            }

            // ---- Datos (fuente pequeña porque el rollo es angosto) ----
            // Cada dato en una sola línea "Etiqueta: valor", anclada a la izquierda.
            g2.setFont(new Font("Arial", Font.PLAIN, 11));
            y = dibujarLinea(g2, "Usuario: " + datos.nombre, y);
            y = dibujarLinea(g2, "Puntaje: " + puntaje, y);
            y = dibujarLinea(g2, "Tiempo: " + tiempoTexto, y);
            y = dibujarLinea(g2, "Fecha: " + fechaTexto, y);

            y += 8;
            g2.drawLine(0, y, ancho, y);
            y += 16;

            g2.setFont(new Font("Arial", Font.ITALIC, 9));
            g2.drawString("¡Gracias por jugar!", 0, y);

            return Printable.PAGE_EXISTS;

        }, formato);

        try {
            job.print();
        } catch (PrinterException ex) {
            JOptionPane.showMessageDialog(parent,
                    "No se pudo imprimir el ticket: " + ex.getMessage(),
                    "Error de impresión", JOptionPane.ERROR_MESSAGE);
        }
    }

    // ---------------- Datos del jugador ----------------

    private static class DatosJugador {
        String nombre;
        String rutaFoto;
    }

    private static DatosJugador obtenerDatosJugador() {
        DatosJugador d = new DatosJugador();

        if (Sesion.isEsInvitado() || !Sesion.haySesionActiva()) {
            d.nombre = (Sesion.isEsInvitado() && Sesion.getNombreInvitado() != null)
                    ? Sesion.getNombreInvitado()
                    : "Invitado";
            d.rutaFoto = null; // los invitados no tienen foto de perfil
            return d;
        }

        String sql = "SELECT nombre_usuario, imagen_perfil FROM Usuario WHERE id_usuario = ?";

        Conexion conexion = new Conexion();

        try (Connection con = conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, Sesion.getIdUsuarioActual());

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    d.nombre = rs.getString("nombre_usuario");
                    d.rutaFoto = rs.getString("imagen_perfil"); // puede venir NULL, cargarImagen ya lo maneja
                } else {
                    d.nombre = "Usuario";
                    d.rutaFoto = null;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            d.nombre = "Usuario";
            d.rutaFoto = null;
        }

        return d;
    }

    // ---------------- Utilidades de impresión ----------------

    private static int dibujarLinea(Graphics2D g2, String texto, int y) {
        FontMetrics fm = g2.getFontMetrics();
        g2.drawString(texto, 0, y + fm.getAscent());
        return y + fm.getHeight() + 4;
    }

    /**
     * Calcula el ancho/alto final manteniendo proporción, dado un máximo de ancho y alto.
     * Retorna un arreglo {anchoFinal, altoFinal}.
     */
    private static int[] calcularDimensiones(BufferedImage img, int maxAncho, int maxAlto) {
        int w = img.getWidth();
        int h = img.getHeight();
        float escala = Math.min((float) maxAncho / w, (float) maxAlto / h);
        int nuevoAncho = Math.round(w * escala);
        int nuevoAlto = Math.round(h * escala);
        return new int[]{nuevoAncho, nuevoAlto};
    }

    /**
     * Carga una imagen de forma SÍNCRONA usando ImageIO, para evitar el problema
     * de getScaledInstance()/Toolkit (que cargan en un hilo aparte y en impresión
     * pueden no estar listas a tiempo, resultando en que no se imprima nada).
     */
    private static BufferedImage cargarImagen(String ruta) {
        if (ruta == null || ruta.isEmpty()) return null;

        try {
            BufferedImage img;

            if (ruta.startsWith("/Multimedia")) {
                URL url = TicketImpresora.class.getResource(ruta);
                if (url == null) {
                    System.out.println("cargarImagen(): recurso no encontrado en el classpath -> " + ruta);
                    return null;
                }
                img = ImageIO.read(url);
            } else {
                // ruta absoluta en disco (por si en el futuro cambia el almacenamiento)
                File f = new File(ruta);
                if (!f.exists()) {
                    System.out.println("cargarImagen(): archivo no existe en disco -> " + ruta);
                    return null;
                }
                img = ImageIO.read(f);
            }

            if (img == null) {
                System.out.println("cargarImagen(): ImageIO no pudo decodificar -> " + ruta);
                return null;
            }

            return img;

        } catch (Exception e) {
            // DEBUG TEMPORAL: para saber por qué no carga la imagen (ruta incorrecta, recurso null, etc.)
            System.out.println("cargarImagen() FALLÓ para ruta: " + ruta);
            e.printStackTrace();
            return null;
        }
    }

    private static PrintService buscarImpresoraTermica() {
        PrintService[] servicios = PrinterJob.lookupPrintServices();
        for (PrintService s : servicios) {
            String nombre = s.getName().toUpperCase();
            if (nombre.contains("POS90") || nombre.contains("PR100") || nombre.contains("POS") || nombre.contains("TERMICA")) {
                return s;
            }
        }
        return null; // usará la predeterminada del sistema si no la encuentra
    }
}