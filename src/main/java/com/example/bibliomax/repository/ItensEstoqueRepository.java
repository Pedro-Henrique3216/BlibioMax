package com.example.bibliomax.repository;

import com.example.bibliomax.model.ItensEstoque;
import com.example.bibliomax.model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ItensEstoqueRepository extends JpaRepository<ItensEstoque, Long> {

    boolean existsByLivro(Livro livro);

    ItensEstoque findByLivroId(Long id);



}
