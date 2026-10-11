package com.mycompany.torneodeportivo.controlador;

import com.mycompany.torneodeportivo.modelo.*;
import com.mycompany.torneodeportivo.servicio.DatosServicio;
import com.mycompany.torneodeportivo.util.Alertas;
import com.mycompany.torneodeportivo.util.Convertidores;
import com.mycompany.torneodeportivo.util.Sesion;
import com.mycompany.torneodeportivo.util.Validador;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

public class PartidoController {

    @FXML private ScrollPane panelForm;
    @FXML private TableView<Partido> tabla;
    @FXML private TableColumn<Partido, Integer> colId;
    @FXML private TableColumn<Partido, String> colLocal;
    @FXML private TableColumn<Partido, String> colVisitante;
    @FXML private TableColumn<Partido, LocalDate> colFecha;
    @FXML private TableColumn<Partido, LocalTime> colHora;
    @FXML private TableColumn<Partido, String> colLugar;
    @FXML private TableColumn<Partido, EstadoPartido> colEstado;
    @FXML private TableColumn<Partido, String> colMarcador;
    @FXML private TableColumn<Partido, String> colGanador;

    @FXML private ComboBox<Equipo> cmbLocal;
    @FXML private ComboBox<Equipo> cmbVisitante;
    @FXML private DatePicker dpFecha;
    @FXML private TextField txtHora;
    @FXML private TextField txtLugar;
    @FXML private ComboBox<EstadoPartido> cmbEstado;
    @FXML private TextField txtGolesLocal;
    @FXML private TextField txtGolesVisitante;
    @FXML private ComboBox<EstadoResultado> cmbEstadoResultado;
    @FXML private Button btnGuardar;

    private final DatosServicio servicio = DatosServicio.getInstancia();
    private Partido enEdicion;

    @FXML
    private void initialize() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colLocal.setCellValueFactory(new PropertyValueFactory<>("nombreLocal"));
        colVisitante.setCellValueFactory(new PropertyValueFactory<>("nombreVisitante"));
        colFecha.setCellValueFactory(new PropertyValueFactory<>("fecha"));
        colHora.setCellValueFactory(new PropertyValueFactory<>("hora"));
        colLugar.setCellValueFactory(new PropertyValueFactory<>("lugar"));
        colEstado.setCellValueFactory(new PropertyValueFactory<>("estado"));
        colMarcador.setCellValueFactory(new PropertyValueFactory<>("marcador"));
        colGanador.setCellValueFactory(d -> new SimpleStringProperty(
                d.getValue().getResultado() == null ? "—" : d.getValue().getResultado().getGanadorTexto()));

        tabla.setItems(servicio.getPartidos());
        cmbLocal.setItems(servicio.getEquipos());
        cmbLocal.setConverter(Convertidores.equipos());
        cmbVisitante.setItems(servicio.getEquipos());
        cmbVisitante.setConverter(Convertidores.equipos());
        cmbEstado.setItems(FXCollections.observableArrayList(EstadoPartido.values()));
        cmbEstadoResultado.setItems(FXCollections.observableArrayList(EstadoResultado.values()));

