package ec.edu.ups.poo.clases.vista.carrito;

import ec.edu.ups.poo.clases.modelo.Carrito;
import ec.edu.ups.poo.clases.util.FormateadorUtils;
import ec.edu.ups.poo.clases.util.MensajeInternacionalizacionHandler;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.net.URL;
import java.util.List;
import java.text.SimpleDateFormat;
import java.util.Locale;
/**
 * Clase que representa la vista de listado de carritos de compra.
 * Permite buscar, listar y visualizar detalles de carritos, mostrando información clave como fecha, usuario y montos.
 */
public class CarritoListaView extends JInternalFrame{
    private JPanel panelPrincipal;
    private JTextField txtBuscar;
    private JButton btnBuscar;
    private JButton btnListar;
    private JTable tblProductos;
    private JButton btnDetalle;
    private JLabel lblBuscarCodigo;
    private DefaultTableModel modelo;
    private MensajeInternacionalizacionHandler mi;
    /**
     * Constructor de la vista de listado de carritos.
     * Configura componentes gráficos, aplica idioma y asigna íconos.
     * @param mi Handler de internacionalización.
     */
    public CarritoListaView(MensajeInternacionalizacionHandler mi) {
        setContentPane(panelPrincipal);
        setTitle("Listado de Carritos");
        setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);
        setSize(700, 500);
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
     * Carga una lista de carritos en la tabla, formateando fecha y valores monetarios de acuerdo al idioma local.
     * @param listaCarritos Lista de carritos a mostrar.
     */
    public void cargarDatos(List<Carrito> listaCarritos) {
        modelo.setNumRows(0);
        Locale locale = mi.getLocale();

        for (Carrito carrito : listaCarritos) {
            String fechaFormateada = FormateadorUtils.formatearFecha(carrito.getFechaCreacion().getTime(), locale);
            Object[] fila = {
                    carrito.getCodigo(),
                    carrito.getUsuario().getUsername(),
                    fechaFormateada,
                    FormateadorUtils.formatearMoneda(carrito.calcularSubtotal(), locale),
                    FormateadorUtils.formatearMoneda(carrito.calcularIVA(), locale),
                    FormateadorUtils.formatearMoneda(carrito.calcularTotal(), locale)
            };
            modelo.addRow(fila);
        }
    }

