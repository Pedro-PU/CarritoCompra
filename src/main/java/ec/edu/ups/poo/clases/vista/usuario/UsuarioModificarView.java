package ec.edu.ups.poo.clases.vista.usuario;

import ec.edu.ups.poo.clases.util.MensajeInternacionalizacionHandler;

import javax.swing.*;
import java.net.URL;
/**
 * Clase que representa la ventana interna para modificar usuarios.
 * Permite buscar un usuario, editar sus datos y guardar los cambios.
 */
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
    private JLabel lblNombre;
    private JTextField txtNombre;
    private JLabel lblFecha;
    private JSpinner spnDia;
    private JSpinner spnMes;
    private JSpinner spnAnio;
    private JLabel lblCelular;
    private JTextField txtCelular;
    private JLabel lblCorreo;
    private JTextField txtCorreo;
    private MensajeInternacionalizacionHandler mi;
    /**
     * Constructor que inicializa la ventana de edición de usuarios.
     * Configura el tamaño, título, idioma, íconos y modelos para los spinners de fecha.
     * Inicialmente deshabilita los campos de edición hasta que se busque un usuario.
     * @param mi Manejador de mensajes para la internacionalización.
     */
    public UsuarioModificarView(MensajeInternacionalizacionHandler mi) {
        super("Editar Usuarios", true,true,false,true);
        this.mi = mi;
        setContentPane(panelPrincipal);
        setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);
        setSize(500, 500);
        cambiarIdioma();
        inicializarImagenes();
        spnDia.setModel(new SpinnerNumberModel(1, 1, 31, 1));
        spnMes.setModel(new SpinnerNumberModel(1, 1, 12, 1));
        spnAnio.setModel(new SpinnerNumberModel(2000, 1, 2100, 1));
        habilitarCampos(false);

    }
    /**
     * Cambia los textos de todos los componentes a los correspondientes en el idioma seleccionado.
     * Utiliza el manejador de internacionalización para obtener los textos.
     */
    public void cambiarIdioma() {
        setTitle(mi.get("usuario.modificar.titulo.ventana"));
        lblBuscar.setText(mi.get("usuario.modificar.buscar"));
        lblUsername.setText(mi.get("usuario.modificar.nombre"));
        lblContrasenia.setText(mi.get("usuario.modificar.contrasena"));
        btnBuscar.setText(mi.get("usuario.modificar.buscar.btn"));
        btnEditar.setText(mi.get("usuario.modificar.editar.btn"));

        UIManager.put("OptionPane.yesButtonText", mi.get("dialogo.boton.si"));
        UIManager.put("OptionPane.noButtonText", mi.get("dialogo.boton.no"));
        UIManager.put("OptionPane.cancelButtonText", mi.get("dialogo.boton.cancelar"));
        UIManager.put("OptionPane.okButtonText", mi.get("dialogo.boton.aceptar"));
    }
    /**
     * Muestra un mensaje emergente informativo al usuario.
     * @param mensaje Texto a mostrar en el diálogo.
     */
    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }
    /**
     * Muestra un cuadro de diálogo con pregunta de confirmación y devuelve
     * true si el usuario elige "Sí".
     * @param mensaje Pregunta a mostrar.
     * @return true si el usuario confirma, false en otro caso.
     */
    public boolean mostrarMensajePregunta(String mensaje) {
        int respuesta = JOptionPane.showConfirmDialog(this, mensaje, mi.get("dialogo.title.pregunta"),
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        return respuesta == JOptionPane.YES_OPTION;
    }
    /**
     * Limpia todos los campos de entrada del formulario, dejando los spinners de fecha en 0.
     */
    public void limpiarCampos() {
        txtName.setText("");
        txtUsername.setText("");
        txtContrasenia.setText("");
        txtNombre.setText("");
        txtCelular.setText("");
        txtCorreo.setText("");
        spnDia.setValue(0);
        spnMes.setValue(0);
        spnAnio.setValue(0);
    }
    /**
     * Carga y asigna los iconos a los botones Buscar y Editar desde los recursos.
     */
    public void inicializarImagenes(){
        URL buscar = UsuarioModificarView.class.getClassLoader().getResource("imagenes/buscar.png");
        if (buscar != null) {
            ImageIcon iconoBtnIniciarSesion = new ImageIcon(buscar);
            btnBuscar.setIcon(iconoBtnIniciarSesion);
        } else {
            System.err.println("Error: No se ha cargado el icono de Login");
        }

        URL editar = UsuarioModificarView.class.getClassLoader().getResource("imagenes/editar.png");
        if (editar != null) {
            ImageIcon iconoBtnIniciarSesion = new ImageIcon(editar);
            btnEditar.setIcon(iconoBtnIniciarSesion);
        } else {
            System.err.println("Error: No se ha cargado el icono de Login");
        }
    }
    /**
     * Habilita o deshabilita los campos de edición de datos del usuario.
     * El campo de usuario (username) siempre permanece deshabilitado para evitar modificaciones.
     * @param habilitar true para activar los campos de edición, false para desactivarlos.
     */
    public void habilitarCampos(boolean habilitar) {
        txtUsername.setEnabled(false);
        txtContrasenia.setEnabled(habilitar);
        txtNombre.setEnabled(habilitar);
        txtCelular.setEnabled(habilitar);
        txtCorreo.setEnabled(habilitar);
        spnDia.setEnabled(habilitar);
        spnMes.setEnabled(habilitar);
        spnAnio.setEnabled(habilitar);
    }
    /**
     * Obtiene la etiqueta del texto de búsqueda.
     * @return JLabel correspondiente a la etiqueta buscar.
     */
    public JLabel getLblBuscar() {
        return lblBuscar;
    }

    /**
     * Establece la etiqueta del texto de búsqueda.
     * @param lblBuscar JLabel a asignar.
     */
    public void setLblBuscar(JLabel lblBuscar) {
        this.lblBuscar = lblBuscar;
    }

    /**
     * Obtiene la etiqueta para el nombre de usuario.
     * @return JLabel correspondiente al nombre de usuario.
     */
    public JLabel getLblUsername() {
        return lblUsername;
    }

    /**
     * Establece la etiqueta para el nombre de usuario.
     * @param lblUsername JLabel a asignar.
     */
    public void setLblUsername(JLabel lblUsername) {
        this.lblUsername = lblUsername;
    }

    /**
     * Obtiene la etiqueta para la contraseña.
     * @return JLabel correspondiente a la contraseña.
     */
    public JLabel getLblContrasenia() {
        return lblContrasenia;
    }

    /**
     * Establece la etiqueta para la contraseña.
     * @param lblContrasenia JLabel a asignar.
     */
    public void setLblContrasenia(JLabel lblContrasenia) {
        this.lblContrasenia = lblContrasenia;
    }

    /**
     * Obtiene la etiqueta para el nombre.
     * @return JLabel correspondiente al nombre.
     */
    public JLabel getLblNombre() {
        return lblNombre;
    }

    /**
     * Establece la etiqueta para el nombre.
     * @param lblNombre JLabel a asignar.
     */
    public void setLblNombre(JLabel lblNombre) {
        this.lblNombre = lblNombre;
    }

    /**
     * Obtiene el campo de texto para el nombre.
     * @return JTextField correspondiente al nombre.
     */
    public JTextField getTxtNombre() {
        return txtNombre;
    }

    /**
     * Establece el campo de texto para el nombre.
     * @param txtNombre JTextField a asignar.
     */
    public void setTxtNombre(JTextField txtNombre) {
        this.txtNombre = txtNombre;
    }

    /**
     * Obtiene la etiqueta para la fecha.
     * @return JLabel correspondiente a la fecha.
     */
    public JLabel getLblFecha() {
        return lblFecha;
    }

    /**
     * Establece la etiqueta para la fecha.
     * @param lblFecha JLabel a asignar.
     */
    public void setLblFecha(JLabel lblFecha) {
        this.lblFecha = lblFecha;
    }

    /**
     * Obtiene el spinner para el día.
     * @return JSpinner para el día.
     */
    public JSpinner getSpnDia() {
        return spnDia;
    }

    /**
     * Establece el spinner para el día.
     * @param spnDia JSpinner a asignar.
     */
    public void setSpnDia(JSpinner spnDia) {
        this.spnDia = spnDia;
    }

    /**
     * Obtiene el spinner para el mes.
     * @return JSpinner para el mes.
     */
    public JSpinner getSpnMes() {
        return spnMes;
    }

    /**
     * Establece el spinner para el mes.
     * @param spnMes JSpinner a asignar.
     */
    public void setSpnMes(JSpinner spnMes) {
        this.spnMes = spnMes;
    }

    /**
     * Obtiene el spinner para el año.
     * @return JSpinner para el año.
     */
    public JSpinner getSpnAnio() {
        return spnAnio;
    }

    /**
     * Establece el spinner para el año.
     * @param spnAnio JSpinner a asignar.
     */
    public void setSpnAnio(JSpinner spnAnio) {
        this.spnAnio = spnAnio;
    }

    /**
     * Obtiene la etiqueta para el celular.
     * @return JLabel correspondiente al celular.
     */
    public JLabel getLblCelular() {
        return lblCelular;
    }

    /**
     * Establece la etiqueta para el celular.
     * @param lblCelular JLabel a asignar.
     */
    public void setLblCelular(JLabel lblCelular) {
        this.lblCelular = lblCelular;
    }

    /**
     * Obtiene el campo de texto para el celular.
     * @return JTextField correspondiente al celular.
     */
    public JTextField getTxtCelular() {
        return txtCelular;
    }

    /**
     * Establece el campo de texto para el celular.
     * @param txtCelular JTextField a asignar.
     */
    public void setTxtCelular(JTextField txtCelular) {
        this.txtCelular = txtCelular;
    }

    /**
     * Obtiene la etiqueta para el correo.
     * @return JLabel correspondiente al correo.
     */
    public JLabel getLblCorreo() {
        return lblCorreo;
    }

    /**
     * Establece la etiqueta para el correo.
     * @param lblCorreo JLabel a asignar.
     */
    public void setLblCorreo(JLabel lblCorreo) {
        this.lblCorreo = lblCorreo;
    }

    /**
     * Obtiene el campo de texto para el correo.
     * @return JTextField correspondiente al correo.
     */
    public JTextField getTxtCorreo() {
        return txtCorreo;
    }

    /**
     * Establece el campo de texto para el correo.
     * @param txtCorreo JTextField a asignar.
     */
    public void setTxtCorreo(JTextField txtCorreo) {
        this.txtCorreo = txtCorreo;
    }

    /**
     * Obtiene el campo de texto para el nombre (txtName).
     * @return JTextField correspondiente al nombre.
     */
    public JTextField getTxtName() {
        return txtName;
    }

    /**
     * Establece el campo de texto para el nombre (txtName).
     * @param txtName JTextField a asignar.
     */
    public void setTxtName(JTextField txtName) {
        this.txtName = txtName;
    }

    /**
     * Obtiene el campo de texto para el nombre de usuario.
     * @return JTextField correspondiente al username.
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
     * Obtiene el botón para buscar.
     * @return JButton botón buscar.
     */
    public JButton getBtnBuscar() {
        return btnBuscar;
    }

    /**
     * Establece el botón para buscar.
     * @param btnBuscar JButton a asignar.
     */
    public void setBtnBuscar(JButton btnBuscar) {
        this.btnBuscar = btnBuscar;
    }

    /**
     * Obtiene el campo de texto para la contraseña.
     * @return JTextField correspondiente a la contraseña.
     */
    public JTextField getTxtContrasenia() {
        return txtContrasenia;
    }

    /**
     * Establece el campo de texto para la contraseña.
     * @param txtContrasenia JTextField a asignar.
     */
    public void setTxtContrasenia(JTextField txtContrasenia) {
        this.txtContrasenia = txtContrasenia;
    }

    /**
     * Obtiene el botón para editar.
     * @return JButton botón editar.
     */
    public JButton getBtnEditar() {
        return btnEditar;
    }

    /**
     * Establece el botón para editar.
     * @param btnEditar JButton a asignar.
     */
    public void setBtnEditar(JButton btnEditar) {
        this.btnEditar = btnEditar;
    }

}
