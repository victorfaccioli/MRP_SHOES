package org.example.DAO;

import org.example.configuration.Conexao;
import org.w3c.dom.ls.LSOutput;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Scanner;

import static org.example.Main.menu;

public class ProdutoDAO {

    public Integer produtoId;
    public String nome;
    public Double preco;

    public Integer getProdutoId() {
        return produtoId;
    }

    public void setProdutoId(Integer produtoId) {
        this.produtoId = produtoId;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Double getPreco() {
        return preco;
    }

    public void setPreco(Double preco) {
        this.preco = preco;
    }



    public static void cadastrarProduto() {

        Scanner input = new Scanner(System.in);
        System.out.println("Digite nome do produto");
        String nome = input.nextLine();
        System.out.println("Digite o pre�o: ");
        Double preco = input.nextDouble();

        Conexao c = new Conexao();
        Connection con = c.getConnection();

        try {
            PreparedStatement p = con.prepareStatement("insert into produto (nome_produto, preco) values (?, ?)", Statement.RETURN_GENERATED_KEYS);
            p.setString(1, nome);
            p.setDouble(2, preco);
            p.executeUpdate();
            System.out.println("Produto "+ nome +" cadastrado R$" + preco );

            p.close();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        menu();
    }

    public static void verificarProdutos(){

    }

    public ArrayList<ProdutoDAO> estoqueProdutos() {
        Conexao c = new Conexao();
        Connection con = c.getConnection();

        ArrayList<ProdutoDAO> list = new ArrayList<ProdutoDAO>();

        try {
            PreparedStatement p = con.prepareStatement("select * from produto");
            ResultSet resultSet = p.executeQuery();
            while (resultSet.next()){
                Integer produtoId = resultSet.getInt("produto_id");
                String nome = resultSet.getString("nome_produto");
                Double preco = resultSet.getDouble("preco");
                ProdutoDAO produtoDao = new ProdutoDAO();
                produtoDao.setProdutoId(produtoId);
                list.add(produtoDao);
            }
            resultSet.close();
            p.close();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return list;

    }
}

