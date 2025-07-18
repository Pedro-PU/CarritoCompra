package ec.edu.ups.poo.clases.vista.usuario;

import ec.edu.ups.poo.clases.util.MensajeInternacionalizacionHandler;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
/**
 * Clase que representa la ventana interna para crear usuarios.
 * Permite ingresar datos del usuario y realizar acciones de registro.
 */
public class UsuarioCrearView extends JInternalFrame {
    private JPanel panelPrincipal;
    private JTextField txtUsername;
    private JTextField txtContrasenia;
    private JButton btnAceptar;
    private JButton btnLimpiar;
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
    private JTextField txtCorreo;
    private JLabel lblCorreo;
    private MensajeInternacionalizacionHandler mi;
    /**
     * Constructor que inicializa la ventana para crear usuarios,
     * configura el contenido, modelos de spinners, eventos y carga imágenes.
     * @param mi Manejador de mensajes para internacionalización.
     */
    public UsuarioCrearView(MensajeInternacionalizacionHandler mi) {
        super("Registrar Usuarios", true,true,false,true);
        this.mi = mi;
        setContentPane(panelPrincipal);
        setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);
        setSize(500, 500);
        spnDia.setModel(new SpinnerNumberModel(1, 1, 31, 1));
        spnMes.setModel(new SpinnerNumberModel(1, 1, 12, 1));
        spnAnio.setModel(new SpinnerNumberModel(2000, 1, 2100, 1));

