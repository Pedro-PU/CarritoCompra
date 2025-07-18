package ec.edu.ups.poo.clases.vista.carrito;

import ec.edu.ups.poo.clases.modelo.Carrito;
import ec.edu.ups.poo.clases.modelo.ItemCarrito;
import ec.edu.ups.poo.clases.modelo.Producto;
import ec.edu.ups.poo.clases.util.FormateadorUtils;
import ec.edu.ups.poo.clases.util.MensajeInternacionalizacionHandler;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.Locale;
/**
 * Clase que representa la ventana de detalle de un carrito de compras.
 * Permite visualizar los productos añadidos al carrito, sus cantidades, precios y totales calculados.
 */
public class CarritoDetalleView extends JInternalFrame{
    private JPanel panelPrincipal;
    private JTable tblProductos;
    private JTextField txtSubTotal;
    private JTextField txtIVA;
    private JTextField txtTotal;
    private JLabel lblDetalleCarrito;
    private JLabel lblSubtotal;
    private JLabel lblIVA;
    private JLabel lblTotal;
    private DefaultTableModel modelo;
    private MensajeInternacionalizacionHandler mi;

    /**
     * Constructor de la vista de detalle de carrito.
     * Inicializa componentes, configura la tabla y aplica idioma desde el handler.
     * @param mi Handler de internacionalización.
     */
    public CarritoDetalleView(MensajeInternacionalizacionHandler mi) {
        setContentPane(panelPrincipal);
        setTitle("Listado de Carritos");
        setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);
        setSize(500, 500);
        setClosable(true);
        setMaximizable(true);
        setResizable(true);
        setIconifiable(true);

        modelo = new DefaultTableModel();
        tblProductos.setModel(modelo);

