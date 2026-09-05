package com.petshop.company.adapter.out.messaging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class OrderJmsListener {

    private static final Logger log = LoggerFactory.getLogger(OrderJmsListener.class);

    @JmsListener(destination = "order-queue")
    public void receiveMessage(String message) {
        log.info("Mensagem recebida na fila order-queue: {}", message);
    }
}
