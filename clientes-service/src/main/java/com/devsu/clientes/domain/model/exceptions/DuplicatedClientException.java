package com.devsu.clientes.domain.model.exceptions;

public class DuplicatedClientException extends RuntimeException {
    public DuplicatedClientException(String message) {
        super(message);
    }
}
