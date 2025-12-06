package modulos;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Administrador extends Usuario{
    private List<String> empleados;
    private List<Integer> idTareasCreadas;

    public Administrador(){
        super();
        this.empleados =  new ArrayList<>();
        this.idTareasCreadas =  new ArrayList<>();
    }

    public Administrador(String nombre, String correo, String password, boolean alta, List<String> empleados, List<Integer> idTareasCreadas){
        super(nombre, correo, password, alta, true);
        this.empleados = empleados;
        this.idTareasCreadas = idTareasCreadas;
    }

    //getters
    public List<String> getEmpleados(){return empleados;}
    public List<Integer> getIdTareasCreadas(){return idTareasCreadas;}

    //setters
    public void setEmpleados(List<String> empleados){this.empleados = empleados;}
    public void setIdTareasCreadas(List<Integer> idTareasCreadas){this.idTareasCreadas = idTareasCreadas;}

    @Override
    public void mostrarInfo(Scanner scn) {
        super.mostrarInfo(scn);
        if(empleados.isEmpty()){
            System.out.println("Actualmente no se tiene ningún empleado.");
        } else {
            System.out.println("Empleados a cargo:");
            for (int i = 0; i < empleados.size(); i++) {
                System.out.println("  " + (i+1) + ". " + empleados.get(i));
            }
        }
        if(idTareasCreadas.isEmpty()) {
            System.out.println("No se han creado tareas.");
        } else {
            System.out.println("Tareas creadas:");
            for (int i = 0; i < idTareasCreadas.size(); i++) {
                System.out.println("  Tarea ID: " + idTareasCreadas.get(i));
            }
        }
    }

    @Override
    public void menuUsuario(Scanner scn) {
        boolean cerrarSesion = true;
        while(cerrarSesion) {
            System.out.println("#".repeat(50));
            System.out.println("|        Bienvenido al Sistema de Tareas         |");
            System.out.println("#".repeat(50));
            System.out.println("|              Listar Empleados (1)              |");
            System.out.println("|            Listar Tareas Creadas (2)           |");
            System.out.println("|                 Crear Tarea (3)                |");
            System.out.println("|                Asignar Tarea (4)               |");
            System.out.println("|        Dar de alta a nuevo empleado (5)        |");
            System.out.println("|           Dar de baja a empleado (6)           |");
            System.out.println("|           Checar datos personales (7)          |");
            System.out.println("|                Cerrar Sesión (8)               |");
            System.out.println("#".repeat(50));
            boolean x = true;
            while (x) {
                System.out.print("| Ingresa un número: ");
                try {
                    byte a = scn.nextByte();
                    if (a == 1) {
                        x = false;
                        listarEmpleados();
                    } else if (a == 2) {
                        x = false;
                        listarTareasCreadas();
                    } else if (a == 3) {
                        x = false;
                        crearTarea(scn);
                    } else if (a == 4) {
                        x = false;
                        asignarTarea(scn);
                    } else if (a == 5) {
                        x = false;
                        darAlta(scn);
                    } else if (a == 6) {
                        x = false;
                        darBaja(scn);
                    } else if (a == 7) {
                        x = false;
                        mostrarInfo(scn);
                        System.out.print("# ¿Quieres modificar tus datos? (1 si, 2 no): ");
                        try {
                            byte b = scn.nextByte();
                            if (b==1){
                                String info = actualizarInfo(scn);
                                System.out.println(info);
                            }else if(b==2){
                                System.out.println("# Regresando...");
                            }else {
                                System.out.println("# Número inválido, por favor ingrese un número válido (1 ó 2) ");
                                scn.nextLine();
                            }
                        } catch (InputMismatchException e) {
                            System.out.println("# Error: Debe ingresar un número válido (1 ó 2).");
                            scn.nextLine();
                        }
                    } else if (a == 8) {
                        x = false;
                        System.out.println("# Cerrando sesión...");
                        cerrarSesion = false;
                    } else {
                        System.out.println("# Número inválido, por favor ingrese un número válido (1,2,3,4,5,6,7,8).");
                    }
                } catch (InputMismatchException e) {
                    System.out.println("# Error: Debe ingresar un número válido (del 1 al 8).");
                    scn.nextLine();
                }
            }
        }
    }

    private void listarEmpleados() {
        SistemaTareas sistema = new SistemaTareas();
        sistema.listarTodosLosEmpleados();
    }

    private void listarTareasCreadas() {
        SistemaTareas sistema = new SistemaTareas();
        sistema.listarTodasLasTareas();
    }

    private void crearTarea(Scanner scn) {
        SistemaTareas sistema = new SistemaTareas();
        Tareas nuevaTarea = sistema.crearTarea(scn, this.correo);
        if (nuevaTarea != null) {
            idTareasCreadas.add(nuevaTarea.getId());
        }
    }

    private void asignarTarea(Scanner scn) {
        SistemaTareas sistema = new SistemaTareas();
        sistema.asignarTarea(scn);
    }

    private void darAlta(Scanner scn) {
        scn.nextLine();
        BaseDeDatos baseDeDatos = new BaseDeDatos();
        List<String> todosLosCorreos = baseDeDatos.obtenerCorreos();
        List<Usuario> empleadosDadosDeBaja = new ArrayList<>();

        System.out.println("#".repeat(50));
        System.out.println("|            DAR DE ALTA EMPLEADO                |");
        System.out.println("#".repeat(50));

        // Buscar empleados que están dados de baja (alta = false)
        for (String correo : todosLosCorreos) {
            Usuario usuario = baseDeDatos.obtenerUsuario(correo);
            // Solo empleados (tipo = false) que están dados de baja (alta = false)
            if (!usuario.getTipo() && !usuario.getAlta()) {
                empleadosDadosDeBaja.add(usuario);
            }
        }

        if (empleadosDadosDeBaja.isEmpty()) {
            System.out.println("# No hay empleados dados de baja para reactivar.");
            System.out.println("# Para crear un nuevo empleado, use la opción 'Crear Cuenta'");
            System.out.println("# del menú principal y seleccione el tipo 'Empleado'.");
            return;
        }

        System.out.println("| Empleados dados de baja:");
        for (int i = 0; i < empleadosDadosDeBaja.size(); i++) {
            Usuario emp = empleadosDadosDeBaja.get(i);
            System.out.println("| " + (i+1) + ". " + emp.getNombre() + " (" + emp.getCorreo() + ")");
        }

        System.out.print("| Seleccione el empleado a dar de alta (0 para cancelar): ");
        String input = scn.nextLine().trim();

        try {
            int seleccion = Integer.parseInt(input);

            if (seleccion == 0) {
                System.out.println("| Operación cancelada.");
                return;
            }

            if (seleccion > 0 && seleccion <= empleadosDadosDeBaja.size()) {
                Usuario emp = empleadosDadosDeBaja.get(seleccion - 1);
                baseDeDatos.actualizarEstadoAlta(emp.getCorreo(), true);
                System.out.println("# Empleado " + emp.getNombre() + " (" + emp.getCorreo() + ") dado de alta exitosamente.");
            } else {
                System.out.println("# Número inválido.");
            }
        } catch (NumberFormatException e) {
            System.out.println("# Error: Debe ingresar un número válido.");
        }
    }

    private void darBaja(Scanner scn) {
        scn.nextLine();
        BaseDeDatos baseDeDatos = new BaseDeDatos();
        List<String> todosLosCorreos = baseDeDatos.obtenerCorreos();
        List<Usuario> empleadosActivos = new ArrayList<>();

        System.out.println("#".repeat(50));
        System.out.println("|              DAR DE BAJA EMPLEADO              |");
        System.out.println("#".repeat(50));

        // Buscar empleados que están activos (alta = true y tipo = false)
        for (String correo : todosLosCorreos) {
            Usuario usuario = baseDeDatos.obtenerUsuario(correo);
            // Solo empleados (tipo = false) que están activos (alta = true)
            if (!usuario.getTipo() && usuario.getAlta()) {
                empleadosActivos.add(usuario);
            }
        }

        if (empleadosActivos.isEmpty()) {
            System.out.println("# No hay empleados activos para dar de baja.");
            return;
        }

        System.out.println("| Empleados activos:");
        for (int i = 0; i < empleadosActivos.size(); i++) {
            Usuario emp = empleadosActivos.get(i);
            System.out.println("| " + (i+1) + ". " + emp.getNombre() + " (" + emp.getCorreo() + ")");
        }

        System.out.print("| Seleccione el empleado a dar de baja (0 para cancelar): ");
        String input = scn.nextLine().trim();

        try {
            int seleccion = Integer.parseInt(input);

            if (seleccion == 0) {
                System.out.println("| Operación cancelada.");
                return;
            }

            if (seleccion > 0 && seleccion <= empleadosActivos.size()) {
                Usuario emp = empleadosActivos.get(seleccion - 1);
                baseDeDatos.actualizarEstadoAlta(emp.getCorreo(), false);
                System.out.println("# Empleado " + emp.getNombre() + " (" + emp.getCorreo() + ") dado de baja exitosamente.");
            } else {
                System.out.println("# Número inválido.");
            }
        } catch (NumberFormatException e) {
            System.out.println("# Error: Debe ingresar un número válido.");
        }
    }
}