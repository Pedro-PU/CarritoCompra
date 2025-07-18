package ec.edu.ups.poo.clases.controlador;
import ec.edu.ups.poo.clases.dao.PreguntaDAO;
import ec.edu.ups.poo.clases.dao.UsuarioDAO;
import ec.edu.ups.poo.clases.modelo.*;
import ec.edu.ups.poo.clases.util.FormateadorUtils;
import ec.edu.ups.poo.clases.util.MensajeInternacionalizacionHandler;
import ec.edu.ups.poo.clases.vista.cuestionario.CuestionarioRecuperarView;
import ec.edu.ups.poo.clases.vista.cuestionario.CuestionarioView;
import ec.edu.ups.poo.clases.vista.usuario.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.*;

/**
 * Controlador principal para la gestión de usuarios. Maneja las operaciones de autenticación,
 * registro, recuperación de cuenta y CRUD completo de usuarios. Coordina las interacciones
 * entre las vistas de usuario y el DAO correspondiente, incluyendo validaciones y cambios de idioma.
 */
public class UsuarioController {
    private Usuario usuario;
    private final UsuarioDAO usuarioDAO;
    private LoginView loginView;
    private UsuarioCrearView usuarioCrearView;
    private UsuarioEliminarView usuarioEliminarView;
    private UsuarioModificarView usuarioModificarView;
    private UsuarioListarView usuarioListarView;
    private PreguntaDAO preguntaDAO;
    private final MensajeInternacionalizacionHandler mi;

    /**
     * Constructor para el controlador de login/registro. Inicializa los componentes
     * necesarios para la autenticación y registro de usuarios.
     *
     * @param usuarioDAO DAO para operaciones con usuarios.
     * @param loginView Vista de inicio de sesión.
     * @param preguntaDAO DAO para operaciones con preguntas de seguridad.
     * @param mi Handler de internacionalización de mensajes.
     */
    public UsuarioController(UsuarioDAO usuarioDAO, LoginView loginView, PreguntaDAO preguntaDAO,
                             MensajeInternacionalizacionHandler mi) {
        this.usuarioDAO = usuarioDAO;
        this.loginView = loginView;
        this.usuario = null;
        this.preguntaDAO = preguntaDAO;
        this.mi = mi;
        configurarEventosLogin();
    }

    /**
     * Constructor para el controlador de operaciones CRUD de usuarios. Inicializa
     * las vistas de gestión de usuarios y configura sus eventos.
     *
     * @param usuarioDAO DAO para operaciones con usuarios.
     * @param usuarioCrearView Vista para crear usuarios.
     * @param usuarioEliminarView Vista para eliminar usuarios.
     * @param usuarioModificarView Vista para modificar usuarios.
     * @param usuarioListarView Vista para listar usuarios.
     * @param mi Handler de internacionalización de mensajes.
     * @param usuarioLogueado Usuario actualmente autenticado.
     */
    public UsuarioController (UsuarioDAO usuarioDAO, UsuarioCrearView usuarioCrearView, UsuarioEliminarView usuarioEliminarView,
                              UsuarioModificarView usuarioModificarView, UsuarioListarView usuarioListarView, MensajeInternacionalizacionHandler mi,
                              Usuario usuarioLogueado) {
        this.usuarioDAO = usuarioDAO;
        this.usuarioCrearView = usuarioCrearView;
        this.usuarioEliminarView = usuarioEliminarView;
        this.usuarioModificarView = usuarioModificarView;
        this.usuarioListarView = usuarioListarView;
        this.usuario = usuarioLogueado;
        this.mi = mi;
        configurarEventosUsuario();
        configurarVistaModificar();
    }

    /**
     * Configura los eventos para los botones de la vista de login/registro.
     * Incluye autenticación, registro, recuperación de cuenta y cambio de idioma.
     */
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

    /**
     * Configura los eventos para los botones de las vistas de gestión de usuarios.
     * Incluye operaciones CRUD y búsquedas.
     */
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

