package com.example.bibliomax.infra.exception;

import com.example.bibliomax.exceptions.JaCadastrado;
import com.example.bibliomax.exceptions.ObjetoNaoEncontrado;
import com.example.bibliomax.exceptions.PedidoCancelado;
import com.example.bibliomax.exceptions.QuantidadeObjetoNaoEncotrado;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
@Hidden
public class CustomExceptionHandler {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ObjetoNaoEncontrado.class)
    public ResponseEntity<String> handlerObjectNotFound(ObjetoNaoEncontrado ex) {
        return ResponseEntity.status(204).body(ex.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(JaCadastrado.class)
    public ResponseEntity<String> handlerAlreadyExists(JaCadastrado ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<ErroDto>> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        List<FieldError> fieldErrors = ex.getFieldErrors();
        return ResponseEntity.badRequest().body(fieldErrors.stream()
                .map(fieldError -> new ErroDto(fieldError.getField(), fieldError.getDefaultMessage()))
                .toList());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(QuantidadeObjetoNaoEncotrado.class)
    public ResponseEntity<String> handleQuantityObjectNotFound(QuantidadeObjetoNaoEncotrado ex){
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(PedidoCancelado.class)
    public ResponseEntity<String> handleCanceledOrder(PedidoCancelado ex){
        return ResponseEntity.badRequest().body(ex.getMessage());
    }
}
