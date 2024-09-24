package com.example.bibliomax.service;

import com.example.bibliomax.dto.RetornaLivroDto;
import com.example.bibliomax.model.Livro;
import com.example.bibliomax.model.LivroDto;
import com.example.bibliomax.repository.LivroRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class LivroService {

    @Autowired
    private LivroRepository repository;

    public Livro cadastrarLivro(LivroDto livroDto) {
        if(existeLivroComTitulo(livroDto.titulo())){
            throw new RuntimeException("Esse livro ja esta cadastrado");
        }
        Livro livro = livroDto.toLivro();
        return repository.save(livro);
    }

    public Livro buscarLivroPorId(Long id) {
        return repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Livro não encontrado"));

    }

    private Boolean existeLivroComTitulo(String titulo) {
        return repository.existsByTitulo(titulo);
    }

    public Page<RetornaLivroDto> listarLivros(Pageable pageable) {
        return repository.findAll(pageable).map(RetornaLivroDto::new);
    }

    public void deletarLivroById(Long id) {
        repository.deleteById(id);
    }
}
