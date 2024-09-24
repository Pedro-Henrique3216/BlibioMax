package com.example.bibliomax.controller;

import com.example.bibliomax.dto.*;
import com.example.bibliomax.model.Entrada;
import com.example.bibliomax.model.EntradaDto;
import com.example.bibliomax.service.EntradaService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("administrativo")
public class EntradaController {

    @Autowired
    EntradaService entradaService;

    @GetMapping("/entrada/add")
    @SecurityRequirement(name = "bearer-key")
    public ResponseEntity<RetornoCadastroDto> cadastroEntrada(@RequestBody CadastroEntradaRequest cadastroEntradaRequest) {
        Entrada entrada = entradaService.criarEntrada(cadastroEntradaRequest.entradaDto());
        if(cadastroEntradaRequest.itensEntradaDto() != null){
            entradaService.addItensEntrada(entrada, cadastroEntradaRequest.itensEntradaDto());
        }
        System.out.println(entradaService.buscarItensEntradas(entrada));
        return ResponseEntity.ok(new RetornoCadastroDto(new RetornaEntradaDto(cadastroEntradaRequest.entradaDto().numeroNota()),
                entradaService.buscarItensEntradas(entrada)
                        .stream()
                        .map(itens -> new RetornaItensDto(itens.getLivro().getId(), itens.getLivro().getTitulo(), itens.getPreco(), itens.getQuantidade(), itens.getValorTotal()))
                        .toList()));
    }

    @PostMapping("/entrada/salvar")
    @Transactional
    @SecurityRequirement(name = "bearer-key")
    public ResponseEntity<RetornaEntradaItensDto> salvarEntrada(@RequestBody EntradaDto entradaDto) {
        List<RetornaItensDto> dtos = entradaService.saveItensEntrada(entradaDto);
        Entrada entrada = entradaService.buscarEntrada(entradaDto.numeroNota());
        System.out.println(dtos);
        return ResponseEntity.ok(new RetornaEntradaItensDto(
                new RetornaEntradaDto(entradaDto.numeroNota()),
                entrada.getDataEntrada(),
                dtos,
                entrada.getValorTotal(),
                entrada.getStatus()
        ));
    }

    @GetMapping("/entrada")
    @SecurityRequirement(name = "bearer-key")
    public ResponseEntity<RetornaEntradaItensDto> buscarEntrada(@RequestParam(value = "numero_da_nota") Long numeroNota) {
        List<RetornaItensDto> dtos = entradaService.buscaTodosItensEntradas(numeroNota);
        Entrada entrada = entradaService.buscarEntrada(numeroNota);
        return ResponseEntity.ok(new RetornaEntradaItensDto(
                new RetornaEntradaDto(entrada.getNumeroNota()),
                entrada.getDataEntrada(),
                dtos,
                entrada.getValorTotal(),
                entrada.getStatus()
        ));
    }

}
