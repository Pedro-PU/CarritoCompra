package ec.edu.ups.poo.clases.vista;

import ec.edu.ups.poo.clases.util.MensajeInternacionalizacionHandler;
import ec.edu.ups.poo.clases.vista.usuario.LoginView;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.net.URL;
import java.util.Locale;
/**
 * Ventana gráfica encargada de mostrar la interfaz para seleccionar el tipo de almacenamiento
 * y el idioma de la aplicación. Permite elegir entre almacenamiento en memoria o en archivo.
 * También gestiona la carga de íconos y textos traducidos mediante un handler de internacionalización.
 */
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
    /**
     * Constructor de la clase ArchivoView. Inicializa la ventana, sus componentes,
     * íconos e idioma actual a través del handler de internacionalización.
     *
     * @param mi Handler de internacionalización para la carga de textos e idioma.
     */
    public ArchivoView( MensajeInternacionalizacionHandler mi) {
        this.mi = mi;
        setTitle("Almacenamiento");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        add(panelPrincipal);
        inicializarImagenes();
        inicializarComponentes();
        registrarEventos();
    }
    /**
     * Registra eventos de cambio de idioma en el combo de selección de idiomas.
     * Al detectar un nuevo idioma, actualiza los textos de la interfaz.
     */
    private void registrarEventos() {
        cbxIdiomas.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                Locale nuevo = getIdiomaSeleccionado();
                mi.setLenguaje(nuevo.getLanguage(), nuevo.getCountry());
                actualizarTextos();
            }
        });
    }
    /**
     * Inicializa los componentes gráficos como los combos de idioma y tipo de almacenamiento,
     * además sincroniza el idioma actual con el combo de selección.
     */
    public void inicializarComponentes() {
        cbxIdiomas.addItem(mi.get("menu.idioma.es"));
        cbxIdiomas.addItem(mi.get("menu.idioma.en"));
        cbxIdiomas.addItem(mi.get("menu.idioma.fr"));

        cbxTiposGuardado.addItem(mi.get("memoria"));
        cbxTiposGuardado.addItem(mi.get("archivo"));

        sincronizarComboIdiomaConLocale();
        actualizarTextos();
    }
    /**
     * Sincroniza el combo de idiomas con el idioma actual configurado en la aplicación.
     * Selecciona automáticamente el idioma correspondiente en el JComboBox.
     */
    private void sincronizarComboIdiomaConLocale() {
        Locale locale = mi.getLocale();
        int selectedIndex = switch (locale.getLanguage()) {
            case "en" -> 1;
            case "fr" -> 2;
            default -> 0;
        };
        cbxIdiomas.setSelectedIndex(selectedIndex);
    }
    /**
     * Carga las imágenes de los botones "Guardar" y "Salir" desde la carpeta de recursos.
     * Si no se encuentran los íconos, se imprime un error por consola.
     */
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
    /**
     * Actualiza los textos de la interfaz gráfica usando los valores del archivo de internacionalización.
     */
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

        UIManager.put("FileChooser.openDialogTitleText", mi.get("selector.directorio.titulo"));
        UIManager.put("FileChooser.openButtonText", mi.get("selector.directorio.abrir"));
        UIManager.put("FileChooser.cancelButtonText", mi.get("selector.directorio.cancelar"));
        UIManager.put("FileChooser.lookInLabelText", mi.get("selector.directorio.buscarEn"));

        int selectedIndex = cbxTiposGuardado.getSelectedIndex();
        cbxTiposGuardado.removeAllItems();
        cbxTiposGuardado.addItem(mi.get("memoria"));
        cbxTiposGuardado.addItem(mi.get("archivo"));
        cbxTiposGuardado.setSelectedIndex(selectedIndex);
    }
    /**
     * Muestra un mensaje emergente con el texto especificado.
     *
     * @param mensaje Texto a mostrar en el cuadro de diálogo.
     */
    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }
    /**
     * Obtiene el tipo de almacenamiento seleccionado en el combo.
     *
     * @return Cadena que representa el tipo de almacenamiento ("memoria" o "archivo").
     */
    public String getTipoAlmacenamientoSeleccionado() {
        int index = cbxTiposGuardado.getSelectedIndex();
        return index == 0 ? "memoria" : "archivo";
    }
    /**
     * Obtiene el idioma seleccionado en el combo de idiomas.
     *
     * @return Objeto Locale con el idioma y país correspondientes.
     */
    public Locale getIdiomaSeleccionado() {
        int index = cbxIdiomas.getSelectedIndex();
        return switch (index) {
            case 1 -> new Locale("en", "US");
            case 2 -> new Locale("fr", "FR");
            default -> new Locale("es", "EC");
        };
    }
    /**
     * Asigna un listener al botón "Guardar".
     *
     * @param listener Acción a ejecutar al hacer clic en el botón.
     */
    public void setOnGuardar(ActionListener listener) {
        btnGuardar.addActionListener(listener);
    }
    /**
     * Asigna un listener al botón "Salir".
     *
     * @param listener Acción a ejecutar al hacer clic en el botón.
     */
    public void setOnSalir(ActionListener listener) {
        btnSalir.addActionListener(listener);
    }

    /**
     * Obtiene el JLabel del título.
     * @return JLabel del título.
     */
    public JLabel getLblTitulo() {
        return lblTitulo;
    }

    /**
     * Establece el JLabel del título.
     * @param lblTitulo Nuevo JLabel del título.
     */
    public void setLblTitulo(JLabel lblTitulo) {
        this.lblTitulo = lblTitulo;
    }

    /**
     * Obtiene el JComboBox de tipos de guardado.
     * @return JComboBox de tipo de almacenamiento.
     */
    public JComboBox getCbxTiposGuardado() {
        return cbxTiposGuardado;
    }

    /**
     * Establece el JComboBox de tipos de guardado.
     * @param cbxTiposGuardado Nuevo JComboBox de almacenamiento.
     */
    public void setCbxTiposGuardado(JComboBox cbxTiposGuardado) {
        this.cbxTiposGuardado = cbxTiposGuardado;
    }

    /**
     * Obtiene el JLabel de la ruta de archivo.
     * @return JLabel de archivo.
     */
    public JLabel getLblArchivo() {
        return lblArchivo;
    }

    /**
     * Establece el JLabel de la ruta de archivo.
     * @param lblArchivo Nuevo JLabel de archivo.
     */
    public void setLblArchivo(JLabel lblArchivo) {
        this.lblArchivo = lblArchivo;
    }

    /**
     * Obtiene el botón "Guardar".
     * @return JButton para guardar.
     */
    public JButton getBtnGuardar() {
        return btnGuardar;
    }

    /**
     * Establece el botón "Guardar".
     * @param btnGuardar Nuevo botón de guardar.
     */
    public void setBtnGuardar(JButton btnGuardar) {
        this.btnGuardar = btnGuardar;
    }

    /**
     * Obtiene el botón "Salir".
     * @return JButton para salir.
     */
    public JButton getBtnSalir() {
        return btnSalir;
    }

    /**
     * Establece el botón "Salir".
     * @param btnSalir Nuevo botón de salir.
     */
    public void setBtnSalir(JButton btnSalir) {
        this.btnSalir = btnSalir;
    }

    /**
     * Obtiene el JComboBox de selección de idiomas.
     * @return JComboBox de idiomas.
     */
    public JComboBox getCbxIdiomas() {
        return cbxIdiomas;
    }

    /**
     * Establece el JComboBox de selección de idiomas.
     * @param cbxIdiomas Nuevo JComboBox de idiomas.
     */
    public void setCbxIdiomas(JComboBox cbxIdiomas) {
        this.cbxIdiomas = cbxIdiomas;
    }

    /**
     * Obtiene el JLabel del idioma.
     * @return JLabel del idioma.
     */
    public JLabel getLblIdioma() {
        return lblIdioma;
    }

    /**
     * Establece el JLabel del idioma.
     * @param lblIdioma Nuevo JLabel de idioma.
     */
    public void setLblIdioma(JLabel lblIdioma) {
        this.lblIdioma = lblIdioma;
    }

    /**
     * Obtiene el panel principal de la ventana.
     * @return JPanel principal.
     */
    public JPanel getPanelPrincipal() {
        return panelPrincipal;
    }

    /**
     * Establece el panel principal de la ventana.
     * @param panelPrincipal Nuevo JPanel principal.
     */
    public void setPanelPrincipal(JPanel panelPrincipal) {
        this.panelPrincipal = panelPrincipal;
    }

}
