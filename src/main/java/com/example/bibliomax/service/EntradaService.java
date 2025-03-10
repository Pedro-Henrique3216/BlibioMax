package com.example.bibliomax.service;

import com.example.bibliomax.dto.RetornaItensDto;
import com.example.bibliomax.exceptions.EntradaJaConcluida;
import com.example.bibliomax.exceptions.EntryOwnershipException;
import com.example.bibliomax.exceptions.ObjetoNaoEncontrado;
import com.example.bibliomax.model.*;
import com.example.bibliomax.repository.EntradaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EntradaService {

    private final EntradaRepository repository;

    private final BibliotecarioService bibliotecarioService;

    private final ItensEntradaService itensEntradaService;


    @Autowired
    public EntradaService(EntradaRepository repository, BibliotecarioService bibliotecarioService, ItensEntradaService itensEntradaService) {
        this.repository = repository;
        this.bibliotecarioService = bibliotecarioService;
        this.itensEntradaService = itensEntradaService;
    }

    public Entrada criarEntrada(EntradaDto entradaDto, UserDetails userDetails) {
        Bibliotecario bibliotecario = bibliotecarioService.findByUsername(userDetails.getUsername());
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
            throw new EntradaJaConcluida("Essa entrada ja esta concluida");
        }
    }

    public List<ItensEntrada> buscarItensEntradas(Entrada entrada) {
        return itensEntradaService.buscarItensEntradas(entrada);
    }

    public Entrada buscarEntrada(Long numeroNota) {
        return repository.findById(numeroNota).orElseThrow(() -> new ObjetoNaoEncontrado("Entrada não encontrada"));
    }

    public List<RetornaItensDto> saveItensEntrada(EntradaDto entradaDto, UserDetails userDetails){
        Entrada entrada = buscarEntrada(entradaDto.numeroNota());
        Bibliotecario bibliotecario = bibliotecarioService.findByUsername(userDetails.getUsername());
        if (bibliotecario != entrada.getBibliotecario()){
            throw new EntryOwnershipException("Only the librarian who initiated the entry can save it.");
        }
        itensEntradaService.saveItensEntrada(entrada);
        entrada.setStatus(StatusEntrada.CONCLUIDO);
        return itensEntradaService.buscaTodosItensPorEntrada(entrada);
    }

    public List<RetornaItensDto> buscaTodosItensEntradas(Long numeroNota){
        Entrada entrada = buscarEntrada(numeroNota);
        return itensEntradaService.buscaTodosItensPorEntrada(entrada);
    }

}
