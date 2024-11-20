package com.proyectofinalsofx;

import java.util.ArrayList;
import java.util.List;

public class Proceso {

    int idProceso;
    String estado;
    int tiempoLlegada;
    int prioridadInicial;
    int prioridadActual;
    int tiempoCPURequerido;
    int tiempoCPURestante;
    int memoriaRequerida;
    List<Integer> ubicacionMemoria;
    int impresorasSolictadas;
    int impresonasAsignadas;
    int escaneresSolicitados;
    int escaneresAsignados;
    int modemsSolicitados;
    int modemsAsignados;
    int cdsSolicitados;
    int cdsAsignados;
    List<Integer> bloquesMemoria; // Nueva lista para almacenar múltiples bloques
    // Nuevo atributo para almacenar el bloque de memoria asignado

    // Constructor que recibe todos los parámetros necesarios
    public Proceso(int idProceso, String estado, int tiempoLlegada, int prioridadInicial, int prioridadActual,
            int tiempoCPURequerido, int tiempoCPURestante, int memoriaRequerida,
            int impresorasSolictadas, int impresonasAsignadas, int escaneresSolicitados, int escaneresAsignados,
            int modemsSolicitados, int modemsAsignados, int cdsSolicitados, int cdsAsignados) {
        super();
        this.idProceso = idProceso;
        this.estado = estado;
        this.tiempoLlegada = tiempoLlegada;
        this.prioridadInicial = prioridadInicial;
        this.prioridadActual = prioridadActual;
        this.tiempoCPURequerido = tiempoCPURequerido;
        this.tiempoCPURestante = tiempoCPURestante;
        this.memoriaRequerida = memoriaRequerida;
        this.ubicacionMemoria = new ArrayList<>();
        this.impresorasSolictadas = impresorasSolictadas;
        this.impresonasAsignadas = impresonasAsignadas;
        this.escaneresSolicitados = escaneresSolicitados;
        this.escaneresAsignados = escaneresAsignados;
        this.modemsSolicitados = modemsSolicitados;
        this.modemsAsignados = modemsAsignados;
        this.cdsSolicitados = cdsSolicitados;
        this.cdsAsignados = cdsAsignados;
        this.bloquesMemoria = new ArrayList<>();
    }

    // Método para asignar los bloques de memoria
    public void setBloquesAsignados(List<Integer> bloquesMemoria) {
        this.bloquesMemoria = bloquesMemoria;
    }

    // Método para obtener los bloques de memoria asignados
    public List<Integer> getBloquesMemoria() {
        return bloquesMemoria;
    }

    // Resto de los métodos existentes
    public int getIdProceso() {
        return idProceso;
    }

    public void setIdProceso(int idProceso) {
        this.idProceso = idProceso;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public int getTiempoLlegada() {
        return tiempoLlegada;
    }

    public void setTiempoLlegada(int tiempoLlegada) {
        this.tiempoLlegada = tiempoLlegada;
    }

    public int getPrioridadInicial() {
        return prioridadInicial;
    }

    public void setPrioridadInicial(int prioridadInicial) {
        this.prioridadInicial = prioridadInicial;
    }

    public int getPrioridadActual() {
        return prioridadActual;
    }

    public void setPrioridadActual(int prioridadActual) {
        this.prioridadActual = prioridadActual;
    }

    public int getTiempoCPURequerido() {
        return tiempoCPURequerido;
    }

    public void setTiempoCPURequerido(int tiempoCPURequerido) {
        this.tiempoCPURequerido = tiempoCPURequerido;
    }

    public int getTiempoCPURestante() {
        return tiempoCPURestante;
    }

    public void setTiempoCPURestante(int tiempoCPURestante) {
        this.tiempoCPURestante = tiempoCPURestante;
    }

    public int getMemoriaRequerida() {
        return memoriaRequerida;
    }

    public void setMemoriaRequerida(int memoriaRequerida) {
        this.memoriaRequerida = memoriaRequerida;
    }

    public List<Integer> getUbicacionMemoria() {
        return ubicacionMemoria;
    }

    public void setUbicacionMemoria(List<Integer> ubicacionMemoria) {
        this.ubicacionMemoria = ubicacionMemoria;
    }

    public int getImpresorasSolictadas() {
        return impresorasSolictadas;
    }

    public void setImpresorasSolictadas(int impresorasSolictadas) {
        this.impresorasSolictadas = impresorasSolictadas;
    }

    public int getImpresonasAsignadas() {
        return impresonasAsignadas;
    }

    public void setImpresonasAsignadas(int impresonasAsignadas) {
        this.impresonasAsignadas = impresonasAsignadas;
    }

    public int getEscaneresSolicitados() {
        return escaneresSolicitados;
    }

    public void setEscaneresSolicitados(int escaneresSolicitados) {
        this.escaneresSolicitados = escaneresSolicitados;
    }

    public int getEscaneresAsignados() {
        return escaneresAsignados;
    }

    public void setEscaneresAsignados(int escaneresAsignados) {
        this.escaneresAsignados = escaneresAsignados;
    }

    public int getModemsSolicitados() {
        return modemsSolicitados;
    }

    public void setModemsSolicitados(int modemsSolicitados) {
        this.modemsSolicitados = modemsSolicitados;
    }

    public int getModemsAsignados() {
        return modemsAsignados;
    }

    public void setModemsAsignados(int modemsAsignados) {
        this.modemsAsignados = modemsAsignados;
    }

    public int getCdsSolicitados() {
        return cdsSolicitados;
    }

    public void setCdsSolicitados(int cdsSolicitados) {
        this.cdsSolicitados = cdsSolicitados;
    }

    public int getCdsAsignados() {
        return cdsAsignados;
    }

    public void setCdsAsignados(int cdsAsignados) {
        this.cdsAsignados = cdsAsignados;
    }

    public void reducirTiempoCPU() {
        if (tiempoCPURestante > 0) {
            tiempoCPURestante--;
        }
    }

    public boolean estaCompleto() {
        return tiempoCPURestante == 0;
    }
}
