package com.example.bibliomax.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "itens_entrada")
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ItensEntrada {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @JoinColumn(name = "entrada_id")
    @ManyToOne()
    private Entrada entrada;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "livro_id")
    private Livro livro;
    private Integer quantidade;
    @Column(name = "valor_total")
    private Double valorTotal;

    public ItensEntrada(Entrada entrada, Livro livro, Integer quantidade) {
        this.entrada = entrada;
        this.livro = livro;
        this.quantidade = quantidade;
        calculaValorTotal();
    }

    private void calculaValorTotal() {
        this.valorTotal = livro.getPreco() * quantidade;
    }
}
