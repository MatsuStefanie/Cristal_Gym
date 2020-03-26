package controladores;

import dao.EmailDAO;
import entidades.Email;

import java.util.List;

public class EmailControlador {

    private EmailDAO emailDAO;

    public EmailControlador(EmailDAO emailDAO) {
        this.emailDAO = emailDAO;
    }

    public List<Email> buscarTodosEmails(){
        return emailDAO.buscarEmail();
    }
}