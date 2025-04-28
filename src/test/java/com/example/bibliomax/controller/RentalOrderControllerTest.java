package com.example.bibliomax.controller;

import com.example.bibliomax.dto.ItensPedidoRequest;
import com.example.bibliomax.dto.ItensPedidoResponse;
import com.example.bibliomax.dto.PedidoRequest;
import com.example.bibliomax.dto.PedidoResponse;
import com.example.bibliomax.model.*;
import com.example.bibliomax.service.RentalOrderService;
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
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
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
@ActiveProfiles("test")
class RentalOrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private RentalOrderService rentalOrderService;

    @Autowired
    private ObjectMapper jacksonObjectMapper;

    private PedidoRequest orderRequest;

    private RentalOrder order;

    private PedidoResponse orderResponse;

    @TestConfiguration
    static class TestConfig {
        @Bean
        public RentalOrderService rentalOrderService() {
            return mock(RentalOrderService.class);
        }
    }


    @BeforeEach
    void setUp() {
        Pessoa person = new Pessoa();
        person.setUser(new User("test@gmail.com", "12345678", Role.USER));

        Livro book = new Livro();
        book.setId(1L);
        book.setPreco(50.0);

        orderRequest = new PedidoRequest(
                List.of(new ItensPedidoRequest(1L)
                ));

        order = new RentalOrder(null, person);
        order.setId(1L);

        RentalItem itens = new RentalItem(new ItensPedidoPK(book, order));
        order.addItensPedido(itens);

        orderResponse = new PedidoResponse(List.of(
                new ItensPedidoResponse(book.getTitulo(), 1)), BigDecimal.ZERO, StatusRental.CRIADO);

        Mockito.reset(rentalOrderService);
    }


    @Test
    @WithMockUser(username = "test@gmail.com", roles = "USER")
    void shouldSaveOrderWhenUserIsAuthenticated() throws Exception {

        when(rentalOrderService.saveOrder(any(PedidoRequest.class), any(String.class))).thenReturn(order);

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

        when(rentalOrderService.findById(1L, "test@gmail.com")).thenReturn(order);

        String string = mockMvc.perform(get("/pedido/{id}", 1L))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        assertEquals(jacksonObjectMapper.writeValueAsString(orderResponse), string);
    }

    @Test
    @WithMockUser(username = "test@gmail.com", roles = "USER")
    void shouldReturnForbiddenWhenUserTriesToAccessAnotherUsersOrder() throws Exception {

        when(rentalOrderService.findById(1L, "test@gmail.com")).thenThrow(new AccessDeniedException("You do not have permission to access this order"));

       mockMvc.perform(get("/pedido/{id}", 1L))
                .andExpect(status().isForbidden());

    }

    @Test
    @WithMockUser(username = "test@gmail.com", roles = "USER")
    void shouldGetAllOrderWhenUserIsAuthenticated() throws Exception {

        when(rentalOrderService.findAll("test@gmail.com")).thenReturn(List.of(order));

        String string = mockMvc.perform(get("/pedido"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        assertEquals("["+jacksonObjectMapper.writeValueAsString(orderResponse)+"]", string);
    }


}