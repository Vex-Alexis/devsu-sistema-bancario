package com.devsu.clientes.infrastructure.entryPoints.rest.dto.response.success;

public class APISuccessResponse<T> {
    private int status;
    private boolean success;
    private String message;
    private String timestamp;
    private T data;
}
