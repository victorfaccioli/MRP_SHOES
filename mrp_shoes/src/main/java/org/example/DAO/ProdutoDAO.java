package org.example.DAO;

import org.example.configuration.Conexao;
import org.example.enty.Produto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ProdutoDAO {
    public ProdutoDAO() {

    }
    public static void cadastrarProduto() {
        Conexao c = new Conexao();
        Connection con = c.getConnection();

        try {
            PreparedStatement p = con.prepareStatement("insert into produto (nome_produto, preco) values (?, ?)");
            p.setString(1, Produto.getNameProduto());
            p.setDouble(2, Produto.getPreco());
            p.executeUpdate();
            System.out.println("Comando executado");
            p.close();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
}
