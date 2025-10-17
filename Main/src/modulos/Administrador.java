package modulos;
import modulos.Usuario;

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
}
