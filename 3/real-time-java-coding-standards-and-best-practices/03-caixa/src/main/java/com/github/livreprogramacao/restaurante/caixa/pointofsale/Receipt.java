package com.github.livreprogramacao.restaurante.caixa.pointofsale;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Receipt {
    private final Printer printer;
    private final List<Item> items = new ArrayList<>();
    private BigDecimal amount = BigDecimal.ZERO;

    public Receipt(Printer printer) {
        this.printer = printer;
    }

    public void add(String description, Money price) {
        items.add( new Item(description, price) );
        amount = price.receiptTotal(amount);
    }

    public void print() {
        items.forEach( item -> item.print(printer));
        this.totalAmount();
    }

    public void totalAmount() {
//        items.forEach( item -> item.subTotal(amout));
        Money calculateTotalAmount = new Money(amount, "GBP");
        printer.print("Total amount: " + calculateTotalAmount);
    }
}
