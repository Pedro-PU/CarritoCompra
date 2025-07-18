package ec.edu.ups.poo.clases.dao;

import ec.edu.ups.poo.clases.modelo.Carrito;
import ec.edu.ups.poo.clases.modelo.Usuario;

import java.util.List;

/**
 * Interfaz que define las operaciones de acceso a datos (DAO) para la entidad Carrito.
 * Permite realizar acciones CRUD y consultas específicas sobre los carritos almacenados.
 */
public interface CarritoDAO {

    /**
     * Crea y almacena un nuevo carrito en el sistema.
     * @param carrito Carrito a persistir.
     */
    void crear(Carrito carrito);

    /**
     * Busca un carrito por su código único.
     * @param codigo Identificador del carrito.
     * @return Carrito correspondiente al código; null si no se encuentra.
     */
    Carrito buscarPorCodigo(int codigo);

    /**
     * Actualiza la información de un carrito existente.
     * @param carrito Carrito con datos modificados.
     */
    void actualizar(Carrito carrito);

    /**
     * Elimina un carrito del sistema usando su código.
     * @param codigo Identificador del carrito a eliminar.
     */
    void eliminar(int codigo);

    /**
     * Lista todos los carritos almacenados en el sistema.
     * @return Lista de objetos Carrito.
     */
    List<Carrito> listarTodos();

    /**
     * Busca los carritos asociados a un usuario específico.
     * @param usuario Usuario propietario de los carritos.
     * @return Lista de carritos pertenecientes al usuario.
     */
    List<Carrito> buscarPorUsuario(Usuario usuario);
}
