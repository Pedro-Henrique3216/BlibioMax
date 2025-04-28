package com.example.bibliomax.service;

import com.example.bibliomax.dto.ItensPedidoRequest;
import com.example.bibliomax.dto.PedidoRequest;
import com.example.bibliomax.email.EmailRequestSuccess;
import com.example.bibliomax.exceptions.ObjetoNaoEncontrado;
import com.example.bibliomax.exceptions.OrderCreationException;
import com.example.bibliomax.exceptions.QuantidadeObjetoNaoEncotrado;
import com.example.bibliomax.model.*;
import com.example.bibliomax.repository.PedidoRepository;
import jakarta.persistence.OptimisticLockException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
public class RentalOrderService {

    private final PedidoRepository orderRepository;
    private final PagamentoService paymentService;
    private final PessoaService personService;
    private final RentalItemService orderItemService;
    private final ItensEstoqueService stockItemService;
    private final LivroService bookService;
    private final EmailRequestSuccess emailRequestSuccess;
    private final Logger logger = Logger.getLogger(RentalOrderService.class.getName());

    @Autowired
    public RentalOrderService(PedidoRepository orderRepository, PagamentoService paymentService,
                              PessoaService personService, RentalItemService orderItemService,
                              ItensEstoqueService stockItemService, LivroService bookService,
                              EmailRequestSuccess emailRequestSuccess) {
        this.orderRepository = orderRepository;
        this.paymentService = paymentService;
        this.personService = personService;
        this.orderItemService = orderItemService;
        this.stockItemService = stockItemService;
        this.bookService = bookService;
        this.emailRequestSuccess = emailRequestSuccess;
    }

    public void saveOrder(RentalOrder order) {
        orderRepository.save(order);
    }

    @Transactional
    public RentalOrder saveOrder(PedidoRequest dto, String personUsername) {
        int tentativas = 0;
        while (tentativas < 3) {
            try {
                Pessoa person = personService.findByUsername(personUsername);

                List<RentalItem> orderItems = dto.itens().stream()
                        .map(this::createOrderItem)
                        .collect(Collectors.toList());

                RentalOrder order = new RentalOrder(null, person);

                orderItems.forEach(order::addItensPedido);
                orderItemService.saveAll(orderItems);
                orderRepository.save(order);
                logger.info("RentalOrder salvo com sucesso!");
                emailRequestSuccess.sendEmail(order, person);
                logger.info("Email enviado com sucesso!");
                return order;
            } catch (OptimisticLockException e) {
                tentativas++;
                try {
                    Thread.sleep(500);
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }
            }
        }
        throw new OrderCreationException("We're sorry, but the item you tried to order is no longer available. Please try again later");
    }

    private RentalItem createOrderItem(ItensPedidoRequest itensPedidoRequest) {
        Livro book = bookService.buscarLivroPorId(itensPedidoRequest.livroId());
        if (stockItemService.getItensEstoque(itensPedidoRequest.livroId()) == 0) {
            throw new QuantidadeObjetoNaoEncotrado("There is no such amount in stock");
        }
        stockItemService.removeItensEstoque(itensPedidoRequest.livroId(), 1);

        return new RentalItem(new ItensPedidoPK(book, null));
    }


    public List<RentalOrder> findAll(String username) {
        Pessoa person = personService.findByUsername(username);
        return orderRepository.findAllByPerson(person);
    }

    public RentalOrder findById(Long id, String username) {
        RentalOrder order = orderRepository.findById(id).orElseThrow(() -> new ObjetoNaoEncontrado("Order not found"));
        if(isUserAuthorized(order, username)){
            return order;
        }
        throw new AccessDeniedException("You do not have permission to access this order");
    }

    public RentalOrder findById(Long id) {
        return orderRepository.findById(id).orElseThrow(() -> new ObjetoNaoEncontrado("Order not found"));
    }

    public List<RentalOrder> findByDate(LocalDate date, String username) {
        Pessoa person = personService.findByUsername(username);
        return orderRepository.findAllByOrderDateBetweenAndPerson(date, LocalDate.now(), person);
    }

    private boolean isUserAuthorized(RentalOrder rentalOrder, String username){
        return rentalOrder.getPerson().getUser().getUsername().equals(username);
    }
}
