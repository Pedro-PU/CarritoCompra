package ec.edu.ups.poo.clases.vista.usuario;

import ec.edu.ups.poo.clases.util.MensajeInternacionalizacionHandler;

import javax.swing.*;
import java.net.URL;

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

    public UsuarioEliminarView(MensajeInternacionalizacionHandler mi) {
        super("Eliminar Usuarios", true,true,false,true);
        this.mi = mi;
        setContentPane(panelPrincipal);
        setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);
        setSize(500, 500);
        cambiarIdioma();
        inicializarImagenes();
    }
    // Limpia los campos del formulario
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
    // Muestra un mensaje informativo en un diálogo emergente
    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }
    // Muestra una pregunta de confirmación y devuelve true si el usuario presiona "Sí"
    public boolean mostrarMensajePregunta(String mensaje) {
        int respuesta = JOptionPane.showConfirmDialog(this, mensaje, mi.get("dialogo.title.pregunta"),
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        return respuesta == JOptionPane.YES_OPTION;
    }
    // Establece los textos traducidos de todos los componentes
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
    // Carga los íconos en los botones
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
    public JLabel getLblNombre() {
        return lblNombre;
    }

    public void setLblNombre(JLabel lblNombre) {
        this.lblNombre = lblNombre;
    }

    public JTextField getTxtNombre() {
        return txtNombre;
    }

    public void setTxtNombre(JTextField txtNombre) {
        this.txtNombre = txtNombre;
    }

    public JLabel getLblFecha() {
        return lblFecha;
    }

    public void setLblFecha(JLabel lblFecha) {
        this.lblFecha = lblFecha;
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

    public JLabel getLblCelular() {
        return lblCelular;
    }

    public void setLblCelular(JLabel lblCelular) {
        this.lblCelular = lblCelular;
    }

    public JTextField getTxtCelular() {
        return txtCelular;
    }

    public void setTxtCelular(JTextField txtCelular) {
        this.txtCelular = txtCelular;
    }

    public JLabel getLblCorreo() {
        return lblCorreo;
    }

    public void setLblCorreo(JLabel lblCorreo) {
        this.lblCorreo = lblCorreo;
    }

    public JTextField getTxtCorreo() {
        return txtCorreo;
    }

    public void setTxtCorreo(JTextField txtCorreo) {
        this.txtCorreo = txtCorreo;
    }

    public JLabel getLblTitulo() {
        return lblTitulo;
    }

    public void setLblTitulo(JLabel lblTitulo) {
        this.lblTitulo = lblTitulo;
    }

    public JLabel getLblUsername() {
        return lblUsername;
    }

    public void setLblUsername(JLabel lblUsername) {
        this.lblUsername = lblUsername;
    }

    public JLabel getLblContrasenia() {
        return lblContrasenia;
    }

    public void setLblContrasenia(JLabel lblContrasenia) {
        this.lblContrasenia = lblContrasenia;
    }

    public JButton getBtnBuscar() {
        return btnBuscar;
    }

    public void setBtnBuscar(JButton btnBuscar) {
        this.btnBuscar = btnBuscar;
    }

    public JTextField getTxtUsername() {
        return txtUsername;
    }

    public void setTxtUsername(JTextField txtUsername) {
        this.txtUsername = txtUsername;
    }

    public JTextField getTxtContrasenia() {
        return txtContrasenia;
    }

    public void setTxtContrasenia(JTextField txtContrasenia) {
        this.txtContrasenia = txtContrasenia;
    }

    public JButton getBtnEliminar() {
        return btnEliminar;
    }

    public void setBtnEliminar(JButton btnEliminar) {
        this.btnEliminar = btnEliminar;
    }

}
