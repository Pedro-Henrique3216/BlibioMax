package com.example.bibliomax.service;

import com.example.bibliomax.email.EmailInvoiceRequest;
import com.example.bibliomax.exceptions.PedidoCancelado;
import com.example.bibliomax.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PedidoPagamentoServiceTest {

    @Mock
    private PagamentoService paymentService;

    @Mock
    private PedidoService orderService;

    @Mock
    private EmailInvoiceRequest emailInvoiceRequest;

    @InjectMocks
    private PedidoPagamentoService service;

    private Pedido order;
    private Pagamento payment;

    @BeforeEach
    void setUp() {
        Pessoa person = new Pessoa();
        person.setUser(new User("testUser", "12345678", Role.USER));
        payment = new Pagamento(TipoPagamento.PIX ,20.0);
        order = new Pedido(payment, person, TipoPedido.COMPRA, 20.0);
    }

    @Test
    void confirmPaymentSuccess() {
        when(paymentService.findPaymentByOrderId(anyLong())).thenReturn(payment);
        when(orderService.findById(anyLong())).thenReturn(order);
        payment.setDataCriacao(LocalDateTime.now().minusMinutes(30));
        doNothing().when(emailInvoiceRequest).sendInvoice(any(), any());

        service.confirmPayment(anyLong());

        assertEquals(StatusPedido.EM_ANDAMENTO, order.getStatusPedido());
        assertNotNull(payment.getDataPagamento());

        verify(paymentService).save(payment);
        verify(orderService).saveOrder(order);
    }

    @Test
    void confirmPaymentFail_LatePayment() {
        when(paymentService.findPaymentByOrderId(anyLong())).thenReturn(payment);
        when(orderService.findById(anyLong())).thenReturn(order);
        payment.setDataCriacao(LocalDateTime.now().minusHours(3));

        assertThrows(PedidoCancelado.class, () -> service.confirmPayment(anyLong()));
        assertEquals(StatusPedido.CANCELADO, order.getStatusPedido());
        assertNull(payment.getDataPagamento());

        verify(orderService).saveOrder(order);
    }
}