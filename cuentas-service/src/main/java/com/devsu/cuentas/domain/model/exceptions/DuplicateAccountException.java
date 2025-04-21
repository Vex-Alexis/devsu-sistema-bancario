package com.devsu.cuentas.domain.model.exceptions;

public class DuplicateAccountException extends RuntimeException {
    public DuplicateAccountException(String message) {
        super(message);
    }
}
