package com.github.livreprogramacao.restaurante.caixa.pointofsale;

public class ItemFormat {
    private String description;
    private Money price;

    public ItemFormat(String description, Money price) {
        this.description = description;
        this.price = price;
    }

    public void print(Printer p) {
        p.print(description);
        p.print(" ");
        p.print(price.toString());
        p.newline();
    }
}
