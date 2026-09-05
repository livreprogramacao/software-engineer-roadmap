package com.petshop.company.adapter.out.messaging;

import com.petshop.company.domain.dto.OrderDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
public class OrderJmsProducer {

    private static final Logger log = LoggerFactory.getLogger(OrderJmsProducer.class);

    private final JmsTemplate jmsTemplate;
    private static final String QUEUE_NAME = "order-queue";

    public OrderJmsProducer(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    public void sendOrder(OrderDTO order) {
        log.info("Sending order {} to queue {}", order.getId(), QUEUE_NAME);
        jmsTemplate.convertAndSend(QUEUE_NAME, order);
        log.info("Order {} sent successfully", order.getId());
    }
}
