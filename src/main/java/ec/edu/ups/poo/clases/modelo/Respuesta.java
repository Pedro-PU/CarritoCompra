package ec.edu.ups.poo.clases.modelo;

/**
 * Clase que representa una respuesta a una pregunta en el sistema.
 * Almacena la pregunta asociada y la respuesta proporcionada por el usuario.
 */
public class Respuesta {

    private Pregunta pregunta;
    private String respuesta;

    /**
     * Constructor que inicializa una respuesta con su pregunta asociada.
     * La respuesta comienza como null hasta que el usuario la proporcione.
     * @param pregunta Pregunta que se responderá.
     */
    public Respuesta(Pregunta pregunta) {
        this.pregunta = pregunta;
        this.respuesta = null;
    }

    /**
     * Obtiene la pregunta asociada a esta respuesta.
     * @return Objeto Pregunta vinculado.
     */
    public Pregunta getPregunta() {
        return pregunta;
    }

    /**
     * Obtiene el texto de la respuesta proporcionada.
     * @return Cadena con la respuesta; puede ser null si aún no se ha ingresado.
     */
    public String getRespuesta() {
        return respuesta;
    }

    /**
     * Establece el texto de la respuesta proporcionada.
     * @param respuesta Cadena que representa la respuesta del usuario.
     */
    public void setRespuesta(String respuesta) {
        this.respuesta = respuesta;
    }
}

