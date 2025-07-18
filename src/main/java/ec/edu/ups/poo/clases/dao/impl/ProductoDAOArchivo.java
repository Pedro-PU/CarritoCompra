package ec.edu.ups.poo.clases.dao.impl;

import ec.edu.ups.poo.clases.dao.ProductoDAO;
import ec.edu.ups.poo.clases.modelo.Producto;

import java.io.*;
import java.util.ArrayList;
import java.util.List;


/**
 * Implementación del DAO de productos que persiste los datos en un archivo binario mediante RandomAccessFile.
 * Cada producto se guarda con un código, nombre fijo de 30 caracteres y precio.
 */
public class ProductoDAOArchivo implements ProductoDAO {

    private static final int TAM_NOMBRE = 30; // Longitud fija del nombre en caracteres
    private static final int TAM_REGISTRO = 4 + (TAM_NOMBRE * 2) + 8; // int código + nombre UTF-16 + double precio
    private File archivo;

    /**
     * Constructor que inicializa el DAO con el archivo de productos.
     * Si el archivo no existe, se crea y carga con productos de ejemplo.
     * @param archivo Archivo físico para la persistencia.
     */
    public ProductoDAOArchivo(File archivo) {
        this.archivo = archivo;
        try {
            if (!archivo.exists()) {
                archivo.createNewFile();
                crear(new Producto(1, "Prod A", 15));
                crear(new Producto(2, "Prod B", 25));
                crear(new Producto(3, "Prod C", 35));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Registra un nuevo producto en el archivo si su código no existe aún.
     * @param producto Producto a agregar.
     */
    @Override
    public void crear(Producto producto) {
        try {
            if (buscarPorCodigo(producto.getCodigo()) != null) return;
            try (RandomAccessFile raf = new RandomAccessFile(archivo, "rw")) {
                raf.seek(raf.length());
                escribirProducto(raf, producto);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Busca un producto en el archivo por su código.
     * @param codigo Código del producto.
     * @return Producto encontrado; null si no existe.
     */
    @Override
    public Producto buscarPorCodigo(int codigo) {
        try (RandomAccessFile raf = new RandomAccessFile(archivo, "r")) {
            long numRegistros = raf.length() / TAM_REGISTRO;
            for (int i = 0; i < numRegistros; i++) {
                raf.seek(i * TAM_REGISTRO);
                Producto p = leerProducto(raf);
                if (p.getCodigo() == codigo) {
                    return p;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Busca productos cuyo nombre coincide con el texto proporcionado (ignorando mayúsculas).
     * @param nombre Nombre del producto a buscar.
     * @return Lista de productos encontrados.
     */
    @Override
    public List<Producto> buscarPorNombre(String nombre) {
        List<Producto> encontrados = new ArrayList<>();
        try (RandomAccessFile raf = new RandomAccessFile(archivo, "r")) {
            long numRegistros = raf.length() / TAM_REGISTRO;
            for (int i = 0; i < numRegistros; i++) {
                raf.seek(i * TAM_REGISTRO);
                Producto p = leerProducto(raf);
                if (p.getNombre().equalsIgnoreCase(nombre)) {
                    encontrados.add(p);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return encontrados;
    }

    /**
     * Actualiza un producto existente escribiendo sobre su registro en el archivo.
     * @param producto Producto con los nuevos datos.
     */
    @Override
    public void actualizar(Producto producto) {
        try (RandomAccessFile raf = new RandomAccessFile(archivo, "rw")) {
            long pos = buscarPosicionPorCodigo(producto.getCodigo());
            if (pos != -1) {
                raf.seek(pos);
                escribirProducto(raf, producto);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Elimina un producto del archivo y reescribe todos los demás.
     * @param codigo Código del producto a eliminar.
     */
    @Override
    public void eliminar(int codigo) {
        List<Producto> productos = listarTodos();
        productos.removeIf(p -> p.getCodigo() == codigo);
        try (RandomAccessFile raf = new RandomAccessFile(archivo, "rw")) {
            raf.setLength(0);
            for (Producto p : productos) {
                escribirProducto(raf, p);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Devuelve todos los productos almacenados en el archivo.
     * @return Lista de productos.
     */
    @Override
    public List<Producto> listarTodos() {
        List<Producto> lista = new ArrayList<>();
        try (RandomAccessFile raf = new RandomAccessFile(archivo, "r")) {
            long numRegistros = raf.length() / TAM_REGISTRO;
            for (int i = 0; i < numRegistros; i++) {
                raf.seek(i * TAM_REGISTRO);
                Producto p = leerProducto(raf);
                lista.add(p);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lista;
    }

    /**
     * Escribe los datos de un producto en la posición actual del archivo.
     * @param raf Archivo binario.
     * @param p Producto a registrar.
     */
    private void escribirProducto(RandomAccessFile raf, Producto p) throws IOException {
        raf.writeInt(p.getCodigo());

        String nombre = p.getNombre();
        if (nombre.length() > TAM_NOMBRE) {
            nombre = nombre.substring(0, TAM_NOMBRE);
        }
        nombre = String.format("%-" + TAM_NOMBRE + "s", nombre);
        raf.writeChars(nombre); // Cada carácter ocupa 2 bytes (UTF-16)

        raf.writeDouble(p.getPrecio());
    }

    /**
     * Lee los datos de un producto desde la posición actual del archivo.
     * @param raf Archivo binario.
     * @return Producto leído.
     */
    private Producto leerProducto(RandomAccessFile raf) throws IOException {
        int codigo = raf.readInt();
        StringBuilder nombre = new StringBuilder();
        for (int i = 0; i < TAM_NOMBRE; i++) {
            nombre.append(raf.readChar());
        }
        double precio = raf.readDouble();
        return new Producto(codigo, nombre.toString().trim(), precio);
    }

    /**
     * Busca la posición en bytes del registro que contiene el producto con el código especificado.
     * @param codigo Código del producto.
     * @return Posición del registro; -1 si no se encuentra.
     */
    private long buscarPosicionPorCodigo(int codigo) throws IOException {
        try (RandomAccessFile raf = new RandomAccessFile(archivo, "r")) {
            long numRegistros = raf.length() / TAM_REGISTRO;
            for (int i = 0; i < numRegistros; i++) {
                raf.seek(i * TAM_REGISTRO);
                int cod = raf.readInt();
                if (cod == codigo) {
                    return i * TAM_REGISTRO;
                }
            }
        }
        return -1;
    }
}


