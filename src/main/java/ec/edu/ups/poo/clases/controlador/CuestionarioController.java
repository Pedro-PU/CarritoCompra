package ec.edu.ups.poo.clases.controlador;

import ec.edu.ups.poo.clases.dao.CuestionarioDAO;
import ec.edu.ups.poo.clases.modelo.Cuestionario;
import ec.edu.ups.poo.clases.modelo.Respuesta;
import ec.edu.ups.poo.clases.util.MensajeInternacionalizacionHandler;
import ec.edu.ups.poo.clases.vista.cuestionario.CuestionarioRecuperarView;
import ec.edu.ups.poo.clases.vista.cuestionario.CuestionarioView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class CuestionarioController {

    private final CuestionarioView cuestionarioView;
    private final CuestionarioRecuperarView recuperarView;
    private final CuestionarioDAO cuestionarioDAO;
    private final Cuestionario cuestionario;
    private List<Respuesta> preguntasAleatorias;
    private List<Respuesta> respuestasCorrectas;
    private final MensajeInternacionalizacionHandler mi;


    public CuestionarioController(CuestionarioView vista, CuestionarioDAO dao, String username,
                                  MensajeInternacionalizacionHandler mi) {
        this.mi = mi;
        this.cuestionarioView = vista;
        this.cuestionarioDAO = dao;
        this.cuestionario = new Cuestionario(username);
        this.recuperarView = null;

        this.cuestionario.aplicarIdioma(mi);

        List<Respuesta> todasLasPreguntas = cuestionario.preguntasPorDefecto();
        preguntasAleatorias = new ArrayList<>();

        boolean[] usadas = new boolean[todasLasPreguntas.size()];
        int cantidadDeseada = 6;
        int cantidadActual = 0;
        Random random = new Random();

        while (cantidadActual < cantidadDeseada) {
            int indice = random.nextInt(todasLasPreguntas.size());
            if (!usadas[indice]) {
                preguntasAleatorias.add(todasLasPreguntas.get(indice));
                usadas[indice] = true;
                cantidadActual++;
            }
        }


        cargarComboPreguntas();
        configurarEventosCuestionario();
    }
    public CuestionarioController(CuestionarioRecuperarView recuperarView, CuestionarioDAO dao,
                                  String username, String contrasenia, MensajeInternacionalizacionHandler mi){
        this.mi = mi;
        this.cuestionarioDAO = dao;
        this.cuestionarioView = null;
        this.cuestionario = cuestionarioDAO.buscarPorUsername(username);
        this.recuperarView = recuperarView;
        this.respuestasCorrectas = new ArrayList<>();

        if (cuestionario == null) {
            recuperarView.mostrarMensaje(mi.get("cuestionario.recuperar.noPreguntas"));
            recuperarView.dispose();
            return;
        }

        this.preguntasAleatorias = cuestionario.getRespuestas();

        for (int i = 0; i < preguntasAleatorias.size(); i++) {
            String etiqueta = mi.get("cuestionario.pregunta");
            recuperarView.getCbxPreguntas().addItem(etiqueta + " " + (i + 1));
        }

        if (!preguntasAleatorias.isEmpty()) {
            recuperarView.getLblPregunta().setText(preguntasAleatorias.get(0).getEnunciado());
        }

        configurarEventosRecuperar(contrasenia);

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
    }

    private void configurarEventosRecuperar(String contrasenia){
        recuperarView.getCbxPreguntas().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                preguntasRecuperar();
            }
        });

        recuperarView.getBtnGuardar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guardarRespuestasRecuperar();
            }
        });

        recuperarView.getBtnFinalizar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                finalizarRecuperar(contrasenia);
            }
        });
    }

    private void preguntasRecuperar(){
        int index = recuperarView.getCbxPreguntas().getSelectedIndex();
        if (index >= 0 && index < preguntasAleatorias.size()) {
            Respuesta r = preguntasAleatorias.get(index);
            recuperarView.getLblPregunta().setText(r.getEnunciado());

            if (respuestasCorrectas.contains(r)) {
                recuperarView.getTxtRespuesta().setText(r.getRespuesta());
            } else {
                recuperarView.getTxtRespuesta().setText("");
            }
        }
    }

    private void guardarRespuestasRecuperar() {
        int index = recuperarView.getCbxPreguntas().getSelectedIndex();
        if (index < 0 || index >= preguntasAleatorias.size()) {
            recuperarView.mostrarMensaje(mi.get("cuestionario.recuperar.preguntaInvalida"));
            return;
        }

        Respuesta r = preguntasAleatorias.get(index);
        String respuestaUsuario = recuperarView.getTxtRespuesta().getText().trim();

        if (respuestaUsuario.isEmpty()) {
            recuperarView.mostrarMensaje(mi.get("cuestionario.recuperar.respuestaVacia"));
            return;
        }

        if (respuestaUsuario.equalsIgnoreCase(r.getRespuesta())) {
            if (!respuestasCorrectas.contains(r)) {
                respuestasCorrectas.add(r);
                recuperarView.mostrarMensaje(mi.get("cuestionario.recuperar.correcta"));
                r.setRespuesta(respuestaUsuario);
            } else {
                recuperarView.mostrarMensaje(mi.get("cuestionario.recuperar.yaRespondida"));
            }
        } else {
            recuperarView.mostrarMensaje(mi.get("cuestionario.recuperar.incorrecta"));
        }
    }


    private void finalizarRecuperar(String contrasenia) {
        if (respuestasCorrectas.size() >= 3) {
            recuperarView.mostrarMensaje(String.format(mi.get("cuestionario.recuperar.recuperada"), contrasenia));
            recuperarView.dispose();
        } else {
            recuperarView.mostrarMensaje(mi.get("cuestionario.recuperar.minimo"));
        }
    }


    private void preguntasCuestionario(){
        int index = cuestionarioView.getCbxPreguntas().getSelectedIndex();
        if (index >= 0) {
            Respuesta r = preguntasAleatorias.get(index);
            cuestionarioView.getLblPregunta().setText(r.getEnunciado());

            Respuesta respondido = cuestionario.buscarRespuestaPorId(r.getId());
            if (respondido != null) {
                cuestionarioView.getTxtRespuesta().setText(respondido.getRespuesta());
            } else {
                cuestionarioView.getTxtRespuesta().setText("");
            }
        }
    }

    private void guardar(){
        int index = cuestionarioView.getCbxPreguntas().getSelectedIndex();
        if (index < 0) return;

        String texto = cuestionarioView.getTxtRespuesta().getText().trim();
        if (texto.isEmpty()) {
            cuestionarioView.mostrarMensaje(mi.get("cuestionario.guardar.vacia"));
            return;
        }

        Respuesta seleccionada = preguntasAleatorias.get(index);
        seleccionada.setRespuesta(texto);

        if (cuestionario.buscarRespuestaPorId(seleccionada.getId()) == null) {
            cuestionario.agregarRespuesta(seleccionada);
        }

        cuestionarioView.mostrarMensaje(mi.get("cuestionario.guardar.ok"));
    }

    private void finalizar(){
        if (cuestionario.getRespuestas().size() < 3) {
            cuestionarioView.mostrarMensaje(mi.get("cuestionario.finalizar.minimo"));
            return;
        }

        cuestionarioDAO.guardar(cuestionario);
        cuestionarioView.mostrarMensaje(mi.get("cuestionario.finalizar.ok"));
        cuestionarioView.dispose();
    }

    private void cargarComboPreguntas() {
        for (int i = 0; i < preguntasAleatorias.size(); i++) {
            String etiqueta = mi.get("cuestionario.pregunta");
            cuestionarioView.getCbxPreguntas().addItem(etiqueta + " " + (i + 1));
        }

        if (!preguntasAleatorias.isEmpty()) {
            cuestionarioView.getLblPregunta().setText(preguntasAleatorias.get(0).getEnunciado());
        }
    }
}
