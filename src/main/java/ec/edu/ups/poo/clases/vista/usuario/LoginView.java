package ec.edu.ups.poo.clases.vista.usuario;

import ec.edu.ups.poo.clases.util.MensajeInternacionalizacionHandler;

import javax.swing.*;
import java.util.Locale;
import java.net.URL;
/**
 * Ventana principal de inicio de sesión que permite al usuario autenticarse,
 * registrarse, recuperar contraseña, cambiar idioma y salir de la aplicación.
 * Utiliza internacionalización para adaptar la interfaz según el idioma seleccionado.
 */
public class LoginView extends JFrame{
    private JPanel panelPrincipal;
    private JTextField txtUsername;
    private JPasswordField txtContrasenia;
    private JButton btnIniciar;
    private JButton btnRegistrar;
    private JButton btnOlvidar;
    private JButton btnSalir;
    private JLabel lblTitulo;
    private JLabel lblUsuario;
    private JLabel lblContrasenia;
    private JComboBox cbxIdiomas;
    private JLabel lblIdioma;
    private MensajeInternacionalizacionHandler mi;
    /**
     * Constructor de la clase que inicializa la interfaz con los textos internacionalizados
     * y configura la ventana con tamaño, posición y componentes iniciales.
     *
     * @param mi Instancia del manejador de internacionalización para cargar textos multilenguaje.
     */
    public LoginView(MensajeInternacionalizacionHandler mi) {
        this.mi = mi;
        setTitle("Iniciar Sesion");
        //setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        add(panelPrincipal);
        inicializarImagenes();
        inicializarComponentes();
    }
    /**
     * Muestra un mensaje emergente con el texto proporcionado.
     *
     * @param mensaje Mensaje que se desea mostrar al usuario.
     */
    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }
    /**
     * Muestra un cuadro de confirmación con botones de Sí y No.
     *
     * @param mensaje Mensaje de la pregunta a mostrar.
     * @return {@code true} si el usuario selecciona Sí; {@code false} en caso contrario.
     */
    public boolean mostrarMensajePregunta(String mensaje) {
        int respuesta = JOptionPane.showConfirmDialog(this, mensaje, "Confirmación",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        return respuesta == JOptionPane.YES_OPTION;
    }
    /**
     * Inicializa el combo de idiomas con las opciones traducidas e
     * invoca la actualización de los textos de la interfaz.
     */
    public void inicializarComponentes() {
        cbxIdiomas.removeAllItems();
        cbxIdiomas.addItem(mi.get("menu.idioma.es")); // Español
        cbxIdiomas.addItem(mi.get("menu.idioma.en")); // Inglés
        cbxIdiomas.addItem(mi.get("menu.idioma.fr")); // Francés
        actualizarTextos();
    }
    /**
     * Carga los iconos para los botones desde el recurso correspondiente.
     * Si no se encuentra la imagen, se imprime un error por consola.
     */
    public void inicializarImagenes(){
        URL loginURL = LoginView.class.getClassLoader().getResource("imagenes/login.png");
        if (loginURL != null) {
            ImageIcon iconoBtnIniciarSesion = new ImageIcon(loginURL);
            btnIniciar.setIcon(iconoBtnIniciarSesion);
        } else {
            System.err.println("Error: No se ha cargado el icono de Login");
        }

        URL registrarseURL = LoginView.class.getClassLoader().getResource("imagenes/registrarse.png");
        if (registrarseURL != null) {
            ImageIcon iconoBtnRegistrarse = new ImageIcon(registrarseURL);
            btnRegistrar.setIcon(iconoBtnRegistrarse);
        } else {
            System.err.println("Error: No se ha cargado el icono de Registrarse");
        }

        URL olvido = LoginView.class.getClassLoader().getResource("imagenes/confusion.png");
        if (olvido != null) {
            ImageIcon iconoBtnRegistrarse = new ImageIcon(olvido);
            btnOlvidar.setIcon(iconoBtnRegistrarse);
        } else {
            System.err.println("Error: No se ha cargado el icono de Registrarse");
        }

        URL salir = LoginView.class.getClassLoader().getResource("imagenes/salir.png");
        if (salir != null) {
            ImageIcon iconoBtnRegistrarse = new ImageIcon(salir);
            btnSalir.setIcon(iconoBtnRegistrarse);
        } else {
            System.err.println("Error: No se ha cargado el icono de Registrarse");
        }

    }
    /**
     * Actualiza los textos de todos los componentes de la interfaz
     * según el idioma actualmente seleccionado.
     */
    public void actualizarTextos() {
        setTitle(mi.get("app.titulo"));
        lblTitulo.setText(mi.get("login.titulo"));
        lblUsuario.setText(mi.get("login.usuario"));
        lblContrasenia.setText(mi.get("login.contrasenia"));
        lblIdioma.setText(mi.get("login.idioma"));

        btnIniciar.setText(mi.get("login.boton.iniciar"));
        btnRegistrar.setText(mi.get("login.boton.registrar"));
        btnOlvidar.setText(mi.get("login.boton.olvidar"));
        btnSalir.setText(mi.get("login.boton.salir"));
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
        UIManager.put("OptionPane.yesButtonText", mi.get("dialogo.boton.si"));
        UIManager.put("OptionPane.noButtonText", mi.get("dialogo.boton.no"));
        UIManager.put("OptionPane.cancelButtonText", mi.get("dialogo.boton.cancelar"));
        UIManager.put("OptionPane.okButtonText", mi.get("dialogo.boton.aceptar"));

        cbxIdiomas.removeAllItems();
        cbxIdiomas.addItem(mi.get("menu.idioma.es"));
        cbxIdiomas.addItem(mi.get("menu.idioma.en"));
        cbxIdiomas.addItem(mi.get("menu.idioma.fr"));
        cbxIdiomas.setSelectedIndex(selectedIndex);
    }
    /**
     * Devuelve el combo box de selección de idiomas.
     * @return JComboBox con los idiomas disponibles.
     */
    public JComboBox getCbxIdiomas() {
        return cbxIdiomas;
    }

    /**
     * Establece el combo box de selección de idiomas.
     * @param cbxIdiomas JComboBox a asignar.
     */
    public void setCbxIdiomas(JComboBox cbxIdiomas) {
        this.cbxIdiomas = cbxIdiomas;
    }

    /**
     * Devuelve la etiqueta del idioma.
     * @return JLabel que muestra el texto del idioma.
     */
    public JLabel getLblIdioma() {
        return lblIdioma;
    }

    /**
     * Establece la etiqueta del idioma.
     * @param lblIdioma JLabel a asignar.
     */
    public void setLblIdioma(JLabel lblIdioma) {
        this.lblIdioma = lblIdioma;
    }

    /**
     * Devuelve la etiqueta del título.
     * @return JLabel con el título de la ventana.
     */
    public JLabel getLblTitulo() {
        return lblTitulo;
    }

    /**
     * Establece la etiqueta del título.
     * @param lblTitulo JLabel a asignar.
     */
    public void setLblTitulo(JLabel lblTitulo) {
        this.lblTitulo = lblTitulo;
    }

    /**
     * Devuelve la etiqueta del usuario.
     * @return JLabel que indica el campo usuario.
     */
    public JLabel getLblUsuario() {
        return lblUsuario;
    }

    /**
     * Establece la etiqueta del usuario.
     * @param lblUsuario JLabel a asignar.
     */
    public void setLblUsuario(JLabel lblUsuario) {
        this.lblUsuario = lblUsuario;
    }

    /**
     * Devuelve la etiqueta de la contraseña.
     * @return JLabel que indica el campo contraseña.
     */
    public JLabel getLblContrasenia() {
        return lblContrasenia;
    }

    /**
     * Establece la etiqueta de la contraseña.
     * @param lblContrasenia JLabel a asignar.
     */
    public void setLblContrasenia(JLabel lblContrasenia) {
        this.lblContrasenia = lblContrasenia;
    }

    /**
     * Devuelve el botón salir.
     * @return JButton para salir de la aplicación.
     */
    public JButton getBtnSalir() {
        return btnSalir;
    }

    /**
     * Establece el botón salir.
     * @param btnSalir JButton a asignar.
     */
    public void setBtnSalir(JButton btnSalir) {
        this.btnSalir = btnSalir;
    }

    /**
     * Devuelve el botón para olvidar contraseña.
     * @return JButton para recuperar contraseña.
     */
    public JButton getBtnOlvidar() {
        return btnOlvidar;
    }

    /**
     * Establece el botón para olvidar contraseña.
     * @param btnOlvidar JButton a asignar.
     */
    public void setBtnOlvidar(JButton btnOlvidar) {
        this.btnOlvidar = btnOlvidar;
    }

    /**
     * Devuelve el campo de texto para el nombre de usuario.
     * @return JTextField donde se ingresa el nombre de usuario.
     */
    public JTextField getTxtUsername() {
        return txtUsername;
    }

    /**
     * Establece el campo de texto para el nombre de usuario.
     * @param txtUsername JTextField a asignar.
     */
    public void setTxtUsername(JTextField txtUsername) {
        this.txtUsername = txtUsername;
    }

    /**
     * Devuelve el campo de texto para la contraseña.
     * @return JPasswordField donde se ingresa la contraseña.
     */
    public JPasswordField getTxtContrasenia() {
        return txtContrasenia;
    }

    /**
     * Establece el campo de texto para la contraseña.
     * @param txtContrasenia JPasswordField a asignar.
     */
    public void setTxtContrasenia(JPasswordField txtContrasenia) {
        this.txtContrasenia = txtContrasenia;
    }

    /**
     * Devuelve el botón para iniciar sesión.
     * @return JButton para iniciar sesión.
     */
    public JButton getBtnIniciar() {
        return btnIniciar;
    }

    /**
     * Establece el botón para iniciar sesión.
     * @param btnIniciar JButton a asignar.
     */
    public void setBtnIniciar(JButton btnIniciar) {
        this.btnIniciar = btnIniciar;
    }

    /**
     * Devuelve el botón para registrar un nuevo usuario.
     * @return JButton para registrar usuario.
     */
    public JButton getBtnRegistrar() {
        return btnRegistrar;
    }

    /**
     * Establece el botón para registrar un nuevo usuario.
     * @param btnRegistrar JButton a asignar.
     */
    public void setBtnRegistrar(JButton btnRegistrar) {
        this.btnRegistrar = btnRegistrar;
    }

}
