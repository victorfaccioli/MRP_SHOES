package org.example.configuration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {
    private String url = "jdbc:mysql://localhost:3306/mrpshoes";
    private String userName = "root";
    private String password = "root";
    private static Connection connection = null;
    private static Conexao instance = null;

    public Conexao(){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("===== Driver Carregado ======");
        }catch (ClassNotFoundException e){
            // TODO Auto-generated catch block
            System.out.println("===== Driver não carregado: " + e.getMessage() + " =====");
        }
    }

    public Connection getConnection(){
        try{
            if ((connection == null) || (connection.isClosed())){
                connection = DriverManager.getConnection(url,userName,password);
                System.out.println("===== Conexao estabelecida com sucesso =====");
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            System.out.println("===== Conexao não estabelecida: " + e.getMessage() + " =====");
        }
        return connection;
    }

    public void finalize(){
        System.out.println("===== Conexao encerrada =====");
        try{
            connection.close();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
