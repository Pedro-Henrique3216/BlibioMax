package com.example.bibliomax.service;

import com.example.bibliomax.email.EmailInvoiceRequest;
import com.example.bibliomax.exceptions.PedidoCancelado;
import com.example.bibliomax.model.Pagamento;
import com.example.bibliomax.model.RentalOrder;
import com.example.bibliomax.model.StatusRental;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class PedidoPagamentoService {

    private final PagamentoService paymentService;

    private final RentalOrderService orderService;

    private final EmailInvoiceRequest emailInvoiceRequest;

    @Autowired
    public PedidoPagamentoService(PagamentoService paymentService, RentalOrderService orderService, EmailInvoiceRequest emailInvoiceRequest) {
        this.paymentService = paymentService;
        this.orderService = orderService;
        this.emailInvoiceRequest = emailInvoiceRequest;
    }

    @Transactional
    public void confirmPayment(Long orderId) {
        Pagamento payment = paymentService.findPaymentByOrderId(orderId);
        RentalOrder order = orderService.findById(orderId);
        if (payment.getDataCriacao().isBefore(LocalDateTime.now().minusHours(2))) {
            order.setStatusRental(StatusRental.DEVOLVIDO);
            orderService.saveOrder(order);
            throw new PedidoCancelado("Esse pedido esta cancelado");
        }
        payment.setDataPagamento(LocalDateTime.now());
        order.setStatusRental(StatusRental.EM_ANDAMENTO);

        paymentService.save(payment);
        orderService.saveOrder(order);
        emailInvoiceRequest.sendInvoice(order, order.getPerson());
    }
}
