import java.util.*;

public class Main {
    static class Usuario{
        public String nombre, correo, password;
    }
    public static void crearCuenta(Scanner Scan){
        Scanner scn = Scan;
        System.out.println("Por hacer");
    }
    public static void logIn(Scanner Scan){
        System.out.println("##############################");
        System.out.println("     Ingresar al sistema");
        System.out.println("##############################");
        System.out.println("       Crear Cuenta (1)");
        System.out.println("         Ingresar (2)");
        System.out.println("##############################");
        System.out.print("Ingresa un número: ");
        Scanner scn = Scan;
        byte a = scn.nextByte();
        if (a==1){
            crearCuenta(scn);
        }
        System.out.println("Por hacer");

    }
    public static void main(String[] args) {
        Scanner scn = new Scanner(System.in);
        logIn(scn);
    }
}