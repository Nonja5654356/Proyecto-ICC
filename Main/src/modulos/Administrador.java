package modulos;

import java.util.ArrayList;
import java.util.List;

//In this file is where the admin things go, explained on the README.md
public class Administrador extends Usuario{
    private List<String> empleados;
    private List<Integer> idTareasCreadas;

    public Administrador(){
        super();
        this.empleados =  new ArrayList<>();
        this.idTareasCreadas =  new ArrayList<>();
    }

    public Administrador(String nombre, String correo, String password, boolean alta, List<String> empleados, List<Integer> idTareasCreadas){
        super(nombre, correo, password, alta);
        this.empleados = empleados;
        this.idTareasCreadas = idTareasCreadas;
    }

    @Override
    public void mostrarInfo() {
        super.mostrarInfo();
        if(empleados.isEmpty()){
            System.out.println("Actualmente no se tiene nungún empleado.");
        }for (int i = 0; i < empleados.size(); i++) {
            System.out.println(i + " | " + empleados.get(i));
        }
        if(idTareasCreadas.isEmpty()) {
            System.out.println("No se han creado tareas.");
        }for (int i = 0; i < idTareasCreadas.size(); i++) {
            System.out.println("Element at index " + i + ": " + idTareasCreadas.get(i));
        }
    }

    public void menuUsuario() {
        System.out.println("|              Listar Empleados (1)              |");
        System.out.println("|            Listar Tareas Creadas (2)           |");
        System.out.println("|                 Crear Tarea (3)                |");
        System.out.println("|                Asignar Tarea (4)               |");
        System.out.println("|        Dar de alta a nuevo empleado (5)        |");
        System.out.println("|           Dar de baja a empleado (6)           |");
        super.menuUsuario(7,8);
    }
}
