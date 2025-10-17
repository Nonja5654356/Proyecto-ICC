import modulos.Usuario;
import modulos.Administrador;
import modulos.Empleado;
import modulos.SistemaTareas;
import modulos.Tareas;

import java.util.*;

public class Main {

    public static List<Object> crearCuenta(Scanner scn){
        scn.nextLine();
        String n, c, p;
        byte b;
        boolean a;
        System.out.print("Ingresa tu nombre: ");
        n = scn.nextLine();
        System.out.print("Ingresa tu correo: ");
        c = scn.nextLine();
        System.out.print("Ingresa una contraseña para tu cuenta: ");
        p = scn.nextLine();
        System.out.print("Eres administrador? (1 si si, 2 si no): ");
        b = scn.nextByte();
        a = b==1;
        List<Object> list = new ArrayList<>();
        list.add(n);
        list.add(c);
        list.add(p);
        list.add(a);
        return list;
    }

    public static void interfaz(Scanner scn, String nombre){
        System.out.println("#".repeat(50));
        int x = ("Bienvenido" + nombre).length()-4;
        System.out.println("|    Bienvenido " + nombre + "     |");
        System.out.println("#".repeat(50));
        System.out.println("|" + " ".repeat(16) + "Crear Cuenta (1)" + " ".repeat(16) + "|");
        System.out.println("|        Ingresar (2)        |");
        System.out.println("#".repeat(50));
    }

    public static void logIn(Scanner scn){
        System.out.println("#".repeat(50));
        System.out.println("|" + " ".repeat(14) + "Ingresar al sistema" + " ".repeat(15) + "|");
        System.out.println("#".repeat(50));
        System.out.println("|" + " ".repeat(16) + "Crear Cuenta (1)" + " ".repeat(16) + "|");
        System.out.println("|" + " ".repeat(18) + "Ingresar (2)" + " ".repeat(18) + "|");
        System.out.println("|" + " ".repeat(20) + "Salir (3)" + " ".repeat(19) + "|");
        System.out.println("#".repeat(50));
        boolean x = true;
        while(x){
            System.out.print("Ingresa un número: ");
            try {
                byte a = scn.nextByte();
                if(a == 1) {
                    List<Object> c = crearCuenta(scn);
                    if(c.get(3).toString().equals("true")) {
                        List<String> emptyString = new ArrayList<>();
                        List<Integer> emptyList = new ArrayList<>();
                        Administrador newUserA = new Administrador(c.get(0).toString(), c.get(1).toString(), c.get(2).toString(), true, emptyString, emptyList);
                        newUserA.mostrarInfo();
                    } else if (!c.get(3).toString().equals("true")) {
                        Empleado newUserE = new Empleado(c.get(0).toString(), c.get(1).toString(), c.get(2).toString(), true);
                    }
                    x = false;
                    String u = (String) c.get(0);
                    boolean admin = (boolean) c.get(3);
                } else if(a == 2) {
                    System.out.println("Por hacer");
                    x = false;
                } else if(a == 3){
                    System.out.println("Saliendo");
                    x = false;
                } else{
                    System.out.println("Número inválido, por favor ingresa un número válido (1 o 2)");
                }
            } catch (InputMismatchException e) {
                System.out.println("Error: Debe ingresar un número válido (1 o 2).");
                scn.nextLine();
            }
        }

    }
    public static void main(String[] args) {
        Scanner scn = new Scanner(System.in);
        logIn(scn);
    }
}