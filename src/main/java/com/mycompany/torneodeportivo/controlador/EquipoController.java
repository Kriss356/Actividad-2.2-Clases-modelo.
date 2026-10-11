package com.mycompany.torneodeportivo.controlador;

import com.mycompany.torneodeportivo.modelo.Categoria;
import com.mycompany.torneodeportivo.modelo.Equipo;
import com.mycompany.torneodeportivo.modelo.EstadoEquipo;
import com.mycompany.torneodeportivo.servicio.DatosServicio;
import com.mycompany.torneodeportivo.util.Alertas;
import com.mycompany.torneodeportivo.util.Convertidores;
import com.mycompany.torneodeportivo.util.ImagenUtil;
import com.mycompany.torneodeportivo.util.Validador;
import java.io.File;
import java.io.IOException;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class EquipoController {

    @FXML private TableView<Equipo> tabla;
    @FXML private TableColumn<Equipo, Integer> colId;
    @FXML private TableColumn<Equipo, String> colNombre;
    @FXML private TableColumn<Equipo, String> colUniversidad;
    @FXML private TableColumn<Equipo, String> colEntrenador;
    @FXML private TableColumn<Equipo, String> colCategoria;
    @FXML private TableColumn<Equipo, EstadoEquipo> colEstado;

    @FXML private TextField txtNombre;
    @FXML private TextField txtUniversidad;
    @FXML private TextField txtEntrenador;
    @FXML private ComboBox<Categoria> cmbCategoria;
    @FXML private ComboBox<EstadoEquipo> cmbEstado;
    @FXML private ImageView imgLogo;
    @FXML private Button btnGuardar;

    private final DatosServicio servicio = DatosServicio.getInstancia();
    private Equipo enEdicion;
    private File logoElegido; // imagen escogida pero aún no copiada a la carpeta del proyecto

    @FXML
    private void initialize() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombreEquipo"));
        colUniversidad.setCellValueFactory(new PropertyValueFactory<>("universidad"));
        colEntrenador.setCellValueFactory(new PropertyValueFactory<>("entrenador"));
        colCategoria.setCellValueFactory(new PropertyValueFactory<>("nombreCategoria"));
        colEstado.setCellValueFactory(new PropertyValueFactory<>("estado"));
        tabla.setItems(servicio.getEquipos());
        cmbCategoria.setItems(servicio.getCategorias());
        cmbCategoria.setConverter(Convertidores.categorias());
        cmbEstado.setItems(FXCollections.observableArrayList(EstadoEquipo.values()));
        limpiar();
    }

    @FXML
    private void nuevo() {
        limpiar();
        txtNombre.requestFocus();
    }

    @FXML
    private void seleccionarLogo() {
        File f = ImagenUtil.elegir(imgLogo.getScene().getWindow());
        if (f != null) {
            logoElegido = f;
            imgLogo.setImage(new Image(f.toURI().toString(), 140, 140, true, true));
        }
    }

    @FXML
    private void guardar() {
        try {
            String nombre = Validador.texto(txtNombre.getText(), "Nombre del equipo");
            String universidad = Validador.texto(txtUniversidad.getText(), "Universidad");
            String entrenador = Validador.texto(txtEntrenador.getText(), "Entrenador");
            Categoria categoria = Validador.seleccion(cmbCategoria.getValue(), "Categoría");
            EstadoEquipo estado = Validador.seleccion(cmbEstado.getValue(), "Estado");

            String anterior = (enEdicion == null) ? null : enEdicion.getRutaLogo();
            String ruta = anterior;
            String nueva = null;
            if (logoElegido != null) {            // se eligió una imagen nueva: copiarla al proyecto
                nueva = ImagenUtil.guardar(logoElegido);
                ruta = nueva;
            }
            try {
                if (enEdicion == null) {
                    servicio.agregarEquipo(nombre, universidad, entrenador, categoria, ruta, estado);
                } else {
                    servicio.actualizarEquipo(enEdicion, nombre, universidad, entrenador, categoria, ruta, estado);
                }
            } catch (IllegalArgumentException ex) {
                if (nueva != null) ImagenUtil.borrar(nueva); // no dejar imágenes huérfanas
                throw ex;
            }
            if (nueva != null && anterior != null) ImagenUtil.borrar(anterior); // se reemplazó el logo

            Alertas.info(enEdicion == null ? "Equipo registrado correctamente." : "Equipo actualizado correctamente.");
            tabla.refresh();
            limpiar();
        } catch (IllegalArgumentException ex) {
            Alertas.error(ex.getMessage());
        } catch (IOException ex) {
            Alertas.error("No se pudo guardar la imagen: " + ex.getMessage());
        }
    }

    @FXML
    private void editar() {
        Equipo sel = tabla.getSelectionModel().getSelectedItem();
        if (sel == null) {
            Alertas.error("Selecciona un equipo de la tabla para editarlo.");
            return;
        }
        enEdicion = sel;
        logoElegido = null;
        txtNombre.setText(sel.getNombreEquipo());
        txtUniversidad.setText(sel.getUniversidad());
        txtEntrenador.setText(sel.getEntrenador());
        cmbCategoria.setValue(sel.getCategoria());
        cmbEstado.setValue(sel.getEstado());
        imgLogo.setImage(ImagenUtil.cargar(sel.getRutaLogo()));
        btnGuardar.setText("Actualizar");
    }

    @FXML
    private void eliminar() {
        Equipo sel = tabla.getSelectionModel().getSelectedItem();
        if (sel == null) {
            Alertas.error("Selecciona un equipo de la tabla para eliminarlo.");
            return;
        }
        if (!Alertas.confirmar("¿Eliminar el equipo \"" + sel.getNombreEquipo() + "\"?")) return;
        try {
            servicio.eliminarEquipo(sel);
            ImagenUtil.borrar(sel.getRutaLogo());
            Alertas.info("Equipo eliminado correctamente.");
            limpiar();
        } catch (IllegalArgumentException ex) {
            Alertas.error(ex.getMessage());
        }
    }

    @FXML
    private void limpiar() {
        txtNombre.clear();
        txtUniversidad.clear();
        txtEntrenador.clear();
        cmbCategoria.getSelectionModel().clearSelection();
        cmbEstado.getSelectionModel().clearSelection();
        imgLogo.setImage(null);
        logoElegido = null;
        tabla.getSelectionModel().clearSelection();
        enEdicion = null;
        btnGuardar.setText("Guardar");
    }
}
