package com.mycompany.torneodeportivo.util;

import java.util.function.Supplier;
import javafx.animation.PauseTransition;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.util.Duration;

/**
 * Un control del formulario junto con su Label de error (debajo del control) y su regla de validación.
 * Cada campo administra su propio mensaje, de modo que no se sobrescriben entre sí.
 */
public final class CampoValidado {

    private static final String INVALIDO = "campo-invalido";
    private static final String VALIDO = "campo-valido";

    private final Node control;
    private final Label lblError;
    private final Supplier<String> regla;
    private boolean tocado; // el usuario ya interactuó con el campo
    /** Los avisos de "tecla rechazada" son temporales: luego se vuelve a evaluar el campo. */
    private final PauseTransition avisoTemporal = new PauseTransition(Duration.seconds(2.5));

    public CampoValidado(Node control, Label lblError, Supplier<String> regla) {
        this.control = control;
        this.lblError = lblError;
        this.regla = regla;
        lblError.setWrapText(true);
        avisoTemporal.setOnFinished(e -> mostrar());
        lblError.managedProperty().bind(lblError.visibleProperty()); // sin error no ocupa espacio
        reiniciar();
        control.focusedProperty().addListener((obs, antes, ahora) -> {
            if (ahora) {
                tocado = true;
            } else if (tocado) {
                mostrar(); // al salir del campo se revisa
            }
        });
    }

    public Node getControl() { return control; }

    /** Revisa el valor sin mostrar nada. */
    public boolean esValido() { return regla.get() == null; }

    /** Revisa el valor y actualiza el mensaje: lo muestra si hay error y lo oculta si ya es válido. */
    public void mostrar() {
        tocado = true;
        avisoTemporal.stop();
        String error = regla.get();
        if (error == null) {
            lblError.setText("");
            lblError.setVisible(false);
            estilo(VALIDO);
        } else {
            aplicarError(error);
        }
    }

    /**
     * Muestra el aviso de que se rechazó un carácter (no permitido o fuera del límite).
     * Desaparece solo a los pocos segundos o en cuanto el usuario modifica el campo.
     */
    public void mostrarRechazo(String mensaje) {
        tocado = true;
        aplicarError(mensaje);
        avisoTemporal.playFromStart();
    }

    private void aplicarError(String mensaje) {
        lblError.setText(mensaje);
        lblError.setVisible(true);
        estilo(INVALIDO);
    }

    public boolean fueTocado() { return tocado; }

    /** Estado inicial: sin mensaje y sin marcas de color. */
    public void reiniciar() {
        avisoTemporal.stop();
        tocado = false;
        lblError.setText("");
        lblError.setVisible(false);
        control.getStyleClass().removeAll(INVALIDO, VALIDO);
    }

    void permitirCambioProgramatico(boolean permitir) {
        if (permitir) {
            control.getProperties().put(Filtros.PROGRAMATICO, Boolean.TRUE);
        } else {
            control.getProperties().remove(Filtros.PROGRAMATICO);
        }
    }

    private void estilo(String clase) {
        control.getStyleClass().removeAll(INVALIDO, VALIDO);
        control.getStyleClass().add(clase);
    }
}
