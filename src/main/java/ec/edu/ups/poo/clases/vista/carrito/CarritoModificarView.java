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
 * Clase que representa la vista para modificar carritos de compra.
 * Permite buscar un carrito, visualizar sus productos, editar cantidades y mostrar totales con formato local.
 */
public class CarritoModificarView extends JInternalFrame {
    private JTextField txtCodigo;
    private JButton btnBuscar;
    private JTextField txtFecha;
    private JTable tblProductos;
    private JTextField txtSubtotal;
    private JTextField txtIVA;
    private JTextField txtTotal;
    private JButton btnEditar;
    private JPanel panelPrincipal;
    private JLabel lblBuscarCodigo;
    private JLabel lblFecha;
    private DefaultTableModel modelo;
    private MensajeInternacionalizacionHandler mi;

    /**
     * Constructor de la vista para modificar un carrito.
     * Configura el formulario, inicializa íconos e idioma.
     * @param mi Handler de internacionalización.
     */
    public CarritoModificarView(MensajeInternacionalizacionHandler mi) {
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
     * Muestra un mensaje emergente al usuario.
     * @param mensaje Texto del mensaje.
     */
    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }

    /**
     * Carga los productos del carrito en la tabla, mostrando precio formateado.
     * @param carrito Carrito que contiene los productos.
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
                    itemCarrito.getCantidad()
            };
            modelo.addRow(fila);
        }
    }

    /**
     * Solicita al usuario ingresar una nueva cantidad si acepta la confirmación.
     * @param mensaje Texto de confirmación.
     * @return Nueva cantidad como String o null si no fue ingresada.
     */
    public String cantidad(String mensaje) {
        if (mostrarMensajePregunta(mensaje)) {
            String respuesta = JOptionPane.showInputDialog(this, mi.get("carrito.modificar.ingresar.cantidad"));
            if (respuesta != null && !respuesta.trim().isEmpty()) {
                return respuesta.trim();
            }
        }
        return null;
    }

    /**
     * Muestra un cuadro de confirmación con opciones Sí/No.
     * @param mensaje Texto del mensaje.
     * @return true si el usuario responde "Sí"; false si responde "No".
     */
    public boolean mostrarMensajePregunta(String mensaje) {
        int respuesta = JOptionPane.showConfirmDialog(this, mensaje, mi.get("dialogo.titulo.confirmacion"),
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        return respuesta == JOptionPane.YES_OPTION;
    }

    /**
     * Cambia los textos visibles de la interfaz según el idioma actual.
     */
    public void cambiarIdioma() {
        mi.setLenguaje(mi.getLocale().getLanguage(), mi.getLocale().getCountry());

        setTitle(mi.get("carrito.modificar.titulo.ventana"));
        lblBuscarCodigo.setText(mi.get("carrito.modificar.buscar.codigo"));
        lblFecha.setText(mi.get("carrito.modificar.fecha"));

        btnBuscar.setText(mi.get("carrito.modificar.boton.buscar"));
        btnEditar.setText(mi.get("carrito.modificar.boton.editar"));

        Object[] columnas = {
                mi.get("carrito.modificar.columna.codigo"),
                mi.get("carrito.modificar.columna.nombre"),
                mi.get("carrito.modificar.columna.precio"),
                mi.get("carrito.modificar.columna.cantidad")
        };
        modelo.setColumnIdentifiers(columnas);

        UIManager.put("OptionPane.yesButtonText", mi.get("dialogo.boton.si"));
        UIManager.put("OptionPane.noButtonText", mi.get("dialogo.boton.no"));
        UIManager.put("OptionPane.cancelButtonText", mi.get("dialogo.boton.cancelar"));
        UIManager.put("OptionPane.okButtonText", mi.get("dialogo.boton.aceptar"));
    }

    /**
     * Asigna los íconos a los botones de buscar y editar si los recursos están disponibles.
     */
    public void inicializarImagenes() {
        URL editar = CarritoModificarView.class.getClassLoader().getResource("imagenes/editar.png");
        if (editar != null) {
            btnEditar.setIcon(new ImageIcon(editar));
        } else {
            System.err.println("Error: No se ha cargado el icono de Editar");
        }

        URL buscar = CarritoModificarView.class.getClassLoader().getResource("imagenes/buscar.png");
        if (buscar != null) {
            btnBuscar.setIcon(new ImageIcon(buscar));
        } else {
            System.err.println("Error: No se ha cargado el icono de Buscar");
        }
    }

    /**
     * Obtiene el label del campo para buscar el código del carrito.
     * @return JLabel del código de búsqueda.
     */
    public JLabel getLblBuscarCodigo() {
        return lblBuscarCodigo;
    }

    /**
     * Establece el label del campo para buscar el código del carrito.
     * @param lblBuscarCodigo JLabel del código de búsqueda.
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
     * Obtiene el botón para buscar un carrito.
     * @return JButton de búsqueda.
     */
    public JButton getBtnBuscar() {
        return btnBuscar;
    }

    /**
     * Establece el botón para buscar un carrito.
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
     * Obtiene el campo de texto que muestra el subtotal calculado.
     * @return JTextField del subtotal.
     */
    public JTextField getTxtSubtotal() {
        return txtSubtotal;
    }

    /**
     * Establece el campo de texto que muestra el subtotal calculado.
     * @param txtSubtotal JTextField del subtotal.
     */
    public void setTxtSubtotal(JTextField txtSubtotal) {
        this.txtSubtotal = txtSubtotal;
    }

    /**
     * Obtiene el campo de texto que muestra el IVA calculado.
     * @return JTextField del IVA.
     */
    public JTextField getTxtIVA() {
        return txtIVA;
    }

    /**
     * Establece el campo de texto que muestra el IVA calculado.
     * @param txtIVA JTextField del IVA.
     */
    public void setTxtIVA(JTextField txtIVA) {
        this.txtIVA = txtIVA;
    }

    /**
     * Obtiene el campo de texto que muestra el total calculado.
     * @return JTextField del total.
     */
    public JTextField getTxtTotal() {
        return txtTotal;
    }

    /**
     * Establece el campo de texto que muestra el total calculado.
     * @param txtTotal JTextField del total.
     */
    public void setTxtTotal(JTextField txtTotal) {
        this.txtTotal = txtTotal;
    }

    /**
     * Obtiene el botón para editar cantidades o productos del carrito.
     * @return JButton de edición.
     */
    public JButton getBtnEditar() {
        return btnEditar;
    }

    /**
     * Establece el botón para editar cantidades o productos del carrito.
     * @param btnEditar JButton de edición.
     */
    public void setBtnEditar(JButton btnEditar) {
        this.btnEditar = btnEditar;
    }

    /**
     * Obtiene el modelo de la tabla de productos del carrito.
     * @return DefaultTableModel del modelo.
     */
    public DefaultTableModel getModelo() {
        return modelo;
    }

    /**
     * Establece el modelo de la tabla de productos del carrito.
     * @param modelo DefaultTableModel del modelo.
     */
    public void setModelo(DefaultTableModel modelo) {
        this.modelo = modelo;
    }
}
