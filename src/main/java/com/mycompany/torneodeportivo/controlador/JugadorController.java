package com.mycompany.torneodeportivo.controlador;

import com.mycompany.torneodeportivo.modelo.Equipo;
import com.mycompany.torneodeportivo.modelo.EstadoJugador;
import com.mycompany.torneodeportivo.modelo.Jugador;
import com.mycompany.torneodeportivo.modelo.PosicionCampo;
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

public class JugadorController {

    @FXML private TableView<Jugador> tabla;
    @FXML private TableColumn<Jugador, Integer> colId;
    @FXML private TableColumn<Jugador, String> colNombre;
    @FXML private TableColumn<Jugador, Integer> colEdad;
    @FXML private TableColumn<Jugador, Integer> colNumero;
    @FXML private TableColumn<Jugador, PosicionCampo> colPosicion;
    @FXML private TableColumn<Jugador, String> colEquipo;
    @FXML private TableColumn<Jugador, EstadoJugador> colEstado;

    @FXML private TextField txtNombre;
    @FXML private TextField txtEdad;
    @FXML private TextField txtNumero;
    @FXML private ComboBox<PosicionCampo> cmbPosicion;
    @FXML private ComboBox<Equipo> cmbEquipo;
    @FXML private ComboBox<EstadoJugador> cmbEstado;
    @FXML private ImageView imgFoto;
    @FXML private Button btnGuardar;

    private final DatosServicio servicio = DatosServicio.getInstancia();
    private Jugador enEdicion;
    private File fotoElegida;

    @FXML
    private void initialize() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colEdad.setCellValueFactory(new PropertyValueFactory<>("edad"));
        colNumero.setCellValueFactory(new PropertyValueFactory<>("numeroCamiseta"));
        colPosicion.setCellValueFactory(new PropertyValueFactory<>("posicionCampo"));
        colEquipo.setCellValueFactory(new PropertyValueFactory<>("nombreEquipo"));
        colEstado.setCellValueFactory(new PropertyValueFactory<>("estado"));
        tabla.setItems(servicio.getJugadores());
        cmbEquipo.setItems(servicio.getEquipos());
        cmbEquipo.setConverter(Convertidores.equipos());
        cmbPosicion.setItems(FXCollections.observableArrayList(PosicionCampo.values()));
        cmbEstado.setItems(FXCollections.observableArrayList(EstadoJugador.values()));
        limpiar();
    }

    @FXML
    private void nuevo() {
        limpiar();
        txtNombre.requestFocus();
    }

    @FXML
    private void seleccionarFoto() {
        File f = ImagenUtil.elegir(imgFoto.getScene().getWindow());
        if (f != null) {
            fotoElegida = f;
            imgFoto.setImage(new Image(f.toURI().toString(), 140, 140, true, true));
        }
    }

    @FXML
    private void guardar() {
        try {
            String nombre = Validador.texto(txtNombre.getText(), "Nombre");
            int edad = Validador.entero(txtEdad.getText(), "Edad", 15, 45);
            int numero = Validador.entero(txtNumero.getText(), "Número de camiseta", 1, 99);
            PosicionCampo posicion = Validador.seleccion(cmbPosicion.getValue(), "Posición");
            Equipo equipo = Validador.seleccion(cmbEquipo.getValue(), "Equipo");
            EstadoJugador estado = Validador.seleccion(cmbEstado.getValue(), "Estado");

            String anterior = (enEdicion == null) ? null : enEdicion.getRutaFoto();
            String ruta = anterior;
            String nueva = null;
            if (fotoElegida != null) {
                nueva = ImagenUtil.guardar(fotoElegida);
                ruta = nueva;
            }
            try {
                if (enEdicion == null) {
                    servicio.agregarJugador(nombre, edad, numero, posicion, equipo, ruta, estado);
                } else {
                    servicio.actualizarJugador(enEdicion, nombre, edad, numero, posicion, equipo, ruta, estado);
                }
            } catch (IllegalArgumentException ex) {
                if (nueva != null) ImagenUtil.borrar(nueva);
                throw ex;
            }
            if (nueva != null && anterior != null) ImagenUtil.borrar(anterior);

            Alertas.info(enEdicion == null ? "Jugador registrado correctamente." : "Jugador actualizado correctamente.");
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
        Jugador sel = tabla.getSelectionModel().getSelectedItem();
        if (sel == null) {
            Alertas.error("Selecciona un jugador de la tabla para editarlo.");
            return;
        }
        enEdicion = sel;
        fotoElegida = null;
        txtNombre.setText(sel.getNombre());
        txtEdad.setText(String.valueOf(sel.getEdad()));
        txtNumero.setText(String.valueOf(sel.getNumeroCamiseta()));
        cmbPosicion.setValue(sel.getPosicionCampo());
        cmbEquipo.setValue(sel.getEquipo());
        cmbEstado.setValue(sel.getEstado());
        imgFoto.setImage(ImagenUtil.cargar(sel.getRutaFoto()));
        btnGuardar.setText("Actualizar");
    }

    @FXML
    private void eliminar() {
        Jugador sel = tabla.getSelectionModel().getSelectedItem();
        if (sel == null) {
            Alertas.error("Selecciona un jugador de la tabla para eliminarlo.");
            return;
        }
        if (!Alertas.confirmar("¿Eliminar al jugador \"" + sel.getNombre() + "\"?")) return;
        servicio.eliminarJugador(sel);
        ImagenUtil.borrar(sel.getRutaFoto());
        Alertas.info("Jugador eliminado correctamente.");
        limpiar();
    }

    @FXML
    private void limpiar() {
        txtNombre.clear();
        txtEdad.clear();
        txtNumero.clear();
        cmbPosicion.getSelectionModel().clearSelection();
        cmbEquipo.getSelectionModel().clearSelection();
        cmbEstado.getSelectionModel().clearSelection();
        imgFoto.setImage(null);
        fotoElegida = null;
        tabla.getSelectionModel().clearSelection();
        enEdicion = null;
        btnGuardar.setText("Guardar");
    }
}
