package ec.edu.ups.poo.clases.dao.impl;

import ec.edu.ups.poo.clases.dao.PreguntaDAO;
import ec.edu.ups.poo.clases.dao.UsuarioDAO;
import ec.edu.ups.poo.clases.modelo.Pregunta;
import ec.edu.ups.poo.clases.modelo.Respuesta;
import ec.edu.ups.poo.clases.modelo.Rol;
import ec.edu.ups.poo.clases.modelo.Usuario;

import java.io.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementación del DAO de usuarios que persiste los datos en un archivo de texto plano.
 * Gestiona operaciones CRUD y recuperación de usuarios incluyendo sus respuestas asociadas.
 */
public class UsuarioDAOArchivo implements UsuarioDAO {

    private List<Usuario> usuarios;
    private File archivo;
    private PreguntaDAO preguntaDAO;

    /**
     * Constructor que inicializa el DAO con el archivo de usuarios y el DAO de preguntas.
     * Si el archivo existe, carga los usuarios desde disco. Si no, lo crea y carga usuarios por defecto.
     * @param archivo Archivo de persistencia.
     * @param preguntaDAO DAO para consultar preguntas vinculadas a respuestas.
     */
    public UsuarioDAOArchivo(File archivo, PreguntaDAO preguntaDAO) {
        this.archivo = archivo;
        this.usuarios = new ArrayList<>();
        this.preguntaDAO = preguntaDAO;

        if (archivo.exists()) {
            this.usuarios = cargarUsuarios();
        } else {
            guardarUsuarios();
            this.usuarios = cargarUsuarios();
        }

        // Carga usuarios predeterminados si no existen
        if (buscarPorUsername("admin") == null) {
            Usuario admin = new Usuario(
                    "admin", "12345", Rol.ADMINISTRADOR,
                    "Administrador General", "0999999999",
                    new GregorianCalendar(1980, Calendar.JANUARY, 1), "admin@gmail.com"
            );
            List<Pregunta> preguntas = preguntaDAO.listarTodas();
            for (int i = 0; i < 3 && i < preguntas.size(); i++) {
                Respuesta r = new Respuesta(preguntas.get(i));
                r.setRespuesta("respuesta" + (i + 1));
                admin.getRespuestas().add(r);
            }
            usuarios.add(admin);
        }

        if (buscarPorUsername("0301127569") == null) {
            Usuario user = new Usuario(
                    "0301127569", "12345", Rol.USUARIO,
                    "Usuario de Prueba", "0988888888",
                    new GregorianCalendar(1995, Calendar.JUNE, 15), "user@gmail.com"
            );
            usuarios.add(user);
        }

        guardarUsuarios();
    }

    /**
     * Autentica un usuario verificando su nombre de usuario y contraseña.
     * @param username Nombre de usuario.
     * @param contrasenia Contraseña ingresada.
     * @return Usuario válido si las credenciales coinciden, null si no.
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
     * Registra un nuevo usuario y actualiza el archivo.
     * @param usuario Usuario a persistir.
     */
    @Override
    public void crear(Usuario usuario) {
        usuarios.add(usuario);
        guardarUsuarios();
    }

    /**
     * Busca un usuario por su nombre de usuario.
     * @param username Clave única del usuario.
     * @return Usuario correspondiente; null si no existe.
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
     * @param usuario Usuario con nueva información.
     */
    @Override
    public void actualizar(Usuario usuario) {
        for (int i = 0; i < usuarios.size(); i++) {
            if (usuarios.get(i).getUsername().equals(usuario.getUsername())) {
                usuarios.set(i, usuario);
                guardarUsuarios();
                break;
            }
        }
    }

    /**
     * Elimina un usuario del sistema por su nombre de usuario.
     * @param username Clave del usuario a eliminar.
     */
    @Override
    public void eliminar(String username) {
        usuarios.removeIf(u -> u.getUsername().equals(username));
        guardarUsuarios();
    }

