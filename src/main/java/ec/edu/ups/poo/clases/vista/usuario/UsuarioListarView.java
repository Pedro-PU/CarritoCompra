package ec.edu.ups.poo.clases.vista.usuario;

import ec.edu.ups.poo.clases.modelo.Usuario;
import ec.edu.ups.poo.clases.util.MensajeInternacionalizacionHandler;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;

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
        setSize(500, 500);

        modelo = new DefaultTableModel();
        tblUsuarios.setModel(modelo);
        this.mi = mi;
        cambiarIdioma();
    }

    public void cambiarIdioma() {
        setTitle(mi.get("usuario.listar.titulo"));
        lblNombre.setText(mi.get("usuario.listar.buscar"));
        btnBuscar.setText(mi.get("usuario.listar.boton.buscar"));
        btnListar.setText(mi.get("usuario.listar.boton.listar"));

        Object[] columnas = {
                mi.get("usuario.listar.columna.username"),
                mi.get("usuario.listar.columna.contrasenia")
        };
        modelo.setColumnIdentifiers(columnas);

        UIManager.put("OptionPane.yesButtonText", mi.get("dialogo.boton.si"));
        UIManager.put("OptionPane.noButtonText", mi.get("dialogo.boton.no"));
        UIManager.put("OptionPane.cancelButtonText", mi.get("dialogo.boton.cancelar"));
        UIManager.put("OptionPane.okButtonText", mi.get("dialogo.boton.aceptar"));
    }

    public void cargarDatos(List<Usuario> listaUsuarios) {
        modelo.setNumRows(0);
        for (Usuario usuario : listaUsuarios) {
            Object[] fila = {
                    usuario.getUsername(),
                    usuario.getContrasenia()
            };
            modelo.addRow(fila);
        }
    }

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
