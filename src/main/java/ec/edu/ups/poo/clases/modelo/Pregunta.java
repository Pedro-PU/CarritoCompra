package ec.edu.ups.poo.clases.modelo;

public enum Pregunta {
    COLOR_FAVORITO("¿Cuál es tu color favorito?"),
    PRIMERA_MASCOTA("¿Cuál fue tu primera mascota?"),
    COMIDA_FAVORITA("¿Cuál es tu comida favorita?"),
    CIUDAD_NACIMIENTO("¿En qué ciudad naciste?"),
    PROFESOR_FAVORITO("¿Nombre de tu profesor favorito?"),
    CANCION_FAVORITA("¿Canción que más te gusta?"),
    NOMBRE_PRIMER_AMIGO("¿Cómo se llama tu primer amigo?"),
    PELÍCULA_FAVORITA("¿Cuál es tu película favorita?"),
    NOMBRE_MADRE("¿Cuál es el segundo nombre de tu madre?"),
    NOMBRE_PADRE("¿Cuál es el segundo nombre de tu padre?"),
    APODO_INFANCIA("¿Cuál era tu apodo de niño?"),
    OBJETO_PERSONAL("¿Cuál es tu objeto personal más preciado?"),
    NOMBRE_HERMANO("¿Nombre de tu hermano mayor?"),
    NOMBRE_PRIMERA_ESCUELA("¿Cómo se llama tu primera escuela?");

    private final String enunciado;

    Pregunta(String enunciado) {
        this.enunciado = enunciado;
    }

    public String getEnunciado() {
        return enunciado;
    }

}
