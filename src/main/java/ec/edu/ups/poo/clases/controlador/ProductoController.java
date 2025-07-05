package ec.edu.ups.poo.clases.controlador;

import ec.edu.ups.poo.clases.dao.ProductoDAO;
import ec.edu.ups.poo.clases.modelo.Producto;
import ec.edu.ups.poo.clases.util.FormateadorUtils;
import ec.edu.ups.poo.clases.util.MensajeInternacionalizacionHandler;
import ec.edu.ups.poo.clases.vista.carrito.CarritoAnadirView;
import ec.edu.ups.poo.clases.vista.producto.ProductoAnadirView;
import ec.edu.ups.poo.clases.vista.producto.ProductoEditarView;
import ec.edu.ups.poo.clases.vista.producto.ProductoEliminarView;
import ec.edu.ups.poo.clases.vista.producto.ProductoListaView;

import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Locale;


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
    // Vincula los botones de cada vista con sus respectivas acciones
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
    // Valida campos y guarda un nuevo producto si no existe el código
    private void guardarProducto() {
        String codigoTexto = productoAnadirView.getTxtCodigo().getText().trim();
        String nombre = productoAnadirView.getTxtNombre().getText().trim();
        String precioTexto = productoAnadirView.getTxtPrecio().getText().trim();

        if (codigoTexto.isEmpty() || nombre.isEmpty() || precioTexto.isEmpty()) {
            productoAnadirView.mostrarMensaje(mi.get("producto.mensaje.campos.vacios"));
            return;
        }

        if (!codigoTexto.matches("[1-9]\\d*|0")) {//sin letras, sin decimales
            productoAnadirView.mostrarMensaje(mi.get("producto.mensaje.codigo.invalido"));
            return;
        }

        int codigo = Integer.parseInt(codigoTexto);
        if (!precioTexto.matches("\\d+(\\.\\d+)?")) {//número decimal positivo
            productoAnadirView.mostrarMensaje(mi.get("producto.mensaje.precio.invalido"));
            return;
        }

        double precio = Double.parseDouble(precioTexto);
        if (productoDAO.buscarPorCodigo(codigo) != null) {
            productoAnadirView.mostrarMensaje(mi.get("producto.mensaje.error.codigo.existe"));
            return;
        }

        productoDAO.crear(new Producto(codigo, nombre, precio));
        productoAnadirView.mostrarMensaje(mi.get("producto.mensaje.guardado.correctamente"));
        productoAnadirView.limpiarCampos();
        productoAnadirView.mostrarProductos(productoDAO.listarTodos());
    }
    // Filtra productos por nombre desde la vista productoListaView
    private void buscarProducto() {
        String nombre = productoListaView.getTxtBuscar().getText();

        List<Producto> productosEncontrados = productoDAO.buscarPorNombre(nombre);
        productoListaView.cargarDatos(productosEncontrados);
    }
    // Muestra todos los productos registrados
    private void listarProductos() {
        List<Producto> productos = productoDAO.listarTodos();
        productoListaView.cargarDatos(productos);
    }
    // Permite modificar nombre y precio de un producto existente
    private void actualizarProducto() {
        String txtCod = productoEditarView.getTxtBuscar().getText().trim();
        String nombre = productoEditarView.getTxtNombre().getText().trim();
        String txtPrecio = productoEditarView.getTxtPrecio().getText().trim();

        if (txtCod.isEmpty() || nombre.isEmpty() || txtPrecio.isEmpty()) {
            productoEditarView.mostrarMensaje(mi.get("producto.mensaje.campos.vacios"));
            return;
        }

        if (!txtCod.matches("[1-9]\\d*|0")) {
            productoEditarView.mostrarMensaje(mi.get("producto.mensaje.codigo.invalido"));
            return;
        }

        int codigo = Integer.parseInt(txtCod);

        if (!txtPrecio.matches("\\d+(\\.\\d+)?")) {
            productoEditarView.mostrarMensaje(mi.get("producto.mensaje.precio.invalido"));
            return;
        }

        double precio = Double.parseDouble(txtPrecio);

        Producto producto = productoDAO.buscarPorCodigo(codigo);
        if (producto == null) {
            productoEditarView.mostrarMensaje(mi.get("producto.mensaje.no.encontrado"));
            return;
        }
        boolean confirmado = productoEditarView.mostrarMensajePregunta(mi.get("producto.mensaje.actualizar.pregunta"));
        if (!confirmado) {
            productoEliminarView.mostrarMensaje(mi.get("producto.mensaje.actualizacion.cancelada"));
            return;
        }
        producto.setNombre(nombre);
        producto.setPrecio(precio);
        productoDAO.actualizar(producto);
        productoEditarView.mostrarMensaje(mi.get("producto.mensaje.actualizado.correctamente"));
    }
    // Elimina un producto si el código es válido y se confirma la acción
    private void eliminarProducto() {
        String textCodigo = productoEliminarView.getTxtBuscar().getText().trim();
        if (textCodigo.isEmpty()) {
            productoEliminarView.mostrarMensaje(mi.get("producto.mensaje.ingresar.codigo"));
            return;
        }
        if (!textCodigo.matches("[1-9]\\d*|0")) {
            productoEliminarView.mostrarMensaje(mi.get("producto.mensaje.codigo.invalido"));
            return;
        }
        int codigo = Integer.parseInt(textCodigo);
        Producto producto = productoDAO.buscarPorCodigo(codigo);
        if (producto == null) {
            productoEliminarView.mostrarMensaje(mi.get("producto.mensaje.no.encontrado"));
            return;
        }
        boolean confirmado = productoEliminarView.mostrarMensajePregunta(mi.get("producto.mensaje.eliminar.pregunta"));
        if (!confirmado) {
            productoEliminarView.mostrarMensaje(mi.get("producto.mensaje.eliminacion.cancelada"));
            return;
        }
        productoDAO.eliminar(codigo);
        productoEliminarView.mostrarMensaje(mi.get("producto.mensaje.eliminado.correctamente"));
        productoEliminarView.limpiarCampos();
    }
    // Busca un producto y carga los datos en la vista de eliminación
    private void buscarProductoEliminar() {
        String txtCod = productoEliminarView.getTxtBuscar().getText().trim();

        if (txtCod.isEmpty()) {
            productoEliminarView.mostrarMensaje(mi.get("producto.mensaje.error.ingresar.codigo"));
            return;
        }

        if (!txtCod.matches("[1-9]\\d*|0")) {
            productoEliminarView.mostrarMensaje(mi.get("producto.mensaje.codigo.invalido"));
            return;
        }

        int codigo = Integer.parseInt(txtCod);
        Producto producto = productoDAO.buscarPorCodigo(codigo);

        if (producto != null) {
            productoEliminarView.getTxtNombre().setText(producto.getNombre());
            productoEliminarView.getTxtPrecio().setText(FormateadorUtils.formatearMoneda(producto.getPrecio(), mi.getLocale()));
        } else {
            productoEliminarView.mostrarMensaje(mi.get("producto.mensaje.no.encontrado"));
            productoEliminarView.limpiarCampos();
        }
    }
    // Busca un producto para editar y muestra sus datos
    private void buscarProductoEdicion() {
        String txtCod = productoEditarView.getTxtBuscar().getText().trim();

        if (txtCod.isEmpty()) {
            productoEditarView.mostrarMensaje(mi.get("producto.mensaje.error.ingresar.codigo"));
            return;
        }

        if (!txtCod.matches("[1-9]\\d*|0")) {
            productoEditarView.mostrarMensaje(mi.get("producto.mensaje.codigo.invalido"));
            return;
        }

        int codigo = Integer.parseInt(txtCod);
        Producto producto = productoDAO.buscarPorCodigo(codigo);

        if (producto != null) {
            productoEditarView.getTxtNombre().setText(producto.getNombre());
            productoEditarView.getTxtPrecio().setText(FormateadorUtils.formatearMoneda(producto.getPrecio(), mi.getLocale()));
        } else {
            productoEditarView.mostrarMensaje(mi.get("producto.mensaje.no.encontrado"));
            productoEditarView.limpiarCampos();
        }
    }
    // Permite buscar un producto desde la vista del carrito para agregarlo
    private void buscarProductoCarrito() {
        String txtCod = carritoAnadirView.getTxtBuscar().getText().trim();

        if (txtCod.isEmpty()) {
            carritoAnadirView.mostrarMensaje(mi.get("producto.mensaje.error.ingresar.codigo"));
            return;
        }

        if (!txtCod.matches("[1-9]\\d*|0")) {
            carritoAnadirView.mostrarMensaje(mi.get("producto.mensaje.codigo.invalido"));
            return;
        }

        int codigo = Integer.parseInt(txtCod);
        Producto producto = productoDAO.buscarPorCodigo(codigo);

        if (producto != null) {
            carritoAnadirView.getTxtNombre().setText(producto.getNombre());
            carritoAnadirView.getTxtPrecio().setText(FormateadorUtils.formatearMoneda(producto.getPrecio(), mi.getLocale()));
        } else {
            carritoAnadirView.mostrarMensaje(mi.get("producto.mensaje.no.encontrado"));
            carritoAnadirView.limpiarCampos();
        }
    }
    // Actualiza el precio de los productos en la tabla según el idioma
    private void refrescarTablaListaProductos(Locale locale) {
        DefaultTableModel modelo = (DefaultTableModel) productoListaView.getTblProductos().getModel();
        int rowCount = modelo.getRowCount();

        for (int i = 0; i < rowCount; i++) {
            int codigo = (Integer) modelo.getValueAt(i, 0);
            Producto producto = productoDAO.buscarPorCodigo(codigo);
            if (producto != null) {
                modelo.setValueAt(FormateadorUtils.formatearMoneda(producto.getPrecio(), locale), i, 2);
            }
        }
    }
    // Cambia el idioma en las vistas y actualiza los datos formateados
    public void actualizarIdiomaEnVistas() {
        productoAnadirView.cambiarIdioma();
        productoEditarView.cambiarIdioma();
        productoEliminarView.cambiarIdioma();
        productoListaView.cambiarIdioma();
        refrescarTablaListaProductos(mi.getLocale());
    }
}
