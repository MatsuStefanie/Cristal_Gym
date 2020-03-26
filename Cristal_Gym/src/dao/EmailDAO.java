package dao;

import dao.config.Conexao;
import entidades.Biotipo;
import entidades.Cliente;
import entidades.Email;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class EmailDAO {
    private Conexao conexao;

    public EmailDAO(Conexao conexao) {
        this.conexao = conexao;
    }

    private Statement iniciarConexao() {
        try {
            return this.conexao.abrir().createStatement();
        } catch (SQLException e) {
            System.out.println("Busca de email impossibilitada por falta de conexão com o banco");
            throw new RuntimeException("Conexão perdida");
        }
    }

    private List<Email> construirEmail(ResultSet resultSet) throws SQLException {
        List<Email> emails = new ArrayList<>();

        while (resultSet.next()){
            Integer id = resultSet.getInt("id");
            String e_mail = resultSet.getString("email");
            Boolean isPrincipal = resultSet.getBoolean("principal");
            Integer idCliente = resultSet.getInt("idCliente");

            Email email = new Email(id,e_mail,isPrincipal, idCliente);

            emails.add(email);
        }

        return emails;
    }

    public List<Email> buscarEmail() {
        try {
            String query = "Select * from email";
            Statement estado = this.iniciarConexao();
            ResultSet resultSet =  estado.executeQuery(query);
            return this.construirEmail(resultSet);
        } catch (SQLException ex) {
            System.out.println("Problema enquanto convertia email");
            throw new RuntimeException(ex.getMessage());
        }finally {
            conexao.fechar();
        }
    }
    public List<Email> buscarEmailDeClienteExpecifico(Cliente cliente) {
        try {
            String query = "Select email.* from email inner join cliente c on email.idCliente = c.id where idCliente = "+cliente.getId();
            Statement estado = this.iniciarConexao();
            ResultSet resultSet =  estado.executeQuery(query);
            return this.construirEmail(resultSet);
        } catch (SQLException ex) {
            System.out.println("Problema enquanto convertia email");
            throw new RuntimeException(ex.getMessage());
        }
    }
}