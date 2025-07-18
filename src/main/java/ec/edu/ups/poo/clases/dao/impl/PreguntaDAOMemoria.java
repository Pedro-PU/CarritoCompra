package ec.edu.ups.poo.clases.dao.impl;

import ec.edu.ups.poo.clases.dao.PreguntaDAO;
import ec.edu.ups.poo.clases.modelo.Pregunta;
import ec.edu.ups.poo.clases.util.MensajeInternacionalizacionHandler;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Implementación en memoria del DAO de preguntas.
 * Almacena y gestiona las preguntas en una lista local sin persistencia física, ideal para entornos de pruebas o carga inicial.
 */
public class PreguntaDAOMemoria implements PreguntaDAO {

    private final List<Pregunta> preguntas = new ArrayList<>();

    /**
     * Constructor que inicializa el DAO con un conjunto de preguntas predeterminadas.
     * Las preguntas se almacenan con claves de internacionalización.
     * @param mi Manejador de internacionalización para obtener claves de texto.
     */
    public PreguntaDAOMemoria(MensajeInternacionalizacionHandler mi) {
        String[] claves = {
                "pregunta.color_favorito",
                "pregunta.primera_mascota",
                "pregunta.comida_favorita",
                "pregunta.ciudad_nacimiento",
                "pregunta.profesor_favorito",
                "pregunta.cancion_favorita",
                "pregunta.nombre_primer_amigo",
                "pregunta.pelicula_favorita",
                "pregunta.nombre_madre",
                "pregunta.nombre_padre",
                "pregunta.apodo_infancia",
                "pregunta.objeto_personal",
                "pregunta.nombre_hermano",
                "pregunta.nombre_primera_escuela"
        };
        for (int i = 0; i < claves.length; i++) {
            Pregunta r = new Pregunta(i + 1, claves[i]);
            crear(r);
        }
    }

    /**
     * Agrega una nueva pregunta a la lista en memoria.
     * @param enunciadoPregunta Pregunta a insertar.
     */
    @Override
    public void crear(Pregunta enunciadoPregunta) {
        preguntas.add(enunciadoPregunta);
    }

    /**
     * Busca una pregunta por su identificador único.
     * @param id Identificador de la pregunta.
     * @return Objeto Pregunta encontrado; null si no existe.
     */
    @Override
    public Pregunta buscarPorId(int id) {
        for (Pregunta p : preguntas) {
            if (p.getId() == id) {
                return p;
            }
        }
        return null;
    }

    /**
     * Devuelve la lista completa de preguntas almacenadas.
     * @return Lista de objetos Pregunta.
     */
    @Override
    public List<Pregunta> listarTodas() {
        return new ArrayList<>(preguntas);
    }

    /**
     * Actualiza el contenido de una pregunta existente según su ID.
     * @param enunciadoPregunta Pregunta con el contenido nuevo.
     */
    @Override
    public void actualizar(Pregunta enunciadoPregunta) {
        for (int i = 0; i < preguntas.size(); i++) {
            if (preguntas.get(i).getId() == enunciadoPregunta.getId()) {
                preguntas.set(i, enunciadoPregunta);
                return;
            }
        }
    }

    /**
     * Elimina una pregunta de la lista según su identificador.
     * @param id Identificador de la pregunta a eliminar.
     */
    @Override
    public void eliminar(int id) {
        Iterator<Pregunta> iter = preguntas.iterator();
        while (iter.hasNext()) {
            if (iter.next().getId() == id) {
                iter.remove();
                return;
            }
        }
    }
}

