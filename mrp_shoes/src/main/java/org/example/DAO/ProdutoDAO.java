package org.example.DAO;

import org.example.configuration.Conexao;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;

public class ProdutoDAO {
    public static int generatedKey = 0;
    public static void cadastrarProduto(String name, Double price) {
        Conexao c = new Conexao();
        Connection con = c.getConnection();

        try {
            PreparedStatement p = con.prepareStatement("insert into produto (nome_produto, preco) values (?, ?)", Statement.RETURN_GENERATED_KEYS);
            p.setString(1, name);
            p.setDouble(2, price);
            p.executeUpdate();
            System.out.println("Produto cadastrado");

            ResultSet rs = p.getGeneratedKeys();

            if (rs.next()) {
                generatedKey = rs.getInt(1);
            }
            System.out.println(generatedKey);
            p.close();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
    public static void cadastroMateriaias(String nome_material,int quantidade_material,int quantidade_necessario){
        Conexao c = new Conexao();
        Connection con = c.getConnection();

        try {
            PreparedStatement p = con.prepareStatement("insert into materiais (nome_material, quantidade_material, quantidade_necessaria, produto_id) values (?, ?, ? ,?)");
            p.setString(1, nome_material);
            p.setDouble(2, quantidade_material);
            p.setDouble(3, quantidade_necessario);
            p.setDouble(4, generatedKey);
            p.executeUpdate();
            System.out.println("Materiais Cadastrado");
            p.close();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
