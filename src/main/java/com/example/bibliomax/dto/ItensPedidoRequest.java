package com.example.bibliomax.dto;

public record ItensPedidoRequest(
        Long livroId,
        Integer quantidade
) {
}
