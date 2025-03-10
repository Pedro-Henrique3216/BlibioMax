package com.example.bibliomax.dto;

import com.example.bibliomax.model.StatusPedido;
import com.example.bibliomax.model.TipoPagamento;
import com.example.bibliomax.model.TipoPedido;

import java.util.List;

public record PedidoResponse(
        TipoPagamento tipoPagamento,
        TipoPedido tipoPedido,
        List<ItensPedidoResponse> itens,
        double valorTotal,
        StatusPedido statusPedido
) {
}
