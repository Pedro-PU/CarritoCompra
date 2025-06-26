package ec.edu.ups.poo.clases.vista;

import ec.edu.ups.poo.clases.modelo.Carrito;
import ec.edu.ups.poo.clases.modelo.ItemCarrito;
import ec.edu.ups.poo.clases.modelo.Producto;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

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
    private DefaultTableModel modelo;
    public CarritoModificarView() {
        setContentPane(panelPrincipal);
        setTitle("Edicion de Carrito");
        setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);
        setSize(500, 500);
        setClosable(true);
        setMaximizable(true);
        setResizable(true);
        setIconifiable(true);
        modelo = new DefaultTableModel();
        Object[] columnas = {"Código", "Nombre", "Precio","Cantidad"};
        modelo.setColumnIdentifiers(columnas);
        tblProductos.setModel(modelo);
    }

    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }

    public void cargarDatos(Carrito carrito) {
        modelo.setRowCount(0);

        for(ItemCarrito itemCarrito: carrito.obtenerItems()){
            Producto producto = itemCarrito.getProducto();
            Object[] fila = {
                    producto.getCodigo(),
                    producto.getNombre(),
                    producto.getPrecio(),
                    itemCarrito.getCantidad(),
            };
            modelo.addRow(fila);
        }
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
