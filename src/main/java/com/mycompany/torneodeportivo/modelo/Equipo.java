package com.mycompany.torneodeportivo.modelo;

/**
 * Equipo universitario que participa en el torneo y pertenece a una categoría.
 *
 * @author Christian Hernandez Garcia
 * @version 1.0
 */
public class Equipo {

    /** El identificador único (llave primaria). */
    private int id;

    /** El nombre del equipo. */
    private String nombreEquipo;

    /** La universidad que representa. */
    private String universidad;

    /** El nombre del entrenador. */
    private String entrenador = "";

    /** La categoría a la que pertenece. */
    private Categoria categoria;

    /** La ruta relativa del logo. */
    private String rutaLogo = "";

    /** El estado del equipo. */
    private EstadoEquipo estado = EstadoEquipo.INSCRITO;

    /**
     * Constructor vacío. Crea un objeto con los valores por defecto,
     * pensado para frameworks e instanciación sin datos.
     */
    public Equipo() {
        // Estado inicial por defecto; se completa después con los setters.
    }

    /**
     * Constructor con los datos mínimos indispensables para que el objeto
     * nazca en un estado válido. Todos los valores pasan por las validaciones
     * de sus setters.
     *
     * @param id identificador único
     * @param nombreEquipo nombre del equipo
     * @param universidad universidad que representa
     * @param categoria categoría del equipo
     * @throws IllegalArgumentException si algún dato no cumple las validaciones
     */
    public Equipo(int id, String nombreEquipo, String universidad, Categoria categoria) {
        setId(id);
        setNombreEquipo(nombreEquipo);
        setUniversidad(universidad);
        setCategoria(categoria);
    }

    /**
     * Obtiene el identificador único (llave primaria).
     *
     * @return el identificador único (llave primaria)
     */
    public int getId() {
        return id;
    }

    /**
     * Establece el identificador único (llave primaria).
     *
     * @param valor el identificador único (llave primaria)
     * @throws IllegalArgumentException si el valor es negativo
     */
    public final void setId(int valor) {
        if (valor < 0) {
            throw new IllegalArgumentException("El identificador no puede ser negativo.");
        }
        this.id = valor;
    }

    /**
     * Obtiene el nombre del equipo.
     *
     * @return el nombre del equipo
     */
    public String getNombreEquipo() {
        return nombreEquipo;
    }

    /**
     * Establece el nombre del equipo.
     *
     * @param valor el nombre del equipo
     * @throws IllegalArgumentException si el valor es nulo o está vacío
     */
    public final void setNombreEquipo(String valor) {
        if (valor == null || valor.trim().isEmpty()) {
            throw new IllegalArgumentException("El campo \"Nombre del equipo\" no puede estar vacío.");
        }
        this.nombreEquipo = valor.trim();
    }

    /**
     * Obtiene la universidad que representa.
     *
     * @return la universidad que representa
     */
    public String getUniversidad() {
        return universidad;
    }

    /**
     * Establece la universidad que representa.
     *
     * @param valor la universidad que representa
     * @throws IllegalArgumentException si el valor es nulo o está vacío
     */
    public final void setUniversidad(String valor) {
        if (valor == null || valor.trim().isEmpty()) {
            throw new IllegalArgumentException("El campo \"Universidad\" no puede estar vacío.");
        }
        this.universidad = valor.trim();
    }

    /**
     * Obtiene el nombre del entrenador.
     *
     * @return el nombre del entrenador
     */
    public String getEntrenador() {
        return entrenador;
    }

    /**
     * Establece el nombre del entrenador.
     *
     * @param valor el nombre del entrenador
     */
    public final void setEntrenador(String valor) {
        this.entrenador = valor == null ? "" : valor.trim();
    }

    /**
     * Obtiene la categoría a la que pertenece.
     *
     * @return la categoría a la que pertenece
     */
    public Categoria getCategoria() {
        return categoria;
    }

    /**
     * Establece la categoría a la que pertenece.
     *
     * @param valor la categoría a la que pertenece
     * @throws IllegalArgumentException si el valor es nulo
     */
    public final void setCategoria(Categoria valor) {
        if (valor == null) {
            throw new IllegalArgumentException("El campo \"Categoría\" es obligatorio.");
        }
        this.categoria = valor;
    }

    /**
     * Obtiene la ruta relativa del logo.
     *
     * @return la ruta relativa del logo
     */
    public String getRutaLogo() {
        return rutaLogo;
    }

    /**
     * Establece la ruta relativa del logo.
     *
     * @param valor la ruta relativa del logo
     */
    public final void setRutaLogo(String valor) {
        this.rutaLogo = valor == null ? "" : valor.trim();
    }

    /**
     * Obtiene el estado del equipo.
     *
     * @return el estado del equipo
     */
    public EstadoEquipo getEstado() {
        return estado;
    }

    /**
     * Establece el estado del equipo.
     *
     * @param valor el estado del equipo
     * @throws IllegalArgumentException si el valor es nulo
     */
    public final void setEstado(EstadoEquipo valor) {
        if (valor == null) {
            throw new IllegalArgumentException("El campo \"Estado\" es obligatorio.");
        }
        this.estado = valor;
    }

    /**
     * Obtiene el nombre de la categoría del equipo (útil para mostrarlo en tablas).
     *
     * @return nombre de la categoría o cadena vacía
     */
    public String getNombreCategoria() {
        return categoria == null ? "" : categoria.getNombreCategoria();
    }

    /**
     * Compara dos objetos Equipo únicamente por su identificador.
     *
     * @param obj objeto a comparar
     * @return {@code true} si son del mismo tipo y tienen el mismo id
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        return id == ((Equipo) obj).id;
    }

    /**
     * Calcula el código hash a partir del identificador.
     *
     * @return código hash basado en el id
     */
    @Override
    public int hashCode() {
        return Integer.hashCode(id);
    }

    /**
     * Devuelve el estado completo del objeto.
     *
     * @return cadena con todos los atributos
     */
    @Override
    public String toString() {
        return "Equipo[id=" + id + ", nombreEquipo=" + nombreEquipo + ", universidad=" + universidad + ", entrenador=" + entrenador
                + ", categoria=" + getNombreCategoria() + ", rutaLogo=" + rutaLogo + ", estado=" + estado + "]";
    }

}
