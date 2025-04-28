package com.example.bibliomax.service;

import com.example.bibliomax.model.RentalItem;
import com.example.bibliomax.repository.ItensPedidoRepoitory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RentalItemService {

    private final ItensPedidoRepoitory repository;

    @Autowired
    public RentalItemService(ItensPedidoRepoitory repository) {
        this.repository = repository;
    }

    public void saveAll(List<RentalItem> rentalItem) {
        repository.saveAll(rentalItem);
    }
}
