package ec.edu.ups.poo.clases.util;

/**
 * Excepción personalizada que se lanza cuando un correo electrónico no cumple con el formato esperado.
 * Utilizada para validar la estructura del email durante el registro o actualización de datos de usuario.
 */
public class Email extends RuntimeException {

    /**
     * Constructor que crea una nueva excepción con el mensaje especificado.
     * @param message Descripción del error relacionado con el email inválido.
     */
    public Email(String message) {
        super(message);
    }
}
