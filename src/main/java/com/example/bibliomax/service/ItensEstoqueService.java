package com.example.bibliomax.service;

import com.example.bibliomax.dto.ItensEstoqueDto;
import com.example.bibliomax.model.ItensEntrada;
import com.example.bibliomax.model.ItensEstoque;
import com.example.bibliomax.repository.ItensEstoqueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ItensEstoqueService {


     private final ItensEstoqueRepository repository;
    private final ItensEstoqueRepository itensEstoqueRepository;

    @Autowired
    public ItensEstoqueService(ItensEstoqueRepository repository, ItensEstoqueRepository itensEstoqueRepository) {
        this.repository = repository;
        this.itensEstoqueRepository = itensEstoqueRepository;
    }


    @Transactional
    public void addEstoque(List<ItensEntrada> itensEntradas) {
       for (ItensEntrada entrada : itensEntradas) {
           if (repository.existsByLivro(entrada.getLivro())) {
                ItensEstoque itensEstoque = repository.findByLivroId(entrada.getLivro().getId());
                itensEstoque.aumentar(entrada.getQuantidade());
                repository.save(itensEstoque);
           } else {
               ItensEstoque itensEstoque = new ItensEstoque(entrada.getLivro(), entrada.getQuantidade());
               repository.save(itensEstoque);
           }
       }
    }

    public Page<ItensEstoqueDto> findEstoque(Pageable pageable) {
        return repository.findAll(pageable)
                .map(itensEstoque -> new ItensEstoqueDto(itensEstoque.getLivro().getId(), itensEstoque.getLivro().getTitulo(),
                itensEstoque.getQuantidade(),
                itensEstoque.getMinimoQuantidade()));
    }

    public Integer getItensEstoque(Long livroId) {
        return repository.findByLivroId(livroId).getQuantidade();
    }

    public List<ItensEstoqueDto> produtoAbaixoMinimo(){
        List<ItensEstoque> itensEstoque = repository.findAll();
        return itensEstoque.stream()
                .filter(item ->
                        item.getMinimoQuantidade() >= item.getQuantidade())
                .map(item -> new ItensEstoqueDto(item.getLivro().getId(), item.getLivro().getTitulo(),
                        item.getQuantidade(),
                        item.getMinimoQuantidade()))
                .toList();
    }
    @Transactional
    public void removeItensEstoque(Long id, Integer quantity) {
        ItensEstoque itensEstoque = repository.findByLivroId(id);
        itensEstoque.diminuir(quantity);
        itensEstoqueRepository.save(itensEstoque);
    }
}
