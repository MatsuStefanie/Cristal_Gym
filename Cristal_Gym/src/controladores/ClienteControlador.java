package controladores;

import dao.ClienteDAO;
import entidades.Cliente;

import java.util.List;

public class ClienteControlador {


    private ClienteDAO clienteDAO;

    public ClienteControlador(ClienteDAO clienteDAO) {
        this.clienteDAO = clienteDAO;
    }

    public List<Cliente> buscarTodosClientes() {
        return clienteDAO.buscarClientes();
    }
}