    /**
     * Lista todos los usuarios registrados.
     * @return Lista de objetos Usuario.
     */
    @Override
    public List<Usuario> listarTodos() {
        return usuarios;
    }

    /**
     * Filtra los usuarios por su rol asignado.
     * @param rol Rol buscado.
     * @return Lista de usuarios con dicho rol.
     */
    @Override
    public List<Usuario> listarPorRol(Rol rol) {
        return usuarios.stream()
                .filter(u -> u.getRol().equals(rol))
                .collect(Collectors.toList());
    }

    /**
     * Guarda la lista de usuarios en el archivo de texto.
     */
    private void guardarUsuarios() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(archivo))) {
            for (Usuario u : usuarios) {
                StringBuilder sb = new StringBuilder();
                sb.append(u.getUsername()).append(",");
                sb.append(u.getContrasenia()).append(",");
                sb.append(u.getRol().name()).append(",");
                sb.append(u.getNombre()).append(",");
                sb.append(u.getCelular()).append(",");

                GregorianCalendar f = u.getFecha();
                sb.append(f != null
                        ? f.get(Calendar.YEAR) + "-" + f.get(Calendar.MONTH) + "-" + f.get(Calendar.DAY_OF_MONTH)
                        : "0-0-0").append(",");

                sb.append(u.getEmail() != null ? u.getEmail() : "");

                if (u.getRespuestas() != null && !u.getRespuestas().isEmpty()) {
                    sb.append(",");
                    for (Respuesta r : u.getRespuestas()) {
                        sb.append(r.getPregunta().getId()).append("~").append(r.getRespuesta()).append("|");
                    }
                    sb.deleteCharAt(sb.length() - 1); // Elimina el último '|'
                }

                pw.println(sb);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Carga usuarios desde el archivo, reconstruyendo objetos y sus respuestas.
     * @return Lista de usuarios recuperados.
     */
    private List<Usuario> cargarUsuarios() {
        List<Usuario> lista = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                Usuario u = parsear(linea);
                if (u != null) lista.add(u);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lista;
    }

    /**
     * Parsea una línea de texto del archivo para construir un objeto Usuario.
     * @param linea Línea formateada en CSV con respuestas incluidas.
     * @return Usuario reconstruido; null si hay error.
     */
    private Usuario parsear(String linea) {
        try {
            String[] partes = linea.split(",", 8);
            if (partes.length < 7) return null;

            Usuario u = new Usuario();
            u.setUsername(partes[0]);
            u.setContrasenia(partes[1]);
            u.setRol(Rol.valueOf(partes[2]));
            u.setNombre(partes[3]);
            u.setCelular(partes[4]);

            String[] fPartes = partes[5].split("-");
            GregorianCalendar fecha = new GregorianCalendar(
                    Integer.parseInt(fPartes[0]),
                    Integer.parseInt(fPartes[1]),
                    Integer.parseInt(fPartes[2])
            );
            u.setFecha(fecha);
            u.setEmail(partes[6]);
            u.setRespuestas(new ArrayList<>());

            if (partes.length == 8 && !partes[7].isEmpty()) {
                String[] respPartes = partes[7].split("\\|");
                for (String respStr : respPartes) {
                    String[] rSplit = respStr.split("~");
                    if (rSplit.length == 2) {
                        int preguntaId = Integer.parseInt(rSplit[0]);
                        String respuestaTexto = rSplit[1];
                        Pregunta pregunta = preguntaDAO.buscarPorId(preguntaId);
                        if (pregunta != null) {
                            Respuesta r = new Respuesta(pregunta);
                            r.setRespuesta(respuestaTexto);
                            u.getRespuestas().add(r);
                        }
                    }
                }
            }

            return u;
        } catch (Exception e) {
            System.err.println("Error parseando usuario con respuestas: " + e.getMessage());
            return null;
        }
    }
}



