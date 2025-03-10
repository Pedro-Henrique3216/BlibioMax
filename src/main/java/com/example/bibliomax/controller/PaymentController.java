package com.example.bibliomax.controller;

import com.example.bibliomax.service.PedidoPagamentoService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/payment")
@SecurityRequirement(name = "bearer-key")
public class PaymentController {

    private final PedidoPagamentoService paymentService;

    @Autowired
    public PaymentController(PedidoPagamentoService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/{id}")
    public ResponseEntity<Void> confirmPayment(@PathVariable Long id) {
        paymentService.confirmPayment(id);
        return ResponseEntity.noContent().build();
    }
}
