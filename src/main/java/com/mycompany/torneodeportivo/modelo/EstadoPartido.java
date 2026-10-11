package com.mycompany.torneodeportivo.modelo;

/**
 * Estados posibles de un partido.
 *
 * @author Christian Hernandez Garcia
 * @version 1.0
 */
public enum EstadoPartido {
    /** Aún no inicia. */
    PROGRAMADO,
    /** Se está disputando. */
    EN_JUEGO,
    /** Terminó y puede tener resultado. */
    FINALIZADO,
    /** Detenido o cancelado. */
    SUSPENDIDO
}
