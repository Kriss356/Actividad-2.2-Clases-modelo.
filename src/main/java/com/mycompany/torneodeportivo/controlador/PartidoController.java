package com.mycompany.torneodeportivo.controlador;

import com.mycompany.torneodeportivo.modelo.Equipo;
import com.mycompany.torneodeportivo.modelo.EstadoPartido;
import com.mycompany.torneodeportivo.modelo.EstadoResultado;
import com.mycompany.torneodeportivo.modelo.Partido;
import com.mycompany.torneodeportivo.servicio.DatosServicio;
import com.mycompany.torneodeportivo.util.Alertas;
import com.mycompany.torneodeportivo.util.CampoValidado;
import com.mycompany.torneodeportivo.util.Convertidores;
import com.mycompany.torneodeportivo.util.GestorFormulario;
import com.mycompany.torneodeportivo.util.Reglas;
import java.time.LocalDate;
import java.time.LocalTime;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class PartidoController extends FormularioBase<Partido> {

    @FXML private TableColumn<Partido, Integer> colId;
    @FXML private TableColumn<Partido, String> colLocal, colVisitante, colLugar, colMarcador, colGanador;
    @FXML private TableColumn<Partido, LocalDate> colFecha;
    @FXML private TableColumn<Partido, LocalTime> colHora;
    @FXML private TableColumn<Partido, EstadoPartido> colEstado;

    @FXML private ComboBox<Equipo> cmbLocal, cmbVisitante;
    @FXML private DatePicker dpFecha;
    @FXML private TextField txtHora, txtLugar;
    @FXML private ComboBox<EstadoPartido> cmbEstado;
    @FXML private Label errLocal, errVisitante, errFecha, errHora, errLugar, errEstado;

    // Subformulario del resultado (se valida por separado)
    @FXML private TextField txtGolesLocal, txtGolesVisitante;
    @FXML private ComboBox<EstadoResultado> cmbEstadoResultado;
    @FXML private Label errGolesLocal, errGolesVisitante, errEstadoResultado;
    @FXML private Button btnRegistrar;

    private final GestorFormulario gestorResultado = new GestorFormulario(this::actualizarBotones);

    @FXML
    private void initialize() {
        DatosServicio servicio = DatosServicio.getInstancia();
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
        dpFecha.setEditable(false); // la fecha se elige en el calendario

        // Formulario del partido
        gestor.agregar(cmbLocal, errLocal, cmbLocal.valueProperty(), Reglas.seleccion(cmbLocal.valueProperty()));
        CampoValidado visitante = gestor.agregar(cmbVisitante, errVisitante, cmbVisitante.valueProperty(), () -> {
            Equipo v = cmbVisitante.getValue();
            Equipo l = cmbLocal.getValue();
            if (v == null) return "Selecciona una opción.";
            if (l != null && v.equals(l)) return "El visitante debe ser distinto del local.";
            if (l != null && !v.getCategoria().equals(l.getCategoria())) {
                return "Debe ser de la misma categoría que el local (" + l.getNombreCategoria() + ").";
            }
            return null;
        });
        gestor.revalidarCuando(visitante, cmbLocal.valueProperty()); // si cambia el local, se revisa el visitante
        gestor.agregar(dpFecha, errFecha, dpFecha.valueProperty(), Reglas.fecha(dpFecha.valueProperty()));
        gestor.agregarTexto(txtHora, errHora, Reglas.hora(txtHora), Reglas.CAR_HORA, 5, Reglas.MSG_HORA);
        gestor.agregarTexto(txtLugar, errLugar, Reglas.textoGeneral(txtLugar, 3),
                Reglas.CAR_TEXTO, 40, Reglas.MSG_TEXTO);
        gestor.agregar(cmbEstado, errEstado, cmbEstado.valueProperty(), Reglas.seleccion(cmbEstado.valueProperty()));

        // Subformulario del resultado
        gestorResultado.agregarTexto(txtGolesLocal, errGolesLocal, Reglas.entero(txtGolesLocal, 0, 30),
                Reglas.CAR_ENTERO, 2, Reglas.MSG_ENTERO);
        gestorResultado.agregarTexto(txtGolesVisitante, errGolesVisitante, Reglas.entero(txtGolesVisitante, 0, 30),
                Reglas.CAR_ENTERO, 2, Reglas.MSG_ENTERO);
        gestorResultado.agregar(cmbEstadoResultado, errEstadoResultado, cmbEstadoResultado.valueProperty(),
                Reglas.seleccion(cmbEstadoResultado.valueProperty()));
        tabla.getSelectionModel().selectedItemProperty().addListener((obs, antes, ahora) -> actualizarBotones());

        iniciarFormulario();
    }

    /** Registrar resultado: solo comprueba los datos; en esta actividad no se guarda nada. */
    @FXML
    private void registrarResultado() {
        if (gestorResultado.validarTodo()) {
            Alertas.informacion("Validación correcta",
                    "Los datos del resultado cumplen con las validaciones.\n\n"
                    + "En esta actividad no se guarda, modifica ni elimina información.");
        } else {
            gestorResultado.enfocarPrimerInvalido();
        }
    }

    @Override
    protected void actualizarBotones() {
        super.actualizarBotones();
        if (btnRegistrar != null) {
            btnRegistrar.setDisable(tabla.getSelectionModel().getSelectedItem() == null || !gestorResultado.esValido());
        }
    }

    @Override
    protected void limpiarCampos() {
        cmbLocal.getSelectionModel().clearSelection();
        cmbVisitante.getSelectionModel().clearSelection();
        dpFecha.setValue(null);
        txtHora.clear();
        txtLugar.clear();
        cmbEstado.getSelectionModel().clearSelection();
        gestorResultado.silenciar(() -> {
            txtGolesLocal.clear();
            txtGolesVisitante.clear();
            cmbEstadoResultado.getSelectionModel().clearSelection();
        });
    }

    @Override
    protected void cargarCampos(Partido p) {
        cmbLocal.setValue(p.getEquipoLocal());
        cmbVisitante.setValue(p.getEquipoVisitante());
        dpFecha.setValue(p.getFecha());
        txtHora.setText(p.getHora().toString());
        txtLugar.setText(p.getLugar());
        cmbEstado.setValue(p.getEstado());
        gestorResultado.silenciar(() -> {
            if (p.getResultado() != null) {
                txtGolesLocal.setText(String.valueOf(p.getResultado().getGolesLocal()));
                txtGolesVisitante.setText(String.valueOf(p.getResultado().getGolesVisitante()));
                cmbEstadoResultado.setValue(p.getResultado().getEstado());
            } else {
                txtGolesLocal.clear();
                txtGolesVisitante.clear();
                cmbEstadoResultado.getSelectionModel().clearSelection();
            }
        });
    }

    @Override
    protected String describir(Partido p) {
        return p.getNombreLocal() + " vs " + p.getNombreVisitante() + " (" + p.getFecha() + ")";
    }
}
