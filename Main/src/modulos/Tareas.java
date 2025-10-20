package modulos;
//In this file is where the chore details go, explained on the README.md
public class Tareas {
    private final int id;
    private String nombre, descripcion;
    private int[] fechaEntrega;
    private boolean estado;

    public Tareas(){
        this.id = 0;
        this.nombre = "Empty";
        this.descripcion = "No desription";
        this.fechaEntrega = new int[3];
        this.estado = false;
    }

    public Tareas(int id, String nombre, String descripcion, int[] fechaEntrega, boolean estado){
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.fechaEntrega = fechaEntrega;
        this.estado = estado;
    }

    //getters
    public int getId() {return id;}
    public String getNombre() {return nombre;}
    public String getDescripcion() {return descripcion;}
    public int[] getFechaEntrega() {return fechaEntrega;}
    public boolean getEstado(){return estado;}

    //setters
    public void setNombre(String nombre) {this.nombre = nombre;}
    public void setDescripcion(String descripcion) {this.descripcion = descripcion;}
    public void setFechaEntrega(int[] fechaEntrega) {this.fechaEntrega = fechaEntrega;}
    public void setEstado(boolean estado){this.estado = estado;}

    public void mostrarDetallesTarea(int n){
        System.out.println("| " + n + " | " + (estado ? "[X]" : "[ ]") + " | " + nombre + " | " + descripcion + " | " + fechaEntrega[0] + "/" + fechaEntrega[1] + "/" + fechaEntrega[2] + " | ");
    }
}
