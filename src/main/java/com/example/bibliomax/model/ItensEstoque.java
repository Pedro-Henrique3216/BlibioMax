package com.example.bibliomax.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "itens_estoque")
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ItensEstoque {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @JoinColumn(name = "livro_id")
    @OneToOne
    private Livro livro;
    private Integer quantidade = 0;
    @Setter
    @Column(name = "minimo_quantidade")
    private Integer minimoQuantidade = 5;

    public ItensEstoque(Livro livro, Integer quantidade) {
        this.livro = livro;
        this.quantidade = quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade += quantidade;
    }
}
