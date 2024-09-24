package com.example.bibliomax.dto;

import com.example.bibliomax.model.StatusEntrada;

import java.time.LocalDate;
import java.util.List;

public record RetornaEntradaItensDto(
        RetornaEntradaDto cadastroDto,
        LocalDate dataEntrada,
        List<RetornaItensDto> itensDtos,
        Double valorTotal,
        StatusEntrada status


) {
}
