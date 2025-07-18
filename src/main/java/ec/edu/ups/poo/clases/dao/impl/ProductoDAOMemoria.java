package ec.edu.ups.poo.clases.dao.impl;
import ec.edu.ups.poo.clases.modelo.Producto;
import ec.edu.ups.poo.clases.dao.ProductoDAO;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Implementación en memoria del DAO de productos.
 * Los productos se almacenan en una lista interna, útil para pruebas unitarias o aplicaciones temporales sin persistencia.
 */
public class ProductoDAOMemoria implements ProductoDAO {

    private List<Producto> productos;

    /**
     * Constructor que inicializa la lista con algunos productos de ejemplo.
     */
    public ProductoDAOMemoria() {
        productos = new ArrayList<>();
        crear(new Producto(1, "Prod A", 15));
        crear(new Producto(2, "Prod B", 25));
        crear(new Producto(3, "Prod C", 35));
    }

    /**
     * Agrega un nuevo producto a la lista en memoria.
     * @param producto Producto a registrar.
     */
    @Override
    public void crear(Producto producto) {
        productos.add(producto);
    }

    /**
     * Busca un producto por su código único.
     * @param codigo Código del producto.
     * @return Producto encontrado o null si no existe.
     */
    @Override
    public Producto buscarPorCodigo(int codigo) {
        for (Producto producto : productos) {
            if (producto.getCodigo() == codigo) {
                return producto;
            }
        }
        return null;
    }

    /**
     * Busca productos cuyo nombre coincide ignorando mayúsculas.
     * @param nombre Nombre del producto.
     * @return Lista de productos que coinciden.
     */
    @Override
    public List<Producto> buscarPorNombre(String nombre) {
        List<Producto> productosEncontrados = new ArrayList<>();
        for (Producto producto : productos) {
            if (producto.getNombre().equalsIgnoreCase(nombre)) {
                productosEncontrados.add(producto);
            }
        }
        return productosEncontrados;
    }

    /**
     * Actualiza los datos de un producto según su código.
     * @param producto Producto con información nueva.
     */
    @Override
    public void actualizar(Producto producto) {
        for (int i = 0; i < productos.size(); i++) {
            if (productos.get(i).getCodigo() == producto.getCodigo()) {
                productos.set(i, producto);
            }
        }
    }

    /**
     * Elimina un producto de la lista basado en su código.
     * @param codigo Código del producto a eliminar.
     */
    @Override
    public void eliminar(int codigo) {
        Iterator<Producto> iterator = productos.iterator();
        while (iterator.hasNext()) {
            Producto producto = iterator.next();
            if (producto.getCodigo() == codigo) {
                iterator.remove();
            }
        }
    }

    /**
     * Retorna todos los productos registrados en memoria.
     * @return Lista de productos actuales.
     */
    @Override
    public List<Producto> listarTodos() {
        return productos;
    }
}
