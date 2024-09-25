package com.example.bibliomax.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;

public record ItensEntradaDto(
        Integer quantidade,
        Double preco,
        @NotNull(message = "id do livro não pode estar vazio")
        @JsonProperty("livro_id")
        Long livroId
) {
        public ItensEntradaDto {
                if (quantidade == null){
                        quantidade = 1;
                }
                if (preco == null){
                        preco = 0.0;
                }
                if (livroId == null){
                        throw new RuntimeException("id do livro não pode ser null");
                }
        }
}
