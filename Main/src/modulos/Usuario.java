package modulos;

public class Usuario{
    private String nombre, correo, password;
    private boolean alta;

    public Usuario(){
        this.nombre = "Aeaea ioioioi uuu";
        this.correo = "correo@correo.com";
        this.password = "12345";
        this.alta = false;
    }

    public Usuario(String nombre, String correo, String password, boolean alta) {
        this.nombre = nombre;
        this.correo = correo;
        this.password = password;
        this.alta  = alta;
    }

    public void mostrarInfo(){
        System.out.println("Nombre : " + nombre);
        System.out.println("Correo : " + correo);
        System.out.println("Contraseña : " + "*".repeat(password.length()));
        System.out.println("Estado de cuenta : " + (alta ? "Dado de alta" : "No dado de alta aún"));
    }
}
