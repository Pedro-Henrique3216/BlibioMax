package com.example.bibliomax.controller;

import com.example.bibliomax.DTO.RetornaBibliotecarioDto;
import com.example.bibliomax.DTO.RetornaLivroDto;
import com.example.bibliomax.model.BibliotecarioDto;
import com.example.bibliomax.model.Bibliotecario;
import com.example.bibliomax.model.Livro;
import com.example.bibliomax.model.LivroDto;
import com.example.bibliomax.service.BibliotecarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/bibliotecario")
public class BibliotecarioController {

    @Autowired
    private BibliotecarioService bibliotecarioService;

    @PostMapping
    @Transactional
    public ResponseEntity<RetornaBibliotecarioDto> cadastrarBibliotecario(@RequestBody BibliotecarioDto bibliotecarioDto) {
        Bibliotecario bibliotecario =  bibliotecarioService.cadastrarBibliotecario(bibliotecarioDto);
        return ResponseEntity.status(201).body(new RetornaBibliotecarioDto(bibliotecario));
    }

    @GetMapping
    public ResponseEntity<List<RetornaBibliotecarioDto>> listarBibliotecario() {
        List<RetornaBibliotecarioDto> listaBibliotecarios = bibliotecarioService.listarBibliotecarios().stream().map(RetornaBibliotecarioDto::new).toList();

        return ResponseEntity.status(200).body(listaBibliotecarios);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RetornaBibliotecarioDto> buscarBibliotecarioPorId(@PathVariable Long id) {
        Bibliotecario bibliotecario = bibliotecarioService.buscarBibliotecarioPorId(id);
        return ResponseEntity.status(200).body(new RetornaBibliotecarioDto(bibliotecario));
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<RetornaBibliotecarioDto> atualizaBibliotecario(@PathVariable Long id, @RequestBody BibliotecarioDto bibliotecarioDto) {
        Bibliotecario bibliotecario = bibliotecarioService.atualizaBibliotecario(id, bibliotecarioDto);
        return ResponseEntity.status(200).body(new RetornaBibliotecarioDto(bibliotecario));
    }

    @PostMapping("/{id}/cadastrarLivro")
    public ResponseEntity<RetornaLivroDto> cadastraLivro(@PathVariable Long id, @RequestBody LivroDto livroDto){
        Livro livro = bibliotecarioService.cadastrarLivro(id, livroDto.toLivro());
        return ResponseEntity.status(201).body(new RetornaLivroDto(livro));
    }

}
