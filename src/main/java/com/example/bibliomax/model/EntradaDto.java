package com.example.bibliomax.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public record EntradaDto(
        @JsonProperty("numero_da_nota")
        Long numeroNota
) {
}
