package com.forj.queue.queue;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QueueConfig {

    // exchanges
    @Value("${message.forj.exchange}")
    private String forjExchange;

    @Value("${message.complete.exchange}")
    private String completeExchange;

    @Value("${message.cancel.exchange}")
    private String cancelExchange;

    @Value("${message.error.exchange}")
    private String errorExchange;

    // forj - Queue
    @Value("${message.forj.queue.product}")
    private String forjProduct;

    @Value("${message.forj.queue.delivery}")
    private String forjDelivery;

    @Value("${message.forj.queue.delivery-history}")
    private String forjDeliveryHistory;

    // complete - Queue
    @Value("${message.complete.queue.delivery}")
    private String completeDelivery;

    @Value("${message.complete.queue.order}")
    private String completeOrder;

    // cancel - Queue
    @Value("${message.cancel.queue.product}")
    private String cancelProduct;

    // error - Queue
    @Value("${message.error.queue.order}")
    private String errorOrder;

    // exchanges
    @Bean public TopicExchange exchangeForj() { return new TopicExchange(forjExchange); }
    @Bean public TopicExchange exchangeComplete() { return new TopicExchange(completeExchange); }
    @Bean public TopicExchange exchangeCancel() { return new TopicExchange(cancelExchange); }
    @Bean public TopicExchange exchangeError() { return new TopicExchange(errorExchange); }

    // forj - Queue
    @Bean public Queue queueForjProduct() { return new Queue(forjProduct); }
    @Bean public Queue queueForjDelivery() { return new Queue(forjDelivery); }
    @Bean public Queue queueForjDeliveryHistory() { return new Queue(forjDeliveryHistory); }

    @Bean public Binding bindingForjProduct() { return BindingBuilder.bind(queueForjProduct()).to(exchangeForj()).with(forjProduct); }
    @Bean public Binding bindingForjDelivery() { return BindingBuilder.bind(queueForjDelivery()).to(exchangeForj()).with(forjDelivery); }
    @Bean public Binding bindingForjDeliveryHistory() { return BindingBuilder.bind(queueForjDeliveryHistory()).to(exchangeForj()).with(forjDeliveryHistory); }

    // complete - Queue
    @Bean public Queue queueCompleteDelivery() { return new Queue(completeDelivery); }
    @Bean public Queue queueCompleteOrder() { return new Queue(completeOrder); }

    @Bean public Binding bindingCompleteDelivery() { return BindingBuilder.bind(queueCompleteDelivery()).to(exchangeComplete()).with(completeDelivery); }
    @Bean public Binding bindingCompleteOrder() { return BindingBuilder.bind(queueCompleteOrder()).to(exchangeComplete()).with(completeOrder); }

    // cancel - Queue
    @Bean public Queue queueCancelProduct() { return new Queue(cancelProduct); }
    @Bean public Binding bindingCancelProduct() { return BindingBuilder.bind(queueCancelProduct()).to(exchangeCancel()).with(cancelProduct); }

    // Error - Queue
    @Bean public Queue queueErrorOrder() { return new Queue(errorOrder); }
    @Bean public Binding bindingErrorOrder() { return BindingBuilder.bind(queueErrorOrder()).to(exchangeError()).with(errorOrder); }
}
