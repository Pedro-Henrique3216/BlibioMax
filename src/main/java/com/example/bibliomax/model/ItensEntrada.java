package com.example.bibliomax.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "itens_entrada")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@EqualsAndHashCode(of = "id")
public class ItensEntrada {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @JoinColumn(name = "entrada_id")
    @ManyToOne()
    private Entrada entrada;
    @ManyToOne()
    @JoinColumn(name = "livro_id")
    private Livro livro;
    @Setter
    private Double preco;
    private Integer quantidade;
    @Column(name = "valor_total")
    private Double valorTotal;

    public ItensEntrada(Entrada entrada, Livro livro, Integer quantidade, Double preco) {
        this.entrada = entrada;
        this.livro = livro;
        this.preco = preco;
        setQuantidade(quantidade);
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
        calculaValorTotal();
    }

    private void calculaValorTotal() {
        this.valorTotal = this.preco * this.quantidade;
    }
}
