package ec.edu.ups.poo.clases.modelo;

public class Respuesta {
    private Pregunta pregunta;
    private String respuesta;

    public Respuesta(Pregunta pregunta) {
        this.pregunta = pregunta;
        this.respuesta = null;
    }


    public Pregunta getPregunta() {
        return pregunta;
    }

    public String getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(String respuesta) {
        this.respuesta = respuesta;
    }

}
