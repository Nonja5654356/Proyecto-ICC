package modulos;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

//In this file is where the chores system is on, explained on the README.md
public class SistemaTareas {
    private List<String> empleados;
    private List<Integer> tareas;
    private Map<String, List<Integer>> asignaciones;

    //getters
    public List<String> getEmpleados(){return empleados;}
    public List<Integer> getTareas(){return tareas;}
    public Map<String, List<Integer>> getAsignaciones(){return asignaciones;}

    //setters
    private void setEmpleados(){
        BaseDeDatos baseDeDatos = new BaseDeDatos();
        this.empleados = baseDeDatos.obtenerUsuarios();
    }
    private void setTareas(){
        BaseDeDatos baseDeDatos = new BaseDeDatos();
        this.tareas = baseDeDatos.obtenerTareas();
    }
    private void setAsignaciones(){
        BaseDeDatos baseDeDatos = new BaseDeDatos();
        this.asignaciones = baseDeDatos.obtenerAsignaciones();
    }
}
