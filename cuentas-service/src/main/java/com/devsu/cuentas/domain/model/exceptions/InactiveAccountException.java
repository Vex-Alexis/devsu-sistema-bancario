package com.devsu.cuentas.domain.model.exceptions;

public class InactiveAccountException extends RuntimeException {
    public InactiveAccountException(String message) {
        super(message);
    }
}
