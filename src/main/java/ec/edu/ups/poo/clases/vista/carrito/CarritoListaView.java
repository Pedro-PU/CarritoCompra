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
    public CarritoListaView(MensajeInternacionalizacionHandler mi) {
        setContentPane(panelPrincipal);
        setTitle("Listado de Carritos");
        setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);
        setSize(500, 500);
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

    public JLabel getLblBuscarCodigo() {
        return lblBuscarCodigo;
    }

    public void setLblBuscarCodigo(JLabel lblBuscarCodigo) {
        this.lblBuscarCodigo = lblBuscarCodigo;
    }

    public JTextField getTxtBuscar() {
        return txtBuscar;
    }

    public void setTxtBuscar(JTextField txtBuscar) {
        this.txtBuscar = txtBuscar;
    }

    public JButton getBtnBuscar() {
        return btnBuscar;
    }

    public void setBtnBuscar(JButton btnBuscar) {
        this.btnBuscar = btnBuscar;
    }

    public JButton getBtnListar() {
        return btnListar;
    }

    public void setBtnListar(JButton btnListar) {
        this.btnListar = btnListar;
    }

    public JTable getTblProductos() {
        return tblProductos;
    }

    public void setTblProductos(JTable tblProductos) {
        this.tblProductos = tblProductos;
    }

    public JButton getBtnDetalle() {
        return btnDetalle;
    }

    public void setBtnDetalle(JButton btnDetalle) {
        this.btnDetalle = btnDetalle;
    }

    public DefaultTableModel getModelo() {
        return modelo;
    }

    public void setModelo(DefaultTableModel modelo) {
        this.modelo = modelo;
    }

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


    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }

    public void cambiarIdioma(){
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

    public void inicializarImagenes(){
        URL eliminar = CarritoListaView.class.getClassLoader().getResource("imagenes/listar.png");
        if (eliminar != null) {
            ImageIcon iconoBtnIniciarSesion = new ImageIcon(eliminar);
            btnListar.setIcon(iconoBtnIniciarSesion);
        } else {
            System.err.println("Error: No se ha cargado el icono de Login");
        }

        URL buscar = CarritoListaView.class.getClassLoader().getResource("imagenes/buscar.png");
        if (buscar != null) {
            ImageIcon iconoBtnRegistrarse = new ImageIcon(buscar);
            btnBuscar.setIcon(iconoBtnRegistrarse);
        } else {
            System.err.println("Error: No se ha cargado el icono de Registrarse");
        }

        URL detalle = CarritoListaView.class.getClassLoader().getResource("imagenes/detalles.png");
        if (detalle != null) {
            ImageIcon iconoBtnRegistrarse = new ImageIcon(detalle);
            btnDetalle.setIcon(iconoBtnRegistrarse);
        } else {
            System.err.println("Error: No se ha cargado el icono de Registrarse");
        }
    }
}
