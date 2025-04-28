package com.example.bibliomax.repository;

import com.example.bibliomax.model.RentalOrder;
import com.example.bibliomax.model.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface PedidoRepository extends JpaRepository<RentalOrder, Long> {

    List<RentalOrder> findAllByPerson(Pessoa pessoa);

    List<RentalOrder> findAllByOrderDateBetweenAndPerson(LocalDate from, LocalDate to, Pessoa pessoa);
}
