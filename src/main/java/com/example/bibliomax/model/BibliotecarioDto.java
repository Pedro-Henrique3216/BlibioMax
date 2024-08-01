package com.example.bibliomax.model;

import com.fasterxml.jackson.annotation.JsonAlias;

public record BibliotecarioDto(
        String nome,
        String email,
        String senha,
        String telefone,
        @JsonAlias("numero_registro")
        Long numeroRegistro
) {

    public Bibliotecario toBibliotecario() {
        return new Bibliotecario(nome, telefone, numeroRegistro);
    }
}
