package org.example;

import org.example.DAO.ProdutoDAO;
import org.example.configuration.Conexao;
import org.example.enty.Produto;

import java.lang.ref.WeakReference;
import java.util.Scanner;

public class Main {
    private static Scanner input = new Scanner(System.in);
    public static void main(String[] args) {
        // TODO Auto-generated catch block
        Conexao conexao = new Conexao();
        conexao.getConnection();
        WeakReference<Object> reference = new WeakReference<Object>(conexao);

//        while(reference.get() != null){
//            System.gc();
//            System.out.println(".");
//        }
        conexao = new Conexao();
        conexao.getConnection();


        //inicia menu
        menu();
    }
    private static void menu() {
        System.out.println("---------------------------------------------------");
        System.out.println("----------------Welcome to MRP---------------------");
        System.out.println("---------------------------------------------------");
        System.out.println("------Selecione uma operação pra realizar----------");
        System.out.println("---------------------------------------------------");
        System.out.println("||      opção 1 - Cadastrar     |");
        System.out.println("||      opção 2 - Estoque     |");
        System.out.println("||      opção 3 - Fabricar     |");
        System.out.println("||      opção 4 - Sair     |");

        int opt = input.nextInt();
        input.nextLine();
        switch (opt) {
            case 1:
                Produto produto = new Produto();
                System.out.println("Digite o nome do produto: ");
                produto.setName(input.nextLine());
                System.out.println("Digite o preço: ");
                produto.setPrice(input.nextDouble());
                ProdutoDAO.cadastrarProduto();
                break;
            case 2:
                //estoqueProdutos();
                break;
            case 3:
                //fabricarProdutos();
                break;
            case 4:
                System.out.println("Volte Sempre");
                System.exit(0);
            default:
                System.out.println("Opção Invalida!");
                menu();
                break;
        }
    }
    private static void cadastrarProdutos(){
        Produto p1 = new Produto();
        String name;
        double price;

        System.out.println("Digite o nome do produto:");
        name = input.next();
        System.out.println("Digite o preço do produto:");
        price = input.nextDouble();

        p1.setName(name);
        p1.setPrice(price);
    }
}

