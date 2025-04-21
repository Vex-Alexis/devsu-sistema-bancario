package com.devsu.cuentas.infrastructure.entryPoints.rest.handler;

import com.devsu.cuentas.domain.model.exceptions.DuplicateAccountException;
import com.devsu.cuentas.domain.model.exceptions.InactiveAccountException;
import com.devsu.cuentas.domain.model.exceptions.InsufficientBalanceException;
import com.devsu.cuentas.domain.model.exceptions.NotFoundException;
import com.devsu.cuentas.infrastructure.entryPoints.rest.dto.response.error.APIErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<APIErrorResponse> handleNotFound(NotFoundException ex) {
        return buildErrorResponse(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(DuplicateAccountException.class)
    public ResponseEntity<APIErrorResponse> handleInactiveAccount(DuplicateAccountException ex) {
        return buildErrorResponse(HttpStatus.CONFLICT, ex.getMessage());
    }

    @ExceptionHandler(InactiveAccountException.class)
    public ResponseEntity<APIErrorResponse> handleInactiveAccount(InactiveAccountException ex, WebRequest request) {
        return buildErrorResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(InsufficientBalanceException.class)
    public ResponseEntity<APIErrorResponse> handleInsufficientBalance(InsufficientBalanceException ex) {
        return buildErrorResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<APIErrorResponse> handleSQLException(DataIntegrityViolationException ex) {
        String message = "No pudimos procesar tu solicitud. Revisa que toda la información proporcionada sea válida y que el cliente exista en el sistema.";
        return buildErrorResponse(HttpStatus.BAD_REQUEST, message);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<APIErrorResponse> handleGeneral(Exception ex, WebRequest request) {
        return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
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
