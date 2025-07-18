package ec.edu.ups.poo.clases.vista.usuario;

import ec.edu.ups.poo.clases.modelo.Usuario;
import ec.edu.ups.poo.clases.util.FormateadorUtils;
import ec.edu.ups.poo.clases.util.MensajeInternacionalizacionHandler;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.net.URL;
import java.util.List;
import java.util.Locale;
/**
 * Clase que representa la ventana interna para listar usuarios.
 * Permite mostrar una tabla con la lista de usuarios, realizar búsquedas,
 * y cambiar el idioma de la interfaz.
 */
public class UsuarioListarView extends JInternalFrame {
    private JTextField txtBuscar;
    private JButton btnBuscar;
    private JButton btnListar;
    private JTable tblUsuarios;
    private JPanel panelPrincipal;
    private JLabel lblNombre;
    private DefaultTableModel modelo;
    private MensajeInternacionalizacionHandler mi;
    /**
     * Constructor que inicializa la ventana para listar usuarios.
     * Configura el panel principal, el modelo de la tabla, y carga el idioma e íconos.
     *
     * @param mi Objeto para manejar la internacionalización de los textos.
     */
    public UsuarioListarView(MensajeInternacionalizacionHandler mi) {
        super("Listar Usuarios", true,true,false,true);
        setContentPane(panelPrincipal);
        setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);
        setSize(700, 500);

        modelo = new DefaultTableModel();
        tblUsuarios.setModel(modelo);
        this.mi = mi;
        cambiarIdioma();
        inicializarImagenes();
    }
    /**
     * Cambia los textos visibles de la interfaz según el idioma configurado.
     * Actualiza títulos, etiquetas, botones y encabezados de tabla.
     */
    public void cambiarIdioma() {
        setTitle(mi.get("usuario.listar.titulo"));
        lblNombre.setText(mi.get("usuario.listar.buscar"));
        btnBuscar.setText(mi.get("usuario.listar.boton.buscar"));
        btnListar.setText(mi.get("usuario.listar.boton.listar"));

        Object[] columnas = {
                mi.get("usuario.listar.columna.username"),
                mi.get("usuario.listar.columna.contrasenia"),
                mi.get("usuario.listar.columna.nombre"),
                mi.get("usuario.listar.columna.celular"),
                mi.get("usuario.listar.columna.correo"),
                mi.get("usuario.listar.columna.fecha")
        };
        modelo.setColumnIdentifiers(columnas);

        UIManager.put("OptionPane.yesButtonText", mi.get("dialogo.boton.si"));
        UIManager.put("OptionPane.noButtonText", mi.get("dialogo.boton.no"));
        UIManager.put("OptionPane.cancelButtonText", mi.get("dialogo.boton.cancelar"));
        UIManager.put("OptionPane.okButtonText", mi.get("dialogo.boton.aceptar"));
    }
    /**
     * Carga los datos de la lista de usuarios en la tabla.
     * Formatea la fecha según el idioma y localización actual.
     *
     * @param listaUsuarios Lista con objetos Usuario a mostrar en la tabla.
     */
    public void cargarDatos(List<Usuario> listaUsuarios) {
        modelo.setNumRows(0);
        Locale locale = mi.getLocale();

        for (Usuario usuario : listaUsuarios) {
            String fechaFormateada = FormateadorUtils.formatearFecha(usuario.getFecha().getTime(), locale);

            Object[] fila = {
                    usuario.getUsername(),
                    usuario.getContrasenia(),
                    usuario.getNombre(),
                    usuario.getCelular(),
                    usuario.getEmail(),
                    fechaFormateada
            };
            modelo.addRow(fila);
        }
    }
    /**
     * Carga y asigna los íconos para los botones Buscar y Listar.
     * Muestra un error en consola si no se encuentran los archivos de imagen.
     */
    public void inicializarImagenes(){
        URL buscar = UsuarioListarView.class.getClassLoader().getResource("imagenes/buscar.png");
        if (buscar != null) {
            ImageIcon iconoBtnIniciarSesion = new ImageIcon(buscar);
            btnBuscar.setIcon(iconoBtnIniciarSesion);
        } else {
            System.err.println("Error: No se ha cargado el icono de Login");
        }
        URL listar = UsuarioListarView.class.getClassLoader().getResource("imagenes/listar.png");
        if (listar != null) {
            ImageIcon iconoBtnIniciarSesion = new ImageIcon(listar);
            btnListar.setIcon(iconoBtnIniciarSesion);
        } else {
            System.err.println("Error: No se ha cargado el icono de Login");
        }
    }
    /**
     * Obtiene la etiqueta que muestra el texto "Nombre" o similar.
     * @return JLabel correspondiente al nombre.
     */
    public JLabel getLblNombre() {
        return lblNombre;
    }

    /**
     * Establece la etiqueta que muestra el texto "Nombre" o similar.
     * @param lblNombre JLabel a asignar.
     */
    public void setLblNombre(JLabel lblNombre) {
        this.lblNombre = lblNombre;
    }

    /**
     * Obtiene el campo de texto donde se ingresa el texto para buscar usuarios.
     * @return JTextField para la búsqueda.
     */
    public JTextField getTxtBuscar() {
        return txtBuscar;
    }

    /**
     * Establece el campo de texto para la búsqueda de usuarios.
     * @param txtBuscar JTextField a asignar.
     */
    public void setTxtBuscar(JTextField txtBuscar) {
        this.txtBuscar = txtBuscar;
    }

    /**
     * Obtiene el botón para iniciar la acción de búsqueda.
     * @return JButton botón buscar.
     */
    public JButton getBtnBuscar() {
        return btnBuscar;
    }

    /**
     * Establece el botón para iniciar la acción de búsqueda.
     * @param btnBuscar JButton a asignar.
     */
    public void setBtnBuscar(JButton btnBuscar) {
        this.btnBuscar = btnBuscar;
    }

    /**
     * Obtiene el botón para listar todos los usuarios.
     * @return JButton botón listar.
     */
    public JButton getBtnListar() {
        return btnListar;
    }

    /**
     * Establece el botón para listar todos los usuarios.
     * @param btnListar JButton a asignar.
     */
    public void setBtnListar(JButton btnListar) {
        this.btnListar = btnListar;
    }

    /**
     * Obtiene la tabla que muestra los usuarios.
     * @return JTable con los datos de usuarios.
     */
    public JTable getTblUsuarios() {
        return tblUsuarios;
    }

    /**
     * Establece la tabla que muestra los usuarios.
     * @param tblUsuarios JTable a asignar.
     */
    public void setTblUsuarios(JTable tblUsuarios) {
        this.tblUsuarios = tblUsuarios;
    }

    /**
     * Obtiene el modelo de tabla que maneja los datos de la tabla de usuarios.
     * @return DefaultTableModel del JTable.
     */
    public DefaultTableModel getModelo() {
        return modelo;
    }

    /**
     * Establece el modelo de tabla que maneja los datos de la tabla de usuarios.
     * @param modelo DefaultTableModel a asignar.
     */
    public void setModelo(DefaultTableModel modelo) {
        this.modelo = modelo;
    }
}
