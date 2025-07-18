package ec.edu.ups.poo.clases.util;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Clase encargada de manejar la internacionalización del sistema.
 * Carga los recursos de texto según el idioma y país configurados, permitiendo acceder a mensajes localizados.
 */
public class MensajeInternacionalizacionHandler {

    private ResourceBundle bundle;
    private Locale locale;

    /**
     * Constructor que inicializa el manejador con un idioma y país específicos.
     * @param lenguaje Código ISO del idioma (ej. "es", "en", "fr").
     * @param pais Código ISO del país (ej. "EC", "US", "FR").
     */
    public MensajeInternacionalizacionHandler(String lenguaje, String pais) {
        this.locale = new Locale(lenguaje, pais);
        this.bundle = ResourceBundle.getBundle("mensajes", locale);
    }

    /**
     * Obtiene el texto asociado a una clave en el archivo de propiedades del idioma actual.
     * @param key Clave del mensaje.
     * @return Texto localizado correspondiente a la clave.
     */
    public String get(String key) {
        return bundle.getString(key);
    }

    /**
     * Establece un nuevo idioma y país para la internacionalización.
     * Recarga automáticamente el archivo de propiedades correspondiente.
     * @param lenguaje Código ISO del idioma.
     * @param pais Código ISO del país.
     */
    public void setLenguaje(String lenguaje, String pais) {
        this.locale = new Locale(lenguaje, pais);
        this.bundle = ResourceBundle.getBundle("mensajes", locale);
    }

    /**
     * Obtiene la configuración regional actual utilizada por el manejador.
     * @return Objeto Locale que representa el idioma y país actuales.
     */
    public Locale getLocale() {
        return locale;
    }
}
