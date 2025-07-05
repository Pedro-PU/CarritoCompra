package ec.edu.ups.poo.clases.vista;

import ec.edu.ups.poo.clases.util.MensajeInternacionalizacionHandler;
import ec.edu.ups.poo.clases.vista.cuestionario.CuestionarioView;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class PrincipalView extends JFrame {
    private JMenuBar menuBar;
    private JMenu menuProducto;
    private JMenuItem menuCarrito;
    private JMenuItem menuItemCrearProducto;
    private JMenuItem menuItemEliminarProducto;
    private JMenuItem menuItemActualizarProducto;
    private JMenuItem menuItemBuscarProducto;
    private MiJDesktopPane jDesktopPane;//aquí lo uso
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
        // Panel principal con BorderLayout (para incluir jDesktopPane)
        JPanel panelPrincipal = new JPanel(new BorderLayout());
        jDesktopPane = new MiJDesktopPane(mi);

        // Barra de menú y menús
        menuBar = new JMenuBar();
        menuProducto = new JMenu("Producto");
        menuCarrito = new JMenu("Carrito");
        menuSalir = new JMenu("Salir");
        menuUsuario = new JMenu("Usuario");
        menuIdioma = new JMenu("Idioma");
        // Submenús de idioma
        menuItemEspanol = new JMenuItem("Espanol");
        menuItemIngles = new JMenuItem("Ingles");
        menuItemFrances = new JMenuItem("Frances");
        // Submenús de producto
        menuItemCrearProducto = new JMenuItem("Crear Producto");
        menuItemEliminarProducto = new JMenuItem("Eliminar Producto");
        menuItemActualizarProducto = new JMenuItem("Actualizar Producto");
        menuItemBuscarProducto = new JMenuItem("Buscar Producto");
        // Submenús de carrito
        menuItemCrearCarrito = new JMenuItem("Crear Carrito");
        menuItemListarCarrito = new JMenuItem("Listar Carrito");
        menuItemEditarCarrito = new JMenuItem("Editar Carrito");
        // Salida
        menuItemEliminarCarrito = new JMenuItem("Eliminar Carrito");
        menuItemSalir = new JMenuItem("Salir");
        menuItemCerrarSesion = new JMenuItem("Cerrar Sesion");
        // Submenús de usuario
        menuItemCrearUsuario = new JMenuItem("Crear Usuario");
        menuItemEliminarUsuario = new JMenuItem("Eliminar Usuario");
        menuItemEditarUsuario = new JMenuItem("Editar Usuario");
        menuItemListarUsuario = new JMenuItem("Listar Usuario");
        // Agregar ítems a sus menús correspondientes
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
        // Agregar todos los menús a la barra de menú
        menuBar.add(menuCarrito);
        menuBar.add(menuProducto);
        menuBar.add(menuSalir);
        menuBar.add(menuUsuario);
        menuBar.add(menuIdioma);
        setJMenuBar(menuBar);
        // Agregar el área de trabajo (DesktopPane) al panel principal
        panelPrincipal.add(jDesktopPane, BorderLayout.CENTER);
        setContentPane(panelPrincipal);
        // Configuración general de la ventana
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Sistema Carrito de Compras");
        setLocationRelativeTo(null);
        setVisible(true);
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        cambiarIdioma();
        inicializarImagenes();
    }
    // Muestra mensaje emergente
    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }
    // Muestra cuadro de confirmación
    public boolean mostrarMensajePregunta(String mensaje) {
        int respuesta = JOptionPane.showConfirmDialog(this, mensaje, "Confirmación",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        return respuesta == JOptionPane.YES_OPTION;
    }
    // Activa o desactiva todos los campos de edición por rol de usuario
    public void deshabilitarMenusAdministrador() {
        getMenuItemCrearProducto().setEnabled(false);
        getMenuItemActualizarProducto().setEnabled(false);
        getMenuItemEliminarProducto().setEnabled(false);
        getMenuItemCrearUsuario().setEnabled(false);
        getMenuItemEliminarUsuario().setEnabled(false);
        getMenuItemListarUsuario().setEnabled(false);
    }
    // Aplica los textos internacionalizados
    public void cambiarIdioma() {
        jDesktopPane.actualizarIdioma();
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

        UIManager.put("OptionPane.yesButtonText", mi.get("dialogo.boton.si"));
        UIManager.put("OptionPane.noButtonText", mi.get("dialogo.boton.no"));
        UIManager.put("OptionPane.cancelButtonText", mi.get("dialogo.boton.cancelar"));
        UIManager.put("OptionPane.okButtonText", mi.get("dialogo.boton.aceptar"));
    }
    // Asocia íconos a los botones
    public void inicializarImagenes(){
        //Iconos Carrito
        URL carrito = PrincipalView.class.getClassLoader().getResource("imagenes/carrito.png");
        if (carrito != null) {
            ImageIcon iconoCarrito = new ImageIcon(carrito);
            menuCarrito.setIcon(iconoCarrito);
        } else {
            System.err.println("Error: No se ha cargado el icono de Login");
        }

        URL carritoCrear = PrincipalView.class.getClassLoader().getResource("imagenes/agregar.png");
        if (carritoCrear != null) {
            ImageIcon iconoCarritoCr = new ImageIcon(carritoCrear);
            menuItemCrearCarrito.setIcon(iconoCarritoCr);
        } else {
            System.err.println("Error: No se ha cargado el icono de Login");
        }
        URL carritoEditar = PrincipalView.class.getClassLoader().getResource("imagenes/editar.png");
        if (carritoEditar != null) {
            ImageIcon iconoCarritoEd = new ImageIcon(carritoEditar);
            menuItemEditarCarrito.setIcon(iconoCarritoEd);
        } else {
            System.err.println("Error: No se ha cargado el icono de Login");
        }
        URL carritoEliminar = PrincipalView.class.getClassLoader().getResource("imagenes/eliminar.png");
        if (carritoEliminar != null) {
            ImageIcon iconoCarritoEl = new ImageIcon(carritoEliminar);
            menuItemEliminarCarrito.setIcon(iconoCarritoEl);
        } else {
            System.err.println("Error: No se ha cargado el icono de Login");
        }
        URL carritoListar = PrincipalView.class.getClassLoader().getResource("imagenes/listar.png");
        if (carritoListar != null) {
            ImageIcon iconoCarritoLi = new ImageIcon(carritoListar);
            menuItemListarCarrito.setIcon(iconoCarritoLi);
        } else {
            System.err.println("Error: No se ha cargado el icono de Login");
        }

        //Iconos Producto
        URL producto = PrincipalView.class.getClassLoader().getResource("imagenes/producto.png");
        if (producto != null) {
            ImageIcon iconoProducto = new ImageIcon(producto);
            menuProducto.setIcon(iconoProducto);
        } else {
            System.err.println("Error: No se ha cargado el icono de Login");
        }

        URL productoCrear = PrincipalView.class.getClassLoader().getResource("imagenes/agregar.png");
        if (productoCrear != null) {
            ImageIcon iconoProductoCr = new ImageIcon(productoCrear);
            menuItemCrearProducto.setIcon(iconoProductoCr);
        } else {
            System.err.println("Error: No se ha cargado el icono de Login");
        }
        URL productoEditar = PrincipalView.class.getClassLoader().getResource("imagenes/editar.png");
        if (productoEditar != null) {
            ImageIcon iconoProductoEd = new ImageIcon(productoEditar);
            menuItemActualizarProducto.setIcon(iconoProductoEd);
        } else {
            System.err.println("Error: No se ha cargado el icono de Login");
        }
        URL productoEliminar = PrincipalView.class.getClassLoader().getResource("imagenes/eliminar.png");
        if (productoEliminar != null) {
            ImageIcon iconoProductoEl = new ImageIcon(productoEliminar);
            menuItemEliminarProducto.setIcon(iconoProductoEl);
        } else {
            System.err.println("Error: No se ha cargado el icono de Login");
        }
        URL productoListar = PrincipalView.class.getClassLoader().getResource("imagenes/listar.png");
        if (productoListar != null) {
            ImageIcon iconoProductoLi = new ImageIcon(productoListar);
            menuItemBuscarProducto.setIcon(iconoProductoLi);
        } else {
            System.err.println("Error: No se ha cargado el icono de Login");
        }
        //Iconos salir
        URL salir = PrincipalView.class.getClassLoader().getResource("imagenes/salir.png");
        if (salir != null) {
            ImageIcon iconoSalir = new ImageIcon(salir);
            menuSalir.setIcon(iconoSalir);
        } else {
            System.err.println("Error: No se ha cargado el icono de Login");
        }
        URL salirS = PrincipalView.class.getClassLoader().getResource("imagenes/salida.png");
        if (salirS != null) {
            ImageIcon iconoSalirS = new ImageIcon(salirS);
            menuItemSalir.setIcon(iconoSalirS);
        } else {
            System.err.println("Error: No se ha cargado el icono de Login");
        }
        URL cerrar = PrincipalView.class.getClassLoader().getResource("imagenes/cerrar.png");
        if (cerrar != null) {
            ImageIcon iconoCerrar = new ImageIcon(cerrar);
            menuItemCerrarSesion.setIcon(iconoCerrar);
        } else {
            System.err.println("Error: No se ha cargado el icono de Login");
        }
        //Iconos usuario
        URL usuario = PrincipalView.class.getClassLoader().getResource("imagenes/usuario.png");
        if (usuario != null) {
            ImageIcon iconoUsuario = new ImageIcon(usuario);
            menuUsuario.setIcon(iconoUsuario);
        } else {
            System.err.println("Error: No se ha cargado el icono de Login");
        }

        URL usuarioCrear = PrincipalView.class.getClassLoader().getResource("imagenes/agregar.png");
        if (usuarioCrear != null) {
            ImageIcon iconoUsuarioCr = new ImageIcon(usuarioCrear);
            menuItemCrearUsuario.setIcon(iconoUsuarioCr);
        } else {
            System.err.println("Error: No se ha cargado el icono de Login");
        }
        URL usuarioEditar = PrincipalView.class.getClassLoader().getResource("imagenes/editar.png");
        if (usuarioEditar != null) {
            ImageIcon iconoUsuarioEd = new ImageIcon(usuarioEditar);
            menuItemEditarUsuario.setIcon(iconoUsuarioEd);
        } else {
            System.err.println("Error: No se ha cargado el icono de Login");
        }
        URL usuarioEliminar = PrincipalView.class.getClassLoader().getResource("imagenes/eliminar.png");
        if (usuarioEliminar != null) {
            ImageIcon iconoUsuarioEl = new ImageIcon(usuarioEliminar);
            menuItemEliminarUsuario.setIcon(iconoUsuarioEl);
        } else {
            System.err.println("Error: No se ha cargado el icono de Login");
        }
        URL usuarioListar = PrincipalView.class.getClassLoader().getResource("imagenes/listar.png");
        if (usuarioListar != null) {
            ImageIcon iconoUsuarioLi = new ImageIcon(usuarioListar);
            menuItemListarUsuario.setIcon(iconoUsuarioLi);
        } else {
            System.err.println("Error: No se ha cargado el icono de Login");
        }
        //Iconos idioma
        URL idioma = PrincipalView.class.getClassLoader().getResource("imagenes/idioma.png");
        if (idioma != null) {
            ImageIcon iconoIdioma = new ImageIcon(idioma);
            menuIdioma.setIcon(iconoIdioma);
        } else {
            System.err.println("Error: No se ha cargado el icono de Login");
        }
        URL idiomaEspanol = PrincipalView.class.getClassLoader().getResource("imagenes/idiomas.png");
        if (idiomaEspanol  != null) {
            ImageIcon iconoIdiomaEsp = new ImageIcon(idiomaEspanol);
            menuItemEspanol.setIcon(iconoIdiomaEsp);
        } else {
            System.err.println("Error: No se ha cargado el icono de Login");
        }
        URL idiomaIngles = PrincipalView.class.getClassLoader().getResource("imagenes/idiomas.png");
        if (idiomaIngles  != null) {
            ImageIcon iconoIdiomaIng = new ImageIcon(idiomaIngles);
            menuItemIngles.setIcon(iconoIdiomaIng);
        } else {
            System.err.println("Error: No se ha cargado el icono de Login");
        }
        URL idiomaFrances = PrincipalView.class.getClassLoader().getResource("imagenes/idiomas.png");
        if (idiomaFrances  != null) {
            ImageIcon iconoIdiomaFr = new ImageIcon(idiomaFrances);
            menuItemFrances.setIcon(iconoIdiomaFr);
        } else {
            System.err.println("Error: No se ha cargado el icono de Login");
        }
    }
    // Getters y Setters
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

}
