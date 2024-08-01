package com.example.bibliomax.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "livros")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Livro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titulo;
    private String autor;
    private Integer quantidade;
    private String editora;

    public Livro(String titulo, String autor, Integer quantidade, String editora) {
        this.titulo = titulo;
        this.autor = autor;
        this.quantidade = quantidade;
        this.editora = editora;
    }

}
