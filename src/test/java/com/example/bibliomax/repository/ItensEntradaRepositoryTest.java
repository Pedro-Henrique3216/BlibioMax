package com.example.bibliomax.repository;

import com.example.bibliomax.model.*;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
class ItensEntradaRepositoryTest {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private ItensEntradaRepository repository;

    @Test
    void existsLivroForItensEntradaInEntrada() {
        BibliotecarioDto dto = new BibliotecarioDto("Joao", "gsdgsd", "12345678", "11912345678", 123L);
        ItensEntradaDto data = new ItensEntradaDto(12345L, 3, 5L);
        createItensEntrada(data, dto);
        assertTrue(repository.existsLivroForItensEntradaInEntrada(1L, 12345L));
    }

    @Test
    void notExistsLivroForItensEntradaInEntrada() {
        BibliotecarioDto dto = new BibliotecarioDto("Joao", "gsdgsd", "12345678", "11912345678", 123L);
        ItensEntradaDto data = new ItensEntradaDto(12345L, 3, 5L);
        createItensEntrada(data, dto);
        assertFalse(repository.existsLivroForItensEntradaInEntrada(5L, 12345L));
    }

    private ItensEntrada createItensEntrada(ItensEntradaDto dto, BibliotecarioDto dto1) {
        User user = new User(dto1.email(), dto1.senha(), Role.BIBLIOTECARIO);
        this.entityManager.persist(user);
        Bibliotecario bibliotecario = new Bibliotecario(dto1.nome(), dto1.telefone(), dto1.numeroRegistro());
        bibliotecario.setUser(user);
        this.entityManager.persist(bibliotecario);
        Entrada entrada = new Entrada(12345L, bibliotecario);
        this.entityManager.persist(entrada);
        Livro livro = new Livro("Percy", "dafa", "fdfsdf");
        this.entityManager.persist(livro);
        ItensEntrada itensEntrada = new ItensEntrada(entrada, livro, dto.quantidade());
        entityManager.persist(itensEntrada);
        return itensEntrada;
    }
}