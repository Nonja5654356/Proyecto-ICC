package modulos;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
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
            userData += "|" + (!administrador.getEmpleados().isEmpty() ? String.join(",", administrador.getEmpleados()) : "") + "|" + (!administrador.getIdTareasCreadas().isEmpty() ? administrador.getIdTareasCreadas().stream().map(Object::toString).collect(Collectors.joining(",")) : "");
        }else{
            userData += "||";
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
}
