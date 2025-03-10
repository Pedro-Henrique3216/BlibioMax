package com.example.bibliomax.service;

import com.example.bibliomax.model.ItensPedido;
import com.example.bibliomax.repository.ItensPedidoRepoitory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItensPedidoService {

    private final ItensPedidoRepoitory repository;

    @Autowired
    public ItensPedidoService(ItensPedidoRepoitory repository) {
        this.repository = repository;
    }

    public void saveAll(List<ItensPedido> itensPedido) {
        repository.saveAll(itensPedido);
    }
}
