package ec.edu.ups.poo.clases.dao;

import ec.edu.ups.poo.clases.modelo.Cuestionario;

public interface CuestionarioDAO {
    void guardar(Cuestionario cuestionario);
    Cuestionario buscarPorUsername(String username);
}
