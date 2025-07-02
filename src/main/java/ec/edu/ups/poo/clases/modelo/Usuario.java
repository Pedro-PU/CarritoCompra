package ec.edu.ups.poo.clases.modelo;

import java.util.GregorianCalendar;

public class Usuario {
    private String username;
    private String contrasenia;
    private Rol rol;
    private String nombre;
    private String celular;
    private GregorianCalendar fecha;
    private String email;

    public Usuario(String nombreDeUsuario, String contrasenia, Rol rol, String nombre,
                   String celular, GregorianCalendar fecha, String email) {
        this.username = nombreDeUsuario;
        this.contrasenia = contrasenia;
        this.rol = rol;
        this.nombre = nombre;
        this.celular = celular;
        this.fecha = fecha;
        this.email = email;
    }
    public Usuario() {}

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public GregorianCalendar getFecha() {
        return fecha;
    }

    public void setFecha(GregorianCalendar fecha) {
        this.fecha = fecha;
    }
}
