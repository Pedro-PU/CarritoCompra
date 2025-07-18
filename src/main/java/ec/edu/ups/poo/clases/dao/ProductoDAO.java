package ec.edu.ups.poo.clases.dao;

import ec.edu.ups.poo.clases.modelo.Producto;

import java.util.List;

/**
 * Interfaz DAO que define las operaciones de persistencia para la entidad Producto.
 * Permite gestionar productos en el sistema mediante acciones CRUD y búsquedas especializadas.
 */
public interface ProductoDAO {

    /**
     * Registra un nuevo producto en el sistema.
     * @param producto Producto a crear.
     */
    void crear(Producto producto);

    /**
     * Busca un producto utilizando su código único.
     * @param codigo Código identificador del producto.
     * @return Producto encontrado; null si no existe.
     */
    Producto buscarPorCodigo(int codigo);

    /**
     * Busca productos cuyo nombre coincida parcial o totalmente con el parámetro dado.
     * @param nombre Nombre o fragmento de nombre a buscar.
     * @return Lista de productos que coinciden.
     */
    List<Producto> buscarPorNombre(String nombre);

    /**
     * Actualiza los datos de un producto existente.
     * @param producto Producto con la información modificada.
     */
    void actualizar(Producto producto);

    /**
     * Elimina un producto del sistema por su código.
     * @param codigo Código del producto a eliminar.
     */
    void eliminar(int codigo);

    /**
     * Obtiene todos los productos registrados en el sistema.
     * @return Lista completa de productos.
     */
    List<Producto> listarTodos();
}
