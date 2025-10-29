import modulos.*;
import java.util.*;

public class Main {

    public static Usuario crearCuenta(Scanner scn){
        scn.nextLine();
        String n=" ", c=" ", p;
        byte b;
        boolean a = false, x = true;
        BaseDeDatos baseDeDatos = new BaseDeDatos();
        System.out.println("#".repeat(50) + "\n|" + " ".repeat(18) + "Crear Cuenta" + " ".repeat(18) + "|\n" + "#".repeat(50));
        while(x) {
            System.out.print("| Ingrese su nombre: ");
            n = scn.nextLine();
            if(n.isEmpty()){
                System.out.println("# Nombre inválido, intente otra vez: ");
            }else {
                x = false;
            }
        }
        x = true;
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
            Administrador newUser = new Administrador(n, c, p, true, new ArrayList<>(), new ArrayList<>());
            baseDeDatos.guardarUsuario(newUser);
            return newUser;
        }else{
            Empleado newUser = new Empleado(n, c, p, true);
            baseDeDatos.guardarUsuario(newUser);
            return newUser;
        }
    }

    public static Usuario iniciarSesion(Scanner scn, BaseDeDatos baseDeDatos){
        scn.nextLine();
        String n="", p, expectedPassword="";
        boolean x = true;
        boolean existe = false;
        System.out.println("#".repeat(50));
        System.out.println("|                Inicio de sesión                |");
        System.out.println("#".repeat(50));
        while(x) {
            System.out.print("| Ingrese su correo: ");
            n = scn.nextLine();
            List<String> correos = baseDeDatos.obtenerCorreos();
            for (int i = 0; i < correos.size(); i++) {
                if (correos.get(i).equals(n)) {
                    expectedPassword = baseDeDatos.obtenerPassword(i);
                    existe = true;
                    break;
                }
            }
            if (existe) {
                x = false;
            } else {
                System.out.println("# Correo incorrecto o no existente, intente otra vez.");
            }
        }
        int intentos = 0;
        while(intentos<3) {
            System.out.print("| Ingrese su conteaseña: ");
            p = scn.nextLine();
            if (p.equals(expectedPassword)) {
                System.out.println("| Iniciando sesión...");
                return baseDeDatos.obtenerUsuario(n);
            }else{
                System.out.println("# Contraseña incorrecta, intente otra vez.");
                intentos++;
            }
        }if(intentos==3){
            System.out.println("# Demasiados intentos, inténtelo más tarde.");
            return new Empleado();
        }
        return new Empleado();
    }

    public static boolean logIn(Scanner scn, BaseDeDatos baseDeDatos){
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
                    x = false;
                    Usuario newUser = crearCuenta(scn);
                    newUser.menuUsuario();
                } else if(a == 2) {
                    x = false;
                    Usuario newUser = iniciarSesion(scn, baseDeDatos);
                    if(newUser.getCorreo().equals("correo@correo.com")){
                        x=true;
                        return true;
                    }else {
                        newUser.menuUsuario();
                    }
                } else if(a == 3){
                    System.out.println("# Saliendo...");
                    x = false;
                    return false;
                } else{
                    System.out.println("# Número inválido, por favor ingrese un número válido (1, 2 ó 3)");
                }
            } catch (InputMismatchException e) {
                System.out.println("# Error: Debe ingresar un número válido (1, 2 ó 3).");
                scn.nextLine();
            }
        }
        return true;
    }

    public static void main(String[] args) {
        boolean x = true;
        Scanner scn = new Scanner(System.in);
        BaseDeDatos baseDeDatos = new BaseDeDatos();
        baseDeDatos.iniciarBaseDeDatos();
        SistemaTareas sistemaTareas = new SistemaTareas();
        List<String> empleados = sistemaTareas.getEmpleados();
        System.out.println(empleados);
        while(x) {
            x = logIn(scn, baseDeDatos);
        }
    }
}