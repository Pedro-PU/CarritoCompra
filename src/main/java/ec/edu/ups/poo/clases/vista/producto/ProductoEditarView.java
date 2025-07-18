package ec.edu.ups.poo.clases.vista.producto;

import ec.edu.ups.poo.clases.util.MensajeInternacionalizacionHandler;
import ec.edu.ups.poo.clases.vista.usuario.UsuarioModificarView;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.net.URL;
/**
 * Clase que representa la ventana de edición de productos.
 * Permite buscar, mostrar, modificar y eliminar información de productos dentro del sistema.
 */
public class ProductoEditarView extends JInternalFrame {
    private JPanel panelPrincipal;
    private JTextField txtBuscar;
    private JButton btnBuscar;
    private JTable tblProductos;
    private JTextField txtNombre;
    private JTextField txtPrecio;
    private JButton btnActualizar;
    private JLabel lblCodigo;
    private JLabel lblNombre;
    private JLabel lblPrecio;
    private JButton btnEliminar;
    private DefaultTableModel modelo;
    private MensajeInternacionalizacionHandler mi;
    /**
     * Constructor de la ventana de edición de productos.
     * Inicializa los componentes, configura íconos y carga el idioma usando el handler de internacionalización.
     * @param mi Handler de internacionalización.
     */
    public ProductoEditarView(MensajeInternacionalizacionHandler mi) {
        setContentPane(panelPrincipal);
        setTitle("Edición de Productos");
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
        this.mi = mi;
        cambiarIdioma();
        inicializarImagenes();
    }

