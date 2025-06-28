package ec.edu.ups.poo.clases.vista.usuario;

import ec.edu.ups.poo.clases.modelo.Usuario;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;

public class UsuarioListarView extends JInternalFrame {
    private JTextField txtBuscar;
    private JButton btnBuscar;
    private JButton btnListar;
    private JTable tblUsuarios;
    private JPanel panelPrincipal;
    private DefaultTableModel modelo;
    public UsuarioListarView() {
        super("Listar Usuarios", true,true,false,true);
        setContentPane(panelPrincipal);
        setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);
        setSize(500, 500);

        modelo = new DefaultTableModel();
        Object[] columnas = {"Username", "Contraseña"};
        modelo.setColumnIdentifiers(columnas);
        tblUsuarios.setModel(modelo);
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
