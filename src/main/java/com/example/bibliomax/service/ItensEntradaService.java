package com.example.bibliomax.service;

import com.example.bibliomax.dto.RetornaItensDto;
import com.example.bibliomax.model.*;
import com.example.bibliomax.repository.ItensEntradaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ItensEntradaService {

    private final ItensEntradaRepository repository;

    private final LivroService livroService;

    private final ItensEstoqueService itensEstoqueService;

    private final List<ItensEntrada> itensEntradas = new ArrayList<>();

    @Autowired
    public ItensEntradaService(ItensEntradaRepository repository, LivroService livroService, ItensEstoqueService itensEstoqueService) {
        this.repository = repository;
        this.livroService = livroService;
        this.itensEstoqueService = itensEstoqueService;
    }

    public void addItensEntrada(Entrada entrada, ItensEntradaDto itensEntradaDto) {
        Livro livro = livroService.buscarLivroPorId(itensEntradaDto.livroId());
        ItensEntrada itensEntrada = new ItensEntrada(entrada, livro, itensEntradaDto.quantidade(), itensEntradaDto.preco());

        boolean jaTemItem = false;
        for(ItensEntrada item : itensEntradas) {
            if(item.getLivro().equals(itensEntrada.getLivro()) & item.getEntrada().equals(itensEntrada.getEntrada())) {
                item.setQuantidade(item.getQuantidade() + itensEntrada.getQuantidade());
                if (itensEntrada.getPreco() != 0) {
                    item.setPreco(itensEntrada.getPreco());
                }
                jaTemItem = true;
            }
        }
        if(!jaTemItem) {
            if (itensEntrada.getPreco() == 0){
                itensEntrada.setPreco(livro.getPreco());
            }
            itensEntradas.add(itensEntrada);
        }

    }

    public List<ItensEntrada> buscarItensEntradas(Entrada entrada) {
        return itensEntradas.stream().filter(itensEntrada -> itensEntrada.getEntrada().equals(entrada)).toList();
    }

    public void saveItensEntrada(Entrada entrada) {
        List<ItensEntrada> listItensEntradas = buscarItensEntradas(entrada);
        for (ItensEntrada item : listItensEntradas) {
            entrada.setValorTotal(item.getValorTotal());
            Livro livro = item.getLivro();
            livro.setPreco(item.getPreco());
            livroService.updateLivro(livro);
        }
        itensEstoqueService.addEstoque(listItensEntradas);
        repository.saveAll(listItensEntradas);
        itensEntradas.removeAll(listItensEntradas);
    }

    public List<RetornaItensDto> buscaTodosItensPorEntrada(Entrada entrada) {
        return repository.findByEntrada(entrada).stream().map(item ->
                new RetornaItensDto(
                        item.getLivro().getId(),
                        item.getLivro().getTitulo(),
                        item.getPreco(),
                        item.getQuantidade(),
                        item.getValorTotal()
                )
        ).toList();
    }
}
