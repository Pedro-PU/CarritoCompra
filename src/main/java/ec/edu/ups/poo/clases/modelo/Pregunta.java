package ec.edu.ups.poo.clases.modelo;

import ec.edu.ups.poo.clases.util.MensajeInternacionalizacionHandler;

public class Pregunta {
    private int id;
    private String enunciadoPregunta;

    public Pregunta(int id, String enunciadoPregunta) {
        this.id = id;
        this.enunciadoPregunta = enunciadoPregunta;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEnunciadoPreguntaRaw() {
        return enunciadoPregunta;
    }

    public String getEnunciadoPregunta(MensajeInternacionalizacionHandler mi) {
        return mi.get(enunciadoPregunta);
    }

    public void setEnunciadoPregunta(String enunciadoPregunta) {
        this.enunciadoPregunta = enunciadoPregunta;
    }
}
