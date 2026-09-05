package com.github.livreprogramacao.application.application.usecases;

import com.github.livreprogramacao.application.adapters.inbound.database.DatabaseInput;
import com.github.livreprogramacao.application.adapters.outbound.console.ConsoleOutput;

import com.github.livreprogramacao.domain.model.TextConversion;
import com.github.livreprogramacao.domain.ports.input.Input;
import com.github.livreprogramacao.domain.ports.output.Output;

/**
 * Text conversion app!
 *
 */
public class TextConversionApp {

    public static void main( String[] args ) {

        System.out.println( "Text conversion app!" );

        Input input = new DatabaseInput();
        Output output = new ConsoleOutput();

        new TextConversion( input, output ).showInputInUpperCase();

    }

}