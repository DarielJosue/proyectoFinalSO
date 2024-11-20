module com.proyectofinalsofx {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.proyectofinalsofx to javafx.fxml;
    exports com.proyectofinalsofx;
}
