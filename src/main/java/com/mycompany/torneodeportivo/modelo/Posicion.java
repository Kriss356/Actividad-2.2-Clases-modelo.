package com.mycompany.torneodeportivo.modelo;

/**
 * Registro de un equipo en la tabla de posiciones. Los puntos y la diferencia de goles
 * no se almacenan: se calculan a partir de los demás atributos para evitar inconsistencias.
 *
 * @author Christian Hernandez Garcia
 * @version 1.0
 */
public class Posicion {

    /** El identificador único (llave primaria). */
    private int id;

    /** El equipo al que corresponde el registro. */
    private Equipo equipo;

    /** Los partidos jugados. */
    private int partidosJugados;

    /** Los partidos ganados. */
    private int partidosGanados;

    /** Los partidos empatados. */
    private int partidosEmpatados;

    /** Los partidos perdidos. */
    private int partidosPerdidos;

    /** Los goles a favor. */
    private int golesAFavor;

    /** Los goles en contra. */
    private int golesEnContra;

    /** El estado del registro. */
    private EstadoPosicion estado = EstadoPosicion.ACTUALIZADO;

    /**
     * Constructor vacío. Crea un objeto con los valores por defecto,
     * pensado para frameworks e instanciación sin datos.
     */
    public Posicion() {
        // Estado inicial por defecto; se completa después con los setters.
    }

