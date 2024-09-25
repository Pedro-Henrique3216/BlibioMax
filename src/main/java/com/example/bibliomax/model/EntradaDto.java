package com.example.bibliomax.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;

public record EntradaDto(
        @NotNull(message = "numero da nota não pode estar vazio")
        @JsonProperty("numero_da_nota")
        Long numeroNota,
        @NotNull(message = "id do bibliotecario não pode estar vazio")
        Long bibiliotecarioId
) {
}
