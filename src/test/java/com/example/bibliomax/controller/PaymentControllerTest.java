package com.example.bibliomax.controller;

import com.example.bibliomax.service.PedidoPagamentoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class PaymentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PedidoPagamentoService paymentService;

    @TestConfiguration
    static class OrderPaymentServiceTestConfiguration {
        @Bean
        public PedidoPagamentoService paymentService() {
            return mock(PedidoPagamentoService.class);
        }
    }

    @Test
    @WithMockUser(username = "test@gmail.com", roles = "USER")
    void shouldReturnNoContentWhenPaymentIsConfirmed() throws Exception {

        mockMvc.perform(post("/payment/{id}", 1L))
                .andExpect(status().isNoContent());

        verify(paymentService, times(1)).confirmPayment(1L);
    }
}