package ec.edu.ups.poo.clases;

import ec.edu.ups.poo.clases.controlador.CarritoController;
import ec.edu.ups.poo.clases.controlador.ProductoController;
import ec.edu.ups.poo.clases.controlador.UsuarioController;
import ec.edu.ups.poo.clases.dao.*;
import ec.edu.ups.poo.clases.dao.impl.*;
import ec.edu.ups.poo.clases.modelo.Rol;
import ec.edu.ups.poo.clases.modelo.Usuario;
import ec.edu.ups.poo.clases.util.MensajeInternacionalizacionHandler;
import ec.edu.ups.poo.clases.vista.*;
import ec.edu.ups.poo.clases.vista.carrito.CarritoAnadirView;
import ec.edu.ups.poo.clases.vista.carrito.CarritoEliminarView;
import ec.edu.ups.poo.clases.vista.carrito.CarritoListaView;
import ec.edu.ups.poo.clases.vista.carrito.CarritoModificarView;
import ec.edu.ups.poo.clases.vista.producto.ProductoAnadirView;
import ec.edu.ups.poo.clases.vista.producto.ProductoEditarView;
import ec.edu.ups.poo.clases.vista.producto.ProductoEliminarView;
import ec.edu.ups.poo.clases.vista.producto.ProductoListaView;
import ec.edu.ups.poo.clases.vista.usuario.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.Locale;
/**
 * Clase principal que inicia la aplicación de gestión de carritos, productos y usuarios.
 *
 * Esta clase configura el sistema de almacenamiento (memoria o archivo), selecciona el idioma,
 * y lanza la interfaz gráfica de inicio de sesión. Posteriormente, según el usuario autenticado,
 * carga las vistas correspondientes y controla los eventos de los menús.
 */
public class Main {

