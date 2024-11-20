package com.proyectofinalsofx;

import javafx.application.Application;
import javafx.stage.Stage;

public class App extends Application {

    public static GestorMemoria gestorMemoria = new GestorMemoria(32);
    public static GestorRecursos gestorRecursos = new GestorRecursos();
    public static Planificador planificador = new Planificador(gestorMemoria, gestorRecursos);
    public static Simulador simulador = new Simulador(planificador);
    public static SistemaPrincipalVista vista;

    @Override
    public void start(Stage stage) {
        // Inicializar la vista
        vista = new SistemaPrincipalVista(stage);

        // Iniciar la ejecución de los procesos (en un hilo separado)
        vista.iniciarEjecucion();

        // Ya no es necesario crear una nueva escena, solo mostrarla
        stage.setTitle("Simulador de sistema operativo");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args); // Llamar al ciclo de vida de JavaFX
    }
}
