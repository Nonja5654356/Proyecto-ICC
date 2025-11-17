package modulos;

import java.util.List;
import java.util.Map;

//In this file is where the chores system is on, explained on the README.md
public class SistemaTareas {
    private List<String> empleados;
    private List<Tareas> tareas;
    private Map<String, List<Integer>> asignaciones;

    public SistemaTareas(){
        setEmpleados();
        setTareas();
        setAsignaciones();
    }

    //getters
    public List<String> getEmpleados(){return empleados;}
    public List<Tareas> getTareas(){return tareas;}
    public Map<String, List<Integer>> getAsignaciones(){return asignaciones;}

    //setters
    private void setEmpleados(){
        BaseDeDatos baseDeDatos = new BaseDeDatos();
        this.empleados = baseDeDatos.obtenerEmpleados();
    }
    private void setTareas(){
        BaseDeDatos baseDeDatos = new BaseDeDatos();
        this.tareas = baseDeDatos.obtenerTareas();
    }
    private void setAsignaciones(){
        BaseDeDatos baseDeDatos = new BaseDeDatos();
        this.asignaciones = baseDeDatos.obtenerAsignaciones();
    }

    public void listarTareasEmpleado(String correo){
        boolean correoExistente = asignaciones.containsKey(correo);
        boolean tareasPorHacer = false;
        if(correoExistente) {
            List<Integer> tareasEmpleado = asignaciones.get(correo);
            for (int i = 0; i < tareasEmpleado.size(); i++) {
                Tareas tarea = tareas.get(tareasEmpleado.get(i));
                tarea.mostrarDetallesTarea(i+1);
                if(!tarea.getEstado()){
                    tareasPorHacer = true;
                }
            }
            if(!tareasPorHacer){
                System.out.println("# Has completado todas tus tareas :)");
            }
        }else{
            System.out.println("# No tienes tareas asignadas :)");
        }
    }
}