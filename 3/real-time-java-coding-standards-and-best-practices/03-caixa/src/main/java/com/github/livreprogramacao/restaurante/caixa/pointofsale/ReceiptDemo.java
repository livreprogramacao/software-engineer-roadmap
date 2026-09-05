package com.github.livreprogramacao.restaurante.caixa.pointofsale;

import java.math.BigDecimal;

public class ReceiptDemo {

    public static void main(String[] args) {
        new ReceiptDemo().run();
    }

    private void run() {
        Receipt r = new Receipt( new Printer() );

        // Nice cheese and wine evening
        r.add("Brie", new Money(BigDecimal.valueOf(1.95), "GBP"));
        r.add("Tiger Bread", new Money(BigDecimal.valueOf(0.95), "GBP"));
        r.add("Merlot", new Money(BigDecimal.valueOf(7.95), "GBP"));

        r.print();
    }
}
