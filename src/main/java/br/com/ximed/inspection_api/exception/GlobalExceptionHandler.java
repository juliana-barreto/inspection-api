package br.com.ximed.inspection_api.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // 404 - Recurso não encontrado
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> resourceNotFound(ResourceNotFoundException ex, HttpServletRequest req) {
        return buildResponse(HttpStatus.NOT_FOUND, "Resource not found", ex.getMessage(), req);
    }

    // 400 - Dados / Argumentos inválidos
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> illegalArgument(IllegalArgumentException ex, HttpServletRequest req) {
        return buildResponse(HttpStatus.BAD_REQUEST, "Invalid data", ex.getMessage(), req);
    }

    // 400 - Erro de Banco de Dados
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponse> databaseError(DataIntegrityViolationException ex, HttpServletRequest req) {
        return buildResponse(HttpStatus.BAD_REQUEST, "Database error", ex.getMessage(), req);
    }

    // 422 - Violação de Regra de Negócio
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponse> businessRule(BusinessException ex, HttpServletRequest req) {
        return buildResponse(HttpStatus.UNPROCESSABLE_CONTENT, "Business rule violation", ex.getMessage(), req);
    }

    // 422 - Erros de Validação de DTO (@Valid)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationErrors(MethodArgumentNotValidException ex, HttpServletRequest req) {
        List<FieldMessage> errors = ex.getBindingResult().getFieldErrors().stream()
                .map(f -> new FieldMessage(f.getField(), f.getDefaultMessage()))
                .toList();

        ErrorResponse body = new ErrorResponse(
                HttpStatus.UNPROCESSABLE_CONTENT,
                "Validation Error",
                "There are validation errors in the fields below",
                req.getRequestURI(),
                errors
        );

        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_CONTENT).body(body);
    }

    // 500 - Erro Interno Genérico
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception ex, HttpServletRequest req) {
        return buildResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error", ex.getMessage(), req);
    }

    // Método auxiliar centralizado para evitar boilerplate
    private ResponseEntity<ErrorResponse> buildResponse(HttpStatus status, String title, String message, HttpServletRequest req) {
        ErrorResponse body = new ErrorResponse(status, title, message, req.getRequestURI());
        return ResponseEntity.status(status).body(body);
    }
}
