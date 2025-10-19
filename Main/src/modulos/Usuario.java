package modulos;

import java.util.Scanner;

public class Usuario{
    private String nombre;
    private String password;
    private final String correo;
    private boolean alta;
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
        System.out.println("Contraseña : " + "*".repeat(password.length()));
        System.out.println("Estado de cuenta : " + (alta ? "Dado de alta" : "No dado de alta aún"));
    }

    public void actualizarInfo(){
        Scanner scn = new Scanner(System.in);
        System.out.print("Ingresa tu nuevo nombre: ");
        String nombre = scn.nextLine();
        setNombre(nombre);
        System.out.println("Ingresa tu nueva contraseña: ");
        password = scn.nextLine();
        setPassword(password);
    }

    public void menuUsuario(){
        System.out.println("Menu, will be overridden :)");
    }
}