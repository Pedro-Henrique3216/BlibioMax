package com.example.bibliomax.service;

import com.example.bibliomax.model.Pagamento;
import com.example.bibliomax.repository.PagamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PagamentoService {

    private final PagamentoRepository repository;

    @Autowired
    public PagamentoService(PagamentoRepository repository) {
        this.repository = repository;
    }

    public void save(Pagamento payment){
        repository.save(payment);
    }

    public Pagamento findPaymentByOrderId(Long orderId){
        return repository.findByPedidoId(orderId).orElseThrow(() -> new RuntimeException("Payment Not Found"));
    }



}
