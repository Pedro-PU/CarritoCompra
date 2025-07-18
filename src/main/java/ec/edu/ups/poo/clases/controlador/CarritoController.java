package ec.edu.ups.poo.clases.controlador;

import ec.edu.ups.poo.clases.dao.CarritoDAO;
import ec.edu.ups.poo.clases.dao.ProductoDAO;
import ec.edu.ups.poo.clases.modelo.*;
import ec.edu.ups.poo.clases.util.FormateadorUtils;
import ec.edu.ups.poo.clases.vista.carrito.*;
import ec.edu.ups.poo.clases.util.MensajeInternacionalizacionHandler;

import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

/**
 * Controlador principal para la gestión de carritos de compra. Coordina las interacciones
 * entre las vistas de carritos (añadir, modificar, eliminar, listar) y los DAOs correspondientes.
 * Maneja toda la lógica relacionada con operaciones CRUD de carritos, incluyendo la manipulación
 * de productos dentro de ellos, cálculos de totales y cambios de idioma.
 */
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
    private Carrito carritoDetalleActual;
    private Carrito carritoModificarActual;
    private Carrito carritoEliminarActual;
    private final MensajeInternacionalizacionHandler mi;

    /**
     * Constructor del controlador de carritos. Inicializa las vistas y DAOs necesarios,
     * configura los eventos y establece el carrito actual.
     *
     * @param carritoView Vista para añadir productos al carrito.
     * @param productoDAO DAO para operaciones con productos.
     * @param carritoDAO DAO para operaciones con carritos.
     * @param usuario Usuario autenticado.
     * @param carritoListaView Vista para listar carritos existentes.
     * @param carritoModificarView Vista para modificar carritos.
     * @param carritoEliminarView Vista para eliminar carritos.
     * @param mi Handler de internacionalización de mensajes.
     */
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

    /**
     * Configura los listeners para los botones de acción en todas las vistas de carritos.
     * Asocia cada botón con su correspondiente método de acción.
     */
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

    /**
     * Añade un producto al carrito actual después de validar su código y cantidad.
     * Actualiza la tabla de productos y los totales del carrito.
     */
    private void anadirProductoACarrito() {
        int codigo = Integer.parseInt(carritoAnadirView.getTxtBuscar().getText());
        Producto producto = productoDAO.buscarPorCodigo(codigo);
        int cantidad =  Integer.parseInt(carritoAnadirView.getCbxCantidad().getSelectedItem().toString());
        carrito.agregarProducto(producto, cantidad);
        actualizarTabla();
        actualizarTotales();
        carritoAnadirView.limpiarCampos();
    }

    /**
     * Actualiza la tabla de productos del carrito en la vista de añadir productos.
     * Muestra código, nombre, precio, cantidad y subtotal por producto.
     */
    private void actualizarTabla() {
        List<ItemCarrito> items = carrito.obtenerItems();
        DefaultTableModel modelo = (DefaultTableModel) carritoAnadirView.getTblProductos().getModel();
        modelo.setNumRows(0);
        for (ItemCarrito item : items) {
            Locale locale = mi.getLocale();
            modelo.addRow(new Object[]{
                    item.getProducto().getCodigo(),
                    item.getProducto().getNombre(),
                    FormateadorUtils.formatearMoneda(item.getProducto().getPrecio(), locale),
                    item.getCantidad(),
                    FormateadorUtils.formatearMoneda(item.getProducto().getPrecio() * item.getCantidad(), locale)
            });
        }
    }

    /**
     * Actualiza los totales (subtotal, IVA y total) del carrito en la vista.
     */
    private void actualizarTotales() {
        Locale locale = mi.getLocale();
        carritoAnadirView.getTxtSubTotal().setText(FormateadorUtils.formatearMoneda(carrito.calcularSubtotal(), locale));
        carritoAnadirView.getTxtIVA().setText(FormateadorUtils.formatearMoneda(carrito.calcularIVA(), locale));
        carritoAnadirView.getTxtTotal().setText(FormateadorUtils.formatearMoneda(carrito.calcularTotal(), locale));
    }

    /**
     * Finaliza y guarda el carrito actual en la base de datos.
     * Valida que el carrito no esté vacío antes de guardar.
     */
    private void aceptarCarrito() {
        if (carrito.estaVacio()) {
            carritoAnadirView.mostrarMensaje(mi.get("carrito.msj.vacio"));
            return;
        }
        carrito.setUsuario(usuario);
        carrito.setFechaCreacion(new GregorianCalendar());
        carritoDAO.crear(carrito);
        String mensaje = mi.get("carrito.msj.guardado")
                .replace("{0}", String.valueOf(carrito.getCodigo()))
                .replace("{1}", usuario.getUsername());
        carritoAnadirView.mostrarMensaje(mensaje);

        carrito.vaciarCarrito();
        actualizarTabla();
        actualizarTotales();
        carritoAnadirView.limpiarCampos();
    }

    /**
     * Elimina un producto seleccionado del carrito actual.
     * Solicita confirmación antes de eliminar el producto.
     */
    private void borrarItemFormulario(){
        int filaSeleccionada = carritoAnadirView.getTblProductos().getSelectedRow();
        if (filaSeleccionada != -1) {
            DefaultTableModel modelo = (DefaultTableModel) carritoAnadirView.getTblProductos().getModel();
            int codigoProducto = Integer.parseInt(modelo.getValueAt(filaSeleccionada, 0).toString());
            boolean confirmado = carritoAnadirView.mostrarMensajePregunta(mi.get("carrito.msj.confirmar.eliminar"));
            if (confirmado) {
                carrito.eliminarProducto(codigoProducto);
                actualizarTabla();
                actualizarTotales();
            }else{
                carritoAnadirView.mostrarMensaje(mi.get("carrito.msj.cancelado"));
            }
        } else {
            carritoAnadirView.mostrarMensaje(mi.get("carrito.msj.seleccione.fila"));
        }
    }

    /**
     * Limpia todos los productos del carrito actual.
     * Solicita confirmación antes de vaciar el carrito.
     */
    private void limpiarFormulario() {
        boolean confirmado = carritoAnadirView.mostrarMensajePregunta(mi.get("carrito.msj.confirmar.vaciar"));
        if (confirmado) {
            carrito.vaciarCarrito();
            actualizarTabla();
            actualizarTotales();
            carritoAnadirView.limpiarCampos();
        }else{
            carritoAnadirView.mostrarMensaje(mi.get("carrito.msj.vaciado"));
        }
    }

    /**
     * Busca un carrito por su código y muestra los resultados en la vista de lista.
     * Valida el formato del código antes de realizar la búsqueda.
     */
    private void buscarCarritoCodigo() {
        String txtCod = carritoListaView.getTxtBuscar().getText().trim();
        if (txtCod.isEmpty()) {
            carritoListaView.cargarDatos(List.of());
            return;
        }
        if (!txtCod.matches("[1-9]\\d*|0")) {
            carritoListaView.mostrarMensaje(mi.get("carrito.msj.codigo.invalido"));
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

    /**
     * Lista todos los carritos según el rol del usuario.
     * Los administradores ven todos los carritos, los usuarios solo los suyos.
     */
    private void listarCarritos() {
        List<Carrito> carritosAMostrar;

        if (usuario.getRol().equals(Rol.ADMINISTRADOR)) {
            carritosAMostrar = carritoDAO.listarTodos();
        } else {
            carritosAMostrar = carritoDAO.buscarPorUsuario(usuario);
        }

        carritoListaView.cargarDatos(carritosAMostrar);
    }

    /**
     * Muestra una ventana con los detalles del carrito seleccionado.
     * Incluye información detallada de productos y totales.
     */
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
                carritoDetalleActual = carrito;
                carritoDetalleView.cargarDatos(carrito);

                Locale locale = mi.getLocale();
                carritoDetalleView.getTxtSubTotal().setText(FormateadorUtils.formatearMoneda(carrito.calcularSubtotal(), locale));
                carritoDetalleView.getTxtIVA().setText(FormateadorUtils.formatearMoneda(carrito.calcularIVA(), locale));
                carritoDetalleView.getTxtTotal().setText(FormateadorUtils.formatearMoneda(carrito.calcularTotal(), locale));

                carritoDetalleView.setVisible(true);
                carritoDetalleView.moveToFront();
                carritoDetalleView.requestFocusInWindow();

            } else {
                carritoListaView.mostrarMensaje(mi.get("carrito.msj.no.encontrado"));
            }
        } else {
            carritoListaView.mostrarMensaje(mi.get("carrito.msj.seleccione.carrito"));
        }
    }

    /**
     * Busca un carrito por código para su modificación.
     * Valida el formato del código antes de realizar la búsqueda.
     */
    private void buscarCarritoCodigoModificar() {
        String txtCod = carritoModificarView.getTxtCodigo().getText().trim();
        if (txtCod.isEmpty()) {
            carritoModificarView.mostrarMensaje(mi.get("carrito.msj.ingrese.codigo"));
            return;
        }
        if (!txtCod.matches("[1-9]\\d*|0")) {
            carritoModificarView.mostrarMensaje(mi.get("carrito.msj.codigo.invalido"));
            return;
        }
        int codigo = Integer.parseInt(txtCod);
        Carrito carritoEncontrado = carritoDAO.buscarPorCodigo(codigo);
        if (carritoEncontrado != null) {
            carritoModificarActual = carritoEncontrado;
            Locale locale = mi.getLocale();
            carritoModificarView.getTxtFecha().setText(
                    FormateadorUtils.formatearFecha(carrito.getFechaCreacion().getTime(), locale)
            );
            carritoModificarView.cargarDatos(carritoEncontrado);
            carritoModificarView.getTxtSubtotal().setText(FormateadorUtils.formatearMoneda(carritoEncontrado.calcularSubtotal(), locale));
            carritoModificarView.getTxtIVA().setText(FormateadorUtils.formatearMoneda(carritoEncontrado.calcularIVA(), locale));
            carritoModificarView.getTxtTotal().setText(FormateadorUtils.formatearMoneda(carritoEncontrado.calcularTotal(), locale));
        } else {
            carritoModificarView.mostrarMensaje(mi.get("carrito.msj.no.encontrado"));
        }
    }

    /**
     * Permite editar la cantidad de un producto en el carrito seleccionado.
     * Valida que la nueva cantidad sea un número entero positivo.
     */
    private void editarCarrito() {
        if (carritoModificarView.getTblProductos().getSelectedRow() != -1) {
            String cantidadStr = carritoModificarView.cantidad(mi.get("carrito.msj.modificar.cantidad"));
            if (cantidadStr != null) {
                cantidadStr = cantidadStr.trim();
                if (!cantidadStr.matches("[1-9]\\d*")) {
                    carritoModificarView.mostrarMensaje(mi.get("carrito.msj.cantidad.invalida"));carritoModificarView.mostrarMensaje("La cantidad debe ser un número entero positivo mayor que cero.");
                    return;
                }
                int nuevaCantidad = Integer.parseInt(cantidadStr);
                int codigoCarrito = Integer.parseInt(carritoModificarView.getTxtCodigo().getText().trim());
                Carrito carritoEncontrado = carritoDAO.buscarPorCodigo(codigoCarrito);
                if (carritoEncontrado == null) {
                    carritoModificarView.mostrarMensaje(mi.get("carrito.msj.no.encontrado"));
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
                    carritoModificarView.mostrarMensaje(mi.get("carrito.msj.producto.no.encontrado"));
                    return;
                }
                carritoDAO.actualizar(carritoEncontrado);
                carritoModificarView.cargarDatos(carritoEncontrado);
                Locale locale = mi.getLocale();
                carritoModificarView.getTxtSubtotal().setText(FormateadorUtils.formatearMoneda(carritoEncontrado.calcularSubtotal(), locale));
                carritoModificarView.getTxtIVA().setText(FormateadorUtils.formatearMoneda(carritoEncontrado.calcularIVA(), locale));
                carritoModificarView.getTxtTotal().setText(FormateadorUtils.formatearMoneda(carritoEncontrado.calcularTotal(), locale));
            } else {
                carritoModificarView.mostrarMensaje(mi.get("carrito.msj.cantidad.no.modificada"));
            }
        } else {
            carritoModificarView.mostrarMensaje(mi.get("carrito.msj.seleccione.item"));
        }
    }

    /**
     * Busca un carrito por código para su eliminación.
     * Valida el formato del código antes de realizar la búsqueda.
     */
    private void buscarCarritoCodigoEliminar() {
        String txtCod = carritoEliminarView.getTxtCodigo().getText().trim();
        if (txtCod.isEmpty()) {
            carritoEliminarView.mostrarMensaje(mi.get("carrito.msj.ingrese.codigo"));
            carritoEliminarView.getBtnEliminar().setEnabled(false);
            return;
        }
        if (!txtCod.matches("[1-9]\\d*|0")) {
            carritoEliminarView.mostrarMensaje(mi.get("carrito.msj.codigo.invalido"));
            carritoEliminarView.getBtnEliminar().setEnabled(false);
            return;
        }
        int codigo = Integer.parseInt(txtCod);
        Carrito carritoEncontrado = carritoDAO.buscarPorCodigo(codigo);
        if (carritoEncontrado != null) {
            carritoEliminarActual = carritoEncontrado;
            Locale locale = mi.getLocale();
            carritoEliminarView.getTxtFecha().setText(
                    FormateadorUtils.formatearFecha(carrito.getFechaCreacion().getTime(), locale)
            );

            carritoEliminarView.cargarDatos(carritoEncontrado);
            carritoEliminarView.getTxtSubtotal().setText(FormateadorUtils.formatearMoneda(carritoEncontrado.calcularSubtotal(), locale));
            carritoEliminarView.getTxtIVA().setText(FormateadorUtils.formatearMoneda(carritoEncontrado.calcularIVA(), locale));
            carritoEliminarView.getTxtTotal().setText(FormateadorUtils.formatearMoneda(carritoEncontrado.calcularTotal(), locale));
            carritoEliminarView.getBtnEliminar().setEnabled(true);
        } else {
            carritoEliminarView.mostrarMensaje(mi.get("carrito.msj.no.encontrado"));
            carritoEliminarView.getBtnEliminar().setEnabled(false);
        }
    }

    /**
     * Elimina un carrito del sistema después de confirmar la acción.
     */
    private void eliminarCarrito(){
        boolean confirmado = carritoEliminarView.mostrarMensajePregunta(mi.get("carrito.msj.eliminar.confirmar"));
        if(confirmado) {
            carritoDAO.eliminar(Integer.parseInt(carritoEliminarView.getTxtCodigo().getText()));
            carritoEliminarView.mostrarMensaje(mi.get("carrito.msj.eliminado"));
            carritoEliminarView.limpiarCampos();
        }else{
            carritoEliminarView.mostrarMensaje(mi.get("carrito.msj.eliminar.cancelada"));
        }
    }

    /**
     * Actualiza el formato de los datos monetarios en la vista de añadir productos
     * según el locale especificado.
     *
     * @param locale Locale para formatear los valores monetarios.
     */
    private void refrescarTablaAnadir(Locale locale) {
        List<ItemCarrito> items = carrito.obtenerItems();
        DefaultTableModel modelo = (DefaultTableModel) carritoAnadirView.getTblProductos().getModel();
        for (int i = 0; i < items.size(); i++) {
            ItemCarrito item = items.get(i);
            modelo.setValueAt(FormateadorUtils.formatearMoneda(item.getProducto().getPrecio(), locale), i, 2);
            modelo.setValueAt(FormateadorUtils.formatearMoneda(item.getProducto().getPrecio() * item.getCantidad(), locale), i, 4);
        }
        actualizarTotales();
    }

    /**
     * Actualiza el formato de los datos en la vista de listar carritos
     * según el locale especificado.
     *
     * @param locale Locale para formatear fechas y valores monetarios.
     */
    private void refrescarTablaLista(Locale locale) {
        int rowCount = carritoListaView.getModelo().getRowCount();

        for (int i = 0; i < rowCount; i++) {
            int codigo = (Integer) carritoListaView.getModelo().getValueAt(i, 0);

            Carrito carrito = carritoDAO.buscarPorCodigo(codigo);
            if (carrito != null) {
                String nuevaFecha = FormateadorUtils.formatearFecha(carrito.getFechaCreacion().getTime(), locale);
                carritoListaView.getModelo().setValueAt(nuevaFecha, i, 2);
                String nuevoSubtotal = FormateadorUtils.formatearMoneda(carrito.calcularSubtotal(), locale);
                carritoListaView.getModelo().setValueAt(nuevoSubtotal, i, 3);
                String nuevoIva = FormateadorUtils.formatearMoneda(carrito.calcularIVA(), locale);
                carritoListaView.getModelo().setValueAt(nuevoIva, i, 4);
                String nuevoTotal = FormateadorUtils.formatearMoneda(carrito.calcularTotal(), locale);
                carritoListaView.getModelo().setValueAt(nuevoTotal, i, 5);
            }
        }
    }

    /**
     * Actualiza el formato de los datos en la vista de modificar carrito
     * según el locale especificado.
     *
     * @param locale Locale para formatear fechas y valores monetarios.
     */
    private void refrescarTablaModificar(Locale locale) {
        if (carritoModificarActual == null) return;
        carritoModificarView.getTxtFecha().setText(FormateadorUtils.formatearFecha(carritoModificarActual.getFechaCreacion().getTime(), locale));
        carritoModificarView.getTxtSubtotal().setText(FormateadorUtils.formatearMoneda(carritoModificarActual.calcularSubtotal(), locale));
        carritoModificarView.getTxtIVA().setText(FormateadorUtils.formatearMoneda(carritoModificarActual.calcularIVA(), locale));
        carritoModificarView.getTxtTotal().setText(FormateadorUtils.formatearMoneda(carritoModificarActual.calcularTotal(), locale));
        int rowCount = carritoModificarView.getTblProductos().getRowCount();

        for (int i = 0; i < rowCount; i++) {
            int codigoProducto = (Integer) carritoModificarView.getTblProductos().getValueAt(i, 0);
            int cantidad = (Integer) carritoModificarView.getTblProductos().getValueAt(i, 3);
            Producto producto = productoDAO.buscarPorCodigo(codigoProducto);

            if (producto != null) {
                carritoModificarView.getTblProductos().setValueAt(
                        FormateadorUtils.formatearMoneda(producto.getPrecio(), locale), i, 2);
            }
        }
    }

    /**
     * Actualiza el formato de los datos en la vista de eliminar carrito
     * según el locale especificado.
     *
     * @param locale Locale para formatear fechas y valores monetarios.
     */
    private void refrescarTablaEliminar(Locale locale) {
        if (carritoEliminarActual == null) return;
        carritoEliminarView.getTxtFecha().setText(FormateadorUtils.formatearFecha(carritoEliminarActual.getFechaCreacion().getTime(), locale));
        carritoEliminarView.getTxtSubtotal().setText(FormateadorUtils.formatearMoneda(carritoEliminarActual.calcularSubtotal(), locale));
        carritoEliminarView.getTxtIVA().setText(FormateadorUtils.formatearMoneda(carritoEliminarActual.calcularIVA(), locale));
        carritoEliminarView.getTxtTotal().setText(FormateadorUtils.formatearMoneda(carritoEliminarActual.calcularTotal(), locale));
        int rowCount = carritoEliminarView.getTblProductos().getRowCount();

        for (int i = 0; i < rowCount; i++) {
            int codigoProducto = (Integer) carritoEliminarView.getTblProductos().getValueAt(i, 0);
            int cantidad = (Integer) carritoEliminarView.getTblProductos().getValueAt(i, 3);
            Producto producto = productoDAO.buscarPorCodigo(codigoProducto);

            if (producto != null) {
                carritoEliminarView.getTblProductos().setValueAt(
                        FormateadorUtils.formatearMoneda(producto.getPrecio(), locale), i, 2);
            }
        }
    }

    /**
     * Actualiza el formato de los datos en la vista de detalle de carrito
     * según el locale especificado.
     *
     * @param locale Locale para formatear valores monetarios.
     */
    private void refrescarDetalle(Locale locale) {
        if (carritoDetalleView != null && !carritoDetalleView.isClosed()) {
            Carrito carrito = carritoDAO.buscarPorCodigo(
                    (int) carritoDetalleView.getTblProductos().getValueAt(0, 0)); // Asumiendo hay código en columna 0
            if (carrito != null) {
                carritoDetalleView.getTxtSubTotal().setText(FormateadorUtils.formatearMoneda(carrito.calcularSubtotal(), locale));
                carritoDetalleView.getTxtIVA().setText(FormateadorUtils.formatearMoneda(carrito.calcularIVA(), locale));
                carritoDetalleView.getTxtTotal().setText(FormateadorUtils.formatearMoneda(carrito.calcularTotal(), locale));
                carritoDetalleView.cargarDatos(carritoDetalleActual);
            }
        }
    }

    /**
     * Actualiza el idioma en todas las vistas asociadas y reformatea los datos
     * según el nuevo locale.
     */
    public void actualizarIdiomaEnVistas() {
        carritoAnadirView.cambiarIdioma();
        carritoListaView.cambiarIdioma();
        carritoModificarView.cambiarIdioma();
        carritoEliminarView.cambiarIdioma();
        if (carritoDetalleView != null && !carritoDetalleView.isClosed()) {
            carritoDetalleView.cambiarIdioma();
        }
        Locale locale = mi.getLocale();
        refrescarTablaAnadir(locale);
        refrescarTablaLista(locale);
        refrescarTablaModificar(locale);
        refrescarTablaEliminar(locale);
        refrescarDetalle(locale);
    }
}
