package com.example.bibliomax.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "rental-orders")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
public class RentalOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "rentalOrder",  orphanRemoval = true)
    private Pagamento payment;
    @ManyToOne
    @JoinColumn(name = "pessoa_id")
    private Pessoa person;
    @OneToMany(mappedBy = "id.rentalOrder", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RentalItem> rentalBooks = new ArrayList<>();
    @Column(name = "status_pedido")
    @Enumerated(EnumType.STRING)
    private StatusRental statusRental;
    @Column(name = "date_pedido")
    private LocalDate orderDate = LocalDate.now();
    private BigDecimal total;

    public RentalOrder(Pagamento payment, Pessoa person) {
        this.payment = payment;
        this.person = person;
        this.statusRental = StatusRental.CRIADO;
        this.total = BigDecimal.ZERO;
    }

    public void addItensPedido(RentalItem rentalItem) {
        this.rentalBooks.add(rentalItem);
        rentalItem.getId().setRentalOrder(this);
    }
}
