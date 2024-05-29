package com.example.bibliomax.service;

import com.example.bibliomax.exceptions.ObjectNotFoundException;
import com.example.bibliomax.model.Bibliotecario;
import com.example.bibliomax.repository.BibliotecarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BibliotecarioService {

    @Autowired
    private BibliotecarioRepository bibliotecarioRepository;

    public Bibliotecario cadastrarBibliotecario(Bibliotecario bibliotecario) {
        return bibliotecarioRepository.save(bibliotecario);
    }

    public List<Bibliotecario> listarBibliotecarios() {
        return bibliotecarioRepository.findAll();
    }

    public Bibliotecario buscarBibliotecarioPorId(Long id) {
        return bibliotecarioRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException("Bibliotecario não encontrado!"));
    }
}
