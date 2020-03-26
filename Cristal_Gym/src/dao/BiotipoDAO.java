package dao;

import dao.config.Conexao;
import entidades.Biotipo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class BiotipoDAO {

    private Conexao conexao;

    public BiotipoDAO(Conexao conexao) {
        this.conexao = conexao;
    }

    private Statement iniciarConexao() {
        try {
            return this.conexao.abrir().createStatement();
        } catch (SQLException e) {
            System.out.println("Busca de biotipo impossibilitada por falta de conexão com o banco");
            throw new RuntimeException("Conexão perdida");
        }
    }

    private List<Biotipo> construirBiotipo(ResultSet resultSet) throws SQLException {
        List<Biotipo> biotipos = new ArrayList<>();

        while (resultSet.next()){
            Integer id = resultSet.getInt("id");
            Float altura = resultSet.getFloat("altura");
            Float peso = resultSet.getFloat("peso");
            Integer idCliente = resultSet.getInt("idCliente");

            Biotipo biotipo = new Biotipo(id,altura,peso, idCliente);

            biotipos.add(biotipo);
        }

        return biotipos;
    }

    public List<Biotipo> buscarBiotipo() {
        try {
            String query = "Select * from biotipo";
            Statement estado = this.iniciarConexao();
            ResultSet resultSet =  estado.executeQuery(query);
            return this.construirBiotipo(resultSet);
        } catch (SQLException ex) {
            System.out.println("Problema enquanto convertia biotipo");
            throw new RuntimeException(ex.getMessage());
        }finally {
            conexao.fechar();
        }
    }
}