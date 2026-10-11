package com.mycompany.torneodeportivo.util;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public class Alertas {
    public static void info(String mensaje) { mostrar(Alert.AlertType.INFORMATION, "Operación exitosa", mensaje); }
    public static void error(String mensaje) { mostrar(Alert.AlertType.ERROR, "Error", mensaje); }

    public static boolean confirmar(String mensaje) {
        Alert a = new Alert(Alert.AlertType.CONFIRMATION, mensaje, ButtonType.YES, ButtonType.NO);
        a.setTitle("Confirmar");
        a.setHeaderText(null);
        return a.showAndWait().orElse(ButtonType.NO) == ButtonType.YES;
    }

    private static void mostrar(Alert.AlertType tipo, String titulo, String mensaje) {
        Alert a = new Alert(tipo, mensaje);
        a.setTitle(titulo);
        a.setHeaderText(null);
        a.showAndWait();
    }
}
