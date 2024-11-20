package com.proyectofinalsofx;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.util.ArrayList;

public class SistemaPrincipalVista {

    private TableView<Proceso> tabla;
    private ArrayList<GridPane> bloquesMemoria;
    public static GestorMemoria gestorMemoria = new GestorMemoria(32);
    public static GestorRecursos gestorRecursos = new GestorRecursos();
    public static Planificador planificador = new Planificador(gestorMemoria, gestorRecursos);
    public static Simulador simulador = new Simulador(planificador);
    private ArrayList<Proceso> procesos;
    private volatile boolean ejecucionActiva = true;
    private Stage stage;

    public SistemaPrincipalVista(Stage stage) {
        this.stage = stage;
        simulador.cargarProcesos("src\\main\\java\\com\\proyectofinalsofx\\procesos.txt");
        procesos = new ArrayList<>(simulador.getProcesos());

        BorderPane root = new BorderPane();
        root.setPrefSize(900, 600);

        Tabla tablaProcesos = new Tabla(FXCollections.observableArrayList(procesos));

        tabla = tablaProcesos.getTableView();

        root.setCenter(tabla);

        crearPanelMemoria(root);

        Scene scene = new Scene(root);
        stage.setTitle("Simulador de sistema operativo");
        stage.setOnCloseRequest(event -> {
            ejecucionActiva = false;
            Platform.exit();
            System.exit(0);
        });
        stage.setScene(scene);
        stage.show();
    }

    private void crearPanelMemoria(BorderPane root) {

        GridPane panelMemoria = new GridPane();
        panelMemoria.setHgap(5);
        panelMemoria.setVgap(5);
        panelMemoria.setAlignment(Pos.CENTER);

        panelMemoria.setStyle("-fx-background-color: linear-gradient(from 0% 0% to 100% 100%, #0000FF, #ADD8E6);");

        bloquesMemoria = new ArrayList<>();
        for (int i = 0; i < 32; i++) {

            GridPane bloque = new GridPane();
            bloque.setPrefSize(120, 60);
            bloque.setStyle(
                    "-fx-border-color: #ADD8E6; " +
                            "-fx-background-color: lightgray; " +
                            "-fx-border-width: 3px; " +
                            "-fx-border-radius: 15px; " +
                            "-fx-background-insets: 0; " +
                            "-fx-padding: 10px;");

            Text texto = new Text("Sin asignar");
            texto.setStyle(
                    "-fx-font-size: 14px; -fx-font-weight: bold; -fx-fill: #333333; -fx-text-alignment: center;");
            bloque.add(texto, 0, 0);

            GridPane.setHalignment(texto, HPos.CENTER);
            GridPane.setValignment(texto, VPos.CENTER);

            bloquesMemoria.add(bloque);

            // Colocar el bloque en el GridPane según su posición (8 columnas y tantas filas
            // como se necesiten)
            panelMemoria.add(bloque, i % 8, i / 8);
        }

        root.setBottom(panelMemoria);
    }

    public void actualizarBloqueMemoria(int indice, boolean ocupado, int idProceso) {
        Platform.runLater(() -> {
            if (indice >= 0 && indice < bloquesMemoria.size()) {
                GridPane bloque = bloquesMemoria.get(indice);
                bloque.getChildren().clear();

                if (ocupado) {
                    bloque.setStyle(
                            "-fx-background-color: #87CEEB; -fx-border-color: #ADD8E6; -fx-border-width: 3px; -fx-border-radius: 15px;");
                    bloque.add(new Text("Proceso: " + idProceso), 0, 0);
                } else {
                    bloque.setStyle(
                            "-fx-background-color: lightgray; -fx-border-color: #ADD8E6; -fx-border-width: 3px; -fx-border-radius: 15px;");
                    bloque.add(new Text("Sin asignar"), 0, 0);
                }

                GridPane.setHalignment(bloque.getChildren().get(0), HPos.CENTER);
                GridPane.setValignment(bloque.getChildren().get(0), VPos.CENTER);

                bloque.requestLayout();
            }
        });
    }

    // Método para actualizar todos los bloques de memoria
    public void actualizarVista() {
        Platform.runLater(() -> {

            // Recorrer cada bloque de memoria y verificar si está ocupado por algún proceso
            for (int i = 0; i < bloquesMemoria.size(); i++) {
                int idProceso = -1;
                for (Proceso p : procesos) {
                    if (p.getBloquesMemoria().contains(i)) {
                        idProceso = p.getIdProceso();
                        break;
                    }
                }

                // Actualizar el bloque de memoria según si está ocupado o no
                actualizarBloqueMemoria(i, idProceso != -1, idProceso);
                tabla.refresh();

            }

            stage.getScene().getRoot().requestLayout();
        });
    }

    public void show() {
        stage.show();
    }

    public void close() {
        stage.close();
    }

    public void iniciarEjecucion() {
        Thread hiloEjecucion = new Thread(() -> {
            while (ejecucionActiva) {

                if (planificador.ejecutar()) {

                    ejecucionActiva = false;
                    System.out.println("Todos los procesos han sido ejecutados. Finalizando hilo...");
                } else {
                    System.out.println("Ejecutando procesos...");
                }

                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    Thread.currentThread().interrupt();
                }
            }
        });

        hiloEjecucion.start();
    }
}