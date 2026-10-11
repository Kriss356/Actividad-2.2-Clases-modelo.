package com.mycompany.torneodeportivo.modelo;

/**
 * Administrador responsable de programar partidos y registrar resultados.
 * Es {@code final}: el conjunto de roles es cerrado y los permisos dependen del tipo exacto.
 *
 * @author Christian Hernandez Garcia
 * @version 1.0
 */
public final class AdministradorPartidos extends Administrador {

    /** El turno asignado. */
    private String turnoAsignado;

    /**
     * Constructor vacío. Crea un objeto con los valores por defecto,
     * pensado para frameworks e instanciación sin datos.
     */
    public AdministradorPartidos() {
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
     * @param turnoAsignado el turno asignado
     * @throws IllegalArgumentException si algún dato no cumple las validaciones
     */
    public AdministradorPartidos(int id, String nombre, int edad, String correo, String usuario, String contrasena, String turnoAsignado) {
        super(id, nombre, edad, correo, usuario, contrasena);
        setTurnoAsignado(turnoAsignado);
    }

    /**
     * Obtiene el turno asignado.
     *
     * @return el turno asignado
     */
    public String getTurnoAsignado() {
        return turnoAsignado;
    }

    /**
     * Establece el turno asignado.
     *
     * @param valor el turno asignado
     * @throws IllegalArgumentException si el valor es nulo o está vacío
     */
    public void setTurnoAsignado(String valor) {
        if (valor == null || valor.trim().isEmpty()) {
            throw new IllegalArgumentException("El campo \"Turno asignado\" no puede estar vacío.");
        }
        this.turnoAsignado = valor.trim();
    }

    /**
     * Obtiene el rol del administrador.
     *
     * @return nombre del rol
     */
    @Override
    public String getRol() {
        return "Administrador de partidos";
    }

    /**
     * Devuelve el estado completo del objeto concatenado con el de su clase padre.
     *
     * @return cadena con todos los atributos
     */
    @Override
    public String toString() {
        return super.toString() + " -> AdministradorPartidos[turnoAsignado=" + turnoAsignado + "]";
    }

}
