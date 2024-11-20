package com.proyectofinalsofx;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Planificador {
    private Queue<Proceso> colaTiempoReal = new LinkedList<>();
    private Queue<Proceso> colaUsuario1 = new LinkedList<>();
    private Queue<Proceso> colaUsuario2 = new LinkedList<>();
    private Queue<Proceso> colaUsuario3 = new LinkedList<>();
    private GestorMemoria gestorMemoria;
    private GestorRecursos gestorRecursos;

    public Planificador(GestorMemoria gestorMemoria, GestorRecursos gestorRecursos) {
        this.gestorMemoria = gestorMemoria;
        this.gestorRecursos = gestorRecursos;
    }

    public void agregarProceso(Proceso proceso) {
        switch (proceso.getPrioridadActual()) {
            case 0:
                colaTiempoReal.add(proceso);
                break;
            case 1:
                colaUsuario1.add(proceso);
                break;
            case 2:
                colaUsuario2.add(proceso);
                break;
            case 3:
                colaUsuario3.add(proceso);
                break;
            default:
                // Opcionalmente, puedes manejar el caso donde la prioridad no sea 0-3
                break;
        }
    }

    // App.vista.actualizarVista(); // Actualizar vista al agregar un proceso

    public boolean ejecutar() {
        if (!colaTiempoReal.isEmpty()) {
            ejecutarProceso(colaTiempoReal.poll());
            return false; // Aún quedan procesos por ejecutar
        } else if (!colaUsuario1.isEmpty()) {
            ejecutarProceso(colaUsuario1.poll());
            return false; // Aún quedan procesos por ejecutar
        } else if (!colaUsuario2.isEmpty()) {
            ejecutarProceso(colaUsuario2.poll());
            return false; // Aún quedan procesos por ejecutar
        } else if (!colaUsuario3.isEmpty()) {
            ejecutarProceso(colaUsuario3.poll());
            return false; // Aún quedan procesos por ejecutar
        }

        // Si todas las colas están vacías, significa que todos los procesos se
        // ejecutaron
        return true;
    }

    private void ejecutarProceso(Proceso proceso) {
        proceso.setEstado("Intentando ejecutar");
        App.vista.actualizarVista();

        // Intentar asignar memoria
        List<Integer> bloquesAsignados = gestorMemoria.asignarMemoria(proceso.getMemoriaRequerida());

        // Intentar asignar recursos
        boolean recursosAsignados = gestorRecursos.asignarRecursos(proceso);

        if (!bloquesAsignados.isEmpty() && recursosAsignados) {
            // Actualizar el estado del proceso
            proceso.setEstado("Ejecutando");
            proceso.setBloquesAsignados(bloquesAsignados);
            App.vista.actualizarVista();

            // Bucle para ejecutar el proceso mientras tenga tiempo de CPU restante
            while (proceso.getTiempoCPURestante() > 0) {
                // Reducir el tiempo de CPU restante
                proceso.reducirTiempoCPU();
                App.vista.actualizarVista();

                try {
                    // Simular el paso del tiempo
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt(); // Restaurar el estado de interrupción
                    e.printStackTrace();
                }
            }

            // El proceso ha terminado
            proceso.setEstado("Terminado");
            gestorMemoria.liberarMemoria(proceso.getBloquesMemoria());
            proceso.getBloquesMemoria().clear(); // Limpiar los bloques asignados
            gestorRecursos.liberarRecursos(proceso);
            App.vista.actualizarVista(); // Reflejar cambios
        } else {
            // No se pudieron asignar recursos o memoria, volver a encolar
            proceso.setEstado("En espera");
            agregarProceso(proceso);

            // Liberar recursos asignados parcialmente si corresponde
            if (!bloquesAsignados.isEmpty()) {
                gestorMemoria.liberarMemoria(bloquesAsignados);
            }
        }
    }

}
