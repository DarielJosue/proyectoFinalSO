package com.proyectofinalsofx;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Simulador {
    private Planificador planificador;
    public List<Proceso> procesos = new ArrayList<>(); // Lista de procesos si se necesita almacenar localmente

    public Simulador(Planificador planificador) {
        this.planificador = planificador;
    }

    public void cargarProcesos(String nombreArchivo) {
        try (BufferedReader br = new BufferedReader(new FileReader(nombreArchivo))) {
            String linea;
            int idProceso = 1;
            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split(",");
                if (partes.length != 8) {
                    System.out.println("Línea inválida (se esperan 8 campos): " + linea);
                    continue;
                }
                try {
                    Proceso proceso = new Proceso(
                            idProceso++, "Creado", Integer.parseInt(partes[0]), Integer.parseInt(partes[1]),
                            Integer.parseInt(partes[1]), Integer.parseInt(partes[2]), Integer.parseInt(partes[2]),
                            Integer.parseInt(partes[3]), Integer.parseInt(partes[4]), 0 /* impresoras */,
                            Integer.parseInt(partes[5]), 0/* escaner */, Integer.parseInt(partes[6]), 0 /* modem */,
                            Integer.parseInt(partes[7]), 0/* cds */);
                    procesos.add(proceso); // Añadir a la lista local
                    // procesosT.add(proceso);
                    planificador.agregarProceso(proceso);
                } catch (NumberFormatException e) {
                    System.out.println("Error al convertir un número en la línea: " + linea);
                }
            }
        } catch (IOException e) {
            System.out.println("Error al leer el archivo de procesos: " + nombreArchivo);
            e.printStackTrace();
        }
    }

    public void ejecutarCiclo() {
        while (!planificador.ejecutar()) {
            // Ejecutar el planificador para mantener la cola de procesos actualizada
            planificador.ejecutar();
        }

        System.out.println("Todos los procesos han sido ejecutados.");
    }

    public List<Proceso> getProcesos() {
        return procesos;
    }
}
