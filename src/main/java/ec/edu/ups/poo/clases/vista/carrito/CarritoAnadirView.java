package ec.edu.ups.poo.clases.vista.carrito;

import ec.edu.ups.poo.clases.util.MensajeInternacionalizacionHandler;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.net.URL;
/**
 * Clase que representa la ventana para añadir productos al carrito de compras.
 * Permite buscar productos, seleccionar cantidad, calcular totales e interactuar con la lista de productos del carrito.
 */
public class CarritoAnadirView extends JInternalFrame {
    private JPanel panelPrincipal;
    private JTextField txtBuscar;
    private JButton btnBuscar;
    private JTextField txtNombre;
    private JTextField txtPrecio;
    private JButton btnAnadir;
    private JTable tblProductos;
    private JTextField txtSubTotal;
    private JTextField txtIVA;
    private JTextField txtTotal;
    private JButton btnAceptar;
    private JButton btnLimpiar;
    private JComboBox cbxCantidad;
    private JButton btnBorrar;
    private JLabel lblBuscarCodigo;
    private JLabel lblNombreProducto;
    private JLabel lblPrecioProducto;
    private JLabel lblCantidad;
    private JLabel lblSubtotal;
    private JLabel lblIVA;
    private JLabel lblTotal;
    private DefaultTableModel modelo;
    private MensajeInternacionalizacionHandler mi;
    /**
     * Constructor de la ventana de carrito de compras.
     * Inicializa componentes, carga datos iniciales, configura idioma e íconos.
     * @param mi Handler de internacionalización.
     */
    public CarritoAnadirView(MensajeInternacionalizacionHandler mi) {
        super("Carrito de Compras", true,true,false,true);
        setContentPane(panelPrincipal);
        setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);
        setSize(500, 500);

        modelo = new DefaultTableModel();
        tblProductos.setModel(modelo);

