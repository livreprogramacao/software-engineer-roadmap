package com.github.livreprogramacao.application.adapters.outbound.console;

import com.github.livreprogramacao.domain.ports.output.Output;

public class ConsoleOutput implements Output {

    @Override
    public void toDisplay( final String toDisplay) {
        System.out.println( toDisplay );
    }

}