package com.proyectofinalsofx;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

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
        procesos = new ArrayList<>(simulador.getProcesos()); // Asumimos que los procesos ya se cargaron correctamente

        // Crear un nuevo BorderPane
        BorderPane root = new BorderPane();
        root.setPrefSize(900, 600);

        // Crear instancia de la clase Tabla con los procesos
        Tabla tablaProcesos = new Tabla(FXCollections.observableArrayList(procesos));

        // Obtener la TableView de la clase Tabla
        tabla = tablaProcesos.getTableView();

        // Agregar tabla al centro del BorderPane
        root.setCenter(tabla);

        // Crear panel de memoria
        crearPanelMemoria(root);

        // Crear y mostrar la escena
        Scene scene = new Scene(root);
        stage.setTitle("Simulador de sistema operativo");
        stage.setScene(scene);
        stage.show();
    }

    private void crearPanelMemoria(BorderPane root) {
        // Crear un panel de GridPane para los bloques de memoria
        GridPane panelMemoria = new GridPane();
        panelMemoria.setHgap(5); // Espaciado horizontal entre bloques
        panelMemoria.setVgap(5); // Espaciado vertical entre bloques
        panelMemoria.setAlignment(Pos.CENTER);

        // Estilo de gradiente para el panel de memoria (fondo blanco a azul)
        panelMemoria.setStyle("-fx-background-color: linear-gradient(from 0% 0% to 100% 100%, #0000FF, #ADD8E6);");

        bloquesMemoria = new ArrayList<>();
        for (int i = 0; i < 32; i++) {
            // Crear un bloque de memoria
            GridPane bloque = new GridPane();
            bloque.setPrefSize(120, 60); // Tamaño consistente para todos los bloques
            bloque.setStyle(
                    "-fx-border-color: #ADD8E6; " +
                            "-fx-background-color: lightgray; " +
                            "-fx-border-width: 3px; " +
                            "-fx-border-radius: 15px; " +
                            "-fx-background-insets: 0; " +
                            "-fx-padding: 10px;");

            // Etiqueta para mostrar el estado del bloque
            Text texto = new Text("Sin asignar");
            texto.setStyle(
                    "-fx-font-size: 14px; -fx-font-weight: bold; -fx-fill: #333333; -fx-text-alignment: center;");
            bloque.add(texto, 0, 0);

            // Centrar el texto en el bloque
            GridPane.setHalignment(texto, HPos.CENTER);
            GridPane.setValignment(texto, VPos.CENTER);

            // Agregar el bloque al arreglo de bloques de memoria
            bloquesMemoria.add(bloque);

            // Colocar el bloque en el GridPane según su posición (8 columnas y tantas filas
            // como se necesiten)
            panelMemoria.add(bloque, i % 8, i / 8);
        }

        // Colocar el panel de memoria en la parte inferior de la ventana
        root.setBottom(panelMemoria);
    }

    // Método para actualizar el estado de un bloque de memoria
    public void actualizarBloqueMemoria(int indice, boolean ocupado, int idProceso) {
        Platform.runLater(() -> {
            if (indice >= 0 && indice < bloquesMemoria.size()) {
                GridPane bloque = bloquesMemoria.get(indice); // Obtener el bloque de memoria visual

                // Limpiar el bloque visual antes de actualizarlo
                bloque.getChildren().clear();

                // Aplicar el color de fondo dependiendo de si el bloque está ocupado o no
                if (ocupado) {
                    bloque.setStyle(
                            "-fx-background-color: #87CEEB; -fx-border-color: #ADD8E6; -fx-border-width: 3px; -fx-border-radius: 15px;"); // Azul
                                                                                                                                          // más
                                                                                                                                          // intenso
                    bloque.add(new Text("Proceso: " + idProceso), 0, 0); // Agregar el ID del proceso al bloque
                } else {
                    bloque.setStyle(
                            "-fx-background-color: lightgray; -fx-border-color: #ADD8E6; -fx-border-width: 3px; -fx-border-radius: 15px;");
                    bloque.add(new Text("Sin asignar"), 0, 0); // Mostrar "Sin asignar" en el bloque
                }

                // Centrar el texto en el bloque
                GridPane.setHalignment(bloque.getChildren().get(0), HPos.CENTER);
                GridPane.setValignment(bloque.getChildren().get(0), VPos.CENTER);

                // Forzar la actualización del layout
                bloque.requestLayout();
            }
        });
    }

    // Método para actualizar todos los bloques de memoria
    public void actualizarVista() {
        Platform.runLater(() -> {
            // Actualizar los datos de la tabla (si es necesario)
            // modeloTabla.fireTableDataChanged(); // Este es un método Swing, en JavaFX
            // puedes actualizar directamente

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

            // Forzar el redibujado de la escena
            stage.getScene().getRoot().requestLayout();
        });
    }

    public void iniciarEjecucion() {
        Thread hiloEjecucion = new Thread(() -> {
            while (ejecucionActiva) {
                // Ejecutar los procesos y verificar si se han completado todos
                if (planificador.ejecutar()) {
                    // Si todos los procesos han sido ejecutados, detener el hilo
                    ejecucionActiva = false;
                    System.out.println("Todos los procesos han sido ejecutados. Finalizando hilo...");
                } else {
                    System.out.println("Ejecutando procesos...");
                }

                try {
                    Thread.sleep(200); // Reducir el tiempo de espera para mayor fluidez
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    Thread.currentThread().interrupt(); // Restaurar el estado de interrupción
                }
            }
        });

        hiloEjecucion.start();
    }
}