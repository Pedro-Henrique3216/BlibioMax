package com.example.bibliomax.dto;

import com.example.bibliomax.model.StatusRental;
import com.example.bibliomax.model.TipoPagamento;

import java.math.BigDecimal;
import java.util.List;

public record PedidoResponse(
        List<ItensPedidoResponse> itens,
        BigDecimal value,
        StatusRental statusRental

) {
}
