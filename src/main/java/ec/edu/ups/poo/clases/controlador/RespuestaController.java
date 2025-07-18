package ec.edu.ups.poo.clases.controlador;

import ec.edu.ups.poo.clases.dao.PreguntaDAO;
import ec.edu.ups.poo.clases.dao.UsuarioDAO;
import ec.edu.ups.poo.clases.modelo.*;
import ec.edu.ups.poo.clases.util.*;
import ec.edu.ups.poo.clases.vista.cuestionario.CuestionarioRecuperarView;
import ec.edu.ups.poo.clases.vista.cuestionario.CuestionarioView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Controlador para la gestión de preguntas y respuestas de seguridad.
 * Maneja el registro de preguntas de seguridad para nuevos usuarios, la actualización
 * de respuestas para usuarios existentes y el proceso de recuperación de contraseña
 * mediante preguntas de seguridad. Coordina las interacciones entre las vistas de
 * cuestionario y los DAOs correspondientes.
 */
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

    /**
     * Constructor para registro de nuevos usuarios. Inicializa el controlador para
     * que nuevos usuarios registren sus preguntas de seguridad.
     *
     * @param vista Vista del cuestionario.
     * @param usuarioDAO DAO para operaciones con usuarios.
     * @param preguntaDAO DAO para operaciones con preguntas.
     * @param mi Handler de internacionalización.
     * @param usuarioController Controlador principal de usuarios.
     */
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
        this.usuario.setRespuestas(new ArrayList<>());
        cargarComboPreguntas();
        configurarEventosCuestionario();

    }

    /**
     * Constructor para modificación de preguntas de seguridad. Permite a usuarios
     * existentes actualizar sus respuestas de seguridad.
     *
     * @param vista Vista del cuestionario.
     * @param usuarioDAO DAO para operaciones con usuarios.
     * @param usuario Usuario existente.
     * @param preguntaDAO DAO para operaciones con preguntas.
     * @param mi Handler de internacionalización.
     * @param usuarioYaRegistrado Indica si el usuario ya está registrado.
     * @param usuarioController Controlador principal de usuarios.
     */
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

    /**
     * Constructor para recuperación de contraseña. Gestiona el proceso de
     * verificación de identidad mediante preguntas de seguridad.
     *
     * @param recuperarView Vista de recuperación.
     * @param preguntaDAO DAO para operaciones con preguntas.
     * @param usuario Usuario que recupera su cuenta.
     * @param mi Handler de internacionalización.
     * @param usuarioDAO DAO para operaciones con usuarios.
     * @param usuarioController Controlador principal de usuarios.
     */
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

    /**
     * Configura los eventos para los componentes de la vista de cuestionario.
     * Incluye manejo de selección de preguntas, guardado de respuestas y finalización del proceso.
     */
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
                    // Actualiza sus textos en caso de que el idioma haya cambiado durante el cuestionario.
                    usuarioController.getLoginView().actualizarTextos();
                    usuarioController.getLoginView().setVisible(true);
                }
            }
        });
    }

    /**
     * Configura los eventos para los componentes de la vista de recuperación.
     * Maneja el proceso de verificación de respuestas y cambio de contraseña.
     *
     * @param contrasenia Contraseña actual del usuario (para mostrarla si la recuperación es exitosa).
     */
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
                    // Actualiza sus textos en caso de que el idioma haya cambiado durante el proceso de recuperación
                    usuarioController.getLoginView().actualizarTextos();
                    usuarioController.getLoginView().setVisible(true);
                }
            }
        });
    }

    /**
     * Cambia el idioma en la vista de cuestionario y actualiza los textos de las preguntas.
     */
    private void cambiarIdiomaCuestionario() {
        String[] clavesIdiomas = {"es", "en", "fr"};
        String[] paisesIdiomas = {"EC", "US", "FR"};
        int index = cuestionarioView.getCbxIdiomas().getSelectedIndex();

        if (index >= 0 && index < 3) {
            mi.setLenguaje(clavesIdiomas[index], paisesIdiomas[index]);
            cuestionarioView.actualizarTextos();
        }
        cuestionarioView.getCbxPreguntas().removeAllItems();
        for (Respuesta r : preguntasAleatorias) {
            cuestionarioView.getCbxPreguntas().addItem(r.getPregunta().getEnunciadoPregunta(mi));
        }
        preguntasCuestionario();
    }

    /**
     * Cambia el idioma en la vista de recuperación y actualiza la pregunta mostrada.
     */
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

    /**
     * Muestra una pregunta aleatoria de las disponibles para el proceso de recuperación.
     * Evita repetir preguntas ya respondidas correctamente.
     */
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

    /**
     * Procesa la respuesta ingresada durante la recuperación de contraseña.
     * Maneja los intentos fallidos y permite cambiar la contraseña si la respuesta es correcta.
     *
     * @param contrasenia Contraseña actual del usuario.
     */
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
                while (true) {
                    String nuevaContrasenia = recuperarView.ingreso(mi.get("cuestionario.recuperar.pregunta.ingrese"));
                    if (nuevaContrasenia == null) {
                        recuperarView.mostrarMensaje(mi.get("cuestionario.recuperar.cambiada.cancelada"));
                        break;
                    }
                    try {
                        usuario.setContrasenia(nuevaContrasenia);
                        usuarioDAO.actualizar(usuario);
                        recuperarView.mostrarMensaje(mi.get("cuestionario.recuperar.cambiada.ok"));
                        break;
                    } catch (Contrasenia e) {
                        recuperarView.mostrarMensaje(mi.get("usuario.error.contrasenia"));
                    }
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

    /**
     * Muestra la pregunta seleccionada y su respuesta (si existe) en la vista de cuestionario.
     */
    private void preguntasCuestionario() {
        int index = cuestionarioView.getCbxPreguntas().getSelectedIndex();
        if (index >= 0 && index < preguntasAleatorias.size()) {
            Respuesta r = preguntasAleatorias.get(index);
            cuestionarioView.getLblPregunta().setText(r.getPregunta().getEnunciadoPregunta(mi));

            if (usuario == null || usuario.getRespuestas() == null) {
                cuestionarioView.getTxtRespuesta().setText("");
                return;
            }

            for (Respuesta resp : usuario.getRespuestas()) {
                if (resp.getPregunta().getId() == r.getPregunta().getId()) {
                    cuestionarioView.getTxtRespuesta().setText(resp.getRespuesta());
                    return;
                }
            }
            cuestionarioView.getTxtRespuesta().setText("");
        }
    }

    /**
     * Guarda la respuesta ingresada para la pregunta seleccionada en el cuestionario.
     */
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

    /**
     * Finaliza el proceso de cuestionario, validando que se hayan respondido al menos 3 preguntas.
     * Guarda los cambios en la base de datos.
     */
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

    /**
     * Inicia el proceso de cuestionario validando los datos básicos del usuario
     * y habilitando la sección de preguntas de seguridad.
     */
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

        try{
            GregorianCalendar fecha = new GregorianCalendar(anio, mes - 1, dia);
            usuario = new Usuario(username,contrasenia, Rol.USUARIO,nombre,celular, fecha  ,correo);
            usuario.setUsername(username);
            usuario.setContrasenia(contrasenia);
            usuario.setCelular(celular);
            usuario.setEmail(correo);
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
        }catch (Cedula e){
            System.out.println(e.getMessage());
            cuestionarioView.mostrarMensaje(mi.get("usuario.error.cedula"));
        }catch (Contrasenia e){
            System.out.println(e.getMessage());
            cuestionarioView.mostrarMensaje(mi.get("usuario.error.contrasenia"));
        }catch (Celular e){
            System.out.println(e.getMessage());
            cuestionarioView.mostrarMensaje(mi.get("usuario.validacion.celular"));
        }catch (Email e){
            System.out.println(e.getMessage());
            cuestionarioView.mostrarMensaje(mi.get("usuario.validacion.correo"));
        }
    }

    /**
     * Carga preguntas aleatorias desde la base de datos para el cuestionario.
     */
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

    /**
     * Configura los campos de la vista con los datos de un usuario existente.
     *
     * @param usuario Usuario cuyos datos se mostrarán.
     */
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
