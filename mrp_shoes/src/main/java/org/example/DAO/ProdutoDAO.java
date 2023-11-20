package org.example.DAO;

import org.example.configuration.Conexao;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import static org.example.Main.*;

public class ProdutoDAO {

    public Integer produtoId;
    public String nome;
    public Double preco;

    public Integer quantidadePossivel;

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

    public Integer getQuantidadePossivel() {
        return quantidadePossivel;
    }

    public void setQuantidadePossivel(Integer quantidadePossivel) {
        this.quantidadePossivel = quantidadePossivel;
    }

    public static void cadastrarProduto() {

        Scanner input = new Scanner(System.in);
        System.out.println("Digite nome do produto");
        String nome = input.nextLine();
        System.out.println("Digite o preco: ");
        Double preco = input.nextDouble();

        Conexao c = new Conexao();
        Connection con = c.getConnection();

        try {
            PreparedStatement p = con.prepareStatement("insert into produto " +
                    "(nome_produto, preco)" +
                    " values (?, ?)", Statement.RETURN_GENERATED_KEYS);
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

    public static void verificarTodosProdutos() {
        ProdutoDAO produtoDAO = new ProdutoDAO();
        ArrayList<ProdutoDAO> dados = produtoDAO.estoqueProdutos();

        int index = 0;
        System.out.println("+----+-----------+-------+");
        System.out.println("| ID | Produto   | Preço |");
        System.out.println("+----+-----------+-------+");

        while (index < dados.size()) {
            ProdutoDAO produto = dados.get(index);
            System.out.printf("| %-2d | %-9s | %-5.2f |\n",
                    produto.getProdutoId(), produto.getNome(), produto.getPreco());
            index++;
        }

        System.out.println("+----+-----------+-------+");

        try {
            for (int i = 0; i < 3; i++) {
                TimeUnit.SECONDS.sleep(5);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        subMenuEstoque();
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
                produtoDao.setNome(nome);
                produtoDao.setPreco(preco);
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


    public static void fabricarProduto(){


        Scanner input = new Scanner(System.in);

        ProdutoDAO produto = new ProdutoDAO();
        produto.quantidadePossivel();
        System.out.println("Quantos produtos deseja fazer? (Max. possivel: " + produto.getQuantidadePossivel() +")" );
        Integer quantidadeFabricar = input.nextInt();
        if(quantidadeFabricar > 0 ){


        }
    }
    public static void quantidadePossivel(){
        Scanner input = new Scanner(System.in);
        Conexao c = new Conexao();
        Connection con = c.getConnection();

        Integer quantidadepossivel = 0;

        ProdutoDAO produto = new ProdutoDAO();
        System.out.println("Qual ID produto que deseja fabricar: ");
        produto.setProdutoId(input.nextInt());
        try{

            PreparedStatement p = con.prepareStatement("select quantidade_material, quantidade_necessaria " +
                    "from materiais WHERE produto_id = ?");
            p.setInt(1,produto.getProdutoId());
            ResultSet resultSet = p.executeQuery();

                Integer quantidadeMaterial = resultSet.getInt("quantidade_material");
                Integer quantidadeNecessaria = resultSet.getInt("quantidade_necessaria");


            if (quantidadeMaterial > 0 && quantidadeMaterial >= quantidadeNecessaria){
                quantidadepossivel = (quantidadeMaterial / quantidadeNecessaria);
            }
            resultSet.close();
            p.close();

        } catch (SQLException e){
            e.printStackTrace();
            System.out.println("Não há estoque do material suficiente!\n Redirecionando para o menu anterior...");
            subMenuFabricar();
        }
        produto.setQuantidadePossivel(quantidadepossivel);
    }


}

