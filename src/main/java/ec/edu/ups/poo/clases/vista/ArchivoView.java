package ec.edu.ups.poo.clases.vista;

import ec.edu.ups.poo.clases.util.MensajeInternacionalizacionHandler;
import ec.edu.ups.poo.clases.vista.usuario.LoginView;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.net.URL;
import java.util.Locale;

public class ArchivoView extends JFrame{
    private JLabel lblTitulo;
    private JComboBox cbxTiposGuardado;
    private JLabel lblArchivo;
    private JButton btnGuardar;
    private JButton btnSalir;
    private JComboBox cbxIdiomas;
    private JLabel lblIdioma;
    private JPanel panelPrincipal;
    private MensajeInternacionalizacionHandler mi;

    public ArchivoView( MensajeInternacionalizacionHandler mi) {
        this.mi = mi;
        setTitle("Almacenamiento");
        //setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        add(panelPrincipal);
        inicializarImagenes();
        inicializarComponentes();
        registrarEventos();
    }

    private void registrarEventos() {
        cbxIdiomas.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                Locale nuevo = getIdiomaSeleccionado();
                mi.setLenguaje(nuevo.getLanguage(), nuevo.getCountry());
                actualizarTextos();
            }
        });
    }

    public void inicializarComponentes() {
        cbxIdiomas.addItem(mi.get("menu.idioma.es"));
        cbxIdiomas.addItem(mi.get("menu.idioma.en"));
        cbxIdiomas.addItem(mi.get("menu.idioma.fr"));

        cbxTiposGuardado.addItem(mi.get("memoria"));
        cbxTiposGuardado.addItem(mi.get("archivo"));

        sincronizarComboIdiomaConLocale();
        actualizarTextos();
    }

    private void sincronizarComboIdiomaConLocale() {
        Locale locale = mi.getLocale();
        int selectedIndex = switch (locale.getLanguage()) {
            case "en" -> 1;
            case "fr" -> 2;
            default -> 0;
        };
        cbxIdiomas.setSelectedIndex(selectedIndex);
    }

    public void inicializarImagenes(){
        URL ArchivoGURL = ArchivoView.class.getClassLoader().getResource("imagenes/guardar.png");
        if (ArchivoGURL != null) {
            ImageIcon iconoBtnGuardar = new ImageIcon(ArchivoGURL);
            btnGuardar.setIcon(iconoBtnGuardar);
        } else {
            System.err.println("Error: No se ha cargado el icono de Login");
        }
        URL ArchivoSURL = ArchivoView.class.getClassLoader().getResource("imagenes/salir.png");
        if (ArchivoSURL != null) {
            ImageIcon iconoBtnSalir = new ImageIcon(ArchivoSURL);
            btnSalir.setIcon(iconoBtnSalir);
        } else {
            System.err.println("Error: No se ha cargado el icono de Login");
        }
    }

    public void actualizarTextos() {
        setTitle(mi.get("app.titulo"));
        lblTitulo.setText(mi.get("archivo.titulo"));
        lblArchivo.setText(mi.get("direccion.archivo"));
        lblIdioma.setText(mi.get("idioma.archivo"));
        btnGuardar.setText(mi.get("guardar.acrhivo"));
        btnSalir.setText(mi.get("salir.acrhivo"));

        UIManager.put("OptionPane.yesButtonText", mi.get("dialogo.boton.si"));
        UIManager.put("OptionPane.noButtonText", mi.get("dialogo.boton.no"));
        UIManager.put("OptionPane.cancelButtonText", mi.get("dialogo.boton.cancelar"));
        UIManager.put("OptionPane.okButtonText", mi.get("dialogo.boton.aceptar"));
    }

    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }

    public String getTipoAlmacenamientoSeleccionado() {
        return cbxTiposGuardado.getSelectedItem().toString();
    }

    public Locale getIdiomaSeleccionado() {
        int index = cbxIdiomas.getSelectedIndex();
        return switch (index) {
            case 1 -> new Locale("en", "US");
            case 2 -> new Locale("fr", "FR");
            default -> new Locale("es", "EC");
        };
    }

    public void setOnGuardar(ActionListener listener) {
        btnGuardar.addActionListener(listener);
    }

    public void setOnSalir(ActionListener listener) {
        btnSalir.addActionListener(listener);
    }

    //Getters y Setters

    public JLabel getLblTitulo() {
        return lblTitulo;
    }

    public void setLblTitulo(JLabel lblTitulo) {
        this.lblTitulo = lblTitulo;
    }

    public JComboBox getCbxTiposGuardado() {
        return cbxTiposGuardado;
    }

    public void setCbxTiposGuardado(JComboBox cbxTiposGuardado) {
        this.cbxTiposGuardado = cbxTiposGuardado;
    }

    public JLabel getLblArchivo() {
        return lblArchivo;
    }

    public void setLblArchivo(JLabel lblArchivo) {
        this.lblArchivo = lblArchivo;
    }

    public JButton getBtnGuardar() {
        return btnGuardar;
    }

    public void setBtnGuardar(JButton btnGuardar) {
        this.btnGuardar = btnGuardar;
    }

    public JButton getBtnSalir() {
        return btnSalir;
    }

    public void setBtnSalir(JButton btnSalir) {
        this.btnSalir = btnSalir;
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

    public JPanel getPanelPrincipal() {
        return panelPrincipal;
    }

    public void setPanelPrincipal(JPanel panelPrincipal) {
        this.panelPrincipal = panelPrincipal;
    }
}
