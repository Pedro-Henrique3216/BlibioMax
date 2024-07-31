package com.example.bibliomax.model;

public record BibliotecarioDto(
        String nome,
        String email,
        String senha,
        String telefone,
        Long numeroRegistro
) {

    public Bibliotecario toBibliotecario() {
        return new Bibliotecario(nome, telefone, numeroRegistro);
    }
}
