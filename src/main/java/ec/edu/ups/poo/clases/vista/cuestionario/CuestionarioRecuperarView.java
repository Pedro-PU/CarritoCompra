package ec.edu.ups.poo.clases.vista.cuestionario;

import ec.edu.ups.poo.clases.util.MensajeInternacionalizacionHandler;

import javax.swing.*;
import java.net.URL;

public class CuestionarioRecuperarView extends JFrame {
    private JTextField txtRespuesta;
    private JLabel lblPregunta;
    private JButton btnFinalizar;
    private JPanel panelPrincipal;
    private JLabel lblTitulo;
    private JComboBox cbxIdiomas;
    private MensajeInternacionalizacionHandler mi;


    public CuestionarioRecuperarView(MensajeInternacionalizacionHandler mi) {
        this.mi = mi;

        setTitle(mi.get("cuestionario.recuperar.titulo"));
        setSize(600, 400);
        setContentPane(panelPrincipal);
        setLocationRelativeTo(null);
        actualizarTextos();
        inicializarImagenes();
    }
    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }

    public boolean mostrarMensajePregunta(String mensaje) {
        int respuesta = JOptionPane.showConfirmDialog(this, mensaje, mi.get("dialogo.title.pregunta"),
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        return respuesta == JOptionPane.YES_OPTION;
    }
    public String ingreso(String mensaje) {
        String respuesta = JOptionPane.showInputDialog(this, mensaje);
        if (respuesta != null && !respuesta.trim().isEmpty()) {
            return respuesta.trim();
        }
        return null;
    }

    public void actualizarTextos() {
        lblTitulo.setText(mi.get("cuestionario.recuperar.titulo"));
        btnFinalizar.setText(mi.get("cuestionario.boton.finalizar"));

        UIManager.put("OptionPane.yesButtonText", mi.get("dialogo.boton.si"));
        UIManager.put("OptionPane.noButtonText", mi.get("dialogo.boton.no"));
        UIManager.put("OptionPane.cancelButtonText", mi.get("dialogo.boton.cancelar"));
        UIManager.put("OptionPane.okButtonText", mi.get("dialogo.boton.aceptar"));
    }

    public void inicializarImagenes(){
        URL guardar = CuestionarioRecuperarView.class.getClassLoader().getResource("imagenes/guardar.png");

        URL finalizar = CuestionarioRecuperarView.class.getClassLoader().getResource("imagenes/finalizar.png");
        if (finalizar != null) {
            ImageIcon iconoBtnIniciarSesion = new ImageIcon(finalizar);
            btnFinalizar.setIcon(iconoBtnIniciarSesion);
        } else {
            System.err.println("Error: No se ha cargado el icono de Login");
        }
    }


    public JLabel getLblTitulo() {
        return lblTitulo;
    }

    public void setLblTitulo(JLabel lblTitulo) {
        this.lblTitulo = lblTitulo;
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

}
