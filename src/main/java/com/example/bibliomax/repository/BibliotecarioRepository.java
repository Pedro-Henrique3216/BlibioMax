package com.example.bibliomax.repository;

import com.example.bibliomax.model.Bibliotecario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BibliotecarioRepository extends JpaRepository<Bibliotecario, Long> {

    Bibliotecario findByUserUsername(String username);
}
