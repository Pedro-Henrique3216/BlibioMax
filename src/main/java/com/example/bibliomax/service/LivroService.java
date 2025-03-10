package com.example.bibliomax.service;

import com.example.bibliomax.dto.RetornaLivroDto;
import com.example.bibliomax.exceptions.JaCadastrado;
import com.example.bibliomax.exceptions.ObjetoNaoEncontrado;
import com.example.bibliomax.model.Livro;
import com.example.bibliomax.model.LivroDto;
import com.example.bibliomax.repository.LivroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class LivroService {

    private final LivroRepository repository;

    @Autowired
    public LivroService(LivroRepository repository) {
        this.repository = repository;
    }

    public Livro cadastrarLivro(LivroDto livroDto) {
        if(repository.existsByTitulo(livroDto.titulo())){
            throw new JaCadastrado("Esse livro ja esta cadastrado");
        }
        Livro livro = livroDto.toLivro();
        return repository.save(livro);
    }

    public Livro buscarLivroPorId(Long id) {
        return repository.findById(id).orElseThrow(() -> new ObjetoNaoEncontrado("Livro não encontrado"));

    }

    public Page<RetornaLivroDto> listarLivros(Pageable pageable) {
        return repository.findAll(pageable).map(RetornaLivroDto::new);
    }

    public void deletarLivroById(Long id) {
        repository.deleteById(id);
    }

    public void updateLivro(Livro livro) {
        repository.save(livro);
    }
}
