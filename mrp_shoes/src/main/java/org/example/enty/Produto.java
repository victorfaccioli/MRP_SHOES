package org.example.enty;


public class Produto {
    public static String nameProduto;
    public static Double preco;

    public static String getNameProduto() {
        return nameProduto;
    }

    public void setName(String name) {
        this.nameProduto = name;
    }

    public static Double getPreco() {
        return preco;
    }

    public void setPrice(Double price) {
        this.preco = price;
    }
}

