package com.example.bibliomax.service;

import com.example.bibliomax.exceptions.ObjectNotFoundException;
import com.example.bibliomax.model.*;
import com.example.bibliomax.repository.BibliotecarioRepository;
import com.example.bibliomax.repository.LivroRepository;
import com.example.bibliomax.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class BibliotecarioService {

    @Autowired
    private BibliotecarioRepository bibliotecarioRepository;

    @Autowired
    private LivroRepository livroRepository;

    @Autowired
    private UserRepository usuarioRepository;

    public Bibliotecario cadastrarBibliotecario(BibliotecarioDto dto) {
        User user = new User(dto.email(), dto.telefone(), Role.BIBLIOTECARIO);
        usuarioRepository.save(user);
        Bibliotecario bibliotecario = new Bibliotecario(dto.nome(), dto.telefone(), dto.numeroRegistro());
        bibliotecario.setUser(user);
        return bibliotecarioRepository.save(bibliotecario);
    }

    public List<Bibliotecario> listarBibliotecarios() {
        return bibliotecarioRepository.findAll();
    }

    public Bibliotecario buscarBibliotecarioPorId(Long id) {
        return bibliotecarioRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException("Bibliotecario não encontrado!"));
    }

    public Bibliotecario atualizaBibliotecario(Long id, BibliotecarioDto bibliotecarioDto) {
        Bibliotecario bibliotecario = buscarBibliotecarioPorId(id);
        if (bibliotecarioDto.nome() != null) {
            bibliotecario.setNome(bibliotecarioDto.nome());
        }
        if (bibliotecarioDto.telefone() != null) {
            bibliotecario.setTelefone(bibliotecarioDto.telefone());
        }
        return bibliotecario;
    }

    public Livro cadastrarLivro(Long id, Livro livro) {
        if(!bibliotecarioRepository.existsById(id)){
            throw new RuntimeException("Esse usuario não existe!");
        }
        return livroRepository.save(livro);
    }
}
