package visualizacoes;

import entidades.Email;

import java.util.List;

public class EmailVisualizador {
    public void mostrarEmails(List<Email> emails) {

        for (Email email : emails) {
            System.out.println("Id: " + email.getId() + " Email: " + email.getEmail() + " É principal: " + email.getPrincipal() + " Id do Cliente: " + email.getIdCliente());
        }
    }
}