package modulos;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

//In this file is where the employee exists in constant suffering able to do nothing but update chores, explained on the README.md
public class Empleado extends Usuario{

    public Empleado() {
        super();
    }

    public Empleado(String nombre, String correo, String password, boolean alta){
        super(nombre, correo, password, alta, false);
    }

    @Override
    public void menuUsuario(Scanner scn) {
        boolean cerrarSesion = true;
        while(cerrarSesion) {
            System.out.println("#".repeat(50));
            System.out.println("|        Bienvenido al Sistema de Tareas         |");
            System.out.println("#".repeat(50));
            System.out.println("|                Checar Tareas (1)               |");
            System.out.println("|         Actualizar estado de Tarea (2)         |");
            System.out.println("|           Checar datos personales (3)          |");
            System.out.println("|                Cerrar Sesión (4)               |");
            System.out.println("#".repeat(50));
            boolean x = true;
            while (x) {
                System.out.print("| Ingresa un número: ");
                try {
                    byte a = scn.nextByte();
                    if (a == 1) {
                        x = false;
                        List<Integer> tareas = listarTareas();
                        System.out.print("# ¿Quieres actualizar el estado de alguna tarea? (1 si, 2 no): ");
                        try {
                            byte b = scn.nextByte();
                            if (b==1){
                                String info = actualizarTarea(scn, tareas);
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
                    } else if (a == 2) {
                        x = false;
                        List<Integer> tareas = listarTareas();
                        String info = actualizarTarea(scn, tareas);
                        System.out.println(info);
                    } else if (a == 3) {
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
                    } else if (a == 4) {
                        x = false;
                        System.out.println("# Cerrando Sesión...");
                        cerrarSesion = false;
                    } else {
                        System.out.println("# Número inválido, por favor ingrese un número válido (1,2,3 ó 4).");
                    }
                } catch (InputMismatchException e) {
                    System.out.println("# Error: Debe ingresar un número válido (1,2,3 ó 4).");
                    scn.nextLine();
                }
            }
        }
    }

    private List<Integer> listarTareas(){
        SistemaTareas sistemaTareas = new SistemaTareas();
        System.out.println("#".repeat(50));
        System.out.println("                       Tareas                      ");
        System.out.println("#".repeat(50));
        List<Integer> tareasEmpleado = sistemaTareas.listarTareasEmpleado(correo);
        return tareasEmpleado;
    }

    private String actualizarTarea(Scanner scn, List<Integer> tareas){
        System.out.print("| Elija el número de tarea a actualizar: ");
        return "Hola";
    }
}