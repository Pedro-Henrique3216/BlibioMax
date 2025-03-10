package com.example.bibliomax.repository;

import com.example.bibliomax.model.Pedido;
import com.example.bibliomax.model.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {

    List<Pedido> findAllByPessoa(Pessoa pessoa);

    List<Pedido> findAllByDatePedidoBetweenAndPessoa(LocalDate from, LocalDate to, Pessoa pessoa);
}