    /**
     * Constructor con los datos mínimos indispensables para que el objeto
     * nazca en un estado válido. Todos los valores pasan por las validaciones
     * de sus setters.
     *
     * @param id identificador único
     * @param equipo equipo al que corresponde el registro
     * @throws IllegalArgumentException si algún dato no cumple las validaciones
     */
    public Posicion(int id, Equipo equipo) {
        setId(id);
        setEquipo(equipo);
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
     * Obtiene el equipo al que corresponde el registro.
     *
     * @return el equipo al que corresponde el registro
     */
    public Equipo getEquipo() {
        return equipo;
    }

    /**
     * Establece el equipo al que corresponde el registro.
     *
     * @param valor el equipo al que corresponde el registro
     * @throws IllegalArgumentException si el valor es nulo
     */
    public final void setEquipo(Equipo valor) {
        if (valor == null) {
            throw new IllegalArgumentException("El campo \"Equipo\" es obligatorio.");
        }
        this.equipo = valor;
    }

    /**
     * Obtiene los partidos jugados.
     *
     * @return los partidos jugados
     */
    public int getPartidosJugados() {
        return partidosJugados;
    }

    /**
     * Establece los partidos jugados.
     *
     * @param valor los partidos jugados
     * @throws IllegalArgumentException si el valor está fuera del rango 0 a 9999
     */
    public final void setPartidosJugados(int valor) {
        if (valor < 0 || valor > 9999) {
            throw new IllegalArgumentException("El campo \"Partidos jugados\" debe estar entre 0 y 9999.");
        }
        this.partidosJugados = valor;
    }

    /**
     * Obtiene los partidos ganados.
     *
     * @return los partidos ganados
     */
    public int getPartidosGanados() {
        return partidosGanados;
    }

    /**
     * Establece los partidos ganados.
     *
     * @param valor los partidos ganados
     * @throws IllegalArgumentException si el valor está fuera del rango 0 a 9999
     */
    public final void setPartidosGanados(int valor) {
        if (valor < 0 || valor > 9999) {
            throw new IllegalArgumentException("El campo \"Partidos ganados\" debe estar entre 0 y 9999.");
        }
        this.partidosGanados = valor;
    }

    /**
     * Obtiene los partidos empatados.
     *
     * @return los partidos empatados
     */
    public int getPartidosEmpatados() {
        return partidosEmpatados;
    }

    /**
     * Establece los partidos empatados.
     *
     * @param valor los partidos empatados
     * @throws IllegalArgumentException si el valor está fuera del rango 0 a 9999
     */
    public final void setPartidosEmpatados(int valor) {
        if (valor < 0 || valor > 9999) {
            throw new IllegalArgumentException("El campo \"Partidos empatados\" debe estar entre 0 y 9999.");
        }
        this.partidosEmpatados = valor;
    }

    /**
     * Obtiene los partidos perdidos.
     *
     * @return los partidos perdidos
     */
    public int getPartidosPerdidos() {
        return partidosPerdidos;
    }

    /**
     * Establece los partidos perdidos.
     *
     * @param valor los partidos perdidos
     * @throws IllegalArgumentException si el valor está fuera del rango 0 a 9999
     */
    public final void setPartidosPerdidos(int valor) {
        if (valor < 0 || valor > 9999) {
            throw new IllegalArgumentException("El campo \"Partidos perdidos\" debe estar entre 0 y 9999.");
        }
        this.partidosPerdidos = valor;
    }

    /**
     * Obtiene los goles a favor.
     *
     * @return los goles a favor
     */
    public int getGolesAFavor() {
        return golesAFavor;
    }

    /**
     * Establece los goles a favor.
     *
     * @param valor los goles a favor
     * @throws IllegalArgumentException si el valor está fuera del rango 0 a 9999
     */
    public final void setGolesAFavor(int valor) {
        if (valor < 0 || valor > 9999) {
            throw new IllegalArgumentException("El campo \"Goles a favor\" debe estar entre 0 y 9999.");
        }
        this.golesAFavor = valor;
    }

    /**
     * Obtiene los goles en contra.
     *
     * @return los goles en contra
     */
    public int getGolesEnContra() {
        return golesEnContra;
    }

    /**
     * Establece los goles en contra.
     *
     * @param valor los goles en contra
     * @throws IllegalArgumentException si el valor está fuera del rango 0 a 9999
     */
    public final void setGolesEnContra(int valor) {
        if (valor < 0 || valor > 9999) {
            throw new IllegalArgumentException("El campo \"Goles en contra\" debe estar entre 0 y 9999.");
        }
        this.golesEnContra = valor;
    }

    /**
     * Obtiene el estado del registro.
     *
     * @return el estado del registro
     */
    public EstadoPosicion getEstado() {
        return estado;
    }

    /**
     * Establece el estado del registro.
     *
     * @param valor el estado del registro
     * @throws IllegalArgumentException si el valor es nulo
     */
    public final void setEstado(EstadoPosicion valor) {
        if (valor == null) {
            throw new IllegalArgumentException("El campo \"Estado\" es obligatorio.");
        }
        this.estado = valor;
    }

    /**
     * Reinicia todas las estadísticas a cero.
     */
    public void reiniciar() {
        partidosJugados = 0;
        partidosGanados = 0;
        partidosEmpatados = 0;
        partidosPerdidos = 0;
        golesAFavor = 0;
        golesEnContra = 0;
    }

    /**
     * Suma un partido jugado al registro.
     *
     * @param golesFavor goles anotados por el equipo
     * @param golesContra goles recibidos por el equipo
     */
    public void registrar(int golesFavor, int golesContra) {
        partidosJugados++;
        golesAFavor += golesFavor;
        golesEnContra += golesContra;
        if (golesFavor > golesContra) {
            partidosGanados++;
        } else if (golesFavor == golesContra) {
            partidosEmpatados++;
        } else {
            partidosPerdidos++;
        }
    }

    /**
     * Obtiene el nombre del equipo.
     *
     * @return nombre del equipo o cadena vacía
     */
    public String getNombreEquipo() {
        return equipo == null ? "" : equipo.getNombreEquipo();
    }

    /**
     * Calcula la diferencia de goles.
     *
     * @return goles a favor menos goles en contra
     */
    public int getDiferenciaGoles() {
        return golesAFavor - golesEnContra;
    }

    /**
     * Calcula los puntos (3 por victoria y 1 por empate).
     *
     * @return puntos acumulados
     */
    public int getPuntos() {
        return partidosGanados * 3 + partidosEmpatados;
    }

    /**
     * Compara dos objetos Posicion únicamente por su identificador.
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
        return id == ((Posicion) obj).id;
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
        return "Posicion[id=" + id + ", equipo=" + getNombreEquipo() + ", partidosJugados=" + partidosJugados
                + ", partidosGanados=" + partidosGanados + ", partidosEmpatados=" + partidosEmpatados
                + ", partidosPerdidos=" + partidosPerdidos + ", golesAFavor=" + golesAFavor
                + ", golesEnContra=" + golesEnContra + ", diferenciaGoles=" + getDiferenciaGoles()
                + ", puntos=" + getPuntos() + ", estado=" + estado + "]";
    }

}
