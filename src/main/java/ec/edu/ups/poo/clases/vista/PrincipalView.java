package ec.edu.ups.poo.clases.vista;

import ec.edu.ups.poo.clases.util.MensajeInternacionalizacionHandler;

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
    private JMenuItem menuUsuario;
    private JMenuItem menuItemCrearUsuario;
    private JMenuItem menuItemEliminarUsuario;
    private JMenuItem menuItemEditarUsuario;
    private JMenuItem menuItemListarUsuario;
    private JMenuItem menuIdioma;
    private JMenuItem menuItemEspanol;
    private JMenuItem menuItemIngles;
    private JMenuItem menuItemFrances;
    private String usuarioAutenticado;
    private MensajeInternacionalizacionHandler mi;

    public PrincipalView(MensajeInternacionalizacionHandler mi, String usuarioAutenticado) {
        this.mi = mi;
        this.usuarioAutenticado = usuarioAutenticado;

        JPanel panelPrincipal = new JPanel(new BorderLayout());

        jDesktopPane = new JDesktopPane();

        // Barra de menú y menús
        menuBar = new JMenuBar();
        menuProducto = new JMenu("Producto");
        menuCarrito = new JMenu("Carrito");
        menuSalir = new JMenu("Salir");
        menuUsuario = new JMenu("Usuario");
        menuIdioma = new JMenu("Idioma");

        menuItemEspanol = new JMenuItem("Espanol");
        menuItemIngles = new JMenuItem("Ingles");
        menuItemFrances = new JMenuItem("Frances");
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
        menuItemCrearUsuario = new JMenuItem("Crear Usuario");
        menuItemEliminarUsuario = new JMenuItem("Eliminar Usuario");
        menuItemEditarUsuario = new JMenuItem("Editar Usuario");
        menuItemListarUsuario = new JMenuItem("Listar Usuario");

        menuIdioma.add(menuItemEspanol);
        menuIdioma.add(menuItemIngles);
        menuIdioma.add(menuItemFrances);
        menuUsuario.add(menuItemCrearUsuario);
        menuUsuario.add(menuItemEliminarUsuario);
        menuUsuario.add(menuItemEditarUsuario);
        menuUsuario.add(menuItemListarUsuario);
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
        menuBar.add(menuUsuario);
        menuBar.add(menuIdioma);
        setJMenuBar(menuBar);

        panelPrincipal.add(jDesktopPane, BorderLayout.CENTER);

        setContentPane(panelPrincipal);

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Sistema Carrito de Compras");
        setLocationRelativeTo(null);
        setVisible(true);
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        actualizarTextos();
    }

    public JMenuItem getMenuItemEspanol() {
        return menuItemEspanol;
    }

    public void setMenuItemEspanol(JMenuItem menuItemEspanol) {
        this.menuItemEspanol = menuItemEspanol;
    }

    public JMenuItem getMenuItemIngles() {
        return menuItemIngles;
    }

    public void setMenuItemIngles(JMenuItem menuItemIngles) {
        this.menuItemIngles = menuItemIngles;
    }

    public JMenuItem getMenuItemFrances() {
        return menuItemFrances;
    }

    public void setMenuItemFrances(JMenuItem menuItemFrances) {
        this.menuItemFrances = menuItemFrances;
    }

    public JMenuItem getMenuItemListarUsuario() {
        return menuItemListarUsuario;
    }

    public void setMenuItemListarUsuario(JMenuItem menuItemListarUsuario) {
        this.menuItemListarUsuario = menuItemListarUsuario;
    }

    public JMenuItem getMenuItemEditarUsuario() {
        return menuItemEditarUsuario;
    }

    public void setMenuItemEditarUsuario(JMenuItem menuItemEditarUsuario) {
        this.menuItemEditarUsuario = menuItemEditarUsuario;
    }

    public JMenuItem getMenuItemEliminarUsuario() {
        return menuItemEliminarUsuario;
    }

    public void setMenuItemEliminarUsuario(JMenuItem menuItemEliminarUsuario) {
        this.menuItemEliminarUsuario = menuItemEliminarUsuario;
    }

    public JMenuItem getMenuItemCrearUsuario() {
        return menuItemCrearUsuario;
    }

    public void setMenuItemCrearUsuario(JMenuItem menuItemCrearUsuario) {
        this.menuItemCrearUsuario = menuItemCrearUsuario;
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
        getMenuItemCrearUsuario().setEnabled(false);
        getMenuItemEliminarUsuario().setEnabled(false);
        getMenuItemListarUsuario().setEnabled(false);
        getMenuItemEditarUsuario().setEnabled(false);
    }

    public void cambiarIdioma() {
        mi.setLenguaje(mi.getLocale().getLanguage(), mi.getLocale().getCountry());
        actualizarTextos();
    }

    public void actualizarTextos() {
        setTitle(mi.get("principal.titulo") + " - " + usuarioAutenticado);

        menuProducto.setText(mi.get("principal.menu.producto"));
        menuCarrito.setText(mi.get("principal.menu.carrito"));
        menuSalir.setText(mi.get("principal.menu.salir"));
        menuUsuario.setText(mi.get("principal.menu.usuario"));
        menuIdioma.setText(mi.get("principal.menu.idioma"));

        menuItemCrearProducto.setText(mi.get("principal.producto.crear"));
        menuItemEliminarProducto.setText(mi.get("principal.producto.eliminar"));
        menuItemActualizarProducto.setText(mi.get("principal.producto.actualizar"));
        menuItemBuscarProducto.setText(mi.get("principal.producto.buscar"));

        menuItemCrearCarrito.setText(mi.get("principal.carrito.crear"));
        menuItemListarCarrito.setText(mi.get("principal.carrito.listar"));
        menuItemEditarCarrito.setText(mi.get("principal.carrito.editar"));
        menuItemEliminarCarrito.setText(mi.get("principal.carrito.eliminar"));

        menuItemSalir.setText(mi.get("principal.menu.salir.sistema"));
        menuItemCerrarSesion.setText(mi.get("principal.menu.salir.sesion"));

        menuItemCrearUsuario.setText(mi.get("principal.usuario.crear"));
        menuItemEliminarUsuario.setText(mi.get("principal.usuario.eliminar"));
        menuItemEditarUsuario.setText(mi.get("principal.usuario.editar"));
        menuItemListarUsuario.setText(mi.get("principal.usuario.listar"));

        menuItemEspanol.setText(mi.get("principal.idioma.es"));
        menuItemIngles.setText(mi.get("principal.idioma.en"));
        menuItemFrances.setText(mi.get("principal.idioma.fr"));
    }


}
