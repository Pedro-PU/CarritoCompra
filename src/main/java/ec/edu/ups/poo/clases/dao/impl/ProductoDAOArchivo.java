package ec.edu.ups.poo.clases.dao.impl;

import ec.edu.ups.poo.clases.dao.ProductoDAO;
import ec.edu.ups.poo.clases.modelo.Producto;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import java.io.*;
import java.util.*;

public class ProductoDAOArchivo implements ProductoDAO, Serializable {
    private List<Producto> productos;
    private File archivo;

    public ProductoDAOArchivo(File archivo) {
        this.archivo = archivo;
        if (archivo.exists()) {
            productos = cargarProductos();
        } else {
            productos = new ArrayList<>();
            // Productos por defecto
            crear(new Producto(1, "Prod A", 15));
            crear(new Producto(2, "Prod B", 25));
            crear(new Producto(3, "Prod C", 35));
            guardarProductos(productos);
        }
    }

    @Override
    public void crear(Producto producto) {
        productos.add(producto);
        guardarProductos(productos);
    }

    @Override
    public Producto buscarPorCodigo(int codigo) {
        for (Producto producto : productos) {
            if (producto.getCodigo() == codigo) {
                return producto;
            }
        }
        return null;
    }

    @Override
    public List<Producto> buscarPorNombre(String nombre) {
        List<Producto> encontrados = new ArrayList<>();
        for (Producto producto : productos) {
            if (producto.getNombre().equalsIgnoreCase(nombre)) {
                encontrados.add(producto);
            }
        }
        return encontrados;
    }

    @Override
    public void actualizar(Producto producto) {
        for (int i = 0; i < productos.size(); i++) {
            if (productos.get(i).getCodigo() == producto.getCodigo()) {
                productos.set(i, producto);
                guardarProductos(productos);
                return;
            }
        }
    }

    @Override
    public void eliminar(int codigo) {
        productos.removeIf(p -> p.getCodigo() == codigo);
        guardarProductos(productos);
    }

    @Override
    public List<Producto> listarTodos() {
        return productos;
    }

    private List<Producto> cargarProductos() {
        List<Producto> lista = new ArrayList<>();
        try (DataInputStream dis = new DataInputStream(new FileInputStream(archivo))) {
            while (dis.available() > 0) {
                int codigo = dis.readInt();
                String nombre = dis.readUTF();
                double precio = dis.readDouble();
                lista.add(new Producto(codigo, nombre, precio));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lista;
    }

    private void guardarProductos(List<Producto> productos) {
        try (DataOutputStream dos = new DataOutputStream(new FileOutputStream(archivo))) {
            for (Producto p : productos) {
                dos.writeInt(p.getCodigo());
                dos.writeUTF(p.getNombre());
                dos.writeDouble(p.getPrecio());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

