package com.example.bibliomax.dto;

import com.example.bibliomax.model.Bibliotecario;

public record RetornaBibliotecarioDto(
        Long id,
        String nome,
        Long numeroRegistro
) {
    public RetornaBibliotecarioDto(Bibliotecario bibliotecario) {
        this(bibliotecario.getId(), bibliotecario.getNome(), bibliotecario.getNumeroRegistro());
    }
}