        this.mi = mi;
        cambiarIdioma();
    }

    /**
     * Llena la tabla de productos con los datos del carrito recibido.
     * Calcula el subtotal por producto considerando cantidad y precio.
     * @param carrito Objeto carrito con la lista de productos.
     */
    public void cargarDatos(Carrito carrito) {
        modelo.setRowCount(0);
        Locale locale = mi.getLocale();

        for (ItemCarrito itemCarrito : carrito.obtenerItems()) {
            Producto producto = itemCarrito.getProducto();
            Object[] fila = {
                    producto.getCodigo(),
                    producto.getNombre(),
                    FormateadorUtils.formatearMoneda(producto.getPrecio(), locale),
                    itemCarrito.getCantidad(),
                    FormateadorUtils.formatearMoneda(producto.getPrecio() * itemCarrito.getCantidad(), locale)
            };
            modelo.addRow(fila);
        }
    }

    /**
     * Muestra un mensaje simple en un cuadro de diálogo.
     * @param mensaje Texto a mostrar.
     */
    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }

    /**
     * Cambia los textos visibles de la interfaz según el idioma actual.
     * Incluye títulos, etiquetas y columnas de la tabla.
     */
    public void cambiarIdioma() {
        mi.setLenguaje(mi.getLocale().getLanguage(), mi.getLocale().getCountry());

        setTitle(mi.get("carrito.detalle.titulo.ventana"));
        lblDetalleCarrito.setText(mi.get("carrito.detalle.titulo"));
        lblSubtotal.setText(mi.get("carrito.detalle.subtotal"));
        lblIVA.setText(mi.get("carrito.detalle.iva"));
        lblTotal.setText(mi.get("carrito.detalle.total"));

        Object[] columnas = {
                mi.get("carrito.detalle.columna.codigo"),
                mi.get("carrito.detalle.columna.nombre"),
                mi.get("carrito.detalle.columna.precio"),
                mi.get("carrito.detalle.columna.cantidad"),
                mi.get("carrito.detalle.columna.subtotal")
        };
        modelo.setColumnIdentifiers(columnas);

        UIManager.put("OptionPane.yesButtonText", mi.get("dialogo.boton.si"));
        UIManager.put("OptionPane.noButtonText", mi.get("dialogo.boton.no"));
        UIManager.put("OptionPane.cancelButtonText", mi.get("dialogo.boton.cancelar"));
        UIManager.put("OptionPane.okButtonText", mi.get("dialogo.boton.aceptar"));
    }
    /**
     * Obtiene el label del encabezado de la vista de detalle de carrito.
     * @return JLabel del detalle del carrito.
     */
    public JLabel getLblDetalleCarrito() {
        return lblDetalleCarrito;
    }

    /**
     * Establece el label del encabezado de la vista de detalle de carrito.
     * @param lblDetalleCarrito JLabel del detalle del carrito.
     */
    public void setLblDetalleCarrito(JLabel lblDetalleCarrito) {
        this.lblDetalleCarrito = lblDetalleCarrito;
    }

    /**
     * Obtiene el label del campo subtotal.
     * @return JLabel del subtotal.
     */
    public JLabel getLblSubtotal() {
        return lblSubtotal;
    }

    /**
     * Establece el label del campo subtotal.
     * @param lblSubtotal JLabel del subtotal.
     */
    public void setLblSubtotal(JLabel lblSubtotal) {
        this.lblSubtotal = lblSubtotal;
    }

    /**
     * Obtiene el label del campo IVA.
     * @return JLabel del IVA.
     */
    public JLabel getLblIVA() {
        return lblIVA;
    }

    /**
     * Establece el label del campo IVA.
     * @param lblIVA JLabel del IVA.
     */
    public void setLblIVA(JLabel lblIVA) {
        this.lblIVA = lblIVA;
    }

    /**
     * Obtiene el label del campo total.
     * @return JLabel del total.
     */
    public JLabel getLblTotal() {
        return lblTotal;
    }

    /**
     * Establece el label del campo total.
     * @param lblTotal JLabel del total.
     */
    public void setLblTotal(JLabel lblTotal) {
        this.lblTotal = lblTotal;
    }

    /**
     * Obtiene la tabla que muestra los productos del carrito.
     * @return JTable de productos.
     */
    public JTable getTblProductos() {
        return tblProductos;
    }

    /**
     * Establece la tabla que muestra los productos del carrito.
     * @param tblProductos JTable de productos.
     */
    public void setTblProductos(JTable tblProductos) {
        this.tblProductos = tblProductos;
    }

    /**
     * Obtiene el campo de texto del subtotal calculado.
     * @return JTextField del subtotal.
     */
    public JTextField getTxtSubTotal() {
        return txtSubTotal;
    }

    /**
     * Establece el campo de texto del subtotal calculado.
     * @param txtSubTotal JTextField del subtotal.
     */
    public void setTxtSubTotal(JTextField txtSubTotal) {
        this.txtSubTotal = txtSubTotal;
    }

    /**
     * Obtiene el campo de texto del IVA calculado.
     * @return JTextField del IVA.
     */
    public JTextField getTxtIVA() {
        return txtIVA;
    }

    /**
     * Establece el campo de texto del IVA calculado.
     * @param txtIVA JTextField del IVA.
     */
    public void setTxtIVA(JTextField txtIVA) {
        this.txtIVA = txtIVA;
    }

    /**
     * Obtiene el campo de texto del total calculado.
     * @return JTextField del total.
     */
    public JTextField getTxtTotal() {
        return txtTotal;
    }

    /**
     * Establece el campo de texto del total calculado.
     * @param txtTotal JTextField del total.
     */
    public void setTxtTotal(JTextField txtTotal) {
        this.txtTotal = txtTotal;
    }

    /**
     * Obtiene el modelo de la tabla de productos.
     * @return DefaultTableModel del modelo.
     */
    public DefaultTableModel getModelo() {
        return modelo;
    }

    /**
     * Establece el modelo de la tabla de productos.
     * @param modelo DefaultTableModel del modelo.
     */
    public void setModelo(DefaultTableModel modelo) {
        this.modelo = modelo;
    }


}
