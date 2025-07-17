package ec.edu.ups.poo.clases.dao.impl;

import ec.edu.ups.poo.clases.dao.PreguntaDAO;
import ec.edu.ups.poo.clases.modelo.Pregunta;
import ec.edu.ups.poo.clases.util.MensajeInternacionalizacionHandler;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class PreguntaDAOArchivo implements PreguntaDAO, Serializable {
    private List<Pregunta> preguntas;
    private File archivo;
    private  MensajeInternacionalizacionHandler mi;

    public PreguntaDAOArchivo(File archivo, MensajeInternacionalizacionHandler mi) {
        this.archivo = archivo;
        this.mi = mi;
        if (archivo.exists()) {
            preguntas = cargarPreguntas();
        } else {
            preguntas = new ArrayList<>();
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
            guardarPreguntas(preguntas);
        }
    }

    @Override
    public void crear(Pregunta enunciadoPregunta) {
        preguntas.add(enunciadoPregunta);
        guardarPreguntas(preguntas);
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
                guardarPreguntas(preguntas);
                return;
            }
        }
    }

    @Override
    public void eliminar(int id) {
        preguntas.removeIf(p -> p.getId() == id);
        guardarPreguntas(preguntas);
    }


    private List<Pregunta> cargarPreguntas() {
        List<Pregunta> lista = new ArrayList<>();
        try (DataInputStream dis = new DataInputStream(new FileInputStream(archivo))) {
            while (dis.available() > 0) {
                int id = dis.readInt();
                String texto = dis.readUTF();
                lista.add(new Pregunta(id, texto));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lista;
    }

    private void guardarPreguntas(List<Pregunta> preguntas) {
        try (DataOutputStream dos = new DataOutputStream(new FileOutputStream(archivo))) {
            for (Pregunta p : preguntas) {
                dos.writeInt(p.getId());
                dos.writeUTF(p.getEnunciadoPregunta(mi));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

