package com.mycompany.torneodeportivo.modelo;

/**
 * Administrador responsable de equipos, jugadores y categorías por edad.
 * Es {@code final}: el conjunto de roles es cerrado y los permisos dependen del tipo exacto.
 *
 * @author Christian Hernandez Garcia
 * @version 1.0
 */
public final class AdministradorEquipos extends Administrador {

    /** La sección asignada. */
    private String seccionAsignada;

    /**
     * Constructor vacío. Crea un objeto con los valores por defecto,
     * pensado para frameworks e instanciación sin datos.
     */
    public AdministradorEquipos() {
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
     * @param seccionAsignada la sección asignada
     * @throws IllegalArgumentException si algún dato no cumple las validaciones
     */
    public AdministradorEquipos(int id, String nombre, int edad, String correo, String usuario, String contrasena, String seccionAsignada) {
        super(id, nombre, edad, correo, usuario, contrasena);
        setSeccionAsignada(seccionAsignada);
    }

    /**
     * Obtiene la sección asignada.
     *
     * @return la sección asignada
     */
    public String getSeccionAsignada() {
        return seccionAsignada;
    }

    /**
     * Establece la sección asignada.
     *
     * @param valor la sección asignada
     * @throws IllegalArgumentException si el valor es nulo o está vacío
     */
    public void setSeccionAsignada(String valor) {
        if (valor == null || valor.trim().isEmpty()) {
            throw new IllegalArgumentException("El campo \"Sección asignada\" no puede estar vacío.");
        }
        this.seccionAsignada = valor.trim();
    }

    /**
     * Obtiene el rol del administrador.
     *
     * @return nombre del rol
     */
    @Override
    public String getRol() {
        return "Administrador de equipos";
    }

    /**
     * Devuelve el estado completo del objeto concatenado con el de su clase padre.
     *
     * @return cadena con todos los atributos
     */
    @Override
    public String toString() {
        return super.toString() + " -> AdministradorEquipos[seccionAsignada=" + seccionAsignada + "]";
    }

}