    /**
     * Cambia el idioma del sistema en la vista de login según la selección del usuario.
     * Actualiza todos los textos de la interfaz.
     */
    private void cambiarIdioma() {
        String[] clavesIdiomas = {"es", "en", "fr"};
        String[] paisesIdiomas = {"EC", "US", "FR"};
        int index = loginView.getCbxIdiomas().getSelectedIndex();

        if (index >= 0 && index < 3) {
            mi.setLenguaje(clavesIdiomas[index], paisesIdiomas[index]);
            loginView.actualizarTextos();
        }
    }

    /**
     * Cierra la aplicación desde la ventana de login.
     */
    private void salir(){
        loginView.dispose();
        System.exit(0);
    }

    /**
     * Autentica un usuario verificando sus credenciales. Si el usuario no tiene
     * preguntas de seguridad completadas, lo redirige a completarlas.
     */
    private void autenticar() {
        String username = loginView.getTxtUsername().getText().trim();
        String contrasenia = loginView.getTxtContrasenia().getText().trim();

        usuario = usuarioDAO.autenticar(username, contrasenia);

        if (usuario == null) {
            loginView.mostrarMensaje(mi.get("login.mensaje.error_autenticacion"));
        } else {
            if (usuario.getRespuestas().size() < 3) {
                loginView.mostrarMensaje(mi.get("login.mensaje.incompleto"));

                CuestionarioView cuestionarioView = new CuestionarioView(mi);
                RespuestaController controller = new RespuestaController(cuestionarioView, usuarioDAO, usuario,preguntaDAO, mi, true, this);
                cuestionarioView.setVisible(true);
                loginView.setVisible(false);

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

    /**
     * Inicia el proceso de recuperación de cuenta mediante preguntas de seguridad.
     * No disponible para usuarios administradores.
     */
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

            if (usuario.getRespuestas().isEmpty()) {
                loginView.mostrarMensaje(mi.get("login.mensaje.sin_preguntas"));
                return;
            }

            CuestionarioRecuperarView recuperarView = new CuestionarioRecuperarView(mi);
            RespuestaController controller = new RespuestaController(
                    recuperarView, preguntaDAO, usuario, mi, usuarioDAO, this);

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

    /**
     * Inicia el proceso de registro de un nuevo usuario mostrando el cuestionario
     * de preguntas de seguridad.
     */
    private void registrar() {
        boolean confirmado = loginView.mostrarMensajePregunta(mi.get("login.mensaje.pregunta_registro"));
        if (confirmado) {
            CuestionarioView cuestionarioView = new CuestionarioView(mi);
            RespuestaController controller = new RespuestaController(
                    cuestionarioView, usuarioDAO, preguntaDAO, mi, this);
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
            loginView.mostrarMensaje(mi.get("login.mensaje.creacion_cancelada"));
        }
    }

    /**
     * Crea un nuevo usuario después de validar todos los campos del formulario.
     * Realiza validaciones de formato para cédula, contraseña, celular y correo.
     */
    private void crear() {
        boolean confirmado = usuarioCrearView.mostrarMensajePregunta(mi.get("usuario.mensaje.crear.pregunta"));
        if (!confirmado) {
            usuarioCrearView.mostrarMensaje(mi.get("usuario.mensaje.crear.cancelado"));
            return;
        }

        String username = usuarioCrearView.getTxtUsername().getText().trim();
        String contrasenia = usuarioCrearView.getTxtContrasenia().getText().trim();
        String nombre = usuarioCrearView.getTxtNombre().getText().trim();
        String celular = usuarioCrearView.getTxtCelular().getText().trim();
        String correo = usuarioCrearView.getTxtCorreo().getText().trim();
        int dia = (int) usuarioCrearView.getSpnDia().getValue();
        int mes = (int) usuarioCrearView.getSpnMes().getValue();
        int anio = (int) usuarioCrearView.getSpnAnio().getValue();

        if (username.isEmpty() || contrasenia.isEmpty() || nombre.isEmpty() || celular.isEmpty() || correo.isEmpty()) {
            usuarioCrearView.mostrarMensaje(mi.get("cuestionario.finalizar.error_datos_vacios"));
            return;
        }

        if (usuarioDAO.buscarPorUsername(username) != null) {
            usuarioCrearView.mostrarMensaje(mi.get("usuario.mensaje.crear.existe"));
            return;
        }

        GregorianCalendar fechaNacimiento = new GregorianCalendar(anio, mes - 1, dia); // mes - 1 porque enero = 0

        try{
            Usuario usuario1 = new Usuario(username, contrasenia, Rol.USUARIO, nombre, celular, fechaNacimiento, correo);
            usuario1.setUsername(username);
            usuario1.setContrasenia(contrasenia);
            usuario1.setCelular(celular);
            usuario1.setEmail(correo);
            usuarioDAO.crear(usuario1);
            usuarioCrearView.mostrarMensaje(mi.get("usuario.mensaje.creado"));
            usuarioCrearView.limpiarCampos();
        }catch (Cedula e){
            System.out.println(e.getMessage());
            usuarioCrearView.mostrarMensaje(mi.get("usuario.error.cedula"));
        }catch (Contrasenia e){
            System.out.println(e.getMessage());
            usuarioCrearView.mostrarMensaje(mi.get("usuario.error.contrasenia"));
        }catch (Celular e){
            System.out.println(e.getMessage());
            usuarioCrearView.mostrarMensaje(mi.get("usuario.validacion.celular"));
        }catch (Email e){
            System.out.println(e.getMessage());
            usuarioCrearView.mostrarMensaje(mi.get("usuario.validacion.correo"));
        }
    }

    /**
     * Elimina un usuario existente después de confirmar la acción.
     */
    private void eliminar() {
        boolean confirmado = usuarioEliminarView.mostrarMensajePregunta(mi.get("usuario.mensaje.eliminar.pregunta"));
        if (!confirmado) {
            usuarioEliminarView.mostrarMensaje(mi.get("usuario.mensaje.crear.cancelado"));
            return;
        }

        String username = usuarioEliminarView.getTxtUsername().getText().trim();

        if (username.isEmpty()) {
            usuarioEliminarView.mostrarMensaje(mi.get("usuario.validacion.username.vacio"));
            return;
        }

        Usuario usuario = usuarioDAO.buscarPorUsername(username);

        if (usuario == null) {
            usuarioEliminarView.mostrarMensaje(mi.get("usuario.validacion.username.noexiste"));
            return;
        }

        usuarioDAO.eliminar(username);
        usuarioEliminarView.mostrarMensaje(mi.get("usuario.mensaje.eliminado"));
        usuarioEliminarView.limpiarCampos();
    }

    /**
     * Busca un usuario para eliminación y muestra sus datos en el formulario.
     */
    private void buscarEliminar() {
        String username = usuarioEliminarView.getTxtUsername().getText().trim();

        if (username.isEmpty()) {
            usuarioEliminarView.mostrarMensaje(mi.get("usuario.validacion.username.buscar.vacio"));
            return;
        }

        Usuario usuario = usuarioDAO.buscarPorUsername(username);

        if (usuario == null) {
            usuarioEliminarView.mostrarMensaje(mi.get("usuario.mensaje.no.encontrado"));
            usuarioEliminarView.limpiarCampos();
            return;
        }

        usuarioEliminarView.getTxtContrasenia().setText(usuario.getContrasenia());
        usuarioEliminarView.getTxtNombre().setText(usuario.getNombre());
        usuarioEliminarView.getTxtCelular().setText(usuario.getCelular());
        usuarioEliminarView.getTxtCorreo().setText(usuario.getEmail());

        GregorianCalendar fecha = usuario.getFecha();
        usuarioEliminarView.getSpnDia().setValue(fecha.get(Calendar.DAY_OF_MONTH));
        usuarioEliminarView.getSpnMes().setValue(fecha.get(Calendar.MONTH) + 1);
        usuarioEliminarView.getSpnAnio().setValue(fecha.get(Calendar.YEAR));
    }

    /**
     * Busca un usuario para modificación. Los usuarios normales solo pueden buscar sus propios datos.
     */
    private void buscarModificar() {
        String username = usuarioModificarView.getTxtName().getText().trim();
        if (usuario.getRol() == Rol.USUARIO) {
            username = usuario.getUsername();
            usuarioModificarView.getTxtName().setText(username); // actualiza campo con su propio username
        }
        Usuario usuario1 = usuarioDAO.buscarPorUsername(username);
        if (usuario1 != null) {
            usuarioModificarView.getTxtUsername().setText(usuario1.getUsername());
            usuarioModificarView.getTxtContrasenia().setText(usuario1.getContrasenia());
            usuarioModificarView.getTxtNombre().setText(usuario1.getNombre());
            usuarioModificarView.getTxtCelular().setText(usuario1.getCelular());
            usuarioModificarView.getTxtCorreo().setText(usuario1.getEmail());

            GregorianCalendar fecha = usuario1.getFecha();
            usuarioModificarView.getSpnDia().setValue(fecha.get(Calendar.DAY_OF_MONTH));
            usuarioModificarView.getSpnMes().setValue(fecha.get(Calendar.MONTH) + 1);
            usuarioModificarView.getSpnAnio().setValue(fecha.get(Calendar.YEAR));

            usuarioModificarView.habilitarCampos(true);
        } else {
            usuarioModificarView.mostrarMensaje(mi.get("usuario.mensaje.no.encontrado"));
            usuarioModificarView.habilitarCampos(false);
        }
    }

    /**
     * Actualiza los datos de un usuario existente después de validaciones.
     */
    private void editar() {
        boolean confirmado = usuarioModificarView.mostrarMensajePregunta(mi.get("usuario.mensaje.editar.pregunta"));
        if (!confirmado) return;

        String usernameOriginal = usuarioModificarView.getTxtName().getText().trim();
        Usuario usuario1 = usuarioDAO.buscarPorUsername(usernameOriginal);

        if (usuario1 == null) {
            usuarioModificarView.mostrarMensaje(mi.get("usuario.mensaje.no.encontrado"));
            return;
        }

        String usernameNuevo = usuarioModificarView.getTxtUsername().getText().trim();
        String contrasenia = usuarioModificarView.getTxtContrasenia().getText().trim();
        String nombre = usuarioModificarView.getTxtNombre().getText().trim();
        String celular = usuarioModificarView.getTxtCelular().getText().trim();
        String correo = usuarioModificarView.getTxtCorreo().getText().trim();
        int dia = (int) usuarioModificarView.getSpnDia().getValue();
        int mes = (int) usuarioModificarView.getSpnMes().getValue();
        int anio = (int) usuarioModificarView.getSpnAnio().getValue();

        if (usernameNuevo.isEmpty() || contrasenia.isEmpty() || nombre.isEmpty() || celular.isEmpty() || correo.isEmpty()) {
            usuarioModificarView.mostrarMensaje(mi.get("cuestionario.finalizar.error_datos_vacios"));
            return;
        }

        if (!usernameNuevo.equals(usernameOriginal) && usuarioDAO.buscarPorUsername(usernameNuevo) != null) {
            usuarioModificarView.mostrarMensaje(mi.get("usuario.mensaje.crear.existe"));
            return;
        }
        try{
            usuario1.setUsername(usernameNuevo);
            usuario1.setContrasenia(contrasenia);
            usuario1.setNombre(nombre);
            usuario1.setCelular(celular);
            usuario1.setEmail(correo);
            usuario1.setFecha(new GregorianCalendar(anio, mes - 1, dia));

            usuarioDAO.actualizar(usuario1);
            usuarioModificarView.mostrarMensaje(mi.get("usuario.mensaje.modificado"));
            if (usuario.getRol() == Rol.ADMINISTRADOR) {
                usuarioModificarView.limpiarCampos();
                usuarioModificarView.habilitarCampos(false);
            }
        }catch (Cedula e){
            System.out.println(e.getMessage());
            usuarioModificarView.mostrarMensaje(mi.get("usuario.error.cedula"));
        }catch (Contrasenia e){
            System.out.println(e.getMessage());
            usuarioModificarView.mostrarMensaje(mi.get("usuario.error.contrasenia"));
        }catch (Celular e){
            System.out.println(e.getMessage());
            usuarioModificarView.mostrarMensaje(mi.get("usuario.validacion.celular"));
        }catch (Email e){
            System.out.println(e.getMessage());
            usuarioModificarView.mostrarMensaje(mi.get("usuario.validacion.correo"));
        }
    }

    /**
     * Configura la vista de modificación según el rol del usuario.
     * Los usuarios normales solo pueden ver/modificar sus propios datos.
     */
    private void configurarVistaModificar() {
        if (usuario.getRol() == Rol.USUARIO) {
            usuarioModificarView.getTxtName().setEnabled(false);
            usuarioModificarView.getBtnBuscar().setEnabled(false);
            usuarioModificarView.getTxtUsername().setEnabled(false);
            usuarioModificarView.getBtnBuscar().setEnabled(false);
            buscarModificar();
        }
    }

    /**
     * Busca un usuario específico por username y lo muestra en la tabla.
     */
    private void buscarUsario(){
        String username = usuarioListarView.getTxtBuscar().getText();
        Usuario usuarioEncontrado = usuarioDAO.buscarPorUsername(username);
        if (usuarioEncontrado != null) {
            usuarioListarView.cargarDatos(List.of(usuarioEncontrado));
        } else {
            usuarioListarView.cargarDatos(new ArrayList<>());
        }
    }

    /**
     * Carga todos los usuarios registrados en la tabla.
     */
    private void listar(){
        List<Usuario> usuarios = usuarioDAO.listarTodos();
        usuarioListarView.cargarDatos(usuarios);
    }

    /**
     * Actualiza el formato de las fechas en la tabla de usuarios según el locale.
     * @param locale Locale para formatear las fechas.
     */
    private void refrescarTablaListaUsuarios(Locale locale) {
        DefaultTableModel modelo = (DefaultTableModel) usuarioListarView.getTblUsuarios().getModel();
        int rowCount = modelo.getRowCount();

        for (int i = 0; i < rowCount; i++) {
            String username = modelo.getValueAt(i, 0).toString();
            Usuario usuario = usuarioDAO.buscarPorUsername(username);
            if (usuario != null) {
                String nuevaFecha = FormateadorUtils.formatearFecha(usuario.getFecha().getTime(), locale);
                modelo.setValueAt(nuevaFecha, i, 5);
            }
        }
    }

    /**
     * Actualiza los textos de todas las vistas de usuario al idioma seleccionado.
     */
    public void actualizarIdiomaEnVistas() {
        usuarioCrearView.cambiarIdioma();
        usuarioEliminarView.cambiarIdioma();
        usuarioModificarView.cambiarIdioma();
        usuarioListarView.cambiarIdioma();
        refrescarTablaListaUsuarios(mi.getLocale());
    }
    // Getters y Setters

    /**
     * Obtiene la vista de login asociada al controlador.
     * @return La vista de login.
     */
    public LoginView getLoginView() {
        return loginView;
    }

    /**
     * Establece la vista de login.
     * @param loginView La vista de login a establecer.
     */
    public void setLoginView(LoginView loginView) {
        this.loginView = loginView;
    }

    /**
     * Obtiene el usuario actualmente autenticado.
     * @return El usuario autenticado.
     */
    public Usuario getUsuarioAutenticado() {
        return usuario;
    }

    /**
     * Establece el usuario autenticado.
     * @param usuario El usuario autenticado a establecer.
     */
    public void setUsuarioAutenticado(Usuario usuario) {
        this.usuario = usuario;
    }
}
