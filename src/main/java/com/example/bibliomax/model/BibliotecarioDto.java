package com.example.bibliomax.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record BibliotecarioDto(
        @NotBlank(message = "Nome não pode estar vazio")
        String nome,
        @NotBlank(message = "email não pode estar vazio")
        @Email(message = "Email invalido")
        String email,
        @NotBlank(message = "senha não pode estar vazio")
        @Pattern(regexp = "^.{8,}$", message = "O campo deve ter no mínimo 8 caracteres")
        String senha,
        @NotBlank(message = "telefone não pode estar vazio")
        @Pattern(regexp = "\\d{11}", message = "O campo deve conter exatamente 11 números")
        String telefone,
        @NotNull(message = "numero do registro não pode estar vazio")
        @JsonAlias("numero_registro")
        Long numeroRegistro
) {

    public Bibliotecario toBibliotecario() {
        return new Bibliotecario(nome, telefone, numeroRegistro);
    }
}
