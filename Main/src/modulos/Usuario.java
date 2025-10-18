package modulos;

import javax.swing.plaf.synth.SynthTabbedPaneUI;
import java.util.Scanner;

public class Usuario{
    private String nombre, password;
    private final String correo;
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

    public void actualizarInfo(){
        Scanner scn = new Scanner(System.in);
        System.out.print("Ingresa tu nuevo nombre: ");
        this.nombre = scn.nextLine();
        System.out.println("Ingresa tu nueva contraseña: ");
        this.password = scn.nextLine();
    }

    public void menuUsuario(){
        System.out.println("| Checar datos personales");
        System.out.println("#".repeat(50));
    }
}
