package controladores;

import dao.BiotipoDAO;
import entidades.Biotipo;
import entidades.Cliente;

import java.util.List;

public class BiotipoControlador {
    private BiotipoDAO biotipoDAO;

    public BiotipoControlador(BiotipoDAO biotipoDAO) {
        this.biotipoDAO = biotipoDAO;
    }

    public List<Biotipo> buscarTodosBiotipos(){
        return biotipoDAO.buscarBiotipo();
    }
    public void inserindoBiotipos(Biotipo biotipo){
        biotipoDAO.novoBiotipo(biotipo);
    }
}