package com.mycompany.torneodeportivo.util;

/** Validaciones de formularios. Lanzan IllegalArgumentException con el mensaje a mostrar. */
public class Validador {

    public static String texto(String valor, String campo) {
        if (valor == null || valor.trim().isEmpty()) {
            throw new IllegalArgumentException("El campo \"" + campo + "\" es obligatorio.");
        }
        return valor.trim();
    }

    public static int entero(String valor, String campo, int min, int max) {
        String t = texto(valor, campo);
        int n;
        try {
            n = Integer.parseInt(t);
        } catch (NumberFormatException ex) {
            throw new IllegalArgumentException("El campo \"" + campo + "\" debe ser un número entero.");
        }
        if (n < min || n > max) {
            throw new IllegalArgumentException("El campo \"" + campo + "\" debe estar entre " + min + " y " + max + ".");
        }
        return n;
    }

    public static <T> T seleccion(T valor, String campo) {
        if (valor == null) throw new IllegalArgumentException("Selecciona un valor en \"" + campo + "\".");
        return valor;
    }
}
