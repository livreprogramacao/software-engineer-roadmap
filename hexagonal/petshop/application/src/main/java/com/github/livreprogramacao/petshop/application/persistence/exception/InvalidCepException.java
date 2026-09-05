package com.github.livreprogramacao.petshop.application.jpa.exception;

public class InvalidCepException extends RuntimeException {
    public InvalidCepException(String message) {
        super(message);
    }
}
