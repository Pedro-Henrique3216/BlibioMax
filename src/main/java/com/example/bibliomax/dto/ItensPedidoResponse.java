package com.example.bibliomax.dto;

public record ItensPedidoResponse(
        String livro,
        int quantidade,
        double valor
) {
}
