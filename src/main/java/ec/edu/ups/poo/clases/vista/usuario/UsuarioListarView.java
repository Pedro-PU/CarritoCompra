package ec.edu.ups.poo.clases.vista.usuario;

import ec.edu.ups.poo.clases.modelo.Usuario;
import ec.edu.ups.poo.clases.util.FormateadorUtils;
import ec.edu.ups.poo.clases.util.MensajeInternacionalizacionHandler;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.net.URL;
import java.util.List;
import java.util.Locale;

public class UsuarioListarView extends JInternalFrame {
    private JTextField txtBuscar;
    private JButton btnBuscar;
    private JButton btnListar;
    private JTable tblUsuarios;
    private JPanel panelPrincipal;
    private JLabel lblNombre;
    private DefaultTableModel modelo;
    private MensajeInternacionalizacionHandler mi;
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
    // Método para cambiar los textos a otro idioma
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
    // Carga la tabla con los datos de los usuarios
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
    // Carga los iconos para los botones
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

    public JButton getBtnListar() {
        return btnListar;
    }

    public void setBtnListar(JButton btnListar) {
        this.btnListar = btnListar;
    }

    public JTable getTblUsuarios() {
        return tblUsuarios;
    }

    public void setTblUsuarios(JTable tblUsuarios) {
        this.tblUsuarios = tblUsuarios;
    }

    public DefaultTableModel getModelo() {
        return modelo;
    }

    public void setModelo(DefaultTableModel modelo) {
        this.modelo = modelo;
    }
}
