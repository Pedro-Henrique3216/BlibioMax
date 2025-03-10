package com.example.bibliomax.controller;

import com.example.bibliomax.dto.PedidoRequest;
import com.example.bibliomax.dto.PedidoResponse;
import com.example.bibliomax.model.Pedido;
import com.example.bibliomax.model.PedidoMapper;
import com.example.bibliomax.service.PedidoService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping("/pedido")
@SecurityRequirement(name = "bearer-key")
public class PedidoController {

    private final PedidoService pedidoService;

    private final Logger log = Logger.getLogger("PedidoController");

    @Autowired
    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @PostMapping
    public ResponseEntity<PedidoResponse> save(@RequestBody PedidoRequest dto, @AuthenticationPrincipal UserDetails user){
        Pedido pedido = pedidoService.saveOrder(dto, user.getUsername());
        PedidoResponse pedidoResponse = PedidoMapper.toResponse(pedido);
        return ResponseEntity.ok(pedidoResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PedidoResponse> findById(@PathVariable Long id, @AuthenticationPrincipal UserDetails user){
        Pedido pedido = pedidoService.findById(id, user.getUsername());
        PedidoResponse pedidoResponse = PedidoMapper.toResponse(pedido);
        return ResponseEntity.ok(pedidoResponse);

    }

    @GetMapping()
    public ResponseEntity<List<PedidoResponse>> findAll(@AuthenticationPrincipal UserDetails user){
        List<Pedido> pedidos = pedidoService.findAll(user.getUsername());
        List<PedidoResponse> pedidoResponse = pedidos.stream().map(PedidoMapper::toResponse).toList();
        return ResponseEntity.ok(pedidoResponse);
    }

    @GetMapping("/por-data")
    public ResponseEntity<List<PedidoResponse>> findAllByDate(@RequestParam LocalDate data, @AuthenticationPrincipal UserDetails user){
        List<Pedido> pedidos = pedidoService.findByDate(data, user.getUsername());
        List<PedidoResponse> pedidoResponse = pedidos.stream().map(PedidoMapper::toResponse).toList();
        return ResponseEntity.ok(pedidoResponse);
    }



}
