package ec.edu.ups.poo.clases.vista.cuestionario;

import ec.edu.ups.poo.clases.util.MensajeInternacionalizacionHandler;

import javax.swing.*;
import java.net.URL;
import java.util.Locale;
/**
 * Clase que representa la ventana de recuperación de cuestionario.
 * Permite mostrar una pregunta secreta, recibir la respuesta del usuario y finalizar el proceso de recuperación.
 */
public class CuestionarioRecuperarView extends JFrame {
    private JTextField txtRespuesta;
    private JLabel lblPregunta;
    private JButton btnFinalizar;
    private JPanel panelPrincipal;
    private JLabel lblTitulo;
    private JComboBox cbxIdiomas;
    private JLabel lblIdioma;
    private MensajeInternacionalizacionHandler mi;
    /**
     * Constructor de la ventana de recuperación del cuestionario.
     * Inicializa componentes, configura la interfaz, carga idioma e íconos.
     * @param mi Handler de internacionalización.
     */
    public CuestionarioRecuperarView(MensajeInternacionalizacionHandler mi) {
        this.mi = mi;

        setTitle(mi.get("cuestionario.recuperar.titulo"));
        setSize(600, 400);
        setContentPane(panelPrincipal);
        setLocationRelativeTo(null);
        inicializarComponentes();
        inicializarImagenes();
    }
    /**
     * Muestra un mensaje emergente simple al usuario.
     * @param mensaje Texto del mensaje a mostrar.
     */
    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }
    /**
     * Muestra una pregunta al usuario con opciones Sí/No.
     * @param mensaje Texto de la pregunta.
     * @return true si el usuario acepta; false en caso contrario.
     */
    public boolean mostrarMensajePregunta(String mensaje) {
        int respuesta = JOptionPane.showConfirmDialog(this, mensaje, mi.get("dialogo.title.pregunta"),
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        return respuesta == JOptionPane.YES_OPTION;
    }
    /**
     * Solicita una entrada textual al usuario a través de un cuadro de diálogo.
     * @param mensaje Mensaje que solicita la entrada.
     * @return Respuesta ingresada por el usuario o null si está vacía.
     */
    public String ingreso(String mensaje) {
        String respuesta = JOptionPane.showInputDialog(this, mensaje);
        if (respuesta != null && !respuesta.trim().isEmpty()) {
            return respuesta.trim();
        }
        return null;
    }
    /**
     * Inicializa el combo box de idiomas y actualiza los textos visibles según el idioma actual.
     */
    public void inicializarComponentes() {
        cbxIdiomas.removeAllItems();
        cbxIdiomas.addItem(mi.get("menu.idioma.es")); // Español
        cbxIdiomas.addItem(mi.get("menu.idioma.en")); // Inglés
        cbxIdiomas.addItem(mi.get("menu.idioma.fr")); // Francés
        actualizarTextos();
    }
    /**
     * Cambia los textos visibles de la interfaz según el idioma seleccionado.
     * Actualiza el combo box y los botones del sistema.
     */
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
    /**
     * Carga el ícono del botón de finalizar desde los recursos del proyecto.
     */
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
    /**
     * Obtiene el label que indica el idioma actual.
     * @return JLabel del idioma.
     */
    public JLabel getLblIdioma() {
        return lblIdioma;
    }

    /**
     * Establece el label que indica el idioma actual.
     * @param lblIdioma JLabel del idioma.
     */
    public void setLblIdioma(JLabel lblIdioma) {
        this.lblIdioma = lblIdioma;
    }

    /**
     * Obtiene el combo box que permite seleccionar el idioma.
     * @return JComboBox de idiomas.
     */
    public JComboBox getCbxIdiomas() {
        return cbxIdiomas;
    }

    /**
     * Establece el combo box que permite seleccionar el idioma.
     * @param cbxIdiomas JComboBox de idiomas.
     */
    public void setCbxIdiomas(JComboBox cbxIdiomas) {
        this.cbxIdiomas = cbxIdiomas;
    }

    /**
     * Obtiene el label del título de la ventana.
     * @return JLabel del título.
     */
    public JLabel getLblTitulo() {
        return lblTitulo;
    }

    /**
     * Establece el label del título de la ventana.
     * @param lblTitulo JLabel del título.
     */
    public void setLblTitulo(JLabel lblTitulo) {
        this.lblTitulo = lblTitulo;
    }

    /**
     * Obtiene el campo de texto donde el usuario ingresa la respuesta.
     * @return JTextField de respuesta.
     */
    public JTextField getTxtRespuesta() {
        return txtRespuesta;
    }

    /**
     * Establece el campo de texto donde el usuario ingresa la respuesta.
     * @param txtRespuesta JTextField de respuesta.
     */
    public void setTxtRespuesta(JTextField txtRespuesta) {
        this.txtRespuesta = txtRespuesta;
    }

    /**
     * Obtiene el label que muestra la pregunta secreta.
     * @return JLabel de la pregunta.
     */
    public JLabel getLblPregunta() {
        return lblPregunta;
    }

    /**
     * Establece el label que muestra la pregunta secreta.
     * @param lblPregunta JLabel de la pregunta.
     */
    public void setLblPregunta(JLabel lblPregunta) {
        this.lblPregunta = lblPregunta;
    }

    /**
     * Obtiene el botón para finalizar el proceso de recuperación.
     * @return JButton de finalizar.
     */
    public JButton getBtnFinalizar() {
        return btnFinalizar;
    }

    /**
     * Establece el botón para finalizar el proceso de recuperación.
     * @param btnFinalizar JButton de finalizar.
     */
    public void setBtnFinalizar(JButton btnFinalizar) {
        this.btnFinalizar = btnFinalizar;
    }


}
