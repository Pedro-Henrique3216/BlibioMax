package com.example.bibliomax.dto;

import com.example.bibliomax.model.Entrada;

import java.util.List;

public record RetornoCadastroDto(
        RetornaEntradaDto entrada,
        List<RetornaItensDto> itensEntrada
) {
}
