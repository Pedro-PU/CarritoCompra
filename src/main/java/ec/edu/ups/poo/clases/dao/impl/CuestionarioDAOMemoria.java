package ec.edu.ups.poo.clases.dao.impl;

import ec.edu.ups.poo.clases.dao.CuestionarioDAO;
import ec.edu.ups.poo.clases.modelo.Cuestionario;
import ec.edu.ups.poo.clases.modelo.Respuesta;


import java.util.List;
import java.util.ArrayList;

public class CuestionarioDAOMemoria implements CuestionarioDAO {

    private List<Cuestionario> cuestionarios;
    public CuestionarioDAOMemoria() {
        this.cuestionarios = new ArrayList<>();
        Cuestionario cuestionarioAdmin = new Cuestionario("admin");
        //cuestionarioAdmin.aplicarIdioma(mi);
        List<Respuesta> preguntas = cuestionarioAdmin.preguntasPorDefecto();

        preguntas.get(0).setRespuesta("Negro");
        preguntas.get(1).setRespuesta("Kobu");
        preguntas.get(2).setRespuesta("Churrasco");

        cuestionarioAdmin.agregarRespuesta(preguntas.get(0));
        cuestionarioAdmin.agregarRespuesta(preguntas.get(1));
        cuestionarioAdmin.agregarRespuesta(preguntas.get(2));

        guardar(cuestionarioAdmin);
    }

    @Override
    public void guardar(Cuestionario cuestionario) {
        cuestionarios.add(cuestionario);
    }

    @Override
    public Cuestionario buscarPorUsername(String username) {
        for (Cuestionario cuestionario : cuestionarios) {
            if (cuestionario.getUsername().equalsIgnoreCase(username)) {
                return cuestionario;
            }
        }
        return null;
    }
}
