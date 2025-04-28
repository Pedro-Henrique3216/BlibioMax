package com.example.bibliomax.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "pagamentos")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@EqualsAndHashCode(of = "id")
@Setter
public class Pagamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private TipoPagamento tipo;
    private Double valor;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pedido_id")
    private RentalOrder rentalOrder;
    @Column(name = "data_criacao")
    private LocalDateTime dataCriacao = LocalDateTime.now();
    @Column(name = "data_pagamento")
    private LocalDateTime dataPagamento;
    public Pagamento(TipoPagamento tipo, Double valor) {
        this.tipo = tipo;
        this.valor = valor;
    }
}
