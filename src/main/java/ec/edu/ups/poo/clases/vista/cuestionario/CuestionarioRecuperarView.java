package ec.edu.ups.poo.clases.vista.cuestionario;

import ec.edu.ups.poo.clases.util.MensajeInternacionalizacionHandler;

import javax.swing.*;
import java.net.URL;
import java.util.Locale;

public class CuestionarioRecuperarView extends JFrame {
    private JTextField txtRespuesta;
    private JLabel lblPregunta;
    private JButton btnFinalizar;
    private JPanel panelPrincipal;
    private JLabel lblTitulo;
    private JComboBox cbxIdiomas;
    private JLabel lblIdioma;
    private MensajeInternacionalizacionHandler mi;


    public CuestionarioRecuperarView(MensajeInternacionalizacionHandler mi) {
        this.mi = mi;

        setTitle(mi.get("cuestionario.recuperar.titulo"));
        setSize(600, 400);
        setContentPane(panelPrincipal);
        setLocationRelativeTo(null);
        inicializarComponentes();
        inicializarImagenes();
    }
    // Muestra un mensaje emergente con texto plano
    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }
    // Muestra un diálogo de sí/no y retorna si el usuario aceptó
    public boolean mostrarMensajePregunta(String mensaje) {
        int respuesta = JOptionPane.showConfirmDialog(this, mensaje, mi.get("dialogo.title.pregunta"),
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        return respuesta == JOptionPane.YES_OPTION;
    }
    // Solicita una entrada textual al usuario mediante un diálogo
    public String ingreso(String mensaje) {
        String respuesta = JOptionPane.showInputDialog(this, mensaje);
        if (respuesta != null && !respuesta.trim().isEmpty()) {
            return respuesta.trim();
        }
        return null;
    }
    // Inicializa el combo de idiomas y actualiza los textos según el idioma actual
    public void inicializarComponentes() {
        cbxIdiomas.removeAllItems();
        cbxIdiomas.addItem(mi.get("menu.idioma.es")); // Español
        cbxIdiomas.addItem(mi.get("menu.idioma.en")); // Inglés
        cbxIdiomas.addItem(mi.get("menu.idioma.fr")); // Francés
        actualizarTextos();
    }
    // Cambia los textos visibles de la interfaz según el idioma seleccionado
    public void actualizarTextos() {
        lblTitulo.setText(mi.get("cuestionario.recuperar.titulo"));
        btnFinalizar.setText(mi.get("cuestionario.boton.finalizar"));
        lblIdioma.setText(mi.get("login.idioma"));
        UIManager.put("OptionPane.yesButtonText", mi.get("dialogo.boton.si"));
        UIManager.put("OptionPane.noButtonText", mi.get("dialogo.boton.no"));
        UIManager.put("OptionPane.cancelButtonText", mi.get("dialogo.boton.cancelar"));
        UIManager.put("OptionPane.okButtonText", mi.get("dialogo.boton.aceptar"));

        Locale currentLocale = mi.getLocale();
        int selectedIndex = 0;
        if ("en".equals(currentLocale.getLanguage()) && "US".equals(currentLocale.getCountry())) {
            selectedIndex = 1;
        } else if ("fr".equals(currentLocale.getLanguage()) && "FR".equals(currentLocale.getCountry())) {
            selectedIndex = 2;
        }
        if (cbxIdiomas.getSelectedIndex() != selectedIndex) {
            cbxIdiomas.setSelectedIndex(selectedIndex);
        }
        cbxIdiomas.removeAllItems();
        cbxIdiomas.addItem(mi.get("menu.idioma.es"));
        cbxIdiomas.addItem(mi.get("menu.idioma.en"));
        cbxIdiomas.addItem(mi.get("menu.idioma.fr"));
        cbxIdiomas.setSelectedIndex(selectedIndex);
    }
    // Asigna el ícono al botón de finalizar si se encuentra el recurso
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
    //Getters y Setters
    public JLabel getLblIdioma() {
        return lblIdioma;
    }

    public void setLblIdioma(JLabel lblIdioma) {
        this.lblIdioma = lblIdioma;
    }

    public JComboBox getCbxIdiomas() {
        return cbxIdiomas;
    }

    public void setCbxIdiomas(JComboBox cbxIdiomas) {
        this.cbxIdiomas = cbxIdiomas;
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
