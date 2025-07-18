package ec.edu.ups.poo.clases.modelo;

/**
 * Excepción personalizada que se lanza cuando un número de celular no cumple con los criterios de validación.
 * Generalmente usada para señalar errores en formato o longitud durante el registro de usuarios.
 */
public class Celular extends RuntimeException {

    /**
     * Constructor que crea una nueva excepción con el mensaje especificado.
     * @param message Texto descriptivo del error relacionado al número celular.
     */
    public Celular(String message) {
        super(message);
    }
}
