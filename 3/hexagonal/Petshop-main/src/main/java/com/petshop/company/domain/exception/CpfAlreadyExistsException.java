package com.petshop.company.domain.exception;

public class CpfAlreadyExistsException extends RuntimeException {
    public CpfAlreadyExistsException(String cpf) {
        super("CPF já cadastrado: " + cpf);
    }
}
