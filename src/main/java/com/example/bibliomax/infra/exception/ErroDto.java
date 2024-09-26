package com.example.bibliomax.infra.exception;

public record ErroDto(
        String campo,
        String mensagem
) {
}
