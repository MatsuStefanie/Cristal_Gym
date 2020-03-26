package visualizacoes;

import entidades.Biotipo;

import java.util.List;

public class BiotipoVisualizador {

    public void mostrarBiotipo(List<Biotipo> biotipos) {
        for (Biotipo biotipo : biotipos) {
            System.out.println("Id: " + biotipo.getId() + " Peso: " + biotipo.getPeso() + " Altura: " + biotipo.getAltura() + " Id do Cliente: " + biotipo.getIdCliente());
        }
    }
}
