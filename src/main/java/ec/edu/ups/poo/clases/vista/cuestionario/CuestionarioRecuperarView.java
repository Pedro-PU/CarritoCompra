package ec.edu.ups.poo.clases.vista.cuestionario;

import javax.swing.*;

public class CuestionarioRecuperarView extends JFrame {
    private JComboBox<String> cbxPreguntas;
    private JTextField txtRespuesta;
    private JLabel lblPregunta;
    private JButton btnFinalizar;
    private JButton btnGuardar;
    private JPanel panelPrincipal;
    public CuestionarioRecuperarView() {
        setTitle("Cuestionario Recuperar Preguntas");
        setSize(600, 400);
        setContentPane(panelPrincipal);
        setLocationRelativeTo(null);
    }
    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
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

    public JLabel getLblPregunta() {
        return lblPregunta;
    }

    public void setLblPregunta(JLabel lblPregunta) {
        this.lblPregunta = lblPregunta;
    }

    public JButton getBtnFinalizar() {
        return btnFinalizar;
    }

    public void setBtnFinalizar(JButton btnFinalizar) {
        this.btnFinalizar = btnFinalizar;
    }

    public JButton getBtnGuardar() {
        return btnGuardar;
    }

    public void setBtnGuardar(JButton btnGuardar) {
        this.btnGuardar = btnGuardar;
    }
}
