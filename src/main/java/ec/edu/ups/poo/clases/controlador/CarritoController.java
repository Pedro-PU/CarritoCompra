package ec.edu.ups.poo.clases.controlador;

import ec.edu.ups.poo.clases.dao.CarritoDAO;
import ec.edu.ups.poo.clases.modelo.Carrito;
import ec.edu.ups.poo.clases.modelo.ItemCarrito;
import ec.edu.ups.poo.clases.modelo.Producto;
import ec.edu.ups.poo.clases.vista.CarritoAnadirView;

import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class CarritoController {
    private final CarritoAnadirView carritoView;
    private final ProductoController productoController;
    private final Carrito carrito;
    private final CarritoDAO carritoDAO;

    public CarritoController(CarritoAnadirView carritoView, ProductoController productoController, CarritoDAO carritoDAO) {
        this.carritoView = carritoView;
        this.productoController = productoController;
        this.carritoDAO = carritoDAO;
        this.carrito = new Carrito();
        carrito.setCodigo(carrito.hashCode());

        configurarEventos();
    }
    private void configurarEventos() {
        carritoView.getBtnAnadir().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                anadirProductoACarrito();
            }
        });
        carritoView.getBtnAceptar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                aceptarCarrito();
            }
        });
        carritoView.getBtnLimpiar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                limpiarFormulario();
            }
        });
    }
    private void anadirProductoACarrito() {
        String textoCodigo = carritoView.getTxtBuscar().getText();
        if (textoCodigo.isEmpty()) {
            carritoView.mostrarMensaje("Ingresa el código del producto.");
            return;
        }

        int codigoProducto = Integer.parseInt(textoCodigo);
        Producto producto = productoController.buscarPorCodigo(codigoProducto);

        if (producto == null) {
            carritoView.mostrarMensaje("Producto no encontrado.");
            return;
        }

        int cantidad = Integer.parseInt(carritoView.getCbxCantidad().getSelectedItem().toString());

        carrito.agregarProducto(producto, cantidad);
        actualizarTabla();
        actualizarTotales();
        carritoView.limpiarCampos();
    }

    private void actualizarTabla() {
        List<ItemCarrito> items = carrito.obtenerItems();

        String[] columnas = {"Código", "Nombre", "Precio", "Cantidad", "Subtotal"};
        String[][] datos = new String[items.size()][5];

        for (int i = 0; i < items.size(); i++) {
            ItemCarrito item = items.get(i);
            Producto p = item.getProducto();

            datos[i][0] = String.valueOf(p.getCodigo());
            datos[i][1] = p.getNombre();
            datos[i][2] = String.format("%.2f", p.getPrecio());
            datos[i][3] = String.valueOf(item.getCantidad());
            datos[i][4] = String.format("%.2f", p.getPrecio() * item.getCantidad());
        }

        carritoView.getTblProductos().setModel(new DefaultTableModel(datos, columnas));
    }

    private void actualizarTotales() {
        double subtotal = carrito.calcularTotal();
        double iva = subtotal * 0.12;
        double total = subtotal + iva;

        carritoView.getTxtSubTotal().setText(String.format("%.2f", subtotal));
        carritoView.getTxtIVA().setText(String.format("%.2f", iva));
        carritoView.getTxtTotal().setText(String.format("%.2f", total));
    }

    private void aceptarCarrito() {
        if (carrito.estaVacio()) {
            carritoView.mostrarMensaje("El carrito está vacío.");
            return;
        }

        carritoDAO.crear(carrito);

        carritoView.mostrarMensaje("Carrito guardado con éxito. Código: " + carrito.getCodigo());

        carrito.vaciarCarrito();
        actualizarTabla();
        actualizarTotales();
        carritoView.limpiarCampos();
    }

    private void limpiarFormulario() {
        carrito.vaciarCarrito();
        actualizarTabla();
        actualizarTotales();
        carritoView.limpiarCampos();
    }
}
