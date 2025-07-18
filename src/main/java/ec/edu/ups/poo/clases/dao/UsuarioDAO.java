package ec.edu.ups.poo.clases.dao;
import ec.edu.ups.poo.clases.modelo.Rol;
import ec.edu.ups.poo.clases.modelo.Usuario;

import java.util.List;

/**
 * Interfaz DAO para gestionar operaciones de acceso a datos de usuarios.
 * Define métodos para autenticación, búsqueda, modificación y listado según criterios.
 */
public interface UsuarioDAO {

    /**
     * Verifica las credenciales de un usuario.
     * @param username Nombre de usuario o cédula.
     * @param contrasenia Contraseña ingresada.
     * @return Usuario autenticado si las credenciales son válidas; null en caso contrario.
     */
    Usuario autenticar(String username, String contrasenia);

    /**
     * Registra un nuevo usuario en el sistema.
     * @param usuario Objeto Usuario a almacenar.
     */
    void crear(Usuario usuario);

    /**
     * Busca un usuario por su nombre de usuario (cédula).
     * @param username Nombre de usuario.
     * @return Usuario correspondiente; null si no se encuentra.
     */
    Usuario buscarPorUsername(String username);

    /**
     * Actualiza los datos de un usuario existente.
     * @param usuario Usuario con información modificada.
     */
    void actualizar(Usuario usuario);

    /**
     * Elimina un usuario del sistema utilizando su nombre de usuario.
     * @param username Identificador del usuario a eliminar.
     */
    void eliminar(String username);

    /**
     * Lista todos los usuarios registrados.
     * @return Lista completa de usuarios.
     */
    List<Usuario> listarTodos();

    /**
     * Filtra los usuarios según su rol asignado (ADMINISTRADOR o USUARIO).
     * @param rol Tipo de rol.
     * @return Lista de usuarios con el rol indicado.
     */
    List<Usuario> listarPorRol(Rol rol);
}

