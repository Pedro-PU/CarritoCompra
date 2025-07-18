package ec.edu.ups.poo.clases.modelo;

/**
 * Excepción personalizada que se lanza cuando una contraseña no cumple con los criterios de validación del sistema.
 * Generalmente involucra longitud mínima, uso de mayúsculas, minúsculas y caracteres especiales.
 */
public class Contrasenia extends RuntimeException {

    /**
     * Constructor que crea una nueva excepción con un mensaje explicativo.
     * @param message Descripción del error relacionado a la contraseña.
     */
    public Contrasenia(String message) {
        super(message);
    }
}
