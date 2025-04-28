package com.example.bibliomax.dto;

import com.example.bibliomax.model.StatusEntrada;

import java.time.LocalDateTime;
import java.util.List;

public record RetornaEntradaItensDto(
        RetornaEntradaDto cadastroDto,
        LocalDateTime dataEntrada,
        List<RetornaItensDto> itensDtos,
        Double valorTotal,
        StatusEntrada status


) {
}
