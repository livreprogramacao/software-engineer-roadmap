package com.github.livreprogramacao.restaurante.caixa.pointofsale;

public class Bill {

    private float total = 0F;

    public void add(float itemPrice) {
        total += itemPrice;
    }

    public float getTotal() {
        return total;
    }

}
