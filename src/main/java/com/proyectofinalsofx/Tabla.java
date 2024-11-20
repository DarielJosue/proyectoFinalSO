package com.proyectofinalsofx;

import javafx.beans.property.*;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TableCell;
import javafx.scene.layout.Background;
import javafx.scene.control.TableRow;
import javafx.scene.paint.Color;

import java.util.List;

public class Tabla {

    private ObservableList<Proceso> procesos;
    private TableView<Proceso> tableView;

    public Tabla(ObservableList<Proceso> procesos) {
        this.procesos = procesos;
        this.tableView = new TableView<>();

        // Crear columnas existentes
        TableColumn<Proceso, Integer> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getIdProceso()).asObject());

        TableColumn<Proceso, String> estadoCol = new TableColumn<>("Estatus");
        estadoCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getEstado()));

        // Cambiar el color de la celda según el estado del proceso
        estadoCol.setCellFactory(column -> {
            return new TableCell<Proceso, String>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);

                    // Asegurarse de que la celda no esté vacía
                    if (empty || item == null) {
                        setText(null);
                        setStyle("");
                    } else {
                        setText(item);

                        // Cambiar el color de fondo según el estado
                        switch (item) {
                            case "Ejecutando":
                                setStyle("-fx-background-color: lightgreen;"); // Color verde para "Ejecutando"
                                break;
                            case "Terminado":
                                setStyle("-fx-background-color: lightgray;"); // Color gris para "Terminado"
                                break;
                            case "Creado":
                                setStyle("-fx-background-color: lightblue;"); // Color azul para "Creado"
                                break;
                            default:
                                setStyle(""); // Estilo por defecto
                                break;
                        }
                    }
                }
            };
        });

        TableColumn<Proceso, Integer> tiempoLlegadaCol = new TableColumn<>("Tiempo Llegada");
        tiempoLlegadaCol
                .setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getTiempoLlegada()).asObject());

        // Configuración de otras columnas...
        TableColumn<Proceso, Integer> prioridadInicialCol = new TableColumn<>("Prioridad Inicial");
        prioridadInicialCol.setCellValueFactory(
                data -> new SimpleIntegerProperty(data.getValue().getPrioridadInicial()).asObject());

        TableColumn<Proceso, Integer> prioridadActualCol = new TableColumn<>("Prioridad Actual");
        prioridadActualCol.setCellValueFactory(
                data -> new SimpleIntegerProperty(data.getValue().getPrioridadActual()).asObject());

        TableColumn<Proceso, Integer> tiempoCPURequeridoCol = new TableColumn<>("Tiempo Procesador Requerido");
        tiempoCPURequeridoCol.setCellValueFactory(
                data -> new SimpleIntegerProperty(data.getValue().getTiempoCPURequerido()).asObject());

        TableColumn<Proceso, Integer> tiempoCPURestanteCol = new TableColumn<>("Tiempo Procesador Restante");
        tiempoCPURestanteCol.setCellValueFactory(
                data -> new SimpleIntegerProperty(data.getValue().getTiempoCPURestante()).asObject());

        TableColumn<Proceso, Integer> memoriaRequeridaCol = new TableColumn<>("Memoria Requerida");
        memoriaRequeridaCol.setCellValueFactory(
                data -> new SimpleIntegerProperty(data.getValue().getMemoriaRequerida()).asObject());

        TableColumn<Proceso, String> ubicacionMemoriaCol = new TableColumn<>("Ubicación Memoria");
        ubicacionMemoriaCol.setCellValueFactory(data -> {
            List<Integer> bloquesAsignados = data.getValue().getBloquesMemoria();
            return new SimpleStringProperty(bloquesAsignados.isEmpty() ? "Sin asignar" : bloquesAsignados.toString());
        });

        TableColumn<Proceso, Integer> impresorasSolicitadasCol = new TableColumn<>("Impresoras Solicitadas");
        impresorasSolicitadasCol.setCellValueFactory(
                data -> new SimpleIntegerProperty(data.getValue().getImpresorasSolictadas()).asObject());

        TableColumn<Proceso, Integer> impresorasAsignadasCol = new TableColumn<>("Impresoras Asignadas");
        impresorasAsignadasCol.setCellValueFactory(
                data -> new SimpleIntegerProperty(data.getValue().getImpresonasAsignadas()).asObject());

        TableColumn<Proceso, Integer> escaneresSolicitadosCol = new TableColumn<>("Escáneres Solicitados");
        escaneresSolicitadosCol.setCellValueFactory(
                data -> new SimpleIntegerProperty(data.getValue().getEscaneresSolicitados()).asObject());

        TableColumn<Proceso, Integer> escaneresAsignadosCol = new TableColumn<>("Escáneres Asignados");
        escaneresAsignadosCol.setCellValueFactory(
                data -> new SimpleIntegerProperty(data.getValue().getEscaneresAsignados()).asObject());

        TableColumn<Proceso, Integer> modemsSolicitadosCol = new TableColumn<>("Modems Solicitados");
        modemsSolicitadosCol.setCellValueFactory(
                data -> new SimpleIntegerProperty(data.getValue().getModemsSolicitados()).asObject());

        TableColumn<Proceso, Integer> modemsAsignadosCol = new TableColumn<>("Modems Asignados");
        modemsAsignadosCol.setCellValueFactory(
                data -> new SimpleIntegerProperty(data.getValue().getModemsAsignados()).asObject());

        TableColumn<Proceso, Integer> cdsSolicitadosCol = new TableColumn<>("CDs Solicitados");
        cdsSolicitadosCol
                .setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getCdsSolicitados()).asObject());

        TableColumn<Proceso, Integer> cdsAsignadosCol = new TableColumn<>("CDs Asignados");
        cdsAsignadosCol
                .setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getCdsAsignados()).asObject());

        // Añadir todas las columnas
        tableView.getColumns().addAll(idCol, estadoCol, tiempoLlegadaCol, prioridadInicialCol, prioridadActualCol,
                tiempoCPURequeridoCol, tiempoCPURestanteCol, memoriaRequeridaCol, ubicacionMemoriaCol,
                impresorasSolicitadasCol, impresorasAsignadasCol, escaneresSolicitadosCol, escaneresAsignadosCol,
                modemsSolicitadosCol, modemsAsignadosCol, cdsSolicitadosCol, cdsAsignadosCol);

        // Cambiar el color de toda la fila según el estado del proceso
        tableView.setRowFactory(tv -> {
            return new TableRow<Proceso>() {
                @Override
                protected void updateItem(Proceso item, boolean empty) {
                    super.updateItem(item, empty);

                    if (item != null) {

                        switch (item.getEstado()) {
                            case "Ejecutando":
                                setStyle("-fx-background-color: lightgreen;");
                                break;
                            case "Terminado":
                                setStyle("-fx-background-color: lightgray;");
                                break;
                            case "Creado":
                                setStyle("-fx-background-color: lightblue;");
                                break;
                            default:
                                setStyle(""); // Estilo por defecto
                                break;
                        }
                    } else {
                        setStyle("");
                    }
                }
            };
        });

        tableView.setItems(procesos);
    }

    public void updateTable() {
        tableView.refresh();
    }

    public TableView<Proceso> getTableView() {
        return tableView;
    }
}
