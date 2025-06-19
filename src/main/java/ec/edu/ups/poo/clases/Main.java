package ec.edu.ups.poo.clases;

import ec.edu.ups.poo.clases.controlador.CarritoController;
import ec.edu.ups.poo.clases.controlador.ProductoController;
import ec.edu.ups.poo.clases.dao.CarritoDAO;
import ec.edu.ups.poo.clases.dao.ProductoDAO;
import ec.edu.ups.poo.clases.dao.impl.CarritoDAOMemoria;
import ec.edu.ups.poo.clases.dao.impl.ProductoDAOMemoria;
import ec.edu.ups.poo.clases.vista.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main {
    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(() -> {
            PrincipalView principalView = new PrincipalView();
            ProductoDAO productoDAO = new ProductoDAOMemoria();
            CarritoDAO carritoDAO = new CarritoDAOMemoria();
            CarritoAnadirView carritoAnadirView = new CarritoAnadirView();

            ProductoAnadirView productoAnadirView = new ProductoAnadirView();
            ProductoEditarView productoEditarView = new ProductoEditarView();
            ProductoEliminarView productoEliminarView = new ProductoEliminarView();
            ProductoListaView productoListaView = new ProductoListaView();
            /*
            ProductoController productoController = new ProductoController(productoDAO);
            productoController.setProductoAnadirView(productoAnadirView);
            productoController.setProductoEditarView(productoEditarView);
            productoController.setProductoEliminarView(productoEliminarView);
            productoController.setProductoListaView(productoListaView);
            */
            ProductoController productoController = new ProductoController(productoDAO, productoAnadirView,
                    productoListaView, productoEditarView, productoEliminarView, carritoAnadirView);

            CarritoController carritoController = new CarritoController(carritoAnadirView, productoController, carritoDAO);

            principalView.getMenuItemCrearCarrito().addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if(!carritoAnadirView.isVisible()) {
                        carritoAnadirView.setVisible(true);
                        principalView.getjDesktopPane().add(carritoAnadirView);
                    }
                }
            });
            principalView.getMenuItemCrearProducto().addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if(!productoAnadirView.isVisible()) {
                        productoAnadirView.setVisible(true);
                        principalView.getjDesktopPane().add(productoAnadirView);
                    }
                }
            });
            principalView.getMenuItemActualizarProducto().addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if(!productoEditarView.isVisible()) {
                        productoEditarView.setVisible(true);
                        principalView.getjDesktopPane().add(productoEditarView);
                    }
                }
            });
            principalView.getMenuItemEliminarProducto().addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if(!productoEliminarView.isVisible()) {
                        productoEliminarView.setVisible(true);
                        principalView.getjDesktopPane().add(productoEliminarView);
                    }
                }
            });
            principalView.getMenuItemBuscarProducto().addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if(!productoListaView.isVisible()) {
                        productoListaView.setVisible(true);
                        principalView.getjDesktopPane().add(productoListaView);
                    }
                }
            });
        });
    }
}