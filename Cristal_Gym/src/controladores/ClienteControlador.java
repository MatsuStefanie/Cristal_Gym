package controladores;

import dao.BiotipoDAO;
import dao.ClienteDAO;
import dao.EmailDAO;
import entidades.Cliente;

import java.util.List;

public class ClienteControlador {

    private ClienteDAO clienteDAO;
    private EmailDAO emailDAO;
    private BiotipoDAO biotipoDAO;

    public ClienteControlador(ClienteDAO clienteDAO, EmailDAO emailDAO, BiotipoDAO biotipoDAO) {
        this.clienteDAO = clienteDAO;
        this.emailDAO = emailDAO;
        this.biotipoDAO = biotipoDAO;
    }

    public List<Cliente> buscarTodosClientes() {
        return clienteDAO.buscarClientes();
    }

    public void criar(Cliente novoCliente) {
        clienteDAO.inserir(novoCliente);
    }

    public List<Cliente> buscarTudoDeClientes() {
        return clienteDAO.buscarClientes();
    }

    public void atualizar(Cliente atualizarCliente) {
        clienteDAO.atualizarCliente(atualizarCliente);
    }

    public Integer trazerId() {
        return clienteDAO.buscarUltimoIdCliente();
    }

    public Cliente buscarEspecificoDesseId(Integer idCliente) {
        return clienteDAO.buscarClienteEspecifico(idCliente);
    }

    public void excluirTudoDe(Integer idCliente){
        emailDAO.deletarEmailDe(idCliente);
        biotipoDAO.excluirBiotipoDe(idCliente);
        clienteDAO.excluirTudoDe(idCliente);
    }
}
