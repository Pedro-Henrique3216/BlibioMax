package com.example.bibliomax.service;

import com.example.bibliomax.model.*;
import com.example.bibliomax.repository.ItensEntradaRedisRepository;
import com.example.bibliomax.repository.ItensEntradaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ItensEntradaServiceTest {

    @Mock
    private LivroService livroService;

    @InjectMocks
    private ItensEntradaService itensEntradaService;

    @Mock
    private ItensEntradaRedisRepository itensEntradaRedisRepository;

    private Entrada invoiceEntry;
    private Livro book;
    private List<ItensEntrada> entryItems;


    @BeforeEach
    void setUp() {
        invoiceEntry = new Entrada(1L, new Bibliotecario());
        book = new Livro("teste", "Livro Teste", "tesdasd");
        book.setId(1L);
        entryItems = new ArrayList<>();
        ItensEntrada entryItem = new ItensEntrada(invoiceEntry, book, 3, 50.0);
        entryItems.add(entryItem);
    }

    @Test
    void verifyIfsaveItensEntradaInRedisDatabase() {
        ItensEntradaDto itensEntradaDto = new ItensEntradaDto(3, 50.0, book.getId());
        itensEntradaService.addItensEntrada(invoiceEntry, itensEntradaDto);
        verify(itensEntradaRedisRepository).save(entryItems, invoiceEntry.getNumeroNota());
    }

    @Test
    void verifyIfGetAllItensEntradaInRedisDatabase() {
        when(itensEntradaRedisRepository.getItensEntrada(invoiceEntry)).thenReturn(Optional.of(entryItems));
        assertEquals(itensEntradaService.buscarItensEntradas(invoiceEntry), entryItems);
    }
}