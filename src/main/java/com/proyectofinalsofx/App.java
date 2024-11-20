package com.proyectofinalsofx;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;

public class App extends Application {

    public static GestorMemoria gestorMemoria = new GestorMemoria(32);
    public static GestorRecursos gestorRecursos = new GestorRecursos();
    public static Planificador planificador = new Planificador(gestorMemoria, gestorRecursos);
    public static Simulador simulador = new Simulador(planificador);
    public static SistemaPrincipalVista vista;

    @Override
    public void start(Stage primaryStage) {
        VentanaMenu ventanaMenu = new VentanaMenu(primaryStage);

        ventanaMenu.iniciarBoton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                vista = new SistemaPrincipalVista(primaryStage);
                vista.iniciarEjecucion();
                ventanaMenu.getStage().close();
                vista.show();
            }
        });

        ventanaMenu.mostrarVentana();
    }

    public static void main(String[] args) {
        launch(args);
    }
}