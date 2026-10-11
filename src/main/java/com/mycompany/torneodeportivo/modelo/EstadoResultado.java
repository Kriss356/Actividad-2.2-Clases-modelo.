package com.mycompany.torneodeportivo.modelo;

/**
 * Estados posibles de un resultado.
 *
 * @author Christian Hernandez Garcia
 * @version 1.0
 */
public enum EstadoResultado {
    /** Capturado. */
    REGISTRADO,
    /** Pendiente de validar. */
    EN_REVISION,
    /** Validado oficialmente. */
    CONFIRMADO
}
