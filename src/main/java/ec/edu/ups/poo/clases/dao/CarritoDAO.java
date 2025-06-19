package ec.edu.ups.poo.clases.dao;

import ec.edu.ups.poo.clases.modelo.Carrito;

import java.util.List;

public interface CarritoDAO {
    void crear(Carrito carrito);

    Carrito buscarPorCodigo(int codigo);

    void actualizar(Carrito carrito);

    void eliminar(int codigo);

    List<Carrito> listarTodos();
}
