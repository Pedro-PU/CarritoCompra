package ec.edu.ups.poo.clases.vista.cuestionario;

import ec.edu.ups.poo.clases.util.MensajeInternacionalizacionHandler;

import javax.swing.*;

public class CuestionarioView extends JFrame {
    private JPanel panelPrincipal;
    private JComboBox<String> cbxPreguntas;
    private JTextField txtRespuesta;
    private JButton btnGuardar;
    private JButton btnFinalizar;
    private JLabel lblPregunta;
    private JLabel lblTitulo;
    private MensajeInternacionalizacionHandler mi;

    public CuestionarioView(MensajeInternacionalizacionHandler mi) {
        this.mi = mi;
        setTitle(mi.get("cuestionario.titulo"));
        setSize(600, 400);
        setContentPane(panelPrincipal);
        setLocationRelativeTo(null);
        actualizarTextos();
    }

    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }

    public void actualizarTextos() {
        lblTitulo.setText(mi.get("cuestionario.titulo"));
        btnGuardar.setText(mi.get("cuestionario.boton.guardar"));
        btnFinalizar.setText(mi.get("cuestionario.boton.finalizar"));

        UIManager.put("OptionPane.yesButtonText", mi.get("dialogo.boton.si"));
        UIManager.put("OptionPane.noButtonText", mi.get("dialogo.boton.no"));
        UIManager.put("OptionPane.cancelButtonText", mi.get("dialogo.boton.cancelar"));
        UIManager.put("OptionPane.okButtonText", mi.get("dialogo.boton.aceptar"));
    }


    public JLabel getLblTitulo() {
        return lblTitulo;
    }

    public void setLblTitulo(JLabel lblTitulo) {
        this.lblTitulo = lblTitulo;
    }

    public JComboBox<String> getCbxPreguntas() {
        return cbxPreguntas;
    }

    public void setCbxPreguntas(JComboBox<String> cbxPreguntas) {
        this.cbxPreguntas = cbxPreguntas;
    }

    public JTextField getTxtRespuesta() {
        return txtRespuesta;
    }

    public void setTxtRespuesta(JTextField txtRespuesta) {
        this.txtRespuesta = txtRespuesta;
    }

    public JButton getBtnGuardar() {
        return btnGuardar;
    }

    public void setBtnGuardar(JButton btnGuardar) {
        this.btnGuardar = btnGuardar;
    }

    public JButton getBtnFinalizar() {
        return btnFinalizar;
    }

    public void setBtnFinalizar(JButton btnFinalizar) {
        this.btnFinalizar = btnFinalizar;
    }

    public JLabel getLblPregunta() {
        return lblPregunta;
    }

    public void setLblPregunta(JLabel lblPregunta) {
        this.lblPregunta = lblPregunta;
    }
}
