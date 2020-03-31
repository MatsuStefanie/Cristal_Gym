package dao;

import dao.config.Conexao;
import entidades.Biotipo;
import entidades.Cliente;
import entidades.Email;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ClienteDAO {
    private Conexao conexao;
    private BiotipoDAO biotipoDAO;
    private EmailDAO emailDAO;

    public ClienteDAO(Conexao conexao, EmailDAO emailDAO) {
        this.conexao = conexao;
        this.emailDAO = emailDAO;
    }

    public ClienteDAO() {
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
            String query = getQueryBuscarCliente();

            Statement estado = this.iniciarConexao();
            ResultSet resultSet = estado.executeQuery(query);
            return this.construirClientes(resultSet);
        } catch (SQLException ex) {
            System.out.println("Deu ruim enquando estava convertendo clientes");
            throw new RuntimeException(ex.getMessage());
        }
    }

    public Integer buscarUltimoIdCliente() {
        try {
            return this.buscarUltimoIdCliente(this.conexao.abrir().createStatement());
        } catch (SQLException ex) {
            System.out.println("Nada encontrado");
            throw new RuntimeException(ex.getMessage());
        }
    }

    public Integer buscarUltimoIdCliente(Statement estado) {
        try {
            String query = buscarUltimo();
            ResultSet resultSet = estado.executeQuery(query);
            resultSet.next();
            Integer id = resultSet.getInt("id");
            return id;
        } catch (SQLException e) {
            System.out.println("Nada encontrado");
            throw new RuntimeException(e.getMessage());
        }
    }

    public Cliente buscarClienteEspecifico(Integer idCliente) {
        try {
            return buscarClienteEspecifico(idCliente, this.conexao.abrir().createStatement());
        }catch (SQLException ex){
            System.out.println("Nada encontrado");
            throw new RuntimeException(ex.getMessage());
        }
    }

    public Cliente buscarClienteEspecifico(Integer idCliente, Statement estado){
        try {
            Cliente cliente = new Cliente();
            String query = buscarCliente(idCliente);
            ResultSet resultSet = estado.executeQuery(query);
            resultSet.next();
            cliente.setId(resultSet.getInt("id"));
            cliente.setNome(resultSet.getString("nome"));
            cliente.setCpf(resultSet.getString("cpf"));
            cliente.setDataNascimento(resultSet.getDate("nascimento", Calendar.getInstance()).toLocalDate());
            return cliente;
        } catch (SQLException e) {
            System.out.println("Nada encontrado");
            throw new RuntimeException(e.getMessage());
        }
    }

    public String buscarCliente(Integer idCliente){
        return "Select * from cliente where cliente.id ="+idCliente;
    }

    private String buscarUltimo() {
        return "SELECT cliente.id FROM cliente ORDER BY id DESC LIMIT 1;";
    }

    private String getQueryBuscarCliente() {
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
                LocalDate dataNascimento = resultSet.getDate("nascimento", Calendar.getInstance()).toLocalDate();
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

    public void inserir(Cliente novoCliente) {

        try {
            PreparedStatement query = conexao.abrir().prepareStatement("insert into cliente values (default,?,?,?);");

            query.setString(1, novoCliente.getNome());
            query.setString(2, novoCliente.getCpf());
            query.setDate(3, java.sql.Date.valueOf(novoCliente.getDataNascimento()));

            query.executeUpdate();

            Integer id = buscarUltimoIdCliente(conexao.abrir().createStatement());
            novoCliente.setId(id);
            /*
            // inserir biotipo
            b.setIdCliente(id);
            biotipoDAO.novoBiotipo(b);

            // inserir email
            e.setIdCliente(id);
            emailDAO.novoEmail(e);
            */

        } catch (SQLException e) {
            System.out.println("Dev tivemos problemas em conexão ao MySql");
            e.printStackTrace();
        }
    }

    public void atualizarCliente(Cliente cliente) {
        try {
            PreparedStatement query = conexao.abrir().prepareStatement("update cliente " +
                    "set nome = ? , cpf = ? , nascimento = ? " +
                    "where id = ? ");
            query.setString(1, cliente.getNome());
            query.setString(2, cliente.getCpf());
            query.setDate(3, java.sql.Date.valueOf(cliente.getDataNascimento()));
            query.setInt(4, cliente.getId());
            query.executeUpdate();
        } catch (SQLException e) {
            System.out.println("DEV tivemos problemas no update");
            e.printStackTrace();
        }
    }

    public void excluirTudoDe(Integer idCliente){
        try{
           PreparedStatement query = conexao.abrir().prepareStatement("DELETE FROM cliente " +
                    "where id = ? ");
            query.setInt(1,idCliente);
            query.executeUpdate();
        }catch (SQLException ex){
            ex.printStackTrace();
        }
    }
}