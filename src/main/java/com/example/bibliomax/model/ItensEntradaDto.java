package com.example.bibliomax.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ItensEntradaDto(
        @JsonProperty("numero_da_nota")
        Long numeroNota,
        Integer quantidade,
        @JsonProperty("livro_id")
        Long livroId
) {
}
