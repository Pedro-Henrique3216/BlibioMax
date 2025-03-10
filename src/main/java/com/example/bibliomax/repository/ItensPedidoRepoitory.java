package com.example.bibliomax.repository;

import com.example.bibliomax.model.ItensPedido;
import com.example.bibliomax.model.ItensPedidoPK;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItensPedidoRepoitory extends JpaRepository<ItensPedido, ItensPedidoPK> {
}
