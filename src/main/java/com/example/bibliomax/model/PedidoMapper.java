package com.example.bibliomax.model;

import com.example.bibliomax.dto.ItensPedidoResponse;
import com.example.bibliomax.dto.PedidoResponse;

public class PedidoMapper {

    public static PedidoResponse toResponse(RentalOrder rentalOrder) {
        return new PedidoResponse(
                rentalOrder.getRentalBooks().stream()
                        .map(itensPedido -> new ItensPedidoResponse(
                                itensPedido.getId().getLivro().getTitulo(),
                                1
                        )).toList(),
                rentalOrder.getTotal(),
                rentalOrder.getStatusRental()
        );
    }
}
