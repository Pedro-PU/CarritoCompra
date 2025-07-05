package ec.edu.ups.poo.clases.vista.carrito;

import ec.edu.ups.poo.clases.util.MensajeInternacionalizacionHandler;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.net.URL;

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
    // Llena el comboBox de cantidad con valores del 1 al 20
    private void cargarDatos() {
        cbxCantidad.removeAllItems();
        for(int i = 0; i < 20; i++){
            cbxCantidad.addItem(String.valueOf(i+1));
        }
    }
    // Cambia todos los textos visibles al idioma seleccionado
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
    // Carga y asigna íconos a los botones si están disponibles
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
    //Getters y Setters
    public JLabel getLblBuscarCodigo() {
        return lblBuscarCodigo;
    }

    public void setLblBuscarCodigo(JLabel lblBuscarCodigo) {
        this.lblBuscarCodigo = lblBuscarCodigo;
    }

    public JLabel getLblNombreProducto() {
        return lblNombreProducto;
    }

    public void setLblNombreProducto(JLabel lblNombreProducto) {
        this.lblNombreProducto = lblNombreProducto;
    }

    public JLabel getLblPrecioProducto() {
        return lblPrecioProducto;
    }

    public void setLblPrecioProducto(JLabel lblPrecioProducto) {
        this.lblPrecioProducto = lblPrecioProducto;
    }

    public JLabel getLblCantidad() {
        return lblCantidad;
    }

    public void setLblCantidad(JLabel lblCantidad) {
        this.lblCantidad = lblCantidad;
    }

    public JLabel getLblSubtotal() {
        return lblSubtotal;
    }

    public void setLblSubtotal(JLabel lblSubtotal) {
        this.lblSubtotal = lblSubtotal;
    }

    public JLabel getLblIVA() {
        return lblIVA;
    }

    public void setLblIVA(JLabel lblIVA) {
        this.lblIVA = lblIVA;
    }

    public JLabel getLblTotal() {
        return lblTotal;
    }

    public void setLblTotal(JLabel lblTotal) {
        this.lblTotal = lblTotal;
    }

    public JTextField getTxtBuscar() {
        return txtBuscar;
    }

    public void setTxtBuscar(JTextField txtBuscar) {
        this.txtBuscar = txtBuscar;
    }

    public JButton getBtnBuscar() {
        return btnBuscar;
    }

    public void setBtnBuscar(JButton btnBuscar) {
        this.btnBuscar = btnBuscar;
    }

    public JTextField getTxtNombre() {
        return txtNombre;
    }

    public void setTxtNombre(JTextField txtNombre) {
        this.txtNombre = txtNombre;
    }

    public JTextField getTxtPrecio() {
        return txtPrecio;
    }

    public void setTxtPrecio(JTextField txtPrecio) {
        this.txtPrecio = txtPrecio;
    }

    public JButton getBtnAnadir() {
        return btnAnadir;
    }

    public void setBtnAnadir(JButton btnAnadir) {
        this.btnAnadir = btnAnadir;
    }

    public JTable getTblProductos() {
        return tblProductos;
    }

    public void setTblProductos(JTable tblProductos) {
        this.tblProductos = tblProductos;
    }

    public JTextField getTxtSubTotal() {
        return txtSubTotal;
    }

    public void setTxtSubTotal(JTextField txtSubTotal) {
        this.txtSubTotal = txtSubTotal;
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

    public JButton getBtnAceptar() {
        return btnAceptar;
    }

    public void setBtnAceptar(JButton btnAceptar) {
        this.btnAceptar = btnAceptar;
    }

    public JButton getBtnLimpiar() {
        return btnLimpiar;
    }

    public void setBtnLimpiar(JButton btnLimpiar) {
        this.btnLimpiar = btnLimpiar;
    }

    public JComboBox getCbxCantidad() {
        return cbxCantidad;
    }

    public void setCbxCantidad(JComboBox cbxCantidad) {
        this.cbxCantidad = cbxCantidad;
    }

    public JButton getBtnBorrar() {
        return btnBorrar;
    }

    public void setBtnBorrar(JButton btnBorrar) {
        this.btnBorrar = btnBorrar;
    }

    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }

    public boolean mostrarMensajePregunta(String mensaje) {
        int respuesta = JOptionPane.showConfirmDialog(this, mensaje, "Confirmación",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        return respuesta == JOptionPane.YES_OPTION;
    }

    public void limpiarCampos() {
        txtNombre.setText("");
        txtPrecio.setText("");
    }
}
