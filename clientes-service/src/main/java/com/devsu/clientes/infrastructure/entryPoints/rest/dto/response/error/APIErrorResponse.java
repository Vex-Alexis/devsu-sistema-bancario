package com.devsu.clientes.infrastructure.entryPoints.rest.dto.response.error;


public class APIErrorResponse {
    private int status;
    private boolean success;
    private String message;
    private String timestamp;

    public APIErrorResponse(int status, boolean success, String message, String timestamp) {
        this.status = status;
        this.success = success;
        this.message = message;
        this.timestamp = timestamp;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
