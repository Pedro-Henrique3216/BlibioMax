package com.example.bibliomax.service;

import com.example.bibliomax.dto.ItensPedidoRequest;
import com.example.bibliomax.dto.PedidoRequest;
import com.example.bibliomax.email.EmailRequestSuccess;
import com.example.bibliomax.exceptions.ObjetoNaoEncontrado;
import com.example.bibliomax.exceptions.QuantidadeObjetoNaoEncotrado;
import com.example.bibliomax.model.*;
import com.example.bibliomax.repository.PedidoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.access.AccessDeniedException;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PedidoServiceTest {

    @Mock
    private PedidoRepository orderRepository;

    @Mock
    private PagamentoService paymentService;

    @Mock
    private PessoaService personService;

    @Mock
    private ItensPedidoService orderItemService;

    @Mock
    private ItensEstoqueService stockItemService;

    @Mock
    private LivroService bookService;

    @Mock
    private EmailRequestSuccess emailRequestSuccess;

    @InjectMocks
    private PedidoService pedidoService;

    private Pessoa pessoa;
    private Pedido pedido;
    private Livro livro;
    private PedidoRequest pedidoRequest;

    @BeforeEach
    void setUp() {
        pessoa = new Pessoa();
        pessoa.setUser(new User("testUser", "12345678", Role.USER));

        livro = new Livro();
        livro.setId(1L);
        livro.setPreco(50.0);

        pedidoRequest = new PedidoRequest(TipoPagamento.PIX, TipoPedido.COMPRA, List.of(
                new ItensPedidoRequest(1L, 2)
        ));

        pedido = new Pedido();
        pedido.setPessoa(pessoa);
    }

    @Test
    void saveOrder_Success() {
        when(personService.findByUsername(anyString())).thenReturn(pessoa);
        when(bookService.buscarLivroPorId(anyLong())).thenReturn(livro);
        when(stockItemService.getItensEstoque(anyLong())).thenReturn(10);
        when(orderRepository.save(any(Pedido.class))).thenReturn(pedido);
        doNothing().when(stockItemService).removeItensEstoque(anyLong(), anyInt());
        doNothing().when(emailRequestSuccess).sendEmail(any(), any());

        Pedido result = pedidoService.saveOrder(pedidoRequest, "testUser");

        assertNotNull(result);
        verify(orderRepository, atLeastOnce()).save(any(Pedido.class));
    }

    @Test
    void saveOrder_Fail_StockUnavailable() {
        when(stockItemService.getItensEstoque(anyLong())).thenReturn(1);
        assertThrows(QuantidadeObjetoNaoEncotrado.class, () ->
                pedidoService.saveOrder(pedidoRequest, "testUser"));
    }


    @Test
    void findById_Success() {
        when(orderRepository.findById(anyLong())).thenReturn(Optional.of(pedido));
        Pedido result = pedidoService.findById(1L, "testUser");
        assertEquals(pedido, result);
    }

    @Test
    void findById_Fall_UserNotAuthorized() {
        when(orderRepository.findById(anyLong())).thenReturn(Optional.of(pedido));

        assertThrows(AccessDeniedException.class, () -> pedidoService.findById(1L, "Caio"));
    }

    @Test
    void findById_Fail_NotFound() {
        when(orderRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(ObjetoNaoEncontrado.class, () -> pedidoService.findById(1L, "testUser"));
    }

    @Test
    void findByDate_Success() {
        when(personService.findByUsername(anyString())).thenReturn(pessoa);
        when(orderRepository.findAllByDatePedidoBetweenAndPessoa(any(), any(), any()))
                .thenReturn(List.of(pedido));
        List<Pedido> result = pedidoService.findByDate(LocalDate.now(), "testUser");
        assertFalse(result.isEmpty());
    }
}