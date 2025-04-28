package com.example.bibliomax.service;

import com.example.bibliomax.dto.RetornaItensDto;
import com.example.bibliomax.model.*;
import com.example.bibliomax.repository.ItensEntradaRedisRepository;
import com.example.bibliomax.repository.ItensEntradaRepository;
import com.example.bibliomax.repository.ItensEstoqueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.naming.OperationNotSupportedException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ItensEntradaService {

    private final ItensEntradaRepository repository;

    private final LivroService livroService;

    private final ItensEstoqueService itensEstoqueService;

    private final ItensEntradaRedisRepository itensEntradaRedisRepository;

    @Autowired
    public ItensEntradaService(ItensEntradaRepository repository, LivroService livroService, ItensEstoqueService itensEstoqueService, ItensEntradaRedisRepository itensEntradaRedisRepository) {
        this.repository = repository;
        this.livroService = livroService;
        this.itensEstoqueService = itensEstoqueService;
        this.itensEntradaRedisRepository = itensEntradaRedisRepository;
    }

    public void addItensEntrada(Entrada entrada, ItensEntradaDto itensEntradaDto) {
        List<ItensEntrada> itensEntradas = itensEntradaRedisRepository.getItensEntrada(entrada).orElse(new ArrayList<>());
        Livro livro = livroService.buscarLivroPorId(itensEntradaDto.livroId());
        ItensEntrada itemEntrada = new ItensEntrada(entrada, livro, itensEntradaDto.quantidade(), itensEntradaDto.preco());

        boolean jaTemItem = false;
        for(ItensEntrada item : itensEntradas) {
            if(item.getLivro().equals(itemEntrada.getLivro()) & item.getEntrada().equals(itemEntrada.getEntrada())) {
                item.setQuantidade(item.getQuantidade() + itemEntrada.getQuantidade());
                if (itemEntrada.getPreco() != 0) {
                    item.setPreco(itemEntrada.getPreco());
                }
                jaTemItem = true;
                break;
            }
        }
        if(!jaTemItem) {
            if (itemEntrada.getPreco() == 0){
                itemEntrada.setPreco(livro.getPreco());
            }
            itensEntradas.add(itemEntrada);
        }
        itensEntradaRedisRepository.save(itensEntradas, entrada.getNumeroNota());
    }


    public void saveItensEntrada(Entrada entrada)  {
        List<ItensEntrada> itensEntradas = itensEntradaRedisRepository.getItensEntrada(entrada).orElseThrow(() -> new RuntimeException("teste"));
        for (ItensEntrada item : itensEntradas) {
            entrada.setValorTotal(item.getValorTotal());
            Livro livro = item.getLivro();
            livro.setPreco(item.getPreco());
            livroService.updateLivro(livro);
        }
        itensEstoqueService.addEstoque(itensEntradas);
        repository.saveAll(itensEntradas);
        itensEntradaRedisRepository.deleteItensEntrada(entrada);
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

    public List<ItensEntrada> buscarItensEntradas(Entrada entrada) {
        return itensEntradaRedisRepository.getItensEntrada(entrada).orElse(null);
    }
}
