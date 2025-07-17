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

public class UsuarioDAOArchivo implements UsuarioDAO {

    private List<Usuario> usuarios;
    private File archivo;
    private PreguntaDAO preguntaDAO;

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
        if (buscarPorUsername("admin") == null) {
            Usuario admin = new Usuario(
                    "admin",
                    "12345",
                    Rol.ADMINISTRADOR,
                    "Administrador General",
                    "0999999999",
                    new GregorianCalendar(1980, Calendar.JANUARY, 1),
                    "admin@gmail.com"
            );
            List<Pregunta> preguntas = preguntaDAO.listarTodas();
            for (int i = 0; i < 3 && i < preguntas.size(); i++) {
                Pregunta pregunta = preguntas.get(i);
                Respuesta respuesta = new Respuesta(pregunta);
                respuesta.setRespuesta("respuesta" + (i + 1));
                admin.getRespuestas().add(respuesta);
            }
            usuarios.add(admin);
        }
        if (buscarPorUsername("0301127569") == null) {
            Usuario user = new Usuario(
                    "0301127569",
                    "12345",
                    Rol.USUARIO,
                    "Usuario de Prueba",
                    "0988888888",
                    new GregorianCalendar(1995, Calendar.JUNE, 15),
                    "user@gmail.com"
            );
            usuarios.add(user);
        }
        guardarUsuarios();
    }

    @Override
    public Usuario autenticar(String username, String contrasenia) {
        for (Usuario usuario : usuarios) {
            if (usuario.getUsername().equals(username) && usuario.getContrasenia().equals(contrasenia)) {
                return usuario;
            }
        }
        return null;
    }

    @Override
    public void crear(Usuario usuario) {
        usuarios.add(usuario);
        guardarUsuarios();
    }

    @Override
    public Usuario buscarPorUsername(String username) {
        for (Usuario usuario : usuarios) {
            if (usuario.getUsername().equals(username)) {
                return usuario;
            }
        }
        return null;
    }

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

    @Override
    public void eliminar(String username) {
        usuarios.removeIf(u -> u.getUsername().equals(username));
        guardarUsuarios();
    }

    @Override
    public List<Usuario> listarTodos() {
        return usuarios;
    }

    @Override
    public List<Usuario> listarPorRol(Rol rol) {
        return usuarios.stream()
                .filter(u -> u.getRol().equals(rol))
                .collect(Collectors.toList());
    }

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
                sb.append(f != null ? f.get(Calendar.YEAR) + "-" + f.get(Calendar.MONTH) + "-" + f.get(Calendar.DAY_OF_MONTH) : "0-0-0").append(",");
                sb.append(u.getEmail() != null ? u.getEmail() : "");

                if (u.getRespuestas() != null && !u.getRespuestas().isEmpty()) {
                    sb.append(",");
                    for (Respuesta r : u.getRespuestas()) {
                        sb.append(r.getPregunta().getId()).append("~")
                                .append(r.getRespuesta()).append("|");
                    }
                    sb.deleteCharAt(sb.length() - 1); // Quitar último '|'
                }

                pw.println(sb.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

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

    private Usuario parsear(String linea) {
        try {
            String[] partes = linea.split(",", 8); // hasta 8 si hay respuestas

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
                            Respuesta respuesta = new Respuesta(pregunta);
                            respuesta.setRespuesta(respuestaTexto);
                            u.getRespuestas().add(respuesta);
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


