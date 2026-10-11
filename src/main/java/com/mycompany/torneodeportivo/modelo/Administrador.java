package com.mycompany.torneodeportivo.modelo;

/**
 * Clase abstracta que representa a un administrador del torneo.
 * Agrupa las credenciales y la sesión comunes; los permisos concretos se definen
 * en las tres subclases finales (equipos, partidos y torneo).
 *
 * @author Christian Hernandez Garcia
 * @version 1.0
 */
public abstract class Administrador extends Persona {

    /** El correo electrónico. */
    private String correo;

    /** El nombre de usuario. */
    private String usuario;

    /** La contraseña de acceso. */
    private String contrasena;

    /** El estado de la cuenta. */
    private EstadoAdministrador estado = EstadoAdministrador.ACTIVO;

    /**
     * Constructor vacío. Crea un objeto con los valores por defecto,
     * pensado para frameworks e instanciación sin datos.
     */
    public Administrador() {
        super();
    }

    /**
     * Constructor con los datos mínimos indispensables para que el objeto
     * nazca en un estado válido. Todos los valores pasan por las validaciones
     * de sus setters.
     *
     * @param id identificador único del administrador
     * @param nombre nombre completo
     * @param edad edad en años
     * @param correo correo electrónico
     * @param usuario nombre de usuario para iniciar sesión
     * @param contrasena contraseña (mínimo 4 caracteres)
     * @throws IllegalArgumentException si algún dato no cumple las validaciones
     */
    public Administrador(int id, String nombre, int edad, String correo, String usuario, String contrasena) {
        super(id, nombre, edad);
        setCorreo(correo);
        setUsuario(usuario);
        setContrasena(contrasena);
    }

    /**
     * Obtiene el correo electrónico.
     *
     * @return el correo electrónico
     */
    public String getCorreo() {
        return correo;
    }

    /**
     * Establece el correo electrónico.
     *
     * @param valor el correo electrónico
     * @throws IllegalArgumentException si el correo es nulo o no tiene formato válido
     */
    public final void setCorreo(String valor) {
        if (valor == null || valor.trim().length() < 5 || !valor.contains("@")) {
            throw new IllegalArgumentException("El correo electrónico no es válido.");
        }
        this.correo = valor.trim();
    }

    /**
     * Obtiene el nombre de usuario.
     *
     * @return el nombre de usuario
     */
    public String getUsuario() {
        return usuario;
    }

    /**
     * Establece el nombre de usuario.
     *
     * @param valor el nombre de usuario
     * @throws IllegalArgumentException si el valor es nulo o está vacío
     */
    public final void setUsuario(String valor) {
        if (valor == null || valor.trim().isEmpty()) {
            throw new IllegalArgumentException("El campo \"Usuario\" no puede estar vacío.");
        }
        this.usuario = valor.trim();
    }

    /**
     * Obtiene la contraseña de acceso.
     *
     * @return la contraseña de acceso
     */
    public String getContrasena() {
        return contrasena;
    }

    /**
     * Establece la contraseña de acceso.
     *
     * @param valor la contraseña de acceso
     * @throws IllegalArgumentException si la contraseña es nula o tiene menos de 4 caracteres
     */
    public final void setContrasena(String valor) {
        if (valor == null || valor.length() < 4) {
            throw new IllegalArgumentException("La contraseña debe tener al menos 4 caracteres.");
        }
        this.contrasena = valor;
    }

    /**
     * Obtiene el estado de la cuenta.
     *
     * @return el estado de la cuenta
     */
    public EstadoAdministrador getEstado() {
        return estado;
    }

    /**
     * Establece el estado de la cuenta.
     *
     * @param valor el estado de la cuenta
     * @throws IllegalArgumentException si el valor es nulo
     */
    public final void setEstado(EstadoAdministrador valor) {
        if (valor == null) {
            throw new IllegalArgumentException("El campo \"Estado\" es obligatorio.");
        }
        this.estado = valor;
    }

    /**
     * Valida las credenciales. Es {@code final} porque la lógica de autenticación
     * no debe poder alterarse en las subclases.
     *
     * @param usuario usuario capturado
     * @param contrasena contraseña capturada
     * @return {@code true} si la cuenta está activa y las credenciales coinciden
     */
    public final boolean iniciarSesion(String usuario, String contrasena) {
        return estado == EstadoAdministrador.ACTIVO
                && this.usuario.equals(usuario) && this.contrasena.equals(contrasena);
    }

    /**
     * Cierra la sesión del administrador. Es {@code final} para conservar un comportamiento único.
     */
    public final void cerrarSesion() {
        // La navegación entre pantallas se resuelve en la capa de controladores.
    }

    /**
     * Resume los datos del administrador.
     *
     * @return texto con nombre, correo y estado
     */
    public final String consultarInformacion() {
        return getNombre() + " (" + correo + ") - " + estado;
    }

    /**
     * Devuelve el estado completo del objeto concatenado con el de su clase padre.
     *
     * @return cadena con todos los atributos
     */
    @Override
    public String toString() {
        return super.toString() + " -> Administrador[correo=" + correo + ", usuario=" + usuario + ", contrasena=****, estado=" + estado + "]";
    }

}
