package com.forj.order.infrastructure.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OrderApplicationQueueConfig {

    @Value("${message.exchange}")
    private String exchange;

    @Value("${message.queue.delivery}")
    private String queueDelivery;

    @Bean public TopicExchange exchange() { return new TopicExchange(exchange); }

    @Bean public Queue queueProduct() { return new Queue(queueDelivery); }

    @Bean public Binding bindingProduct() { return BindingBuilder.bind(queueProduct()).to(exchange()).with(queueDelivery); }
}
