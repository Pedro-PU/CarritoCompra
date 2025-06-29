package ec.edu.ups.poo.clases.vista.usuario;

import ec.edu.ups.poo.clases.util.MensajeInternacionalizacionHandler;

import javax.swing.*;

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

    public LoginView(MensajeInternacionalizacionHandler mi) {
        this.mi = mi;
        setTitle("Iniciar Sesion");
        //setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);
        add(panelPrincipal);
        inicializarComponentes();
    }

    public JComboBox getCbxIdiomas() {
        return cbxIdiomas;
    }

    public void setCbxIdiomas(JComboBox cbxIdiomas) {
        this.cbxIdiomas = cbxIdiomas;
    }

    public JLabel getLblIdioma() {
        return lblIdioma;
    }

    public void setLblIdioma(JLabel lblIdioma) {
        this.lblIdioma = lblIdioma;
    }

    public JLabel getLblTitulo() {
        return lblTitulo;
    }

    public void setLblTitulo(JLabel lblTitulo) {
        this.lblTitulo = lblTitulo;
    }

    public JLabel getLblUsuario() {
        return lblUsuario;
    }

    public void setLblUsuario(JLabel lblUsuario) {
        this.lblUsuario = lblUsuario;
    }

    public JLabel getLblContrasenia() {
        return lblContrasenia;
    }

    public void setLblContrasenia(JLabel lblContrasenia) {
        this.lblContrasenia = lblContrasenia;
    }

    public JButton getBtnSalir() {
        return btnSalir;
    }

    public void setBtnSalir(JButton btnSalir) {
        this.btnSalir = btnSalir;
    }

    public JButton getBtnOlvidar() {
        return btnOlvidar;
    }

    public void setBtnOlvidar(JButton btnOlvidar) {
        this.btnOlvidar = btnOlvidar;
    }

    public JTextField getTxtUsername() {
        return txtUsername;
    }

    public void setTxtUsername(JTextField txtUsername) {
        this.txtUsername = txtUsername;
    }

    public JPasswordField getTxtContrasenia() {
        return txtContrasenia;
    }

    public void setTxtContrasenia(JPasswordField txtContrasenia) {
        this.txtContrasenia = txtContrasenia;
    }

    public JButton getBtnIniciar() {
        return btnIniciar;
    }

    public void setBtnIniciar(JButton btnIniciar) {
        this.btnIniciar = btnIniciar;
    }

    public JButton getBtnRegistrar() {
        return btnRegistrar;
    }

    public void setBtnRegistrar(JButton btnRegistrar) {
        this.btnRegistrar = btnRegistrar;
    }

    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }

    public boolean mostrarMensajePregunta(String mensaje) {
        int respuesta = JOptionPane.showConfirmDialog(this, mensaje, "Confirmación",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        return respuesta == JOptionPane.YES_OPTION;
    }

    public void inicializarComponentes() {
        cbxIdiomas.removeAllItems();
        cbxIdiomas.addItem(mi.get("menu.idioma.es")); // Español
        cbxIdiomas.addItem(mi.get("menu.idioma.en")); // Inglés
        cbxIdiomas.addItem(mi.get("menu.idioma.fr")); // Francés
        String lang = mi.getLocale().getLanguage();
        String country = mi.getLocale().getCountry();
        int selectedIndex = 0;
        if ("en".equals(lang) && "US".equals(country)) {
            selectedIndex = 1;
        } else if ("fr".equals(lang) && "FR".equals(country)) {
            selectedIndex = 2;
        }
        cbxIdiomas.setSelectedIndex(selectedIndex);
        actualizarTextos();
    }

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

        UIManager.put("OptionPane.yesButtonText", mi.get("dialogo.boton.si"));
        UIManager.put("OptionPane.noButtonText", mi.get("dialogo.boton.no"));
        UIManager.put("OptionPane.cancelButtonText", mi.get("dialogo.boton.cancelar"));
        UIManager.put("OptionPane.okButtonText", mi.get("dialogo.boton.aceptar"));
    }

}
