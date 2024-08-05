package com.example.bibliomax.controller;

import com.example.bibliomax.dto.RetornaBibliotecarioDto;
import com.example.bibliomax.dto.RetornaLivroDto;
import com.example.bibliomax.model.BibliotecarioDto;
import com.example.bibliomax.model.Bibliotecario;
import com.example.bibliomax.model.Livro;
import com.example.bibliomax.model.LivroDto;
import com.example.bibliomax.service.BibliotecarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Controller
@RequestMapping("/bibliotecario")
public class BibliotecarioController {

    @Autowired
    private BibliotecarioService bibliotecarioService;

    @PostMapping
    @Transactional
    public ResponseEntity<RetornaBibliotecarioDto> cadastrarBibliotecario(@RequestBody BibliotecarioDto bibliotecarioDto, UriComponentsBuilder uriBuilder) {
        Bibliotecario bibliotecario =  bibliotecarioService.cadastrarBibliotecario(bibliotecarioDto);
        URI uri = uriBuilder.path("/bibliotecario/{id}").buildAndExpand(bibliotecario.getId()).toUri();
        return ResponseEntity.created(uri).body(new RetornaBibliotecarioDto(bibliotecario));
    }

    @GetMapping
    public ResponseEntity<Page<RetornaBibliotecarioDto>> listarBibliotecario(Pageable pageable) {
        Page<RetornaBibliotecarioDto> listaBibliotecarios = bibliotecarioService.listarBibliotecarios(pageable).map(RetornaBibliotecarioDto::new);
        return ResponseEntity.ok(listaBibliotecarios);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RetornaBibliotecarioDto> buscarBibliotecarioPorId(@PathVariable Long id) {
        Bibliotecario bibliotecario = bibliotecarioService.buscarBibliotecarioPorId(id);
        return ResponseEntity.ok(new RetornaBibliotecarioDto(bibliotecario));
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<RetornaBibliotecarioDto> atualizaBibliotecario(@PathVariable Long id, @RequestBody BibliotecarioDto bibliotecarioDto) {
        Bibliotecario bibliotecario = bibliotecarioService.atualizaBibliotecario(id, bibliotecarioDto);
        return ResponseEntity.ok(new RetornaBibliotecarioDto(bibliotecario));
    }

    @PostMapping("/cadastrarLivro")
    public ResponseEntity<RetornaLivroDto> cadastraLivro(@RequestBody LivroDto livroDto, UriComponentsBuilder uriBuilder){
        Livro livro = bibliotecarioService.cadastrarLivro(livroDto);
        URI uri = uriBuilder.path("/bibliotecario/cadastrarLivro/{id}").buildAndExpand(livro.getId()).toUri();
        return ResponseEntity.created(uri).body(new RetornaLivroDto(livro));
    }

}
