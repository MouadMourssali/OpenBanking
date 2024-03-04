package org.sid.cutomerservice.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistrar;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.handler.annotation.support.DefaultMessageHandlerMethodFactory;

@Configuration
@ConfigurationProperties(prefix="customer")
public class CustomerEventProducerConfiguration {

    @Value("${customer.exchange.name}")
    private String customerExchangeName;

    @Value("${customer.queue.name}")
    private String customerQueueName;

    @Value("${customer.routing.key}")
    private String customerRoutingKeyName;

    @Bean
    public DirectExchange getCustomerDirectExchange() {
        return new DirectExchange(customerExchangeName);
    }

    @Bean
    public Queue getCustomerQueue() {
        return new Queue(customerQueueName);
    }

    @Bean
    public Binding bindCustomerQueueForExchange() {
        return BindingBuilder.bind(getCustomerQueue()).to(getCustomerDirectExchange()).with(customerRoutingKeyName);
    }

    @Bean
    public Jackson2JsonMessageConverter producerJackson2JsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public MappingJackson2MessageConverter consumerJackson2MessageConverter() {
        return new MappingJackson2MessageConverter();
    }

    @Bean
    public DefaultMessageHandlerMethodFactory messageHandlerMethodFactory() {
        DefaultMessageHandlerMethodFactory factory = new DefaultMessageHandlerMethodFactory();
        factory.setMessageConverter(consumerJackson2MessageConverter());
        return factory;
    }

    public void configureRabbitListeners(final RabbitListenerEndpointRegistrar registrar) {
        registrar.setMessageHandlerMethodFactory(messageHandlerMethodFactory());
    }
}
