package com.mycompany.torneodeportivo.controlador;

import com.mycompany.torneodeportivo.modelo.Administrador;
import com.mycompany.torneodeportivo.modelo.EstadoAdministrador;
import com.mycompany.torneodeportivo.servicio.DatosServicio;
import com.mycompany.torneodeportivo.util.Reglas;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class AdministradorController extends FormularioBase<Administrador> {

    @FXML private TableColumn<Administrador, Integer> colId, colEdad;
    @FXML private TableColumn<Administrador, String> colNombre, colCorreo, colUsuario, colRol;
    @FXML private TableColumn<Administrador, EstadoAdministrador> colEstado;

    @FXML private TextField txtNombre, txtEdad, txtCorreo, txtUsuario;
    @FXML private PasswordField txtContrasena;
    @FXML private ComboBox<String> cmbRol;
    @FXML private ComboBox<EstadoAdministrador> cmbEstado;
    @FXML private Label errNombre, errEdad, errCorreo, errUsuario, errContrasena, errRol, errEstado;

    @FXML
    private void initialize() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colEdad.setCellValueFactory(new PropertyValueFactory<>("edad"));
        colCorreo.setCellValueFactory(new PropertyValueFactory<>("correo"));
        colUsuario.setCellValueFactory(new PropertyValueFactory<>("usuario"));
        colRol.setCellValueFactory(new PropertyValueFactory<>("rol"));
        colEstado.setCellValueFactory(new PropertyValueFactory<>("estado"));
        tabla.setItems(DatosServicio.getInstancia().getAdministradores());
        cmbRol.setItems(FXCollections.observableArrayList(
                "Administrador de equipos", "Administrador de partidos", "Administrador del torneo"));
        cmbEstado.setItems(FXCollections.observableArrayList(EstadoAdministrador.values()));

        gestor.agregarTexto(txtNombre, errNombre, Reglas.persona(txtNombre, 3),
                Reglas.CAR_PERSONA, 40, Reglas.MSG_PERSONA);
        gestor.agregarTexto(txtEdad, errEdad, Reglas.entero(txtEdad, 18, 70), Reglas.CAR_ENTERO, 2, Reglas.MSG_ENTERO);
        gestor.agregarTexto(txtCorreo, errCorreo, Reglas.correo(txtCorreo), Reglas.CAR_CORREO, 40, Reglas.MSG_CORREO);
        gestor.agregarTexto(txtUsuario, errUsuario, Reglas.usuario(txtUsuario, 4),
                Reglas.CAR_USUARIO, 20, Reglas.MSG_USUARIO);
        gestor.agregarTexto(txtContrasena, errContrasena, Reglas.contrasena(txtContrasena, 4),
                Reglas.CAR_CUALQUIERA, 20, Reglas.MSG_CUALQUIERA);
        gestor.agregar(cmbRol, errRol, cmbRol.valueProperty(), Reglas.seleccion(cmbRol.valueProperty()));
        gestor.agregar(cmbEstado, errEstado, cmbEstado.valueProperty(), Reglas.seleccion(cmbEstado.valueProperty()));

        iniciarFormulario();
    }

    @Override
    protected void limpiarCampos() {
        txtNombre.clear();
        txtEdad.clear();
        txtCorreo.clear();
        txtUsuario.clear();
        txtContrasena.clear();
        cmbRol.getSelectionModel().clearSelection();
        cmbEstado.getSelectionModel().clearSelection();
    }

    @Override
    protected void cargarCampos(Administrador a) {
        txtNombre.setText(a.getNombre());
        txtEdad.setText(String.valueOf(a.getEdad()));
        txtCorreo.setText(a.getCorreo());
        txtUsuario.setText(a.getUsuario());
        txtContrasena.setText(a.getContrasena());
        cmbRol.setValue(a.getRol());
        cmbEstado.setValue(a.getEstado());
    }

    @Override
    protected String describir(Administrador a) {
        return a.getNombre() + " (" + a.getUsuario() + ")";
    }
}
