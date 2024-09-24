package com.example.bibliomax.dto;

public record RetornaItensDto(
        Long livro_id,
        String titulo,
        Double preco,
        Integer quantidade,
        Double valor_total
) {
}
