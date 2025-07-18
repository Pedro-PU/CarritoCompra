package ec.edu.ups.poo.clases.vista.cuestionario;

import ec.edu.ups.poo.clases.util.MensajeInternacionalizacionHandler;

import javax.swing.*;
import java.net.URL;
import java.util.Locale;
/**
 * Clase que representa la ventana principal del cuestionario de registro.
 * Permite ingresar datos personales, seleccionar una pregunta de seguridad y guardar respuestas.
 * También gestiona la internacionalización de textos e íconos.
 */
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
    private JComboBox cbxIdiomas;
    private JLabel lblIdioma;
    private MensajeInternacionalizacionHandler mi;
    /**
     * Constructor de la vista del cuestionario.
     * Inicializa los componentes, configura idioma, íconos y el estado inicial del formulario.
     * @param mi Handler de internacionalización.
     */
    public CuestionarioView(MensajeInternacionalizacionHandler mi) {
        this.mi = mi;
        setTitle(mi.get("cuestionario.titulo"));
        setSize(600, 500);
        setContentPane(panelPrincipal);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        inicializarComponentes();
        inicializarImagenes();
        habilitarPreguntas(false);
        spnDia.setModel(new SpinnerNumberModel(1, 1, 31, 1));
        spnMes.setModel(new SpinnerNumberModel(1, 1, 12, 1));
        spnAnio.setModel(new SpinnerNumberModel(2000, 1, 2100, 1));
    }
    /**
     * Muestra un mensaje simple al usuario en un cuadro de diálogo.
     * @param mensaje Texto del mensaje.
     */
    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }
    /**
     * Inicializa el combo de idiomas y aplica los textos localizados.
     */
    public void inicializarComponentes() {
        cbxIdiomas.removeAllItems();
        cbxIdiomas.addItem(mi.get("menu.idioma.es")); // Español
        cbxIdiomas.addItem(mi.get("menu.idioma.en")); // Inglés
        cbxIdiomas.addItem(mi.get("menu.idioma.fr")); // Francés
        actualizarTextos();
    }
    /**
     * Cambia todos los textos visibles según el idioma seleccionado.
     * También actualiza los ítems del combo box de idioma.
     */
    public void actualizarTextos() {
        lblTitulo.setText(mi.get("cuestionario.titulo"));
        lblIdioma.setText(mi.get("login.idioma"));
        btnGuardar.setText(mi.get("cuestionario.boton.guardar"));
        btnFinalizar.setText(mi.get("cuestionario.boton.finalizar"));
        lblPregunta.setText(mi.get("cuestionario.pregunta"));
        lblNombre.setText(mi.get("cuestionario.nombre"));
        lblFecha.setText(mi.get("cuestionario.fecha"));
        lblCelular.setText(mi.get("cuestionario.celular"));
        lblUsername.setText(mi.get("cuestionario.username"));
        lblContrasenia.setText(mi.get("cuestionario.contrasenia"));
        lblCorreo.setText(mi.get("cuestionario.correo"));
        BtnIniciarCuestionario.setText(mi.get("cuestionario.boton.iniciar"));

        UIManager.put("OptionPane.yesButtonText", mi.get("dialogo.boton.si"));
        UIManager.put("OptionPane.noButtonText", mi.get("dialogo.boton.no"));
        UIManager.put("OptionPane.cancelButtonText", mi.get("dialogo.boton.cancelar"));
        UIManager.put("OptionPane.okButtonText", mi.get("dialogo.boton.aceptar"));

        // Actualiza el idioma seleccionado visualmente en el combo
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
     * Asigna íconos a los botones si los recursos están disponibles.
     */
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

        URL mostrar = CuestionarioView.class.getClassLoader().getResource("imagenes/mostrar.png");
        if (mostrar != null) {
            ImageIcon iconoBtnIniciarSesion = new ImageIcon(mostrar);
            BtnIniciarCuestionario.setIcon(iconoBtnIniciarSesion);
        } else {
            System.err.println("Error: No se ha cargado el icono de Login");
        }
    }
    /**
     * Habilita o deshabilita los campos relacionados a la pregunta de seguridad.
     * @param habilitar true para activar los campos; false para desactivarlos.
     */
    public void habilitarPreguntas(boolean habilitar) {
        cbxPreguntas.setEnabled(habilitar);
        txtRespuesta.setEnabled(habilitar);
        btnGuardar.setEnabled(habilitar);
        btnFinalizar.setEnabled(habilitar);
    }

    /**
     * Obtiene el label del idioma actual.
     * @return JLabel del idioma.
     */
    public JLabel getLblIdioma() {
        return lblIdioma;
    }

    /**
     * Establece el label del idioma actual.
     * @param lblIdioma JLabel del idioma.
     */
    public void setLblIdioma(JLabel lblIdioma) {
        this.lblIdioma = lblIdioma;
    }

    /**
     * Obtiene el combo box para seleccionar idioma.
     * @return JComboBox de idiomas.
     */
    public JComboBox getCbxIdiomas() {
        return cbxIdiomas;
    }

    /**
     * Establece el combo box para seleccionar idioma.
     * @param cbxIdiomas JComboBox de idiomas.
     */
    public void setCbxIdiomas(JComboBox cbxIdiomas) {
        this.cbxIdiomas = cbxIdiomas;
    }

    /**
     * Obtiene el campo de texto para el correo.
     * @return JTextField del correo.
     */
    public JTextField getTxtCorreo() {
        return txtCorreo;
    }

    /**
     * Establece el campo de texto para el correo.
     * @param txtCorreo JTextField del correo.
     */
    public void setTxtCorreo(JTextField txtCorreo) {
        this.txtCorreo = txtCorreo;
    }

    /**
     * Obtiene el label del correo electrónico.
     * @return JLabel del correo.
     */
    public JLabel getLblCorreo() {
        return lblCorreo;
    }

    /**
     * Establece el label del correo electrónico.
     * @param lblCorreo JLabel del correo.
     */
    public void setLblCorreo(JLabel lblCorreo) {
        this.lblCorreo = lblCorreo;
    }

    /**
     * Obtiene el botón para iniciar el cuestionario.
     * @return JButton de iniciar.
     */
    public JButton getBtnIniciarCuestionario() {
        return BtnIniciarCuestionario;
    }

    /**
     * Establece el botón para iniciar el cuestionario.
     * @param btnIniciarCuestionario JButton de iniciar.
     */
    public void setBtnIniciarCuestionario(JButton btnIniciarCuestionario) {
        BtnIniciarCuestionario = btnIniciarCuestionario;
    }

    /**
     * Obtiene el campo de texto del nombre de usuario.
     * @return JTextField del usuario.
     */
    public JTextField getTxtUsername() {
        return txtUsername;
    }

    /**
     * Establece el campo de texto del nombre de usuario.
     * @param txtUsername JTextField del usuario.
     */
    public void setTxtUsername(JTextField txtUsername) {
        this.txtUsername = txtUsername;
    }

    /**
     * Obtiene el handler de internacionalización.
     * @return MensajeInternacionalizacionHandler.
     */
    public MensajeInternacionalizacionHandler getMi() {
        return mi;
    }

    /**
     * Establece el handler de internacionalización.
     * @param mi Handler.
     */
    public void setMi(MensajeInternacionalizacionHandler mi) {
        this.mi = mi;
    }

    /**
     * Obtiene el label de la contraseña.
     * @return JLabel de la contraseña.
     */
    public JLabel getLblContrasenia() {
        return lblContrasenia;
    }

    /**
     * Establece el label de la contraseña.
     * @param lblContrasenia JLabel.
     */
    public void setLblContrasenia(JLabel lblContrasenia) {
        this.lblContrasenia = lblContrasenia;
    }

    /**
     * Obtiene el label del nombre de usuario.
     * @return JLabel del usuario.
     */
    public JLabel getLblUsername() {
        return lblUsername;
    }

    /**
     * Establece el label del nombre de usuario.
     * @param lblUsername JLabel.
     */
    public void setLblUsername(JLabel lblUsername) {
        this.lblUsername = lblUsername;
    }

    /**
     * Obtiene el campo de texto para la contraseña.
     * @return JTextField de contraseña.
     */
    public JTextField getTxtContrasenia() {
        return txtContrasenia;
    }

    /**
     * Establece el campo de texto para la contraseña.
     * @param txtContrasenia JTextField.
     */
    public void setTxtContrasenia(JTextField txtContrasenia) {
        this.txtContrasenia = txtContrasenia;
    }

    /**
     * Obtiene el label del título.
     * @return JLabel del título.
     */
    public JLabel getLblTitulo() {
        return lblTitulo;
    }

    /**
     * Establece el label del título.
     * @param lblTitulo JLabel.
     */
    public void setLblTitulo(JLabel lblTitulo) {
        this.lblTitulo = lblTitulo;
    }

    /**
     * Obtiene el campo de texto del nombre.
     * @return JTextField del nombre.
     */
    public JTextField getTxtNombre() {
        return txtNombre;
    }

    /**
     * Establece el campo de texto del nombre.
     * @param txtNombre JTextField del nombre.
     */
    public void setTxtNombre(JTextField txtNombre) {
        this.txtNombre = txtNombre;
    }

    /**
     * Obtiene el spinner del día de nacimiento.
     * @return JSpinner del día.
     */
    public JSpinner getSpnDia() {
        return spnDia;
    }

    /**
     * Establece el spinner del día de nacimiento.
     * @param spnDia JSpinner.
     */
    public void setSpnDia(JSpinner spnDia) {
        this.spnDia = spnDia;
    }

    /**
     * Obtiene el spinner del mes de nacimiento.
     * @return JSpinner del mes.
     */
    public JSpinner getSpnMes() {
        return spnMes;
    }

    /**
     * Establece el spinner del mes de nacimiento.
     * @param spnMes JSpinner.
     */
    public void setSpnMes(JSpinner spnMes) {
        this.spnMes = spnMes;
    }

    /**
     * Obtiene el spinner del año de nacimiento.
     * @return JSpinner del año.
     */
    public JSpinner getSpnAnio() {
        return spnAnio;
    }

    /**
     * Establece el spinner del año de nacimiento.
     * @param spnAnio JSpinner.
     */
    public void setSpnAnio(JSpinner spnAnio) {
        this.spnAnio = spnAnio;
    }

    /**
     * Obtiene el campo de texto del celular.
     * @return JTextField del celular.
     */
    public JTextField getTxtCelular() {
        return txtCelular;
    }

    /**
     * Establece el campo de texto del celular.
     * @param txtCelular JTextField.
     */
    public void setTxtCelular(JTextField txtCelular) {
        this.txtCelular = txtCelular;
    }

    /**
     * Obtiene el label del nombre.
     * @return JLabel del nombre.
     */
    public JLabel getLblNombre() {
        return lblNombre;
    }

    /**
     * Establece el label del nombre.
     * @param lblNombre JLabel del nombre.
     */
    public void setLblNombre(JLabel lblNombre) {
        this.lblNombre = lblNombre;
    }

    /**
     * Obtiene el label de la fecha de nacimiento.
     * @return JLabel de la fecha.
     */
    public JLabel getLblFecha() {
        return lblFecha;
    }

    /**
     * Establece el label de la fecha de nacimiento.
     * @param lblFecha JLabel de la fecha.
     */
    public void setLblFecha(JLabel lblFecha) {
        this.lblFecha = lblFecha;
    }

    /**
     * Obtiene el label del celular.
     * @return JLabel del celular.
     */
    public JLabel getLblCelular() {
        return lblCelular;
    }

    /**
     * Establece el label del celular.
     * @param lblCelular JLabel del celular.
     */
    public void setLblCelular(JLabel lblCelular) {
        this.lblCelular = lblCelular;
    }

    /**
     * Obtiene el combo box de preguntas de seguridad.
     * @return JComboBox de preguntas.
     */
    public JComboBox<String> getCbxPreguntas() {
        return cbxPreguntas;
    }

    /**
     * Establece el combo box de preguntas de seguridad.
     * @param cbxPreguntas JComboBox de preguntas.
     */
    public void setCbxPreguntas(JComboBox<String> cbxPreguntas) {
        this.cbxPreguntas = cbxPreguntas;
    }

    /**
     * Obtiene el campo de texto para la respuesta a la pregunta de seguridad.
     * @return JTextField de respuesta.
     */
    public JTextField getTxtRespuesta() {
        return txtRespuesta;
    }

    /**
     * Establece el campo de texto para la respuesta a la pregunta de seguridad.
     * @param txtRespuesta JTextField de respuesta.
     */
    public void setTxtRespuesta(JTextField txtRespuesta) {
        this.txtRespuesta = txtRespuesta;
    }

    /**
     * Obtiene el botón para guardar la respuesta del cuestionario.
     * @return JButton de guardar.
     */
    public JButton getBtnGuardar() {
        return btnGuardar;
    }

    /**
     * Establece el botón para guardar la respuesta del cuestionario.
     * @param btnGuardar JButton de guardar.
     */
    public void setBtnGuardar(JButton btnGuardar) {
        this.btnGuardar = btnGuardar;
    }

    /**
     * Obtiene el botón para finalizar el cuestionario.
     * @return JButton de finalizar.
     */
    public JButton getBtnFinalizar() {
        return btnFinalizar;
    }

    /**
     * Establece el botón para finalizar el cuestionario.
     * @param btnFinalizar JButton de finalizar.
     */
    public void setBtnFinalizar(JButton btnFinalizar) {
        this.btnFinalizar = btnFinalizar;
    }

    /**
     * Obtiene el label de la pregunta de seguridad seleccionada.
     * @return JLabel de la pregunta.
     */
    public JLabel getLblPregunta() {
        return lblPregunta;
    }

    /**
     * Establece el label de la pregunta de seguridad seleccionada.
     * @param lblPregunta JLabel de la pregunta.
     */
    public void setLblPregunta(JLabel lblPregunta) {
        this.lblPregunta = lblPregunta;
    }
}
