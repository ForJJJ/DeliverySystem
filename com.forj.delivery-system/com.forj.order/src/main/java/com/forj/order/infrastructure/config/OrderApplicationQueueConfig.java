package com.forj.order.infrastructure.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OrderApplicationQueueConfig {

    @Bean
    public Jackson2JsonMessageConverter producerJackson2MessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Value("${message.exchange}")
    private String exchange;

    @Value("${message.queue.delivery}")
    private String queueDelivery;

    @Value("${message.queue.delivery-history}")
    private String queueDeliveryHistory;

    @Bean public TopicExchange exchange() { return new TopicExchange(exchange); }

    @Bean public Queue queueDelivery() { return new Queue(queueDelivery); }
    @Bean public Queue queueDeliveryHistory() { return new Queue(queueDeliveryHistory); }

    @Bean public Binding bindingDelivery() { return BindingBuilder.bind(queueDelivery()).to(exchange()).with(queueDelivery); }
    @Bean public Binding bindingDeliveryHistory() { return BindingBuilder.bind(queueDeliveryHistory()).to(exchange()).with(queueDeliveryHistory); }
}
