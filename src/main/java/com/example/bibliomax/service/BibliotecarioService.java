package com.example.bibliomax.service;

import com.example.bibliomax.model.Bibliotecario;
import com.example.bibliomax.repository.BibliotecarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BibliotecarioService {

    @Autowired
    BibliotecarioRepository bibliotecarioRepository;

    public Bibliotecario cadastrarBibliotecario(Bibliotecario bibliotecario) {
        return bibliotecarioRepository.save(bibliotecario);
    }
}
