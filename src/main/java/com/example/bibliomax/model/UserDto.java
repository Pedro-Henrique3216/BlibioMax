package com.example.bibliomax.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record UserDto(
        @NotBlank(message = "email não pode estar vazio")
                @Email(message = "Email invalido")
        String email,
        @NotBlank(message = "senha não pode estar vazio")
        @Pattern(regexp = "^.{8,}$", message = "O campo deve ter no mínimo 8 caracteres")
        String senha
) {


}
