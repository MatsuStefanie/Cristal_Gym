package dao.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {

    private static final String MY_DRIVER =  "com.mysql.cj.jdbc.Driver";
    private static final String URL =  "jdbc:mysql://127.0.0.1:3306/cristal_gym?useTimezone=true&serverTimezone=UTC";
    private static final String USUARIO =  "root";
    private static final String SENHA =  "1234";

    private static Connection connection;

    public Connection abrir() throws SQLException {

        if(connection == null || connection.isClosed()){
            try {
                Class.forName(MY_DRIVER);
            } catch (ClassNotFoundException e) {
                new RuntimeException("Jar MySql não encontrado");
            }
            connection = DriverManager.getConnection(URL, USUARIO,SENHA);
        }
        return connection;
    }

    public void fechar(){
        try {
            connection.close();
        } catch (SQLException e) {
            System.out.println("Erro ao fechar conexao");
            e.printStackTrace();
        }
    }
}
