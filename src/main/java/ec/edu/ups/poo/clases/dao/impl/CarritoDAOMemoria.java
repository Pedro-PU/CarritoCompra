package ec.edu.ups.poo.clases.dao.impl;

import ec.edu.ups.poo.clases.dao.CarritoDAO;
import ec.edu.ups.poo.clases.modelo.Carrito;
import ec.edu.ups.poo.clases.modelo.Usuario;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Implementación en memoria del DAO de carritos.
 * Almacena y gestiona los carritos sin persistencia en disco, útil para pruebas o sesiones temporales.
 */
public class CarritoDAOMemoria implements CarritoDAO {

    private List<Carrito> carritos;
    private int contCodigo = 1;

    /**
     * Constructor que inicializa la lista de carritos vacía.
     */
    public CarritoDAOMemoria() {
        carritos = new ArrayList<>();
    }

    /**
     * Crea un nuevo carrito, asignándole un código único y guardando una copia en memoria.
     * @param carrito Carrito a registrar.
     */
    @Override
    public void crear(Carrito carrito) {
        carrito.setCodigo(contCodigo++);
        Carrito copia = carrito.copiar();
        carritos.add(copia);
        System.out.println("Guardado carrito con código " + copia.getCodigo() +
                " con " + carrito.obtenerItems().size() + " items.");
    }

    /**
     * Busca un carrito por su código entre los almacenados en memoria.
     * @param codigo Código único del carrito.
     * @return Carrito encontrado; null si no existe.
     */
    @Override
    public Carrito buscarPorCodigo(int codigo) {
        for (Carrito carrito : carritos) {
            if (carrito.getCodigo() == codigo) {
                return carrito;
            }
        }
        return null;
    }

    /**
     * Actualiza el carrito en la lista si el código coincide.
     * @param carrito Carrito con datos modificados.
     */
    @Override
    public void actualizar(Carrito carrito) {
        for (int i = 0; i < carritos.size(); i++) {
            if (carritos.get(i).getCodigo() == carrito.getCodigo()) {
                carritos.set(i, carrito);
            }
        }
    }

    /**
     * Elimina un carrito de la lista según su código.
     * @param codigo Código del carrito a eliminar.
     */
    @Override
    public void eliminar(int codigo) {
        Iterator<Carrito> iterator = carritos.iterator();
        while (iterator.hasNext()) {
            Carrito carrito = iterator.next();
            if (carrito.getCodigo() == codigo) {
                iterator.remove();
            }
        }
    }

    /**
     * Devuelve la lista completa de carritos en memoria, con log informativo por consola.
     * @return Lista de carritos.
     */
    @Override
    public List<Carrito> listarTodos() {
        for (Carrito c : carritos) {
            System.out.println("Listado - Carrito " + c.getCodigo() +
                    " con " + c.obtenerItems().size() + " productos.");
        }
        return carritos;
    }

    /**
     * Filtra los carritos según el nombre de usuario asociado.
     * @param usuario Usuario cuyos carritos se desean buscar.
     * @return Lista de carritos del usuario.
     */
    @Override
    public List<Carrito> buscarPorUsuario(Usuario usuario) {
        List<Carrito> resultado = new ArrayList<>();
        for (Carrito carrito : carritos) {
            if (carrito.getUsuario().getUsername().equals(usuario.getUsername())) {
                resultado.add(carrito);
            }
        }
        return resultado;
    }
}

