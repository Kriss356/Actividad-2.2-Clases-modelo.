package com.mycompany.torneodeportivo.modelo;

/**
 * Categoría por rango de edad que agrupa equipos para lograr una competencia equilibrada.
 *
 * @author Christian Hernandez Garcia
 * @version 1.0
 */
public class Categoria {

    /** El identificador único (llave primaria). */
    private int id;

    /** El nombre de la categoría. */
    private String nombreCategoria;

    /** La edad mínima permitida. */
    private int edadMinima;

    /** La edad máxima permitida. */
    private int edadMaxima;

    /** El estado de la categoría. */
    private EstadoCategoria estado = EstadoCategoria.ABIERTA;

    /**
     * Constructor vacío. Crea un objeto con los valores por defecto,
     * pensado para frameworks e instanciación sin datos.
     */
    public Categoria() {
        // Estado inicial por defecto; se completa después con los setters.
    }

    /**
     * Constructor con los datos mínimos indispensables para que el objeto
     * nazca en un estado válido. Todos los valores pasan por las validaciones
     * de sus setters.
     *
     * @param id identificador único
     * @param nombreCategoria nombre de la categoría
     * @param edadMinima edad mínima
     * @param edadMaxima edad máxima
     * @throws IllegalArgumentException si algún dato no cumple las validaciones
     */
    public Categoria(int id, String nombreCategoria, int edadMinima, int edadMaxima) {
        setId(id);
        setNombreCategoria(nombreCategoria);
        setEdadMinima(edadMinima);
        setEdadMaxima(edadMaxima);
        if (edadMinima > edadMaxima) {
            throw new IllegalArgumentException("La edad mínima no puede ser mayor que la máxima.");
        }
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
     * Obtiene el nombre de la categoría.
     *
     * @return el nombre de la categoría
     */
    public String getNombreCategoria() {
        return nombreCategoria;
    }

    /**
     * Establece el nombre de la categoría.
     *
     * @param valor el nombre de la categoría
     * @throws IllegalArgumentException si el valor es nulo o está vacío
     */
    public final void setNombreCategoria(String valor) {
        if (valor == null || valor.trim().isEmpty()) {
            throw new IllegalArgumentException("El campo \"Nombre\" no puede estar vacío.");
        }
        this.nombreCategoria = valor.trim();
    }

    /**
     * Obtiene la edad mínima permitida.
     *
     * @return la edad mínima permitida
     */
    public int getEdadMinima() {
        return edadMinima;
    }

    /**
     * Establece la edad mínima permitida.
     *
     * @param valor la edad mínima permitida
     * @throws IllegalArgumentException si el valor está fuera del rango 1 a 100
     */
    public final void setEdadMinima(int valor) {
        if (valor < 1 || valor > 100) {
            throw new IllegalArgumentException("El campo \"Edad mínima\" debe estar entre 1 y 100.");
        }
        this.edadMinima = valor;
    }

    /**
     * Obtiene la edad máxima permitida.
     *
     * @return la edad máxima permitida
     */
    public int getEdadMaxima() {
        return edadMaxima;
    }

    /**
     * Establece la edad máxima permitida.
     *
     * @param valor la edad máxima permitida
     * @throws IllegalArgumentException si el valor está fuera del rango 1 a 100
     */
    public final void setEdadMaxima(int valor) {
        if (valor < 1 || valor > 100) {
            throw new IllegalArgumentException("El campo \"Edad máxima\" debe estar entre 1 y 100.");
        }
        this.edadMaxima = valor;
    }

    /**
     * Obtiene el estado de la categoría.
     *
     * @return el estado de la categoría
     */
    public EstadoCategoria getEstado() {
        return estado;
    }

    /**
     * Establece el estado de la categoría.
     *
     * @param valor el estado de la categoría
     * @throws IllegalArgumentException si el valor es nulo
     */
    public final void setEstado(EstadoCategoria valor) {
        if (valor == null) {
            throw new IllegalArgumentException("El campo \"Estado\" es obligatorio.");
        }
        this.estado = valor;
    }

    /**
     * Indica si una edad pertenece al rango de la categoría.
     *
     * @param edad edad a revisar
     * @return {@code true} si está dentro del rango
     */
    public boolean admiteEdad(int edad) {
        return edad >= edadMinima && edad <= edadMaxima;
    }

    /**
     * Compara dos objetos Categoria únicamente por su identificador.
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
        return id == ((Categoria) obj).id;
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
        return "Categoria[id=" + id + ", nombreCategoria=" + nombreCategoria + ", edadMinima=" + edadMinima
                + ", edadMaxima=" + edadMaxima + ", estado=" + estado + "]";
    }

}
