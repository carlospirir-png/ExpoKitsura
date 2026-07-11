package main.Usuario;

import java.awt.*;
import java.awt.print.*;
import javax.print.*;
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

    private static final String LOGO_PATH = "/Multimedia/utiles/logotipo/logofK.png";

    public static void imprimir(Component parent, int puntaje, int tiempoSegundos, int vidasPerdidas) {

        DatosJugador datos = obtenerDatosJugador();

        int min = tiempoSegundos / 60;
        int seg = tiempoSegundos % 60;
        String tiempoTexto = String.format("%02d:%02d", min, seg);

        String vidasTexto = (vidasPerdidas <= 0)
                ? "Ninguna"
                : vidasPerdidas + " vida(s) perdida(s)";

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

        job.setPrintable((Graphics g, PageFormat pf, int pageIndex) -> {

            if (pageIndex > 0) return Printable.NO_SUCH_PAGE;

            Graphics2D g2 = (Graphics2D) g;
            g2.translate(pf.getImageableX(), pf.getImageableY());
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            int ancho = (int) pf.getImageableWidth();
            int y = 0;

            // ---- Logo ----
            Image logo = cargarImagen(LOGO_PATH, ancho - 20, 60);
            if (logo != null) {
                int x = (ancho - logo.getWidth(null)) / 2;
                g2.drawImage(logo, x, y, null);
                y += logo.getHeight(null) + 8;
            }

            // ---- Título ----
            g2.setFont(new Font("Arial", Font.BOLD, 13));
            String titulo = "KITSURA";
            FontMetrics fmT = g2.getFontMetrics();
            g2.drawString(titulo, (ancho - fmT.stringWidth(titulo)) / 2, y + fmT.getAscent());
            y += fmT.getHeight() + 6;

            g2.drawLine(0, y, ancho, y);
            y += 12;

            // ---- Foto de perfil ----
            Image foto = cargarImagen(datos.rutaFoto, 70, 70);
            if (foto != null) {
                int x = (ancho - foto.getWidth(null)) / 2;
                g2.drawImage(foto, x, y, null);
                y += foto.getHeight(null) + 12;
            }

            // ---- Datos (fuente pequeña porque el rollo es angosto) ----
            g2.setFont(new Font("Arial", Font.PLAIN, 10));
            y = dibujarLinea(g2, "Usuario:", datos.nombre, ancho, y);
            y = dibujarLinea(g2, "Puntaje:", String.valueOf(puntaje), ancho, y);
            y = dibujarLinea(g2, "Tiempo:", tiempoTexto, ancho, y);
            y = dibujarLinea(g2, "Vidas:", vidasTexto, ancho, y);
            y = dibujarLinea(g2, "Fecha:", fechaTexto, ancho, y);

            y += 8;
            g2.drawLine(0, y, ancho, y);
            y += 16;

            g2.setFont(new Font("Arial", Font.ITALIC, 9));
            String gracias = "¡Gracias por jugar!";
            FontMetrics fmG = g2.getFontMetrics();
            g2.drawString(gracias, (ancho - fmG.stringWidth(gracias)) / 2, y);

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

    private static int dibujarLinea(Graphics2D g2, String etiqueta, String valor, int ancho, int y) {
        FontMetrics fm = g2.getFontMetrics();
        g2.drawString(etiqueta, 0, y + fm.getAscent());
        g2.drawString(valor, ancho - fm.stringWidth(valor), y + fm.getAscent());
        return y + fm.getHeight() + 4;
    }

    private static Image cargarImagen(String ruta, int maxAncho, int maxAlto) {
        if (ruta == null || ruta.isEmpty()) return null;

        try {
            ImageIcon icon;
            if (ruta.startsWith("/Multimedia")) {
                icon = new ImageIcon(TicketImpresora.class.getResource(ruta));
            } else {
                icon = new ImageIcon(ruta); // ruta absoluta en disco (por si en el futuro cambia el almacenamiento)
            }

            Image img = icon.getImage();
            int w = img.getWidth(null);
            int h = img.getHeight(null);
            if (w <= 0 || h <= 0) return null;

            float escala = Math.min((float) maxAncho / w, (float) maxAlto / h);
            int nuevoAncho = Math.round(w * escala);
            int nuevoAlto = Math.round(h * escala);

            return img.getScaledInstance(nuevoAncho, nuevoAlto, Image.SCALE_SMOOTH);
        } catch (Exception e) {
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