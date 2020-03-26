package dao;

import dao.config.Conexao;
import entidades.Biotipo;
import entidades.Cliente;
import entidades.Email;
import utils.Container;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ClienteDAO {

    private Conexao conexao;


    EmailDAO emailDAO = Container.buscarDependencia(EmailDAO.class);
    //BiotipoDAO biotipoDAO = Container.buscarDependencia(BiotipoDAO.class);

    public ClienteDAO(Conexao conexao) {
        this.conexao = conexao;
    }

    private Statement iniciarConexao() {
        try {
            return this.conexao.abrir().createStatement();
        } catch (SQLException e) {
            System.out.println("Busca de clientes impossibilitada por falta de conexao com o banco");
            throw new RuntimeException("Conexao Perdida");
        }
    }

    public List<Cliente> buscarClientes() {

        try {

            String query = getQueryBuscarEstudantes();


            Statement estado = this.iniciarConexao();
            ResultSet resultSet = estado.executeQuery(query);
            return this.construirClientes(resultSet);

        } catch (SQLException ex) {
            System.out.println("Deu ruim enquando estava convertendo clientes");
            throw new RuntimeException(ex.getMessage());
        } finally {
            conexao.fechar();
        }
    }

    private String getQueryBuscarEstudantes() {
        return "select cliente.id cliente_id,\n" +
                "       nome,\n" +
                "       cpf,\n" +
                "       nascimento,\n" +
                "       b.id       biotipo_id,\n" +
                "       altura,\n" +
                "       peso,\n" +
                "       e.id       email_id,\n" +
                "       email,\n" +
                "       principal\n" +
                "from cliente\n" +
                "       inner join biotipo b on cliente.id = b.idCliente\n" +
                "       inner join email e on cliente.id = e.idCliente\n" +
                "order by cliente_id, principal desc;";
    }

    private List<Cliente> construirClientes(ResultSet resultSet) throws SQLException {

        List<Cliente> clientes = new ArrayList<>();


        while (resultSet.next()) {
            //ResultSet olhando o proximo e recebe id
            Integer id = resultSet.getInt("cliente_id");
            Cliente cliente;

            //Se esse id não estiver na lista faz um novo cliente
            if (!existeClienteLista(id, clientes)) {
                String nome = resultSet.getString("nome");
                String cpf = resultSet.getString("cpf");
                Date dataNascimento = resultSet.getDate("nascimento", Calendar.getInstance());
                cliente = new Cliente(id, nome, cpf, dataNascimento);
                clientes.add(cliente);
            } else {
                //Se não, pega esse mesmo cliente para utilizar nos proximos testes
                cliente = clientes
                        .stream()
                        .filter(cliente1 -> cliente1.getId().equals(id))
                        .findFirst()
                        .orElseGet(null);
            }

            //Dados biotipo
            Integer idBiotipo = resultSet.getInt("biotipo_id");
            //Se esse id de biotipo não estiver na lista, fazer um novo biotipo para cliente
            if (!existeBiotipo(idBiotipo, clientes)) {
                Float altura = resultSet.getFloat("altura");
                Float peso = resultSet.getFloat("peso");
                Integer idCliente = resultSet.getInt("cliente_id");

                Biotipo biotipo = new Biotipo(idBiotipo, altura, peso, idCliente);
                cliente.addBiotipo(biotipo);
            }

            //Dados Email
            Integer idEmail = resultSet.getInt("email_id");

            //Se esse id de email não estiver na lista, fazer um novo email para cliente
            if (!existeEmail(idEmail, clientes)) {
                String e_mail = resultSet.getString("email");
                Boolean isPrincipal = resultSet.getBoolean("principal");
                Integer idClienteEmail = resultSet.getInt("cliente_id");

                Email email = new Email(idEmail, e_mail, isPrincipal, idClienteEmail);
                cliente.addEmail(email);
            }
        }

        return clientes;
    }

    private boolean existeEmail(Integer idEmail, List<Cliente> clientes) {
        return clientes
                .stream()
                .flatMap(cliente -> cliente.getEmails().stream())
                .map(Email::getId)
                .anyMatch(integer -> integer.equals(idEmail));
    }

    private boolean existeBiotipo(Integer idBiotipo, List<Cliente> clientes) {
        return clientes
                .stream()
                .flatMap(cliente -> cliente.getBiotipos().stream())
                .map(Biotipo::getId)
                .anyMatch(integer -> integer.equals(idBiotipo));
    }

    private boolean existeClienteLista(Integer id, List<Cliente> clientes) {
        boolean existe = clientes
                .stream()
                .map(Cliente::getId)
                .anyMatch(idClinte -> idClinte.equals(id));

        ;

        return existe;
    }
}