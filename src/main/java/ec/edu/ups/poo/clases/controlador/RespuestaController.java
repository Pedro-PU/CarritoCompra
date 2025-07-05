package ec.edu.ups.poo.clases.controlador;

import ec.edu.ups.poo.clases.dao.PreguntaDAO;
import ec.edu.ups.poo.clases.dao.UsuarioDAO;
import ec.edu.ups.poo.clases.modelo.Pregunta;
import ec.edu.ups.poo.clases.modelo.Respuesta;
import ec.edu.ups.poo.clases.modelo.Rol;
import ec.edu.ups.poo.clases.modelo.Usuario;
import ec.edu.ups.poo.clases.util.MensajeInternacionalizacionHandler;
import ec.edu.ups.poo.clases.vista.cuestionario.CuestionarioRecuperarView;
import ec.edu.ups.poo.clases.vista.cuestionario.CuestionarioView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.*;
import java.util.stream.Collectors;

public class RespuestaController {

    private CuestionarioView cuestionarioView;
    private final CuestionarioRecuperarView recuperarView;
    private final UsuarioDAO usuarioDAO;
    private final PreguntaDAO preguntaDAO;
    private final MensajeInternacionalizacionHandler mi;

    private Usuario usuario;
    private boolean usuarioYaRegistrado;
    private List<Respuesta> preguntasAleatorias;
    private List<Respuesta> respuestasCorrectas;
    private final int MAX_INTENTOS = 3;
    private int intentosFallidos = 0;
    private Respuesta preguntaActual;
    private final UsuarioController usuarioController;

    public RespuestaController(CuestionarioView vista, UsuarioDAO usuarioDAO, PreguntaDAO preguntaDAO,
                               MensajeInternacionalizacionHandler mi, UsuarioController usuarioController) {
        this.mi = mi;
        this.usuarioDAO = usuarioDAO;
        this.preguntaDAO = preguntaDAO;
        this.cuestionarioView = vista;
        this.recuperarView = null;
        this.usuario = new Usuario();
        this.usuarioYaRegistrado = false;
        this.usuarioController = usuarioController;

        cargarComboPreguntas();
        configurarEventosCuestionario();
    }

    public RespuestaController(CuestionarioView vista, UsuarioDAO usuarioDAO, Usuario usuario,
                               PreguntaDAO preguntaDAO, MensajeInternacionalizacionHandler mi,
                               boolean usuarioYaRegistrado, UsuarioController usuarioController) {
        this.mi = mi;
        this.usuarioDAO = usuarioDAO;
        this.preguntaDAO = preguntaDAO;
        this.usuario = usuario;
        this.usuarioYaRegistrado = usuarioYaRegistrado;
        this.cuestionarioView = vista;
        this.recuperarView = null;
        this.respuestasCorrectas = new ArrayList<>();
        this.usuarioController = usuarioController;

        setearCamposVista(usuario);
        cargarComboPreguntas();
        configurarEventosCuestionario();
    }

    public RespuestaController(CuestionarioRecuperarView recuperarView,
                               PreguntaDAO preguntaDAO,
                               Usuario usuario,
                               MensajeInternacionalizacionHandler mi,
                               UsuarioDAO usuarioDAO, UsuarioController usuarioController) {
        this.mi = mi;
        this.usuarioDAO = usuarioDAO;
        this.preguntaDAO = preguntaDAO;
        this.recuperarView = recuperarView;
        this.usuario = usuario;
        this.respuestasCorrectas = new ArrayList<>();
        this.usuarioController = usuarioController;

        if (usuario == null || usuario.getRespuestas().isEmpty()) {
            recuperarView.mostrarMensaje(mi.get("cuestionario.recuperar.noPreguntas"));
            recuperarView.dispose();
            return;
        }
        configurarEventosRecuperar(usuario.getContrasenia());
        mostrarPreguntaAleatoria();
    }


