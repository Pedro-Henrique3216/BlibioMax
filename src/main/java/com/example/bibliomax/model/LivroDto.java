package com.example.bibliomax.model;

public record LivroDto(
        String titulo,
        String autor,
        String editora
) {

    public Livro toLivro(){
        return new Livro(titulo, autor, editora);
    }
}
