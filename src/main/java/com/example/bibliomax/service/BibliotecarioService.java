package com.example.bibliomax.service;

import com.example.bibliomax.model.*;
import com.example.bibliomax.repository.BibliotecarioRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BibliotecarioService {

    @Autowired
    private BibliotecarioRepository bibliotecarioRepository;

    @Autowired
    private LivroService livroService;

    @Autowired
    private UserService userService;

    public Bibliotecario cadastrarBibliotecario(BibliotecarioDto dto) {
        User user = userService.cadastrarUser(dto.email(), dto.senha(), Role.BIBLIOTECARIO);
        Bibliotecario bibliotecario = new Bibliotecario(dto.nome(), dto.telefone(), dto.numeroRegistro());
        bibliotecario.setUser(user);
        return bibliotecarioRepository.save(bibliotecario);
    }

    public Page<Bibliotecario> listarBibliotecarios(Pageable pageable) {
        return bibliotecarioRepository.findAll(pageable);
    }

    public Bibliotecario buscarBibliotecarioPorId(Long id) {
        return bibliotecarioRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Bibliotecario não encontrado!"));
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

    public Livro cadastrarLivro(LivroDto livroDto) {
        return livroService.cadastrarLivro(livroDto.toLivro());
    }

}
