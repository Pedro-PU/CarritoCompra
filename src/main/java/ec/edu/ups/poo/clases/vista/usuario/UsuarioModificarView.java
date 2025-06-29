package ec.edu.ups.poo.clases.vista.usuario;

import ec.edu.ups.poo.clases.util.MensajeInternacionalizacionHandler;

import javax.swing.*;

public class UsuarioModificarView extends JInternalFrame {
    private JTextField txtName;
    private JButton btnBuscar;
    private JTextField txtContrasenia;
    private JButton btnEditar;
    private JPanel panelPrincipal;
    private JTextField txtUsername;
    private JLabel lblBuscar;
    private JLabel lblUsername;
    private JLabel lblContrasenia;
    private MensajeInternacionalizacionHandler mi;

    public UsuarioModificarView(MensajeInternacionalizacionHandler mi) {
        super("Editar Usuarios", true,true,false,true);
        this.mi = mi;
        setContentPane(panelPrincipal);
        setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);
        setSize(500, 500);
        cambiarIdioma();
    }

    public void cambiarIdioma() {
        setTitle(mi.get("usuario.modificar.titulo.ventana"));
        lblBuscar.setText(mi.get("usuario.modificar.buscar"));
        lblUsername.setText(mi.get("usuario.modificar.nombre"));
        lblContrasenia.setText(mi.get("usuario.modificar.contrasena"));
        btnBuscar.setText(mi.get("usuario.modificar.buscar.btn"));
        btnEditar.setText(mi.get("usuario.modificar.editar.btn"));
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
        txtName.setText("");
        txtUsername.setText("");
        txtContrasenia.setText("");
    }

    public JTextField getTxtName() {
        return txtName;
    }

    public void setTxtName(JTextField txtName) {
        this.txtName = txtName;
    }

    public JTextField getTxtUsername() {
        return txtUsername;
    }

    public void setTxtUsername(JTextField txtUsername) {
        this.txtUsername = txtUsername;
    }

    public JButton getBtnBuscar() {
        return btnBuscar;
    }

    public void setBtnBuscar(JButton btnBuscar) {
        this.btnBuscar = btnBuscar;
    }

    public JTextField getTxtContrasenia() {
        return txtContrasenia;
    }

    public void setTxtContrasenia(JTextField txtContrasenia) {
        this.txtContrasenia = txtContrasenia;
    }

    public JButton getBtnEditar() {
        return btnEditar;
    }

    public void setBtnEditar(JButton btnEliminar) {
        this.btnEditar = btnEliminar;
    }
}
