package com.example.bibliomax.model;

import jakarta.validation.constraints.NotBlank;

public record LivroDto(
        @NotBlank(message = "titulo do livro não pode estar vazio")
        String titulo,
        @NotBlank(message = "autor do livro não pode estar vazio")
        String autor,
        @NotBlank(message = "editora não pode estar vazio")
        String editora
) {

    public Livro toLivro(){
        return new Livro(titulo, autor, editora);
    }
}
