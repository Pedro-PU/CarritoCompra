package ec.edu.ups.poo.clases.dao.impl;

import ec.edu.ups.poo.clases.dao.PreguntaDAO;
import ec.edu.ups.poo.clases.modelo.Pregunta;
import ec.edu.ups.poo.clases.util.MensajeInternacionalizacionHandler;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class PreguntaDAOArchivo implements PreguntaDAO {

    private static final int ENUNCIADO_LENGTH = 100; // bytes fijos para el texto
    private static final int RECORD_SIZE = 4 + ENUNCIADO_LENGTH; // 4 bytes int + 100 bytes texto

    private final File archivo;
    private final MensajeInternacionalizacionHandler mi;

    public PreguntaDAOArchivo(File archivo, MensajeInternacionalizacionHandler mi) {
        this.archivo = archivo;
        this.mi = mi;

        // Si el archivo no existe, creamos y llenamos con preguntas por defecto
        if (!archivo.exists()) {
            try {
                archivo.createNewFile();
                // Preguntas por defecto (guardamos las claves, no los textos)
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
                    // Guardamos la clave, no el texto internacionalizado aquí
                    crear(new Pregunta(i + 1, claves[i]));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private String formatearTexto(String texto) {
        byte[] bytes = texto.getBytes(StandardCharsets.UTF_8);
        if (bytes.length > ENUNCIADO_LENGTH) {
            // Si el texto es más largo que ENUNCIADO_LENGTH bytes, lo truncamos
            return new String(bytes, 0, ENUNCIADO_LENGTH, StandardCharsets.UTF_8);
        } else if (bytes.length < ENUNCIADO_LENGTH) {
            // Si es más corto, rellenamos con espacios para que ocupe ENUNCIADO_LENGTH
            StringBuilder sb = new StringBuilder(texto);
            while (sb.toString().getBytes(StandardCharsets.UTF_8).length < ENUNCIADO_LENGTH) {
                sb.append(' ');
            }
            return sb.toString();
        } else {
            return texto;
        }
    }

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

    @Override
    public void crear(Pregunta pregunta) {
        try (RandomAccessFile raf = new RandomAccessFile(archivo, "rw")) {
            raf.seek(raf.length());
            raf.writeInt(pregunta.getId());

            // Guardamos la clave (no traducida), la traducción se hace en getEnunciadoPregunta(mi)
            String textoFormateado = formatearTexto(pregunta.getEnunciadoPreguntaRaw());
            raf.write(textoFormateado.getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

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

    @Override
    public void eliminar(int id) {
        List<Pregunta> preguntas = listarTodas();
        Iterator<Pregunta> iter = preguntas.iterator();
        while (iter.hasNext()) {
            if (iter.next().getId() == id) {
                iter.remove();
                break;
            }
        }
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
}

