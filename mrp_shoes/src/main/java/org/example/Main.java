package org.example;

import org.example.configuration.Conexao;

import java.lang.ref.WeakReference;
import java.util.Scanner;

import static org.example.DAO.MateriaDao.cadastrarMateria;
import static org.example.DAO.MateriaDao.verificarMateriais;
import static org.example.DAO.ProdutoDAO.cadastrarProduto;
import static org.example.DAO.ProdutoDAO.verificarProdutos;

public class Main {
    static Scanner input = new Scanner(System.in);

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

        System.out.println("-------------------------------------------");
        System.out.println("||------------Welcome to MRP-------------||");
        System.out.println("||---------------------------------------||");
        System.out.println("||-Selecione uma operação pra realizar--||");
        System.out.println("-------------------------------------------");
        //inicia menu
        menu();

    }
    public static void menu() {
        System.out.println("------------------〖MENU〗------------------");
        System.out.println("||           opção 1 - Cadastrar       ||");
        System.out.println("||           opção 2 - Estoque         ||");
        System.out.println("||           opção 3 - Fabricar        ||");
        System.out.println("||           opção 4 - Sair            ||");
        System.out.println("-------------------------------------------");

        int opt = input.nextInt();

        switch (opt) {
            case 1:
                subMenuCadastro();
                break;
            case 2:
                subMenuEstoque();
                break;
            case 3:
                //fabricarProdutos();
                break;
            case 4:
                System.out.println("Volte Sempre");
                System.exit(0);
            default:
                System.out.println("Opção Invalida, redirecionado para o menu principa");
                menu();
                break;
        }
    }

    public static void subMenuCadastro(){
        System.out.println("----------------〖CADASTRO〗----------------");
        System.out.println("||           opção 1 - Produto        ||");
        System.out.println("||           opção 2 - Material       ||");
        System.out.println("-------------------------------------------");
        int option = input.nextInt();
        switch (option){
            case 1:
                cadastrarProduto();
                break;
            case 2:
                cadastrarMateria();
                break;
            default:
                System.out.println("Opção Invalida, redirecionado para o menu principal");
                menu();

        }
    }

    public static void subMenuEstoque(){
        System.out.println("----------------〖ESTOQUE〗-----------------");
        System.out.println("||           opção 1 - Produto        ||");
        System.out.println("||           opção 2 - Material       ||");
        System.out.println("-------------------------------------------");
        int option = input.nextInt();
        switch (option){
            case 1:
                verificarProdutos();
            case 2:
                verificarMateriais();
                break;
            default:
                System.out.println("Opção Invalida, redirecionado para o menu principal");
                menu();

        }
    }
    public static void subMenuFabricar(){
        System.out.println("------------------〖Fabricar〗------------------");
        System.out.println("||           opção 1 - Produto        ||");
        System.out.println("||           opção 2 - Material       ||");
        System.out.println("-------------------------------------------");
        int option = input.nextInt();
        switch (option){
            case 1:
                verificarProdutos();
                break;
            case 2:
                verificarMateriais();
                break;
            default:
                System.out.println("Opção Invalida, redirecionado para o menu principal");
                menu();

        }
    }
}

