package ec.edu.ups.poo.clases.vista.producto;

import ec.edu.ups.poo.clases.modelo.Producto;
import ec.edu.ups.poo.clases.util.FormateadorUtils;
import ec.edu.ups.poo.clases.util.MensajeInternacionalizacionHandler;
import ec.edu.ups.poo.clases.vista.usuario.UsuarioListarView;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.net.URL;
import java.util.List;

public class ProductoListaView extends JInternalFrame {
    private JPanel panelPrincipal;
    private JTextField txtBuscar;
    private JButton btnBuscar;
    private JButton btnListar;
    private JTable tblProductos;
    private JLabel lblNombre;
    private DefaultTableModel modelo;
    private MensajeInternacionalizacionHandler mi;
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
    // Cambia los textos visibles al idioma actual usando el handler de internacionalización
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
    // Llena la tabla con una lista de productos
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
    // Carga los íconos para los botones desde los recursos del proyecto
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
    //Getters y Setters
    public JLabel getLblNombre() {
        return lblNombre;
    }

    public void setLblNombre(JLabel lblNombre) {
        this.lblNombre = lblNombre;
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

    public JTable getTblProductos() {
        return tblProductos;
    }

    public void setTblProductos(JTable tblProductos) {
        this.tblProductos = tblProductos;
    }

    public JPanel getPanelPrincipal() {
        return panelPrincipal;
    }

    public void setPanelPrincipal(JPanel panelPrincipal) {
        this.panelPrincipal = panelPrincipal;
    }

    public JButton getBtnListar() {
        return btnListar;
    }

    public void setBtnListar(JButton btnListar) {
        this.btnListar = btnListar;
    }

    public DefaultTableModel getModelo() {
        return modelo;
    }

    public void setModelo(DefaultTableModel modelo) {
        this.modelo = modelo;
    }
}
