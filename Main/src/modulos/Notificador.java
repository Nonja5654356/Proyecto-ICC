package modulos;

import java.time.LocalDate;
import java.util.List;

public class Notificador extends Thread {
    private boolean activo;
    private final int intervaloSegundos;

    public Notificador() {
        this.activo = true;
        this.intervaloSegundos = 30; // Revisa cada 30 segundos
    }

    public Notificador(int intervaloSegundos) {
        this.activo = true;
        this.intervaloSegundos = intervaloSegundos;
    }

    @Override
    public void run() {
        System.out.println("[NOTIFICADOR] Sistema de alertas iniciado.");
        System.out.println("[NOTIFICADOR] Revisando tareas cada " + intervaloSegundos + " segundos...\n");

        while (activo) {
            try {
                revisarTareas();
                Thread.sleep(intervaloSegundos * 1000L); // Convierte a milisegundos
            } catch (InterruptedException e) {
                System.out.println("[NOTIFICADOR] Sistema de alertas interrumpido.");
                activo = false;
            }
        }

        System.out.println("[NOTIFICADOR] Sistema de alertas finalizado.");
    }
    private void revisarTareas() {
        try {
            SistemaTareas sistema = new SistemaTareas();
            List<Tareas> todasLasTareas = sistema.getTareas();

            if (todasLasTareas.isEmpty()) {
                return;
            }

            LocalDate fechaActual = LocalDate.now();
            boolean hayAlertas = false;

            for (Tareas tarea : todasLasTareas) {
                if (!tarea.getEstado()) { // Solo revisa tareas no completadas
                    int[] fechaEntrega = tarea.getFechaEntrega();
                    LocalDate fechaLimite = LocalDate.of(fechaEntrega[2], fechaEntrega[1], fechaEntrega[0]);

                    long diasRestantes = java.time.temporal.ChronoUnit.DAYS.between(fechaActual, fechaLimite);

                    if (diasRestantes < 0) {
                        // Tarea vencida
                        if (!hayAlertas) {
                            System.out.println("\n" + "=".repeat(50));
                            System.out.println("             ALERTAS DE TAREAS  ");
                            System.out.println("=".repeat(50));
                            hayAlertas = true;
                        }
                        System.out.println("VENCIDA: \"" + tarea.getNombre() + "\"");
                        System.out.println("   Venció hace " + Math.abs(diasRestantes) + " día(s)");
                        System.out.println("   Fecha límite: " + fechaEntrega[0] + "/" + fechaEntrega[1] + "/" + fechaEntrega[2]);
                    } else if (diasRestantes == 0) {
                        // Vence hoy
                        if (!hayAlertas) {
                            System.out.println("\n" + "=".repeat(50));
                            System.out.println("             ALERTAS DE TAREAS  ");
                            System.out.println("=".repeat(50));
                            hayAlertas = true;
                        }
                        System.out.println(" URGENTE: \"" + tarea.getNombre() + "\"");
                        System.out.println("   ¡Vence HOY!");
                        System.out.println("   Fecha límite: " + fechaEntrega[0] + "/" + fechaEntrega[1] + "/" + fechaEntrega[2]);
                    } else if (diasRestantes <= 3) {
                        // Próxima a vencer (3 días o menos)
                        if (!hayAlertas) {
                            System.out.println("\n" + "=".repeat(50));
                            System.out.println("             ALERTAS DE TAREAS  ");
                            System.out.println("=".repeat(50));
                            hayAlertas = true;
                        }
                        System.out.println(" PRÓXIMA: \"" + tarea.getNombre() + "\"");
                        System.out.println("   Vence en " + diasRestantes + " día(s)");
                        System.out.println("   Fecha límite: " + fechaEntrega[0] + "/" + fechaEntrega[1] + "/" + fechaEntrega[2]);
                    }
                }
            }

            if (hayAlertas) {
                System.out.println("=".repeat(50) + "\n");
            }

        } catch (Exception e) {
            // Manejo silencioso de errores para no interrumpir el hilo
            // En un sistema real, se registraría en un log
        }
    }
    public void detener() {
        this.activo = false;
        this.interrupt();
    }
    public boolean estaActivo() {
        return activo;
    }
}