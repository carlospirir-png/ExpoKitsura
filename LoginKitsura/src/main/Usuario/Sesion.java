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
    
    // Ruta de la foto que se usa si el usuario aún no ha elegido una propia
    public static final String FOTO_PERFIL_DEFECTO = "/Multimedia/utiles/ImagenesPerfil/Seccion1/PE_S1_N9.png";
    
    private static int idUsuarioActual = -1;
    private static boolean esInvitado = false;
    private static String nombreInvitado = null;
    
    // NUEVO
    private static String nombreUsuarioRegistrado = null;
    private static String rutaFotoPerfil = FOTO_PERFIL_DEFECTO;
    
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
        nombreUsuarioRegistrado = null;
        rutaFotoPerfil = FOTO_PERFIL_DEFECTO; // los invitados siempre arrancan con la de defecto
    }

    public static boolean isEsInvitado() {
        return esInvitado;
    }

    public static String getNombreInvitado() {
        return nombreInvitado;
    }
    
    // Setter para el nombre de un usuario registrado/logueado
    public static void setNombreUsuario(String nombre) {
        nombreUsuarioRegistrado = nombre;
    }

    // Devuelve el nombre correcto sin importar si es invitado o registrado
    public static String getNombreUsuario() {
        return esInvitado ? nombreInvitado : nombreUsuarioRegistrado;
    }

    // NUEVO: foto de perfil
    public static String getRutaFotoPerfil() {
        return rutaFotoPerfil;
    }
    
    public static boolean haySesionActiva() {
        return idUsuarioActual != -1 || esInvitado;
    }
    
    public static void setRutaFotoPerfil(String ruta) {
        if (ruta != null && !ruta.isEmpty()) {
            rutaFotoPerfil = ruta;
        }
    }

    public static void cerrarSesion() {
        idUsuarioActual = -1;
        esInvitado = false;
        nombreInvitado = null;
        nombreUsuarioRegistrado = null;
        rutaFotoPerfil = FOTO_PERFIL_DEFECTO;
    }
}