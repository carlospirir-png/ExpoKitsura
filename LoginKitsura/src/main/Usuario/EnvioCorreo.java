package main.Usuario;

import javax.mail.*;
import javax.mail.internet.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Random;

import main.conexion.Conexion;

public class EnvioCorreo {

    private static String codigoGenerado;
    private static final String EMAIL_REMITENTE = "kitsuragt@gmail.com";
    private static final String PASSWORD_APP = "jmlq fhxd dqcg unsf";

    /**
     * Verifica si el correo pertenece a una cuenta existente (y activa) en KITSURA_DB.
     * Ajusta el nombre de la tabla/columnas si tu esquema usa otros (ej. "email" en vez de "correo").
     */
    public static boolean existeCuentaConCorreo(String correo) {
        String sql = "SELECT id_usuario FROM Usuario WHERE correo = ? AND estado = 'activo'";

        Connection con = new Conexion().getConnection();
        if (con == null) {
            // getConnection() ya imprime el error; aquí solo evitamos el NullPointerException
            return false;
        }

        try (Connection c = con;
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, correo);

            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Intenta enviar el código de verificación.
     * @return true si el correo existe como cuenta activa y el envío fue exitoso.
     *         false si el correo no está registrado, o si ocurrió un error al enviar.
     */
    public static boolean enviarCodigo(String destinatario) {
        if (!existeCuentaConCorreo(destinatario)) {
            // No se envía nada si el correo no corresponde a una cuenta registrada
            return false;
        }

        codigoGenerado = String.valueOf(100000 + new Random().nextInt(900000));

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(EMAIL_REMITENTE, PASSWORD_APP);
            }
        });

        try {
            Message mensaje = new MimeMessage(session);
            mensaje.setFrom(new InternetAddress(EMAIL_REMITENTE, "Kitsura - Recuperación de Cuenta"));
            mensaje.setRecipients(Message.RecipientType.TO, InternetAddress.parse(destinatario));
            mensaje.setSubject("Tu código de verificación");

            String html = construirPlantillaHTML(codigoGenerado);

            MimeBodyPart cuerpoHtml = new MimeBodyPart();
            cuerpoHtml.setContent(html, "text/html; charset=UTF-8");

            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(cuerpoHtml);

            mensaje.setContent(multipart);

            Transport.send(mensaje);
            return true;

        } catch (MessagingException | java.io.UnsupportedEncodingException e) {
            e.printStackTrace();
            return false;
        }
    }

    private static String construirPlantillaHTML(String codigo) {
        return "<!DOCTYPE html>"
            + "<html>"
            + "<body style='margin:0; padding:0; background-color:#f4f4f7; font-family:Segoe UI, Arial, sans-serif;'>"
            + "<table width='100%' cellpadding='0' cellspacing='0' style='background-color:#f4f4f7; padding:40px 0;'>"
            + "<tr><td align='center'>"
            + "<table width='480' cellpadding='0' cellspacing='0' style='background-color:#ffffff; border-radius:8px; overflow:hidden; box-shadow:0 2px 8px rgba(0,0,0,0.08);'>"

            + "<tr><td style='background-color:#111111; padding:24px 32px;'>"
            + "<span style='color:#ffffff; font-size:20px; font-weight:600; letter-spacing:0.5px;'>KITSURA</span>"
            + "</td></tr>"

            + "<tr><td style='padding:32px;'>"
            + "<h2 style='color:#111111; font-size:20px; margin:0 0 16px;'>Verifica tu identidad</h2>"
            + "<p style='color:#555555; font-size:14px; line-height:1.6; margin:0 0 24px;'>"
            + "Recibimos una solicitud para restablecer la contraseña de tu cuenta. "
            + "Usa el siguiente código para continuar con el proceso:</p>"

            + "<table width='100%' cellpadding='0' cellspacing='0'><tr><td align='center'>"
            + "<div style='background-color:#f4f4f7; border-radius:6px; padding:20px; margin:0 0 24px;'>"
            + "<span style='font-size:32px; font-weight:700; letter-spacing:8px; color:#111111;'>" + codigo + "</span>"
            + "</div></td></tr></table>"

            + "<p style='color:#555555; font-size:14px; line-height:1.6; margin:0 0 8px;'>"
            + "Este código es válido durante los próximos <strong>10 minutos</strong>.</p>"
            + "<p style='color:#999999; font-size:13px; line-height:1.6; margin:0;'>"
            + "Si no solicitaste este cambio, puedes ignorar este mensaje de forma segura.</p>"
            + "</td></tr>"

            + "<tr><td style='background-color:#fafafa; padding:20px 32px; border-top:1px solid #eeeeee;'>"
            + "<p style='color:#aaaaaa; font-size:12px; margin:0; text-align:center;'>"
            + "© 2026 Kitsura. Todos los derechos reservados.<br>"
            + "Este es un mensaje automático, por favor no respondas a este correo.</p>"
            + "</td></tr>"

            + "</table>"
            + "</td></tr>"
            + "</table>"
            + "</body>"
            + "</html>";
    }

    public static boolean verificarCodigo(String codigoIngresado) {
        return codigoGenerado != null && codigoGenerado.equals(codigoIngresado);
    }

    public static void limpiarCodigo() {
        codigoGenerado = null;
    }
}