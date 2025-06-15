package ec.edu.ups.poo.clases.vista;

import ec.edu.ups.poo.clases.modelo.Producto;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;

public class ProductoEditarView extends JFrame{
    private JPanel panelPrincipal;
    private JTextField txtBuscar;
    private JButton btnBuscar;
    private JTable tblProductos;
    private JTextField txtNombre;
    private JTextField txtPrecio;
    private JButton btnActualizar;
    private JButton btnEliminar;
    private DefaultTableModel modelo;

    public ProductoEditarView() {
        setContentPane(panelPrincipal);
        setTitle("Edición de Productos");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(800, 400);
        setLocationRelativeTo(null);
        setVisible(true);

        modelo = new DefaultTableModel();
        Object[] columnas = {"Código", "Nombre", "Precio"};
        modelo.setColumnIdentifiers(columnas);
        tblProductos.setModel(modelo);
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

    public JTable getTblProductos() {
        return tblProductos;
    }

    public void setTblProductos(JTable tblProductos) {
        this.tblProductos = tblProductos;
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

    public JButton getBtnActualizar() {
        return btnActualizar;
    }

    public void setBtnActualizar(JButton btnActualizar) {
        this.btnActualizar = btnActualizar;
    }

    public JButton getBtnEliminar() {
        return btnEliminar;
    }

    public void setBtnEliminar(JButton btnEliminar) {
        this.btnEliminar = btnEliminar;
    }
    public void cargarDatos(List<Producto> listaProductos) {
        modelo.setRowCount(0);
        for (Producto producto : listaProductos) {
            modelo.addRow(new Object[]{
                    producto.getCodigo(),
                    producto.getNombre(),
                    producto.getPrecio()
            });
        }
    }

    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }

    public void limpiarCampos() {
        txtNombre.setText("");
        txtPrecio.setText("");
    }

    public int getCodigoProductoSeleccionado() {
        int fila = tblProductos.getSelectedRow();
        if (fila != -1) {
            return (int) tblProductos.getValueAt(fila, 0);
        }
        return -1;
    }
}
