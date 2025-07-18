package ec.edu.ups.poo.clases.modelo;

import ec.edu.ups.poo.clases.util.MensajeInternacionalizacionHandler;

/**
 * Clase que representa una pregunta utilizada en el sistema, típicamente para cuestionarios de recuperación o seguridad.
 * Soporta internacionalización y ofrece tanto el acceso directo como la versión localizada del enunciado.
 */
public class Pregunta {

    private int id;
    private String enunciadoPregunta;

    /**
     * Constructor que inicializa la pregunta con su identificador y clave de enunciado.
     * @param id Identificador único de la pregunta.
     * @param enunciadoPregunta Clave asociada al mensaje internacionalizado.
     */
    public Pregunta(int id, String enunciadoPregunta) {
        this.id = id;
        this.enunciadoPregunta = enunciadoPregunta;
    }

    /**
     * Obtiene el identificador de la pregunta.
     * @return Entero que representa el id de la pregunta.
     */
    public int getId() {
        return id;
    }

    /**
     * Establece el identificador de la pregunta.
     * @param id Valor entero del id.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Obtiene el enunciado sin aplicar internacionalización.
     * Generalmente es una clave del archivo de propiedades.
     * @return Texto bruto del enunciado.
     */
    public String getEnunciadoPreguntaRaw() {
        return enunciadoPregunta;
    }

    /**
     * Obtiene el enunciado traducido utilizando el manejador de internacionalización.
     * @param mi Handler de internacionalización que proporciona el texto traducido.
     * @return Enunciado traducido según el idioma actual.
     */
    public String getEnunciadoPregunta(MensajeInternacionalizacionHandler mi) {
        return mi.get(enunciadoPregunta);
    }

    /**
     * Establece la clave del enunciado de la pregunta.
     * @param enunciadoPregunta Texto que será usado como clave de traducción.
     */
    public void setEnunciadoPregunta(String enunciadoPregunta) {
        this.enunciadoPregunta = enunciadoPregunta;
    }
}

