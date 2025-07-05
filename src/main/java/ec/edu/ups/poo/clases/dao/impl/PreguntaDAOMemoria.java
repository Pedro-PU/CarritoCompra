package ec.edu.ups.poo.clases.dao.impl;

import ec.edu.ups.poo.clases.dao.PreguntaDAO;
import ec.edu.ups.poo.clases.modelo.Pregunta;
import ec.edu.ups.poo.clases.util.MensajeInternacionalizacionHandler;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class PreguntaDAOMemoria implements PreguntaDAO {
    private final List<Pregunta> preguntas = new ArrayList<>();

    public PreguntaDAOMemoria(MensajeInternacionalizacionHandler mi) {
        // Preguntas por defecto
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
    @Override
    public void crear(Pregunta enunciadoPregunta) {
        preguntas.add(enunciadoPregunta);
    }

    @Override
    public Pregunta buscarPorId(int id) {
        for (Pregunta p : preguntas) {
            if (p.getId() == id) {
                return p;
            }
        }
        return null;
    }

    @Override
    public List<Pregunta> listarTodas() {
        return new ArrayList<>(preguntas);
    }

    @Override
    public void actualizar(Pregunta enunciadoPregunta) {
        for (int i = 0; i < preguntas.size(); i++) {
            if (preguntas.get(i).getId() == enunciadoPregunta.getId()) {
                preguntas.set(i, enunciadoPregunta);
                return;
            }
        }
    }

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
