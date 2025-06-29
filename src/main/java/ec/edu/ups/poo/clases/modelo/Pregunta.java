package ec.edu.ups.poo.clases.modelo;
import ec.edu.ups.poo.clases.util.MensajeInternacionalizacionHandler;

public enum Pregunta {
    COLOR_FAVORITO("pregunta.color_favorito"),
    PRIMERA_MASCOTA("pregunta.primera_mascota"),
    COMIDA_FAVORITA("pregunta.comida_favorita"),
    CIUDAD_NACIMIENTO("pregunta.ciudad_nacimiento"),
    PROFESOR_FAVORITO("pregunta.profesor_favorito"),
    CANCION_FAVORITA("pregunta.cancion_favorita"),
    NOMBRE_PRIMER_AMIGO("pregunta.nombre_primer_amigo"),
    PELICULA_FAVORITA("pregunta.pelicula_favorita"),
    NOMBRE_MADRE("pregunta.nombre_madre"),
    NOMBRE_PADRE("pregunta.nombre_padre"),
    APODO_INFANCIA("pregunta.apodo_infancia"),
    OBJETO_PERSONAL("pregunta.objeto_personal"),
    NOMBRE_HERMANO("pregunta.nombre_hermano"),
    NOMBRE_PRIMERA_ESCUELA("pregunta.nombre_primera_escuela");

    private String enunciado;
    private MensajeInternacionalizacionHandler mi;

    Pregunta(String enunciado) {
        this.enunciado = enunciado;
    }
    Pregunta() {}

    public void setMensajeIdioma(MensajeInternacionalizacionHandler mi) {
        this.mi = mi;
    }

    public String getEnunciado() {
        if (mi != null) {
            return mi.get(enunciado);
        } else {
            return enunciado;
        }
    }

}
