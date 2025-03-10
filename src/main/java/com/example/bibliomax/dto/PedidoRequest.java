package com.example.bibliomax.dto;

import com.example.bibliomax.model.TipoPagamento;
import com.example.bibliomax.model.TipoPedido;

import java.util.List;

public record PedidoRequest(
    TipoPagamento tipoPagamento,
    TipoPedido tipoPedido,
    List<ItensPedidoRequest> itens
) {
}
