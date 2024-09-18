package com.forj.product.infrastructure.messaging;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductMessageProducer {

    @Value("${message.error.queue.order}")
    private String productErrorQueue;

    @Value("${message.forj.queue.delivery}")
    private String deliveryQueue;

    private final RabbitTemplate rabbitTemplate;

    public void reduceQuantitySuccessNotifier(
            ProductOrderMessage message
    ) {
        rabbitTemplate.convertAndSend(deliveryQueue, message);
    }

    public void rollbackToOrder(
            ProductOrderMessage message, String errorMessage
    ) {

        ProductOrderErrorMessage productErrorMessage = new ProductOrderErrorMessage(
                message.orderId(),
                message.requestCompanyId(),
                message.receivingCompanyId(),
                message.productId(),
                message.quantity(),
                errorMessage
        );

        rabbitTemplate.convertAndSend(productErrorQueue, productErrorMessage);
    }
}
