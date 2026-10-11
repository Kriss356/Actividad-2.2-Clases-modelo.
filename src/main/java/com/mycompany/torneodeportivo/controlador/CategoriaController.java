package com.mycompany.torneodeportivo.controlador;

import com.mycompany.torneodeportivo.modelo.Categoria;
import com.mycompany.torneodeportivo.modelo.EstadoCategoria;
import com.mycompany.torneodeportivo.servicio.DatosServicio;
import com.mycompany.torneodeportivo.util.Alertas;
import com.mycompany.torneodeportivo.util.Validador;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

public class CategoriaController {

    @FXML private TableView<Categoria> tabla;
    @FXML private TableColumn<Categoria, Integer> colId;
    @FXML private TableColumn<Categoria, String> colNombre;
    @FXML private TableColumn<Categoria, Integer> colMin;
    @FXML private TableColumn<Categoria, Integer> colMax;
    @FXML private TableColumn<Categoria, EstadoCategoria> colEstado;

    @FXML private TextField txtNombre;
    @FXML private TextField txtEdadMin;
    @FXML private TextField txtEdadMax;
    @FXML private ComboBox<EstadoCategoria> cmbEstado;
    @FXML private Button btnGuardar;

    private final DatosServicio servicio = DatosServicio.getInstancia();
    private Categoria enEdicion; // null = se está creando un registro nuevo

    @FXML
    private void initialize() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombreCategoria"));
        colMin.setCellValueFactory(new PropertyValueFactory<>("edadMinima"));
        colMax.setCellValueFactory(new PropertyValueFactory<>("edadMaxima"));
        colEstado.setCellValueFactory(new PropertyValueFactory<>("estado"));
        tabla.setItems(servicio.getCategorias());
        cmbEstado.setItems(FXCollections.observableArrayList(EstadoCategoria.values()));
        limpiar();
    }

    @FXML
    private void nuevo() {
        limpiar();
        txtNombre.requestFocus();
    }

    @FXML
    private void guardar() {
        try {
            String nombre = Validador.texto(txtNombre.getText(), "Nombre");
            int min = Validador.entero(txtEdadMin.getText(), "Edad mínima", 10, 60);
            int max = Validador.entero(txtEdadMax.getText(), "Edad máxima", 10, 60);
            EstadoCategoria estado = Validador.seleccion(cmbEstado.getValue(), "Estado");

            if (enEdicion == null) {
                servicio.agregarCategoria(nombre, min, max, estado);
                Alertas.info("Categoría registrada correctamente.");
            } else {
                servicio.actualizarCategoria(enEdicion, nombre, min, max, estado);
                Alertas.info("Categoría actualizada correctamente.");
            }
            tabla.refresh();
            limpiar();
        } catch (IllegalArgumentException ex) {
            Alertas.error(ex.getMessage());
        }
    }

    @FXML
    private void editar() {
        Categoria sel = tabla.getSelectionModel().getSelectedItem();
        if (sel == null) {
            Alertas.error("Selecciona una categoría de la tabla para editarla.");
            return;
        }
        enEdicion = sel;
        txtNombre.setText(sel.getNombreCategoria());
        txtEdadMin.setText(String.valueOf(sel.getEdadMinima()));
        txtEdadMax.setText(String.valueOf(sel.getEdadMaxima()));
        cmbEstado.setValue(sel.getEstado());
        btnGuardar.setText("Actualizar");
    }

    @FXML
    private void eliminar() {
        Categoria sel = tabla.getSelectionModel().getSelectedItem();
        if (sel == null) {
            Alertas.error("Selecciona una categoría de la tabla para eliminarla.");
            return;
        }
        if (!Alertas.confirmar("¿Eliminar la categoría \"" + sel.getNombreCategoria() + "\"?")) return;
        try {
            servicio.eliminarCategoria(sel);
            Alertas.info("Categoría eliminada correctamente.");
            limpiar();
        } catch (IllegalArgumentException ex) {
            Alertas.error(ex.getMessage());
        }
    }

    @FXML
    private void limpiar() {
        txtNombre.clear();
        txtEdadMin.clear();
        txtEdadMax.clear();
        cmbEstado.getSelectionModel().clearSelection();
        tabla.getSelectionModel().clearSelection();
        enEdicion = null;
        btnGuardar.setText("Guardar");
    }
}
