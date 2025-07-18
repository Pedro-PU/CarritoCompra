package ec.edu.ups.poo.clases.vista.carrito;

import ec.edu.ups.poo.clases.modelo.Carrito;
import ec.edu.ups.poo.clases.modelo.ItemCarrito;
import ec.edu.ups.poo.clases.modelo.Producto;
import ec.edu.ups.poo.clases.util.FormateadorUtils;
import ec.edu.ups.poo.clases.util.MensajeInternacionalizacionHandler;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.net.URL;
import java.util.Locale;
/**
 * Clase que representa la vista para eliminar carritos de compra del sistema.
 * Permite buscar un carrito por código, visualizar su contenido y eliminarlo tras confirmación.
 */
public class CarritoEliminarView extends JInternalFrame {
    private JTextField txtCodigo;
    private JButton btnBuscar;
    private JTextField txtFecha;
    private JTable tblProductos;
    private JButton btnEliminar;
    private JTextField txtSubtotal;
    private JTextField txtIVA;
    private JTextField txtTotal;
    private JPanel panelPrincipal;
    private JLabel lblBuscarCodigo;
    private JLabel lblFecha;
    private JLabel lblSubtotal;
    private JLabel lblIVA;
    private JLabel lblTotal;
    private DefaultTableModel modelo;
    private MensajeInternacionalizacionHandler mi;

    /**
     * Constructor de la vista de eliminación de carritos.
     * Configura el formulario y aplica internacionalización.
     * @param mi Handler de internacionalización.
     */
    public CarritoEliminarView(MensajeInternacionalizacionHandler mi) {
        setContentPane(panelPrincipal);
        setTitle("Edición de Carrito");
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
        inicializarImagenes();
    }

