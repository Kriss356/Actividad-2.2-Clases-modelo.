package com.mycompany.torneodeportivo.controlador;

import com.mycompany.torneodeportivo.App;
import com.mycompany.torneodeportivo.modelo.Administrador;
import com.mycompany.torneodeportivo.servicio.DatosServicio;
import com.mycompany.torneodeportivo.util.Alertas;
import com.mycompany.torneodeportivo.util.Sesion;
import com.mycompany.torneodeportivo.util.Validador;
import java.io.IOException;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController {

    @FXML private TextField txtUsuario;
    @FXML private PasswordField txtContrasena;

    @FXML
    private void iniciarSesion() {
        try {
            String usuario = Validador.texto(txtUsuario.getText(), "Usuario");
            String contrasena = Validador.texto(txtContrasena.getText(), "Contraseña");
            Administrador a = DatosServicio.getInstancia().autenticar(usuario, contrasena);
            if (a == null) {
                Alertas.error("Usuario o contraseña incorrectos, o la cuenta no está activa.");
                return;
            }
            Sesion.setActual(a);
            App.setRoot("menu");
        } catch (IllegalArgumentException ex) {
            Alertas.error(ex.getMessage());
        } catch (IOException ex) {
            Alertas.error("No se pudo abrir el menú: " + ex.getMessage());
        }
    }

    /** Cierra la aplicación completa, previa confirmación del usuario. */
    @FXML
    private void salir() {
        if (Alertas.confirmar("¿Deseas cerrar la aplicación?")) {
            Platform.exit();
        }
    }
}
