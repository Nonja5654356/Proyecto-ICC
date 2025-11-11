package modulos;

import java.util.InputMismatchException;
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
    public void menuUsuario() {
        boolean cerrarSesion = true;
        Scanner scn = new Scanner(System.in);
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
                        listarTareas();
                    } else if (a == 2) {
                        x = false;
                        //actualizarTarea();
                    } else if (a == 3) {
                        x = false;
                        mostrarInfo();
                        System.out.print("# ¿Quieres modificar tus datos? (1 si, 2 no): ");
                        try {
                            byte b = scn.nextByte();
                            if (b==1){
                                actualizarInfo();
                            } else {
                                System.out.println("# Número inválido, por favor ingrese un número válido (1 ó 2) ");
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

    private void listarTareas(){
        SistemaTareas sistemaTareas = new SistemaTareas();
        System.out.println("#".repeat(50));
        System.out.println("                       Tareas                      ");
        System.out.println("#".repeat(50));
        sistemaTareas.listarTareasEmpleado(correo);
    }
}