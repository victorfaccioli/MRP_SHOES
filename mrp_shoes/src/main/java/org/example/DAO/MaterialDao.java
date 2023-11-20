package org.example.DAO;

import org.example.configuration.Conexao;

import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import static org.example.Main.*;

public class MaterialDao {

    public Integer materialId;
    public String nomeMaterial;
    public static Integer quantidadeMaterial;
    public Integer quantidadeNecessaria;
    public Integer produtoId;

    public Integer getMaterialId() {
        return materialId;
    }
    public void setMaterialId(Integer materialId) {
        this.materialId = materialId;
    }
    public String getNomeMaterial() {
        return nomeMaterial;
    }
    public void setNomeMaterial(String nomeMaterial) {
        this.nomeMaterial = nomeMaterial;
    }
    public Integer getQuantidadeMaterial() {
        return quantidadeMaterial;
    }
    public void setQuantidadeMaterial(Integer quantidadeMaterial) {
        this.quantidadeMaterial = quantidadeMaterial;
    }
    public Integer getQuantidadeNecessario() {
        return quantidadeNecessaria;
    }
    public void setQuantidadeNecessario(Integer quantidadeNecessario) {this.quantidadeNecessaria = quantidadeNecessario;}
    public Integer getProdutoId() {
        return produtoId;
    }
    public void setProdutoId(Integer produtoId) {
        this.produtoId = produtoId;
    }
    public MaterialDao() {

    }
    public MaterialDao(Integer quantidadeMaterial, Integer quantidadeNecessaria) {
        this.quantidadeMaterial = quantidadeMaterial;
        this.quantidadeNecessaria = quantidadeNecessaria;

    }

    public static void cadastrarMateria(){
        Scanner input = new Scanner(System.in);

        System.out.println("Digite nome do material: ");
        String nome_material = input.nextLine();
        System.out.println("Digite a quantidade necessario para fabricar produto:");
        Integer quantidade_necessario = input.nextInt();
        System.out.println("Digite quantos materiais possui em estoque atualmente:");
        Integer quantidade_material = input.nextInt();
        System.out.println("Digite o c�digo do produto para vincular o material");
        Integer produto_id = input.nextInt();


        Conexao c = new Conexao();
        Connection con = c.getConnection();

        try {
            PreparedStatement p = con.prepareStatement("insert into materiais " +
                    "(nome_material, quantidade_material, quantidade_necessaria, produto_id) " +
                    "values (?, ?, ? ,?)");
            p.setString(1, nome_material);
            p.setDouble(2, quantidade_material);
            p.setDouble(3, quantidade_necessario);
            p.setInt(4,produto_id);
            p.executeUpdate();
            System.out.println("Materiais Cadastrado");
            p.close();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        menu();

    }

    public static void verificarTodosMateriais() {
        MaterialDao materialDao = new MaterialDao();
        ArrayList<MaterialDao> dados = materialDao.estoqueTodosMateriais();

        int index = 0;
        System.out.println("+----+------------+------------+-------------+-------------------+");
        System.out.println("| ID | Material   | Quantidade | Quantidade  | Produto vinculado | ");
        System.out.println("+----+------------| necessaria |  estoque    |-------------------+");

        while (index < dados.size()) {
            MaterialDao materia = dados.get(index);
            System.out.printf("| %-2d |  %-9s  |    %-2d    |    %-2d     |       %-2d        |\n",
                    materia.getMaterialId(),
                    materia.getNomeMaterial(),
                    materia.getQuantidadeNecessario(),
                    materia.getQuantidadeMaterial(),
                    materia.getProdutoId());
            index++;
        }
            System.out.println("+----+------------+------------+-------------+-------------------+");
        try {
            for (int i = 0; i < 3; i++) {
                TimeUnit.SECONDS.sleep(5);
            }
        } catch (Exception e) {
            System.out.println(e);
        }

        subMenuEstoque();


    }


    public ArrayList<MaterialDao> estoqueTodosMateriais() {

        Conexao c = new Conexao();
        Connection con = c.getConnection();

        ArrayList<MaterialDao> list = new ArrayList<MaterialDao>();

        try {

            PreparedStatement p = con.prepareStatement("select * from materiais");
            ResultSet resultSet = p.executeQuery();

            while (resultSet.next()){
                Integer materialId = resultSet.getInt("material_id");
                String nomeMaterial = resultSet.getString("nome_material");
                Integer quantidadeMaterial = resultSet.getInt("quantidade_material");
                Integer quantidadeNecessaria = resultSet.getInt("quantidade_necessaria");
                Integer produtoId = resultSet.getInt("produto_id");

                MaterialDao materialDao = new MaterialDao();

                materialDao.setMaterialId(materialId);
                materialDao.setNomeMaterial(nomeMaterial);
                materialDao.setQuantidadeMaterial(quantidadeMaterial);
                materialDao.setQuantidadeNecessario(quantidadeNecessaria);
                materialDao.setProdutoId(produtoId);

                list.add(materialDao);
            }

            resultSet.close();
            p.close();


        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return list;

    }

    public static void addEstoqueMateriais() {
        Conexao c = new Conexao();
        Connection con = c.getConnection();

        MaterialDao materialDao = new MaterialDao();
        Scanner input = new Scanner(System.in);


        System.out.println("Digite o ID do material: ");
        Integer ID_material = input.nextInt();
        System.out.println("Digite a quantidade que será adicionada ao estoque?:");
        Integer entradaMaterial = input.nextInt();


        try {
            PreparedStatement selectStatement = con.prepareStatement("SELECT material_id, quantidade_material " +
                    "from materiais where material_id = ?");
            selectStatement.setInt(1, ID_material);
            ResultSet resultSet = selectStatement.executeQuery();

            if (resultSet.next()) {

                Integer currentId = resultSet.getInt("material_id");
                Integer currentQuantidade = resultSet.getInt("quantidade_material");


                Integer newQuantidade = currentQuantidade + entradaMaterial;

                PreparedStatement updateStatement = con.prepareStatement("update materiais set quantidade_material = ? where material_id = ?");
                updateStatement.setInt(1, newQuantidade);
                updateStatement.setInt(2, currentId);
                updateStatement.executeUpdate();
                updateStatement.close();
            }

            resultSet.close();
            selectStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    public static MaterialDao quantidadeMateriais(Integer produtoId) {
        try (Scanner input = new Scanner(System.in);
             Connection con = new Conexao().getConnection();
             PreparedStatement p = con.prepareStatement("SELECT quantidade_material, quantidade_necessaria " +
                     "FROM materiais WHERE produto_id = ?")) {

            p.setInt(1, produtoId);
            try (ResultSet resultSet = p.executeQuery()) {
                if (resultSet.next()) {
                    Integer quantidadeMaterial = resultSet.getInt("quantidade_material");
                    Integer quantidadeNecessaria = resultSet.getInt("quantidade_necessaria");

                    return new MaterialDao(quantidadeMaterial, quantidadeNecessaria);
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