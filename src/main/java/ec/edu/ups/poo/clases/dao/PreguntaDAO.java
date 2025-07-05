package ec.edu.ups.poo.clases.dao;

import ec.edu.ups.poo.clases.modelo.Pregunta;
import ec.edu.ups.poo.clases.modelo.Respuesta;

import java.util.List;

public interface PreguntaDAO {
    void crear(Pregunta enunciadoPregunta);

    Pregunta buscarPorId(int id);

    List<Pregunta> listarTodas();

    void actualizar(Pregunta enunciadoPregunta);

    void eliminar(int id);
}
