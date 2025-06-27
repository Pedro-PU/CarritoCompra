package ec.edu.ups.poo.clases.controlador;

import ec.edu.ups.poo.clases.dao.CarritoDAO;
import ec.edu.ups.poo.clases.dao.ProductoDAO;
import ec.edu.ups.poo.clases.modelo.*;
import ec.edu.ups.poo.clases.vista.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyVetoException;
import java.util.GregorianCalendar;
import java.util.List;

public class CarritoController {
    private final CarritoAnadirView carritoAnadirView;
    private final ProductoDAO productoDAO;
    private final Carrito carrito;
    private final CarritoDAO carritoDAO;
    private final Usuario usuario;
    private final CarritoListaView carritoListaView;
    private CarritoDetalleView carritoDetalleView;
    private final CarritoModificarView carritoModificarView;
    private final CarritoEliminarView carritoEliminarView;

    public CarritoController(CarritoAnadirView carritoView, ProductoDAO productoDAO,
                             CarritoDAO carritoDAO, Usuario usuario, CarritoListaView carritoListaView, CarritoModificarView carritoModificarView, CarritoEliminarView carritoEliminarView) {
        this.carritoAnadirView = carritoView;
        this.productoDAO = productoDAO;
        this.carritoDAO = carritoDAO;
        this.usuario = usuario;
        this.carritoListaView = carritoListaView;
        this.carritoModificarView = carritoModificarView;
        this.carritoEliminarView = carritoEliminarView;
        this.carrito = new Carrito();
        carrito.setCodigo(carrito.hashCode());

        configurarEventosEnVistas();
    }
    private void configurarEventosEnVistas() {
        carritoAnadirView.getBtnAnadir().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                anadirProductoACarrito();
            }
        });
        carritoAnadirView.getBtnAceptar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                aceptarCarrito();
            }
        });
        carritoAnadirView.getBtnLimpiar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                limpiarFormulario();
            }
        });
        carritoAnadirView.getBtnBorrar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                borrarItemFormulario();
            }
        });
        carritoListaView.getBtnBuscar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buscarCarritoCodigo();
            }
        });
        carritoListaView.getBtnListar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                listarCarritos();
            }
        });
        carritoListaView.getBtnDetalle().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarDetalle();
            }
        });
        carritoModificarView.getBtnBuscar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buscarCarritoCodigoModificar();
            }
        });
        carritoModificarView.getBtnEditar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editarCarrito();
            }
        });
        carritoEliminarView.getBtnBuscar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buscarCarritoCodigoEliminar();
            }
        });
        carritoEliminarView.getBtnEliminar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                eliminarCarrito();
            }
        });

    }
    private void anadirProductoACarrito() {
        int codigo = Integer.parseInt(carritoAnadirView.getTxtBuscar().getText());
        Producto producto = productoDAO.buscarPorCodigo(codigo);
        int cantidad =  Integer.parseInt(carritoAnadirView.getCbxCantidad().getSelectedItem().toString());
        carrito.agregarProducto(producto, cantidad);
        actualizarTabla();
        actualizarTotales();
        carritoAnadirView.limpiarCampos();
    }

    private void actualizarTabla() {
        List<ItemCarrito> items = carrito.obtenerItems();
        DefaultTableModel modelo = (DefaultTableModel) carritoAnadirView.getTblProductos().getModel();
        modelo.setNumRows(0);
        for (ItemCarrito item : items) {
            modelo.addRow(new Object[]{ item.getProducto().getCodigo(),
                    item.getProducto().getNombre(),
                    item.getProducto().getPrecio(),
                    item.getCantidad(),
                    item.getProducto().getPrecio() * item.getCantidad() });
        }
    }

    private void actualizarTotales() {
        String subtotal = String.format("%.2f", carrito.calcularSubtotal());
        String iva = String.format("%.2f", carrito.calcularIVA());
        String total = String.format("%.2f", carrito.calcularTotal());

        carritoAnadirView.getTxtSubTotal().setText(subtotal);
        carritoAnadirView.getTxtIVA().setText(iva);
        carritoAnadirView.getTxtTotal().setText(total);
    }

    private void aceptarCarrito() {
        if (carrito.estaVacio()) {
            carritoAnadirView.mostrarMensaje("El carrito está vacío");
            return;
        }
        carrito.setUsuario(usuario);
        carrito.setFechaCreacion(new GregorianCalendar());
        carritoDAO.crear(carrito);
        carritoAnadirView.mostrarMensaje("Carrito guardado con éxito. Código: " + carrito.getCodigo() +
                "\nCon el usuario: "+usuario.getUsername());

        carrito.vaciarCarrito();
        actualizarTabla();
        actualizarTotales();
        carritoAnadirView.limpiarCampos();
    }
    private void borrarItemFormulario(){
        int filaSeleccionada = carritoAnadirView.getTblProductos().getSelectedRow();
        if (filaSeleccionada != -1) {
            DefaultTableModel modelo = (DefaultTableModel) carritoAnadirView.getTblProductos().getModel();
            int codigoProducto = Integer.parseInt(modelo.getValueAt(filaSeleccionada, 0).toString());
            boolean confirmado = carritoAnadirView.mostrarMensajePregunta("¿Desea eliminar el producto?");
            if (confirmado) {
                carrito.eliminarProducto(codigoProducto);
                actualizarTabla();
                actualizarTotales();
            }else{
                carritoAnadirView.mostrarMensaje("Eliminación cancelada");
            }
        } else {
            carritoAnadirView.mostrarMensaje("Seleccione una fila para eliminar");
        }
    }

    private void limpiarFormulario() {
        boolean confirmado = carritoAnadirView.mostrarMensajePregunta("¿Deseas vaciar el carrito?");
        if (confirmado) {
            carrito.vaciarCarrito();
            actualizarTabla();
            actualizarTotales();
            carritoAnadirView.limpiarCampos();
        }else{
            carritoAnadirView.mostrarMensaje("Cancelada");
        }
    }

    private void buscarCarritoCodigo() {
        String txtCod = carritoListaView.getTxtBuscar().getText();
        if (!txtCod.isEmpty() && txtCod.matches("\\d+")) {
            int codigo = Integer.parseInt(txtCod);
            Carrito carrito = carritoDAO.buscarPorCodigo(codigo);
            if (carrito != null) {
                if (usuario.getRol().equals(Rol.ADMINISTRADOR)) {
                    carritoListaView.cargarDatos(List.of(carrito));
                }
                else if (carrito.getUsuario().getUsername().equals(usuario.getUsername())) {
                    carritoListaView.cargarDatos(List.of(carrito));
                } else {
                    carritoListaView.cargarDatos(List.of());
                }
            } else {
                carritoListaView.cargarDatos(List.of());
            }
        }
    }
    private void listarCarritos() {
        List<Carrito> carritosAMostrar;

        if (usuario.getRol().equals(Rol.ADMINISTRADOR)) {
            carritosAMostrar = carritoDAO.listarTodos();
        } else {
            carritosAMostrar = carritoDAO.buscarPorUsuario(usuario);
        }

        carritoListaView.cargarDatos(carritosAMostrar);
    }
    private void mostrarDetalle() {
        int filaSeleccionada = carritoListaView.getTblProductos().getSelectedRow();

        if (filaSeleccionada != -1) {
            int codigoCarrito = (int) carritoListaView.getModelo().getValueAt(filaSeleccionada, 0);

            Carrito carrito = carritoDAO.buscarPorCodigo(codigoCarrito);
            if (carrito != null) {
                if (carritoDetalleView == null || carritoDetalleView.isClosed()) {
                    carritoDetalleView = new CarritoDetalleView();
                    carritoListaView.getDesktopPane().add(carritoDetalleView);
                }

                carritoDetalleView.cargarDatos(carrito);

                carritoDetalleView.getTxtSubTotal().setText(String.format("%.2f", carrito.calcularSubtotal()));
                carritoDetalleView.getTxtIVA().setText(String.format("%.2f", carrito.calcularIVA()));
                carritoDetalleView.getTxtTotal().setText(String.format("%.2f", carrito.calcularTotal()));

                carritoDetalleView.setVisible(true);
                carritoDetalleView.moveToFront();
                carritoDetalleView.requestFocusInWindow();

            } else {
                carritoDetalleView.mostrarMensaje("Carrito no encontrado");
            }
        } else {
            carritoDetalleView.mostrarMensaje("Seleccione un carrito de la tabla");
        }
    }

    private void buscarCarritoCodigoModificar() {
        int codigo = Integer.parseInt(carritoModificarView.getTxtCodigo().getText());
        Carrito carritoEncontrado = carritoDAO.buscarPorCodigo(codigo);
        if (carritoEncontrado != null) {
            carritoModificarView.getTxtFecha().setText(carritoEncontrado.getFechaCreacion());
            carritoModificarView.cargarDatos(carritoEncontrado);
            carritoModificarView.getTxtSubtotal().setText(String.valueOf(carritoEncontrado.calcularSubtotal()));
            carritoModificarView.getTxtIVA().setText(String.valueOf(carritoEncontrado.calcularIVA()));
            carritoModificarView.getTxtTotal().setText(String.valueOf(carritoEncontrado.calcularTotal()));
        } else {
            carritoModificarView.mostrarMensaje("Carrito no encontrado");
        }
    }

    private void editarCarrito(){
        if(carritoModificarView.getTblProductos().getSelectedRow() != -1) {
            int nuevaCantidad = Integer.parseInt(JOptionPane.showInputDialog(carritoModificarView, "Ingrese la nueva cantidad:"));

            int codigoCarrito = Integer.parseInt(carritoModificarView.getTxtCodigo().getText());
            Carrito carritoEncontrado = carritoDAO.buscarPorCodigo(codigoCarrito);

            for (ItemCarrito item: carritoEncontrado.obtenerItems()) {
                if (item.getProducto().getCodigo() == (Integer) carritoModificarView.getTblProductos().getValueAt(carritoModificarView.getTblProductos().getSelectedRow(), 0)) {
                    item.setCantidad(nuevaCantidad);
                }
            }
            carritoDAO.actualizar(carritoEncontrado);
            carritoModificarView.cargarDatos(carritoEncontrado);

            carritoModificarView.getTxtSubtotal().setText(String.valueOf(carritoEncontrado.calcularSubtotal()));
            carritoModificarView.getTxtIVA().setText(String.valueOf(carritoEncontrado.calcularIVA()));
            carritoModificarView.getTxtTotal().setText(String.valueOf(carritoEncontrado.calcularTotal()));

        } else {
            carritoModificarView.mostrarMensaje("Seleccione un Item para editar");

        }
    }

    private void buscarCarritoCodigoEliminar(){
        int codigo = Integer.parseInt(carritoEliminarView.getTxtCodigo().getText());
        Carrito carritoEncontrado = carritoDAO.buscarPorCodigo(codigo);
        if (carritoEncontrado != null) {
            carritoEliminarView.getTxtFecha().setText(carritoEncontrado.getFechaCreacion());
            carritoEliminarView.cargarDatos(carritoEncontrado);
            carritoEliminarView.getTxtSubtotal().setText(String.valueOf(carritoEncontrado.calcularSubtotal()));
            carritoEliminarView.getTxtIVA().setText(String.valueOf(carritoEncontrado.calcularIVA()));
            carritoEliminarView.getTxtTotal().setText(String.valueOf(carritoEncontrado.calcularTotal()));
            carritoEliminarView.getBtnEliminar().setEnabled(true);
        } else {
            carritoEliminarView.mostrarMensaje("Carrito no encontrado");
            carritoEliminarView.getBtnEliminar().setEnabled(false);
        }
    }

    private void eliminarCarrito(){
        boolean confirmado = carritoEliminarView.mostrarMensajePregunta("¿Deseas eliminar el carrito?");
        if(confirmado) {
            carritoDAO.eliminar(Integer.parseInt(carritoEliminarView.getTxtCodigo().getText()));
            carritoEliminarView.mostrarMensaje("Carrito eliminado");
            carritoEliminarView.limpiarCampos();
        }else{
            carritoEliminarView.mostrarMensaje("Acción cancelada");
        }
    }


}
