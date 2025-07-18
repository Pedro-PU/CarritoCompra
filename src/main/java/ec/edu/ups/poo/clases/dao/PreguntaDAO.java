package ec.edu.ups.poo.clases.dao;

import ec.edu.ups.poo.clases.modelo.Pregunta;
import ec.edu.ups.poo.clases.modelo.Respuesta;

import java.util.List;

/**
 * Interfaz DAO para la entidad Pregunta.
 * Define las operaciones básicas de persistencia y gestión de preguntas utilizadas en el sistema,
 * típicamente para recuperación de cuentas o formularios.
 */
public interface PreguntaDAO {

    /**
     * Crea y almacena una nueva pregunta.
     * @param enunciadoPregunta Pregunta a registrar.
     */
    void crear(Pregunta enunciadoPregunta);

    /**
     * Busca una pregunta por su identificador único.
     * @param id ID numérico de la pregunta.
     * @return Pregunta correspondiente; null si no se encuentra.
     */
    Pregunta buscarPorId(int id);

    /**
     * Lista todas las preguntas disponibles en el sistema.
     * @return Lista de preguntas.
     */
    List<Pregunta> listarTodas();

    /**
     * Actualiza los datos de una pregunta existente.
     * @param enunciadoPregunta Pregunta con el nuevo contenido.
     */
    void actualizar(Pregunta enunciadoPregunta);

    /**
     * Elimina una pregunta del sistema por su ID.
     * @param id Identificador de la pregunta a borrar.
     */
    void eliminar(int id);
}
