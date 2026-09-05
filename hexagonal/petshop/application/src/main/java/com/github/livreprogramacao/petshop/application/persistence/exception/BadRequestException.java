package com.github.livreprogramacao.petshop.application.jpa.exception;

public class BadRequestException extends RuntimeException {

    public BadRequestException(String message) {
        super(message);
    }
}
