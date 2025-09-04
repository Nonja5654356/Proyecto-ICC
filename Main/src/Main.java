import java.util.*;

public class Main {
    static class Usuario{
        public String nombre, correo, password;

        public Usuario(String nombre, String correo, String password) {
            this.nombre = nombre;
            this.correo = correo;
            this.password = password;
        }
    }
    public static void crearCuenta(Scanner scn){
        scn.nextLine();
        String n, c, p;
        System.out.print("Ingresa tu nombre: ");
        n = scn.nextLine();
        System.out.print("Ingresa tu correo: ");
        c = scn.nextLine();
        System.out.print("Ingresa una contraseña para tu cuenta: ");
        p = scn.nextLine();
        Usuario newUser = new Usuario(n, c, p);
        System.out.println(newUser);
    }
    public static void logIn(Scanner scn){
        System.out.println("##############################");
        System.out.println("|    Ingresar al sistema     |");
        System.out.println("##############################");
        System.out.println("|      Crear Cuenta (1)      |");
        System.out.println("|        Ingresar (2)        |");
        System.out.println("##############################");
        boolean x = true;
        while(x){
            System.out.print("Ingresa un número: ");
            try {
                byte a = scn.nextByte();
                if (a == 1) {
                    crearCuenta(scn);
                    x = false;
                } else if (a == 2) {
                    System.out.println("Por hacer");
                    x = false;
                } else {
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