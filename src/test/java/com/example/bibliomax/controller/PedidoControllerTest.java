package com.example.bibliomax.controller;

import com.example.bibliomax.dto.ItensPedidoRequest;
import com.example.bibliomax.dto.ItensPedidoResponse;
import com.example.bibliomax.dto.PedidoRequest;
import com.example.bibliomax.dto.PedidoResponse;
import com.example.bibliomax.model.*;
import com.example.bibliomax.service.PedidoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class PedidoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PedidoService orderService;

    @Autowired
    private ObjectMapper jacksonObjectMapper;

    private PedidoRequest orderRequest;

    private Pedido order;

    private PedidoResponse orderResponse;

    @TestConfiguration
    static class TestConfig {
        @Bean
        public PedidoService pedidoService() {
            return mock(PedidoService.class);
        }
    }


    @BeforeEach
    void setUp() {
        Pessoa person = new Pessoa();
        person.setUser(new User("test@gmail.com", "12345678", Role.USER));

        Livro book = new Livro();
        book.setId(1L);
        book.setPreco(50.0);

        orderRequest = new PedidoRequest(TipoPagamento.PIX, TipoPedido.COMPRA, List.of(
                new ItensPedidoRequest(1L, 2)
        ));

        Pagamento payment = new Pagamento(TipoPagamento.PIX, 100.00);
        order = new Pedido(payment, person, TipoPedido.COMPRA, 100.0);
        order.setId(1L);

        ItensPedido itens = new ItensPedido(new ItensPedidoPK(book, order), 2, 100.0);
        order.addItensPedido(itens);

        orderResponse = new PedidoResponse(TipoPagamento.PIX, TipoPedido.COMPRA, List.of(
                new ItensPedidoResponse(book.getTitulo(), 2, 100.0)), 100.0, StatusPedido.CRIADO);

        Mockito.reset(orderService);
    }


    @Test
    @WithMockUser(username = "test@gmail.com", roles = "USER")
    void shouldSaveOrderWhenUserIsAuthenticated() throws Exception {

        when(orderService.saveOrder(any(PedidoRequest.class), any(String.class))).thenReturn(order);

        mockMvc.perform(post("/pedido")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jacksonObjectMapper.writeValueAsString(orderRequest)))
                .andExpect(status().isOk());
    }

    @Test
    void notShouldSaveOrderWhenUserIsNotAuthenticated() throws Exception {

        mockMvc.perform(post("/pedido")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jacksonObjectMapper.writeValueAsString(orderRequest)))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "test@gmail.com", roles = "USER")
    void shouldGetOrderByIdWhenUserIsAuthenticated() throws Exception {

        when(orderService.findById(1L, "test@gmail.com")).thenReturn(order);

        String string = mockMvc.perform(get("/pedido/{id}", 1L))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        assertEquals(jacksonObjectMapper.writeValueAsString(orderResponse), string);
    }

    @Test
    @WithMockUser(username = "test@gmail.com", roles = "USER")
    void shouldReturnForbiddenWhenUserTriesToAccessAnotherUsersOrder() throws Exception {

        when(orderService.findById(1L, "test@gmail.com")).thenThrow(new AccessDeniedException("You do not have permission to access this order"));

       mockMvc.perform(get("/pedido/{id}", 1L))
                .andExpect(status().isForbidden());

    }

    @Test
    @WithMockUser(username = "test@gmail.com", roles = "USER")
    void shouldGetAllOrderWhenUserIsAuthenticated() throws Exception {

        when(orderService.findAll("test@gmail.com")).thenReturn(List.of(order));

        String string = mockMvc.perform(get("/pedido"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        assertEquals("["+jacksonObjectMapper.writeValueAsString(orderResponse)+"]", string);
    }


}