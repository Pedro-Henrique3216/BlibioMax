package com.example.bibliomax.dto;

import java.util.List;

public record PedidoRequest(
    List<ItensPedidoRequest> itens
) {
}
