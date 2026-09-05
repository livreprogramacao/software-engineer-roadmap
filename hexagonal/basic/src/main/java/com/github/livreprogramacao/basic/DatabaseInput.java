package com.github.livreprogramacao.basic;

import com.github.livreprogramacao.basic.domain.ports.input.Input;

public class DatabaseInput implements Input {

    @Override
    public String fetch() {

        return "inputText";

    }

}