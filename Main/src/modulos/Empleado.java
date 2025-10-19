package modulos;
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
        System.out.println("#".repeat(50));
        System.out.println("|        Bienvenido al Sistema de Tareas         |");
        System.out.println("#".repeat(50));
        System.out.println("| Hola");
        System.out.println("|           Checar datos personales (2)          |");
        System.out.println("|                Cerrar Sesión (3)               |");
        System.out.println("#".repeat(50));
    }
}