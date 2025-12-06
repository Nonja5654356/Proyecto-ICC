package modulos;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class SistemaTareas {
    private List<String> empleados;
    private List<Tareas> tareas;
    private Map<String, List<Integer>> asignaciones;

    public SistemaTareas() {
        setEmpleados();
        setTareas();
        setAsignaciones();
    }

    //getters
    public List<String> getEmpleados() {
        return empleados;
    }

    public List<Tareas> getTareas() {
        return tareas;
    }

    public Map<String, List<Integer>> getAsignaciones() {
        return asignaciones;
    }

    //setters
    private void setEmpleados() {
        BaseDeDatos baseDeDatos = new BaseDeDatos();
        this.empleados = baseDeDatos.obtenerEmpleados();
    }

    private void setTareas() {
        BaseDeDatos baseDeDatos = new BaseDeDatos();
        this.tareas = baseDeDatos.obtenerTareas();
    }

    private void setAsignaciones() {
        BaseDeDatos baseDeDatos = new BaseDeDatos();
        this.asignaciones = baseDeDatos.obtenerAsignaciones();
    }

    public List<Integer> listarTareasEmpleado(String correo) {
        boolean correoExistente = asignaciones.containsKey(correo);
        boolean tareasPorHacer = false;
        List<Integer> tareasEmpleado = new ArrayList<>();
        if (correoExistente) {
            tareasEmpleado = asignaciones.get(correo);
            for (int i = 0; i < tareasEmpleado.size(); i++) {
                Tareas tarea = tareas.get(tareasEmpleado.get(i));
                tarea.mostrarDetallesTarea(i + 1);
                if (!tarea.getEstado()) {
                    tareasPorHacer = true;
                }
            }
            if (!tareasPorHacer) {
                System.out.println("# Has completado todas tus tareas :)");
                return new ArrayList<>();
            }
        } else {
            System.out.println("# No tienes tareas asignadas :)");
        }
        return tareasEmpleado;
    }

    public void listarTodosLosEmpleados(){
        if(empleados.isEmpty()){
            System.out.println("# No hay empleados registrados.");
        } else {
            System.out.println("#".repeat(50));
            System.out.println("|                   EMPLEADOS                    |");
            System.out.println("#".repeat(50));
            for (int i = 0; i < empleados.size(); i++) {
                System.out.println("| " + (i + 1) + ". " + empleados.get(i));
            }
            System.out.println("#".repeat(50));
        }
    }

    public void listarTodasLasTareas(){
        if(tareas.isEmpty()){
            System.out.println("# No hay tareas creadas.");
        } else {
            System.out.println("#".repeat(50));
            System.out.println("|                     TAREAS                     |");
            System.out.println("#".repeat(50));
            for (int i = 0; i < tareas.size(); i++) {
                tareas.get(i).mostrarDetallesTarea(i + 1);
            }
            System.out.println("#".repeat(50));
        }
    }

    public Tareas crearTarea(Scanner scn, String correoAdmin) {
        scn.nextLine();
        String nombre = "", descripcion = "";
        int[] fecha = new int[3];

        System.out.println("#".repeat(50));
        System.out.println("|                  CREAR TAREA                   |");
        System.out.println("#".repeat(50));

        boolean nombreValido = false;
        while (!nombreValido) {
            System.out.print("| Ingrese el nombre de la tarea (0 para cancelar): ");
            nombre = scn.nextLine().trim();
            if (nombre.equals("0")) {
                System.out.println("| Operación cancelada.");
                return null;
            }

            // Validar que no contenga caracteres especiales problemáticos
            if (nombre.contains("|") || nombre.contains("{") || nombre.contains("}") ||
                    nombre.contains("[") || nombre.contains("]")) {
                System.out.println("# Error: El nombre no puede contener los caracteres: | { } [ ]");
                System.out.println("# Por favor, intente con otro nombre.");
            } else if (nombre.isEmpty()) {
                System.out.println("# Error: El nombre no puede estar vacío.");
            } else {
                // Nombre válido, salir del bucle
                nombreValido = true;
            }
        }

        boolean descripcionValida = false;
        while (!descripcionValida) {
            System.out.print("| Ingrese la descripción de la tarea: ");
            descripcion = scn.nextLine().trim();

            // Validar que no contenga caracteres especiales problemáticos
            if (descripcion.contains("|") || descripcion.contains("{") || descripcion.contains("}") ||
                    descripcion.contains("[") || descripcion.contains("]")) {
                System.out.println("# Error: La descripción no puede contener los caracteres: | { } [ ]");
                System.out.println("# Por favor, intente con otra descripción.");
            } else if (descripcion.isEmpty()) {
                System.out.println("# Error: La descripción no puede estar vacía.");
            } else {
                // Descripción válida, salir del bucle
                descripcionValida = true;
            }
        }

        boolean fechaValida = false;
        while (!fechaValida) {
            try {
                System.out.print("| Ingrese el día de entrega (1-31): ");
                fecha[0] = scn.nextInt();
                System.out.print("| Ingrese el mes de entrega (1-12): ");
                fecha[1] = scn.nextInt();
                System.out.print("| Ingrese el año de entrega (ej: 2025): ");
                fecha[2] = scn.nextInt();

                if (fecha[0] >= 1 && fecha[0] <= 31 && fecha[1] >= 1 && fecha[1] <= 12 && fecha[2] >= 2025) {
                    fechaValida = true;
                } else {
                    System.out.println("# Fecha inválida. Intente nuevamente.");
                }
            } catch (Exception e) {
                System.out.println("# Error: Debe ingresar números válidos.");
                scn.nextLine();
            }
        }

        int nuevoId = tareas.size();
        Tareas nuevaTarea = new Tareas(nuevoId, nombre, descripcion, fecha, false);

        BaseDeDatos baseDeDatos = new BaseDeDatos();
        baseDeDatos.guardarTarea(nuevaTarea);

        System.out.println("# Tarea creada exitosamente con ID: " + nuevoId);
        return nuevaTarea;
    }

    public void asignarTarea(Scanner scn) {
        scn.nextLine();

        if (empleados.isEmpty()) {
            System.out.println("# No hay empleados disponibles para asignar tareas.");
            return;
        }

        if (tareas.isEmpty()) {
            System.out.println("# No hay tareas creadas para asignar.");
            return;
        }

        System.out.println("#".repeat(50));
        System.out.println("|                 ASIGNAR TAREA                  |");
        System.out.println("#".repeat(50));

        listarTodosLosEmpleados();

        int empleadoSeleccionado = -1;
        boolean seleccionValida = false;

        while (!seleccionValida) {
            try {
                System.out.print("| Seleccione el número de empleado (0 para cancelar): ");
                empleadoSeleccionado = scn.nextInt();

                if (empleadoSeleccionado == 0) {
                    System.out.println("| Operación cancelada.");
                    return;
                }

                if (empleadoSeleccionado > 0 && empleadoSeleccionado <= empleados.size()) {
                    seleccionValida = true;
                } else {
                    System.out.println("# Número inválido. Intente nuevamente.");
                }
            } catch (Exception e) {
                System.out.println("# Error: Debe ingresar un número válido.");
                scn.nextLine();
            }
        }

        String correoEmpleado = empleados.get(empleadoSeleccionado - 1);

        listarTodasLasTareas();

        int tareaSeleccionada = -1;
        seleccionValida = false;

        while (!seleccionValida) {
            try {
                System.out.print("| Seleccione el número de tarea (0 para cancelar): ");
                tareaSeleccionada = scn.nextInt();

                if (tareaSeleccionada == 0) {
                    System.out.println("| Operación cancelada.");
                    return;
                }

                if (tareaSeleccionada > 0 && tareaSeleccionada <= tareas.size()) {
                    seleccionValida = true;
                } else {
                    System.out.println("# Número inválido. Intente nuevamente.");
                }
            } catch (Exception e) {
                System.out.println("# Error: Debe ingresar un número válido.");
                scn.nextLine();
            }
        }

        int idTarea = tareas.get(tareaSeleccionada - 1).getId();

        BaseDeDatos baseDeDatos = new BaseDeDatos();
        baseDeDatos.guardarAsignacion(correoEmpleado, idTarea);

        System.out.println("# Tarea asignada exitosamente a " + correoEmpleado);
    }

    public void actualizarEstadoTarea(Scanner scn, int numeroTarea, List<Integer> tareasEmpleado) {
        if (numeroTarea < 1 || numeroTarea > tareasEmpleado.size()) {
            System.out.println("# Número de tarea inválido.");
            return;
        }

        int idTarea = tareasEmpleado.get(numeroTarea - 1);
        Tareas tarea = tareas.get(idTarea);

        System.out.println("#".repeat(50));
        System.out.println("|            ACTUALIZAR ESTADO TAREA             |");
        System.out.println("#".repeat(50));
        System.out.println("| Tarea: " + tarea.getNombre());
        System.out.println("| Estado actual: " + (tarea.getEstado() ? "[X] Completada" : "[ ] Pendiente"));

        // Permitir cambiar el estado sin importar si ya está completada
        System.out.print("| ¿Marcar como " + (tarea.getEstado() ? "pendiente" : "completada") + "? (1 sí, 2 no): ");

        try {
            byte opcion = scn.nextByte();
            if (opcion == 1) {
                // Cambiar el estado al opuesto del actual
                boolean nuevoEstado = !tarea.getEstado();
                tarea.setEstado(nuevoEstado);
                BaseDeDatos baseDeDatos = new BaseDeDatos();
                baseDeDatos.actualizarEstadoTarea(idTarea, nuevoEstado);
                System.out.println("# Tarea marcada como " + (nuevoEstado ? "completada [X]" : "pendiente [ ]") + ".");
            } else {
                System.out.println("# Operación cancelada.");
            }
        } catch (Exception e) {
            System.out.println("# Error: Debe ingresar un número válido (1 o 2).");
            scn.nextLine();
        }
    }
}