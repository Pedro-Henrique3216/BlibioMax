package com.example.bibliomax.email;

import com.example.bibliomax.model.ItensPedido;
import com.example.bibliomax.model.Pedido;
import com.example.bibliomax.model.Pessoa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class EmailRequestSuccess {

    private final EmailSender emailSender;

    @Autowired
    public EmailRequestSuccess(EmailSender emailSender) {
        this.emailSender = emailSender;
    }

    @Async("taskExecutor")
    public void sendEmail(Pedido pedido, Pessoa pessoa) {
        StringBuilder sb = new StringBuilder();
        sb.append("Numero do pedido ").append(pedido.getId());
        sb.append("\nItens: ");
        for(ItensPedido itens : pedido.getItensPedido()){
            sb.append("\n").append(itens);
        }
        sb.append("\nValor do pedido ").append(pedido.getValor());
        emailSender.emailSend(
            pessoa.getUser().getUsername(),
            "Pedido efetuado com sucesso",
            sb.toString()
        );
    }
}
