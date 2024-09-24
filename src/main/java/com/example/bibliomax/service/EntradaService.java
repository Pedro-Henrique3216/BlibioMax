package com.example.bibliomax.service;

import com.example.bibliomax.dto.RetornaItensDto;
import com.example.bibliomax.model.*;
import com.example.bibliomax.repository.EntradaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EntradaService {

    @Autowired
    private EntradaRepository repository;

    @Autowired
    private BibliotecarioService bibliotecarioService;

    @Autowired
    private ItensEntradaService itensEntradaService;


    public Entrada criarEntrada(EntradaDto entradaDto) {
        Bibliotecario bibliotecario = bibliotecarioService.buscarBibliotecarioPorId(entradaDto.bibiliotecarioId());
        Entrada entrada = new Entrada(entradaDto.numeroNota(), bibliotecario);
        if(!repository.existsByNumeroNota(entradaDto.numeroNota())) {
            return repository.save(entrada);
        }
        return buscarEntrada(entrada.getNumeroNota());
    }

    public void addItensEntrada(Entrada entrada, ItensEntradaDto itensEntradaDto) {
        if(entrada.getStatus() != StatusEntrada.CONCLUIDO){
            itensEntradaService.addItensEntrada(entrada, itensEntradaDto);
        } else {
            throw new RuntimeException("Essa entrada ja esta concluida");
        }
    }

    public List<ItensEntrada> buscarItensEntradas(Entrada entrada) {
        return itensEntradaService.buscarItensEntradas(entrada);
    }

    public Entrada buscarEntrada(Long numeroNota) {
        return repository.findById(numeroNota).orElseThrow(() -> new RuntimeException("Entrada não encontrada"));
    }

    public List<RetornaItensDto> saveItensEntrada(EntradaDto entradaDto){
        Entrada entrada = buscarEntrada(entradaDto.numeroNota());
        itensEntradaService.saveItensEntrada(entrada);
        entrada.setStatus(StatusEntrada.CONCLUIDO);
        return itensEntradaService.buscaTodosItensPorEntrada(entrada);
    }

    public List<RetornaItensDto> buscaTodosItensEntradas(Long numeroNota){
        Entrada entrada = buscarEntrada(numeroNota);
        return itensEntradaService.buscaTodosItensPorEntrada(entrada);
    }

}
