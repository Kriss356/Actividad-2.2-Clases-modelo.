package com.mycompany.torneodeportivo.modelo;

/**
 * Estados posibles de una categoría por edad.
 *
 * @author Christian Hernandez Garcia
 * @version 1.0
 */
public enum EstadoCategoria {
    /** Acepta inscripciones de equipos. */
    ABIERTA,
    /** Ya está en juego. */
    EN_COMPETENCIA,
    /** Finalizó su competencia. */
    CERRADA
}
