package com.example.bibliomax.dto;

import com.example.bibliomax.model.Livro;

public record RetornaLivroDto(
        Long id,
        String titulo,
        String autor,
        String editora
) {
    public RetornaLivroDto(Livro livro) {
        this(livro.getId(), livro.getTitulo(), livro.getAutor(), livro.getEditora());
    }
}
