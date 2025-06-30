package ec.edu.ups.poo.clases.controlador;
import ec.edu.ups.poo.clases.dao.CuestionarioDAO;
import ec.edu.ups.poo.clases.dao.UsuarioDAO;
import ec.edu.ups.poo.clases.dao.impl.CuestionarioDAOMemoria;
import ec.edu.ups.poo.clases.modelo.Cuestionario;
import ec.edu.ups.poo.clases.modelo.Rol;
import ec.edu.ups.poo.clases.modelo.Usuario;
import ec.edu.ups.poo.clases.util.MensajeInternacionalizacionHandler;
import ec.edu.ups.poo.clases.vista.cuestionario.CuestionarioRecuperarView;
import ec.edu.ups.poo.clases.vista.cuestionario.CuestionarioView;
import ec.edu.ups.poo.clases.vista.usuario.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

public class UsuarioController {
    private Usuario usuario;
    private final UsuarioDAO usuarioDAO;
    private LoginView loginView;
    private UsuarioCrearView usuarioCrearView;
    private UsuarioEliminarView usuarioEliminarView;
    private UsuarioModificarView usuarioModificarView;
    private UsuarioListarView usuarioListarView;
    private CuestionarioDAO cuestionarioDAO;
    private final MensajeInternacionalizacionHandler mi;

    public UsuarioController(UsuarioDAO usuarioDAO, LoginView loginView, CuestionarioDAO cuestionarioDAO,
                             MensajeInternacionalizacionHandler mi) {
        this.usuarioDAO = usuarioDAO;
        this.loginView = loginView;
        this.usuario = null;
        this.cuestionarioDAO = cuestionarioDAO;
        this.mi = mi;

        configurarEventosLogin();
    }

