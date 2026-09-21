package com.github.livreprogramacao.basic;

import com.github.livreprogramacao.basic.domain.ports.input.Input;
import com.github.livreprogramacao.basic.domain.ports.output.Output;
import com.github.livreprogramacao.basic.domain.services.TextConversion;

/**
 * Hello world!
 *
 */
public class App {

    public static void main(String[] args) {

        System.out.println("Hello World!");

        Input input = new DatabaseInput();
        Output output = new ConsoleOutput();

        new TextConversion(input, output).showInputInUpperCase();

    }

}