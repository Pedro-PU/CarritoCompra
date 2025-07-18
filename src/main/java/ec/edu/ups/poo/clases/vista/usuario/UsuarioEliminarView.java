package ec.edu.ups.poo.clases.vista.usuario;

import ec.edu.ups.poo.clases.util.MensajeInternacionalizacionHandler;

import javax.swing.*;
import java.net.URL;
/**
 * Vista interna para eliminar usuarios.
 * Contiene campos para mostrar y modificar datos de usuario antes de eliminarlo.
 */
public class UsuarioEliminarView extends JInternalFrame {
    private JPanel panelPrincipal;
    private JTextField txtUsername;
    private JTextField txtContrasenia;
    private JButton btnEliminar;
    private JButton btnBuscar;
    private JLabel lblTitulo;
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
     * Constructor que inicializa la ventana y sus componentes.
     * @param mi Manejador de mensajes para internacionalización.
     */
    public UsuarioEliminarView(MensajeInternacionalizacionHandler mi) {
        super("Eliminar Usuarios", true,true,false,true);
        this.mi = mi;
        setContentPane(panelPrincipal);
        setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);
        setSize(500, 500);
        cambiarIdioma();
        inicializarImagenes();
    }
    /**
     * Limpia todos los campos del formulario, dejando los valores en vacío o cero.
     */
    public void limpiarCampos() {
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
     * Muestra un mensaje informativo en un diálogo emergente.
     * @param mensaje Texto a mostrar.
     */
    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }
    /**
     * Muestra un cuadro de diálogo con una pregunta de confirmación.
     * @param mensaje Texto de la pregunta.
     * @return true si el usuario selecciona "Sí", false en caso contrario.
     */
    public boolean mostrarMensajePregunta(String mensaje) {
        int respuesta = JOptionPane.showConfirmDialog(this, mensaje, mi.get("dialogo.title.pregunta"),
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        return respuesta == JOptionPane.YES_OPTION;
    }
    /**
     * Actualiza los textos de todos los componentes según el idioma seleccionado.
     */
    public void cambiarIdioma() {
        mi.setLenguaje(mi.getLocale().getLanguage(), mi.getLocale().getCountry());

        setTitle(mi.get("usuario.eliminar.titulo.ventana"));
        lblTitulo.setText(mi.get("usuario.eliminar.titulo"));
        lblUsername.setText(mi.get("usuario.eliminar.nombre"));
        lblContrasenia.setText(mi.get("usuario.eliminar.contrasena"));
        lblNombre.setText(mi.get("usuario.eliminar.nombre.real"));
        lblFecha.setText(mi.get("usuario.eliminar.fecha"));
        lblCelular.setText(mi.get("usuario.eliminar.celular"));
        lblCorreo.setText(mi.get("usuario.eliminar.correo"));
        btnBuscar.setText(mi.get("usuario.eliminar.buscar"));
        btnEliminar.setText(mi.get("usuario.eliminar.eliminar"));

        UIManager.put("OptionPane.yesButtonText", mi.get("dialogo.boton.si"));
        UIManager.put("OptionPane.noButtonText", mi.get("dialogo.boton.no"));
        UIManager.put("OptionPane.cancelButtonText", mi.get("dialogo.boton.cancelar"));
        UIManager.put("OptionPane.okButtonText", mi.get("dialogo.boton.aceptar"));
    }
    /**
     * Carga los iconos en los botones desde recursos.
     * Muestra mensajes de error si no se cargan correctamente.
     */
    public void inicializarImagenes(){
        URL buscar = UsuarioEliminarView.class.getClassLoader().getResource("imagenes/buscar.png");
        if (buscar != null) {
            ImageIcon iconoBtnIniciarSesion = new ImageIcon(buscar);
            btnBuscar.setIcon(iconoBtnIniciarSesion);
        } else {
            System.err.println("Error: No se ha cargado el icono de Login");
        }

        URL eliminar = UsuarioEliminarView.class.getClassLoader().getResource("imagenes/eliminar.png");
        if (eliminar != null) {
            ImageIcon iconoBtnIniciarSesion = new ImageIcon(eliminar);
            btnEliminar.setIcon(iconoBtnIniciarSesion);
        } else {
            System.err.println("Error: No se ha cargado el icono de Login");
        }
    }
    // Getters y Setters

    /**
     * Obtiene la etiqueta del nombre.
     * @return JLabel con el nombre.
     */
    public JLabel getLblNombre() {
        return lblNombre;
    }

    /**
     * Establece la etiqueta del nombre.
     * @param lblNombre JLabel a asignar.
     */
    public void setLblNombre(JLabel lblNombre) {
        this.lblNombre = lblNombre;
    }

    /**
     * Obtiene el campo de texto para el nombre.
     * @return JTextField con el nombre.
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
     * Obtiene la etiqueta de la fecha.
     * @return JLabel con la fecha.
     */
    public JLabel getLblFecha() {
        return lblFecha;
    }

    /**
     * Establece la etiqueta de la fecha.
     * @param lblFecha JLabel a asignar.
     */
    public void setLblFecha(JLabel lblFecha) {
        this.lblFecha = lblFecha;
    }

    /**
     * Obtiene el spinner del día.
     * @return JSpinner para el día.
     */
    public JSpinner getSpnDia() {
        return spnDia;
    }

    /**
     * Establece el spinner del día.
     * @param spnDia JSpinner a asignar.
     */
    public void setSpnDia(JSpinner spnDia) {
        this.spnDia = spnDia;
    }

    /**
     * Obtiene el spinner del mes.
     * @return JSpinner para el mes.
     */
    public JSpinner getSpnMes() {
        return spnMes;
    }

    /**
     * Establece el spinner del mes.
     * @param spnMes JSpinner a asignar.
     */
    public void setSpnMes(JSpinner spnMes) {
        this.spnMes = spnMes;
    }

    /**
     * Obtiene el spinner del año.
     * @return JSpinner para el año.
     */
    public JSpinner getSpnAnio() {
        return spnAnio;
    }

    /**
     * Establece el spinner del año.
     * @param spnAnio JSpinner a asignar.
     */
    public void setSpnAnio(JSpinner spnAnio) {
        this.spnAnio = spnAnio;
    }

    /**
     * Obtiene la etiqueta del celular.
     * @return JLabel con el celular.
     */
    public JLabel getLblCelular() {
        return lblCelular;
    }

    /**
     * Establece la etiqueta del celular.
     * @param lblCelular JLabel a asignar.
     */
    public void setLblCelular(JLabel lblCelular) {
        this.lblCelular = lblCelular;
    }

    /**
     * Obtiene el campo de texto para el celular.
     * @return JTextField con el celular.
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
     * Obtiene la etiqueta del correo.
     * @return JLabel con el correo.
     */
    public JLabel getLblCorreo() {
        return lblCorreo;
    }

    /**
     * Establece la etiqueta del correo.
     * @param lblCorreo JLabel a asignar.
     */
    public void setLblCorreo(JLabel lblCorreo) {
        this.lblCorreo = lblCorreo;
    }

    /**
     * Obtiene el campo de texto para el correo.
     * @return JTextField con el correo.
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
     * Obtiene la etiqueta del título.
     * @return JLabel con el título.
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
     * Obtiene la etiqueta del username.
     * @return JLabel con el username.
     */
    public JLabel getLblUsername() {
        return lblUsername;
    }

    /**
     * Establece la etiqueta del username.
     * @param lblUsername JLabel a asignar.
     */
    public void setLblUsername(JLabel lblUsername) {
        this.lblUsername = lblUsername;
    }

    /**
     * Obtiene la etiqueta de la contraseña.
     * @return JLabel con la contraseña.
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
     * Obtiene el botón de buscar.
     * @return JButton para buscar.
     */
    public JButton getBtnBuscar() {
        return btnBuscar;
    }

    /**
     * Establece el botón de buscar.
     * @param btnBuscar JButton a asignar.
     */
    public void setBtnBuscar(JButton btnBuscar) {
        this.btnBuscar = btnBuscar;
    }

    /**
     * Obtiene el campo de texto para el username.
     * @return JTextField con el username.
     */
    public JTextField getTxtUsername() {
        return txtUsername;
    }

    /**
     * Establece el campo de texto para el username.
     * @param txtUsername JTextField a asignar.
     */
    public void setTxtUsername(JTextField txtUsername) {
        this.txtUsername = txtUsername;
    }

    /**
     * Obtiene el campo de texto para la contraseña.
     * @return JTextField con la contraseña.
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
     * Obtiene el botón para eliminar.
     * @return JButton para eliminar.
     */
    public JButton getBtnEliminar() {
        return btnEliminar;
    }

    /**
     * Establece el botón para eliminar.
     * @param btnEliminar JButton a asignar.
     */
    public void setBtnEliminar(JButton btnEliminar) {
        this.btnEliminar = btnEliminar;
    }
}
