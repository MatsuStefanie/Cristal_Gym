package controladores;

import dao.BiotipoDAO;
import entidades.Biotipo;

import java.util.List;

public class BiotipoControlador {
    private BiotipoDAO biotipoDAO;

    public BiotipoControlador(BiotipoDAO biotipoDAO) {
        this.biotipoDAO = biotipoDAO;
    }
    public List<Biotipo> buscarTodosBiotipos(){
        return biotipoDAO.buscarBiotipo();
    }
}