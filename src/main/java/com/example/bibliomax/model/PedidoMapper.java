package com.example.bibliomax.model;

import com.example.bibliomax.dto.ItensPedidoResponse;
import com.example.bibliomax.dto.PedidoResponse;

public class PedidoMapper {

    public static PedidoResponse toResponse(Pedido pedido) {
        return new PedidoResponse(
                pedido.getPagamento().getTipo(),
                pedido.getTipoPedido(),
                pedido.getItensPedido().stream()
                        .map(itensPedido -> new ItensPedidoResponse(
                                itensPedido.getId().getLivro().getTitulo(),
                                itensPedido.getQuantidade(),
                                itensPedido.getValor()
                        )).toList(),
                pedido.getValor(),
                pedido.getStatusPedido()
        );
    }
}
