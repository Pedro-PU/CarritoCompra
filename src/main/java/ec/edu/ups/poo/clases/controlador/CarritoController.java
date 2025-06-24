package ec.edu.ups.poo.clases.controlador;

import ec.edu.ups.poo.clases.dao.CarritoDAO;
import ec.edu.ups.poo.clases.dao.ProductoDAO;
import ec.edu.ups.poo.clases.modelo.*;
import ec.edu.ups.poo.clases.vista.CarritoAnadirView;
import ec.edu.ups.poo.clases.vista.CarritoListaView;

import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.GregorianCalendar;
import java.util.List;

public class CarritoController {
    private final CarritoAnadirView carritoAnadirView;
    private final ProductoDAO productoDAO;
    private final Carrito carrito;
    private final CarritoDAO carritoDAO;
    private final Usuario usuario;
    private final CarritoListaView carritoListaView;

    public CarritoController(CarritoAnadirView carritoView, ProductoDAO productoDAO,
                             CarritoDAO carritoDAO, Usuario usuario, CarritoListaView carritoListaView) {
        this.carritoAnadirView = carritoView;
        this.productoDAO = productoDAO;
        this.carritoDAO = carritoDAO;
        this.usuario = usuario;
        this.carritoListaView = carritoListaView;
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

        carrito.vaciarCarrito();//Limpia y borra lo anteriormente Ingresado
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
    public void listarCarritos() {
        List<Carrito> carritosAMostrar;

        if (usuario.getRol().equals(Rol.ADMINISTRADOR)) {
            carritosAMostrar = carritoDAO.listarTodos();
        } else {
            carritosAMostrar = carritoDAO.buscarPorUsuario(usuario);
        }

        carritoListaView.cargarDatos(carritosAMostrar);
    }

}
