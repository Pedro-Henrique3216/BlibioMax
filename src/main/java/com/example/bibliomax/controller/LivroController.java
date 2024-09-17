package com.example.bibliomax.controller;

import com.example.bibliomax.dto.RetornaLivroDto;
import com.example.bibliomax.service.LivroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/livro")
public class LivroController {

    @Autowired
    LivroService livroService;

    @GetMapping
    public ResponseEntity<Page<RetornaLivroDto>> getLivros(Pageable pageable) {
        return ResponseEntity.ok(livroService.listarLivros(pageable));
    }



}
