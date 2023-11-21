package org.example.DAO;

import org.example.configuration.Conexao;

import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import static org.example.DAO.MaterialDao.*;

import static org.example.Main.*;

public class ProdutoDAO {

    public Integer produtoId;
    public String nome;
    public Double preco;
    public Integer quantidadePossivel;
    public  Integer quantidadeProduto;

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
    public Integer getQuantidadeProduto() {return quantidadeProduto;}
    public void setQuantidadeProduto(Integer quantidadeProduto) {this.quantidadeProduto = quantidadeProduto;}

    public ProdutoDAO() {

    }
    public ProdutoDAO(Integer quantidadeProduto) {this.quantidadeProduto = quantidadeProduto;}

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
        System.out.println("+----+-----------+-------+------------+");
        System.out.println("| ID | Produto   | Preço | Quantidade |");
        System.out.println("+----+-----------+-------+------------+");

        while (index < dados.size()) {
            ProdutoDAO produto = dados.get(index);
            System.out.printf("| %-2d | %-9s | %-5.2f | %-2d |\n",
                    produto.getProdutoId(), produto.getNome(), produto.getPreco(),produto.getQuantidadeProduto());
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
                int quantidade_produtos = resultSet.getInt("quantidade_produtos");

                ProdutoDAO produtoDao = new ProdutoDAO();
                produtoDao.setProdutoId(produtoId);
                produtoDao.setNome(nome);
                produtoDao.setPreco(preco);
                produtoDao.setQuantidadeProduto(quantidade_produtos);
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



        System.out.println("Qual ID produto que deseja fabricar: ");
        Integer produto_id = input.nextInt();
        Integer qtPossivelProduto = produto.quantidadePossivel(produto_id);

        System.out.println("Quantos produtos deseja fazer? (Max. possivel: " + qtPossivelProduto +")" );
        Integer quantidadeFabricar = input.nextInt();


        if(quantidadeFabricar > 0 && quantidadeFabricar <= qtPossivelProduto){
            try{
                Integer novaQuantidadeProduto = quantidadesProdutos(produto_id).getQuantidadeProduto() + quantidadeFabricar;

                Integer novaQuantidadeMaterial = quantidadeMateriais(produto_id).getQuantidadeMaterial() -
                        (quantidadeFabricar * quantidadeMateriais(produto_id).getQuantidadeNecessario());

                Conexao c = new Conexao();
                Connection con = c.getConnection();
                PreparedStatement p = con.prepareStatement("update materiais " +
                        "join produto on produto.produto_id = materiais.produto_id " +
                        "set produto.quantidade_produtos = ?, materiais.quantidade_material = ? " +
                        "where produto.produto_id = ? ");
                p.setInt(1,novaQuantidadeProduto);
                p.setInt(2,novaQuantidadeMaterial);
                p.setInt(3,produto_id);
                int resultSet = p.executeUpdate();

                p.close();


                System.out.println("Enviado pedido para produção!!!\n Redirecionando para o menu anterior... ");
                subMenuFabricar();

            }catch(SQLException e) {
                System.out.println("Opção invalida!!!\n Redirecionando para o menu anterior... ");
                subMenuFabricar();
            }
        }else if(quantidadeFabricar > qtPossivelProduto){
            System.out.println("Essa quantidade não pode ser fabricada por falta de material.");
            subMenuFabricar();
        }
        subMenuFabricar();
    }
    public static Integer quantidadePossivel(Integer id){
        Scanner input = new Scanner(System.in);
        Conexao c = new Conexao();
        Connection con = c.getConnection();

        Integer quantidadepossivel = 0;

        ProdutoDAO produto = new ProdutoDAO();
        produto.setProdutoId(id);
        try{

            PreparedStatement p = con.prepareStatement("select quantidade_material, quantidade_necessaria " +
                    "from materiais WHERE produto_id = ?");
            p.setInt(1,produto.getProdutoId());
            ResultSet resultSet = p.executeQuery();

            if (resultSet.next()) {
                Integer quantidadeMaterial = resultSet.getInt("quantidade_material");
                Integer quantidadeNecessaria = resultSet.getInt("quantidade_necessaria");
                if (quantidadeMaterial > 0 && quantidadeMaterial >= quantidadeNecessaria){
                    quantidadepossivel = (quantidadeMaterial / quantidadeNecessaria);
                }
            }
            resultSet.close();
            p.close();

        } catch (SQLException e){
            e.printStackTrace();
            System.out.println("Não há estoque do material suficiente!\n Redirecionando para o menu anterior...");
            subMenuFabricar();
        }
        return quantidadepossivel;
    }

    public static ProdutoDAO quantidadesProdutos(Integer produtoId) {
        try (Scanner input = new Scanner(System.in);
             Connection con = new Conexao().getConnection();
             PreparedStatement p = con.prepareStatement("SELECT quantidade_produtos " +
                     "FROM produto WHERE produto_id = ?")) {

            p.setInt(1, produtoId);
            try (ResultSet resultSet = p.executeQuery()) {
                if (resultSet.next()) {
                    Integer quantidadeMaterial = resultSet.getInt("quantidade_produtos");


                    return new ProdutoDAO(quantidadeMaterial);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Não há estoque do material suficiente!\n Redirecionando para o menu anterior...");
            subMenuFabricar();
        }

        return null;
    }
}


