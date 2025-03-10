package com.example.bibliomax.service;

import com.example.bibliomax.model.Pessoa;
import com.example.bibliomax.repository.PessoaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PessoaService {

    private final PessoaRepository repository;

    @Autowired
    public PessoaService(PessoaRepository repository) {
        this.repository = repository;
    }

    public Pessoa findByUsername(String username) {
        return repository.findByUserUsername(username);
    }

}
