package ec.edu.ups.poo.clases.controlador;

import ec.edu.ups.poo.clases.dao.CarritoDAO;
import ec.edu.ups.poo.clases.dao.ProductoDAO;
import ec.edu.ups.poo.clases.modelo.*;
import ec.edu.ups.poo.clases.vista.carrito.*;
import ec.edu.ups.poo.clases.util.MensajeInternacionalizacionHandler;

import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
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
    private final MensajeInternacionalizacionHandler mi;

    public CarritoController(CarritoAnadirView carritoView, ProductoDAO productoDAO,
                             CarritoDAO carritoDAO, Usuario usuario, CarritoListaView carritoListaView, CarritoModificarView carritoModificarView, CarritoEliminarView carritoEliminarView,
                             MensajeInternacionalizacionHandler mi) {
        this.carritoAnadirView = carritoView;
        this.productoDAO = productoDAO;
        this.carritoDAO = carritoDAO;
        this.usuario = usuario;
        this.carritoListaView = carritoListaView;
        this.carritoModificarView = carritoModificarView;
        this.carritoEliminarView = carritoEliminarView;
        this.mi = mi;
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
        String txtCod = carritoListaView.getTxtBuscar().getText().trim();
        if (txtCod.isEmpty()) {
            carritoListaView.cargarDatos(List.of());
            return;
        }
        if (!txtCod.matches("[1-9]\\d*|0")) {
            carritoListaView.mostrarMensaje("El código debe ser un número entero positivo válido.");
            carritoListaView.cargarDatos(List.of());
            return;
        }
        int codigo = Integer.parseInt(txtCod);
        Carrito carrito = carritoDAO.buscarPorCodigo(codigo);
        if (carrito != null) {
            if (usuario.getRol().equals(Rol.ADMINISTRADOR)
                    || carrito.getUsuario().getUsername().equals(usuario.getUsername())) {
                carritoListaView.cargarDatos(List.of(carrito));
            } else {
                carritoListaView.cargarDatos(List.of());
            }
        } else {
            carritoListaView.cargarDatos(List.of());
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
                    carritoDetalleView = new CarritoDetalleView(mi);
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
        String txtCod = carritoModificarView.getTxtCodigo().getText().trim();
        if (txtCod.isEmpty()) {
            carritoModificarView.mostrarMensaje("Por favor, ingrese el código del carrito.");
            return;
        }
        if (!txtCod.matches("[1-9]\\d*|0")) {
            carritoModificarView.mostrarMensaje("El código debe ser un número entero positivo válido.");
            return;
        }
        int codigo = Integer.parseInt(txtCod);
        Carrito carritoEncontrado = carritoDAO.buscarPorCodigo(codigo);

        if (carritoEncontrado != null) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            String fechaFormateada = dateFormat.format(carrito.getFechaCreacion().getTime());
            carritoModificarView.getTxtFecha().setText(fechaFormateada);
            carritoModificarView.cargarDatos(carritoEncontrado);
            carritoModificarView.getTxtSubtotal().setText(String.valueOf(carritoEncontrado.calcularSubtotal()));
            carritoModificarView.getTxtIVA().setText(String.valueOf(carritoEncontrado.calcularIVA()));
            carritoModificarView.getTxtTotal().setText(String.valueOf(carritoEncontrado.calcularTotal()));
        } else {
            carritoModificarView.mostrarMensaje("Carrito no encontrado");
        }
    }

    private void editarCarrito() {
        if (carritoModificarView.getTblProductos().getSelectedRow() != -1) {
            String cantidadStr = carritoModificarView.cantidad("¿Desea modificar la cantidad del producto?");
            if (cantidadStr != null) {
                cantidadStr = cantidadStr.trim();
                if (!cantidadStr.matches("[1-9]\\d*")) {
                    carritoModificarView.mostrarMensaje("La cantidad debe ser un número entero positivo mayor que cero.");
                    return;
                }
                int nuevaCantidad = Integer.parseInt(cantidadStr);
                int codigoCarrito = Integer.parseInt(carritoModificarView.getTxtCodigo().getText().trim());
                Carrito carritoEncontrado = carritoDAO.buscarPorCodigo(codigoCarrito);
                if (carritoEncontrado == null) {
                    carritoModificarView.mostrarMensaje("Carrito no encontrado.");
                    return;
                }
                boolean itemEncontrado = false;
                for (ItemCarrito item : carritoEncontrado.obtenerItems()) {
                    int codigoProducto = (Integer) carritoModificarView.getTblProductos().getValueAt(
                            carritoModificarView.getTblProductos().getSelectedRow(), 0);
                    if (item.getProducto().getCodigo() == codigoProducto) {
                        item.setCantidad(nuevaCantidad);
                        itemEncontrado = true;
                        break;
                    }
                }
                if (!itemEncontrado) {
                    carritoModificarView.mostrarMensaje("Producto no encontrado en el carrito.");
                    return;
                }
                carritoDAO.actualizar(carritoEncontrado);
                carritoModificarView.cargarDatos(carritoEncontrado);
                carritoModificarView.getTxtSubtotal().setText(String.valueOf(carritoEncontrado.calcularSubtotal()));
                carritoModificarView.getTxtIVA().setText(String.valueOf(carritoEncontrado.calcularIVA()));
                carritoModificarView.getTxtTotal().setText(String.valueOf(carritoEncontrado.calcularTotal()));
            } else {
                carritoModificarView.mostrarMensaje("Cantidad no modificada.");
            }
        } else {
            carritoModificarView.mostrarMensaje("Seleccione un Item para editar.");
        }
    }

    private void buscarCarritoCodigoEliminar() {
        String txtCod = carritoEliminarView.getTxtCodigo().getText().trim();
        if (txtCod.isEmpty()) {
            carritoEliminarView.mostrarMensaje("Por favor, ingrese el código del carrito.");
            carritoEliminarView.getBtnEliminar().setEnabled(false);
            return;
        }
        if (!txtCod.matches("[1-9]\\d*|0")) {
            carritoEliminarView.mostrarMensaje("El código debe ser un número entero positivo válido.");
            carritoEliminarView.getBtnEliminar().setEnabled(false);
            return;
        }
        int codigo = Integer.parseInt(txtCod);
        Carrito carritoEncontrado = carritoDAO.buscarPorCodigo(codigo);
        if (carritoEncontrado != null) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            String fechaFormateada = dateFormat.format(carrito.getFechaCreacion().getTime());

            carritoEliminarView.getTxtFecha().setText(fechaFormateada);
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

    public void actualizarIdiomaEnVistas() {
        carritoAnadirView.cambiarIdioma();
        carritoListaView.cambiarIdioma();
        carritoModificarView.cambiarIdioma();
        carritoEliminarView.cambiarIdioma();
        if (carritoDetalleView != null && !carritoDetalleView.isClosed()) {
            carritoDetalleView.cambiarIdioma();
        }
    }

}
