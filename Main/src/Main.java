import modulos.*;
import java.util.*;

public class Main {
    // Hoy empiezo a documentar

    // crearCuenta
    /**

     */
    public static Usuario crearCuenta(Scanner scn, BaseDeDatos baseDeDatos) {
        scn.nextLine();
        String n = " ", c = " ", p = " ";
        byte b;
        boolean a = false, x = true, atSign = false;
        boolean existe;
        System.out.println("#".repeat(50) + "\n|" + " ".repeat(18) + "Crear Cuenta" + " ".repeat(18) + "|\n" + "#".repeat(50));
        while (x) {
            System.out.print("| Ingrese su nombre (Ingrese 0 para regresar): ");
            n = scn.nextLine().trim();
            if (n.equals("0")){
                System.out.println("| Regresando...");
                return new Empleado();
            }
            if (n.isEmpty()) {
                System.out.println("# Nombre inválido, intente otra vez: ");
            } else if (!n.matches("[a-zA-ZáéíóúÁÉÍÓÚàèìòùÀÈÌÒÙâêîôûÂÊÎÔÛäëïöüÄËÏÖÜñÑ ]+")) {
                System.out.println("# El nombre solo puede contener letras y espacios, intente otra vez: ");
            } else {
                x = false;
            }
        }
        x = true;
        while (x) {
            existe = false;
            System.out.print("| Ingrese su correo (Ingrese 0 para regresar): ");
            c = scn.nextLine().trim();
            if (c.equals("0")){
                System.out.println("| Regresando...");
                return new Empleado();
            }
            List<String> correos = baseDeDatos.obtenerCorreos();
            for (String correo : correos) {
                if (correo.equals(c)) {
                    existe = true;
                    break;
                }
            }
            if(!existe) {
                int aS = 0;
                atSign = false;
                for(int i=0; i<c.length();i++){
                    if(c.charAt(i) == '@'){
                        aS++;
                    }
                }if(aS>1){
                    atSign = true;
                }
                if (!c.contains("@")){
                    System.out.println("# Correo inválido, debe contener \"@\", intente otra vez: ");
                } else if(!c.endsWith(".com")) {
                    System.out.println("# Correo inválido, debe terminar con \".com\", intente otra vez: ");
                }else if(c.startsWith("@")){
                    System.out.println("# Correo inválido, no debe iniciar con \"@\", intente otra vez: ");
                }else if(atSign) {
                    System.out.println("# Correo inválido, no puede tener mas de 1 \"@\", intente otra vez: ");
                }else {
                    x = false;
                }
            }else{
                System.out.println("# Ese correo ya ha sido utilizado, intente otra vez: ");
            }
        }
        x = true;
        while (x) {
            System.out.print("| Ingrese una contraseña para su cuenta (Ingrese 0 para regresar): ");
            p = scn.nextLine().trim();
            if(p.equals("0")){
                System.out.println("| Regresando...");
                return new Empleado();
            }
            if (p.isEmpty()) {
                System.out.println("# La contraseña no puede estar vacía. Intente otra vez: ");
            } else if (p.contains(" ")) {
                System.out.println("# La contraseña no puede contener espacios. Intente otra vez: ");
            } else if (p.length() < 7) {
                System.out.println("# La contraseña debe tener al menos 8 caracteres. Intente otra vez: ");
            } else {
                x = false;
            }
        }
        x = true;
        while(x) {
            try {
                System.out.print("| Seleccione su rol (1.- Empleado 2.- Administrador) (Ingrese 0 para regresar) :");
                b = scn.nextByte();
                if (b==0){
                    System.out.println("| Regresando...");
                    return new Empleado();
                }
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

    // iniciarSesion
    /**
    iniciarSesion hace lo siguiente:
        1.- se usa scn.nextline() para limpiar el scanner.
        2.- se inicializan las variables.
        3.- se inicializan los booleanos existe y x.
        4.- se inicia el ciclo con x.
        5.- el usuario debe poner su correo.
            5.0.- si se escoge 0, regresa empleado vacío.
            5.1.- se checa si el correo existe en la base de datos.
                5.1.0.- si no se encuentra el correo, se le muestra al usuario que el correo no existe, el ciclo continúa.
                5.1.1.- si se encuentra el correo, existe se vuelve verdadero y se obtiene la contraseña correcta del correo.
        6.- se le pide al usuario la contraseña del correo.
            6.1.- si se escribe 0, se regresa el usuario vacío.
            6.2.- si el usuario de equivoca, un contador crece, si el contador llega a 3, se le indicará al usuario que ha intentado varias veces y se regresa el empleado vacío.
            6.3.- si el usuario escribe la contraseña correcta, se usa la base de datos para obtener al usuario, se regresa al usuario.
     */
    public static Usuario iniciarSesion(Scanner scn, BaseDeDatos baseDeDatos){
        scn.nextLine();
        String n="", p, expectedPassword="";
        boolean x = true;
        boolean existe = false;
        System.out.println("#".repeat(50));
        System.out.println("|                Inicio de sesión                |");
        System.out.println("#".repeat(50));
        while(x) {
            System.out.print("| Ingrese su correo (Ingrese 0 para regresar): ");
            n = scn.nextLine();
            if (n.equals("0")){
                System.out.println("Regresando...");
                return new Empleado();
            }
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
            System.out.print("| Ingrese su contraseña (Ingrese 0 para regresar): ");
            p = scn.nextLine();
            if (p.equals("0")){
                System.out.println("Regresando...");
                return new Empleado();
            }
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

    // logIn
    /**
    logIn hace lo siguiente:
        1.- Imprime el menú.
        2.- inicia un ciclo con el booleano x.
        3.- si se elige 1, se llama srearCuenta.
            3.0.- x se vuelve false, terminano el ciclo de la pantalla de login.
            3.1.- si se regresa el empleado vacio, la funcion regresa true, continuando el ciclo de login y regreando al inicio de la funcion.
            3.2.- si no se regresa el empleado vacio, se llama menuUsuario.
        4.- si se elige 2, se llama a iniciarSesion.
            4.0.- x se vuelve false, terminando el ciclo de la pantalla de login.
            4.1.- si se regresa el empleado vacio, la funcion regresa true, continuando el ciclo de login y regreando al inicio de la funcion.
            4.2.- si no se regresa el empleado vacio, se llama menuUsuario.
        5.- si se elige 3, se regresa false, terminando el ciclo de Main y terminando el programa.
        6.- si se elige otro número, se imprime el mensaje de error y se continúa el ciclo.
        7.- si se elige otras cosas, se atrapa el input mismatch error y se le indica al usuario, se continúa el ciclo.
     */
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
                    Usuario newUser = crearCuenta(scn, baseDeDatos);
                    if(newUser.getCorreo().equals("correo@correo.com")){
                        return true;
                    }else {
                        newUser.menuUsuario(scn);
                    }
                } else if(a == 2) {
                    x = false;
                    Usuario newUser = iniciarSesion(scn, baseDeDatos);
                    if(newUser.getCorreo().equals("correo@correo.com")){
                        return true;
                    }else {
                        newUser.menuUsuario(scn);
                    }
                } else if(a == 3){
                    System.out.println("# Saliendo...");
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

    // Main
    /**
    En el main ocurre lo siguiente:
        1.- se crea un booleano llamado x, fijado como true.
        2.- se crea el scanner que se va a utilizar a lo largo del programa.
        3.- se crea la instancia de la base de datos y se crea la base de datos en caso de que esta no exisita.
        4.- con el booleano x se crea un bucle, llamando login hasta que este regrese falso, terminando el bucle y con eso el programa.
    */
    public static void main(String[] args) {
        boolean x = true;
        Scanner scn = new Scanner(System.in);
        BaseDeDatos baseDeDatos = new BaseDeDatos();
        baseDeDatos.iniciarBaseDeDatos();
        while(x) {
            x = logIn(scn, baseDeDatos);
        }
    }
}