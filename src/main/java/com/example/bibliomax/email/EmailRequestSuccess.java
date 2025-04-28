package com.example.bibliomax.email;

import com.example.bibliomax.model.RentalItem;
import com.example.bibliomax.model.RentalOrder;
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
    public void sendEmail(RentalOrder rentalOrder, Pessoa pessoa) {
        StringBuilder sb = new StringBuilder();
        sb.append("Numero do rentalOrder ").append(rentalOrder.getId());
        sb.append("\nItens: ");
        for(RentalItem itens : rentalOrder.getRentalBooks()){
            sb.append("\n").append(itens);
        }
        sb.append("\nValor do rentalOrder ").append(rentalOrder.getTotal());
        sb.append("\nData do Pedido ").append(rentalOrder.getOrderDate());
        emailSender.emailSend(
            pessoa.getUser().getUsername(),
            "Pedido efetuado com sucesso",
            sb.toString()
        );
    }
}
