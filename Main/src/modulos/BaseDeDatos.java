package modulos;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public class BaseDeDatos {
    private static final String DATABASE_DIR = "database";
    private static final String USUARIOS_LISTA = Paths.get(DATABASE_DIR, "usuarios.txt").toString();
    private static final String TAREAS_LISTA = Paths.get(DATABASE_DIR, "tareas.txt").toString();
    private static final String ASIGNACIONES_LISTA = Paths.get(DATABASE_DIR, "asignaciones.txt").toString();

    public void iniciarBaseDeDatos() {
        File directorio = new File(DATABASE_DIR);
        if (!directorio.exists()) {
            directorio.mkdir();
        }
        crearArchivos(USUARIOS_LISTA);
        crearArchivos(TAREAS_LISTA);
        crearArchivos(ASIGNACIONES_LISTA);
    }

    private void crearArchivos(String filePath) {
        File file = new File(filePath);
    }

    public void guardarUsuario(Usuario user) {
        Path usuariosDir = Paths.get(DATABASE_DIR, "usuarios.txt");
        List<String> lines = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(usuariosDir.toFile()))) {
            String line;
            while ((line = br.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String userData = user.getNombre() + "|" + user.getCorreo() + "|" + user.getPassword() + "|" + user.getAlta() + "|" + user.getTipo();
        if(user instanceof Administrador administrador){
            userData += "|" + (!administrador.getEmpleados().isEmpty() ? String.join(",", administrador.getEmpleados()) : " ") + "|" + (!administrador.getIdTareasCreadas().isEmpty() ? administrador.getIdTareasCreadas().stream().map(Object::toString).collect(Collectors.joining(",")) : "0") + "|";
        }else{
            userData += "|||";
        }
        lines.add(userData);
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(usuariosDir.toFile()))) {
            for (String line : lines) {
                bw.write(line);
                bw.newLine();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public List<String> obtenerCorreos(){
        Path usuariosDir = Paths.get(DATABASE_DIR, "usuarios.txt");
        List<String> lines = new ArrayList<>();
        List<String> correos = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(usuariosDir.toFile()))) {
            String line;
            while ((line = br.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        for (String data : lines) {
            String[] parts = data.split("\\|");
            correos.add(parts[1]);
        }
        return correos;
    }

    public String obtenerPassword(int x){
        Path usuariosDir = Paths.get(DATABASE_DIR, "usuarios.txt");
        List<String> lines = new ArrayList<>();
        List<String> passwords = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(usuariosDir.toFile()))) {
            String line;
            while ((line = br.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        for (String data : lines) {
            String[] parts = data.split("\\|");
            passwords.add(parts[2]);
        }
        return passwords.get(x);
    }

    public Usuario obtenerUsuario(String correo){
        int x = -1;
        Path usuariosDir = Paths.get(DATABASE_DIR, "usuarios.txt");
        List<String> lines = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(usuariosDir.toFile()))) {
            String line;
            while ((line = br.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        for(int i=0; i<lines.size();i++){
            String data = lines.get(i);
            String[] parts = data.split("\\|");
            if(parts[1].equals(correo)){
                x = i;
                break;
            }
        }
        if(x==-1){
            return new Empleado();
        }
        String[] datos = lines.get(x).split("\\|");
        if(Boolean.parseBoolean(datos[4])){
            String[] empleados = datos[5].split(",");
            List<String> listaEmpleados = new ArrayList<>(List.of(empleados));
            List<Integer> listaTareas = (datos.length==7) ? Arrays.stream(datos[6].split(",")).map(String::trim).map(Integer::parseInt).collect(Collectors.toList()) : new ArrayList<>();
            return new Administrador(datos[0], datos[1], datos[2], Boolean.parseBoolean(datos[3]), listaEmpleados, listaTareas);
        }else{
            return new Empleado(datos[0], datos[1], datos[2], Boolean.parseBoolean(datos[3]));
        }
    }

    /// Modificar para que genere el ID automáticamente, eliminar ID de tareas
    public void guardarTarea(Tareas tarea) {
        Path tareasDir = Paths.get(DATABASE_DIR, "tareas.txt");
        List<String> lines = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(tareasDir.toFile()))) {
            String line;
            while ((line = br.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String fechaEntrega = Arrays.toString(tarea.getFechaEntrega());
        fechaEntrega = fechaEntrega.substring(1, fechaEntrega.length() - 1);
        String datosTarea = tarea.getId() + "|" + tarea.getNombre() + "|" + tarea.getDescripcion() + "|" + fechaEntrega + "|" + tarea.getEstado();
        lines.add(datosTarea);
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(tareasDir.toFile()))) {
            for (String line : lines) {
                bw.write(line);
                bw.newLine();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Tareas> obtenerTareas(){
        Path usuariosDir = Paths.get(DATABASE_DIR, "tareas.txt");
        List<String> lines = new ArrayList<>();
        List<Tareas> listaTareas = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(usuariosDir.toFile()))) {
            String line;
            while ((line = br.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        for (String data : lines) {
            String[] parts = data.split("\\|");
            String[] tareasAsignadas = parts[3].split(",");
            int[] tareasArray = new int[tareasAsignadas.length];
            for (int i = 0; i < tareasAsignadas.length; i++) {
                tareasArray[i] = Integer.parseInt(tareasAsignadas[i].trim());
            }
            Tareas tarea = new Tareas(Integer.parseInt(parts[0]), parts[1], parts[2], tareasArray, parts[4].equals("true"));
            listaTareas.add(tarea);
        }
        return listaTareas;
    }

    public List<String> obtenerEmpleados(){
        Path usuariosDir = Paths.get(DATABASE_DIR, "usuarios.txt");
        List<String> lines = new ArrayList<>();
        List<String> empleados = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(usuariosDir.toFile()))) {
            String line;
            while ((line = br.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        for (String data : lines) {
            String[] parts = data.split("\\|");
            if(!Boolean.parseBoolean(parts[4])) {
                empleados.add(parts[1]);
            }
        }

        return empleados;
    }

    public void guardarAsignacion(String correo, int id) {
        int x = -1;
        Path usuariosDir = Paths.get(DATABASE_DIR, "asignaciones.txt");
        List<String> lines = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(usuariosDir.toFile()))) {
            String line;
            while ((line = br.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        for(int i=0; i<lines.size();i++){
            String data = lines.get(i);
            String[] parts = data.split("\\|");
            if(parts[0].equals(correo)){
                x = i;
                break;
            }
        }

        if(x>=0){
            String actualizar = lines.get(x);
            actualizar = actualizar + ", " + id;
            lines.set(x,actualizar);
        }else{
            String asignacion = correo + "|" + id;
            lines.add(asignacion);
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(usuariosDir.toFile()))) {
            for (String line : lines) {
                bw.write(line);
                bw.newLine();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Map<String, List<Integer>> obtenerAsignaciones(){
        Path usuariosDir = Paths.get(DATABASE_DIR, "asignaciones.txt");
        List<String> lines = new ArrayList<>();
        Map<String, List<Integer>> asignaciones = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader(usuariosDir.toFile()))) {
            String line;
            while ((line = br.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        for (String data : lines) {
            String[] parts = data.split("\\|");
            String[] tareas = parts[1].split(",");
            List<Integer> listaTareas = new ArrayList<>();
            for (String tarea : tareas) {
                listaTareas.add(Integer.parseInt(tarea.trim()));
            }
            asignaciones.put(parts[0], listaTareas);
        }
        return asignaciones;
    }
}
