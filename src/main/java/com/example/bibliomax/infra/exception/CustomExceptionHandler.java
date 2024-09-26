package com.example.bibliomax.infra.exception;

import com.example.bibliomax.exceptions.JaCadastrado;
import com.example.bibliomax.exceptions.ObjetoNaoEncontrado;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;

@ControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(ObjetoNaoEncontrado.class)
    public ResponseEntity<String> handleObjetoNaoEncontrado(ObjetoNaoEncontrado ex) {
        return ResponseEntity.status(204).body(ex.getMessage());
    }

    @ExceptionHandler(JaCadastrado.class)
    public ResponseEntity<String> handleJaCadastrado(JaCadastrado ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<ErroDto>> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        List<FieldError> fieldErrors = ex.getFieldErrors();
        return ResponseEntity.badRequest().body(fieldErrors.stream()
                .map(fieldError -> new ErroDto(fieldError.getField(), fieldError.getDefaultMessage()))
                .toList());
    }
}
