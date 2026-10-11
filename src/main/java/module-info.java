module com.mycompany.torneodeportivo {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.mycompany.torneodeportivo to javafx.fxml;
    opens com.mycompany.torneodeportivo.controlador to javafx.fxml;
    opens com.mycompany.torneodeportivo.modelo to javafx.base;

    exports com.mycompany.torneodeportivo;
}
