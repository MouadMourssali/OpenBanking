package org.sid.cutomerservice.listeners;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class CustomerMessageSender {


    public void sendMessage(RabbitTemplate rabbitTemplate, String customerExchange, String customerRoutingKey, Object customerData) {

        rabbitTemplate.convertAndSend(customerExchange, customerRoutingKey, customerData);
    }
}