    /**
     * Muestra un mensaje simple en pantalla dentro de un cuadro de diálogo.
     * @param mensaje Texto del mensaje a mostrar.
     */
    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }

    /**
     * Cambia los textos visibles de la interfaz al idioma actual.
     * Configura etiquetas, botones y columnas de la tabla.
     */
    public void cambiarIdioma() {
        mi.setLenguaje(mi.getLocale().getLanguage(), mi.getLocale().getCountry());

        setTitle(mi.get("carrito.lista.titulo.ventana"));
        lblBuscarCodigo.setText(mi.get("carrito.lista.buscar.codigo"));
        btnBuscar.setText(mi.get("carrito.lista.boton.buscar"));
        btnListar.setText(mi.get("carrito.lista.boton.listar"));
        btnDetalle.setText(mi.get("carrito.lista.boton.detalle"));

        Object[] columnas = {
                mi.get("carrito.lista.columna.codigo"),
                mi.get("carrito.lista.columna.usuario"),
                mi.get("carrito.lista.columna.fecha"),
                mi.get("carrito.lista.columna.subtotal"),
                mi.get("carrito.lista.columna.iva"),
                mi.get("carrito.lista.columna.total")
        };
        modelo.setColumnIdentifiers(columnas);

        UIManager.put("OptionPane.yesButtonText", mi.get("dialogo.boton.si"));
        UIManager.put("OptionPane.noButtonText", mi.get("dialogo.boton.no"));
        UIManager.put("OptionPane.cancelButtonText", mi.get("dialogo.boton.cancelar"));
        UIManager.put("OptionPane.okButtonText", mi.get("dialogo.boton.aceptar"));
    }

    /**
     * Carga íconos gráficos en los botones si los recursos están disponibles.
     * Asocia imágenes a acciones de listar, buscar y ver detalle.
     */
    public void inicializarImagenes() {
        URL listar = CarritoListaView.class.getClassLoader().getResource("imagenes/listar.png");
        if (listar != null) {
            btnListar.setIcon(new ImageIcon(listar));
        } else {
            System.err.println("Error: No se ha cargado el icono de Listar");
        }

        URL buscar = CarritoListaView.class.getClassLoader().getResource("imagenes/buscar.png");
        if (buscar != null) {
            btnBuscar.setIcon(new ImageIcon(buscar));
        } else {
            System.err.println("Error: No se ha cargado el icono de Buscar");
        }

        URL detalle = CarritoListaView.class.getClassLoader().getResource("imagenes/detalles.png");
        if (detalle != null) {
            btnDetalle.setIcon(new ImageIcon(detalle));
        } else {
            System.err.println("Error: No se ha cargado el icono de Detalle");
        }
    }

    /**
     * Obtiene el label para el campo de búsqueda por código de carrito.
     * @return JLabel del código a buscar.
     */
    public JLabel getLblBuscarCodigo() {
        return lblBuscarCodigo;
    }

    /**
     * Establece el label para el campo de búsqueda por código de carrito.
     * @param lblBuscarCodigo JLabel del código.
     */
    public void setLblBuscarCodigo(JLabel lblBuscarCodigo) {
        this.lblBuscarCodigo = lblBuscarCodigo;
    }

    /**
     * Obtiene el campo de texto donde se ingresa el código a buscar.
     * @return JTextField del código.
     */
    public JTextField getTxtBuscar() {
        return txtBuscar;
    }

    /**
     * Establece el campo de texto donde se ingresa el código a buscar.
     * @param txtBuscar JTextField del código.
     */
    public void setTxtBuscar(JTextField txtBuscar) {
        this.txtBuscar = txtBuscar;
    }

    /**
     * Obtiene el botón para ejecutar la búsqueda.
     * @return JButton de búsqueda.
     */
    public JButton getBtnBuscar() {
        return btnBuscar;
    }

    /**
     * Establece el botón para ejecutar la búsqueda.
     * @param btnBuscar JButton de búsqueda.
     */
    public void setBtnBuscar(JButton btnBuscar) {
        this.btnBuscar = btnBuscar;
    }

    /**
     * Obtiene el botón que permite listar todos los carritos.
     * @return JButton de listar.
     */
    public JButton getBtnListar() {
        return btnListar;
    }

    /**
     * Establece el botón que permite listar todos los carritos.
     * @param btnListar JButton de listar.
     */
    public void setBtnListar(JButton btnListar) {
        this.btnListar = btnListar;
    }

    /**
     * Obtiene la tabla que muestra los carritos en la vista.
     * @return JTable de carritos.
     */
    public JTable getTblProductos() {
        return tblProductos;
    }

    /**
     * Establece la tabla que muestra los carritos en la vista.
     * @param tblProductos JTable de carritos.
     */
    public void setTblProductos(JTable tblProductos) {
        this.tblProductos = tblProductos;
    }

    /**
     * Obtiene el botón para visualizar el detalle de un carrito.
     * @return JButton de detalle.
     */
    public JButton getBtnDetalle() {
        return btnDetalle;
    }

    /**
     * Establece el botón para visualizar el detalle de un carrito.
     * @param btnDetalle JButton de detalle.
     */
    public void setBtnDetalle(JButton btnDetalle) {
        this.btnDetalle = btnDetalle;
    }

    /**
     * Obtiene el modelo de la tabla que estructura los datos de los carritos.
     * @return DefaultTableModel del modelo.
     */
    public DefaultTableModel getModelo() {
        return modelo;
    }

    /**
     * Establece el modelo de la tabla que estructura los datos de los carritos.
     * @param modelo DefaultTableModel del modelo.
     */
    public void setModelo(DefaultTableModel modelo) {
        this.modelo = modelo;
    }

}
