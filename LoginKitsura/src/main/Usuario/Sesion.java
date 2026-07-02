package main.Usuario;
/**
 * Maneja el usuario que actualmente tiene la sesión iniciada.
 * Debe llamarse a Sesion.setIdUsuarioActual(id) justo después de
 * validar el login exitosamente (correo + contraseña correctos),
 * antes de abrir el MenuPrincipal.
 *
 * Para invitados, se debe llamar a Sesion.iniciarSesionInvitado(nombre)
 * justo después de registrar al invitado, antes de abrir el MenuPrincipal.
 */
public class Sesion {
    private static int idUsuarioActual = -1;
    private static boolean esInvitado = false;
    private static String nombreInvitado = null;

    private Sesion() {
        // Clase de utilidad, no se instancia
    }

    public static int getIdUsuarioActual() {
        return idUsuarioActual;
    }

    public static void setIdUsuarioActual(int id) {
        idUsuarioActual = id;
        esInvitado = false;
        nombreInvitado = null;
    }

    public static void iniciarSesionInvitado(String nombre) {
        idUsuarioActual = -1;
        esInvitado = true;
        nombreInvitado = nombre;
    }

    public static boolean isEsInvitado() {
        return esInvitado;
    }

    public static String getNombreInvitado() {
        return nombreInvitado;
    }

    public static boolean haySesionActiva() {
        return idUsuarioActual != -1 || esInvitado;
    }

    public static void cerrarSesion() {
        idUsuarioActual = -1;
        esInvitado = false;
        nombreInvitado = null;
    }
}