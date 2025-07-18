package ec.edu.ups.poo.clases.dao.impl;

import ec.edu.ups.poo.clases.dao.PreguntaDAO;
import ec.edu.ups.poo.clases.modelo.Pregunta;
import ec.edu.ups.poo.clases.util.MensajeInternacionalizacionHandler;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
/**
 * Implementación del DAO de Pregunta que persiste los datos en archivo binario fijo.
 * Utiliza RandomAccessFile para almacenamiento eficiente de preguntas con enunciados internacionalizables.
 */
public class PreguntaDAOArchivo implements PreguntaDAO {

    private static final int ENUNCIADO_LENGTH = 100; // Tamaño fijo en bytes para el texto
    private static final int RECORD_SIZE = 4 + ENUNCIADO_LENGTH; // ID (int) + texto

    private final File archivo;
    private final MensajeInternacionalizacionHandler mi;

    /**
     * Constructor que inicializa el DAO con el archivo de preguntas y el manejador de internacionalización.
     * Si el archivo no existe, crea uno nuevo y lo llena con preguntas por defecto.
     * @param archivo Archivo binario donde se guardan las preguntas.
     * @param mi Manejador para acceder a traducciones de los enunciados.
     */
    public PreguntaDAOArchivo(File archivo, MensajeInternacionalizacionHandler mi) {
        this.archivo = archivo;
        this.mi = mi;

        if (!archivo.exists()) {
            try {
                archivo.createNewFile();
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
                    crear(new Pregunta(i + 1, claves[i]));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Añade una nueva pregunta al archivo, con su ID y clave del enunciado.
     * @param pregunta Pregunta a registrar.
     */
    @Override
    public void crear(Pregunta pregunta) {
        try (RandomAccessFile raf = new RandomAccessFile(archivo, "rw")) {
            raf.seek(raf.length());
            raf.writeInt(pregunta.getId());
            String textoFormateado = formatearTexto(pregunta.getEnunciadoPreguntaRaw());
            raf.write(textoFormateado.getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Busca una pregunta por su ID en el archivo.
     * @param id Identificador numérico de la pregunta.
     * @return Pregunta encontrada; null si no existe.
     */
    @Override
    public Pregunta buscarPorId(int id) {
        try (RandomAccessFile raf = new RandomAccessFile(archivo, "r")) {
            long pos = buscarPosicionPorId(id, raf);
            if (pos >= 0) {
                raf.seek(pos);
                int actualId = raf.readInt();
                byte[] textoBytes = new byte[ENUNCIADO_LENGTH];
                raf.readFully(textoBytes);
                String clave = new String(textoBytes, StandardCharsets.UTF_8).trim();
                return new Pregunta(actualId, clave);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Devuelve todas las preguntas registradas en el archivo.
     * @return Lista de objetos Pregunta.
     */
    @Override
    public List<Pregunta> listarTodas() {
        List<Pregunta> lista = new ArrayList<>();
        try (RandomAccessFile raf = new RandomAccessFile(archivo, "r")) {
            raf.seek(0);
            while (raf.getFilePointer() < raf.length()) {
                int id = raf.readInt();
                byte[] textoBytes = new byte[ENUNCIADO_LENGTH];
                raf.readFully(textoBytes);
                String clave = new String(textoBytes, StandardCharsets.UTF_8).trim();
                lista.add(new Pregunta(id, clave));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lista;
    }

    /**
     * Actualiza el enunciado de una pregunta existente.
     * @param pregunta Pregunta con los datos modificados.
     */
    @Override
    public void actualizar(Pregunta pregunta) {
        try (RandomAccessFile raf = new RandomAccessFile(archivo, "rw")) {
            long pos = buscarPosicionPorId(pregunta.getId(), raf);
            if (pos >= 0) {
                raf.seek(pos);
                raf.writeInt(pregunta.getId());
                String textoFormateado = formatearTexto(pregunta.getEnunciadoPreguntaRaw());
                raf.write(textoFormateado.getBytes(StandardCharsets.UTF_8));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Elimina una pregunta del archivo reescribiendo todos los registros menos el que coincide con el ID.
     * @param id ID de la pregunta a eliminar.
     */
    @Override
    public void eliminar(int id) {
        List<Pregunta> preguntas = listarTodas();
        preguntas.removeIf(p -> p.getId() == id);
        try (RandomAccessFile raf = new RandomAccessFile(archivo, "rw")) {
            raf.setLength(0);
            for (Pregunta p : preguntas) {
                raf.writeInt(p.getId());
                String textoFormateado = formatearTexto(p.getEnunciadoPreguntaRaw());
                raf.write(textoFormateado.getBytes(StandardCharsets.UTF_8));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Busca la posición en el archivo donde se encuentra la pregunta con el ID dado.
     * @param id ID a buscar.
     * @param raf Archivo de acceso aleatorio.
     * @return Posición del registro o -1 si no se encuentra.
     */
    private long buscarPosicionPorId(int id, RandomAccessFile raf) throws IOException {
        raf.seek(0);
        while (raf.getFilePointer() < raf.length()) {
            long pos = raf.getFilePointer();
            int actualId = raf.readInt();
            raf.skipBytes(ENUNCIADO_LENGTH);
            if (actualId == id) {
                return pos;
            }
        }
        return -1;
    }

    /**
     * Formatea el texto del enunciado para ajustarse al tamaño definido.
     * Trunca si excede; rellena con espacios si es más corto.
     * @param texto Texto original.
     * @return Cadena con longitud fija.
     */
    private String formatearTexto(String texto) {
        byte[] bytes = texto.getBytes(StandardCharsets.UTF_8);
        if (bytes.length > ENUNCIADO_LENGTH) {
            return new String(bytes, 0, ENUNCIADO_LENGTH, StandardCharsets.UTF_8);
        } else {
            StringBuilder sb = new StringBuilder(texto);
            while (sb.toString().getBytes(StandardCharsets.UTF_8).length < ENUNCIADO_LENGTH) {
                sb.append(' ');
            }
            return sb.toString();
        }
    }
}


