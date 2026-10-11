package com.mycompany.torneodeportivo.modelo;

/**
 * Estados posibles de un equipo dentro del torneo.
 *
 * @author Christian Hernandez Garcia
 * @version 1.0
 */
public enum EstadoEquipo {
    /** Registrado en el torneo. */
    INSCRITO,
    /** Avanzó de fase. */
    CLASIFICADO,
    /** Fuera de competencia. */
    ELIMINADO,
    /** Abandonó el torneo. */
    RETIRADO
}
