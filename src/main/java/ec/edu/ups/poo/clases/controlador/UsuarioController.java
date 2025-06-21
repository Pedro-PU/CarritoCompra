package ec.edu.ups.poo.clases.controlador;
import ec.edu.ups.poo.clases.dao.UsuarioDAO;
import ec.edu.ups.poo.clases.modelo.Rol;
import ec.edu.ups.poo.clases.modelo.Usuario;
import ec.edu.ups.poo.clases.vista.LoginView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UsuarioController {
    private Usuario usuario;
    private final UsuarioDAO usuarioDAO;
    private final LoginView loginView;

    public UsuarioController(UsuarioDAO usuarioDAO, LoginView loginView) {
        this.usuarioDAO = usuarioDAO;
        this.loginView = loginView;
        this.usuario = null;
        configurarEventos();
    }
    private void configurarEventos(){
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

    private void registrar(){
        boolean confirmado = loginView.mostrarMensajePregunta("¿Desea crear el usuario?");
        if (confirmado) {
            String username = loginView.getTxtUsername().getText();
            String contrasenia = loginView.getTxtContrasenia().getText();
            Usuario usuario1 = new Usuario(username, contrasenia, Rol.USUARIO);
            usuarioDAO.crear(usuario1);
            loginView.mostrarMensaje("Usuario creado");
        }else{
            loginView.mostrarMensaje("Creación cancelada");
        }

    }
}
