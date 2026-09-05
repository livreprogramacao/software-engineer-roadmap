package com.github.livreprogramacao.petshop.application.jpa.exception;

public class CpfAlreadyExistsException extends RuntimeException {
    public CpfAlreadyExistsException(String cpf) {
        super("CPF já cadastrado: " + cpf);
    }
}
