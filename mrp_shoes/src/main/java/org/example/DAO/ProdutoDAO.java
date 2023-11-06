package org.example.DAO;

import org.example.configuration.Conexao;
import org.example.enty.Produto;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ProdutoDAO {
    public ProdutoDAO() {

    }
    public void addProduto(Produto p1) {
        Conexao c = new Conexao();
        Connection con = c.getConnection();

        try {
            PreparedStatement p = con.prepareStatement("insert into users (name, price) values (?, ?)");
            p.setString(1, p1.getName());
            p.setDouble(2, p1.getPrice());
            System.out.println(p);
            p.executeUpdate();
            System.out.println("Comando executado");
            p.close();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
}
