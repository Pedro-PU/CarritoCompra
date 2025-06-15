package ec.edu.ups.poo.clases.controlador;

import ec.edu.ups.poo.clases.dao.ProductoDAO;
import ec.edu.ups.poo.clases.modelo.Producto;
import ec.edu.ups.poo.clases.vista.ProductoAnadirView;
import ec.edu.ups.poo.clases.vista.ProductoEditarView;
import ec.edu.ups.poo.clases.vista.ProductoListaView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;


public class ProductoController {
    private final ProductoAnadirView productoAnadirView;
    private final ProductoListaView productoListaView;
    private final ProductoDAO productoDAO;
    private final ProductoEditarView productoGestionView;


    public ProductoController(ProductoDAO productoDAO,
                              ProductoAnadirView productoAnadirView,
                              ProductoListaView productoListaView,
                              ProductoEditarView productoGestionView) {
        this.productoDAO = productoDAO;
        this.productoAnadirView = productoAnadirView;
        this.productoListaView = productoListaView;
        this.productoGestionView = productoGestionView;
        configurarEventos();
    }

    private void configurarEventos() {
        productoAnadirView.getBtnAceptar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guardarProducto();
            }
        });

        productoListaView.getBtnBuscar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buscarProducto();
            }
        });

        productoListaView.getBtnListar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                listarProductos();
            }
        });
        // Buscar producto por código
        productoGestionView.getBtnBuscar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buscarProductoGestion();
            }
        });

        // Seleccionar fila en la tabla
        productoGestionView.getTblProductos().getSelectionModel().addListSelectionListener(e -> {
            int fila = productoGestionView.getTblProductos().getSelectedRow();
            if (fila != -1) {
                String nombre = (String) productoGestionView.getTblProductos().getValueAt(fila, 1);
                double precio = (double) productoGestionView.getTblProductos().getValueAt(fila, 2);

                productoGestionView.getTxtNombre().setText(nombre);
                productoGestionView.getTxtPrecio().setText(String.valueOf(precio));
            }
        });

        // Botón actualizar producto
        productoGestionView.getBtnActualizar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actualizarProducto();
            }
        });

        // Botón eliminar producto
        productoGestionView.getBtnEliminar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                eliminarProducto();
            }
        });
    }

    private void guardarProducto() {
        int codigo = Integer.parseInt(productoAnadirView.getTxtCodigo().getText());
        String nombre = productoAnadirView.getTxtNombre().getText();
        double precio = Double.parseDouble(productoAnadirView.getTxtPrecio().getText());

        productoDAO.crear(new Producto(codigo, nombre, precio));
        productoAnadirView.mostrarMensaje("Producto guardado correctamente");
        productoAnadirView.limpiarCampos();
        productoAnadirView.mostrarProductos(productoDAO.listarTodos());
    }

    private void buscarProducto() {
        String nombre = productoListaView.getTxtBuscar().getText();

        List<Producto> productosEncontrados = productoDAO.buscarPorNombre(nombre);
        productoListaView.cargarDatos(productosEncontrados);
    }

    private void listarProductos() {
        List<Producto> productos = productoDAO.listarTodos();
        productoListaView.cargarDatos(productos);
    }

    private void buscarProductoGestion() {
        String txtCod = productoGestionView.getTxtBuscar().getText();
        if (!txtCod.isEmpty()) {
            int codigo = Integer.parseInt(txtCod);
            Producto producto = productoDAO.buscarPorCodigo(codigo);
            if (producto != null) {
                productoGestionView.cargarDatos(List.of(producto));
            } else {
                productoGestionView.mostrarMensaje("Producto no encontrado");
                productoGestionView.cargarDatos(List.of());
                productoGestionView.limpiarCampos();
            }
        } else {
            productoGestionView.mostrarMensaje("Ingresa un código para buscar");
        }
    }

    private void actualizarProducto() {
        int codigo = productoGestionView.getCodigoProductoSeleccionado();
        String nombre = productoGestionView.getTxtNombre().getText();
        String txtPrecio = productoGestionView.getTxtPrecio().getText();

        if (codigo != -1 && !nombre.isEmpty() && !txtPrecio.isEmpty()) {
            Producto producto = productoDAO.buscarPorCodigo(codigo);
            if (producto != null) {
                double precio = Double.parseDouble(txtPrecio);
                producto.setNombre(nombre);
                producto.setPrecio(precio);
                productoDAO.actualizar(producto);
                productoGestionView.mostrarMensaje("Producto actualizado correctamente");
                productoGestionView.cargarDatos(List.of(producto));
            }
        } else {
            productoGestionView.mostrarMensaje("Selecciona un producto de la tabla y completa los datos");
        }
    }

    private void eliminarProducto() {
        int codigo = productoGestionView.getCodigoProductoSeleccionado();
        if (codigo != -1) {
            productoDAO.eliminar(codigo);
            productoGestionView.mostrarMensaje("Producto eliminado correctamente");
            productoGestionView.cargarDatos(List.of());
            productoGestionView.limpiarCampos();
        } else {
            productoGestionView.mostrarMensaje("Selecciona un producto para eliminar");
        }
    }


}
