package ec.edu.ups.poo.clases;

import ec.edu.ups.poo.clases.controlador.CarritoController;
import ec.edu.ups.poo.clases.controlador.ProductoController;
import ec.edu.ups.poo.clases.controlador.UsuarioController;
import ec.edu.ups.poo.clases.dao.CarritoDAO;
import ec.edu.ups.poo.clases.dao.ProductoDAO;
import ec.edu.ups.poo.clases.dao.UsuarioDAO;
import ec.edu.ups.poo.clases.dao.impl.CarritoDAOMemoria;
import ec.edu.ups.poo.clases.dao.impl.ProductoDAOMemoria;
import ec.edu.ups.poo.clases.dao.impl.UsuarioDAOMemoria;
import ec.edu.ups.poo.clases.modelo.Rol;
import ec.edu.ups.poo.clases.modelo.Usuario;
import ec.edu.ups.poo.clases.vista.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Main {
    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(() -> {
            ProductoDAO productoDAO = new ProductoDAOMemoria();
            CarritoDAO carritoDAO = new CarritoDAOMemoria();

            UsuarioDAO usuarioDAO = new UsuarioDAOMemoria();
            LoginView loginView = new LoginView();
            loginView.setVisible(true);

            UsuarioController usuarioController = new UsuarioController(usuarioDAO, loginView);

            loginView.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    Usuario usuarioAuntenticado = usuarioController.getUsuarioAutenticado();
                    if(usuarioAuntenticado != null) {
                        PrincipalView principalView = new PrincipalView();
                        CarritoAnadirView carritoAnadirView = new CarritoAnadirView();
                        CarritoListaView carritoListaView = new CarritoListaView();
                        CarritoModificarView carritoModificarView = new CarritoModificarView();
                        CarritoEliminarView carritoEliminarView = new CarritoEliminarView();

                        ProductoAnadirView productoAnadirView = new ProductoAnadirView();
                        ProductoEditarView productoEditarView = new ProductoEditarView();
                        ProductoEliminarView productoEliminarView = new ProductoEliminarView();
                        ProductoListaView productoListaView = new ProductoListaView();

                        UsuarioCrearView usuarioCrearView = new UsuarioCrearView();
                        UsuarioEliminarView usuarioEliminarView = new UsuarioEliminarView();
                        UsuarioModificarView usuarioModificarView = new UsuarioModificarView();
                        UsuarioListarView usuarioListarView = new UsuarioListarView();

                        ProductoController productoController = new ProductoController(productoDAO, productoAnadirView,
                                productoListaView, productoEditarView, productoEliminarView, carritoAnadirView);

                        CarritoController carritoController = new CarritoController(carritoAnadirView, productoDAO, carritoDAO,usuarioAuntenticado,
                                carritoListaView, carritoModificarView, carritoEliminarView);

                        UsuarioController usuarioController = new UsuarioController(usuarioDAO, usuarioCrearView, usuarioEliminarView, usuarioModificarView,
                                usuarioListarView);

                        principalView.mostrarMensaje("Bienvenido: " + usuarioAuntenticado.getUsername());
                        if (usuarioAuntenticado.getRol().equals(Rol.USUARIO)) {
                            principalView.deshabilitarMenusAdministrador();
                        }

                        principalView.getMenuItemCrearUsuario().addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                if(!usuarioCrearView.isVisible()) {
                                    usuarioCrearView.setVisible(true);
                                    principalView.getjDesktopPane().add(usuarioCrearView);
                                }
                            }
                        });
                        principalView.getMenuItemEliminarUsuario().addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                if(!usuarioEliminarView.isVisible()) {
                                    usuarioEliminarView.setVisible(true);
                                    principalView.getjDesktopPane().add(usuarioEliminarView);
                                }
                            }
                        });
                        principalView.getMenuItemEditarUsuario().addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                if(!usuarioModificarView.isVisible()) {
                                    usuarioModificarView.setVisible(true);
                                    principalView.getjDesktopPane().add(usuarioModificarView);
                                }
                            }
                        });
                        principalView.getMenuItemListarUsuario().addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                if(!usuarioListarView.isVisible()) {
                                    usuarioListarView.setVisible(true);
                                    principalView.getjDesktopPane().add(usuarioListarView);
                                }
                            }
                        });
                        principalView.getMenuItemCrearCarrito().addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                if(!carritoAnadirView.isVisible()) {
                                    carritoAnadirView.setVisible(true);
                                    principalView.getjDesktopPane().add(carritoAnadirView);
                                }
                            }
                        });
                        principalView.getMenuItemListarCarrito().addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                if(!carritoListaView.isVisible()) {
                                    carritoListaView.setVisible(true);
                                    principalView.getjDesktopPane().add(carritoListaView);
                                }
                            }
                        });
                        principalView.getMenuItemEditarCarrito().addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                if(!carritoModificarView.isVisible()) {
                                    carritoModificarView.setVisible(true);
                                    principalView.getjDesktopPane().add(carritoModificarView);
                                }
                            }
                        });
                        principalView.getMenuItemEliminarCarrito().addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                if(!carritoEliminarView.isVisible()) {
                                    carritoEliminarView.setVisible(true);
                                    principalView.getjDesktopPane().add(carritoEliminarView);
                                }
                            }
                        });
                        principalView.getMenuItemCrearProducto().addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                if(!productoAnadirView.isVisible()) {
                                    productoAnadirView.setVisible(true);
                                    principalView.getjDesktopPane().add(productoAnadirView);
                                }
                            }
                        });
                        principalView.getMenuItemActualizarProducto().addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                if(!productoEditarView.isVisible()) {
                                    productoEditarView.setVisible(true);
                                    principalView.getjDesktopPane().add(productoEditarView);
                                }
                            }
                        });
                        principalView.getMenuItemEliminarProducto().addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                if(!productoEliminarView.isVisible()) {
                                    productoEliminarView.setVisible(true);
                                    principalView.getjDesktopPane().add(productoEliminarView);
                                }
                            }
                        });
                        principalView.getMenuItemBuscarProducto().addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                if(!productoListaView.isVisible()) {
                                    productoListaView.setVisible(true);
                                    principalView.getjDesktopPane().add(productoListaView);
                                }
                            }
                        });
                        principalView.getMenuItemCerrarSesion().addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                boolean confirmado = principalView.mostrarMensajePregunta("¿Desea cerrar sesión?");
                                if(confirmado) {
                                    principalView.dispose();
                                    usuarioController.setUsuarioAutenticado(null);
                                    loginView.setVisible(true);
                                }
                            }
                        });
                        principalView.getMenuItemSalir().addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                boolean confirmado = principalView.mostrarMensajePregunta("¿Desea Salir?");
                                if(confirmado) {
                                    principalView.dispose();
                                    System.exit(0);
                                }
                            }
                        });
                    }
                }
            });
        });
    }
}