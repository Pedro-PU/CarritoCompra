package ec.edu.ups.poo.clases.vista;

import ec.edu.ups.poo.clases.util.MensajeInternacionalizacionHandler;
import ec.edu.ups.poo.clases.vista.cuestionario.CuestionarioView;

import javax.swing.*;
import java.awt.*;
import java.net.URL;
/**
 * Ventana principal del sistema de carrito de compras.
 * Contiene menús para gestionar productos, carritos, usuarios, idioma e inicio/cierre de sesión.
 * Utiliza una barra de menús y un panel central tipo DesktopPane para cargar subventanas internas.
 */
public class PrincipalView extends JFrame {
    private JMenuBar menuBar;
    private JMenu menuProducto;
    private JMenu menuCarrito;
    private JMenuItem menuItemCrearProducto;
    private JMenuItem menuItemEliminarProducto;
    private JMenuItem menuItemActualizarProducto;
    private JMenuItem menuItemBuscarProducto;
    private MiJDesktopPane jDesktopPane;//aquí lo uso
    private JMenuItem menuItemCrearCarrito;
    private JMenuItem menuItemListarCarrito;
    private JMenuItem menuItemEditarCarrito;
    private JMenuItem menuItemEliminarCarrito;
    private JMenu menuSalir;
    private JMenuItem menuItemCerrarSesion;
    private JMenuItem menuItemSalir;
    private JMenu menuUsuario;
    private JMenuItem menuItemCrearUsuario;
    private JMenuItem menuItemEliminarUsuario;
    private JMenuItem menuItemEditarUsuario;
    private JMenuItem menuItemListarUsuario;
    private JMenu menuIdioma;
    private JMenuItem menuItemEspanol;
    private JMenuItem menuItemIngles;
    private JMenuItem menuItemFrances;
    private String usuarioAutenticado;
    private MensajeInternacionalizacionHandler mi;
    /**
     * Constructor de la vista principal.
     * Inicializa todos los menús y elementos gráficos, y aplica configuraciones generales.
     *
     * @param mi Manejador de internacionalización utilizado para mostrar los textos en el idioma seleccionado.
     * @param usuarioAutenticado Nombre del usuario actualmente autenticado, usado para mostrar en el título.
     */
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
    /**
     * Muestra un mensaje emergente al usuario.
     *
     * @param mensaje Texto del mensaje a mostrar.
     */
    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }
    /**
     * Muestra un cuadro de confirmación con opciones Sí y No.
     *
     * @param mensaje Mensaje de la pregunta de confirmación.
     * @return true si el usuario selecciona "Sí", false en caso contrario.
     */
    public boolean mostrarMensajePregunta(String mensaje) {
        int respuesta = JOptionPane.showConfirmDialog(this, mensaje, "Confirmación",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        return respuesta == JOptionPane.YES_OPTION;
    }
    /**
     * Desactiva opciones de menú que solo están disponibles para usuarios con rol administrador.
     * Se utiliza para restringir el acceso según el rol del usuario autenticado.
     */
    public void deshabilitarMenusAdministrador() {
        getMenuItemCrearProducto().setEnabled(false);
        getMenuItemActualizarProducto().setEnabled(false);
        getMenuItemEliminarProducto().setEnabled(false);
        getMenuItemCrearUsuario().setEnabled(false);
        getMenuItemEliminarUsuario().setEnabled(false);
        getMenuItemListarUsuario().setEnabled(false);
    }
    /**
     * Cambia el idioma de todos los textos de la interfaz.
     * Se utiliza para aplicar la internacionalización en tiempo de ejecución.
     */
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
    /**
     * Inicializa los íconos de los elementos de menú.
     * Asocia imágenes locales a los botones relacionados con el carrito.
     * Muestra errores por consola si los recursos no se encuentran.
     */
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
    /**
     * Devuelve el ítem de menú para seleccionar idioma Español.
     *
     * @return JMenuItem correspondiente al idioma Español.
     */
    public JMenuItem getMenuItemEspanol() {
        return menuItemEspanol;
    }

    /**
     * Establece el ítem de menú para seleccionar idioma Español.
     *
     * @param menuItemEspanol El nuevo JMenuItem que representa el idioma Español.
     */
    public void setMenuItemEspanol(JMenuItem menuItemEspanol) {
        this.menuItemEspanol = menuItemEspanol;
    }

    /**
     * Devuelve el panel de escritorio (DesktopPane) que contiene las ventanas internas.
     *
     * @return JDesktopPane usado en esta vista principal.
     */
    public JMenuItem getMenuItemIngles() {
        return menuItemIngles;
    }

    /**
     * Devuelve el ítem de menú que permite crear un nuevo carrito.
     *
     * @return JMenuItem para la acción de crear carrito.
     */
    public void setMenuItemIngles(JMenuItem menuItemIngles) {
        this.menuItemIngles = menuItemIngles;
    }

    /**
     * Devuelve el ítem de menú que permite crear un nuevo carrito.
     *
     * @return JMenuItem para la acción de crear carrito.
     */
    public JMenuItem getMenuItemFrances() {
        return menuItemFrances;
    }

    /**
     * Establece el ítem de menú que permite cambiar el idioma a francés.
     *
     * @param menuItemFrances El nuevo JMenuItem correspondiente al idioma francés.
     */
    public void setMenuItemFrances(JMenuItem menuItemFrances) {
        this.menuItemFrances = menuItemFrances;
    }


    /**
     * Obtiene el ítem de menú para listar usuarios.
     * @return el JMenuItem para listar usuarios.
     */
    public JMenuItem getMenuItemListarUsuario() {
        return menuItemListarUsuario;
    }

    /**
     * Establece el ítem de menú para listar usuarios.
     * @param menuItemListarUsuario el JMenuItem a establecer.
     */
    public void setMenuItemListarUsuario(JMenuItem menuItemListarUsuario) {
        this.menuItemListarUsuario = menuItemListarUsuario;
    }

    /**
     * Obtiene el ítem de menú para editar usuarios.
     * @return el JMenuItem para editar usuarios.
     */
    public JMenuItem getMenuItemEditarUsuario() {
        return menuItemEditarUsuario;
    }

    /**
     * Establece el ítem de menú para editar usuarios.
     * @param menuItemEditarUsuario el JMenuItem a establecer.
     */
    public void setMenuItemEditarUsuario(JMenuItem menuItemEditarUsuario) {
        this.menuItemEditarUsuario = menuItemEditarUsuario;
    }

    /**
     * Obtiene el ítem de menú para eliminar usuarios.
     * @return el JMenuItem para eliminar usuarios.
     */
    public JMenuItem getMenuItemEliminarUsuario() {
        return menuItemEliminarUsuario;
    }

    /**
     * Establece el ítem de menú para eliminar usuarios.
     * @param menuItemEliminarUsuario el JMenuItem a establecer.
     */
    public void setMenuItemEliminarUsuario(JMenuItem menuItemEliminarUsuario) {
        this.menuItemEliminarUsuario = menuItemEliminarUsuario;
    }

    /**
     * Obtiene el ítem de menú para crear usuarios.
     * @return el JMenuItem para crear usuarios.
     */
    public JMenuItem getMenuItemCrearUsuario() {
        return menuItemCrearUsuario;
    }

    /**
     * Establece el ítem de menú para crear usuarios.
     * @param menuItemCrearUsuario el JMenuItem a establecer.
     */
    public void setMenuItemCrearUsuario(JMenuItem menuItemCrearUsuario) {
        this.menuItemCrearUsuario = menuItemCrearUsuario;
    }

    /**
     * Obtiene el ítem de menú para eliminar carritos.
     * @return el JMenuItem para eliminar carritos.
     */
    public JMenuItem getMenuItemEliminarCarrito() {
        return menuItemEliminarCarrito;
    }

    /**
     * Establece el ítem de menú para eliminar carritos.
     * @param menuItemEliminarCarrito el JMenuItem a establecer.
     */
    public void setMenuItemEliminarCarrito(JMenuItem menuItemEliminarCarrito) {
        this.menuItemEliminarCarrito = menuItemEliminarCarrito;
    }

    /**
     * Obtiene el ítem de menú para editar carritos.
     * @return el JMenuItem para editar carritos.
     */
    public JMenuItem getMenuItemEditarCarrito() {
        return menuItemEditarCarrito;
    }

    /**
     * Establece el ítem de menú para editar carritos.
     * @param menuItemEditarCarrito el JMenuItem a establecer.
     */
    public void setMenuItemEditarCarrito(JMenuItem menuItemEditarCarrito) {
        this.menuItemEditarCarrito = menuItemEditarCarrito;
    }

    /**
     * Obtiene el menú de salida.
     * @return el JMenu para salir.
     */
    public JMenuItem getMenuSalir() {
        return menuSalir;
    }

    /**
     * Establece el menú de salida.
     * @param menuSalir el JMenu a establecer.
     */
    public void setMenuSalir(JMenu menuSalir) {
        this.menuSalir = menuSalir;
    }

    /**
     * Obtiene el ítem de menú para cerrar sesión.
     * @return el JMenuItem para cerrar sesión.
     */
    public JMenuItem getMenuItemCerrarSesion() {
        return menuItemCerrarSesion;
    }

    /**
     * Establece el ítem de menú para cerrar sesión.
     * @param menuItemCerrarSesion el JMenuItem a establecer.
     */
    public void setMenuItemCerrarSesion(JMenuItem menuItemCerrarSesion) {
        this.menuItemCerrarSesion = menuItemCerrarSesion;
    }

    /**
     * Obtiene el ítem de menú para salir de la aplicación.
     * @return el JMenuItem para salir.
     */
    public JMenuItem getMenuItemSalir() {
        return menuItemSalir;
    }

    /**
     * Establece el ítem de menú para salir de la aplicación.
     * @param menuItemSalir el JMenuItem a establecer.
     */
    public void setMenuItemSalir(JMenuItem menuItemSalir) {
        this.menuItemSalir = menuItemSalir;
    }

    /**
     * Obtiene el ítem de menú para listar carritos.
     * @return el JMenuItem para listar carritos.
     */
    public JMenuItem getMenuItemListarCarrito() {
        return menuItemListarCarrito;
    }

    /**
     * Establece el ítem de menú para listar carritos.
     * @param menuItemListarCarrito el JMenuItem a establecer.
     */
    public void setMenuItemListarCarrito(JMenuItem menuItemListarCarrito) {
        this.menuItemListarCarrito = menuItemListarCarrito;
    }

    /**
     * Obtiene el ítem de menú para crear productos.
     * @return el JMenuItem para crear productos.
     */
    public JMenuItem getMenuItemCrearProducto() {
        return menuItemCrearProducto;
    }

    /**
     * Establece el ítem de menú para crear productos.
     * @param menuItemCrearProducto el JMenuItem a establecer.
     */
    public void setMenuItemCrearProducto(JMenuItem menuItemCrearProducto) {
        this.menuItemCrearProducto = menuItemCrearProducto;
    }

    /**
     * Obtiene el ítem de menú para eliminar productos.
     * @return el JMenuItem para eliminar productos.
     */
    public JMenuItem getMenuItemEliminarProducto() {
        return menuItemEliminarProducto;
    }

    /**
     * Establece el ítem de menú para eliminar productos.
     * @param menuItemEliminarProducto el JMenuItem a establecer.
     */
    public void setMenuItemEliminarProducto(JMenuItem menuItemEliminarProducto) {
        this.menuItemEliminarProducto = menuItemEliminarProducto;
    }

    /**
     * Obtiene el ítem de menú para actualizar productos.
     * @return el JMenuItem para actualizar productos.
     */
    public JMenuItem getMenuItemActualizarProducto() {
        return menuItemActualizarProducto;
    }

    /**
     * Establece el ítem de menú para actualizar productos.
     * @param menuItemActualizarProducto el JMenuItem a establecer.
     */
    public void setMenuItemActualizarProducto(JMenuItem menuItemActualizarProducto) {
        this.menuItemActualizarProducto = menuItemActualizarProducto;
    }

    /**
     * Obtiene el ítem de menú para buscar productos.
     * @return el JMenuItem para buscar productos.
     */
    public JMenuItem getMenuItemBuscarProducto() {
        return menuItemBuscarProducto;
    }

    /**
     * Establece el ítem de menú para buscar productos.
     * @param menuItemBuscarProducto el JMenuItem a establecer.
     */
    public void setMenuItemBuscarProducto(JMenuItem menuItemBuscarProducto) {
        this.menuItemBuscarProducto = menuItemBuscarProducto;
    }

    /**
     * Obtiene el panel principal de escritorio.
     * @return el JDesktopPane.
     */
    public JDesktopPane getjDesktopPane() {
        return jDesktopPane;
    }

    /**
     * Obtiene el menú de carrito.
     * @return el JMenu de carrito.
     */
    public JMenuItem getMenuCarrito() {
        return menuCarrito;
    }

    /**
     * Establece el menú de carrito.
     * @param menuCarrito el JMenu a establecer.
     */
    public void setMenuCarrito(JMenu menuCarrito) {
        this.menuCarrito = menuCarrito;
    }

    /**
     * Obtiene el ítem de menú para crear carritos.
     * @return el JMenuItem para crear carritos.
     */
    public JMenuItem getMenuItemCrearCarrito() {
        return menuItemCrearCarrito;
    }

    /**
     * Establece el ítem de menú para crear carritos.
     * @param menuItemCrearCarrito el JMenuItem a establecer.
     */
    public void setMenuItemCrearCarrito(JMenuItem menuItemCrearCarrito) {
        this.menuItemCrearCarrito = menuItemCrearCarrito;
    }

}
