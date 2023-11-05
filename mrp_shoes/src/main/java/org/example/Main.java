package org.example;

import org.example.configuration.Conexao;

import java.lang.ref.WeakReference;

public class Main {
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
    }
}