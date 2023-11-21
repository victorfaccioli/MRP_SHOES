package org.example;

import org.example.configuration.Conexao;

import java.lang.ref.WeakReference;
import java.util.InputMismatchException;
import java.util.Scanner;

import static org.example.DAO.MaterialDao.*;
import static org.example.DAO.ProdutoDAO.*;

public class Main {

    public static void main(String[] args) {
        // TODO Auto-generated catch block

        System.out.println("-------------------------------------------");
        System.out.println("||------------Welcome to MRP-------------||");
        System.out.println("||---------------------------------------||");
        System.out.println("||-Selecione uma operacao pra realizar--||");
        System.out.println("-------------------------------------------");
        menu();

    }
    public static void menu() {
            Scanner input = new Scanner(System.in);
            System.out.println("------------------MENU------------------");
            System.out.println("||           opcao 1 - Cadastrar       ||");
            System.out.println("||           opcao 2 - Estoque         ||");
            System.out.println("||           opcao 3 - Fabricar        ||");
            System.out.println("||           opcao 4 - Sair            ||");
            System.out.println("-----------------------------------------");

            if(input.hasNextInt()) {
                Integer opt = input.nextInt();
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
    }

    public static void subMenuCadastro(){
        Scanner input = new Scanner(System.in);
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
                break;
        }
    }

    public static void subMenuEstoque(){
        Scanner input = new Scanner(System.in);
        System.out.println("-----------------ESTOQUE----------------");
        System.out.println("||           opcao 1 - Materiais      ||");
        System.out.println("||           opcao 2 - Produtos       ||");
        System.out.println("||           opcao 3 - Menu anterior  ||");
        System.out.println("----------------------------------------");
        Integer option = input.nextInt();
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
                        break;
                    case 3:
                        menu();
                        break;
                    default:
                        System.out.println("Opcao Invalida!!!");
                        subMenuEstoque();
                        break;
                }
                break;

            case 2:
                verificarTodosProdutos();
                break;

            case 3:
                menu();
                break;
            default:
                System.out.println("Opcao Invalida!!!");
                subMenuEstoque();
                break;
        }
    }
    public static void subMenuFabricar(){
        Scanner input = new Scanner(System.in);
        System.out.println("------------------Fabricar--------------");
        System.out.println("||           opcao 1 - Produto        ||");
        System.out.println("||           opcao 2 - Menu           ||");
        System.out.println("----------------------------------------");
        Integer option = input.nextInt();
        switch (option){
            case 1:
                fabricarProduto();
            case 2:
                menu();
                break;
            default:
                System.out.println("Opcao Invalida!!!");
                subMenuFabricar();
                break;
        }
    }
}