        btnLimpiar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                limpiarCampos();
            }
        });
        cambiarIdioma();
        inicializarImagenes();
    }
    /**
     * Limpia todos los campos del formulario, dejando los valores vacíos o por defecto.
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
     * Muestra un mensaje emergente simple para informar al usuario.
     * @param mensaje Texto del mensaje a mostrar.
     */
    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }
    /**
     * Muestra un cuadro de diálogo de confirmación con opciones Sí y No,
     * retornando true si el usuario elige Sí.
     * @param mensaje Texto de la pregunta a mostrar.
     * @return true si el usuario selecciona Sí, false en caso contrario.
     */
    public boolean mostrarMensajePregunta(String mensaje) {
        int respuesta = JOptionPane.showConfirmDialog(this, mensaje, mi.get("dialogo.title.pregunta"),
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        return respuesta == JOptionPane.YES_OPTION;
    }
    /**
     * Carga las imágenes (íconos) para los botones Aceptar y Limpiar,
     * mostrando un mensaje de error si no se cargan correctamente.
     */
    public void inicializarImagenes(){
        URL aceptar = UsuarioCrearView.class.getClassLoader().getResource("imagenes/aceptar.png");
        if (aceptar != null) {
            ImageIcon iconoBtnIniciarSesion = new ImageIcon(aceptar);
            btnAceptar.setIcon(iconoBtnIniciarSesion);
        } else {
            System.err.println("Error: No se ha cargado el icono de Login");
        }

        URL limpiar = UsuarioCrearView.class.getClassLoader().getResource("imagenes/limpiar.png");
        if (limpiar != null) {
            ImageIcon iconoBtnRegistrarse = new ImageIcon(limpiar);
            btnLimpiar.setIcon(iconoBtnRegistrarse);
        } else {
            System.err.println("Error: No se ha cargado el icono de Registrarse");
        }
    }
    /**
     * Actualiza los textos de todos los componentes de la ventana
     * según el idioma configurado en el manejador de mensajes.
     */
    public void cambiarIdioma() {
        setTitle(mi.get("usuario.crear.titulo.ventana"));
        lblTitulo.setText(mi.get("usuario.crear.titulo"));
        lblUsername.setText(mi.get("usuario.crear.nombre"));
        lblContrasenia.setText(mi.get("usuario.crear.contrasena"));
        lblNombre.setText(mi.get("usuario.crear.nombre.real"));
        lblFecha.setText(mi.get("usuario.crear.fecha"));
        lblCelular.setText(mi.get("usuario.crear.celular"));
        lblCorreo.setText(mi.get("usuario.crear.correo"));
        btnAceptar.setText(mi.get("usuario.crear.aceptar"));
        btnLimpiar.setText(mi.get("usuario.crear.limpiar"));

        UIManager.put("OptionPane.yesButtonText", mi.get("dialogo.boton.si"));
        UIManager.put("OptionPane.noButtonText", mi.get("dialogo.boton.no"));
        UIManager.put("OptionPane.cancelButtonText", mi.get("dialogo.boton.cancelar"));
        UIManager.put("OptionPane.okButtonText", mi.get("dialogo.boton.aceptar"));
    }
    // Getters y Setters

    /**
     * Obtiene el campo de texto para el correo electrónico.
     * @return JTextField del correo.
     */
    public JTextField getTxtCorreo() {
        return txtCorreo;
    }

    /**
     * Establece el campo de texto para el correo electrónico.
     * @param txtCorreo JTextField a asignar.
     */
    public void setTxtCorreo(JTextField txtCorreo) {
        this.txtCorreo = txtCorreo;
    }

    /**
     * Obtiene la etiqueta del correo electrónico.
     * @return JLabel del correo.
     */
    public JLabel getLblCorreo() {
        return lblCorreo;
    }

    /**
     * Establece la etiqueta del correo electrónico.
     * @param lblCorreo JLabel a asignar.
     */
    public void setLblCorreo(JLabel lblCorreo) {
        this.lblCorreo = lblCorreo;
    }

    /**
     * Obtiene el botón Limpiar.
     * @return JButton Limpiar.
     */
    public JButton getBtnLimpiar() {
        return btnLimpiar;
    }

    /**
     * Establece el botón Limpiar.
     * @param btnLimpiar JButton a asignar.
     */
    public void setBtnLimpiar(JButton btnLimpiar) {
        this.btnLimpiar = btnLimpiar;
    }

    /**
     * Obtiene la etiqueta del nombre.
     * @return JLabel del nombre.
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
     * @return JTextField del nombre.
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
     * @return JLabel de la fecha.
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
     * @return JSpinner del día.
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
     * @return JSpinner del mes.
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
     * @return JSpinner del año.
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
     * @return JLabel del celular.
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
     * Obtiene el campo de texto del celular.
     * @return JTextField del celular.
     */
    public JTextField getTxtCelular() {
        return txtCelular;
    }

    /**
     * Establece el campo de texto del celular.
     * @param txtCelular JTextField a asignar.
     */
    public void setTxtCelular(JTextField txtCelular) {
        this.txtCelular = txtCelular;
    }

    /**
     * Obtiene la etiqueta del título.
     * @return JLabel del título.
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
     * @return JLabel del username.
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
     * @return JLabel de la contraseña.
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
     * Obtiene el campo de texto del username.
     * @return JTextField del username.
     */
    public JTextField getTxtUsername() {
        return txtUsername;
    }

    /**
     * Establece el campo de texto del username.
     * @param txtUsername JTextField a asignar.
     */
    public void setTxtUsername(JTextField txtUsername) {
        this.txtUsername = txtUsername;
    }

    /**
     * Obtiene el campo de texto de la contraseña.
     * @return JTextField de la contraseña.
     */
    public JTextField getTxtContrasenia() {
        return txtContrasenia;
    }

    /**
     * Establece el campo de texto de la contraseña.
     * @param txtContrasenia JTextField a asignar.
     */
    public void setTxtContrasenia(JTextField txtContrasenia) {
        this.txtContrasenia = txtContrasenia;
    }

    /**
     * Obtiene el botón Aceptar.
     * @return JButton Aceptar.
     */
    public JButton getBtnAceptar() {
        return btnAceptar;
    }

    /**
     * Establece el botón Aceptar.
     * @param btnAceptar JButton a asignar.
     */
    public void setBtnAceptar(JButton btnAceptar) {
        this.btnAceptar = btnAceptar;
    }

    /**
     * Obtiene el botón Limpiar (se usa también como botón Salir).
     * @return JButton Limpiar.
     */
    public JButton getBtnSalir() {
        return btnLimpiar;
    }

    /**
     * Establece el botón Limpiar (se usa también como botón Salir).
     * @param btnSalir JButton a asignar.
     */
    public void setBtnSalir(JButton btnSalir) {
        this.btnLimpiar = btnSalir;
    }


}
