package com.example.bibliomax.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Table(name = "rental-item")
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class RentalItem {

    @EmbeddedId
    private ItensPedidoPK id;

    private LocalDate dueDate;
    private LocalDate returnDate;
    private BigDecimal lateFee;

    public RentalItem(ItensPedidoPK id) {
        this.id = id;
        dueDate = LocalDate.now();
        returnDate = LocalDate.now().plusDays(7);
        lateFee = BigDecimal.ZERO;
    }

    public BigDecimal calculateLateFee(BigDecimal fee) {
        if (LocalDate.now().isAfter(returnDate)){
            long rentalDays = ChronoUnit.DAYS.between(returnDate, LocalDate.now());
            this.lateFee = BigDecimal.valueOf(rentalDays).multiply(fee);
        }
        return lateFee;
    }
}
