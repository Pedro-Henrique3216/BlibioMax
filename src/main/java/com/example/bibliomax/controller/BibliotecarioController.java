package com.example.bibliomax.controller;

import com.example.bibliomax.DTO.RetornaBibliotecarioDto;
import com.example.bibliomax.model.BibliotecarioDto;
import com.example.bibliomax.model.Bibliotecario;
import com.example.bibliomax.service.BibliotecarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/bibliotecario")
public class BibliotecarioController {

    @Autowired
    BibliotecarioService bibliotecarioService;

    @PostMapping
    public ResponseEntity<RetornaBibliotecarioDto> cadastrarBibliotecario(@RequestBody BibliotecarioDto bibliotecarioDto) {
        Bibliotecario bibliotecario = bibliotecarioDto.toBibliotecario();
        bibliotecarioService.cadastrarBibliotecario(bibliotecario);
        return ResponseEntity.status(201).body(new RetornaBibliotecarioDto(bibliotecario));
    }

    @GetMapping
    public ResponseEntity<List<RetornaBibliotecarioDto>> listarBibliotecario() {
        List<RetornaBibliotecarioDto> listaBibliotecarios = bibliotecarioService.listarBibliotecarios().stream().map(RetornaBibliotecarioDto::new).toList();

        return ResponseEntity.status(200).body(listaBibliotecarios);
    }

}