    private void configurarEventosCuestionario() {
        cuestionarioView.getCbxPreguntas().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                preguntasCuestionario();
            }
        });
        cuestionarioView.getBtnGuardar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guardar();
            }
        });

        cuestionarioView.getBtnFinalizar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                finalizar();
            }
        });

        cuestionarioView.getBtnIniciarCuestionario().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                iniciarCuestionario();
            }
        });
        cuestionarioView.getCbxIdiomas().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cambiarIdiomaCuestionario();
            }
        });
        cuestionarioView.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                if (usuarioController != null) {
                    usuarioController.getLoginView().actualizarTextos();
                    usuarioController.getLoginView().setVisible(true);
                }
            }
        });
    }

    private void configurarEventosRecuperar(String contrasenia) {
        recuperarView.getBtnFinalizar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                procesarRespuesta(contrasenia);
                mostrarPreguntaAleatoria();
            }
        });
        recuperarView.getCbxIdiomas().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cambiarIdiomaCuestionarioRecuperar();
            }
        });
        recuperarView.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                if (usuarioController != null) {
                    usuarioController.getLoginView().actualizarTextos();
                    usuarioController.getLoginView().setVisible(true);
                }
            }
        });
    }
    private void cambiarIdiomaCuestionario() {
        String[] clavesIdiomas = {"es", "en", "fr"};
        String[] paisesIdiomas = {"EC", "US", "FR"};
        int index = cuestionarioView.getCbxIdiomas().getSelectedIndex();

        if (index >= 0 && index < 3) {
            mi.setLenguaje(clavesIdiomas[index], paisesIdiomas[index]);
            cuestionarioView.actualizarTextos();
        }
    }

    private void cambiarIdiomaCuestionarioRecuperar() {
        String[] clavesIdiomas = {"es", "en", "fr"};
        String[] paisesIdiomas = {"EC", "US", "FR"};
        int index = recuperarView.getCbxIdiomas().getSelectedIndex();

        if (index >= 0 && index < 3) {
            mi.setLenguaje(clavesIdiomas[index], paisesIdiomas[index]);
            recuperarView.actualizarTextos();
            recuperarView.getLblPregunta().setText(preguntaActual.getPregunta().getEnunciadoPregunta(mi));
        }
    }

    private void mostrarPreguntaAleatoria() {
        List<Respuesta> disponibles = usuario.getRespuestas().stream()
                .filter(r -> !respuestasCorrectas.contains(r))
                .collect(Collectors.toList());

        if (disponibles.isEmpty()) {
            recuperarView.mostrarMensaje(mi.get("cuestionario.recuperar.sin_mas_preguntas"));
            recuperarView.dispose();
            return;
        }

        Collections.shuffle(disponibles);
        preguntaActual = disponibles.get(0);

        recuperarView.getLblPregunta().setText(preguntaActual.getPregunta().getEnunciadoPregunta(mi));
        recuperarView.getTxtRespuesta().setText("");
    }

    private void procesarRespuesta(String contrasenia) {
        String respuestaUsuario = recuperarView.getTxtRespuesta().getText().trim();

        if (respuestaUsuario.isEmpty()) {
            recuperarView.mostrarMensaje(mi.get("cuestionario.recuperar.respuestaVacia"));
            return;
        }

        if (respuestaUsuario.equalsIgnoreCase(preguntaActual.getRespuesta())) {
            recuperarView.mostrarMensaje(mi.get("cuestionario.recuperar.correcta"));
            recuperarView.mostrarMensaje(String.format(mi.get("cuestionario.recuperar.recuperada"), contrasenia));

            boolean deseaCambiar = recuperarView.mostrarMensajePregunta(mi.get("cuestionario.recuperar.pregunta.cambiar"));

            if (deseaCambiar) {
                String nuevaContrasenia = recuperarView.ingreso(mi.get("cuestionario.recuperar.pregunta.ingrese"));
                if (nuevaContrasenia != null) {
                    usuario.setContrasenia(nuevaContrasenia);
                    usuarioDAO.actualizar(usuario);
                    recuperarView.mostrarMensaje(mi.get("cuestionario.recuperar.cambiada.ok"));
                } else {
                    recuperarView.mostrarMensaje(mi.get("cuestionario.recuperar.cambiada.cancelada"));
                }
            }

            recuperarView.dispose();
        } else {
            intentosFallidos++;
            recuperarView.mostrarMensaje(mi.get("cuestionario.recuperar.incorrecta"));

            if (intentosFallidos >= MAX_INTENTOS) {
                recuperarView.mostrarMensaje(mi.get("cuestionario.recuperar.intentos_agotados"));
                recuperarView.dispose();
            } else {
                mostrarPreguntaAleatoria();
            }
        }
    }

    private void preguntasCuestionario() {
        int index = cuestionarioView.getCbxPreguntas().getSelectedIndex();
        if (index >= 0 && index < preguntasAleatorias.size()) {
            Respuesta r = preguntasAleatorias.get(index);
            cuestionarioView.getLblPregunta().setText(r.getPregunta().getEnunciadoPregunta(mi));

            for (Respuesta resp : usuario.getRespuestas()) {
                if (resp.getPregunta().getId() == r.getPregunta().getId()) {
                    cuestionarioView.getTxtRespuesta().setText(resp.getRespuesta());
                    return;
                }
            }
            cuestionarioView.getTxtRespuesta().setText("");
        }
    }

    private void guardar() {
        int index = cuestionarioView.getCbxPreguntas().getSelectedIndex();
        if (index < 0) return;

        String texto = cuestionarioView.getTxtRespuesta().getText().trim();
        if (texto.isEmpty()) {
            cuestionarioView.mostrarMensaje(mi.get("cuestionario.guardar.vacia"));
            return;
        }

        Respuesta seleccionada = preguntasAleatorias.get(index);
        seleccionada.setRespuesta(texto);

        boolean encontrada = false;
        for (Respuesta r : usuario.getRespuestas()) {
            if (r.getPregunta().getId() == seleccionada.getPregunta().getId()) {
                r.setRespuesta(texto);
                encontrada = true;
                break;
            }
        }

        if (!encontrada) {
            usuario.getRespuestas().add(seleccionada);
        }

        cuestionarioView.mostrarMensaje(mi.get("cuestionario.guardar.ok"));
    }

    private void finalizar() {
        if (usuario.getRespuestas().size() < 3) {
            cuestionarioView.mostrarMensaje(mi.get("cuestionario.finalizar.minimo"));
            return;
        }

        if (!usuarioYaRegistrado) {
            usuarioDAO.crear(usuario);
        } else {
            usuarioDAO.actualizar(usuario);
        }

        cuestionarioView.mostrarMensaje(mi.get("cuestionario.finalizar.ok"));
        cuestionarioView.dispose();
    }

    private void iniciarCuestionario() {
        // Validar campos del usuario
        String username = cuestionarioView.getTxtUsername().getText().trim();
        String contrasenia = cuestionarioView.getTxtContrasenia().getText().trim();
        String nombre = cuestionarioView.getTxtNombre().getText().trim();
        String celular = cuestionarioView.getTxtCelular().getText().trim();
        String correo = cuestionarioView.getTxtCorreo().getText().trim();
        int dia = (int) cuestionarioView.getSpnDia().getValue();
        int mes = (int) cuestionarioView.getSpnMes().getValue();
        int anio = (int) cuestionarioView.getSpnAnio().getValue();

        if (username.isEmpty() || contrasenia.isEmpty() || nombre.isEmpty() || celular.isEmpty() || correo.isEmpty()) {
            cuestionarioView.mostrarMensaje(mi.get("login.mensaje.error_campos_vacios"));
            return;
        }

        if (usuarioDAO.buscarPorUsername(username) != null) {
            cuestionarioView.mostrarMensaje(mi.get("login.mensaje.error_usuario_existente"));
            return;
        }

        if (!celular.matches("\\d{7,15}")) {
            cuestionarioView.mostrarMensaje(mi.get("cuestionario.validacion.celular"));
            return;
        }

        if (!correo.matches("^[\\w\\.-]+@[\\w\\.-]+\\.[a-zA-Z]{2,}$")) {
            cuestionarioView.mostrarMensaje(mi.get("cuestionario.validacion.correo"));
            return;
        }

        GregorianCalendar fecha = new GregorianCalendar(anio, mes - 1, dia);
        usuario = new Usuario(username,contrasenia, Rol.USUARIO,nombre,celular, fecha  ,correo);
        usuario.setRespuestas(new ArrayList<>());

        // Bloquear campos
        cuestionarioView.getTxtUsername().setEnabled(false);
        cuestionarioView.getTxtContrasenia().setEnabled(false);
        cuestionarioView.getTxtNombre().setEnabled(false);
        cuestionarioView.getTxtCelular().setEnabled(false);
        cuestionarioView.getTxtCorreo().setEnabled(false);
        cuestionarioView.getSpnDia().setEnabled(false);
        cuestionarioView.getSpnMes().setEnabled(false);
        cuestionarioView.getSpnAnio().setEnabled(false);
        cuestionarioView.getBtnIniciarCuestionario().setEnabled(false);

        // Mostrar preguntas
        cargarComboPreguntas();
        cuestionarioView.habilitarPreguntas(true);
    }

    private void cargarComboPreguntas() {
        if (preguntasAleatorias != null && !preguntasAleatorias.isEmpty()) return;

        List<Pregunta> todas = preguntaDAO.listarTodas();
        Collections.shuffle(todas);

        preguntasAleatorias = new ArrayList<>();
        for (int i = 0; i < Math.min(10, todas.size()); i++) {
            Pregunta p = todas.get(i);
            preguntasAleatorias.add(new Respuesta(p));
        }

        cuestionarioView.getCbxPreguntas().removeAllItems();
        for (Respuesta r : preguntasAleatorias) {
            cuestionarioView.getCbxPreguntas().addItem(r.getPregunta().getEnunciadoPregunta(mi));
        }
    }


    private void setearCamposVista(Usuario usuario) {
        cuestionarioView.getTxtUsername().setText(usuario.getUsername());
        cuestionarioView.getTxtContrasenia().setText(usuario.getContrasenia());
        cuestionarioView.getTxtNombre().setText(usuario.getNombre());
        cuestionarioView.getTxtCelular().setText(usuario.getCelular());
        cuestionarioView.getTxtCorreo().setText(usuario.getEmail());

        GregorianCalendar fecha = usuario.getFecha();
        cuestionarioView.getSpnDia().setValue(fecha.get(Calendar.DAY_OF_MONTH));
        cuestionarioView.getSpnMes().setValue(fecha.get(Calendar.MONTH) + 1);
        cuestionarioView.getSpnAnio().setValue(fecha.get(Calendar.YEAR));

        cuestionarioView.getTxtUsername().setEnabled(false);
        cuestionarioView.getTxtContrasenia().setEnabled(false);
        cuestionarioView.getTxtNombre().setEnabled(false);
        cuestionarioView.getTxtCelular().setEnabled(false);
        cuestionarioView.getTxtCorreo().setEnabled(false);
        cuestionarioView.getSpnDia().setEnabled(false);
        cuestionarioView.getSpnMes().setEnabled(false);
        cuestionarioView.getSpnAnio().setEnabled(false);

        cuestionarioView.getBtnIniciarCuestionario().setEnabled(false);
        cuestionarioView.habilitarPreguntas(true);
    }
}
