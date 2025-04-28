package com.example.bibliomax.controller;

import com.example.bibliomax.dto.PedidoRequest;
import com.example.bibliomax.dto.PedidoResponse;
import com.example.bibliomax.model.RentalOrder;
import com.example.bibliomax.model.PedidoMapper;
import com.example.bibliomax.service.RentalOrderService;
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

    private final RentalOrderService rentalOrderService;

    private final Logger log = Logger.getLogger("PedidoController");

    @Autowired
    public PedidoController(RentalOrderService rentalOrderService) {
        this.rentalOrderService = rentalOrderService;
    }

    @PostMapping
    public ResponseEntity<PedidoResponse> save(@RequestBody PedidoRequest dto, @AuthenticationPrincipal UserDetails user){
        RentalOrder rentalOrder = rentalOrderService.saveOrder(dto, user.getUsername());
        PedidoResponse pedidoResponse = PedidoMapper.toResponse(rentalOrder);
        return ResponseEntity.ok(pedidoResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PedidoResponse> findById(@PathVariable Long id, @AuthenticationPrincipal UserDetails user){
        RentalOrder rentalOrder = rentalOrderService.findById(id, user.getUsername());
        PedidoResponse pedidoResponse = PedidoMapper.toResponse(rentalOrder);
        return ResponseEntity.ok(pedidoResponse);

    }

    @GetMapping()
    public ResponseEntity<List<PedidoResponse>> findAll(@AuthenticationPrincipal UserDetails user){
        List<RentalOrder> rentalOrders = rentalOrderService.findAll(user.getUsername());
        List<PedidoResponse> pedidoResponse = rentalOrders.stream().map(PedidoMapper::toResponse).toList();
        return ResponseEntity.ok(pedidoResponse);
    }

    @GetMapping("/por-data")
    public ResponseEntity<List<PedidoResponse>> findAllByDate(@RequestParam LocalDate data, @AuthenticationPrincipal UserDetails user){
        List<RentalOrder> rentalOrders = rentalOrderService.findByDate(data, user.getUsername());
        List<PedidoResponse> pedidoResponse = rentalOrders.stream().map(PedidoMapper::toResponse).toList();
        return ResponseEntity.ok(pedidoResponse);
    }



}
