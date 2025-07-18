package ec.edu.ups.poo.clases.vista.producto;

import ec.edu.ups.poo.clases.modelo.Producto;
import ec.edu.ups.poo.clases.util.FormateadorUtils;
import ec.edu.ups.poo.clases.util.MensajeInternacionalizacionHandler;
import ec.edu.ups.poo.clases.vista.usuario.UsuarioListarView;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.net.URL;
import java.util.List;
/**
 * Clase que representa la ventana para listar productos del sistema.
 * Permite buscar productos por nombre, visualizar el listado completo y mostrar sus detalles en tabla.
 */
public class ProductoListaView extends JInternalFrame {
    private JPanel panelPrincipal;
    private JTextField txtBuscar;
    private JButton btnBuscar;
    private JButton btnListar;
    private JTable tblProductos;
    private JLabel lblNombre;
    private DefaultTableModel modelo;
    private MensajeInternacionalizacionHandler mi;
    /**
     * Constructor de la ventana para listar productos.
     * Inicializa componentes, configura el modelo de la tabla, carga íconos e idioma.
     * @param mi Handler de internacionalización.
     */
    public ProductoListaView(MensajeInternacionalizacionHandler mi) {

        setContentPane(panelPrincipal);
        setTitle("Listado de Productos");
        setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);
        setSize(500, 500);
        //setResizable(false);
        //setLocation(100,500);
        //setVisible(true);
        //pack();
        setClosable(true);
        setMaximizable(true);
        setResizable(true);
        setIconifiable(true);

        modelo = new DefaultTableModel();
        tblProductos.setModel(modelo);

        this.mi = mi;
        cambiarIdioma();
        inicializarImagenes();
    }
    /**
     * Cambia los textos visibles al idioma actual usando el handler de internacionalización.
     * También actualiza los identificadores de columna de la tabla.
     */
    public void cambiarIdioma() {
        mi.setLenguaje(mi.getLocale().getLanguage(), mi.getLocale().getCountry());

        setTitle(mi.get("producto.lista.titulo.ventana"));
        lblNombre.setText(mi.get("producto.lista.nombre"));
        btnBuscar.setText(mi.get("producto.lista.boton.buscar"));
        btnListar.setText(mi.get("producto.lista.boton.listar"));

        modelo.setColumnIdentifiers(new String[] {
                mi.get("producto.lista.tabla.codigo"),
                mi.get("producto.lista.tabla.nombre"),
                mi.get("producto.lista.tabla.precio")
        });

        UIManager.put("OptionPane.yesButtonText", mi.get("dialogo.boton.si"));
        UIManager.put("OptionPane.noButtonText", mi.get("dialogo.boton.no"));
        UIManager.put("OptionPane.cancelButtonText", mi.get("dialogo.boton.cancelar"));
        UIManager.put("OptionPane.okButtonText", mi.get("dialogo.boton.aceptar"));
    }
    /**
     * Llena la tabla con los datos de una lista de productos.
     * @param listaProductos Lista de productos a mostrar.
     */
    public void cargarDatos(List<Producto> listaProductos) {
        modelo.setNumRows(0);
        for (Producto producto : listaProductos) {
            Object[] fila = {
                    producto.getCodigo(),
                    producto.getNombre(),
                    FormateadorUtils.formatearMoneda(producto.getPrecio(), mi.getLocale())
            };
            modelo.addRow(fila);
        }
    }
    /**
     * Carga los íconos en los botones de buscar y listar desde los recursos del proyecto.
     */
    public void inicializarImagenes(){
        URL buscar = ProductoListaView.class.getClassLoader().getResource("imagenes/buscar.png");
        if (buscar != null) {
            ImageIcon iconoBtnIniciarSesion = new ImageIcon(buscar);
            btnBuscar.setIcon(iconoBtnIniciarSesion);
        } else {
            System.err.println("Error: No se ha cargado el icono de Login");
        }
        URL listar = ProductoListaView.class.getClassLoader().getResource("imagenes/listar.png");
        if (listar != null) {
            ImageIcon iconoBtnIniciarSesion = new ImageIcon(listar);
            btnListar.setIcon(iconoBtnIniciarSesion);
        } else {
            System.err.println("Error: No se ha cargado el icono de Login");
        }
    }
    /**
     * Obtiene el label del nombre para el campo de búsqueda.
     * @return JLabel del nombre.
     */
    public JLabel getLblNombre() {
        return lblNombre;
    }

    /**
     * Establece el label del nombre para el campo de búsqueda.
     * @param lblNombre JLabel del nombre.
     */
    public void setLblNombre(JLabel lblNombre) {
        this.lblNombre = lblNombre;
    }

    /**
     * Obtiene el campo de texto usado para buscar productos.
     * @return JTextField de búsqueda.
     */
    public JTextField getTxtBuscar() {
        return txtBuscar;
    }

    /**
     * Establece el campo de texto usado para buscar productos.
     * @param txtBuscar JTextField de búsqueda.
     */
    public void setTxtBuscar(JTextField txtBuscar) {
        this.txtBuscar = txtBuscar;
    }

    /**
     * Obtiene el botón de búsqueda.
     * @return JButton de buscar.
     */
    public JButton getBtnBuscar() {
        return btnBuscar;
    }

    /**
     * Establece el botón de búsqueda.
     * @param btnBuscar JButton de buscar.
     */
    public void setBtnBuscar(JButton btnBuscar) {
        this.btnBuscar = btnBuscar;
    }

    /**
     * Obtiene la tabla donde se muestran los productos.
     * @return JTable de productos.
     */
    public JTable getTblProductos() {
        return tblProductos;
    }

    /**
     * Establece la tabla donde se muestran los productos.
     * @param tblProductos JTable de productos.
     */
    public void setTblProductos(JTable tblProductos) {
        this.tblProductos = tblProductos;
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
     * @param panelPrincipal JPanel principal.
     */
    public void setPanelPrincipal(JPanel panelPrincipal) {
        this.panelPrincipal = panelPrincipal;
    }

    /**
     * Obtiene el botón para listar todos los productos.
     * @return JButton de listar.
     */
    public JButton getBtnListar() {
        return btnListar;
    }

    /**
     * Establece el botón para listar todos los productos.
     * @param btnListar JButton de listar.
     */
    public void setBtnListar(JButton btnListar) {
        this.btnListar = btnListar;
    }

    /**
     * Obtiene el modelo de la tabla de productos.
     * @return DefaultTableModel del modelo.
     */
    public DefaultTableModel getModelo() {
        return modelo;
    }

    /**
     * Establece el modelo de la tabla de productos.
     * @param modelo DefaultTableModel del modelo.
     */
    public void setModelo(DefaultTableModel modelo) {
        this.modelo = modelo;
    }

}
