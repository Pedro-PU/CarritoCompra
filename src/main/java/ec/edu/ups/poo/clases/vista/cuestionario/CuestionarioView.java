package ec.edu.ups.poo.clases.vista.cuestionario;

import ec.edu.ups.poo.clases.util.MensajeInternacionalizacionHandler;

import javax.swing.*;
import java.net.URL;

public class CuestionarioView extends JFrame {
    private JPanel panelPrincipal;
    private JComboBox<String> cbxPreguntas;
    private JTextField txtRespuesta;
    private JButton btnGuardar;
    private JButton btnFinalizar;
    private JLabel lblPregunta;
    private JLabel lblTitulo;
    private JTextField txtNombre;
    private JSpinner spnDia;
    private JSpinner spnMes;
    private JSpinner spnAnio;
    private JTextField txtCelular;
    private JLabel lblNombre;
    private JLabel lblFecha;
    private JLabel lblCelular;
    private JTextField txtUsername;
    private JTextField txtContrasenia;
    private JLabel lblUsername;
    private JLabel lblContrasenia;
    private JButton BtnIniciarCuestionario;
    private JTextField txtCorreo;
    private JLabel lblCorreo;
    private MensajeInternacionalizacionHandler mi;

    public CuestionarioView(MensajeInternacionalizacionHandler mi) {
        this.mi = mi;
        setTitle(mi.get("cuestionario.titulo"));
        setSize(600, 400);
        setContentPane(panelPrincipal);
        setLocationRelativeTo(null);
        actualizarTextos();
        inicializarImagenes();
        habilitarPreguntas(false);
        spnDia.setModel(new SpinnerNumberModel(1, 1, 31, 1));
        spnMes.setModel(new SpinnerNumberModel(1, 1, 12, 1));
        spnAnio.setModel(new SpinnerNumberModel(2000, 1, 2100, 1));
    }

    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }

    public void actualizarTextos() {
        lblTitulo.setText(mi.get("cuestionario.titulo"));
        btnGuardar.setText(mi.get("cuestionario.boton.guardar"));
        btnFinalizar.setText(mi.get("cuestionario.boton.finalizar"));
        lblPregunta.setText(mi.get("cuestionario.pregunta"));

        UIManager.put("OptionPane.yesButtonText", mi.get("dialogo.boton.si"));
        UIManager.put("OptionPane.noButtonText", mi.get("dialogo.boton.no"));
        UIManager.put("OptionPane.cancelButtonText", mi.get("dialogo.boton.cancelar"));
        UIManager.put("OptionPane.okButtonText", mi.get("dialogo.boton.aceptar"));
    }

    public void inicializarImagenes(){
        URL guardar = CuestionarioView.class.getClassLoader().getResource("imagenes/guardar.png");
        if (guardar != null) {
            ImageIcon iconoBtnIniciarSesion = new ImageIcon(guardar);
            btnGuardar.setIcon(iconoBtnIniciarSesion);
        } else {
            System.err.println("Error: No se ha cargado el icono de Login");
        }

        URL finalizar = CuestionarioView.class.getClassLoader().getResource("imagenes/finalizar.png");
        if (finalizar != null) {
            ImageIcon iconoBtnIniciarSesion = new ImageIcon(finalizar);
            btnFinalizar.setIcon(iconoBtnIniciarSesion);
        } else {
            System.err.println("Error: No se ha cargado el icono de Login");
        }
    }

    public void habilitarPreguntas(boolean habilitar) {
        cbxPreguntas.setEnabled(habilitar);
        txtRespuesta.setEnabled(habilitar);
        btnGuardar.setEnabled(habilitar);
        btnFinalizar.setEnabled(habilitar);
    }

    public JTextField getTxtCorreo() {
        return txtCorreo;
    }

    public void setTxtCorreo(JTextField txtCorreo) {
        this.txtCorreo = txtCorreo;
    }

    public JLabel getLblCorreo() {
        return lblCorreo;
    }

    public void setLblCorreo(JLabel lblCorreo) {
        this.lblCorreo = lblCorreo;
    }

    public JButton getBtnIniciarCuestionario() {
        return BtnIniciarCuestionario;
    }

    public void setBtnIniciarCuestionario(JButton btnIniciarCuestionario) {
        BtnIniciarCuestionario = btnIniciarCuestionario;
    }

    public JTextField getTxtUsername() {
        return txtUsername;
    }

    public void setTxtUsername(JTextField txtUsername) {
        this.txtUsername = txtUsername;
    }

    public MensajeInternacionalizacionHandler getMi() {
        return mi;
    }

    public void setMi(MensajeInternacionalizacionHandler mi) {
        this.mi = mi;
    }

    public JLabel getLblContrasenia() {
        return lblContrasenia;
    }

    public void setLblContrasenia(JLabel lblContrasenia) {
        this.lblContrasenia = lblContrasenia;
    }

    public JLabel getLblUsername() {
        return lblUsername;
    }

    public void setLblUsername(JLabel lblUsername) {
        this.lblUsername = lblUsername;
    }

    public JTextField getTxtContrasenia() {
        return txtContrasenia;
    }

    public void setTxtContrasenia(JTextField txtContrasenia) {
        this.txtContrasenia = txtContrasenia;
    }

    public JLabel getLblTitulo() {
        return lblTitulo;
    }

    public void setLblTitulo(JLabel lblTitulo) {
        this.lblTitulo = lblTitulo;
    }

    public JTextField getTxtNombre() {
        return txtNombre;
    }

    public void setTxtNombre(JTextField txtNombre) {
        this.txtNombre = txtNombre;
    }

    public JSpinner getSpnDia() {
        return spnDia;
    }

    public void setSpnDia(JSpinner spnDia) {
        this.spnDia = spnDia;
    }

    public JSpinner getSpnMes() {
        return spnMes;
    }

    public void setSpnMes(JSpinner spnMes) {
        this.spnMes = spnMes;
    }

    public JSpinner getSpnAnio() {
        return spnAnio;
    }

    public void setSpnAnio(JSpinner spnAnio) {
        this.spnAnio = spnAnio;
    }

    public JTextField getTxtCelular() {
        return txtCelular;
    }

    public void setTxtCelular(JTextField txtCelular) {
        this.txtCelular = txtCelular;
    }

    public JLabel getLblNombre() {
        return lblNombre;
    }

    public void setLblNombre(JLabel lblNombre) {
        this.lblNombre = lblNombre;
    }

    public JLabel getLblFecha() {
        return lblFecha;
    }

    public void setLblFecha(JLabel lblFecha) {
        this.lblFecha = lblFecha;
    }

    public JLabel getLblCelular() {
        return lblCelular;
    }

    public void setLblCelular(JLabel lblCelular) {
        this.lblCelular = lblCelular;
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
