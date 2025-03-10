package com.example.bibliomax.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "itens_pedidos")
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ItensPedido {

    @EmbeddedId
    private ItensPedidoPK id;
    private Integer quantidade;
    private Double valor;

}
