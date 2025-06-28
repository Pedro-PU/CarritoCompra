package ec.edu.ups.poo.clases.vista.carrito;

import ec.edu.ups.poo.clases.modelo.Carrito;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;

public class CarritoListaView extends JInternalFrame{
    private JPanel panelPrincipal;
    private JTextField txtBuscar;
    private JButton btnBuscar;
    private JButton btnListar;
    private JTable tblProductos;
    private JButton btnDetalle;
    private DefaultTableModel modelo;
    public CarritoListaView() {
        setContentPane(panelPrincipal);
        setTitle("Listado de Carritos");
        setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);
        setSize(500, 500);
        setClosable(true);
        setMaximizable(true);
        setResizable(true);
        setIconifiable(true);

        modelo = new DefaultTableModel();
        Object[] columnas = {"Codigo", "Usuario", "Fecha", "Subtotal", "IVA", "Total"};
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

    public JButton getBtnListar() {
        return btnListar;
    }

    public void setBtnListar(JButton btnListar) {
        this.btnListar = btnListar;
    }

    public JTable getTblProductos() {
        return tblProductos;
    }

    public void setTblProductos(JTable tblProductos) {
        this.tblProductos = tblProductos;
    }

    public JButton getBtnDetalle() {
        return btnDetalle;
    }

    public void setBtnDetalle(JButton btnDetalle) {
        this.btnDetalle = btnDetalle;
    }

    public DefaultTableModel getModelo() {
        return modelo;
    }

    public void setModelo(DefaultTableModel modelo) {
        this.modelo = modelo;
    }

    public void cargarDatos(List<Carrito> listaCarritos) {
        modelo.setNumRows(0);

        for (Carrito carrito : listaCarritos) {
            System.out.println("Cargando carrito: Código = " + carrito.getCodigo() +
                    ", Subtotal = " + carrito.calcularSubtotal());

            String fechaFormateada = carrito.getFechaCreacion();

            Object[] fila = {
                    carrito.getCodigo(),
                    carrito.getUsuario().getUsername(),
                    fechaFormateada,
                    String.format("%.2f", carrito.calcularSubtotal()),
                    String.format("%.2f", carrito.calcularIVA()),
                    String.format("%.2f", carrito.calcularTotal())
            };
            modelo.addRow(fila);
        }
    }

}
