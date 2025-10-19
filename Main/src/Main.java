import modulos.*;

import java.util.*;

public class Main {

    public static Usuario crearCuenta(Scanner scn){
        scn.nextLine();
        String n=" ", c=" ", p=" ";
        byte b;
        boolean a = false, x = true;
        System.out.println("#".repeat(50) + "\n|" + " ".repeat(18) + "Crear Cuenta" + " ".repeat(18) + "|\n" + "#".repeat(50));
        System.out.print("| Ingrese su nombre: ");
        n = scn.nextLine();
        while(x) {
            System.out.print("| Ingrese su correo: ");
            c = scn.nextLine();
            if(!(c.contains("@") && c.endsWith(".com"))){
                System.out.println("# Correo inválido, intente otra vez: ");
            }else {
                x = false;
            }
        }
        System.out.print("| Ingrese una contraseña para su cuenta: ");
        p = scn.nextLine();
        x = true;
        while(x) {
            try {
                System.out.print("| Seleccione su rol (1.- Empleado 2.- Administrador) :");
                b = scn.nextByte();
                if (0 < b && b < 3) {
                    a = b == 2;
                    x = false;
                } else {
                    System.out.println("# Número inválido, por favor ingresa un número válido (1 o 2)");
                }
            } catch (InputMismatchException e) {
                System.out.println("# Error: Debe ingresar un número válido (1 o 2).");
                scn.nextLine();
            }
        }
        if(a){
            return new Administrador(n, c, p, true, new ArrayList<>(), new ArrayList<>());
        }else{
            return new Empleado(n, c, p, true);
        }
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
            System.out.print("| Ingresa un número: ");
            try {
                byte a = scn.nextByte();
                if(a == 1) {
                    Usuario newUser = crearCuenta(scn);
                    newUser.menuUsuario();
                    x = false;
                } else if(a == 2) {
                    System.out.println("Por hacer");
                    x = false;
                } else if(a == 3){
                    System.out.println("Saliendo");
                    x = false;
                } else{
                    System.out.println("# Número inválido, por favor ingresa un número válido (1, 2 o 3)");
                }
            } catch (InputMismatchException e) {
                System.out.println("# Error: Debe ingresar un número válido (1, 2 o 3).");
                scn.nextLine();
            }
        }

    }

    public static void main(String[] args) {
        Scanner scn = new Scanner(System.in);
        logIn(scn);
    }
}