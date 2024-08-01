package com.example.bibliomax.model;

public record LivroDto(
        String titulo,
        String autor,
        Integer quantidade,
        String editora
) {

    public Livro toLivro(){
        return new Livro(titulo, autor, quantidade, editora);
    }
}
