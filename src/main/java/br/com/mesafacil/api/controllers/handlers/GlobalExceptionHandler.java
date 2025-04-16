package br.com.mesafacil.api.controllers.handlers;

import br.com.mesafacil.api.services.exceptions.ResourceNotFoundException;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiError> handleResourceNotFoundException(ResourceNotFoundException ex) {
        ApiError apiError = new ApiError(
                HttpStatus.NOT_FOUND.value(),
                ex.getMessage(),
                LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiError);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationError> handleValidationErrors(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        ValidationError validationError = new ValidationError(
                HttpStatus.BAD_REQUEST.value(),
                "Erro de validação",
                LocalDateTime.now(),
                errors
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(validationError);
    }

    // Classes de suporte para representar erros
    @Schema(name = "Erro", description = "Representação de erro padrão")
    public record ApiError(
            @Schema(description = "Código do status HTTP", example = "404")
            int status,

            @Schema(description = "Mensagem de erro", example = "Usuário não encontrado com ID: 1")
            String message,

            @Schema(description = "Data e hora do erro", example = "2023-09-21T14:30:15.123")
            LocalDateTime timestamp) {}

    @Schema(name = "Erro de Validação", description = "Representação de erro de validação")
    public record ValidationError(
            @Schema(description = "Código do status HTTP", example = "400")
            int status,

            @Schema(description = "Mensagem de erro", example = "Erro de validação")
            String message,

            @Schema(description = "Data e hora do erro", example = "2023-09-21T14:30:15.123")
            LocalDateTime timestamp,

            @Schema(description = "Lista de erros de validação por campo")
            Map<String, String> errors) {}
}