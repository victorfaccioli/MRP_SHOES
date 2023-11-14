package org.example;

import org.example.DAO.MateriaDao;
import org.example.configuration.Conexao;

import java.lang.ref.WeakReference;
import java.util.Scanner;

import static org.example.DAO.MateriaDao.*;
import static org.example.DAO.ProdutoDAO.*;

public class Main {
    static Scanner input = new Scanner(System.in);

    public static void main(String[] args) {
        // TODO Auto-generated catch block
        Conexao conexao = new Conexao();
        conexao.getConnection();
        WeakReference<Object> reference = new WeakReference<Object>(conexao);

        conexao = new Conexao();
        conexao.getConnection();

        System.out.println("-------------------------------------------");
        System.out.println("||------------Welcome to MRP-------------||");
        System.out.println("||---------------------------------------||");
        System.out.println("||-Selecione uma operacao pra realizar--||");
        System.out.println("-------------------------------------------");
        menu();

    }
    public static void menu() {
        System.out.println("------------------MENU------------------");
        System.out.println("||           opcao 1 - Cadastrar       ||");
        System.out.println("||           opcao 2 - Estoque         ||");
        System.out.println("||           opcao 3 - Fabricar        ||");
        System.out.println("||           opcao 4 - Sair            ||");
        System.out.println("-----------------------------------------");

        int opt = input.nextInt();

        switch (opt) {
            case 1:
                subMenuCadastro();
                break;
            case 2:
                subMenuEstoque();
                break;
            case 3:
                subMenuFabricar();
                break;
            case 4:
                System.out.println("Volte Sempre");
                System.exit(0);
            default:
                System.out.println("Opcao Invalida!!!");
                menu();
                break;
        }
    }

    public static void subMenuCadastro(){
        System.out.println("-----------------CADASTRO--------------");
        System.out.println("||           opcao 1 - Produto        ||");
        System.out.println("||           opcao 2 - Material       ||");
        System.out.println("----------------------------------------");
        int option = input.nextInt();
        switch (option){
            case 1:
                cadastrarProduto();
                break;
            case 2:
                cadastrarMateria();
                break;
            default:
                System.out.println("Opcao Invalida!!!");
                menu();

        }
    }

    public static void subMenuEstoque(){
        System.out.println("-----------------ESTOQUE----------------");
        System.out.println("||           opcao 1 - Materiais      ||");
        System.out.println("||           opcao 2 - Produtos       ||");
        System.out.println("||           opcao 3 - Menu anterior  ||");
        System.out.println("----------------------------------------");
        int option = input.nextInt();
        switch (option){
            case 1:
                System.out.println("-----------------ESTOQUE----------------------------------");
                System.out.println("||           opcao 1 - Verificar materiais              ||");
                System.out.println("||           opcao 2 - Informa chegada de materia prima ||");
                System.out.println("||           opcao 3 - Menu principal                   ||");
                System.out.println("----------------------------------------------------------");
                int opt = input.nextInt();
                switch (opt){
                    case 1:
                        verificarTodosMateriais();
                        break;
                    case 2:
                        addEstoqueMateriais();
                    case 3:
                        menu();
                    default:
                        System.out.println("Opcao Invalida!!!");
                        subMenuEstoque();

                }

            case 2:
                verificarTodosProdutos();
            case 3:
                menu();
            default:
                System.out.println("Opcao Invalida!!!");
                subMenuEstoque();

        }
    }
    public static void subMenuFabricar(){
        System.out.println("------------------Fabricar--------------");
        System.out.println("||           opcao 1 - Produto        ||");
        System.out.println("||           opcao 2 - Menu           ||");
        System.out.println("----------------------------------------");
        int option = input.nextInt();
        switch (option){
            case 1:
                fabricarProduto();
            case 2:
                menu();
            default:
                System.out.println("Opcao Invalida!!!");
                subMenuFabricar();

        }
    }
}
