package com.example.bibliomax.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Table(name = "pessoas")
@Entity(name = "user")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id", callSuper = false)
@Inheritance(strategy = InheritanceType.JOINED)
public class Pessoa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
    private String telefone;
    @OneToMany
    private List<Livro> livrosAlugados = new ArrayList<>();

    public void validaSePodeAlugar(){
        if(livrosAlugados.size() > 5){
            throw new RuntimeException("Você não pode alugar mais livros");
        }
    }
}
