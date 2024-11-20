package com.proyectofinalsofx;

public class GestorRecursos {
    private int impresoras = 2;
    private int escaneres = 1;
    private int modems = 1;
    private int cds = 1;

    // Método para asignar recursos al proceso
    public boolean asignarRecursos(Proceso proceso) {
        System.out.println("Intentando asignar recursos para el proceso " + proceso.getIdProceso());

        // Mostrar los recursos solicitados antes de la asignación
        System.out.println("Recursos solicitados para el proceso " + proceso.getIdProceso() + ": ");
        System.out.println("Impresoras solicitadas: " + proceso.getImpresorasSolictadas());
        System.out.println("Escáneres solicitados: " + proceso.getEscaneresSolicitados());
        System.out.println("Modems solicitados: " + proceso.getModemsSolicitados());
        System.out.println("CDs solicitados: " + proceso.getCdsSolicitados());

        if (proceso.getImpresorasSolictadas() <= impresoras &&
                proceso.getEscaneresSolicitados() <= escaneres &&
                proceso.getModemsSolicitados() <= modems &&
                proceso.getCdsSolicitados() <= cds) {

            impresoras -= proceso.getImpresorasSolictadas();
            escaneres -= proceso.getEscaneresSolicitados();
            modems -= proceso.getModemsSolicitados();
            cds -= proceso.getCdsSolicitados();

            // Mostrar los recursos asignados
            System.out.println("Recursos asignados al proceso " + proceso.getIdProceso() + ": ");
            System.out.println("Impresoras asignadas: " + proceso.getImpresorasSolictadas());
            System.out.println("Escáneres asignados: " + proceso.getEscaneresSolicitados());
            System.out.println("Modems asignados: " + proceso.getModemsSolicitados());
            System.out.println("CDs asignados: " + proceso.getCdsSolicitados());

            // Mostrar los recursos disponibles después de la asignación
            System.out.println("Recursos disponibles después de la asignación: ");
            System.out.println("Impresoras disponibles: " + impresoras);
            System.out.println("Escáneres disponibles: " + escaneres);
            System.out.println("Modems disponibles: " + modems);
            System.out.println("CDs disponibles: " + cds);

            return true;
        }

        System.out.println("No hay suficientes recursos para el proceso " + proceso.getIdProceso());
        System.out.println("Recursos solicitados: " + proceso.getImpresorasSolictadas() + " impresoras, " +
                proceso.getEscaneresSolicitados() + " escáneres, " + proceso.getModemsSolicitados() + " modems, " +
                proceso.getCdsSolicitados() + " CDs");
        System.out.println("Recursos disponibles: " + getRecursosDisponibles());
        return false;
    }

    // Método para liberar los recursos de un proceso
    public void liberarRecursos(Proceso proceso) {
        // Libera los recursos que habían sido asignados al proceso
        impresoras += proceso.getImpresorasSolictadas();
        escaneres += proceso.getEscaneresSolicitados();
        modems += proceso.getModemsSolicitados();
        cds += proceso.getCdsSolicitados();

        // Log de depuración
        System.out.println("Recursos liberados para el proceso " + proceso.getIdProceso());
    }

    // Método para obtener los recursos disponibles en el sistema
    public String getRecursosDisponibles() {
        return "Impresoras: " + impresoras + ", Escáneres: " + escaneres +
                ", Modems: " + modems + ", CDs: " + cds;
    }

    // Método para obtener los recursos solicitados por un proceso
    public String getRecursosSolicitados(Proceso proceso) {
        return "Impresoras solicitadas: " + proceso.getImpresorasSolictadas() +
                ", Escáneres solicitados: " + proceso.getEscaneresSolicitados() +
                ", Modems solicitados: " + proceso.getModemsSolicitados() +
                ", CDs solicitados: " + proceso.getCdsSolicitados();
    }
}
