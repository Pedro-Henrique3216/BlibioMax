package com.example.bibliomax.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "pedidos")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "pedido",  orphanRemoval = true)
    private Pagamento pagamento;
    @ManyToOne
    @JoinColumn(name = "pessoa_id")
    private Pessoa pessoa;
    @Column(name = "tipo_pedido")
    private TipoPedido tipoPedido;
    @OneToMany(mappedBy = "id.pedido", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ItensPedido> itensPedido = new ArrayList<>();
    private Double valor;
    @Column(name = "status_pedido")
    private StatusPedido statusPedido;
    @Column(name = "date_pedido")
    private LocalDate datePedido  = LocalDate.now();

    public Pedido(Pagamento pagamento, Pessoa pessoa, TipoPedido tipoPedido, Double valor) {
        this.pagamento = pagamento;
        this.pessoa = pessoa;
        this.tipoPedido = tipoPedido;
        this.valor = valor;
        this.statusPedido = StatusPedido.CRIADO;
    }

    public void addItensPedido(ItensPedido itensPedido) {
        this.itensPedido.add(itensPedido);
        itensPedido.getId().setPedido(this);
    }
}
