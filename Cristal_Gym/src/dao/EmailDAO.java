package dao;

import dao.config.Conexao;
import entidades.Cliente;
import entidades.Email;

import java.sql.PreparedStatement;
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

    public void novoEmail(Email email) {
        try {
            PreparedStatement query = conexao.abrir().prepareStatement("INSERT into email values (default, ?, ? , ?)");

            query.setString(1, email.getEmail());
            query.setBoolean(2, email.getIsPrincipal());
            query.setInt(3, email.getIdCliente());

            query.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Tivemos problemas na conexão MySQL");
            e.printStackTrace();
        }
    }

    private List<Email> visualizarEmail(ResultSet resultSet) throws SQLException {
        List<Email> emails = new ArrayList<>();

        while (resultSet.next()) {
            Integer id = resultSet.getInt("id");
            String e_mail = resultSet.getString("email");
            Boolean isPrincipal = resultSet.getBoolean("principal");
            Integer idCliente = resultSet.getInt("idCliente");

            Email email = new Email(id, e_mail, isPrincipal, idCliente);

            emails.add(email);
        }

        return emails;
    }

    public List<Email> buscarEmail() {
        try {
            String query = "Select * from email";
            Statement estado = this.iniciarConexao();
            ResultSet resultSet = estado.executeQuery(query);
            return this.visualizarEmail(resultSet);
        } catch (SQLException ex) {
            System.out.println("Problema enquanto convertia email");
            throw new RuntimeException(ex.getMessage());
        }
    }

    public List<Email> buscarEmailDeClienteEspecifico(Integer idCliente) {
        try {
            String query = "Select email.* from email inner join cliente c on email.idCliente = c.id where email.idCliente = ".concat(idCliente.toString());
            Statement estado = this.iniciarConexao();
            ResultSet resultSet = estado.executeQuery(query);
            return this.visualizarEmail(resultSet);
        } catch (SQLException ex) {
            System.out.println("Problema enquanto convertia email");
            throw new RuntimeException(ex.getMessage());
        }
    }

    public void atualizarEmail(Email atualizarEmail) {
        try {
            PreparedStatement query = conexao.abrir().prepareStatement("update email " +
                    "set email = ?,principal =?, idCliente=? where id = ?;");
            query.setString(1, atualizarEmail.getEmail());
            query.setBoolean(2, atualizarEmail.getIsPrincipal());
            query.setInt(3, atualizarEmail.getIdCliente());
            query.setInt(4, atualizarEmail.getId());
            query.executeUpdate();
        } catch (SQLException e) {
            System.out.println("DEV tivemos problemas no update");
            e.printStackTrace();
        }
    }

    public void deletarEmailDe(Integer idCliente){
        try{
            PreparedStatement query = conexao.abrir().prepareStatement("delete from email where email.idCliente = ?;");
            query.setInt(1,idCliente);
            query.executeUpdate();
        }catch (SQLException e){
            System.out.println("+----------------------------------Email não excluido----------------------------------+");
            e.printStackTrace();
        }
    }

    public void deletarEmail(Email email) {
        try {
            PreparedStatement query = conexao.abrir().prepareStatement
                    ("delete from email where email.idCliente = ? and email.id = ?;");
            query.setInt(1, email.getIdCliente());
            query.setInt(2, email.getId());
            query.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("DEV tivemos problemas ao excluir");
            ex.printStackTrace();
        }
    }
}