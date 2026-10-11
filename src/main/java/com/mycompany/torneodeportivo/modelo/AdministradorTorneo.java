package com.mycompany.torneodeportivo.modelo;

/**
 * Administrador responsable de la gestión general, la consulta de resultados y la tabla de posiciones.
 * Es {@code final}: el conjunto de roles es cerrado y los permisos dependen del tipo exacto.
 *
 * @author Christian Hernandez Garcia
 * @version 1.0
 */
public final class AdministradorTorneo extends Administrador {

    /** El nivel de permiso. */
    private String nivelPermiso;

    /**
     * Constructor vacío. Crea un objeto con los valores por defecto,
     * pensado para frameworks e instanciación sin datos.
     */
    public AdministradorTorneo() {
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
     * @param nivelPermiso el nivel de permiso
     * @throws IllegalArgumentException si algún dato no cumple las validaciones
     */
    public AdministradorTorneo(int id, String nombre, int edad, String correo, String usuario, String contrasena, String nivelPermiso) {
        super(id, nombre, edad, correo, usuario, contrasena);
        setNivelPermiso(nivelPermiso);
    }

    /**
     * Obtiene el nivel de permiso.
     *
     * @return el nivel de permiso
     */
    public String getNivelPermiso() {
        return nivelPermiso;
    }

    /**
     * Establece el nivel de permiso.
     *
     * @param valor el nivel de permiso
     * @throws IllegalArgumentException si el valor es nulo o está vacío
     */
    public void setNivelPermiso(String valor) {
        if (valor == null || valor.trim().isEmpty()) {
            throw new IllegalArgumentException("El campo \"Nivel de permiso\" no puede estar vacío.");
        }
        this.nivelPermiso = valor.trim();
    }

    /**
     * Obtiene el rol del administrador.
     *
     * @return nombre del rol
     */
    @Override
    public String getRol() {
        return "Administrador del torneo";
    }

    /**
     * Devuelve el estado completo del objeto concatenado con el de su clase padre.
     *
     * @return cadena con todos los atributos
     */
    @Override
    public String toString() {
        return super.toString() + " -> AdministradorTorneo[nivelPermiso=" + nivelPermiso + "]";
    }

}
