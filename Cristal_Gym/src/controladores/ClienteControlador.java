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

    public void criar(Cliente novoCliente) {
        clienteDAO.inserir(novoCliente);
    }
    public List<Cliente> buscarTudoDeClientes(){
        return clienteDAO.buscarClientes();
    }

    public void atualizar(Cliente atualizarCliente){
        clienteDAO.atualizarCliente(atualizarCliente);
    }

    public Integer trazerId(){
        return clienteDAO.buscarUltimoIdCliente();
    }
}
