package br.com.ximed.inspection_api.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;

record FieldMessage(String field, String message) {}

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public record ErrorResponse(
        LocalDateTime timestamp,
        Integer status,
        String error,
        String message,
        String path,
        List<FieldMessage> errors
) {
    public ErrorResponse(HttpStatus status, String error, String message, String path) {
        this(LocalDateTime.now(), status.value(), error, message, path, List.of());
    }

    public ErrorResponse(HttpStatus status, String error, String message, String path, List<FieldMessage> errors) {
        this(LocalDateTime.now(), status.value(), error, message, path, errors);
    }
}
