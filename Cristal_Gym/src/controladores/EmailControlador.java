package controladores;

import dao.EmailDAO;
import entidades.Cliente;
import entidades.Email;

import java.util.List;

public class EmailControlador {

    private EmailDAO emailDAO;

    public EmailControlador(EmailDAO emailDAO) {
        this.emailDAO = emailDAO;
    }

    public void criar(Email novoEmail){
        emailDAO.novoEmail(novoEmail);
    }

    public List<Email> buscarTodosEmailsDeClienteEspecifico(Integer idCliente){
        return emailDAO.buscarEmailDeClienteEspecifico(idCliente);
    }

    public void atualizar(Email atualizarEmail){
        emailDAO.atualizarEmail(atualizarEmail);
    }
    public void excluir(Email email){
        emailDAO.deletarEmail(email);
    }
    public List<Email> buscarTodosEmails(){
        return emailDAO.buscarEmail();
    }
}