package com.example.bibliomax.controller;

import com.example.bibliomax.dto.RetornaBibliotecarioDto;
import com.example.bibliomax.dto.RetornaLivroDto;
import com.example.bibliomax.model.Bibliotecario;
import com.example.bibliomax.model.BibliotecarioDto;
import com.example.bibliomax.model.Livro;
import com.example.bibliomax.model.LivroDto;
import com.example.bibliomax.service.BibliotecarioService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/bibliotecario")
public class BibliotecarioController {

    @Autowired
    private BibliotecarioService bibliotecarioService;

    @PostMapping
    @Transactional
    public ResponseEntity<RetornaBibliotecarioDto> cadastrarBibliotecario(@RequestBody @Valid BibliotecarioDto bibliotecarioDto, UriComponentsBuilder uriBuilder) {
        Bibliotecario bibliotecario =  bibliotecarioService.cadastrarBibliotecario(bibliotecarioDto);
        URI uri = uriBuilder.path("/bibliotecario/{id}").buildAndExpand(bibliotecario.getId()).toUri();
        return ResponseEntity.created(uri).body(new RetornaBibliotecarioDto(bibliotecario));
    }

    @SecurityRequirement(name = "bearer-key")
    @GetMapping
    public ResponseEntity<Page<RetornaBibliotecarioDto>> listarBibliotecario(Pageable pageable) {
        Page<RetornaBibliotecarioDto> listaBibliotecarios = bibliotecarioService.listarBibliotecarios(pageable).map(RetornaBibliotecarioDto::new);
        return ResponseEntity.ok(listaBibliotecarios);
    }

    @SecurityRequirement(name = "bearer-key")
    @GetMapping("/{id}")
    public ResponseEntity<RetornaBibliotecarioDto> buscarBibliotecarioPorId(@PathVariable Long id) {
        Bibliotecario bibliotecario = bibliotecarioService.buscarBibliotecarioPorId(id);
        return ResponseEntity.ok(new RetornaBibliotecarioDto(bibliotecario));
    }

    @SecurityRequirement(name = "bearer-key")
    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<RetornaBibliotecarioDto> atualizaBibliotecario(@PathVariable Long id, @RequestBody @Valid BibliotecarioDto bibliotecarioDto) {
        Bibliotecario bibliotecario = bibliotecarioService.atualizaBibliotecario(id, bibliotecarioDto);
        return ResponseEntity.ok(new RetornaBibliotecarioDto(bibliotecario));
    }

    @SecurityRequirement(name = "bearer-key")
    @PostMapping("/cadastrarLivro")
    public ResponseEntity<RetornaLivroDto> cadastraLivro(@RequestBody @Valid LivroDto livroDto, UriComponentsBuilder uriBuilder){
        Livro livro = bibliotecarioService.cadastrarLivro(livroDto);
        URI uri = uriBuilder.path("/bibliotecario/cadastrarLivro/{id}").buildAndExpand(livro.getId()).toUri();
        return ResponseEntity.created(uri).body(new RetornaLivroDto(livro));
    }

}
