package com.example.bibliomax.dto;

import com.example.bibliomax.model.EntradaDto;
import com.example.bibliomax.model.ItensEntradaDto;

public record CadastroEntradaRequest(
        EntradaDto entradaDto,
        ItensEntradaDto itensEntradaDto
) {
}
