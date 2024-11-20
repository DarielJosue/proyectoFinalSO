package com.proyectofinalsofx;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class VentanaMenu {

    public static GestorMemoria gestorMemoria = new GestorMemoria(32);
    public static GestorRecursos gestorRecursos = new GestorRecursos();
    public static Planificador planificador = new Planificador(gestorMemoria, gestorRecursos);
    public static Simulador simulador = new Simulador(planificador);
    public static SistemaPrincipalVista vista; // Declaramos el objeto de la vista
    public Button iniciarBoton;
    private Stage stage;

    public VentanaMenu(Stage stage) {
        this.stage = stage;

        stage.setTitle("Bienvenido al Sistema Operativo");

        BorderPane root = new BorderPane();

        Image fondo = new Image(getClass().getResource("/com/proyectofinalsofx/gif1.gif").toExternalForm());
        ImageView fondoGif = new ImageView(fondo);

        fondoGif.setPreserveRatio(true);

        root.widthProperty().addListener((obs, oldWidth, newWidth) -> fondoGif.setFitWidth(newWidth.doubleValue()));
        root.heightProperty()
                .addListener((obs, oldHeight, newHeight) -> fondoGif.setFitHeight(newHeight.doubleValue()));

        root.setCenter(fondoGif);

        StackPane panelBoton = new StackPane();
        panelBoton.setStyle("-fx-background-color: transparent;");

        iniciarBoton = new Button("Iniciar Sistema");
        iniciarBoton.setFont(new Font("Arial", 20));
        iniciarBoton.setTextFill(Color.WHITE);
        iniciarBoton.setStyle("-fx-background-color: #0066cc; -fx-border-color: white; -fx-border-width: 2px;");
        iniciarBoton.setOnMouseEntered(e -> iniciarBoton
                .setStyle("-fx-background-color: #0057b8; -fx-border-color: white; -fx-border-width: 2px;"));
        iniciarBoton.setOnMouseExited(e -> iniciarBoton
                .setStyle("-fx-background-color: #0066cc; -fx-border-color: white; -fx-border-width: 2px;"));

        panelBoton.getChildren().add(iniciarBoton);
        root.setBottom(panelBoton);

        // Acción al presionar el botón "Iniciar Sistema"
        iniciarBoton.setOnAction(e -> {

            vista = new SistemaPrincipalVista(stage);
            vista.iniciarEjecucion();
            stage.close();
            vista.show();
        });

        Scene scene = new Scene(root, 900, 600);
        scene.widthProperty().addListener((obs, oldVal, newVal) -> fondoGif.setFitWidth(newVal.doubleValue()));
        scene.heightProperty().addListener((obs, oldVal, newVal) -> fondoGif.setFitHeight(newVal.doubleValue()));
        stage.setFullScreen(true);

        stage.setScene(scene);

        stage.show();
    }

    public Stage getStage() {
        return stage;
    }

    public void mostrarVentana() {
        Platform.runLater(() -> {

            Stage stage = new Stage();
            new VentanaMenu(stage);
        });
    }
}
