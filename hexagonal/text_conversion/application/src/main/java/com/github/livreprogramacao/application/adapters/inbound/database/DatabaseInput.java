package com.github.livreprogramacao.application.adapters.inbound.database;

import com.github.livreprogramacao.domain.ports.input.Input;

public class DatabaseInput implements Input {

    @Override
    public String fetch() {

        return "Hello Hexagonal world!";

    }


}
