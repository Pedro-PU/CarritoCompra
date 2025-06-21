package ec.edu.ups.poo.clases.dao;
import ec.edu.ups.poo.clases.modelo.Rol;
import ec.edu.ups.poo.clases.modelo.Usuario;

import java.util.List;

public interface UsuarioDAO {
    Usuario autenticar(String username, String contrasenia);

    void crear(Usuario usuario);

    Usuario buscarPorUsername(String username);

    void actualizar(Usuario usuario);

    void eliminar(String username);

    List<Usuario> listarTodos();

    List<Usuario> listarPorRol(Rol rol);
}
