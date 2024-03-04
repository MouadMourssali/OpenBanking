package org.sid.cutomerservice.listeners;


import org.sid.cutomerservice.event.CustomerEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;



@Component
@ConfigurationProperties(prefix="customer")
public class CustomerEventListener {
    private static final Logger LOG = LoggerFactory.getLogger(CustomerEventListener.class);

    private RabbitTemplate rabbitTemplate;
    private Exchange exchange;
    private CustomerMessageSender customerMessageSender;

    @Value("${customer.exchange.name}")
    private String customerExchangeName;
    @Value("${customer.queue.name}")
    private String customerQueueName;
    @Value("${customer.routing.key}")
    private String customerRoutingKey;

    public CustomerEventListener(RabbitTemplate rabbitTemplate, Exchange exchange,
                                     CustomerMessageSender customerMessageSender) {
        super();
        this.rabbitTemplate = rabbitTemplate;
        this.exchange = exchange;
        this.customerMessageSender = customerMessageSender;
    }

    @EventListener
    public void onApplicationEvent(CustomerEvent customerEvent) {

        this.customerMessageSender.sendMessage(rabbitTemplate, customerExchangeName, customerRoutingKey, customerEvent.getCustomer());

    }
}
