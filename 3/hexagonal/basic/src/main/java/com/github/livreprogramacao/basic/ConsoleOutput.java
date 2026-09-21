package com.github.livreprogramacao.basic;

import com.github.livreprogramacao.basic.domain.ports.output.Output;

public class ConsoleOutput implements Output {

    @Override
    public void toDisplay( final String toDisplay) {

        System.out.println( toDisplay );

    }

}