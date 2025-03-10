package com.example.bibliomax.exceptions;

public class PedidoCancelado extends RuntimeException{

    public PedidoCancelado(String message) {
        super(message);
    }
}