    public UsuarioController (UsuarioDAO usuarioDAO, UsuarioCrearView usuarioCrearView, UsuarioEliminarView usuarioEliminarView,
                              UsuarioModificarView usuarioModificarView, UsuarioListarView usuarioListarView, MensajeInternacionalizacionHandler mi) {
        this.usuarioDAO = usuarioDAO;
        this.usuarioCrearView = usuarioCrearView;
        this.usuarioEliminarView = usuarioEliminarView;
        this.usuarioModificarView = usuarioModificarView;
        this.usuarioListarView = usuarioListarView;
        this.mi = mi;
        configurarEventosUsuario();
    }
    private void configurarEventosLogin(){
        loginView.getBtnIniciar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                autenticar();
            }
        });
        loginView.getBtnRegistrar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                registrar();
            }
        });
        loginView.getBtnOlvidar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                recuperar();
            }
        });
        loginView.getBtnSalir().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                salir();
            }
        });
        loginView.getCbxIdiomas().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cambiarIdioma();
            }
        });
    }

    private void configurarEventosUsuario(){
        usuarioCrearView.getBtnAceptar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                crear();
            }
        });
        usuarioEliminarView.getBtnEliminar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                eliminar();
            }
        });
        usuarioEliminarView.getBtnBuscar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buscarEliminar();
            }
        });
        usuarioModificarView.getBtnBuscar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buscarModificar();
            }
        });
        usuarioModificarView.getBtnEditar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editar();
            }
        });
        usuarioListarView.getBtnListar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                listar();
            }
        });
        usuarioListarView.getBtnBuscar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buscarUsario();
            }
        });
    }



    private void cambiarIdioma() {
        String[] clavesIdiomas = {"es", "en", "fr"};
        String[] paisesIdiomas = {"EC", "US", "FR"};
        int index = loginView.getCbxIdiomas().getSelectedIndex();

        if (index >= 0 && index < 3) {
            mi.setLenguaje(clavesIdiomas[index], paisesIdiomas[index]);
            loginView.actualizarTextos();
        }
    }

    private void salir(){
        loginView.dispose();
        System.exit(0);
    }

    private void autenticar() {
        String username = loginView.getTxtUsername().getText().trim();
        String contrasenia = loginView.getTxtContrasenia().getText().trim();

        usuario = usuarioDAO.autenticar(username, contrasenia);

        if (usuario == null) {
            loginView.mostrarMensaje(mi.get("login.mensaje.error_autenticacion"));
        } else {
            Cuestionario cuestionario = cuestionarioDAO.buscarPorUsername(username);

            if (cuestionario == null || cuestionario.getRespuestas().size() < 3) {
                loginView.mostrarMensaje(mi.get("login.mensaje.incompleto"));

                CuestionarioView cuestionarioView = new CuestionarioView(mi);
                CuestionarioController controller = new CuestionarioController(
                        cuestionarioView, cuestionarioDAO, username, mi);
                cuestionarioView.setVisible(true);
                loginView.setVisible(false);

                cuestionarioView.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                cuestionarioView.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosed(WindowEvent e) {
                        loginView.setVisible(true);
                    }
                });

            } else {
                loginView.dispose();
            }
        }
    }


    public Usuario getUsuarioAutenticado() {
        return usuario;
    }

    public void setUsuarioAutenticado(Usuario usuario) {
        this.usuario = usuario;
    }

    private void registrar() {
        boolean confirmado = loginView.mostrarMensajePregunta(mi.get("login.mensaje.pregunta_registro"));
        if (confirmado) {
            String username = loginView.getTxtUsername().getText().trim();
            String contrasenia = loginView.getTxtContrasenia().getText().trim();

            if (usuarioDAO.buscarPorUsername(username) != null) {
                loginView.mostrarMensaje(mi.get("login.mensaje.error_usuario_existente"));
                return;
            }

            Usuario nuevoUsuario = new Usuario(username, contrasenia, Rol.USUARIO);
            usuarioDAO.crear(nuevoUsuario);
            loginView.mostrarMensaje(mi.get("login.mensaje.usuario_creado"));

            CuestionarioView cuestionarioView = new CuestionarioView(mi);
            CuestionarioController cuestionarioController = new CuestionarioController(cuestionarioView,
                    cuestionarioDAO, username, mi);
            cuestionarioView.setVisible(true);

            loginView.setVisible(false);

            cuestionarioView.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            cuestionarioView.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e){
                    loginView.setVisible(true);
                }
            });
        } else {
            loginView.mostrarMensaje(mi.get("login.mensaje.creacion_cancelada"));
        }
    }

    private void recuperar() {
        boolean confirmado = loginView.mostrarMensajePregunta(mi.get("login.mensaje.pregunta_recuperar"));
        if (confirmado) {
            String username = loginView.getTxtUsername().getText().trim();

            Usuario usuario = usuarioDAO.buscarPorUsername(username);
            if (usuario == null) {
                loginView.mostrarMensaje(mi.get("login.mensaje.usuario_no_encontrado"));
                return;
            }

            if (usuario.getRol() == Rol.ADMINISTRADOR) {
                loginView.mostrarMensaje(mi.get("login.mensaje.recuperacion_no_disponible_admin"));
                return;
            }

            Cuestionario cuestionario = cuestionarioDAO.buscarPorUsername(username);
            if (cuestionario == null || cuestionario.getRespuestas().isEmpty()) {
                loginView.mostrarMensaje(mi.get("login.mensaje.sin_preguntas"));
                return;
            }

            CuestionarioRecuperarView recuperarView = new CuestionarioRecuperarView(mi);
            CuestionarioController controller = new CuestionarioController(
                    recuperarView, cuestionarioDAO, username, usuario.getContrasenia(), mi
            );

            recuperarView.setVisible(true);
            loginView.setVisible(false);

            recuperarView.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            recuperarView.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    loginView.setVisible(true);
                }
            });

        } else {
            loginView.mostrarMensaje(mi.get("login.mensaje.recuperacion_cancelada"));
        }
    }


    private void crear() {
        boolean confirmado = usuarioCrearView.mostrarMensajePregunta(mi.get("usuario.mensaje.crear.pregunta"));
        if (confirmado) {
            String username = usuarioCrearView.getTxtUsername().getText();
            String contrasenia = usuarioCrearView.getTxtContrasenia().getText();

            if (usuarioDAO.buscarPorUsername(username) != null) {
                usuarioCrearView.mostrarMensaje(mi.get("usuario.mensaje.crear.existe"));
                return;
            }

            Usuario usuario1 = new Usuario(username, contrasenia, Rol.USUARIO);
            usuarioDAO.crear(usuario1);
            usuarioCrearView.mostrarMensaje(mi.get("usuario.mensaje.creado"));
            usuarioCrearView.limpiarCampos();
        } else {
            usuarioCrearView.mostrarMensaje(mi.get("usuario.mensaje.crear.cancelado"));
        }
    }
    private void eliminar(){
        boolean confirmado = usuarioEliminarView.mostrarMensajePregunta(mi.get("usuario.mensaje.eliminar.pregunta"));
        if (confirmado) {
            String username = usuarioEliminarView.getTxtUsername().getText();
            Usuario usuario1 = usuarioDAO.buscarPorUsername(username);
            usuarioDAO.eliminar(username);
            usuarioEliminarView.mostrarMensaje(mi.get("usuario.mensaje.eliminado"));
            usuarioEliminarView.limpiarCampos();
        }else{
            usuarioEliminarView.mostrarMensaje(mi.get("usuario.mensaje.crear.cancelado"));
        }
    }
    private void buscarEliminar(){
        String username = usuarioEliminarView.getTxtUsername().getText();
        Usuario usuario1 = usuarioDAO.buscarPorUsername(username);
        if(usuario1 != null){
            usuarioEliminarView.getTxtContrasenia().setText(usuario1.getContrasenia());
        }else{
            usuarioEliminarView.mostrarMensaje(mi.get("usuario.mensaje.no.encontrado"));
        }
    }
    private void buscarModificar(){
        String username = usuarioModificarView.getTxtName().getText();
        Usuario usuario1 = usuarioDAO.buscarPorUsername(username);
        if(usuario1 != null){
            usuarioModificarView.getTxtUsername().setText(usuario1.getUsername());
            usuarioModificarView.getTxtContrasenia().setText(usuario1.getContrasenia());
        }else{
            usuarioModificarView.mostrarMensaje(mi.get("usuario.mensaje.no.encontrado"));
        }
    }
    private void editar(){
        boolean confirmado = usuarioModificarView.mostrarMensajePregunta(mi.get("usuario.mensaje.editar.pregunta"));
        if (confirmado) {
            String username = usuarioModificarView.getTxtName().getText();
            Usuario usuario1 = usuarioDAO.buscarPorUsername(username);
            if(usuario1 != null){
                usuario1.setUsername(usuarioModificarView.getTxtUsername().getText());
                usuario1.setContrasenia(usuarioModificarView.getTxtContrasenia().getText());
                usuarioDAO.actualizar(usuario1);
                usuarioModificarView.mostrarMensaje(mi.get("usuario.mensaje.modificado"));
                usuarioModificarView.limpiarCampos();
            }else{
                usuarioModificarView.mostrarMensaje(mi.get("usuario.mensaje.no.encontrado"));
            }
        }
    }
    private void buscarUsario(){
        String username = usuarioListarView.getTxtBuscar().getText();
        Usuario usuarioEncontrado = usuarioDAO.buscarPorUsername(username);
        if (usuarioEncontrado != null) {
            usuarioListarView.cargarDatos(List.of(usuarioEncontrado));
        } else {
            usuarioListarView.cargarDatos(new ArrayList<>());
        }
    }
    private void listar(){
        List<Usuario> usuarios = usuarioDAO.listarTodos();
        usuarioListarView.cargarDatos(usuarios);
    }

    public void actualizarIdiomaEnVistas() {
        usuarioCrearView.cambiarIdioma();
        usuarioEliminarView.cambiarIdioma();
        usuarioModificarView.cambiarIdioma();
        usuarioListarView.cambiarIdioma();
    }
}
