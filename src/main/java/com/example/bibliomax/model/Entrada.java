package com.example.bibliomax.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "entrada")
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Entrada {
    @Id
    @Column(name = "numero_da_nota")
    private Long numeroNota;
    @ManyToOne
    @JoinColumn(name = "bibliotecario_id")
    private Bibliotecario bibliotecario;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ItensEntrada> livros = new ArrayList<>();
    @Column(name = "valor_total")
    private Double valorTotal;
    @Column(name = "data_entrada")
    private LocalDate dataEntrada;

    public Entrada(Long numeroNota, Bibliotecario bibliotecario) {
        this.numeroNota = numeroNota;
        this.bibliotecario = bibliotecario;
        dataEntrada = LocalDate.now();
    }
}