    /**
     * Muestra un mensaje en un cuadro de diálogo.
     * @param mensaje Texto del mensaje.
     */
    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }

    /**
     * Llena la tabla con los productos de un carrito.
     * @param carrito Carrito del cual se obtiene la información.
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
            };
            modelo.addRow(fila);
        }
    }

    /**
     * Muestra un cuadro de confirmación con opciones Sí/No.
     * @param mensaje Pregunta al usuario.
     * @return true si el usuario acepta; false si cancela.
     */
    public boolean mostrarMensajePregunta(String mensaje) {
        int respuesta = JOptionPane.showConfirmDialog(this, mensaje, mi.get("dialogo.title.pregunta"),
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        return respuesta == JOptionPane.YES_OPTION;
    }

    /**
     * Limpia todos los campos del formulario y la tabla.
     */
    public void limpiarCampos() {
        txtCodigo.setText("");
        txtFecha.setText("");
        txtSubtotal.setText("");
        txtIVA.setText("");
        txtTotal.setText("");
        modelo.setRowCount(0);
    }

    /**
     * Cambia los textos visibles del formulario al idioma actual.
     */
    public void cambiarIdioma() {
        mi.setLenguaje(mi.getLocale().getLanguage(), mi.getLocale().getCountry());

        setTitle(mi.get("carrito.eliminar.titulo"));
        lblBuscarCodigo.setText(mi.get("carrito.eliminar.codigo"));
        lblFecha.setText(mi.get("carrito.eliminar.fecha"));
        lblSubtotal.setText(mi.get("carrito.eliminar.subtotal"));
        lblIVA.setText(mi.get("carrito.eliminar.iva"));
        lblTotal.setText(mi.get("carrito.eliminar.total"));
        btnBuscar.setText(mi.get("carrito.eliminar.boton.buscar"));
        btnEliminar.setText(mi.get("carrito.eliminar.boton.eliminar"));

        modelo.setColumnIdentifiers(new Object[] {
                mi.get("carrito.eliminar.tabla.codigo"),
                mi.get("carrito.eliminar.tabla.nombre"),
                mi.get("carrito.eliminar.tabla.precio"),
                mi.get("carrito.eliminar.tabla.cantidad")
        });

        UIManager.put("OptionPane.yesButtonText", mi.get("dialogo.boton.si"));
        UIManager.put("OptionPane.noButtonText", mi.get("dialogo.boton.no"));
        UIManager.put("OptionPane.cancelButtonText", mi.get("dialogo.boton.cancelar"));
        UIManager.put("OptionPane.okButtonText", mi.get("dialogo.boton.aceptar"));
    }

    /**
     * Carga los íconos gráficos de los botones si los recursos están disponibles.
     */
    public void inicializarImagenes() {
        URL eliminar = CarritoEliminarView.class.getClassLoader().getResource("imagenes/eliminar.png");
        if (eliminar != null) {
            btnEliminar.setIcon(new ImageIcon(eliminar));
        } else {
            System.err.println("Error: No se ha cargado el icono de Eliminar");
        }

        URL buscar = CarritoEliminarView.class.getClassLoader().getResource("imagenes/buscar.png");
        if (buscar != null) {
            btnBuscar.setIcon(new ImageIcon(buscar));
        } else {
            System.err.println("Error: No se ha cargado el icono de Buscar");
        }
    }

    /**
     * Obtiene el label del campo para buscar el código de carrito.
     * @return JLabel del código.
     */
    public JLabel getLblBuscarCodigo() {
        return lblBuscarCodigo;
    }

    /**
     * Establece el label del campo para buscar el código de carrito.
     * @param lblBuscarCodigo JLabel del código.
     */
    public void setLblBuscarCodigo(JLabel lblBuscarCodigo) {
        this.lblBuscarCodigo = lblBuscarCodigo;
    }

    /**
     * Obtiene el label que indica la fecha del carrito.
     * @return JLabel de la fecha.
     */
    public JLabel getLblFecha() {
        return lblFecha;
    }

    /**
     * Establece el label que indica la fecha del carrito.
     * @param lblFecha JLabel de la fecha.
     */
    public void setLblFecha(JLabel lblFecha) {
        this.lblFecha = lblFecha;
    }

    /**
     * Obtiene el label del subtotal del carrito.
     * @return JLabel del subtotal.
     */
    public JLabel getLblSubtotal() {
        return lblSubtotal;
    }

    /**
     * Establece el label del subtotal del carrito.
     * @param lblSubtotal JLabel del subtotal.
     */
    public void setLblSubtotal(JLabel lblSubtotal) {
        this.lblSubtotal = lblSubtotal;
    }

    /**
     * Obtiene el label del IVA del carrito.
     * @return JLabel del IVA.
     */
    public JLabel getLblIVA() {
        return lblIVA;
    }

    /**
     * Establece el label del IVA del carrito.
     * @param lblIVA JLabel del IVA.
     */
    public void setLblIVA(JLabel lblIVA) {
        this.lblIVA = lblIVA;
    }

    /**
     * Obtiene el label del total del carrito.
     * @return JLabel del total.
     */
    public JLabel getLblTotal() {
        return lblTotal;
    }

    /**
     * Establece el label del total del carrito.
     * @param lblTotal JLabel del total.
     */
    public void setLblTotal(JLabel lblTotal) {
        this.lblTotal = lblTotal;
    }

    /**
     * Limpia el contenido de la tabla de productos.
     */
    public void limpiarTabla() {
        modelo.setRowCount(0);
    }

    /**
     * Obtiene el campo de texto para el código del carrito.
     * @return JTextField del código.
     */
    public JTextField getTxtCodigo() {
        return txtCodigo;
    }

    /**
     * Establece el campo de texto para el código del carrito.
     * @param txtCodigo JTextField del código.
     */
    public void setTxtCodigo(JTextField txtCodigo) {
        this.txtCodigo = txtCodigo;
    }

    /**
     * Obtiene el botón para buscar carritos.
     * @return JButton de búsqueda.
     */
    public JButton getBtnBuscar() {
        return btnBuscar;
    }

    /**
     * Establece el botón para buscar carritos.
     * @param btnBuscar JButton de búsqueda.
     */
    public void setBtnBuscar(JButton btnBuscar) {
        this.btnBuscar = btnBuscar;
    }

    /**
     * Obtiene el campo de texto de la fecha del carrito.
     * @return JTextField de la fecha.
     */
    public JTextField getTxtFecha() {
        return txtFecha;
    }

    /**
     * Establece el campo de texto de la fecha del carrito.
     * @param txtFecha JTextField de la fecha.
     */
    public void setTxtFecha(JTextField txtFecha) {
        this.txtFecha = txtFecha;
    }

    /**
     * Obtiene la tabla que lista los productos del carrito.
     * @return JTable de productos.
     */
    public JTable getTblProductos() {
        return tblProductos;
    }

    /**
     * Establece la tabla que lista los productos del carrito.
     * @param tblProductos JTable de productos.
     */
    public void setTblProductos(JTable tblProductos) {
        this.tblProductos = tblProductos;
    }

    /**
     * Obtiene el botón para eliminar un carrito.
     * @return JButton de eliminar.
     */
    public JButton getBtnEliminar() {
        return btnEliminar;
    }

    /**
     * Establece el botón para eliminar un carrito.
     * @param btnEliminar JButton de eliminar.
     */
    public void setBtnEliminar(JButton btnEliminar) {
        this.btnEliminar = btnEliminar;
    }

    /**
     * Obtiene el campo de texto del subtotal calculado.
     * @return JTextField del subtotal.
     */
    public JTextField getTxtSubtotal() {
        return txtSubtotal;
    }

    /**
     * Establece el campo de texto del subtotal calculado.
     * @param txtSubtotal JTextField del subtotal.
     */
    public void setTxtSubtotal(JTextField txtSubtotal) {
        this.txtSubtotal = txtSubtotal;
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
