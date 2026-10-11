package com.mycompany.torneodeportivo.modelo;

/**
 * Clase base abstracta de todas las entidades humanas del sistema.
 * Concentra los datos comunes (id, nombre y edad) y no puede instanciarse directamente,
 * porque una "persona genérica" no existe en el dominio: siempre es un administrador o un jugador.
 *
 * @author Christian Hernandez Garcia
 * @version 1.0
 */
public abstract class Persona {

    /** El identificador único (llave primaria). */
    private int id;

    /** El nombre completo de la persona. */
    private String nombre;

    /** La edad en años. */
    private int edad;

    /**
     * Constructor vacío. Crea un objeto con los valores por defecto,
     * pensado para frameworks e instanciación sin datos.
     */
    public Persona() {
        // Estado inicial por defecto; se completa después con los setters.
    }

    /**
     * Constructor con los datos mínimos indispensables para que el objeto
     * nazca en un estado válido. Todos los valores pasan por las validaciones
     * de sus setters.
     *
     * @param id identificador único de la persona
     * @param nombre nombre completo
     * @param edad edad en años
     * @throws IllegalArgumentException si algún dato no cumple las validaciones
     */
    public Persona(int id, String nombre, int edad) {
        setId(id);
        setNombre(nombre);
        setEdad(edad);
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
     * Obtiene el nombre completo de la persona.
     *
     * @return el nombre completo de la persona
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el nombre completo de la persona.
     *
     * @param valor el nombre completo de la persona
     * @throws IllegalArgumentException si el valor es nulo o está vacío
     */
    public final void setNombre(String valor) {
        if (valor == null || valor.trim().isEmpty()) {
            throw new IllegalArgumentException("El campo \"Nombre\" no puede estar vacío.");
        }
        this.nombre = valor.trim();
    }

    /**
     * Obtiene la edad en años.
     *
     * @return la edad en años
     */
    public int getEdad() {
        return edad;
    }

    /**
     * Establece la edad en años.
     *
     * @param valor la edad en años
     * @throws IllegalArgumentException si el valor está fuera del rango 15 a 100
     */
    public final void setEdad(int valor) {
        if (valor < 15 || valor > 100) {
            throw new IllegalArgumentException("El campo \"Edad\" debe estar entre 15 y 100.");
        }
        this.edad = valor;
    }

    /**
     * Obtiene el rol de la persona dentro del sistema (método abstracto: cada subclase concreta lo define).
     *
     * @return nombre del rol
     */
    public abstract String getRol();

    /**
     * Compara dos personas únicamente por su identificador y tipo.
     * Es {@code final} para que ninguna subclase rompa el contrato basado en el id.
     *
     * @param obj objeto a comparar
     * @return {@code true} si son del mismo tipo y tienen el mismo id
     */
    @Override
    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        return id == ((Persona) obj).id;
    }

    /**
     * Calcula el código hash a partir del identificador.
     *
     * @return código hash basado en el id
     */
    @Override
    public final int hashCode() {
        return Integer.hashCode(id);
    }

    /**
     * Devuelve el estado completo del objeto.
     *
     * @return cadena con todos los atributos
     */
    @Override
    public String toString() {
        return "Persona[id=" + id + ", nombre=" + nombre + ", edad=" + edad + "]";
    }

}