    /**
     * Muestra un mensaje en un cuadro de diálogo.
     * @param mensaje Texto del mensaje a mostrar.
     */
    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }
    /**
     * Muestra una pregunta en un cuadro de diálogo con opciones Sí/No.
     * @param mensaje Pregunta a mostrar.
     * @return true si el usuario responde "Sí"; false si responde "No".
     */
    public boolean mostrarMensajePregunta(String mensaje) {
        int respuesta = JOptionPane.showConfirmDialog(this, mensaje, mi.get("dialogo.title.pregunta"),
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        return respuesta == JOptionPane.YES_OPTION;
    }
    /**
     * Limpia los campos del formulario de edición de productos.
     */
    public void limpiarCampos() {
        txtNombre.setText("");
        txtPrecio.setText("");
    }
    /**
     * Cambia el idioma de los componentes visibles utilizando el handler de internacionalización.
     */
    public void cambiarIdioma() {
        setTitle(mi.get("producto.editar.titulo.ventana"));

        if (lblCodigo != null) lblCodigo.setText(mi.get("producto.editar.codigo"));
        if (lblNombre != null) lblNombre.setText(mi.get("producto.editar.nombre"));
        if (lblPrecio != null) lblPrecio.setText(mi.get("producto.editar.precio"));

        if (btnBuscar != null) btnBuscar.setText(mi.get("producto.editar.buscar"));
        if (btnActualizar != null) btnActualizar.setText(mi.get("producto.editar.actualizar"));
        if (btnEliminar != null) btnEliminar.setText(mi.get("producto.editar.eliminar"));

        UIManager.put("OptionPane.yesButtonText", mi.get("dialogo.boton.si"));
        UIManager.put("OptionPane.noButtonText", mi.get("dialogo.boton.no"));
        UIManager.put("OptionPane.cancelButtonText", mi.get("dialogo.boton.cancelar"));
        UIManager.put("OptionPane.okButtonText", mi.get("dialogo.boton.aceptar"));
    }
    /**
     * Carga los íconos en los botones si los archivos de imagen están disponibles.
     */
    public void inicializarImagenes(){
        URL buscar = ProductoEditarView.class.getClassLoader().getResource("imagenes/buscar.png");
        if (buscar != null) {
            ImageIcon iconoBtnIniciarSesion = new ImageIcon(buscar);
            btnBuscar.setIcon(iconoBtnIniciarSesion);
        } else {
            System.err.println("Error: No se ha cargado el icono de Login");
        }

        URL editar = ProductoEditarView.class.getClassLoader().getResource("imagenes/editar.png");
        if (editar != null) {
            ImageIcon iconoBtnIniciarSesion = new ImageIcon(editar);
            btnActualizar.setIcon(iconoBtnIniciarSesion);
        } else {
            System.err.println("Error: No se ha cargado el icono de Login");
        }
    }
    /**
     * Obtiene el label del código del producto.
     * @return JLabel del código.
     */
    public JLabel getLblCodigo() {
        return lblCodigo;
    }

    /**
     * Establece el label del código del producto.
     * @param lblCodigo JLabel del código.
     */
    public void setLblCodigo(JLabel lblCodigo) {
        this.lblCodigo = lblCodigo;
    }

    /**
     * Obtiene el label del nombre del producto.
     * @return JLabel del nombre.
     */
    public JLabel getLblNombre() {
        return lblNombre;
    }

    /**
     * Establece el label del nombre del producto.
     * @param lblNombre JLabel del nombre.
     */
    public void setLblNombre(JLabel lblNombre) {
        this.lblNombre = lblNombre;
    }

    /**
     * Obtiene el label del precio del producto.
     * @return JLabel del precio.
     */
    public JLabel getLblPrecio() {
        return lblPrecio;
    }

    /**
     * Establece el label del precio del producto.
     * @param lblPrecio JLabel del precio.
     */
    public void setLblPrecio(JLabel lblPrecio) {
        this.lblPrecio = lblPrecio;
    }

    /**
     * Obtiene el campo de texto para buscar productos.
     * @return JTextField de búsqueda.
     */
    public JTextField getTxtBuscar() {
        return txtBuscar;
    }

    /**
     * Establece el campo de texto para buscar productos.
     * @param txtBuscar JTextField de búsqueda.
     */
    public void setTxtBuscar(JTextField txtBuscar) {
        this.txtBuscar = txtBuscar;
    }

    /**
     * Obtiene el botón para buscar productos.
     * @return JButton de buscar.
     */
    public JButton getBtnBuscar() {
        return btnBuscar;
    }

    /**
     * Establece el botón para buscar productos.
     * @param btnBuscar JButton de buscar.
     */
    public void setBtnBuscar(JButton btnBuscar) {
        this.btnBuscar = btnBuscar;
    }

    /**
     * Obtiene la tabla que muestra los productos.
     * @return JTable de productos.
     */
    public JTable getTblProductos() {
        return tblProductos;
    }

    /**
     * Establece la tabla que muestra los productos.
     * @param tblProductos JTable de productos.
     */
    public void setTblProductos(JTable tblProductos) {
        this.tblProductos = tblProductos;
    }

    /**
     * Obtiene el campo de texto para el nombre del producto.
     * @return JTextField del nombre.
     */
    public JTextField getTxtNombre() {
        return txtNombre;
    }

    /**
     * Establece el campo de texto para el nombre del producto.
     * @param txtNombre JTextField del nombre.
     */
    public void setTxtNombre(JTextField txtNombre) {
        this.txtNombre = txtNombre;
    }

    /**
     * Obtiene el campo de texto para el precio del producto.
     * @return JTextField del precio.
     */
    public JTextField getTxtPrecio() {
        return txtPrecio;
    }

    /**
     * Establece el campo de texto para el precio del producto.
     * @param txtPrecio JTextField del precio.
     */
    public void setTxtPrecio(JTextField txtPrecio) {
        this.txtPrecio = txtPrecio;
    }

    /**
     * Obtiene el botón para actualizar un producto.
     * @return JButton de actualizar.
     */
    public JButton getBtnActualizar() {
        return btnActualizar;
    }

    /**
     * Establece el botón para actualizar un producto.
     * @param btnActualizar JButton de actualizar.
     */
    public void setBtnActualizar(JButton btnActualizar) {
        this.btnActualizar = btnActualizar;
    }

    /**
     * Obtiene el botón para eliminar un producto.
     * @return JButton de eliminar.
     */
    public JButton getBtnEliminar() {
        return btnEliminar;
    }

    /**
     * Establece el botón para eliminar un producto.
     * @param btnEliminar JButton de eliminar.
     */
    public void setBtnEliminar(JButton btnEliminar) {
        this.btnEliminar = btnEliminar;
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
