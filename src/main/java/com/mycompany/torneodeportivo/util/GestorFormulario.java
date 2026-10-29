package com.mycompany.torneodeportivo.util;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputControl;

/**
 * Administra todos los campos validados de un formulario: escucha sus cambios (escribir, borrar,
 * pegar o elegir), actualiza los mensajes y avisa al controlador para que ajuste los botones.
 */
public class GestorFormulario {

    private final List<CampoValidado> campos = new ArrayList<>();
    private final Runnable alCambiar;
    private boolean silencio;

    /** @param alCambiar acción que se ejecuta cada vez que cambia el estado del formulario */
    public GestorFormulario(Runnable alCambiar) {
        this.alCambiar = alCambiar;
    }

    /** Registra un control cualquiera (ComboBox, DatePicker...) observando su valor. */
    public CampoValidado agregar(Node control, Label error, ObservableValue<?> valor, Supplier<String> regla) {
        CampoValidado campo = new CampoValidado(control, error, regla);
        campos.add(campo);
        valor.addListener((obs, antes, ahora) -> {
            if (silencio) return;
            campo.mostrar();
            alCambiar.run();
        });
        return campo;
    }

    /** Registra un campo de texto: valida con textProperty() y restringe los caracteres. */
    public CampoValidado agregarTexto(TextInputControl campo, Label error, Supplier<String> regla,
                                      String patronCaracter, int max, String mensajeRechazo) {
        CampoValidado c = agregar(campo, error, campo.textProperty(), regla);
        Filtros.restringir(campo, patronCaracter, max, mensajeRechazo, c::mostrarRechazo);
        return c;
    }

    /** Vuelve a revisar un campo cuando cambia otro del que depende (por ejemplo, edad mínima y máxima). */
    public void revalidarCuando(CampoValidado campo, ObservableValue<?> otro) {
        otro.addListener((obs, antes, ahora) -> {
            if (silencio) return;
            if (campo.fueTocado()) campo.mostrar();
            alCambiar.run();
        });
    }

    /** Revisa todos los campos mostrando todos los mensajes. @return true si todos son válidos */
    public boolean validarTodo() {
        boolean todoValido = true;
        for (CampoValidado c : campos) {
            c.mostrar();
            todoValido &= c.esValido();
        }
        return todoValido;
    }

    /** Revisa todos los campos sin mostrar mensajes (para habilitar o no el botón Guardar). */
    public boolean esValido() {
        for (CampoValidado c : campos) {
            if (!c.esValido()) return false;
        }
        return true;
    }

    public void enfocarPrimerInvalido() {
        for (CampoValidado c : campos) {
            if (!c.esValido()) {
                c.getControl().requestFocus();
                return;
            }
        }
    }

    public void enfocarPrimero() {
        if (!campos.isEmpty()) campos.get(0).getControl().requestFocus();
    }

    /**
     * Ejecuta una acción que cambia los campos desde el programa (limpiar o cargar un registro)
     * sin disparar mensajes, y al terminar deja los campos sin marcas.
     */
    public void silenciar(Runnable accion) {
        silencio = true;
        for (CampoValidado c : campos) c.permitirCambioProgramatico(true);
        try {
            accion.run();
        } finally {
            silencio = false;
            for (CampoValidado c : campos) {
                c.permitirCambioProgramatico(false);
                c.reiniciar();
            }
        }
        alCambiar.run();
    }
}
