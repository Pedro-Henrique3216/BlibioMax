package com.example.bibliomax.repository;

import com.example.bibliomax.model.Entrada;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EntradaRepository extends JpaRepository<Entrada, Long> {

    Boolean existsByNumeroNota(Long numeroNota);
}
