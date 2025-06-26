package ec.edu.ups.poo.clases.vista;

import javax.swing.*;
import java.awt.*;

public class PrincipalView extends JFrame {
    private JMenuBar menuBar;
    private JMenu menuProducto;
    private JMenuItem menuCarrito;
    private JMenuItem menuItemCrearProducto;
    private JMenuItem menuItemEliminarProducto;
    private JMenuItem menuItemActualizarProducto;
    private JMenuItem menuItemBuscarProducto;
    private JDesktopPane jDesktopPane;
    private JMenuItem menuItemCrearCarrito;
    private JMenuItem menuItemListarCarrito;
    private JMenuItem menuItemEditarCarrito;
    private JMenuItem menuItemEliminarCarrito;
    private JMenuItem menuSalir;
    private JMenuItem menuItemCerrarSesion;
    private JMenuItem menuItemSalir;

    public PrincipalView() {
        // Panel principal que usará BorderLayout
        JPanel panelPrincipal = new JPanel(new BorderLayout());

        // Panel central con el área de escritorio (internas)
        jDesktopPane = new JDesktopPane();

        // Barra de menú y menús
        menuBar = new JMenuBar();
        menuProducto = new JMenu("Producto");
        menuCarrito = new JMenu("Carrito");
        menuSalir = new JMenu("Salir");

        menuItemCrearProducto = new JMenuItem("Crear Producto");
        menuItemEliminarProducto = new JMenuItem("Eliminar Producto");
        menuItemActualizarProducto = new JMenuItem("Actualizar Producto");
        menuItemBuscarProducto = new JMenuItem("Buscar Producto");
        menuItemCrearCarrito = new JMenuItem("Crear Carrito");
        menuItemListarCarrito = new JMenuItem("Listar Carrito");
        menuItemEditarCarrito = new JMenuItem("Editar Carrito");
        menuItemEliminarCarrito = new JMenuItem("Eliminar Carrito");
        menuItemSalir = new JMenuItem("Salir");
        menuItemCerrarSesion = new JMenuItem("Cerrar Sesion");

        menuSalir.add(menuItemSalir);
        menuSalir.add(menuItemCerrarSesion);
        menuCarrito.add(menuItemCrearCarrito);
        menuCarrito.add(menuItemListarCarrito);
        menuCarrito.add(menuItemEditarCarrito);
        menuCarrito.add(menuItemEliminarCarrito);
        menuProducto.add(menuItemCrearProducto);
        menuProducto.add(menuItemEliminarProducto);
        menuProducto.add(menuItemActualizarProducto);
        menuProducto.add(menuItemBuscarProducto);
        menuBar.add(menuCarrito);
        menuBar.add(menuProducto);
        menuBar.add(menuSalir);
        setJMenuBar(menuBar);

        panelPrincipal.add(jDesktopPane, BorderLayout.CENTER);

        setContentPane(panelPrincipal);

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Sistema Carrito de Compras");
        setLocationRelativeTo(null);
        setVisible(true);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
    }

    public JMenuItem getMenuItemEliminarCarrito() {
        return menuItemEliminarCarrito;
    }

    public void setMenuItemEliminarCarrito(JMenuItem menuItemEliminarCarrito) {
        this.menuItemEliminarCarrito = menuItemEliminarCarrito;
    }

    public JMenuItem getMenuItemEditarCarrito() {
        return menuItemEditarCarrito;
    }

    public void setMenuItemEditarCarrito(JMenuItem menuItemEditarCarrito) {
        this.menuItemEditarCarrito = menuItemEditarCarrito;
    }

    public JMenuItem getMenuSalir() {
        return menuSalir;
    }

    public void setMenuSalir(JMenuItem menuSalir) {
        this.menuSalir = menuSalir;
    }

    public JMenuItem getMenuItemCerrarSesion() {
        return menuItemCerrarSesion;
    }

    public void setMenuItemCerrarSesion(JMenuItem menuItemCerrarSesion) {
        this.menuItemCerrarSesion = menuItemCerrarSesion;
    }

    public JMenuItem getMenuItemSalir() {
        return menuItemSalir;
    }

    public void setMenuItemSalir(JMenuItem menuItemSalir) {
        this.menuItemSalir = menuItemSalir;
    }

    public JMenuItem getMenuItemListarCarrito() {
        return menuItemListarCarrito;
    }

    public void setMenuItemListarCarrito(JMenuItem menuItemListarCarrito) {
        this.menuItemListarCarrito = menuItemListarCarrito;
    }

    public JMenuItem getMenuItemCrearProducto() {
        return menuItemCrearProducto;
    }

    public void setMenuItemCrearProducto(JMenuItem menuItemCrearProducto) {
        this.menuItemCrearProducto = menuItemCrearProducto;
    }

    public JMenuItem getMenuItemEliminarProducto() {
        return menuItemEliminarProducto;
    }

    public void setMenuItemEliminarProducto(JMenuItem menuItemEliminarProducto) {
        this.menuItemEliminarProducto = menuItemEliminarProducto;
    }

    public JMenuItem getMenuItemActualizarProducto() {
        return menuItemActualizarProducto;
    }

    public void setMenuItemActualizarProducto(JMenuItem menuItemActualizarProducto) {
        this.menuItemActualizarProducto = menuItemActualizarProducto;
    }

    public JMenuItem getMenuItemBuscarProducto() {
        return menuItemBuscarProducto;
    }

    public void setMenuItemBuscarProducto(JMenuItem menuItemBuscarProducto) {
        this.menuItemBuscarProducto = menuItemBuscarProducto;
    }

    public JDesktopPane getjDesktopPane() {
        return jDesktopPane;
    }

    public JMenuItem getMenuCarrito() {
        return menuCarrito;
    }

    public void setMenuCarrito(JMenuItem menuCarrito) {
        this.menuCarrito = menuCarrito;
    }

    public JMenuItem getMenuItemCrearCarrito() {
        return menuItemCrearCarrito;
    }

    public void setMenuItemCrearCarrito(JMenuItem menuItemCrearCarrito) {
        this.menuItemCrearCarrito = menuItemCrearCarrito;
    }



    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }

    public boolean mostrarMensajePregunta(String mensaje) {
        int respuesta = JOptionPane.showConfirmDialog(this, mensaje, "Confirmación",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        return respuesta == JOptionPane.YES_OPTION;
    }

    public void deshabilitarMenusAdministrador() {
        getMenuItemCrearProducto().setEnabled(false);
        getMenuItemBuscarProducto().setEnabled(false);
        getMenuItemActualizarProducto().setEnabled(false);
        getMenuItemEliminarProducto().setEnabled(false);
    }
}
