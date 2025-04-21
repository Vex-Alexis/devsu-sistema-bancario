package com.devsu.clientes.infrastructure.entryPoints.rest.handler;

import com.devsu.clientes.domain.model.exceptions.ClienteNotFoundException;
import com.devsu.clientes.domain.model.exceptions.DuplicatedClientException;
import com.devsu.clientes.infrastructure.entryPoints.rest.dto.response.error.APIErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestControllerAdvice
public class GlobalExceptionHandler {

    //@ExceptionHandler({ClienteNotFoundException.class/*, CuentaNotFoundException.class*/})
    @ExceptionHandler(ClienteNotFoundException.class)
    public ResponseEntity<APIErrorResponse> handleNotFound(ClienteNotFoundException ex) {
        return buildErrorResponse(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(DuplicatedClientException.class)
    public ResponseEntity<APIErrorResponse> handleDuplicatedClient(DuplicatedClientException ex) {
        return buildErrorResponse(HttpStatus.CONFLICT, ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<APIErrorResponse> handleGeneral(Exception ex, WebRequest request) {
        return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Error interno del servidor");
    }

    private ResponseEntity<APIErrorResponse> buildErrorResponse(HttpStatus status, String message) {
        APIErrorResponse error = new APIErrorResponse(
                status.value(),
                false,
                message,
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
        );
        return ResponseEntity.status(status).contentType(MediaType.APPLICATION_JSON).body(error);
    }
}