        cargarDatos();
        this.mi = mi;
        cambiarIdioma();
        inicializarImagenes();
    }
    /**
     * Llena el combo box de cantidad con los valores del 1 al 20.
     */
    private void cargarDatos() {
        cbxCantidad.removeAllItems();
        for(int i = 0; i < 20; i++){
            cbxCantidad.addItem(String.valueOf(i+1));
        }
    }
    /**
     * Cambia todos los textos visibles según el idioma seleccionado.
     * Incluye etiquetas, títulos, botones y columnas de tabla.
     */
    public void cambiarIdioma() {
        mi.setLenguaje(mi.getLocale().getLanguage(), mi.getLocale().getCountry());

        setTitle(mi.get("carrito.anadir.titulo"));
        lblBuscarCodigo.setText(mi.get("carrito.anadir.buscar.codigo"));
        lblNombreProducto.setText(mi.get("carrito.anadir.nombre"));
        lblPrecioProducto.setText(mi.get("carrito.anadir.precio"));
        lblCantidad.setText(mi.get("carrito.anadir.cantidad"));
        lblSubtotal.setText(mi.get("carrito.anadir.subtotal"));
        lblIVA.setText(mi.get("carrito.anadir.iva"));
        lblTotal.setText(mi.get("carrito.anadir.total"));

        btnBuscar.setText(mi.get("carrito.anadir.boton.buscar"));
        btnAnadir.setText(mi.get("carrito.anadir.boton.anadir"));
        btnAceptar.setText(mi.get("carrito.anadir.boton.aceptar"));
        btnLimpiar.setText(mi.get("carrito.anadir.boton.limpiar"));
        btnBorrar.setText(mi.get("carrito.anadir.boton.borrar"));

        modelo.setColumnIdentifiers(new Object[]{
                mi.get("carrito.anadir.tabla.codigo"),
                mi.get("carrito.anadir.tabla.nombre"),
                mi.get("carrito.anadir.tabla.precio"),
                mi.get("carrito.anadir.tabla.cantidad"),
                mi.get("carrito.anadir.tabla.subtotal")
        });
        UIManager.put("OptionPane.yesButtonText", mi.get("dialogo.boton.si"));
        UIManager.put("OptionPane.noButtonText", mi.get("dialogo.boton.no"));
        UIManager.put("OptionPane.cancelButtonText", mi.get("dialogo.boton.cancelar"));
        UIManager.put("OptionPane.okButtonText", mi.get("dialogo.boton.aceptar"));
    }
    /**
     * Carga íconos a los botones del formulario si los recursos están disponibles.
     * Asocia imágenes específicas a cada acción del carrito.
     */
    public void inicializarImagenes(){
        URL aceptar = CarritoAnadirView.class.getClassLoader().getResource("imagenes/aceptar.png");
        if (aceptar != null) {
            ImageIcon iconoBtnIniciarSesion = new ImageIcon(aceptar);
            btnAceptar.setIcon(iconoBtnIniciarSesion);
        } else {
            System.err.println("Error: No se ha cargado el icono de Login");
        }

        URL limpiar = CarritoAnadirView.class.getClassLoader().getResource("imagenes/limpiar.png");
        if (limpiar != null) {
            ImageIcon iconoBtnRegistrarse = new ImageIcon(limpiar);
            btnLimpiar.setIcon(iconoBtnRegistrarse);
        } else {
            System.err.println("Error: No se ha cargado el icono de Registrarse");
        }

        URL anadir = CarritoAnadirView.class.getClassLoader().getResource("imagenes/anadir.png");
        if (anadir != null) {
            ImageIcon iconoBtnRegistrarse = new ImageIcon(anadir);
            btnAnadir.setIcon(iconoBtnRegistrarse);
        } else {
            System.err.println("Error: No se ha cargado el icono de Registrarse");
        }

        URL borrar = CarritoAnadirView.class.getClassLoader().getResource("imagenes/anadir.png");
        if (borrar != null) {
            ImageIcon iconoBtnRegistrarse = new ImageIcon(borrar);
            btnBorrar.setIcon(iconoBtnRegistrarse);
        } else {
            System.err.println("Error: No se ha cargado el icono de Registrarse");
        }

        URL buscar = CarritoAnadirView.class.getClassLoader().getResource("imagenes/buscar.png");
        if (buscar != null) {
            ImageIcon iconoBtnRegistrarse = new ImageIcon(buscar);
            btnBuscar.setIcon(iconoBtnRegistrarse);
        } else {
            System.err.println("Error: No se ha cargado el icono de Registrarse");
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
     * Muestra una pregunta de confirmación en un cuadro de diálogo con opciones Sí/No.
     * @param mensaje Pregunta a presentar al usuario.
     * @return true si el usuario selecciona "Sí"; false si selecciona "No".
     */
    public boolean mostrarMensajePregunta(String mensaje) {
        int respuesta = JOptionPane.showConfirmDialog(this, mensaje, "Confirmación",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        return respuesta == JOptionPane.YES_OPTION;
    }

    /**
     * Limpia los campos de texto relacionados con el nombre y precio del producto.
     */
    public void limpiarCampos() {
        txtNombre.setText("");
        txtPrecio.setText("");
    }

    /**
     * Obtiene el label para el código de búsqueda.
     * @return JLabel del código.
     */
    public JLabel getLblBuscarCodigo() {
        return lblBuscarCodigo;
    }

    /**
     * Establece el label para el código de búsqueda.
     * @param lblBuscarCodigo JLabel del código.
     */
    public void setLblBuscarCodigo(JLabel lblBuscarCodigo) {
        this.lblBuscarCodigo = lblBuscarCodigo;
    }

    /**
     * Obtiene el label del nombre del producto.
     * @return JLabel del nombre.
     */
    public JLabel getLblNombreProducto() {
        return lblNombreProducto;
    }

    /**
     * Establece el label del nombre del producto.
     * @param lblNombreProducto JLabel del nombre.
     */
    public void setLblNombreProducto(JLabel lblNombreProducto) {
        this.lblNombreProducto = lblNombreProducto;
    }

    /**
     * Obtiene el label del precio del producto.
     * @return JLabel del precio.
     */
    public JLabel getLblPrecioProducto() {
        return lblPrecioProducto;
    }

    /**
     * Establece el label del precio del producto.
     * @param lblPrecioProducto JLabel del precio.
     */
    public void setLblPrecioProducto(JLabel lblPrecioProducto) {
        this.lblPrecioProducto = lblPrecioProducto;
    }

    /**
     * Obtiene el label de cantidad seleccionada.
     * @return JLabel de cantidad.
     */
    public JLabel getLblCantidad() {
        return lblCantidad;
    }

    /**
     * Establece el label de cantidad seleccionada.
     * @param lblCantidad JLabel de cantidad.
     */
    public void setLblCantidad(JLabel lblCantidad) {
        this.lblCantidad = lblCantidad;
    }

    /**
     * Obtiene el label del subtotal.
     * @return JLabel del subtotal.
     */
    public JLabel getLblSubtotal() {
        return lblSubtotal;
    }

    /**
     * Establece el label del subtotal.
     * @param lblSubtotal JLabel del subtotal.
     */
    public void setLblSubtotal(JLabel lblSubtotal) {
        this.lblSubtotal = lblSubtotal;
    }

    /**
     * Obtiene el label del IVA.
     * @return JLabel del IVA.
     */
    public JLabel getLblIVA() {
        return lblIVA;
    }

    /**
     * Establece el label del IVA.
     * @param lblIVA JLabel del IVA.
     */
    public void setLblIVA(JLabel lblIVA) {
        this.lblIVA = lblIVA;
    }

    /**
     * Obtiene el label del total.
     * @return JLabel del total.
     */
    public JLabel getLblTotal() {
        return lblTotal;
    }

    /**
     * Establece el label del total.
     * @param lblTotal JLabel del total.
     */
    public void setLblTotal(JLabel lblTotal) {
        this.lblTotal = lblTotal;
    }

    /**
     * Obtiene el campo de texto para buscar productos por código.
     * @return JTextField de búsqueda.
     */
    public JTextField getTxtBuscar() {
        return txtBuscar;
    }

    /**
     * Establece el campo de texto para buscar productos por código.
     * @param txtBuscar JTextField de búsqueda.
     */
    public void setTxtBuscar(JTextField txtBuscar) {
        this.txtBuscar = txtBuscar;
    }

    /**
     * Obtiene el botón para iniciar la búsqueda de productos.
     * @return JButton de buscar.
     */
    public JButton getBtnBuscar() {
        return btnBuscar;
    }

    /**
     * Establece el botón para iniciar la búsqueda de productos.
     * @param btnBuscar JButton de buscar.
     */
    public void setBtnBuscar(JButton btnBuscar) {
        this.btnBuscar = btnBuscar;
    }

    /**
     * Obtiene el campo de texto del nombre del producto.
     * @return JTextField del nombre.
     */
    public JTextField getTxtNombre() {
        return txtNombre;
    }

    /**
     * Establece el campo de texto del nombre del producto.
     * @param txtNombre JTextField del nombre.
     */
    public void setTxtNombre(JTextField txtNombre) {
        this.txtNombre = txtNombre;
    }

    /**
     * Obtiene el campo de texto del precio del producto.
     * @return JTextField del precio.
     */
    public JTextField getTxtPrecio() {
        return txtPrecio;
    }

    /**
     * Establece el campo de texto del precio del producto.
     * @param txtPrecio JTextField del precio.
     */
    public void setTxtPrecio(JTextField txtPrecio) {
        this.txtPrecio = txtPrecio;
    }

    /**
     * Obtiene el botón para añadir un producto al carrito.
     * @return JButton de añadir.
     */
    public JButton getBtnAnadir() {
        return btnAnadir;
    }

    /**
     * Establece el botón para añadir un producto al carrito.
     * @param btnAnadir JButton de añadir.
     */
    public void setBtnAnadir(JButton btnAnadir) {
        this.btnAnadir = btnAnadir;
    }

    /**
     * Obtiene la tabla que lista los productos en el carrito.
     * @return JTable de productos.
     */
    public JTable getTblProductos() {
        return tblProductos;
    }

    /**
     * Establece la tabla que lista los productos en el carrito.
     * @param tblProductos JTable de productos.
     */
    public void setTblProductos(JTable tblProductos) {
        this.tblProductos = tblProductos;
    }

    /**
     * Obtiene el campo de texto del subtotal general.
     * @return JTextField del subtotal.
     */
    public JTextField getTxtSubTotal() {
        return txtSubTotal;
    }

    /**
     * Establece el campo de texto del subtotal general.
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
     * Obtiene el campo de texto del total de la compra.
     * @return JTextField del total.
     */
    public JTextField getTxtTotal() {
        return txtTotal;
    }

    /**
     * Establece el campo de texto del total de la compra.
     * @param txtTotal JTextField del total.
     */
    public void setTxtTotal(JTextField txtTotal) {
        this.txtTotal = txtTotal;
    }

    /**
     * Obtiene el botón para aceptar y continuar la compra.
     * @return JButton de aceptar.
     */
    public JButton getBtnAceptar() {
        return btnAceptar;
    }

    /**
     * Establece el botón para aceptar y continuar la compra.
     * @param btnAceptar JButton de aceptar.
     */
    public void setBtnAceptar(JButton btnAceptar) {
        this.btnAceptar = btnAceptar;
    }

    /**
     * Obtiene el botón para limpiar los campos del formulario.
     * @return JButton de limpiar.
     */
    public JButton getBtnLimpiar() {
        return btnLimpiar;
    }

    /**
     * Establece el botón para limpiar los campos del formulario.
     * @param btnLimpiar JButton de limpiar.
     */
    public void setBtnLimpiar(JButton btnLimpiar) {
        this.btnLimpiar = btnLimpiar;
    }

    /**
     * Obtiene el combo box de cantidad de productos.
     * @return JComboBox de cantidad.
     */
    public JComboBox getCbxCantidad() {
        return cbxCantidad;
    }

    /**
     * Establece el combo box de cantidad de productos.
     * @param cbxCantidad JComboBox de cantidad.
     */
    public void setCbxCantidad(JComboBox cbxCantidad) {
        this.cbxCantidad = cbxCantidad;
    }

    /**
     * Obtiene el botón para borrar un producto del carrito.
     * @return JButton de borrar.
     */
    public JButton getBtnBorrar() {
        return btnBorrar;
    }

    /**
     * Establece el botón para borrar un producto del carrito.
     * @param btnBorrar JButton de borrar.
     */
    public void setBtnBorrar(JButton btnBorrar) {
        this.btnBorrar = btnBorrar;
    }

}
