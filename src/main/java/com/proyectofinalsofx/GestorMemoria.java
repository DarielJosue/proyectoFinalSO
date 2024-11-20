package com.proyectofinalsofx;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GestorMemoria {
    private ArrayList<Boolean> bloques;
    private Random random;

    public GestorMemoria(int numBloques) {
        bloques = new ArrayList<>();
        random = new Random();
        for (int i = 0; i < numBloques; i++) {
            bloques.add(false); // Inicializar todos los bloques como libres
        }
    }

    public List<Integer> asignarMemoria(int tamanioRequerido) {
        List<Integer> bloquesAsignados = new ArrayList<>();
        int bloquesNecesarios = (int) Math.ceil(tamanioRequerido / 32.0); // Ajuste según tamaño de bloque

        // Crear una lista de índices libres
        List<Integer> indicesLibres = new ArrayList<>();
        for (int i = 0; i < bloques.size(); i++) {
            if (!bloques.get(i)) {
                indicesLibres.add(i);
            }
        }

        // Asignar bloques al azar
        while (bloquesAsignados.size() < bloquesNecesarios && !indicesLibres.isEmpty()) {
            int indiceAleatorio = random.nextInt(indicesLibres.size());
            int bloqueSeleccionado = indicesLibres.remove(indiceAleatorio);
            bloques.set(bloqueSeleccionado, true);
            bloquesAsignados.add(bloqueSeleccionado);
        }

        // Verifica que se asignaron suficientes bloques
        if (bloquesAsignados.size() < bloquesNecesarios) {
            // Si no se asignaron suficientes bloques, liberar los asignados
            liberarMemoria(bloquesAsignados);
            return new ArrayList<>(); // Retornar lista vacía
        }

        return bloquesAsignados;
    }

    public void liberarMemoria(List<Integer> bloquesAsignados) {
        for (int indice : bloquesAsignados) {
            if (indice >= 0 && indice < bloques.size()) {
                bloques.set(indice, false);
            }
        }
    }

    public boolean isBloqueOcupado(int indice) {
        return bloques.get(indice);
    }
}
