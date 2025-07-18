package ec.edu.ups.poo.clases.modelo;

/**
 * Excepción personalizada utilizada para señalar errores relacionados con cédulas no válidas.
 * Se lanza cuando el formato o el contenido de una cédula ecuatoriana no cumplen los criterios de validación.
 */
public class Cedula extends RuntimeException {

  /**
   * Constructor que crea una instancia de la excepción con un mensaje descriptivo.
   * @param message Texto explicativo del error.
   */
  public Cedula(String message) {
    super(message);
  }
}
