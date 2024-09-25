package com.example.bibliomax.dto;

import com.example.bibliomax.model.EntradaDto;
import com.example.bibliomax.model.ItensEntradaDto;
import jakarta.validation.Valid;

public record CadastroEntradaRequest(
        @Valid
        EntradaDto entradaDto,
        @Valid
        ItensEntradaDto itensEntradaDto
) {
}
