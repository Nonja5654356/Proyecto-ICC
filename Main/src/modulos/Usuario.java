package modulos;

import java.util.Scanner;

public abstract class Usuario{
    protected String nombre;
    protected String password;
    protected final String correo;
    protected boolean alta;
    final boolean tipo;

    public Usuario(){
        this.nombre = "Aeaea ioioioi uuu";
        this.correo = "correo@correo.com";
        this.password = "12345";
        this.alta = false;
        this.tipo = false;
    }

    public Usuario(String nombre, String correo, String password, boolean alta, boolean tipo) {
        this.nombre = nombre;
        this.correo = correo;
        this.password = password;
        this.alta  = alta;
        this.tipo = tipo;
    }

    //getters
    public boolean getTipo(){return tipo;}
    public String getNombre(){return nombre;}
    public String getCorreo(){return correo;}
    public String getPassword(){return password;}
    public boolean getAlta(){return alta;}

    //setters
    public void setNombre(String nombre){this.nombre = nombre;}
    public void setPassword(String password){this.password = password;}
    public void setAlta(boolean alta){this.alta = alta;}

    public void mostrarInfo(){
        System.out.println("Nombre : " + nombre);
        System.out.println("Correo : " + correo);
        System.out.println("Contraseña : " + password);
        System.out.println("Estado de cuenta : " + (alta ? "Dado de alta" : "No dado de alta aún"));
    }

    public void actualizarInfo(){
        Scanner scn = new Scanner(System.in);
        System.out.print("| Ingresa tu nuevo nombre (Ingrese 0 para cancelar): ");
        String nombre = scn.nextLine().trim();
        if (nombre.equals("0")) {
            System.out.println("| Regresando... ");
            //Regresar
        }
        if (nombre.isEmpty()){
            System.out.println("# Nombre inválido, intente otra vez: ");
        } else if (!nombre.matches("[a-zA-ZáéíóúÁÉÍÓÚàèìòùÀÈÌÒÙâêîôûÂÊÎÔÛäëïöüÄËÏÖÜñÑ ]+")) {
            System.out.println("# El nombre solo puede contener letras y espacios");
        }
        setNombre(nombre);
        System.out.print("| Ingresa tu nueva contraseña (Ingrese 0 para cancelar): ");
        String password = scn.nextLine();
        if (password.equals("0")) {
            System.out.println("| Regresando...");
            //Regresar
        }
        if (password.isEmpty()) {
            System.out.println("# La contraseña no puede estar vacia, Intente otra vez: ");
        } else if (password.contains(" ")) {
            System.out.println("# La contraseña no puede contener espacios. Intente otra vez: ");
        } else if (password.length() < 7){
            System.out.println("# La contraseña debe tener al menos 8 caracteres. Intente otra vez: ");
        }
        setPassword(password);
        BaseDeDatos baseDeDatos = new BaseDeDatos();
        baseDeDatos.actualizarDatosUsuario(correo, nombre, password);
        System.out.println("Información actualizada");
    }

    public void menuUsuario(){}
}