package main.Usuario;

/**
 * Maneja el usuario que actualmente tiene la sesión iniciada.
 * Debe llamarse a Sesion.setIdUsuarioActual(id) justo después de
 * validar el login exitosamente (correo + contraseña correctos),
 * antes de abrir el MenuPrincipal.
 */
public class Sesion {

    private static int idUsuarioActual = -1;

    private Sesion() {
        // Clase de utilidad, no se instancia
    }

    public static int getIdUsuarioActual() {
        return idUsuarioActual;
    }

    public static void setIdUsuarioActual(int id) {
        idUsuarioActual = id;
    }

    public static boolean haySesionActiva() {
        return idUsuarioActual != -1;
    }

    public static void cerrarSesion() {
        idUsuarioActual = -1;
    }
}