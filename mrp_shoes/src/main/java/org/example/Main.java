package org.example;

import org.example.DAO.ProdutoDAO;
import org.example.configuration.Conexao;

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

        System.out.println("---------------------------------------------------");
        System.out.println("----------------Welcome to MRP---------------------");
        System.out.println("---------------------------------------------------");
        System.out.println("------Selecione uma operação pra realizar----------");
        System.out.println("---------------------------------------------------");
        //inicia menu
        menu();

    }
    private static void menu() {
        System.out.println("---------------------------------------------------");
        System.out.println("||      opção 1 - Cadastrar     |");
        System.out.println("||      opção 2 - Estoque     |");
        System.out.println("||      opção 3 - Fabricar     |");
        System.out.println("||      opção 4 - Sair     |");

        int opt = input.nextInt();
        input.nextLine();
        switch (opt) {
            case 1:
                System.out.println("Digite o nome do produto: ");
                String name = input.nextLine();
                System.out.println("Digite o preço: ");
                Double price = input.nextDouble();
                ProdutoDAO.cadastrarProduto(name,price);
                System.out.println("Agora insira os materia desse Produto:");
                System.out.println("quantos materiais esse produto vai ter?");
                int qt = input.nextInt();

                for (int i = 0; i < qt; i++) {
                    input.nextLine();
                    String nameMt;
                    int qtdMt,qtdMtNc;
                    System.out.println("Qual o nome do material?");
                    nameMt = input.nextLine();
                    System.out.println("Quanto material tem no estoque?");
                    qtdMt = input.nextInt();
                    System.out.println("Quanto desse material será necessesario para fazer o produto?");
                    qtdMtNc = input.nextInt();

                    ProdutoDAO.cadastroMateriaias(nameMt,qtdMt,qtdMtNc);
                }
                menu();
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
}