        // Solo el AdministradorPartidos puede modificar; el AdministradorTorneo únicamente consulta
        boolean puedeEditar = Sesion.esPartidos();
        panelForm.setVisible(puedeEditar);
        panelForm.setManaged(puedeEditar);
        limpiar();
    }

    @FXML
    private void nuevo() { limpiar(); }

    @FXML
    private void guardar() {
        try {
            Equipo local = Validador.seleccion(cmbLocal.getValue(), "Equipo local");
            Equipo visitante = Validador.seleccion(cmbVisitante.getValue(), "Equipo visitante");
            LocalDate fecha = Validador.seleccion(dpFecha.getValue(), "Fecha");
            LocalTime hora = leerHora(txtHora.getText());
            String lugar = Validador.texto(txtLugar.getText(), "Lugar");
            EstadoPartido estado = Validador.seleccion(cmbEstado.getValue(), "Estado");

            if (enEdicion == null) {
                servicio.agregarPartido(local, visitante, fecha, hora, lugar, estado);
                Alertas.info("Partido programado correctamente.");
            } else {
                servicio.actualizarPartido(enEdicion, local, visitante, fecha, hora, lugar, estado);
                Alertas.info("Partido actualizado correctamente.");
            }
            tabla.refresh();
            limpiar();
        } catch (IllegalArgumentException ex) {
            Alertas.error(ex.getMessage());
        }
    }

    @FXML
    private void editar() {
        Partido sel = tabla.getSelectionModel().getSelectedItem();
        if (sel == null) {
            Alertas.error("Selecciona un partido de la tabla para editarlo.");
            return;
        }
        enEdicion = sel;
        cmbLocal.setValue(sel.getEquipoLocal());
        cmbVisitante.setValue(sel.getEquipoVisitante());
        dpFecha.setValue(sel.getFecha());
        txtHora.setText(sel.getHora().toString());
        txtLugar.setText(sel.getLugar());
        cmbEstado.setValue(sel.getEstado());
        if (sel.getResultado() != null) {
            txtGolesLocal.setText(String.valueOf(sel.getResultado().getGolesLocal()));
            txtGolesVisitante.setText(String.valueOf(sel.getResultado().getGolesVisitante()));
            cmbEstadoResultado.setValue(sel.getResultado().getEstado());
        }
        btnGuardar.setText("Actualizar");
    }

    @FXML
    private void eliminar() {
        Partido sel = tabla.getSelectionModel().getSelectedItem();
        if (sel == null) {
            Alertas.error("Selecciona un partido de la tabla para eliminarlo.");
            return;
        }
        if (!Alertas.confirmar("¿Eliminar el partido " + sel.getNombreLocal() + " vs " + sel.getNombreVisitante() + "?")) return;
        servicio.eliminarPartido(sel);
        Alertas.info("Partido eliminado correctamente. La tabla de posiciones fue recalculada.");
        limpiar();
    }

    @FXML
    private void registrarResultado() {
        try {
            Partido sel = tabla.getSelectionModel().getSelectedItem();
            if (sel == null) {
                throw new IllegalArgumentException("Selecciona en la tabla el partido al que quieres registrar el resultado.");
            }
            if (sel.getEstado() == EstadoPartido.SUSPENDIDO) {
                throw new IllegalArgumentException("No se puede registrar el resultado de un partido suspendido.");
            }
            int gl = Validador.entero(txtGolesLocal.getText(), "Goles del local", 0, 30);
            int gv = Validador.entero(txtGolesVisitante.getText(), "Goles del visitante", 0, 30);
            EstadoResultado er = Validador.seleccion(cmbEstadoResultado.getValue(), "Estado del resultado");

            servicio.registrarResultado(sel, gl, gv, er);
            Alertas.info("Resultado registrado. El partido pasó a Finalizado y la tabla de posiciones se actualizó.");
            tabla.refresh();
            limpiar();
        } catch (IllegalArgumentException ex) {
            Alertas.error(ex.getMessage());
        }
    }

    @FXML
    private void limpiar() {
        cmbLocal.getSelectionModel().clearSelection();
        cmbVisitante.getSelectionModel().clearSelection();
        dpFecha.setValue(null);
        txtHora.clear();
        txtLugar.clear();
        cmbEstado.getSelectionModel().clearSelection();
        txtGolesLocal.clear();
        txtGolesVisitante.clear();
        cmbEstadoResultado.getSelectionModel().clearSelection();
        tabla.getSelectionModel().clearSelection();
        enEdicion = null;
        btnGuardar.setText("Guardar");
    }

    private LocalTime leerHora(String texto) {
        String t = Validador.texto(texto, "Hora");
        try {
            return LocalTime.parse(t);
        } catch (DateTimeParseException ex) {
            throw new IllegalArgumentException("La hora debe tener el formato HH:mm, por ejemplo 16:30.");
        }
    }
}
