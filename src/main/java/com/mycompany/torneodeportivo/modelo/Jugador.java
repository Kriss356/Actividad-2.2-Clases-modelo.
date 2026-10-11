package com.mycompany.torneodeportivo.modelo;

/**
 * Jugador inscrito en un equipo universitario.
 * Es {@code final}: no se prevén especializaciones de jugador y así se protege su integridad.
 *
 * @author Christian Hernandez Garcia
 * @version 1.0
 */
public final class Jugador extends Persona {

    /** El número de camiseta. */
    private int numeroCamiseta;

    /** La posición en el campo. */
    private PosicionCampo posicionCampo;

    /** El equipo al que pertenece. */
    private Equipo equipo;

    /** La ruta relativa de la foto. */
    private String rutaFoto = "";

    /** El estado del jugador. */
    private EstadoJugador estado = EstadoJugador.HABILITADO;

    /**
     * Constructor vacío. Crea un objeto con los valores por defecto,
     * pensado para frameworks e instanciación sin datos.
     */
    public Jugador() {
        super();
    }

    /**
     * Constructor con los datos mínimos indispensables para que el objeto
     * nazca en un estado válido. Todos los valores pasan por las validaciones
     * de sus setters.
     *
     * @param id identificador único de la persona
     * @param nombre nombre completo
     * @param edad edad en años
     * @param numeroCamiseta número de camiseta (1 a 99)
     * @param posicionCampo posición en el campo
     * @param equipo equipo al que pertenece
     * @throws IllegalArgumentException si algún dato no cumple las validaciones
     */
    public Jugador(int id, String nombre, int edad, int numeroCamiseta, PosicionCampo posicionCampo, Equipo equipo) {
        super(id, nombre, edad);
        setNumeroCamiseta(numeroCamiseta);
        setPosicionCampo(posicionCampo);
        setEquipo(equipo);
    }

    /**
     * Obtiene el número de camiseta.
     *
     * @return el número de camiseta
     */
    public int getNumeroCamiseta() {
        return numeroCamiseta;
    }

    /**
     * Establece el número de camiseta.
     *
     * @param valor el número de camiseta
     * @throws IllegalArgumentException si el valor está fuera del rango 1 a 99
     */
    public void setNumeroCamiseta(int valor) {
        if (valor < 1 || valor > 99) {
            throw new IllegalArgumentException("El campo \"Número de camiseta\" debe estar entre 1 y 99.");
        }
        this.numeroCamiseta = valor;
    }

    /**
     * Obtiene la posición en el campo.
     *
     * @return la posición en el campo
     */
    public PosicionCampo getPosicionCampo() {
        return posicionCampo;
    }

    /**
     * Establece la posición en el campo.
     *
     * @param valor la posición en el campo
     * @throws IllegalArgumentException si el valor es nulo
     */
    public void setPosicionCampo(PosicionCampo valor) {
        if (valor == null) {
            throw new IllegalArgumentException("El campo \"Posición\" es obligatorio.");
        }
        this.posicionCampo = valor;
    }

    /**
     * Obtiene el equipo al que pertenece.
     *
     * @return el equipo al que pertenece
     */
    public Equipo getEquipo() {
        return equipo;
    }

    /**
     * Establece el equipo al que pertenece.
     *
     * @param valor el equipo al que pertenece
     * @throws IllegalArgumentException si el valor es nulo
     */
    public void setEquipo(Equipo valor) {
        if (valor == null) {
            throw new IllegalArgumentException("El campo \"Equipo\" es obligatorio.");
        }
        this.equipo = valor;
    }

    /**
     * Obtiene la ruta relativa de la foto.
     *
     * @return la ruta relativa de la foto
     */
    public String getRutaFoto() {
        return rutaFoto;
    }

    /**
     * Establece la ruta relativa de la foto.
     *
     * @param valor la ruta relativa de la foto
     */
    public void setRutaFoto(String valor) {
        this.rutaFoto = valor == null ? "" : valor.trim();
    }

    /**
     * Obtiene el estado del jugador.
     *
     * @return el estado del jugador
     */
    public EstadoJugador getEstado() {
        return estado;
    }

    /**
     * Establece el estado del jugador.
     *
     * @param valor el estado del jugador
     * @throws IllegalArgumentException si el valor es nulo
     */
    public void setEstado(EstadoJugador valor) {
        if (valor == null) {
            throw new IllegalArgumentException("El campo \"Estado\" es obligatorio.");
        }
        this.estado = valor;
    }

    /**
     * Obtiene el nombre del equipo del jugador (útil para mostrarlo en tablas).
     *
     * @return nombre del equipo o cadena vacía
     */
    public String getNombreEquipo() {
        return equipo == null ? "" : equipo.getNombreEquipo();
    }

    /**
     * Obtiene el rol de la persona.
     *
     * @return la cadena "Jugador"
     */
    @Override
    public String getRol() {
        return "Jugador";
    }

    /**
     * Devuelve el estado completo del objeto concatenado con el de su clase padre.
     *
     * @return cadena con todos los atributos
     */
    @Override
    public String toString() {
        return super.toString() + " -> Jugador[numeroCamiseta=" + numeroCamiseta + ", posicionCampo=" + posicionCampo
                + ", equipo=" + getNombreEquipo() + ", rutaFoto=" + rutaFoto + ", estado=" + estado + "]";
    }

}
