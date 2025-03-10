package com.example.bibliomax.repository;

import com.example.bibliomax.model.*;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@ActiveProfiles("test")
class ItensEntradaRepositoryTest {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private ItensEntradaRepository repository;

    @BeforeEach
    void setUp() {
        BibliotecarioDto dto = new BibliotecarioDto("Joao", "gsdgsd", "12345678", "11912345678", 123L);
        ItensEntradaDto data = new ItensEntradaDto(3, 200.0, 5L);
        User user = new User(dto.email(), dto.senha(), Role.BIBLIOTECARIO);
        this.entityManager.persist(user);
        Bibliotecario bibliotecario = new Bibliotecario(dto.nome(), dto.telefone(), dto.numeroRegistro());
        bibliotecario.setUser(user);
        this.entityManager.persist(bibliotecario);
        Entrada entrada = new Entrada(12345L, bibliotecario);
        this.entityManager.persist(entrada);
        Livro livro = new Livro("Percy", "dafa", "fdfsdf");
        this.entityManager.persist(livro);
        ItensEntrada itensEntrada = new ItensEntrada(entrada, livro, data.quantidade(), data.preco());
        entityManager.persist(itensEntrada);
    }

    @Test
    void existsLivroForItensEntradaInEntrada() {
        assertTrue(repository.existsLivroForItensEntradaInEntrada(1L, 12345L));
    }

    @Test
    void notExistsLivroForItensEntradaInEntrada() {
        assertFalse(repository.existsLivroForItensEntradaInEntrada(5L, 12345L));
    }

}