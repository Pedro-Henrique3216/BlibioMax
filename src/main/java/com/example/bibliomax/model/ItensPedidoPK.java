package com.example.bibliomax.model;

import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Embeddable
@Getter
@Setter
@EqualsAndHashCode(of = {"livro", "rentalOrder"})
public class ItensPedidoPK {

    @ManyToOne
    @JoinColumn(name = "livro_id")
    private Livro livro;
    @ManyToOne
    @JoinColumn(name = "pedido_id")
    private RentalOrder rentalOrder;

}
