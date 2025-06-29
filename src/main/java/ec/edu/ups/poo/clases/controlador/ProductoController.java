package ec.edu.ups.poo.clases.controlador;

import ec.edu.ups.poo.clases.dao.ProductoDAO;
import ec.edu.ups.poo.clases.modelo.Producto;
import ec.edu.ups.poo.clases.util.MensajeInternacionalizacionHandler;
import ec.edu.ups.poo.clases.vista.carrito.CarritoAnadirView;
import ec.edu.ups.poo.clases.vista.producto.ProductoAnadirView;
import ec.edu.ups.poo.clases.vista.producto.ProductoEditarView;
import ec.edu.ups.poo.clases.vista.producto.ProductoEliminarView;
import ec.edu.ups.poo.clases.vista.producto.ProductoListaView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;


public class ProductoController {
    private final ProductoAnadirView productoAnadirView;
    private final ProductoListaView productoListaView;
    private final ProductoDAO productoDAO;
    private final ProductoEditarView productoEditarView;
    private final ProductoEliminarView productoEliminarView;
    private final CarritoAnadirView carritoAnadirView;
    private final MensajeInternacionalizacionHandler mi;


    public ProductoController(ProductoDAO productoDAO,
                              ProductoAnadirView productoAnadirView,
                              ProductoListaView productoListaView,
                              ProductoEditarView productoGestionView, ProductoEliminarView productoEliminarView,
                              CarritoAnadirView carritoAnadirView, MensajeInternacionalizacionHandler mi) {
        this.productoDAO = productoDAO;
        this.productoAnadirView = productoAnadirView;
        this.productoListaView = productoListaView;
        this.productoEditarView = productoGestionView;
        this.productoEliminarView = productoEliminarView;
        this.carritoAnadirView = carritoAnadirView;
        this.mi = mi;
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

        productoEditarView.getBtnBuscar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buscarProductoEdicion();
            }
        });

        productoEditarView.getBtnActualizar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actualizarProducto();
            }
        });

        productoEliminarView.getBtnEliminar().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                eliminarProducto();
            }
        });
        productoEliminarView.getBtnBuscar().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                buscarProductoEliminar();
            }
        });
        carritoAnadirView.getBtnBuscar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buscarProductoCarrito();
            }
        });
    }

    private void guardarProducto() {
        int codigo = Integer.parseInt(productoAnadirView.getTxtCodigo().getText());
        String nombre = productoAnadirView.getTxtNombre().getText();
        double precio = Double.parseDouble(productoAnadirView.getTxtPrecio().getText());

        if (productoDAO.buscarPorCodigo(codigo) != null) {
            productoAnadirView.mostrarMensaje(mi.get("producto.mensaje.error.codigo.existe"));
            return;
        }
        productoDAO.crear(new Producto(codigo, nombre, precio));
        productoAnadirView.mostrarMensaje(mi.get("producto.mensaje.guardado.correctamente"));
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



    private void actualizarProducto() {
        String txtCod = productoEditarView.getTxtBuscar().getText();
        int codigo = Integer.parseInt(txtCod);
        String nombre = productoEditarView.getTxtNombre().getText();
        String txtPrecio = productoEditarView.getTxtPrecio().getText();
        if (codigo != -1 ) {
            Producto producto = productoDAO.buscarPorCodigo(codigo);
            if (producto != null) {
                boolean confirmado = productoEditarView.mostrarMensajePregunta(mi.get("producto.mensaje.actualizar.pregunta"));
                if (confirmado) {
                    double precio = Double.parseDouble(txtPrecio);
                    producto.setNombre(nombre);
                    producto.setPrecio(precio);
                    productoDAO.actualizar(producto);
                    productoEditarView.mostrarMensaje(mi.get("producto.mensaje.actualizado.correctamente"));
                }else{
                    productoEliminarView.mostrarMensaje(mi.get("producto.mensaje.actualizacion.cancelada"));
                }
            }
        } else {
            productoEditarView.mostrarMensaje(mi.get("producto.mensaje.error.codigo.invalido"));
        }
    }

    private void eliminarProducto() {
        String text_codigo = productoEliminarView.getTxtBuscar().getText();
        int codigo = Integer.parseInt(text_codigo);
        String nombre = productoEliminarView.getTxtNombre().getText();
        String txtPrecio = productoEliminarView.getTxtPrecio().getText();
        if(codigo != -1 && !nombre.isEmpty() && !txtPrecio.isEmpty()){
            Producto producto = productoDAO.buscarPorCodigo(codigo);
            if (producto != null) {
                boolean confirmado = productoEliminarView.mostrarMensajePregunta(mi.get("producto.mensaje.eliminar.pregunta"));
                if (confirmado) {
                    productoDAO.eliminar(codigo);
                    productoEliminarView.mostrarMensaje(mi.get("producto.mensaje.eliminado.correctamente"));
                    productoEliminarView.limpiarCampos();
                } else {
                    productoEliminarView.mostrarMensaje(mi.get("producto.mensaje.eliminacion.cancelada"));
                }
            }
        }else{
            productoEliminarView.mostrarMensaje(mi.get("producto.mensaje.error.codigo.invalido"));
        }
    }
    private void buscarProductoEliminar() {
        String txtCod = productoEliminarView.getTxtBuscar().getText();
        if (!txtCod.isEmpty()) {
            int codigo = Integer.parseInt(txtCod);
            Producto producto = productoDAO.buscarPorCodigo(codigo);
            if (producto != null) {
                productoEliminarView.getTxtNombre().setText(producto.getNombre());
                productoEliminarView.getTxtPrecio().setText(String.valueOf(producto.getPrecio()));
            } else {
                productoEliminarView.mostrarMensaje(mi.get("producto.mensaje.no.encontrado"));
                productoEliminarView.limpiarCampos();
            }
        } else {
            productoEliminarView.mostrarMensaje(mi.get("producto.mensaje.error.ingresar.codigo"));
        }
    }
    private void buscarProductoEdicion() {
        String txtCod = productoEditarView.getTxtBuscar().getText();
        if (!txtCod.isEmpty()) {
            int codigo = Integer.parseInt(txtCod);
            Producto producto = productoDAO.buscarPorCodigo(codigo);
            if (producto != null) {
                productoEditarView.getTxtNombre().setText(producto.getNombre());
                productoEditarView.getTxtPrecio().setText(String.valueOf(producto.getPrecio()));
            } else {
                productoEditarView.mostrarMensaje(mi.get("producto.mensaje.no.encontrado"));productoEditarView.mostrarMensaje("Producto no encontrado");
                productoEditarView.limpiarCampos();
            }
        } else {
            productoEditarView.mostrarMensaje(mi.get("producto.mensaje.error.ingresar.codigo"));
        }
    }
    private void buscarProductoCarrito() {
        String txtCod = carritoAnadirView.getTxtBuscar().getText();
        if (!txtCod.isEmpty()) {
            int codigo = Integer.parseInt(txtCod);
            Producto producto = productoDAO.buscarPorCodigo(codigo);
            if (producto != null) {
                carritoAnadirView.getTxtNombre().setText(producto.getNombre());
                carritoAnadirView.getTxtPrecio().setText(String.valueOf(producto.getPrecio()));
            } else {
                carritoAnadirView.mostrarMensaje(mi.get("producto.mensaje.no.encontrado"));
                carritoAnadirView.limpiarCampos();
            }
        } else {
            carritoAnadirView.mostrarMensaje(mi.get("producto.mensaje.error.ingresar.codigo"));
        }
    }
}
