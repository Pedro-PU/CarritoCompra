package ec.edu.ups.poo.clases.vista;

import ec.edu.ups.poo.clases.controlador.ProductoController;
import ec.edu.ups.poo.clases.dao.ProductoDAO;
import ec.edu.ups.poo.clases.dao.impl.ProductoDAOMemoria;

public class Main {
    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {

                ProductoAnadirView productoView = new ProductoAnadirView();
                ProductoListaView productoListaView = new ProductoListaView();
                ProductoDAO productoDAO = new ProductoDAOMemoria();
                ProductoEditarView productoGestionView = new ProductoEditarView();
                ProductoEliminarView productoEliminarView = new ProductoEliminarView();
                new ProductoController(productoDAO, productoView, productoListaView, productoGestionView, productoEliminarView);
            }
        });
    }
}