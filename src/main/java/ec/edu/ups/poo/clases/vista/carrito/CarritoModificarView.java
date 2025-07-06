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

    public CarritoModificarView(MensajeInternacionalizacionHandler mi) {
        setContentPane(panelPrincipal);
        setTitle("Edicion de Carrito");
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
    // Muestra un mensaje emergente al usuario
    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }
    // Carga los productos del carrito en la tabla con formato de moneda
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
    // Solicita al usuario ingresar una nueva cantidad si acepta la confirmación
    public String cantidad(String mensaje) {
        if (mostrarMensajePregunta(mensaje)) {
            String respuesta = JOptionPane.showInputDialog(this, mi.get("carrito.modificar.ingresar.cantidad"));
            if (respuesta != null && !respuesta.trim().isEmpty()) {
                return respuesta.trim();
            }
        }
        return null;
    }
    // Muestra un diálogo de confirmación y devuelve si el usuario aceptó
    public boolean mostrarMensajePregunta(String mensaje) {
        int respuesta = JOptionPane.showConfirmDialog(this, mensaje, mi.get("dialogo.titulo.confirmacion"),
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        return respuesta == JOptionPane.YES_OPTION;
    }
    // Cambia los textos de la interfaz según el idioma actual
    public void cambiarIdioma(){
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
                mi.get("carrito.modificar.columna.cantidad"),
        };
        modelo.setColumnIdentifiers(columnas);

        UIManager.put("OptionPane.yesButtonText", mi.get("dialogo.boton.si"));
        UIManager.put("OptionPane.noButtonText", mi.get("dialogo.boton.no"));
        UIManager.put("OptionPane.cancelButtonText", mi.get("dialogo.boton.cancelar"));
        UIManager.put("OptionPane.okButtonText", mi.get("dialogo.boton.aceptar"));
    }
    // Asigna los íconos a los botones de buscar y editar
    public void inicializarImagenes(){
        URL eliminar = CarritoModificarView.class.getClassLoader().getResource("imagenes/editar.png");
        if (eliminar != null) {
            ImageIcon iconoBtnIniciarSesion = new ImageIcon(eliminar);
            btnEditar.setIcon(iconoBtnIniciarSesion);
        } else {
            System.err.println("Error: No se ha cargado el icono de Login");
        }

        URL buscar = CarritoModificarView.class.getClassLoader().getResource("imagenes/buscar.png");
        if (buscar != null) {
            ImageIcon iconoBtnRegistrarse = new ImageIcon(buscar);
            btnBuscar.setIcon(iconoBtnRegistrarse);
        } else {
            System.err.println("Error: No se ha cargado el icono de Registrarse");
        }
    }
    //Getters y Setters
    public JLabel getLblBuscarCodigo() {
        return lblBuscarCodigo;
    }

    public void setLblBuscarCodigo(JLabel lblBuscarCodigo) {
        this.lblBuscarCodigo = lblBuscarCodigo;
    }

    public JLabel getLblFecha() {
        return lblFecha;
    }

    public void setLblFecha(JLabel lblFecha) {
        this.lblFecha = lblFecha;
    }

    public JTextField getTxtCodigo() {
        return txtCodigo;
    }

    public void setTxtCodigo(JTextField txtCodigo) {
        this.txtCodigo = txtCodigo;
    }

    public JButton getBtnBuscar() {
        return btnBuscar;
    }

    public void setBtnBuscar(JButton btnBuscar) {
        this.btnBuscar = btnBuscar;
    }

    public JTextField getTxtFecha() {
        return txtFecha;
    }

    public void setTxtFecha(JTextField txtFecha) {
        this.txtFecha = txtFecha;
    }

    public JTable getTblProductos() {
        return tblProductos;
    }

    public void setTblProductos(JTable tblProductos) {
        this.tblProductos = tblProductos;
    }

    public JTextField getTxtSubtotal() {
        return txtSubtotal;
    }

    public void setTxtSubtotal(JTextField txtSubtotal) {
        this.txtSubtotal = txtSubtotal;
    }

    public JTextField getTxtIVA() {
        return txtIVA;
    }

    public void setTxtIVA(JTextField txtIVA) {
        this.txtIVA = txtIVA;
    }

    public JTextField getTxtTotal() {
        return txtTotal;
    }

    public void setTxtTotal(JTextField txtTotal) {
        this.txtTotal = txtTotal;
    }

    public JButton getBtnEditar() {
        return btnEditar;
    }

    public void setBtnEditar(JButton btnEditar) {
        this.btnEditar = btnEditar;
    }

    public DefaultTableModel getModelo() {
        return modelo;
    }

    public void setModelo(DefaultTableModel modelo) {
        this.modelo = modelo;
    }
}
