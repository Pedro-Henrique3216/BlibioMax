package com.example.bibliomax.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ItensEstoqueDto(
        @JsonProperty("id_livro")
        Long idLivro,
        String titulo,
        Integer quantidade,
        @JsonProperty("minimo_quantidade")
        Integer minimoQuantidade
) {
}
