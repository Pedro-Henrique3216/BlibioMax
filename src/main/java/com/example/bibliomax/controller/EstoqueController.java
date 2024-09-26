package com.example.bibliomax.controller;

import com.example.bibliomax.dto.ItensEstoqueDto;
import com.example.bibliomax.service.ItensEstoqueService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/administrativo")
@SecurityRequirement(name = "bearer-key")
public class EstoqueController {

    @Autowired
    private ItensEstoqueService itensEstoqueService;

    @GetMapping("/estoque")
    @Transactional
    public ResponseEntity<Page<ItensEstoqueDto>> getItensEstoque(Pageable pageable) {
        return ResponseEntity.ok(itensEstoqueService.findEstoque(pageable));
    }

    @GetMapping("/estoque/acabando")
    public ResponseEntity<List<ItensEstoqueDto>> getItensEstoqueAcabando() {
        return ResponseEntity.ok(itensEstoqueService.produtoAbaixoMinimo());
    }


}
