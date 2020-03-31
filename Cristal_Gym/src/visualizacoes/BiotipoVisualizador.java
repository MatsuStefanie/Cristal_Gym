package visualizacoes;

import entidades.Biotipo;

import java.util.List;
import java.util.Scanner;

public class BiotipoVisualizador {

    Scanner ler =  new Scanner(System.in);
    public void mostrarBiotipo(List<Biotipo> biotipos) {
        for (Biotipo biotipo : biotipos) {
            System.out.println("Id: " + biotipo.getId() + " Peso: " + biotipo.getPeso() + " Altura: " + biotipo.getAltura() + " Id do Cliente: " + biotipo.getIdCliente());
        }
    }

    public void inserirBiotipo(Biotipo biotipo){
        System.out.println("+--------------------------------------------------------------------------------------+");
        System.out.println("|                                  Inserindo o biotipo                                 |");
        System.out.println("+--------------------------------------------------------------------------------------+");
        System.out.println("|                               *** Qual o novo peso ***                               |");
        biotipo.setPeso(ler.nextFloat());
    }
    public void inserir(Integer idCliente){
        new Biotipo(idCliente);
    }
}
