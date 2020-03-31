package controladores;

import dao.EmailDAO;
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

    public List<Email> buscarTodosEmails(){
        return emailDAO.buscarEmail();
    }
}