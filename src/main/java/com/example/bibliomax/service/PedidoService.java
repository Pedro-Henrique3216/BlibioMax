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
public class PedidoService {

    private final PedidoRepository orderRepository;
    private final PagamentoService paymentService;
    private final PessoaService personService;
    private final ItensPedidoService orderItemService;
    private final ItensEstoqueService stockItemService;
    private final LivroService bookService;
    private final EmailRequestSuccess emailRequestSuccess;
    private final Logger logger = Logger.getLogger(PedidoService.class.getName());

    @Autowired
    public PedidoService(PedidoRepository orderRepository, PagamentoService paymentService,
                         PessoaService personService, ItensPedidoService orderItemService,
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

    public void saveOrder(Pedido order) {
        orderRepository.save(order);
    }

    @Transactional
    public Pedido saveOrder(PedidoRequest dto, String personUsername) {
        int tentativas = 0;
        while (tentativas < 3) {
            try {
                Pessoa person = personService.findByUsername(personUsername);

                List<ItensPedido> orderItems = dto.itens().stream()
                        .map(this::createOrderItem)
                        .collect(Collectors.toList());

                double totalOrderValue = orderItems.stream().mapToDouble(ItensPedido::getValor).sum();
                Pagamento payment = new Pagamento(dto.tipoPagamento(), totalOrderValue);
                paymentService.save(payment);

                Pedido order = new Pedido(payment, person, dto.tipoPedido(), totalOrderValue);
                logger.info("teste");
                orderItems.forEach(order::addItensPedido);
                orderItemService.saveAll(orderItems);
                orderRepository.save(order);
                emailRequestSuccess.sendEmail(order, person);

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

    private ItensPedido createOrderItem(ItensPedidoRequest itensPedidoRequest) {
        Livro book = bookService.buscarLivroPorId(itensPedidoRequest.livroId());
        if (stockItemService.getItensEstoque(itensPedidoRequest.livroId()) < itensPedidoRequest.quantidade()) {
            throw new QuantidadeObjetoNaoEncotrado("There is no such amount in stock");
        }
        double totalItemPrice = book.getPreco() * itensPedidoRequest.quantidade();
        stockItemService.removeItensEstoque(itensPedidoRequest.livroId(), itensPedidoRequest.quantidade());

        return new ItensPedido(new ItensPedidoPK(book, null), itensPedidoRequest.quantidade(), totalItemPrice);
    }


    public List<Pedido> findAll(String username) {
        Pessoa person = personService.findByUsername(username);
        return orderRepository.findAllByPessoa(person);
    }

    public Pedido findById(Long id, String username) {
        Pedido order = orderRepository.findById(id).orElseThrow(() -> new ObjetoNaoEncontrado("Order not found"));
        if(isUserAuthorized(order, username)){
            return order;
        }
        throw new AccessDeniedException("You do not have permission to access this order");
    }

    public Pedido findById(Long id) {
        return orderRepository.findById(id).orElseThrow(() -> new ObjetoNaoEncontrado("Order not found"));
    }

    public List<Pedido> findByDate(LocalDate date, String username) {
        Pessoa person = personService.findByUsername(username);
        return orderRepository.findAllByDatePedidoBetweenAndPessoa(date, LocalDate.now(), person);
    }

    private boolean isUserAuthorized(Pedido pedido, String username){
        return pedido.getPessoa().getUser().getUsername().equals(username);
    }
}
