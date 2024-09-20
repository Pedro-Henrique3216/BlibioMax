package com.example.bibliomax.repository;

import com.example.bibliomax.model.ItensEntrada;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ItensEntradaRepository extends JpaRepository<ItensEntrada, Long> {

    @Query("""
        select case when count(1) > 0 then true else false end from ItensEntrada itensEntrada where itensEntrada.livro.id = :idLivro and itensEntrada.entrada.numeroNota = :entradaId
    """)
    boolean existsLivroForItensEntradaInEntrada(Long idLivro, Long entradaId);
}
