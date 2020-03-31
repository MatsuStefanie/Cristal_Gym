package visualizacoes;

import entidades.Biotipo;
import entidades.Cliente;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

public class ClienteVisualizador {

    DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

    public void mostrarClientes(List<Cliente> clientes) {

    }

    public void mostrarTudoDeCliente(List<Cliente> clientes) {
        for (Cliente cliente : clientes) {
            System.out.println("\nMatricula: " + cliente.getId() + " Nome:" + cliente.getNome() + " CPF:" + cliente.getCpf() + " Data Nasc.: " + dateFormat.format(cliente.getDataNascimento()));
            System.out.println("Emails:");
            cliente.getEmails().forEach(email -> {System.out.println(email.toString()); });
            System.out.println("Biotipo:");
            cliente.getBiotipos().forEach(biotipo -> {System.out.println(biotipo.toString()); });
        }
    }
}