package ec.edu.ups.poo.clases.modelo;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

public class Usuario {
    private String username;
    private String contrasenia;
    private Rol rol;
    private String nombre;
    private String celular;
    private GregorianCalendar fecha;
    private String email;
    private List<Respuesta> respuestas;

    public Usuario(String nombreDeUsuario, String contrasenia, Rol rol, String nombre,
                   String celular, GregorianCalendar fecha, String email) {
        this.username = nombreDeUsuario;
        this.contrasenia = contrasenia;
        this.rol = rol;
        this.nombre = nombre;
        this.celular = celular;
        this.fecha = fecha;
        this.email = email;
        this.respuestas = new ArrayList<>();
    }
    public Usuario() {}

    public List<Respuesta> getRespuestas() {
        return respuestas;
    }

    public void setRespuestas(List<Respuesta> respuestas) {
        this.respuestas = respuestas;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) throws Email{
        if(!validarEmail(email)){
            throw new Email("No es un número de teléfono válido");
        }else{
            this.email = email;
        }
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) throws Cedula {
        if (!validarCedula(username)) {
            throw new Cedula("No es una cédula válida");
        } else {
            this.username = username;
        }
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) throws Contrasenia {
        if(!validarContrasenia(contrasenia)) {
            throw new Contrasenia("No es una contraseña válida");
        }else{
            this.contrasenia = contrasenia;
        }
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

    public void setCelular(String celular) throws Celular{
        if(!validarCelular(celular)){
            throw new Celular("No es un número de teléfono válido");
        }else{
            this.celular = celular;
        }
    }

    public GregorianCalendar getFecha() {
        return fecha;
    }

    public void setFecha(GregorianCalendar fecha) {
        this.fecha = fecha;
    }

    //Validación contrasenia
    private boolean validarContrasenia(String contrasenia) {
        if (contrasenia == null || contrasenia.length() < 6)
            return false;
        boolean tieneMayuscula = false;
        boolean tieneMinuscula = false;
        boolean tieneEspecial = false;

        for (char c : contrasenia.toCharArray()) {
            if (Character.isUpperCase(c))
                tieneMayuscula = true;
            else if (Character.isLowerCase(c))
                tieneMinuscula = true;
            else if (c == '@' || c == '_' || c == '-')
                tieneEspecial = true;
            if (tieneMayuscula && tieneMinuscula && tieneEspecial)
                return true;
        }
        return false;
    }

    //Validación cédula
    private boolean validarCedula(String cedula) {
        if (cedula == null || cedula.length() != 10) {
            return false;
        }
        try {
            int provincia = Integer.parseInt(cedula.substring(0, 2));
            int tercerDigito = Character.getNumericValue(cedula.charAt(2));

            if (provincia < 1 || provincia > 24 || tercerDigito >= 6)
                return false;
            int suma = 0;
            for (int i = 0; i < 9; i++) {
                int num = Character.getNumericValue(cedula.charAt(i));
                if (i % 2 == 0) {
                    num *= 2;
                    if (num > 9) num -= 9;
                }
                suma += num;
            }
            int digitoVerificador = Character.getNumericValue(cedula.charAt(9));
            int decenaSuperior = ((suma + 9) / 10) * 10;
            int resultado = decenaSuperior - suma;
            if (resultado == 10)
                resultado = 0;
            return resultado == digitoVerificador;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    private boolean validarCelular(String celular) {
        if (!celular.matches("\\d{7,15}")) {
            return false;
        }else{
            return true;
        }
    }
    private boolean validarEmail(String email) {
        if (!email.matches("^[\\w\\.-]+@[\\w\\.-]+\\.[a-zA-Z]{2,}$")) {
            return false;
        }else{
            return true;
        }
    }
}
