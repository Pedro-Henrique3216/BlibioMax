package com.example.bibliomax.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "entrada")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@EqualsAndHashCode(of = "numeroNota")
public class Entrada {
    @Id
    @Column(name = "numero_da_nota")
    private Long numeroNota;
    @ManyToOne
    @JoinColumn(name = "bibliotecario_id")
    private Bibliotecario bibliotecario;
    @Column(name = "valor_total")
    private Double valorTotal = 0.0;
    @Column(name = "data_entrada")
    private LocalDate dataEntrada;
    @Setter
    @Enumerated(EnumType.STRING)
    StatusEntrada status = StatusEntrada.EM_ANDAMENTO;

    public Entrada(Long numeroNota, Bibliotecario bibliotecario) {
        this.numeroNota = numeroNota;
        this.bibliotecario = bibliotecario;
        dataEntrada = LocalDate.now();
    }

    public void setValorTotal(Double valorTotal) {
        this.valorTotal = getValorTotal() + valorTotal;
    }
}
