package ec.edu.ups.poo.clases.controlador;
import ec.edu.ups.poo.clases.dao.UsuarioDAO;
import ec.edu.ups.poo.clases.modelo.Rol;
import ec.edu.ups.poo.clases.modelo.Usuario;
import ec.edu.ups.poo.clases.vista.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

    public UsuarioController(UsuarioDAO usuarioDAO, LoginView loginView) {
        this.usuarioDAO = usuarioDAO;
        this.loginView = loginView;
        this.usuario = null;
        configurarEventosLogin();
    }

    public UsuarioController (UsuarioDAO usuarioDAO, UsuarioCrearView usuarioCrearView, UsuarioEliminarView usuarioEliminarView,
                              UsuarioModificarView usuarioModificarView, UsuarioListarView usuarioListarView) {
        this.usuarioDAO = usuarioDAO;
        this.usuarioCrearView = usuarioCrearView;
        this.usuarioEliminarView = usuarioEliminarView;
        this.usuarioModificarView = usuarioModificarView;
        this.usuarioListarView = usuarioListarView;
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
    private void autenticar() {
        String username = loginView.getTxtUsername().getText();
        String contrasenia = loginView.getTxtContrasenia().getText();

        usuario = usuarioDAO.autenticar(username, contrasenia);
        if(usuario == null){
            loginView.mostrarMensaje("Usuario o contraseña incorrectos");

        }else{
            loginView.dispose();
        }
    }

    public Usuario getUsuarioAutenticado() {
        return usuario;
    }

    public void setUsuarioAutenticado(Usuario usuario) {
        this.usuario = usuario;
    }

    private void registrar() {
        boolean confirmado = loginView.mostrarMensajePregunta("¿Desea crear el usuario?");
        if (confirmado) {
            String username = loginView.getTxtUsername().getText();
            String contrasenia = loginView.getTxtContrasenia().getText();

            if (usuarioDAO.buscarPorUsername(username) != null) {
                loginView.mostrarMensaje("Error: El nombre de usuario ya está en uso");
                return;
            }

            Usuario usuario1 = new Usuario(username, contrasenia, Rol.USUARIO);
            usuarioDAO.crear(usuario1);
            loginView.mostrarMensaje("Usuario creado");
        } else {
            loginView.mostrarMensaje("Creación cancelada");
        }
    }


    private void crear() {
        boolean confirmado = usuarioCrearView.mostrarMensajePregunta("¿Desea crear el usuario?");
        if (confirmado) {
            String username = usuarioCrearView.getTxtUsername().getText();
            String contrasenia = usuarioCrearView.getTxtContrasenia().getText();

            if (usuarioDAO.buscarPorUsername(username) != null) {
                usuarioCrearView.mostrarMensaje("Error: El nombre de usuario ya existe");
                return;
            }

            Usuario usuario1 = new Usuario(username, contrasenia, Rol.USUARIO);
            usuarioDAO.crear(usuario1);
            usuarioCrearView.mostrarMensaje("Usuario creado");
            usuarioCrearView.limpiarCampos();
        } else {
            usuarioCrearView.mostrarMensaje("Creación cancelada");
        }
    }
    private void eliminar(){
        boolean confirmado = usuarioEliminarView.mostrarMensajePregunta("¿Desea eliminar el usuario?");
        if (confirmado) {
            String username = usuarioEliminarView.getTxtUsername().getText();
            Usuario usuario1 = usuarioDAO.buscarPorUsername(username);
            usuarioDAO.eliminar(username);
            usuarioEliminarView.mostrarMensaje("Usuario eliminado");
            usuarioEliminarView.limpiarCampos();
        }else{
            usuarioEliminarView.mostrarMensaje("Creación cancelada");
        }
    }
    private void buscarEliminar(){
        String username = usuarioEliminarView.getTxtUsername().getText();
        Usuario usuario1 = usuarioDAO.buscarPorUsername(username);
        if(usuario1 != null){
            usuarioEliminarView.getTxtContrasenia().setText(usuario1.getContrasenia());
        }else{
            usuarioEliminarView.mostrarMensaje("Usuario no encontrado");
        }
    }
    private void buscarModificar(){
        String username = usuarioModificarView.getTxtName().getText();
        Usuario usuario1 = usuarioDAO.buscarPorUsername(username);
        if(usuario1 != null){
            usuarioModificarView.getTxtUsername().setText(usuario1.getUsername());
            usuarioModificarView.getTxtContrasenia().setText(usuario1.getContrasenia());
        }else{
            usuarioModificarView.mostrarMensaje("Usuario no encontrado");
        }
    }
    private void editar(){
        boolean confirmado = usuarioModificarView.mostrarMensajePregunta("¿Desea editar el usuario?");
        if (confirmado) {
            String username = usuarioModificarView.getTxtName().getText();
            Usuario usuario1 = usuarioDAO.buscarPorUsername(username);
            if(usuario1 != null){
                usuario1.setUsername(usuarioModificarView.getTxtUsername().getText());
                usuario1.setContrasenia(usuarioModificarView.getTxtContrasenia().getText());
                usuarioDAO.actualizar(usuario1);
                usuarioModificarView.mostrarMensaje("Usuario modificado");
                usuarioModificarView.limpiarCampos();
            }else{
                usuarioModificarView.mostrarMensaje("Usuario no encontrado");
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
}
