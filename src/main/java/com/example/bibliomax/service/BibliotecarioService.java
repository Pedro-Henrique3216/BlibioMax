package com.example.bibliomax.service;

import com.example.bibliomax.exceptions.ObjetoNaoEncontrado;
import com.example.bibliomax.model.*;
import com.example.bibliomax.repository.BibliotecarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class BibliotecarioService {

    private final BibliotecarioRepository bibliotecarioRepository;

    private final LivroService livroService;

    private final UserService userService;

    @Autowired
    public BibliotecarioService(BibliotecarioRepository bibliotecarioRepository, LivroService livroService, UserService userService) {
        this.bibliotecarioRepository = bibliotecarioRepository;
        this.livroService = livroService;
        this.userService = userService;
    }

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
        return bibliotecarioRepository.findById(id).orElseThrow(() -> new ObjetoNaoEncontrado("Bibliotecario não encontrado!"));
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
        return livroService.cadastrarLivro(livroDto);
    }

    public Bibliotecario findByUsername(String username) {
        return bibliotecarioRepository.findByUserUsername(username);
    }

}
