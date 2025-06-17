package ec.edu.ups.poo.clases.vista;

import javax.swing.*;

public class ProductoEliminarView extends JFrame {
    private JPanel panelPrincipal;
    private JTextField txtBuscar;
    private JButton btnBuscar;
    private JTextField txtNombre;
    private JTextField txtPrecio;
    private JButton btnEliminar;
    public ProductoEliminarView(){
        setContentPane(panelPrincipal);
        setTitle("Edición de Productos");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(800, 400);
        setLocationRelativeTo(null);
        setVisible(true);
        txtNombre.setEnabled(false);
        txtPrecio.setEnabled(false);

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

    public void setTxtNombre(String nombre) {
        this.txtNombre.setText(nombre);
    }

    public JTextField getTxtPrecio() {
        return txtPrecio;
    }

    public void setTxtPrecio(String txtPrecio) {
        this.txtPrecio.setText(txtPrecio);
    }

    public JButton getBtnEliminar() {
        return btnEliminar;
    }

    public void setBtnEliminar(JButton btnEliminar) {
        this.btnEliminar = btnEliminar;
    }
    public boolean mostrarMensajePregunta(String mensaje) {
        int respuesta = JOptionPane.showConfirmDialog(this, mensaje, "Confirmación",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        return respuesta == JOptionPane.YES_OPTION;
    }

    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }
    public void limpiarCampos() {
        txtNombre.setText("");
        txtPrecio.setText("");
    }

}
