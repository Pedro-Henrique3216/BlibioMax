package com.example.bibliomax.repository;

import com.example.bibliomax.model.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PessoaRepository extends JpaRepository<Pessoa, Long> {

    Pessoa findByUserUsername(String email);

}
