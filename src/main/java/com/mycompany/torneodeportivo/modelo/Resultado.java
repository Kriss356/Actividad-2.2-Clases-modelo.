package com.mycompany.torneodeportivo.modelo;

/**
 * Resultado de un partido: marcador, equipo ganador (o empate) y estado de validación.
 *
 * @author Christian Hernandez Garcia
 * @version 1.0
 */
public class Resultado {

    /** El identificador único (llave primaria). */
    private int id;

    /** Los goles del equipo local. */
    private int golesLocal;

    /** Los goles del equipo visitante. */
    private int golesVisitante;

    /** El equipo ganador ({@code null} si fue empate). */
    private Equipo equipoGanador;

    /** El estado del resultado. */
    private EstadoResultado estado = EstadoResultado.REGISTRADO;

    /**
     * Constructor vacío. Crea un objeto con los valores por defecto,
     * pensado para frameworks e instanciación sin datos.
     */
    public Resultado() {
        // Estado inicial por defecto; se completa después con los setters.
    }

    /**
     * Constructor con los datos mínimos indispensables para que el objeto
     * nazca en un estado válido. Todos los valores pasan por las validaciones
     * de sus setters.
     *
     * @param id identificador único
     * @param golesLocal goles del local
     * @param golesVisitante goles del visitante
     * @throws IllegalArgumentException si algún dato no cumple las validaciones
     */
    public Resultado(int id, int golesLocal, int golesVisitante) {
        setId(id);
        setGolesLocal(golesLocal);
        setGolesVisitante(golesVisitante);
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
     * Obtiene los goles del equipo local.
     *
     * @return los goles del equipo local
     */
    public int getGolesLocal() {
        return golesLocal;
    }

    /**
     * Establece los goles del equipo local.
     *
     * @param valor los goles del equipo local
     * @throws IllegalArgumentException si el valor está fuera del rango 0 a 99
     */
    public final void setGolesLocal(int valor) {
        if (valor < 0 || valor > 99) {
            throw new IllegalArgumentException("El campo \"Goles del local\" debe estar entre 0 y 99.");
        }
        this.golesLocal = valor;
    }

    /**
     * Obtiene los goles del equipo visitante.
     *
     * @return los goles del equipo visitante
     */
    public int getGolesVisitante() {
        return golesVisitante;
    }

    /**
     * Establece los goles del equipo visitante.
     *
     * @param valor los goles del equipo visitante
     * @throws IllegalArgumentException si el valor está fuera del rango 0 a 99
     */
    public final void setGolesVisitante(int valor) {
        if (valor < 0 || valor > 99) {
            throw new IllegalArgumentException("El campo \"Goles del visitante\" debe estar entre 0 y 99.");
        }
        this.golesVisitante = valor;
    }

    /**
     * Obtiene el equipo ganador ({@code null} si fue empate).
     *
     * @return el equipo ganador ({@code null} si fue empate)
     */
    public Equipo getEquipoGanador() {
        return equipoGanador;
    }

    /**
     * Establece el equipo ganador ({@code null} si fue empate).
     *
     * @param valor el equipo ganador ({@code null} si fue empate)
     */
    public final void setEquipoGanador(Equipo valor) {
        this.equipoGanador = valor;
    }

    /**
     * Obtiene el estado del resultado.
     *
     * @return el estado del resultado
     */
    public EstadoResultado getEstado() {
        return estado;
    }

    /**
     * Establece el estado del resultado.
     *
     * @param valor el estado del resultado
     * @throws IllegalArgumentException si el valor es nulo
     */
    public final void setEstado(EstadoResultado valor) {
        if (valor == null) {
            throw new IllegalArgumentException("El campo \"Estado\" es obligatorio.");
        }
        this.estado = valor;
    }

    /**
     * Determina el ganador comparando los goles con los equipos del partido.
     *
     * @param local equipo local del partido
     * @param visitante equipo visitante del partido
     */
    public void determinarGanador(Equipo local, Equipo visitante) {
        if (golesLocal > golesVisitante) {
            equipoGanador = local;
        } else if (golesVisitante > golesLocal) {
            equipoGanador = visitante;
        } else {
            equipoGanador = null;
        }
    }

    /**
     * Obtiene el ganador en forma de texto.
     *
     * @return nombre del equipo ganador o "Empate"
     */
    public String getGanadorTexto() {
        return equipoGanador == null ? "Empate" : equipoGanador.getNombreEquipo();
    }

    /**
     * Compara dos objetos Resultado únicamente por su identificador.
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
        return id == ((Resultado) obj).id;
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
        return "Resultado[id=" + id + ", golesLocal=" + golesLocal + ", golesVisitante=" + golesVisitante
                + ", equipoGanador=" + getGanadorTexto() + ", estado=" + estado + "]";
    }

}
