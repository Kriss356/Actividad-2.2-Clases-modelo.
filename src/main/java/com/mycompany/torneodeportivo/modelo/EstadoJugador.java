package com.mycompany.torneodeportivo.modelo;

/**
 * Estados posibles de un jugador.
 *
 * @author Christian Hernandez Garcia
 * @version 1.0
 */
public enum EstadoJugador {
    /** Puede jugar. */
    HABILITADO,
    /** Suspendido por sanción. */
    SANCIONADO,
    /** No puede jugar por lesión. */
    LESIONADO
}
