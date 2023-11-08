package org.example.DAO;

import org.example.configuration.Conexao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

import static org.example.Main.menu;

public class MateriaDao {

    public String nome_material;
    public Integer quantidade_material;
    public Integer quantidade_necessario;
    public Integer produto_id;

    public static void cadastrarMateria(){
        Scanner input = new Scanner(System.in);

        System.out.println("Digite nome do material: ");
        String nome_material = input.nextLine();
        System.out.println("Digite a quantidade necessario para fabricar produto:");
        Integer quantidade_necessario = input.nextInt();
        System.out.println("Digite quantos materiais possui em estoque atualmente:");
        Integer quantidade_material = input.nextInt();
        System.out.println("Digite o código do produto para vincular o material");
        Integer produto_id = input.nextInt();


        Conexao c = new Conexao();
        Connection con = c.getConnection();

        try {
            PreparedStatement p = con.prepareStatement("insert into materiais (nome_material, quantidade_material, quantidade_necessaria, produto_id) values (?, ?, ? ,?)");
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

    public static void verificarMateriais(){

    }
}
