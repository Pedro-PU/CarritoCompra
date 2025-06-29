package ec.edu.ups.poo.clases.vista.usuario;

import ec.edu.ups.poo.clases.util.MensajeInternacionalizacionHandler;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UsuarioCrearView extends JInternalFrame {
    private JPanel panelPrincipal;
    private JTextField txtUsername;
    private JPasswordField txtContrasenia;
    private JButton btnAceptar;
    private JButton btnLimpiar;
    private JLabel lblTitulo;
    private JLabel lblUsername;
    private JLabel lblContrasenia;
    private MensajeInternacionalizacionHandler mi;

    public UsuarioCrearView(MensajeInternacionalizacionHandler mi) {
        super("Registrar Usuarios", true,true,false,true);
        this.mi = mi;
        setContentPane(panelPrincipal);
        setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);
        setSize(500, 500);

        btnLimpiar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                limpiarCampos();
            }
        });
        cambiarIdioma();
    }
    public void limpiarCampos() {
        txtUsername.setText("");
        txtContrasenia.setText("");
    }
    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }

    public boolean mostrarMensajePregunta(String mensaje) {
        int respuesta = JOptionPane.showConfirmDialog(this, mensaje, "Confirmación",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        return respuesta == JOptionPane.YES_OPTION;
    }

    public JLabel getLblTitulo() {
        return lblTitulo;
    }

    public void setLblTitulo(JLabel lblTitulo) {
        this.lblTitulo = lblTitulo;
    }

    public JLabel getLblUsername() {
        return lblUsername;
    }

    public void setLblUsername(JLabel lblUsername) {
        this.lblUsername = lblUsername;
    }

    public JLabel getLblContrasenia() {
        return lblContrasenia;
    }

    public void setLblContrasenia(JLabel lblContrasenia) {
        this.lblContrasenia = lblContrasenia;
    }

    public JTextField getTxtUsername() {
        return txtUsername;
    }

    public void setTxtUsername(JTextField txtUsername) {
        this.txtUsername = txtUsername;
    }

    public JPasswordField getTxtContrasenia() {
        return txtContrasenia;
    }

    public void setTxtContrasenia(JPasswordField txtContrasenia) {
        this.txtContrasenia = txtContrasenia;
    }

    public JButton getBtnAceptar() {
        return btnAceptar;
    }

    public void setBtnAceptar(JButton btnAceptar) {
        this.btnAceptar = btnAceptar;
    }

    public JButton getBtnSalir() {
        return btnLimpiar;
    }

    public void setBtnSalir(JButton btnSalir) {
        this.btnLimpiar = btnSalir;
    }

    public void cambiarIdioma() {
        setTitle(mi.get("usuario.crear.titulo.ventana"));
        lblTitulo.setText(mi.get("usuario.crear.titulo"));
        lblUsername.setText(mi.get("usuario.crear.nombre"));
        lblContrasenia.setText(mi.get("usuario.crear.contrasena"));
        btnAceptar.setText(mi.get("usuario.crear.aceptar"));
        btnLimpiar.setText(mi.get("usuario.crear.limpiar"));

        UIManager.put("OptionPane.yesButtonText", mi.get("dialogo.boton.si"));
        UIManager.put("OptionPane.noButtonText", mi.get("dialogo.boton.no"));
        UIManager.put("OptionPane.cancelButtonText", mi.get("dialogo.boton.cancelar"));
        UIManager.put("OptionPane.okButtonText", mi.get("dialogo.boton.aceptar"));
    }
}
