package com.example.bibliomax.service;

import com.example.bibliomax.model.Bibliotecario;
import com.example.bibliomax.model.Entrada;
import com.example.bibliomax.model.EntradaDto;
import com.example.bibliomax.repository.EntradaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EntradaService {

    @Autowired
    private EntradaRepository repository;

    public Entrada criarEntrada(EntradaDto entradaDto, Bibliotecario bibliotecario) {
        Entrada entrada = new Entrada(entradaDto.numeroNota(), bibliotecario);
        return repository.save(entrada);
    }

    public Boolean existeEntrada(Long numeroNota) {
        return repository.existsByNumeroNota(numeroNota);
    }

    public Entrada buscarEntrada(Long numeroNota) {
        return repository.findById(numeroNota).orElseThrow(() -> new RuntimeException("Entrada não encontrada"));
    }
}
