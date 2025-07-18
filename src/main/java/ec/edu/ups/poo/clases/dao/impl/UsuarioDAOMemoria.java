package ec.edu.ups.poo.clases.dao.impl;

import ec.edu.ups.poo.clases.dao.PreguntaDAO;
import ec.edu.ups.poo.clases.dao.UsuarioDAO;
import ec.edu.ups.poo.clases.modelo.Pregunta;
import ec.edu.ups.poo.clases.modelo.Respuesta;
import ec.edu.ups.poo.clases.modelo.Rol;
import ec.edu.ups.poo.clases.modelo.Usuario;

import java.util.*;

/**
 * Implementación en memoria del DAO de usuarios.
 * Los datos se almacenan en una lista local durante la ejecución, ideal para entornos de pruebas o aplicaciones no persistentes.
 */
public class UsuarioDAOMemoria implements UsuarioDAO {

    private List<Usuario> usuarios;

    /**
     * Constructor que inicializa el DAO con usuarios predeterminados (admin y usuario de prueba).
     * También asocia preguntas de seguridad al administrador.
     * @param preguntaDAO DAO para obtener las preguntas asociadas a las respuestas.
     */
    public UsuarioDAOMemoria(PreguntaDAO preguntaDAO) {
        this.usuarios = new ArrayList<>();

        Usuario admin = new Usuario(
                "admin",
                "12345",
                Rol.ADMINISTRADOR,
                "Administrador General",
                "0999999999",
                new GregorianCalendar(1980, Calendar.JANUARY, 1),
                "admin@gmail.com"
        );

        Usuario user = new Usuario(
                "0301127569",
                "12345",
                Rol.USUARIO,
                "Usuario de Prueba",
                "0988888888",
                new GregorianCalendar(1995, Calendar.JUNE, 15),
                "user@gmail.com"
        );

        List<Pregunta> preguntas = preguntaDAO.listarTodas();
        for (int i = 0; i < 3 && i < preguntas.size(); i++) {
            Respuesta r = new Respuesta(preguntas.get(i));
            r.setRespuesta("respuesta" + (i + 1));
            admin.getRespuestas().add(r);
        }

        crear(admin);
        crear(user);
    }

    /**
     * Verifica las credenciales de un usuario.
     * @param username Nombre de usuario.
     * @param contrasenia Contraseña.
     * @return Usuario válido si coincide; null si no.
     */
    @Override
    public Usuario autenticar(String username, String contrasenia) {
        for (Usuario usuario : usuarios) {
            if (usuario.getUsername().equals(username) &&
                    usuario.getContrasenia().equals(contrasenia)) {
                return usuario;
            }
        }
        return null;
    }

    /**
     * Agrega un nuevo usuario a la lista.
     * @param usuario Usuario a registrar.
     */
    @Override
    public void crear(Usuario usuario) {
        usuarios.add(usuario);
    }

    /**
     * Busca un usuario por su nombre de usuario.
     * @param username Nombre de usuario.
     * @return Usuario encontrado o null.
     */
    @Override
    public Usuario buscarPorUsername(String username) {
        for (Usuario usuario : usuarios) {
            if (usuario.getUsername().equals(username)) {
                return usuario;
            }
        }
        return null;
    }

    /**
     * Actualiza los datos de un usuario existente.
     * @param usuario Usuario modificado.
     */
    @Override
    public void actualizar(Usuario usuario) {
        for (int i = 0; i < usuarios.size(); i++) {
            if (usuarios.get(i).getUsername().equals(usuario.getUsername())) {
                usuarios.set(i, usuario);
                break;
            }
        }
    }

    /**
     * Elimina un usuario de la lista según su nombre de usuario.
     * @param username Nombre de usuario a eliminar.
     */
    @Override
    public void eliminar(String username) {
        Iterator<Usuario> iterator = usuarios.iterator();
        while (iterator.hasNext()) {
            if (iterator.next().getUsername().equals(username)) {
                iterator.remove();
            }
        }
    }

    /**
     * Devuelve la lista completa de usuarios almacenados en memoria.
     * @return Lista de usuarios.
     */
    @Override
    public List<Usuario> listarTodos() {
        return usuarios;
    }

    /**
     * Filtra los usuarios por su rol (ADMINISTRADOR o USUARIO).
     * @param rol Rol a buscar.
     * @return Lista de usuarios con ese rol.
     */
    @Override
    public List<Usuario> listarPorRol(Rol rol) {
        List<Usuario> usuariosEncontrados = new ArrayList<>();
        for (Usuario usuario : usuarios) {
            if (usuario.getRol().equals(rol)) {
                usuariosEncontrados.add(usuario);
            }
        }
        return usuariosEncontrados;
    }
}

