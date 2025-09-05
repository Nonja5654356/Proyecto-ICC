public class Usuario{
    private String nombre, correo, password;
    private boolean admin;

    public Usuario(String nombre, String correo, String password, boolean admin) {
        this.nombre = nombre;
        this.correo = correo;
        this.password = password;
        this.admin = admin;
    }
}
