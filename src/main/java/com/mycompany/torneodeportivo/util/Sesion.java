package com.mycompany.torneodeportivo.util;

import com.mycompany.torneodeportivo.modelo.Administrador;
import com.mycompany.torneodeportivo.modelo.AdministradorEquipos;
import com.mycompany.torneodeportivo.modelo.AdministradorPartidos;
import com.mycompany.torneodeportivo.modelo.AdministradorTorneo;

/** Guarda al administrador que inició sesión y permite consultar su rol. */
public class Sesion {
    private static Administrador actual;

    public static Administrador getActual() { return actual; }
    public static void setActual(Administrador a) { actual = a; }
    public static void cerrar() { actual = null; }

    public static boolean esEquipos() { return actual instanceof AdministradorEquipos; }
    public static boolean esPartidos() { return actual instanceof AdministradorPartidos; }
    public static boolean esTorneo() { return actual instanceof AdministradorTorneo; }

    public static String nombreRol() {
        return actual == null ? "" : actual.getRol();
    }
}
