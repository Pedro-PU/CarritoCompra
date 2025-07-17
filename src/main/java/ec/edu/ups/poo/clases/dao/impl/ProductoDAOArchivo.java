package ec.edu.ups.poo.clases.dao.impl;

import ec.edu.ups.poo.clases.dao.ProductoDAO;
import ec.edu.ups.poo.clases.modelo.Producto;

import java.io.*;
import java.util.ArrayList;
import java.util.List;


public class ProductoDAOArchivo implements ProductoDAO {
    private static final int TAM_NOMBRE = 30; // 30 caracteres
    private static final int TAM_REGISTRO = 4 + (TAM_NOMBRE * 2) + 8; // int + String (UTF-16) + double
    private File archivo;

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

    private void escribirProducto(RandomAccessFile raf, Producto p) throws IOException {
        raf.writeInt(p.getCodigo());

        String nombre = p.getNombre();
        if (nombre.length() > TAM_NOMBRE) {
            nombre = nombre.substring(0, TAM_NOMBRE);
        }
        nombre = String.format("%-" + TAM_NOMBRE + "s", nombre); // Rellenar con espacios
        raf.writeChars(nombre); // 2 bytes por carácter

        raf.writeDouble(p.getPrecio());
    }

    private Producto leerProducto(RandomAccessFile raf) throws IOException {
        int codigo = raf.readInt();

        StringBuilder nombre = new StringBuilder();
        for (int i = 0; i < TAM_NOMBRE; i++) {
            nombre.append(raf.readChar());
        }

        double precio = raf.readDouble();
        return new Producto(codigo, nombre.toString().trim(), precio);
    }

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

    @Override
    public void crear(Producto producto) {
        try {
            if (buscarPorCodigo(producto.getCodigo()) != null) {
                System.out.println("Producto con código ya existente");
                return;
            }
            try (RandomAccessFile raf = new RandomAccessFile(archivo, "rw")) {
                raf.seek(raf.length());
                escribirProducto(raf, producto);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

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
}

