package com.mycompany.torneodeportivo.controlador;

import com.mycompany.torneodeportivo.util.Alertas;
import com.mycompany.torneodeportivo.util.GestorFormulario;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;

/**
 * Comportamiento común de todos los formularios: estado de los botones, Nuevo, Validar, Guardar,
 * Editar, Eliminar y Limpiar. Cada controlador concreto solo registra sus campos y define cómo
 * limpiarlos y cargarlos.
 *
 * En esta actividad Guardar, Editar y Eliminar son solo visuales: no modifican ningún dato.
 */
public abstract class FormularioBase<T> {

    @FXML protected TableView<T> tabla;
    @FXML protected Button btnNuevo, btnValidar, btnGuardar, btnEditar, btnEliminar, btnLimpiar;

    /** Gestiona los campos validados; cada cambio vuelve a calcular el estado de los botones. */
    protected final GestorFormulario gestor = new GestorFormulario(this::actualizarBotones);

    /** Deja vacíos todos los campos del formulario. */
    protected abstract void limpiarCampos();

    /** Muestra en el formulario los datos del registro seleccionado (acción visual). */
    protected abstract void cargarCampos(T registro);

    /** Texto corto que identifica a un registro en los mensajes. */
    protected abstract String describir(T registro);

    /** Debe llamarse al final del initialize() de cada controlador. */
    protected final void iniciarFormulario() {
        // Editar y Eliminar solo se habilitan cuando hay un registro seleccionado en la tabla
        btnEditar.disableProperty().bind(tabla.getSelectionModel().selectedItemProperty().isNull());
        btnEliminar.disableProperty().bind(tabla.getSelectionModel().selectedItemProperty().isNull());
        restablecer();
    }

    /** Nuevo, Validar y Limpiar siempre disponibles; Guardar solo si todo el formulario es válido. */
    protected void actualizarBotones() {
        if (btnGuardar == null) return;
        btnNuevo.setDisable(false);
        btnValidar.setDisable(false);
        btnLimpiar.setDisable(false);
        btnGuardar.setDisable(!gestor.esValido());
    }

    @FXML
    protected void nuevo() {
        restablecer();
        gestor.enfocarPrimero();
    }

    @FXML
    protected void limpiar() {
        restablecer();
    }

    @FXML
    protected void validar() {
        validarFormulario();
    }

    @FXML
    protected void guardar() {
        validarFormulario(); // en esta actividad Guardar solo valida; no guarda información
    }

    @FXML
    protected void editar() {
        T seleccionado = tabla.getSelectionModel().getSelectedItem();
        if (seleccionado == null) return;
        gestor.silenciar(() -> cargarCampos(seleccionado));
        gestor.validarTodo();
        actualizarBotones();
    }

    @FXML
    protected void eliminar() {
        T seleccionado = tabla.getSelectionModel().getSelectedItem();
        if (seleccionado == null) return;
        Alertas.informacion("Eliminar (acción visual)",
                "Registro seleccionado: " + describir(seleccionado)
                + "\n\nEn esta actividad la eliminación es solo visual: no se eliminó ninguna información.");
        tabla.getSelectionModel().clearSelection();
    }

    private void validarFormulario() {
        if (gestor.validarTodo()) {
            Alertas.informacion("Validación correcta",
                    "Todos los datos cumplen con las validaciones.\n\n"
                    + "En esta actividad no se guarda, modifica ni elimina información.");
        } else {
            gestor.enfocarPrimerInvalido();
        }
        actualizarBotones();
    }

    private void restablecer() {
        tabla.getSelectionModel().clearSelection();
        gestor.silenciar(this::limpiarCampos);
        actualizarBotones();
    }
}