    /**
     * Método principal que arranca la aplicación.
     * Configura el sistema de internacionalización y los DAOs según el tipo de almacenamiento seleccionado.
     *
     * @param args Argumentos de línea de comandos (no utilizados).
     */
    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(() -> {

            // Vista de selección de tipo de almacenamiento e idioma
            ArchivoView archivoView = new ArchivoView(new MensajeInternacionalizacionHandler("es", "EC"));
            archivoView.setVisible(true);

            archivoView.setOnGuardar(e -> {
                Locale locale = archivoView.getIdiomaSeleccionado();
                MensajeInternacionalizacionHandler mi = new MensajeInternacionalizacionHandler(locale.getLanguage(), locale.getCountry());
                mi.setLenguaje(locale.getLanguage(), locale.getCountry());
                String tipo = archivoView.getTipoAlmacenamientoSeleccionado();

                // Declaración de DAOs
                ProductoDAO productoDAO;
                CarritoDAO carritoDAO;
                PreguntaDAO preguntaDAO;
                UsuarioDAO usuarioDAO;

                // Selección de almacenamiento: archivo o memoria
                if (tipo.equals("archivo")) {
                    JFileChooser fileChooser = new JFileChooser();
                    fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

                    int seleccion = fileChooser.showOpenDialog(archivoView);
                    if (seleccion == JFileChooser.APPROVE_OPTION) {
                        File dir = fileChooser.getSelectedFile();
                        productoDAO = new ProductoDAOArchivo(new File(dir, "productos.dat"));
                        carritoDAO = new CarritoDAOArchivo(new File(dir, "carrito.txt"), productoDAO);
                        preguntaDAO = new PreguntaDAOArchivo(new File(dir, "preguntas.dat"), mi);
                        usuarioDAO = new UsuarioDAOArchivo(new File(dir, "usuarios.txt"), preguntaDAO);
                    } else {
                        archivoView.mostrarMensaje(mi.get("errorDAO"));
                        return;
                    }
                } else {
                    productoDAO = new ProductoDAOMemoria();
                    carritoDAO = new CarritoDAOMemoria();
                    preguntaDAO = new PreguntaDAOMemoria(mi);
                    usuarioDAO = new UsuarioDAOMemoria(preguntaDAO);
                }

                archivoView.dispose();

                // Vista de login e inicialización del controlador de usuarios
                LoginView loginView = new LoginView(mi);
                loginView.setVisible(true);
                UsuarioController usuarioController = new UsuarioController(usuarioDAO, loginView, preguntaDAO, mi);

                // Acciones al cerrar la ventana de login
                loginView.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosed(WindowEvent e) {
                        Usuario usuarioAuntenticado = usuarioController.getUsuarioAutenticado();
                        if(usuarioAuntenticado != null) {
                            // Inicialización de vistas
                            PrincipalView principalView = new PrincipalView(mi, usuarioAuntenticado.getUsername());

                            CarritoAnadirView carritoAnadirView = new CarritoAnadirView(mi);
                            CarritoListaView carritoListaView = new CarritoListaView(mi);
                            CarritoModificarView carritoModificarView = new CarritoModificarView(mi);
                            CarritoEliminarView carritoEliminarView = new CarritoEliminarView(mi);

                            ProductoAnadirView productoAnadirView = new ProductoAnadirView(mi);
                            ProductoEditarView productoEditarView = new ProductoEditarView(mi);
                            ProductoEliminarView productoEliminarView = new ProductoEliminarView(mi);
                            ProductoListaView productoListaView = new ProductoListaView(mi);

                            UsuarioCrearView usuarioCrearView = new UsuarioCrearView(mi);
                            UsuarioEliminarView usuarioEliminarView = new UsuarioEliminarView(mi);
                            UsuarioModificarView usuarioModificarView = new UsuarioModificarView(mi);
                            UsuarioListarView usuarioListarView = new UsuarioListarView(mi);

                            // Inicialización de controladores
                            ProductoController productoController = new ProductoController(productoDAO, productoAnadirView,
                                    productoListaView, productoEditarView, productoEliminarView, carritoAnadirView, mi);

                            CarritoController carritoController = new CarritoController(carritoAnadirView, productoDAO, carritoDAO, usuarioAuntenticado,
                                    carritoListaView, carritoModificarView, carritoEliminarView, mi);

                            UsuarioController usuarioController = new UsuarioController(usuarioDAO, usuarioCrearView, usuarioEliminarView,
                                    usuarioModificarView, usuarioListarView, mi, usuarioAuntenticado);

                            // Mensaje de bienvenida
                            principalView.mostrarMensaje(mi.get("principal.bienvenido") + usuarioAuntenticado.getUsername());
                            principalView.setTitle(mi.get("principal.titulo") + " - " + usuarioAuntenticado.getUsername());

                            // Restricciones para el rol USUARIO
                            if (usuarioAuntenticado.getRol().equals(Rol.USUARIO)) {
                                principalView.deshabilitarMenusAdministrador();
                            }

                            // Asociación de eventos a los ítems del menú principal
                            // Menú Usuario
                            principalView.getMenuItemCrearUsuario().addActionListener(e1 -> abrirVentana(principalView, usuarioCrearView));
                            principalView.getMenuItemEliminarUsuario().addActionListener(e1 -> abrirVentana(principalView, usuarioEliminarView));
                            principalView.getMenuItemEditarUsuario().addActionListener(e1 -> abrirVentana(principalView, usuarioModificarView));
                            principalView.getMenuItemListarUsuario().addActionListener(e1 -> abrirVentana(principalView, usuarioListarView));

                            // Menú Carrito
                            principalView.getMenuItemCrearCarrito().addActionListener(e1 -> abrirVentana(principalView, carritoAnadirView));
                            principalView.getMenuItemListarCarrito().addActionListener(e1 -> abrirVentana(principalView, carritoListaView));
                            principalView.getMenuItemEditarCarrito().addActionListener(e1 -> abrirVentana(principalView, carritoModificarView));
                            principalView.getMenuItemEliminarCarrito().addActionListener(e1 -> abrirVentana(principalView, carritoEliminarView));

                            // Menú Producto
                            principalView.getMenuItemCrearProducto().addActionListener(e1 -> abrirVentana(principalView, productoAnadirView));
                            principalView.getMenuItemActualizarProducto().addActionListener(e1 -> abrirVentana(principalView, productoEditarView));
                            principalView.getMenuItemEliminarProducto().addActionListener(e1 -> abrirVentana(principalView, productoEliminarView));
                            principalView.getMenuItemBuscarProducto().addActionListener(e1 -> abrirVentana(principalView, productoListaView));

                            // Menú sesión
                            principalView.getMenuItemCerrarSesion().addActionListener(e1 -> {
                                boolean confirmado = principalView.mostrarMensajePregunta(mi.get("principal.cerrar"));
                                if(confirmado) {
                                    principalView.dispose();
                                    usuarioController.setUsuarioAutenticado(null);
                                    loginView.actualizarTextos();
                                    loginView.setVisible(true);
                                }
                            });

                            principalView.getMenuItemSalir().addActionListener(e1 -> {
                                boolean confirmado = principalView.mostrarMensajePregunta(mi.get("principal.salir"));
                                if(confirmado) {
                                    principalView.dispose();
                                    System.exit(0);
                                }
                            });

                            // Cambiar idioma
                            principalView.getMenuItemEspanol().addActionListener(e1 -> {
                                mi.setLenguaje("es", "EC");
                                actualizarIdioma(principalView, usuarioController, productoController, carritoController);
                            });
                            principalView.getMenuItemIngles().addActionListener(e1 -> {
                                mi.setLenguaje("en", "US");
                                actualizarIdioma(principalView, usuarioController, productoController, carritoController);
                            });
                            principalView.getMenuItemFrances().addActionListener(e1 -> {
                                mi.setLenguaje("fr", "FR");
                                actualizarIdioma(principalView, usuarioController, productoController, carritoController);
                            });
                        }
                    }
                });
            });

            // Acción del botón salir en la vista de selección de almacenamiento
            archivoView.setOnSalir(e -> System.exit(0));
        });
    }

    /**
     * Método auxiliar que abre una ventana en el escritorio si no está visible.
     *
     * @param principalView Vista principal que contiene el escritorio.
     * @param internalView Vista interna a mostrar.
     */
    private static void abrirVentana(PrincipalView principalView, javax.swing.JInternalFrame internalView) {
        if (!internalView.isVisible()) {
            internalView.setVisible(true);
            principalView.getjDesktopPane().add(internalView);
        }
    }

    /**
     * Método auxiliar que actualiza los textos de idioma en todas las vistas y controladores.
     *
     * @param principalView Vista principal.
     * @param usuarioController Controlador de usuario.
     * @param productoController Controlador de producto.
     * @param carritoController Controlador de carrito.
     */
    private static void actualizarIdioma(PrincipalView principalView, UsuarioController usuarioController,
                                         ProductoController productoController, CarritoController carritoController) {
        principalView.cambiarIdioma();
        usuarioController.actualizarIdiomaEnVistas();
        productoController.actualizarIdiomaEnVistas();
        carritoController.actualizarIdiomaEnVistas();
    }
}
