package ec.edu.ups.poo.clases.vista.producto;

import ec.edu.ups.poo.clases.util.MensajeInternacionalizacionHandler;

import javax.swing.*;

public class ProductoEliminarView extends JInternalFrame {
    private JPanel panelPrincipal;
    private JTextField txtBuscar;
    private JButton btnBuscar;
    private JTextField txtNombre;
    private JTextField txtPrecio;
    private JButton btnEliminar;
    private JLabel lblCodigo;
    private JLabel lblNombre;
    private JLabel lblPrecio;
    private MensajeInternacionalizacionHandler mi;

    public ProductoEliminarView(MensajeInternacionalizacionHandler mi){
        setContentPane(panelPrincipal);
        setTitle("Edición de Productos");
        setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);
        setSize(500, 500);
        //setResizable(false);
        //setLocation(100,500);
        //setVisible(true);
        //pack();
        setClosable(true);
        setMaximizable(true);
        setResizable(true);
        setIconifiable(true);
        this.mi = mi;
        cambiarIdioma();
    }

    public JLabel getLblCodigo() {
        return lblCodigo;
    }

    public void setLblCodigo(JLabel lblCodigo) {
        this.lblCodigo = lblCodigo;
    }

    public JLabel getLblNombre() {
        return lblNombre;
    }

    public void setLblNombre(JLabel lblNombre) {
        this.lblNombre = lblNombre;
    }

    public JLabel getLblPrecio() {
        return lblPrecio;
    }

    public void setLblPrecio(JLabel lblPrecio) {
        this.lblPrecio = lblPrecio;
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
    public void cambiarIdioma() {
        if (mi == null) return;

        setTitle(mi.get("producto.eliminar.titulo.ventana"));

        if (lblCodigo != null) lblCodigo.setText(mi.get("producto.eliminar.codigo"));
        if (lblNombre != null) lblNombre.setText(mi.get("producto.eliminar.nombre"));
        if (lblPrecio != null) lblPrecio.setText(mi.get("producto.eliminar.precio"));

        if (btnBuscar != null) btnBuscar.setText(mi.get("producto.eliminar.buscar"));
        if (btnEliminar != null) btnEliminar.setText(mi.get("producto.eliminar.eliminar"));

        UIManager.put("OptionPane.yesButtonText", mi.get("dialogo.boton.si"));
        UIManager.put("OptionPane.noButtonText", mi.get("dialogo.boton.no"));
        UIManager.put("OptionPane.cancelButtonText", mi.get("dialogo.boton.cancelar"));
        UIManager.put("OptionPane.okButtonText", mi.get("dialogo.boton.aceptar"));
    }

}
