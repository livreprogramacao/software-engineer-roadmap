package br.com.livreprogramacao.example;

import br.com.livreprogramacao.example.algebraicdatatype.openjdk.B;
import br.com.livreprogramacao.example.algebraicdatatype.openjdk.C;
import br.com.livreprogramacao.example.algebraicdatatype.openjdk.I;

public class App {

    void test (C c) {
        if (c instanceof I)
            System.out.println("It's an I");
    }

    public static void main(String[] args) {
        System.out.println("Running...");

        // Sealed classes and conversions
        new App().test(new B());

        System.out.println("done!");
    }
}
