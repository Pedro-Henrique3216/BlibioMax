package com.example.bibliomax.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "Bibliotecarios")
@Table(name = "bibliotecarios")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Bibliotecario extends Pessoa{
    
    private Long numeroRegistro;

    public Bibliotecario(String nome, String telefone, Long numeroRegistro) {
        this.setNome(nome);
        this.setTelefone(telefone);
        this.numeroRegistro = numeroRegistro;
    }
}
