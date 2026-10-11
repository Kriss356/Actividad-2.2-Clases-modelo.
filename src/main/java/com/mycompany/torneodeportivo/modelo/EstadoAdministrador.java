package com.mycompany.torneodeportivo.modelo;

/**
 * Estados posibles de la cuenta de un administrador.
 *
 * @author Christian Hernandez Garcia
 * @version 1.0
 */
public enum EstadoAdministrador {
    /** Puede iniciar sesión. */
    ACTIVO,
    /** Cuenta deshabilitada. */
    INACTIVO,
    /** Acceso suspendido temporalmente. */
    SUSPENDIDO
}
